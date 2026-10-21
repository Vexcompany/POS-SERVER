/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model_online;

import java.util.Date;

public class StokOpnamDetailO2O {
    private String id;
    private int id_stok_opnam_detail_offline;
    private int stok_opnam_id;
    private String m_barang_id;
    private float jumlah_komputer;
    private float jumlah_toko;
    private Date updated;
    private Date created;
    private long total;
    private int id_cabang;

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
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

    public int getId_stok_opnam_detail_offline() {
        return this.id_stok_opnam_detail_offline;
    }

    public void setId_stok_opnam_detail_offline(int id_stok_opnam_detail_offline) {
        this.id_stok_opnam_detail_offline = id_stok_opnam_detail_offline;
    }

    public int getStok_opnam_id() {
        return this.stok_opnam_id;
    }

    public void setStok_opnam_id(int stok_opnam_id) {
        this.stok_opnam_id = stok_opnam_id;
    }

    public String getM_barang_id() {
        return this.m_barang_id;
    }

    public void setM_barang_id(String m_barang_id) {
        this.m_barang_id = m_barang_id;
    }

    public float getJumlah_komputer() {
        return this.jumlah_komputer;
    }

    public void setJumlah_komputer(float jumlah_komputer) {
        this.jumlah_komputer = jumlah_komputer;
    }

    public float getJumlah_toko() {
        return this.jumlah_toko;
    }

    public void setJumlah_toko(float jumlah_toko) {
        this.jumlah_toko = jumlah_toko;
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

