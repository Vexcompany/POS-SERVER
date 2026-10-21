/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view.report;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import minimart.engine.controller.Helper;
import minimart.engine.model.Pembelian;
import minimart.engine.service.KaryawanService;
import minimart.engine.service.PelunasanHutangService;
import minimart.engine.service.PembelianService;
import minimart.engine.service.SupplierService;
import minimart.ui.listener.ConfirmationDialogListener;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.listener.PelunasanHutangDialogListener;
import minimart.ui.view.PelunasanHutangDialog;
import minimart.ui.view.report.ReportHutangDetail;

public class ReportHutangPanel
extends JPanel {
    private JButton detailButton;
    private JButton exitButton;
    private JLabel jLabel3;
    private JScrollPane jScrollPane1;
    private JButton pelunasanBtn;
    private JTable reportTable;
    private JFormattedTextField totalPembelianField;
    private String[] reportColumn = new String[]{"ID", "Tanggal", "Pembayaran", "Jatuh Tempo", "Supplier", "Total", "Titipan", "Keterangan", "STATUS PELUNASAN"};
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private KaryawanService karyawanService = new KaryawanService();
    private PembelianService pembelianService = new PembelianService();
    private SupplierService supplierService = new SupplierService();
    private PelunasanHutangService pelunasanHutangService = new PelunasanHutangService();
    private Date startDate;
    private Date endDate;
    private DefaultPanelListener defaultPanelListener;
    private int screenWidth;
    private int screenHeight;
    private String idSupplier;
    private String keterangan;
    private Helper helper = Helper.getInstance();
    private boolean canGoThrough;

    public ReportHutangPanel(String idSupplier, String keterangan) {
        this.initComponents();
        this.startDate = this.startDate;
        this.endDate = this.endDate;
        this.idSupplier = idSupplier;
        this.keterangan = keterangan;
        this.loadData();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    }

    public void addListener(DefaultPanelListener listener) {
        this.defaultPanelListener = listener;
    }

    private void loadData() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<Object[]> pembelians = this.keterangan.equals("") ? this.pembelianService.getHutang(this.idSupplier) : this.pembelianService.getHutangSudahJatuhTempo(this.idSupplier);
        long totalPembelian = 0L;
        for (Object[] pembelian : pembelians) {
            Object[] rowData = new Object[]{pembelian[0], pembelian[1], pembelian[2], pembelian[3], pembelian[4], pembelian[5], pembelian[6], ((Date)pembelian[3]).before(new Date()) || ((Date)pembelian[3]).equals(new Date()) ? "SUDAH JATUH TEMPO" : "BELUM JATUH TEMPO", pembelian[7]};
            totalPembelian += ((Long)pembelian[5]).longValue();
            this.reportTableModel.addRow(rowData);
        }
        this.totalPembelianField.setValue(totalPembelian);
    }

    private void initComponents() {
        this.jScrollPane1 = new JScrollPane();
        this.reportTable = new JTable();
        this.detailButton = new JButton();
        this.exitButton = new JButton();
        this.jLabel3 = new JLabel();
        this.totalPembelianField = new JFormattedTextField();
        this.pelunasanBtn = new JButton();
        this.setPreferredSize(new Dimension(800, 600));
        this.reportTable.setModel(this.reportTableModel);
        this.jScrollPane1.setViewportView(this.reportTable);
        this.detailButton.setText("Detail");
        this.detailButton.setPreferredSize(new Dimension(83, 23));
        this.detailButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportHutangPanel.this.detailButtonActionPerformed(evt);
            }
        });
        this.exitButton.setText("Exit");
        this.exitButton.setPreferredSize(new Dimension(83, 23));
        this.exitButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportHutangPanel.this.exitButtonActionPerformed(evt);
            }
        });
        this.exitButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportHutangPanel.this.exitButtonKeyPressed(evt);
            }
        });
        this.jLabel3.setFont(new Font("Tahoma", 0, 18));
        this.jLabel3.setText("Total Pembelian");
        this.totalPembelianField.setEditable(false);
        this.totalPembelianField.setBackground(new Color(255, 255, 0));
        this.totalPembelianField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("\u00a4 #,##0.--"))));
        this.totalPembelianField.setFont(new Font("Tahoma", 0, 18));
        this.pelunasanBtn.setText("Pelunasan");
        this.pelunasanBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportHutangPanel.this.pelunasanBtnActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(217, 217, 217).addComponent(this.detailButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.pelunasanBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.exitButton, -2, -1, -2).addGap(0, 0, Short.MAX_VALUE)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1, -1, 780, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addContainerGap(-1, Short.MAX_VALUE).addComponent(this.jLabel3).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.totalPembelianField, -2, 150, -2))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(31, 31, 31).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel3, GroupLayout.Alignment.TRAILING).addComponent(this.totalPembelianField, GroupLayout.Alignment.TRAILING, -2, 36, -2)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.detailButton, -2, -1, -2).addComponent(this.exitButton, -2, -1, -2).addComponent(this.pelunasanBtn)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, 440, -2).addGap(46, 46, 46)));
    }

    private void exitButtonActionPerformed(ActionEvent evt) {
        this.defaultPanelListener.exitClick();
    }

    private void detailButtonActionPerformed(ActionEvent evt) {
        if (this.reportTable.getSelectedRow() != -1) {
            String id = this.reportTableModel.getValueAt(this.reportTable.getSelectedRow(), 0).toString();
            ReportHutangDetail rpd = new ReportHutangDetail(null, true, id);
            int w = rpd.getWidth();
            int h = rpd.getHeight();
            rpd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            rpd.setVisible(true);
        }
    }

    private void exitButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.defaultPanelListener.exitClick();
        }
    }

    private void pelunasanBtnActionPerformed(ActionEvent evt) {
        Pembelian pembelian = this.pembelianService.getPembelian(this.reportTableModel.getValueAt(this.reportTable.getSelectedRow(), 0).toString());
        if (pembelian.getPelunasan().equals("LUNAS")) {
            JOptionPane.showMessageDialog(null, "Pembelian Sudah Lunas");
        } else if (pembelian.getKeterangan().equals("BELUM DIPROSES")) {
            JOptionPane.showMessageDialog(null, "Pembelian Belum Diproses");
        } else {
            PelunasanHutangDialog phd = new PelunasanHutangDialog(null, true, this.reportTableModel.getValueAt(this.reportTable.getSelectedRow(), 0) + "", this.reportTable.getSelectedRow());
            phd.addListener(new pelunasanHutangDialogEventHandler());
            int w = phd.getWidth();
            int h = phd.getHeight();
            phd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            phd.setVisible(true);
        }
    }

    private class pelunasanHutangDialogEventHandler
    implements PelunasanHutangDialogListener {
        private pelunasanHutangDialogEventHandler() {
        }

        @Override
        public void lunasSaveClick(int row) {
            ReportHutangPanel.this.reportTableModel.setValueAt("LUNAS", row, 8);
            ReportHutangPanel.this.reportTableModel.setValueAt("0", row, 6);
        }

        @Override
        public void sebagianSaveClick(long titipan, int row) {
            if (titipan >= Long.parseLong(ReportHutangPanel.this.reportTable.getValueAt(row, 5) + "")) {
                ReportHutangPanel.this.reportTableModel.setValueAt("LUNAS", row, 8);
                Object obj = ReportHutangPanel.this.reportTableModel.getValueAt(row, 5);
                ReportHutangPanel.this.reportTableModel.setValueAt(obj, row, 6);
            } else if (titipan < Long.parseLong(ReportHutangPanel.this.reportTable.getValueAt(row, 5) + "")) {
                ReportHutangPanel.this.reportTableModel.setValueAt(titipan, row, 6);
            }
        }
    }

    private class confirmDialogEventHandler
    implements ConfirmationDialogListener {
        private confirmDialogEventHandler() {
        }

        @Override
        public void yesClick() {
            ReportHutangPanel.this.canGoThrough = true;
        }

        @Override
        public void noClick() {
            ReportHutangPanel.this.canGoThrough = false;
        }
    }
}

