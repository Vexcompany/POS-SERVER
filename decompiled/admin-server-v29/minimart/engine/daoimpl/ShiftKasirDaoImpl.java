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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.dao.ShiftKasirDao;
import minimart.engine.model.ShiftKasir;
import minimart.engine.model_online.ShiftKasirO2O;
import minimart.engine.rowmapper.ShiftKasirO2ORowMapper;
import minimart.engine.rowmapper.ShiftKasirRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class ShiftKasirDaoImpl
extends SimpleJdbcDaoSupport
implements ShiftKasirDao {
    public ShiftKasirDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int create(ShiftKasir shiftKasir) {
        String sql = "INSERT INTO m_shift_kasir (id_kasir, opened_by, closed_by, opening, closing, total_uang, jumlah_setoran, transfer, debit, qris, voucher, status_sync_online) VALUES (:idKasir, :openedBy, :closedBy, :opening, :closing, :totalUang, :jumlahSetoran, :transfer, :debit, :qris, :voucher, false)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)shiftKasir);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int update(ShiftKasir shiftKasir) {
        String sql = "UPDATE m_shift_kasir SET id_kasir=:idKasir, opened_by=:openedBy, closed_by=:closedBy, opening=:opening, closing=:closing, total_uang=:totalUang , jumlah_setoran=:jumlahSetoran ,transfer=:transfer, debit=:debit, qris=:qris, voucher=:voucher, status_sync_online=false  WHERE id=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)shiftKasir);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<ShiftKasir> getShifts(String startDate, String endDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date datePlus = null;
        try {
            datePlus = formatter.parse(endDate);
        }
        catch (ParseException ex) {
            Logger.getLogger(ShiftKasirDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        Calendar c = Calendar.getInstance();
        c.setTime(datePlus);
        c.add(5, 1);
        datePlus = c.getTime();
        String nextDay = formatter.format(datePlus);
        String sql = "SELECT * FROM m_shift_kasir WHERE OPENING>=? AND OPENING <=? ORDER BY ID_KASIR";
        List penjualans = null;
        try {
            penjualans = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new ShiftKasirRowMapper(), new Object[]{startDate, nextDay});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return penjualans;
    }

    @Override
    public List<ShiftKasir> getShiftsByKasir(String karyawan, String startDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date datePlus = null;
        try {
            datePlus = formatter.parse(startDate);
        }
        catch (ParseException ex) {
            Logger.getLogger(ShiftKasirDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        Calendar c = Calendar.getInstance();
        c.setTime(datePlus);
        c.add(5, 1);
        datePlus = c.getTime();
        String nextDay = formatter.format(datePlus);
        String sql = "SELECT * FROM m_shift_kasir WHERE OPENING>? AND OPENING <? and OPENED_BY = ?";
        List penjualans = null;
        try {
            penjualans = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new ShiftKasirRowMapper(), new Object[]{startDate, nextDay, karyawan});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return penjualans;
    }

    @Override
    public ShiftKasir getOpenedShiftKasir(int idkasir) {
        String sql = "SELECT * FROM m_shift_kasir WHERE ID_KASIR = ? AND CLOSING IS NULL order by opening limit 1";
        ShiftKasir shiftKasir = null;
        try {
            shiftKasir = (ShiftKasir)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new ShiftKasirRowMapper(), new Object[]{idkasir});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return shiftKasir;
    }

    @Override
    public Date getKasirDate(int idKasir) {
        String sql = "SELECT opening FROM m_shift_kasir where id_kasir = ? and closing is null";
        Date openDate = null;
        try {
            openDate = (Date)this.getSimpleJdbcTemplate().queryForObject(sql, Date.class, new Object[]{idKasir});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return openDate;
    }

    @Override
    public List<ShiftKasir> getIsOpenShifts(String tanggal) {
        tanggal = tanggal + " 11:00:00";
        String sql = "SELECT * FROM m_shift_kasir WHERE closing is null and opening > ?";
        List penjualans = null;
        try {
            penjualans = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new ShiftKasirRowMapper(), new Object[]{tanggal});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return penjualans;
    }

    @Override
    public List<ShiftKasir> getIsOpenShiftsBeforeDate(String date) {
        String sql = "SELECT * FROM m_shift_kasir WHERE closing is null and opening < ?";
        List penjualans = null;
        try {
            penjualans = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new ShiftKasirRowMapper(), new Object[]{date});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return penjualans;
    }

    @Override
    public List<ShiftKasir> getIsOpenAnyShifts() {
        String sql = "SELECT * FROM m_shift_kasir WHERE closing is null";
        List penjualans = null;
        try {
            penjualans = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new ShiftKasirRowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return penjualans;
    }

    @Override
    public List<ShiftKasirO2O> getAllShiftKasirUnsynced() {
        List list = null;
        String sql = "SELECT * FROM m_shift_kasir WHERE status_sync_online = false";
        try {
            list = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new ShiftKasirO2ORowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return list;
    }

    @Override
    public int updateShiftKasirUnsynced(String ids) {
        int result = -1;
        String sql = "UPDATE m_shift_kasir SET status_sync_online = true WHERE id IN (" + ids + ")";
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int[] upsertDataShiftKasirDariOnline(ShiftKasirO2O[] datas) {
        int[] results = null;
        String sql_upsert = "INSERT INTO m_shift_kasir (id, id_kasir, opened_by, closed_by, opening, closing, total_uang, jumlah_setoran, transfer, debit, qris, voucher, status_sync_online) VALUES (:id_offline, :id_kasir, :opened_by, :closed_by, :opening, :closing, :total_uang, :jumlah_setoran, :transfer, :debit, :qris, :voucher, true) ON DUPLICATE KEY UPDATE id_kasir = VALUES(id_kasir), opened_by = VALUES(opened_by), closed_by = VALUES(closed_by), opening = VALUES(opening), closing = VALUES(closing), total_uang = VALUES(total_uang), jumlah_setoran = VALUES(jumlah_setoran), transfer = VALUES(transfer), debit = VALUES(debit), qris = VALUES(qris), voucher = VALUES(voucher), status_sync_online = true;";
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
        String sql = "SELECT DATE_FORMAT(coalesce(MAX(opening), 0), '%Y-%m-%d') FROM m_shift_kasir";
        try {
            result = (String)this.getSimpleJdbcTemplate().queryForObject(sql, String.class, new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }
}

