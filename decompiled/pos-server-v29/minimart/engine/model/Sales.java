/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

public class Sales {
    private String id;
    private String ktp;
    private String nama;
    private String alamat;
    private String telepon;
    private int target;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKtp() {
        return this.ktp;
    }

    public void setKtp(String ktp) {
        this.ktp = ktp;
    }

    public String getNama() {
        return this.nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return this.alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelepon() {
        return this.telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public int getTarget() {
        return this.target;
    }

    public void setTarget(int target) {
        this.target = target;
    }
}

