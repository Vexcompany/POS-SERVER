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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import minimart.engine.component.UppercaseDocumentFilter;
import minimart.engine.model.Jenis;
import minimart.engine.model.Supplier;
import minimart.engine.service.JenisService;
import minimart.engine.service.SupplierService;
import minimart.ui.listener.ReportMasterBarangDialogListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class ReportMasterBarangDialog
extends JDialog {
    private JLabel alamatSupField;
    private JButton cancelButton;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JRadioButton jRadioButton1;
    private JRadioButton jRadioButton2;
    private JSeparator jSeparator1;
    private JTextField jenisBarangField;
    private JComboBox lokasiCmb;
    private JLabel namaSupField;
    private JTextField rakField;
    private JTextField supplierIdField;
    private JButton tampilkanButton;
    private JenisService jenisService = new JenisService();
    private DocumentFilter filter = new UppercaseDocumentFilter();
    private Jenis jenis;
    private int screenHeight;
    private int screenWidth;
    private ReportMasterBarangDialogListener reportMasterBarangDialogListener;
    private SupplierService supplierService = new SupplierService();
    private ButtonGroup bg = new ButtonGroup();

    public ReportMasterBarangDialog(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        ((AbstractDocument)this.jenisBarangField.getDocument()).setDocumentFilter(this.filter);
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setTitle("Report Master Barang");
        this.bg.add(this.jRadioButton1);
        this.bg.add(this.jRadioButton2);
        this.jRadioButton1.setSelected(true);
        this.rButton1Klik();
    }

    private void rButton1Klik() {
        this.lokasiCmb.setEnabled(false);
        this.supplierIdField.setEnabled(false);
        this.jenisBarangField.setEnabled(true);
        this.rakField.setEnabled(true);
        this.jenisBarangField.requestFocus();
        this.jenisBarangField.selectAll();
    }

    private void rButton2Klik() {
        this.jenisBarangField.setEnabled(false);
        this.rakField.setEnabled(false);
        this.lokasiCmb.setEnabled(true);
        this.supplierIdField.setEnabled(true);
        this.supplierIdField.requestFocus();
        this.supplierIdField.selectAll();
    }

    public void addListener(ReportMasterBarangDialogListener listener) {
        this.reportMasterBarangDialogListener = listener;
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jenisBarangField = new JTextField();
        this.cancelButton = new JButton();
        this.tampilkanButton = new JButton();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.rakField = new JTextField();
        this.jLabel4 = new JLabel();
        this.supplierIdField = new JTextField();
        this.namaSupField = new JLabel();
        this.alamatSupField = new JLabel();
        this.jLabel5 = new JLabel();
        this.jRadioButton1 = new JRadioButton();
        this.jRadioButton2 = new JRadioButton();
        this.jSeparator1 = new JSeparator();
        this.jLabel6 = new JLabel();
        this.lokasiCmb = new JComboBox();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("Jenis Barang");
        this.jenisBarangField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportMasterBarangDialog.this.jenisBarangFieldKeyPressed(evt);
            }
        });
        this.cancelButton.setText("Cancel");
        this.cancelButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportMasterBarangDialog.this.cancelButtonActionPerformed(evt);
            }
        });
        this.cancelButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportMasterBarangDialog.this.cancelButtonKeyPressed(evt);
            }
        });
        this.tampilkanButton.setText("Tampilkan");
        this.tampilkanButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportMasterBarangDialog.this.tampilkanButtonActionPerformed(evt);
            }
        });
        this.tampilkanButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportMasterBarangDialog.this.tampilkanButtonKeyPressed(evt);
            }
        });
        this.jLabel2.setText("Kosong=semua");
        this.jLabel3.setText("Rak");
        this.rakField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportMasterBarangDialog.this.rakFieldKeyPressed(evt);
            }
        });
        this.jLabel4.setText("Supplier");
        this.supplierIdField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportMasterBarangDialog.this.supplierIdFieldKeyPressed(evt);
            }
        });
        this.namaSupField.setText("-");
        this.alamatSupField.setText("-");
        this.jLabel5.setText("F2=cari");
        this.jRadioButton1.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
                ReportMasterBarangDialog.this.jRadioButton1MouseClicked(evt);
            }
        });
        this.jRadioButton2.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
                ReportMasterBarangDialog.this.jRadioButton2MouseClicked(evt);
            }
        });
        this.jLabel6.setText("Stok");
        this.lokasiCmb.setModel(new DefaultComboBoxModel<String>(new String[]{"Toko", "Gudang"}));
        this.lokasiCmb.setEnabled(false);
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.jSeparator1).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jRadioButton2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel4).addComponent(this.jLabel6)).addGap(32, 32, 32).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.supplierIdField, -2, 180, -2).addComponent(this.lokasiCmb, -2, 198, -2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addComponent(this.tampilkanButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cancelButton)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.namaSupField, -2, 180, -2).addComponent(this.alamatSupField, -2, 180, -2))))).addGroup(layout.createSequentialGroup().addComponent(this.jRadioButton1).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addComponent(this.jLabel3).addGap(53, 53, 53))).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.rakField, -2, 95, -2).addComponent(this.jenisBarangField, -2, 180, -2)).addGap(41, 41, 41).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jLabel2, -1, -1, Short.MAX_VALUE).addComponent(this.jLabel5, -2, 74, -2))))).addContainerGap(44, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jRadioButton1).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.jenisBarangField, -2, -1, -2).addComponent(this.jLabel2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel5).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.rakField, -2, -1, -2))))).addGap(5, 5, 5).addComponent(this.jSeparator1, -2, 10, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.supplierIdField, -2, -1, -2).addComponent(this.jLabel4)).addComponent(this.jRadioButton2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.namaSupField).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.alamatSupField).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.lokasiCmb, -2, -1, -2).addComponent(this.jLabel6)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.cancelButton).addComponent(this.tampilkanButton)).addContainerGap()));
        this.pack();
    }

    private void jenisBarangFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.dispose();
        } else if (evt.getKeyCode() == 113) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Jenis Barang");
            sd.addSearchDialogListener(new searchJenisDialogEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
        } else if (evt.getKeyCode() == 10) {
            if (this.jenisBarangField.getText().equals("")) {
                this.rakField.requestFocus();
            } else {
                Jenis jenisTemp = this.jenisService.getJenis(this.jenisBarangField.getText());
                if (jenisTemp == null) {
                    JOptionPane.showMessageDialog(null, "Jenis Barang Tidak Ditemukan");
                } else {
                    this.rakField.requestFocus();
                }
            }
        }
    }

    private void tampilkanButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27 || evt.getKeyCode() == 38 || evt.getKeyCode() == 37) {
            this.supplierIdField.requestFocus();
            this.supplierIdField.selectAll();
        } else if (evt.getKeyCode() == 39) {
            this.cancelButton.requestFocus();
        } else if (evt.getKeyCode() == 10) {
            this.dispose();
            if (this.jRadioButton1.isSelected()) {
                this.reportMasterBarangDialogListener.tampilkanClick(this.jenisBarangField.getText(), this.rakField.getText());
            } else if (this.jRadioButton2.isSelected()) {
                this.reportMasterBarangDialogListener.tampilKanClickBySupplier(this.lokasiCmb.getSelectedItem().toString(), this.supplierIdField.getText());
            }
        }
    }

    private void cancelButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.jenisBarangField.requestFocus();
            this.jenisBarangField.selectAll();
        } else if (evt.getKeyCode() == 38 || evt.getKeyCode() == 37) {
            this.tampilkanButton.requestFocus();
        } else if (evt.getKeyCode() == 39) {
            this.cancelButton.requestFocus();
        } else if (evt.getKeyCode() == 10) {
            this.dispose();
        }
    }

    private void tampilkanButtonActionPerformed(ActionEvent evt) {
        this.dispose();
        if (this.jRadioButton1.isSelected()) {
            this.reportMasterBarangDialogListener.tampilkanClick(this.jenisBarangField.getText(), this.rakField.getText());
        } else if (this.jRadioButton2.isSelected()) {
            this.reportMasterBarangDialogListener.tampilKanClickBySupplier(this.lokasiCmb.getSelectedItem().toString(), this.supplierIdField.getText());
        }
    }

    private void cancelButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void rakFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.tampilkanButton.requestFocus();
        } else if (evt.getKeyCode() == 38 || evt.getKeyCode() == 27) {
            this.jenisBarangField.requestFocus();
        }
    }

    private void supplierIdFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 113) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Supplier");
            sd.addSearchDialogListener(new searchSupplierDialogEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
        } else if (evt.getKeyCode() == 10) {
            if (this.supplierIdField.getText().equals("")) {
                this.tampilkanButton.requestFocus();
            } else {
                Supplier supplier = this.supplierService.getSupplier(this.supplierIdField.getText());
                if (supplier == null) {
                    JOptionPane.showMessageDialog(null, "Jenis Barang Tidak Ditemukan");
                } else {
                    this.namaSupField.setText(supplier.getNama());
                    this.alamatSupField.setText(supplier.getAlamat());
                    this.tampilkanButton.requestFocus();
                }
            }
        } else if (evt.getKeyCode() == 38 || evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void jRadioButton1MouseClicked(MouseEvent evt) {
        this.rButton1Klik();
    }

    private void jRadioButton2MouseClicked(MouseEvent evt) {
        this.rButton2Klik();
    }

    private class searchPanelSupplierEventHandler
    implements SearchPanelListener {
        private searchPanelSupplierEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            ReportMasterBarangDialog.this.supplierIdField.setText(chooseValue);
            Supplier supplier = ReportMasterBarangDialog.this.supplierService.getSupplier(chooseValue);
            ReportMasterBarangDialog.this.namaSupField.setText(supplier.getNama());
            ReportMasterBarangDialog.this.alamatSupField.setText(supplier.getAlamat());
            ReportMasterBarangDialog.this.tampilkanButton.requestFocus();
        }
    }

    class searchSupplierDialogEventHandler
    implements SearchDialogListener {
        searchSupplierDialogEventHandler() {
        }

        @Override
        public void searchClick(String searchWord) {
            Object[] colName = new String[]{"KODE SUPPLIER", "NAMA SUPPLIER", "ALAMAT", "KOTA"};
            DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
            List<Supplier> suppliers = ReportMasterBarangDialog.this.supplierService.getSuppliers(searchWord);
            for (Supplier supplier : suppliers) {
                Object[] temp = new String[]{supplier.getId(), supplier.getNama(), supplier.getAlamat(), supplier.getKota()};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Supplier");
            sp.addSearchPanelListener(new searchPanelSupplierEventHandler());
            sp.setVisible(true);
        }

        public void dispose() {
        }
    }

    private class searchPanelJenisEventHandler
    implements SearchPanelListener {
        private searchPanelJenisEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            ReportMasterBarangDialog.this.jenis = ReportMasterBarangDialog.this.jenisService.getJenis(chooseValue);
            ReportMasterBarangDialog.this.jenisBarangField.setText(chooseValue);
            ReportMasterBarangDialog.this.rakField.requestFocus();
        }
    }

    private class searchJenisDialogEventHandler
    implements SearchDialogListener {
        private searchJenisDialogEventHandler() {
        }

        @Override
        public void searchClick(String searchWord) {
            Object[] colName = new String[]{"JENIS"};
            DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
            List<Jenis> jenisses = ReportMasterBarangDialog.this.jenisService.getJenisses(searchWord);
            for (Jenis jenis : jenisses) {
                Object[] temp = new String[]{jenis.getJenis()};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Jenis Barang");
            sp.addSearchPanelListener(new searchPanelJenisEventHandler());
            sp.setVisible(true);
        }

        public void dispose() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}

