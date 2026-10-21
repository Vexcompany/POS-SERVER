/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CreationHelper
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.xssf.usermodel.XSSFWorkbook
 */
package minimart.ui.view.report;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import minimart.engine.component.UppercaseDocumentFilter;
import minimart.engine.model.Customer;
import minimart.engine.service.CustomerService;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.view.report.ReportPoinPenjualanCustomer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReportMasterCustomerPanel
extends JPanel {
    private JButton cariButton;
    private JButton cetakBtn;
    private JLabel jLabel1;
    private JScrollPane jScrollPane1;
    private JButton keluarButton;
    private JTable reportTable;
    private JTextField searchField;
    private String[] reportColumn = new String[]{"ID Customer", "KTP", "Nama", "Alamat", "Telepon", "Hutang", "Kota", "Poin", "Tanggal Lahir", "Limit Kredit", "TOP", "Tipe", "Created", "Updated"};
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn);
    private DefaultPanelListener listener;
    private CustomerService customerService = new CustomerService();
    private DocumentFilter filter = new UppercaseDocumentFilter();

    public ReportMasterCustomerPanel() {
        this.initComponents();
        this.loadCustomer("");
        this.jScrollPane1.setHorizontalScrollBarPolicy(30);
        TableColumn col1 = this.reportTable.getColumnModel().getColumn(1);
        TableColumn col2 = this.reportTable.getColumnModel().getColumn(2);
        col1.setPreferredWidth(130);
        col2.setPreferredWidth(130);
        ((AbstractDocument)this.searchField.getDocument()).setDocumentFilter(this.filter);
        this.reportTable.setAutoResizeMode(0);
    }

    public void addListener(DefaultPanelListener listener) {
        this.listener = listener;
    }

    private void clearTable() {
        int size = this.reportTableModel.getRowCount();
        for (int a = 0; a < size; ++a) {
            this.reportTableModel.removeRow(0);
        }
    }

    private void createExcel() {
        try {
            XSSFWorkbook wb = new XSSFWorkbook();
            CreationHelper createhelper = wb.getCreationHelper();
            Sheet sheet = wb.createSheet("new sheet");
            Row row = null;
            Cell cell = null;
            row = sheet.createRow(0);
            cell = row.createCell(0);
            cell.setCellValue("ID Customer");
            cell = row.createCell(1);
            cell.setCellValue("KTP");
            cell = row.createCell(2);
            cell.setCellValue("Nama");
            cell = row.createCell(3);
            cell.setCellValue("Alamat");
            cell = row.createCell(4);
            cell.setCellValue("Telp");
            cell = row.createCell(5);
            cell.setCellValue("Hutang");
            cell = row.createCell(6);
            cell.setCellValue("Kota");
            cell = row.createCell(7);
            cell.setCellValue("Poin");
            cell = row.createCell(8);
            cell.setCellValue("Tanggal Lahir");
            cell = row.createCell(9);
            cell.setCellValue("Limit Kredit");
            cell = row.createCell(10);
            cell.setCellValue("TOP");
            cell = row.createCell(11);
            cell.setCellValue("Tipe");
            cell = row.createCell(12);
            cell.setCellValue("Created");
            cell = row.createCell(13);
            cell.setCellValue("Updated");
            for (int i = 0; i < this.reportTableModel.getRowCount(); ++i) {
                row = sheet.createRow(i + 1);
                for (int j = 0; j < this.reportTableModel.getColumnCount(); ++j) {
                    cell = row.createCell(j);
                    cell.setCellValue(this.reportTableModel.getValueAt(i, j) + "");
                }
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String nowDateString = simpleDateFormat.format(new Date());
            String url = "docStok/customer.xls";
            FileOutputStream out = new FileOutputStream(url);
            wb.write((OutputStream)out);
            out.close();
            JOptionPane.showMessageDialog(null, "Data Telah Tersimpan Pada " + url);
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(ReportPoinPenjualanCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(ReportPoinPenjualanCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadCustomer(String nama) {
        this.clearTable();
        List<Customer> customers = this.customerService.getCustomers(nama);
        for (Customer customer : customers) {
            Object[] rowValue = new Object[]{customer.getId(), customer.getKtp(), customer.getNama(), customer.getAlamat(), customer.getTelepon(), customer.getHutang(), customer.getKota(), customer.getPoint(), customer.getTanggalLahir(), customer.getLimitKredit(), customer.getTop(), customer.getTipe(), customer.getCreated(), customer.getUpdated()};
            this.reportTableModel.addRow(rowValue);
        }
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jScrollPane1 = new JScrollPane();
        this.reportTable = new JTable();
        this.keluarButton = new JButton();
        this.cariButton = new JButton();
        this.searchField = new JTextField();
        this.cetakBtn = new JButton();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setFont(new Font("Tahoma", 1, 18));
        this.jLabel1.setText("Report Master Customer");
        this.reportTable.setModel(this.reportTableModel);
        this.jScrollPane1.setViewportView(this.reportTable);
        this.keluarButton.setText("Keluar");
        this.keluarButton.setPreferredSize(new Dimension(73, 23));
        this.keluarButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportMasterCustomerPanel.this.keluarButtonActionPerformed(evt);
            }
        });
        this.cariButton.setText("Cari");
        this.cariButton.setPreferredSize(new Dimension(73, 23));
        this.cariButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportMasterCustomerPanel.this.cariButtonActionPerformed(evt);
            }
        });
        this.cetakBtn.setText("Cetak");
        this.cetakBtn.setOpaque(false);
        this.cetakBtn.setPreferredSize(new Dimension(73, 23));
        this.cetakBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportMasterCustomerPanel.this.cetakBtnActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1, -1, 780, Short.MAX_VALUE)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(this.searchField, -2, 156, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cariButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.keluarButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cetakBtn, -2, -1, -2)).addGroup(layout.createSequentialGroup().addGap(288, 288, 288).addComponent(this.jLabel1).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1).addGap(18, 18, 18).addComponent(this.jScrollPane1, -1, 492, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.keluarButton, -2, -1, -2).addComponent(this.cariButton, -2, -1, -2).addComponent(this.searchField, -2, -1, -2).addComponent(this.cetakBtn, -2, -1, -2)).addGap(23, 23, 23)));
    }

    private void keluarButtonActionPerformed(ActionEvent evt) {
        this.listener.exitClick();
    }

    private void cariButtonActionPerformed(ActionEvent evt) {
        this.loadCustomer(this.searchField.getText());
    }

    private void cetakBtnActionPerformed(ActionEvent evt) {
        this.createExcel();
    }
}

