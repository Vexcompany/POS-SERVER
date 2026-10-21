/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 */
package minimart.engine.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import minimart.engine.model.PenukaranVoucher;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class PenukaranVoucherRowMapper
implements ParameterizedRowMapper<PenukaranVoucher> {
    public PenukaranVoucher mapRow(ResultSet rs, int rowNum) throws SQLException {
        PenukaranVoucher penukaranVoucher = new PenukaranVoucher();
        penukaranVoucher.setId(rs.getInt("ID"));
        penukaranVoucher.setKaryawanId(rs.getString("M_KARYAWAN_ID"));
        penukaranVoucher.setCustomerId(rs.getString("M_CUSTOMER_ID"));
        penukaranVoucher.setPenjualanId(rs.getString("T_PENJUALAN_ID"));
        penukaranVoucher.setJumlahVoucher(rs.getInt("JUMLAH_VOUCHER"));
        penukaranVoucher.setTanggal(rs.getDate("TANGGAL"));
        return penukaranVoucher;
    }
}

