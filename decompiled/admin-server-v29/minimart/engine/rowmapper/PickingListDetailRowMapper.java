/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.PickingListDetail;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PickingListDetailRowMapper
implements ParameterizedRowMapper<PickingListDetail> {
    public PickingListDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
        PickingListDetail pld = new PickingListDetail();
        pld.setId(rs.getLong("id"));
        pld.setIdPickingListHeader(rs.getInt("id_picking_list_header"));
        pld.setIdSKU(rs.getString("id_sku"));
        pld.setQuantityRequested(rs.getInt("quantity_requested"));
        pld.setQuantityArea1(rs.getInt("quantity_area_1"));
        pld.setQuantityArea2(rs.getInt("quantity_area_2"));
        pld.setSisaStok(rs.getInt("sisa_stok"));
        pld.setStokArea1(rs.getInt("stok_area_1"));
        pld.setStokArea2(rs.getInt("stok_area_2"));
        pld.setPkm(rs.getInt("pkm"));
        pld.setPkn(rs.getInt("pkn"));
        pld.setQuantityAccepted1(rs.getInt("quantity_accepted_1"));
        pld.setQuantityAccepted2(rs.getInt("quantity_accepted_2"));
        pld.setNamaBarang(rs.getString("nama_item"));
        pld.setJenis(rs.getString("jenis_item"));
        pld.setSatuanCabang1(rs.getString("satuan_cabang_1"));
        pld.setSatuanCabang2(rs.getString("satuan_cabang_2"));
        pld.setKonversiCabang1(rs.getInt("konversi_cabang_1"));
        pld.setKonversiCabang2(rs.getInt("konversi_cabang_2"));
        pld.setHpp(rs.getLong("hpp"));
        return pld;
    }
}

