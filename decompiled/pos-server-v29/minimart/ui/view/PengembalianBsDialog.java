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
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import minimart.engine.controller.Helper;
import minimart.engine.model.Barang;
import minimart.engine.service.BarangService;
import minimart.ui.listener.PengembalianBsDialogListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class PengembalianBsDialog
extends JDialog {
    private JButton cancelButton;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JTextField jumlahPengembalianField;
    private JTextField kodeBarangField;
    private JTextField namaBarangField;
    private JLabel satuan1;
    private JLabel satuan2;
    private JButton saveButton;
    private JTextField sisaBsField;
    private DefaultTableModel pengembalianBsTableModel;
    private PengembalianBsDialogListener listener;
    private Barang barang;
    private BarangService barangService = new BarangService();
    private SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Barang");
    private int screenWidth;
    private int screenHeight;
    private Helper helper = Helper.getInstance();

    public PengembalianBsDialog(Frame parent, boolean modal, DefaultTableModel tableModel) {
        super(parent, modal);
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.pengembalianBsTableModel = tableModel;
    }

    public void addListener(PengembalianBsDialogListener listener) {
        this.listener = listener;
    }

    protected void escapeClick() {
        this.kodeBarangField.requestFocus();
        this.kodeBarangField.selectAll();
    }

    private void setDefault() {
        this.kodeBarangField.setText("");
        this.namaBarangField.setText("");
        this.sisaBsField.setText("");
        this.jumlahPengembalianField.setText("");
    }

    public void loadDataFromTable(int row) {
        this.jumlahPengembalianField.setText(this.pengembalianBsTableModel.getValueAt(row, 2) + "");
    }

    public void loadKodeBarang(String kodeBarang) {
        this.setDefault();
        this.barang = this.barangService.getBarang(kodeBarang);
        if (this.barang != null) {
            this.kodeBarangField.setText(kodeBarang);
            this.namaBarangField.setText(this.barang.getNama());
            this.sisaBsField.setText(this.barang.getStokBs() + "");
            this.satuan1.setText(this.barang.getSatuan1());
            this.satuan2.setText(this.barang.getSatuan1());
            this.jumlahPengembalianField.requestFocus();
            this.jumlahPengembalianField.selectAll();
        } else {
            JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan");
        }
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.kodeBarangField = new JTextField();
        this.jLabel2 = new JLabel();
        this.namaBarangField = new JTextField();
        this.jLabel3 = new JLabel();
        this.sisaBsField = new JTextField();
        this.jLabel4 = new JLabel();
        this.jumlahPengembalianField = new JTextField();
        this.satuan1 = new JLabel();
        this.satuan2 = new JLabel();
        this.saveButton = new JButton();
        this.cancelButton = new JButton();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("Kode Barang");
        this.kodeBarangField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PengembalianBsDialog.this.kodeBarangFieldKeyPressed(evt);
            }
        });
        this.jLabel2.setText("Nama Barang");
        this.namaBarangField.setEditable(false);
        this.jLabel3.setText("Sisa BS");
        this.sisaBsField.setBackground(new Color(153, 255, 204));
        this.sisaBsField.setEditable(false);
        this.jLabel4.setText("Jumlah Pengembalian");
        this.jumlahPengembalianField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PengembalianBsDialog.this.jumlahPengembalianFieldKeyPressed(evt);
            }
        });
        this.saveButton.setText("Save");
        this.saveButton.setPreferredSize(new Dimension(73, 23));
        this.saveButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PengembalianBsDialog.this.saveButtonActionPerformed(evt);
            }
        });
        this.saveButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PengembalianBsDialog.this.saveButtonKeyPressed(evt);
            }
        });
        this.cancelButton.setText("Cancel");
        this.cancelButton.setPreferredSize(new Dimension(73, 23));
        this.cancelButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PengembalianBsDialog.this.cancelButtonActionPerformed(evt);
            }
        });
        this.cancelButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PengembalianBsDialog.this.cancelButtonKeyPressed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(33, 33, 33).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jLabel1).addComponent(this.jLabel2, -1, -1, Short.MAX_VALUE).addComponent(this.jLabel3).addComponent(this.jLabel4)).addGap(58, 58, 58).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.kodeBarangField).addComponent(this.namaBarangField).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.jumlahPengembalianField, GroupLayout.Alignment.LEADING).addComponent(this.sisaBsField, GroupLayout.Alignment.LEADING, -2, 81, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.satuan1, -1, 52, Short.MAX_VALUE).addComponent(this.satuan2, -1, -1, Short.MAX_VALUE))))).addGroup(layout.createSequentialGroup().addGap(109, 109, 109).addComponent(this.saveButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.cancelButton, -2, -1, -2))).addContainerGap(49, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.kodeBarangField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.namaBarangField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.satuan1, -1, -1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.sisaBsField, -2, -1, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel4).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jumlahPengembalianField, -2, -1, -2).addComponent(this.satuan2, -2, 20, -2))).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.saveButton, -2, -1, -2).addComponent(this.cancelButton, -2, -1, -2)).addContainerGap()));
        this.pack();
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

    private void jumlahPengembalianFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (this.helper.isFloat(this.jumlahPengembalianField.getText())) {
                if (Float.parseFloat(this.jumlahPengembalianField.getText()) > this.barang.getStokBs()) {
                    this.jumlahPengembalianField.setBackground(Color.red);
                } else {
                    this.saveButton.requestFocus();
                    this.jumlahPengembalianField.setBackground(Color.white);
                }
            } else {
                this.jumlahPengembalianField.setBackground(Color.red);
            }
        } else if (evt.getKeyCode() == 38 || evt.getKeyCode() == 27) {
            this.escapeClick();
        }
    }

    private void saveClick() {
        Object[] rowValue = new Object[]{this.kodeBarangField.getText(), this.namaBarangField.getText(), this.jumlahPengembalianField.getText(), this.satuan1.getText()};
        this.listener.saveClick(rowValue);
        this.escapeClick();
    }

    private void saveButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.saveClick();
        } else if (evt.getKeyCode() == 38) {
            this.jumlahPengembalianField.requestFocus();
            this.jumlahPengembalianField.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        }
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

    private void saveButtonActionPerformed(ActionEvent evt) {
        this.saveClick();
    }

    private void cancelButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void searchTable(String idBarang) {
        for (int a = 0; a < this.pengembalianBsTableModel.getRowCount(); ++a) {
            String barangId = this.pengembalianBsTableModel.getValueAt(a, 0).toString();
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
            PengembalianBsDialog.this.loadKodeBarang(chooseValue);
            PengembalianBsDialog.this.searchTable(chooseValue);
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
            List<Barang> barangs = PengembalianBsDialog.this.barangService.getBarangs("", searchWord, false, "", true);
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

