/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.toedter.calendar.JDateChooser
 */
package minimart.ui.view.report;

import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import minimart.engine.component.UppercaseDocumentFilter;
import minimart.engine.model.Customer;
import minimart.engine.service.CustomerService;
import minimart.ui.listener.ReportPenjualanDialogListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class ReportPenjualanDialog
extends JDialog {
    private JLabel customerAlamatField;
    private JTextField customerIdField;
    private JLabel customerNameField;
    private JDateChooser endDateField;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JButton keluarButton;
    private JComboBox<String> pembayaranDialog;
    private JDateChooser startDateField;
    private JButton tampilkanButton;
    private ReportPenjualanDialogListener reportPenjualanDialogListener;
    private int screenWidth;
    private int screenHeight;
    private DocumentFilter filter = new UppercaseDocumentFilter();
    private CustomerService customerService = new CustomerService();

    public ReportPenjualanDialog(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        this.startDateField.setDate(new Date());
        this.endDateField.setDate(new Date());
        this.startDateField.setFocusCycleRoot(false);
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        ((AbstractDocument)this.customerIdField.getDocument()).setDocumentFilter(this.filter);
        this.setTitle("Report Penjualan");
    }

    public void addListener(ReportPenjualanDialogListener reportPenjualanDialogListener) {
        this.reportPenjualanDialogListener = reportPenjualanDialogListener;
    }

    private void loadKodeCustomer() {
        Customer customer = this.customerService.getCustomerById(this.customerIdField.getText());
        if (customer == null) {
            JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan");
        } else {
            this.customerNameField.setText(customer.getNama());
            this.customerAlamatField.setText(customer.getAlamat());
            this.tampilkanButton.requestFocus();
        }
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.startDateField = new JDateChooser();
        this.jLabel2 = new JLabel();
        this.endDateField = new JDateChooser();
        this.tampilkanButton = new JButton();
        this.keluarButton = new JButton();
        this.customerAlamatField = new JLabel();
        this.customerNameField = new JLabel();
        this.customerIdField = new JTextField();
        this.jLabel5 = new JLabel();
        this.jLabel3 = new JLabel();
        this.jLabel4 = new JLabel();
        this.pembayaranDialog = new JComboBox();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("Dari Tanggal");
        this.jLabel2.setText("Sampai Tanggal");
        this.tampilkanButton.setText("Tampilkan");
        this.tampilkanButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPenjualanDialog.this.tampilkanButtonActionPerformed(evt);
            }
        });
        this.tampilkanButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPenjualanDialog.this.tampilkanButtonKeyPressed(evt);
            }
        });
        this.keluarButton.setText("Keluar");
        this.keluarButton.setPreferredSize(new Dimension(79, 23));
        this.keluarButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPenjualanDialog.this.keluarButtonActionPerformed(evt);
            }
        });
        this.keluarButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPenjualanDialog.this.keluarButtonKeyPressed(evt);
            }
        });
        this.customerAlamatField.setText("-");
        this.customerNameField.setText("-");
        this.customerIdField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPenjualanDialog.this.customerIdFieldKeyPressed(evt);
            }
        });
        this.jLabel5.setText("Customer");
        this.jLabel3.setText("Kosong=Tampilkan Semua");
        this.jLabel4.setText("Pembayaran");
        this.pembayaranDialog.setModel(new DefaultComboBoxModel<String>(new String[]{"SEMUA", "CASH", "KREDIT", "TRANSFER", "DEBIT"}));
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(31, 31, 31).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(53, 53, 53).addComponent(this.tampilkanButton).addGap(18, 18, 18).addComponent(this.keluarButton, -2, -1, -2)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2, GroupLayout.Alignment.TRAILING).addComponent(this.jLabel1)).addComponent(this.jLabel5).addComponent(this.jLabel4)).addGap(11, 11, 11).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent((Component)this.endDateField, -1, 146, Short.MAX_VALUE).addComponent((Component)this.startDateField, -1, 146, Short.MAX_VALUE).addComponent(this.customerAlamatField, -1, -1, Short.MAX_VALUE).addComponent(this.customerNameField, -1, -1, Short.MAX_VALUE).addComponent(this.customerIdField).addComponent(this.pembayaranDialog, 0, -1, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel3))).addContainerGap(-1, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(13, 13, 13).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.customerIdField, -2, -1, -2).addComponent(this.jLabel5).addComponent(this.jLabel3)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.customerNameField).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.customerAlamatField).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4).addComponent(this.pembayaranDialog, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent((Component)this.startDateField, -2, -1, -2).addComponent(this.jLabel1)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent((Component)this.endDateField, -2, -1, -2).addComponent(this.jLabel2)).addGap(11, 11, 11).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.keluarButton, -2, -1, -2).addComponent(this.tampilkanButton)).addGap(39, 39, 39)));
        this.pack();
    }

    private void escapeClick() {
        this.dispose();
    }

    private void tampilkanButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 39) {
            this.keluarButton.requestFocus();
        } else if (evt.getKeyCode() == 10) {
            String keterangan = "";
            if (this.pembayaranDialog.getSelectedItem().equals("CASH")) {
                keterangan = "CASH";
            } else if (this.pembayaranDialog.getSelectedItem().equals("KREDIT")) {
                keterangan = "KREDIT";
            } else if (this.pembayaranDialog.getSelectedItem().equals("TRANSFER")) {
                keterangan = "TRANSFER";
            } else if (this.pembayaranDialog.getSelectedItem().toString().equals("DEBIT")) {
                keterangan = "DEBIT";
            }
            this.reportPenjualanDialogListener.tampilkanClick(this.startDateField.getDate(), this.endDateField.getDate(), this.customerIdField.getText(), keterangan);
            this.dispose();
        }
    }

    private void keluarButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 37) {
            this.keluarButton.requestFocus();
        } else if (evt.getKeyCode() == 10) {
            this.dispose();
        }
    }

    private void tampilkanButtonActionPerformed(ActionEvent evt) {
        String keterangan = "";
        if (this.pembayaranDialog.getSelectedItem().equals("CASH")) {
            keterangan = "CASH";
        } else if (this.pembayaranDialog.getSelectedItem().equals("KREDIT")) {
            keterangan = "KREDIT";
        } else if (this.pembayaranDialog.getSelectedItem().equals("TRANSFER")) {
            keterangan = "TRANSFER";
        } else if (this.pembayaranDialog.getSelectedItem().toString().equals("DEBIT")) {
            keterangan = "DEBIT";
        }
        System.out.println("keterangan=" + keterangan);
        this.reportPenjualanDialogListener.tampilkanClick(this.startDateField.getDate(), this.endDateField.getDate(), this.customerIdField.getText(), keterangan);
        this.dispose();
    }

    private void keluarButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void customerIdFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 113) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Customer");
            sd.addSearchDialogListener(new searchCustomerDialogEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
        } else if (evt.getKeyCode() == 10) {
            if (this.customerIdField.getText().equals("")) {
                this.tampilkanButton.requestFocus();
            } else {
                this.loadKodeCustomer();
            }
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private class searchPanelCustomerEventHandler
    implements SearchPanelListener {
        private searchPanelCustomerEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            ReportPenjualanDialog.this.customerIdField.setText(chooseValue);
            ReportPenjualanDialog.this.loadKodeCustomer();
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
            List<Customer> customers = ReportPenjualanDialog.this.customerService.getCustomers(searchWord);
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

