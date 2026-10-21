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
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.DocumentFilter;
import javax.swing.text.NumberFormatter;
import minimart.engine.component.UppercaseDocumentFilter;
import minimart.engine.controller.Helper;
import minimart.engine.model.Barang;
import minimart.engine.model.Customer;
import minimart.engine.model.Karyawan;
import minimart.engine.model.PenjualanParsial;
import minimart.engine.model.PenjualanParsialDetail;
import minimart.engine.model.PenjualanSementara;
import minimart.engine.model.PromoPeriode;
import minimart.engine.model.PromoVoucher;
import minimart.engine.model.Sales;
import minimart.engine.model.SalesOrder;
import minimart.engine.model.SalesOrderDetail;
import minimart.engine.model.ShiftKasir;
import minimart.engine.model.TransaksiOnline;
import minimart.engine.model.TransaksiOnlineDetail;
import minimart.engine.service.BarangService;
import minimart.engine.service.CustomerService;
import minimart.engine.service.KonstantaService;
import minimart.engine.service.PenjualanDetailService;
import minimart.engine.service.PenjualanParsialDetailService;
import minimart.engine.service.PenjualanParsialService;
import minimart.engine.service.PenjualanSementaraService;
import minimart.engine.service.PenjualanService;
import minimart.engine.service.PromoHematDetailService;
import minimart.engine.service.PromoPeriodeService;
import minimart.engine.service.PromoTebusDetailService;
import minimart.engine.service.PromoTebusMurahDetailService;
import minimart.engine.service.PromoVoucherService;
import minimart.engine.service.SalesOrderService;
import minimart.engine.service.SalesService;
import minimart.engine.service.ShiftKasirService;
import minimart.engine.service.StokOpnamMandiriService;
import minimart.engine.service.TransaksiOnlineService;
import minimart.ui.listener.CetakNotaListener;
import minimart.ui.listener.CloseKasirListener;
import minimart.ui.listener.ConfirmationDialogListener;
import minimart.ui.listener.PasswordInputListener;
import minimart.ui.listener.PenjualanBarcodeListener;
import minimart.ui.listener.PenjualanDetailDialogListener;
import minimart.ui.listener.PenjualanPanelListener;
import minimart.ui.listener.PenjualanParsialDialogListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.CetakNota;
import minimart.ui.view.CetakNota2;
import minimart.ui.view.CloseKasir1;
import minimart.ui.view.ConfirmationDialog;
import minimart.ui.view.PasswordInputDialog;
import minimart.ui.view.PenjualanDetailDialog1;
import minimart.ui.view.PenjualanParsialDialog;
import minimart.ui.view.PopupPromo;
import minimart.ui.view.ReprintDialog;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class PenjualanPanel1
extends JPanel {
    private JTextField customerField;
    private JLabel customerLbl;
    private JButton importBtn;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel11;
    private JLabel jLabel13;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JMenu jMenu1;
    private JMenu jMenu2;
    private JMenuBar jMenuBar1;
    private JScrollPane jScrollPane1;
    private JDateChooser jatuhTempoField;
    private JLabel jatuhTempoLbl;
    private JLabel karyawanField;
    private JComboBox keteranganCmb;
    private JLabel keteranganLbl;
    private JLabel kodeImportLabel;
    private JTable penjualanTable;
    private JLabel promoLbl;
    private JButton promoPwp;
    private JButton reprintBtn;
    private JTextField salesField;
    private JLabel salesLbl;
    private JLabel salesNameField;
    private JLabel shortcutLbl;
    private JButton tebusMurahBtn;
    private JFormattedTextField totalField;
    private JTextField ucImportField;
    private PenjualanPanelListener listener;
    private int screenWidth;
    private int screenHeight;
    private KonstantaService konstantaService = new KonstantaService();
    private CustomerService customerService = new CustomerService();
    private PenjualanService penjualanService = new PenjualanService();
    private Customer customer;
    private Karyawan karyawan;
    private PromoVoucher promoVoucher;
    private Sales sales;
    private SalesService salesService = new SalesService();
    private PromoVoucherService promoVoucherService = new PromoVoucherService();
    private PromoPeriodeService promoPeriodeService = new PromoPeriodeService();
    private SalesOrderService soService = new SalesOrderService();
    private TransaksiOnlineService toService = new TransaksiOnlineService();
    private KonstantaService ks = new KonstantaService();
    private String[] transactionColumn = new String[]{"Kode Barang", "Nama Barang", "Qty", "Sat", "Harga", "Total", "Disc", "Harga Akhir"};
    long total = 0L;
    private List<Object[]> dataTebusMurah;
    private List<Object[]> dataTebusPWP = new ArrayList<Object[]>();
    private List<Object[]> nominalTebusMurah;
    private DefaultTableModel penjualanTableModel = new DefaultTableModel(new String[0][1], this.transactionColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private boolean promoTebusConfirm;
    private boolean promoPWPConfirm;
    private DocumentFilter filter = new UppercaseDocumentFilter();
    private Helper helper = Helper.getInstance();
    private PenjualanSementaraService penjualanSementaraService = new PenjualanSementaraService();
    private BarangService barangService = new BarangService();
    private PenjualanDetailService detailService = new PenjualanDetailService();
    private PromoPeriode promoPeriode;
    private PenjualanParsialDetailService penjualanParsialDetailService = new PenjualanParsialDetailService();
    private PenjualanParsialService penjualanParsialService = new PenjualanParsialService();
    private boolean canGoThrough;
    private ShiftKasirService shiftKasirService = new ShiftKasirService();
    private String keteranganSoftware;
    private StokOpnamMandiriService stokOpnamMandiriService = new StokOpnamMandiriService();
    private PromoHematDetailService promoHematDetailService = new PromoHematDetailService();
    private ShiftKasir shiftKasir;
    private PromoTebusMurahDetailService promoTebusMurahDetailService = new PromoTebusMurahDetailService();
    private PromoTebusDetailService promoTebusDetailService = new PromoTebusDetailService();
    private String startDateString;
    private String endDateString;

    public PenjualanPanel1(String shortcut, String keteranganSoftware) {
        this.initComponents();
        this.keteranganSoftware = keteranganSoftware;
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        ((AbstractDocument)this.customerField.getDocument()).setDocumentFilter(this.filter);
        ShiftKasir shiftKasir = new ShiftKasirService().getOpenedShiftKasir(this.helper.getKasir());
        this.karyawanField.setText(shiftKasir.getOpenedBy());
        this.shortcutLbl.setText(shortcut);
        this.penjualanTable.setSelectionMode(0);
        this.jatuhTempoField.setEnabled(false);
        this.jatuhTempoField.setDate(new Date());
        this.kodeImportLabel.setVisible(false);
        this.ucImportField.setVisible(false);
        this.importBtn.setVisible(false);
        String importMethod = this.ks.getConstantValue("salesOrderImport");
        if (shortcut.equals("F11") || shortcut.equals("F10") || shortcut.equals("F3") || shortcut.equals("F4")) {
            this.salesLbl.setVisible(true);
            this.salesField.setVisible(true);
            this.salesNameField.setVisible(true);
            this.keteranganLbl.setVisible(true);
            this.keteranganCmb.setVisible(true);
            this.jatuhTempoField.setVisible(true);
            this.jatuhTempoLbl.setVisible(true);
        } else {
            this.salesLbl.setVisible(false);
            this.salesField.setVisible(false);
            this.salesNameField.setVisible(false);
            this.keteranganLbl.setVisible(false);
            this.keteranganCmb.setVisible(false);
            this.jatuhTempoField.setVisible(false);
            this.jatuhTempoLbl.setVisible(false);
        }
        if (importMethod.equals("new")) {
            if (shortcut.equals("F11") || shortcut.equals("F10")) {
                this.customerField.setEditable(false);
                this.salesField.setEditable(false);
                this.keteranganCmb.setEnabled(false);
                this.jatuhTempoField.setEnabled(false);
                this.kodeImportLabel.setVisible(true);
                this.ucImportField.setVisible(true);
                this.importBtn.setVisible(true);
                this.importBtn.setText("Import Sales Order");
                if (shortcut.equals("F10")) {
                    this.importBtn.setText("Import Transaksi Online");
                }
            } else {
                this.customerField.setEditable(true);
                this.keteranganCmb.setEditable(true);
                this.kodeImportLabel.setVisible(false);
                this.ucImportField.setVisible(false);
                this.importBtn.setVisible(false);
            }
        }
        this.promoVoucher = this.promoVoucherService.getPromoVoucherById(1);
        this.promoPeriode = this.promoPeriodeService.getPromoPeriode(1);
        TableColumn col1 = this.penjualanTable.getColumnModel().getColumn(1);
        TableColumn col2 = this.penjualanTable.getColumnModel().getColumn(2);
        TableColumn col3 = this.penjualanTable.getColumnModel().getColumn(3);
        int width = 35;
        col1.setPreferredWidth(170);
        col2.setPreferredWidth(width);
        col3.setPreferredWidth(width);
        this.customer = this.helper.getStatusProgram().equals("GUDANG") && this.helper.getJenisProgram().equals("GUDANGTOKO") ? this.customerService.getCustomerById("SINAR") : this.customerService.getCustomerById("C");
        this.customerField.setText(this.customer.getId());
        this.customerLbl.setText(this.customer.getNama() + "-" + this.customer.getAlamat());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.startDateString = simpleDateFormat.format(new Date());
        this.endDateString = simpleDateFormat.format(new Date());
        this.nominalTebusMurah = this.promoTebusMurahDetailService.getAvailableTebusMurah(this.startDateString, this.endDateString);
        this.loadData();
        this.checkAllPromoTebus();
        this.keteranganCmb.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == 1) {
                    if (PenjualanPanel1.this.keteranganCmb.getSelectedItem().equals("CASH")) {
                        PenjualanPanel1.this.jatuhTempoField.setDate(new Date());
                        PenjualanPanel1.this.penjualanSementaraService.updateKeteranganPenjualanSementara("CASH", PenjualanPanel1.this.shortcutLbl.getText());
                    } else if (PenjualanPanel1.this.keteranganCmb.getSelectedItem().equals("KREDIT")) {
                        if (PenjualanPanel1.this.customer.getId().equals("C")) {
                            JOptionPane.showMessageDialog(null, "Pilih Customer Terlebih Dahulu");
                            PenjualanPanel1.this.keteranganCmb.setSelectedIndex(0);
                        } else if (PenjualanPanel1.this.customer.getLimitKredit() == 0 || PenjualanPanel1.this.customer.getTop() == 0) {
                            JOptionPane.showMessageDialog(null, "Customer " + PenjualanPanel1.this.customer.getNama() + " Belum Diberikan Fasilitas Kredit");
                            PenjualanPanel1.this.keteranganCmb.setSelectedIndex(0);
                        } else if (PenjualanPanel1.this.customer.getCreatedByCabang().equals(PenjualanPanel1.this.konstantaService.getConstantValue("idCabang")) || PenjualanPanel1.this.shortcutLbl.getText().equals("F11")) {
                            PenjualanPanel1.this.penjualanSementaraService.updateKeteranganPenjualanSementara("KREDIT", PenjualanPanel1.this.shortcutLbl.getText());
                            Calendar c = Calendar.getInstance();
                            c.setTime(new Date());
                            c.add(5, PenjualanPanel1.this.customer.getTop());
                            PenjualanPanel1.this.jatuhTempoField.setDate(c.getTime());
                        } else {
                            JOptionPane.showMessageDialog(null, "Tidak boleh memberikan kredit customer cabang lain");
                            PenjualanPanel1.this.keteranganCmb.setSelectedIndex(0);
                        }
                    }
                } else if (e.getStateChange() == 2) {
                    // empty if block
                }
            }
        });
    }

    public static Integer extractNumberSuffix(String input) {
        String numberPart = input.replaceAll("^\\D+", "");
        int number = Integer.parseInt(numberPart);
        return number;
    }

    public static String padWithLeadingZeros(int input) {
        return String.format("%010d", input);
    }

    private void checkAllPromoTebus() {
        int rowCount = this.penjualanTableModel.getRowCount();
        for (int a = 0; a < rowCount; ++a) {
            String idbarang = this.penjualanTableModel.getValueAt(a, 0) + "";
            String satuan = this.penjualanTableModel.getValueAt(a, 3) + "";
            List<Object[]> data = this.promoTebusDetailService.getPromoTebusDetailByBarangId(idbarang, satuan);
            float jumlahBarang = this.penjualanSementaraService.getJumlahPenjualanSementara(this.helper.getKasir(), this.shortcutLbl.getText(), idbarang, satuan);
            if (data.size() <= 0) continue;
            StringBuilder stringBuilder = new StringBuilder();
            if (!(jumlahBarang >= Float.parseFloat(stringBuilder.append(data.get(0)[4]).append("").toString()))) continue;
            this.checkButtonPromoTebus("add", data);
        }
    }

    private void checkButtonPromoTebus(String state, List<Object[]> data) {
        if (state.equals("add")) {
            if (data.size() > 0) {
                boolean ada = false;
                for (int a = 0; a < this.dataTebusPWP.size(); ++a) {
                    ada = this.dataTebusPWP.get(a)[0].equals(data.get(0)[0]);
                }
                if (!ada) {
                    this.dataTebusPWP.add(data.get(0));
                }
                if (this.dataTebusPWP.size() > 0) {
                    this.promoPwp.setEnabled(true);
                } else {
                    this.promoPwp.setEnabled(false);
                }
            }
        } else if (state.equals("remove") && data.size() > 0) {
            this.removeDataPromoTebus(data.get(0)[0] + "");
        }
    }

    private void checkButtonPromo() {
        this.dataTebusMurah = this.totalField.getValue() == null ? this.promoTebusMurahDetailService.getPromoTebusMurahByNominal(this.startDateString, this.endDateString, 0L) : this.promoTebusMurahDetailService.getPromoTebusMurahByNominal(this.startDateString, this.endDateString, Long.parseLong(this.totalField.getValue().toString()));
        if (this.dataTebusMurah.size() > 0) {
            this.tebusMurahBtn.setEnabled(true);
        } else {
            this.tebusMurahBtn.setEnabled(false);
        }
    }

    private void insertPress() {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date myDate = this.shiftKasirService.getKasirDate(this.helper.getKasir());
        Calendar cal = Calendar.getInstance();
        cal.setTime(myDate);
        Calendar elevenCal = Calendar.getInstance();
        elevenCal.set(11, 11);
        elevenCal.set(12, 0);
        elevenCal.set(13, 0);
        elevenCal.set(14, 0);
        boolean isAfter11 = myDate.after(elevenCal.getTime());
        if (this.stokOpnamMandiriService.getStokOpnamMandiriUnSubmit().size() > 0 && isAfter11) {
            PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
            passwordInputDialog2.addPasswordInputDialogListener(new passwordInputClosingKasir());
            int w = passwordInputDialog2.getWidth();
            int h = passwordInputDialog2.getHeight();
            passwordInputDialog2.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            passwordInputDialog2.setJudulText("Selsaikan Stok Opnam Mandiri Terlebih Dahulu");
            passwordInputDialog2.setVisible(true);
        } else {
            int recentTransaction = this.penjualanSementaraService.getRecentTransaction(this.helper.getKasir());
            if (recentTransaction > 0) {
                JOptionPane.showMessageDialog(null, "Harap Menyelesaikan Transaksi yang Sedang Berlangsung");
            } else {
                CloseKasir1 closeKasir = new CloseKasir1(null, true);
                closeKasir.setListener(new closeKasirEventHandler());
                int w = closeKasir.getWidth();
                int h = closeKasir.getHeight();
                closeKasir.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                closeKasir.setVisible(true);
            }
        }
    }

    private void removeAllRow() {
        int rowCount = this.penjualanTableModel.getRowCount();
        for (int a = 0; a < rowCount; ++a) {
            this.penjualanTableModel.removeRow(0);
        }
    }

    private void tebusMurahBtnClick() {
        PopupPromo popUpPromo = new PopupPromo(null, true, this.dataTebusMurah, "TEBUSMURAH");
        popUpPromo.addListener(new popUpConfirmDialogEventHandler());
        int w = popUpPromo.getWidth();
        int h = popUpPromo.getHeight();
        popUpPromo.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
        popUpPromo.setVisible(true);
        this.penjualanTable.requestFocus();
    }

    private void tebusPWPClick() {
        System.out.println("KlikBtn  =" + this.dataTebusPWP.size());
        PopupPromo popUpPromo = new PopupPromo(null, true, this.dataTebusPWP, "PWP");
        popUpPromo.addListener(new popUpConfirmPWPDialogEventHandler());
        int w = popUpPromo.getWidth();
        int h = popUpPromo.getHeight();
        popUpPromo.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
        popUpPromo.setVisible(true);
        this.penjualanTable.requestFocus();
    }

    private void loadData() {
        List<Object[]> penjualanSementaras = this.penjualanSementaraService.getPenjualanSementaraByShortcut2(this.shortcutLbl.getText());
        long total = 0L;
        if (penjualanSementaras.size() > 0) {
            if (this.shortcutLbl.getText() == "F10") {
                this.penjualanSementaraService.deletePenjualanSementaraByShortcut("F10");
                this.customerField.setText("");
                this.customerLbl.setText("-");
                this.salesField.setText("");
                this.removeAllRow();
            } else {
                this.customer = this.customerService.getCustomerById(penjualanSementaras.get(0)[0].toString());
                this.customerField.setText(this.customer.getId());
                this.customerLbl.setText(this.customer.getNama() + "-" + this.customer.getAlamat());
                this.sales = this.salesService.getSales(penjualanSementaras.get(0)[1] + "");
                if (this.sales != null) {
                    this.salesField.setText(this.sales.getId());
                    this.salesNameField.setText(this.sales.getNama());
                }
                this.keteranganCmb.setEnabled(false);
                this.keteranganCmb.setSelectedItem(penjualanSementaras.get(0)[9]);
                if (penjualanSementaras.get(0)[9].equals("KREDIT")) {
                    Calendar c = Calendar.getInstance();
                    c.setTime(new Date());
                    c.add(5, this.customer.getTop());
                    this.jatuhTempoField.setDate(c.getTime());
                }
                for (Object[] penjualanSementara : penjualanSementaras) {
                    total += Long.parseLong(penjualanSementara[8].toString());
                    float tempValue = ((Float)penjualanSementara[4]).floatValue();
                    Object[] rowValue = new Object[]{penjualanSementara[2], penjualanSementara[3], tempValue == (float)((long)tempValue) ? Integer.valueOf(Math.round(((Float)penjualanSementara[4]).floatValue())) : penjualanSementara[4], penjualanSementara[5], penjualanSementara[6], (long)((float)((Long)penjualanSementara[6]).longValue() * ((Float)penjualanSementara[4]).floatValue()), penjualanSementara[7], penjualanSementara[8]};
                    this.penjualanTableModel.addRow(rowValue);
                }
            }
        }
        this.totalField.setValue(total);
        this.checkButtonPromo();
        this.cekPromoLbl();
    }

    private void cekPromoLbl() {
        long totalBelanja = Long.parseLong(this.totalField.getValue() + "");
        boolean cek = false;
        for (Object[] data : this.nominalTebusMurah) {
            long nominal = Long.parseLong(data[0] + "");
            if (totalBelanja < nominal * 80L / 100L || totalBelanja >= nominal) continue;
            this.promoLbl.setText("Tambah " + (totalBelanja - nominal) + " lagi untuk bisa tebus murah Rp" + nominal);
            cek = true;
            break;
        }
        if (!cek) {
            this.promoLbl.setText("-");
        }
    }

    public void setDefaultFocus() {
        this.penjualanTable.requestFocusInWindow();
    }

    private void initComponents() {
        this.jLabel13 = new JLabel();
        this.jMenuBar1 = new JMenuBar();
        this.jMenu1 = new JMenu();
        this.jMenu2 = new JMenu();
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jScrollPane1 = new JScrollPane();
        this.penjualanTable = new JTable();
        this.jLabel5 = new JLabel();
        this.jLabel6 = new JLabel();
        this.jLabel7 = new JLabel();
        this.jLabel8 = new JLabel();
        this.jLabel9 = new JLabel();
        this.jLabel3 = new JLabel();
        this.jLabel4 = new JLabel();
        this.jLabel10 = new JLabel();
        this.customerField = new JTextField();
        this.customerLbl = new JLabel();
        this.karyawanField = new JLabel();
        this.shortcutLbl = new JLabel();
        this.jLabel11 = new JLabel();
        this.totalField = new JFormattedTextField();
        this.salesLbl = new JLabel();
        this.salesField = new JTextField();
        this.salesNameField = new JLabel();
        this.keteranganLbl = new JLabel();
        this.keteranganCmb = new JComboBox();
        this.jatuhTempoLbl = new JLabel();
        this.jatuhTempoField = new JDateChooser();
        this.reprintBtn = new JButton();
        this.kodeImportLabel = new JLabel();
        this.ucImportField = new JTextField();
        this.importBtn = new JButton();
        this.promoPwp = new JButton();
        this.tebusMurahBtn = new JButton();
        this.promoLbl = new JLabel();
        this.jLabel13.setText("jLabel13");
        this.jMenu1.setText("File");
        this.jMenuBar1.add(this.jMenu1);
        this.jMenu2.setText("Edit");
        this.jMenuBar1.add(this.jMenu2);
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setText("Kasir/");
        this.jLabel2.setText("Customer");
        this.penjualanTable.setModel(this.penjualanTableModel);
        this.penjualanTable.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanPanel1.this.penjualanTableKeyPressed(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.penjualanTable);
        this.jLabel5.setText("F1:Customer");
        this.jLabel6.setText("F2:Print");
        this.jLabel7.setText("Enter:Edit");
        this.jLabel8.setText("Delete:Hapus");
        this.jLabel9.setText("ESC-Keluar");
        this.jLabel3.setText(":");
        this.jLabel4.setText(":");
        this.jLabel10.setText("F5-F12:PendingTransaksi");
        this.customerField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanPanel1.this.customerFieldKeyPressed(evt);
            }
        });
        this.karyawanField.setText("-");
        this.shortcutLbl.setFont(new Font("Tahoma", 1, 14));
        this.shortcutLbl.setForeground(new Color(204, 0, 0));
        this.shortcutLbl.setText("F5");
        this.jLabel11.setFont(new Font("Tahoma", 1, 24));
        this.jLabel11.setText("Total");
        this.totalField.setEditable(false);
        this.totalField.setBackground(new Color(255, 255, 0));
        this.totalField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("\u00a4#,##0.--"))));
        this.totalField.setText("0");
        this.totalField.setFont(new Font("Tahoma", 1, 24));
        this.salesLbl.setText("Sales");
        this.salesField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanPanel1.this.salesFieldKeyPressed(evt);
            }
        });
        this.salesNameField.setText("-");
        this.keteranganLbl.setText("Keterangan");
        this.keteranganCmb.setModel(new DefaultComboBoxModel<String>(new String[]{"CASH", "KREDIT"}));
        this.keteranganCmb.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent evt) {
                PenjualanPanel1.this.keteranganCmbItemStateChanged(evt);
            }
        });
        this.keteranganCmb.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PenjualanPanel1.this.keteranganCmbActionPerformed(evt);
            }
        });
        this.keteranganCmb.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanPanel1.this.keteranganCmbKeyPressed(evt);
            }
        });
        this.jatuhTempoLbl.setText("Jatuh Tempo");
        this.reprintBtn.setText("REPRINT");
        this.reprintBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PenjualanPanel1.this.reprintBtnActionPerformed(evt);
            }
        });
        this.kodeImportLabel.setText("Kode Import");
        this.importBtn.setText("Import Sales Order");
        this.importBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PenjualanPanel1.this.importBtnActionPerformed(evt);
            }
        });
        this.promoPwp.setBackground(new Color(51, 255, 51));
        this.promoPwp.setText("PROMO PWP");
        this.promoPwp.setEnabled(false);
        this.promoPwp.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
                PenjualanPanel1.this.promoPwpMouseClicked(evt);
            }
        });
        this.tebusMurahBtn.setBackground(new Color(51, 255, 51));
        this.tebusMurahBtn.setText("TEBUS MURAH");
        this.tebusMurahBtn.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
                PenjualanPanel1.this.tebusMurahBtnMouseClicked(evt);
            }
        });
        this.promoLbl.setFont(new Font("Tahoma", 1, 14));
        this.promoLbl.setForeground(new Color(255, 0, 0));
        this.promoLbl.setHorizontalAlignment(4);
        this.promoLbl.setText("-");
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1).addGroup(layout.createSequentialGroup().addComponent(this.jLabel5).addGap(40, 40, 40).addComponent(this.jLabel6).addGap(41, 41, 41).addComponent(this.jLabel10).addGap(43, 43, 43).addComponent(this.jLabel7).addGap(42, 42, 42).addComponent(this.jLabel8).addGap(18, 18, 18).addComponent(this.jLabel9).addGap(0, 33, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.shortcutLbl))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel3).addComponent(this.jLabel4))).addComponent(this.salesLbl).addComponent(this.keteranganLbl).addComponent(this.kodeImportLabel)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.customerField, -1, 124, Short.MAX_VALUE).addComponent(this.karyawanField, -1, -1, Short.MAX_VALUE).addComponent(this.salesField).addComponent(this.keteranganCmb, 0, -1, Short.MAX_VALUE).addComponent(this.ucImportField)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.importBtn).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.promoLbl, -1, -1, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.customerLbl, -2, 162, -2).addGroup(layout.createSequentialGroup().addComponent(this.jatuhTempoLbl).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent((Component)this.jatuhTempoField, -2, 123, -2)).addComponent(this.salesNameField)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addComponent(this.jLabel11).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.totalField, -2, 247, -2)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addComponent(this.promoPwp).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.tebusMurahBtn)).addComponent(this.reprintBtn, GroupLayout.Alignment.TRAILING)))))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1, -1, -1, Short.MAX_VALUE).addComponent(this.shortcutLbl)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.karyawanField))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.customerLbl, -1, -1, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.jLabel4).addComponent(this.customerField, -2, -1, -2)).addGap(0, 0, Short.MAX_VALUE))).addGap(5, 5, 5).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.salesLbl).addComponent(this.salesField, -2, -1, -2).addComponent(this.salesNameField)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.totalField, -2, 40, -2).addComponent(this.jLabel11, -1, -1, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.reprintBtn).addGap(17, 17, 17))).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.kodeImportLabel).addComponent(this.ucImportField, -2, -1, -2).addComponent(this.importBtn)).addComponent(this.promoLbl, GroupLayout.Alignment.TRAILING)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.keteranganLbl).addComponent(this.keteranganCmb, -2, -1, -2).addComponent(this.jatuhTempoLbl)).addComponent((Component)this.jatuhTempoField, -2, -1, -2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.promoPwp).addComponent(this.tebusMurahBtn))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, 376, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel5).addComponent(this.jLabel6).addComponent(this.jLabel7).addComponent(this.jLabel8).addComponent(this.jLabel9).addComponent(this.jLabel10)).addGap(68, 68, 68)));
    }

    private void removeDataPromoTebus(String idBarangPromo) {
        for (int x = 0; x < this.dataTebusPWP.size(); ++x) {
            if (!this.dataTebusPWP.get(x)[0].equals(idBarangPromo)) continue;
            this.dataTebusPWP.remove(x);
        }
        if (this.dataTebusPWP.size() == 0) {
            System.out.println("size=" + this.dataTebusPWP.size());
            this.promoPwp.setEnabled(false);
        }
    }

    private void checkPromoPeriode() {
        if (this.promoPeriode.getAktifkan().equalsIgnoreCase("AKTIF") && !this.customer.getId().equalsIgnoreCase("C")) {
            Date nowDate = new Date();
            GregorianCalendar nowCalendar = new GregorianCalendar();
            nowCalendar.setTime(nowDate);
            String end = nowCalendar.get(1) + "-" + (nowCalendar.get(2) + 1) + "-" + nowCalendar.get(5);
            if (nowCalendar.get(7) > 1) {
                ((Calendar)nowCalendar).add(5, -nowCalendar.get(7) + 2);
            } else if (nowCalendar.get(7) == 1) {
                ((Calendar)nowCalendar).add(5, -6);
            }
            String start = nowCalendar.get(1) + "-" + (nowCalendar.get(2) + 1) + "-" + nowCalendar.get(5);
            List<Object[]> objects = this.detailService.readPenjualanDetailByCustomerBarangId(start, end, this.customer.getId(), this.promoPeriode.getBarangId());
            if (objects.size() == 0) {
                JOptionPane.showMessageDialog(null, this.promoPeriode.getPesan());
            }
        }
    }

    private void f2Press() {
        if (this.tebusMurahBtn.isEnabled()) {
            this.tebusMurahBtnClick();
        }
        if (!this.promoTebusConfirm) {
            if (this.promoPwp.isEnabled()) {
                this.tebusPWPClick();
            }
            if (!this.promoPWPConfirm) {
                ShiftKasir shiftKasir = this.shiftKasirService.getOpenedShiftKasir(this.helper.getKasir());
                if (shiftKasir != null) {
                    if (this.penjualanTableModel.getRowCount() > 0) {
                        if (this.keteranganCmb.getSelectedItem().equals("KREDIT")) {
                            if (Float.parseFloat(this.totalField.getValue().toString()) + (float)this.penjualanService.getTotalKreditCustomer(this.customer.getId()) > (float)this.customer.getLimitKredit()) {
                                PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
                                passwordInputDialog2.addPasswordInputDialogListener(new passwordSaveEventHandler());
                                int w = passwordInputDialog2.getWidth();
                                int h = passwordInputDialog2.getHeight();
                                passwordInputDialog2.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                                passwordInputDialog2.setJudulText("Customer Sudah Melebihi Batas Kredit, Lanjutkan?");
                                passwordInputDialog2.setVisible(true);
                            } else if (this.penjualanService.getNotaJatuhTempo(this.customer.getId()).size() > 0) {
                                PasswordInputDialog passwordInputDialog3 = new PasswordInputDialog(null, true, "Input Password");
                                passwordInputDialog3.addPasswordInputDialogListener(new passwordSaveEventHandler());
                                int w = passwordInputDialog3.getWidth();
                                int h = passwordInputDialog3.getHeight();
                                passwordInputDialog3.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                                passwordInputDialog3.setJudulText("Nota Jatuh Tempo Harus Dibayarkan Terlebih Dahulu, Lanjutkan?");
                                passwordInputDialog3.setVisible(true);
                            } else if (this.promoVoucher.getStatus().equals("AKTIF")) {
                                int result = 1;
                                boolean canGo = false;
                                String scLabel = this.shortcutLbl.getText();
                                int idSo = 0;
                                int idTo = 0;
                                String statusPembayaranTO = null;
                                String kodeMetodePembayaran = null;
                                if (scLabel == "F11") {
                                    int csoRes = this.checkSO();
                                    if (csoRes != 0) {
                                        canGo = true;
                                    }
                                    if (csoRes > 0) {
                                        idSo = csoRes;
                                    }
                                } else if (scLabel == "F10") {
                                    TransaksiOnline to = this.checkTO();
                                    if (to != null) {
                                        idTo = to.getId();
                                        statusPembayaranTO = to.getStatus_pembayaran();
                                        kodeMetodePembayaran = to.getKode_metode_pembayaran();
                                        canGo = true;
                                    }
                                } else {
                                    canGo = true;
                                }
                                if (canGo && result > 0) {
                                    CetakNota cetakNota = new CetakNota(null, true, this.shortcutLbl.getText(), Float.valueOf(Float.parseFloat(this.totalField.getValue().toString())), this.jatuhTempoField.getDate(), this.keteranganCmb.getSelectedItem().toString(), this.promoPwp.isEnabled(), this.tebusMurahBtn.isEnabled());
                                    cetakNota.setIdSo(idSo);
                                    cetakNota.setIdTo(idTo);
                                    cetakNota.setStatusPembayaranTO(statusPembayaranTO);
                                    cetakNota.setKodeMetodePembayaran(kodeMetodePembayaran);
                                    cetakNota.addListener(new cetakNotaEventHandler());
                                    int w = cetakNota.getWidth();
                                    int h = cetakNota.getHeight();
                                    cetakNota.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                                    cetakNota.setVisible(true);
                                    cetakNota.requestDefaultFocus();
                                } else {
                                    JOptionPane.showMessageDialog(null, "Terjadi Kesalahan pengecekan Sales Order");
                                }
                            } else {
                                int result = 1;
                                boolean canGo = false;
                                String scLabel = this.shortcutLbl.getText();
                                int idSo = 0;
                                int idTo = 0;
                                String statusPembayaranTO = null;
                                String kodeMetodePembayaran = null;
                                if (scLabel == "F11") {
                                    int csoRes = this.checkSO();
                                    if (csoRes != 0) {
                                        canGo = true;
                                    }
                                    if (csoRes > 0) {
                                        idSo = csoRes;
                                    }
                                } else if (scLabel == "F10") {
                                    TransaksiOnline to = this.checkTO();
                                    if (to != null) {
                                        idTo = to.getId();
                                        statusPembayaranTO = to.getStatus_pembayaran();
                                        kodeMetodePembayaran = to.getKode_metode_pembayaran();
                                        canGo = true;
                                    }
                                } else {
                                    canGo = true;
                                }
                                if (canGo && result > 0) {
                                    CetakNota cetakNota = new CetakNota(null, true, this.shortcutLbl.getText(), Float.valueOf(Float.parseFloat(this.totalField.getValue().toString())), this.jatuhTempoField.getDate(), this.keteranganCmb.getSelectedItem().toString(), this.promoPwp.isEnabled(), this.tebusMurahBtn.isEnabled());
                                    cetakNota.setIdSo(idSo);
                                    cetakNota.setIdTo(idTo);
                                    cetakNota.setStatusPembayaranTO(statusPembayaranTO);
                                    cetakNota.setKodeMetodePembayaran(kodeMetodePembayaran);
                                    cetakNota.addListener(new cetakNotaEventHandler());
                                    int w = cetakNota.getWidth();
                                    int h = cetakNota.getHeight();
                                    cetakNota.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                                    cetakNota.setVisible(true);
                                    cetakNota.requestDefaultFocus();
                                } else {
                                    JOptionPane.showMessageDialog(null, "Terjadi Kesalahan pengecekan Sales Order");
                                }
                            }
                        } else if (this.promoVoucher.getStatus().equals("AKTIF")) {
                            int result = 1;
                            boolean canGo = false;
                            String scLabel = this.shortcutLbl.getText();
                            int idSo = 0;
                            int idTo = 0;
                            String statusPembayaranTO = null;
                            String kodeMetodePembayaran = null;
                            if (scLabel == "F11") {
                                int csoRes = this.checkSO();
                                if (csoRes != 0) {
                                    canGo = true;
                                }
                                if (csoRes > 0) {
                                    idSo = csoRes;
                                }
                            } else if (scLabel == "F10") {
                                TransaksiOnline to = this.checkTO();
                                if (to != null) {
                                    idTo = to.getId();
                                    statusPembayaranTO = to.getStatus_pembayaran();
                                    kodeMetodePembayaran = to.getKode_metode_pembayaran();
                                    canGo = true;
                                }
                            } else {
                                canGo = true;
                            }
                            if (canGo && result > 0) {
                                CetakNota2 cetakNota = new CetakNota2(null, true, this.shortcutLbl.getText(), Float.valueOf(Float.parseFloat(this.totalField.getValue().toString())), this.jatuhTempoField.getDate(), this.keteranganCmb.getSelectedItem().toString());
                                cetakNota.setIdSo(idSo);
                                cetakNota.setIdTo(idTo);
                                cetakNota.setStatusPembayaranTO(statusPembayaranTO);
                                cetakNota.setKodeMetodePembayaran(kodeMetodePembayaran);
                                cetakNota.addListener(new cetakNotaEventHandler());
                                int w = cetakNota.getWidth();
                                int h = cetakNota.getHeight();
                                cetakNota.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                                cetakNota.setVisible(true);
                                cetakNota.requestDefaultFocus();
                            } else {
                                JOptionPane.showMessageDialog(null, "Terjadi Kesalahan pencetakan Transaksi");
                            }
                        } else {
                            int result = 1;
                            boolean canGo = false;
                            String scLabel = this.shortcutLbl.getText();
                            int idSo = 0;
                            int idTo = 0;
                            String statusPembayaranTO = null;
                            String kodeMetodePembayaran = null;
                            if (scLabel == "F11") {
                                int csoRes = this.checkSO();
                                if (csoRes != 0) {
                                    canGo = true;
                                }
                                if (csoRes > 0) {
                                    idSo = csoRes;
                                }
                            } else if (scLabel == "F10") {
                                TransaksiOnline to = this.checkTO();
                                if (to != null) {
                                    idTo = to.getId();
                                    statusPembayaranTO = to.getStatus_pembayaran();
                                    kodeMetodePembayaran = to.getKode_metode_pembayaran();
                                    canGo = true;
                                }
                            } else {
                                canGo = true;
                            }
                            if (canGo && result > 0) {
                                CetakNota cetakNota = new CetakNota(null, true, this.shortcutLbl.getText(), Float.valueOf(Float.parseFloat(this.totalField.getValue().toString())), this.jatuhTempoField.getDate(), this.keteranganCmb.getSelectedItem().toString(), this.promoPwp.isEnabled(), this.tebusMurahBtn.isEnabled());
                                cetakNota.setIdSo(idSo);
                                cetakNota.setIdTo(idTo);
                                cetakNota.setStatusPembayaranTO(statusPembayaranTO);
                                cetakNota.setKodeMetodePembayaran(kodeMetodePembayaran);
                                cetakNota.addListener(new cetakNotaEventHandler());
                                int w = cetakNota.getWidth();
                                int h = cetakNota.getHeight();
                                cetakNota.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                                cetakNota.setVisible(true);
                                cetakNota.requestDefaultFocus();
                            } else {
                                JOptionPane.showMessageDialog(null, "Terjadi Kesalahan pencetakan Transaksi");
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Masukkan Data Terlebih Dahulu");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Kasir sedang tertutup, tutup dan buka ulang kasir untuk melakukan transaksi");
                }
            }
        }
    }

    private void customerFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (this.customerField.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Masukkan Nomor HP customer");
                this.customerField.setText(this.customer.getId());
                this.customerLbl.setText(this.customer.getNama() + "-" + this.customer.getAlamat());
            } else {
                this.customer = this.customerField.getText().equalsIgnoreCase("C") ? this.customerService.getCustomerById("C") : this.customerService.getCustomerByTelp(this.customerField.getText());
                if (this.customer == null) {
                    JOptionPane.showMessageDialog(null, "Data Customer Tidak Ditemukan");
                    this.customer = this.customerService.getCustomerById("C");
                    this.customerField.setText(this.customer.getId());
                    this.customerLbl.setText(this.customer.getNama() + "-" + this.customer.getAlamat());
                } else if (this.customer.getTag() != null && this.customer.getTag().equals("D")) {
                    JOptionPane.showMessageDialog(null, "Data Customer Sudah Tidak Aktif");
                    this.customer = this.customerService.getCustomerById("C");
                    this.customerField.setText(this.customer.getId());
                    this.customerLbl.setText(this.customer.getNama() + "-" + this.customer.getAlamat());
                } else if (this.customer != null) {
                    System.out.println(this.customer.getIdCabang());
                    if (this.customer.getIdCabang() > 0) {
                        JOptionPane.showMessageDialog(null, "Gunakan Menu Penjualan Cabang untuk customer Cabang");
                    } else {
                        this.penjualanSementaraService.updateCustomerPenjualanSementara(this.customer.getId(), this.shortcutLbl.getText());
                        this.customerLbl.setText(this.customer.getNama() + "-" + this.customer.getAlamat());
                        this.customerField.setText(this.customer.getId());
                        this.penjualanTable.requestFocus();
                    }
                }
            }
        } else if (evt.getKeyCode() == 112 || evt.getKeyCode() == 113) {
            this.customerField.requestFocus();
            this.customerField.selectAll();
        } else if (evt.getKeyCode() == 114) {
            this.listener.shortcutClick("F3");
        } else if (evt.getKeyCode() == 115) {
            this.listener.shortcutClick("F4");
        } else if (evt.getKeyCode() == 116) {
            this.listener.shortcutClick("F5");
        } else if (evt.getKeyCode() == 117) {
            this.listener.shortcutClick("F6");
        } else if (evt.getKeyCode() == 118) {
            this.listener.shortcutClick("F7");
        } else if (evt.getKeyCode() == 119) {
            this.listener.shortcutClick("F8");
        } else if (evt.getKeyCode() == 120) {
            this.listener.shortcutClick("F9");
        } else if (evt.getKeyCode() == 121) {
            this.listener.shortcutClick("F10");
        } else if (evt.getKeyCode() == 122) {
            this.listener.shortcutClick("F11");
        } else if (evt.getKeyCode() == 123) {
            this.listener.shortcutClick("F12");
        } else if (evt.getKeyCode() == 19 || evt.getKeyCode() == 36) {
            ReprintDialog reprintDialog = new ReprintDialog(null, true);
            int w = reprintDialog.getWidth();
            int h = reprintDialog.getHeight();
            reprintDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            reprintDialog.setVisible(true);
        } else if (evt.getKeyCode() == 27) {
            int a = JOptionPane.showConfirmDialog(null, "Transaksi Penjualan Telah Selesai?", "Keluar Transaksi Penjualan", 0, 3);
            if (a == 0) {
                this.listener.escapeClick();
            }
        } else if (evt.getKeyCode() == 155) {
            if (this.helper.getJenisProgram().equalsIgnoreCase("GUDANGSAJA")) {
                this.insertPress();
            } else if (this.helper.getJenisProgram().equalsIgnoreCase("GUDANGTOKO") && this.helper.getStatusProgram().equals("TOKO")) {
                this.insertPress();
            }
        }
    }

    private void salesFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 113) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Sales");
            sd.addSearchDialogListener(new searchSalesDialogEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
        } else if (evt.getKeyCode() == 10) {
            if (this.salesField.getText().equals("")) {
                SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Sales");
                sd.addSearchDialogListener(new searchSalesDialogEventHandler());
                int w = sd.getWidth();
                int h = sd.getHeight();
                sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                sd.setVisible(true);
            } else {
                this.sales = this.salesService.getSales(this.salesField.getText());
                if (this.sales == null) {
                    JOptionPane.showMessageDialog(null, "Data Sales Tidak Ditemukan");
                } else {
                    this.salesNameField.setName(this.sales.getNama());
                    this.penjualanTable.requestFocus();
                }
            }
        } else if (evt.getKeyCode() == 114) {
            this.listener.shortcutClick("F3");
        } else if (evt.getKeyCode() == 115) {
            this.listener.shortcutClick("F4");
        } else if (evt.getKeyCode() == 116) {
            this.listener.shortcutClick("F5");
        } else if (evt.getKeyCode() == 117) {
            this.listener.shortcutClick("F6");
        } else if (evt.getKeyCode() == 118) {
            this.listener.shortcutClick("F7");
        } else if (evt.getKeyCode() == 119) {
            this.listener.shortcutClick("F8");
        } else if (evt.getKeyCode() == 120) {
            this.listener.shortcutClick("F9");
        } else if (evt.getKeyCode() == 121) {
            this.listener.shortcutClick("F10");
        } else if (evt.getKeyCode() == 122) {
            this.listener.shortcutClick("F11");
        } else if (evt.getKeyCode() == 123) {
            this.listener.shortcutClick("F12");
        } else if (evt.getKeyCode() == 19) {
            ReprintDialog reprintDialog = new ReprintDialog(null, true);
            int w = reprintDialog.getWidth();
            int h = reprintDialog.getHeight();
            reprintDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            reprintDialog.setVisible(true);
        } else if (evt.getKeyCode() == 27) {
            int a = JOptionPane.showConfirmDialog(null, "Transaksi Penjualan Telah Selesai?", "Keluar Transaksi Penjualan", 0, 3);
            if (a == 0) {
                this.listener.escapeClick();
            }
        } else if (evt.getKeyCode() == 155) {
            if (this.helper.getJenisProgram().equalsIgnoreCase("GUDANGSAJA")) {
                this.insertPress();
            } else if (this.helper.getJenisProgram().equalsIgnoreCase("GUDANGTOKO") && this.helper.getStatusProgram().equals("TOKO")) {
                this.insertPress();
            }
        }
    }

    private void keteranganCmbKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 112) {
            this.customerField.requestFocus();
            this.customerField.selectAll();
        } else if (evt.getKeyCode() == 113) {
            if (this.keteranganSoftware.equals("TOKO")) {
                this.f2Press();
            } else if (this.keteranganSoftware.equals("GUDANG")) {
                PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
                passwordInputDialog2.addPasswordInputDialogListener(new passwordInputPenjualan());
                int w = passwordInputDialog2.getWidth();
                int h = passwordInputDialog2.getHeight();
                passwordInputDialog2.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                passwordInputDialog2.setJudulText("Input Password Untuk Mencetak Penjualan");
                passwordInputDialog2.setVisible(true);
            }
        } else if (evt.getKeyCode() == 114) {
            this.listener.shortcutClick("F3");
        } else if (evt.getKeyCode() == 115) {
            this.listener.shortcutClick("F4");
        } else if (evt.getKeyCode() == 116) {
            this.listener.shortcutClick("F5");
        } else if (evt.getKeyCode() == 117) {
            this.listener.shortcutClick("F6");
        } else if (evt.getKeyCode() == 118) {
            this.listener.shortcutClick("F7");
        } else if (evt.getKeyCode() == 119) {
            this.listener.shortcutClick("F8");
        } else if (evt.getKeyCode() == 120) {
            this.listener.shortcutClick("F9");
        } else if (evt.getKeyCode() == 121) {
            this.listener.shortcutClick("F10");
        } else if (evt.getKeyCode() == 122) {
            this.listener.shortcutClick("F11");
        } else if (evt.getKeyCode() == 123) {
            this.listener.shortcutClick("F12");
        } else if (evt.getKeyCode() == 19) {
            ReprintDialog reprintDialog = new ReprintDialog(null, true);
            int w = reprintDialog.getWidth();
            int h = reprintDialog.getHeight();
            reprintDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            reprintDialog.setVisible(true);
        } else if (evt.getKeyCode() == 27) {
            int a = JOptionPane.showConfirmDialog(null, "Transaksi Penjualan Telah Selesai?", "Keluar Transaksi Penjualan", 0, 3);
            if (a == 0) {
                this.listener.escapeClick();
            }
        } else if (evt.getKeyCode() == 155) {
            if (this.helper.getJenisProgram().equalsIgnoreCase("GUDANGSAJA")) {
                this.insertPress();
            } else if (this.helper.getJenisProgram().equalsIgnoreCase("GUDANGTOKO") && this.helper.getStatusProgram().equals("TOKO")) {
                this.insertPress();
            }
        } else if (evt.getKeyCode() == 10) {
            this.penjualanTable.requestFocus();
        } else if (evt.getKeyCode() == 155) {
            if (this.helper.getJenisProgram().equalsIgnoreCase("GUDANGSAJA")) {
                this.insertPress();
            } else if (this.helper.getJenisProgram().equalsIgnoreCase("GUDANGTOKO") && this.helper.getStatusProgram().equals("TOKO")) {
                this.insertPress();
            }
        }
    }

    private void reprintBtnActionPerformed(ActionEvent evt) {
        ReprintDialog reprintDialog = new ReprintDialog(null, true);
        int w = reprintDialog.getWidth();
        int h = reprintDialog.getHeight();
        reprintDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
        reprintDialog.setVisible(true);
        this.penjualanTable.requestFocus();
    }

    private void importBtnActionPerformed(ActionEvent evt) {
        String scLabel = this.shortcutLbl.getText();
        String uc = this.ucImportField.getText();
        int idCabang = Integer.parseInt(this.konstantaService.getConstantValue("idCabang"));
        if (scLabel.equals("F11")) {
            if (uc.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Harap isi code Sales Order untuk import");
            } else {
                boolean result = false;
                SalesOrder so = this.soService.getSalesOrderByUc(uc);
                if (so == null) {
                    JOptionPane.showMessageDialog(null, "Kode Import tidak ditemukan");
                } else if (so.isImported()) {
                    JOptionPane.showMessageDialog(null, "Kode sudah pernah terpakai, harap hubungi korwil");
                } else if (!so.isApproved()) {
                    JOptionPane.showMessageDialog(null, "Sales Order belum di approve, hubungi korwil");
                } else if (so.getId_cabang() != idCabang) {
                    JOptionPane.showMessageDialog(null, "Sales Order bukan untuk cabang ini");
                } else {
                    this.totalField.setValue(0);
                    this.customer = this.customerService.getCustomerById(so.getM_customer_id());
                    this.customerField.setText(this.customer.getTelepon());
                    this.customerField.requestFocus();
                    KeyEvent enterKey = new KeyEvent(this.customerField, 401, System.currentTimeMillis(), 0, 10, '\n');
                    this.customerFieldKeyPressed(enterKey);
                    this.salesField.setText(so.getM_sales_id());
                    KeyEvent enterKey2 = new KeyEvent(this.salesField, 401, System.currentTimeMillis(), 0, 10, '\n');
                    this.salesFieldKeyPressed(enterKey2);
                    this.jatuhTempoField.setDate(so.getJatuh_tempo());
                    if ("KREDIT".equals(so.getKeterangan())) {
                        this.keteranganCmb.setSelectedIndex(1);
                    } else {
                        this.keteranganCmb.setSelectedIndex(0);
                    }
                    List<SalesOrderDetail> sods = this.soService.getSalesOrderDetailById(so.getId());
                    if (sods.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Sales order kosong");
                    } else {
                        this.totalField.setText("0");
                        int rowCount = this.penjualanTableModel.getRowCount();
                        for (int a = 0; a < rowCount; ++a) {
                            this.penjualanTableModel.removeRow(0);
                        }
                        System.out.println(System.currentTimeMillis() / 1000L);
                        this.penjualanSementaraService.deletePenjualanSementaraByShortcut(this.shortcutLbl.getText());
                        ArrayList<PenjualanSementara> penjualanSementarasPush = new ArrayList<PenjualanSementara>();
                        for (int i = 0; i < sods.size(); ++i) {
                            SalesOrderDetail sod = sods.get(i);
                            Object[] table = new Object[]{sod.getId_sku(), sod.getNama_barang(), Float.valueOf(sod.getJumlah_barang()), sod.getSatuan(), sod.getHarga_jual(), Float.valueOf((float)sod.getHarga_jual() * sod.getJumlah_barang()), 0, sod.getTotal_harga()};
                            this.penjualanTableModel.addRow(table);
                            this.totalField.setValue(Float.valueOf(Float.parseFloat(this.totalField.getValue().toString()) + Float.parseFloat(table[7].toString())));
                            PenjualanSementara penjualanSementara = new PenjualanSementara();
                            penjualanSementara.setKasir(this.helper.getKasir());
                            penjualanSementara.setStatus(this.helper.getStatusProgram());
                            penjualanSementara.setShortcut(this.shortcutLbl.getText());
                            penjualanSementara.setCustomerId(this.customerField.getText());
                            penjualanSementara.setSalesId(this.salesField.getText());
                            penjualanSementara.setBarangId(table[0].toString());
                            penjualanSementara.setJumlahBarang(Float.parseFloat(table[2].toString()));
                            penjualanSementara.setSatuan(table[3].toString());
                            penjualanSementara.setHargaJual((long)Float.parseFloat(table[4].toString()));
                            penjualanSementara.setDiscount((long)Float.parseFloat(table[6].toString()));
                            penjualanSementara.setTotalHarga((long)Float.parseFloat(table[7].toString()));
                            penjualanSementara.setNotaKhusus(true);
                            penjualanSementara.setKeterangan(this.keteranganCmb.getSelectedItem().toString());
                            penjualanSementara.setPickinglistId("SO-" + so.getId());
                            penjualanSementarasPush.add(penjualanSementara);
                        }
                        this.penjualanSementaraService.createPenjualanSementaraBatch(penjualanSementarasPush);
                    }
                }
            }
        } else if (scLabel.equals("F10")) {
            if (uc.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Harap isi kode Transaksi Online untuk import");
            } else {
                boolean result = false;
                TransaksiOnline to = this.checkTO();
                if (to == null) {
                    JOptionPane.showMessageDialog(null, "Kode Transaksi Online tidak ditemukan");
                } else {
                    this.customer = this.customerService.getCustomerById(to.getId_customer());
                    this.customerField.setText(this.customer.getTelepon());
                    this.customerField.requestFocus();
                    KeyEvent enterKey = new KeyEvent(this.customerField, 401, System.currentTimeMillis(), 0, 10, '\n');
                    this.customerFieldKeyPressed(enterKey);
                    this.jatuhTempoField.setDate(new Date());
                    this.keteranganCmb.setSelectedIndex(0);
                    List<TransaksiOnlineDetail> tods = this.toService.getTransaksiOnlineDetailById(to.getId());
                    if (tods.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Transaksi Online kosong");
                    } else {
                        PenjualanSementara penjualanSementara;
                        Object[] table;
                        System.out.println("MetodePembayaran:" + to.getKode_metode_pembayaran());
                        if (to.getKode_metode_pembayaran().equals("bayarditempat")) {
                            this.keteranganCmb.setEnabled(true);
                        }
                        this.totalField.setValue(to.getTotal());
                        int rowCount = this.penjualanTableModel.getRowCount();
                        for (int a = 0; a < rowCount; ++a) {
                            this.penjualanTableModel.removeRow(0);
                        }
                        System.out.println(System.currentTimeMillis() / 1000L);
                        this.penjualanSementaraService.deletePenjualanSementaraByShortcut(this.shortcutLbl.getText());
                        ArrayList<PenjualanSementara> penjualanSementarasPush = new ArrayList<PenjualanSementara>();
                        for (int i = 0; i < tods.size(); ++i) {
                            TransaksiOnlineDetail tod = tods.get(i);
                            int harga_jual = tod.getHarga_jual();
                            if (tod.getHarga_promo() > 0) {
                                harga_jual = tod.getHarga_promo();
                            }
                            table = new Object[]{tod.getM_barang_id(), tod.getNama_barang(), tod.getJumlah_barang_revisi(), tod.getSatuan(), harga_jual, harga_jual * tod.getJumlah_barang_revisi(), 0, tod.getTotal_harga()};
                            this.penjualanTableModel.addRow(table);
                            penjualanSementara = new PenjualanSementara();
                            penjualanSementara.setKasir(this.helper.getKasir());
                            penjualanSementara.setStatus(this.helper.getStatusProgram());
                            penjualanSementara.setShortcut(this.shortcutLbl.getText());
                            penjualanSementara.setCustomerId(this.customerField.getText());
                            penjualanSementara.setSalesId(this.salesField.getText());
                            penjualanSementara.setBarangId(table[0].toString());
                            penjualanSementara.setJumlahBarang(Float.parseFloat(table[2].toString()));
                            penjualanSementara.setSatuan(table[3].toString());
                            penjualanSementara.setHargaJual((long)Float.parseFloat(table[4].toString()));
                            penjualanSementara.setDiscount((long)Float.parseFloat(table[6].toString()));
                            penjualanSementara.setTotalHarga((long)Float.parseFloat(table[7].toString()));
                            penjualanSementara.setNotaKhusus(true);
                            penjualanSementara.setKeterangan(this.keteranganCmb.getSelectedItem().toString());
                            penjualanSementara.setIdTransaksiOnline("PPEN" + PenjualanPanel1.padWithLeadingZeros(to.getId()));
                            penjualanSementarasPush.add(penjualanSementara);
                        }
                        int ongkir = to.getBiaya_kirim();
                        int diskonVoucher = to.getNominal_voucher_dipakai();
                        int diskonPoint = to.getPotongan_dari_point();
                        if (ongkir != 0) {
                            table = new Object[]{"ONGKIRAPPS", "ONGKOS KIRIM", 1, "PCS", ongkir, ongkir, 0, ongkir};
                            this.penjualanTableModel.addRow(table);
                            penjualanSementara = new PenjualanSementara();
                            penjualanSementara.setKasir(this.helper.getKasir());
                            penjualanSementara.setStatus(this.helper.getStatusProgram());
                            penjualanSementara.setShortcut(this.shortcutLbl.getText());
                            penjualanSementara.setCustomerId(this.customerField.getText());
                            penjualanSementara.setSalesId(this.salesField.getText());
                            penjualanSementara.setBarangId(table[0].toString());
                            penjualanSementara.setJumlahBarang(Float.parseFloat(table[2].toString()));
                            penjualanSementara.setSatuan(table[3].toString());
                            penjualanSementara.setHargaJual((long)Float.parseFloat(table[4].toString()));
                            penjualanSementara.setDiscount((long)Float.parseFloat(table[6].toString()));
                            penjualanSementara.setTotalHarga((long)Float.parseFloat(table[7].toString()));
                            penjualanSementara.setNotaKhusus(true);
                            penjualanSementara.setKeterangan(this.keteranganCmb.getSelectedItem().toString());
                            penjualanSementara.setIdTransaksiOnline("PPEN" + PenjualanPanel1.padWithLeadingZeros(to.getId()));
                            penjualanSementarasPush.add(penjualanSementara);
                        }
                        if (diskonVoucher != 0) {
                            table = new Object[]{"VOUCHERAPPS", "DISKON VOUCHER APPS", 1, "PCS", -1 * diskonVoucher, -1 * diskonVoucher, 0, -1 * diskonVoucher};
                            this.penjualanTableModel.addRow(table);
                            penjualanSementara = new PenjualanSementara();
                            penjualanSementara.setKasir(this.helper.getKasir());
                            penjualanSementara.setStatus(this.helper.getStatusProgram());
                            penjualanSementara.setShortcut(this.shortcutLbl.getText());
                            penjualanSementara.setCustomerId(this.customerField.getText());
                            penjualanSementara.setSalesId(this.salesField.getText());
                            penjualanSementara.setBarangId(table[0].toString());
                            penjualanSementara.setJumlahBarang(Float.parseFloat(table[2].toString()));
                            penjualanSementara.setSatuan(table[3].toString());
                            penjualanSementara.setHargaJual((long)Float.parseFloat(table[4].toString()));
                            penjualanSementara.setDiscount((long)Float.parseFloat(table[6].toString()));
                            penjualanSementara.setTotalHarga((long)Float.parseFloat(table[7].toString()));
                            penjualanSementara.setNotaKhusus(true);
                            penjualanSementara.setKeterangan(this.keteranganCmb.getSelectedItem().toString());
                            penjualanSementara.setIdTransaksiOnline("PPEN" + PenjualanPanel1.padWithLeadingZeros(to.getId()));
                            penjualanSementarasPush.add(penjualanSementara);
                        }
                        if (diskonPoint != 0) {
                            table = new Object[]{"POINTAPPS", "DISKON POINT APPS", 1, "PCS", -1 * diskonPoint, -1 * diskonPoint, 0, -1 * diskonPoint};
                            this.penjualanTableModel.addRow(table);
                            penjualanSementara = new PenjualanSementara();
                            penjualanSementara.setKasir(this.helper.getKasir());
                            penjualanSementara.setStatus(this.helper.getStatusProgram());
                            penjualanSementara.setShortcut(this.shortcutLbl.getText());
                            penjualanSementara.setCustomerId(this.customerField.getText());
                            penjualanSementara.setSalesId(this.salesField.getText());
                            penjualanSementara.setBarangId(table[0].toString());
                            penjualanSementara.setJumlahBarang(Float.parseFloat(table[2].toString()));
                            penjualanSementara.setSatuan(table[3].toString());
                            penjualanSementara.setHargaJual((long)Float.parseFloat(table[4].toString()));
                            penjualanSementara.setDiscount((long)Float.parseFloat(table[6].toString()));
                            penjualanSementara.setTotalHarga((long)Float.parseFloat(table[7].toString()));
                            penjualanSementara.setNotaKhusus(true);
                            penjualanSementara.setKeterangan(this.keteranganCmb.getSelectedItem().toString());
                            penjualanSementara.setIdTransaksiOnline("PPEN" + PenjualanPanel1.padWithLeadingZeros(to.getId()));
                            penjualanSementarasPush.add(penjualanSementara);
                        }
                        this.penjualanSementaraService.createPenjualanSementaraBatch(penjualanSementarasPush);
                    }
                }
            }
        }
    }

    private void keteranganCmbItemStateChanged(ItemEvent evt) {
        this.penjualanTable.requestFocus();
    }

    private void keteranganCmbActionPerformed(ActionEvent evt) {
    }

    private void penjualanTableKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 112) {
            this.customerField.requestFocus();
            this.customerField.selectAll();
        } else if (evt.getKeyCode() == 113) {
            if (this.keteranganSoftware.equals("TOKO")) {
                this.f2Press();
            } else if (this.keteranganSoftware.equals("GUDANG")) {
                PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
                passwordInputDialog2.addPasswordInputDialogListener(new passwordInputPenjualan());
                int w = passwordInputDialog2.getWidth();
                int h = passwordInputDialog2.getHeight();
                passwordInputDialog2.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                passwordInputDialog2.setJudulText("Input Password");
                passwordInputDialog2.setVisible(true);
            }
        } else if (evt.getKeyCode() == 114) {
            this.listener.shortcutClick("F3");
        } else if (evt.getKeyCode() == 115) {
            this.listener.shortcutClick("F4");
        } else if (evt.getKeyCode() == 116) {
            this.listener.shortcutClick("F5");
        } else if (evt.getKeyCode() == 117) {
            this.listener.shortcutClick("F6");
        } else if (evt.getKeyCode() == 118) {
            this.listener.shortcutClick("F7");
        } else if (evt.getKeyCode() == 119) {
            this.listener.shortcutClick("F8");
        } else if (evt.getKeyCode() == 120) {
            this.listener.shortcutClick("F9");
        } else if (evt.getKeyCode() == 121) {
            this.listener.shortcutClick("F10");
        } else if (evt.getKeyCode() == 122) {
            this.listener.shortcutClick("F11");
        } else if (evt.getKeyCode() == 123) {
            this.listener.shortcutClick("F12");
        } else if (evt.getKeyCode() == 19 || evt.getKeyCode() == 36) {
            ReprintDialog reprintDialog = new ReprintDialog(null, true);
            int w = reprintDialog.getWidth();
            int h = reprintDialog.getHeight();
            reprintDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            reprintDialog.setVisible(true);
        } else if (evt.getKeyCode() == 35) {
            ReprintDialog reprintDialog = new ReprintDialog(null, true);
            int w = reprintDialog.getWidth();
            int h = reprintDialog.getHeight();
            reprintDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            reprintDialog.setVisible(true);
        } else if (evt.getKeyCode() == 27) {
            int a = JOptionPane.showConfirmDialog(null, "Transaksi Penjualan Telah Selesai?", "Keluar Transaksi Penjualan", 0, 3);
            if (a == 0) {
                this.listener.escapeClick();
            }
        } else if (evt.getKeyCode() == 10) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String nowDate = simpleDateFormat.format(new Date());
            List<ShiftKasir> shiftKasirs = this.shiftKasirService.getIsOpenShiftsBeforeDate(nowDate);
            System.out.println(shiftKasirs.size());
            if (shiftKasirs.isEmpty()) {
                if (this.penjualanTable.getSelectedRow() != -1) {
                    if (this.shortcutLbl.getText().equals("F12")) {
                        JOptionPane.showMessageDialog(null, "Penjualan Tidak Dapat Diedit");
                    } else {
                        int sumBarang = this.penjualanSementaraService.sumBarang(this.helper.getKasir(), this.shortcutLbl.getText(), this.penjualanTableModel.getValueAt(this.penjualanTable.getSelectedRow(), 0).toString(), this.penjualanTableModel.getValueAt(this.penjualanTable.getSelectedRow(), 3).toString());
                        float selectedItem = Float.parseFloat(this.penjualanTableModel.getValueAt(this.penjualanTable.getSelectedRow(), 2).toString());
                        if (sumBarang <= 0 || selectedItem < 0.0f) {
                            boolean notaKhusus = false;
                            if (this.customer.getId().equals("2302070001_2")) {
                                notaKhusus = true;
                            }
                            PenjualanDetailDialog1 penjualanDetailDialog = new PenjualanDetailDialog1(null, true, this.penjualanTableModel, this.customer.getId(), this.shortcutLbl.getText(), notaKhusus, (Long)this.totalField.getValue(), this.keteranganCmb.getSelectedItem().toString());
                            penjualanDetailDialog.escapeClick();
                            penjualanDetailDialog.addListener(new penjualanDetailDialogEventHandler());
                            int w = penjualanDetailDialog.getWidth();
                            int h = penjualanDetailDialog.getHeight();
                            penjualanDetailDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                            penjualanDetailDialog.setVisible(true);
                        } else if (sumBarang > 0) {
                            boolean notaKhusus = false;
                            if (this.customer.getId().equals("2302070001_2")) {
                                notaKhusus = true;
                            }
                            PenjualanDetailDialog1 penjualanDetailDialog = new PenjualanDetailDialog1(null, true, this.penjualanTableModel, this.customer.getId(), this.shortcutLbl.getText(), notaKhusus, Long.parseLong(this.totalField.getValue().toString()), this.keteranganCmb.getSelectedItem().toString());
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
                        }
                    }
                } else if (this.shortcutLbl.getText().equals("F12")) {
                    JOptionPane.showMessageDialog(null, "Penjualan Tidak Dapat Diedit");
                } else {
                    boolean notaKhusus = false;
                    if (this.customer.getId().equals("2103130001_2")) {
                        notaKhusus = true;
                    }
                    PenjualanDetailDialog1 penjualanDetailDialog = new PenjualanDetailDialog1(null, true, this.penjualanTableModel, this.customer.getId(), this.shortcutLbl.getText(), notaKhusus, (Long)this.totalField.getValue(), this.keteranganCmb.getSelectedItem().toString());
                    penjualanDetailDialog.escapeClick();
                    penjualanDetailDialog.addListener(new penjualanDetailDialogEventHandler());
                    int w = penjualanDetailDialog.getWidth();
                    int h = penjualanDetailDialog.getHeight();
                    penjualanDetailDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                    penjualanDetailDialog.setVisible(true);
                }
            } else {
                String namaKasir = "";
                for (ShiftKasir shiftKasir : shiftKasirs) {
                    namaKasir = namaKasir + shiftKasir.getIdKasir() + ",";
                }
                JOptionPane.showMessageDialog(null, "Kasir " + namaKasir + " belum di closing hari kemarin");
            }
        } else if (evt.getKeyCode() == 127) {
            int isvoid = this.penjualanSementaraService.isVoid(this.helper.getKasir(), this.shortcutLbl.getText(), this.penjualanTableModel.getValueAt(this.penjualanTable.getSelectedRow(), 0).toString());
            if (isvoid == 1) {
                JOptionPane.showMessageDialog(null, "Item Void Tidak Dapat Diubah");
            } else {
                PasswordInputDialog passwordInputDialog3 = new PasswordInputDialog(null, true, "Input Password");
                passwordInputDialog3.addPasswordInputDialogListener(new passwordInputDialogEventHandler2());
                int w = passwordInputDialog3.getWidth();
                int h = passwordInputDialog3.getHeight();
                passwordInputDialog3.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                passwordInputDialog3.setJudulText("Input Password Untuk Delete Barang");
                passwordInputDialog3.setVisible(true);
            }
        } else if (evt.getKeyCode() == 155) {
            if (this.helper.getJenisProgram().equalsIgnoreCase("GUDANGSAJA")) {
                this.insertPress();
            } else if (this.helper.getJenisProgram().equalsIgnoreCase("GUDANGTOKO") && this.helper.getStatusProgram().equals("TOKO")) {
                this.insertPress();
            }
        } else if (evt.getKeyCode() == 34 && this.shortcutLbl.getText().equalsIgnoreCase("F12")) {
            PenjualanParsialDialog ppd = new PenjualanParsialDialog(null, true);
            ppd.addDialogListener(new penjualanParsialDialogEventHandler());
            int w = ppd.getWidth();
            int h = ppd.getHeight();
            ppd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            ppd.setVisible(true);
        }
    }

    private void tebusMurahBtnMouseClicked(MouseEvent evt) {
        if (this.tebusMurahBtn.isEnabled()) {
            this.tebusMurahBtnClick();
        }
    }

    private void promoPwpMouseClicked(MouseEvent evt) {
        if (this.promoPwp.isEnabled()) {
            this.tebusPWPClick();
        }
    }

    public void addTransaksiListener(PenjualanPanelListener transaksiListener) {
        this.listener = transaksiListener;
    }

    private int checkSO() {
        int idSO;
        if (this.shortcutLbl.getText().equals("F11")) {
            String uc = this.ucImportField.getText();
            SalesOrder so = this.soService.getSalesOrderByUc(uc);
            int idCabang = Integer.parseInt(this.konstantaService.getConstantValue("idCabang"));
            if (so == null) {
                idSO = 0;
                JOptionPane.showMessageDialog(null, "Kode Import tidak ditemukan");
            } else {
                idSO = so.getId();
                if (so.isImported()) {
                    idSO = 0;
                    JOptionPane.showMessageDialog(null, "Kode sudah pernah tercetak, harap hubungi korwil");
                } else if (!so.isApproved()) {
                    idSO = 0;
                    JOptionPane.showMessageDialog(null, "Sales Order belum di approve, hubungi korwil");
                } else if (so.getId_cabang() != idCabang) {
                    idSO = 0;
                    JOptionPane.showMessageDialog(null, "Sales Order bukan untuk cabang ini");
                }
            }
        } else {
            idSO = -1;
        }
        return idSO;
    }

    private TransaksiOnline checkTO() {
        String uc = this.ucImportField.getText();
        System.out.println(uc);
        int idTO = PenjualanPanel1.extractNumberSuffix(uc);
        TransaksiOnline to = this.toService.getTransaksiOnlineByUc(idTO);
        if (this.shortcutLbl.getText().equals("F10")) {
            int idCabang = Integer.parseInt(this.konstantaService.getConstantValue("idCabang"));
            if (to != null) {
                idTO = to.getId();
                if (to.getId_transaksi_offline() != null) {
                    JOptionPane.showMessageDialog(null, "Kode Transaksi sudah tercetak dengan ID Penjualan " + to.getId_transaksi_offline());
                    to = null;
                }
                if (to.getM_sales_id() != null && !"".equals(to.getM_sales_id()) && !to.isApproval_sales()) {
                    JOptionPane.showMessageDialog(null, "Transaksi belum mendapat approval dari sales " + to.getNama_sales());
                    to = null;
                }
                if (!to.isApproval_superuser()) {
                    JOptionPane.showMessageDialog(null, "Transaksi belum mendapat approval dari Superuser / Manager Marketing / Korwil. Hubungi pihak terkait!");
                    to = null;
                } else if (idCabang != 10 && to.getId_cabang() != idCabang) {
                    to = null;
                    JOptionPane.showMessageDialog(null, "Transaksi Online bukan untuk cabang ini");
                }
            }
        } else {
            to = null;
        }
        return to;
    }

    private class passwordInputDialogEventHandler
    implements PasswordInputListener {
        private passwordInputDialogEventHandler() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("admin") || karyawan.getRole().equalsIgnoreCase("manager")) {
                PenjualanPanel1.this.insertPress();
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class closeKasirEventHandler
    implements CloseKasirListener {
        private closeKasirEventHandler() {
        }

        @Override
        public void keluarPenjualan() {
            PenjualanPanel1.this.listener.escapeClick();
        }
    }

    private class penjualanBarcodeEventHandler
    implements PenjualanBarcodeListener {
        private penjualanBarcodeEventHandler() {
        }

        @Override
        public void saveClick(Object[] rowValue) {
            int a;
            Long currentTime = System.currentTimeMillis();
            boolean newData = true;
            for (a = 0; a < PenjualanPanel1.this.penjualanTableModel.getRowCount(); ++a) {
                if (!PenjualanPanel1.this.penjualanTableModel.getValueAt(a, 0).equals(rowValue[0])) continue;
                newData = false;
                break;
            }
            if (newData) {
                PenjualanPanel1.this.penjualanTableModel.addRow(rowValue);
                PenjualanPanel1.this.totalField.setValue(Math.round(Float.parseFloat(PenjualanPanel1.this.totalField.getValue().toString()) + Float.parseFloat(rowValue[7].toString())));
            } else {
                PenjualanPanel1.this.totalField.setValue(Math.round(Float.parseFloat(PenjualanPanel1.this.totalField.getValue().toString()) - Float.parseFloat(PenjualanPanel1.this.penjualanTableModel.getValueAt(a, 7).toString())));
                PenjualanPanel1.this.totalField.setValue(Math.round(Float.parseFloat(PenjualanPanel1.this.totalField.getValue().toString()) + Float.parseFloat(rowValue[7].toString())));
                PenjualanPanel1.this.penjualanTableModel.removeRow(a);
                PenjualanPanel1.this.penjualanTableModel.addRow(rowValue);
            }
            PenjualanSementara penjualanSementara = new PenjualanSementara();
            penjualanSementara.setKasir(PenjualanPanel1.this.helper.getKasir());
            penjualanSementara.setStatus(PenjualanPanel1.this.helper.getStatusProgram());
            penjualanSementara.setShortcut(PenjualanPanel1.this.shortcutLbl.getText());
            penjualanSementara.setCustomerId(PenjualanPanel1.this.customer.getId());
            penjualanSementara.setBarangId(rowValue[0].toString());
            penjualanSementara.setJumlahBarang(Float.parseFloat(rowValue[2].toString()));
            penjualanSementara.setSatuan(rowValue[3].toString());
            penjualanSementara.setHargaJual((long)Float.parseFloat(rowValue[4].toString()));
            penjualanSementara.setDiscount((long)Float.parseFloat(rowValue[6].toString()));
            penjualanSementara.setTotalHarga((long)Float.parseFloat(rowValue[7].toString()));
            penjualanSementara.setKeterangan(PenjualanPanel1.this.keteranganCmb.getSelectedItem().toString());
            penjualanSementara.setSalesId(PenjualanPanel1.this.salesField.getText());
            int status = PenjualanPanel1.this.penjualanSementaraService.deletePenjualanSementara(PenjualanPanel1.this.shortcutLbl.getText(), penjualanSementara.getBarangId());
            PenjualanPanel1.this.penjualanSementaraService.createPenjualanSementara(penjualanSementara);
        }

        @Override
        public void refreshData() {
            if (PenjualanPanel1.this.shortcutLbl.getText().equals("F3")) {
                PenjualanPanel1.this.listener.shortcutClick("F3");
            } else if (PenjualanPanel1.this.shortcutLbl.getText().equals("F4")) {
                PenjualanPanel1.this.listener.shortcutClick("F4");
            } else if (PenjualanPanel1.this.shortcutLbl.getText().equals("F5")) {
                PenjualanPanel1.this.listener.shortcutClick("F5");
            } else if (PenjualanPanel1.this.shortcutLbl.getText().equals("F6")) {
                PenjualanPanel1.this.listener.shortcutClick("F6");
            } else if (PenjualanPanel1.this.shortcutLbl.getText().equals("F7")) {
                PenjualanPanel1.this.listener.shortcutClick("F7");
            } else if (PenjualanPanel1.this.shortcutLbl.getText().equals("F8")) {
                PenjualanPanel1.this.listener.shortcutClick("F8");
            } else if (PenjualanPanel1.this.shortcutLbl.getText().equals("F9")) {
                PenjualanPanel1.this.listener.shortcutClick("F9");
            } else if (PenjualanPanel1.this.shortcutLbl.getText().equals("F10")) {
                PenjualanPanel1.this.listener.shortcutClick("F10");
            } else if (PenjualanPanel1.this.shortcutLbl.getText().equals("F11")) {
                PenjualanPanel1.this.listener.shortcutClick("F11");
            } else if (PenjualanPanel1.this.shortcutLbl.getText().equals("F12")) {
                PenjualanPanel1.this.listener.shortcutClick("F12");
            }
        }
    }

    private class passwordSaveEventHandler
    implements PasswordInputListener {
        private passwordSaveEventHandler() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR") || karyawan.getRole().equalsIgnoreCase("ACCOUNTING")) {
                if (PenjualanPanel1.this.promoVoucher.getStatus().equals("AKTIF")) {
                    int result = 1;
                    boolean canGo = false;
                    String scLabel = PenjualanPanel1.this.shortcutLbl.getText();
                    int idSo = 0;
                    int idTo = 0;
                    String statusPembayaranTO = null;
                    String kodeMetodePembayaran = null;
                    if (scLabel == "F11") {
                        int csoRes = PenjualanPanel1.this.checkSO();
                        if (csoRes != 0) {
                            canGo = true;
                        }
                        if (csoRes > 0) {
                            idSo = csoRes;
                        }
                    } else if (scLabel == "F10") {
                        TransaksiOnline to = PenjualanPanel1.this.checkTO();
                        if (to != null) {
                            idTo = to.getId();
                            statusPembayaranTO = to.getStatus_pembayaran();
                            kodeMetodePembayaran = to.getKode_metode_pembayaran();
                            canGo = true;
                        }
                    } else {
                        canGo = true;
                    }
                    if (canGo && result > 0) {
                        CetakNota cetakNota = new CetakNota(null, true, PenjualanPanel1.this.shortcutLbl.getText(), Float.valueOf(Float.parseFloat(PenjualanPanel1.this.totalField.getValue().toString())), PenjualanPanel1.this.jatuhTempoField.getDate(), PenjualanPanel1.this.keteranganCmb.getSelectedItem().toString(), PenjualanPanel1.this.promoPwp.isEnabled(), PenjualanPanel1.this.tebusMurahBtn.isEnabled());
                        cetakNota.setIdSo(idSo);
                        cetakNota.setIdTo(idTo);
                        cetakNota.setStatusPembayaranTO(statusPembayaranTO);
                        cetakNota.setKodeMetodePembayaran(kodeMetodePembayaran);
                        cetakNota.addListener(new cetakNotaEventHandler());
                        int w = cetakNota.getWidth();
                        int h = cetakNota.getHeight();
                        cetakNota.setLocation((PenjualanPanel1.this.screenWidth - w) / 2, (PenjualanPanel1.this.screenHeight - h) / 2);
                        cetakNota.setVisible(true);
                        cetakNota.requestDefaultFocus();
                    } else {
                        JOptionPane.showMessageDialog(null, "Terjadi Kesalahan pengecekan Sales Order");
                    }
                } else {
                    int result = 1;
                    boolean canGo = false;
                    String scLabel = PenjualanPanel1.this.shortcutLbl.getText();
                    int idSo = 0;
                    int idTo = 0;
                    String statusPembayaranTO = null;
                    String kodeMetodePembayaran = null;
                    if (scLabel == "F11") {
                        int csoRes = PenjualanPanel1.this.checkSO();
                        if (csoRes != 0) {
                            canGo = true;
                        }
                        if (csoRes > 0) {
                            idSo = csoRes;
                        }
                    } else if (scLabel == "F10") {
                        TransaksiOnline to = PenjualanPanel1.this.checkTO();
                        if (to != null) {
                            idTo = to.getId();
                            statusPembayaranTO = to.getStatus_pembayaran();
                            kodeMetodePembayaran = to.getKode_metode_pembayaran();
                            canGo = true;
                        }
                    } else {
                        canGo = true;
                    }
                    if (canGo && result > 0) {
                        CetakNota cetakNota = new CetakNota(null, true, PenjualanPanel1.this.shortcutLbl.getText(), Float.valueOf(Float.parseFloat(PenjualanPanel1.this.totalField.getValue().toString())), PenjualanPanel1.this.jatuhTempoField.getDate(), PenjualanPanel1.this.keteranganCmb.getSelectedItem().toString(), PenjualanPanel1.this.promoPwp.isEnabled(), PenjualanPanel1.this.tebusMurahBtn.isEnabled());
                        cetakNota.setIdSo(idSo);
                        cetakNota.setIdTo(idTo);
                        cetakNota.setStatusPembayaranTO(statusPembayaranTO);
                        cetakNota.setKodeMetodePembayaran(kodeMetodePembayaran);
                        cetakNota.addListener(new cetakNotaEventHandler());
                        int w = cetakNota.getWidth();
                        int h = cetakNota.getHeight();
                        cetakNota.setLocation((PenjualanPanel1.this.screenWidth - w) / 2, (PenjualanPanel1.this.screenHeight - h) / 2);
                        cetakNota.setVisible(true);
                        cetakNota.requestDefaultFocus();
                    } else {
                        JOptionPane.showMessageDialog(null, "Terjadi Kesalahan pengecekan Sales Order");
                    }
                }
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class cetakNotaEventHandler
    implements CetakNotaListener {
        private cetakNotaEventHandler() {
        }

        @Override
        public void printClick() {
            PenjualanPanel1.this.totalField.setValue(0L);
            if (PenjualanPanel1.this.helper.getKasir() == 100 && PenjualanPanel1.this.helper.getStatusProgram().equals("GUDANG")) {
                PenjualanPanel1.this.customer = PenjualanPanel1.this.customerService.getCustomerById("SINAR");
            } else {
                PenjualanPanel1.this.customer = PenjualanPanel1.this.customerService.getCustomerById("C");
            }
            PenjualanPanel1.this.customerField.setText(PenjualanPanel1.this.customer.getId());
            PenjualanPanel1.this.customerLbl.setText(PenjualanPanel1.this.customer.getNama() + "-" + PenjualanPanel1.this.customer.getAlamat());
            PenjualanPanel1.this.keteranganCmb.setSelectedIndex(0);
            PenjualanPanel1.this.sales = null;
            PenjualanPanel1.this.salesField.setText("");
            PenjualanPanel1.this.salesNameField.setText("-");
            int rowCount = PenjualanPanel1.this.penjualanTableModel.getRowCount();
            for (int a = 0; a < rowCount; ++a) {
                PenjualanPanel1.this.penjualanTableModel.removeRow(0);
            }
            PenjualanPanel1.this.penjualanSementaraService.deletePenjualanSementaraByShortcut(PenjualanPanel1.this.shortcutLbl.getText());
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
            for (a = 0; a < PenjualanPanel1.this.penjualanTableModel.getRowCount(); ++a) {
                System.out.println("dataif=" + PenjualanPanel1.this.penjualanTableModel.getValueAt(a, 0) + "||" + Float.parseFloat(PenjualanPanel1.this.penjualanTableModel.getValueAt(a, 2).toString()) + PenjualanPanel1.this.penjualanTableModel.getValueAt(a, 3));
                if (!PenjualanPanel1.this.penjualanTableModel.getValueAt(a, 0).equals(rowValue[0]) || !(Float.parseFloat(PenjualanPanel1.this.penjualanTableModel.getValueAt(a, 2).toString()) > 0.0f)) continue;
                newData = false;
                break;
            }
            if (newData) {
                PenjualanPanel1.this.penjualanTableModel.addRow(rowValue);
                PenjualanPanel1.this.totalField.setValue(Math.round(Float.parseFloat(PenjualanPanel1.this.totalField.getValue().toString()) + Float.parseFloat(rowValue[7].toString())));
            } else {
                if (removeRow) {
                    PenjualanPanel1.this.penjualanSementaraService.deletePenjualanSementara(PenjualanPanel1.this.shortcutLbl.getText(), PenjualanPanel1.this.penjualanTableModel.getValueAt(a, 0).toString(), Float.parseFloat(PenjualanPanel1.this.penjualanTableModel.getValueAt(a, 2).toString()), PenjualanPanel1.this.penjualanTableModel.getValueAt(a, 3).toString());
                    PenjualanPanel1.this.totalField.setValue(Math.round(Float.parseFloat(PenjualanPanel1.this.totalField.getValue().toString()) - Float.parseFloat(PenjualanPanel1.this.penjualanTableModel.getValueAt(a, 7).toString())));
                    PenjualanPanel1.this.penjualanTableModel.removeRow(a);
                }
                PenjualanPanel1.this.penjualanTableModel.addRow(rowValue);
                PenjualanPanel1.this.totalField.setValue(Math.round(Float.parseFloat(PenjualanPanel1.this.totalField.getValue().toString()) + Float.parseFloat(rowValue[7].toString())));
            }
            PenjualanPanel1.this.cekPromoLbl();
            PenjualanSementara penjualanSementara = new PenjualanSementara();
            penjualanSementara.setKasir(PenjualanPanel1.this.helper.getKasir());
            penjualanSementara.setStatus(PenjualanPanel1.this.helper.getStatusProgram());
            penjualanSementara.setShortcut(PenjualanPanel1.this.shortcutLbl.getText());
            penjualanSementara.setCustomerId(PenjualanPanel1.this.customer.getId());
            penjualanSementara.setSalesId(PenjualanPanel1.this.salesField.getText());
            penjualanSementara.setBarangId(rowValue[0].toString());
            penjualanSementara.setJumlahBarang(Float.parseFloat(rowValue[2].toString()));
            penjualanSementara.setSatuan(rowValue[3].toString());
            penjualanSementara.setHargaJual((long)Float.parseFloat(rowValue[4].toString()));
            penjualanSementara.setDiscount((long)Float.parseFloat(rowValue[6].toString()));
            penjualanSementara.setTotalHarga((long)Float.parseFloat(rowValue[7].toString()));
            penjualanSementara.setNotaKhusus((Boolean)rowValue[8]);
            penjualanSementara.setKeterangan(PenjualanPanel1.this.keteranganCmb.getSelectedItem().toString());
            penjualanSementara.setPromo(rowValue[9].toString());
            int status = PenjualanPanel1.this.penjualanSementaraService.createPenjualanSementara(penjualanSementara);
            PenjualanPanel1.this.keteranganCmb.setEnabled(false);
        }

        @Override
        public void deleteClick(String idBarang) {
            PenjualanSementara penjualanSementara = new PenjualanSementara();
            penjualanSementara.setBarangId(idBarang);
            PenjualanPanel1.this.penjualanSementaraService.deletePenjualanSementara(PenjualanPanel1.this.shortcutLbl.getText(), penjualanSementara.getBarangId());
            PenjualanPanel1.this.removeAllRow();
            PenjualanPanel1.this.loadData();
        }

        @Override
        public void refreshData() {
            PenjualanPanel1.this.removeAllRow();
            PenjualanPanel1.this.loadData();
        }

        @Override
        public DefaultTableModel getPenjualanTableModel() {
            return PenjualanPanel1.this.penjualanTableModel;
        }

        @Override
        public void changeButtonPromoTebusMurah() {
            PenjualanPanel1.this.checkButtonPromo();
        }

        @Override
        public void changeButtonPromoTebus(String state, List<Object[]> data) {
            PenjualanPanel1.this.checkButtonPromoTebus(state, data);
        }
    }

    private class searchPanelCustomerEventHandler
    implements SearchPanelListener {
        private searchPanelCustomerEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            PenjualanPanel1.this.customer = PenjualanPanel1.this.customerService.getCustomerById(chooseValue);
            PenjualanPanel1.this.penjualanSementaraService.updateCustomerPenjualanSementara(PenjualanPanel1.this.customer.getId(), PenjualanPanel1.this.shortcutLbl.getText());
            PenjualanPanel1.this.customerField.setText(PenjualanPanel1.this.customer.getId());
            PenjualanPanel1.this.customerLbl.setText(PenjualanPanel1.this.customer.getNama() + "-" + PenjualanPanel1.this.customer.getAlamat());
            PenjualanPanel1.this.keteranganCmb.setSelectedIndex(0);
            PenjualanPanel1.this.jatuhTempoField.setDate(new Date());
            PenjualanPanel1.this.penjualanTable.requestFocus();
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
            List<Customer> customers = PenjualanPanel1.this.customerService.getCustomers(searchWord);
            for (Customer customer : customers) {
                Object[] temp;
                if (PenjualanPanel1.this.helper.getJenisProgram().equalsIgnoreCase("GUDANGTOKO")) {
                    if (PenjualanPanel1.this.helper.getStatusProgram().equalsIgnoreCase("TOKO")) {
                        temp = new String[]{customer.getId(), customer.getNama(), customer.getAlamat(), customer.getKota()};
                        dtm.addRow(temp);
                        continue;
                    }
                    if (!PenjualanPanel1.this.helper.getStatusProgram().equalsIgnoreCase("GUDANG") || !customer.getNama().equalsIgnoreCase("SINARMART")) continue;
                    temp = new String[]{customer.getId(), customer.getNama(), customer.getAlamat(), customer.getKota()};
                    dtm.addRow(temp);
                    continue;
                }
                if (!PenjualanPanel1.this.helper.getJenisProgram().equalsIgnoreCase("GUDANGSAJA")) continue;
                temp = new String[]{customer.getId(), customer.getNama(), customer.getAlamat(), customer.getKota()};
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
            PenjualanPanel1.this.canGoThrough = true;
        }

        @Override
        public void noClick() {
            PenjualanPanel1.this.canGoThrough = false;
        }
    }

    private class popUpConfirmDialogEventHandler
    implements ConfirmationDialogListener {
        private popUpConfirmDialogEventHandler() {
        }

        @Override
        public void yesClick() {
            PenjualanPanel1.this.promoTebusConfirm = true;
        }

        @Override
        public void noClick() {
            PenjualanPanel1.this.promoTebusConfirm = false;
        }
    }

    private class popUpConfirmPWPDialogEventHandler
    implements ConfirmationDialogListener {
        private popUpConfirmPWPDialogEventHandler() {
        }

        @Override
        public void yesClick() {
            PenjualanPanel1.this.promoPWPConfirm = true;
        }

        @Override
        public void noClick() {
            PenjualanPanel1.this.promoPWPConfirm = false;
        }
    }

    private class searchSalesDialogEventHandler
    implements SearchDialogListener {
        private searchSalesDialogEventHandler() {
        }

        @Override
        public void searchClick(String searchWord) {
            Object[] colName = new String[]{"KODE SALES", "NAMA SALES", "ALAMAT", "TARGET"};
            DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
            List<Sales> saleses = PenjualanPanel1.this.salesService.getSaleses(searchWord);
            for (Sales sales : saleses) {
                Object[] temp = new String[]{sales.getId(), sales.getNama(), sales.getAlamat(), sales.getTarget() + ""};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Sales");
            sp.addSearchPanelListener(new searchPanelSalesEventHandler());
            sp.setVisible(true);
        }

        public void dispose() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        private class searchPanelSalesEventHandler
        implements SearchPanelListener {
            private searchPanelSalesEventHandler() {
            }

            @Override
            public void tableClick(String chooseValue) {
                PenjualanPanel1.this.salesField.setText(chooseValue);
                PenjualanPanel1.this.sales = PenjualanPanel1.this.salesService.getSales(PenjualanPanel1.this.salesField.getText());
                PenjualanPanel1.this.penjualanSementaraService.updateSalesPenjualanSementara(PenjualanPanel1.this.sales.getId(), PenjualanPanel1.this.shortcutLbl.getText());
                PenjualanPanel1.this.salesNameField.setText(PenjualanPanel1.this.sales.getNama());
                PenjualanPanel1.this.penjualanTable.requestFocus();
            }
        }
    }

    private class penjualanParsialDialogEventHandler
    implements PenjualanParsialDialogListener {
        private penjualanParsialDialogEventHandler() {
        }

        @Override
        public void okKlik(String penjualanParsialId) {
            PenjualanParsial penjualanParsial = PenjualanPanel1.this.penjualanParsialService.getPenjualanParsial(penjualanParsialId);
            if (penjualanParsial == null) {
                JOptionPane.showMessageDialog(null, "DATA TIDAK DITEMUKAN");
            } else {
                PenjualanPanel1.this.customer = PenjualanPanel1.this.customerService.getCustomerById(penjualanParsial.getCustomerId());
                PenjualanPanel1.this.customerField.setText(PenjualanPanel1.this.customer.getId());
                PenjualanPanel1.this.customerLbl.setText(PenjualanPanel1.this.customer.getNama());
                List<PenjualanParsialDetail> penjualanParsialDetails = PenjualanPanel1.this.penjualanParsialDetailService.getPenjualanParsials(penjualanParsialId);
                for (PenjualanParsialDetail penjualanParsialDetail : penjualanParsialDetails) {
                    float tempValue;
                    PenjualanPanel1.this.total += penjualanParsialDetail.getTotalHarga();
                    Barang barang = PenjualanPanel1.this.barangService.getBarang(penjualanParsialDetail.getBarangId());
                    if (barang == null) continue;
                    Object[] rowValue = new Object[]{barang.getId(), barang.getNama(), (tempValue = penjualanParsialDetail.getJumlahBarang()) == (float)((long)tempValue) ? (Number)((long)penjualanParsialDetail.getJumlahBarang()) : (Number)Float.valueOf(penjualanParsialDetail.getJumlahBarang()), penjualanParsialDetail.getSatuan(), penjualanParsialDetail.getHargaJual(), (long)((float)penjualanParsialDetail.getHargaJual() * penjualanParsialDetail.getJumlahBarang()), penjualanParsialDetail.getDiscount(), penjualanParsialDetail.getTotalHarga()};
                    PenjualanPanel1.this.penjualanTableModel.addRow(rowValue);
                    PenjualanSementara penjualanSementara = new PenjualanSementara();
                    penjualanSementara.setKasir(PenjualanPanel1.this.helper.getKasir());
                    penjualanSementara.setStatus(PenjualanPanel1.this.helper.getStatusProgram());
                    penjualanSementara.setShortcut(PenjualanPanel1.this.shortcutLbl.getText());
                    penjualanSementara.setCustomerId(PenjualanPanel1.this.customer.getId());
                    penjualanSementara.setSalesId(PenjualanPanel1.this.salesField.getText());
                    penjualanSementara.setBarangId(penjualanParsialDetail.getBarangId());
                    penjualanSementara.setJumlahBarang(penjualanParsialDetail.getJumlahBarang());
                    penjualanSementara.setSatuan(penjualanParsialDetail.getSatuan());
                    penjualanSementara.setHargaJual(penjualanParsialDetail.getHargaJual());
                    penjualanSementara.setDiscount(penjualanParsialDetail.getDiscount());
                    penjualanSementara.setTotalHarga(penjualanParsialDetail.getTotalHarga());
                    penjualanSementara.setNotaKhusus(false);
                    penjualanSementara.setKeterangan("CASH");
                    PenjualanPanel1.this.penjualanSementaraService.createPenjualanSementara(penjualanSementara);
                }
                PenjualanPanel1.this.totalField.setValue(PenjualanPanel1.this.total);
            }
        }
    }

    private class passwordInputDialogEventHandler2
    implements PasswordInputListener {
        private passwordInputDialogEventHandler2() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR") || karyawan.getRole().equalsIgnoreCase("MANAGER") || karyawan.getRole().equalsIgnoreCase("ADMIN")) {
                int a;
                if (PenjualanPanel1.this.shortcutLbl.getText().equals("F12") || PenjualanPanel1.this.shortcutLbl.getText().equals("F11")) {
                    PenjualanPanel1.this.canGoThrough = false;
                    ConfirmationDialog confirmationDialog = new ConfirmationDialog(null, true, "DELETE SEMUA DATA PENJUALAN?");
                    confirmationDialog.addListener(new confirmDialogEventHandler());
                    int w = confirmationDialog.getWidth();
                    int h = confirmationDialog.getHeight();
                    confirmationDialog.setLocation((PenjualanPanel1.this.screenWidth - w) / 2, (PenjualanPanel1.this.screenHeight - h) / 2);
                    confirmationDialog.setVisible(true);
                    if (PenjualanPanel1.this.canGoThrough) {
                        PenjualanPanel1.this.penjualanSementaraService.deletePenjualanSementaraByShortcut(PenjualanPanel1.this.shortcutLbl.getText());
                        PenjualanPanel1.this.removeAllRow();
                        PenjualanPanel1.this.customerField.setText("C");
                        PenjualanPanel1.this.customerLbl.setText("CASH");
                        PenjualanPanel1.this.totalField.setValue(0L);
                    }
                } else if (PenjualanPanel1.this.penjualanTable.getSelectedRow() != -1 && (a = JOptionPane.showConfirmDialog(null, "Hapus Data Ini?", "Delete Data", 0, 3)) == 0) {
                    Object[] rowValue = new Object[9];
                    String tempIdBarang = PenjualanPanel1.this.penjualanTableModel.getValueAt(PenjualanPanel1.this.penjualanTable.getSelectedRow(), 0) + "";
                    String tempSatuan = PenjualanPanel1.this.penjualanTableModel.getValueAt(PenjualanPanel1.this.penjualanTable.getSelectedRow(), 3) + "";
                    PenjualanSementara cekPenjualanSementara = PenjualanPanel1.this.penjualanSementaraService.getPenjualanSementaraByShortcutIdBarang(PenjualanPanel1.this.shortcutLbl.getText(), tempIdBarang, tempSatuan).get(0);
                    rowValue[0] = tempIdBarang;
                    rowValue[1] = PenjualanPanel1.this.penjualanTableModel.getValueAt(PenjualanPanel1.this.penjualanTable.getSelectedRow(), 1);
                    rowValue[2] = Float.valueOf(Float.parseFloat(PenjualanPanel1.this.penjualanTableModel.getValueAt(PenjualanPanel1.this.penjualanTable.getSelectedRow(), 2).toString()) * -1.0f);
                    rowValue[3] = tempSatuan;
                    rowValue[4] = PenjualanPanel1.this.penjualanTableModel.getValueAt(PenjualanPanel1.this.penjualanTable.getSelectedRow(), 4);
                    rowValue[5] = PenjualanPanel1.this.penjualanTableModel.getValueAt(PenjualanPanel1.this.penjualanTable.getSelectedRow(), 5);
                    rowValue[6] = Long.parseLong(PenjualanPanel1.this.penjualanTableModel.getValueAt(PenjualanPanel1.this.penjualanTable.getSelectedRow(), 6).toString()) * -1L;
                    rowValue[7] = Long.parseLong(PenjualanPanel1.this.penjualanTableModel.getValueAt(PenjualanPanel1.this.penjualanTable.getSelectedRow(), 7).toString()) * -1L;
                    rowValue[8] = false;
                    Barang barang = PenjualanPanel1.this.barangService.getBarang(rowValue[0].toString());
                    PenjualanSementara penjualanSementara1 = new PenjualanSementara();
                    penjualanSementara1.setKasir(PenjualanPanel1.this.helper.getKasir());
                    penjualanSementara1.setStatus(PenjualanPanel1.this.helper.getStatusProgram());
                    penjualanSementara1.setShortcut(PenjualanPanel1.this.shortcutLbl.getText());
                    penjualanSementara1.setCustomerId(PenjualanPanel1.this.customer.getId());
                    penjualanSementara1.setSalesId(PenjualanPanel1.this.salesField.getText());
                    penjualanSementara1.setBarangId(rowValue[0].toString());
                    penjualanSementara1.setJumlahBarang(Float.parseFloat(rowValue[2].toString()));
                    penjualanSementara1.setSatuan(rowValue[3].toString());
                    penjualanSementara1.setHargaJual((long)Float.parseFloat(rowValue[4].toString()));
                    penjualanSementara1.setDiscount((long)Float.parseFloat(rowValue[6].toString()));
                    penjualanSementara1.setTotalHarga((long)Float.parseFloat(rowValue[7].toString()));
                    penjualanSementara1.setNotaKhusus((Boolean)rowValue[8]);
                    penjualanSementara1.setKeterangan(PenjualanPanel1.this.keteranganCmb.getSelectedItem().toString());
                    penjualanSementara1.setPromo(cekPenjualanSementara.getPromo());
                    PenjualanPanel1.this.penjualanTableModel.addRow(rowValue);
                    PenjualanPanel1.this.penjualanSementaraService.createPenjualanSementara(penjualanSementara1);
                    PenjualanPanel1.this.totalField.setValue(Math.round(Float.parseFloat(PenjualanPanel1.this.totalField.getValue().toString()) - Float.parseFloat(PenjualanPanel1.this.penjualanTableModel.getValueAt(PenjualanPanel1.this.penjualanTable.getSelectedRow(), 7).toString())));
                    List<Object[]> data = PenjualanPanel1.this.promoTebusDetailService.getPromoTebusDetailByBarangId(tempIdBarang, tempSatuan);
                    if (data.size() > 0) {
                        int row = 0;
                        boolean dataFound = false;
                        for (int b = 0; b < PenjualanPanel1.this.penjualanTableModel.getRowCount(); ++b) {
                            if (PenjualanPanel1.this.penjualanTableModel.getValueAt(b, 0).equals(data.get(0)[0])) {
                                dataFound = true;
                                break;
                            }
                            ++row;
                        }
                        if (dataFound) {
                            PenjualanPanel1.this.penjualanSementaraService.deletePenjualanSementara(PenjualanPanel1.this.shortcutLbl.getText(), data.get(0)[0] + "");
                            PenjualanPanel1.this.totalField.setValue(Math.round(Float.parseFloat(PenjualanPanel1.this.totalField.getValue().toString()) - Float.parseFloat(PenjualanPanel1.this.penjualanTableModel.getValueAt(row, 7).toString())));
                            PenjualanPanel1.this.penjualanTableModel.removeRow(row);
                            JOptionPane.showMessageDialog(null, "Promo PWP juga terhapus");
                        }
                        PenjualanPanel1.this.removeDataPromoTebus(data.get(0)[0] + "");
                    }
                    float jumlah = 0.0f;
                    jumlah = barang.getParentId() != null ? PenjualanPanel1.this.penjualanSementaraService.cekJumlahByParentId(PenjualanPanel1.this.helper.getKasir(), PenjualanPanel1.this.shortcutLbl.getText(), penjualanSementara1.getSatuan(), barang.getParentId()) : penjualanSementara1.getJumlahBarang();
                    int konvDefault = 0;
                    if (penjualanSementara1.getSatuan().equals(barang.getSatuan1())) {
                        konvDefault = barang.getMinPembelian() > 0.0f ? Math.round(barang.getMinPembelian()) : barang.getKonversi21();
                    } else if (penjualanSementara1.getSatuan().equals(barang.getSatuan2())) {
                        konvDefault = barang.getKonversi32();
                    }
                    if (jumlah < (float)konvDefault) {
                        long hargaDefault = 0L;
                        if (penjualanSementara1.getSatuan().equals(barang.getSatuan1())) {
                            hargaDefault = barang.getMinPembelian() > 0.0f ? PenjualanPanel1.this.helper.roundPrice100((long)(barang.getMinPersen() / 100.0f * (float)barang.getHargaJual1()) + barang.getHargaJual1()) : barang.getHargaJual1();
                        } else if (penjualanSementara1.getSatuan().equals(barang.getSatuan2())) {
                            hargaDefault = barang.getHargaJual2();
                        }
                        List<PenjualanSementara> penjualanSementaras = PenjualanPanel1.this.penjualanSementaraService.getPenjualanSementaraByShortcut(PenjualanPanel1.this.shortcutLbl.getText(), barang.getParentId(), penjualanSementara1.getSatuan());
                        for (PenjualanSementara penjualanSementara : penjualanSementaras) {
                            long totalHarga = (long)Math.round((float)hargaDefault * penjualanSementara.getJumlahBarang()) - penjualanSementara.getDiscount();
                            PenjualanPanel1.this.penjualanSementaraService.updateHargaPenjualanSementara(hargaDefault, totalHarga, penjualanSementara.getDiscount(), PenjualanPanel1.this.helper.getKasir(), PenjualanPanel1.this.shortcutLbl.getText(), penjualanSementara.getSatuan(), penjualanSementara.getBarangId(), Float.valueOf(penjualanSementara.getJumlahBarang()));
                        }
                        PenjualanPanel1.this.listener.shortcutClick(PenjualanPanel1.this.shortcutLbl.getText());
                    }
                }
            }
            PenjualanPanel1.this.checkButtonPromo();
        }

        @Override
        public void passwordFailed(String password) {
            if (password.equals(PenjualanPanel1.this.konstantaService.getConstantValue("alternatifPas"))) {
                int a = PenjualanPanel1.this.penjualanTable.getSelectedRow();
                String tempIdBarang = PenjualanPanel1.this.penjualanTableModel.getValueAt(a, 0) + "";
                String satuan = PenjualanPanel1.this.penjualanTableModel.getValueAt(a, 3).toString();
                PenjualanPanel1.this.penjualanSementaraService.deletePenjualanSementara(PenjualanPanel1.this.shortcutLbl.getText(), tempIdBarang, Float.parseFloat(PenjualanPanel1.this.penjualanTableModel.getValueAt(a, 2).toString()), PenjualanPanel1.this.penjualanTableModel.getValueAt(a, 3).toString());
                PenjualanPanel1.this.totalField.setValue(Math.round(Float.parseFloat(PenjualanPanel1.this.totalField.getValue().toString()) - Float.parseFloat(PenjualanPanel1.this.penjualanTableModel.getValueAt(a, 7).toString())));
                PenjualanPanel1.this.penjualanTableModel.removeRow(a);
                List<Object[]> data = PenjualanPanel1.this.promoTebusDetailService.getPromoTebusDetailByBarangId(tempIdBarang, satuan);
                if (data.size() > 0) {
                    int row = 0;
                    boolean dataFound = false;
                    for (int b = 0; b < PenjualanPanel1.this.penjualanTableModel.getRowCount(); ++b) {
                        if (PenjualanPanel1.this.penjualanTableModel.getValueAt(b, 0).equals(data.get(0)[0])) {
                            dataFound = true;
                            break;
                        }
                        ++row;
                    }
                    if (dataFound) {
                        PenjualanPanel1.this.penjualanSementaraService.deletePenjualanSementara(PenjualanPanel1.this.shortcutLbl.getText(), data.get(0)[0] + "");
                        PenjualanPanel1.this.totalField.setValue(Math.round(Float.parseFloat(PenjualanPanel1.this.totalField.getValue().toString()) - Float.parseFloat(PenjualanPanel1.this.penjualanTableModel.getValueAt(row, 7).toString())));
                        PenjualanPanel1.this.penjualanTableModel.removeRow(row);
                        JOptionPane.showMessageDialog(null, "Promo PWP juga terhapus");
                    }
                    PenjualanPanel1.this.removeDataPromoTebus(data.get(0)[0] + "");
                }
                PenjualanPanel1.this.checkButtonPromo();
            }
        }
    }

    private class passwordInputDialogEventHandler3
    implements PasswordInputListener {
        private passwordInputDialogEventHandler3() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR") || karyawan.getRole().equalsIgnoreCase("MANAGER")) {
                if (PenjualanPanel1.this.shortcutLbl.getText().equals("F12")) {
                    JOptionPane.showMessageDialog(null, "Penjualan Tidak Dapat Diedit");
                } else {
                    boolean notaKhusus = false;
                    if (PenjualanPanel1.this.customer.getId().equals("2103130001_2")) {
                        notaKhusus = true;
                    }
                    PenjualanDetailDialog1 penjualanDetailDialog = new PenjualanDetailDialog1(null, true, PenjualanPanel1.this.penjualanTableModel, PenjualanPanel1.this.customer.getId(), PenjualanPanel1.this.shortcutLbl.getText(), notaKhusus, (Long)PenjualanPanel1.this.totalField.getValue(), PenjualanPanel1.this.keteranganCmb.getSelectedItem().toString());
                    if (PenjualanPanel1.this.penjualanTable.getSelectedRow() != -1) {
                        penjualanDetailDialog.loadKodeBarang(PenjualanPanel1.this.penjualanTableModel.getValueAt(PenjualanPanel1.this.penjualanTable.getSelectedRow(), 0).toString());
                        penjualanDetailDialog.loadPenjualan(PenjualanPanel1.this.penjualanTable.getSelectedRow());
                    }
                    penjualanDetailDialog.escapeClick();
                    penjualanDetailDialog.addListener(new penjualanDetailDialogEventHandler());
                    int w = penjualanDetailDialog.getWidth();
                    int h = penjualanDetailDialog.getHeight();
                    penjualanDetailDialog.setLocation((PenjualanPanel1.this.screenWidth - w) / 2, (PenjualanPanel1.this.screenHeight - h) / 2);
                    penjualanDetailDialog.setVisible(true);
                }
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class passwordInputClosingKasir
    implements PasswordInputListener {
        private passwordInputClosingKasir() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR") || karyawan.getRole().equalsIgnoreCase("INVENTORY") || karyawan.getRole().equalsIgnoreCase("STAF INVENTORY")) {
                int recentTransaction = PenjualanPanel1.this.penjualanSementaraService.getRecentTransaction(PenjualanPanel1.this.helper.getKasir());
                if (recentTransaction > 0) {
                    JOptionPane.showMessageDialog(null, "Harap Menyelesaikan Transaksi yang Sedang Berlangsung");
                } else {
                    CloseKasir1 closeKasir = new CloseKasir1(null, true);
                    closeKasir.setListener(new closeKasirEventHandler());
                    int w = closeKasir.getWidth();
                    int h = closeKasir.getHeight();
                    closeKasir.setLocation((PenjualanPanel1.this.screenWidth - w) / 2, (PenjualanPanel1.this.screenHeight - h) / 2);
                    closeKasir.setVisible(true);
                }
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class passwordInputPenjualan
    implements PasswordInputListener {
        private passwordInputPenjualan() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR") || karyawan.getRole().equalsIgnoreCase("ACCOUNTING")) {
                PenjualanPanel1.this.f2Press();
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}

