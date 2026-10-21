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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import minimart.engine.model.Barang;
import minimart.engine.model.Barcode;
import minimart.engine.model.Customer;
import minimart.engine.model.Penjualan;
import minimart.engine.model.PenjualanDetail;
import minimart.engine.service.BarangService;
import minimart.engine.service.BarcodeService;
import minimart.engine.service.CustomerService;
import minimart.engine.service.KaryawanService;
import minimart.engine.service.PenjualanDetailService;
import minimart.engine.service.PenjualanService;
import minimart.engine.service.StokOpnamMandiriDetailService;
import minimart.ui.view.report.ReportPoinPenjualanCustomer;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ReportPenjualanDetail
extends JDialog {
    private JButton cetakBtn;
    private JTextField customerIdField;
    private JLabel customerNameField;
    private JButton exitBtn;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel5;
    private JScrollPane jScrollPane1;
    private JLabel karyawanField;
    private JTable reportTable;
    private JFormattedTextField totalField;
    private String[] reportColumn = new String[]{"Kode Barang", "Nama Barang", "Qty", "Sat", "Harga", "Total", "Disc", "Harga Akhir"};
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn);
    private PenjualanService penjualanService = new PenjualanService();
    private PenjualanDetailService penjualanDetailService = new PenjualanDetailService();
    private BarangService barangService = new BarangService();
    private KaryawanService karyawanService = new KaryawanService();
    private CustomerService customerService = new CustomerService();
    private String idPenjualan;
    private BarcodeService barcodeService = new BarcodeService();
    private StokOpnamMandiriDetailService stokOpnamMandiriDetailService = new StokOpnamMandiriDetailService();
    private static final int SOM_HIDE_N_DAYS = 1;

    public ReportPenjualanDetail(Frame parent, boolean modal, String idPenjualan) {
        super(parent, modal);
        this.initComponents();
        this.idPenjualan = idPenjualan;
        TableColumn col1 = this.reportTable.getColumnModel().getColumn(1);
        TableColumn col2 = this.reportTable.getColumnModel().getColumn(2);
        TableColumn col3 = this.reportTable.getColumnModel().getColumn(3);
        int width = 20;
        col1.setPreferredWidth(130);
        col2.setPreferredWidth(width);
        col3.setPreferredWidth(width);
        this.loadData(idPenjualan);
    }

    private void loadData(String idPenjualan) {
        Penjualan penjualan = this.penjualanService.getPenjualan(idPenjualan);
        this.totalField.setValue(penjualan.getTotalPenjualan());
        this.karyawanField.setText(this.karyawanService.getKaryawan(penjualan.getKaryawanId()).getNama());
        this.customerIdField.setText(penjualan.getCustomerId());
        Customer customer = this.customerService.getCustomerById(penjualan.getCustomerId());
        this.customerNameField.setText(customer.getNama() + "-" + customer.getAlamat());
        List<PenjualanDetail> penjualanDetails = this.penjualanDetailService.readPenjualanDetailByPenjualanId(idPenjualan);
        HashSet<String> hiddenIds = new HashSet<String>(this.stokOpnamMandiriDetailService.getBarangIdHiddenBySom(1));
        for (PenjualanDetail penjualanDetail : penjualanDetails) {
            Object[] rowValue = new Object[8];
            rowValue[0] = penjualanDetail.getBarangId();
            rowValue[1] = this.barangService.getBarang(penjualanDetail.getBarangId()).getNama();
            if (hiddenIds.contains(penjualanDetail.getBarangId())) {
                rowValue[2] = "-";
                rowValue[3] = penjualanDetail.getSatuan();
                rowValue[4] = "-";
                rowValue[5] = "-";
                rowValue[6] = "-";
                rowValue[7] = "-";
            } else {
                rowValue[2] = Float.valueOf(penjualanDetail.getJumlahBarang());
                rowValue[3] = penjualanDetail.getSatuan();
                rowValue[4] = penjualanDetail.getHargaJual();
                rowValue[5] = Float.valueOf((float)penjualanDetail.getHargaJual() * penjualanDetail.getJumlahBarang());
                rowValue[6] = penjualanDetail.getDiscount2();
                rowValue[7] = penjualanDetail.getTotalHarga();
            }
            this.reportTableModel.addRow(rowValue);
        }
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.customerIdField = new JTextField();
        this.karyawanField = new JLabel();
        this.customerNameField = new JLabel();
        this.totalField = new JFormattedTextField();
        this.jLabel5 = new JLabel();
        this.jScrollPane1 = new JScrollPane();
        this.reportTable = new JTable();
        this.cetakBtn = new JButton();
        this.exitBtn = new JButton();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("Karyawan");
        this.jLabel2.setText("Customer");
        this.customerIdField.setEditable(false);
        this.customerIdField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPenjualanDetail.this.customerIdFieldKeyPressed(evt);
            }
        });
        this.karyawanField.setText("-");
        this.customerNameField.setText("-");
        this.totalField.setEditable(false);
        this.totalField.setBackground(new Color(255, 255, 0));
        this.totalField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("\u00a4#,##0.--"))));
        this.totalField.setFont(new Font("Tahoma", 1, 24));
        this.totalField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPenjualanDetail.this.totalFieldKeyPressed(evt);
            }
        });
        this.jLabel5.setFont(new Font("Tahoma", 1, 24));
        this.jLabel5.setText("Total");
        this.reportTable.setModel(this.reportTableModel);
        this.reportTable.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPenjualanDetail.this.reportTableKeyPressed(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.reportTable);
        this.cetakBtn.setText("Cetak Exel");
        this.cetakBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPenjualanDetail.this.cetakBtnActionPerformed(evt);
            }
        });
        this.exitBtn.setText("Exit");
        this.exitBtn.setPreferredSize(new Dimension(75, 23));
        this.exitBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPenjualanDetail.this.exitBtnActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addGap(18, 18, 18).addComponent(this.karyawanField, -1, 114, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel2).addGap(18, 18, 18).addComponent(this.customerIdField))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.customerNameField, -2, 172, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 112, Short.MAX_VALUE).addComponent(this.jLabel5, -2, 74, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.totalField, -2, 232, -2)).addGroup(layout.createSequentialGroup().addComponent(this.cetakBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.exitBtn, -2, -1, -2).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.totalField).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.karyawanField)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.customerIdField, -2, -1, -2).addComponent(this.customerNameField))).addGroup(layout.createSequentialGroup().addComponent(this.jLabel5, -1, -1, Short.MAX_VALUE).addGap(3, 3, 3))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.cetakBtn).addComponent(this.exitBtn, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, 499, -2).addContainerGap()));
        this.pack();
    }

    private void totalFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void reportTableKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void customerIdFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void cetakBtnActionPerformed(ActionEvent evt) {
        try {
            HSSFWorkbook wb = new HSSFWorkbook();
            CreationHelper createhelper = wb.getCreationHelper();
            Sheet sheet = wb.createSheet("new sheet");
            Row row = null;
            Cell cell = null;
            row = sheet.createRow(0);
            cell = row.createCell(0);
            cell.setCellValue("Code,Quantity,UnitCost,");
            for (int i = 0; i < this.reportTableModel.getRowCount(); ++i) {
                row = sheet.createRow(i + 1);
                cell = row.createCell(0);
                float tempJumlah = 0.0f;
                long tempUnitcost = Long.parseLong(this.reportTableModel.getValueAt(i, 7).toString());
                Barang tempBarang = this.barangService.getBarang(this.reportTableModel.getValueAt(i, 0).toString());
                System.out.println(tempBarang.getNama());
                Barcode barcode = this.barcodeService.getBarcodes(this.reportTableModel.getValueAt(i, 0).toString(), tempBarang.getSatuan1()).get(0);
                if (this.reportTableModel.getValueAt(i, 3).toString().equals(tempBarang.getSatuan1())) {
                    tempJumlah = Float.parseFloat(this.reportTableModel.getValueAt(i, 2).toString());
                    tempUnitcost /= (long)((int)tempJumlah);
                } else if (this.reportTableModel.getValueAt(i, 3).toString().equals(tempBarang.getSatuan2())) {
                    tempJumlah = Float.parseFloat(this.reportTableModel.getValueAt(i, 2).toString()) * (float)tempBarang.getKonversi21();
                    tempUnitcost /= (long)((int)tempJumlah);
                } else if (this.reportTableModel.getValueAt(i, 3).toString().equals(tempBarang.getSatuan3())) {
                    tempJumlah = Float.parseFloat(this.reportTableModel.getValueAt(i, 2).toString()) * (float)tempBarang.getKonversi21() * (float)tempBarang.getKonversi32();
                    tempUnitcost /= (long)((int)tempJumlah);
                }
                cell.setCellValue(barcode.getBarcode() + "," + (int)tempJumlah + "," + tempUnitcost + ",");
            }
            String url = "penjualanDetail/" + this.idPenjualan + ".csv";
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

    private void exitBtnActionPerformed(ActionEvent evt) {
        this.dispose();
    }
}

