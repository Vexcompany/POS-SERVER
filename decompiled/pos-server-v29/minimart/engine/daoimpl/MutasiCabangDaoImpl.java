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

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.controller.Helper;
import minimart.engine.dao.MutasiCabangDao;
import minimart.engine.model.Karyawan;
import minimart.engine.model.MutasiCabang;
import minimart.engine.model_online.PenjualanO2O;
import minimart.engine.rowmapper.MacRowMapper;
import minimart.engine.rowmapper.PenjualanO2ORowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class MutasiCabangDaoImpl
extends SimpleJdbcDaoSupport
implements MutasiCabangDao {
    private Helper helper = Helper.getInstance();

    public MutasiCabangDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createPenjualan(MutasiCabang mac) {
        String sql = "INSERT INTO t_mutasi_cabang (ID, M_CUSTOMER_ID, M_KARYAWAN_ID, KASIR, TANGGAL, TOTAL_PENJUALAN, STATUS_PELUNASAN, CREATED, UPDATED, JATUH_TEMPO, KETERANGAN, M_SHIFT_KASIR_ID, ID_TRANSAKSI_ONLINE) VALUES (:id, :customerId, :karyawanId, :kasir, :tanggal, :totalPenjualan, :statusPelunasan, :created, :updated, :jatuhTempo, :keterangan, :shiftKasirId, :idTransaksiOnline)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)mac);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int[] createPenjualanBatch(List<MutasiCabang> macs) {
        String sql = "INSERT INTO t_mutasi_cabang (ID, M_CUSTOMER_ID, M_KARYAWAN_ID, KASIR, TANGGAL, TOTAL_PENJUALAN, STATUS, STATUS_PELUNASAN, CREATED, UPDATED, JATUH_TEMPO, KETERANGAN, M_SHIFT_KASIR_ID, ID_TRANSAKSI_ONLINE) VALUES (:id, :customerId, :karyawanId, :kasir, :tanggal, :totalPenjualan, :status, :statusPelunasan, :created, :updated, :jatuhTempo, :keterangan, :shiftKasirId, :idTransaksiOnline)";
        int[] results = null;
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch((Object[])macs.toArray());
        try {
            results = this.getSimpleJdbcTemplate().batchUpdate(sql, batch);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return results;
    }

    @Override
    public MutasiCabang getPenjualan(String id) {
        String sql = "SELECT * FROM t_mutasi_cabang WHERE ID = ? AND STATUS = ?";
        MutasiCabang mac = null;
        try {
            mac = (MutasiCabang)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new MacRowMapper(), new Object[]{id, "TOKO"});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return mac;
    }

    @Override
    public List<Object[]> getPenjualans(String startDate, String endDate, String customerId, String salesId) {
        String sql = "SELECT T_MUTASI_CABANG.ID AS T_MUTASI_CABANG_ID, TANGGAL, JATUH_TEMPO, T_MUTASI_CABANG.CREATED AS CREATED, M_CUSTOMER_ID, NAMA, M_KARYAWAN_ID, TOTAL_PENJUALAN, T_MUTASI_CABANG.KETERANGAN AS KETERANGAN  FROM T_MUTASI_CABANG JOIN M_CUSTOMER ON (T_MUTASI_CABANG.M_CUSTOMER_ID = M_CUSTOMER.ID) WHERE TANGGAL>=? AND TANGGAL<=? AND M_CUSTOMER_ID LIKE ? AND STATUS = ? ORDER BY CREATED DESC";
        LinkedList<Object[]> penjualans = new LinkedList<Object[]>();
        try {
            customerId = '%' + customerId + '%';
            List rows = this.getJdbcTemplate().queryForList(sql, new Object[]{startDate, endDate, customerId, salesId, "TOKO"});
            for (Map row : rows) {
                Object[] returnValue = new Object[10];
                returnValue[0] = row.get("T_MUTASI_CABANG_ID");
                returnValue[1] = row.get("TANGGAL");
                returnValue[2] = row.get("JATUH_TEMPO");
                returnValue[3] = row.get("CREATED");
                returnValue[4] = row.get("M_CUSTOMER_ID");
                returnValue[5] = row.get("NAMA");
                returnValue[6] = row.get("M_KARYAWAN_ID");
                returnValue[7] = row.get("TOTAL_PENJUALAN");
                returnValue[8] = row.get("KETERANGAN");
                penjualans.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return penjualans;
    }

    @Override
    public List<MutasiCabang> getPenjualansKredit(String startDate, String endDate, String customerId) {
        String sql = "SELECT * FROM t_mutasi_cabang WHERE TANGGAL>=? AND TANGGAL<=? AND M_CUSTOMER_ID LIKE ? AND STATUS = ? AND KETERANGAN = 'KREDIT' AND M_SHIFT_KASIR_ID <> 0 AND M_SHIFT_KASIR_ID IS NOT NULL ORDER BY CREATED DESC";
        List penjualans = null;
        try {
            customerId = '%' + customerId + '%';
            penjualans = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new MacRowMapper(), new Object[]{startDate, endDate, customerId, "TOKO"});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return penjualans;
    }

    @Override
    public List<Object[]> getPenjualansWithKeterangan(String startDate, String endDate, String customerId, String keterangan) {
        String sql = "SELECT T_PENJUALAN.ID AS T_PENJUALAN_ID, TANGGAL, JATUH_TEMPO, T_PENJUALAN.CREATED AS CREATED, M_CUSTOMER_ID, NAMA, M_KARYAWAN_ID, TOTAL_PENJUALAN, T_PENJUALAN.KETERANGAN AS KETERANGAN,VOUCHER FROM t_penjualan JOIN M_CUSTOMER ON (T_PENJUALAN.M_CUSTOMER_ID = M_CUSTOMER.ID) WHERE TANGGAL>=? AND TANGGAL<=? AND M_CUSTOMER_ID LIKE ? AND STATUS = ? AND T_PENJUALAN.KETERANGAN like ? AND M_SHIFT_KASIR_ID <> 0 AND M_SHIFT_KASIR_ID IS NOT NULL ORDER BY T_PENJUALAN.CREATED DESC";
        LinkedList<Object[]> penjualans = new LinkedList<Object[]>();
        try {
            customerId = '%' + customerId + '%';
            keterangan = '%' + keterangan + '%';
            List rows = this.getJdbcTemplate().queryForList(sql, new Object[]{startDate, endDate, customerId, "TOKO", keterangan});
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("T_PENJUALAN_ID"), row.get("TANGGAL"), row.get("JATUH_TEMPO"), row.get("CREATED"), row.get("M_CUSTOMER_ID"), row.get("NAMA"), row.get("M_KARYAWAN_ID"), row.get("TOTAL_PENJUALAN"), row.get("KETERANGAN"), row.get("VOUCHER")};
                penjualans.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return penjualans;
    }

    @Override
    public List<Object[]> getPiutangJatuhTempo() {
        String sql = "SELECT T_PENJUALAN.ID AS T_PENJUALAN_ID, NAMA, TANGGAL, JATUH_TEMPO, M_SALES_ID, TOTAL_PENJUALAN FROM T_PENJUALAN JOIN M_CUSTOMER ON (T_PENJUALAN.M_CUSTOMER_ID = M_CUSTOMER.ID) WHERE STATUS_PELUNASAN = 'BELUM LUNAS' AND JATUH_TEMPO<= CURDATE() ORDER BY M_CUSTOMER_ID";
        LinkedList<Object[]> penjualans = new LinkedList<Object[]>();
        try {
            List rows = this.getJdbcTemplate().queryForList(sql);
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("T_PENJUALAN_ID"), row.get("NAMA"), row.get("TANGGAL"), row.get("JATUH_TEMPO"), row.get("M_SALES_ID"), row.get("TOTAL_PENJUALAN")};
                penjualans.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return penjualans;
    }

    @Override
    public List<Object[]> getPiutang(String customerId) {
        String sql = "SELECT T_PENJUALAN.ID AS T_PENJUALAN_ID, NAMA, TANGGAL, JATUH_TEMPO, M_SALES_ID, TOTAL_PENJUALAN, STATUS_PELUNASAN, REFERENSI FROM T_PENJUALAN JOIN M_CUSTOMER ON (T_PENJUALAN.M_CUSTOMER_ID = M_CUSTOMER.ID) WHERE M_CUSTOMER_ID LIKE ? AND STATUS_PELUNASAN = 'BELUM LUNAS' ORDER BY T_PENJUALAN.CREATED DESC";
        LinkedList<Object[]> penjualans = new LinkedList<Object[]>();
        customerId = '%' + customerId + '%';
        try {
            List rows = this.getJdbcTemplate().queryForList(sql, new Object[]{customerId});
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("T_PENJUALAN_ID"), row.get("NAMA"), row.get("TANGGAL"), row.get("JATUH_TEMPO"), row.get("M_SALES_ID"), row.get("TOTAL_PENJUALAN"), row.get("STATUS_PELUNASAN"), row.get("REFERENSI")};
                penjualans.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return penjualans;
    }

    @Override
    public long getTotalKeuntunganPenjualan(String idPenjualan) {
        long keuntungan = 0L;
        String sql = "select sum(total_keuntungan) from t_penjualan_detail where t_penjualan_id = ? group by t_penjualan_id;";
        try {
            keuntungan = (Long)this.getSimpleJdbcTemplate().queryForObject(sql, Long.class, new Object[]{idPenjualan});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return keuntungan;
    }

    @Override
    public List<Object[]> getPenjualanSales(String salesId, String startDate, String endDate) {
        String sql = "SELECT m_jenis_jenis,sum(t_penjualan_detail.total_harga),sum(t_penjualan_detail.total_keuntungan) FROM t_penjualan join t_penjualan_detail on (t_penjualan.id = t_penjualan_detail.t_penjualan_id) join m_barang on (m_barang.id = t_penjualan_detail.m_barang_id) where t_penjualan.m_sales_id = ? and t_penjualan.tanggal >= ? and t_penjualan.tanggal <= ? group by m_jenis_jenis";
        LinkedList<Object[]> objects = new LinkedList<Object[]>();
        try {
            List rows = this.getJdbcTemplate().queryForList(sql, new Object[]{salesId, startDate, endDate});
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("m_jenis_jenis"), row.get("sum(t_penjualan_detail.total_harga)"), row.get("sum(t_penjualan_detail.total_keuntungan)")};
                objects.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return objects;
    }

    @Override
    public List<Object[]> getPoinPenjualanCustomers(String startDate, String endDate) {
        String sql = "select m_customer_id,nama, sum(poin) from t_penjualan join m_customer on (t_penjualan.m_customer_id = m_customer.id) where tanggal >= ? and tanggal <= ? group by m_customer_id;";
        LinkedList<Object[]> objects = new LinkedList<Object[]>();
        try {
            List rows = this.getJdbcTemplate().queryForList(sql, new Object[]{startDate, endDate});
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("m_customer_id"), row.get("nama"), row.get("sum(poin)")};
                objects.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return objects;
    }

    @Override
    public List<Object[]> getRekapPenjualan(String status, String startDate, String endDate) {
        String sql = "SELECT TANGGAL, SUM(TOTAL_PENJUALAN) FROM t_penjualan WHERE STATUS = ? AND TANGGAL >= ? AND TANGGAL <= ? AND M_SHIFT_KASIR_ID <> 0 AND M_SHIFT_KASIR_ID IS NOT NULL GROUP BY TANGGAL ORDER BY TANGGAL DESC";
        LinkedList<Object[]> objects = new LinkedList<Object[]>();
        try {
            List rows = this.getJdbcTemplate().queryForList(sql, new Object[]{status, startDate, endDate});
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("TANGGAL"), ((BigDecimal)row.get("SUM(TOTAL_PENJUALAN)")).longValue()};
                objects.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return objects;
    }

    @Override
    public List<Object[]> getRekapPenjualanByKeterangan(String status, String startDate, String endDate, String keterangan) {
        String sql = "SELECT TANGGAL, SUM(TOTAL_PENJUALAN) FROM t_penjualan WHERE STATUS = ? AND TANGGAL >= ? AND TANGGAL <= ? AND KETERANGAN = ? GROUP BY TANGGAL ORDER BY TANGGAL DESC";
        LinkedList<Object[]> objects = new LinkedList<Object[]>();
        try {
            List rows = this.getJdbcTemplate().queryForList(sql, new Object[]{status, startDate, endDate, keterangan});
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("TANGGAL"), ((BigDecimal)row.get("SUM(TOTAL_PENJUALAN)")).longValue()};
                objects.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return objects;
    }

    @Override
    public List<MutasiCabang> getPenjualanTerakhir(String date) {
        System.out.println(date);
        String sql = "SELECT * FROM t_penjualan WHERE TANGGAL = ? AND KASIR = ? AND STATUS = ? ORDER BY CREATED DESC";
        List penjualans = null;
        try {
            penjualans = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new MacRowMapper(), new Object[]{date, Helper.getInstance().getKasir(), "TOKO"});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return penjualans;
    }

    @Override
    public int updatePenjualan(MutasiCabang penjualan) {
        String sql = "UPDATE t_penjualan SET M_CUSTOMER_ID=:customerId, M_KARYAWAN_ID=:karyawanId, M_SALES_ID=:salesId, TANGGAL=:tanggal, TOTAL_PENJUALAN=:totalPenjualan, POIN=:poin, UPDATED=:updated, JATUH_TEMPO=:jatuhTempo, KETERANGAN=:keterangan, STATUS_PELUNASAN=:statusPelunasan, M_SHIFT_KASIR_ID=:shiftKasirId, STATUS_SYNC_ONLINE = false,ELIGIBLE_PWP=:eligiblePwp, ELIGIBLE_TEBUS_MURAH=:eligibleTebusMurah WHERE ID=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)penjualan);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int deletePenjualan(String id) {
        String sql = "DELETE FROM t_penjualan WHERE ID = ?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{id});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public String getLastId() {
        String sql = "SELECT ID FROM t_penjualan ORDER BY ID DESC LIMIT ?";
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
    public long getTotalKreditCustomer(String idCustomer) {
        long totalKredit = 0L;
        String sql = "SELECT SUM(TOTAL_PENJUALAN) FROM T_PENJUALAN WHERE M_CUSTOMER_ID = ? AND STATUS_PELUNASAN = 'BELUM LUNAS' GROUP BY M_CUSTOMER_ID";
        try {
            totalKredit = (Long)this.getSimpleJdbcTemplate().queryForObject(sql, Long.class, new Object[]{idCustomer});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return totalKredit;
    }

    @Override
    public long getTotalVoucherShift(int kasir, String keterangan) {
        long totalVoucher = 0L;
        keterangan = '%' + keterangan + '%';
        String sql = "SELECT SUM(JUMLAH) FROM T_PENJUALAN JOIN M_VOUCHER ON (T_PENJUALAN.VOUCHER = M_VOUCHER.ID) WHERE KETERANGAN LIKE ? AND KASIR = ? AND (t_penjualan.M_SHIFT_KASIR_ID is null or t_penjualan.M_SHIFT_KASIR_ID = 0)";
        try {
            totalVoucher = this.getSimpleJdbcTemplate().queryForObject(sql, Long.class, new Object[]{keterangan, kasir}) == null ? 0L : (Long)this.getSimpleJdbcTemplate().queryForObject(sql, Long.class, new Object[]{keterangan, kasir});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return totalVoucher;
    }

    @Override
    public long getTotalVoucherByIdShift(int shiftKasirId, String keterangan) {
        long totalVoucher = 0L;
        keterangan = '%' + keterangan + '%';
        String sql = "SELECT SUM(JUMLAH) FROM T_PENJUALAN JOIN M_VOUCHER ON (T_PENJUALAN.VOUCHER = M_VOUCHER.ID) WHERE KETERANGAN LIKE ? AND M_SHIFT_KASIR_ID = ? ";
        try {
            totalVoucher = this.getSimpleJdbcTemplate().queryForObject(sql, Long.class, new Object[]{keterangan, shiftKasirId}) == null ? 0L : (Long)this.getSimpleJdbcTemplate().queryForObject(sql, Long.class, new Object[]{keterangan, shiftKasirId});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return totalVoucher;
    }

    @Override
    public long getTotalOpenedShift(int kasir, String keterangan) {
        long totalKasir = 0L;
        keterangan = '%' + keterangan + '%';
        String sql = "SELECT SUM(TOTAL_PENJUALAN) FROM T_PENJUALAN WHERE KETERANGAN like ? AND KASIR = ? AND (M_SHIFT_KASIR_ID is null or M_SHIFT_KASIR_ID = 0)";
        try {
            totalKasir = this.getSimpleJdbcTemplate().queryForObject(sql, Long.class, new Object[]{keterangan, kasir}) == null ? 0L : (Long)this.getSimpleJdbcTemplate().queryForObject(sql, Long.class, new Object[]{keterangan, kasir});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return totalKasir;
    }

    @Override
    public long getTotalPenjualanByShift(int shiftKasirId, String keterangan) {
        long totalKasir = 0L;
        keterangan = "%" + keterangan + "%";
        String sql = "SELECT SUM(TOTAL_PENJUALAN) FROM T_PENJUALAN WHERE M_SHIFT_KASIR_ID = ? and KETERANGAN LIKE ?";
        try {
            totalKasir = this.getSimpleJdbcTemplate().queryForObject(sql, Long.class, new Object[]{shiftKasirId, keterangan}) == null ? 0L : (Long)this.getSimpleJdbcTemplate().queryForObject(sql, Long.class, new Object[]{shiftKasirId, keterangan});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return totalKasir;
    }

    @Override
    public List<Object[]> getPenjualansByShift(int shiftKasirId, String tanggal) {
        String sql = "";
        sql = shiftKasirId == 0 ? "SELECT T_PENJUALAN.ID AS ID, T_PENJUALAN.M_CUSTOMER_ID AS CUSTOMER, TANGGAL, TOTAL_PENJUALAN,KETERANGAN,M_VOUCHER.JUMLAH AS JUMLAH FROM T_PENJUALAN LEFT JOIN M_VOUCHER  ON (T_PENJUALAN.VOUCHER = M_VOUCHER.ID) where TANGGAL = ?" : "SELECT T_PENJUALAN.ID AS ID, T_PENJUALAN.M_CUSTOMER_ID AS CUSTOMER, TANGGAL, TOTAL_PENJUALAN,KETERANGAN,M_VOUCHER.JUMLAH AS JUMLAH FROM T_PENJUALAN LEFT JOIN M_VOUCHER  ON (T_PENJUALAN.VOUCHER = M_VOUCHER.ID) WHERE M_SHIFT_KASIR_ID = ? and TANGGAL = ?";
        LinkedList<Object[]> penjualans = new LinkedList<Object[]>();
        try {
            List rows = shiftKasirId == 0 ? this.getJdbcTemplate().queryForList(sql, new Object[]{tanggal}) : this.getJdbcTemplate().queryForList(sql, new Object[]{shiftKasirId, tanggal});
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("ID"), row.get("CUSTOMER"), row.get("TANGGAL"), row.get("TOTAL_PENJUALAN"), row.get("KETERANGAN"), row.get("JUMLAH")};
                penjualans.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return penjualans;
    }

    @Override
    public List<Object[]> getPenjualansByShift(int shiftKasirId) {
        String sql = "";
        sql = shiftKasirId == 0 ? "SELECT T_PENJUALAN.ID AS ID, T_PENJUALAN.M_CUSTOMER_ID AS CUSTOMER, TANGGAL, TOTAL_PENJUALAN,KETERANGAN,M_VOUCHER.JUMLAH AS JUMLAH FROM T_PENJUALAN LEFT JOIN M_VOUCHER  ON (T_PENJUALAN.VOUCHER = M_VOUCHER.ID)" : "SELECT T_PENJUALAN.ID AS ID, T_PENJUALAN.M_CUSTOMER_ID AS CUSTOMER, TANGGAL, TOTAL_PENJUALAN,KETERANGAN,M_VOUCHER.JUMLAH AS JUMLAH FROM T_PENJUALAN LEFT JOIN M_VOUCHER  ON (T_PENJUALAN.VOUCHER = M_VOUCHER.ID) WHERE M_SHIFT_KASIR_ID = ?";
        LinkedList<Object[]> penjualans = new LinkedList<Object[]>();
        try {
            List rows = shiftKasirId == 0 ? this.getJdbcTemplate().queryForList(sql) : this.getJdbcTemplate().queryForList(sql, new Object[]{shiftKasirId});
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("ID"), row.get("CUSTOMER"), row.get("TANGGAL"), row.get("TOTAL_PENJUALAN"), row.get("KETERANGAN"), row.get("JUMLAH")};
                penjualans.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return penjualans;
    }

    @Override
    public List<MutasiCabang> getNotaJatuhTempo(String idCustomer) {
        String sql = "SELECT * FROM T_PENJUALAN WHERE M_CUSTOMER_ID = ? AND STATUS_PELUNASAN = 'BELUM LUNAS' AND JATUH_TEMPO <= ?";
        List penjualans = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String nowDate = simpleDateFormat.format(new Date());
            System.out.println(idCustomer + "," + nowDate);
            penjualans = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new MacRowMapper(), new Object[]{idCustomer, nowDate});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return penjualans;
    }

    @Override
    public List<Object[]> getTopCustomer(String startDate, String endDate, int limit) {
        String sql = "SELECT m_customer_id, sum(total_penjualan) FROM t_penjualan where tanggal >= ? and tanggal <= ? group by m_customer_Id order by sum(total_penjualan) desc ";
        String sql2 = "";
        LinkedList<Object[]> objects = new LinkedList<Object[]>();
        try {
            List rows;
            if (limit > 0) {
                sql2 = "limit ?";
                rows = this.getJdbcTemplate().queryForList(sql + sql2, new Object[]{startDate, endDate, limit});
            } else {
                rows = this.getJdbcTemplate().queryForList(sql + sql2, new Object[]{startDate, endDate});
            }
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("m_customer_id"), ((BigDecimal)row.get("SUM(TOTAL_PENJUALAN)")).longValue()};
                objects.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return objects;
    }

    @Override
    public List<PenjualanO2O> getAllPenjualanUnsyncedByKaryawan() {
        Karyawan karyawan = Helper.getInstance().getKaryawan();
        String sql = "SELECT * FROM t_penjualan WHERE status_sync_online = false AND m_karyawan_id = ?";
        List<PenjualanO2O> objects = new ArrayList<PenjualanO2O>();
        try {
            objects = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PenjualanO2ORowMapper(), new Object[]{karyawan.getId()});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return objects;
    }

    @Override
    public List<PenjualanO2O> getAllPenjualanUnsynced() {
        String sql = "SELECT * FROM t_penjualan WHERE status_sync_online = false";
        ArrayList<PenjualanO2O> objects = new ArrayList();
        try {
            objects = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new PenjualanO2ORowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return objects;
    }

    @Override
    public int[] updatePenjualanBatch(List<MutasiCabang> dataPenjualan) {
        String sql = "UPDATE t_penjualan SET M_CUSTOMER_ID=:customerId, M_KARYAWAN_ID=:karyawanId, M_SALES_ID=:salesId, TANGGAL=:tanggal, TOTAL_PENJUALAN=:totalPenjualan, POIN=:poin, UPDATED=:updated, JATUH_TEMPO=:jatuhTempo, KETERANGAN=:keterangan, STATUS_PELUNASAN=:statusPelunasan, M_SHIFT_KASIR_ID=:shiftKasirId, STATUS_SYNC_ONLINE = false,ELIGIBLE_PWP=:eligiblePwp, ELIGIBLE_TEBUS_MURAH=:eligibleTebusMurah WHERE ID=:id";
        int[] results = null;
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch((Object[])dataPenjualan.toArray());
        try {
            results = this.getSimpleJdbcTemplate().batchUpdate(sql, batch);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return results;
    }

    @Override
    public int[] updateStatusSyncedPenjualan(List<PenjualanO2O> data) {
        String sql = "update t_penjualan set status_sync_online = true where id = :id";
        int[] results = null;
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch((Object[])data.toArray());
        try {
            results = this.getSimpleJdbcTemplate().batchUpdate(sql, batch);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return results;
    }

    @Override
    public int updateStatusSyncedPenjualan(String ids) {
        String sql = "update t_penjualan set status_sync_online = true where id in (" + ids + ")";
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
    public int updatePenjualanShift(int shiftKasirId) {
        String sql = "update t_penjualan set m_shift_kasir_id = ? where kasir = ? and (m_shift_kasir_id = 0 or m_shift_kasir_id is null)";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{shiftKasirId, this.helper.getKasir()});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int[] upsertDataPenjualanDariOnline(PenjualanO2O[] datas) {
        int[] results = null;
        String sql_upsert = "INSERT INTO t_penjualan (id, m_customer_id, m_karyawan_id, m_sales_id, kasir, tanggal, jatuh_tempo, keterangan, total_penjualan, status, poin, created, updated, status_pelunasan, status_sync_online, voucher) VALUES(:id_penjualan_offline, :m_customer_id, :m_karyawan_id, :m_sales_id, :kasir, :tanggal, :jatuh_tempo, :keterangan, :total_penjualan, :status, :poin, :created, :updated, :status_pelunasan, true, voucher) ON DUPLICATE KEY UPDATE m_customer_id = VALUES(m_customer_id), m_karyawan_id = VALUES(m_karyawan_id), m_sales_id = VALUES(m_sales_id), kasir = VALUES(kasir), tanggal = VALUES(tanggal), jatuh_tempo = VALUES(jatuh_tempo), keterangan = VALUES(keterangan), total_penjualan = VALUES(total_penjualan), status = VALUES(status), poin = VALUES(poin), created = VALUES(created), updated = VALUES(updated), status_pelunasan = VALUES(status_pelunasan), status_sync_online = true, voucher = VALUES(voucher)";
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
        String sql = "SELECT DATE_FORMAT(GREATEST(coalesce(MAX(created), 0), coalesce(MAX(updated), 0)), '%Y-%m-%d') FROM t_penjualan";
        try {
            result = (String)this.getSimpleJdbcTemplate().queryForObject(sql, String.class, new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<Object[]> getPerformaOutlet(String startDate, String endDate) {
        String sql = "SELECT sum(total_penjualan) as total_penjualan, count(id) as total_struk, datediff(?,?) as selisih_hari FROM minimarket.t_penjualan WHERE tanggal >= ? and tanggal <= ?";
        List rows = null;
        LinkedList<Object[]> objects = new LinkedList<Object[]>();
        try {
            rows = this.getJdbcTemplate().queryForList(sql, new Object[]{endDate, startDate, startDate, endDate});
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("total_penjualan"), row.get("total_struk"), row.get("selisih_hari")};
                objects.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return objects;
    }

    @Override
    public List<Object[]> getPerformaPromo(String startDate, String endDate) {
        String sql = "SELECT tanggal, count(distinct t_penjualan.id) as total_transaksi, m_karyawan_id, sum(total_penjualan) as total_penjualan, sum(eligible_pwp) as eligible_pwp, sum(eligible_tebus_murah) as eligible_tebus_murah FROM minimarket.t_penjualan where tanggal >= ? and tanggal <= ? group by tanggal, m_karyawan_id";
        List rows = null;
        LinkedList<Object[]> objects = new LinkedList<Object[]>();
        try {
            rows = this.getJdbcTemplate().queryForList(sql, new Object[]{startDate, endDate});
            for (Map row : rows) {
                Object[] returnValue = new Object[]{row.get("tanggal"), row.get("total_transaksi"), row.get("m_karyawan_id"), row.get("total_penjualan"), row.get("eligible_pwp"), row.get("eligible_tebus_murah")};
                objects.add(returnValue);
            }
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return objects;
    }
}

