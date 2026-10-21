/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model_online.POSupplierDetail;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class POSupplierDetailRowMapper
implements ParameterizedRowMapper<POSupplierDetail> {
    public POSupplierDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
        POSupplierDetail psd = new POSupplierDetail();
        psd.setId(rs.getLong("id"));
        psd.setId_po_header(rs.getInt("id_po_header"));
        psd.setId_sku(rs.getString("id_sku"));
        psd.setQuantity(rs.getFloat("quantity_terima"));
        psd.setSatuan(rs.getString("satuan"));
        psd.setKonversi_satuan(rs.getInt("konversi_satuan"));
        psd.setQuantity_terkecil(rs.getFloat("quantity_terima_terkecil"));
        psd.setId_order_supplier_detail(rs.getInt("id_order_supplier_detail"));
        psd.setHpp_terakhir(rs.getLong("hpp_terakhir"));
        psd.setHarga_beli(rs.getLong("harga_beli"));
        psd.setCreated(rs.getDate("created"));
        psd.setUpdated(rs.getDate("updated"));
        psd.setAvailable(rs.getBoolean("available"));
        return psd;
    }
}

