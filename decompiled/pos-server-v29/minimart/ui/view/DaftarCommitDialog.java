/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import minimart.engine.model.CustomerCommit;
import minimart.engine.model.CustomerPointCommit;
import minimart.engine.service.CustomerCommitService;
import minimart.engine.service.CustomerPointCommitService;

public class DaftarCommitDialog
extends JDialog {
    private JTable customerTable;
    private JButton exitButton;
    private JLabel jLabel1;
    private JRadioButtonMenuItem jRadioButtonMenuItem1;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JScrollPane jScrollPane4;
    private JTabbedPane jTabbedPane1;
    private JTable jTable3;
    private JTable pointTable;
    private JButton syncButton;
    private CustomerCommitService customerCommitService = new CustomerCommitService();
    private CustomerPointCommitService customerPointCommitService = new CustomerPointCommitService();

    public DaftarCommitDialog(Frame parent, boolean modal) {
        super(parent, modal);
        this.setTitle("Daftar Commit");
        this.initComponents();
        this.fetchCustomerCommits();
        this.fetchPointCommits();
    }

    private void initComponents() {
        this.jRadioButtonMenuItem1 = new JRadioButtonMenuItem();
        this.jLabel1 = new JLabel();
        this.jTabbedPane1 = new JTabbedPane();
        this.jScrollPane1 = new JScrollPane();
        this.jScrollPane2 = new JScrollPane();
        this.customerTable = new JTable();
        this.jScrollPane3 = new JScrollPane();
        this.pointTable = new JTable();
        this.jScrollPane4 = new JScrollPane();
        this.jTable3 = new JTable();
        this.syncButton = new JButton();
        this.exitButton = new JButton();
        this.jRadioButtonMenuItem1.setSelected(true);
        this.jRadioButtonMenuItem1.setText("jRadioButtonMenuItem1");
        this.setDefaultCloseOperation(2);
        this.jLabel1.setFont(new Font("Ubuntu", 0, 48));
        this.jLabel1.setText("Daftar Commit");
        this.customerTable.setModel(new DefaultTableModel(new Object[][]{new Object[0], new Object[0], new Object[0], new Object[0]}, new String[0]));
        this.jScrollPane2.setViewportView(this.customerTable);
        this.jScrollPane1.setViewportView(this.jScrollPane2);
        this.jTabbedPane1.addTab("Customer", this.jScrollPane1);
        this.jScrollPane3.setViewportView(this.pointTable);
        this.jTabbedPane1.addTab("Point", this.jScrollPane3);
        this.jTable3.setModel(new DefaultTableModel(new Object[][]{{null, null, null, null}, {null, null, null, null}, {null, null, null, null}, {null, null, null, null}}, new String[]{"Title 1", "Title 2", "Title 3", "Title 4"}));
        this.jScrollPane4.setViewportView(this.jTable3);
        this.jTabbedPane1.addTab("Stock", this.jScrollPane4);
        this.syncButton.setFont(new Font("Ubuntu", 0, 24));
        this.syncButton.setText("Sinkronisasi");
        this.syncButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                DaftarCommitDialog.this.syncButtonActionPerformed(evt);
            }
        });
        this.exitButton.setText("Keluar");
        this.exitButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                DaftarCommitDialog.this.exitButtonActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.syncButton, GroupLayout.Alignment.TRAILING, -2, 464, -2).addComponent(this.exitButton, GroupLayout.Alignment.TRAILING, -2, 136, -2)).addGap(21, 21, 21)).addGroup(layout.createSequentialGroup().addComponent(this.jTabbedPane1).addContainerGap()))));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap(-1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addComponent(this.exitButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.syncButton)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addComponent(this.jLabel1).addGap(14, 14, 14))).addGap(18, 18, 18).addComponent(this.jTabbedPane1, -2, 458, -2).addGap(14, 14, 14)));
        this.pack();
    }

    private void exitButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void syncButtonActionPerformed(ActionEvent evt) {
        if (this.jScrollPane2.isShowing()) {
            try {
                int status = this.customerCommitService.pushCustomerCommits();
                if (status == 1) {
                    this.customerPointCommitService.pushCustomerCommits();
                    this.customerCommitService.pullCustomers();
                    JOptionPane.showMessageDialog(null, "Data berhasil disinkronkan");
                    this.fetchCustomerCommits();
                }
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        } else if (this.jScrollPane3.isShowing()) {
            try {
                int status = this.customerCommitService.pushCustomerCommits();
                if (status == 1) {
                    this.customerPointCommitService.pushCustomerCommits();
                    this.customerCommitService.pullCustomers();
                    JOptionPane.showMessageDialog(null, "Data berhasil disinkronkan");
                    this.fetchPointCommits();
                }
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (!"Nimbus".equals(info.getName())) continue;
                UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(DaftarCommitDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex) {
            Logger.getLogger(DaftarCommitDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex) {
            Logger.getLogger(DaftarCommitDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(DaftarCommitDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                DaftarCommitDialog dialog = new DaftarCommitDialog((Frame)new JFrame(), true);
                dialog.addWindowListener(new WindowAdapter(){

                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    private void fetchCustomerCommits() {
        Object[] colCustomerName = new String[]{"ID COMMIT", "ID CUSTOMER", "TIPE QUERY", "COMMIT DIBUAT", "STATUS COMMIT"};
        DefaultTableModel dtmCustomer = new DefaultTableModel(new String[0][1], colCustomerName);
        List<CustomerCommit> customers = this.customerCommitService.getUncommited();
        for (CustomerCommit customer : customers) {
            Object[] temp = new String[]{customer.getCustomerTransactionCommitId(), customer.getIdCustomer(), customer.getQueryType(), customer.getTransactionCreated().toString(), customer.getStatusDelete() == false ? "Active" : "Deleted"};
            dtmCustomer.addRow(temp);
        }
        this.customerTable.setModel(dtmCustomer);
    }

    private void fetchPointCommits() {
        Object[] colPointName = new String[]{"ID COMMIT", "ID CUSTOMER", "ID TRANSAKSI", "TIPE QUERY", "POINT", "COMMIT DIBUAT", "STATUS COMMIT"};
        DefaultTableModel dtmPoint = new DefaultTableModel(new String[0][1], colPointName);
        List<CustomerPointCommit> points = this.customerPointCommitService.getUncommited();
        for (CustomerPointCommit poin : points) {
            Object[] temp = new String[]{poin.getpointTransactionCommitId(), poin.getKtpCustomer(), poin.getIdTransaction(), poin.getQueryType(), Integer.toString(poin.getPoint()), poin.getTransactionCreated().toString(), poin.getStatusDelete() == false ? "Active" : "Deleted"};
            dtmPoint.addRow(temp);
        }
        this.pointTable.setModel(dtmPoint);
    }
}

