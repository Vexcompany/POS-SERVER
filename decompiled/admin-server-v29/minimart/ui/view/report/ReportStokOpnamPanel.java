/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.sf.jasperreports.engine.JasperFillManager
 *  net.sf.jasperreports.engine.JasperPrint
 *  net.sf.jasperreports.view.JasperViewer
 */
package minimart.ui.view.report;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.Connection;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
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
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.controller.Helper;
import minimart.engine.model.Karyawan;
import minimart.engine.model.StockCommit;
import minimart.engine.model.StokOpnam;
import minimart.engine.model.StokOpnamDetail;
import minimart.engine.service.BarangService;
import minimart.engine.service.KaryawanService;
import minimart.engine.service.SinkronisasiBarangService;
import minimart.engine.service.StokOpnamDetailService;
import minimart.engine.service.StokOpnamService;
import minimart.engine.service.SyncService;
import minimart.ui.listener.ConfirmationDialogListener;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.listener.PasswordInputListener;
import minimart.ui.view.ConfirmationDialog;
import minimart.ui.view.PasswordInputDialog;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class ReportStokOpnamPanel
extends JPanel {
    private JButton belumDiprosesBtn;
    private JComboBox bulanCmb;
    private JButton cetakBtn;
    private JLabel jLabel1;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JButton prosesBtn;
    private JTable stokOpnamDetailTable;
    private JTable stokOpnamTable;
    private JComboBox tahunCmb;
    private JButton tampilkanDataBtn;
    private JButton tampilkanDetailBtn;
    private JButton tampilkanSelisihBtn;
    private JFormattedTextField totalHargaFieldMin;
    private JFormattedTextField totalHargaFieldPlus;
    private JFormattedTextField totalSOField;
    private JButton tutupBtn;
    private String[] stokOpnamCol = new String[]{"No Opnam", "Tanggal Opnam", "Karyawan", "Status Opnam", "Lokasi", "Tipe", "Tipe Name", "Keterangan", "Total"};
    private SyncService syncService = new SyncService();
    private float totalHargaBarangMin = 0.0f;
    private float totalHargaBarangPlus = 0.0f;
    private Helper helper = Helper.getInstance();
    private SinkronisasiBarangService sinkronisasiBarangService = new SinkronisasiBarangService();
    private DefaultTableModel stokOpnamTM = new DefaultTableModel(new String[0][1], this.stokOpnamCol){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private DefaultPanelListener listener;
    private String[] stokOpnamDetailCol = new String[]{"Kode Barang", "Nama Barang", "Stok Komp", "Stok Fisik", "Selisih", "Satuan", "Nilai Barang"};
    private DefaultTableModel stokOpnamDetailTM = new DefaultTableModel(new String[0][1], this.stokOpnamDetailCol){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private StokOpnamService stokOpnamService = new StokOpnamService();
    private StokOpnamDetailService stokOpnamDetailService = new StokOpnamDetailService();
    private KaryawanService karyawanService = new KaryawanService();
    private BarangService barangService = new BarangService();
    private boolean selisih = false;
    private boolean canGoThrough;
    private int screenWidth = 0;
    private int screenHeight = 0;

    public ReportStokOpnamPanel() {
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    }

    public void addListener(DefaultPanelListener listener) {
        this.listener = listener;
    }

    private void clearStokOpnamTable() {
        int size = this.stokOpnamTM.getRowCount();
        for (int a = 0; a < size; ++a) {
            this.stokOpnamTM.removeRow(0);
        }
    }

    private void clearStokOpnamDetailTable() {
        int size = this.stokOpnamDetailTM.getRowCount();
        for (int a = 0; a < size; ++a) {
            this.stokOpnamDetailTM.removeRow(0);
        }
    }

    private void tampilkanClick(String statusOpnam) {
        this.clearStokOpnamTable();
        String startDate = this.tahunCmb.getSelectedItem() + "-";
        String endDate = this.tahunCmb.getSelectedItem() + "-";
        if (this.bulanCmb.getSelectedIndex() == 0) {
            startDate = startDate + "01-01";
            endDate = endDate + "01-31";
        } else if (this.bulanCmb.getSelectedIndex() == 1) {
            startDate = startDate + "02-01";
            endDate = endDate + "02-28";
        } else if (this.bulanCmb.getSelectedIndex() == 2) {
            startDate = startDate + "03-01";
            endDate = endDate + "03-31";
        } else if (this.bulanCmb.getSelectedIndex() == 3) {
            startDate = startDate + "04-01";
            endDate = endDate + "04-30";
        } else if (this.bulanCmb.getSelectedIndex() == 4) {
            startDate = startDate + "05-01";
            endDate = endDate + "05-31";
        } else if (this.bulanCmb.getSelectedIndex() == 5) {
            startDate = startDate + "06-01";
            endDate = endDate + "06-30";
        } else if (this.bulanCmb.getSelectedIndex() == 6) {
            startDate = startDate + "07-01";
            endDate = endDate + "07-31";
        } else if (this.bulanCmb.getSelectedIndex() == 7) {
            startDate = startDate + "08-01";
            endDate = endDate + "08-31";
        } else if (this.bulanCmb.getSelectedIndex() == 8) {
            startDate = startDate + "09-01";
            endDate = endDate + "09-30";
        } else if (this.bulanCmb.getSelectedIndex() == 9) {
            startDate = startDate + "10-01";
            endDate = endDate + "10-31";
        } else if (this.bulanCmb.getSelectedIndex() == 10) {
            startDate = startDate + "11-01";
            endDate = endDate + "11-30";
        } else if (this.bulanCmb.getSelectedIndex() == 11) {
            startDate = startDate + "12-01";
            endDate = endDate + "12-31";
        }
        List<StokOpnam> stokOpnams = this.stokOpnamService.getStokOpnams(statusOpnam, startDate, endDate);
        long total = 0L;
        for (StokOpnam stokOpnam : stokOpnams) {
            Object[] rowData = new Object[10];
            rowData[0] = stokOpnam.getId();
            rowData[1] = stokOpnam.getTanggal();
            rowData[2] = this.karyawanService.getAllKaryawan(stokOpnam.getKaryawanId()).getNama();
            rowData[3] = stokOpnam.getStatusOpnam();
            rowData[4] = stokOpnam.getLokasi();
            rowData[5] = stokOpnam.getTipe();
            rowData[6] = stokOpnam.getTipeName();
            rowData[7] = stokOpnam.getKeterangan();
            List<Object[]> stokOpnamDetails = this.stokOpnamDetailService.getStokOpnamDetails(stokOpnam.getId() + "");
            rowData[8] = stokOpnamDetails.isEmpty() ? Long.valueOf(Long.parseLong("0")) : Long.valueOf(this.stokOpnamService.getTotalOpnamById(stokOpnam.getId()));
            this.stokOpnamTM.addRow(rowData);
            this.totalSOField.setValue(total += ((Long)rowData[8]).longValue());
        }
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.bulanCmb = new JComboBox();
        this.tahunCmb = new JComboBox();
        this.tutupBtn = new JButton();
        this.cetakBtn = new JButton();
        this.tampilkanDataBtn = new JButton();
        this.jScrollPane1 = new JScrollPane();
        this.stokOpnamTable = new JTable();
        this.tampilkanDetailBtn = new JButton();
        this.tampilkanSelisihBtn = new JButton();
        this.jScrollPane2 = new JScrollPane();
        this.stokOpnamDetailTable = new JTable();
        this.totalHargaFieldMin = new JFormattedTextField();
        this.totalHargaFieldPlus = new JFormattedTextField();
        this.totalSOField = new JFormattedTextField();
        this.prosesBtn = new JButton();
        this.belumDiprosesBtn = new JButton();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setText("Periode");
        this.bulanCmb.setModel(new DefaultComboBoxModel<String>(new String[]{"JANUARI", "FEBRUARI", "MARET", "APRIL", "MEI", "JUNI", "JULI", "AGUSTUS", "SEPTEMBER", "OKTOBER", "NOVEMBER", "DESEMBER"}));
        this.tahunCmb.setModel(new DefaultComboBoxModel<String>(new String[]{"2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"}));
        this.tutupBtn.setText("Tutup");
        this.tutupBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportStokOpnamPanel.this.tutupBtnActionPerformed(evt);
            }
        });
        this.cetakBtn.setText("Cetak");
        this.cetakBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportStokOpnamPanel.this.cetakBtnActionPerformed(evt);
            }
        });
        this.tampilkanDataBtn.setText("Tampilkan Semua");
        this.tampilkanDataBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportStokOpnamPanel.this.tampilkanDataBtnActionPerformed(evt);
            }
        });
        this.stokOpnamTable.setModel(this.stokOpnamTM);
        this.jScrollPane1.setViewportView(this.stokOpnamTable);
        this.tampilkanDetailBtn.setText("Tampilkan Detail Data");
        this.tampilkanDetailBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportStokOpnamPanel.this.tampilkanDetailBtnActionPerformed(evt);
            }
        });
        this.tampilkanSelisihBtn.setText("Tampilkan Selisih Stok");
        this.tampilkanSelisihBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportStokOpnamPanel.this.tampilkanSelisihBtnActionPerformed(evt);
            }
        });
        this.stokOpnamDetailTable.setModel(this.stokOpnamDetailTM);
        this.jScrollPane2.setViewportView(this.stokOpnamDetailTable);
        this.totalHargaFieldMin.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));
        this.prosesBtn.setText("Proses Stok");
        this.prosesBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportStokOpnamPanel.this.prosesBtnActionPerformed(evt);
            }
        });
        this.belumDiprosesBtn.setText("Belum Diproses");
        this.belumDiprosesBtn.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
                ReportStokOpnamPanel.this.belumDiprosesBtnMouseClicked(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addGap(18, 18, 18).addComponent(this.bulanCmb, -2, 105, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.tahunCmb, -2, 68, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.tampilkanDataBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.belumDiprosesBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.totalSOField, -2, 149, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.tutupBtn)).addGroup(layout.createSequentialGroup().addComponent(this.tampilkanDetailBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.tampilkanSelisihBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.totalHargaFieldPlus, -2, 136, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.totalHargaFieldMin, -2, 136, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.prosesBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cetakBtn)).addComponent(this.jScrollPane2)).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.bulanCmb, -2, -1, -2).addComponent(this.tahunCmb, -2, -1, -2).addComponent(this.tutupBtn).addComponent(this.tampilkanDataBtn).addComponent(this.totalSOField, -2, -1, -2).addComponent(this.belumDiprosesBtn)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, 93, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.tampilkanDetailBtn).addComponent(this.tampilkanSelisihBtn).addComponent(this.cetakBtn).addComponent(this.totalHargaFieldMin, -2, -1, -2).addComponent(this.totalHargaFieldPlus, -2, -1, -2).addComponent(this.prosesBtn)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane2, -2, 374, -2).addContainerGap(32, Short.MAX_VALUE)));
    }

    private void tutupBtnActionPerformed(ActionEvent evt) {
        this.listener.exitClick();
    }

    private void tampilkanDataBtnActionPerformed(ActionEvent evt) {
        this.tampilkanClick("");
    }

    private void tampilkanDetailBtnActionPerformed(ActionEvent evt) {
        long totalHargaBarangMin = 0L;
        long totalHargaBarangPlus = 0L;
        this.selisih = false;
        this.clearStokOpnamDetailTable();
        String id = this.stokOpnamTM.getValueAt(this.stokOpnamTable.getSelectedRow(), 0).toString();
        List<Object[]> stokOpnamDetails = this.stokOpnamDetailService.getStokOpnamDetails(id);
        for (Object[] stokOpnamDetail : stokOpnamDetails) {
            this.stokOpnamDetailTM.addRow(stokOpnamDetail);
            if ((Long)stokOpnamDetail[6] < 0L) {
                totalHargaBarangMin = (long)Math.round(((Long)stokOpnamDetail[6]).longValue()) + totalHargaBarangMin;
            } else {
                totalHargaBarangPlus = (long)Math.round(((Long)stokOpnamDetail[6]).longValue()) + totalHargaBarangPlus;
            }
            this.totalHargaFieldPlus.setValue(totalHargaBarangPlus);
            this.totalHargaFieldMin.setValue(totalHargaBarangMin);
        }
    }

    private void tampilkanSelisihBtnActionPerformed(ActionEvent evt) {
        long totalHargaBarangMin = 0L;
        long totalHargaBarangPlus = 0L;
        this.selisih = false;
        this.clearStokOpnamDetailTable();
        String id = this.stokOpnamTM.getValueAt(this.stokOpnamTable.getSelectedRow(), 0).toString();
        List<Object[]> stokOpnamDetails = this.stokOpnamDetailService.getStokOpnamDetails(id);
        for (Object[] stokOpnamDetail : stokOpnamDetails) {
            if ((Double)stokOpnamDetail[4] == 0.0) continue;
            this.stokOpnamDetailTM.addRow(stokOpnamDetail);
            if ((Long)stokOpnamDetail[6] < 0L) {
                totalHargaBarangMin = (long)Math.round(((Long)stokOpnamDetail[6]).longValue()) + totalHargaBarangMin;
            } else {
                totalHargaBarangPlus = (long)Math.round(((Long)stokOpnamDetail[6]).longValue()) + totalHargaBarangPlus;
            }
            this.totalHargaFieldPlus.setValue(totalHargaBarangPlus);
            this.totalHargaFieldMin.setValue(totalHargaBarangMin);
        }
    }

    private void cetakBtnActionPerformed(ActionEvent evt) {
        String id = this.stokOpnamTM.getValueAt(this.stokOpnamTable.getSelectedRow(), 0).toString();
        if (!this.selisih) {
            try {
                File rear = new File("ireport/StokOpnamFullReport.jasper");
                String path = rear.getAbsolutePath();
                HashMap<String, String> parameter = new HashMap<String, String>();
                parameter.put("STOK_OPNAM_ID", id);
                JasperPrint print = JasperFillManager.fillReport((String)path, parameter, (Connection)ConnectionManager.getInstance().getDataSource().getConnection("root", "root"));
                JasperViewer.viewReport((JasperPrint)print, (boolean)false);
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Dokumen Tidak Ada " + ex);
            }
        } else {
            try {
                File rear = new File("ireport/StokOpnamSelisihFullReport.jasper");
                String path = rear.getAbsolutePath();
                HashMap<String, String> parameter = new HashMap<String, String>();
                parameter.put("STOK_OPNAM_ID", id);
                JasperPrint print = JasperFillManager.fillReport((String)path, parameter, (Connection)ConnectionManager.getInstance().getDataSource().getConnection("root", "root"));
                JasperViewer.viewReport((JasperPrint)print, (boolean)false);
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Dokumen Tidak Ada " + ex);
            }
        }
    }

    private void prosesBtnActionPerformed(ActionEvent evt) {
        PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
        passwordInputDialog2.addPasswordInputDialogListener(new passwordInputDialogEventHandler());
        int w = passwordInputDialog2.getWidth();
        int h = passwordInputDialog2.getHeight();
        passwordInputDialog2.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
        passwordInputDialog2.setJudulText("Input Password Melakukan Proses Adjustment");
        passwordInputDialog2.setVisible(true);
    }

    private void belumDiprosesBtnMouseClicked(MouseEvent evt) {
        this.tampilkanClick("BELUM DIPROSES");
    }

    private class confirmDialogEventHandler
    implements ConfirmationDialogListener {
        private confirmDialogEventHandler() {
        }

        @Override
        public void yesClick() {
            ReportStokOpnamPanel.this.canGoThrough = true;
        }

        @Override
        public void noClick() {
            ReportStokOpnamPanel.this.canGoThrough = false;
        }
    }

    private class passwordInputDialogEventHandler
    implements PasswordInputListener {
        private passwordInputDialogEventHandler() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR") || karyawan.getRole().equalsIgnoreCase("INVENTORY")) {
                String id = ReportStokOpnamPanel.this.stokOpnamTM.getValueAt(ReportStokOpnamPanel.this.stokOpnamTable.getSelectedRow(), 0).toString();
                StokOpnam stokOpnam = ReportStokOpnamPanel.this.stokOpnamService.getStokOpnam(id);
                if (stokOpnam.getStatusOpnam().equals("SUDAH DIPROSES")) {
                    JOptionPane.showMessageDialog(null, "Stok Opnam Telah Diproses");
                } else {
                    ReportStokOpnamPanel.this.canGoThrough = false;
                    ConfirmationDialog confirmationDialog = new ConfirmationDialog(null, true, "Data Stok Opnam Akan Diproses dan Tidak Dapat Diubah Lagi! Lanjutkan?");
                    confirmationDialog.addListener(new confirmDialogEventHandler());
                    int w = confirmationDialog.getWidth();
                    int h = confirmationDialog.getHeight();
                    confirmationDialog.setLocation((ReportStokOpnamPanel.this.screenWidth - w) / 2, (ReportStokOpnamPanel.this.screenHeight - h) / 2);
                    confirmationDialog.setVisible(true);
                    if (ReportStokOpnamPanel.this.canGoThrough) {
                        List<StokOpnamDetail> stokOpnamDetails = ReportStokOpnamPanel.this.stokOpnamDetailService.getStokSelisihOpnamDetails(stokOpnam.getId() + "");
                        for (StokOpnamDetail stokOpnamDetail : stokOpnamDetails) {
                            StockCommit stockCommit2;
                            float jumlahSelisih = stokOpnamDetail.getJumlahToko() - stokOpnamDetail.getJumlahKomp();
                            if (stokOpnam.getLokasi().equalsIgnoreCase("Toko")) {
                                ReportStokOpnamPanel.this.barangService.updateStokBarangToko(jumlahSelisih, stokOpnamDetail.getBarangId());
                                stockCommit2 = new StockCommit();
                                stockCommit2.setIdBarang(stokOpnamDetail.getBarangId());
                                stockCommit2.setIdTransaksi(stokOpnam.getId() + "");
                                stockCommit2.setQueryType("tambah");
                                stockCommit2.setStockType("toko");
                                stockCommit2.setTransactionType("Stok Opnam");
                                stockCommit2.setJumlahBarang(jumlahSelisih);
                                ReportStokOpnamPanel.this.sinkronisasiBarangService.insertCommit(stockCommit2);
                                continue;
                            }
                            if (!stokOpnam.getLokasi().equalsIgnoreCase("Gudang")) continue;
                            ReportStokOpnamPanel.this.barangService.updateStokBarangGudang(jumlahSelisih, stokOpnamDetail.getBarangId());
                            stockCommit2 = new StockCommit();
                            stockCommit2.setIdBarang(stokOpnamDetail.getBarangId());
                            stockCommit2.setIdTransaksi(stokOpnam.getId() + "");
                            stockCommit2.setQueryType("tambah");
                            stockCommit2.setStockType("gudang");
                            stockCommit2.setTransactionType("Stok Opnam");
                            stockCommit2.setJumlahBarang(jumlahSelisih);
                            ReportStokOpnamPanel.this.sinkronisasiBarangService.insertCommit(stockCommit2);
                        }
                        stokOpnam.setStatusOpnam("SUDAH DIPROSES");
                        stokOpnam.setUpdated(new Date());
                        ReportStokOpnamPanel.this.stokOpnamService.updateStokOpnam(stokOpnam);
                        JOptionPane.showMessageDialog(null, "Data Stok Opnam Telah Diproses");
                        ReportStokOpnamPanel.this.stokOpnamTM.setValueAt("SUDAH DIPROSES", ReportStokOpnamPanel.this.stokOpnamTable.getSelectedRow(), 3);
                        ReportStokOpnamPanel.this.syncService.doSyncStokOpnamAndStokOpnamUnsynced();
                    }
                }
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}

