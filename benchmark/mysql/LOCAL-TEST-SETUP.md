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
