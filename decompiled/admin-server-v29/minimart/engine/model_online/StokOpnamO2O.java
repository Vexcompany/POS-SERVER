/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model_online;

import java.util.Date;

public class StokOpnamO2O {
    private String id;
    private int id_stok_opnam_offline;
    private String m_karyawan_id;
    private String status_opnam;
    private String lokasi;
    private String tipe;
    private String tipe_name;
    private Date tanggal;
    private String keterangan;
    private Date updated;
    private Date created;
    private int id_cabang;

    public int getId_cabang() {
        return this.id_cabang;
    }

    public void setId_cabang(int id_cabang) {
        this.id_cabang = id_cabang;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getId_stok_opnam_offline() {
        return this.id_stok_opnam_offline;
    }

    public void setId_stok_opnam_offline(int id_stok_opnam_offline) {
        this.id_stok_opnam_offline = id_stok_opnam_offline;
    }

    public String getM_karyawan_id() {
        return this.m_karyawan_id;
    }

    public void setM_karyawan_id(String m_karyawan_id) {
        this.m_karyawan_id = m_karyawan_id;
    }

    public String getStatus_opnam() {
        return this.status_opnam;
    }

    public void setStatus_opnam(String status_opnam) {
        this.status_opnam = status_opnam;
    }

    public String getLokasi() {
        return this.lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getTipe() {
        return this.tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getTipe_name() {
        return this.tipe_name;
    }

    public void setTipe_name(String tipe_name) {
        this.tipe_name = tipe_name;
    }

    public Date getTanggal() {
        return this.tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getKeterangan() {
        return this.keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public Date getUpdated() {
        return this.updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}

