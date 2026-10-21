/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model_online;

import java.util.Date;

public class PengembalianBarangO2O {
    private String id;
    private long id_pengembalian_barang_offline;
    private String m_customer_id;
    private String t_penjualan_id;
    private String m_karyawan_id;
    private Date tanggal;
    private long total_pengembalian;
    private String keterangan;
    private Date created;
    private Date updated;
    private String pelunasan;
    private int cetak_voucher;
    private int id_cabang;
    private String no_hp;

    public String getNo_hp() {
        return this.no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public PengembalianBarangO2O(String id, long id_pengembalian_barang_offline, String m_customer_id, String t_penjualan_id, String m_karyawan_id, Date tanggal, long total_pengembalian, String keterangan, Date created, Date updated, String pelunasan, int cetak_voucher, int id_cabang) {
        this.id = id;
        this.id_pengembalian_barang_offline = id_pengembalian_barang_offline;
        this.m_customer_id = m_customer_id;
        this.t_penjualan_id = t_penjualan_id;
        this.m_karyawan_id = m_karyawan_id;
        this.tanggal = tanggal;
        this.total_pengembalian = total_pengembalian;
        this.keterangan = keterangan;
        this.created = created;
        this.updated = updated;
        this.pelunasan = pelunasan;
        this.cetak_voucher = cetak_voucher;
        this.id_cabang = id_cabang;
    }

    public PengembalianBarangO2O() {
    }

    public int getCetak_voucher() {
        return this.cetak_voucher;
    }

    public void setCetak_voucher(int cetak_voucher) {
        this.cetak_voucher = cetak_voucher;
    }

    public String getT_penjualan_id() {
        return this.t_penjualan_id;
    }

    public void setT_penjualan_id(String t_penjualan_id) {
        this.t_penjualan_id = t_penjualan_id;
    }

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

    public long getId_pengembalian_barang_offline() {
        return this.id_pengembalian_barang_offline;
    }

    public void setId_pengembalian_barang_offline(long id_pengembalian_barang_offline) {
        this.id_pengembalian_barang_offline = id_pengembalian_barang_offline;
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

    public Date getTanggal() {
        return this.tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public long getTotal_pengembalian() {
        return this.total_pengembalian;
    }

    public void setTotal_pengembalian(long total_pengembalian) {
        this.total_pengembalian = total_pengembalian;
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

    public String getPelunasan() {
        return this.pelunasan;
    }

    public void setPelunasan(String pelunasan) {
        this.pelunasan = pelunasan;
    }

    public Date getUpdated() {
        return this.updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}

