/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 *  org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport
 */
package minimart.engine.daoimpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import minimart.engine.controller.OnlineConnManager;
import minimart.engine.dao.PenjualanDetailOnlineDao;
import minimart.engine.rowmapper.LongRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PenjualanDetailOnlineDaoImpl
extends SimpleJdbcDaoSupport
implements PenjualanDetailOnlineDao {
    public PenjualanDetailOnlineDaoImpl() {
        this.setDataSource((DataSource)OnlineConnManager.getInstance().getDataSource());
    }

    @Override
    public List<Long> getPenjualanDetailOnlineId(int idCabang, String start_date, String end_date) throws SQLException {
        String sql = "SELECT id_penjualan_detail_offline from t_penjualan_detail where id_cabang = ? and created >= ? and created < ? and available = true order by id_penjualan_detail_offline asc";
        List<Long> list = new ArrayList<Long>();
        try {
            list = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new LongRowMapper(), new Object[]{idCabang, start_date, end_date});
            return list;
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
            return list;
        }
    }

    @Override
    public List<Long> getPenjualanDetailOnlineIdAll(int idCabang, String start_date, String end_date) throws SQLException {
        String sql = "SELECT id_penjualan_detail_offline from t_penjualan_detail where id_cabang = ? and available = true order by id_penjualan_detail_offline asc";
        List<Long> list = new ArrayList<Long>();
        try {
            list = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new LongRowMapper(), new Object[]{idCabang, start_date, end_date});
            return list;
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
            return list;
        }
    }

    @Override
    public int deletePenjualanDetailOnline(String ids, String idCabang) {
        String sql = "UPDATE t_penjualan_detail SET available = 0 WHERE id_cabang = " + idCabang + " and id_penjualan_detail_offline IN (" + ids + ")";
        System.out.println(sql);
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }
}

