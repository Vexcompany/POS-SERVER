/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import minimart.engine.model.Barang;
import minimart.engine.model.PromoHematDetail;
import minimart.engine.model.PromoTebusDetail;
import minimart.engine.model.PromoTebusMurahDetail;
import minimart.engine.service.BarangService;
import minimart.engine.service.PromoHematDetailService;
import minimart.engine.service.PromoTebusDetailService;
import minimart.engine.service.PromoTebusMurahDetailService;
import minimart.ui.listener.PromoHematDialogListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class PromoTebusDialog
extends JDialog {
    private JFormattedTextField discountField;
    private JButton exitBtn;
    private JTextField idPromoField;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JTextField jumlahField;
    private JTextField kodeField;
    private JTextField namaField;
    private JLabel namaPromoLbl;
    private JComboBox<String> satuan2Cmb;
    private JComboBox satuanCmb;
    private JLabel satuanLbl;
    private JButton saveBtn;
    private BarangService barangService = new BarangService();
    private int screenHeight;
    private int screenWidth;
    private SearchDialog sd;
    private Barang barang;
    private PromoHematDialogListener promoHematDialogListener;
    private DefaultTableModel promoTableModel;
    private PromoHematDetailService promoHematDetailService = new PromoHematDetailService();
    private PromoTebusMurahDetailService promoTebusMurahDetailService = new PromoTebusMurahDetailService();
    private PromoTebusDetailService promoTebusDetailService = new PromoTebusDetailService();
    private String startDateString;
    private String endDateString;
    private String promoTebusId;
    private Barang promoBarang;

    public PromoTebusDialog(Frame parent, boolean modal, DefaultTableModel promoTableModel, Date startDate, Date endDate, String promoTebusId) {
        super(parent, modal);
        this.initComponents();
        this.promoTableModel = promoTableModel;
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.startDateString = simpleDateFormat.format(startDate);
        this.endDateString = simpleDateFormat.format(endDate);
        this.promoTebusId = promoTebusId;
    }

    public void addListener(PromoHematDialogListener promoHematDialogListener) {
        this.promoHematDialogListener = promoHematDialogListener;
    }

    private void setDefaultData() {
        this.kodeField.setText("");
        this.namaField.setText("");
        this.satuanCmb.removeAllItems();
        this.satuan2Cmb.removeAllItems();
        this.discountField.setValue(0);
        this.jumlahField.setText("1");
        this.satuanLbl.setText("");
        this.idPromoField.setText("");
        this.namaPromoLbl.setText("-");
    }

    private void saveClick() {
        Object[] rowValue = new Object[]{this.barang.getId(), this.barang.getNama(), this.jumlahField.getText(), this.satuanCmb.getSelectedItem(), this.idPromoField.getText(), this.namaPromoLbl.getText(), this.discountField.getValue(), this.satuan2Cmb.getSelectedItem()};
        if ((Long)rowValue[6] == 0L) {
            JOptionPane.showMessageDialog(null, "Masukkan Discount Barang Terlebih Dahulu");
        } else if (rowValue[4].equals("")) {
            JOptionPane.showMessageDialog(null, "Masukkan Promo Barang Terlebih Dahulu");
        } else {
            this.promoHematDialogListener.saveClick(rowValue);
        }
    }

    protected void loadKodeBarang2(String kodeBarang) {
        Barang tempBarang = this.barangService.getBarang(kodeBarang);
        if (tempBarang != null) {
            this.idPromoField.setText(tempBarang.getId());
            this.namaPromoLbl.setText(tempBarang.getNama());
            this.satuan2Cmb.removeAllItems();
            this.satuan2Cmb.addItem(tempBarang.getSatuan1());
            if (!tempBarang.getSatuan2().equals("")) {
                this.satuan2Cmb.addItem(tempBarang.getSatuan2());
            }
            if (!tempBarang.getSatuan3().equals("")) {
                this.satuan2Cmb.addItem(tempBarang.getSatuan3());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Barang Tidak Ditemukan");
        }
    }

    protected void loadPromoBarang(String idItemPromo) {
        this.promoBarang = this.barangService.getBarang(idItemPromo);
        this.namaPromoLbl.setText(this.promoBarang.getNama());
    }

    protected void loadKodeBarang(String kodeBarang) {
        this.setDefaultData();
        this.barang = this.barangService.getBarang(kodeBarang);
        if (this.barang != null) {
            PromoHematDetail promoHematDetail = this.promoHematDetailService.getPromoHematDetailByBarangId(this.barang.getId(), this.startDateString, this.endDateString);
            if (promoHematDetail != null) {
                this.barang = null;
                JOptionPane.showMessageDialog(null, "BARANG MASIH TERDAFTAR DALAM PROMO HEMAT");
            } else {
                this.kodeField.setText(this.barang.getId());
                this.namaField.setText(this.barang.getNama());
                this.satuanCmb.addItem(this.barang.getSatuan1());
                if (!this.barang.getSatuan2().equals("")) {
                    this.satuanCmb.addItem(this.barang.getSatuan2());
                }
                if (!this.barang.getSatuan3().equals("")) {
                    this.satuanCmb.addItem(this.barang.getSatuan3());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Barang Tidak Ditemukan");
        }
    }

    protected void loadPromoData(int row) {
        if (this.promoTableModel.getValueAt(row, 3).equals(this.barang.getSatuan1())) {
            this.satuanCmb.setSelectedIndex(0);
            this.satuanLbl.setText(this.barang.getSatuan1());
        } else if (this.promoTableModel.getValueAt(row, 3).equals(this.barang.getSatuan2())) {
            this.satuanCmb.setSelectedIndex(1);
            this.satuanLbl.setText(this.barang.getSatuan2());
        } else if (this.promoTableModel.getValueAt(row, 3).equals(this.barang.getSatuan3())) {
            this.satuanCmb.setSelectedIndex(2);
            this.satuanLbl.setText(this.barang.getSatuan3());
        }
        this.discountField.setValue(this.promoTableModel.getValueAt(row, 6));
        this.jumlahField.setText(this.promoTableModel.getValueAt(row, 2).toString());
        this.idPromoField.setText(this.promoTableModel.getValueAt(row, 4).toString());
        this.namaPromoLbl.setText(this.promoTableModel.getValueAt(row, 5).toString());
        this.promoBarang = this.barangService.getBarang(this.promoTableModel.getValueAt(row, 4).toString());
        this.loadKodeBarang2(this.promoBarang.getId());
        if (this.promoTableModel.getValueAt(row, 7).toString().equals(this.promoBarang.getSatuan1())) {
            this.satuan2Cmb.setSelectedIndex(0);
        } else if (this.promoTableModel.getValueAt(row, 7).toString().equals(this.promoBarang.getSatuan2())) {
            this.satuan2Cmb.setSelectedIndex(1);
        } else if (this.promoTableModel.getValueAt(row, 7).toString().equals(this.promoBarang.getSatuan3())) {
            this.satuan2Cmb.setSelectedIndex(2);
        }
    }

    private void escapeClick() {
        this.kodeField.requestFocus();
        this.kodeField.selectAll();
    }

    private void searchDataPromo(String idBarang) {
        boolean find = false;
        for (int a = 0; a < this.promoTableModel.getRowCount(); ++a) {
            String barangId = this.promoTableModel.getValueAt(a, 0).toString();
            if (!idBarang.equals(barangId)) continue;
            find = true;
            this.loadPromoData(a);
            break;
        }
        if (!find) {
            this.jumlahField.requestFocus();
            this.jumlahField.selectAll();
        }
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.jLabel4 = new JLabel();
        this.kodeField = new JTextField();
        this.namaField = new JTextField();
        this.satuanCmb = new JComboBox();
        this.satuanLbl = new JLabel();
        this.saveBtn = new JButton();
        this.exitBtn = new JButton();
        this.jLabel6 = new JLabel();
        this.jLabel8 = new JLabel();
        this.jumlahField = new JTextField();
        this.idPromoField = new JTextField();
        this.namaPromoLbl = new JLabel();
        this.discountField = new JFormattedTextField();
        this.jLabel10 = new JLabel();
        this.jLabel5 = new JLabel();
        this.jLabel7 = new JLabel();
        this.satuan2Cmb = new JComboBox();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("Kode Barang");
        this.jLabel2.setText("Nama Barang");
        this.jLabel3.setText("Satuan");
        this.jLabel4.setText("Promo Barang");
        this.kodeField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoTebusDialog.this.kodeFieldKeyPressed(evt);
            }
        });
        this.namaField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoTebusDialog.this.namaFieldKeyPressed(evt);
            }
        });
        this.satuanCmb.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoTebusDialog.this.satuanCmbKeyPressed(evt);
            }
        });
        this.saveBtn.setText("Save");
        this.saveBtn.setPreferredSize(new Dimension(73, 23));
        this.saveBtn.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoTebusDialog.this.saveBtnKeyPressed(evt);
            }
        });
        this.exitBtn.setText("Exit");
        this.exitBtn.setPreferredSize(new Dimension(73, 23));
        this.exitBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PromoTebusDialog.this.exitBtnActionPerformed(evt);
            }
        });
        this.exitBtn.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoTebusDialog.this.exitBtnKeyPressed(evt);
            }
        });
        this.jLabel6.setText("F2:cari");
        this.jLabel8.setText("Jumlah Pembelian");
        this.jumlahField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoTebusDialog.this.jumlahFieldKeyPressed(evt);
            }
        });
        this.idPromoField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoTebusDialog.this.idPromoFieldKeyPressed(evt);
            }
        });
        this.namaPromoLbl.setText("-");
        this.discountField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));
        this.discountField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoTebusDialog.this.discountFieldKeyPressed(evt);
            }
        });
        this.jLabel10.setText("Discount");
        this.jLabel5.setText("F2:cari");
        this.jLabel7.setText("Satuan Item Promo");
        this.satuan2Cmb.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoTebusDialog.this.satuan2CmbKeyPressed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.jLabel8, -1, -1, Short.MAX_VALUE).addComponent(this.jLabel1, GroupLayout.Alignment.LEADING).addComponent(this.jLabel2, -1, -1, Short.MAX_VALUE)).addGap(37, 37, 37)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel3).addComponent(this.jLabel4).addComponent(this.jLabel10)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE))).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.namaField, GroupLayout.Alignment.LEADING).addComponent(this.kodeField, GroupLayout.Alignment.LEADING, -1, 136, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel6).addContainerGap()).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.satuan2Cmb, -2, 73, -2).addComponent(this.saveBtn, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.exitBtn, -2, -1, -2).addGap(96, 96, 96))).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.discountField, GroupLayout.Alignment.LEADING).addComponent(this.namaPromoLbl, GroupLayout.Alignment.LEADING, -1, -1, Short.MAX_VALUE).addComponent(this.idPromoField, GroupLayout.Alignment.LEADING, -1, 136, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel5)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.jumlahField, GroupLayout.Alignment.LEADING).addComponent(this.satuanCmb, GroupLayout.Alignment.LEADING, 0, 72, Short.MAX_VALUE)))).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(this.satuanLbl)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel7).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()))));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.kodeField, -2, -1, -2).addComponent(this.jLabel6)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.namaField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel8).addComponent(this.jumlahField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.satuanCmb, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel4).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.satuanLbl, -2, 20, -2)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.idPromoField, -2, -1, -2).addComponent(this.jLabel5)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.namaPromoLbl).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.discountField, -2, -1, -2).addComponent(this.jLabel10)).addGap(0, 0, Short.MAX_VALUE))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel7).addComponent(this.satuan2Cmb, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.saveBtn, -2, -1, -2).addComponent(this.exitBtn, -2, -1, -2)).addContainerGap()));
        this.pack();
    }

    private void exitBtnActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void kodeFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 113) {
            this.sd = new SearchDialog(null, true, "Pencarian Data Barang");
            this.sd.addSearchDialogListener(new searchBarangDialogEventHandler());
            int w = this.sd.getWidth();
            int h = this.sd.getHeight();
            this.sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            this.sd.setVisible(true);
        } else if (evt.getKeyCode() == 10 || evt.getKeyCode() == 9) {
            if (this.kodeField.getText().equals("")) {
                this.sd = new SearchDialog(null, true, "Pencarian Data Barang");
                this.sd.addSearchDialogListener(new searchBarangDialogEventHandler());
                int w = this.sd.getWidth();
                int h = this.sd.getHeight();
                this.sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                this.sd.setVisible(true);
            } else {
                this.loadKodeBarang(this.kodeField.getText());
                if (this.barang != null) {
                    this.searchDataPromo(this.barang.getId());
                    this.satuanCmb.requestFocus();
                } else {
                    this.kodeField.setText("");
                }
            }
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void satuanCmbKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.idPromoField.requestFocus();
            this.idPromoField.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 37 || evt.getKeyCode() == 38) {
            this.jumlahField.requestFocus();
        }
    }

    private void saveBtnKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            PromoHematDetail promoHematDetail = this.promoHematDetailService.getPromoHematDetailByBarangId(this.idPromoField.getText(), this.startDateString, this.endDateString);
            PromoTebusMurahDetail promoTebusMurahDetail = this.promoTebusMurahDetailService.getPromoTebusMurahDetailByBarangId(this.idPromoField.getText(), this.startDateString, this.endDateString);
            PromoTebusDetail promoTebusDetail = this.promoTebusDetailService.getPromoTebusDetailByBarangId(this.idPromoField.getText(), this.satuanCmb.getSelectedItem().toString(), this.startDateString, this.endDateString);
            if (promoHematDetail != null) {
                JOptionPane.showMessageDialog(null, "Item ini masih aktif di promo hemat dengan ID: " + promoHematDetail.getPromoHematId());
            } else if (promoTebusMurahDetail != null) {
                JOptionPane.showMessageDialog(null, "Item ini masih aktif di Promo Tebus Murah dengan ID: " + promoTebusMurahDetail.getPromoTebusMurahId());
            } else if (promoTebusDetail != null) {
                if (promoTebusDetail.getPromoTebusId().equals(this.promoTebusId)) {
                    this.saveClick();
                    this.escapeClick();
                } else {
                    JOptionPane.showMessageDialog(null, "Item ini masih aktif di Promo Tebus dengan ID: " + promoTebusDetail.getPromoTebusId());
                }
            } else {
                this.saveClick();
                this.escapeClick();
            }
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 38) {
            this.satuan2Cmb.requestFocus();
        }
    }

    private void exitBtnKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.dispose();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 37 || evt.getKeyCode() == 38) {
            this.saveBtn.requestFocus();
        }
    }

    private void discountFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.satuan2Cmb.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 37 || evt.getKeyCode() == 38) {
            this.idPromoField.requestFocus();
        }
    }

    private void idPromoFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 113) {
            this.sd = new SearchDialog(null, true, "Pencarian Data Barang");
            this.sd.addSearchDialogListener(new searchBarangDialogEventHandler2());
            int w = this.sd.getWidth();
            int h = this.sd.getHeight();
            this.sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            this.sd.setVisible(true);
        } else if (evt.getKeyCode() == 10) {
            if (this.idPromoField.getText().equals("")) {
                this.sd = new SearchDialog(null, true, "Pencarian Data Barang");
                this.sd.addSearchDialogListener(new searchBarangDialogEventHandler2());
                int w = this.sd.getWidth();
                int h = this.sd.getHeight();
                this.sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                this.sd.setVisible(true);
            } else {
                this.loadKodeBarang2(this.idPromoField.getText());
                this.discountField.requestFocus();
                this.discountField.setText(this.discountField.getText().replace(".", ""));
                this.discountField.selectAll();
            }
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 37 || evt.getKeyCode() == 38) {
            this.satuanCmb.requestFocus();
        }
    }

    private void jumlahFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.satuanCmb.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 37 || evt.getKeyCode() == 38) {
            this.kodeField.requestFocus();
            this.kodeField.selectAll();
        }
    }

    private void namaFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.jumlahField.requestFocus();
            this.jumlahField.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 37 || evt.getKeyCode() == 38) {
            this.kodeField.requestFocus();
            this.kodeField.selectAll();
        }
    }

    private void satuan2CmbKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.saveBtn.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 37 || evt.getKeyCode() == 38) {
            this.discountField.requestFocus();
            this.discountField.setText(this.discountField.getText().replace(".", ""));
            this.discountField.selectAll();
        }
    }

    private class searchPanelBarangEventHandler2
    implements SearchPanelListener {
        private searchPanelBarangEventHandler2() {
        }

        @Override
        public void tableClick(String chooseValue) {
            PromoTebusDialog.this.loadKodeBarang2(chooseValue);
            PromoTebusDialog.this.discountField.requestFocus();
            PromoTebusDialog.this.discountField.setText(PromoTebusDialog.this.discountField.getText().replace(".", ""));
            PromoTebusDialog.this.discountField.selectAll();
        }
    }

    private class searchBarangDialogEventHandler2
    implements SearchDialogListener {
        private searchBarangDialogEventHandler2() {
        }

        @Override
        public void searchClick(String searchWord) {
            Object[] colName = new String[]{"KODE BARANG", "NAMA BARANG", "SATUAN", "JENIS"};
            DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
            List<Barang> barangs = PromoTebusDialog.this.barangService.getBarangs("", searchWord, false, "", true);
            for (Barang barang : barangs) {
                Object[] temp = new String[]{barang.getId(), barang.getNama(), barang.getSatuan1(), barang.getJenis()};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Barang");
            sp.addSearchPanelListener(new searchPanelBarangEventHandler2());
            sp.setVisible(true);
        }
    }

    private class searchPanelBarangEventHandler
    implements SearchPanelListener {
        private searchPanelBarangEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            PromoTebusDialog.this.loadKodeBarang(chooseValue);
            PromoTebusDialog.this.searchDataPromo(chooseValue);
        }
    }

    private class searchBarangDialogEventHandler
    implements SearchDialogListener {
        private searchBarangDialogEventHandler() {
        }

        @Override
        public void searchClick(String searchWord) {
            Object[] colName = new String[]{"KODE BARANG", "NAMA BARANG", "SATUAN", "JENIS"};
            DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
            List<Barang> barangs = PromoTebusDialog.this.barangService.getBarangs("", searchWord, false, "", true);
            for (Barang barang : barangs) {
                Object[] temp = new String[]{barang.getId(), barang.getNama(), barang.getSatuan1(), barang.getJenis()};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Barang");
            sp.addSearchPanelListener(new searchPanelBarangEventHandler());
            sp.setVisible(true);
        }
    }
}

