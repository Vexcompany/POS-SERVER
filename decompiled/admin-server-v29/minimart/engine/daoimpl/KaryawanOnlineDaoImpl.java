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
import minimart.engine.controller.OnlineConnManager;
import minimart.engine.dao.KaryawanOnlineDao;
import minimart.engine.model.Karyawan;
import minimart.engine.rowmapper.KaryawanRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class KaryawanOnlineDaoImpl
extends SimpleJdbcDaoSupport
implements KaryawanOnlineDao {
    public KaryawanOnlineDaoImpl() {
        this.setDataSource((DataSource)OnlineConnManager.getInstance().getDataSource());
    }

    @Override
    public List<Karyawan> getKaryawanOnline(String idCabang) {
        String sql = "SELECT * FROM m_karyawan WHERE id_cabang like ?";
        List karyawan = null;
        try {
            idCabang = "%[" + idCabang + "]%";
            karyawan = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new KaryawanRowMapper(), new Object[]{idCabang});
        }
        catch (DataAccessException e) {
            System.out.println((Object)e);
        }
        return karyawan;
    }

    @Override
    public int updateKaryawanPassword(Karyawan karyawan) {
        String sql = "UPDATE m_karyawan SET password=:password, updated=CURRENT_TIMESTAMP WHERE id=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)karyawan);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }
}

