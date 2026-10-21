/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.Date;

public class BatchPengiriman {
    int id;
    String kodeBatch;
    int idCabangPenerima;
    Date tanggal;
    String nopol;
    String namaDriver;
    String status;
    Date created_at;
    Date updated_at;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKodeBatch() {
        return this.kodeBatch;
    }

    public void setKodeBatch(String kodeBatch) {
        this.kodeBatch = kodeBatch;
    }

    public int getIdCabangPenerima() {
        return this.idCabangPenerima;
    }

    public void setIdCabangPenerima(int idCabangPenerima) {
        this.idCabangPenerima = idCabangPenerima;
    }

    public Date getTanggal() {
        return this.tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getNopol() {
        return this.nopol;
    }

    public void setNopol(String nopol) {
        this.nopol = nopol;
    }

    public String getNamaDriver() {
        return this.namaDriver;
    }

    public void setNamaDriver(String namaDriver) {
        this.namaDriver = namaDriver;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return this.updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public BatchPengiriman(int id, String kodeBatch, int idCabangPenerima, Date tanggal, String nopol, String namaDriver, String status, Date created_at, Date updated_at) {
        this.id = id;
        this.kodeBatch = kodeBatch;
        this.idCabangPenerima = idCabangPenerima;
        this.tanggal = tanggal;
        this.nopol = nopol;
        this.namaDriver = namaDriver;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public BatchPengiriman() {
    }
}

