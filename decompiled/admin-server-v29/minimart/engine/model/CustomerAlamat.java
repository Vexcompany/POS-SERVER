/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class CustomerAlamat {
    private Integer id;
    private String idCustomer;
    private String judul;
    private String penerima;
    private String alamat;
    private String notesDriver;
    private String telepon;
    private String latitude;
    private String longitude;
    private Date deletedAt;
    private Date createdAt;
    private Date updatedAt;
    private Boolean isDefault;
    private String idRegKelurahan;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdCustomer() {
        return this.idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getJudul() {
        return this.judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPenerima() {
        return this.penerima;
    }

    public void setPenerima(String penerima) {
        this.penerima = penerima;
    }

    public String getAlamat() {
        return this.alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNotesDriver() {
        return this.notesDriver;
    }

    public void setNotesDriver(String notesDriver) {
        this.notesDriver = notesDriver;
    }

    public String getTelepon() {
        return this.telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Date getDeletedAt() {
        return this.deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getIsDefault() {
        return this.isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getIdRegKelurahan() {
        return this.idRegKelurahan;
    }

    public void setIdRegKelurahan(String idRegKelurahan) {
        this.idRegKelurahan = idRegKelurahan;
    }
}

