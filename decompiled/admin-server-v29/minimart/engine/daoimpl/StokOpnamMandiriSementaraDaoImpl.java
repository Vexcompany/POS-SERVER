/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport
 */
package minimart.engine.daoimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.dao.StokOpnamMandiriSementaraDao;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class StokOpnamMandiriSementaraDaoImpl
extends SimpleJdbcDaoSupport
implements StokOpnamMandiriSementaraDao {
    public StokOpnamMandiriSementaraDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public List<Object[]> geDetailBySomId(int somId) {
        String sql = "SELECT m_barang_id, m_barang.nama nama, stok_input, void, soms.created created, lokasi, m_karyawan_id, input_ke FROM minimarket.stok_opnam_mandiri_sementara soms join minimarket.stok_opnam_mandiri_detail somd on (soms.stok_opnam_mandiri_detail_id = somd.id) join minimarket.m_barang on (somd.m_barang_id = m_barang.id)where stok_opnam_mandiri_id = ? order by m_barang_id , created";
        ArrayList<Object[]> soms = new ArrayList<Object[]>();
        try {
            System.out.println("SOMID=" + somId);
            List rows = this.getJdbcTemplate().queryForList(sql, new Object[]{somId});
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("m_barang_id"), row.get("nama"), row.get("stok_input"), row.get("void"), row.get("created"), row.get("lokasi"), row.get("m_karyawan_id"), row.get("input_ke")};
                soms.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return soms;
    }
}

