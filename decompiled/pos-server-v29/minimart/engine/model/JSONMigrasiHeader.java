/*
 * Decompiled with CFR 0.152.
 */
package minimart.engine.model;

import java.util.List;
import minimart.engine.model.JSONItemMigrasi;

public class JSONMigrasiHeader {
    private int id_cabang_pengirim;
    private int id_cabang_penerima;
    private String status_1;
    private String id_penjualan_offline;
    private long total_penjualan;
    private String id_picking_list;
    private List<JSONItemMigrasi> jsonItemMigrasis;

    public String getId_picking_list() {
        return this.id_picking_list;
    }

    public void setId_picking_list(String id_picking_list) {
        this.id_picking_list = id_picking_list;
    }

    public List<JSONItemMigrasi> getJsonItemMigrasis() {
        return this.jsonItemMigrasis;
    }

    public void setJsonItemMigrasis(List<JSONItemMigrasi> jsonItemMigrasis) {
        this.jsonItemMigrasis = jsonItemMigrasis;
    }

    public String getId_penjualan_offline() {
        return this.id_penjualan_offline;
    }

    public void setId_penjualan_offline(String id_penjualan_offline) {
        this.id_penjualan_offline = id_penjualan_offline;
    }

    public long getTotal_penjualan() {
        return this.total_penjualan;
    }

    public void setTotal_penjualan(long total_penjualan) {
        this.total_penjualan = total_penjualan;
    }

    public JSONMigrasiHeader(int id_cabang_pengirim, int id_cabang_penerima, String status_1, String id_penjualan_offline, long total_penjualan) {
        this.id_cabang_pengirim = id_cabang_pengirim;
        this.id_cabang_penerima = id_cabang_penerima;
        this.status_1 = status_1;
        this.id_penjualan_offline = id_penjualan_offline;
        this.total_penjualan = total_penjualan;
    }

    public JSONMigrasiHeader(int id_cabang_pengirim, int id_cabang_penerima, String status_1) {
        this.id_cabang_pengirim = id_cabang_pengirim;
        this.id_cabang_penerima = id_cabang_penerima;
        this.status_1 = status_1;
    }

    public JSONMigrasiHeader() {
    }

    public int getId_cabang_pengirim() {
        return this.id_cabang_pengirim;
    }

    public void setId_cabang_pengirim(int id_cabang_pengirim) {
        this.id_cabang_pengirim = id_cabang_pengirim;
    }

    public int getId_cabang_penerima() {
        return this.id_cabang_penerima;
    }

    public void setId_cabang_penerima(int id_cabang_penerima) {
        this.id_cabang_penerima = id_cabang_penerima;
    }

    public String getStatus_1() {
        return this.status_1;
    }

    public void setStatus_1(String status_1) {
        this.status_1 = status_1;
    }
}

