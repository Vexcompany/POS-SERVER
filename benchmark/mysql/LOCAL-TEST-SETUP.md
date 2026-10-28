# POS-SERVER v29 LOCAL database test setup

This document separates the **LOCAL runtime database** from the disposable performance benchmark.

## Verified v29 runtime defaults

`KaryawanService.authLoginOnline()` creates a LOCAL `DbCredential` with these defaults:

- host: `127.0.0.1`
- port: `3306`
- database: `minimarket`
- username: `root`
- password: empty string
- branch name/code: `LOCAL`

The values can be overridden with Java system properties:

```text
-Dpos.local.db.host=127.0.0.1
-Dpos.local.db.port=3306
-Dpos.local.db.name=minimarket
-Dpos.local.db.user=root
-Dpos.local.db.password=...
```

`ConnectionManager.configure()` builds the MySQL URL as `jdbc:mysql://<host>:<port>/<database>` using those credentials.

## Important database separation

Do **not** point the POS application at `minimarket_benchmark` just because that database contains 100,000 synthetic products. The benchmark intentionally contains only a small subset of the v29 schema.

Use this layout:

```text
minimarket              <- LOCAL application database
minimarket_benchmark    <- disposable query-performance benchmark
```

The benchmark is currently limited to `m_cabang`, `m_jenis`, `m_karyawan`, `m_role`, `m_barang`, and `m_barcode`, with 100,000 synthetic products and 300,000 barcodes. It is not sufficient for a full POS workflow.

## Safe VPS preparation

Check the LOCAL database without modifying it:

```bash
sudo mysql -e "SHOW DATABASES LIKE 'minimarket';"
sudo mysql minimarket -e "SHOW TABLES;"
```

If `minimarket` is empty, **do not import the benchmark SQL into it**. A complete operational v29 dump is not present in this repository yet.

## Running the packaged POS v29

The extracted package contains:

```text
extracted/pos-server-v29/POS server v29/minimarket.jar
```

The package README specifies:

```bash
java -jar "minimarket.jar"
```

For a LOCAL test with explicit database settings, run from the package directory:

```bash
cd ~/POS-SERVER/extracted/pos-server-v29/"POS server v29"
java -Dpos.local.db.host=127.0.0.1 \
     -Dpos.local.db.port=3306 \
     -Dpos.local.db.name=minimarket \
     -Dpos.local.db.user=root \
     -Dpos.local.db.password='' \
     -jar minimarket.jar
```

The application still requires the **real v29 operational schema** in `minimarket` before a complete login/POS workflow can be expected to work.

## Next schema-extraction priority

Use the decompiled v29 source as evidence and continue extracting dependencies in this order:

1. LOCAL login and employee/role tables.
2. Product, barcode, category, rack, package and pricing tables.
3. Sales header/detail, temporary sales, payment/shift tables.
4. Stock and stock-opname tables.
5. Customer/member and returns tables.
6. Synchronization/outbox tables used by LOCAL/online sync.

Only add a table/column to a synthetic schema when a DAO, service, model, or row mapper provides source evidence. Never treat the six-table benchmark as a complete production schema.
