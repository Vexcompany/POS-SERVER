/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 *  org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport
 */
package minimart.engine.daoimpl;

import java.util.List;
import javax.sql.DataSource;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.dao.PenukaranVoucherDao;
import minimart.engine.model.PenukaranVoucher;
import minimart.engine.rowmapper.PenukaranVoucherRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PenukaranVoucherDaoImpl
extends SimpleJdbcDaoSupport
implements PenukaranVoucherDao {
    public PenukaranVoucherDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createPenukaranVoucher(PenukaranVoucher penukaranVoucher) {
        String sql = "INSERT INTO t_penukaran_voucher (ID, M_KARYAWAN_ID, M_CUSTOMER_ID, T_PENJUALAN_ID, JUMLAH_VOUCHER, TANGGAL) VALUES (:id, :karyawanId, :customerId, :penjualanId, :jumlahVoucher, :tanggal)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)penukaranVoucher);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<PenukaranVoucher> getPenukaranVoucher(String startdate, String enddate) {
        String sql = "SELECT * FROM t_penukaran_voucher WHERE TANGGAL>=? AND TANGGAL<=? ";
        List penukaranPoints = null;
        try {
            penukaranPoints = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PenukaranVoucherRowMapper(), new Object[]{startdate, enddate});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return penukaranPoints;
    }
}

