/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view.report;

import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import minimart.engine.controller.Helper;
import minimart.engine.model.Barang;
import minimart.engine.model.Pembelian;
import minimart.engine.model.PembelianDetail;
import minimart.engine.model.StockCommit;
import minimart.engine.model.Supplier;
import minimart.engine.service.BarangService;
import minimart.engine.service.PembelianDetailService;
import minimart.engine.service.PembelianService;
import minimart.engine.service.SinkronisasiBarangService;
import minimart.engine.service.SupplierService;
import minimart.engine.service.SyncService;
import minimart.ui.listener.ConfirmationDialogListener;
import minimart.ui.listener.ReportPembelianDetailListener;
import minimart.ui.view.ConfirmationDialog;
import minimart.ui.view.report.KenaikanHargaDialog;

public class ReportPembelianDetail
extends JDialog {
    private JFormattedTextField dateField;
    private JTextField discField;
    private JTextField idField;
    private JTextField idSuppField;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JScrollPane jScrollPane1;
    private JButton keluarBtn;
    private JButton kenaikanBtn;
    private JButton marginBtn;
    private JFormattedTextField netTotalField;
    private JTextField ppnField;
    private JButton prosesBtn;
    private JTable reportTable;
    private JLabel suppNameField;
    private String[] reportColumn = new String[]{"Kode Barang", "Nama Barang", "Qty", "Sat", "Harga", "Total", "Disc", "Harga Akhir"};
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private PembelianDetailService pembelianDetailService = new PembelianDetailService();
    private SupplierService supplierService = new SupplierService();
    private PembelianService pembelianService = new PembelianService();
    private BarangService barangService = new BarangService();
    private Helper helper = Helper.getInstance();
    private String idPembelian;
    private Pembelian pembelian;
    private int screenWidth = 0;
    private int screenHeight = 0;
    private boolean canGoThrough;
    private ReportPembelianDetailListener reportPembelianDetailPanelListener;
    private List<PembelianDetail> pembelianDetails;
    private SyncService syncService = new SyncService();
    private SinkronisasiBarangService sinkronisasiBarangService = new SinkronisasiBarangService();

    public ReportPembelianDetail(Frame parent, boolean modal, String idPembelian) {
        super(parent, modal);
        this.initComponents();
        TableColumn col1 = this.reportTable.getColumnModel().getColumn(1);
        TableColumn col2 = this.reportTable.getColumnModel().getColumn(2);
        TableColumn col3 = this.reportTable.getColumnModel().getColumn(3);
        int width = 20;
        col1.setPreferredWidth(130);
        col2.setPreferredWidth(width);
        col3.setPreferredWidth(width);
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.idPembelian = idPembelian;
        this.pembelian = this.pembelianService.getPembelian(idPembelian);
        if (this.pembelian.getKeterangan().equals("SUDAH DIPROSES")) {
            this.prosesBtn.setEnabled(false);
        } else if (this.helper.getKaryawan().getRole().equals("DIREKTUR") || this.helper.getKaryawan().getRole().equals("MANAGER") || this.helper.getKaryawan().getRole().equals("ADMIN")) {
            this.prosesBtn.setEnabled(true);
        }
        this.loadData(idPembelian);
    }

    private void loadData(String idPembelian) {
        Pembelian pembelian = this.pembelianService.getPembelian(idPembelian);
        this.pembelianDetails = this.pembelianDetailService.getPembelianDetails(idPembelian);
        Supplier supplierData = this.supplierService.getSupplier(pembelian.getSupplierId());
        this.dateField.setValue(pembelian.getTanggal());
        this.idSuppField.setText(supplierData.getId());
        this.suppNameField.setText(supplierData.getNama());
        this.netTotalField.setValue(pembelian.getTotal());
        this.discField.setText(pembelian.getDiscount1() + "");
        this.ppnField.setText(pembelian.getPpn() + "");
        this.idField.setText(idPembelian);
        for (PembelianDetail pembelianDetail : this.pembelianDetails) {
            Barang dataBarang = this.barangService.getBarang(pembelianDetail.getBarangId());
            Object[] rowValue = new Object[]{dataBarang.getId(), dataBarang.getNama(), Float.valueOf(pembelianDetail.getJumlahBarang()), pembelianDetail.getSatuan(), pembelianDetail.getHargaBeli(), pembelianDetail.getTotalHarga() + pembelianDetail.getDiscount2(), pembelianDetail.getDiscount2(), pembelianDetail.getTotalHarga()};
            this.reportTableModel.addRow(rowValue);
        }
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.dateField = new JFormattedTextField();
        this.idField = new JTextField();
        this.idSuppField = new JTextField();
        this.suppNameField = new JLabel();
        this.jLabel4 = new JLabel();
        this.jScrollPane1 = new JScrollPane();
        this.reportTable = new JTable();
        this.netTotalField = new JFormattedTextField();
        this.jLabel3 = new JLabel();
        this.jLabel5 = new JLabel();
        this.jLabel6 = new JLabel();
        this.ppnField = new JTextField();
        this.discField = new JTextField();
        this.keluarBtn = new JButton();
        this.prosesBtn = new JButton();
        this.kenaikanBtn = new JButton();
        this.marginBtn = new JButton();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("Tanggal");
        this.jLabel2.setText("ID Pembelian");
        this.dateField.setEditable(false);
        this.dateField.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(DateFormat.getDateInstance(0))));
        this.dateField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPembelianDetail.this.dateFieldKeyPressed(evt);
            }
        });
        this.idField.setEditable(false);
        this.idField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPembelianDetail.this.idFieldKeyPressed(evt);
            }
        });
        this.idSuppField.setEditable(false);
        this.idSuppField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPembelianDetail.this.idSuppFieldKeyPressed(evt);
            }
        });
        this.suppNameField.setText("-");
        this.jLabel4.setText("Supplier");
        this.reportTable.setModel(this.reportTableModel);
        this.reportTable.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPembelianDetail.this.reportTableKeyPressed(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.reportTable);
        this.netTotalField.setEditable(false);
        this.netTotalField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("\u00a4#,##0.--"))));
        this.netTotalField.setText("0");
        this.netTotalField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPembelianDetail.this.netTotalFieldKeyPressed(evt);
            }
        });
        this.jLabel3.setText("Net Total");
        this.jLabel5.setText("PPN");
        this.jLabel6.setText("Disc");
        this.ppnField.setEditable(false);
        this.ppnField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPembelianDetail.this.ppnFieldKeyPressed(evt);
            }
        });
        this.discField.setEditable(false);
        this.discField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPembelianDetail.this.discFieldKeyPressed(evt);
            }
        });
        this.keluarBtn.setText("Keluar");
        this.keluarBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPembelianDetail.this.keluarBtnActionPerformed(evt);
            }
        });
        this.prosesBtn.setText("Proses");
        this.prosesBtn.setEnabled(false);
        this.prosesBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPembelianDetail.this.prosesBtnActionPerformed(evt);
            }
        });
        this.kenaikanBtn.setText("Kenaikan Harga");
        this.kenaikanBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPembelianDetail.this.kenaikanBtnActionPerformed(evt);
            }
        });
        this.marginBtn.setText("Analisa Margin");
        this.marginBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPembelianDetail.this.marginBtnActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jLabel2).addComponent(this.jLabel4)).addGap(22, 22, 22).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.idSuppField, -1, 130, Short.MAX_VALUE).addComponent(this.dateField).addComponent(this.idField, -1, 130, Short.MAX_VALUE)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(398, 398, 398).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel5, -1, 23, Short.MAX_VALUE).addComponent(this.jLabel6, -1, -1, Short.MAX_VALUE))).addGroup(layout.createSequentialGroup().addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.suppNameField, -2, 304, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.jLabel3))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.netTotalField, -2, 141, -2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.discField, GroupLayout.Alignment.LEADING, -1, 57, Short.MAX_VALUE).addComponent(this.ppnField, GroupLayout.Alignment.LEADING)))).addComponent(this.jScrollPane1).addGroup(layout.createSequentialGroup().addComponent(this.keluarBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.prosesBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.kenaikanBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.marginBtn).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.dateField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.idField, -2, -1, -2))).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel6).addComponent(this.discField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel5).addComponent(this.ppnField, -2, -1, -2)))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.idSuppField, -2, -1, -2).addComponent(this.suppNameField).addComponent(this.jLabel4).addComponent(this.netTotalField, -2, -1, -2).addComponent(this.jLabel3)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.keluarBtn).addComponent(this.prosesBtn).addComponent(this.kenaikanBtn).addComponent(this.marginBtn)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, 421, -2).addGap(60, 60, 60)));
        this.pack();
    }

    private void dateFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void idFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void idSuppFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void discFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void ppnFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void netTotalFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void reportTableKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void keluarBtnActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void prosesBtnActionPerformed(ActionEvent evt) {
        this.canGoThrough = false;
        ConfirmationDialog confirmationDialog = new ConfirmationDialog(null, true, "Data Akan Diproses dan Tidak Dapat Diubah Lagi! Lanjutkan?");
        confirmationDialog.addListener(new confirmDialogEventHandler());
        int w = confirmationDialog.getWidth();
        int h = confirmationDialog.getHeight();
        confirmationDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
        confirmationDialog.setVisible(true);
        Pembelian pembelianCheck = this.pembelianService.getPembelian(this.idPembelian);
        String keteranganPembelian = pembelianCheck.getKeterangan();
        if (keteranganPembelian.equals("SUDAH DIPROSES")) {
            JOptionPane.showMessageDialog(null, "PEMBELIAN SUDAH DIPROSES");
        }
        if (this.canGoThrough && !keteranganPembelian.equals("SUDAH DIPROSES")) {
            int updateBarangStatus = 0;
            this.pembelianDetails = this.pembelianDetailService.getPembelianDetails(this.idPembelian);
            for (PembelianDetail pembelianDetail : this.pembelianDetails) {
                StockCommit stockCommit1;
                Barang barang = this.barangService.getBarang(pembelianDetail.getBarangId());
                float addStok = 0.0f;
                if (pembelianDetail.getSatuan().equals(barang.getSatuan1())) {
                    addStok = pembelianDetail.getJumlahBarang();
                } else if (pembelianDetail.getSatuan().equals(barang.getSatuan2())) {
                    addStok = pembelianDetail.getJumlahBarang() * (float)barang.getKonversi21();
                } else if (pembelianDetail.getSatuan().equals(barang.getSatuan3())) {
                    addStok = pembelianDetail.getJumlahBarang() * (float)barang.getKonversi21() * (float)barang.getKonversi32();
                }
                System.out.println(addStok);
                if (this.helper.getJenisProgram().equals("GUDANGTOKO")) {
                    if (this.pembelian.getStatus().equals("GUDANG")) {
                        updateBarangStatus = this.barangService.updateStokBarangGudang(addStok, barang.getId());
                        stockCommit1 = new StockCommit();
                        stockCommit1.setIdBarang(barang.getId());
                        stockCommit1.setIdTransaksi(this.pembelian.getId());
                        stockCommit1.setQueryType("tambah");
                        stockCommit1.setStockType("gudang");
                        stockCommit1.setTransactionType("Pembelian");
                        stockCommit1.setJumlahBarang(addStok);
                        this.sinkronisasiBarangService.insertCommit(stockCommit1);
                        continue;
                    }
                    if (!this.pembelian.getStatus().equals("TOKO")) continue;
                    updateBarangStatus = this.barangService.updateStokBarangToko(addStok, barang.getId());
                    stockCommit1 = new StockCommit();
                    stockCommit1.setIdBarang(barang.getId());
                    stockCommit1.setIdTransaksi(this.pembelian.getId());
                    stockCommit1.setQueryType("tambah");
                    stockCommit1.setStockType("toko");
                    stockCommit1.setTransactionType("Pembelian");
                    stockCommit1.setJumlahBarang(addStok);
                    this.sinkronisasiBarangService.insertCommit(stockCommit1);
                    continue;
                }
                if (!this.helper.getJenisProgram().equals("GUDANGSAJA")) continue;
                updateBarangStatus = this.barangService.updateStokBarangGudang(addStok, barang.getId());
                stockCommit1 = new StockCommit();
                stockCommit1.setIdBarang(barang.getId());
                stockCommit1.setIdTransaksi(this.pembelian.getId());
                stockCommit1.setQueryType("tambah");
                stockCommit1.setStockType("gudang");
                stockCommit1.setTransactionType("Pembelian");
                stockCommit1.setJumlahBarang(addStok);
                this.sinkronisasiBarangService.insertCommit(stockCommit1);
            }
            if (updateBarangStatus == 1) {
                this.pembelian.setKeterangan("SUDAH DIPROSES");
                this.pembelian.setUpdated(new Date());
                int result = this.pembelianService.updatePembelian(this.pembelian);
                if (result == 1) {
                    JOptionPane.showMessageDialog(null, "Proses Penambahan Stok Gudang Berhasil");
                    this.prosesBtn.setEnabled(false);
                    this.reportPembelianDetailPanelListener.prosesClick();
                    this.syncService.doSyncPembelianAndPembelianDetailUnsynced();
                } else {
                    JOptionPane.showMessageDialog(null, "Proses Penambahan Stok Gudang Gagal");
                }
            }
        }
    }

    private void kenaikanBtnActionPerformed(ActionEvent evt) {
        Object[] reportColumn = new String[]{"Nama Barang", "Harga Jual1 / HargaBeli", "Harga Jual2 / Harga Beli", "Harga Jual3 / Harga Beli"};
        DefaultTableModel tableModel = new DefaultTableModel(new String[0][1], reportColumn);
        for (int a = 0; a < this.reportTableModel.getRowCount(); ++a) {
            Barang barang = this.barangService.getBarang(this.reportTableModel.getValueAt(a, 0).toString());
            long barangSatTerkecil = this.helper.getHargaBeliTerakhirSatuanTerkecil(barang.getId());
            if (!(!barang.getSatuan1().equals("") && barang.getHargaJual1() <= barangSatTerkecil || !barang.getSatuan2().equals("") && barang.getHargaJual2() <= barangSatTerkecil * (long)barang.getKonversi21()) && (barang.getSatuan3().equals("") || barang.getHargaJual3() > barangSatTerkecil * (long)barang.getKonversi21() * (long)barang.getKonversi32())) continue;
            Object[] rowValue = new Object[5];
            rowValue[0] = barang.getNama();
            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.CEILING);
            float tempMargin1 = (float)(barang.getHargaJual1() - barangSatTerkecil) / (float)barangSatTerkecil * 100.0f;
            rowValue[1] = barang.getHargaJual1() + " / " + barangSatTerkecil + " [" + df.format(tempMargin1) + "%]";
            float tempMargin2 = (float)(barang.getHargaJual2() - barangSatTerkecil * (long)barang.getKonversi21()) / (float)(barangSatTerkecil * (long)barang.getKonversi21()) * 100.0f;
            rowValue[2] = barang.getHargaJual2() + " / " + barangSatTerkecil * (long)barang.getKonversi21() + " [" + df.format(tempMargin2) + "%]";
            float tempMargin3 = (float)(barang.getHargaJual3() - barangSatTerkecil * (long)barang.getKonversi21() * (long)barang.getKonversi32()) / (float)barangSatTerkecil * (float)barang.getKonversi21() * (float)barang.getKonversi32() * 100.0f;
            rowValue[3] = barang.getHargaJual3() + " / " + barangSatTerkecil * (long)barang.getKonversi21() * (long)barang.getKonversi32() + " [" + df.format(tempMargin3) + "%]";
            tableModel.addRow(rowValue);
        }
        KenaikanHargaDialog kenaikanHargaDialog = new KenaikanHargaDialog(null, true, tableModel);
        int w = kenaikanHargaDialog.getWidth();
        int h = kenaikanHargaDialog.getHeight();
        kenaikanHargaDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
        kenaikanHargaDialog.setVisible(true);
    }

    private void marginBtnActionPerformed(ActionEvent evt) {
        Object[] reportColumn = new String[]{"Nama Barang", "Harga Jual1 / HargaBeli", "Harga Jual2 / Harga Beli", "Harga Jual3 / Harga Beli"};
        DefaultTableModel tableModel = new DefaultTableModel(new String[0][1], reportColumn);
        for (int a = 0; a < this.reportTableModel.getRowCount(); ++a) {
            Barang barang = this.barangService.getBarang(this.reportTableModel.getValueAt(a, 0).toString());
            long barangSatTerkecil = this.helper.getHargaBeliTerakhirSatuanTerkecil(barang.getId());
            Object[] rowValue = new Object[5];
            rowValue[0] = barang.getNama();
            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.CEILING);
            float tempMargin1 = (float)(barang.getHargaJual1() - barangSatTerkecil) / (float)barangSatTerkecil * 100.0f;
            rowValue[1] = barang.getHargaJual1() + " / " + barangSatTerkecil + " [" + df.format(tempMargin1) + "%]";
            float tempMargin2 = (float)(barang.getHargaJual2() - barangSatTerkecil * (long)barang.getKonversi21()) / (float)(barangSatTerkecil * (long)barang.getKonversi21()) * 100.0f;
            rowValue[2] = barang.getHargaJual2() + " / " + barangSatTerkecil * (long)barang.getKonversi21() + " [" + df.format(tempMargin2) + "%]";
            float tempMargin3 = (float)(barang.getHargaJual3() - barangSatTerkecil * (long)barang.getKonversi21() * (long)barang.getKonversi32()) / (float)(barangSatTerkecil * (long)barang.getKonversi21() * (long)barang.getKonversi32()) * 100.0f;
            rowValue[3] = barang.getHargaJual3() + " / " + barangSatTerkecil * (long)barang.getKonversi21() * (long)barang.getKonversi32() + " [" + df.format(tempMargin3) + "%]";
            tableModel.addRow(rowValue);
        }
        KenaikanHargaDialog kenaikanHargaDialog = new KenaikanHargaDialog(null, true, tableModel);
        int w = kenaikanHargaDialog.getWidth();
        int h = kenaikanHargaDialog.getHeight();
        kenaikanHargaDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
        kenaikanHargaDialog.setVisible(true);
    }

    public void setListener(ReportPembelianDetailListener listener) {
        this.reportPembelianDetailPanelListener = listener;
    }

    private class confirmDialogEventHandler
    implements ConfirmationDialogListener {
        private confirmDialogEventHandler() {
        }

        @Override
        public void yesClick() {
            ReportPembelianDetail.this.canGoThrough = true;
        }

        @Override
        public void noClick() {
            ReportPembelianDetail.this.canGoThrough = false;
        }
    }
}

