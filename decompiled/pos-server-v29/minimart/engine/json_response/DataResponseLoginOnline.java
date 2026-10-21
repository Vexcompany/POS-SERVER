/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.json_response;

import minimart.engine.model.DbCredential;
import minimart.engine.model.Karyawan_1;

public class DataResponseLoginOnline {
    Karyawan_1 karyawan;
    DbCredential[] list_cabang;

    public Karyawan_1 getKaryawan() {
        return this.karyawan;
    }

    public void setKaryawan(Karyawan_1 karyawan) {
        this.karyawan = karyawan;
    }

    public DbCredential[] getList_cabang() {
        return this.list_cabang;
    }

    public void setList_cabang(DbCredential[] list_cabang) {
        this.list_cabang = list_cabang;
    }
}

