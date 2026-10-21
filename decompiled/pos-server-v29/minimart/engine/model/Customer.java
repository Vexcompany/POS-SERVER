/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 */
package minimart.engine.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Customer {
    private String id;
    private String ktp;
    private String nama;
    private String alamat;
    private Date tanggalLahir;
    private String kota;
    private String telepon;
    private long hutang;
    private long point;
    private boolean available;
    private Date created;
    private Date updated;
    private int limitKredit;
    private int top;
    private String tipe;
    private int idCabang;
    private String keterangan;
    private String tag;
    private String namaPerusahaan;
    private String createdByCabang;
    private String nomorRekening;
    private String bank;

    public String getNomorRekening() {
        return this.nomorRekening;
    }

    public void setNomorRekening(String nomorRekening) {
        this.nomorRekening = nomorRekening;
    }

    public String getBank() {
        return this.bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getId() {
        return this.id;
    }

    public int getIdCabang() {
        return this.idCabang;
    }

    public void setIdCabang(int id) {
        this.idCabang = id;
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

    public long getHutang() {
        return this.hutang;
    }

    public void setHutang(long hutang) {
        this.hutang = hutang;
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

    public String getKota() {
        return this.kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public long getPoint() {
        return this.point;
    }

    public void setPoint(long point) {
        this.point = point;
    }

    public String getKtp() {
        return this.ktp;
    }

    public void setKtp(String ktp) {
        this.ktp = ktp;
    }

    public Date getTanggalLahir() {
        return this.tanggalLahir;
    }

    public void setTanggalLahir(Date tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public int getLimitKredit() {
        return this.limitKredit;
    }

    public void setLimitKredit(int limitKredit) {
        this.limitKredit = limitKredit;
    }

    public int getTop() {
        return this.top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public String getTipe() {
        return this.tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getKeterangan() {
        return this.keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getNamaPerusahaan() {
        return this.namaPerusahaan;
    }

    public void setNamaPerusahaan(String namaPerusahaan) {
        this.namaPerusahaan = namaPerusahaan;
    }

    public String getCreatedByCabang() {
        return this.createdByCabang;
    }

    public void setCreatedByCabang(String createdByCabang) {
        this.createdByCabang = createdByCabang;
    }
}

