/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.Barang;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class BarangRowMapper
implements ParameterizedRowMapper<Barang> {
    public Barang mapRow(ResultSet rs, int rowNum) throws SQLException {
        Barang barang = new Barang();
        barang.setId(rs.getString("ID"));
        barang.setJenis(rs.getString("M_JENIS_JENIS"));
        barang.setRakIdToko(rs.getString("RAK_TOKO"));
        barang.setRakIdGudang(rs.getString("RAK_GUDANG"));
        barang.setNama(rs.getString("NAMA"));
        barang.setSatuan1(rs.getString("SATUAN_1"));
        barang.setSatuan2(rs.getString("SATUAN_2"));
        barang.setSatuan3(rs.getString("SATUAN_3"));
        barang.setKonversi21(rs.getInt("KONVERSI_21"));
        barang.setKonversi32(rs.getInt("KONVERSI_32"));
        barang.setHargaJual1(rs.getLong("HARGA_JUAL_1"));
        barang.setHargaJual2(rs.getLong("HARGA_JUAL_2"));
        barang.setHargaJual3(rs.getLong("HARGA_JUAL_3"));
        barang.setMinPembelian(rs.getFloat("MIN_PEMBELIAN"));
        barang.setMinPersen(rs.getFloat("MIN_PERSEN"));
        barang.setBatasAtasStock(rs.getLong("BATAS_ATAS_STOCK"));
        barang.setReorderPoint(rs.getLong("REORDER_POINT"));
        barang.setStokToko(rs.getFloat("STOK_TOKO"));
        barang.setStokGudang(rs.getFloat("STOK_GUDANG"));
        barang.setStokPusat(rs.getFloat("STOK_PUSAT"));
        barang.setStokBs(rs.getFloat("STOK_BS"));
        barang.setUpdateHarga(rs.getInt("UPDATE_HARGA"));
        barang.setCreated(rs.getDate("CREATED"));
        barang.setUpdated(rs.getDate("UPDATED"));
        barang.setHpp(rs.getLong("HPP"));
        barang.setHppUpdated(rs.getLong("HPP_UPDATED"));
        barang.setParentId(rs.getString("PARENT_ID"));
        barang.setTag(rs.getString("TAG"));
        barang.setExpDay(rs.getInt("EXPDAY"));
        return barang;
    }
}

