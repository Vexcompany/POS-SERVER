/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

public class DbCredential {
    int id_cabang;
    String server_ip_address;
    String mysql_port;
    String mysql_database;
    String mysql_username;
    String mysql_password;
    String nama_cabang;
    String alamat_cabang;
    String tipe_cabang;
    String tipe_toko;
    boolean is_gudang;

    public int getId_cabang() {
        return this.id_cabang;
    }

    public void setId_cabang(int id_cabang) {
        this.id_cabang = id_cabang;
    }

    public String getServer_ip_address() {
        return this.server_ip_address;
    }

    public void setServer_ip_address(String server_ip_address) {
        this.server_ip_address = server_ip_address;
    }

    public String getMysql_port() {
        return this.mysql_port;
    }

    public void setMysql_port(String mysql_port) {
        this.mysql_port = mysql_port;
    }

    public String getMysql_database() {
        return this.mysql_database;
    }

    public void setMysql_database(String mysql_database) {
        this.mysql_database = mysql_database;
    }

    public String getMysql_username() {
        return this.mysql_username;
    }

    public void setMysql_username(String mysql_username) {
        this.mysql_username = mysql_username;
    }

    public String getMysql_password() {
        return this.mysql_password;
    }

    public void setMysql_password(String mysql_password) {
        this.mysql_password = mysql_password;
    }

    public String getNama_cabang() {
        return this.nama_cabang;
    }

    public void setNama_cabang(String nama_cabang) {
        this.nama_cabang = nama_cabang;
    }

    public String getAlamat_cabang() {
        return this.alamat_cabang;
    }

    public void setAlamat_cabang(String alamat_cabang) {
        this.alamat_cabang = alamat_cabang;
    }

    public String getTipe_cabang() {
        return this.tipe_cabang;
    }

    public void setTipe_cabang(String tipe_cabang) {
        this.tipe_cabang = tipe_cabang;
    }

    public String getTipe_toko() {
        return this.tipe_toko;
    }

    public void setTipe_toko(String tipe_toko) {
        this.tipe_toko = tipe_toko;
    }

    public boolean isIs_gudang() {
        return this.is_gudang;
    }

    public void setIs_gudang(boolean is_gudang) {
        this.is_gudang = is_gudang;
    }
}

