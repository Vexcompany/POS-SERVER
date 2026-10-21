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
import minimart.engine.dao.HargaBertingkatDao;
import minimart.engine.model.HargaBertingkat;
import minimart.engine.rowmapper.HargaBertingkatRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class HargaBertingkatDaoImpl
extends SimpleJdbcDaoSupport
implements HargaBertingkatDao {
    public HargaBertingkatDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createHargaBertingkat(HargaBertingkat hargaBertingkat) {
        String sql = "INSERT INTO m_harga_bertingkat (ID, M_BARANG_ID, QUANTITY, HARGA_JUAL, CREATED, UPDATED) VALUES (:id, :barangId, :quantity, :hargaJual, :created, :updated)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)hargaBertingkat);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updateHargaBertingkat(HargaBertingkat hargaBertingkat) {
        String sql = "UPDATE m_harga_bertingkat SET QUANTITY=:quantity, HARGA_JUAL=:hargaJual, UPDATED=:updated WHERE ID=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)hargaBertingkat);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int deleteHargaBertingkat(HargaBertingkat hargaBertingkat) {
        String sql = "DELETE FROM m_harga_bertingkat WHERE ID = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{hargaBertingkat.getId()});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<HargaBertingkat> getHargaBertingkatsByBarangId(String barangId) {
        String sql = "SELECT * FROM m_harga_bertingkat WHERE M_BARANG_ID LIKE ? ORDER BY ID ASC";
        List hargaBertingkats = null;
        try {
            hargaBertingkats = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new HargaBertingkatRowMapper(), new Object[]{barangId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return hargaBertingkats;
    }
}

