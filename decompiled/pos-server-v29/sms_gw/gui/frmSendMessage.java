/*
 * Decompiled with CFR 0.152.
 */
package sms_gw.gui;

import java.awt.EventQueue;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import sms_gw.comm.SMSClient;

public class frmSendMessage
extends JFrame {
    private JButton btnKirim;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JPanel jPanel1;
    private JPanel jPanel2;
    public static List lstRxTx;
    public static JTextField txtBaurate;
    public static JTextField txtCom;
    private JTextField txtPesan;
    public static JTextField txtSMSC;
    private JTextField txtTujuan;

    public frmSendMessage() {
        this.initComponents();
        txtCom.setText("COM44");
        this.txtTujuan.setText("082140062820");
        this.txtPesan.setText("test");
        txtBaurate.setText("115200");
        txtSMSC.setText("+6281100000");
    }

    private void initComponents() {
        this.jPanel1 = new JPanel();
        this.txtTujuan = new JTextField();
        this.txtPesan = new JTextField();
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.btnKirim = new JButton();
        this.jPanel2 = new JPanel();
        this.jLabel3 = new JLabel();
        txtCom = new JTextField();
        this.jLabel4 = new JLabel();
        this.jLabel5 = new JLabel();
        txtSMSC = new JTextField();
        txtBaurate = new JTextField();
        lstRxTx = new List();
        this.setDefaultCloseOperation(3);
        this.setTitle("SMS Sender Dengan Java");
        this.jPanel1.setBorder(BorderFactory.createTitledBorder("Message"));
        this.jLabel1.setText("No HP");
        this.jLabel2.setText("Pesan");
        this.btnKirim.setText("Kirim Pesan");
        this.btnKirim.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                frmSendMessage.this.btnKirimActionPerformed(evt);
            }
        });
        GroupLayout jPanel1Layout = new GroupLayout(this.jPanel1);
        this.jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2).addComponent(this.jLabel1)).addGap(26, 26, 26).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.txtTujuan, -2, 114, -2).addComponent(this.txtPesan, -1, 313, Short.MAX_VALUE))).addComponent(this.btnKirim, GroupLayout.Alignment.TRAILING)).addContainerGap()));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap(-1, Short.MAX_VALUE).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.txtTujuan, -2, -1, -2).addComponent(this.jLabel1)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.txtPesan, -2, 51, -2).addComponent(this.jLabel2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.btnKirim)));
        this.jPanel2.setBorder(BorderFactory.createTitledBorder("Setting"));
        this.jLabel3.setText("Com :");
        this.jLabel4.setText("Baudrate :");
        this.jLabel5.setText("Smsc :");
        GroupLayout jPanel2Layout = new GroupLayout(this.jPanel2);
        this.jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addContainerGap().addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel3).addComponent(this.jLabel4).addComponent(this.jLabel5)).addGap(27, 27, 27).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(txtSMSC, -2, 161, -2).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(txtBaurate, GroupLayout.Alignment.LEADING).addComponent(txtCom, GroupLayout.Alignment.LEADING, -1, 94, Short.MAX_VALUE))).addContainerGap(139, Short.MAX_VALUE)));
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(jPanel2Layout.createSequentialGroup().addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(txtCom, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4).addComponent(txtBaurate, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel5).addComponent(txtSMSC, -2, -1, -2))));
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(lstRxTx, GroupLayout.Alignment.LEADING, -1, 404, Short.MAX_VALUE).addComponent(this.jPanel1, GroupLayout.Alignment.LEADING, -1, -1, Short.MAX_VALUE).addComponent(this.jPanel2, GroupLayout.Alignment.LEADING, -1, -1, Short.MAX_VALUE)).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jPanel2, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jPanel1, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(lstRxTx, -2, 155, -2).addContainerGap(13, Short.MAX_VALUE)));
        this.pack();
    }

    private void btnKirimActionPerformed(ActionEvent evt) {
        SMSClient sc = new SMSClient(1);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                new frmSendMessage().setVisible(true);
            }
        });
    }
}

