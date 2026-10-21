/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class SupplierOnline {
    private String id;
    private String idOffline;
    private String nama;
    private String alamat;
    private String kota;
    private String telepon;
    private String contactPerson;
    private boolean available;
    private Date created;
    private Date updated;
    private int top;
    private String tipeToko;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdOffline() {
        return this.idOffline;
    }

    public void setIdOffline(String idOffline) {
        this.idOffline = idOffline;
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

    public String getKota() {
        return this.kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getTelepon() {
        return this.telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getContactPerson() {
        return this.contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public boolean isAvailable() {
        return this.available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
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

    public int getTop() {
        return this.top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public String getTipeToko() {
        return this.tipeToko;
    }

    public void setTipeToko(String tipeToko) {
        this.tipeToko = tipeToko;
    }
}

