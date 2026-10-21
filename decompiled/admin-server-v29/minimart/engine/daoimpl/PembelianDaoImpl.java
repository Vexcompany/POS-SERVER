/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 *  org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 *  org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport
 */
package minimart.engine.daoimpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.controller.Helper;
import minimart.engine.dao.PembelianDao;
import minimart.engine.model.Pembelian;
import minimart.engine.model_online.PembelianO2O;
import minimart.engine.rowmapper.PembelianO2ORowMapper;
import minimart.engine.rowmapper.PembelianRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PembelianDaoImpl
extends SimpleJdbcDaoSupport
implements PembelianDao {
    private Helper helper = Helper.getInstance();

    public PembelianDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createPembelian(Pembelian pembelian) {
        String sql = "INSERT INTO t_pembelian (ID, M_SUPPLIER_ID, M_KARYAWAN_ID, FAKTUR_ID,TANGGAL, PEMBAYARAN, JATUH_TEMPO, DISCOUNT_1, PPN, TOTAL, STATUS, KETERANGAN, PELUNASAN, TITIPAN, CREATED, UPDATED) VALUES (:id, :supplierId, :karyawanId, :fakturId, :tanggal, :pembayaran, :jatuhTempo, :discount1, :ppn, :total, :status, :keterangan, :pelunasan,:titipan, :created, :updated)";
        int result = 0;
        System.out.println(pembelian.getTanggal());
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)pembelian);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int createPembelianWithPOId(Pembelian pembelian) {
        String sql = "INSERT INTO t_pembelian (ID, M_SUPPLIER_ID, M_KARYAWAN_ID, FAKTUR_ID,TANGGAL, PEMBAYARAN, JATUH_TEMPO, DISCOUNT_1, PPN, TOTAL, STATUS, KETERANGAN, PELUNASAN, TITIPAN, CREATED, UPDATED, ID_PO_ONLINE) VALUES (:id, :supplierId, :karyawanId, :fakturId, :tanggal, :pembayaran, :jatuhTempo, :discount1, :ppn, :total, :status, :keterangan, :pelunasan,:titipan, :created, :updated, :id_po_online)";
        int result = 0;
        System.out.println(pembelian.getTanggal());
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)pembelian);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public Pembelian getPembelian(String idPembelian) {
        System.out.println(idPembelian);
        String sql = "SELECT * FROM t_pembelian WHERE ID = ?";
        Pembelian pembelian = null;
        try {
            pembelian = (Pembelian)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new PembelianRowMapper(), new Object[]{idPembelian});
        }
        catch (DataAccessException e) {
            System.out.println("Null Data");
        }
        return pembelian;
    }

    @Override
    public Pembelian getPembelianByStatus(String idPembelian) {
        String sql = "SELECT * FROM t_pembelian WHERE ID = ?";
        Pembelian pembelian = null;
        try {
            pembelian = (Pembelian)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new PembelianRowMapper(), new Object[]{idPembelian});
        }
        catch (DataAccessException e) {
            System.out.println("Null Data");
        }
        return pembelian;
    }

    @Override
    public int updatePembelian(Pembelian pembelian) {
        String sql = "UPDATE t_pembelian SET M_SUPPLIER_ID=:supplierId, M_KARYAWAN_ID=:karyawanId, FAKTUR_ID=:fakturId, TANGGAL=:tanggal, PEMBAYARAN=:pembayaran, JATUH_TEMPO=:jatuhTempo, DISCOUNT_1=:discount1, PPN=:ppn, TOTAL=:total, STATUS=:status, KETERANGAN=:keterangan, PELUNASAN=:pelunasan, TITIPAN=:titipan,UPDATED=:updated, STATUS_SYNC_ONLINE = false WHERE ID=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)pembelian);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int deletePembelian(Pembelian pembelian) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int deletePembelianForce(Pembelian pembelian) {
        String sql = "DELETE FROM t_pembelian WHERE ID = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{pembelian.getId()});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public String getLastId() {
        String sql = "SELECT ID FROM t_pembelian ORDER BY ID DESC LIMIT ?";
        String id = "";
        try {
            id = (String)this.getSimpleJdbcTemplate().queryForObject(sql, String.class, new Object[]{1});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return id;
    }

    @Override
    public List<Pembelian> getPembelianByDate(String startDate, String endDate, String idSupplier, String idKaryawan, String keterangan) {
        String sql = "SELECT * FROM t_pembelian WHERE TANGGAL >= ? AND TANGGAL <= ? AND M_SUPPLIER_ID LIKE ? AND M_KARYAWAN_ID LIKE ? AND KETERANGAN LIKE ? ";
        List pembelians = null;
        try {
            idSupplier = '%' + idSupplier + '%';
            idKaryawan = '%' + idKaryawan + '%';
            keterangan = '%' + keterangan + '%';
            pembelians = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PembelianRowMapper(), new Object[]{startDate, endDate, idSupplier, idKaryawan, keterangan});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return pembelians;
    }

    @Override
    public List<Pembelian> getPembelian(String startDate, String endDate, String pembayaran, String keterangan) {
        String sql = "SELECT * FROM t_pembelian WHERE TANGGAL >= ? AND TANGGAL <= ? AND PEMBAYARAN = ? AND KETERANGAN = ?";
        List pembelians = null;
        try {
            pembelians = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PembelianRowMapper(), new Object[]{startDate, endDate, pembayaran, keterangan});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return pembelians;
    }

    @Override
    public List<Object[]> getHutang(String idSupplier) {
        String sql = "SELECT T_PEMBELIAN.ID AS T_PEMBELIAN_ID, TANGGAL, PEMBAYARAN, JATUH_TEMPO, NAMA, TOTAL, TITIPAN, PELUNASAN FROM T_PEMBELIAN JOIN M_SUPPLIER ON (T_PEMBELIAN.M_SUPPLIER_ID = M_SUPPLIER.ID) WHERE M_SUPPLIER_ID LIKE ? AND PELUNASAN = ?";
        LinkedList<Object[]> pembelians = new LinkedList<Object[]>();
        try {
            idSupplier = '%' + idSupplier + '%';
            List rows = this.getJdbcTemplate().queryForList(sql, new Object[]{idSupplier, "BELUM LUNAS"});
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("T_PEMBELIAN_ID"), row.get("TANGGAL"), row.get("PEMBAYARAN"), row.get("JATUH_TEMPO"), row.get("NAMA"), row.get("TOTAL"), row.get("TITIPAN"), row.get("PELUNASAN")};
                pembelians.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return pembelians;
    }

    @Override
    public List<Object[]> getHutangSudahJatuhTempo(String idSupplier) {
        String sql = "SELECT T_PEMBELIAN.ID AS T_PEMBELIAN_ID, TANGGAL, PEMBAYARAN, JATUH_TEMPO, NAMA, TOTAL, TITIPAN, PELUNASAN FROM T_PEMBELIAN JOIN M_SUPPLIER ON (T_PEMBELIAN.M_SUPPLIER_ID = M_SUPPLIER.ID) WHERE M_SUPPLIER_ID LIKE ? AND PELUNASAN = ? AND JATUH_TEMPO <= ?";
        LinkedList<Object[]> pembelians = new LinkedList<Object[]>();
        try {
            idSupplier = '%' + idSupplier + '%';
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String startDateString = simpleDateFormat.format(new Date());
            List rows = this.getJdbcTemplate().queryForList(sql, new Object[]{idSupplier, "BELUM LUNAS", startDateString});
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("T_PEMBELIAN_ID"), row.get("TANGGAL"), row.get("PEMBAYARAN"), row.get("JATUH_TEMPO"), row.get("NAMA"), row.get("TOTAL"), row.get("TITIPAN"), row.get("PELUNASAN")};
                pembelians.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return pembelians;
    }

    @Override
    public List<PembelianO2O> getAllPembelianUnsynced() {
        String sql = "SELECT * FROM t_pembelian WHERE status_sync_online = false";
        ArrayList<PembelianO2O> results = new ArrayList<PembelianO2O>();
        try {
            results.addAll(this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PembelianO2ORowMapper(), new Object[0]));
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return results;
    }

    @Override
    public int updateStatusSyncedPembelian(String ids) {
        String sql = "update t_pembelian set status_sync_online = true where id in (" + ids + ")";
        int result = -1;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int[] upsertDataPembelianDariOnline(PembelianO2O[] datas) {
        int[] results = null;
        String sql_upsert = "INSERT INTO t_pembelian (id, m_supplier_id, m_karyawan_id, faktur_id, tanggal, pembayaran, jatuh_tempo, discount_1, ppn, total, status, keterangan, pelunasan, created, updated, titipan, status_sync_online) VALUES(:id_pembelian_offline, :m_supplier_id, :m_karyawan_id, :faktur_id, :tanggal, :pembayaran, :jatuh_tempo, :discount_1, :ppn, :total, :status, :keterangan, :pelunasan, :created, :updated, :titipan, true) ON DUPLICATE KEY UPDATE m_supplier_id = VALUES(m_supplier_id), m_karyawan_id = VALUES(m_karyawan_id), faktur_id = VALUES(faktur_id), tanggal = VALUES(tanggal), pembayaran = VALUES(pembayaran), jatuh_tempo = VALUES(jatuh_tempo), discount_1 = VALUES(discount_1), ppn = VALUES(ppn), total = VALUES(total), status = VALUES(status), keterangan = VALUES(keterangan), pelunasan = VALUES(pelunasan), created = VALUES(created), updated = VALUES(updated), titipan = VALUES(titipan), status_sync_online = true";
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch((Object[])datas);
        try {
            results = this.getSimpleJdbcTemplate().batchUpdate(sql_upsert, batch);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return results;
    }

    @Override
    public String getLatestDate() {
        String result = null;
        String sql = "SELECT  DATE_FORMAT(GREATEST(coalesce(MAX(created), 0), coalesce(MAX(updated), 0)), '%Y-%m-%d') FROM t_pembelian";
        try {
            result = (String)this.getSimpleJdbcTemplate().queryForObject(sql, String.class, new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int checkIdPoExist(int idPo) {
        String sql = "SELECT COUNT(*) as exist FROM t_pembelian WHERE id_po_online = ? ";
        int exist = 0;
        try {
            exist = (Integer)this.getSimpleJdbcTemplate().queryForObject(sql, Integer.class, new Object[]{idPo});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return exist;
    }
}

