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
import minimart.engine.dao.SupplierOnlineDao;
import minimart.engine.model.SupplierOnline;
import minimart.engine.rowmapper.SupplierOnlineRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class SupplierOnlineDaoImpl
extends SimpleJdbcDaoSupport
implements SupplierOnlineDao {
    public SupplierOnlineDaoImpl() {
        this.setDataSource((DataSource)OnlineConnManager.getInstance().getDataSource());
    }

    @Override
    public int createSupplier(SupplierOnline supplierOnline) {
        String sql = "INSERT INTO m_supplier (id, id_offline, nama, alamat, kota, telepon,contact_person, available, created, updated, top, tipe_toko) VALUES (:id, :idOffline, :nama, :alamat, :kota, :telepon, :contactPerson, :available, :created, :updated, :top, :tipeToko) ON DUPLICATE KEY UPDATE updated = NOW()";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)supplierOnline);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updateSupplier(SupplierOnline supplierOnline) {
        String sql = "UPDATE m_supplier SET id_offline=:idOffline, nama=:nama, alamat=:alamat, kota=:kota, telepon=:telepon, contact_person=:contactPerson, available=:available, updated=:updated, top=:top WHERE id=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)supplierOnline);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int deleteSupplier(String id) {
        String sql = "DELETE FROM m_supplier WHERE ID = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{id});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<SupplierOnline> getSupplierOnline(String tipeToko) {
        String sql = "SELECT * FROM m_supplier WHERE tipe_toko = ?";
        List suppliers = null;
        try {
            suppliers = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new SupplierOnlineRowMapper(), new Object[]{tipeToko});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return suppliers;
    }
}

