/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.BatchPengirimanDetail;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class BatchPengirimanDetailRowMapper
implements ParameterizedRowMapper<BatchPengirimanDetail> {
    public BatchPengirimanDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
        BatchPengirimanDetail bpd = new BatchPengirimanDetail();
        bpd.setId(rs.getLong("id"));
        bpd.setIdSku(rs.getString("id_sku"));
        bpd.setQuantityKirim(rs.getInt("quantity_kirim"));
        bpd.setSatuanKirim(rs.getString("satuan_kirim"));
        bpd.setKonversiSatuanKirim(rs.getInt("konversi_satuan_kirim"));
        bpd.setIdTPaketPengiriman(rs.getInt("id_t_paket_pengiriman"));
        bpd.setQuantityTerima(rs.getInt("quantity_terima"));
        bpd.setSatuanTerima(rs.getString("satuan_terima"));
        bpd.setKonversiSatuanTerima(rs.getInt("konversi_satuan_terima"));
        bpd.setStatusLock(rs.getBoolean("status_lock"));
        bpd.setNamaItem(rs.getString("nama_item"));
        bpd.setJenisItem(rs.getString("jenis_item"));
        bpd.setHpp(rs.getLong("hpp"));
        bpd.setKodePaket(rs.getString("kode_paket"));
        bpd.setIdCabangPengirim(rs.getInt("id_cabang_pengirim"));
        return bpd;
    }
}

