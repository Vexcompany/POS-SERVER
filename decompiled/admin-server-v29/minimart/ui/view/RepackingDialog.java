/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import minimart.engine.controller.Helper;
import minimart.engine.model.Barang;
import minimart.engine.model.Karyawan;
import minimart.engine.model.Paket;
import minimart.engine.model.PaketDetail;
import minimart.engine.model.Repack;
import minimart.engine.model.RepackDetail;
import minimart.engine.model.StockCommit;
import minimart.engine.service.BarangService;
import minimart.engine.service.PaketDetailService;
import minimart.engine.service.PaketService;
import minimart.engine.service.RepackDetailService;
import minimart.engine.service.RepackService;
import minimart.engine.service.StokCommitService;
import minimart.ui.listener.PasswordInputListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.PasswordInputDialog;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class RepackingDialog
extends JDialog {
    private JButton exitBtn;
    private JLabel idBarangLbl;
    private JTextField idPaketField;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JScrollPane jScrollPane1;
    private JTextField jumlahField;
    private JLabel namaBarangLbl;
    private JTable paketTable;
    private JButton saveBtn;
    private Paket paket;
    private int screenWidth;
    private int screenHeight;
    private BarangService barangService = new BarangService();
    private PaketService paketService = new PaketService();
    private PaketDetailService paketDetailService = new PaketDetailService();
    private RepackService repackService = new RepackService();
    private RepackDetailService repackDetailService = new RepackDetailService();
    private StokCommitService stokCommitService = new StokCommitService();
    private Helper helper = Helper.getInstance();
    private String[] repackTMColumn = new String[]{"ID Barang", "Nama Barang", "Perubahan Stok", "Satuan"};
    private DefaultTableModel repackTM = new DefaultTableModel(new String[0][1], this.repackTMColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public RepackingDialog(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        TableColumn col1 = this.paketTable.getColumnModel().getColumn(0);
        TableColumn col2 = this.paketTable.getColumnModel().getColumn(1);
        TableColumn col3 = this.paketTable.getColumnModel().getColumn(2);
        TableColumn col4 = this.paketTable.getColumnModel().getColumn(3);
        col1.setPreferredWidth(10);
        col2.setPreferredWidth(100);
        col3.setPreferredWidth(10);
        col4.setPreferredWidth(10);
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.jLabel4 = new JLabel();
        this.jLabel5 = new JLabel();
        this.jumlahField = new JTextField();
        this.jLabel6 = new JLabel();
        this.jLabel7 = new JLabel();
        this.jScrollPane1 = new JScrollPane();
        this.paketTable = new JTable();
        this.exitBtn = new JButton();
        this.saveBtn = new JButton();
        this.idPaketField = new JTextField();
        this.idBarangLbl = new JLabel();
        this.namaBarangLbl = new JLabel();
        this.jLabel8 = new JLabel();
        this.jLabel9 = new JLabel();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("ID Paket");
        this.jLabel2.setText("Barang Yang Dikonversi");
        this.jLabel3.setText("ID Barang");
        this.jLabel4.setText("Nama Barang");
        this.jLabel5.setText("Jumlah yang Akan Dikonversi");
        this.jumlahField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                RepackingDialog.this.jumlahFieldKeyPressed(evt);
            }
        });
        this.jLabel6.setText("Satuan");
        this.jLabel7.setText("Perubahan Konversi");
        this.paketTable.setModel(this.repackTM);
        this.jScrollPane1.setViewportView(this.paketTable);
        this.exitBtn.setText("Exit");
        this.exitBtn.setPreferredSize(new Dimension(72, 23));
        this.exitBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                RepackingDialog.this.exitBtnActionPerformed(evt);
            }
        });
        this.saveBtn.setText("Save");
        this.saveBtn.setPreferredSize(new Dimension(72, 23));
        this.saveBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                RepackingDialog.this.saveBtnActionPerformed(evt);
            }
        });
        this.saveBtn.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                RepackingDialog.this.saveBtnKeyPressed(evt);
            }
        });
        this.idPaketField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                RepackingDialog.this.idPaketFieldKeyPressed(evt);
            }
        });
        this.idBarangLbl.setText("-");
        this.namaBarangLbl.setText("-");
        this.jLabel8.setText("[tekan enter untuk melakukan konversi]");
        this.jLabel9.setText("[Masukkan minus untuk UNPACKING]");
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2).addComponent(this.jLabel7).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel5).addComponent(this.jLabel1).addComponent(this.jLabel3).addComponent(this.jLabel4)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.namaBarangLbl).addComponent(this.idBarangLbl).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addComponent(this.jumlahField, -2, 74, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel6)).addComponent(this.idPaketField)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel9).addComponent(this.jLabel8)))))).addGap(0, 99, Short.MAX_VALUE)).addComponent(this.jScrollPane1).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(this.saveBtn, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.exitBtn, -2, -1, -2))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.idPaketField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.idBarangLbl)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4).addComponent(this.namaBarangLbl)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel5).addComponent(this.jumlahField, -2, -1, -2).addComponent(this.jLabel6).addComponent(this.jLabel8)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel7).addComponent(this.jLabel9)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, 275, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.exitBtn, -2, -1, -2).addComponent(this.saveBtn, -2, -1, -2)).addContainerGap(-1, Short.MAX_VALUE)));
        this.pack();
    }

    private void exitBtnActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void idPaketFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 113) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Paket");
            sd.addSearchDialogListener(new searchPaketDialogEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
        } else if (evt.getKeyCode() == 10) {
            if (this.idPaketField.getText().equals("")) {
                SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Paket");
                sd.addSearchDialogListener(new searchPaketDialogEventHandler());
                int w = sd.getWidth();
                int h = sd.getHeight();
                sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                sd.setVisible(true);
            } else {
                this.loadPaket();
            }
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void setDefault() {
        this.idPaketField.setText("");
        this.idBarangLbl.setText("-");
        this.namaBarangLbl.setText("-");
        this.jumlahField.setText("");
    }

    private void removeAllRow() {
        int rowCount = this.repackTM.getRowCount();
        for (int a = 0; a < rowCount; ++a) {
            this.repackTM.removeRow(0);
        }
    }

    private void jumlahFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.removeAllRow();
            if (Integer.parseInt(this.jumlahField.getText()) > 0) {
                List<PaketDetail> paketDetails = this.paketDetailService.getPaketDetail(this.idPaketField.getText());
                boolean available = true;
                String namaKosong = "";
                for (PaketDetail paketDetail : paketDetails) {
                    Barang tempBarang = this.barangService.getBarang(paketDetail.getBarangId());
                    if (!(tempBarang.getStokToko() <= 0.0f)) continue;
                    available = false;
                    namaKosong = tempBarang.getNama();
                }
                if (available) {
                    this.loadDataClick();
                } else {
                    PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
                    passwordInputDialog2.addPasswordInputDialogListener(new passwordInputDialogEventHandler());
                    int w = passwordInputDialog2.getWidth();
                    int h = passwordInputDialog2.getHeight();
                    passwordInputDialog2.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                    passwordInputDialog2.setJudulText("Stok " + namaKosong + " masih kosong, lanjutkan?");
                    passwordInputDialog2.setVisible(true);
                }
            } else if (Integer.parseInt(this.jumlahField.getText()) == 0) {
                JOptionPane.showMessageDialog(null, "Masukkan angka dengan benar");
            } else if (Integer.parseInt(this.jumlahField.getText()) < 0) {
                Barang tempBarang = this.barangService.getBarang(this.paket.getBarangId());
                if (tempBarang.getStokToko() <= 0.0f) {
                    PasswordInputDialog passwordInputDialog3 = new PasswordInputDialog(null, true, "Input Password");
                    passwordInputDialog3.addPasswordInputDialogListener(new passwordInputDialogEventHandler());
                    int w = passwordInputDialog3.getWidth();
                    int h = passwordInputDialog3.getHeight();
                    passwordInputDialog3.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                    passwordInputDialog3.setJudulText("Stok " + tempBarang.getNama() + " kosong, lanjut unrepack?");
                    passwordInputDialog3.setVisible(true);
                } else {
                    this.loadDataClick();
                }
            }
        } else if (evt.getKeyCode() == 27) {
            this.idPaketField.requestFocus();
            this.idPaketField.selectAll();
        }
    }

    private void saveBtnKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
            passwordInputDialog2.addPasswordInputDialogListener(new passwordInputDialogEventHandler2());
            int w = passwordInputDialog2.getWidth();
            int h = passwordInputDialog2.getHeight();
            passwordInputDialog2.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            passwordInputDialog2.setJudulText("Input Password Untuk Melakukan Repack");
            passwordInputDialog2.setVisible(true);
        }
    }

    private void saveBtnActionPerformed(ActionEvent evt) {
        if (this.repackTM.getRowCount() > 0) {
            PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
            passwordInputDialog2.addPasswordInputDialogListener(new passwordInputDialogEventHandler2());
            int w = passwordInputDialog2.getWidth();
            int h = passwordInputDialog2.getHeight();
            passwordInputDialog2.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            passwordInputDialog2.setJudulText("Input Password Untuk Melakukan Repack");
            passwordInputDialog2.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Masukkan Data Terlebih Dahulu");
        }
    }

    private void loadPaket() {
        this.paket = this.paketService.getPaket(this.idPaketField.getText());
        this.idBarangLbl.setText(this.paket.getBarangId());
        this.namaBarangLbl.setText(this.barangService.getBarang(this.paket.getBarangId()).getNama());
        this.jumlahField.requestFocus();
        this.jumlahField.selectAll();
    }

    private void loadDataClick() {
        List<PaketDetail> paketDetails = this.paketDetailService.getPaketDetail(this.idPaketField.getText());
        Barang addBarang = this.barangService.getBarang(this.paket.getBarangId());
        Object[] addRowValue = new Object[]{this.paket.getBarangId(), addBarang.getNama(), this.jumlahField.getText(), addBarang.getSatuan1()};
        this.repackTM.addRow(addRowValue);
        for (PaketDetail paketDetail : paketDetails) {
            Barang tempBarang = this.barangService.getBarang(paketDetail.getBarangId());
            Object[] rowValue = new Object[]{paketDetail.getBarangId(), tempBarang.getNama(), Float.valueOf(paketDetail.getJumlahBarang() * -1.0f * Float.parseFloat(this.jumlahField.getText())), tempBarang.getSatuan1()};
            this.repackTM.addRow(rowValue);
        }
        this.saveBtn.requestFocus();
    }

    private class passwordInputDialogEventHandler2
    implements PasswordInputListener {
        private passwordInputDialogEventHandler2() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR") || karyawan.getRole().equalsIgnoreCase("MANAGER") || karyawan.getRole().equalsIgnoreCase("ADMIN")) {
                if (!Helper.getInstance().isInteger(RepackingDialog.this.jumlahField.getText())) {
                    RepackingDialog.this.jumlahField.setBackground(Color.red);
                } else {
                    Repack repack = new Repack();
                    repack.setId(RepackingDialog.this.helper.generateCodeGeneral(RepackingDialog.this.repackService.getLastId()));
                    repack.setKaryawanId(RepackingDialog.this.helper.getKaryawan().getId());
                    repack.setTanggal(new Date());
                    repack.setBarangId(RepackingDialog.this.idBarangLbl.getText());
                    repack.setJumlah(Integer.parseInt(RepackingDialog.this.jumlahField.getText()));
                    repack.setCreated(new Date());
                    int saveRepack = RepackingDialog.this.repackService.createRepack(repack);
                    if (saveRepack == 1) {
                        ArrayList<RepackDetail> batchRepackDetail = new ArrayList<RepackDetail>();
                        ArrayList<StockCommit> stockCommits = new ArrayList<StockCommit>();
                        for (int a = 0; a < RepackingDialog.this.repackTM.getRowCount(); ++a) {
                            RepackDetail repackDetail = new RepackDetail();
                            Barang tempBarang = RepackingDialog.this.barangService.getBarang(RepackingDialog.this.repackTM.getValueAt(a, 0) + "");
                            repackDetail.setRepackId(repack.getId());
                            repackDetail.setBarangId(tempBarang.getId());
                            repackDetail.setJumlahBarang(Float.parseFloat(RepackingDialog.this.repackTM.getValueAt(a, 2).toString()));
                            repackDetail.setSatuan(tempBarang.getSatuan1());
                            batchRepackDetail.add(repackDetail);
                            RepackingDialog.this.barangService.updateStokBarangToko(Float.parseFloat(RepackingDialog.this.repackTM.getValueAt(a, 2).toString()), tempBarang.getId());
                            StockCommit stockCommit1 = new StockCommit();
                            stockCommit1.setIdBarang(tempBarang.getId());
                            stockCommit1.setIdTransaksi(repack.getId());
                            stockCommit1.setQueryType("tambah");
                            stockCommit1.setStockType("toko");
                            stockCommit1.setTransactionType("Repack");
                            stockCommit1.setJumlahBarang(repackDetail.getJumlahBarang());
                            stockCommit1.setStatusCommit(false);
                            stockCommit1.setStokAwal(tempBarang.getStokToko());
                            stockCommits.add(stockCommit1);
                        }
                        int[] savePenjualanDetailStatus = RepackingDialog.this.repackDetailService.createRepackDetailBatch(batchRepackDetail);
                        int[] saveStockCommitStatus = RepackingDialog.this.stokCommitService.insertStockCommitBatch(stockCommits);
                        int total = 0;
                        for (int a = 0; a < savePenjualanDetailStatus.length; ++a) {
                            total += savePenjualanDetailStatus[a];
                        }
                        if (total == savePenjualanDetailStatus.length && saveRepack == 1) {
                            JOptionPane.showMessageDialog(null, "Repack Berhasil Tersimpan");
                            RepackingDialog.this.removeAllRow();
                            RepackingDialog.this.setDefault();
                        } else {
                            JOptionPane.showMessageDialog(null, "Repack Gagal Tersimpan");
                        }
                    }
                }
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class passwordInputDialogEventHandler
    implements PasswordInputListener {
        private passwordInputDialogEventHandler() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR") || karyawan.getRole().equalsIgnoreCase("MANAGER") || karyawan.getRole().equalsIgnoreCase("ADMIN")) {
                RepackingDialog.this.loadDataClick();
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class searchPanelPaketEventHandler
    implements SearchPanelListener {
        private searchPanelPaketEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            RepackingDialog.this.idPaketField.setText(chooseValue);
            RepackingDialog.this.loadPaket();
        }
    }

    private class searchPaketDialogEventHandler
    implements SearchDialogListener {
        private searchPaketDialogEventHandler() {
        }

        @Override
        public void searchClick(String searchWord) {
            Object[] colName = new String[]{"ID PAKET", "ID BARANG", "NAMA BARANG"};
            DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
            List<Object[]> datas = RepackingDialog.this.paketService.getPaketByNama(searchWord);
            for (Object[] data : datas) {
                dtm.addRow(data);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Paket");
            sp.addSearchPanelListener(new searchPanelPaketEventHandler());
            sp.setVisible(true);
        }

        public void dispose() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}

