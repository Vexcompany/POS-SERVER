/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.TransaksiOnlineDetail;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class TransaksiOnlineDetailRowMapper
implements ParameterizedRowMapper<TransaksiOnlineDetail> {
    public TransaksiOnlineDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
        TransaksiOnlineDetail tod = new TransaksiOnlineDetail();
        tod.setId(rs.getLong("ID"));
        tod.setTransaksi_id(rs.getInt("TRANSAKSI_ID"));
        tod.setId_cabang(rs.getInt("ID_CABANG"));
        tod.setItem_id(rs.getString("ITEM_ID"));
        tod.setM_barang_id(rs.getString("M_BARANG_ID"));
        tod.setJumlah_barang(rs.getInt("JUMLAH_BARANG"));
        tod.setJumlah_barang_revisi(rs.getInt("JUMLAH_BARANG_REVISI"));
        tod.setSatuan(rs.getString("SATUAN"));
        tod.setHarga_jual(rs.getInt("HARGA_JUAL"));
        tod.setDiscount_per_item(rs.getInt("DISCOUNT_PER_ITEM"));
        tod.setTotal_harga(rs.getInt("TOTAL_HARGA"));
        tod.setTotal_keuntungan(rs.getInt("TOTAL_KEUNTUNGAN"));
        tod.setHarga_promo(rs.getInt("HARGA_PROMO"));
        tod.setNama_barang(rs.getString("NAMA_BARANG"));
        return tod;
    }
}

