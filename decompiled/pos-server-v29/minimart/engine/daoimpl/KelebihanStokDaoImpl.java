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
import minimart.engine.dao.KelebihanStokDao;
import minimart.engine.model.KelebihanStok;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class KelebihanStokDaoImpl
extends SimpleJdbcDaoSupport
implements KelebihanStokDao {
    private Helper helper = Helper.getInstance();

    public KelebihanStokDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createKelebihanStok(KelebihanStok kelebihanStok) {
        String sql = "INSERT INTO KELEBIHAN_STOK (ID, M_BARANG_ID, STOK_SYSTEM, STOK_INPUT, INPUT_OLEH, OTORISASI_OLEH, CREATED, KETERANGAN) VALUES (:id, :barangId, :stokSystem, :stokInput, :inputOleh, :otorisasiOleh, :created, :keterangan)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)kelebihanStok);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<Object[]> getKelebihanStok(String startDate, String endDate) {
        String sql = "SELECT kelebihan_stok.id AS KELEBIHAN_STOK_ID , m_barang_id, nama, stok_system, stok_input, input_oleh, otorisasi_oleh, kelebihan_stok.created as created, keterangan FROM kelebihan_stok JOIN m_barang on (kelebihan_stok.m_barang_Id = m_barang.id) where kelebihan_stok.CREATED>=? AND kelebihan_stok.CREATED<=?";
        LinkedList<Object[]> objects = new LinkedList<Object[]>();
        try {
            List rows = this.getJdbcTemplate().queryForList(sql, new Object[]{startDate, endDate});
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("KELEBIHAN_STOK_ID"), row.get("m_barang_id"), row.get("nama"), row.get("stok_system"), row.get("stok_input"), row.get("input_oleh"), row.get("otorisasi_oleh"), row.get("created"), row.get("keterangan")};
                objects.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return objects;
    }
}

