/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

public class PickingListDetail {
    long id;
    int idPickingListHeader;
    String idSKU;
    String namaBarang;
    String jenis;
    int quantityRequested;
    int quantityArea1;
    int quantityArea2;
    int sisaStok;
    int stokArea1;
    int stokArea2;
    int pkm;
    int pkn;
    int quantityAccepted1;
    int quantityAccepted2;
    String satuanCabang1;
    String satuanCabang2;
    int konversiCabang1;
    int konversiCabang2;
    long hpp;

    public String getJenis() {
        return this.jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public long getHpp() {
        return this.hpp;
    }

    public void setHpp(long hpp) {
        this.hpp = hpp;
    }

    public String getNamaBarang() {
        return this.namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getSatuanCabang1() {
        return this.satuanCabang1;
    }

    public void setSatuanCabang1(String satuanCabang1) {
        this.satuanCabang1 = satuanCabang1;
    }

    public String getSatuanCabang2() {
        return this.satuanCabang2;
    }

    public void setSatuanCabang2(String satuanCabang2) {
        this.satuanCabang2 = satuanCabang2;
    }

    public int getKonversiCabang1() {
        return this.konversiCabang1;
    }

    public void setKonversiCabang1(int konversiCabang1) {
        this.konversiCabang1 = konversiCabang1;
    }

    public int getKonversiCabang2() {
        return this.konversiCabang2;
    }

    public void setKonversiCabang2(int konversiCabang2) {
        this.konversiCabang2 = konversiCabang2;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIdPickingListHeader() {
        return this.idPickingListHeader;
    }

    public void setIdPickingListHeader(int idPickingListHeader) {
        this.idPickingListHeader = idPickingListHeader;
    }

    public String getIdSKU() {
        return this.idSKU;
    }

    public void setIdSKU(String idSKU) {
        this.idSKU = idSKU;
    }

    public int getQuantityRequested() {
        return this.quantityRequested;
    }

    public void setQuantityRequested(int quantityRequested) {
        this.quantityRequested = quantityRequested;
    }

    public int getQuantityArea1() {
        return this.quantityArea1;
    }

    public void setQuantityArea1(int quantityArea1) {
        this.quantityArea1 = quantityArea1;
    }

    public int getQuantityArea2() {
        return this.quantityArea2;
    }

    public void setQuantityArea2(int quantityArea2) {
        this.quantityArea2 = quantityArea2;
    }

    public int getSisaStok() {
        return this.sisaStok;
    }

    public void setSisaStok(int sisaStok) {
        this.sisaStok = sisaStok;
    }

    public int getStokArea1() {
        return this.stokArea1;
    }

    public void setStokArea1(int stokArea1) {
        this.stokArea1 = stokArea1;
    }

    public int getStokArea2() {
        return this.stokArea2;
    }

    public void setStokArea2(int stokArea2) {
        this.stokArea2 = stokArea2;
    }

    public int getPkm() {
        return this.pkm;
    }

    public void setPkm(int pkm) {
        this.pkm = pkm;
    }

    public int getPkn() {
        return this.pkn;
    }

    public void setPkn(int pkn) {
        this.pkn = pkn;
    }

    public int getQuantityAccepted1() {
        return this.quantityAccepted1;
    }

    public void setQuantityAccepted1(int quantityAccepted1) {
        this.quantityAccepted1 = quantityAccepted1;
    }

    public int getQuantityAccepted2() {
        return this.quantityAccepted2;
    }

    public void setQuantityAccepted2(int quantityAccepted2) {
        this.quantityAccepted2 = quantityAccepted2;
    }
}

