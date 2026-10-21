/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

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
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import minimart.engine.model.Barang;
import minimart.engine.service.BarangService;
import minimart.ui.listener.DaftarVoucherDialogListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class DaftarVoucherDialog
extends JDialog {
    private JButton cancelBtn;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel5;
    private JTextField kodeField;
    private JTextField maxField;
    private JTextField namaField;
    private JLabel satuanLbl;
    private JButton saveBtn;
    private int screenHeight;
    private int screenWidth;
    private BarangService barangService = new BarangService();
    private Barang barang;
    private DefaultTableModel daftarTableModel;
    private SearchDialog sd;
    private DaftarVoucherDialogListener listener;

    public DaftarVoucherDialog(Frame parent, boolean modal, DefaultTableModel daftarTableModel) {
        super(parent, modal);
        this.initComponents();
        this.daftarTableModel = daftarTableModel;
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.kodeField = new JTextField();
        this.namaField = new JTextField();
        this.satuanLbl = new JLabel();
        this.maxField = new JTextField();
        this.saveBtn = new JButton();
        this.cancelBtn = new JButton();
        this.jLabel5 = new JLabel();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("Kode Barang");
        this.jLabel2.setText("Nama Barang");
        this.jLabel3.setText("Maksimal Pembelian");
        this.kodeField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                DaftarVoucherDialog.this.kodeFieldKeyPressed(evt);
            }
        });
        this.namaField.setEditable(false);
        this.satuanLbl.setText("-");
        this.maxField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                DaftarVoucherDialog.this.maxFieldKeyPressed(evt);
            }
        });
        this.saveBtn.setText("Save");
        this.saveBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                DaftarVoucherDialog.this.saveBtnActionPerformed(evt);
            }
        });
        this.saveBtn.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                DaftarVoucherDialog.this.saveBtnKeyPressed(evt);
            }
        });
        this.cancelBtn.setText("Cancel");
        this.cancelBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                DaftarVoucherDialog.this.cancelBtnActionPerformed(evt);
            }
        });
        this.cancelBtn.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                DaftarVoucherDialog.this.cancelBtnKeyPressed(evt);
            }
        });
        this.jLabel5.setText("*Isi 0 (nol) jika tidak ada maksimal pembelian");
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.saveBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cancelBtn)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jLabel2).addComponent(this.jLabel3)).addGap(22, 22, 22).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel5).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addComponent(this.maxField, -2, 87, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.satuanLbl, -2, 69, -2)).addComponent(this.namaField, -1, 206, Short.MAX_VALUE).addComponent(this.kodeField))))).addContainerGap(-1, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.kodeField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.namaField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.satuanLbl).addComponent(this.maxField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel5).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.saveBtn).addComponent(this.cancelBtn)).addContainerGap()));
        this.pack();
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
                    this.searchTable(this.barang.getId());
                }
                this.maxField.requestFocus();
                this.maxField.selectAll();
            }
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void maxFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.saveBtn.requestFocus();
        } else if (evt.getKeyCode() == 38) {
            this.kodeField.requestFocus();
            this.kodeField.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        }
    }

    private void cancelBtnKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.dispose();
        } else if (evt.getKeyCode() == 37) {
            this.saveBtn.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        }
    }

    private void saveBtnKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.saveClick();
        } else if (evt.getKeyCode() == 39) {
            this.cancelBtn.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 38) {
            this.maxField.requestFocus();
            this.maxField.selectAll();
        }
    }

    private void saveBtnActionPerformed(ActionEvent evt) {
        this.saveClick();
    }

    private void cancelBtnActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void saveClick() {
        Object[] rowValue = new Object[9];
        rowValue[0] = this.barang.getId();
        rowValue[1] = this.barang.getNama();
        rowValue[2] = this.maxField.getText();
        rowValue[3] = this.barang.getSatuan1();
        this.listener.saveClick(rowValue);
        this.escapeClick();
    }

    public void addListener(DaftarVoucherDialogListener daftarVoucherDialogListener) {
        this.listener = daftarVoucherDialogListener;
    }

    protected void loadPenjualan(int row) {
        this.maxField.setText(this.daftarTableModel.getValueAt(row, 2).toString());
    }

    protected void escapeClick() {
        this.kodeField.requestFocus();
        this.kodeField.selectAll();
    }

    private void searchTable(String idBarang) {
        boolean find = false;
        for (int a = 0; a < this.daftarTableModel.getRowCount(); ++a) {
            String barangId = this.daftarTableModel.getValueAt(a, 0).toString();
            if (!idBarang.equals(barangId)) continue;
            find = true;
            this.loadPenjualan(a);
            break;
        }
        if (!find) {
            this.maxField.requestFocus();
            this.maxField.selectAll();
        }
    }

    private void setDefaultData() {
        this.kodeField.setText("");
        this.namaField.setText("");
        this.maxField.setText("0");
    }

    protected void loadKodeBarang(String kodeBarang) {
        this.setDefaultData();
        this.barang = this.barangService.getBarang(kodeBarang);
        this.kodeField.setText(kodeBarang);
        this.namaField.setText(this.barang.getNama());
        this.satuanLbl.setText(this.barang.getSatuan1());
    }

    private class searchPanelBarangEventHandler
    implements SearchPanelListener {
        private searchPanelBarangEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            DaftarVoucherDialog.this.loadKodeBarang(chooseValue);
            DaftarVoucherDialog.this.searchTable(chooseValue);
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
            List<Barang> barangs = DaftarVoucherDialog.this.barangService.getBarangs("", searchWord, false, "", true);
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

