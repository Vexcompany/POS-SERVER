/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

public class JSONResponseMigrasi {
    String status;
    int id_migrasi_header;

    public JSONResponseMigrasi() {
    }

    public JSONResponseMigrasi(String status, int id_migrasi_header) {
        this.status = status;
        this.id_migrasi_header = id_migrasi_header;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId_migrasi_header() {
        return this.id_migrasi_header;
    }

    public void setId_migrasi_header(int id_migrasi_header) {
        this.id_migrasi_header = id_migrasi_header;
    }
}

