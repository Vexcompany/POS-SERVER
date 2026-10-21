/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.SalesOrderDetail;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class SalesOrderDetailRowMapper
implements ParameterizedRowMapper<SalesOrderDetail> {
    public SalesOrderDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
        SalesOrderDetail sod = new SalesOrderDetail();
        sod.setId(rs.getLong("id"));
        sod.setId_sales_order(rs.getInt("id_sales_order"));
        sod.setId_sku(rs.getString("id_sku"));
        sod.setNama_barang(rs.getString("nama_barang"));
        sod.setJumlah_barang(rs.getFloat("jumlah_barang"));
        sod.setSatuan(rs.getString("satuan"));
        sod.setHarga_jual(rs.getLong("harga_jual"));
        sod.setTotal_harga(rs.getLong("total_harga"));
        sod.setId_cabang(rs.getInt("id_cabang"));
        return sod;
    }
}

