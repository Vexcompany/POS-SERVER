/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model_online;

import java.util.Date;

public class PelunasanPiutangO2O {
    private String id;
    private int id_pelunasan_piutang_offline;
    private String t_penjualan_id;
    private String m_karyawan_id;
    private Date tanggal_pelunasan;
    private String keterangan;
    private Date created;
    private Date updated;
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

    public int getId_pelunasan_piutang_offline() {
        return this.id_pelunasan_piutang_offline;
    }

    public void setId_pelunasan_piutang_offline(int id_pelunasan_piutang_offline) {
        this.id_pelunasan_piutang_offline = id_pelunasan_piutang_offline;
    }

    public String getT_penjualan_id() {
        return this.t_penjualan_id;
    }

    public void setT_penjualan_id(String t_penjualan_id) {
        this.t_penjualan_id = t_penjualan_id;
    }

    public String getM_karyawan_id() {
        return this.m_karyawan_id;
    }

    public void setM_karyawan_id(String m_karyawan_id) {
        this.m_karyawan_id = m_karyawan_id;
    }

    public Date getTanggal_pelunasan() {
        return this.tanggal_pelunasan;
    }

    public void setTanggal_pelunasan(Date tanggal_pelunasan) {
        this.tanggal_pelunasan = tanggal_pelunasan;
    }

    public String getKeterangan() {
        return this.keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
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

