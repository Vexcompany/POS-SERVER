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
import java.text.NumberFormat;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import minimart.engine.controller.Helper;
import minimart.engine.model.Barang;
import minimart.engine.service.BarangService;
import minimart.ui.listener.PenerimaanBsDialogListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class PenerimaanBsDialog
extends JDialog {
    private JButton cancelButton;
    private JFormattedTextField hppField;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JTextField jumlahBarangField;
    private JTextField kodeBarangField;
    private JTextField namaBarangField;
    private JLabel satuanField;
    private JLabel satuanTokoLbl;
    private JButton saveButton;
    private JTextField stokTokoField;
    private JLabel stokTokoLbl;
    private JFormattedTextField totalField;
    private int screenWidth;
    private int screenHeight;
    private SearchDialog sd;
    private String lokasi;
    private BarangService barangService = new BarangService();
    private DefaultTableModel penerimaanBSTalbeModel;
    private Barang barang;
    private Helper helper = Helper.getInstance();
    private PenerimaanBsDialogListener bsDialogListener;

    public PenerimaanBsDialog(Frame parent, boolean modal, DefaultTableModel tableModel, String lokasi) {
        super(parent, modal);
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.penerimaanBSTalbeModel = tableModel;
        this.lokasi = lokasi;
        this.stokTokoLbl.setText("Stok " + lokasi);
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.kodeBarangField = new JTextField();
        this.namaBarangField = new JTextField();
        this.jumlahBarangField = new JTextField();
        this.stokTokoLbl = new JLabel();
        this.saveButton = new JButton();
        this.cancelButton = new JButton();
        this.satuanField = new JLabel();
        this.jLabel3 = new JLabel();
        this.stokTokoField = new JTextField();
        this.satuanTokoLbl = new JLabel();
        this.jLabel4 = new JLabel();
        this.jLabel5 = new JLabel();
        this.hppField = new JFormattedTextField();
        this.totalField = new JFormattedTextField();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("Kode Barang");
        this.jLabel2.setText("Nama Barang");
        this.kodeBarangField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenerimaanBsDialog.this.kodeBarangFieldKeyPressed(evt);
            }
        });
        this.namaBarangField.setEditable(false);
        this.jumlahBarangField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenerimaanBsDialog.this.jumlahBarangFieldKeyPressed(evt);
            }
        });
        this.stokTokoLbl.setText("Stok Toko");
        this.saveButton.setLabel("Save");
        this.saveButton.setPreferredSize(new Dimension(73, 23));
        this.saveButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PenerimaanBsDialog.this.saveButtonActionPerformed(evt);
            }
        });
        this.saveButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenerimaanBsDialog.this.saveButtonKeyPressed(evt);
            }
        });
        this.cancelButton.setText("Cancel");
        this.cancelButton.setPreferredSize(new Dimension(73, 23));
        this.cancelButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PenerimaanBsDialog.this.cancelButtonActionPerformed(evt);
            }
        });
        this.cancelButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenerimaanBsDialog.this.cancelButtonKeyPressed(evt);
            }
        });
        this.jLabel3.setText("Jumlah Barang");
        this.stokTokoField.setEnabled(false);
        this.satuanTokoLbl.setText("-");
        this.jLabel4.setText("Hpp");
        this.jLabel5.setText("Total Hpp");
        this.hppField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));
        this.totalField.setEditable(false);
        this.totalField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(107, 107, 107).addComponent(this.saveButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.cancelButton, -2, -1, -2)).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1, -1, -1, Short.MAX_VALUE).addComponent(this.stokTokoLbl, GroupLayout.Alignment.TRAILING, -1, -1, Short.MAX_VALUE).addComponent(this.jLabel2, -1, -1, Short.MAX_VALUE).addComponent(this.jLabel4, GroupLayout.Alignment.TRAILING, -1, -1, Short.MAX_VALUE).addComponent(this.jLabel3, GroupLayout.Alignment.TRAILING, -1, 104, Short.MAX_VALUE).addComponent(this.jLabel5, -1, -1, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jumlahBarangField, -1, 143, Short.MAX_VALUE).addComponent(this.hppField)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.satuanField, -1, 43, Short.MAX_VALUE)).addGroup(GroupLayout.Alignment.TRAILING, layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.kodeBarangField, -1, 190, Short.MAX_VALUE).addComponent(this.namaBarangField).addGroup(layout.createSequentialGroup().addComponent(this.stokTokoField, -2, 130, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.satuanTokoLbl, -2, 27, -2).addGap(18, 18, 18))).addGroup(layout.createSequentialGroup().addComponent(this.totalField, -1, 143, Short.MAX_VALUE).addGap(47, 47, 47))))).addGap(58, 58, 58)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.kodeBarangField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.namaBarangField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.stokTokoLbl, -1, -1, Short.MAX_VALUE).addComponent(this.stokTokoField).addComponent(this.satuanTokoLbl)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.satuanField, GroupLayout.Alignment.TRAILING, -1, -1, Short.MAX_VALUE).addComponent(this.jumlahBarangField, GroupLayout.Alignment.TRAILING, -2, -1, -2)).addComponent(this.jLabel3, GroupLayout.Alignment.TRAILING)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4).addComponent(this.hppField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel5).addComponent(this.totalField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.saveButton, -2, -1, -2).addComponent(this.cancelButton, -2, -1, -2)).addContainerGap()));
        this.pack();
    }

    private void setDefault() {
        this.kodeBarangField.setText("");
        this.namaBarangField.setText("");
        this.jumlahBarangField.setText("");
    }

    protected void escapeClick() {
        this.kodeBarangField.requestFocus();
        this.kodeBarangField.selectAll();
    }

    protected void loadKodeBarang(String kodeBarang) {
        this.setDefault();
        this.barang = this.barangService.getBarang(kodeBarang);
        if (this.barang != null) {
            this.kodeBarangField.setText(kodeBarang);
            this.namaBarangField.setText(this.barang.getNama());
            this.satuanField.setText(this.barang.getSatuan1());
            if (this.lokasi.equalsIgnoreCase("GUDANG")) {
                this.stokTokoField.setText(this.barang.getStokGudang() + "");
            } else if (this.lokasi.equalsIgnoreCase("TOKO")) {
                this.stokTokoField.setText(this.barang.getStokToko() + "");
            }
            this.satuanTokoLbl.setText(this.barang.getSatuan1());
            this.jumlahBarangField.requestFocus();
            this.jumlahBarangField.selectAll();
            this.hppField.setValue(this.barang.getHpp());
        } else {
            JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan");
        }
    }

    public void addListener(PenerimaanBsDialogListener bsDialogListener) {
        this.bsDialogListener = bsDialogListener;
    }

    private void kodeBarangFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 113) {
            this.sd = new SearchDialog(null, true, "Pencarian Data Barang");
            this.sd.addSearchDialogListener(new searchBarangDialogEventHandler());
            int w = this.sd.getWidth();
            int h = this.sd.getHeight();
            this.sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            this.sd.setVisible(true);
        } else if (evt.getKeyCode() == 10 || evt.getKeyCode() == 9) {
            if (this.kodeBarangField.getText().equals("")) {
                this.sd = new SearchDialog(null, true, "Pencarian Data Barang");
                this.sd.addSearchDialogListener(new searchBarangDialogEventHandler());
                int w = this.sd.getWidth();
                int h = this.sd.getHeight();
                this.sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                this.sd.setVisible(true);
            } else {
                this.loadKodeBarang(this.kodeBarangField.getText());
                this.searchTable(this.kodeBarangField.getText());
            }
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void jumlahBarangFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (this.helper.isInteger(this.jumlahBarangField.getText())) {
                this.saveButton.requestFocus();
                this.jumlahBarangField.setBackground(Color.white);
                this.totalField.setValue(this.barang.getHpp() * (long)Integer.parseInt(this.jumlahBarangField.getText()));
            } else {
                this.jumlahBarangField.setBackground(Color.red);
            }
        } else if (evt.getKeyCode() == 38 || evt.getKeyCode() == 27) {
            this.escapeClick();
        }
    }

    private void cancelButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void cancelButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.dispose();
        } else if (evt.getKeyCode() == 37 || evt.getKeyCode() == 38) {
            this.saveButton.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        }
    }

    private void saveClick() {
        Object[] rowValue = new Object[]{this.kodeBarangField.getText(), this.namaBarangField.getText(), this.jumlahBarangField.getText(), this.satuanField.getText(), this.hppField.getValue(), this.totalField.getValue()};
        this.bsDialogListener.saveClick(rowValue);
        this.escapeClick();
    }

    private void saveButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.saveClick();
        } else if (evt.getKeyCode() == 38) {
            this.jumlahBarangField.requestFocus();
            this.jumlahBarangField.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        }
    }

    private void saveButtonActionPerformed(ActionEvent evt) {
        this.saveClick();
    }

    protected void loadDataFromTable(int row) {
        this.jumlahBarangField.setText(this.penerimaanBSTalbeModel.getValueAt(row, 2) + "");
        this.jumlahBarangField.selectAll();
    }

    private void searchTable(String idBarang) {
        for (int a = 0; a < this.penerimaanBSTalbeModel.getRowCount(); ++a) {
            String barangId = this.penerimaanBSTalbeModel.getValueAt(a, 0).toString();
            if (!idBarang.equals(barangId)) continue;
            this.loadDataFromTable(a);
            break;
        }
    }

    private class searchPanelBarangEventHandler
    implements SearchPanelListener {
        private searchPanelBarangEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            PenerimaanBsDialog.this.loadKodeBarang(chooseValue);
            PenerimaanBsDialog.this.searchTable(chooseValue);
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
            List<Barang> barangs = PenerimaanBsDialog.this.barangService.getBarangs("", searchWord, false, "", true);
            for (Barang barang : barangs) {
                Object[] temp = new String[]{barang.getId(), barang.getNama(), barang.getSatuan1(), barang.getJenis()};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Barang");
            sp.addSearchPanelListener(new searchPanelBarangEventHandler());
            sp.setVisible(true);
        }

        public void dispose() {
        }
    }
}

