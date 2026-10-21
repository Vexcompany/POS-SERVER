/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

public class Cabang {
    private int id;
    private String nama;
    private String Alamat;
    private String tipeToko;
    private String tipeCabang;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return this.nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return this.Alamat;
    }

    public void setAlamat(String Alamat) {
        this.Alamat = Alamat;
    }

    public String getTipeToko() {
        return this.tipeToko;
    }

    public void setTipeToko(String tipeToko) {
        this.tipeToko = tipeToko;
    }

    public String getTipeCabang() {
        return this.tipeCabang;
    }

    public void setTipeCabang(String tipeCabang) {
        this.tipeCabang = tipeCabang;
    }
}

