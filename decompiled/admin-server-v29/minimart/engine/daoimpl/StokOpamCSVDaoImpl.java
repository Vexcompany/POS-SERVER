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

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.controller.Helper;
import minimart.engine.dao.StokOpnamCSVDao;
import minimart.engine.model.StokOpnamCSV;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class StokOpamCSVDaoImpl
extends SimpleJdbcDaoSupport
implements StokOpnamCSVDao {
    private Helper helper = Helper.getInstance();

    public StokOpamCSVDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createStokOpnamCSV(StokOpnamCSV cSV) {
        String sql = "INSERT INTO stok_opnam_csv (BARCODE, M_BARANG_ID, PENANGGUNG_JAWAB, LOKASI, STOK) VALUES (:barcode, :barangId, :penanggungJawab, :lokasi, :stok)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)cSV);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int deleteStokOpnamCSV() {
        String sql = "DELETE FROM stok_opnam_csv";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<Object[]> getStokOpnamCSVs() {
        String sql = "select m_barang_id, sum(stok) from stok_opnam_csv group by m_barang_id";
        LinkedList<Object[]> objects = new LinkedList<Object[]>();
        try {
            List rows = this.getJdbcTemplate().queryForList(sql);
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("m_barang_id"), row.get("sum(stok)")};
                objects.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return objects;
    }

    @Override
    public int getJumlahStokCSVs(String m_barang_id) {
        String sql = "SELECT SUM(STOK) FROM STOK_OPNAM_CSV WHERE M_BARANG_ID = ?";
        int stok = 0;
        try {
            if (this.getSimpleJdbcTemplate().queryForObject(sql, Integer.class, new Object[]{m_barang_id}) != null) {
                stok = (Integer)this.getSimpleJdbcTemplate().queryForObject(sql, Integer.class, new Object[]{m_barang_id});
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return stok;
    }
}

