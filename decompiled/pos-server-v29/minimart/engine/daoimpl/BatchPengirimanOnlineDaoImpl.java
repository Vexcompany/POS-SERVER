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
import minimart.engine.dao.BatchPengirimanOnlineDao;
import minimart.engine.model.BatchPengiriman;
import minimart.engine.model.BatchPengirimanDetail;
import minimart.engine.rowmapper.BatchPengirimanDetailRowMapper;
import minimart.engine.rowmapper.BatchPengirimanOnlineRowMapper;
import minimart.engine.service.KonstantaService;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class BatchPengirimanOnlineDaoImpl
extends SimpleJdbcDaoSupport
implements BatchPengirimanOnlineDao {
    KonstantaService ks = new KonstantaService();

    public BatchPengirimanOnlineDaoImpl() {
        this.setDataSource((DataSource)OnlineConnManager.getInstance().getDataSource());
    }

    @Override
    public BatchPengiriman getBatchPengirimanByKodeBatch(String kodeBatch) {
        String sql = "SELECT * FROM t_batch_pengiriman WHERE kode_batch = ?";
        BatchPengiriman bp = null;
        try {
            bp = (BatchPengiriman)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new BatchPengirimanOnlineRowMapper(), new Object[]{kodeBatch});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return bp;
    }

    @Override
    public List<BatchPengirimanDetail> getBatchPengirimanDetailByIdBatchPengiriman(Integer id_batch_pengiriman) {
        int id_cabang_pengirim = Integer.parseInt(this.ks.getConstantValue("idCabang"));
        String sql_check = "SELECT count(*) from migration_header mh where mh.id_batch_pengiriman = ? and mh.id_cabang_pengirim = ?";
        int exist = -1;
        try {
            exist = (Integer)this.getSimpleJdbcTemplate().queryForObject(sql_check, Integer.class, new Object[]{id_batch_pengiriman, id_cabang_pengirim});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        List bpds = null;
        if (exist == 0) {
            String sql = "SELECT tppd.*, tpp.kode_paket, tpp.id_cabang_pengirim, i.nama_item, i.jenis_item, i.hpp FROM t_paket_pengiriman_detail tppd inner join t_paket_pengiriman tpp on tppd.id_t_paket_pengiriman = tpp.id and tpp.deleted_at is null and tpp.status_cancel = 0 inner join item i on i.id_sku = tppd.id_sku and i.id_cabang = tpp.id_cabang_pengirim WHERE tpp.id_batch_pengiriman = ? and tpp.id_cabang_pengirim = ? and tppd.deleted_at is null and tppd.quantity_terima is not null and tppd.quantity_terima != 0";
            try {
                bpds = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new BatchPengirimanDetailRowMapper(), new Object[]{id_batch_pengiriman, id_cabang_pengirim});
            }
            catch (DataAccessException e) {
                System.out.println("Message: " + e.getMessage());
            }
        }
        return bpds;
    }
}

