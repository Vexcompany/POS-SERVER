/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.DateFormat;
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
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import minimart.engine.controller.Helper;
import minimart.engine.model.Barang;
import minimart.engine.model.Customer;
import minimart.engine.model.PenjualanParsial;
import minimart.engine.model.PenjualanParsialDetail;
import minimart.engine.service.BarangService;
import minimart.engine.service.CustomerService;
import minimart.engine.service.PenjualanParsialDetailService;
import minimart.engine.service.PenjualanParsialService;
import minimart.ui.listener.ConfirmationDialogListener;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.listener.PenjualanDetailDialogListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.ConfirmationDialog;
import minimart.ui.view.PenjualanDetailDialog1;
import minimart.ui.view.ReprintDialogParsial;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class PenjualanParsialPanel
extends JPanel {
    private JTextField customerIdField;
    private JLabel customerNameField;
    private JButton detailButton;
    private JButton exitButton;
    private JTextField idPenjualanField;
    private JLabel jLabel1;
    private JLabel jLabel11;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JScrollPane jScrollPane1;
    private JTable penjualanTable;
    private JButton saveButton;
    private JFormattedTextField tanggalField;
    private JFormattedTextField totalField;
    private JTextField totalItemField;
    private int screenWidth;
    private int screenHeight;
    private DefaultPanelListener defaultPanelListener;
    private PenjualanParsial penjualanParsial = new PenjualanParsial();
    private String[] transactionColumn = new String[]{"Kode Barang", "Nama Barang", "Qty", "Sat", "Harga", "Total", "Disc", "Harga Akhir"};
    private BarangService barangService = new BarangService();
    private CustomerService customerService = new CustomerService();
    private Customer customer;
    private Helper helper = Helper.getInstance();
    private List<PenjualanParsialDetail> penjualanParsialDetails;
    private PenjualanParsialService penjualanParsialService = new PenjualanParsialService();
    private PenjualanParsialDetailService penjualanParsialDetailService = new PenjualanParsialDetailService();
    private boolean canGoThrough;
    private String status;
    private DefaultTableModel penjualanTableModel = new DefaultTableModel(new String[0][1], this.transactionColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public PenjualanParsialPanel() {
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        TableColumn col1 = this.penjualanTable.getColumnModel().getColumn(1);
        TableColumn col2 = this.penjualanTable.getColumnModel().getColumn(2);
        TableColumn col3 = this.penjualanTable.getColumnModel().getColumn(3);
        int width = 20;
        col1.setPreferredWidth(130);
        col2.setPreferredWidth(width);
        col3.setPreferredWidth(width);
        this.tanggalField.setValue(new Date());
        this.customer = this.customerService.getCustomerById("C");
        this.customerNameField.setText(this.customer.getNama() + "-" + this.customer.getAlamat());
        this.customerIdField.setText("C");
        this.penjualanTable.requestFocus();
        this.totalField.setValue(0);
    }

    public void setDefaultFocus() {
        this.idPenjualanField.requestFocusInWindow();
    }

    public void addListener(DefaultPanelListener listener) {
        this.defaultPanelListener = listener;
    }

    private void saveDataParsial() {
        String idGenerate = this.helper.generateCodeGeneral(this.penjualanParsialService.getLastId());
        System.out.println(idGenerate);
        int result = 0;
        this.penjualanParsial.setCustomerId(this.customerIdField.getText());
        this.penjualanParsial.setKaryawanId(this.helper.getKaryawan().getId());
        this.penjualanParsial.setTanggal(new Date());
        if (this.status.equalsIgnoreCase("CREATE")) {
            this.penjualanParsial.setId(idGenerate);
            result = this.penjualanParsialService.create(this.penjualanParsial);
        } else if (this.status.equalsIgnoreCase("UPDATE")) {
            result = this.penjualanParsialService.update(this.penjualanParsial);
            this.penjualanParsialDetailService.delete(this.penjualanParsial.getId());
        }
        if (result == 1) {
            for (int a = 0; a < this.penjualanTableModel.getRowCount(); ++a) {
                PenjualanParsialDetail penjualanParsialDetail = new PenjualanParsialDetail();
                penjualanParsialDetail.setPenjualanParsialId(this.penjualanParsial.getId());
                penjualanParsialDetail.setBarangId(this.penjualanTableModel.getValueAt(a, 0).toString());
                penjualanParsialDetail.setDiscount(Long.parseLong(this.penjualanTableModel.getValueAt(a, 6).toString()));
                penjualanParsialDetail.setHargaJual(Long.parseLong(this.penjualanTableModel.getValueAt(a, 4).toString()));
                penjualanParsialDetail.setJumlahBarang(Float.parseFloat(this.penjualanTableModel.getValueAt(a, 2).toString()));
                penjualanParsialDetail.setSatuan(this.penjualanTableModel.getValueAt(a, 3).toString());
                penjualanParsialDetail.setTotalHarga(Long.parseLong(this.penjualanTableModel.getValueAt(a, 7).toString()));
                this.penjualanParsialDetailService.create(penjualanParsialDetail);
            }
        }
        this.penjualanParsialDetails = this.penjualanParsialDetailService.getPenjualanParsials(this.penjualanParsial.getId());
        PrinterJob job = PrinterJob.getPrinterJob();
        PageFormat pf = job.defaultPage();
        Paper p = pf.getPaper();
        p.setSize(p.getWidth(), p.getHeight() + 1500.0);
        p.setImageableArea(15.0, 0.0, p.getWidth(), p.getHeight() + 1500.0);
        pf.setPaper(p);
        Book book = new Book();
        book.append(new printer(), pf);
        job.setPageable(book);
        try {
            job.print();
        }
        catch (PrinterException printerException) {
            // empty catch block
        }
        this.resetData();
    }

    private void resetData() {
        int rowSize = this.penjualanTableModel.getRowCount();
        for (int a = 0; a < rowSize; ++a) {
            this.penjualanTableModel.removeRow(0);
        }
        this.totalField.setValue(0);
        this.totalItemField.setText("0");
        this.idPenjualanField.setText("");
        this.customerIdField.setText("C");
        this.customerNameField.setText("CASH");
    }

    private void detailClick() {
        this.penjualanTable.requestFocus();
        if (this.penjualanTableModel.getRowCount() == 0) {
            PenjualanDetailDialog1 penjualanDetailDialog = new PenjualanDetailDialog1(null, true, this.penjualanTableModel, this.customer.getId(), "", false, ((Integer)this.totalField.getValue()).intValue(), "CASH");
            penjualanDetailDialog.addListener(new penjualanDetailDialogEventHandler());
            int w = penjualanDetailDialog.getWidth();
            int h = penjualanDetailDialog.getHeight();
            penjualanDetailDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            penjualanDetailDialog.setVisible(true);
        }
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.idPenjualanField = new JTextField();
        this.jScrollPane1 = new JScrollPane();
        this.penjualanTable = new JTable();
        this.detailButton = new JButton();
        this.saveButton = new JButton();
        this.exitButton = new JButton();
        this.totalField = new JFormattedTextField();
        this.jLabel11 = new JLabel();
        this.tanggalField = new JFormattedTextField();
        this.jLabel3 = new JLabel();
        this.totalItemField = new JTextField();
        this.jLabel4 = new JLabel();
        this.customerIdField = new JTextField();
        this.jLabel5 = new JLabel();
        this.jLabel6 = new JLabel();
        this.jLabel7 = new JLabel();
        this.jLabel8 = new JLabel();
        this.jLabel9 = new JLabel();
        this.customerNameField = new JLabel();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setText("Tanggal");
        this.jLabel2.setText("ID Penjualan");
        this.idPenjualanField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanParsialPanel.this.idPenjualanFieldKeyPressed(evt);
            }
        });
        this.penjualanTable.setModel(this.penjualanTableModel);
        this.penjualanTable.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanParsialPanel.this.penjualanTableKeyPressed(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.penjualanTable);
        this.detailButton.setText("Detail");
        this.detailButton.setPreferredSize(new Dimension(74, 23));
        this.detailButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanParsialPanel.this.detailButtonKeyPressed(evt);
            }
        });
        this.saveButton.setLabel("Save");
        this.saveButton.setPreferredSize(new Dimension(74, 23));
        this.saveButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PenjualanParsialPanel.this.saveButtonActionPerformed(evt);
            }
        });
        this.saveButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanParsialPanel.this.saveButtonKeyPressed(evt);
            }
        });
        this.exitButton.setText("Exit");
        this.exitButton.setPreferredSize(new Dimension(74, 23));
        this.exitButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PenjualanParsialPanel.this.exitButtonActionPerformed(evt);
            }
        });
        this.exitButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanParsialPanel.this.exitButtonKeyPressed(evt);
            }
        });
        this.totalField.setBackground(new Color(255, 255, 0));
        this.totalField.setEditable(false);
        this.totalField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("\u00a4#,##0.--"))));
        this.totalField.setText("0");
        this.totalField.setFont(new Font("Tahoma", 1, 12));
        this.jLabel11.setFont(new Font("Tahoma", 1, 12));
        this.jLabel11.setText("Total");
        this.tanggalField.setEditable(false);
        this.tanggalField.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(DateFormat.getDateInstance(1))));
        this.jLabel3.setText("Total Item");
        this.totalItemField.setText("0");
        this.jLabel4.setText("Customer");
        this.customerIdField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanParsialPanel.this.customerIdFieldKeyPressed(evt);
            }
        });
        this.jLabel5.setText(":");
        this.jLabel6.setText(":");
        this.jLabel7.setText(":");
        this.jLabel8.setText(":");
        this.jLabel9.setText(":");
        this.customerNameField.setText(" ");
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, -1, 780, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addComponent(this.jLabel1).addGap(41, 41, 41)).addComponent(this.jLabel2)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jLabel7).addComponent(this.jLabel6, GroupLayout.Alignment.TRAILING).addComponent(this.jLabel5, GroupLayout.Alignment.TRAILING)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.tanggalField).addComponent(this.idPenjualanField, -2, 137, -2)).addGap(102, 102, 102).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel3).addComponent(this.jLabel11)).addGap(26, 26, 26).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jLabel9).addComponent(this.jLabel8))).addGroup(layout.createSequentialGroup().addComponent(this.customerIdField, -2, 91, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.customerNameField, -1, -1, Short.MAX_VALUE))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.totalItemField, -2, 68, -2).addComponent(this.totalField, -2, 148, -2)).addGap(0, 0, Short.MAX_VALUE)))).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(278, 278, 278).addComponent(this.detailButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.saveButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.exitButton, -2, -1, -2)).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel4, -2, 61, -2))).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.tanggalField, -2, -1, -2).addComponent(this.jLabel11).addComponent(this.jLabel5)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.idPenjualanField, -2, -1, -2).addComponent(this.jLabel3).addComponent(this.jLabel7)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4).addComponent(this.customerIdField, -2, -1, -2).addComponent(this.jLabel6).addComponent(this.customerNameField))).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.totalField, -2, -1, -2).addComponent(this.jLabel8)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.totalItemField, -2, -1, -2).addComponent(this.jLabel9)))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.detailButton, -2, -1, -2).addComponent(this.saveButton, -2, -1, -2).addComponent(this.exitButton, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, -1, -2).addGap(73, 73, 73)));
    }

    private void exitButtonActionPerformed(ActionEvent evt) {
        this.defaultPanelListener.exitClick();
    }

    private void idPenjualanFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (this.idPenjualanField.getText().equals("")) {
                this.idPenjualanField.setText("DATA BARU");
                this.status = "CREATE";
                this.penjualanTable.requestFocus();
            } else if (!this.idPenjualanField.getText().equals("")) {
                this.penjualanParsial = this.penjualanParsialService.getPenjualanParsial(this.idPenjualanField.getText());
                if (this.penjualanParsial == null) {
                    JOptionPane.showMessageDialog(null, "DATA TIDAK DITEMUKAN");
                } else {
                    this.status = "UPDATE";
                    this.customer = this.customerService.getCustomerById(this.penjualanParsial.getCustomerId());
                    this.customerIdField.setText(this.customer.getId());
                    this.customerNameField.setText(this.customer.getNama());
                    List<PenjualanParsialDetail> parsialDetails = this.penjualanParsialDetailService.getPenjualanParsials(this.penjualanParsial.getId());
                    for (PenjualanParsialDetail parsialDetail : parsialDetails) {
                        Object[] rowValue = new Object[]{parsialDetail.getBarangId(), this.barangService.getBarang(parsialDetail.getBarangId()).getNama(), Float.valueOf(parsialDetail.getJumlahBarang()), parsialDetail.getSatuan(), parsialDetail.getHargaJual(), Float.valueOf((float)parsialDetail.getHargaJual() * parsialDetail.getJumlahBarang()), parsialDetail.getDiscount(), parsialDetail.getTotalHarga()};
                        this.penjualanTableModel.addRow(rowValue);
                    }
                    this.penjualanTable.requestFocus();
                }
            }
        } else if (evt.getKeyCode() == 27) {
            this.defaultPanelListener.exitClick();
        } else if (evt.getKeyCode() == 19 || evt.getKeyCode() == 36) {
            System.out.println("REPRINT");
            ReprintDialogParsial reprintDialog = new ReprintDialogParsial(null, true);
            int w = reprintDialog.getWidth();
            int h = reprintDialog.getHeight();
            reprintDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            reprintDialog.setVisible(true);
        }
    }

    private void detailButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.detailClick();
        } else if (evt.getKeyCode() == 39) {
            this.saveButton.requestFocus();
        } else if (evt.getKeyCode() == 27 || evt.getKeyCode() == 38) {
            this.customerIdField.requestFocus();
            this.customerIdField.selectAll();
        }
    }

    private void penjualanTableKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            PenjualanDetailDialog1 penjualanDetailDialog = new PenjualanDetailDialog1(null, true, this.penjualanTableModel, this.customer.getId(), "", false, ((Integer)this.totalField.getValue()).intValue(), "CASH");
            if (this.penjualanTable.getSelectedRow() != -1) {
                penjualanDetailDialog.loadKodeBarang(this.penjualanTableModel.getValueAt(this.penjualanTable.getSelectedRow(), 0).toString());
                penjualanDetailDialog.loadPenjualan(this.penjualanTable.getSelectedRow());
            }
            penjualanDetailDialog.escapeClick();
            penjualanDetailDialog.addListener(new penjualanDetailDialogEventHandler());
            int w = penjualanDetailDialog.getWidth();
            int h = penjualanDetailDialog.getHeight();
            penjualanDetailDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            penjualanDetailDialog.setVisible(true);
        } else if (evt.getKeyCode() == 112) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Customer");
            sd.addSearchDialogListener(new searchCustomerDialogEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
        } else if (evt.getKeyCode() == 27) {
            int a = JOptionPane.showConfirmDialog(null, "Akhiri Entry Data?", "Akhiri Entry Data", 0, 3);
            if (a == 0) {
                Float total = Float.valueOf(0.0f);
                for (int x = 0; x < this.penjualanTableModel.getRowCount(); ++x) {
                    total = Float.valueOf(total.floatValue() + Float.parseFloat(this.penjualanTableModel.getValueAt(x, 7).toString()));
                }
                this.saveButton.requestFocus();
                this.totalField.setValue(total);
                this.totalItemField.setText(this.penjualanTableModel.getRowCount() + "");
            }
        } else if (evt.getKeyCode() == 127) {
            int a;
            if (this.penjualanTable.getSelectedRow() != -1 && (a = JOptionPane.showConfirmDialog(null, "Hapus Data Ini?", "Delete Data", 0, 3)) == 0) {
                this.totalItemField.setText(Integer.parseInt(this.totalItemField.getText()) - 1 + "");
                this.totalField.setValue(Math.round(Float.parseFloat(this.totalField.getValue().toString()) - Float.parseFloat(this.penjualanTableModel.getValueAt(this.penjualanTable.getSelectedRow(), 7).toString())));
                this.penjualanTableModel.removeRow(this.penjualanTable.getSelectedRow());
            }
        } else if (evt.getKeyCode() == 113) {
            this.saveButton.requestFocus();
        } else if (evt.getKeyCode() == 19 || evt.getKeyCode() == 36) {
            System.out.println("REPRINT");
            ReprintDialogParsial reprintDialog = new ReprintDialogParsial(null, true);
            int w = reprintDialog.getWidth();
            int h = reprintDialog.getHeight();
            reprintDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            reprintDialog.setVisible(true);
        } else if (evt.getKeyCode() == 32) {
            String idGenerate = this.helper.generateCodeGeneral(this.penjualanParsialService.getLastId());
            System.out.println(idGenerate);
        }
    }

    private void saveButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.canGoThrough = false;
            ConfirmationDialog confirmationDialog = new ConfirmationDialog(null, true, "SIMPAN DAN AKHIRI PENJUALAN?");
            confirmationDialog.addListener(new confirmDialogEventHandler());
            int w = confirmationDialog.getWidth();
            int h = confirmationDialog.getHeight();
            confirmationDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            confirmationDialog.setVisible(true);
            if (this.canGoThrough) {
                this.idPenjualanField.requestFocus();
                this.saveDataParsial();
            }
        } else if (evt.getKeyCode() == 39) {
            this.exitButton.requestFocus();
        } else if (evt.getKeyCode() == 38 || evt.getKeyCode() == 37) {
            this.detailButton.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.idPenjualanField.requestFocus();
            this.idPenjualanField.selectAll();
        }
    }

    private void saveButtonActionPerformed(ActionEvent evt) {
        this.canGoThrough = false;
        ConfirmationDialog confirmationDialog = new ConfirmationDialog(null, true, "SIMPAN DAN AKHIRI PENJUALAN?");
        confirmationDialog.addListener(new confirmDialogEventHandler());
        int w = confirmationDialog.getWidth();
        int h = confirmationDialog.getHeight();
        confirmationDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
        confirmationDialog.setVisible(true);
        if (this.canGoThrough) {
            this.idPenjualanField.requestFocus();
            this.saveDataParsial();
        }
    }

    private void exitButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.defaultPanelListener.exitClick();
        } else if (evt.getKeyCode() == 38 || evt.getKeyCode() == 37) {
            this.saveButton.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.idPenjualanField.requestFocus();
            this.idPenjualanField.selectAll();
        }
    }

    private void customerIdFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (this.customerIdField.getText().equals("")) {
                SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Customer");
                sd.addSearchDialogListener(new searchCustomerDialogEventHandler());
                int w = sd.getWidth();
                int h = sd.getHeight();
                sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                sd.setVisible(true);
            } else {
                this.customer = this.customerService.getCustomerById(this.customerIdField.getText());
                if (this.customer == null) {
                    JOptionPane.showMessageDialog(null, "Data Customer Tidak Ditemukan");
                } else {
                    this.customerNameField.setText(this.customer.getNama() + "-" + this.customer.getAlamat());
                    this.penjualanTable.requestFocus();
                }
            }
        } else if (evt.getKeyCode() == 113) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Customer");
            sd.addSearchDialogListener(new searchCustomerDialogEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
        } else if (evt.getKeyCode() == 38 || evt.getKeyCode() == 27) {
            this.idPenjualanField.requestFocus();
            this.idPenjualanField.selectAll();
        }
    }

    private int rataKanan(String price, int x) {
        int len = price.length();
        for (int i = 0; i < len; ++i) {
            x -= 4;
        }
        return x;
    }

    class printer
    implements Printable {
        printer() {
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
            int xRataKanan = 160;
            g.setFont(new Font("Times New Roman", 0, 8));
            Customer customer = PenjualanParsialPanel.this.customerService.getCustomerById(PenjualanParsialPanel.this.customerIdField.getText());
            String pattern = "dd-MM-yyyy";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            String tanggal = format.format(PenjualanParsialPanel.this.penjualanParsial.getTanggal());
            g.drawString("Cust: " + customer.getId() + " - " + customer.getNama(), x + 0, y + yK);
            g.drawString("Tgl " + tanggal, x + 0, y + (yK += 8));
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            g.drawString("No Faktur. " + PenjualanParsialPanel.this.penjualanParsial.getId(), x + 0, y + (yK += yP));
            g.drawString("Kasir: " + PenjualanParsialPanel.this.helper.getKaryawan().getNama(), x + 100, y + yK);
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            for (PenjualanParsialDetail penjualanParsialDetail : PenjualanParsialPanel.this.penjualanParsialDetails) {
                Barang barang = PenjualanParsialPanel.this.barangService.getBarang(penjualanParsialDetail.getBarangId());
                g.drawString("(" + penjualanParsialDetail.getJumlahBarang() + penjualanParsialDetail.getSatuan() + ") " + barang.getNama(), x + 0, y + (yK += yP));
            }
            g.drawString("-----------------------------------------------------------------------------------------------", x + 0, y + (yK += 8));
            g.drawString("TERIMA KASIH", x + 0, y + (yK += yP));
            g.drawString("SEGERA BAYARKAN KE KASIR", x + 0, y + (yK += yP));
            g.drawString("MENGGUNAKAN STRUK INI", x + 0, y + (yK += yP));
            return 0;
        }
    }

    private class penjualanDetailDialogEventHandler
    implements PenjualanDetailDialogListener {
        private penjualanDetailDialogEventHandler() {
        }

        @Override
        public void saveClick(Object[] rowValue, boolean removeRow) {
            int a;
            boolean newData = true;
            for (a = 0; a < PenjualanParsialPanel.this.penjualanTableModel.getRowCount(); ++a) {
                if (!PenjualanParsialPanel.this.penjualanTableModel.getValueAt(a, 0).equals(rowValue[0])) continue;
                newData = false;
                break;
            }
            if (newData) {
                PenjualanParsialPanel.this.penjualanTableModel.addRow(rowValue);
                PenjualanParsialPanel.this.totalField.setValue(Math.round(Float.parseFloat(PenjualanParsialPanel.this.totalField.getValue().toString()) + Float.parseFloat(rowValue[7].toString())));
                PenjualanParsialPanel.this.totalItemField.setText(Integer.parseInt(PenjualanParsialPanel.this.totalItemField.getText()) + 1 + "");
            } else {
                PenjualanParsialPanel.this.totalField.setValue(Math.round(Float.parseFloat(PenjualanParsialPanel.this.totalField.getValue().toString()) - Float.parseFloat(PenjualanParsialPanel.this.penjualanTableModel.getValueAt(a, 7).toString())));
                PenjualanParsialPanel.this.totalField.setValue(Math.round(Float.parseFloat(PenjualanParsialPanel.this.totalField.getValue().toString()) + Float.parseFloat(rowValue[7].toString())));
                PenjualanParsialPanel.this.penjualanTableModel.removeRow(a);
                PenjualanParsialPanel.this.penjualanTableModel.addRow(rowValue);
            }
        }

        @Override
        public void deleteClick(String idBarang) {
            for (int a = 0; a < PenjualanParsialPanel.this.penjualanTableModel.getRowCount(); ++a) {
                if (!PenjualanParsialPanel.this.penjualanTableModel.getValueAt(a, 0).equals(idBarang)) continue;
                PenjualanParsialPanel.this.totalItemField.setText(Integer.parseInt(PenjualanParsialPanel.this.totalItemField.getText()) - 1 + "");
                PenjualanParsialPanel.this.totalField.setValue(Math.round(Float.parseFloat(PenjualanParsialPanel.this.totalField.getValue().toString()) - Float.parseFloat(PenjualanParsialPanel.this.penjualanTableModel.getValueAt(a, 7).toString())));
                PenjualanParsialPanel.this.penjualanTableModel.removeRow(a);
                break;
            }
        }

        @Override
        public void refreshData() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public DefaultTableModel getPenjualanTableModel() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void changeButtonPromoTebusMurah() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void changeButtonPromoTebus(String state, List<Object[]> data) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class searchPanelCustomerEventHandler
    implements SearchPanelListener {
        private searchPanelCustomerEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            PenjualanParsialPanel.this.customer = PenjualanParsialPanel.this.customerService.getCustomerById(chooseValue);
            PenjualanParsialPanel.this.customerIdField.setText(PenjualanParsialPanel.this.customer.getId());
            PenjualanParsialPanel.this.customerNameField.setText(PenjualanParsialPanel.this.customer.getNama() + "-" + PenjualanParsialPanel.this.customer.getAlamat());
            PenjualanParsialPanel.this.penjualanTable.requestFocus();
        }
    }

    private class searchCustomerDialogEventHandler
    implements SearchDialogListener {
        private searchCustomerDialogEventHandler() {
        }

        @Override
        public void searchClick(String searchWord) {
            Object[] colName = new String[]{"KODE CUSTOMER", "NAMA CUSTOMER", "ALAMAT", "KOTA"};
            DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
            List<Customer> customers = PenjualanParsialPanel.this.customerService.getCustomers(searchWord);
            for (Customer customer : customers) {
                Object[] temp = new String[]{customer.getId(), customer.getNama(), customer.getAlamat(), customer.getKota()};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Customer");
            sp.addSearchPanelListener(new searchPanelCustomerEventHandler());
            sp.setVisible(true);
        }

        public void dispose() {
        }
    }

    private class confirmDialogEventHandler
    implements ConfirmationDialogListener {
        private confirmDialogEventHandler() {
        }

        @Override
        public void yesClick() {
            PenjualanParsialPanel.this.canGoThrough = true;
        }

        @Override
        public void noClick() {
            PenjualanParsialPanel.this.canGoThrough = false;
        }
    }
}

