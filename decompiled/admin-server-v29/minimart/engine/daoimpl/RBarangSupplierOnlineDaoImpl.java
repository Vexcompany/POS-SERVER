/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 *  org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport
 */
package minimart.engine.daoimpl;

import javax.sql.DataSource;
import minimart.engine.controller.OnlineConnManager;
import minimart.engine.dao.RBarangSupplierOnlineDao;
import minimart.engine.model.RBarangSupplierOnline;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class RBarangSupplierOnlineDaoImpl
extends SimpleJdbcDaoSupport
implements RBarangSupplierOnlineDao {
    public RBarangSupplierOnlineDaoImpl() {
        this.setDataSource((DataSource)OnlineConnManager.getInstance().getDataSource());
    }

    @Override
    public int createRBarangSupplier(RBarangSupplierOnline rbs) {
        String sql = "INSERT INTO r_supplier_barang (id, id_sku, id_supplier, tipe_toko)VALUES (:id, :barangId, :supplierId, :tipeToko) ON DUPLICATE KEY UPDATE tipe_toko = :tipeToko";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)rbs);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int deleteRBSByBarangIdSupplierId(String barangId, String supplierId, String tipeToko) {
        String sql = "DELETE FROM r_supplier_barang WHERE id_sku = ? AND id_supplier = ? AND tipe_toko = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{barangId, supplierId, tipeToko});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }
}

