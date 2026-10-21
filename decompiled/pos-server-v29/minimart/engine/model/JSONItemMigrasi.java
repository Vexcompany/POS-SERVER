/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

public class JSONItemMigrasi {
    int id_migration_header;
    int id_cabang_penerima;
    int id_cabang_pengirim;
    String id_item;
    float jumlah_permintaan;
    String status_1;
    String tipe;
    String satuan;
    long hpp;

    public long getHpp() {
        return this.hpp;
    }

    public void setHpp(long hpp) {
        this.hpp = hpp;
    }

    public JSONItemMigrasi(int id_migration_header, int id_cabang_penerima, int id_cabang_pengirim, String id_item, float jumlah_permintaan, String status_1, String tipe, String satuan, int hpp) {
        this.id_migration_header = id_migration_header;
        this.id_cabang_penerima = id_cabang_penerima;
        this.id_cabang_pengirim = id_cabang_pengirim;
        this.id_item = id_item;
        this.jumlah_permintaan = jumlah_permintaan;
        this.status_1 = status_1;
        this.tipe = tipe;
        this.satuan = satuan;
        this.hpp = hpp;
    }

    public JSONItemMigrasi() {
    }

    public JSONItemMigrasi(int id_migration_header, int id_cabang_penerima, int id_cabang_pengirim, String id_item, float jumlah_permintaan, String status_1, String tipe, String satuan) {
        this.id_migration_header = id_migration_header;
        this.id_cabang_penerima = id_cabang_penerima;
        this.id_cabang_pengirim = id_cabang_pengirim;
        this.id_item = id_item;
        this.jumlah_permintaan = jumlah_permintaan;
        this.status_1 = status_1;
        this.tipe = tipe;
        this.satuan = satuan;
    }

    public int getId_migration_header() {
        return this.id_migration_header;
    }

    public void setId_migration_header(int id_migration_header) {
        this.id_migration_header = id_migration_header;
    }

    public int getId_cabang_penerima() {
        return this.id_cabang_penerima;
    }

    public void setId_cabang_penerima(int id_cabang_penerima) {
        this.id_cabang_penerima = id_cabang_penerima;
    }

    public int getId_cabang_pengirim() {
        return this.id_cabang_pengirim;
    }

    public void setId_cabang_pengirim(int id_cabang_pengirim) {
        this.id_cabang_pengirim = id_cabang_pengirim;
    }

    public String getId_item() {
        return this.id_item;
    }

    public void setId_item(String id_item) {
        this.id_item = id_item;
    }

    public float getJumlah_permintaan() {
        return this.jumlah_permintaan;
    }

    public void setJumlah_permintaan(float jumlah_permintaan) {
        this.jumlah_permintaan = jumlah_permintaan;
    }

    public String getStatus_1() {
        return this.status_1;
    }

    public void setStatus_1(String status_1) {
        this.status_1 = status_1;
    }

    public String getTipe() {
        return this.tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getSatuan() {
        return this.satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }
}

