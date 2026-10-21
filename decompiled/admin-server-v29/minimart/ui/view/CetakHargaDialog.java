/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jasperreports.engine.JasperFillManager
 *  net.sf.jasperreports.engine.JasperPrint
 *  net.sf.jasperreports.view.JasperViewer
 */
package minimart.ui.view;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.model.Barang;
import minimart.engine.service.BarangService;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class CetakHargaDialog
extends JDialog {
    private JButton cetakButton1;
    private JButton cetakButton2;
    private JButton cetakButton3;
    private JButton hapusButton;
    private JLabel jLabel1;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JList listBarang1;
    private JList listBarang2;
    private JButton moveBackButton;
    private JButton moveButton;
    private JTextField namaField;
    private BarangService barangService = new BarangService();
    private List<Barang> list;
    private DefaultListModel listModel1 = new DefaultListModel();
    private DefaultListModel listModel2 = new DefaultListModel();

    public CetakHargaDialog(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        List<Barang> listBarangKosong = this.barangService.getBarangsUpdateHarga(1);
        for (int a = 0; a < listBarangKosong.size(); ++a) {
            this.listModel2.add(a, listBarangKosong.get(a).getNama());
        }
    }

    private void initComponents() {
        this.jScrollPane1 = new JScrollPane();
        this.listBarang1 = new JList();
        this.jLabel1 = new JLabel();
        this.namaField = new JTextField();
        this.jScrollPane2 = new JScrollPane();
        this.listBarang2 = new JList();
        this.moveButton = new JButton();
        this.moveBackButton = new JButton();
        this.cetakButton1 = new JButton();
        this.hapusButton = new JButton();
        this.cetakButton2 = new JButton();
        this.cetakButton3 = new JButton();
        this.setDefaultCloseOperation(2);
        this.listBarang1.setModel(this.listModel1);
        this.jScrollPane1.setViewportView(this.listBarang1);
        this.jLabel1.setText("Nama Barang:");
        this.namaField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                CetakHargaDialog.this.namaFieldKeyPressed(evt);
            }
        });
        this.listBarang2.setModel(this.listModel2);
        this.jScrollPane2.setViewportView(this.listBarang2);
        this.moveButton.setText(">>>");
        this.moveButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CetakHargaDialog.this.moveButtonActionPerformed(evt);
            }
        });
        this.moveBackButton.setText("<<<");
        this.moveBackButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CetakHargaDialog.this.moveBackButtonActionPerformed(evt);
            }
        });
        this.cetakButton1.setText("Cetak 1");
        this.cetakButton1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CetakHargaDialog.this.cetakButton1ActionPerformed(evt);
            }
        });
        this.hapusButton.setText("Hapus");
        this.hapusButton.setMaximumSize(new Dimension(73, 23));
        this.hapusButton.setMinimumSize(new Dimension(73, 23));
        this.hapusButton.setPreferredSize(new Dimension(73, 23));
        this.hapusButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CetakHargaDialog.this.hapusButtonActionPerformed(evt);
            }
        });
        this.cetakButton2.setText("Cetak 1-2");
        this.cetakButton2.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CetakHargaDialog.this.cetakButton2ActionPerformed(evt);
            }
        });
        this.cetakButton3.setText("Cetak 1-3");
        this.cetakButton3.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CetakHargaDialog.this.cetakButton3ActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jScrollPane1, -2, 247, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.moveButton, -1, 76, Short.MAX_VALUE).addComponent(this.moveBackButton, -1, -1, Short.MAX_VALUE))).addGroup(layout.createSequentialGroup().addComponent(this.namaField, -2, 183, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.hapusButton, -2, -1, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane2, GroupLayout.Alignment.TRAILING, -2, 242, -2).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addComponent(this.cetakButton1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cetakButton2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cetakButton3))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.namaField, -2, -1, -2).addComponent(this.cetakButton1).addComponent(this.cetakButton2).addComponent(this.cetakButton3).addComponent(this.hapusButton, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addComponent(this.moveButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.moveBackButton)).addComponent(this.jScrollPane2, -1, 315, Short.MAX_VALUE).addComponent(this.jScrollPane1, GroupLayout.Alignment.TRAILING)).addContainerGap()));
        this.pack();
    }

    private void namaFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.listModel1.removeAllElements();
            this.list = this.barangService.getBarangs("", this.namaField.getText(), false, "", true);
            for (int a = 0; a < this.list.size(); ++a) {
                this.listModel1.add(0, this.list.get(a).getNama());
            }
        }
    }

    private void moveButtonActionPerformed(ActionEvent evt) {
        int index = this.listBarang1.getSelectedIndex();
        this.listModel2.add(0, this.listModel1.get(index));
        String namaBarang = this.listModel1.getElementAt(index).toString();
        Barang barang = this.barangService.getBarangs("", namaBarang, false, "", true).get(0);
        barang.setUpdateHarga(1);
        this.barangService.updateBarang(barang);
        this.listModel1.remove(index);
    }

    private void moveBackButtonActionPerformed(ActionEvent evt) {
        int index = this.listBarang2.getSelectedIndex();
        this.listModel1.add(0, this.listModel2.get(index));
        String namaBarang = this.listModel2.getElementAt(index).toString();
        Barang barang = this.barangService.getBarangs("", namaBarang, false, "", true).get(0);
        barang.setUpdateHarga(0);
        this.barangService.updateBarang(barang);
        this.listModel2.remove(index);
    }

    private void hapusButtonActionPerformed(ActionEvent evt) {
        for (int a = 0; a < this.listModel2.size(); ++a) {
            String namaBarang = this.listModel2.getElementAt(a).toString();
            Barang barang = this.barangService.getBarangs("", namaBarang, false, "", true).get(0);
            barang.setUpdateHarga(0);
            this.barangService.updateBarang(barang);
        }
        this.listModel2.removeAllElements();
    }

    private void cetakButton1ActionPerformed(ActionEvent evt) {
        try {
            File rear = new File("ireport/CetakHargaBarang2.jasper");
            String path = rear.getAbsolutePath();
            HashMap<String, Integer> parameter = new HashMap<String, Integer>();
            parameter.put("UPDATE_HARGA", 1);
            JasperPrint print = JasperFillManager.fillReport((String)path, parameter, (Connection)ConnectionManager.getInstance().getDataSource().getConnection("root", "root"));
            JasperViewer.viewReport((JasperPrint)print, (boolean)false);
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Dokumen Tidak Ada " + ex);
        }
    }

    private void cetakButton2ActionPerformed(ActionEvent evt) {
        try {
            File rear = new File("ireport/CetakHargaBarang12.jasper");
            String path = rear.getAbsolutePath();
            HashMap<String, Integer> parameter = new HashMap<String, Integer>();
            parameter.put("UPDATE_HARGA", 1);
            JasperPrint print = JasperFillManager.fillReport((String)path, parameter, (Connection)ConnectionManager.getInstance().getDataSource().getConnection("root", "root"));
            JasperViewer.viewReport((JasperPrint)print, (boolean)false);
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Dokumen Tidak Ada " + ex);
        }
    }

    private void cetakButton3ActionPerformed(ActionEvent evt) {
        try {
            File rear = new File("ireport/CetakHargaBarang123.jasper");
            String path = rear.getAbsolutePath();
            HashMap<String, Integer> parameter = new HashMap<String, Integer>();
            parameter.put("UPDATE_HARGA", 1);
            JasperPrint print = JasperFillManager.fillReport((String)path, parameter, (Connection)ConnectionManager.getInstance().getDataSource().getConnection("root", "root"));
            JasperViewer.viewReport((JasperPrint)print, (boolean)false);
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Dokumen Tidak Ada " + ex);
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
            Logger.getLogger(CetakHargaDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex) {
            Logger.getLogger(CetakHargaDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex) {
            Logger.getLogger(CetakHargaDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(CetakHargaDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                CetakHargaDialog dialog = new CetakHargaDialog((Frame)new JFrame(), true);
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
}

