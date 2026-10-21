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
import java.util.Date;
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
import javax.swing.text.AbstractDocument;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.DocumentFilter;
import javax.swing.text.NumberFormatter;
import minimart.engine.component.UppercaseDocumentFilter;
import minimart.engine.controller.Helper;
import minimart.engine.model.Customer;
import minimart.engine.model.Piutang;
import minimart.engine.service.CustomerService;
import minimart.engine.service.PiutangService;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class PiutangDialog
extends JDialog {
    private JButton cancelButton;
    private JLabel custAlamatLbl;
    private JLabel custNameLbl;
    private JTextField customerField;
    private JTextField idField;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JFormattedTextField jumlahField;
    private JTextField keteranganField;
    private JButton saveButton;
    private int screenHeight;
    private int screenWidth;
    private CustomerService customerService = new CustomerService();
    private Customer customer;
    private Helper helper = Helper.getInstance();
    private PiutangService piutangService = new PiutangService();
    private DocumentFilter filter = new UppercaseDocumentFilter();

    public PiutangDialog(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        ((AbstractDocument)this.keteranganField.getDocument()).setDocumentFilter(this.filter);
        this.setTitle("Entry Piutang");
        this.setDefault();
    }

    private void setDefault() {
        this.idField.setText("");
        this.customerField.setText("");
        this.custAlamatLbl.setText("-");
        this.custNameLbl.setText("-");
        this.keteranganField.setText("");
        this.jumlahField.setValue(0);
        this.idField.requestFocus();
    }

    private void saveClick() {
        Piutang piutang = new Piutang();
        piutang.setTanggal(new Date());
        piutang.setId(this.helper.generateCodeGeneral(this.piutangService.getLastId()));
        piutang.setKaryawanId(this.helper.getKaryawan().getId());
        piutang.setCustomerId(this.customer.getId());
        piutang.setTipe("PIUTANG");
        piutang.setJumlah((Long)this.jumlahField.getValue());
        piutang.setKeterangan(this.keteranganField.getText());
        piutang.setCreated(new Date());
        piutang.setUpdated(new Date());
        int result = this.piutangService.createPiutang(piutang);
        if (result == 1) {
            this.customer.setHutang(this.customer.getHutang() + (Long)this.jumlahField.getValue());
            int update = this.customerService.updateCustomer(this.customer);
            JOptionPane.showMessageDialog(null, "Penyimpanan Data Berhasil");
            this.setDefault();
        } else if (result == 0) {
            JOptionPane.showMessageDialog(null, "Penyimpanan Data Gagal");
        }
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.idField = new JTextField();
        this.customerField = new JTextField();
        this.jumlahField = new JFormattedTextField();
        this.custNameLbl = new JLabel();
        this.saveButton = new JButton();
        this.cancelButton = new JButton();
        this.custAlamatLbl = new JLabel();
        this.jLabel4 = new JLabel();
        this.keteranganField = new JTextField();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("ID Piutang");
        this.jLabel2.setText("Customer");
        this.jLabel3.setText("Jumlah Hutang");
        this.idField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PiutangDialog.this.idFieldKeyPressed(evt);
            }
        });
        this.customerField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PiutangDialog.this.customerFieldKeyPressed(evt);
            }
        });
        this.jumlahField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));
        this.jumlahField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PiutangDialog.this.jumlahFieldKeyPressed(evt);
            }
        });
        this.custNameLbl.setText("-");
        this.saveButton.setText("Save");
        this.saveButton.setPreferredSize(new Dimension(73, 23));
        this.saveButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PiutangDialog.this.saveButtonActionPerformed(evt);
            }
        });
        this.saveButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PiutangDialog.this.saveButtonKeyPressed(evt);
            }
        });
        this.cancelButton.setText("Cancel");
        this.cancelButton.setPreferredSize(new Dimension(73, 23));
        this.cancelButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PiutangDialog.this.cancelButtonActionPerformed(evt);
            }
        });
        this.cancelButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PiutangDialog.this.cancelButtonKeyPressed(evt);
            }
        });
        this.custAlamatLbl.setText("-");
        this.jLabel4.setText("Keterangan");
        this.keteranganField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PiutangDialog.this.keteranganFieldKeyPressed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(28, 28, 28).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jLabel2).addComponent(this.jLabel3).addComponent(this.jLabel4)).addGap(23, 23, 23).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(10, 10, 10).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.custNameLbl, -1, -1, Short.MAX_VALUE).addComponent(this.custAlamatLbl, -2, 244, -2))).addComponent(this.customerField, -2, 101, -2).addComponent(this.idField, -2, 152, -2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.keteranganField, GroupLayout.Alignment.LEADING, -2, 208, -2).addGroup(layout.createSequentialGroup().addComponent(this.saveButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cancelButton, -2, -1, -2)).addComponent(this.jumlahField, GroupLayout.Alignment.LEADING, -2, 150, -2))).addContainerGap(11, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.idField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.customerField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.custNameLbl).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.custAlamatLbl).addGap(8, 8, 8).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.jumlahField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4).addComponent(this.keteranganField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.saveButton, -2, -1, -2).addComponent(this.cancelButton, -2, -1, -2)).addContainerGap()));
        this.pack();
    }

    private void idFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (this.idField.getText().equals("")) {
                this.idField.setText(this.helper.generateCodeGeneral(this.piutangService.getLastId()));
                this.customerField.requestFocus();
                this.customerField.selectAll();
            } else {
                this.customerField.requestFocus();
                this.customerField.selectAll();
            }
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void customerFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (this.customerField.getText().equals("")) {
                SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Customer");
                sd.addSearchDialogListener(new searchCustomerDialogEventHandler());
                int w = sd.getWidth();
                int h = sd.getHeight();
                sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                sd.setVisible(true);
            } else {
                this.loadKodeCustomer();
            }
        } else if (evt.getKeyCode() == 38) {
            this.idField.requestFocus();
            this.idField.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.idField.requestFocus();
            this.idField.selectAll();
        } else if (evt.getKeyCode() == 113) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Customer");
            sd.addSearchDialogListener(new searchCustomerDialogEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
        }
    }

    private void jumlahFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (this.helper.isInteger(this.jumlahField.getText())) {
                this.jumlahField.setBackground(Color.white);
                this.keteranganField.requestFocus();
                this.keteranganField.selectAll();
            } else {
                this.jumlahField.setBackground(Color.red);
            }
        } else if (evt.getKeyCode() == 38) {
            this.customerField.requestFocus();
            this.customerField.selectAll();
            this.jumlahField.setBackground(Color.white);
        } else if (evt.getKeyCode() == 27) {
            this.idField.requestFocus();
            this.idField.selectAll();
        }
    }

    private void saveButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.saveClick();
        } else if (evt.getKeyCode() == 38 || evt.getKeyCode() == 37) {
            this.keteranganField.requestFocus();
            this.keteranganField.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.idField.requestFocus();
            this.idField.selectAll();
        } else if (evt.getKeyCode() == 39) {
            this.cancelButton.requestFocus();
        }
    }

    private void cancelButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.dispose();
        } else if (evt.getKeyCode() == 38 || evt.getKeyCode() == 37) {
            this.saveButton.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.idField.requestFocus();
            this.idField.selectAll();
        }
    }

    private void keteranganFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.saveButton.requestFocus();
        } else if (evt.getKeyCode() == 38 || evt.getKeyCode() == 37) {
            this.jumlahField.requestFocus();
            this.jumlahField.setText(this.jumlahField.getText().replace(".", ""));
            this.jumlahField.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.idField.requestFocus();
        }
    }

    private void cancelButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void saveButtonActionPerformed(ActionEvent evt) {
        this.saveClick();
    }

    private void loadKodeCustomer() {
        this.customer = this.customerService.getCustomerById(this.customerField.getText());
        if (this.customer == null) {
            JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan");
        } else {
            this.custNameLbl.setText(this.customer.getNama());
            this.custAlamatLbl.setText(this.customer.getAlamat());
            this.jumlahField.requestFocus();
            this.jumlahField.setText(this.jumlahField.getText().replace(".", ""));
            this.jumlahField.selectAll();
        }
    }

    private class searchPanelCustomerEventHandler
    implements SearchPanelListener {
        private searchPanelCustomerEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            PiutangDialog.this.customerField.setText(chooseValue);
            PiutangDialog.this.loadKodeCustomer();
        }
    }

    private class searchCustomerDialogEventHandler
    implements SearchDialogListener {
        private searchCustomerDialogEventHandler() {
        }

        @Override
        public void searchClick(String searchWord) {
            Object[] colName = new String[]{"KODE CUSTOMER", "NAMA CUSTOMER", "ALAMAT", "KOTA"};
            DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
            List<Customer> customers = PiutangDialog.this.customerService.getCustomers(searchWord);
            for (Customer customer : customers) {
                Object[] temp = new String[]{customer.getId(), customer.getNama(), customer.getAlamat(), customer.getKota()};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Customer");
            sp.addSearchPanelListener(new searchPanelCustomerEventHandler());
            sp.setVisible(true);
        }

        public void dispose() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}

