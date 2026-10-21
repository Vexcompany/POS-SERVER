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
import minimart.engine.controller.OnlineConnManager;
import minimart.engine.dao.PaketOnlineDao;
import minimart.engine.model.Paket;
import minimart.engine.model.PaketDetail;
import minimart.engine.rowmapper.PaketDetailRowMapper;
import minimart.engine.rowmapper.PaketRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PaketOnlineDaoImpl
extends SimpleJdbcDaoSupport
implements PaketOnlineDao {
    public PaketOnlineDaoImpl() {
        this.setDataSource((DataSource)OnlineConnManager.getInstance().getDataSource());
    }

    @Override
    public List<Paket> getPaketOnline(String tipeToko) {
        String sql = "SELECT * FROM m_paket WHERE tipe_toko = ?";
        List pakets = null;
        try {
            pakets = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PaketRowMapper(), new Object[]{tipeToko});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return pakets;
    }

    @Override
    public List<PaketDetail> getPaketDetailOnline(String paketIds) {
        String sql = "SELECT * FROM m_paket_detail WHERE m_paket_id in (" + paketIds + ")";
        List paketDetails = null;
        try {
            paketDetails = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PaketDetailRowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return paketDetails;
    }
}

