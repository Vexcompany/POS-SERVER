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
import java.util.Date;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import minimart.engine.component.UppercaseDocumentFilter;
import minimart.engine.controller.Helper;
import minimart.engine.model.Karyawan;
import minimart.engine.model.Supplier;
import minimart.engine.service.SupplierService;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class SupplierMaster
extends JDialog {
    private JTextField alamatField;
    private JLabel alamatLbl;
    private JTextField cpField;
    private JLabel cpLbl;
    private JButton dataBaruButton;
    private JButton deleteButton;
    private JButton exitButton;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JTextField kodeSupplierField;
    private JLabel kodeSupplierLbl;
    private JTextField kotaField;
    private JLabel kotaLbl;
    private JTextField lamaField;
    private JTextField namaSupplierField;
    private JLabel namaSupplierLbl;
    private JButton saveButton;
    private JTextField telpField;
    private JLabel telpLbl;
    private DocumentFilter filter = new UppercaseDocumentFilter();
    private SupplierService supplierService = new SupplierService();
    private String action = "update";
    private int screenWidth;
    private int screenHeight;
    private Supplier supplier;
    private Helper helper = Helper.getInstance();

    public SupplierMaster(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        this.initComponents2();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setTitle("Entry Tabel Supplier");
    }

    private void initComponents2() {
        ((AbstractDocument)this.kodeSupplierField.getDocument()).setDocumentFilter(this.filter);
        ((AbstractDocument)this.namaSupplierField.getDocument()).setDocumentFilter(this.filter);
        ((AbstractDocument)this.alamatField.getDocument()).setDocumentFilter(this.filter);
        ((AbstractDocument)this.kotaField.getDocument()).setDocumentFilter(this.filter);
        ((AbstractDocument)this.telpField.getDocument()).setDocumentFilter(this.filter);
        ((AbstractDocument)this.cpField.getDocument()).setDocumentFilter(this.filter);
        Karyawan karyawan = this.helper.getKaryawan();
        if (karyawan.getRole().equalsIgnoreCase("ADMIN")) {
            this.deleteButton.setEnabled(false);
        }
    }

    private void loadKodeSupplier() {
        this.action = "update";
        this.supplier = this.supplierService.getSupplier(this.kodeSupplierField.getText());
        if (this.supplier == null) {
            JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan");
        } else {
            this.namaSupplierField.setText(this.supplier.getNama());
            this.alamatField.setText(this.supplier.getAlamat());
            this.kotaField.setText(this.supplier.getKota());
            this.telpField.setText(this.supplier.getTelepon());
            this.cpField.setText(this.supplier.getContactPerson());
            this.namaSupplierField.requestFocus();
            this.lamaField.setText(this.supplier.getTop() + "");
        }
    }

    private void setDefault() {
        this.namaSupplierField.setText("");
        this.alamatField.setText("");
        this.kotaField.setText("");
        this.telpField.setText("");
        this.cpField.setText("");
        this.lamaField.setText("0");
    }

    private void escapeClick() {
        this.kodeSupplierField.requestFocus();
        this.kodeSupplierField.selectAll();
    }

    private void saveSupplier() {
        if (this.action.equals("create")) {
            this.supplier = new Supplier();
        }
        this.supplier.setId(this.kodeSupplierField.getText());
        this.supplier.setNama(this.namaSupplierField.getText());
        this.supplier.setAlamat(this.alamatField.getText());
        this.supplier.setKota(this.kotaField.getText());
        this.supplier.setTelepon(this.telpField.getText());
        this.supplier.setContactPerson(this.cpField.getText());
        this.supplier.setAvailable(true);
        this.supplier.setUpdated(new Date());
        this.supplier.setTop(Integer.parseInt(this.lamaField.getText().toString()));
        int result = 0;
        if (this.action.equals("create")) {
            this.supplier.setCreated(new Date());
            result = this.supplierService.createSupplier(this.supplier);
        } else if (this.action.equals("update")) {
            result = this.supplierService.updateSupplier(this.supplier);
        }
        if (result == 1) {
            JOptionPane.showMessageDialog(null, "Data Telah Tersimpan");
            this.escapeClick();
            this.setDefault();
            this.kodeSupplierField.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "Penyimpanan Data Gagal");
            this.escapeClick();
        }
    }

    private void deleteSupplier() {
        int confirm = JOptionPane.showConfirmDialog(null, "Data Akan Dihapus?", "Delete Data Confirmation", 2);
        if (confirm == 0) {
            int result = this.supplierService.deleteSupplier(this.kodeSupplierField.getText());
            if (result == 1) {
                JOptionPane.showMessageDialog(null, "Data Telah Terhapus");
                this.escapeClick();
                this.setDefault();
                this.kodeSupplierField.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Penghapusan Data Gagal");
                this.escapeClick();
            }
        }
    }

    private void newData() {
        this.action = "create";
        this.setDefault();
        this.kodeSupplierField.setText(Helper.getInstance().generateCodeGeneral(this.supplierService.getLastId()));
        this.namaSupplierField.requestFocus();
    }

    private void initComponents() {
        this.kodeSupplierLbl = new JLabel();
        this.namaSupplierLbl = new JLabel();
        this.alamatLbl = new JLabel();
        this.kotaLbl = new JLabel();
        this.telpLbl = new JLabel();
        this.kodeSupplierField = new JTextField();
        this.namaSupplierField = new JTextField();
        this.alamatField = new JTextField();
        this.kotaField = new JTextField();
        this.telpField = new JTextField();
        this.cpLbl = new JLabel();
        this.cpField = new JTextField();
        this.saveButton = new JButton();
        this.deleteButton = new JButton();
        this.exitButton = new JButton();
        this.dataBaruButton = new JButton();
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.lamaField = new JTextField();
        this.setDefaultCloseOperation(2);
        this.kodeSupplierLbl.setText("Kode Supplier");
        this.namaSupplierLbl.setText("Nama Supplier");
        this.alamatLbl.setText("Alamat");
        this.kotaLbl.setText("Kota");
        this.telpLbl.setText("No. Telp");
        this.kodeSupplierField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                SupplierMaster.this.kodeSupplierFieldKeyPressed(evt);
            }
        });
        this.namaSupplierField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                SupplierMaster.this.namaSupplierFieldKeyPressed(evt);
            }
        });
        this.alamatField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                SupplierMaster.this.alamatFieldKeyPressed(evt);
            }
        });
        this.kotaField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                SupplierMaster.this.kotaFieldKeyPressed(evt);
            }
        });
        this.telpField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                SupplierMaster.this.telpFieldKeyPressed(evt);
            }
        });
        this.cpLbl.setText("Cnt. Person");
        this.cpField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                SupplierMaster.this.cpFieldKeyPressed(evt);
            }
        });
        this.saveButton.setText("Save");
        this.saveButton.setPreferredSize(new Dimension(73, 23));
        this.saveButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                SupplierMaster.this.saveButtonActionPerformed(evt);
            }
        });
        this.saveButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                SupplierMaster.this.saveButtonKeyPressed(evt);
            }
        });
        this.deleteButton.setText("Delete");
        this.deleteButton.setPreferredSize(new Dimension(73, 23));
        this.deleteButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                SupplierMaster.this.deleteButtonActionPerformed(evt);
            }
        });
        this.deleteButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                SupplierMaster.this.deleteButtonKeyPressed(evt);
            }
        });
        this.exitButton.setText("Exit");
        this.exitButton.setPreferredSize(new Dimension(73, 23));
        this.exitButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                SupplierMaster.this.exitButtonActionPerformed(evt);
            }
        });
        this.exitButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                SupplierMaster.this.exitButtonKeyPressed(evt);
            }
        });
        this.dataBaruButton.setText("Data Baru");
        this.dataBaruButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                SupplierMaster.this.dataBaruButtonActionPerformed(evt);
            }
        });
        this.jLabel1.setText("F1");
        this.jLabel2.setText("Lama Kredit");
        this.jLabel3.setText("Hari");
        this.lamaField.setText("0");
        this.lamaField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                SupplierMaster.this.lamaFieldKeyPressed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(24, 24, 24).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.namaSupplierLbl).addComponent(this.kodeSupplierLbl).addComponent(this.alamatLbl).addComponent(this.kotaLbl).addComponent(this.telpLbl).addComponent(this.cpLbl).addComponent(this.jLabel2)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.cpField).addComponent(this.telpField).addComponent(this.kotaField).addComponent(this.alamatField).addComponent(this.namaSupplierField).addComponent(this.kodeSupplierField, -1, 206, Short.MAX_VALUE)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.dataBaruButton)).addGroup(layout.createSequentialGroup().addGap(39, 39, 39).addComponent(this.jLabel1)))).addGroup(layout.createSequentialGroup().addGap(1, 1, 1).addComponent(this.lamaField, -2, 40, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel3)))).addGroup(layout.createSequentialGroup().addGap(59, 59, 59).addComponent(this.saveButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.deleteButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.exitButton, -2, -1, -2))).addContainerGap(-1, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.kodeSupplierLbl).addComponent(this.kodeSupplierField, -2, -1, -2).addComponent(this.dataBaruButton)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.namaSupplierLbl).addComponent(this.namaSupplierField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.alamatLbl).addComponent(this.alamatField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.kotaLbl).addComponent(this.kotaField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.telpLbl).addComponent(this.telpField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.cpField, -2, -1, -2).addComponent(this.cpLbl)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.jLabel3).addComponent(this.lamaField, -2, -1, -2)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.saveButton, -2, -1, -2).addComponent(this.deleteButton, -2, -1, -2).addComponent(this.exitButton, -2, -1, -2))).addComponent(this.jLabel1)).addContainerGap(-1, Short.MAX_VALUE)));
        this.pack();
    }

    private void kodeSupplierFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 113) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Supplier");
            sd.addSearchDialogListener(new searchSupplierDialogEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
        } else if (evt.getKeyCode() == 10) {
            if (this.kodeSupplierField.getText().equals("")) {
                SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Supplier");
                sd.addSearchDialogListener(new searchSupplierDialogEventHandler());
                int w = sd.getWidth();
                int h = sd.getHeight();
                sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                sd.setVisible(true);
            } else {
                this.loadKodeSupplier();
            }
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        } else if (evt.getKeyCode() == 112) {
            this.newData();
        }
    }

    private void namaSupplierFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10) {
            this.alamatField.requestFocus();
            this.alamatField.selectAll();
        } else if (evt.getKeyCode() == 38) {
            this.kodeSupplierField.requestFocus();
            this.kodeSupplierField.selectAll();
        }
    }

    private void alamatFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10) {
            this.kotaField.requestFocus();
            this.kotaField.selectAll();
        } else if (evt.getKeyCode() == 38) {
            this.namaSupplierField.requestFocus();
            this.namaSupplierField.selectAll();
        }
    }

    private void kotaFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10) {
            this.telpField.requestFocus();
            this.telpField.selectAll();
        } else if (evt.getKeyCode() == 38) {
            this.alamatField.requestFocus();
            this.alamatField.selectAll();
        }
    }

    private void telpFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10) {
            this.cpField.requestFocus();
            this.cpField.selectAll();
        } else if (evt.getKeyCode() == 38) {
            this.kotaField.requestFocus();
            this.kotaField.selectAll();
        }
    }

    private void dataBaruButtonActionPerformed(ActionEvent evt) {
        this.newData();
    }

    private void saveButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10) {
            this.saveSupplier();
        } else if (evt.getKeyCode() == 39) {
            this.deleteButton.requestFocus();
        } else if (evt.getKeyCode() == 38) {
            this.cpField.requestFocus();
            this.cpField.selectAll();
        }
    }

    private void saveButtonActionPerformed(ActionEvent evt) {
        this.saveSupplier();
    }

    private void deleteButtonActionPerformed(ActionEvent evt) {
        this.deleteSupplier();
    }

    private void deleteButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        }
        if (evt.getKeyCode() == 10) {
            this.deleteSupplier();
        } else if (evt.getKeyCode() == 39) {
            this.exitButton.requestFocus();
        } else if (evt.getKeyCode() == 37) {
            this.saveButton.requestFocus();
        }
    }

    private void exitButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void exitButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10) {
            this.dispose();
        } else if (evt.getKeyCode() == 37) {
            this.deleteButton.requestFocus();
        }
    }

    private void cpFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10) {
            this.lamaField.requestFocus();
            this.lamaField.setText(this.lamaField.getText().replace(".", ""));
            this.lamaField.selectAll();
        } else if (evt.getKeyCode() == 38) {
            this.telpField.requestFocus();
            this.telpField.selectAll();
        }
    }

    private void lamaFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10) {
            this.saveButton.requestFocus();
        } else if (evt.getKeyCode() == 38) {
            this.cpField.requestFocus();
            this.cpField.selectAll();
        }
    }

    private class searchPanelSupplierEventHandler
    implements SearchPanelListener {
        private searchPanelSupplierEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            SupplierMaster.this.kodeSupplierField.setText(chooseValue);
            SupplierMaster.this.loadKodeSupplier();
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
            List<Supplier> suppliers = SupplierMaster.this.supplierService.getSuppliers(searchWord);
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
}

