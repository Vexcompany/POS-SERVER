# POS-SERVER v29 LOCAL database test setup

This document separates the LOCAL runtime database from the disposable performance benchmark.

## Verified v29 LOCAL login and database defaults

KaryawanService.authLoginOnline() permits the LOCAL development shortcut only when environment variables POS_LOCAL_USERNAME and POS_LOCAL_PASSWORD are set and match the supplied login. The shortcut creates a LOCAL credential; it does not authenticate by querying m_karyawan.

The LOCAL database credential defaults are:

- host: 127.0.0.1
- port: 3306
- database: minimarket
- username: root
- password: empty string
- branch name/code: LOCAL

The database values can be overridden with Java system properties:

    -Dpos.local.db.host=127.0.0.1
    -Dpos.local.db.port=3306
    -Dpos.local.db.name=minimarket
    -Dpos.local.db.user=root
    -Dpos.local.db.password=...

ConnectionManager.configure() builds the MySQL URL as jdbc:mysql://<host>:<port>/<database> using those credentials.

## Database separation

    minimarket              <- LOCAL application database
    minimarket_benchmark    <- disposable query-performance benchmark

The benchmark contains only six tables: m_cabang, m_jenis, m_karyawan, m_role, m_barang, and m_barcode. It is designed for query profiling with 100,000 synthetic products and 300,000 barcodes, not for a complete POS workflow.

## Safe VPS checks

These commands are read-only:

    sudo mysql -e "SHOW DATABASES LIKE 'minimarket';"
    sudo mysql minimarket -e "SHOW TABLES;"
    sudo mysql minimarket_benchmark -e "SHOW TABLES;"

If minimarket is empty, do not import 00_synthetic_core_benchmark.sql into it. That script drops/recreates benchmark tables only inside minimarket_benchmark, and it cannot supply the full operational schema.

## Export the original schema safely (only from a source DB that has the real v29 schema)

The VPS discussed in this setup currently has no confirmed operational v29 schema. Do not run this against an empty `minimarket` database and do not treat an empty export as useful evidence. Run it on the original/source MySQL host that actually contains the v29 branch database:

```bash
sudo mysqldump --no-data --routines --triggers --events \
  --single-transaction --skip-lock-tables minimarket \
  > "$HOME/minimarket-v29-schema-only.sql"

test -s "$HOME/minimarket-v29-schema-only.sql"
sha256sum "$HOME/minimarket-v29-schema-only.sql"
```

- `--no-data` excludes table rows; `--routines --triggers --events` captures programmable schema objects needed by some workflows.
- This does not export users/grants. Review the output for `DEFINER` clauses or internal hostnames before sharing it.
- Keep the export outside the application database. Do not import it directly into `minimarket` or production. First review it against SOURCE-SCHEMA-INVENTORY.md, then import into a newly named disposable schema after resolving the database name and any DEFINER dependencies.
- If the original v29 database is unavailable, stop here rather than synthesizing missing tables/procedures from DAO names.

## Run the packaged application (only after the real schema is present)

The extracted package path is:

    extracted/pos-server-v29/POS server v29/minimarket.jar

The package README specifies java -jar "minimarket.jar". For a LOCAL development login, set throwaway test credentials and run from the package directory. Do not reuse production credentials:

    cd ~/POS-SERVER/extracted/pos-server-v29/"POS server v29"
    export POS_LOCAL_USERNAME='local-test-user'
    export POS_LOCAL_PASSWORD='replace-with-a-throwaway-password'
    java -Dpos.local.db.host=127.0.0.1 \
         -Dpos.local.db.port=3306 \
         -Dpos.local.db.name=minimarket \
         -Dpos.local.db.user=root \
         -Dpos.local.db.password='' \
         -jar minimarket.jar

This starts the packaged desktop application; it does not create or migrate the database. A complete operational v29 schema is still required for successful LOCAL database initialization and full POS workflow. No claim is made that this command has been run on the VPS.

## Schema evidence

See SOURCE-SCHEMA-INVENTORY.md for the DAO/model/RowMapper table map and SCHEMA-ROADMAP.md for known gaps. The repository does not currently contain a confirmed complete operational v29 SQL dump.
