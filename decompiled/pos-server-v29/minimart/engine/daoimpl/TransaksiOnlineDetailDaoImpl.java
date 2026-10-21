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
import minimart.engine.dao.TransaksiOnlineDetailDao;
import minimart.engine.model.TransaksiOnlineDetail;
import minimart.engine.rowmapper.TransaksiOnlineDetailRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class TransaksiOnlineDetailDaoImpl
extends SimpleJdbcDaoSupport
implements TransaksiOnlineDetailDao {
    public TransaksiOnlineDetailDaoImpl() {
        this.setDataSource((DataSource)OnlineConnManager.getInstance().getDataSource());
    }

    @Override
    public List<TransaksiOnlineDetail> getTransaksiOnlineDetailByIdTransaksiOnline(int idTransaksiOnline) {
        String sql = "SELECT tod.*, i.nama_item as nama_barang FROM t_transaksi_apps_details tod inner join item i on tod.m_barang_id = i.id_sku and tod.id_cabang = i.id_cabang WHERE transaksi_id = ? and deleted_at is null";
        List tods = null;
        try {
            tods = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new TransaksiOnlineDetailRowMapper(), new Object[]{idTransaksiOnline});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return tods;
    }
}

