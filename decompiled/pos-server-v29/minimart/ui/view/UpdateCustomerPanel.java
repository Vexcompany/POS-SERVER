/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.xssf.usermodel.XSSFSheet
 *  org.apache.poi.xssf.usermodel.XSSFWorkbook
 */
package minimart.ui.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import minimart.engine.model.Customer;
import minimart.engine.service.CustomerCommitService;
import minimart.engine.service.CustomerService;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.view.StokOpnamPanel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class UpdateCustomerPanel
extends JPanel {
    private JTable dataBarangTbl;
    private JTextField fileNameField;
    private JButton importBtn;
    private JScrollPane jScrollPane2;
    private JButton keluarBtn;
    private JButton updateBtn;
    private String[] barangTMColumn = new String[]{"ID Customer", "KTP", "Nama", "Alamat", "Telepon", "Hutang", "Kota", "Poin", "Tanggal Lahir", "Limit Kredit", "TOP", "Tipe", "Created", "Updated"};
    private CustomerCommitService customerCommitService = new CustomerCommitService();
    private DefaultTableModel barangTM = new DefaultTableModel(new String[0][1], this.barangTMColumn);
    private DefaultPanelListener defaultPanelListener;
    private CustomerService customerService = new CustomerService();

    public UpdateCustomerPanel() {
        this.initComponents();
        this.dataBarangTbl.setAutoResizeMode(0);
    }

    private void initComponents() {
        this.importBtn = new JButton();
        this.fileNameField = new JTextField();
        this.keluarBtn = new JButton();
        this.jScrollPane2 = new JScrollPane();
        this.dataBarangTbl = new JTable();
        this.updateBtn = new JButton();
        this.setPreferredSize(new Dimension(800, 600));
        this.importBtn.setText("Import Excel");
        this.importBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                UpdateCustomerPanel.this.importBtnActionPerformed(evt);
            }
        });
        this.keluarBtn.setText("Keluar");
        this.keluarBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                UpdateCustomerPanel.this.keluarBtnActionPerformed(evt);
            }
        });
        this.dataBarangTbl.setModel(this.barangTM);
        this.jScrollPane2.setViewportView(this.dataBarangTbl);
        this.updateBtn.setText("Update");
        this.updateBtn.setPreferredSize(new Dimension(73, 23));
        this.updateBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                UpdateCustomerPanel.this.updateBtnActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane2).addGroup(layout.createSequentialGroup().addComponent(this.importBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.fileNameField, -2, 287, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 243, Short.MAX_VALUE).addComponent(this.updateBtn, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.keluarBtn, -2, 72, -2))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.importBtn).addComponent(this.fileNameField, -2, -1, -2).addComponent(this.keluarBtn).addComponent(this.updateBtn, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane2, -1, 549, Short.MAX_VALUE).addContainerGap()));
    }

    private void importBtnActionPerformed(ActionEvent evt) {
        JFileChooser jfc = new JFileChooser();
        jfc.showOpenDialog(null);
        File file = jfc.getSelectedFile();
        this.fileNameField.setText(file.getName());
        try {
            this.clearTable();
            FileInputStream fir = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook((InputStream)fir);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator rowIt = sheet.iterator();
            rowIt.next();
            while (rowIt.hasNext()) {
                Row row = (Row)rowIt.next();
                Iterator cellIterator = row.cellIterator();
                int a = 0;
                Object[] rowValue = new Object[15];
                while (cellIterator.hasNext()) {
                    Cell cell = (Cell)cellIterator.next();
                    rowValue[a] = a == 7 ? Integer.valueOf(Math.round(Float.parseFloat(cell.toString()))) : cell.toString();
                    ++a;
                }
                this.barangTM.addRow(rowValue);
            }
            workbook.close();
            fir.close();
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(StokOpnamPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(StokOpnamPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void keluarBtnActionPerformed(ActionEvent evt) {
        this.defaultPanelListener.exitClick();
    }

    private void updateBtnActionPerformed(ActionEvent evt) {
        int result = 0;
        for (int a = 0; a < this.barangTM.getRowCount(); ++a) {
            Customer customer = new Customer();
            customer.setId(this.barangTM.getValueAt(a, 0) + "");
            customer.setKtp(this.barangTM.getValueAt(a, 1) + "");
            customer.setNama(this.barangTM.getValueAt(a, 2) + "");
            customer.setAlamat(this.barangTM.getValueAt(a, 3) + "");
            customer.setTelepon(this.barangTM.getValueAt(a, 4) + "");
            customer.setHutang(Long.parseLong(this.barangTM.getValueAt(a, 5) + ""));
            customer.setKota(this.barangTM.getValueAt(a, 6) + "");
            customer.setPoint(Long.parseLong(this.barangTM.getValueAt(a, 7) + ""));
            try {
                customer.setTanggalLahir(new SimpleDateFormat("yyyy-MM-dd").parse(this.barangTM.getValueAt(a, 8) + ""));
            }
            catch (ParseException ex) {
                Logger.getLogger(UpdateCustomerPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            customer.setLimitKredit(Integer.parseInt(this.barangTM.getValueAt(a, 9) + ""));
            customer.setTop(Integer.parseInt(this.barangTM.getValueAt(a, 10) + ""));
            customer.setTipe(this.barangTM.getValueAt(a, 11) + "");
            try {
                customer.setCreated(new SimpleDateFormat("yyyy-MM-dd").parse(this.barangTM.getValueAt(a, 12) + ""));
            }
            catch (ParseException ex) {
                Logger.getLogger(UpdateCustomerPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                customer.setUpdated(new SimpleDateFormat("yyyy-MM-dd").parse(this.barangTM.getValueAt(a, 13) + ""));
            }
            catch (ParseException ex) {
                Logger.getLogger(UpdateCustomerPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            result = this.customerService.updateCustomer(customer);
            String succ_msg = new String();
            if (result == 0) {
                JOptionPane.showMessageDialog(null, "Penyimpanan Data " + customer.getId() + " Gagal");
                continue;
            }
            if (result != 1) continue;
            result &= this.customerCommitService.insertCustomerCommit(customer, "UPDATE") != false ? 1 : 0;
            succ_msg = "Data berhasil di update di lokal, mohon sinkronisasi data terlebih dahulu";
        }
        if (result == 1) {
            JOptionPane.showMessageDialog(null, "Penyimpanan Data Berhasil");
        }
    }

    public void addListener(DefaultPanelListener listener) {
        this.defaultPanelListener = listener;
    }

    private void clearTable() {
        int size = this.barangTM.getRowCount();
        for (int a = 0; a < size; ++a) {
            this.barangTM.removeRow(0);
        }
    }
}

