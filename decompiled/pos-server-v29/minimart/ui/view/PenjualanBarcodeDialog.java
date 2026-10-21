/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import minimart.engine.controller.Helper;
import minimart.engine.model.Barang;
import minimart.engine.model.Barcode;
import minimart.engine.model.PenjualanSementara;
import minimart.engine.model.PromoHematDetail;
import minimart.engine.model.PromoTebusDetail;
import minimart.engine.service.BarangService;
import minimart.engine.service.BarcodeService;
import minimart.engine.service.PenjualanSementaraService;
import minimart.engine.service.PromoHematDetailService;
import minimart.engine.service.PromoTebusDetailService;
import minimart.ui.listener.PenjualanBarcodeListener;

public class PenjualanBarcodeDialog
extends JDialog {
    private JTextField barcodeField;
    private JFormattedTextField discountField;
    private JFormattedTextField hargaField;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jumlahBarangLbl;
    private JLabel namaBarangLbl;
    private JLabel promoLbl;
    private JLabel promoLbl2;
    private JLabel rakLbl;
    private JLabel satuanLbl;
    private Barang barang;
    private BarangService barangService = new BarangService();
    private PenjualanBarcodeListener listener;
    private DefaultTableModel penjualanTableModel;
    private Helper helper = Helper.getInstance();
    private BarcodeService barcodeService = new BarcodeService();
    private PromoHematDetailService promoHematDetailService = new PromoHematDetailService();
    private PromoTebusDetailService promoTebusDetailService = new PromoTebusDetailService();
    private PenjualanSementaraService penjualanSementaraService = new PenjualanSementaraService();
    private String customerId;
    int jumlahBarang;
    String satuan;
    float harga;
    int totalHarga;
    float disc;
    float hargaAkhir;
    private String startDateString;
    private String endDateString;
    String shortcut;

    public PenjualanBarcodeDialog(Frame parent, boolean modal, DefaultTableModel penjualanTableModel, String shortcut, String customerId) {
        super(parent, modal);
        this.initComponents();
        this.setTitle("Input Barcode");
        this.penjualanTableModel = penjualanTableModel;
        this.customerId = customerId;
        this.barcodeField.setText("");
        this.shortcut = shortcut;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.startDateString = simpleDateFormat.format(new Date());
        this.endDateString = simpleDateFormat.format(new Date());
    }

    private void setDefault() {
        this.namaBarangLbl.setText("");
        this.jumlahBarangLbl.setText("");
        this.satuanLbl.setText("");
        this.jumlahBarang = 0;
        this.disc = 0.0f;
        this.harga = 0.0f;
        this.totalHarga = 0;
        this.hargaAkhir = 0.0f;
        this.satuan = "";
    }

    public void addListener(PenjualanBarcodeListener listener) {
        this.listener = listener;
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.namaBarangLbl = new JLabel();
        this.jumlahBarangLbl = new JLabel();
        this.satuanLbl = new JLabel();
        this.barcodeField = new JTextField();
        this.hargaField = new JFormattedTextField();
        this.rakLbl = new JLabel();
        this.promoLbl = new JLabel();
        this.promoLbl2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.discountField = new JFormattedTextField();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setFont(new Font("Tahoma", 1, 14));
        this.jLabel1.setText("Barcode Barang");
        this.jLabel2.setFont(new Font("Tahoma", 1, 14));
        this.jLabel2.setText("Detail");
        this.namaBarangLbl.setFont(new Font("Tahoma", 1, 14));
        this.jumlahBarangLbl.setFont(new Font("Tahoma", 1, 14));
        this.satuanLbl.setFont(new Font("Tahoma", 1, 14));
        this.barcodeField.setFont(new Font("Tahoma", 1, 14));
        this.barcodeField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanBarcodeDialog.this.barcodeFieldKeyPressed(evt);
            }
        });
        this.hargaField.setBackground(new Color(255, 255, 0));
        this.hargaField.setEditable(false);
        this.hargaField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("\u00a4 #,##0.--"))));
        this.hargaField.setFont(new Font("Tahoma", 1, 18));
        this.rakLbl.setFont(new Font("Tahoma", 1, 14));
        this.rakLbl.setForeground(new Color(204, 0, 0));
        this.rakLbl.setText("-");
        this.promoLbl.setFont(new Font("Tahoma", 1, 14));
        this.promoLbl.setForeground(new Color(255, 0, 0));
        this.promoLbl.setText("-");
        this.promoLbl2.setFont(new Font("Tahoma", 1, 14));
        this.promoLbl2.setForeground(new Color(255, 0, 0));
        this.promoLbl2.setText("-");
        this.jLabel3.setFont(new Font("Tahoma", 1, 12));
        this.jLabel3.setText("Discount");
        this.discountField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));
        this.discountField.setFont(new Font("Tahoma", 1, 12));
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(25, 25, 25).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.promoLbl, -1, -1, Short.MAX_VALUE).addComponent(this.promoLbl2, -1, -1, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jLabel1, -1, -1, Short.MAX_VALUE).addComponent(this.jLabel2).addComponent(this.jLabel3, -1, -1, Short.MAX_VALUE)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.barcodeField, -2, 183, -2).addComponent(this.namaBarangLbl, -2, 220, -2).addGroup(layout.createSequentialGroup().addComponent(this.jumlahBarangLbl, -2, 59, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.satuanLbl, -2, 41, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.rakLbl, -2, 63, -2)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.discountField).addComponent(this.hargaField, -1, 183, Short.MAX_VALUE))).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.promoLbl).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.promoLbl2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.barcodeField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jLabel2, -1, -1, Short.MAX_VALUE).addComponent(this.namaBarangLbl, -1, -1, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.satuanLbl, -1, -1, Short.MAX_VALUE).addComponent(this.jumlahBarangLbl, -1, -1, Short.MAX_VALUE).addComponent(this.rakLbl, GroupLayout.Alignment.TRAILING)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.hargaField, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.discountField, -2, -1, -2)).addContainerGap()));
        this.pack();
    }

    private void barcodeFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.setDefault();
            Long currentTime = System.currentTimeMillis();
            String barcodeText = this.barcodeField.getText().replace(" ", "");
            this.barcodeField.setText(barcodeText);
            this.barang = this.barangService.getBarang(barcodeText);
            if (this.barang != null) {
                int jumlahSekarang;
                PromoHematDetail promoHematDetail = this.promoHematDetailService.getPromoHematDetailByBarangId(this.barang.getId(), this.startDateString, this.endDateString);
                PromoTebusDetail promoTebusDetail = this.promoTebusDetailService.getPromoTebusDetailByBarangId(this.barang.getId());
                String promoTxt = "";
                String promoTxt2 = "";
                if (promoHematDetail != null) {
                    promoTxt = "PROMO";
                    if (promoHematDetail.getKhususPelanggan().equals("YA")) {
                        promoTxt = promoTxt + " KHUSUS PELANGGAN";
                    }
                    promoTxt = promoTxt + " MINIMUM " + promoHematDetail.getMinimumPembelian() + promoHematDetail.getSatuan();
                    this.promoLbl.setText(promoTxt);
                } else if (promoTebusDetail != null) {
                    promoTxt = this.barangService.getBarang(promoTebusDetail.getPromoBarangId()).getNama() + " DISC " + promoTebusDetail.getDiscount() + ",";
                    promoTxt2 = "TIAP PEMBELIAN " + promoTebusDetail.getJumlah() + promoTebusDetail.getSatuan() + " " + this.barangService.getBarang(promoTebusDetail.getBarangId()).getNama();
                    this.promoLbl.setText(promoTxt);
                    this.promoLbl2.setText(promoTxt2);
                } else if (this.barang.getMinPembelian() > 1.0f) {
                    this.promoLbl.setText("Grosir minimum pembelian=" + this.barang.getMinPembelian());
                } else {
                    this.promoLbl.setText("");
                }
                Barcode barcode = this.barcodeService.getBarcode(this.barcodeField.getText());
                this.namaBarangLbl.setText(this.barang.getNama());
                this.searchPenjualan(this.barang.getId());
                if (barcode.getSatuan().equals(this.satuan) || this.satuan.equals("")) {
                    this.jumlahBarangLbl.setText(this.jumlahBarang + 1 + "");
                    this.satuanLbl.setText(barcode.getSatuan());
                } else if (this.satuan.equals(this.barang.getSatuan1())) {
                    if (barcode.getSatuan().equals(this.barang.getSatuan2())) {
                        this.jumlahBarangLbl.setText(this.jumlahBarang + this.barang.getKonversi21() + "");
                    } else if (barcode.getSatuan().equals(this.barang.getSatuan3())) {
                        this.jumlahBarangLbl.setText(this.jumlahBarang + this.barang.getKonversi21() * this.barang.getKonversi32() + "");
                    }
                    this.satuanLbl.setText(this.barang.getSatuan1());
                } else if (this.satuan.equals(this.barang.getSatuan2())) {
                    if (barcode.getSatuan().equals(this.barang.getSatuan3())) {
                        this.jumlahBarangLbl.setText(this.jumlahBarang + this.barang.getKonversi32() + "");
                        this.satuanLbl.setText(this.barang.getSatuan2());
                    } else if (barcode.getSatuan().equals(this.barang.getSatuan1())) {
                        jumlahSekarang = this.jumlahBarang * this.barang.getKonversi21();
                        this.jumlahBarangLbl.setText(jumlahSekarang + 1 + "");
                        this.satuanLbl.setText(this.barang.getSatuan1());
                    }
                } else if (this.satuan.equals(this.barang.getSatuan3())) {
                    if (barcode.getSatuan().equals(this.barang.getSatuan1())) {
                        jumlahSekarang = this.jumlahBarang * this.barang.getKonversi21() * this.barang.getKonversi32();
                        this.jumlahBarangLbl.setText(jumlahSekarang + 1 + "");
                        this.satuanLbl.setText(this.barang.getSatuan1());
                    } else if (barcode.getSatuan().equals(this.barang.getSatuan2())) {
                        jumlahSekarang = this.jumlahBarang * this.barang.getKonversi32();
                        this.jumlahBarangLbl.setText(jumlahSekarang + 1 + "");
                        this.satuanLbl.setText(this.barang.getSatuan2());
                    }
                }
                long hargaBeliTerakhir = this.helper.getHargaBeliTerakhirSatuanTerkecil(this.barang.getId());
                long lastPrice = 0L;
                if (this.satuanLbl.getText().equals(this.barang.getSatuan1())) {
                    lastPrice = hargaBeliTerakhir;
                    if (lastPrice >= this.barang.getHargaJual1()) {
                        this.hargaField.setBackground(Color.red);
                    } else {
                        this.hargaField.setBackground(Color.YELLOW);
                    }
                } else if (this.satuanLbl.getText().equals(this.barang.getSatuan2())) {
                    lastPrice = hargaBeliTerakhir * (long)this.barang.getKonversi21();
                    if (lastPrice >= this.barang.getHargaJual2()) {
                        this.hargaField.setBackground(Color.red);
                    } else {
                        this.hargaField.setBackground(Color.YELLOW);
                    }
                } else if (this.satuanLbl.getText().equals(this.barang.getSatuan3())) {
                    lastPrice = hargaBeliTerakhir * (long)this.barang.getKonversi21() * (long)this.barang.getKonversi32();
                    if (lastPrice >= this.barang.getHargaJual3()) {
                        this.hargaField.setBackground(Color.red);
                    } else {
                        this.hargaField.setBackground(Color.YELLOW);
                    }
                }
                this.addRow(currentTime);
            } else {
                this.namaBarangLbl.setText("BARANG TIDAK DITEMUKAN");
                this.namaBarangLbl.setForeground(Color.red);
                this.hargaField.setValue(0);
                this.barcodeField.selectAll();
            }
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void searchPenjualan(String idBarang) {
        for (int a = 0; a < this.penjualanTableModel.getRowCount(); ++a) {
            String barangId = this.penjualanTableModel.getValueAt(a, 0).toString();
            if (!idBarang.equals(barangId)) continue;
            this.loadPenjualan(a);
            break;
        }
    }

    protected void loadPenjualan(int row) {
        this.jumlahBarang = Integer.parseInt(this.penjualanTableModel.getValueAt(row, 2).toString());
        this.satuan = this.penjualanTableModel.getValueAt(row, 3).toString();
        this.harga = Float.parseFloat(this.penjualanTableModel.getValueAt(row, 4).toString());
        this.disc = Float.parseFloat(this.penjualanTableModel.getValueAt(row, 6).toString());
        this.hargaAkhir = Float.parseFloat(this.penjualanTableModel.getValueAt(row, 7).toString());
    }

    private long getDiscountPromo(PromoTebusDetail promoTebusDetail, float jumlahBarang) {
        long discount = promoTebusDetail.getDiscount();
        boolean find = false;
        int row = 0;
        int multiply = 0;
        for (int a = 0; a < this.penjualanTableModel.getRowCount(); ++a) {
            String barangId = this.penjualanTableModel.getValueAt(a, 0).toString();
            if (!promoTebusDetail.getBarangId().equals(barangId)) continue;
            find = true;
            row = a;
            break;
        }
        if (find) {
            String satuan = this.penjualanTableModel.getValueAt(row, 3).toString();
            if (promoTebusDetail.getSatuan().equals(satuan) && Float.parseFloat(this.penjualanTableModel.getValueAt(row, 2).toString()) >= promoTebusDetail.getJumlah() && jumlahBarang < (float)(multiply = (int)(Float.parseFloat(this.penjualanTableModel.getValueAt(row, 2).toString()) / promoTebusDetail.getJumlah()))) {
                multiply = (int)jumlahBarang;
            }
        }
        return discount * (long)multiply;
    }

    private void addRow(Long currentTime) {
        PromoHematDetail promoHematDetail;
        Object[] rowValue = new Object[8];
        rowValue[0] = this.barang.getId();
        rowValue[1] = this.barang.getNama();
        rowValue[2] = this.jumlahBarangLbl.getText();
        rowValue[3] = this.satuanLbl.getText();
        float hargaJual = 0.0f;
        if (this.satuanLbl.getText().equals(this.barang.getSatuan1())) {
            promoHematDetail = this.promoHematDetailService.getPromoHematDetailByBarangId(this.barang.getId(), this.barang.getSatuan1(), Float.parseFloat(this.jumlahBarangLbl.getText()), this.startDateString, this.endDateString);
            List<PromoTebusDetail> promoTebusDetails = this.promoTebusDetailService.getPromoTebusDetailByIdPromoBarang(this.barang.getId(), this.barang.getSatuan1());
            if (promoHematDetail != null) {
                boolean pass = true;
                if (promoHematDetail.getKhususPelanggan().equals("YA") && this.customerId.equals("C")) {
                    pass = false;
                }
                hargaJual = (float)Integer.parseInt(this.jumlahBarangLbl.getText()) >= promoHematDetail.getMinimumPembelian() && pass ? (float)promoHematDetail.getHargaPromo() : (float)this.barang.getHargaJual1();
            } else if (promoTebusDetails.size() > 0) {
                this.disc = this.getDiscountPromo(promoTebusDetails.get(0), Float.parseFloat(this.jumlahBarangLbl.getText()));
                this.discountField.setValue(Float.valueOf(this.disc));
                if (Float.parseFloat(this.jumlahBarangLbl.getText()) < this.barang.getMinPembelian()) {
                    long resultValue = (long)(this.barang.getMinPersen() / 100.0f * (float)this.barang.getHargaJual1()) + this.barang.getHargaJual1();
                    hargaJual = resultValue;
                } else {
                    hargaJual = this.barang.getHargaJual1();
                }
            } else if (Float.parseFloat(this.jumlahBarangLbl.getText()) < this.barang.getMinPembelian()) {
                long resultValue = this.helper.roundPrice100((long)(this.barang.getMinPersen() / 100.0f * (float)this.barang.getHargaJual1()) + this.barang.getHargaJual1());
                hargaJual = resultValue;
            } else {
                hargaJual = this.barang.getHargaJual1();
            }
        } else if (this.satuanLbl.getText().equals(this.barang.getSatuan2())) {
            promoHematDetail = this.promoHematDetailService.getPromoHematDetailByBarangId(this.barang.getId(), this.barang.getSatuan2(), Float.parseFloat(this.jumlahBarangLbl.getText()), this.startDateString, this.endDateString);
            if (promoHematDetail == null) {
                hargaJual = this.barang.getHargaJual2();
            } else {
                boolean pass = true;
                if (promoHematDetail.getKhususPelanggan().equals("YA") && this.customerId.equals("C")) {
                    pass = false;
                }
                hargaJual = (float)Integer.parseInt(this.jumlahBarangLbl.getText()) >= promoHematDetail.getMinimumPembelian() && pass ? (float)promoHematDetail.getHargaPromo() : (float)this.barang.getHargaJual2();
            }
        } else if (this.satuanLbl.getText().equals(this.barang.getSatuan3())) {
            promoHematDetail = this.promoHematDetailService.getPromoHematDetailByBarangId(this.barang.getId(), this.barang.getSatuan3(), Float.parseFloat(this.jumlahBarangLbl.getText()), this.startDateString, this.endDateString);
            if (promoHematDetail == null) {
                hargaJual = this.barang.getHargaJual3();
            } else {
                boolean pass = true;
                if (promoHematDetail.getKhususPelanggan().equals("YA") && this.customerId.equals("C")) {
                    pass = false;
                }
                hargaJual = (float)Integer.parseInt(this.jumlahBarangLbl.getText()) >= promoHematDetail.getMinimumPembelian() && pass ? (float)promoHematDetail.getHargaPromo() : (float)this.barang.getHargaJual3();
            }
        }
        this.hargaField.setValue(Float.valueOf(hargaJual * (float)Integer.parseInt(this.jumlahBarangLbl.getText())));
        rowValue[4] = Float.valueOf(hargaJual);
        rowValue[5] = (long)(hargaJual * Float.parseFloat(this.jumlahBarangLbl.getText()));
        rowValue[6] = (long)this.disc;
        rowValue[7] = (Long)rowValue[5] - (Long)rowValue[6];
        this.listener.saveClick(rowValue);
        if (this.barang.getParentId() != null && !this.barang.getParentId().equalsIgnoreCase("")) {
            if (this.satuanLbl.getText().equals(this.barang.getSatuan1())) {
                float jumlah = this.penjualanSementaraService.cekJumlahByParentId(this.helper.getKasir(), this.shortcut, this.satuanLbl.getText(), this.barang.getParentId());
                int result = 0;
                if (jumlah >= (float)this.barang.getKonversi21()) {
                    long tempHarga = this.barang.getHargaJual2() / (long)this.barang.getKonversi21();
                    List<PenjualanSementara> penjualanSementaras = this.penjualanSementaraService.getPenjualanSementaraByShortcut(this.shortcut, this.barang.getParentId(), this.barang.getSatuan1());
                    for (PenjualanSementara penjualanSementara : penjualanSementaras) {
                        long totalHarga = Math.round((float)tempHarga * penjualanSementara.getJumlahBarang());
                        result = this.penjualanSementaraService.updateHargaPenjualanSementara(tempHarga, totalHarga, this.helper.getKasir(), this.shortcut, this.satuanLbl.getText(), penjualanSementara.getBarangId());
                    }
                } else if (this.barang.getMinPembelian() > 0.0f && jumlah >= this.barang.getMinPembelian()) {
                    List<PenjualanSementara> penjualanSementaras = this.penjualanSementaraService.getPenjualanSementaraByShortcut(this.shortcut, this.barang.getParentId(), this.barang.getSatuan1());
                    for (PenjualanSementara penjualanSementara : penjualanSementaras) {
                        long totalHarga = Math.round((float)this.barang.getHargaJual1() * penjualanSementara.getJumlahBarang());
                        result = this.penjualanSementaraService.updateHargaPenjualanSementara(this.barang.getHargaJual1(), totalHarga, this.helper.getKasir(), this.shortcut, this.satuanLbl.getText(), penjualanSementara.getBarangId());
                    }
                }
                if (result > 0) {
                    this.listener.refreshData();
                }
            } else if (this.satuanLbl.getText().equals(this.barang.getSatuan2())) {
                float jumlah = this.penjualanSementaraService.cekJumlahByParentId(this.helper.getKasir(), this.shortcut, this.satuanLbl.getText(), this.barang.getParentId());
                int result = 0;
                if (jumlah >= (float)this.barang.getKonversi21()) {
                    System.out.println("masuk cek");
                    long harga = this.barang.getHargaJual3() / (long)this.barang.getKonversi32();
                    List<PenjualanSementara> penjualanSementaras = this.penjualanSementaraService.getPenjualanSementaraByShortcut(this.shortcut, this.barang.getParentId(), this.barang.getSatuan2());
                    for (PenjualanSementara penjualanSementara : penjualanSementaras) {
                        long totalHarga = Math.round((float)harga * penjualanSementara.getJumlahBarang());
                        result = this.penjualanSementaraService.updateHargaPenjualanSementara(harga, totalHarga, this.helper.getKasir(), this.shortcut, this.satuanLbl.getText(), penjualanSementara.getBarangId());
                    }
                }
                if (result > 0) {
                    this.listener.refreshData();
                }
            }
        }
        this.barcodeField.selectAll();
    }
}

