/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.TransaksiOnline;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class TransaksiOnlineRowMapper
implements ParameterizedRowMapper<TransaksiOnline> {
    public TransaksiOnline mapRow(ResultSet rs, int rowNum) throws SQLException {
        TransaksiOnline to = new TransaksiOnline();
        to.setId(rs.getInt("ID"));
        to.setId_customer(rs.getString("ID_CUSTOMER"));
        to.setId_cabang(rs.getInt("ID_CABANG"));
        to.setTanggal_transaksi(rs.getDate("TANGGAL_TRANSAKSI"));
        to.setSubtotal(rs.getInt("SUBTOTAL"));
        to.setNominal_voucher_dipakai(rs.getInt("NOMINAL_VOUCHER_DIPAKAI"));
        to.setBiaya_kirim(rs.getInt("BIAYA_KIRIM"));
        to.setTotal(rs.getLong("TOTAL"));
        to.setStatus(rs.getString("STATUS"));
        to.setPotongan_dari_point(rs.getInt("POTONGAN_DARI_POINT"));
        to.setId_transaksi_offline(rs.getString("ID_TRANSAKSI_OFFLINE"));
        to.setKode_metode_pembayaran(rs.getString("KODE_METODE_PEMBAYARAN"));
        to.setStatus_pembayaran(rs.getString("STATUS_PEMBAYARAN"));
        to.setApproval_sales(rs.getBoolean("APPROVAL_SALES"));
        to.setApproval_superuser(rs.getBoolean("APPROVAL_SUPERUSER"));
        to.setM_sales_id(rs.getString("m_sales_id"));
        to.setNama_sales("nama_sales");
        return to;
    }
}

