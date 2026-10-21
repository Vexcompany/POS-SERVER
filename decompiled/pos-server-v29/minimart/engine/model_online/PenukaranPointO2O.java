/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model_online;

import java.util.Date;

public class PenukaranPointO2O {
    private String id;
    private int id_penukaran_point_offline;
    private String m_customer_id;
    private String m_karyawan_id;
    private int jumlah_point;
    private String keterangan;
    private Date tanggal;
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

    public int getId_penukaran_point_offline() {
        return this.id_penukaran_point_offline;
    }

    public void setId_penukaran_point_offline(int id_penukaran_point_offline) {
        this.id_penukaran_point_offline = id_penukaran_point_offline;
    }

    public String getM_customer_id() {
        return this.m_customer_id;
    }

    public void setM_customer_id(String m_customer_id) {
        this.m_customer_id = m_customer_id;
    }

    public String getM_karyawan_id() {
        return this.m_karyawan_id;
    }

    public void setM_karyawan_id(String m_karyawan_id) {
        this.m_karyawan_id = m_karyawan_id;
    }

    public int getJumlah_point() {
        return this.jumlah_point;
    }

    public void setJumlah_point(int jumlah_point) {
        this.jumlah_point = jumlah_point;
    }

    public String getKeterangan() {
        return this.keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public Date getTanggal() {
        return this.tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
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

