/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

public class PromoVoucher {
    private int id;
    private int maximumVoucher;
    private int minimumTransaksi;
    private String khususPelanggan;
    private String status;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaximumVoucher() {
        return this.maximumVoucher;
    }

    public void setMaximumVoucher(int maximumVoucher) {
        this.maximumVoucher = maximumVoucher;
    }

    public int getMinimumTransaksi() {
        return this.minimumTransaksi;
    }

    public void setMinimumTransaksi(int minimumTransaksi) {
        this.minimumTransaksi = minimumTransaksi;
    }

    public String getKhususPelanggan() {
        return this.khususPelanggan;
    }

    public void setKhususPelanggan(String khususPelanggan) {
        this.khususPelanggan = khususPelanggan;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

