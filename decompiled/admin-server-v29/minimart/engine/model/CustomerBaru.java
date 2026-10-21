/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class CustomerBaru {
    private long id;
    private String ktp;
    private String nama;
    private String alamat;
    private String telepon;
    private String kota;
    private String createdBy;
    private Date created;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
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

    public String getKota() {
        return this.kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}

