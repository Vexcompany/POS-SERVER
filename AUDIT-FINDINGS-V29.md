# POS-SERVER V29 — Audit Findings

**Scope:** Static inspection of the CFR 0.152 decompiled Java included in the V29 archive and stored under `decompiled/`. This is an audit of the available runtime bundle, not the original source tree.

**Important limitation:** The repository currently does not contain a confirmed original buildable source project or a verified reproducible build configuration. Findings below are based on decompiled code. No production database was queried or modified, and the candidate patches have not been compiled or tested in the POS runtime.

## Executive summary

Two candidate fixes are already available as separate draft PRs:

- [Draft PR #1 — Stock Opname product identity](https://github.com/Vexcompany/POS-SERVER/pull/1)
- [Draft PR #2 — Responsive product-search preview](https://github.com/Vexcompany/POS-SERVER/pull/2)
- [Draft PR #4 — SOM progress lookup with multiple matches](https://github.com/Vexcompany/POS-SERVER/pull/4)

Both remain **draft / not merged**. The audit found additional risks that should be addressed or explicitly tested before production deployment.

## Findings

### P1 — Stock Opname final submission can leave partially applied data

**File:** `decompiled/pos-server-v29/minimart/ui/view/StokOpnamMandiriPanel.java`, method `simpanDataFinal()`.

The finalization sequence creates the parent stock-opname record, adjusts product stock item by item, writes stock-commit records, creates detail rows, and only afterward marks the Stock Opname as submitted. These are separate service/DAO operations; no transaction boundary spanning the complete sequence is visible in this code.

Several DAO/service methods catch database exceptions and return `0` or `null`. The caller does not consistently check the stock-update, stock-commit, or detail-batch results before continuing. The UI also calls `resetData()` after attempting the submit even when the final update result is not `1`.

**Risk:** A failure halfway through finalization could leave stock adjusted without all corresponding detail/commit records, or leave a submission incomplete while the UI clears its table. Retrying could then apply some stock differences twice.

**Recommendation:** Treat finalization as an idempotent transaction: validate all source rows first; persist stock adjustments, commit rows, detail rows, and submitted status atomically where the database/storage architecture permits; check every affected-row/batch result; only clear UI state after confirmed success. If remote sync is necessarily separate, use a durable outbox/retry state rather than pretending the local transaction and remote upload are atomic.

**Validation needed:** Inject or simulate failures at each stage against a disposable database and verify that either all local changes commit once or none do.

### P1 — Product identity and displayed Stock Opname row can diverge

**Files:**
- `decompiled/pos-server-v29/minimart/ui/view/StokOpnamMandiriDialog.java`
- `decompiled/pos-server-v29/minimart/ui/view/StokOpnamMandiriPanel.java`

The existing edit path searched the SOM table using column 0 even though column 0 is the SOM detail ID and column 1 is the product ID. The save handler also loaded stock/HPP using the detail's existing product ID instead of the newly selected product ID, and did not update the detail's product ID. This is a concrete candidate for the reported A4/F4 stale-product symptom.

Candidate correction is in [Draft PR #1](https://github.com/Vexcompany/POS-SERVER/pull/1). A second static review also replaced the ambiguous `getBarang(String)` lookup with a direct `m_barang.ID = ?` lookup for this edit path, since the generic method matches either barcode or product ID. It still needs build and safe database verification.

### P1 — Product search preview blocks the Swing UI thread

**File:** `decompiled/pos-server-v29/minimart/ui/view/SearchPanelBarang.java`.

The original preview handler performs synchronous database lookups from keyboard navigation. It retrieves a product plus multiple barcode lookups and Stock Opname/constant status, which can stall the Swing event-dispatch thread for each row movement.

Candidate correction is in [Draft PR #2](https://github.com/Vexcompany/POS-SERVER/pull/2): a lightweight product lookup, debounce, and `SwingWorker` background loading. It remains uncompiled and untested in the actual application.

### P2 — Rack filter argument is ignored by the product DAO query

**File:** `decompiled/pos-server-v29/minimart/engine/daoimpl/BarangDaoImpl.java`, method `getBarangsByJenisRakNama(...)`.

The method accepts `rak` and normalizes it, but the SQL filters by product type and name only; no rack column is included in the WHERE clause.

**Risk:** Callers expecting a rack-specific result may see products from other racks. This is a confirmed mismatch between the method arguments and the query, but the affected user-facing screen(s) and intended rack semantics should be confirmed before changing behavior.

### P2 — Stock Opname “in progress” lookup can silently report no match

**File:** `decompiled/pos-server-v29/minimart/engine/daoimpl/StokOpnamMandiriDetailDaoImpl.java`, method `getStokOpnamMandiriDetailOnProgress(...)`.

The query can return multiple rows for the same product when it appears in more than one unsubmitted Stock Opname. The method uses `queryForObject()`, catches the resulting data-access exception, and returns an empty string.

**Risk:** Multiple matching records can be treated like no active Stock Opname, potentially causing the UI to show stock when it should be hidden or fail to flag a product as in progress. [Draft PR #4](https://github.com/Vexcompany/POS-SERVER/pull/4) adds `LIMIT 1` because the caller only needs to know whether any qualifying row exists. This is a candidate static fix; validate the multiple-active-SOM business rule in a disposable database before merge.

### P2 — Batch-update DAO method is an explicit runtime failure

**File:** `decompiled/pos-server-v29/minimart/engine/daoimpl/StokOpnamMandiriDetailDaoImpl.java`, method `updateStokOpnamMandiriDetailBatch(...)`.

The implementation throws `UnsupportedOperationException("Not supported yet.")`.

**Risk:** Any caller of this method will fail at runtime. The repository code-search connector did not return reliable caller matches, so whether a current UI workflow reaches it is **not confirmed**. Check all call sites before deciding whether to implement it or remove the unused API.

### P2 — Connection configuration uses a non-pooling DataSource

**File:** `decompiled/pos-server-v29/minimart/engine/controller/ConnectionManager.java`.

The configured DataSource is Spring `DriverManagerDataSource`, which creates connections directly rather than providing a connection pool.

**Risk:** Frequent short-lived DAO calls can add connection setup overhead and amplify UI latency. This is a performance concern, not proof that connection creation is the only cause of the observed delay. Any pool change must account for the deployed MySQL version, app shutdown/reconnect behavior, and the library versions in the runtime bundle.

### P3 — Database errors are often swallowed into default values

Several DAO methods catch `DataAccessException`, print only the exception message, and return `0`, `null`, or an empty string. That can make an actual database failure indistinguishable from a legitimate “not found” or “no change” result.

**Recommendation:** Add structured logging with operation and entity identifiers (excluding credentials and sensitive customer data), and propagate a clear success/failure result to the UI. Avoid changing this globally in one patch; review call sites so failure handling remains compatible.

### P1 — Saving an input revision is split across two independent updates

**File:** `decompiled/pos-server-v29/minimart/ui/view/StokOpnamMandiriPanel.java`, method `saveButtonActionPerformed(...)`; DAO methods `updateRevisi1(...)` and `updateRevisi2(...)`.

The UI sets the parent Stock Opname's `tanggalInputN` timestamp and updates the parent row first. It then separately copies revision values to the next input using `updateRevisi1` or `updateRevisi2`. If the second operation fails, the first timestamp may already be persisted. A subsequent save can then hit the “input already saved, refresh data” guard, even though the revision-copy operation failed.

The UI also treats an update count of zero as failure. Depending on the database driver's affected-row semantics, a successful update that does not change any values can report zero, so this condition needs verification.

**Recommendation:** Save the input timestamp and its corresponding detail-row revision copy in one transaction. Check SQL execution/exception status separately from “rows changed,” and show a recoverable error without marking the input complete when either step fails.

### P1 — Final Stock Opname ID is allocated with a non-atomic MAX-like lookup

**Files:** `decompiled/pos-server-v29/minimart/ui/view/StokOpnamMandiriPanel.java`, method `simpanDataFinal()`; `decompiled/pos-server-v29/minimart/engine/daoimpl/StokOpnamDaoImpl.java`, method `getLastId()`.

Final submission assigns the new stock-opname ID using `getLastId() + 1`. The DAO selects the highest existing ID, and if that query throws an exception it catches the error and returns the default value `0`. This approach is not safe against two concurrent submissions: both can read the same highest ID and attempt to insert the same next ID. A database lookup failure can also lead to an attempted ID of `1`.

**Recommendation:** Use a database-generated auto-increment/sequence key where schema compatibility allows, or allocate IDs through a transaction-safe mechanism with a uniqueness constraint and explicit retry/error handling. Never interpret failure to read the last ID as an empty table.

### P2 — Stock Opname product list performs one extra package-stock query per product

**File:** `decompiled/pos-server-v29/minimart/ui/view/StokOpnamPanelNew.java`, methods that load rows for a new Stock Opname; service/DAO: `PaketService.getStokBarangByDetailPaket(...)` and `PaketDaoImpl.getStokBarangByDetailPaket(...)`.

When loading each product row for the Toko location, the UI calls `getStokBarangByDetailPaket(barang.getId())`. That method executes a separate aggregate query over `m_paket`, `m_paket_detail`, and `m_barang`. The same pattern appears in two row-loading paths.

**Risk:** Loading a large Stock Opname list can execute hundreds of additional database round trips, adding latency and increasing database load.

**Recommendation:** Replace the per-row query with one aggregate query for all product IDs (grouped by component product ID), build an in-memory map, and populate the rows from that map. Keep the current calculation semantics and compare old/new results for package products in a test database before replacing it.

## Validation plan before release

1. Recover the original source project or document a reproducible, verified build path for the exact deployed V29 runtime.
2. Build in a separate branch/CI environment; do not compile decompiled classes and assume the resulting application is equivalent to the original JAR.
3. Use a disposable database or backup restored to an isolated test environment.
4. Search: rapid Up/Down, Enter on first/last row, no selection, search misses, cached/repeated products, and slow database response.
5. Stock Opname: A4 → F4 → another product → F4; verify displayed product ID, detail ID, stock snapshot, HPP, and the saved database row after every step.
6. Final submit: test a zero-difference item, positive/negative stock adjustments, and simulated failures during stock update, commit insert, detail insert, status update, and sync. Confirm retry cannot double-adjust stock.
7. Rack filtering: compare selected rack results against the database and intended screen behavior.
8. Only merge/deploy after a reviewer has checked the source-level patch and the test results are recorded.

## Status

- Draft PR #1: candidate Stock Opname identity fix; static inspection only.
- Draft PR #2: candidate product-search responsiveness fix; static inspection only.
- Draft PR #3: consolidated audit findings and validation plan.
- Draft PR #4: candidate fix for multiple active SOM rows; static SQL review only.
- This report documents further findings; it does not apply any production changes.
- No PR has been merged and no operational POS database has been modified by this audit.
