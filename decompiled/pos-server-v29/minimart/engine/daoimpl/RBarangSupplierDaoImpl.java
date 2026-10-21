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

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.dao.RBarangSupplierDao;
import minimart.engine.model.RBarangSupplier;
import minimart.engine.rowmapper.RBarangSupplierRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class RBarangSupplierDaoImpl
extends SimpleJdbcDaoSupport
implements RBarangSupplierDao {
    public RBarangSupplierDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createRBarangSupplier(RBarangSupplier rbs) {
        String sql = "INSERT INTO r_barang_supplier (ID, M_BARANG_ID, M_SUPPLIER_ID) VALUES (:id, :barangId, :supplierId)";
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
    public List<RBarangSupplier> readRBSByBarangId(String barangId) {
        String sql = "SELECT * FROM r_barang_supplier WHERE M_BARANG_ID = ?";
        List rbses = null;
        try {
            rbses = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new RBarangSupplierRowMapper(), new Object[]{barangId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return rbses;
    }

    @Override
    public List<Object[]> readRBSBySupplierId(String supplierId) {
        String sql = "SELECT R_BARANG_SUPPLIER.M_BARANG_ID AS M_BARANG_ID, M_BARANG.NAMA AS NAMA_BARANG, M_BARANG.M_JENIS_JENIS AS JENIS, M_BARANG.STOK_TOKO AS STOK_TOKO, M_BARANG.STOK_GUDANG AS STOK_GUDANG, M_BARANG.SATUAN_1 AS SATUAN_1, M_SUPPLIER.NAMA AS NAMA_SUPPLIER FROM r_barang_supplier JOIN M_BARANG ON(R_BARANG_SUPPLIER.M_BARANG_ID = M_BARANG.ID) JOIN M_SUPPLIER ON (R_BARANG_SUPPLIER.M_SUPPLIER_ID = M_SUPPLIER.ID) WHERE M_SUPPLIER_ID LIKE ? ORDER BY M_SUPPLIER.NAMA";
        LinkedList<Object[]> data = new LinkedList<Object[]>();
        try {
            supplierId = '%' + supplierId + '%';
            System.out.println(supplierId);
            List rows = this.getJdbcTemplate().queryForList(sql, new Object[]{supplierId});
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("M_BARANG_ID"), row.get("NAMA_BARANG"), row.get("JENIS"), row.get("STOK_TOKO"), row.get("STOK_GUDANG"), row.get("SATUAN_1"), row.get("NAMA_SUPPLIER")};
                data.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return data;
    }

    @Override
    public int deleteRBSByBarangIdSupplierId(String barangId, String supplierId) {
        String sql = "DELETE FROM r_barang_supplier WHERE M_BARANG_ID = ? AND M_SUPPLIER_ID = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{barangId, supplierId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public boolean isRBSHasCreated(String barangId, String supplierId) {
        String sql = "SELECT * FROM r_barang_supplier WHERE M_BARANG_ID = ? AND M_SUPPLIER_ID = ?";
        RBarangSupplier rbs = null;
        try {
            rbs = (RBarangSupplier)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new RBarangSupplierRowMapper(), new Object[]{barangId, supplierId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return rbs != null;
    }

    @Override
    public List<RBarangSupplier> readRBSAll() {
        String sql = "SELECT * FROM r_barang_supplier";
        List rbses = null;
        try {
            rbses = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new RBarangSupplierRowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return rbses;
    }
}

