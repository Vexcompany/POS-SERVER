/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 *  org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport
 */
package minimart.engine.daoimpl;

import java.util.List;
import javax.sql.DataSource;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.dao.CabangDao;
import minimart.engine.model.Cabang;
import minimart.engine.rowmapper.CabangRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class CabangDaoImpl
extends SimpleJdbcDaoSupport
implements CabangDao {
    public CabangDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public Cabang getCabang(int idCabang) {
        String sql = "SELECT * FROM m_cabang WHERE ID = ?";
        Cabang customer = null;
        try {
            customer = (Cabang)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new CabangRowMapper(), new Object[]{idCabang});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return customer;
    }

    @Override
    public List<Cabang> getCabang(String namaCabang) {
        String sql = "SELECT * FROM m_cabang WHERE NAMA LIKE ?";
        List cabangs = null;
        try {
            namaCabang = '%' + namaCabang + '%';
            cabangs = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new CabangRowMapper(), new Object[]{namaCabang});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return cabangs;
    }
}

