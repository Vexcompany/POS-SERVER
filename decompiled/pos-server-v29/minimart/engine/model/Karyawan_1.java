/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class Karyawan_1 {
    private String id;
    private String nama;
    private String password;
    private String role;
    private long gaji;
    private boolean available;
    private Date created;
    private Date updated;
    private String id_cabang;
    private String tipe_toko;

    public String getId_cabang() {
        return this.id_cabang;
    }

    public void setId_cabang(String id_cabang) {
        this.id_cabang = id_cabang;
    }

    public String getTipe_toko() {
        return this.tipe_toko;
    }

    public void setTipe_toko(String tipe_toko) {
        this.tipe_toko = tipe_toko;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return this.nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getGaji() {
        return this.gaji;
    }

    public void setGaji(long gaji) {
        this.gaji = gaji;
    }

    public boolean isAvailable() {
        return this.available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return this.updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}

