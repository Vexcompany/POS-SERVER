/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import minimart.engine.model.Customer;
import minimart.engine.service.CustomerService;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class SmsForm
extends JPanel {
    private JTable customerTable;
    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JButton jButton4;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JTextArea jTextArea1;
    private JTextField jTextField1;
    private String[] customerColumn = new String[]{"ID Customer", "Nama Customer", "Alamat", "Nomor Telp"};
    private DefaultPanelListener defaultPanelListener;
    private int screenWidth;
    private int screenHeight;
    private CustomerService customerService = new CustomerService();
    private Customer customer = new Customer();
    private DefaultTableModel customerTableModel = new DefaultTableModel(new String[0][1], this.customerColumn);

    public SmsForm() {
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jTextField1 = new JTextField();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.jScrollPane2 = new JScrollPane();
        this.jTextArea1 = new JTextArea();
        this.jButton1 = new JButton();
        this.jButton2 = new JButton();
        this.jButton3 = new JButton();
        this.jButton4 = new JButton();
        this.jScrollPane3 = new JScrollPane();
        this.customerTable = new JTable();
        this.setMaximumSize(new Dimension(800, 600));
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setText("Pencarian Customer");
        this.jTextField1.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                SmsForm.this.jTextField1KeyPressed(evt);
            }
        });
        this.jLabel2.setText("Penerima Pesan");
        this.jLabel3.setText("Pesan");
        this.jTextArea1.setColumns(20);
        this.jTextArea1.setRows(5);
        this.jScrollPane2.setViewportView(this.jTextArea1);
        this.jButton1.setText("Kirim");
        this.jButton1.setPreferredSize(new Dimension(72, 23));
        this.jButton2.setText("Keluar");
        this.jButton2.setPreferredSize(new Dimension(72, 23));
        this.jButton2.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                SmsForm.this.jButton2ActionPerformed(evt);
            }
        });
        this.jButton3.setText("Hapus");
        this.jButton4.setText("Hapus Semua");
        this.customerTable.setModel(this.customerTableModel);
        this.jScrollPane3.setViewportView(this.customerTable);
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jLabel2).addComponent(this.jLabel3)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jButton1, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jButton2, -2, -1, -2)).addComponent(this.jTextField1, -2, 254, -2).addGroup(layout.createSequentialGroup().addComponent(this.jScrollPane3, -2, 546, -2).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jButton4, -1, -1, Short.MAX_VALUE).addComponent(this.jButton3, -1, -1, Short.MAX_VALUE))).addComponent(this.jScrollPane2, -2, 465, -2)).addContainerGap(15, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.jTextField1, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2).addGroup(layout.createSequentialGroup().addComponent(this.jButton3).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jButton4)).addComponent(this.jScrollPane3, -2, 284, -2)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.jLabel3).addGap(242, 242, 242)).addGroup(layout.createSequentialGroup().addGap(13, 13, 13).addComponent(this.jScrollPane2, -2, 200, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jButton1, -2, -1, -2).addComponent(this.jButton2, -2, -1, -2)).addContainerGap(37, Short.MAX_VALUE)))));
    }

    private void jTextField1KeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 113) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Customer");
            sd.addSearchDialogListener(new searchCustomerDialogEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
        }
    }

    private void jButton2ActionPerformed(ActionEvent evt) {
        this.defaultPanelListener.exitClick();
    }

    public void addListener(DefaultPanelListener listener) {
        this.defaultPanelListener = listener;
    }

    private class searchPanelCustomerEventHandler
    implements SearchPanelListener {
        private searchPanelCustomerEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            SmsForm.this.customer = SmsForm.this.customerService.getCustomerById(chooseValue);
            Object[] data = new Object[]{SmsForm.this.customer.getId(), SmsForm.this.customer.getNama(), SmsForm.this.customer.getAlamat(), SmsForm.this.customer.getTelepon()};
            SmsForm.this.customerTableModel.addRow(data);
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
            List<Customer> customers = SmsForm.this.customerService.getCustomers(searchWord);
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

