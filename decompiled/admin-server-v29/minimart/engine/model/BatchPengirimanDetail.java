/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

public class BatchPengirimanDetail {
    long id;
    String idSku;
    int quantityKirim;
    String satuanKirim;
    int konversiSatuanKirim;
    int idTPaketPengiriman;
    int quantityTerima;
    String satuanTerima;
    int konversiSatuanTerima;
    boolean statusLock;
    String namaItem;
    String jenisItem;
    long hpp;
    String kodePaket;
    int idCabangPengirim;

    public String getKodePaket() {
        return this.kodePaket;
    }

    public void setKodePaket(String kodePaket) {
        this.kodePaket = kodePaket;
    }

    public int getIdCabangPengirim() {
        return this.idCabangPengirim;
    }

    public void setIdCabangPengirim(int idCabangPengirim) {
        this.idCabangPengirim = idCabangPengirim;
    }

    public BatchPengirimanDetail(long id, String idSku, int quantityKirim, String satuanKirim, int konversiSatuanKirim, int idTPaketPengiriman, int quantityTerima, String satuanTerima, int konversiSatuanTerima, boolean statusLock, String namaItem, String jenisItem, long hpp, String kodePaket, int idCabangPengirim) {
        this.id = id;
        this.idSku = idSku;
        this.quantityKirim = quantityKirim;
        this.satuanKirim = satuanKirim;
        this.konversiSatuanKirim = konversiSatuanKirim;
        this.idTPaketPengiriman = idTPaketPengiriman;
        this.quantityTerima = quantityTerima;
        this.satuanTerima = satuanTerima;
        this.konversiSatuanTerima = konversiSatuanTerima;
        this.statusLock = statusLock;
        this.namaItem = namaItem;
        this.jenisItem = jenisItem;
        this.hpp = hpp;
        this.kodePaket = kodePaket;
        this.idCabangPengirim = idCabangPengirim;
    }

    public BatchPengirimanDetail(long id, String idSku, int quantityKirim, String satuanKirim, int konversiSatuanKirim, int idTPaketPengiriman, int quantityTerima, String satuanTerima, int konversiSatuanTerima, boolean statusLock, String namaItem, String jenisItem, long hpp) {
        this.id = id;
        this.idSku = idSku;
        this.quantityKirim = quantityKirim;
        this.satuanKirim = satuanKirim;
        this.konversiSatuanKirim = konversiSatuanKirim;
        this.idTPaketPengiriman = idTPaketPengiriman;
        this.quantityTerima = quantityTerima;
        this.satuanTerima = satuanTerima;
        this.konversiSatuanTerima = konversiSatuanTerima;
        this.statusLock = statusLock;
        this.namaItem = namaItem;
        this.jenisItem = jenisItem;
        this.hpp = hpp;
    }

    public String getNamaItem() {
        return this.namaItem;
    }

    public void setNamaItem(String namaItem) {
        this.namaItem = namaItem;
    }

    public String getJenisItem() {
        return this.jenisItem;
    }

    public void setJenisItem(String jenisItem) {
        this.jenisItem = jenisItem;
    }

    public long getHpp() {
        return this.hpp;
    }

    public void setHpp(long hpp) {
        this.hpp = hpp;
    }

    public BatchPengirimanDetail() {
    }

    public BatchPengirimanDetail(long id, String idSku, int quantityKirim, String satuanKirim, int konversiSatuanKirim, int idTPaketPengiriman, int quantityTerima, String satuanTerima, int konversiSatuanTerima, boolean statusLock) {
        this.id = id;
        this.idSku = idSku;
        this.quantityKirim = quantityKirim;
        this.satuanKirim = satuanKirim;
        this.konversiSatuanKirim = konversiSatuanKirim;
        this.idTPaketPengiriman = idTPaketPengiriman;
        this.quantityTerima = quantityTerima;
        this.satuanTerima = satuanTerima;
        this.konversiSatuanTerima = konversiSatuanTerima;
        this.statusLock = statusLock;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdSku() {
        return this.idSku;
    }

    public void setIdSku(String idSku) {
        this.idSku = idSku;
    }

    public int getQuantityKirim() {
        return this.quantityKirim;
    }

    public void setQuantityKirim(int quantityKirim) {
        this.quantityKirim = quantityKirim;
    }

    public String getSatuanKirim() {
        return this.satuanKirim;
    }

    public void setSatuanKirim(String satuanKirim) {
        this.satuanKirim = satuanKirim;
    }

    public int getKonversiSatuanKirim() {
        return this.konversiSatuanKirim;
    }

    public void setKonversiSatuanKirim(int konversiSatuanKirim) {
        this.konversiSatuanKirim = konversiSatuanKirim;
    }

    public int getIdTPaketPengiriman() {
        return this.idTPaketPengiriman;
    }

    public void setIdTPaketPengiriman(int idTPaketPengiriman) {
        this.idTPaketPengiriman = idTPaketPengiriman;
    }

    public int getQuantityTerima() {
        return this.quantityTerima;
    }

    public void setQuantityTerima(int quantityTerima) {
        this.quantityTerima = quantityTerima;
    }

    public String getSatuanTerima() {
        return this.satuanTerima;
    }

    public void setSatuanTerima(String satuanTerima) {
        this.satuanTerima = satuanTerima;
    }

    public int getKonversiSatuanTerima() {
        return this.konversiSatuanTerima;
    }

    public void setKonversiSatuanTerima(int konversiSatuanTerima) {
        this.konversiSatuanTerima = konversiSatuanTerima;
    }

    public boolean isStatusLock() {
        return this.statusLock;
    }

    public void setStatusLock(boolean statusLock) {
        this.statusLock = statusLock;
    }
}

