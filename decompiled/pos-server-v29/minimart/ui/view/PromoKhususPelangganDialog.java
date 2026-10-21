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
import minimart.engine.model.PromoTebusDetail;
import minimart.engine.service.BarangService;
import minimart.engine.service.PromoTebusDetailService;
import minimart.ui.listener.PromoHematDialogListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class PromoKhususPelangganDialog
extends JDialog {
    private JButton exitBtn;
    private JFormattedTextField hargaField;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JTextField kodeField;
    private JTextField minimumField;
    private JTextField namaField;
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

    public PromoKhususPelangganDialog(Frame parent, boolean modal, DefaultTableModel promoTableModel) {
        super(parent, modal);
        this.initComponents();
        this.promoTableModel = promoTableModel;
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    }

    public void addListener(PromoHematDialogListener promoHematDialogListener) {
        this.promoHematDialogListener = promoHematDialogListener;
    }

    private void setDefaultData() {
        this.kodeField.setText("");
        this.namaField.setText("");
        this.satuanCmb.removeAllItems();
        this.hargaField.setValue(0);
        this.minimumField.setText("1");
        this.satuanLbl.setText("");
    }

    private void saveClick() {
        Object[] rowValue = new Object[]{this.barang.getId(), this.barang.getNama(), this.satuanCmb.getSelectedItem(), this.hargaField.getValue(), this.minimumField.getText()};
        if ((Integer)rowValue[3] == 0) {
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

    protected void loadPromoHematData(int row) {
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
        this.hargaField.setValue(this.promoTableModel.getValueAt(row, 3));
        this.minimumField.setText(this.promoTableModel.getValueAt(row, 4).toString());
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
            this.loadPromoHematData(a);
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
        this.minimumField = new JTextField();
        this.kodeField = new JTextField();
        this.namaField = new JTextField();
        this.satuanCmb = new JComboBox();
        this.hargaField = new JFormattedTextField();
        this.satuanLbl = new JLabel();
        this.saveBtn = new JButton();
        this.exitBtn = new JButton();
        this.jLabel6 = new JLabel();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("Kode Barang");
        this.jLabel2.setText("Nama Barang");
        this.jLabel3.setText("Satuan");
        this.jLabel4.setText("Harga Promo");
        this.jLabel5.setText("Minimum Pembelian");
        this.minimumField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoKhususPelangganDialog.this.minimumFieldKeyPressed(evt);
            }
        });
        this.kodeField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoKhususPelangganDialog.this.kodeFieldKeyPressed(evt);
            }
        });
        this.satuanCmb.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoKhususPelangganDialog.this.satuanCmbKeyPressed(evt);
            }
        });
        this.hargaField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoKhususPelangganDialog.this.hargaFieldKeyPressed(evt);
            }
        });
        this.saveBtn.setText("Save");
        this.saveBtn.setPreferredSize(new Dimension(73, 23));
        this.saveBtn.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoKhususPelangganDialog.this.saveBtnKeyPressed(evt);
            }
        });
        this.exitBtn.setText("Exit");
        this.exitBtn.setPreferredSize(new Dimension(73, 23));
        this.exitBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PromoKhususPelangganDialog.this.exitBtnActionPerformed(evt);
            }
        });
        this.exitBtn.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoKhususPelangganDialog.this.exitBtnKeyPressed(evt);
            }
        });
        this.jLabel6.setText("F2:cari");
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap(-1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel5).addComponent(this.jLabel1).addComponent(this.jLabel2).addComponent(this.jLabel3).addComponent(this.jLabel4)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.namaField).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.hargaField, GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent(this.minimumField, -2, 99, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.satuanLbl, -1, -1, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addComponent(this.saveBtn, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.exitBtn, -2, -1, -2).addGap(22, 22, 22))).addGap(52, 52, 52)).addGroup(layout.createSequentialGroup().addComponent(this.kodeField, -2, 136, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel6)).addComponent(this.satuanCmb, -2, 72, -2)).addGap(22, 22, 22)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.kodeField, -2, -1, -2).addComponent(this.jLabel6)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.namaField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.satuanCmb, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4).addComponent(this.hargaField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.satuanLbl, GroupLayout.Alignment.TRAILING, -2, 20, -2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel5).addComponent(this.minimumField, -2, -1, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.saveBtn, -2, -1, -2).addComponent(this.exitBtn, -2, -1, -2)).addContainerGap()));
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
            this.hargaField.setText(this.hargaField.getText().replace(".", ""));
            this.hargaField.requestFocus();
            this.hargaField.selectAll();
            this.satuanLbl.setText(this.satuanCmb.getSelectedItem().toString());
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        }
    }

    private void hargaFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.minimumField.requestFocus();
            this.minimumField.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 38) {
            this.satuanCmb.requestFocus();
        }
    }

    private void minimumFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.saveBtn.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 38) {
            this.hargaField.requestFocus();
            this.hargaField.setText(this.hargaField.getText().replace(".", ""));
            this.hargaField.selectAll();
        }
    }

    private void saveBtnKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.saveClick();
            this.escapeClick();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 38) {
            this.minimumField.requestFocus();
            this.minimumField.selectAll();
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

    private class searchPanelBarangEventHandler
    implements SearchPanelListener {
        private searchPanelBarangEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            PromoKhususPelangganDialog.this.loadKodeBarang(chooseValue);
            PromoKhususPelangganDialog.this.searchDataPromo(chooseValue);
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
            List<Barang> barangs = PromoKhususPelangganDialog.this.barangService.getBarangs("", searchWord, false, "", true);
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

