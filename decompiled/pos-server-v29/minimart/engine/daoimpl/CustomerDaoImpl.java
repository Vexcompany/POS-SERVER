/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 *  org.springframework.jdbc.core.simple.ParameterizedRowMapper
 *  org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport
 */
package minimart.engine.daoimpl;

import java.util.List;
import javax.sql.DataSource;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.dao.CustomerDao;
import minimart.engine.model.Customer;
import minimart.engine.rowmapper.CustomerRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class CustomerDaoImpl
extends SimpleJdbcDaoSupport
implements CustomerDao {
    public CustomerDaoImpl() {
        this.setDataSource((DataSource)ConnectionManager.getInstance().getDataSource());
    }

    @Override
    public int createCustomer(Customer customer) {
        String sql = "INSERT INTO m_customer (ID, KTP, NAMA, ALAMAT, TELEPON, HUTANG, KOTA, TANGGAL_LAHIR, POINT, AVAILABLE, CREATED, UPDATED, LIMIT_KREDIT, TOP, TIPE, KETERANGAN, TAG, NAMA_PERUSAHAAN, NOMOR_REKENING, BANK) VALUES (:id, :ktp,:nama, :alamat, :telepon, :hutang, :kota, :tanggalLahir, :point, :available, :created, :updated, :limitKredit, :top, :tipe, :keterangan, :tag, :namaPerusahaan, :nomorRekening, :bank)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)customer);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int createCustomerSyncFromOnline(Customer customer) {
        String sql = "INSERT INTO m_customer (ID, KTP, NAMA, ALAMAT, TELEPON, HUTANG, KOTA, TANGGAL_LAHIR, POINT, AVAILABLE, CREATED, UPDATED, LIMIT_KREDIT, TOP, TIPE, KETERANGAN, TAG, NAMA_PERUSAHAAN, createdbyCabang, nomor_rekening, bank) VALUES (:id, :ktp,:nama, :alamat, :telepon, :hutang, :kota, :tanggalLahir, :point, :available, :created, :updated, :limitKredit, :top, :tipe, :keterangan, :tag, :namaPerusahaan, :idCabang, :nomorRekening, :bank)";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)customer);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updatePointCustomer(int jumlah, String idCustomer) {
        String sql = "UPDATE m_customer SET point = point+? WHERE ID = ?;";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{jumlah, idCustomer});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public Customer getCustomerById(String id) {
        String sql = "SELECT * FROM m_customer WHERE ID = ?";
        Customer customer = null;
        try {
            customer = (Customer)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new CustomerRowMapper(), new Object[]{id});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return customer;
    }

    @Override
    public Customer getCustomerByTelp(String telp) {
        String sql = "SELECT * FROM m_customer WHERE TELEPON = ?";
        Customer customer = null;
        try {
            customer = (Customer)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new CustomerRowMapper(), new Object[]{telp});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return customer;
    }

    @Override
    public Customer getCustomerByIdCabang(int idCabang) {
        String sql = "SELECT * FROM m_customer WHERE idCabang = ?";
        Customer customer = null;
        try {
            customer = (Customer)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new CustomerRowMapper(), new Object[]{idCabang});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return customer;
    }

    @Override
    public List<Customer> getCustomers(String nama) {
        String sql = "SELECT * FROM m_customer WHERE NAMA LIKE ?";
        List customers = null;
        try {
            nama = '%' + nama + '%';
            customers = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new CustomerRowMapper(), new Object[]{nama});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return customers;
    }

    @Override
    public List<Customer> getCabangs() {
        String sql = "SELECT * FROM m_customer WHERE idCabang >0";
        List customers = null;
        try {
            customers = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new CustomerRowMapper(), new Object[0]);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return customers;
    }

    @Override
    public List<Customer> getCustomersByTelepon(String telepon) {
        String sql = "SELECT * FROM m_customer WHERE TELEPON = ?";
        List customers = null;
        try {
            customers = this.getSimpleJdbcTemplate().query(sql, (ParameterizedRowMapper)new CustomerRowMapper(), new Object[]{telepon});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return customers;
    }

    @Override
    public int updateCustomer(Customer customer) {
        String sql = "UPDATE m_customer SET KTP=:ktp, NAMA=:nama, ALAMAT=:alamat, TELEPON=:telepon, HUTANG=:hutang, KOTA=:kota, TANGGAL_LAHIR=:tanggalLahir, POINT=:point, AVAILABLE=:available, UPDATED=:updated, LIMIT_KREDIT=:limitKredit, TOP=:top , TIPE=:tipe, KETERANGAN=:keterangan, TAG=:tag, NAMA_PERUSAHAAN=:namaPerusahaan, NOMOR_REKENING=:nomorRekening, BANK=:bank WHERE ID=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)customer);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updateCustomerSyncFromOnline(Customer customer) {
        String sql = "UPDATE m_customer SET KTP=:ktp, NAMA=:nama, ALAMAT=:alamat, TELEPON=:telepon, HUTANG=:hutang, KOTA=:kota, TANGGAL_LAHIR=:tanggalLahir, POINT=:point, AVAILABLE=:available, UPDATED=:updated, LIMIT_KREDIT=:limitKredit, TOP=:top , TIPE=:tipe, KETERANGAN=:keterangan, TAG=:tag, NAMA_PERUSAHAAN=:namaPerusahaan, createdByCabang=:idCabang, NOMOR_REKENING=:nomorRekening, BANK=:bank WHERE ID=:id";
        int result = 0;
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource((Object)customer);
        try {
            result = this.getSimpleJdbcTemplate().update(sql, (SqlParameterSource)parameterSource);
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int updateCustomer2(Customer customer, String newId) {
        System.out.println(customer.getUpdated());
        String sql = "UPDATE m_customer SET ID=?, KTP=?, NAMA=?, ALAMAT=?, TELEPON=?, HUTANG=?, KOTA=?, TANGGAL_LAHIR=?, POINT=?, AVAILABLE=?, UPDATED=?, LIMIT_KREDIT=?, TOP=? , TIPE =?, KETERANGAN=?, TAG=?, NAMA_PERUSAHAAN=? , NOMOR_REKENING=?, BANK=? WHERE ID=?";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{newId, customer.getKtp(), customer.getNama(), customer.getAlamat(), customer.getTelepon(), customer.getHutang(), customer.getKota(), customer.getTanggalLahir(), customer.getPoint(), customer.isAvailable(), customer.getUpdated(), customer.getLimitKredit(), customer.getTop(), customer.getTipe(), customer.getKeterangan(), customer.getTag(), customer.getNamaPerusahaan(), customer.getNomorRekening(), customer.getBank(), customer.getId()});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }

    @Override
    public int deleteCustomer(String id) {
        String sql = "DELETE FROM m_customer WHERE ID = ?";
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
        String sql = "SELECT ID FROM m_customer WHERE ID <> 'C' AND ID <> 'SINAR' AND ID <> 'LUKAS' ORDER BY ID DESC LIMIT ?";
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
    public Customer getCustomerByKtp(String ktp) {
        String sql = "SELECT * FROM m_customer WHERE KTP = ?";
        Customer customer = null;
        try {
            customer = (Customer)this.getSimpleJdbcTemplate().queryForObject(sql, (ParameterizedRowMapper)new CustomerRowMapper(), new Object[]{ktp});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return customer;
    }

    @Override
    public int updateHardPointCustomer(int jumlah, String ktpCustomer) {
        String sql = "UPDATE m_customer SET point = ? WHERE KTP = ?;";
        int result = 0;
        try {
            result = this.getSimpleJdbcTemplate().update(sql, new Object[]{jumlah, ktpCustomer});
        }
        catch (DataAccessException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return result;
    }
}

