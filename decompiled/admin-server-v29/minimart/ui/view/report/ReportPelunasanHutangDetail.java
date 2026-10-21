/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view.report;

import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import minimart.engine.controller.Helper;
import minimart.engine.model.Barang;
import minimart.engine.model.Pembelian;
import minimart.engine.model.PembelianDetail;
import minimart.engine.model.Supplier;
import minimart.engine.service.BarangService;
import minimart.engine.service.PembelianDetailService;
import minimart.engine.service.PembelianService;
import minimart.engine.service.SupplierService;
import minimart.ui.listener.ConfirmationDialogListener;

public class ReportPelunasanHutangDetail
extends JDialog {
    private JFormattedTextField dateField;
    private JTextField discField;
    private JTextField idField;
    private JTextField idSuppField;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JScrollPane jScrollPane1;
    private JButton keluarBtn;
    private JFormattedTextField netTotalField;
    private JTextField ppnField;
    private JTable reportTable;
    private JLabel suppNameField;
    private String[] reportColumn = new String[]{"Kode Barang", "Nama Barang", "Qty", "Sat", "Harga", "Total", "Disc", "Harga Akhir"};
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn);
    private PembelianDetailService pembelianDetailService = new PembelianDetailService();
    private SupplierService supplierService = new SupplierService();
    private PembelianService pembelianService = new PembelianService();
    private BarangService barangService = new BarangService();
    private Helper helper = Helper.getInstance();
    private String idPembelian;
    private Pembelian pembelian;
    private int screenWidth = 0;
    private int screenHeight = 0;
    private boolean canGoThrough;

    public ReportPelunasanHutangDetail(Frame parent, boolean modal, String idPembelian) {
        super(parent, modal);
        this.initComponents();
        TableColumn col1 = this.reportTable.getColumnModel().getColumn(1);
        TableColumn col2 = this.reportTable.getColumnModel().getColumn(2);
        TableColumn col3 = this.reportTable.getColumnModel().getColumn(3);
        int width = 20;
        col1.setPreferredWidth(130);
        col2.setPreferredWidth(width);
        col3.setPreferredWidth(width);
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.idPembelian = idPembelian;
        this.pembelian = this.pembelianService.getPembelian(idPembelian);
        this.loadData(idPembelian);
    }

    private void loadData(String idPembelian) {
        Pembelian pembelian = this.pembelianService.getPembelian(idPembelian);
        List<PembelianDetail> pembelianDetails = this.pembelianDetailService.getPembelianDetails(idPembelian);
        Supplier supplierData = this.supplierService.getSupplier(pembelian.getSupplierId());
        this.dateField.setValue(pembelian.getTanggal());
        this.idSuppField.setText(supplierData.getId());
        this.suppNameField.setText(supplierData.getNama());
        this.netTotalField.setValue(pembelian.getTotal());
        this.discField.setText(pembelian.getDiscount1() + "");
        this.ppnField.setText(pembelian.getPpn() + "");
        this.idField.setText(idPembelian);
        for (PembelianDetail pembelianDetail : pembelianDetails) {
            Barang dataBarang = this.barangService.getBarang(pembelianDetail.getBarangId());
            Object[] rowValue = new Object[]{dataBarang.getId(), dataBarang.getNama(), Float.valueOf(pembelianDetail.getJumlahBarang()), pembelianDetail.getSatuan(), pembelianDetail.getHargaBeli(), pembelianDetail.getTotalHarga() + pembelianDetail.getDiscount2(), pembelianDetail.getDiscount2(), pembelianDetail.getTotalHarga()};
            this.reportTableModel.addRow(rowValue);
        }
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.dateField = new JFormattedTextField();
        this.idField = new JTextField();
        this.idSuppField = new JTextField();
        this.suppNameField = new JLabel();
        this.jLabel4 = new JLabel();
        this.jScrollPane1 = new JScrollPane();
        this.reportTable = new JTable();
        this.netTotalField = new JFormattedTextField();
        this.jLabel3 = new JLabel();
        this.jLabel5 = new JLabel();
        this.jLabel6 = new JLabel();
        this.ppnField = new JTextField();
        this.discField = new JTextField();
        this.keluarBtn = new JButton();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("Tanggal");
        this.jLabel2.setText("ID Pembelian");
        this.dateField.setEditable(false);
        this.dateField.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(DateFormat.getDateInstance(0))));
        this.dateField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPelunasanHutangDetail.this.dateFieldKeyPressed(evt);
            }
        });
        this.idField.setEditable(false);
        this.idField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPelunasanHutangDetail.this.idFieldKeyPressed(evt);
            }
        });
        this.idSuppField.setEditable(false);
        this.idSuppField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPelunasanHutangDetail.this.idSuppFieldKeyPressed(evt);
            }
        });
        this.suppNameField.setText("-");
        this.jLabel4.setText("Supplier");
        this.reportTable.setModel(this.reportTableModel);
        this.reportTable.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPelunasanHutangDetail.this.reportTableKeyPressed(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.reportTable);
        this.netTotalField.setEditable(false);
        this.netTotalField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("\u00a4#,##0.--"))));
        this.netTotalField.setText("0");
        this.netTotalField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPelunasanHutangDetail.this.netTotalFieldKeyPressed(evt);
            }
        });
        this.jLabel3.setText("Net Total");
        this.jLabel5.setText("PPN");
        this.jLabel6.setText("Disc");
        this.ppnField.setEditable(false);
        this.ppnField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPelunasanHutangDetail.this.ppnFieldKeyPressed(evt);
            }
        });
        this.discField.setEditable(false);
        this.discField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPelunasanHutangDetail.this.discFieldKeyPressed(evt);
            }
        });
        this.keluarBtn.setText("Keluar");
        this.keluarBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPelunasanHutangDetail.this.keluarBtnActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jLabel2).addComponent(this.jLabel4)).addGap(22, 22, 22).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.idSuppField, -1, 130, Short.MAX_VALUE).addComponent(this.dateField).addComponent(this.idField, -1, 130, Short.MAX_VALUE)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(398, 398, 398).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel5, -1, 23, Short.MAX_VALUE).addComponent(this.jLabel6, -1, -1, Short.MAX_VALUE))).addGroup(layout.createSequentialGroup().addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.suppNameField, -2, 304, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.jLabel3))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.netTotalField, -2, 141, -2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.discField, GroupLayout.Alignment.LEADING, -1, 57, Short.MAX_VALUE).addComponent(this.ppnField, GroupLayout.Alignment.LEADING)))).addComponent(this.jScrollPane1).addGroup(layout.createSequentialGroup().addComponent(this.keluarBtn).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.dateField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.idField, -2, -1, -2))).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel6).addComponent(this.discField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel5).addComponent(this.ppnField, -2, -1, -2)))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.idSuppField, -2, -1, -2).addComponent(this.suppNameField).addComponent(this.jLabel4).addComponent(this.netTotalField, -2, -1, -2).addComponent(this.jLabel3)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE).addComponent(this.keluarBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, 421, -2).addGap(60, 60, 60)));
        this.pack();
    }

    private void dateFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void idFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void idSuppFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void discFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void ppnFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void netTotalFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void reportTableKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void keluarBtnActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private class confirmDialogEventHandler
    implements ConfirmationDialogListener {
        private confirmDialogEventHandler() {
        }

        @Override
        public void yesClick() {
            ReportPelunasanHutangDetail.this.canGoThrough = true;
        }

        @Override
        public void noClick() {
            ReportPelunasanHutangDetail.this.canGoThrough = false;
        }
    }
}

