/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 *  org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport
 */
package minimart.engine.daoimpl;

import javax.sql.DataSource;
import minimart.engine.controller.OnlineConnManager;
import minimart.engine.dao.TransaksiOnlineDao;
import minimart.engine.model.TransaksiOnline;
import minimart.engine.rowmapper.TransaksiOnlineRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class TransaksiOnlineDaoImpl
extends SimpleJdbcDaoSupport
implements TransaksiOnlineDao {
    public TransaksiOnlineDaoImpl() {
        this.setDataSource((DataSource)OnlineConnManager.getInstance().getDataSource());
    }

    @Override
    public TransaksiOnline getTransaksiOnlineByUniqueCode(int idTransaksiOnline) {
        String sql = "select tta.*, rcs.m_sales_id, s.nama as 'nama_sales' from t_transaksi_apps tta LEFT JOIN v_r_customer_sales_active rcs ON rcs.customer_id = tta.id_customer LEFT JOIN m_sales s on rcs.m_sales_id = s.id where tta.id = ? and tta.deleted_at is null and tta.status not in ('CANCEL', 'DIBATALKAN', 'GAGAL')";
        TransaksiOnline to = null;
        try {
            to = (TransaksiOnline)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new TransaksiOnlineRowMapper(), new Object[]{idTransaksiOnline});
        }
        catch (DataAccessException e) {
            System.out.println((Object)e);
        }
        return to;
    }

    @Override
    public int updateIdTransaksiOfflineTransaksiOnline(int id, String idTransaksiOffline) {
        String sql = "UPDATE t_transaksi_apps SET status_pembayaran = 'DIBAYAR', status = 'DIPROSES', tanggal_pembayaran = CURRENT_TIMESTAMP, id_transaksi_offline = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{idTransaksiOffline, id});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updateIdTransaksiOnlineStatusLog(int id, String status, String karyawanId) {
        String sql = "INSERT INTO t_transaksi_apps_status_logs(id_transaksi, status, keterangan) VALUES (?, ?, ?)";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{id, status, "USING JAVA => " + karyawanId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }
}

