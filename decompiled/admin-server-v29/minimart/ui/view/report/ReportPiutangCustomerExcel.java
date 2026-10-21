/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CellType
 *  org.apache.poi.ss.usermodel.Row
 *  org.apache.poi.xssf.usermodel.XSSFSheet
 *  org.apache.poi.xssf.usermodel.XSSFWorkbook
 */
package minimart.ui.view.report;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import minimart.engine.controller.Helper;
import minimart.engine.model.Customer;
import minimart.engine.model.PelunasanPiutang;
import minimart.engine.model.Penjualan;
import minimart.engine.model.ShiftKasir;
import minimart.engine.service.CustomerService;
import minimart.engine.service.PelunasanPiutangService;
import minimart.engine.service.PenjualanService;
import minimart.engine.service.SalesService;
import minimart.engine.service.ShiftKasirService;
import minimart.engine.service.SyncService;
import minimart.ui.listener.ConfirmationDialogListener;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.view.ConfirmationDialog;
import minimart.ui.view.StokOpnamPanel;
import minimart.ui.view.report.ReportPenjualanDetail;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReportPiutangCustomerExcel
extends JPanel {
    private JButton detailBtn;
    private JButton importBtn;
    private JLabel jLabel3;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JTabbedPane jTabbedPane1;
    private JTable jTable1;
    private JTable jTable2;
    private JButton keluarButton;
    private JButton pelunasanBtn;
    private JFormattedTextField totalField;
    private String[] reportColumn = new String[]{"ID Penjualan", "Customer", "Tanggal", "Jatuh Tempo", "Sales", "Jumlah", "Keterangan", "STATUS PELUNASAN", "Referensi"};
    private PenjualanService penjualanService = new PenjualanService();
    private DefaultPanelListener defaultPanelListener;
    private Helper helper = Helper.getInstance();
    private CustomerService customerService = new CustomerService();
    private Customer customer = new Customer();
    private int screenWidth;
    private int screenHeight;
    private SalesService salesService = new SalesService();
    private boolean canGoThrough;
    private SyncService syncService = new SyncService();
    private ShiftKasir shiftKasir = new ShiftKasir();
    private PelunasanPiutangService pelunasanPiutangService = new PelunasanPiutangService();
    private String[] transactionColumn = new String[]{"ID Penjualan", "Nama Customer", "Total Penjualan", "Nomor Pemesanan", "Total Shopee", "Nominal Pelunasan"};
    private String[] errorColumn = new String[]{"ID Penjualan", "No Pesanan", "Total Penjualan", "Jumlah Pelunasan"};
    private DefaultTableModel errorTableModel = new DefaultTableModel(new String[0][1], this.errorColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private DefaultTableModel penjualanTableModel = new DefaultTableModel(new String[0][1], this.transactionColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public ReportPiutangCustomerExcel() {
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.shiftKasir = new ShiftKasirService().getOpenedShiftKasir(this.helper.getKasir());
    }

    private void initComponents() {
        this.importBtn = new JButton();
        this.keluarButton = new JButton();
        this.jLabel3 = new JLabel();
        this.totalField = new JFormattedTextField();
        this.detailBtn = new JButton();
        this.pelunasanBtn = new JButton();
        this.jTabbedPane1 = new JTabbedPane();
        this.jScrollPane2 = new JScrollPane();
        this.jTable1 = new JTable();
        this.jScrollPane3 = new JScrollPane();
        this.jTable2 = new JTable();
        this.setPreferredSize(new Dimension(800, 600));
        this.addAncestorListener(new AncestorListener(){

            @Override
            public void ancestorMoved(AncestorEvent evt) {
            }

            @Override
            public void ancestorAdded(AncestorEvent evt) {
                ReportPiutangCustomerExcel.this.formAncestorAdded(evt);
            }

            @Override
            public void ancestorRemoved(AncestorEvent evt) {
            }
        });
        this.importBtn.setText("Import Excel");
        this.importBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPiutangCustomerExcel.this.importBtnActionPerformed(evt);
            }
        });
        this.keluarButton.setText("Keluar");
        this.keluarButton.setPreferredSize(new Dimension(72, 23));
        this.keluarButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPiutangCustomerExcel.this.keluarButtonActionPerformed(evt);
            }
        });
        this.jLabel3.setFont(new Font("Tahoma", 0, 24));
        this.jLabel3.setText("Total");
        this.totalField.setEditable(false);
        this.totalField.setBackground(new Color(255, 255, 0));
        this.totalField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("\u00a4 #,##0.--"))));
        this.totalField.setFont(new Font("Tahoma", 0, 18));
        this.detailBtn.setText("Detail");
        this.detailBtn.setPreferredSize(new Dimension(72, 23));
        this.detailBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPiutangCustomerExcel.this.detailBtnActionPerformed(evt);
            }
        });
        this.pelunasanBtn.setText("Pelunasan");
        this.pelunasanBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPiutangCustomerExcel.this.pelunasanBtnActionPerformed(evt);
            }
        });
        this.jTable1.setModel(this.penjualanTableModel);
        this.jScrollPane2.setViewportView(this.jTable1);
        this.jTabbedPane1.addTab("PelunasanData", this.jScrollPane2);
        this.jTable2.setModel(this.errorTableModel);
        this.jScrollPane3.setViewportView(this.jTable2);
        this.jTabbedPane1.addTab("Error", this.jScrollPane3);
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jTabbedPane1).addGroup(layout.createSequentialGroup().addComponent(this.importBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.pelunasanBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.detailBtn, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.keluarButton, -2, -1, -2).addGap(0, 0, Short.MAX_VALUE)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(0, 224, Short.MAX_VALUE).addComponent(this.jLabel3).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.totalField, -2, 235, -2))))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel3).addGap(35, 35, 35).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.importBtn).addComponent(this.keluarButton, -2, -1, -2).addComponent(this.detailBtn, -2, -1, -2).addComponent(this.pelunasanBtn))).addComponent(this.totalField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jTabbedPane1, -2, 467, -2).addContainerGap(29, Short.MAX_VALUE)));
    }

    private void clearTable() {
        int size = this.penjualanTableModel.getRowCount();
        for (int a = 0; a < size; ++a) {
            this.penjualanTableModel.removeRow(0);
        }
    }

    private void importBtnActionPerformed(ActionEvent evt) {
        this.clearTable();
        JFileChooser jfc = new JFileChooser();
        jfc.showOpenDialog(null);
        File file = jfc.getSelectedFile();
        try {
            this.clearTable();
            FileInputStream fir = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook((InputStream)fir);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator rowIt = sheet.iterator();
            rowIt.next();
            long total = 0L;
            while (rowIt.hasNext()) {
                Row row = (Row)rowIt.next();
                Iterator cellIterator = row.cellIterator();
                int a = 0;
                Object[] rowValue = new Object[10];
                while (cellIterator.hasNext()) {
                    Cell cell = (Cell)cellIterator.next();
                    cell.setCellType(CellType.STRING);
                    rowValue[a] = cell.getStringCellValue();
                    ++a;
                }
                Penjualan tempPenjualan = this.penjualanService.getPenjualan(rowValue[0].toString());
                if (tempPenjualan.getStatusPelunasan().equals("BELUM LUNAS")) {
                    this.penjualanTableModel.addRow(rowValue);
                } else {
                    this.errorTableModel.addRow(rowValue);
                }
                this.totalField.setValue(total += Long.parseLong(rowValue[5].toString()));
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

    private void keluarButtonActionPerformed(ActionEvent evt) {
        this.defaultPanelListener.exitClick();
    }

    private void pelunasanBtnActionPerformed(ActionEvent evt) {
        this.canGoThrough = false;
        ConfirmationDialog confirmationDialog = new ConfirmationDialog(null, true, "Lakukan Pelunasan Semua Transaksi, Lanjutkan?");
        confirmationDialog.addListener(new confirmDialogEventHandler());
        int w = confirmationDialog.getWidth();
        int h = confirmationDialog.getHeight();
        confirmationDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
        confirmationDialog.setVisible(true);
        if (this.canGoThrough) {
            ArrayList<Penjualan> dataPenjualan = new ArrayList<Penjualan>();
            ArrayList<PelunasanPiutang> dataPelunasan = new ArrayList<PelunasanPiutang>();
            for (int a = 0; a < this.penjualanTableModel.getRowCount(); ++a) {
                Penjualan tempPenjualan = this.penjualanService.getPenjualan(this.penjualanTableModel.getValueAt(a, 0).toString());
                tempPenjualan.setStatusPelunasan("SUDAH LUNAS");
                dataPenjualan.add(tempPenjualan);
                PelunasanPiutang pelunasanPiutang = new PelunasanPiutang();
                pelunasanPiutang.setKaryawanId(this.helper.getKaryawan().getId());
                pelunasanPiutang.setPenjualanId(this.penjualanTableModel.getValueAt(a, 0).toString());
                pelunasanPiutang.setTanggalPelunasan(new Date());
                pelunasanPiutang.setKeterangan("TRANSFER");
                pelunasanPiutang.setJumlahPelunasan(Long.parseLong(this.penjualanTableModel.getValueAt(a, 2).toString()));
                pelunasanPiutang.setShiftKasirId(this.shiftKasir.getId());
                dataPelunasan.add(pelunasanPiutang);
            }
            int[] result = this.penjualanService.updatePenjualanBatch(dataPenjualan);
            int[] result2 = this.pelunasanPiutangService.createPelunasanPiutangBatch(dataPelunasan);
            ArrayList<String> idGagal = new ArrayList<String>();
            ArrayList<String> pelunasanGagal = new ArrayList<String>();
            for (int a = 0; a < result.length; ++a) {
                if (result[a] != 0) continue;
                idGagal.add(((Penjualan)dataPenjualan.get(a)).getId());
            }
            for (int b = 0; b < result2.length; ++b) {
                if (result2[b] != 0) continue;
                pelunasanGagal.add(((PelunasanPiutang)dataPelunasan.get(b)).getPenjualanId());
            }
            if (idGagal.size() > 0) {
                JOptionPane.showMessageDialog(null, "Penjualan Gagal Terupdate: " + this.openArray(idGagal));
            }
            if (pelunasanGagal.size() > 0) {
                JOptionPane.showMessageDialog(null, "Pelunasan Gagal Tersimpan: " + this.openArray(pelunasanGagal));
            }
            if (idGagal.size() == 0 && pelunasanGagal.size() == 0) {
                JOptionPane.showMessageDialog(null, "Data berhasil Tersimpan");
                this.clearTable();
            }
            this.syncService.doSyncPelunasanPiutangUnsynced();
        }
    }

    private String openArray(List<String> datas) {
        String allData = "";
        for (String data : datas) {
            allData = allData + "," + data + ",";
        }
        return allData;
    }

    private void formAncestorAdded(AncestorEvent evt) {
    }

    private void detailBtnActionPerformed(ActionEvent evt) {
        if (this.jTable1.getSelectedRow() != -1) {
            String id = this.penjualanTableModel.getValueAt(this.jTable1.getSelectedRow(), 0).toString();
            ReportPenjualanDetail rpd = new ReportPenjualanDetail(null, true, id);
            int w = rpd.getWidth();
            int h = rpd.getHeight();
            rpd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            rpd.setVisible(true);
        }
    }

    public void addListener(DefaultPanelListener listener) {
        this.defaultPanelListener = listener;
    }

    private class confirmDialogEventHandler
    implements ConfirmationDialogListener {
        private confirmDialogEventHandler() {
        }

        @Override
        public void yesClick() {
            ReportPiutangCustomerExcel.this.canGoThrough = true;
        }

        @Override
        public void noClick() {
            ReportPiutangCustomerExcel.this.canGoThrough = false;
        }
    }
}

