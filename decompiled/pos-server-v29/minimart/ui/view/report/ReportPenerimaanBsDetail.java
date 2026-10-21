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
import java.text.DateFormat;
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
import minimart.engine.controller.Helper;
import minimart.engine.model.Barang;
import minimart.engine.model.PenerimaanBs;
import minimart.engine.model.PenerimaanBsDetail;
import minimart.engine.model.StockCommit;
import minimart.engine.service.BarangService;
import minimart.engine.service.PenerimaanBsDetailService;
import minimart.engine.service.PenerimaanBsService;
import minimart.engine.service.SinkronisasiBarangService;
import minimart.ui.listener.ConfirmationDialogListener;
import minimart.ui.view.ConfirmationDialog;

public class ReportPenerimaanBsDetail
extends JDialog {
    private JFormattedTextField dateField;
    private JTextField idField;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JScrollPane jScrollPane1;
    private JButton keluarBtn;
    private JButton prosesBtn;
    private JTable reportTable;
    private JLabel suppNameField;
    private String[] reportColumn = new String[]{"Kode Barang", "Nama Barang", "Qty", "Sat"};
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn);
    private PenerimaanBsDetailService penerimaanBsDetailService = new PenerimaanBsDetailService();
    private PenerimaanBsService penerimaanBsService = new PenerimaanBsService();
    private BarangService barangService = new BarangService();
    private Helper helper = Helper.getInstance();
    private String idPenerimaanBs;
    private PenerimaanBs penerimaanBs;
    private int screenWidth = 0;
    private int screenHeight = 0;
    private boolean canGoThrough;
    private SinkronisasiBarangService sinkronisasiBarangService = new SinkronisasiBarangService();

    public ReportPenerimaanBsDetail(Frame parent, boolean modal, String idPenerimaan) {
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
        this.idPenerimaanBs = idPenerimaan;
        this.penerimaanBs = this.penerimaanBsService.getPenerimaanBs(this.idPenerimaanBs);
        if (this.penerimaanBs.getStatus().equals("SUDAH DIPROSES")) {
            this.prosesBtn.setEnabled(false);
        } else if (!this.helper.getKaryawan().getRole().equals("MANAGER") && !this.helper.getKaryawan().getRole().equals("DIREKTUR")) {
            this.prosesBtn.setEnabled(false);
        }
        this.loadData(this.idPenerimaanBs);
    }

    private void loadData(String idPenerimaanBs) {
        List<PenerimaanBsDetail> penerimaanBsDetails = this.penerimaanBsDetailService.getPenerimaanBsDetailsByPenerimaanBsId(idPenerimaanBs);
        this.dateField.setValue(this.penerimaanBs.getTanggal());
        this.idField.setText(idPenerimaanBs);
        for (PenerimaanBsDetail penerimaanBsDetail : penerimaanBsDetails) {
            Barang dataBarang = this.barangService.getBarang(penerimaanBsDetail.getBarangId());
            Object[] rowValue = new Object[8];
            rowValue[0] = dataBarang.getId();
            rowValue[1] = dataBarang.getNama();
            rowValue[2] = Float.valueOf(penerimaanBsDetail.getJumlahBarang());
            rowValue[3] = penerimaanBsDetail.getSatuan();
            this.reportTableModel.addRow(rowValue);
        }
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.dateField = new JFormattedTextField();
        this.idField = new JTextField();
        this.suppNameField = new JLabel();
        this.jScrollPane1 = new JScrollPane();
        this.reportTable = new JTable();
        this.keluarBtn = new JButton();
        this.prosesBtn = new JButton();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("Tanggal");
        this.jLabel2.setText("ID Pembelian");
        this.dateField.setEditable(false);
        this.dateField.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(DateFormat.getDateInstance(0))));
        this.dateField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPenerimaanBsDetail.this.dateFieldKeyPressed(evt);
            }
        });
        this.idField.setEditable(false);
        this.idField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPenerimaanBsDetail.this.idFieldKeyPressed(evt);
            }
        });
        this.suppNameField.setText("-");
        this.reportTable.setModel(this.reportTableModel);
        this.reportTable.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPenerimaanBsDetail.this.reportTableKeyPressed(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.reportTable);
        this.keluarBtn.setText("Keluar");
        this.keluarBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPenerimaanBsDetail.this.keluarBtnActionPerformed(evt);
            }
        });
        this.prosesBtn.setText("Proses");
        this.prosesBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPenerimaanBsDetail.this.prosesBtnActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, -1, 780, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jLabel2)).addGap(22, 22, 22).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.dateField).addComponent(this.idField, -1, 130, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.suppNameField, -2, 304, -2)).addGroup(layout.createSequentialGroup().addComponent(this.keluarBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.prosesBtn))).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.dateField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.idField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.suppNameField).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.keluarBtn).addComponent(this.prosesBtn)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, 421, -2).addGap(60, 60, 60)));
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
        if (this.canGoThrough) {
            int updateBarangStatus = 0;
            for (int a = 0; a < this.reportTableModel.getRowCount(); ++a) {
                StockCommit stockCommit1;
                Barang barang = this.barangService.getBarang(this.reportTableModel.getValueAt(a, 0).toString());
                float addStok = 0.0f;
                if (this.reportTableModel.getValueAt(a, 3).equals(barang.getSatuan1())) {
                    addStok = Float.parseFloat(this.reportTableModel.getValueAt(a, 2).toString());
                } else if (this.reportTableModel.getValueAt(a, 3).equals(barang.getSatuan2())) {
                    addStok = Float.parseFloat(this.reportTableModel.getValueAt(a, 2).toString()) * (float)barang.getKonversi21();
                } else if (this.reportTableModel.getValueAt(a, 3).equals(barang.getSatuan3())) {
                    addStok = Float.parseFloat(this.reportTableModel.getValueAt(a, 2).toString()) * (float)barang.getKonversi21() * (float)barang.getKonversi32();
                }
                if (this.penerimaanBs.getLokasi().equalsIgnoreCase("TOKO")) {
                    updateBarangStatus = this.barangService.updateStokBarangToko(-addStok, barang.getId());
                    stockCommit1 = new StockCommit();
                    stockCommit1.setIdBarang(barang.getId());
                    stockCommit1.setIdTransaksi(this.penerimaanBs.getId());
                    stockCommit1.setQueryType("kurang");
                    stockCommit1.setStockType("toko");
                    stockCommit1.setTransactionType("Penerimaan BS");
                    stockCommit1.setJumlahBarang(addStok);
                    this.sinkronisasiBarangService.insertCommit(stockCommit1);
                } else if (this.penerimaanBs.getLokasi().equalsIgnoreCase("GUDANG")) {
                    updateBarangStatus = this.barangService.updateStokBarangGudang(-addStok, barang.getId());
                    stockCommit1 = new StockCommit();
                    stockCommit1.setIdBarang(barang.getId());
                    stockCommit1.setIdTransaksi(this.penerimaanBs.getId());
                    stockCommit1.setQueryType("kurang");
                    stockCommit1.setStockType("gudang");
                    stockCommit1.setTransactionType("Penerimaan BS");
                    stockCommit1.setJumlahBarang(addStok);
                    this.sinkronisasiBarangService.insertCommit(stockCommit1);
                }
                updateBarangStatus = this.barangService.updateStokBs(addStok, barang.getId());
                StockCommit stockCommit2 = new StockCommit();
                stockCommit2.setIdBarang(barang.getId());
                stockCommit2.setIdTransaksi(this.penerimaanBs.getId());
                stockCommit2.setQueryType("tambah");
                stockCommit2.setStockType("bs");
                stockCommit2.setTransactionType("Penerimaan BS");
                stockCommit2.setJumlahBarang(addStok);
                this.sinkronisasiBarangService.insertCommit(stockCommit2);
            }
            if (updateBarangStatus == 1) {
                this.penerimaanBs.setStatus("SUDAH DIPROSES");
                this.penerimaanBs.setUpdated(new Date());
                int result = this.penerimaanBsService.updatePenerimaanBs(this.penerimaanBs);
                if (result == 1) {
                    JOptionPane.showMessageDialog(null, "Proses Penambahan Stok BS Berhasil");
                    this.prosesBtn.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Proses Penambahan Stok BS Gagal");
                }
            }
        }
    }

    private class confirmDialogEventHandler
    implements ConfirmationDialogListener {
        private confirmDialogEventHandler() {
        }

        @Override
        public void yesClick() {
            ReportPenerimaanBsDetail.this.canGoThrough = true;
        }

        @Override
        public void noClick() {
            ReportPenerimaanBsDetail.this.canGoThrough = false;
        }
    }
}

