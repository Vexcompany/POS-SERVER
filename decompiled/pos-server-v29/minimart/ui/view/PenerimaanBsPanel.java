/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.toedter.calendar.JDateChooser
 */
package minimart.ui.view;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
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
import java.text.NumberFormat;
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
import javax.swing.table.TableColumn;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import minimart.engine.controller.Helper;
import minimart.engine.model.Barang;
import minimart.engine.model.Karyawan;
import minimart.engine.model.PenerimaanBs;
import minimart.engine.model.PenerimaanBsDetail;
import minimart.engine.service.BarangService;
import minimart.engine.service.KaryawanService;
import minimart.engine.service.MutasiBarangService;
import minimart.engine.service.PenerimaanBsDetailService;
import minimart.engine.service.PenerimaanBsService;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.listener.PenerimaanBsDialogListener;
import minimart.ui.view.PenerimaanBsDialog;

public class PenerimaanBsPanel
extends JPanel {
    private JDateChooser dateField;
    private JButton detailButton;
    private JButton exitButton;
    private JTextField idField;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JScrollPane jScrollPane1;
    private JTextField jumlahBarang;
    private JLabel karyawanLbl;
    private JComboBox lokasiCmb;
    private JTable penerimaanBsTable;
    private JButton saveButton;
    private JFormattedTextField totalField;
    private String[] transactionColumn = new String[]{"Kode Barang", "Nama Barang", "Qty", "Sat", "HPP", "Total Hpp"};
    private DefaultTableModel penerimaanBsTableModel = new DefaultTableModel(new String[0][1], this.transactionColumn);
    private KeyboardFocusManager manager;
    private keyDispatcher keyDispatcher;
    private int screenWidth;
    private int screenHeight;
    private PenerimaanBsService penerimaanBsService = new PenerimaanBsService();
    private PenerimaanBsDetailService penerimaanBsDetailService = new PenerimaanBsDetailService();
    private KaryawanService karyawanService = new KaryawanService();
    private BarangService barangService = new BarangService();
    private PenerimaanBs penerimaanBs = new PenerimaanBs();
    private Helper helper = Helper.getInstance();
    private boolean isCreated = true;
    private DefaultPanelListener listener;
    private MutasiBarangService mutasiBarangService = new MutasiBarangService();

    public PenerimaanBsPanel() {
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.keyDispatcher = new keyDispatcher();
        this.manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        this.manager.addKeyEventDispatcher(this.keyDispatcher);
        this.resetData();
        TableColumn col2 = this.penerimaanBsTable.getColumnModel().getColumn(2);
        TableColumn col3 = this.penerimaanBsTable.getColumnModel().getColumn(3);
        int width = 20;
        col2.setPreferredWidth(width);
        col3.setPreferredWidth(width);
    }

    public void setDefaultFocus() {
        this.idField.requestFocusInWindow();
    }

    private void escapeClick() {
        this.idField.requestFocus();
        this.idField.selectAll();
    }

    private void saveClick() {
        PenerimaanBs checkData = this.penerimaanBsService.getPenerimaanBs(this.idField.getText());
        if (checkData == null) {
            this.penerimaanBs.setId(this.helper.generateCodeGeneral(this.penerimaanBsService.getLastId()));
            this.penerimaanBs.setKaryawanId(this.helper.getKaryawan().getId());
            this.penerimaanBs.setTanggal(this.dateField.getDate());
            this.penerimaanBs.setLokasi(this.lokasiCmb.getSelectedItem() + "");
            this.penerimaanBs.setStatus("BELUM DIPROSES");
            this.penerimaanBs.setCreated(new Date());
            this.penerimaanBs.setUpdated(new Date());
            int penerimaanBsStatus = this.penerimaanBsService.createPenerimaanBs(this.penerimaanBs);
            int penerimaanBsDetailStatus = 0;
            if (penerimaanBsStatus == 1) {
                for (int a = 0; a < this.penerimaanBsTableModel.getRowCount(); ++a) {
                    PenerimaanBsDetail penerimaanBsDetail = new PenerimaanBsDetail();
                    penerimaanBsDetail.setBarangId(this.penerimaanBsTableModel.getValueAt(a, 0) + "");
                    penerimaanBsDetail.setJumlahBarang(Float.parseFloat(this.penerimaanBsTableModel.getValueAt(a, 2) + ""));
                    penerimaanBsDetail.setSatuan(this.penerimaanBsTableModel.getValueAt(a, 3) + "");
                    penerimaanBsDetail.setPenerimaanBsId(this.penerimaanBs.getId());
                    penerimaanBsDetail.setHpp(Long.parseLong(this.penerimaanBsTableModel.getValueAt(a, 4) + ""));
                    penerimaanBsDetail.setTotalHpp(Long.parseLong(this.penerimaanBsTableModel.getValueAt(a, 5) + ""));
                    penerimaanBsDetail.setCreated(new Date());
                    penerimaanBsDetail.setUpdated(new Date());
                    penerimaanBsDetailStatus = this.penerimaanBsDetailService.createPenerimaanBsDetail(penerimaanBsDetail);
                }
            }
            if (penerimaanBsDetailStatus == 1 && penerimaanBsStatus == 1) {
                PrinterJob job = PrinterJob.getPrinterJob();
                PageFormat pf = job.defaultPage();
                Paper p = pf.getPaper();
                p.setSize(p.getWidth(), p.getHeight() + 1500.0);
                p.setImageableArea(15.0, 0.0, p.getWidth(), p.getHeight() + 1500.0);
                pf.setPaper(p);
                Book book = new Book();
                book.append(new bsPrinter(), pf);
                job.setPageable(book);
                boolean ok = job.printDialog();
                try {
                    job.print();
                }
                catch (PrinterException printerException) {
                    // empty catch block
                }
                this.resetData();
                this.escapeClick();
                this.idField.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Penyimpanan Data Gagal");
            }
        } else if (checkData != null) {
            this.penerimaanBs.setKaryawanId(this.helper.getKaryawan().getId());
            this.penerimaanBs.setTanggal(this.dateField.getDate());
            this.penerimaanBs.setLokasi(this.lokasiCmb.getSelectedItem().toString());
            this.penerimaanBs.setUpdated(new Date());
            this.penerimaanBsService.updatePenerimaanBs(this.penerimaanBs);
            List<PenerimaanBsDetail> penerimaanBsDetails = this.penerimaanBsDetailService.getPenerimaanBsDetailsByPenerimaanBsId(this.penerimaanBs.getId());
            for (PenerimaanBsDetail penerimaanBsDetail : penerimaanBsDetails) {
                this.penerimaanBsDetailService.deletePenerimaanBsDetail(penerimaanBsDetail);
            }
            int penerimaanBsDetailStatus = 0;
            for (int a = 0; a < this.penerimaanBsTableModel.getRowCount(); ++a) {
                PenerimaanBsDetail penerimaanBsDetail = new PenerimaanBsDetail();
                penerimaanBsDetail.setBarangId(this.penerimaanBsTableModel.getValueAt(a, 0) + "");
                penerimaanBsDetail.setJumlahBarang(Float.parseFloat(this.penerimaanBsTableModel.getValueAt(a, 2) + ""));
                penerimaanBsDetail.setSatuan(this.penerimaanBsTableModel.getValueAt(a, 3) + "");
                penerimaanBsDetail.setPenerimaanBsId(this.penerimaanBs.getId());
                penerimaanBsDetail.setCreated(new Date());
                penerimaanBsDetail.setUpdated(new Date());
                penerimaanBsDetailStatus = this.penerimaanBsDetailService.createPenerimaanBsDetail(penerimaanBsDetail);
            }
            if (penerimaanBsDetailStatus == 1) {
                PrinterJob job = PrinterJob.getPrinterJob();
                PageFormat pf = job.defaultPage();
                Paper p = pf.getPaper();
                p.setSize(p.getWidth(), p.getHeight() + 1500.0);
                p.setImageableArea(15.0, 0.0, p.getWidth(), p.getHeight() + 1500.0);
                pf.setPaper(p);
                Book book = new Book();
                book.append(new bsPrinter(), pf);
                job.setPageable(book);
                boolean ok = job.printDialog();
                try {
                    job.print();
                }
                catch (PrinterException printerException) {
                    // empty catch block
                }
                this.resetData();
                this.escapeClick();
                this.idField.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Penyimpanan Data Gagal");
            }
        }
    }

    private void resetData() {
        this.dateField.setDate(new Date());
        int countRow = this.penerimaanBsTableModel.getRowCount();
        for (int a = 0; a < countRow; ++a) {
            this.penerimaanBsTableModel.removeRow(0);
        }
        this.jumlahBarang.setText("0");
        this.karyawanLbl.setText(this.helper.getKaryawan().getNama());
        this.saveButton.setEnabled(true);
    }

    public void addListener(DefaultPanelListener listener) {
        this.listener = listener;
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.jLabel4 = new JLabel();
        this.idField = new JTextField();
        this.jLabel7 = new JLabel();
        this.jLabel8 = new JLabel();
        this.jLabel5 = new JLabel();
        this.jLabel6 = new JLabel();
        this.dateField = new JDateChooser();
        this.detailButton = new JButton();
        this.saveButton = new JButton();
        this.exitButton = new JButton();
        this.jScrollPane1 = new JScrollPane();
        this.penerimaanBsTable = new JTable();
        this.karyawanLbl = new JLabel();
        this.jumlahBarang = new JTextField();
        this.jLabel9 = new JLabel();
        this.lokasiCmb = new JComboBox();
        this.totalField = new JFormattedTextField();
        this.jLabel10 = new JLabel();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setText("Tanggal");
        this.jLabel2.setText("No. Transaksi");
        this.jLabel3.setText(":");
        this.jLabel4.setText(":");
        this.idField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenerimaanBsPanel.this.idFieldKeyPressed(evt);
            }
        });
        this.jLabel7.setText("Jumlah Barang");
        this.jLabel8.setText(":");
        this.jLabel5.setText("Karyawan");
        this.jLabel6.setText(":");
        this.detailButton.setText("Detail");
        this.detailButton.setPreferredSize(new Dimension(73, 23));
        this.detailButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PenerimaanBsPanel.this.detailButtonActionPerformed(evt);
            }
        });
        this.detailButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenerimaanBsPanel.this.detailButtonKeyPressed(evt);
            }
        });
        this.saveButton.setText("Save");
        this.saveButton.setPreferredSize(new Dimension(73, 23));
        this.saveButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PenerimaanBsPanel.this.saveButtonActionPerformed(evt);
            }
        });
        this.saveButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenerimaanBsPanel.this.saveButtonKeyPressed(evt);
            }
        });
        this.exitButton.setText("Exit");
        this.exitButton.setPreferredSize(new Dimension(73, 23));
        this.exitButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PenerimaanBsPanel.this.exitButtonActionPerformed(evt);
            }
        });
        this.exitButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenerimaanBsPanel.this.exitButtonKeyPressed(evt);
            }
        });
        this.penerimaanBsTable.setModel(this.penerimaanBsTableModel);
        this.penerimaanBsTable.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenerimaanBsPanel.this.penerimaanBsTableKeyPressed(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.penerimaanBsTable);
        this.karyawanLbl.setText("karyawanLbl");
        this.jumlahBarang.setEditable(false);
        this.jumlahBarang.setBackground(new Color(153, 255, 204));
        this.jLabel9.setText("Lokasi");
        this.lokasiCmb.setModel(new DefaultComboBoxModel<String>(new String[]{"TOKO"}));
        this.lokasiCmb.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenerimaanBsPanel.this.lokasiCmbKeyPressed(evt);
            }
        });
        this.totalField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));
        this.totalField.setFont(new Font("Tahoma", 0, 14));
        this.totalField.setValue(new Long(0L));
        this.jLabel10.setFont(new Font("Tahoma", 0, 14));
        this.jLabel10.setText("Total");
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1)).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2).addComponent(this.jLabel1).addComponent(this.jLabel7)).addGap(38, 38, 38).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel3).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.idField, -2, 111, -2).addGap(60, 60, 60).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel5).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel6)).addComponent(this.jLabel9)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.karyawanLbl, -2, 102, -2).addGroup(layout.createSequentialGroup().addComponent(this.lokasiCmb, 0, -1, Short.MAX_VALUE).addGap(5, 5, 5))).addGap(0, 0, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addComponent(this.jLabel8).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jumlahBarang).addGap(44, 44, 44)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel4).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent((Component)this.dateField, -2, 155, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.jLabel10, -2, 127, -2)))).addGroup(layout.createSequentialGroup().addGap(251, 251, 251).addComponent(this.detailButton, -2, -1, -2).addGap(18, 18, 18).addComponent(this.saveButton, -2, -1, -2).addGap(18, 18, 18).addComponent(this.exitButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 157, Short.MAX_VALUE).addComponent(this.totalField, -2, 127, -2))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.jLabel4).addComponent(this.jLabel5).addComponent(this.jLabel6).addComponent(this.karyawanLbl)).addComponent((Component)this.dateField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.jLabel3).addComponent(this.idField, -2, -1, -2)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel9).addComponent(this.lokasiCmb, -2, -1, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel7).addComponent(this.jLabel8)).addComponent(this.jLabel10, GroupLayout.Alignment.TRAILING)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.detailButton, -2, -1, -2).addComponent(this.saveButton, -2, -1, -2).addComponent(this.exitButton, -2, -1, -2).addComponent(this.totalField, -2, -1, -2))).addComponent(this.jumlahBarang, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jScrollPane1, -1, 462, Short.MAX_VALUE).addContainerGap()));
    }

    private void idFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.penerimaanBs = this.penerimaanBsService.getPenerimaanBs(this.idField.getText());
            if (this.idField.getText().equals("") || this.penerimaanBs == null) {
                this.resetData();
                this.isCreated = true;
                this.penerimaanBs = new PenerimaanBs();
                this.idField.setText(Helper.getInstance().generateCodeGeneral(this.penerimaanBsService.getLastId()));
                this.lokasiCmb.requestFocus();
            } else {
                this.resetData();
                if (this.penerimaanBs.getStatus().equalsIgnoreCase("SUDAH DIPROSES")) {
                    this.saveButton.setEnabled(false);
                }
                this.isCreated = false;
                List<PenerimaanBsDetail> penerimaanBsDetails = this.penerimaanBsDetailService.getPenerimaanBsDetailsByPenerimaanBsId(this.penerimaanBs.getId());
                Karyawan karyawanData = this.karyawanService.getKaryawan(this.penerimaanBs.getKaryawanId());
                this.karyawanLbl.setText(karyawanData.getNama());
                this.lokasiCmb.setSelectedItem(this.penerimaanBs.getLokasi());
                float totalBarang = 0.0f;
                for (PenerimaanBsDetail penerimaanBsDetail : penerimaanBsDetails) {
                    Barang dataBarang = this.barangService.getBarang(penerimaanBsDetail.getBarangId());
                    Object[] rowValue = new Object[8];
                    rowValue[0] = dataBarang.getId();
                    rowValue[1] = dataBarang.getNama();
                    rowValue[2] = Float.valueOf(penerimaanBsDetail.getJumlahBarang());
                    rowValue[3] = penerimaanBsDetail.getSatuan();
                    totalBarang += penerimaanBsDetail.getJumlahBarang();
                    this.penerimaanBsTableModel.addRow(rowValue);
                }
                this.jumlahBarang.setText(totalBarang + "");
                this.lokasiCmb.requestFocus();
            }
        } else if (evt.getKeyCode() == 27) {
            this.listener.exitClick();
        }
    }

    private void detailButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.penerimaanBsTable.requestFocus();
            if (this.penerimaanBsTableModel.getRowCount() == 0) {
                PenerimaanBsDialog penerimaanBsDialog = new PenerimaanBsDialog(null, true, this.penerimaanBsTableModel, this.lokasiCmb.getSelectedItem().toString());
                penerimaanBsDialog.addListener(new penerimaanBsDialogEventHandler());
                int w = penerimaanBsDialog.getWidth();
                int h = penerimaanBsDialog.getHeight();
                penerimaanBsDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                penerimaanBsDialog.setVisible(true);
            }
        } else if (evt.getKeyCode() == 39) {
            this.saveButton.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.idField.requestFocus();
            this.idField.selectAll();
        } else if (evt.getKeyCode() == 37 || evt.getKeyCode() == 38) {
            this.lokasiCmb.requestFocus();
        }
    }

    private void penerimaanBsTableKeyPressed(KeyEvent evt) {
        int a;
        if (evt.getKeyCode() == 10) {
            PenerimaanBsDialog penerimaanBsDialog = new PenerimaanBsDialog(null, true, this.penerimaanBsTableModel, this.lokasiCmb.getSelectedItem().toString());
            if (this.penerimaanBsTable.getSelectedRow() != -1) {
                penerimaanBsDialog.loadKodeBarang(this.penerimaanBsTableModel.getValueAt(this.penerimaanBsTable.getSelectedRow(), 0).toString());
                penerimaanBsDialog.loadDataFromTable(this.penerimaanBsTable.getSelectedRow());
            }
            penerimaanBsDialog.escapeClick();
            penerimaanBsDialog.addListener(new penerimaanBsDialogEventHandler());
            int w = penerimaanBsDialog.getWidth();
            int h = penerimaanBsDialog.getHeight();
            penerimaanBsDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            penerimaanBsDialog.setVisible(true);
        } else if (evt.getKeyCode() == 27) {
            int a2 = JOptionPane.showConfirmDialog(null, "Akhiri Entry Data?", "Akhiri Entry Data", 0, 3);
            if (a2 == 0) {
                float total = 0.0f;
                for (int x = 0; x < this.penerimaanBsTableModel.getRowCount(); ++x) {
                    total += Float.parseFloat(this.penerimaanBsTableModel.getValueAt(x, 2).toString());
                }
                this.saveButton.requestFocus();
                this.jumlahBarang.setText(total + "");
            }
        } else if (evt.getKeyCode() == 127 && this.penerimaanBsTable.getSelectedRow() != -1 && (a = JOptionPane.showConfirmDialog(null, "Hapus Data Ini?", "Delete Data", 0, 3)) == 0) {
            this.penerimaanBsTableModel.removeRow(this.penerimaanBsTable.getSelectedRow());
        }
    }

    private void saveButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.saveClick();
        } else if (evt.getKeyCode() == 39) {
            this.exitButton.requestFocus();
        } else if (evt.getKeyCode() == 38 || evt.getKeyCode() == 37) {
            this.detailButton.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.idField.requestFocus();
            this.idField.selectAll();
        }
    }

    private void exitButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.listener.exitClick();
        } else if (evt.getKeyCode() == 38 || evt.getKeyCode() == 37) {
            this.saveButton.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.idField.requestFocus();
            this.idField.selectAll();
        }
    }

    private void exitButtonActionPerformed(ActionEvent evt) {
        this.listener.exitClick();
    }

    private void saveButtonActionPerformed(ActionEvent evt) {
        this.saveClick();
    }

    private void detailButtonActionPerformed(ActionEvent evt) {
        this.penerimaanBsTable.requestFocus();
        if (this.penerimaanBsTableModel.getRowCount() == 0) {
            PenerimaanBsDialog penerimaanBsDialog = new PenerimaanBsDialog(null, true, this.penerimaanBsTableModel, this.lokasiCmb.getSelectedItem().toString());
            penerimaanBsDialog.addListener(new penerimaanBsDialogEventHandler());
            int w = penerimaanBsDialog.getWidth();
            int h = penerimaanBsDialog.getHeight();
            penerimaanBsDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            penerimaanBsDialog.setVisible(true);
        }
    }

    private void lokasiCmbKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.detailButton.requestFocus();
        } else if (evt.getKeyCode() == 38) {
            this.idField.requestFocus();
        }
    }

    class bsPrinter
    implements Printable {
        bsPrinter() {
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
            g.drawString("Entry BS " + PenerimaanBsPanel.this.lokasiCmb.getSelectedItem() + " - " + PenerimaanBsPanel.this.penerimaanBs.getId(), x + 0, y + yK);
            g.drawString("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -", x + 0, y + (yK += yP));
            yK += yP;
            for (int a = 0; a < PenerimaanBsPanel.this.penerimaanBsTableModel.getRowCount(); ++a) {
                Barang barang = PenerimaanBsPanel.this.barangService.getBarang(PenerimaanBsPanel.this.penerimaanBsTableModel.getValueAt(a, 0).toString());
                g.drawString(barang.getNama() + " (" + barang.getRakIdToko() + ")", x + 0, y + yK);
                float tempValue = Float.parseFloat(PenerimaanBsPanel.this.penerimaanBsTableModel.getValueAt(a, 2).toString());
                g.drawString(tempValue + PenerimaanBsPanel.this.penerimaanBsTableModel.getValueAt(a, 3).toString() + " / " + (barang.getStokBs() + tempValue) + barang.getSatuan1(), x + 20, y + (yK += yP));
                yK += yP;
            }
            String pattern = "dd-MM-yyyy";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            String tanggal = format.format(PenerimaanBsPanel.this.penerimaanBs.getTanggal());
            g.drawString("Tgl " + tanggal, x + 0, y + yK);
            g.drawString("Jam " + PenerimaanBsPanel.this.penerimaanBs.getTanggal().getHours() + ":" + PenerimaanBsPanel.this.penerimaanBs.getTanggal().getMinutes() + ":" + PenerimaanBsPanel.this.penerimaanBs.getTanggal().getSeconds(), x + 120, y + yK);
            return 0;
        }
    }

    private class penerimaanBsDialogEventHandler
    implements PenerimaanBsDialogListener {
        private penerimaanBsDialogEventHandler() {
        }

        @Override
        public void saveClick(Object[] rowValue) {
            int a;
            boolean newData = true;
            for (a = 0; a < PenerimaanBsPanel.this.penerimaanBsTableModel.getRowCount(); ++a) {
                if (!PenerimaanBsPanel.this.penerimaanBsTableModel.getValueAt(a, 0).equals(rowValue[0])) continue;
                newData = false;
                break;
            }
            if (newData) {
                PenerimaanBsPanel.this.penerimaanBsTableModel.addRow(rowValue);
                long newValue = (Long)PenerimaanBsPanel.this.totalField.getValue() + Long.parseLong(rowValue[5] + "");
                PenerimaanBsPanel.this.totalField.setValue(newValue);
            } else {
                long newValue = (Long)PenerimaanBsPanel.this.totalField.getValue() - Long.parseLong(PenerimaanBsPanel.this.penerimaanBsTableModel.getValueAt(a, 5) + "") + Long.parseLong(rowValue[5] + "");
                PenerimaanBsPanel.this.penerimaanBsTableModel.removeRow(a);
                PenerimaanBsPanel.this.penerimaanBsTableModel.addRow(rowValue);
                PenerimaanBsPanel.this.totalField.setValue(newValue);
            }
        }
    }

    private class keyDispatcher
    implements KeyEventDispatcher {
        private keyDispatcher() {
        }

        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (e.getID() == 401) {
                // empty if block
            }
            return false;
        }
    }
}

