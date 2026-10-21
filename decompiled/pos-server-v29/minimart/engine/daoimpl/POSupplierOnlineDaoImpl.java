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
import minimart.engine.dao.POSupplierOnlineDao;
import minimart.engine.model_online.POSupplier;
import minimart.engine.model_online.POSupplierDetail;
import minimart.engine.rowmapper.POSupplierDetailRowMapper;
import minimart.engine.rowmapper.POSupplierRowMapper;
import minimart.engine.service.KonstantaService;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class POSupplierOnlineDaoImpl
extends SimpleJdbcDaoSupport
implements POSupplierOnlineDao {
    private final KonstantaService ks = new KonstantaService();

    public POSupplierOnlineDaoImpl() {
        this.setDataSource((DataSource)OnlineConnManager.getInstance().getDataSource());
    }

    @Override
    public List<POSupplier> getPOSupplierUnsyncedOffline(int idCabang) {
        String sql = "CALL sp_read_po_supplier_unsynced_offline(?)";
        List pos = null;
        try {
            pos = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new POSupplierRowMapper(), new Object[]{idCabang});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return pos;
    }

    @Override
    public List<POSupplierDetail> getPOSupplierDetailUnsyncedOffline(int idCabang) {
        String sql = "CALL sp_read_po_supplier_detail_unsynced_offline(?)";
        List pods = null;
        try {
            pods = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new POSupplierDetailRowMapper(), new Object[]{idCabang});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return pods;
    }

    @Override
    public void markStatusSyncOffline(int id) {
        String sql = "UPDATE po_supplier SET sync_pembelian_offline = 1 WHERE id = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{id});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
    }
}

