/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.hssf.usermodel.HSSFWorkbook
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CreationHelper
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.ss.usermodel.Sheet
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
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import minimart.engine.controller.Helper;
import minimart.engine.model.Barang;
import minimart.engine.service.BarangService;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.view.report.ReportPoinPenjualanCustomer;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ReportStokTerakhirPanel
extends JPanel {
    private JButton cetakButton;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JScrollPane jScrollPane1;
    private JButton keluarButton;
    private JTable reportTable;
    private JFormattedTextField totalField;
    private String[] reportColumn = new String[]{"Nama Barang", "Jenis", "Stok Barang", "Nilai Barang"};
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn);
    private DefaultPanelListener listener;
    private BarangService barangService = new BarangService();
    private String jenisData;
    private String rakData;
    private Helper helper = Helper.getInstance();
    float total = 0.0f;

    public ReportStokTerakhirPanel(String jenis, String rak) {
        this.initComponents();
        this.jenisData = jenis;
        this.rakData = rak;
        this.loadData(jenis, rak);
        TableColumn col1 = this.reportTable.getColumnModel().getColumn(0);
        col1.setPreferredWidth(20);
    }

    private void loadData(String jenis, String rak) {
        List<Barang> barangs = this.barangService.getBarangsByJenisRakNama(jenis, rak, "", "", true);
        for (Barang barang : barangs) {
            Object[] rowValue = new Object[]{barang.getNama() + " (" + barang.getKonversi21() + "/" + barang.getKonversi32() + ")", barang.getJenis(), barang.getStokToko() + barang.getStokGudang() + "/ " + barang.getSatuan1(), Float.valueOf((float)barang.getHpp() * (barang.getStokToko() + barang.getStokGudang()))};
            this.total += Float.parseFloat(rowValue[3].toString());
            this.reportTableModel.addRow(rowValue);
        }
        this.totalField.setValue(Float.valueOf(this.total));
    }

    private void createExcel() {
        try {
            HSSFWorkbook wb = new HSSFWorkbook();
            CreationHelper createhelper = wb.getCreationHelper();
            Sheet sheet = wb.createSheet("new sheet");
            Row row = null;
            Cell cell = null;
            row = sheet.createRow(0);
            cell = row.createCell(0);
            cell.setCellValue("Nama Barang");
            cell = row.createCell(1);
            cell.setCellValue("Jenis");
            cell = row.createCell(2);
            cell.setCellValue("Stok Barang");
            cell = row.createCell(3);
            cell.setCellValue("Harga 1");
            cell = row.createCell(4);
            cell.setCellValue("Harga 2");
            cell = row.createCell(5);
            cell.setCellValue("Harga 3");
            for (int i = 0; i < this.reportTableModel.getRowCount(); ++i) {
                row = sheet.createRow(i + 1);
                for (int j = 0; j < this.reportTableModel.getColumnCount(); ++j) {
                    cell = row.createCell(j);
                    cell.setCellValue(this.reportTableModel.getValueAt(i, j) + "");
                }
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String nowDateString = simpleDateFormat.format(new Date());
            String url = "docStok/" + nowDateString + ".xls";
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

    public void addListener(DefaultPanelListener listener) {
        this.listener = listener;
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jScrollPane1 = new JScrollPane();
        this.reportTable = new JTable();
        this.keluarButton = new JButton();
        this.cetakButton = new JButton();
        this.totalField = new JFormattedTextField();
        this.jLabel2 = new JLabel();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setFont(new Font("Tahoma", 1, 18));
        this.jLabel1.setText("Report Stok Terakhir");
        this.reportTable.setModel(this.reportTableModel);
        this.jScrollPane1.setViewportView(this.reportTable);
        this.keluarButton.setText("Keluar");
        this.keluarButton.setPreferredSize(new Dimension(73, 23));
        this.keluarButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportStokTerakhirPanel.this.keluarButtonActionPerformed(evt);
            }
        });
        this.cetakButton.setText("Cetak");
        this.cetakButton.setPreferredSize(new Dimension(73, 23));
        this.cetakButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportStokTerakhirPanel.this.cetakButtonActionPerformed(evt);
            }
        });
        this.totalField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));
        this.totalField.setToolTipText("");
        this.totalField.setFont(new Font("Tahoma", 0, 18));
        this.jLabel2.setFont(new Font("Tahoma", 0, 18));
        this.jLabel2.setText("Total Nilai Barang");
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.totalField, -2, 273, -2)).addComponent(this.jScrollPane1, GroupLayout.Alignment.TRAILING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(0, 628, Short.MAX_VALUE).addComponent(this.cetakButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.keluarButton, -2, -1, -2))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.totalField, -2, -1, -2).addComponent(this.jLabel2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jScrollPane1, -2, 467, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.keluarButton, -2, -1, -2).addComponent(this.cetakButton, -2, -1, -2)).addContainerGap(47, Short.MAX_VALUE)));
    }

    private void keluarButtonActionPerformed(ActionEvent evt) {
        this.listener.exitClick();
    }

    private void cetakButtonActionPerformed(ActionEvent evt) {
        this.createExcel();
    }
}

