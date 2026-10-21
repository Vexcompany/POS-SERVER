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

public class PromoTebusMurahDialog
extends JDialog {
    private JButton exitBtn;
    private JFormattedTextField hargaField;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JTextField kodeField;
    private JTextField maksField;
    private JFormattedTextField minPembelianField;
    private JTextField namaField;
    private JComboBox satuanCmb;
    private JLabel satuanLbl;
    private JButton saveBtn;
    private BarangService barangService = new BarangService();
    private PromoTebusMurahDetailService promoTebusMurahDetailService = new PromoTebusMurahDetailService();
    private PromoHematDetailService promoHematDetailService = new PromoHematDetailService();
    private PromoTebusDetailService promoTebusDetailService = new PromoTebusDetailService();
    private int screenHeight;
    private int screenWidth;
    private SearchDialog sd;
    private Barang barang;
    private PromoHematDialogListener promoHematDialogListener;
    private DefaultTableModel promoTableModel;
    private String startDateString;
    private String endDateString;
    private String promoTebusMurahId;

    public PromoTebusMurahDialog(Frame parent, boolean modal, DefaultTableModel promoTableModel, Date startDate, Date endDate, String promoTebusMurahId) {
        super(parent, modal);
        this.initComponents();
        this.promoTableModel = promoTableModel;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.startDateString = simpleDateFormat.format(startDate);
        this.endDateString = simpleDateFormat.format(endDate);
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.promoTebusMurahId = promoTebusMurahId;
    }

    public void addListener(PromoHematDialogListener promoHematDialogListener) {
        this.promoHematDialogListener = promoHematDialogListener;
    }

    private void setDefaultData() {
        this.kodeField.setText("");
        this.namaField.setText("");
        this.satuanCmb.removeAllItems();
        this.minPembelianField.setValue(0);
        this.hargaField.setValue(0);
        this.maksField.setText("0");
        this.satuanLbl.setText("");
    }

    private void checkActivePromoHemat() {
        PromoTebusMurahDetail promoTebusMurahDetail = this.promoTebusMurahDetailService.getPromoTebusMurahDetailByBarangId(this.kodeField.getText(), this.satuanCmb.getSelectedItem().toString(), this.startDateString, this.endDateString);
        if (promoTebusMurahDetail != null) {
            JOptionPane.showMessageDialog(null, "Item ini masih aktif di promo Tebus Murah dengan ID :" + promoTebusMurahDetail.getPromoTebusMurahId());
        }
    }

    private void saveClick() {
        Object[] rowValue = new Object[]{this.barang.getId(), this.barang.getNama(), this.satuanCmb.getSelectedItem(), this.minPembelianField.getValue(), this.hargaField.getValue(), this.maksField.getText()};
        if ((Integer)rowValue[4] == 0 || (Integer)rowValue[3] == 0 || this.maksField.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Masukkan Harga Barang Terlebih Dahulu");
        } else {
            this.promoHematDialogListener.saveClick(rowValue);
        }
    }

    protected void loadKodeBarang(String kodeBarang) {
        this.setDefaultData();
        this.barang = this.barangService.getBarang(kodeBarang);
        if (this.barang != null) {
            PromoTebusDetailService promoTebusDetailService = new PromoTebusDetailService();
            PromoTebusDetail promoTebusDetail = promoTebusDetailService.getPromoTebusDetailByBarangId(kodeBarang);
            if (promoTebusDetail != null) {
                this.barang = null;
                JOptionPane.showMessageDialog(null, "BARANG MASIH TERDAFTAR DALAM PROMO TEBUS");
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

    protected void loadPromoTebusMurahData(int row) {
        if (this.promoTableModel.getValueAt(row, 2).equals(this.barang.getSatuan1())) {
            this.satuanCmb.setSelectedIndex(0);
            this.satuanLbl.setText(this.barang.getSatuan1());
        } else if (this.promoTableModel.getValueAt(row, 2).equals(this.barang.getSatuan2())) {
            this.satuanCmb.setSelectedIndex(1);
            this.satuanLbl.setText(this.barang.getSatuan2());
        } else if (this.promoTableModel.getValueAt(row, 2).equals(this.barang.getSatuan3())) {
            this.satuanCmb.setSelectedIndex(2);
            this.satuanLbl.setText(this.barang.getSatuan3());
        }
        this.minPembelianField.setValue(this.promoTableModel.getValueAt(row, 3));
        this.hargaField.setValue(this.promoTableModel.getValueAt(row, 4));
        this.maksField.setText(this.promoTableModel.getValueAt(row, 5).toString());
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
            this.loadPromoTebusMurahData(a);
            break;
        }
        if (!find) {
            this.satuanCmb.requestFocus();
        }
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.jLabel4 = new JLabel();
        this.jLabel5 = new JLabel();
        this.kodeField = new JTextField();
        this.namaField = new JTextField();
        this.satuanCmb = new JComboBox();
        this.hargaField = new JFormattedTextField();
        this.satuanLbl = new JLabel();
        this.saveBtn = new JButton();
        this.exitBtn = new JButton();
        this.jLabel6 = new JLabel();
        this.jLabel7 = new JLabel();
        this.minPembelianField = new JFormattedTextField();
        this.maksField = new JTextField();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("Kode Barang");
        this.jLabel2.setText("Nama Barang");
        this.jLabel3.setText("Satuan");
        this.jLabel4.setText("Harga Promo");
        this.jLabel5.setText("Minimum Pembelian");
        this.kodeField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoTebusMurahDialog.this.kodeFieldKeyPressed(evt);
            }
        });
        this.satuanCmb.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoTebusMurahDialog.this.satuanCmbKeyPressed(evt);
            }
        });
        this.hargaField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoTebusMurahDialog.this.hargaFieldKeyPressed(evt);
            }
        });
        this.saveBtn.setText("Save");
        this.saveBtn.setPreferredSize(new Dimension(73, 23));
        this.saveBtn.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoTebusMurahDialog.this.saveBtnKeyPressed(evt);
            }
        });
        this.exitBtn.setText("Exit");
        this.exitBtn.setPreferredSize(new Dimension(73, 23));
        this.exitBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PromoTebusMurahDialog.this.exitBtnActionPerformed(evt);
            }
        });
        this.exitBtn.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoTebusMurahDialog.this.exitBtnKeyPressed(evt);
            }
        });
        this.jLabel6.setText("F2:cari");
        this.jLabel7.setText("Maksimum Kuantiti");
        this.minPembelianField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoTebusMurahDialog.this.minPembelianFieldKeyPressed(evt);
            }
        });
        this.maksField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoTebusMurahDialog.this.maksFieldKeyPressed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel7, -1, -1, Short.MAX_VALUE).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jLabel2).addComponent(this.jLabel3)).addGap(27, 27, 27)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel4).addGap(0, 0, Short.MAX_VALUE))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.namaField).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent(this.hargaField, -2, 109, -2).addGap(69, 69, 69).addComponent(this.satuanLbl, -1, -1, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addComponent(this.saveBtn, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.exitBtn, -2, -1, -2).addGap(22, 22, 22))).addGap(52, 52, 52)).addGroup(layout.createSequentialGroup().addComponent(this.kodeField, -2, 136, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel6)).addComponent(this.satuanCmb, -2, 72, -2)).addComponent(this.maksField, -2, 109, -2)).addGap(22, 22, 22)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel5).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.minPembelianField).addGap(143, 143, 143)))));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.kodeField, -2, -1, -2).addComponent(this.jLabel6)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.namaField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.satuanCmb, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel5).addComponent(this.minPembelianField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.satuanLbl, GroupLayout.Alignment.TRAILING, -2, 20, -2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.hargaField, -2, -1, -2).addComponent(this.jLabel4))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel7).addComponent(this.maksField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.saveBtn, -2, -1, -2).addComponent(this.exitBtn, -2, -1, -2)).addContainerGap()));
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
            } else if (this.barang != null) {
                this.satuanCmb.requestFocus();
            } else {
                this.kodeField.setText("");
            }
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void satuanCmbKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.minPembelianField.setText(this.minPembelianField.getText().replace(".", ""));
            this.minPembelianField.requestFocus();
            this.minPembelianField.selectAll();
            this.satuanLbl.setText(this.satuanCmb.getSelectedItem().toString());
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        }
    }

    private void hargaFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.maksField.requestFocus();
            this.maksField.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 38) {
            this.satuanCmb.requestFocus();
        }
    }

    private void saveBtnKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            PromoTebusMurahDetail promoTebusMurahDetail = this.promoTebusMurahDetailService.getPromoTebusMurahDetailByBarangId(this.kodeField.getText(), this.satuanCmb.getSelectedItem().toString(), this.startDateString, this.endDateString);
            PromoHematDetail promoHematDetail = this.promoHematDetailService.getPromoHematDetailByBarangId(this.kodeField.getText(), this.satuanCmb.getSelectedItem().toString(), Float.parseFloat(this.minPembelianField.getText()), this.startDateString, this.endDateString);
            PromoTebusDetail promoTebusDetail = this.promoTebusDetailService.getPromoTebusDetailByBarangId(this.kodeField.getText(), this.satuanCmb.getSelectedItem().toString(), this.startDateString, this.endDateString);
            if (promoTebusDetail != null) {
                JOptionPane.showMessageDialog(null, "Item ini masih aktif di promo tebus dengan ID :" + promoTebusDetail.getPromoTebusId());
            } else if (promoHematDetail != null) {
                JOptionPane.showMessageDialog(null, "Item ini masih aktif di promo Hemat dengan ID :" + promoHematDetail.getPromoHematId());
            } else if (promoTebusMurahDetail != null) {
                if (promoTebusMurahDetail.getPromoTebusMurahId().equals(this.promoTebusMurahId)) {
                    this.saveClick();
                    this.escapeClick();
                } else {
                    JOptionPane.showMessageDialog(null, "Item ini masih aktif di promo Tebus Murah dengan ID :" + promoTebusMurahDetail.getPromoTebusMurahId());
                }
            } else {
                this.saveClick();
                this.escapeClick();
            }
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 38) {
            this.maksField.requestFocus();
            this.maksField.selectAll();
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

    private void minPembelianFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.hargaField.setText(this.hargaField.getText().replace(".", ""));
            this.hargaField.requestFocus();
            this.hargaField.selectAll();
            this.satuanLbl.setText(this.satuanCmb.getSelectedItem().toString());
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        }
    }

    private void maksFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.saveBtn.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 37 || evt.getKeyCode() == 38) {
            if (evt.getKeyCode() == 10) {
                this.hargaField.setText(this.hargaField.getText().replace(".", ""));
                this.hargaField.requestFocus();
                this.hargaField.selectAll();
                this.satuanLbl.setText(this.satuanCmb.getSelectedItem().toString());
            } else if (evt.getKeyCode() == 27) {
                this.escapeClick();
            }
        }
    }

    private class searchPanelBarangEventHandler
    implements SearchPanelListener {
        private searchPanelBarangEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            PromoTebusMurahDialog.this.loadKodeBarang(chooseValue);
            PromoTebusMurahDialog.this.searchDataPromo(chooseValue);
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
            List<Barang> barangs = PromoTebusMurahDialog.this.barangService.getBarangs("", searchWord, false, "", true);
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

