# Local testing — POS v29

The v29 login now has two paths:

- **Online:** normal login continues to `api.dev.paperpen.sinarmart.id`.
- **Local:** when the submitted credentials match the local credentials supplied through environment variables, the API call is skipped and a local database credential is used.

## Local credentials

Set these only in the runtime environment; do not commit the password to Git:

```bash
export POS_LOCAL_USERNAME=GIO_415
export POS_LOCAL_PASSWORD='<your local password>'
```

The local database defaults are:

- host: `127.0.0.1`
- port: `3306`
- database: `minimarket`
- MySQL user: `root`
- MySQL password: empty

They can be overridden with `pos.local.db.host`, `pos.local.db.port`, `pos.local.db.name`, `pos.local.db.user`, and `pos.local.db.password` JVM system properties.

The local path disables the employee/SOM/package online synchronization performed during online startup. Normal online login behavior is left intact.

A compatible local MySQL schema is still required for the POS database operations. The local login path is an authentication/API bypass for development, not a database/schema generator.
