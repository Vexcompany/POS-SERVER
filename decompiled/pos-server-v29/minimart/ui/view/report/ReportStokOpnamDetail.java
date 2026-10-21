/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.toedter.calendar.JDateChooser
 */
package minimart.ui.view.report;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import minimart.engine.controller.Helper;
import minimart.engine.model.Barang;
import minimart.engine.service.BarangService;
import minimart.engine.service.StokOpnamDetailService;
import minimart.engine.service.StokOpnamService;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class ReportStokOpnamDetail
extends JPanel {
    private JDateChooser endDateField;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JScrollPane jScrollPane1;
    private JButton keluarButton;
    private JTextField kodeBarangField;
    private JComboBox<String> lokasiCmb;
    private JLabel namabarangLbl;
    private JButton rekapBtn;
    private JTable reportTable;
    private JDateChooser startDateField;
    private JButton tampilkanButton;
    private JFormattedTextField totalField;
    private JLabel totalLbl;
    private String[] reportColumn = new String[]{"StokOpnam Id", "Tanggal", "Karyawan", "ID Barang", "Nama Barang", "Stok Komp", "Stok Fisik", "Selisih", "Total", "Status Opnam"};
    private String[] reportColumnRekap = new String[]{"ID Barang", "Nama Barang", "Stok Komp", "Stok Fisik", "Selisih", "Total"};
    private String[] reportColumnOutlet = new String[]{"StokOpnam Id", "Tanggal", "Karyawan", "ID Barang", "Nama Barang", "Stok Fisik", "Selisih", "Status Opnam"};
    private String[] reportColumnRekapOutlet = new String[]{"ID Barang", "Nama Barang", "Stok Fisik", "Selisih"};
    private SearchDialog sd;
    private int screenHeight;
    private int screenWidth;
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private DefaultPanelListener defaultPanelListener;
    private Helper helper = Helper.getInstance();
    private Date transactionDate;
    private BarangService barangService = new BarangService();
    private StokOpnamService stokOpnamService = new StokOpnamService();
    private StokOpnamDetailService stokOpnamDetailService = new StokOpnamDetailService();
    private Barang barang = new Barang();

    public ReportStokOpnamDetail() {
        this.initComponents();
        this.startDateField.setDate(new Date());
        this.endDateField.setDate(new Date());
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        if (this.helper.getKaryawan().getRole().equals("KASIR") || this.helper.getKaryawan().getRole().equals("ADMIN")) {
            this.totalField.setVisible(true);
            this.totalLbl.setVisible(true);
        }
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.startDateField = new JDateChooser();
        this.tampilkanButton = new JButton();
        this.keluarButton = new JButton();
        this.jScrollPane1 = new JScrollPane();
        this.reportTable = new JTable();
        this.totalLbl = new JLabel();
        this.totalField = new JFormattedTextField();
        this.jLabel2 = new JLabel();
        this.endDateField = new JDateChooser();
        this.lokasiCmb = new JComboBox();
        this.jLabel4 = new JLabel();
        this.rekapBtn = new JButton();
        this.jLabel5 = new JLabel();
        this.kodeBarangField = new JTextField();
        this.namabarangLbl = new JLabel();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setText("Dari Tanggal");
        this.startDateField.addMouseListener((MouseListener)new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
                ReportStokOpnamDetail.this.startDateFieldMouseClicked(evt);
            }
        });
        this.tampilkanButton.setText("Tampilkan");
        this.tampilkanButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportStokOpnamDetail.this.tampilkanButtonActionPerformed(evt);
            }
        });
        this.keluarButton.setText("Keluar");
        this.keluarButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportStokOpnamDetail.this.keluarButtonActionPerformed(evt);
            }
        });
        this.reportTable.setModel(this.reportTableModel);
        this.jScrollPane1.setViewportView(this.reportTable);
        this.totalLbl.setFont(new Font("Tahoma", 0, 24));
        this.totalLbl.setText("Total");
        this.totalField.setEditable(false);
        this.totalField.setBackground(new Color(255, 255, 0));
        this.totalField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("\u00a4 #,##0.--"))));
        this.totalField.setFont(new Font("Tahoma", 0, 18));
        this.jLabel2.setText("Sampai Tanggal");
        this.endDateField.addMouseListener((MouseListener)new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
                ReportStokOpnamDetail.this.endDateFieldMouseClicked(evt);
            }
        });
        this.lokasiCmb.setModel(new DefaultComboBoxModel<String>(new String[]{"Gudang", "Toko"}));
        this.jLabel4.setText("Lokasi");
        this.rekapBtn.setText("Rekap");
        this.rekapBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportStokOpnamDetail.this.rekapBtnActionPerformed(evt);
            }
        });
        this.jLabel5.setText("Kode Barang");
        this.kodeBarangField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportStokOpnamDetail.this.kodeBarangFieldKeyPressed(evt);
            }
        });
        this.namabarangLbl.setText("-");
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent(this.jLabel1).addGap(39, 39, 39).addComponent((Component)this.startDateField, -2, 150, -2)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel4, -1, -1, Short.MAX_VALUE).addGap(20, 20, 20))).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.lokasiCmb, 0, -1, Short.MAX_VALUE).addComponent((Component)this.endDateField, -1, -1, Short.MAX_VALUE).addComponent(this.kodeBarangField, -2, 150, -2)))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.totalLbl).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.totalField, -2, 235, -2)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(74, 74, 74).addComponent(this.namabarangLbl, -2, 280, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 110, Short.MAX_VALUE).addComponent(this.tampilkanButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.rekapBtn, -2, 80, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.keluarButton, -2, 101, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel5).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.totalLbl).addComponent(this.totalField, -2, -1, -2).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel2)).addGroup(layout.createSequentialGroup().addComponent((Component)this.startDateField, -2, -1, -2).addGap(5, 5, 5).addComponent((Component)this.endDateField, -2, -1, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.lokasiCmb, -2, -1, -2).addComponent(this.jLabel4)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel5).addComponent(this.kodeBarangField, -2, -1, -2)).addGap(8, 8, 8).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.tampilkanButton).addComponent(this.keluarButton).addComponent(this.rekapBtn).addComponent(this.namabarangLbl)).addGap(18, 18, 18).addComponent(this.jScrollPane1, -2, 383, -2).addContainerGap(19, Short.MAX_VALUE)));
    }

    private void clearTable() {
        int size = this.reportTableModel.getRowCount();
        for (int a = 0; a < size; ++a) {
            this.reportTableModel.removeRow(0);
        }
    }

    private void tampilkanButtonActionPerformed(ActionEvent evt) {
        this.clearTable();
        this.transactionDate = this.startDateField.getDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDateString = simpleDateFormat.format(this.startDateField.getDate());
        String endDateString = simpleDateFormat.format(this.endDateField.getDate());
        long jumlahUang = 0L;
        this.reportTableModel.setColumnIdentifiers(this.reportColumn);
        List<Object[]> stokOpnamDetails = this.stokOpnamDetailService.getStokOpnamDetails(startDateString, endDateString, this.lokasiCmb.getSelectedItem() + "", this.kodeBarangField.getText());
        for (Object[] stokOpnamDetail : stokOpnamDetails) {
            Object[] rowValue = new Object[]{stokOpnamDetail[0], stokOpnamDetail[1], stokOpnamDetail[2], stokOpnamDetail[3], stokOpnamDetail[4], stokOpnamDetail[5], stokOpnamDetail[6], stokOpnamDetail[7], stokOpnamDetail[8], stokOpnamDetail[9]};
            jumlahUang += (long)Math.round(Float.parseFloat(rowValue[8] + ""));
            this.reportTableModel.addRow(rowValue);
        }
        this.totalField.setValue(jumlahUang);
    }

    private void keluarButtonActionPerformed(ActionEvent evt) {
        this.defaultPanelListener.exitClick();
    }

    private void startDateFieldMouseClicked(MouseEvent evt) {
    }

    private void endDateFieldMouseClicked(MouseEvent evt) {
    }

    private void rekapBtnActionPerformed(ActionEvent evt) {
        this.clearTable();
        this.transactionDate = this.startDateField.getDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDateString = simpleDateFormat.format(this.startDateField.getDate());
        String endDateString = simpleDateFormat.format(this.endDateField.getDate());
        long jumlahUang = 0L;
        this.reportTableModel.setColumnIdentifiers(this.reportColumnRekap);
        List<Object[]> stokOpnamDetails = this.stokOpnamDetailService.getStokOpnamDetailsRekap(startDateString, endDateString, this.lokasiCmb.getSelectedItem() + "", this.kodeBarangField.getText());
        for (Object[] stokOpnamDetail : stokOpnamDetails) {
            Object[] rowValue = new Object[]{stokOpnamDetail[0], stokOpnamDetail[1], stokOpnamDetail[2], stokOpnamDetail[3], stokOpnamDetail[4], stokOpnamDetail[5]};
            jumlahUang += (long)Math.round(Float.parseFloat(rowValue[5] + ""));
            this.reportTableModel.addRow(rowValue);
        }
        this.totalField.setValue(jumlahUang);
    }

    private void kodeBarangFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 113) {
            this.sd = new SearchDialog(null, true, "Pencarian Data Barang");
            this.sd.addSearchDialogListener(new searchBarangDialogEventHandler());
            int w = this.sd.getWidth();
            int h = this.sd.getHeight();
            this.sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            this.sd.setVisible(true);
        }
    }

    public void addListener(DefaultPanelListener listener) {
        this.defaultPanelListener = listener;
    }

    private void loadKodeBarang(String idBarang) {
        this.barang = this.barangService.getBarang(idBarang);
        if (this.barang != null) {
            this.kodeBarangField.setText(this.barang.getId());
            this.namabarangLbl.setText(this.barang.getNama());
            this.tampilkanButton.requestFocus();
        } else if (this.barang == null) {
            JOptionPane.showMessageDialog(null, "Barang Tidak Ditemukan");
        }
    }

    class serverPrinter
    implements Printable {
        serverPrinter() {
        }

        @Override
        public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
            if (page > 0) {
                return 1;
            }
            Graphics2D g2d = (Graphics2D)g;
            g2d.translate(pf.getImageableX(), pf.getImageableY());
            int y = 0;
            int x = 5;
            int yK = 18;
            int yP = 11;
            g.setFont(new Font("Times New Roman", 0, 8));
            g.drawString("Report Keuangan: " + ReportStokOpnamDetail.this.totalField.getText(), x + 0, y + yK);
            String pattern = "dd-MM-yyyy";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            String tanggal = format.format(ReportStokOpnamDetail.this.transactionDate);
            g.drawString("Penjualan Tanggal: " + tanggal, x + 0, y + (yK += yP));
            String nowDate = format.format(new Date());
            g.drawString("Dicetak Tanggal: " + nowDate, x + 0, y + (yK += yP));
            g.drawString("Jam " + new Date().getHours() + ":" + new Date().getMinutes() + ":" + new Date().getSeconds(), x + 120, y + yK);
            g.drawString("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -", x + 0, y + (yK += yP));
            yK += yP;
            for (int a = 0; a < ReportStokOpnamDetail.this.reportTableModel.getRowCount(); ++a) {
                g.drawString(ReportStokOpnamDetail.this.reportTableModel.getValueAt(a, 0).toString(), x + 0, y + yK);
                yK += yP;
                if (ReportStokOpnamDetail.this.reportTableModel.getValueAt(a, 1) != null) {
                    g.drawString("+" + ReportStokOpnamDetail.this.reportTableModel.getValueAt(a, 1).toString(), x + 15, y + yK);
                } else if (ReportStokOpnamDetail.this.reportTableModel.getValueAt(a, 2) != null) {
                    g.drawString("-" + ReportStokOpnamDetail.this.reportTableModel.getValueAt(a, 2).toString(), x + 15, y + yK);
                }
                yK += yP;
            }
            return 0;
        }
    }

    private class searchPanelBarangEventHandler
    implements SearchPanelListener {
        private searchPanelBarangEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            ReportStokOpnamDetail.this.loadKodeBarang(chooseValue);
        }
    }

    private class searchBarangDialogEventHandler
    implements SearchDialogListener {
        private searchBarangDialogEventHandler() {
        }

        @Override
        public void searchClick(String searchWord) {
            Object[] colName = new String[]{"KODE BARANG", "NAMA BARANG", "SATUAN", "JENIS"};
            DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
            List<Barang> barangs = ReportStokOpnamDetail.this.barangService.getBarangs("", searchWord, false, "", true);
            for (Barang barang : barangs) {
                Object[] temp = new String[]{barang.getId(), barang.getNama(), barang.getSatuan1(), barang.getJenis()};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Barang");
            sp.addSearchPanelListener(new searchPanelBarangEventHandler());
            sp.setVisible(true);
        }
    }
}

