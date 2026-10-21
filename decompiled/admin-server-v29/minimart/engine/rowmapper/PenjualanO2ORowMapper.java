/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model_online.PenjualanO2O;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PenjualanO2ORowMapper
implements ParameterizedRowMapper<PenjualanO2O> {
    public PenjualanO2O mapRow(ResultSet rs, int rowNum) throws SQLException {
        PenjualanO2O penjualanO2O = new PenjualanO2O();
        penjualanO2O.setId_penjualan_offline(rs.getString("ID"));
        penjualanO2O.setM_customer_id(rs.getString("M_CUSTOMER_ID"));
        penjualanO2O.setM_karyawan_id(rs.getString("M_KARYAWAN_ID"));
        penjualanO2O.setM_sales_id(rs.getString("M_SALES_ID"));
        penjualanO2O.setKasir(rs.getInt("KASIR"));
        penjualanO2O.setTanggal(rs.getDate("TANGGAL"));
        penjualanO2O.setTotal_penjualan(rs.getLong("TOTAL_PENJUALAN"));
        penjualanO2O.setStatus(rs.getString("STATUS"));
        penjualanO2O.setPoin(rs.getInt("POIN"));
        penjualanO2O.setCreated(rs.getTimestamp("CREATED"));
        penjualanO2O.setUpdated(rs.getTimestamp("UPDATED"));
        penjualanO2O.setJatuh_tempo(rs.getDate("JATUH_TEMPO"));
        penjualanO2O.setKeterangan(rs.getString("KETERANGAN"));
        penjualanO2O.setStatus_pelunasan(rs.getString("STATUS_PELUNASAN"));
        penjualanO2O.setId_transaksi_online(rs.getString("ID_TRANSAKSI_ONLINE"));
        penjualanO2O.setEligiblePwp(rs.getBoolean("ELIGIBLE_PWP"));
        penjualanO2O.setEligibleTebusMurah(rs.getBoolean("ELIGIBLE_TEBUS_MURAH"));
        penjualanO2O.setLama_transaksi(rs.getInt("LAMA_TRANSAKSI"));
        return penjualanO2O;
    }
}

