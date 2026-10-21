/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model_online.POSupplier;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class POSupplierRowMapper
implements ParameterizedRowMapper<POSupplier> {
    public POSupplier mapRow(ResultSet rs, int rowNum) throws SQLException {
        POSupplier po_sup = new POSupplier();
        po_sup.setId(rs.getInt("id"));
        po_sup.setId_cabang_terima(rs.getInt("id_cabang_terima"));
        po_sup.setTanggal_po(rs.getDate("tanggal_po"));
        po_sup.setEstimasi_kedatangan(rs.getDate("estimasi_kedatangan"));
        po_sup.setId_supplier(rs.getString("id_supplier"));
        po_sup.setStatus(rs.getString("status"));
        po_sup.setCreated(rs.getDate("created"));
        po_sup.setUpdated(rs.getDate("updated"));
        po_sup.setAvailable(rs.getBoolean("available"));
        po_sup.setId_header_order_supplier(rs.getInt("id_header_order_supplier"));
        po_sup.setTipe_toko(rs.getString("tipe_toko"));
        po_sup.setPembayaran(rs.getString("pembayaran"));
        po_sup.setJatuh_tempo(rs.getDate("jatuh_tempo"));
        po_sup.setDiscount_1(rs.getFloat("discount_1"));
        po_sup.setPpn(rs.getFloat("ppn"));
        po_sup.setStatus_toko(rs.getString("status_toko"));
        po_sup.setSync_pembelian_online(rs.getBoolean("sync_pembelian_offline"));
        return po_sup;
    }
}

