/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

public class Region {
    String idProvinsi;
    String provinsi;
    String idKota;
    String kota;
    String idKecamatan;
    String kecamatan;
    String idKelurahan;
    String kelurahan;

    public Region() {
    }

    public Region(String idProvinsi, String provinsi, String idKota, String kota, String idKecamatan, String kecamatan, String idKelurahan, String kelurahan) {
        this.idProvinsi = idProvinsi;
        this.provinsi = provinsi;
        this.idKota = idKota;
        this.kota = kota;
        this.idKecamatan = idKecamatan;
        this.kecamatan = kecamatan;
        this.idKelurahan = idKelurahan;
        this.kelurahan = kelurahan;
    }

    public String getIdProvinsi() {
        return this.idProvinsi;
    }

    public void setIdProvinsi(String idProvinsi) {
        this.idProvinsi = idProvinsi;
    }

    public String getProvinsi() {
        return this.provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getIdKota() {
        return this.idKota;
    }

    public void setIdKota(String idKota) {
        this.idKota = idKota;
    }

    public String getKota() {
        return this.kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getIdKecamatan() {
        return this.idKecamatan;
    }

    public void setIdKecamatan(String idKecamatan) {
        this.idKecamatan = idKecamatan;
    }

    public String getKecamatan() {
        return this.kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getIdKelurahan() {
        return this.idKelurahan;
    }

    public void setIdKelurahan(String idKelurahan) {
        this.idKelurahan = idKelurahan;
    }

    public String getKelurahan() {
        return this.kelurahan;
    }

    public void setKelurahan(String kelurahan) {
        this.kelurahan = kelurahan;
    }
}

