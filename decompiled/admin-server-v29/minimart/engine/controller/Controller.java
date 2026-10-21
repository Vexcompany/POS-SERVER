/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  net.sf.jasperreports.engine.JasperFillManager
 *  net.sf.jasperreports.engine.JasperPrint
 *  net.sf.jasperreports.view.JasperViewer
 */
package minimart.engine.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import minimart.engine.controller.ConnectionManager;
import minimart.engine.controller.Helper;
import minimart.engine.model.Barang;
import minimart.engine.model.Karyawan;
import minimart.engine.model.Konstanta;
import minimart.engine.model.Role;
import minimart.engine.model.ShiftKasir;
import minimart.engine.restengine.CommonEngine;
import minimart.engine.service.BarangService;
import minimart.engine.service.ConstantOnlineService;
import minimart.engine.service.CustomerCommitService;
import minimart.engine.service.CustomerPointCommitService;
import minimart.engine.service.KaryawanService;
import minimart.engine.service.KasirService;
import minimart.engine.service.KonstantaService;
import minimart.engine.service.MigrasiOnlineService;
import minimart.engine.service.POSupplierOnlineService;
import minimart.engine.service.PaketService;
import minimart.engine.service.RoleService;
import minimart.engine.service.SalesService;
import minimart.engine.service.ShiftKasirService;
import minimart.engine.service.SinkronisasiBarangService;
import minimart.engine.service.StokOpnamMandiriOnlineService;
import minimart.engine.service.StokOpnamMandiriService;
import minimart.engine.service.SupplierService;
import minimart.engine.service.SyncPromoService;
import minimart.engine.service.SyncService;
import minimart.ui.listener.ConfirmationDialogListener;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.listener.LoginOnlineListener;
import minimart.ui.listener.MainPanelListener;
import minimart.ui.listener.NewKasirListener;
import minimart.ui.listener.OpenedKasirListener;
import minimart.ui.listener.PasswordInputListener;
import minimart.ui.listener.PembelianPanelListener;
import minimart.ui.listener.PenjualanPanelListener;
import minimart.ui.listener.ReportBarangMinDialogListener;
import minimart.ui.listener.ReportDibawahHargaBeliListener;
import minimart.ui.listener.ReportHistoryHargaDialogListener;
import minimart.ui.listener.ReportHistoryStokDialogListener;
import minimart.ui.listener.ReportHutangDialogListener;
import minimart.ui.listener.ReportJenisRakDialogListener;
import minimart.ui.listener.ReportKeuntunganDialogListener;
import minimart.ui.listener.ReportMasterBarangDialogListener;
import minimart.ui.listener.ReportPelunasanHutangDialogListener;
import minimart.ui.listener.ReportPembelianDetailDialogListener;
import minimart.ui.listener.ReportPembelianDialogListener;
import minimart.ui.listener.ReportPenerimaanBsListener;
import minimart.ui.listener.ReportPenjualanDetailDialogListener;
import minimart.ui.listener.ReportPenjualanDialogListener;
import minimart.ui.listener.ReportPiutangDialogListener;
import minimart.ui.listener.ReportStokBsBarangDialogListener;
import minimart.ui.view.ChangePassDialog;
import minimart.ui.view.ConfirmationDialog;
import minimart.ui.view.CreateCustomerPanel;
import minimart.ui.view.CustomerMaster;
import minimart.ui.view.DaftarCommitDialog;
import minimart.ui.view.DaftarPromoVoucher;
import minimart.ui.view.JenisBarangMaster;
import minimart.ui.view.KaryawanMaster;
import minimart.ui.view.KoreksiStokDialog;
import minimart.ui.view.LoginOnlinePanel;
import minimart.ui.view.LoginPanel;
import minimart.ui.view.MainPanel;
import minimart.ui.view.MutasiBarangPanel;
import minimart.ui.view.NewKasir;
import minimart.ui.view.OpenedKasir;
import minimart.ui.view.ParentMasterUpdate;
import minimart.ui.view.PasswordInputDialog;
import minimart.ui.view.PembelianPanel;
import minimart.ui.view.PembelianPanel2;
import minimart.ui.view.PengaturanStokOpnam;
import minimart.ui.view.PengembalianBarangPanel;
import minimart.ui.view.Penjualan2Panel;
import minimart.ui.view.PenjualanByExcel;
import minimart.ui.view.PenjualanByExcel2;
import minimart.ui.view.PenjualanCabangPanel;
import minimart.ui.view.PenjualanCabangPanelUsingBatchPengiriman;
import minimart.ui.view.PenjualanPanel1;
import minimart.ui.view.PenjualanParsialPanel;
import minimart.ui.view.PenukaranPointDialog;
import minimart.ui.view.PoinBelanjaSetting;
import minimart.ui.view.PromoDiskonByJenisPanel;
import minimart.ui.view.PromoHematPanel;
import minimart.ui.view.PromoKhususPelanggan;
import minimart.ui.view.PromoTebusMurahPanel;
import minimart.ui.view.PromoTebusPanel;
import minimart.ui.view.PromoVoucherBelanja;
import minimart.ui.view.RepackingDialog;
import minimart.ui.view.RolePanel;
import minimart.ui.view.SalesMaster;
import minimart.ui.view.StokOpnamMandiriPanel;
import minimart.ui.view.StokOpnamPanelNew;
import minimart.ui.view.StokOpnamParsial;
import minimart.ui.view.StoreNameDialog;
import minimart.ui.view.SupplierMaster;
import minimart.ui.view.TabelBarangMaster;
import minimart.ui.view.UpdateBarangPanel;
import minimart.ui.view.UpdateCustomerPanel;
import minimart.ui.view.report.CetakHargaPanel;
import minimart.ui.view.report.ReportBarangMinPanel;
import minimart.ui.view.report.ReportDibawahHargaBeliDialog;
import minimart.ui.view.report.ReportDibawahHargaBeliPanel;
import minimart.ui.view.report.ReportHistoryHargaDialog;
import minimart.ui.view.report.ReportHistoryHargaPanel;
import minimart.ui.view.report.ReportHistoryStokPanel;
import minimart.ui.view.report.ReportHutangDialog;
import minimart.ui.view.report.ReportHutangPanel;
import minimart.ui.view.report.ReportJenisRakDialog;
import minimart.ui.view.report.ReportKeuanganGlobal;
import minimart.ui.view.report.ReportKeuanganKasirPanel;
import minimart.ui.view.report.ReportKeuanganShiftPanel;
import minimart.ui.view.report.ReportKeuntunganDialog;
import minimart.ui.view.report.ReportKeuntunganPanel;
import minimart.ui.view.report.ReportMasterBarangDialog;
import minimart.ui.view.report.ReportMasterBarangPanel;
import minimart.ui.view.report.ReportMasterCustomerPanel;
import minimart.ui.view.report.ReportMasterKaryawanPanel;
import minimart.ui.view.report.ReportMasterSupplierPanel;
import minimart.ui.view.report.ReportMutasiPanel;
import minimart.ui.view.report.ReportPelunasanHutangPanel;
import minimart.ui.view.report.ReportPelunasanPiutangPanel;
import minimart.ui.view.report.ReportPelunasantHutangDialog;
import minimart.ui.view.report.ReportPelunasantPiutangDialog;
import minimart.ui.view.report.ReportPembelianDetailDialog;
import minimart.ui.view.report.ReportPembelianDetailPanel;
import minimart.ui.view.report.ReportPembelianDialog;
import minimart.ui.view.report.ReportPembelianPanel;
import minimart.ui.view.report.ReportPenerimaanBsPanel;
import minimart.ui.view.report.ReportPengembalianBarang;
import minimart.ui.view.report.ReportPenjualanBarang2;
import minimart.ui.view.report.ReportPenjualanCustomer;
import minimart.ui.view.report.ReportPenjualanDetailDialog;
import minimart.ui.view.report.ReportPenjualanDetailPanel;
import minimart.ui.view.report.ReportPenjualanDialog;
import minimart.ui.view.report.ReportPenjualanPanel;
import minimart.ui.view.report.ReportPenjualanSales;
import minimart.ui.view.report.ReportPenukaranPoin;
import minimart.ui.view.report.ReportPenukaranVoucherPanel;
import minimart.ui.view.report.ReportPerformaAllPromo;
import minimart.ui.view.report.ReportPerformaOutlet;
import minimart.ui.view.report.ReportPerformaPromo;
import minimart.ui.view.report.ReportPiutangCustomer;
import minimart.ui.view.report.ReportPiutangCustomerExcel;
import minimart.ui.view.report.ReportPlanogram;
import minimart.ui.view.report.ReportPoinPenjualanCustomer;
import minimart.ui.view.report.ReportPromoHemat;
import minimart.ui.view.report.ReportPromoTebus;
import minimart.ui.view.report.ReportPromoTebusMurah;
import minimart.ui.view.report.ReportRekapPenjualan;
import minimart.ui.view.report.ReportRepackPanel;
import minimart.ui.view.report.ReportReprinInvoiceLog;
import minimart.ui.view.report.ReportSelisihInputStok;
import minimart.ui.view.report.ReportStokBsBarangPanel;
import minimart.ui.view.report.ReportStokOpnamDetail;
import minimart.ui.view.report.ReportStokOpnamMandiriSementara;
import minimart.ui.view.report.ReportStokOpnamPanel;
import minimart.ui.view.report.ReportStokTerakhirPanel;
import minimart.ui.view.report.ReportStokTransaction;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class Controller
extends JFrame {
    private boolean canGoThrough;
    private JMenuBar menuBar;
    private JMenu exitMenu;
    private JMenu tabelMenu;
    private JMenu mutasiMenu;
    private JMenu reportMenu;
    private JMenu pengaturanMenu;
    private JMenu cetakMenu;
    private JMenu commitMenu;
    private JMenuItem pengembalianBarangMenuItem;
    private JMenuItem mutasiBarangMenuItem;
    private JMenuItem repackMenuItem;
    private JMenuItem labelHargaMenuItem;
    private JMenuItem[] reportMenuItems;
    private JMenuItem[] updateMenuItems;
    private JMenuItem[] exitMenuItems;
    private JMenuItem[] tabelMenuItems;
    private JMenuItem[] penjualanMenuItems;
    private JMenuItem[] pembelianMenuItems;
    private JMenuItem[] mutasiBarangMenuItems;
    private JMenuItem[] penukaranPointBelanja;
    private JMenuItem[] pengaturanMenuItems;
    private JMenuItem[] commitMenuItems;
    private JMenu[] mutasiMenuItems;
    private JMenu stokOpnamMenu;
    private JMenu reportBarangMenu;
    private JMenu reportPenjualanMenu;
    private JMenu reportPromoMenu;
    private JMenu reportBSMenu;
    private JMenu reportPembelianMenu;
    private JMenu reportHutangMenu;
    private JMenu promoMenu;
    private JMenu promoVoucherMenu;
    private JMenuItem[] promoMenuItems;
    private JMenuItem[] promoVoucherMenuItem;
    private JMenuItem[] reportBarangMenuItem;
    private JMenuItem[] reportPenjualanMenuItem;
    private JMenuItem[] reportPromoMenuItem;
    private JMenuItem[] reportPembelianMenuItem;
    private JMenuItem[] reportHutangMenuItem;
    private JMenu reportKeuanganKasirMenu;
    private JMenuItem[] reportKeuanganKasirMenuItem;
    private JMenu reportMasterMenu;
    private JMenuItem[] reportMasterMenuItem;
    private JMenu reportStokMenu;
    private JMenuItem[] reportStokMenuItem;
    private JMenu reportPiutangMenu;
    private JMenuItem[] reportPiutangMenuItem;
    private JMenuItem[] stokOpnameMenuItems;
    private JMenuItem roleMenuItem;
    private JMenuItem pengaturanSoMenuItem;
    private LoginPanel loginPanel;
    private LoginOnlinePanel loginOnlinePanel;
    private boolean sinkron = false;
    private int screenWidth = 0;
    private int screenHeight = 0;
    private String tipeProgram = "PUSAT";
    private Helper helper = Helper.getInstance();
    private KasirService kasirService;
    private KaryawanService karyawanService;
    private KonstantaService konstantaService;
    private POSupplierOnlineService poService;
    private ShiftKasirService shiftKasirService;
    private BarangService barangService;
    private SyncService syncService;
    private SyncPromoService syncPromoService;
    private PaketService paketService;
    private StokOpnamMandiriOnlineService somoService;
    private MigrasiOnlineService moService;
    private CommonEngine comm;
    private ObjectMapper mapper;
    private ConstantOnlineService cos;
    private SupplierService supplierService;
    SinkronisasiBarangService sinkronisasiBarangService;
    private CustomerCommitService customerCommitService;
    private CustomerPointCommitService customerPointCommitService;
    private StokOpnamMandiriService stokOpnamMandiriService;
    private SalesService salesService;
    private int kasirId;
    private int version;
    private boolean kasirParsial = false;
    private String keteranganSoftware;
    private JMenu sinkronisasiMenu;
    private JMenuItem[] sinkronisasiMenuItems;
    private RoleService roleService;
    private String tipeOutlet;

    public void initAllService() {
        this.kasirService = new KasirService();
        this.konstantaService = new KonstantaService();
        this.poService = new POSupplierOnlineService();
        this.syncService = new SyncService();
        this.shiftKasirService = new ShiftKasirService();
        this.barangService = new BarangService();
        this.syncPromoService = new SyncPromoService();
        this.paketService = new PaketService();
        this.somoService = new StokOpnamMandiriOnlineService();
        this.moService = new MigrasiOnlineService();
        this.comm = new CommonEngine("https://admin.sinarmart.id");
        this.mapper = new ObjectMapper();
        this.cos = new ConstantOnlineService();
        this.supplierService = new SupplierService();
        this.sinkronisasiBarangService = new SinkronisasiBarangService();
        this.customerCommitService = new CustomerCommitService();
        this.customerPointCommitService = new CustomerPointCommitService();
        this.stokOpnamMandiriService = new StokOpnamMandiriService();
        this.salesService = new SalesService();
        this.roleService = new RoleService();
        this.karyawanService = new KaryawanService();
    }

    public Controller() {
        this.helper.setIsServer(true);
        this.setResizable(false);
        this.initComponent();
        this.setAction();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.loginOnlinePanel.addLoginListener(new loginOnlineEventHandler());
        this.setContentPane(this.loginOnlinePanel);
        this.setFrame();
    }

    private void applyRoleVisibility(Role role, String menu, Component component) {
        if (menu.equals(role.getMenu())) {
            component.setVisible(role.getAccess() == 1);
        }
    }

    private void cekRole() {
        List<Role> roles = this.roleService.getRoles("");
        String[] dataRole = new String[]{"DIRTABELBARANG,DIREKTUR,tabel_barang,1", "MANTABELBARANG,MANAGER,tabel_barang,1", "ACCTABELBARANG,ACCOUNTING,tabel_barang,0", "INVTABELBARANG,INVENTORY,tabel_barang,0", "INVTABELBARANG,STAF INVENTORY,tabel_barang,0", "ADMTABELBARANG,ADMIN,tabel_barang,1", "KASTABELBARANG,KASIR,tabel_barang,1", "DIRTABELJENIS,DIREKTUR,tabel_jenis,1", "MANTABELJENIS,MANAGER,tabel_jenis,1", "ACCTABELJENIS,ACCOUNTING,tabel_jenis,0", "INVTABELJENIS,INVENTORY,tabel_jenis,0", "INVTABELJENIS,STAF INVENTORY,tabel_jenis,0", "ADMTABELJENIS,ADMIN,tabel_jenis,0", "KASTABELJENIS,KASIR,tabel_jenis,0", "DIRTABELCUSTOMER,DIREKTUR,tabel_customer,1", "MANTABELCUSTOMER,MANAGER,tabel_customer,1", "ACCTABELCUSTOMER,ACCOUNTING,tabel_customer,0", "INVTABELCUSTOMER,INVENTORY,tabel_customer,0", "INVTABELCUSTOMER,STAF INVENTORY,tabel_customer,0", "ADMTABELCUSTOMER,ADMIN,tabel_customer,1", "KASTABELCUSTOMER,KASIR,tabel_customer,1", "DIRTABELSUPPLIER,DIREKTUR,tabel_supplier,1", "MANTABELSUPPLIER,MANAGER,tabel_supplier,1", "ACCTABELSUPPLIER,ACCOUNTING,tabel_supplier,0", "INVTABELSUPPLIER,INVENTORY,tabel_supplier,0", "INVTABELSUPPLIER,STAF INVENTORY,tabel_supplier,0", "ADMTABELSUPPLIER,ADMIN,tabel_supplier,0", "KASTABELSUPPLIER,KASIR,tabel_supplier,0", "DIRTABELKARYAWAN,DIREKTUR,tabel_karyawan,1", "MANTABELKARYAWAN,MANAGER,tabel_karyawan,0", "ACCTABELKARYAWAN,ACCOUNTING,tabel_karyawan,0", "INVTABELKARYAWAN,INVENTORY,tabel_karyawan,0", "INVTABELKARYAWAN,STAF INVENTORY,tabel_karyawan,0", "ADMTABELKARYAWAN,ADMIN,tabel_karyawan,0", "KASTABELKARYAWAN,KASIR,tabel_karyawan,0", "DIRTABELSALES,DIREKTUR,tabel_sales,1", "MANTABELSALES,MANAGER,tabel_sales,0", "ACCTABELSALES,ACCOUNTING,tabel_sales,0", "INVTABELSALES,INVENTORY,tabel_sales,0", "INVTABELSALES,STAF INVENTORY,tabel_sales,0", "ADMTABELSALES,ADMIN,tabel_sales,0", "KASTABELSALES,KASIR,tabel_sales,0", "DIRTABELPARENT,DIREKTUR,tabel_parent,1", "MANTABELPARENT,MANAGER,tabel_parent,1", "ACCTABELPARENT,ACCOUNTING,tabel_parent,0", "INVTABELPARENT,INVENTORY,tabel_parent,0", "INVTABELPARENT,STAF INVENTORY,tabel_parent,0", "ADMTABELPARENT,ADMIN,tabel_parent,0", "KASTABELPARENT,KASIR,tabel_parent,0", "DIRTABELPAKET,DIREKTUR,tabel_paket,1", "MANTABELPAKET,MANAGER,tabel_paket,1", "ACCTABELPAKET,ACCOUNTING,tabel_paket,0", "INVTABELPAKET,INVENTORY,tabel_paket,0", "INVTABELPAKET,STAF INVENTORY,tabel_paket,0", "ADMTABELPAKET,ADMIN,tabel_paket,0", "KASTABELPAKET,KASIR,tabel_paket,0", "DIRMENUENTRYPEMBELIAN,DIREKTUR,menu_entry_pembelian,1", "MANMENUENTRYPEMBELIAN,MANAGER,menu_entry_pembelian,0", "ACCMENUENTRYPEMBELIAN,ACCOUNTING,menu_entry_pembelian,0", "INVMENUENTRYPEMBELIAN,INVENTORY,menu_entry_pembelian,0", "INVMENUENTRYPEMBELIAN,STAF INVENTORY,menu_entry_pembelian,0", "ADMMENUENTRYPEMBELIAN,ADMIN,menu_entry_pembelian,0", "KASMENUENTRYPEMBELIAN,KASIR,menu_entry_pembelian,0", "DIRMENUENTRYPENJUALAN,DIREKTUR,menu_entry_penjualan,1", "MANMENUENTRYPENJUALAN,MANAGER,menu_entry_penjualan,1", "ACCMENUENTRYPENJUALAN,ACCOUNTING,menu_entry_penjualan,0", "INVMENUENTRYPENJUALAN,INVENTORY,menu_entry_penjualan,0", "INVMENUENTRYPENJUALAN,STAF INVENTORY,menu_entry_penjualan,0", "ADMMENUENTRYPENJUALAN,ADMIN,menu_entry_penjualan,1", "KASMENUENTRYPENJUALAN,KASIR,menu_entry_penjualan,1", "DIRMENUSTOKOPNAM,DIREKTUR,menu_stok_opnam,1", "MANMENUSTOKOPNAM,MANAGER,menu_stok_opnam,0", "ACCMENUSTOKOPNAM,ACCOUNTING,menu_stok_opnam,0", "INVMENUSTOKOPNAM,INVENTORY,menu_stok_opnam,1", "INVMENUSTOKOPNAM,STAF INVENTORY,menu_stok_opnam,1", "ADMMENUSTOKOPNAM,ADMIN,menu_stok_opnam,1", "KASMENUSTOKOPNAM,KASIR,menu_stok_opnam,1", "DIRMENUREPORTPROMO,DIREKTUR,menu_report_promo,1", "MANMENUREPORTPROMO,MANAGER,menu_report_promo,0", "ACCMENUREPORTPROMO,ACCOUNTING,menu_report_promo,0", "INVMENUREPORTPROMO,INVENTORY,menu_report_promo,0", "INVMENUREPORTPROMO,STAF INVENTORY,menu_report_promo,0", "ADMMENUREPORTPROMO,ADMIN,menu_report_promo,0", "KASMENUREPORTPROMO,KASIR,menu_report_promo,0", "DIRMENUREPORTPEMBELIAN,DIREKTUR,menu_report_pembelian,1", "MANMENUREPORTPEMBELIAN,MANAGER,menu_report_pembelian,0", "ACCMENUREPORTPEMBELIAN,ACCOUNTING,menu_report_pembelian,1", "INVMENUREPORTPEMBELIAN,INVENTORY,menu_report_pembelian,0", "INVMENUREPORTPEMBELIAN,STAF INVENTORY,menu_report_pembelian,0", "ADMMENUREPORTPEMBELIAN,ADMIN,menu_report_pembelian,0", "KASMENUREPORTPEMBELIAN,KASIR,menu_report_pembelian,0", "DIRMENUREPORTHUTANG,DIREKTUR,menu_report_hutang,1", "MANMENUREPORTHUTANG,MANAGER,menu_report_hutang,0", "ACCMENUREPORTHUTANG,ACCOUNTING,menu_report_hutang,1", "INVMENUREPORTHUTANG,INVENTORY,menu_report_hutang,0", "INVMENUREPORTHUTANG,STAF INVENTORY,menu_report_hutang,0", "ADMMENUREPORTHUTANG,ADMIN,menu_report_hutang,0", "KASMENUREPORTHUTANG,KASIR,menu_report_hutang,0", "DIRMENUREPORTBARANG,DIREKTUR,menu_report_barang,1", "MANMENUREPORTBARANG,MANAGER,menu_report_barang,1", "ACCMENUREPORTBARANG,ACCOUNTING,menu_report_barang,1", "INVMENUREPORTBARANG,INVENTORY,menu_report_barang,1", "INVMENUREPORTBARANG,STAF INVENTORY,menu_report_barang,1", "ADMMENUREPORTBARANG,ADMIN,menu_report_barang,1", "KASMENUREPORTBARANG,KASIR,menu_report_barang,1", "DIRMENUREPORTKEUANGANKASIR,DIREKTUR,menu_report_keuangan_kasir,1", "MANMENUREPORTKEUANGANKASIR,MANAGER,menu_report_keuangan_kasir,0", "ACCMENUREPORTKEUANGANKASIR,ACCOUNTING,menu_report_keuangan_kasir,1", "INVMENUREPORTKEUANGANKASIR,INVENTORY,menu_report_keuangan_kasir,0", "INVMENUREPORTKEUANGANKASIR,STAF INVENTORY,menu_report_keuangan_kasir,0", "ADMMENUREPORTKEUANGANKASIR,ADMIN,menu_report_keuangan_kasir,1", "KASMENUREPORTKEUANGANKASIR,KASIR,menu_report_keuangan_kasir,0", "DIRMENUREPORTSTOK,DIREKTUR,menu_report_stok,1", "MANMENUREPORTSTOK,MANAGER,menu_report_stok,0", "ACCMENUREPORTSTOK,ACCOUNTING,menu_report_stok,1", "INVMENUREPORTSTOK,INVENTORY,menu_report_stok,1", "INVMENUREPORTSTOK,STAF INVENTORY,menu_report_stok,1", "ADNMENUREPORTSTOK,ADMIN,menu_report_stok,1", "KASMENUREPORTSTOK,KASIR,menu_report_stok,0", "DIRMENUREPORTPIUTANG,DIREKTUR,menu_report_piutang,1", "MANMENUREPORTPIUTANG,MANAGER,menu_report_piutang,1", "ACCMENUREPORTPIUTANG,ACCOUNTING,menu_report_piutang,1", "INVMENUREPORTPIUTANG,INVENTORY,menu_report_piutang,0", "INVMENUREPORTPIUTANG,STAF INVENTORY,menu_report_piutang,0", "ADMMENUREPORTPIUTANG,ADMIN,menu_report_piutang,1", "KASMENUREPORTPIUTANG,KASIR,menu_report_piutang,0", "DIRMENUREPORTMASTER,DIREKTUR,menu_report_master,1", "MANMENUREPORTMASTER,MANAGER,menu_report_master,0", "ACCMENUREPORTMASTER,ACCOUNTING,menu_report_master,0", "INVMENUREPORTMASTER,INVENTORY,menu_report_master,0", "INVMENUREPORTMASTER,STAF INVENTORY,menu_report_master,0", "ADMMENUREPORTMASTER,ADMIN,menu_report_master,0", "KASMENUREPORTMASTER,KASIR,menu_report_master,0", "DIRMENUPROMO,DIREKTUR,menu_promo,1", "MANMENUPROMO,MANAGER,menu_promo,0", "ACCMENUPROMO,ACCOUNTING,menu_promo,0", "INVMENUPROMO,INVENTORY,menu_promo,0", "INVMENUPROMO,STAF INVENTORY,menu_promo,0", "ADMMENUPROMO,ADMIN,menu_promo,0", "KASMENUPROMO,KASIR,menu_promo,0", "DIRREPORTPENJUALAN,DIREKTUR,report_penjualan,1", "MANREPORTPENJUALAN,MANAGER,report_penjualan,0", "ACCREPORTPENJUALAN,ACCOUNTING,report_penjualan,1", "INVREPORTPENJUALAN,INVENTORY,report_penjualan,0", "INVREPORTPENJUALAN,STAF INVENTORY,report_penjualan,0", "ADMREPORTPENJUALAN,ADMIN,report_penjualan,1", "KASREPORTPENJUALAN,KASIR,report_penjualan,0", "DIRREPORTPEMBELIAN,DIREKTUR,mutasi_pembelian,1", "MANREPORTPEMBELIAN,MANAGER,mutasi_pembelian,0", "ACCREPORTPEMBELIAN,ACCOUNTING,mutasi_pembelian,0", "INVREPORTPEMBELIAN,INVENTORY,mutasi_pembelian,0", "INVREPORTPEMBELIAN,STAF INVENTORY,mutasi_pembelian,0", "ADMREPORTPEMBELIAN,ADMIN,mutasi_pembelian,0", "KASREPORTPEMBELIAN,KASIR,mutasi_pembelian,0", "DIRMUTASIPEMBELIAN2,DIREKTUR,mutasi_pembelian2,1", "MANMUTASIPEMBELIAN2,MANAGER,mutasi_pembelian2,0", "ACCMUTASIPEMBELIAN2,ACCOUNTING,mutasi_pembelian2,0", "INVMUTASIPEMBELIAN2,INVENTORY,mutasi_pembelian2,0", "INVMUTASIPEMBELIAN2,STAF INVENTORY,mutasi_pembelian2,0", "ADMMUTASIPEMBELIAN2,ADMIN,mutasi_pembelian2,0", "KASMUTASIPEMBELIAN2,KASIR,mutasi_pembelian2,0", "DIRMUTASIPENJUALAN,DIREKTUR,mutasi_penjualan,1", "MANMUTASIPENJUALAN,MANAGER,mutasi_penjualan,1", "ACCMUTASIPENJUALAN,ACCOUNTING,mutasi_penjualan,0", "INVMUTASIPENJUALAN,INVENTORY,mutasi_penjualan,0", "INVMUTASIPENJUALAN,STAF INVENTORY,mutasi_penjualan,0", "ADMMUTASIPENJUALAN,ADMIN,mutasi_penjualan,1", "KASMUTASIPENJUALAN,KASIR,mutasi_penjualan,1", "DIRMUTASIPENJUALAN2,DIREKTUR,mutasi_penjualan2,1", "MANMUTASIPENJUALAN2,MANAGER,mutasi_penjualan2,1", "ACCMUTASIPENJUALAN2,ACCOUNTING,mutasi_penjualan2,0", "INVMUTASIPENJUALAN2,INVENTORY,mutasi_penjualan2,0", "INVMUTASIPENJUALAN2,STAF INVENTORY,mutasi_penjualan2,0", "ADMMUTASIPENJUALAN2,ADMIN,mutasi_penjualan2,0", "KASMUTASIPENJUALAN2,KASIR,mutasi_penjualan2,0", "DIRPENJUALANCABANG,DIREKTUR,mutasi_penjualan_cabang,1", "MANPENJUALANCABANG,MANAGER,mutasi_penjualan_cabang,1", "ACCPENJUALANCABANG,ACCOUNTING,mutasi_penjualan_cabang,0", "INVPENJUALANCABANG,INVENTORY,mutasi_penjualan_cabang,0", "INVPENJUALANCABANG,STAF INVENTORY,mutasi_penjualan_cabang,0", "ADMPENJUALANCABANG,ADMIN,mutasi_penjualan_cabang,1", "KASPENJUALANCABANG,KASIR,mutasi_penjualan_cabang,0", "DIRPENJUALANPARSIAL,DIREKTUR,mutasi_penjualan_parsial,1", "MANPENJUALANPARSIAL,MANAGER,mutasi_penjualan_parsial,1", "ACCPENJUALANPARSIAL,ACCOUNTING,mutasi_penjualan_parsial,0", "INVPENJUALANPARSIAL,INVENTORY,mutasi_penjualan_parsial,0", "INVPENJUALANPARSIAL,STAF INVENTORY,mutasi_penjualan_parsial,0", "ADMPENJUALANPARSIAL,ADMIN,mutasi_penjualan_parsial,1", "KASPENJUALANPARSIAL,KASIR,mutasi_penjualan_parsial,1", "DIRPENJUALANEXCEL2,DIREKTUR,mutasi_penjualan_excel2,1", "MANPENJUALANEXCEL2,MANAGER,mutasi_penjualan_excel2,1", "ACCPENJUALANEXCEL2,ACCOUNTING,mutasi_penjualan_excel2,0", "INVPENJUALANEXCEL2,INVENTORY,mutasi_penjualan_excel2,0", "INVPENJUALANEXCEL2,STAF INVENTORY,mutasi_penjualan_excel2,0", "ADMPENJUALANEXCEL2,ADMIN,mutasi_penjualan_excel2,1", "KASPENJUALANEXCEL2,KASIR,mutasi_penjualan_excel2,0", "DIRMUTASIPENJUALANCABANGNEW,DIREKTUR,mutasi_penjualan_cabang_new,1", "MANMUTASIPENJUALANCABANGNEW,MANAGER,mutasi_penjualan_cabang_new,1", "ACCMUTASIPENJUALANCABANGNEW,ACCOUNTING,mutasi_penjualan_cabang_new,1", "INVMUTASIPENJUALANCABANGNEW,INVENTORY,mutasi_penjualan_cabang_new,0", "INVMUTASIPENJUALANCABANGNEW,STAF INVENTORY,mutasi_penjualan_cabang_new,0", "ADMMUTASIPENJUALANCABANGNEW,ADMIN,mutasi_penjualan_cabang_new,1", "KASMUTASIPENJUALANCABANGNEW,KASIR,mutasi_penjualan_cabang_new,0", "DIRPENUKARANPOINBELANJA,DIREKTUR,penukaran_poin_belanja,1", "MANPENUKARANPOINBELANJA,MANAGER,penukaran_poin_belanja,0", "ACCPENUKARANPOINBELANJA,ACCOUNTING,penukaran_poin_belanja,1", "INVPENUKARANPOINBELANJA,INVENTORY,penukaran_poin_belanja,0", "INVPENUKARANPOINBELANJA,STAF INVENTORY,penukaran_poin_belanja,0", "ADMPENUKARANPOINBELANJA,ADMIN,penukaran_poin_belanja,1", "KASPENUKARANPOINBELANJA,KASIR,penukaran_poin_belanja,0", "DIRPENGEMBALIANBARANG,DIREKTUR,entry_pengembalian_barang,1", "MANPENGEMBALIANBARANG,MANAGER,entry_pengembalian_barang,0", "ACCPENGEMBALIANBARANG,ACCOUNTING,entry_pengembalian_barang,1", "INVPENGEMBALIANBARANG,INVENTORY,entry_pengembalian_barang,0", "INVPENGEMBALIANBARANG,STAF INVENTORY,entry_pengembalian_barang,0", "ADMPENGEMBALIANBARANG,ADMIN,entry_pengembalian_barang,1", "KASPENGEMBALIANBARANG,KASIR,entry_pengembalian_barang,0", "DIRSTOKOPNAMMANUAL,DIREKTUR,stok_opnam_manual,1", "MANSTOKOPNAMMANUAL,MANAGER,stok_opnam_manual,0", "ACCSTOKOPNAMMANUAL,ACCOUNTING,stok_opnam_manual,0", "INVSTOKOPNAMMANUAL,INVENTORY,stok_opnam_manual,1", "INVSTOKOPNAMMANUAL,STAF INVENTORY,stok_opnam_manual,1", "ADMSTOKOPNAMMANUAL,ADMIN,stok_opnam_manual,0", "KASSTOKOPNAMMANUAL,KASIR,stok_opnam_manual,0", "DIRSTOKOPNAMPARSIAL,DIREKTUR,stok_opnam_parsial,1", "MANSTOKOPNAMPARSIAL,MANAGER,stok_opnam_parsial,0", "ACCSTOKOPNAMPARSIAL,ACCOUNTING,stok_opnam_parsial,0", "INVSTOKOPNAMPARSIAL,INVENTORY,stok_opnam_parsial,1", "INVSTOKOPNAMPARSIAL,STAF INVENTORY,stok_opnam_parsial,1", "ADMSTOKOPNAMPARSIAL,ADMIN,stok_opnam_parsial,0", "KASSTOKOPNAMPARSIAL,KASIR,stok_opnam_parsial,0", "DIRSTOKOPNAMMANDIRI,DIREKTUR,stok_opnam_mandiri,1", "MANSTOKOPNAMMANDIRI,MANAGER,stok_opnam_mandiri,0", "ACCSTOKOPNAMMANDIRI,ACCOUNTING,stok_opnam_mandiri,0", "INVSTOKOPNAMMANDIRI,INVENTORY,stok_opnam_mandiri,1", "INVSTOKOPNAMMANDIRI,STAF INVENTORY,stok_opnam_mandiri,1", "ADMSTOKOPNAMMANDIRI,ADMIN,stok_opnam_mandiri,1", "KASSTOKOPNAMMANDIRI,KASIR,stok_opnam_mandiri,1", "DIRMUTASIBARANG,DIREKTUR,mutasi_barang,1", "MANMUTASIBARANG,MANAGER,mutasi_barang,0", "ACCMUTASIBARANG,ACCOUNTING,mutasi_barang,0", "INVMUTASIBARANG,INVENTORY,mutasi_barang,1", "INVMUTASIBARANG,STAF INVENTORY,mutasi_barang,1", "ADMMUTASIBARANG,ADMIN,mutasi_barang,0", "KASMUTASIBARANG,KASIR,mutasi_barang,0", "DIRREPACKBARANG,DIREKTUR,repack_barang,1", "MANREPACKBARANG,MANAGER,repack_barang,0", "ACCREPACKBARANG,ACCOUNTING,repack_barang,0", "INVREPACKBARANG,INVENTORY,repack_barang,0", "INVREPACKBARANG,STAF INVENTORY,repack_barang,0", "ADMREPACKBARANG,ADMIN,repack_barang,1", "KASREPACKBARANG,KASIR,repack_barang,0", "DIRREPRINTINVOICE,DIREKTUR,reprint_invoice,1", "MANREPRINTINVOICE,MANAGER,reprint_invoice,0", "ACCREPRINTINVOICE,ACCOUNTING,reprint_invoice,1", "INVREPRINTINVOICE,INVENTORY,reprint_invoice,0", "INVREPRINTINVOICE,STAF INVENTORY,reprint_invoice,0", "ADMREPRINTINVOICE,ADMIN,reprint_invoice,1", "KASREPRINTINVOICE,KASIR,reprint_invoice,0", "DIRREPORTKEUNTUNGAN,DIREKTUR,report_keuntungan,1", "MANREPORTKEUNTUNGAN,MANAGER,report_keuntungan,0", "ACCREPORTKEUNTUNGAN,ACCOUNTING,report_keuntungan,1", "INVREPORTKEUNTUNGAN,INVENTORY,report_keuntungan,0", "INVREPORTKEUNTUNGAN,STAF INVENTORY,report_keuntungan,0", "ADMREPORTKEUNTUNGAN,ADMIN,report_keuntungan,0", "KASREPORTKEUNTUNGAN,KASIR,report_keuntungan,0", "DIRREPORTMUTASI,DIREKTUR,report_mutasi,1", "MANREPORTMUTASI,MANAGER,report_mutasi,0", "ACCREPORTMUTASI,ACCOUNTING,report_mutasi,0", "INVREPORTMUTASI,INVENTORY,report_mutasi,1", "INVREPORTMUTASI,STAF INVENTORY,report_mutasi,1", "ADMREPORTMUTASI,ADMIN,report_mutasi,1", "KASREPORTMUTASI,KASIR,report_mutasi,0", "DIRREPORTPENUKARANPOINT,DIREKTUR,report_penukaran_point,1", "MANREPORTPENUKARANPOINT,MANAGER,report_penukaran_point,0", "ACCREPORTPENUKARANPOINT,ACCOUNTING,report_penukaran_point,1", "INVREPORTPENUKARANPOINT,INVENTORY,report_penukaran_point,0", "INVREPORTPENUKARANPOINT,STAF INVENTORY,report_penukaran_point,0", "ADMREPORTPENUKARANPOINT,ADMIN,report_penukaran_point,1", "KASREPORTPENUKARANPOINT,KASIR,report_penukaran_point,0", "DIRREPORTPENGEMBALIANBARANG,DIREKTUR,report_pengembalian_barang,1", "MANREPORTPENGEMBALIANBARANG,MANAGER,report_pengembalian_barang,0", "ACCREPORTPENGEMBALIANBARANG,ACCOUNTING,report_pengembalian_barang,1", "INVREPORTPENGEMBALIANBARANG,INVENTORY,report_pengembalian_barang,1", "INVREPORTPENGEMBALIANBARANG,STAF INVENTORY,report_pengembalian_barang,1", "ADMREPORTPENGEMBALIANBARANG,ADMIN,report_pengembalian_barang,1", "KASREPORTPENGEMBALIANBARANG,KASIR,report_pengembalian_barang,0", "DIRREPORTPENUKARANVOUCHER,DIREKTUR,report_penukaran_voucher,1", "MANREPORTPENUKARANVOUCHER,MANAGER,report_penukaran_voucher,0", "ACCREPORTPENUKARANVOUCHER,ACCOUNTING,report_penukaran_voucher,1", "INVREPORTPENUKARANVOUCHER,INVENTORY,report_penukaran_voucher,0", "INVREPORTPENUKARANVOUCHER,STAF INVENTORY,report_penukaran_voucher,0", "ADMREPORTPENUKARANVOUCHER,ADMIN,report_penukaran_voucher,1", "KASREPORTPENUKARANVOUCHER,KASIR,report_penukaran_voucher,0", "DIRREPORTPERFORMA,DIREKTUR,report_performa,1", "MANREPORTPERFORMA,MANAGER,report_performa,0", "ACCREPORTPERFORMA,ACCOUNTING,report_performa,0", "INVREPORTPERFORMA,INVENTORY,report_performa,0", "INVREPORTPERFORMA,STAF INVENTORY,report_performa,0", "ADMREPORTPERFORMA,ADMIN,report_performa,1", "KASREPORTPERFORMA,KASIR,report_performa,0", "DIRREPORTREPACK,DIREKTUR,report_repack,1", "MANREPORTREPACK,MANAGER,report_repack,0", "ACCREPORTREPACK,ACCOUNTING,report_repack,0", "INVREPORTREPACK,INVENTORY,report_repack,1", "INVREPORTREPACK,STAF INVENTORY,report_repack,1", "ADMREPORTREPACK,ADMIN,report_repack,1", "KASREPORTREPACK,KASIR,report_repack,0", "DIRREPORTSUMMARYPENJUALAN,DIREKTUR,report_summary_penjualan,1", "MANREPORTSUMMARYPENJUALAN,MANAGER,report_summary_penjualan,0", "ACCREPORTSUMMARYPENJUALAN,ACCOUNTING,report_summary_penjualan,1", "INVREPORTSUMMARYPENJUALAN,INVENTORY,report_summary_penjualan,0", "INVREPORTSUMMARYPENJUALAN,STAF INVENTORY,report_summary_penjualan,0", "ADMREPORTSUMMARYPENJUALAN,ADMIN,report_summary_penjualan,1", "KASREPORTSUMMARYPENJUALAN,KASIR,report_summary_penjualan,0", "DIRREPORTDETAILPENJUALAN,DIREKTUR,report_detail_penjualan,1", "MANREPORTDETAILPENJUALAN,MANAGER,report_detail_penjualan,0", "ACCREPORTDETAILPENJUALAN,ACCOUNTING,report_detail_penjualan,1", "INVREPORTDETAILPENJUALAN,INVENTORY,report_detail_penjualan,0", "INVREPORTDETAILPENJUALAN,STAF INVENTORY,report_detail_penjualan,0", "ADMREPORTDETAILPENJUALAN,ADMIN,report_detail_penjualan,1", "KASREPORTDETAILPENJUALAN,KASIR,report_detail_penjualan,0", "DIRREPORTREKAPPENJUALAN,DIREKTUR,report_rekap_penjualan,1", "MANREPORTREKAPPENJUALAN,MANAGER,report_rekap_penjualan,0", "ACCREPORTREKAPPENJUALAN,ACCOUNTING,report_rekap_penjualan,1", "INVREPORTREKAPPENJUALAN,INVENTORY,report_rekap_penjualan,0", "INVREPORTREKAPPENJUALAN,STAF INVENTORY,report_rekap_penjualan,0", "ADMREPORTREKAPPENJUALAN,ADMIN,report_rekap_penjualan,1", "KASREPORTREKAPPENJUALAN,KASIR,report_rekap_penjualan,0", "DIRREPORTPOINPENJUALAN,DIREKTUR,report_poin_penjualan,1", "MANREPORTPOINPENJUALAN,MANAGER,report_poin_penjualan,0", "ACCREPORTPOINPENJUALAN,ACCOUNTING,report_poin_penjualan,1", "INVREPORTPOINPENJUALAN,INVENTORY,report_poin_penjualan,0", "INVREPORTPOINPENJUALAN,STAF INVENTORY,report_poin_penjualan,0", "ADMREPORTPOINPENJUALAN,ADMIN,report_poin_penjualan,1", "KASREPORTPOINPENJUALAN,KASIR,report_poin_penjualan,0", "DIRREPORTBESTSELLER,DIREKTUR,report_best_seller,1", "MANREPORTBESTSELLER,MANAGER,report_best_seller,0", "ACCREPORTBESTSELLER,ACCOUNTING,report_best_seller,0", "INVREPORTBESTSELLER,INVENTORY,report_best_seller,0", "INVREPORTBESTSELLER,STAF INVENTORY,report_best_seller,0", "ADMREPORTBESTSELLER,ADMIN,report_best_seller,1", "KASREPORTBESTSELLER,KASIR,report_best_seller,0", "DIRREKAPPENJUALANSALES,DIREKTUR,rekap_penjualan_sales,1", "MANREKAPPENJUALANSALES,MANAGER,rekap_penjualan_sales,0", "ACCREKAPPENJUALANSALES,ACCOUNTING,rekap_penjualan_sales,1", "INVREKAPPENJUALANSALES,INVENTORY,rekap_penjualan_sales,0", "INVREKAPPENJUALANSALES,STAF INVENTORY,rekap_penjualan_sales,0", "ADMREKAPPENJUALANSALES,ADMIN,rekap_penjualan_sales,1", "KASREKAPPENJUALANSALES,KASIR,rekap_penjualan_sales,0", "DIRREKAPPENJUALANCUSTOMER,DIREKTUR,rekap_penjualan_customer,1", "MANREKAPPENJUALANCUSTOMER,MANAGER,rekap_penjualan_customer,0", "ACCREKAPPENJUALANCUSTOMER,ACCOUNTING,rekap_penjualan_customer,0", "INVREKAPPENJUALANCUSTOMER,INVENTORY,rekap_penjualan_customer,0", "INVREKAPPENJUALANCUSTOMER,STAF INVENTORY,rekap_penjualan_customer,0", "ADMREKAPPENJUALANCUSTOMER,ADMIN,rekap_penjualan_customer,0", "KASREKAPPENJUALANCUSTOMER,KASIR,rekap_penjualan_customer,0", "DIRPROMOHEMAT,DIREKTUR,promo_hemat,1", "MANPROMOHEMAT,MANAGER,promo_hemat,0", "ACCPROMOHEMAT,ACCOUNTING,promo_hemat,0", "INVPROMOHEMAT,INVENTORY,promo_hemat,0", "INVPROMOHEMAT,STAF INVENTORY,promo_hemat,0", "ADMPROMOHEMAT,ADMIN,promo_hemat,0", "KASPROMOHEMAT,KASIR,promo_hemat,0", "DIRPROMOTEBUSMURAHBARANG,DIREKTUR,promo_tebus_murah_barang,1", "MANPROMOTEBUSMURAHBARANG,MANAGER,promo_tebus_murah_barang,0", "ACCPROMOTEBUSMURAHBARANG,ACCOUNTING,promo_tebus_murah_barang,0", "INVPROMOTEBUSMURAHBARANG,INVENTORY,promo_tebus_murah_barang,0", "INVPROMOTEBUSMURAHBARANG,STAF INVENTORY,promo_tebus_murah_barang,0", "ADMPROMOTEBUSMURAHBARANG,ADMIN,promo_tebus_murah_barang,0", "KASPROMOTEBUSMURAHBARANG,KASIR,promo_tebus_murah_barang,0", "DIRPROMOTEBUSMURAHHEMAT,DIREKTUR,promo_tebus_murah_hemat,1", "MANPROMOTEBUSMURAHHEMAT,MANAGER,promo_tebus_murah_hemat,0", "ACCPROMOTEBUSMURAHHEMAT,ACCOUNTING,promo_tebus_murah_hemat,0", "INVPROMOTEBUSMURAHHEMAT,INVENTORY,promo_tebus_murah_hemat,0", "INVPROMOTEBUSMURAHHEMAT,STAF INVENTORY,promo_tebus_murah_hemat,0", "ADMPROMOTEBUSMURAHHEMAT,ADMIN,promo_tebus_murah_hemat,0", "KASPROMOTEBUSMURAHHEMAT,KASIR,promo_tebus_murah_hemat,0", "DIRREPORTSUMMARYPEMBELIAN,DIREKTUR,report_summary_pembelian,1", "MANREPORTSUMMARYPEMBELIAN,MANAGER,report_summary_pembelian,0", "ACCREPORTSUMMARYPEMBELIAN,ACCOUNTING,report_summary_pembelian,1", "INVREPORTSUMMARYPEMBELIAN,INVENTORY,report_summary_pembelian,0", "INVREPORTSUMMARYPEMBELIAN,STAF INVENTORY,report_summary_pembelian,0", "ADMREPORTSUMMARYPEMBELIAN,ADMIN,report_summary_pembelian,0", "KASREPORTSUMMARYPEMBELIAN,KASIR,report_summary_pembelian,0", "DIRREPORTDETAILPEMBELIAN,DIREKTUR,report_detail_pembelian,1", "MANREPORTDETAILPEMBELIAN,MANAGER,report_detail_pembelian,0", "ACCREPORTDETAILPEMBELIAN,ACCOUNTING,report_detail_pembelian,1", "INVREPORTDETAILPEMBELIAN,INVENTORY,report_detail_pembelian,0", "INVREPORTDETAILPEMBELIAN,STAF INVENTORY,report_detail_pembelian,0", "ADMREPORTDETAILPEMBELIAN,ADMIN,report_detail_pembelian,0", "KASREPORTDETAILPEMBELIAN,KASIR,report_detail_pembelian,0", "DIRREPORTHUTANG,DIREKTUR,report_hutang,1", "MANREPORTHUTANG,MANAGER,report_hutang,0", "ACCREPORTHUTANG,ACCOUNTING,report_hutang,1", "INVREPORTHUTANG,INVENTORY,report_hutang,0", "INVREPORTHUTANG,STAF INVENTORY,report_hutang,0", "ADMREPORTHUTANG,ADMIN,report_hutang,1", "KASREPORTHUTANG,KASIR,report_hutang,0", "DIRREPORTPELUNASANHUTANG,DIREKTUR,report_pelunasan_hutang,1", "MANREPORTPELUNASANHUTANG,MANAGER,report_pelunasan_hutang,0", "ACCREPORTPELUNASANHUTANG,ACCOUNTING,report_pelunasan_hutang,1", "INVREPORTPELUNASANHUTANG,INVENTORY,report_pelunasan_hutang,0", "INVREPORTPELUNASANHUTANG,STAF INVENTORY,report_pelunasan_hutang,0", "ADMREPORTPELUNASANHUTANG,ADMIN,report_pelunasan_hutang,1", "KASREPORTPELUNASANHUTANG,KASIR,report_pelunasan_hutang,0", "DIRREPORTBARANGDIBAWAHHPP,DIREKTUR,report_barang_dibawah_hpp,1", "MANREPORTBARANGDIBAWAHHPP,MANAGER,report_barang_dibawah_hpp,0", "ACCREPORTBARANGDIBAWAHHPP,ACCOUNTING,report_barang_dibawah_hpp,0", "INVREPORTBARANGDIBAWAHHPP,INVENTORY,report_barang_dibawah_hpp,0", "INVREPORTBARANGDIBAWAHHPP,STAF INVENTORY,report_barang_dibawah_hpp,0", "ADMREPORTBARANGDIBAWAHHPP,ADMIN,report_barang_dibawah_hpp,0", "KASREPORTBARANGDIBAWAHHPP,KASIR,report_barang_dibawah_hpp,0", "DIRREPORTHISTORYBARANG,DIREKTUR,report_history_barang,1", "MANREPORTHISTORYBARANG,MANAGER,report_history_barang,0", "ACCREPORTHISTORYBARANG,ACCOUNTING,report_history_barang,0", "INVREPORTHISTORYBARANG,INVENTORY,report_history_barang,0", "INVREPORTHISTORYBARANG,STAF INVENTORY,report_history_barang,0", "ADMREPORTHISTORYBARANG,ADMIN,report_history_barang,0", "KASREPORTHISTORYBARANG,KASIR,report_history_barang,0", "DIRREPORTPLANOGRAM,DIREKTUR,report_planogram,1", "MANREPORTPLANOGRAM,MANAGER,report_planogram,1", "ACCREPORTPLANOGRAM,ACCOUNTING,report_planogram,1", "INVREPORTPLANOGRAM,INVENTORY,report_planogram,1", "INVREPORTPLANOGRAM,STAF INVENTORY,report_planogram,1", "ADMREPORTPLANOGRAM,ADMIN,report_planogram,1", "KASREPORTPLANOGRAM,KASIR,report_planogram,1", "DIRREPORTKEUANGANGLOBAL,DIREKTUR,report_keuangan_global,1", "MANREPORTKEUANGANGLOBAL,MANAGER,report_keuangan_global,0", "ACCREPORTKEUANGANGLOBAL,ACCOUNTING,report_keuangan_global,1", "INVREPORTKEUANGANGLOBAL,INVENTORY,report_keuangan_global,0", "INVREPORTKEUANGANGLOBAL,STAF INVENTORY,report_keuangan_global,0", "ADMREPORTKEUANGANGLOBAL,ADMIN,report_keuangan_global,1", "KASREPORTKEUANGANGLOBAL,KASIR,report_keuangan_global,0", "DIRREPORTKEUANGANSHIFT,DIREKTUR,report_keuangan_shift,1", "MANREPORTKEUANGANSHIFT,MANAGER,report_keuangan_shift,0", "ACCREPORTKEUANGANSHIFT,ACCOUNTING,report_keuangan_shift,1", "INVREPORTKEUANGANSHIFT,INVENTORY,report_keuangan_shift,0", "INVREPORTKEUANGANSHIFT,STAF INVENTORY,report_keuangan_shift,0", "ADMREPORTKEUANGANSHIFT,ADMIN,report_keuangan_shift,1", "KASREPORTKEUANGANSHIFT,KASIR,report_keuangan_shift,0", "DIRREPORTSTOKTERAKHIR,DIREKTUR,report_stok_terakhir,1", "MANREPORTSTOKTERAKHIR,MANAGER,report_stok_terakhir,0", "ACCREPORTSTOKTERAKHIR,ACCOUNTING,report_stok_terakhir,1", "INVREPORTSTOKTERAKHIR,INVENTORY,report_stok_terakhir,0", "INVREPORTSTOKTERAKHIR,STAF INVENTORY,report_stok_terakhir,0", "ADMREPORTSTOKTERAKHIR,ADMIN,report_stok_terakhir,0", "KASREPORTSTOKTERAKHIR,KASIR,report_stok_terakhir,0", "DIRREPORTSTOKOPNAM,DIREKTUR,report_stok_opnam,1", "MANREPORTSTOKOPNAM,MANAGER,report_stok_opnam,0", "ACCREPORTSTOKOPNAM,ACCOUNTING,report_stok_opnam,1", "INVREPORTSTOKOPNAM,INVENTORY,report_stok_opnam,1", "INVREPORTSTOKOPNAM,STAF INVENTORY,report_stok_opnam,1", "ADMREPORTSTOKOPNAM,ADMIN,report_stok_opnam,0", "KASREPORTSTOKOPNAM,KASIR,report_stok_opnam,0", "DIRREPORTSTOKOPNAMDETAIL,DIREKTUR,report_stok_opnam_detail,1", "MANREPORTSTOKOPNAMDETAIL,MANAGER,report_stok_opnam_detail,0", "ACCREPORTSTOKOPNAMDETAIL,ACCOUNTING,report_stok_opnam_detail,1", "INVREPORTSTOKOPNAMDETAIL,INVENTORY,report_stok_opnam_detail,1", "INVREPORTSTOKOPNAMDETAIL,STAF INVENTORY,report_stok_opnam_detail,1", "ADMREPORTSTOKOPNAMDETAIL,ADMIN,report_stok_opnam_detail,1", "KASREPORTSTOKOPNAMDETAIL,KASIR,report_stok_opnam_detail,0", "DIRREPORTSTOKTRANSACTION,DIREKTUR,report_stok_transaction,1", "MANREPORTSTOKTRANSACTION,MANAGER,report_stok_transaction,0", "ACCREPORTSTOKTRANSACTION,ACCOUNTING,report_stok_transaction,0", "INVREPORTSTOKTRANSACTION,INVENTORY,report_stok_transaction,1", "INVREPORTSTOKTRANSACTION,STAF INVENTORY,report_stok_transaction,1", "ADMREPORTSTOKTRANSACTION,ADMIN,report_stok_transaction,0", "KASREPORTSTOKTRANSACTION,KASIR,report_stok_transaction,0", "DIRREPORTSELISIHINPUTSTOK,DIREKTUR,report_selisih_input_stok,1", "MANREPORTSELISIHINPUTSTOK,MANAGER,report_selisih_input_stok,0", "ACCREPORTSELISIHINPUTSTOK,ACCOUNTING,report_selisih_input_stok,0", "INVREPORTSELISIHINPUTSTOK,INVENTORY,report_selisih_input_stok,1", "INVREPORTSELISIHINPUTSTOK,STAF INVENTORY,report_selisih_input_stok,1", "ADMREPORTSELISIHINPUTSTOK,ADMIN,report_selisih_input_stok,0", "KASREPORTSELISIHINPUTSTOK,KASIR,report_selisih_input_stok,0", "DIRREPORTPELUNASANPIUTANG,DIREKTUR,report_pelunasan_piutang,1", "MANREPORTPELUNASANPIUTANG,MANAGER,report_pelunasan_piutang,1", "ACCREPORTPELUNASANPIUTANG,ACCOUNTING,report_pelunasan_piutang,1", "INVREPORTPELUNASANPIUTANG,INVENTORY,report_pelunasan_piutang,0", "INVREPORTPELUNASANPIUTANG,STAF INVENTORY,report_pelunasan_piutang,0", "ADMREPORTPELUNASANPIUTANG,ADMIN,report_pelunasan_piutang,1", "KASREPORTPELUNASANPIUTANG,KASIR,report_pelunasan_piutang,0", "DIRREPORTPIUTANGCUSTOMER,DIREKTUR,report_piutang_customer,1", "MANREPORTPIUTANGCUSTOMER,MANAGER,report_piutang_customer,1", "ACCREPORTPIUTANGCUSTOMER,ACCOUNTING,report_piutang_customer,1", "INVREPORTPIUTANGCUSTOMER,INVENTORY,report_piutang_customer,0", "INVREPORTPIUTANGCUSTOMER,STAF INVENTORY,report_piutang_customer,0", "ADMREPORTPIUTANGCUSTOMER,ADMIN,report_piutang_customer,1", "KASREPORTPIUTANGCUSTOMER,KASIR,report_piutang_customer,0", "DIRREPORTPIUTANGCUSTOMEREXCEL,DIREKTUR,report_piutang_customer_excel,1", "MANREPORTPIUTANGCUSTOMEREXCEL,MANAGER,report_piutang_customer_excel,1", "ACCREPORTPIUTANGCUSTOMEREXCEL,ACCOUNTING,report_piutang_customer_excel,1", "INVREPORTPIUTANGCUSTOMEREXCEL,INVENTORY,report_piutang_customer_excel,0", "INVREPORTPIUTANGCUSTOMEREXCEL,STAF INVENTORY,report_piutang_customer_excel,0", "ADMREPORTPIUTANGCUSTOMEREXCEL,ADMIN,report_piutang_customer_excel,1", "KASREPORTPIUTANGCUSTOMEREXCEL,KASIR,report_piutang_customer_excel,0", "DIRREPORTMASTER,DIREKTUR,report_master,1", "MANREPORTMASTER,MANAGER,report_master,0", "ACCREPORTMASTER,ACCOUNTING,report_master,0", "INVREPORTMASTER,INVENTORY,report_master,0", "INVREPORTMASTER,STAF INVENTORY,report_master,0", "ADMREPORTMASTER,ADMIN,report_master,0", "KASREPORTMASTER,KASIR,report_master,0", "DIRREPORTMASTERBARANG,DIREKTUR,report_master_barang,1", "MANREPORTMASTERBARANG,MANAGER,report_master_barang,0", "ACCREPORTMASTERBARANG,ACCOUNTING,report_master_barang,0", "INVREPORTMASTERBARANG,INVENTORY,report_master_barang,0", "INVREPORTMASTERBARANG,STAF INVENTORY,report_master_barang,0", "ADMREPORTMASTERBARANG,ADMIN,report_master_barang,0", "KASREPORTMASTERBARANG,KASIR,report_master_barang,0", "DIRREPORTMASTERCUSTOMER,DIREKTUR,report_master_customer,1", "MANREPORTMASTERCUSTOMER,MANAGER,report_master_customer,0", "ACCREPORTMASTERCUSTOMER,ACCOUNTING,report_master_customer,0", "INVREPORTMASTERCUSTOMER,INVENTORY,report_master_customer,0", "INVREPORTMASTERCUSTOMER,STAF INVENTORY,report_master_customer,0", "ADMREPORTMASTERCUSTOMER,ADMIN,report_master_customer,0", "KASREPORTMASTERCUSTOMER,KASIR,report_master_customer,0", "DIRREPORTMASTERSUPPLIER,DIREKTUR,report_master_supplier,1", "MANREPORTMASTERSUPPLIER,MANAGER,report_master_supplier,0", "ACCREPORTMASTERSUPPLIER,ACCOUNTING,report_master_supplier,0", "INVREPORTMASTERSUPPLIER,INVENTORY,report_master_supplier,0", "INVREPORTMASTERSUPPLIER,STAF INVENTORY,report_master_supplier,0", "ADMREPORTMASTERSUPPLIER,ADMIN,report_master_supplier,0", "KASREPORTMASTERSUPPLIER,KASIR,report_master_supplier,0", "DIRREPORTMASTERKARYAWAN,DIREKTUR,report_master_karyawan,1", "MANREPORTMASTERKARYAWAN,MANAGER,report_master_karyawan,0", "ACCREPORTMASTERKARYAWAN,ACCOUNTING,report_master_karyawan,0", "INVREPORTMASTERKARYAWAN,INVENTORY,report_master_karyawan,0", "INVREPORTMASTERKARYAWAN,STAF INVENTORY,report_master_karyawan,0", "ADMREPORTMASTERKARYAWAN,ADMIN,report_master_karyawan,0", "KASREPORTMASTERKARYAWAN,KASIR,report_master_karyawan,0", "DIRPENGATURANPASSWORD,DIREKTUR,pengaturan_password,1", "MANPENGATURANPASSWORD,MANAGER,pengaturan_password,1", "ACCPENGATURANPASSWORD,ACCOUNTING,pengaturan_password,1", "INVPENGATURANPASSWORD,INVENTORY,pengaturan_password,1", "INVPENGATURANPASSWORD,STAF INVENTORY,pengaturan_password,1", "ADMPENGATURANPASSWORD,ADMIN,pengaturan_password,1", "KASPENGATURANPASSWORD,KASIR,pengaturan_password,1", "DIRPENGATURANDATATOKO,DIREKTUR,pengaturan_data_toko,1", "MANPENGATURANDATATOKO,MANAGER,pengaturan_data_toko,1", "ACCPENGATURANDATATOKO,ACCOUNTING,pengaturan_data_toko,0", "INVPENGATURANDATATOKO,INVENTORY,pengaturan_data_toko,0", "INVPENGATURANDATATOKO,STAF INVENTORY,pengaturan_data_toko,0", "ADMPENGATURANDATATOKO,ADMIN,pengaturan_data_toko,0", "KASPENGATURANDATATOKO,KASIR,pengaturan_data_toko,0", "DIRPENGATURANPOINBELANJA,DIREKTUR,pengaturan_poin_belanja,1", "MANPENGATURANPOINBELANJA,MANAGER,pengaturan_poin_belanja,0", "ACCPENGATURANPOINBELANJA,ACCOUNTING,pengaturan_poin_belanja,0", "INVPENGATURANPOINBELANJA,INVENTORY,pengaturan_poin_belanja,0", "INVPENGATURANPOINBELANJA,STAF INVENTORY,pengaturan_poin_belanja,0", "ADMPENGATURANPOINBELANJA,ADMIN,pengaturan_poin_belanja,0", "KASPENGATURANPOINBELANJA,KASIR,pengaturan_poin_belanja,0", "DIRPENGATURANPROMOHEMAT,DIREKTUR,pengaturan_promo_hemat,1", "MANPENGATURANPROMOHEMAT,MANAGER,pengaturan_promo_hemat,0", "ACCPENGATURANPROMOHEMAT,ACCOUNTING,pengaturan_promo_hemat,0", "INVPENGATURANPROMOHEMAT,INVENTORY,pengaturan_promo_hemat,0", "INVPENGATURANPROMOHEMAT,STAF INVENTORY,pengaturan_promo_hemat,0", "ADMPENGATURANPROMOHEMAT,ADMIN,pengaturan_promo_hemat,0", "KASPENGATURANPROMOHEMAT,KASIR,pengaturan_promo_hemat,0", "DIRPENGATURANPROMOTEBUSMURAH,DIREKTUR,pengaturan_promo_tebus_murah,1", "MANPENGATURANPROMOTEBUSMURAH,MANAGER,pengaturan_promo_tebus_murah,0", "ACCPENGATURANPROMOTEBUSMURAH,ACCOUNTING,pengaturan_promo_tebus_murah,0", "INVPENGATURANPROMOTEBUSMURAH,INVENTORY,pengaturan_promo_tebus_murah,0", "INVPENGATURANPROMOTEBUSMURAH,STAF INVENTORY,pengaturan_promo_tebus_murah,0", "ADMPENGATURANPROMOTEBUSMURAH,ADMIN,pengaturan_promo_tebus_murah,0", "KASPENGATURANPROMOTEBUSMURAHKASIR,KASIR,pengaturan_promo_tebus_murah,0", "DIRPENGATURANPROMOJENIS,DIREKTUR,pengaturan_promo_jenis,1", "MANPENGATURANPROMOJENIS,MANAGER,pengaturan_promo_jenis,0", "ACCPENGATURANPROMOJENIS,ACCOUNTING,pengaturan_promo_jenis,0", "INVPENGATURANPROMOJENIS,INVENTORY,pengaturan_promo_jenis,0", "INVPENGATURANPROMOJENIS,STAF INVENTORY,pengaturan_promo_jenis,0", "ADMPENGATURANPROMOJENIS,ADMIN,pengaturan_promo_jenis,0", "KASPENGATURANPROMOJENIS,KASIR,pengaturan_promo_jenis,0", "DIRPENGATURANPROMOKHUSUSPELANGGAN,DIREKTUR,pengaturan_promo_khusus_pelanggan,1", "MANPENGATURANPROMOKHUSUSPELANGGAN,MANAGER,pengaturan_promo_khusus_pelanggan,0", "ACCPENGATURANPROMOKHUSUSPELANGGAN,ACCOUNTING,pengaturan_promo_khusus_pelanggan,0", "INVPENGATURANPROMOKHUSUSPELANGGAN,INVENTORY,pengaturan_promo_khusus_pelanggan,0", "INVPENGATURANPROMOKHUSUSPELANGGAN,STAF INVENTORY,pengaturan_promo_khusus_pelanggan,0", "ADMPENGATURANPROMOKHUSUSPELANGGAN,ADMIN,pengaturan_promo_khusus_pelanggan,0", "KASPENGATURANPROMOKHUSUSPELANGGAN,KASIR,pengaturan_promo_khusus_pelanggan,0", "DIRPENGATURANPROMOTEBUSHEMAT,DIREKTUR,pengaturan_promo_tebus_hemat,1", "MANPENGATURANPROMOTEBUSHEMAT,MANAGER,pengaturan_promo_tebus_hemat,0", "ACCPENGATURANPROMOTEBUSHEMAT,ACCOUNTING,pengaturan_promo_tebus_hemat,0", "INVPENGATURANPROMOTEBUSHEMAT,INVENTORY,pengaturan_promo_tebus_hemat,0", "INVPENGATURANPROMOTEBUSHEMAT,STAF INVENTORY,pengaturan_promo_tebus_hemat,0", "ADMPENGATURANPROMOTEBUSHEMAT,ADMIN,pengaturan_promo_tebus_hemat,0", "KASPENGATURANPROMOTEBUSHEMAT,KASIR,pengaturan_promo_tebus_hemat,0", "DIRPENGATURANPROMOVOUCHER,DIREKTUR,pengaturan_promo_voucher,1", "MANPENGATURANPROMOVOUCHER,MANAGER,pengaturan_promo_voucher,0", "ACCPENGATURANPROMOVOUCHER,ACCOUNTING,pengaturan_promo_voucher,0", "INVPENGATURANPROMOVOUCHER,INVENTORY,pengaturan_promo_voucher,0", "INVPENGATURANPROMOVOUCHER,STAF INVENTORY,pengaturan_promo_voucher,0", "ADMPENGATURANPROMOVOUCHER,ADMIN,pengaturan_promo_voucher,0", "KASPENGATURANPROMOVOUCHER,KASIR,pengaturan_promo_voucher,0", "DIRPENGATURANROLE,DIREKTUR,pengaturan_role,1", "MANPENGATURANROLE,MANAGER,pengaturan_role,0", "ACCPENGATURANROLE,ACCOUNTING,pengaturan_role,0", "INVPENGATURANROLE,INVENTORY,pengaturan_role,0", "INVPENGATURANROLE,STAF INVENTORY,pengaturan_role,0", "ADMPENGATURANROLE,ADMIN,pengaturan_role,0", "KASPENGATURANROLE,KASIR,pengaturan_role,0", "DIRPENGATURANSTOKOPNAM,DIREKTUR,pengaturan_stok_opnam,1", "MANPENGATURANSTOKOPNAM,MANAGER,pengaturan_stok_opnam,0", "ACCPENGATURANSTOKOPNAM,ACCOUNTING,pengaturan_stok_opnam,0", "INVPENGATURANSTOKOPNAM,INVENTORY,pengaturan_stok_opnam,1", "INVPENGATURANSTOKOPNAM,STAF INVENTORY,pengaturan_stok_opnam,1", "ADMPENGATURANSTOKOPNAM,ADMIN,pengaturan_stok_opnam,0", "KASPENGATURANSTOKOPNAM,KASIR,pengaturan_stok_opnam,0"};
        if (this.helper.getKasir() == 1) {
            int size = dataRole.length;
            for (int a = 0; a < size; ++a) {
                String[] data = dataRole[a].split(",");
                Role cekrole = this.roleService.getRoleById(data[0]);
                if (cekrole != null) continue;
                Role role = new Role();
                role.setId(data[0]);
                role.setRole(data[1]);
                role.setMenu(data[2]);
                role.setAccess(Integer.parseInt(data[3] + ""));
                role.setUpdated(new Date());
                role.setUpdatedBy(this.helper.getKaryawan().getId());
                roles.add(role);
            }
            this.roleService.createRoles(roles);
        }
    }

    private void setPenjualanParsial() {
        if (this.kasirParsial) {
            this.penjualanMenuItems[0].setEnabled(false);
            this.penjualanMenuItems[1].setEnabled(false);
            this.penjualanMenuItems[2].setEnabled(false);
        } else {
            this.penjualanMenuItems[3].setEnabled(false);
        }
    }

    private void setRole2() {
        ConnectionManager cm = ConnectionManager.getInstance();
        if (!cm.getDataSource().getUrl().contains("localhost")) {
            this.commitMenu.setVisible(false);
            this.sinkronisasiMenu.setVisible(false);
        }
        this.mutasiMenuItems[2].setVisible(false);
        List<Role> roles = this.roleService.getRoles(this.helper.getKaryawan().getRole());
        for (Role role : roles) {
            this.applyRoleVisibility(role, "menu_entry_pembelian", this.mutasiMenuItems[0]);
            this.applyRoleVisibility(role, "menu_entry_penjualan", this.mutasiMenuItems[1]);
            this.applyRoleVisibility(role, "menu_stok_opnam", this.stokOpnamMenu);
            this.applyRoleVisibility(role, "menu_report_promo", this.reportPromoMenu);
            this.applyRoleVisibility(role, "menu_report_pembelian", this.reportPembelianMenu);
            this.applyRoleVisibility(role, "menu_report_hutang", this.reportHutangMenu);
            this.applyRoleVisibility(role, "menu_report_barang", this.reportBarangMenu);
            this.applyRoleVisibility(role, "menu_report_keuangan_kasir", this.reportKeuanganKasirMenu);
            this.applyRoleVisibility(role, "menu_report_stok", this.reportStokMenu);
            this.applyRoleVisibility(role, "menu_report_piutang", this.reportPiutangMenu);
            this.applyRoleVisibility(role, "menu_report_master", this.reportMasterMenu);
            this.applyRoleVisibility(role, "menu_promo", this.promoMenu);
            this.applyRoleVisibility(role, "report_penjualan", this.reportPenjualanMenu);
            this.applyRoleVisibility(role, "tabel_barang", this.tabelMenuItems[0]);
            this.applyRoleVisibility(role, "tabel_jenis", this.tabelMenuItems[1]);
            this.applyRoleVisibility(role, "tabel_customer", this.tabelMenuItems[2]);
            this.applyRoleVisibility(role, "tabel_supplier", this.tabelMenuItems[3]);
            this.applyRoleVisibility(role, "tabel_karyawan", this.tabelMenuItems[4]);
            this.applyRoleVisibility(role, "tabel_sales", this.tabelMenuItems[5]);
            this.applyRoleVisibility(role, "tabel_parent", this.tabelMenuItems[6]);
            this.applyRoleVisibility(role, "mutasi_pembelian", this.pembelianMenuItems[0]);
            this.applyRoleVisibility(role, "mutasi_pembelian2", this.pembelianMenuItems[1]);
            this.applyRoleVisibility(role, "mutasi_penjualan", this.penjualanMenuItems[0]);
            this.applyRoleVisibility(role, "mutasi_penjualan2", this.penjualanMenuItems[1]);
            this.applyRoleVisibility(role, "mutasi_penjualan_cabang", this.penjualanMenuItems[2]);
            this.applyRoleVisibility(role, "mutasi_penjualan_parsial", this.penjualanMenuItems[3]);
            this.applyRoleVisibility(role, "mutasi_penjualan_excel2", this.penjualanMenuItems[4]);
            this.applyRoleVisibility(role, "mutasi_penjualan_cabang_new", this.penjualanMenuItems[5]);
            this.applyRoleVisibility(role, "penukaran_poin_belanja", this.mutasiMenuItems[3]);
            this.applyRoleVisibility(role, "entry_pengembalian_barang", this.pengembalianBarangMenuItem);
            this.applyRoleVisibility(role, "repack_barang", this.repackMenuItem);
            this.applyRoleVisibility(role, "stok_opnam_manual", this.stokOpnameMenuItems[0]);
            this.applyRoleVisibility(role, "stok_opnam_parsial", this.stokOpnameMenuItems[1]);
            this.applyRoleVisibility(role, "stok_opnam_mandiri", this.stokOpnameMenuItems[2]);
            this.applyRoleVisibility(role, "mutasi_barang", this.mutasiBarangMenuItem);
            this.applyRoleVisibility(role, "report_keuntungan", this.reportMenuItems[0]);
            this.applyRoleVisibility(role, "report_mutasi", this.reportMenuItems[1]);
            this.applyRoleVisibility(role, "report_penukaran_point", this.reportMenuItems[2]);
            this.applyRoleVisibility(role, "report_pengembalian_barang", this.reportMenuItems[3]);
            this.applyRoleVisibility(role, "report_penukaran_voucher", this.reportMenuItems[4]);
            this.applyRoleVisibility(role, "report_performa", this.reportMenuItems[5]);
            this.applyRoleVisibility(role, "report_repack", this.reportMenuItems[6]);
            this.applyRoleVisibility(role, "reprint_invoice", this.reportMenuItems[7]);
            this.applyRoleVisibility(role, "report_summary_penjualan", this.reportPenjualanMenuItem[0]);
            this.applyRoleVisibility(role, "report_detail_penjualan", this.reportPenjualanMenuItem[1]);
            this.applyRoleVisibility(role, "report_rekap_penjualan", this.reportPenjualanMenuItem[2]);
            this.applyRoleVisibility(role, "report_poin_penjualan", this.reportPenjualanMenuItem[3]);
            this.applyRoleVisibility(role, "report_best_seller", this.reportPenjualanMenuItem[4]);
            this.applyRoleVisibility(role, "rekap_penjualan_sales", this.reportPenjualanMenuItem[5]);
            this.applyRoleVisibility(role, "rekap_penjualan_customer", this.reportPenjualanMenuItem[6]);
            this.applyRoleVisibility(role, "promo_hemat", this.reportPromoMenuItem[0]);
            this.applyRoleVisibility(role, "promo_tebus_murah_barang", this.reportPromoMenuItem[1]);
            this.applyRoleVisibility(role, "promo_tebus_murah_hemat", this.reportPromoMenuItem[2]);
            this.applyRoleVisibility(role, "report_summary_pembelian", this.reportPembelianMenuItem[0]);
            this.applyRoleVisibility(role, "report_detail_pembelian", this.reportPembelianMenuItem[1]);
            this.applyRoleVisibility(role, "report_hutang", this.reportHutangMenuItem[0]);
            this.applyRoleVisibility(role, "report_pelunasan_hutang", this.reportHutangMenuItem[0]);
            this.applyRoleVisibility(role, "report_barang_dibawah_hpp", this.reportBarangMenuItem[0]);
            this.applyRoleVisibility(role, "report_history_barang", this.reportBarangMenuItem[1]);
            this.applyRoleVisibility(role, "report_keuangan_global", this.reportKeuanganKasirMenuItem[0]);
            this.applyRoleVisibility(role, "report_keuangan_shift", this.reportKeuanganKasirMenuItem[1]);
            this.applyRoleVisibility(role, "report_stok_terakhir", this.reportStokMenuItem[0]);
            this.applyRoleVisibility(role, "report_stok_opnam", this.reportStokMenuItem[1]);
            this.applyRoleVisibility(role, "report_stok_opnam_detail", this.reportStokMenuItem[2]);
            this.applyRoleVisibility(role, "report_stok_transaction", this.reportStokMenuItem[3]);
            this.applyRoleVisibility(role, "report_selisih_input_stok", this.reportStokMenuItem[4]);
            this.applyRoleVisibility(role, "report_pelunasan_piutang", this.reportPiutangMenuItem[0]);
            this.applyRoleVisibility(role, "report_piutang_customer", this.reportPiutangMenuItem[1]);
            this.applyRoleVisibility(role, "report_piutang_customer_excel", this.reportPiutangMenuItem[2]);
            this.applyRoleVisibility(role, "report_master", this.reportMasterMenu);
            this.applyRoleVisibility(role, "report_master_barang", this.reportMasterMenuItem[0]);
            this.applyRoleVisibility(role, "report_master_customer", this.reportMasterMenuItem[1]);
            this.applyRoleVisibility(role, "report_master_supplier", this.reportMasterMenuItem[2]);
            this.applyRoleVisibility(role, "report_master_karyawan", this.reportMasterMenuItem[3]);
            this.applyRoleVisibility(role, "pengaturan_password", this.pengaturanMenuItems[0]);
            this.applyRoleVisibility(role, "pengaturan_data_toko", this.pengaturanMenuItems[1]);
            this.applyRoleVisibility(role, "pengaturan_poin_belanja", this.pengaturanMenuItems[2]);
            this.applyRoleVisibility(role, "pengaturan_promo_hemat", this.promoMenuItems[0]);
            this.applyRoleVisibility(role, "pengaturan_promo_tebus_murah", this.promoMenuItems[1]);
            this.applyRoleVisibility(role, "pengaturan_promo_jenis", this.promoMenuItems[2]);
            this.applyRoleVisibility(role, "pengaturan_promo_khusus_pelanggan", this.promoMenuItems[3]);
            this.applyRoleVisibility(role, "pengaturan_promo_tebus_hemat", this.promoMenuItems[4]);
            this.applyRoleVisibility(role, "pengaturan_promo_voucher", this.promoVoucherMenu);
            this.applyRoleVisibility(role, "pengaturan_role", this.roleMenuItem);
            this.applyRoleVisibility(role, "pengaturan_stok_opnam", this.pengaturanSoMenuItem);
        }
        if (this.kasirId == 99) {
            this.commitMenu.setVisible(false);
            this.sinkronisasiMenu.setVisible(false);
            this.mutasiMenuItems[0].setVisible(false);
            this.mutasiMenuItems[1].setVisible(false);
        }
    }

    private void initComponent() {
        this.menuBar = new JMenuBar();
        this.exitMenu = new JMenu("Exit");
        this.tabelMenu = new JMenu("Tabel");
        this.mutasiMenu = new JMenu("Mutasi");
        this.reportMenu = new JMenu("Report");
        this.pengaturanMenu = new JMenu("Pengaturan");
        this.cetakMenu = new JMenu("Cetak");
        this.commitMenu = new JMenu("Commit");
        this.sinkronisasiMenu = new JMenu("Sinkronisasi");
        this.sinkronisasiMenuItems = new JMenuItem[5];
        this.sinkronisasiMenuItems[0] = new JMenuItem("Sinkronisasi Dari Online ke Offline");
        this.sinkronisasiMenuItems[1] = new JMenuItem("Sinkronisasi PO Online");
        this.sinkronisasiMenuItems[2] = new JMenuItem("Sinkronisasi Data m_sales");
        this.sinkronisasiMenuItems[3] = new JMenuItem("Sinkronisasi Promo");
        this.sinkronisasiMenuItems[4] = new JMenuItem("Sinkronisasi Paket");
        this.exitMenuItems = new JMenuItem[2];
        this.tabelMenuItems = new JMenuItem[7];
        this.mutasiMenuItems = new JMenu[4];
        this.reportMenuItems = new JMenuItem[8];
        this.updateMenuItems = new JMenuItem[3];
        this.penjualanMenuItems = new JMenuItem[6];
        this.pembelianMenuItems = new JMenuItem[2];
        this.penukaranPointBelanja = new JMenuItem[1];
        this.pengaturanMenuItems = new JMenuItem[3];
        this.mutasiBarangMenuItems = new JMenuItem[1];
        this.reportPromoMenuItem = new JMenuItem[5];
        this.reportPromoMenuItem[0] = new JMenuItem("Promo Hemat");
        this.reportPromoMenuItem[1] = new JMenuItem("Promo Tebus Murah Barang");
        this.reportPromoMenuItem[2] = new JMenuItem("Promo Tebus Murah Hemat");
        this.reportPromoMenuItem[3] = new JMenuItem("Performa Promo TM/PWP");
        this.reportPromoMenuItem[4] = new JMenuItem("Performa All Promo");
        this.reportPenjualanMenuItem = new JMenuItem[7];
        this.reportPenjualanMenuItem[0] = new JMenuItem("Summary Penjualan");
        this.reportPenjualanMenuItem[1] = new JMenuItem("Detail Penjualan");
        this.reportPenjualanMenuItem[2] = new JMenuItem("Rekap Penjualan");
        this.reportPenjualanMenuItem[3] = new JMenuItem("Poin Penjualan Customer");
        this.reportPenjualanMenuItem[4] = new JMenuItem("Best Seller");
        this.reportPenjualanMenuItem[5] = new JMenuItem("Rekap Penjualan Sales");
        this.reportPenjualanMenuItem[6] = new JMenuItem("Rekap Penjualan Customer");
        this.reportHutangMenuItem = new JMenuItem[2];
        this.reportHutangMenuItem[0] = new JMenuItem("Report Hutang");
        this.reportHutangMenuItem[1] = new JMenuItem("Report Pelunasan Hutang");
        this.reportPembelianMenuItem = new JMenuItem[2];
        this.reportPembelianMenuItem[0] = new JMenuItem("Summary Pembelian");
        this.reportPembelianMenuItem[1] = new JMenuItem("Detail Pembelian");
        this.promoMenuItems = new JMenuItem[5];
        this.promoMenuItems[0] = new JMenuItem("Promo Hemat");
        this.promoMenuItems[1] = new JMenuItem("Promo Tebus Murah Barang");
        this.promoMenuItems[2] = new JMenuItem("Promo Disc By Jenis");
        this.promoMenuItems[3] = new JMenuItem("Promo Khusus Pelanggan");
        this.promoMenuItems[4] = new JMenuItem("Promo Tebus Murah Hemat");
        this.promoVoucherMenuItem = new JMenuItem[2];
        this.promoVoucherMenuItem[0] = new JMenuItem("Promo Voucher Belanja");
        this.promoVoucherMenuItem[1] = new JMenuItem("Daftar Promo Voucher");
        this.reportBarangMenuItem = new JMenuItem[3];
        this.reportBarangMenuItem[0] = new JMenuItem("Report Barang Dibawah Harga Beli");
        this.reportBarangMenuItem[1] = new JMenuItem("Report History Harga");
        this.reportBarangMenuItem[2] = new JMenuItem("Report Planogram");
        this.reportKeuanganKasirMenuItem = new JMenuItem[2];
        this.reportKeuanganKasirMenuItem[0] = new JMenuItem("Keuangan Global");
        this.reportKeuanganKasirMenuItem[1] = new JMenuItem("Keuangan Tiap Shift");
        this.reportPiutangMenuItem = new JMenuItem[3];
        this.stokOpnamMenu = new JMenu("Stok Opname");
        this.stokOpnameMenuItems = new JMenuItem[3];
        this.stokOpnameMenuItems[0] = new JMenuItem("Stok Opname Manual");
        this.stokOpnameMenuItems[1] = new JMenuItem("Stok Opnam Parsial");
        this.stokOpnameMenuItems[2] = new JMenuItem("Stok Opnam Mandiri");
        this.mutasiBarangMenuItem = new JMenuItem("Mutasi Barang");
        this.labelHargaMenuItem = new JMenuItem("Cetak Label Harga");
        this.pengembalianBarangMenuItem = new JMenuItem("Entry Pengembalian Barang");
        this.repackMenuItem = new JMenuItem("Repack Barang");
        this.reportStokMenuItem = new JMenuItem[6];
        this.reportStokMenuItem[0] = new JMenuItem("Stok Terakhir");
        this.reportStokMenuItem[1] = new JMenuItem("Stok Opnam");
        this.reportStokMenuItem[2] = new JMenuItem("Stok Opnam Detail");
        this.reportStokMenuItem[3] = new JMenuItem("Stok transaction");
        this.reportStokMenuItem[4] = new JMenuItem("Selisih input Stok");
        this.reportStokMenuItem[5] = new JMenuItem("Stok Opnam Mandiri Log");
        this.pengaturanMenuItems[0] = new JMenuItem("Password");
        this.pengaturanMenuItems[1] = new JMenuItem("Data Toko");
        this.pengaturanMenuItems[2] = new JMenuItem("Poin Belanja");
        this.updateMenuItems[0] = new JMenuItem("Update Barang Excel");
        this.updateMenuItems[1] = new JMenuItem("Update Customer Excel");
        this.updateMenuItems[2] = new JMenuItem("Create Customer Excel");
        this.reportMenuItems[0] = new JMenuItem("Report Keuntungan");
        this.reportMenuItems[1] = new JMenuItem("Report Mutasi");
        this.reportMenuItems[2] = new JMenuItem("Report Penukaran Poin");
        this.reportMenuItems[3] = new JMenuItem("Report Pengembalian Barang");
        this.reportMenuItems[4] = new JMenuItem("Report Penukaran Voucher");
        this.reportMenuItems[5] = new JMenuItem("Report Performa");
        this.reportMenuItems[6] = new JMenuItem("Report Repack");
        this.reportMenuItems[7] = new JMenuItem("Report Reprint Invoice");
        this.mutasiMenuItems[0] = new JMenu("Entry Pembelian");
        this.mutasiMenuItems[1] = new JMenu("Entry Penjualan");
        this.mutasiMenuItems[2] = new JMenu("Entry Koreksi Stok");
        this.mutasiMenuItems[3] = new JMenu("Penukaran Point");
        this.reportMasterMenu = new JMenu("Report Master");
        this.reportMasterMenuItem = new JMenuItem[4];
        this.reportMasterMenuItem[0] = new JMenuItem("Report Barang");
        this.reportMasterMenuItem[1] = new JMenuItem("Report Customer");
        this.reportMasterMenuItem[2] = new JMenuItem("Report Supplier");
        this.reportMasterMenuItem[3] = new JMenuItem("Report Karyawan");
        this.reportBarangMenu = new JMenu("Report Barang");
        this.reportPenjualanMenu = new JMenu("Report Penjualan");
        this.reportPromoMenu = new JMenu("Report Promo");
        this.reportBSMenu = new JMenu("Report BS");
        this.reportPembelianMenu = new JMenu("Report Pembelian");
        this.reportHutangMenu = new JMenu("Report Hutang");
        this.promoMenu = new JMenu("Promo");
        this.promoVoucherMenu = new JMenu("Promo Voucher");
        this.reportKeuanganKasirMenu = new JMenu("Report Keuangan Kasir");
        this.reportStokMenu = new JMenu("Report Stok");
        this.reportPiutangMenu = new JMenu("Report Piutang");
        this.exitMenuItems[0] = new JMenuItem("Log Out");
        this.exitMenuItems[1] = new JMenuItem("Exit to Dos");
        this.tabelMenuItems[0] = new JMenuItem("Tabel Barang");
        this.tabelMenuItems[1] = new JMenuItem("Tabel Jenis");
        this.tabelMenuItems[2] = new JMenuItem("Tabel Customer");
        this.tabelMenuItems[3] = new JMenuItem("Tabel Supplier");
        this.tabelMenuItems[4] = new JMenuItem("Tabel Karyawan");
        this.tabelMenuItems[5] = new JMenuItem("Tabel Sales");
        this.tabelMenuItems[6] = new JMenuItem("Tabel Parent Barang");
        this.mutasiBarangMenuItems[0] = new JMenuItem("Mutasi Barang");
        this.pembelianMenuItems[0] = new JMenuItem("Pembelian");
        this.pembelianMenuItems[1] = new JMenuItem("Pembelian II");
        this.penjualanMenuItems[0] = new JMenuItem("Penjualan");
        this.penjualanMenuItems[1] = new JMenuItem("Penjualan II");
        this.penjualanMenuItems[2] = new JMenuItem("Penjualan Cabang");
        this.penjualanMenuItems[3] = new JMenuItem("Penjualan Parsial");
        this.penjualanMenuItems[4] = new JMenuItem("Penjualan Excel II");
        this.penjualanMenuItems[5] = new JMenuItem("Penjualan Cabang New");
        this.penukaranPointBelanja[0] = new JMenuItem("Penukaran Point Belanja");
        this.reportPiutangMenuItem[0] = new JMenuItem("Report Pelunasan Piutang");
        this.reportPiutangMenuItem[1] = new JMenuItem("Report Piutang Customer");
        this.reportPiutangMenuItem[2] = new JMenuItem("Pelunasan Piutang Customer Excel");
        this.commitMenuItems = new JMenuItem[4];
        this.commitMenuItems[0] = new JMenuItem("Daftar Commit");
        this.commitMenuItems[1] = new JMenuItem("Sinkronisasi Master Barang");
        this.commitMenuItems[2] = new JMenuItem("Sinkronisasi Master Barang with HPP");
        this.commitMenuItems[3] = new JMenuItem("Sync All Data Transaksi");
        this.roleMenuItem = new JMenuItem("Role");
        this.pengaturanSoMenuItem = new JMenuItem("Pengaturan Stok Opnam");
        this.tabelMenu.setMnemonic('t');
        this.exitMenu.setMnemonic('e');
        this.mutasiMenu.setMnemonic('m');
        this.reportMenu.setMnemonic('r');
        this.pengaturanMenu.setMnemonic('p');
        this.cetakMenu.setMnemonic('k');
        this.commitMenu.setMnemonic('c');
        this.version = 29;
        this.loginOnlinePanel = new LoginOnlinePanel(this.version, this);
        this.cetakMenu.add(this.labelHargaMenuItem);
        for (JMenuItem jMenuItem : this.pengaturanMenuItems) {
            this.pengaturanMenu.add(jMenuItem);
        }
        this.pengaturanMenu.add(this.promoMenu);
        this.pengaturanMenu.add(this.roleMenuItem);
        this.pengaturanMenu.add(this.pengaturanSoMenuItem);
        for (JMenuItem jMenuItem : this.promoMenuItems) {
            this.promoMenu.add(jMenuItem);
        }
        this.promoMenu.add(this.promoVoucherMenu);
        this.promoVoucherMenu.add(this.promoVoucherMenuItem[0]);
        this.promoVoucherMenu.add(this.promoVoucherMenuItem[1]);
        for (JMenuItem jMenuItem : this.sinkronisasiMenuItems) {
            this.sinkronisasiMenu.add(jMenuItem);
        }
        for (JMenuItem jMenuItem : this.exitMenuItems) {
            this.exitMenu.add(jMenuItem);
        }
        for (JMenuItem jMenuItem : this.tabelMenuItems) {
            this.tabelMenu.add(jMenuItem);
        }
        for (JMenuItem jMenuItem : this.mutasiMenuItems) {
            this.mutasiMenu.add(jMenuItem);
        }
        for (JMenuItem jMenuItem : this.commitMenuItems) {
            this.commitMenu.add(jMenuItem);
        }
        for (JMenuItem jMenuItem : this.pembelianMenuItems) {
            this.mutasiMenuItems[0].add(jMenuItem);
        }
        for (JMenuItem jMenuItem : this.penjualanMenuItems) {
            this.mutasiMenuItems[1].add(jMenuItem);
        }
        for (JMenuItem jMenuItem : this.penukaranPointBelanja) {
            this.mutasiMenuItems[3].add(jMenuItem);
        }
        this.mutasiMenu.add(this.pengembalianBarangMenuItem);
        this.mutasiMenu.add(this.repackMenuItem);
        this.mutasiMenu.add(this.stokOpnamMenu);
        for (JMenuItem jMenuItem : this.stokOpnameMenuItems) {
            this.stokOpnamMenu.add(jMenuItem);
        }
        this.mutasiMenu.add(this.mutasiBarangMenuItem);
        for (JMenuItem jMenuItem : this.reportMenuItems) {
            this.reportMenu.add(jMenuItem);
        }
        this.reportMenu.add(this.reportPenjualanMenu);
        for (JMenuItem jMenuItem : this.reportPenjualanMenuItem) {
            this.reportPenjualanMenu.add(jMenuItem);
        }
        this.reportMenu.add(this.reportPromoMenu);
        for (JMenuItem jMenuItem : this.reportPromoMenuItem) {
            this.reportPromoMenu.add(jMenuItem);
        }
        this.reportMenu.add(this.reportPembelianMenu);
        this.reportMenu.add(this.reportHutangMenu);
        for (JMenuItem jMenuItem : this.reportHutangMenuItem) {
            this.reportHutangMenu.add(jMenuItem);
        }
        for (JMenuItem jMenuItem : this.reportPembelianMenuItem) {
            this.reportPembelianMenu.add(jMenuItem);
        }
        this.reportMenu.add(this.reportBarangMenu);
        for (JMenuItem jMenuItem : this.reportBarangMenuItem) {
            this.reportBarangMenu.add(jMenuItem);
        }
        this.reportMenu.add(this.reportKeuanganKasirMenu);
        for (JMenuItem jMenuItem : this.reportKeuanganKasirMenuItem) {
            this.reportKeuanganKasirMenu.add(jMenuItem);
        }
        this.reportMenu.add(this.reportStokMenu);
        for (JMenuItem jMenuItem : this.reportStokMenuItem) {
            this.reportStokMenu.add(jMenuItem);
        }
        this.reportMenu.add(this.reportPiutangMenu);
        for (JMenuItem jMenuItem : this.reportPiutangMenuItem) {
            this.reportPiutangMenu.add(jMenuItem);
        }
        this.reportMenu.add(this.reportMasterMenu);
        for (JMenuItem jMenuItem : this.reportMasterMenuItem) {
            this.reportMasterMenu.add(jMenuItem);
        }
        this.menuBar.add(this.sinkronisasiMenu);
        this.menuBar.add(this.tabelMenu);
        this.menuBar.add(this.mutasiMenu);
        this.menuBar.add(this.commitMenu);
        this.menuBar.add(this.reportMenu);
        this.menuBar.add(this.pengaturanMenu);
        this.menuBar.add(this.cetakMenu);
        this.menuBar.add(this.exitMenu);
        this.setJMenuBar(this.menuBar);
        this.menuBar.setVisible(false);
    }

    private void setFrame() {
        this.setBounds((this.screenWidth - 400) / 2, (this.screenHeight - 300) / 2, 400, 300);
        this.setVisible(true);
        this.setDefaultCloseOperation(3);
        this.setResizable(false);
        int op = this.getDefaultCloseOperation();
        this.setDefaultCloseOperation(0);
    }

    private void setAction() {
        this.commitMenuItems[0].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                DaftarCommitDialog daftarCommit = new DaftarCommitDialog((Frame)Controller.this, true);
                int w = daftarCommit.getWidth();
                int h = daftarCommit.getHeight();
                daftarCommit.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                daftarCommit.setVisible(true);
            }
        });
        this.commitMenuItems[1].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Controller.this.sinkron) {
                    Controller.this.sinkron = true;
                    String statusSinkron = Controller.this.konstantaService.getConstantValue("sinkronStok");
                    if (statusSinkron.equalsIgnoreCase("true")) {
                        Controller.this.canGoThrough = false;
                        ConfirmationDialog confirmationDialog = new ConfirmationDialog(null, true, "STATUS SINKRON OFF, AKTIFKAN SINKRONISASI");
                        confirmationDialog.addListener(new confirmDialogEventHandler());
                        int w = confirmationDialog.getWidth();
                        int h = confirmationDialog.getHeight();
                        confirmationDialog.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                        confirmationDialog.setVisible(true);
                        if (Controller.this.canGoThrough) {
                            Controller.this.konstantaService.updateConstant("sinkronStok", "false");
                            Controller.this.sinkron = false;
                        }
                        Controller.this.sinkron = false;
                    } else {
                        try {
                            int result = Controller.this.sinkronisasiBarangService.sinkronisasiBarang(1);
                            if (result == 1) {
                                JOptionPane.showMessageDialog(null, "Data Barang dan Barcode berhasil di sinkronisasi");
                            } else if (result == 2) {
                                JOptionPane.showMessageDialog(null, "Sebagian data gagal di sinkronisasi, lakukan sinkronisasi sekali lagi");
                            } else {
                                JOptionPane.showMessageDialog(null, "Gagal melakukan sinkronisasi");
                            }
                            Controller.this.sinkron = false;
                        }
                        catch (IOException ex) {
                            Controller.this.konstantaService.updateConstant("sinkronStok", "false");
                            Controller.this.sinkron = false;
                            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    Controller.this.sinkron = false;
                }
            }
        });
        this.commitMenuItems[2].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        this.commitMenuItems[3].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.syncService.doSyncAllUnsynced();
            }
        });
        this.repackMenuItem.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
                passwordInputDialog2.addPasswordInputDialogListener(new passwordInputRepackBarang());
                int w = passwordInputDialog2.getWidth();
                int h = passwordInputDialog2.getHeight();
                passwordInputDialog2.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                passwordInputDialog2.setJudulText("Input Password Untuk Repack Barang");
                passwordInputDialog2.setVisible(true);
            }
        });
        this.reportBarangMenuItem[0].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                ReportDibawahHargaBeliDialog dibawahHargaBeliDialog = new ReportDibawahHargaBeliDialog((Frame)Controller.this, true);
                dibawahHargaBeliDialog.addListener(new dibawahHargaBeliEventHandler());
                int w = dibawahHargaBeliDialog.getWidth();
                int h = dibawahHargaBeliDialog.getHeight();
                dibawahHargaBeliDialog.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                dibawahHargaBeliDialog.setVisible(true);
            }
        });
        this.reportBarangMenuItem[1].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                ReportHistoryHargaDialog reportHistoryHargaDialog = new ReportHistoryHargaDialog((Frame)Controller.this, true);
                reportHistoryHargaDialog.addListener(new reportHistoryHargaDialogEventHandler());
                int w = reportHistoryHargaDialog.getWidth();
                int h = reportHistoryHargaDialog.getHeight();
                reportHistoryHargaDialog.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                reportHistoryHargaDialog.setVisible(true);
            }
        });
        this.reportBarangMenuItem[2].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Report Planogram");
                ReportPlanogram reportPlanogram = new ReportPlanogram();
                reportPlanogram.addListener(new defaultPanelEventHandler());
                Controller.this.setContentPane(reportPlanogram);
            }
        });
        this.pengaturanMenuItems[0].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                ChangePassDialog changePassDialog = new ChangePassDialog((Frame)Controller.this, true);
                int w = changePassDialog.getWidth();
                int h = changePassDialog.getHeight();
                changePassDialog.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                changePassDialog.setVisible(true);
            }
        });
        this.pengaturanMenuItems[1].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                StoreNameDialog storeNameDialog = new StoreNameDialog((Frame)Controller.this, true);
                int w = storeNameDialog.getWidth();
                int h = storeNameDialog.getHeight();
                storeNameDialog.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                storeNameDialog.setVisible(true);
            }
        });
        this.pengaturanMenuItems[2].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Setting PoinBelanja");
                PoinBelanjaSetting poinBelanjaSetting = new PoinBelanjaSetting();
                Controller.this.setContentPane(poinBelanjaSetting);
                poinBelanjaSetting.addListener(new defaultPanelEventHandler());
            }
        });
        this.reportPiutangMenuItem[0].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                ReportPelunasantPiutangDialog reportPiutangDialog = new ReportPelunasantPiutangDialog((Frame)Controller.this, true, "PIUTANG");
                reportPiutangDialog.addListener(new reportPiutangEventHandler());
                int w = reportPiutangDialog.getWidth();
                int h = reportPiutangDialog.getHeight();
                reportPiutangDialog.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                reportPiutangDialog.setVisible(true);
            }
        });
        this.reportMasterMenuItem[3].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Report Master Karyawan");
                ReportMasterKaryawanPanel reportMasterKaryawanPanel = new ReportMasterKaryawanPanel();
                Controller.this.setContentPane(reportMasterKaryawanPanel);
                reportMasterKaryawanPanel.addListener(new defaultPanelEventHandler());
            }
        });
        this.reportMasterMenuItem[2].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Report Master Supplier");
                ReportMasterSupplierPanel reportMasterSupplierPanel = new ReportMasterSupplierPanel();
                Controller.this.setContentPane(reportMasterSupplierPanel);
                reportMasterSupplierPanel.addListener(new defaultPanelEventHandler());
            }
        });
        this.reportMasterMenuItem[1].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Report Master Customer");
                ReportMasterCustomerPanel reportMasterCustomerPanel = new ReportMasterCustomerPanel();
                Controller.this.setContentPane(reportMasterCustomerPanel);
                reportMasterCustomerPanel.addListener(new defaultPanelEventHandler());
            }
        });
        this.reportMasterMenuItem[0].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                int size = Controller.this.stokOpnamMandiriService.getStokOpnamMandiriUnSubmit().size();
                if (size == 0) {
                    ReportMasterBarangDialog reportMasterBarangDialog = new ReportMasterBarangDialog((Frame)Controller.this, true);
                    reportMasterBarangDialog.addListener(new reportMasterBarangDialogEventHandler());
                    int w = reportMasterBarangDialog.getWidth();
                    int h = reportMasterBarangDialog.getHeight();
                    reportMasterBarangDialog.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                    reportMasterBarangDialog.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Stok Opnam Mandiri Sedang Berlangsung");
                }
            }
        });
        this.reportStokMenuItem[0].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                int size = Controller.this.stokOpnamMandiriService.getStokOpnamMandiriUnSubmit().size();
                if (size == 0) {
                    ReportJenisRakDialog reportStokTerakhirDialog = new ReportJenisRakDialog((Frame)Controller.this, true, "Report Stok Terakhir");
                    reportStokTerakhirDialog.addListener(new reportStokTerakhirDialogEventHandler());
                    int w = reportStokTerakhirDialog.getWidth();
                    int h = reportStokTerakhirDialog.getHeight();
                    reportStokTerakhirDialog.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                    reportStokTerakhirDialog.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Stok Opnam Mandiri Sedang Berlangsung");
                }
            }
        });
        this.reportStokMenuItem[1].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Report Stok Opnam");
                ReportStokOpnamPanel reportStokOpnamPanel = new ReportStokOpnamPanel();
                Controller.this.setContentPane(reportStokOpnamPanel);
                reportStokOpnamPanel.addListener(new defaultPanelEventHandler());
            }
        });
        this.reportStokMenuItem[2].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Report Stok Opnam Detail");
                ReportStokOpnamDetail reportStokOpnamDetail = new ReportStokOpnamDetail();
                Controller.this.setContentPane(reportStokOpnamDetail);
                reportStokOpnamDetail.addListener(new defaultPanelEventHandler());
            }
        });
        this.reportStokMenuItem[3].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Report Stok Transaction");
                ReportStokTransaction reportStokOpnamPanel = new ReportStokTransaction();
                Controller.this.setContentPane(reportStokOpnamPanel);
                reportStokOpnamPanel.addListener(new defaultPanelEventHandler());
            }
        });
        this.reportStokMenuItem[4].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Report Selisih Input Stok");
                ReportSelisihInputStok reportSelisihInputStok = new ReportSelisihInputStok();
                Controller.this.setContentPane(reportSelisihInputStok);
                reportSelisihInputStok.addListener(new defaultPanelEventHandler());
            }
        });
        this.reportStokMenuItem[5].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Report Stok Opnam Mandiri Log");
                ReportStokOpnamMandiriSementara reportStokOpnamMandiriSementara = new ReportStokOpnamMandiriSementara();
                Controller.this.setContentPane(reportStokOpnamMandiriSementara);
                reportStokOpnamMandiriSementara.addListener(new defaultPanelEventHandler());
            }
        });
        this.reportKeuanganKasirMenuItem[1].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
                passwordInputDialog2.addPasswordInputDialogListener(new passwordInputKeuanganTiapShift());
                int w = passwordInputDialog2.getWidth();
                int h = passwordInputDialog2.getHeight();
                passwordInputDialog2.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                passwordInputDialog2.setJudulText("Input Password Untuk Membuka Laporan");
                passwordInputDialog2.setVisible(true);
            }
        });
        this.reportKeuanganKasirMenuItem[0].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
                passwordInputDialog2.addPasswordInputDialogListener(new passwordInputKeuanganGlobal());
                int w = passwordInputDialog2.getWidth();
                int h = passwordInputDialog2.getHeight();
                passwordInputDialog2.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                passwordInputDialog2.setJudulText("Input Password Untuk Membuka Laporan");
                passwordInputDialog2.setVisible(true);
            }
        });
        this.reportPenjualanMenuItem[0].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
                passwordInputDialog2.addPasswordInputDialogListener(new passwordInputSummaryPenjualan());
                int w = passwordInputDialog2.getWidth();
                int h = passwordInputDialog2.getHeight();
                passwordInputDialog2.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                passwordInputDialog2.setJudulText("Input Password Untuk Membuka Laporan");
                passwordInputDialog2.setVisible(true);
            }
        });
        this.reportPenjualanMenuItem[1].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
                passwordInputDialog2.addPasswordInputDialogListener(new passwordInputDetailPenjualan());
                int w = passwordInputDialog2.getWidth();
                int h = passwordInputDialog2.getHeight();
                passwordInputDialog2.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                passwordInputDialog2.setJudulText("Input Password Untuk Membuka Laporan");
                passwordInputDialog2.setVisible(true);
            }
        });
        this.reportPenjualanMenuItem[2].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
                passwordInputDialog2.addPasswordInputDialogListener(new passwordInputRekapPenjualan());
                int w = passwordInputDialog2.getWidth();
                int h = passwordInputDialog2.getHeight();
                passwordInputDialog2.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                passwordInputDialog2.setJudulText("Input Password Untuk Membuka Laporan");
                passwordInputDialog2.setVisible(true);
            }
        });
        this.reportPenjualanMenuItem[3].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Report Poin Penjualan");
                ReportPoinPenjualanCustomer rppc = new ReportPoinPenjualanCustomer();
                Controller.this.setContentPane(rppc);
                rppc.addListener(new defaultPanelEventHandler());
            }
        });
        this.reportPenjualanMenuItem[4].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Report Best Seller Item");
                ReportPenjualanBarang2 rpb = new ReportPenjualanBarang2();
                Controller.this.setContentPane(rpb);
                rpb.addListener(new defaultPanelEventHandler());
            }
        });
        this.reportPenjualanMenuItem[5].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
                passwordInputDialog2.addPasswordInputDialogListener(new passwordInputPenjualanSales());
                int w = passwordInputDialog2.getWidth();
                int h = passwordInputDialog2.getHeight();
                passwordInputDialog2.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                passwordInputDialog2.setJudulText("Input Password Untuk Membuka Laporan");
                passwordInputDialog2.setVisible(true);
            }
        });
        this.reportPenjualanMenuItem[6].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Rekap Penjualan Customer");
                ReportPenjualanCustomer rps = new ReportPenjualanCustomer();
                Controller.this.setContentPane(rps);
                rps.addListener(new defaultPanelEventHandler());
            }
        });
        this.reportPiutangMenuItem[1].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Report Piutang Customer");
                ReportPiutangCustomer rpc = new ReportPiutangCustomer();
                Controller.this.setContentPane(rpc);
                rpc.addListener(new defaultPanelEventHandler());
            }
        });
        this.reportPiutangMenuItem[2].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Pelunasan Piutang Customer Excel");
                ReportPiutangCustomerExcel rpc = new ReportPiutangCustomerExcel();
                Controller.this.setContentPane(rpc);
                rpc.addListener(new defaultPanelEventHandler());
            }
        });
        this.reportPromoMenuItem[0].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Report Promo Hemat");
                ReportPromoHemat rpb = new ReportPromoHemat();
                Controller.this.setContentPane(rpb);
                rpb.addListener(new defaultPanelEventHandler());
            }
        });
        this.reportPromoMenuItem[1].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Report Promo Tebus Murah Barang");
                ReportPromoTebus rpt = new ReportPromoTebus();
                Controller.this.setContentPane(rpt);
                rpt.addListener(new defaultPanelEventHandler());
            }
        });
        this.reportPromoMenuItem[2].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Report Promo Tebus Murah Hemat");
                ReportPromoTebusMurah rpt = new ReportPromoTebusMurah();
                Controller.this.setContentPane(rpt);
                rpt.addListener(new defaultPanelEventHandler());
            }
        });
        this.reportPromoMenuItem[3].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Report Performa Promo");
                ReportPerformaPromo rpo = new ReportPerformaPromo();
                Controller.this.setContentPane(rpo);
                rpo.addListener(new defaultPanelEventHandler());
            }
        });
        this.reportPromoMenuItem[4].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Report Performa Promo");
                ReportPerformaAllPromo rpo = new ReportPerformaAllPromo();
                Controller.this.setContentPane(rpo);
                rpo.addListener(new defaultPanelEventHandler());
            }
        });
        this.reportMenuItems[0].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
                passwordInputDialog2.addPasswordInputDialogListener(new passwordInputReportKeuntungan());
                int w = passwordInputDialog2.getWidth();
                int h = passwordInputDialog2.getHeight();
                passwordInputDialog2.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                passwordInputDialog2.setJudulText("Input Password Untuk Merubah Harga");
                passwordInputDialog2.setVisible(true);
            }
        });
        this.reportMenuItems[1].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Report Mutasi Barang");
                ReportMutasiPanel rpb = new ReportMutasiPanel();
                Controller.this.setContentPane(rpb);
                rpb.addListener(new defaultPanelEventHandler());
            }
        });
        this.reportMenuItems[2].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Report Penukaran Poin");
                ReportPenukaranPoin rpn = new ReportPenukaranPoin();
                Controller.this.setContentPane(rpn);
                rpn.addListener(new defaultPanelEventHandler());
            }
        });
        this.reportMenuItems[3].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Report Pengembalian Barang");
                ReportPengembalianBarang rpn = new ReportPengembalianBarang();
                Controller.this.setContentPane(rpn);
                rpn.addListener(new defaultPanelEventHandler());
            }
        });
        this.reportMenuItems[4].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Report Penukaran Voucher");
                ReportPenukaranVoucherPanel rpn = new ReportPenukaranVoucherPanel();
                Controller.this.setContentPane(rpn);
                rpn.addListener(new defaultPanelEventHandler());
            }
        });
        this.reportMenuItems[5].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Report Performa Outlet");
                ReportPerformaOutlet rpo = new ReportPerformaOutlet();
                Controller.this.setContentPane(rpo);
                rpo.addListener(new defaultPanelEventHandler());
            }
        });
        this.reportMenuItems[6].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Report Repack Barang");
                ReportRepackPanel rpo = new ReportRepackPanel();
                Controller.this.setContentPane(rpo);
                rpo.addListener(new defaultPanelEventHandler());
            }
        });
        this.reportMenuItems[7].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Report Reprint Invoice");
                ReportReprinInvoiceLog rri = new ReportReprinInvoiceLog();
                Controller.this.setContentPane(rri);
                rri.addListener(new defaultPanelEventHandler());
            }
        });
        this.reportPembelianMenuItem[0].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.poService.insertPembelianFromPO();
                ReportPembelianDialog reportPembelianDialog = new ReportPembelianDialog(null, true);
                reportPembelianDialog.addListener(new reportPembelianDialogEventHandler());
                int w = reportPembelianDialog.getWidth();
                int h = reportPembelianDialog.getHeight();
                reportPembelianDialog.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                reportPembelianDialog.setVisible(true);
            }
        });
        this.reportPembelianMenuItem[1].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                ReportPembelianDetailDialog reportPembelianDetailDialog = new ReportPembelianDetailDialog(null, true);
                reportPembelianDetailDialog.addListener(new reportPembelianDetailDialogEventHandler());
                int w = reportPembelianDetailDialog.getWidth();
                int h = reportPembelianDetailDialog.getHeight();
                reportPembelianDetailDialog.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                reportPembelianDetailDialog.setVisible(true);
            }
        });
        this.reportHutangMenuItem[0].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.poService.insertPembelianFromPO();
                ReportHutangDialog reportHutangDialog = new ReportHutangDialog(null, true);
                reportHutangDialog.addListener(new reportHutangDialogEventHandler());
                int w = reportHutangDialog.getWidth();
                int h = reportHutangDialog.getHeight();
                reportHutangDialog.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                reportHutangDialog.setVisible(true);
            }
        });
        this.reportHutangMenuItem[1].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                ReportPelunasantHutangDialog reportPelunasanHutangDialog = new ReportPelunasantHutangDialog(null, true);
                reportPelunasanHutangDialog.addListener(new reportPelunasanHutangDialogEventHandler());
                int w = reportPelunasanHutangDialog.getWidth();
                int h = reportPelunasanHutangDialog.getHeight();
                reportPelunasanHutangDialog.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                reportPelunasanHutangDialog.setVisible(true);
            }
        });
        this.exitMenuItems[0].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Login");
                Controller.this.loginOnlinePanel.setDefault();
                Controller.this.setContentPane(Controller.this.loginOnlinePanel);
                Controller.this.setFrame();
                Controller.this.loginOnlinePanel.setDefaultFocus();
            }
        });
        this.exitMenuItems[1].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        this.sinkronisasiMenuItems[0].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.syncService.doUpsertDataShiftKasirDariOnline();
                Controller.this.syncService.doUpsertDataPenjualanDariOnline();
                Controller.this.syncService.doUpsertDataPembelianDariOnline();
                Controller.this.syncService.doUpsertDataPelunasanHutangDariOnline();
                Controller.this.syncService.doUpsertDataPelunasanPiutangDariOnline();
                Controller.this.syncService.doUpsertDataPengembalianBarangDariOnline();
                Controller.this.syncService.doUpsertDataPenukaranPointDariOnline();
                Controller.this.syncService.doUpsertDataStokOpnamDariOnline();
                Controller.this.syncService.doUpsertDataVoucherDariOnline();
            }
        });
        this.sinkronisasiMenuItems[1].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.poService.insertPembelianFromPO();
            }
        });
        this.sinkronisasiMenuItems[2].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                int result = Controller.this.salesService.syncOnlineSalesToOffline();
                System.out.println(result);
            }
        });
        this.sinkronisasiMenuItems[3].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                int result = Controller.this.syncPromoService.syncAllPromo();
                System.out.println(result);
            }
        });
        this.sinkronisasiMenuItems[4].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.paketService.doSyncPaketOnlineToOffline();
            }
        });
        this.penukaranPointBelanja[0].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
                passwordInputDialog2.addPasswordInputDialogListener(new passwordInputPenukaranPoin());
                int w = passwordInputDialog2.getWidth();
                int h = passwordInputDialog2.getHeight();
                passwordInputDialog2.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                passwordInputDialog2.setJudulText("Input Password Untuk Membuka Penukaran Poin");
                passwordInputDialog2.setVisible(true);
            }
        });
        this.penjualanMenuItems[0].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                ShiftKasir shiftKasir = new ShiftKasirService().getOpenedShiftKasir(Controller.this.helper.getKasir());
                if (shiftKasir == null) {
                    String pattern = "yyyy-MM-dd";
                    SimpleDateFormat format = new SimpleDateFormat(pattern);
                    List<ShiftKasir> shiftKasirs = Controller.this.shiftKasirService.getShiftsByKasir(Controller.this.helper.getKaryawan().getId(), format.format(new Date()));
                    if (shiftKasirs.size() > 0) {
                        PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
                        passwordInputDialog2.addPasswordInputDialogListener(new passwordBukaKasir());
                        int w = passwordInputDialog2.getWidth();
                        int h = passwordInputDialog2.getHeight();
                        passwordInputDialog2.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                        passwordInputDialog2.setJudulText("Kasir ID " + Controller.this.helper.getKaryawan().getId() + " Sudah Dipakai Hari ini, masukkan pass ACCC untuk melanjutkan");
                        passwordInputDialog2.setVisible(true);
                    } else {
                        NewKasir newKasir = new NewKasir(null, true);
                        int w = newKasir.getWidth();
                        int h = newKasir.getHeight();
                        newKasir.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                        newKasir.setListener(new newKasirEventHandler());
                        newKasir.setVisible(true);
                    }
                } else {
                    OpenedKasir openedKasir = new OpenedKasir(null, true);
                    int w = openedKasir.getWidth();
                    int h = openedKasir.getHeight();
                    openedKasir.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                    openedKasir.setOpenedKasirListener(new openedKasirEventHandler());
                    openedKasir.setVisible(true);
                }
            }
        });
        this.penjualanMenuItems[1].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Penjualan II");
                Penjualan2Panel transaksiPanel = new Penjualan2Panel();
                transaksiPanel.addListener(new defaultPanelEventHandler());
                Controller.this.setContentPane(transaksiPanel);
                transaksiPanel.setDefaultFocus();
            }
        });
        this.penjualanMenuItems[3].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Penjualan Parsial");
                PenjualanParsialPanel transaksiPanel = new PenjualanParsialPanel();
                transaksiPanel.addListener(new defaultPanelEventHandler());
                Controller.this.setContentPane(transaksiPanel);
                transaksiPanel.setDefaultFocus();
            }
        });
        this.penjualanMenuItems[5].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                ShiftKasir shiftKasir = new ShiftKasirService().getOpenedShiftKasir(Controller.this.helper.getKasir());
                if (shiftKasir == null) {
                    NewKasir newKasir = new NewKasir(null, true);
                    int w = newKasir.getWidth();
                    int h = newKasir.getHeight();
                    newKasir.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                    newKasir.setListener(new newKasirCabangEventHandler2());
                    newKasir.setVisible(true);
                } else {
                    OpenedKasir openedKasir = new OpenedKasir(null, true);
                    int w = openedKasir.getWidth();
                    int h = openedKasir.getHeight();
                    openedKasir.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                    openedKasir.setOpenedKasirListener(new openedKasirCabangEventHandler2());
                    openedKasir.setVisible(true);
                }
            }
        });
        this.penjualanMenuItems[4].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                ShiftKasir shiftKasir = new ShiftKasirService().getOpenedShiftKasir(Controller.this.helper.getKasir());
                if (shiftKasir == null) {
                    NewKasir newKasir = new NewKasir(null, true);
                    int w = newKasir.getWidth();
                    int h = newKasir.getHeight();
                    newKasir.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                    newKasir.setListener(new newKasirExcelEventHandler2());
                    newKasir.setVisible(true);
                } else {
                    OpenedKasir openedKasir = new OpenedKasir(null, true);
                    int w = openedKasir.getWidth();
                    int h = openedKasir.getHeight();
                    openedKasir.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                    openedKasir.setOpenedKasirListener(new openedKasirExcelEventHandler2());
                    openedKasir.setVisible(true);
                }
            }
        });
        this.penjualanMenuItems[2].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                ShiftKasir shiftKasir = new ShiftKasirService().getOpenedShiftKasir(Controller.this.helper.getKasir());
                if (shiftKasir == null) {
                    NewKasir newKasir = new NewKasir(null, true);
                    int w = newKasir.getWidth();
                    int h = newKasir.getHeight();
                    newKasir.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                    newKasir.setListener(new newKasirCabangEventHandler());
                    newKasir.setVisible(true);
                } else {
                    OpenedKasir openedKasir = new OpenedKasir(null, true);
                    int w = openedKasir.getWidth();
                    int h = openedKasir.getHeight();
                    openedKasir.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                    openedKasir.setOpenedKasirListener(new openedKasirCabangEventHandler());
                    openedKasir.setVisible(true);
                }
            }
        });
        this.pembelianMenuItems[0].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Pembelian");
                PembelianPanel pembelianPanel = new PembelianPanel("F5");
                Controller.this.setContentPane(pembelianPanel);
                pembelianPanel.setDefault();
                pembelianPanel.addListener(new pembelianPanelEventHandler());
            }
        });
        this.pembelianMenuItems[1].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Pembelian");
                PembelianPanel2 pembelian2Panel = new PembelianPanel2();
                Controller.this.setContentPane(pembelian2Panel);
                pembelian2Panel.setDefault();
                pembelian2Panel.addListener(new defaultPanelEventHandler());
            }
        });
        this.mutasiBarangMenuItems[0].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Mutasi Barang");
                MutasiBarangPanel mutasiBarangPanel = new MutasiBarangPanel();
                Controller.this.setContentPane(mutasiBarangPanel);
                mutasiBarangPanel.escapeClick();
                mutasiBarangPanel.addListener(new defaultPanelEventHandler());
            }
        });
        this.tabelMenuItems[6].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                ParentMasterUpdate paretMasterUpdate = new ParentMasterUpdate((Frame)Controller.this, true, Controller.this.tipeProgram);
                int w = paretMasterUpdate.getWidth();
                int h = paretMasterUpdate.getHeight();
                paretMasterUpdate.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                paretMasterUpdate.setVisible(true);
            }
        });
        this.tabelMenuItems[0].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                boolean o2oConstantStatus = false;
                try {
                    o2oConstantStatus = Controller.this.cos.getConstantOnline();
                }
                catch (IOException ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println(System.currentTimeMillis());
                if (o2oConstantStatus) {
                    int result = 1;
                    if (result == 1) {
                        TabelBarangMaster tabelBarang = new TabelBarangMaster((Frame)Controller.this, true, Controller.this.tipeProgram);
                        int w = tabelBarang.getWidth();
                        int h = tabelBarang.getHeight();
                        tabelBarang.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                        tabelBarang.setVisible(true);
                    } else if (result == 2) {
                        JOptionPane.showMessageDialog(null, "Sebagian data gagal di sinkronisasi, lakukan sinkronisasi sekali lagi");
                    } else {
                        JOptionPane.showMessageDialog(null, "Gagal melakukan sinkronisasi");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Gagal Mengambil Data Constant dari Online");
                }
            }
        });
        this.tabelMenuItems[1].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                JenisBarangMaster jenisBarang = new JenisBarangMaster((Frame)Controller.this, true);
                int w = jenisBarang.getWidth();
                int h = jenisBarang.getHeight();
                jenisBarang.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                jenisBarang.setVisible(true);
            }
        });
        this.tabelMenuItems[2].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                CustomerMaster customer = new CustomerMaster((Frame)Controller.this, true, Controller.this.tipeOutlet);
                int w = customer.getWidth();
                int h = customer.getHeight();
                customer.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                customer.setVisible(true);
            }
        });
        this.tabelMenuItems[3].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.supplierService.syncSupplierMaster();
                SupplierMaster supplier = new SupplierMaster((Frame)Controller.this, true);
                int w = supplier.getWidth();
                int h = supplier.getHeight();
                supplier.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                supplier.setVisible(true);
            }
        });
        this.tabelMenuItems[4].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                KaryawanMaster karyawan = new KaryawanMaster((Frame)Controller.this, true);
                int w = karyawan.getWidth();
                int h = karyawan.getHeight();
                karyawan.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                karyawan.setVisible(true);
            }
        });
        this.tabelMenuItems[5].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                int result = Controller.this.salesService.syncOnlineSalesToOffline();
                if (result == 1) {
                    SalesMaster sales = new SalesMaster((Frame)Controller.this, true);
                    int w = sales.getWidth();
                    int h = sales.getHeight();
                    sales.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                    sales.setVisible(true);
                }
            }
        });
        this.updateMenuItems[0].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Update Barang Excel");
                UpdateBarangPanel updateBarangPanel = new UpdateBarangPanel();
                updateBarangPanel.addListener(new defaultPanelEventHandler());
                Controller.this.setContentPane(updateBarangPanel);
            }
        });
        this.updateMenuItems[1].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Update Customer Excel");
                UpdateCustomerPanel updateCustomerPanel = new UpdateCustomerPanel();
                updateCustomerPanel.addListener(new defaultPanelEventHandler());
                Controller.this.setContentPane(updateCustomerPanel);
            }
        });
        this.updateMenuItems[2].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Create Customer Excel");
                CreateCustomerPanel createCustomerPanel = new CreateCustomerPanel();
                createCustomerPanel.addListener(new defaultPanelEventHandler());
                Controller.this.setContentPane(createCustomerPanel);
            }
        });
        this.stokOpnameMenuItems[0].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Stop Opname Manual");
                StokOpnamPanelNew sPanel = new StokOpnamPanelNew();
                Controller.this.setContentPane(sPanel);
                sPanel.addListener(new defaultPanelEventHandler());
            }
        });
        this.stokOpnameMenuItems[1].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Stop Opname Parsial");
                StokOpnamParsial sPanel = null;
                try {
                    sPanel = new StokOpnamParsial();
                }
                catch (Exception ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
                Controller.this.setContentPane(sPanel);
                sPanel.addListener(new defaultPanelEventHandler());
            }
        });
        this.stokOpnameMenuItems[2].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                boolean canGo = Controller.this.moService.checkMigrasiUnreceived();
                if (canGo) {
                    Controller.this.menuBar.setVisible(false);
                    Controller.this.setTitle("Stop Opname Mandiri");
                    StokOpnamMandiriPanel sPanel = null;
                    try {
                        sPanel = new StokOpnamMandiriPanel();
                    }
                    catch (Exception ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Controller.this.setContentPane(sPanel);
                    sPanel.addListener(new defaultPanelEventHandler());
                } else {
                    PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Ada migrasi yang belum di terima, masukkan pass IC untuk melanjutkan");
                    passwordInputDialog2.addPasswordInputDialogListener(new passwordInputSOM());
                    int w = passwordInputDialog2.getWidth();
                    int h = passwordInputDialog2.getHeight();
                    passwordInputDialog2.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                    passwordInputDialog2.setJudulText("Input Password Untuk Membuka Laporan");
                    passwordInputDialog2.setVisible(true);
                }
            }
        });
        this.mutasiBarangMenuItem.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Mutasi Stok Barang");
                MutasiBarangPanel mPanel = new MutasiBarangPanel();
                Controller.this.setContentPane(mPanel);
                mPanel.addListener(new defaultPanelEventHandler());
                mPanel.setFocus();
            }
        });
        this.labelHargaMenuItem.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Cetak Label Harga");
                CetakHargaPanel cetakHargaPanel = new CetakHargaPanel();
                cetakHargaPanel.addListener(new defaultPanelEventHandler());
                Controller.this.setContentPane(cetakHargaPanel);
            }
        });
        this.pengembalianBarangMenuItem.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
                passwordInputDialog2.addPasswordInputDialogListener(new passwordInputPengembalianBarang());
                int w = passwordInputDialog2.getWidth();
                int h = passwordInputDialog2.getHeight();
                passwordInputDialog2.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                passwordInputDialog2.setJudulText("Input Password Untuk Melakukan Retur");
                passwordInputDialog2.setVisible(true);
            }
        });
        this.promoMenuItems[0].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Promo Hemat");
                PromoHematPanel promoHematPanel = new PromoHematPanel();
                Controller.this.setContentPane(promoHematPanel);
                promoHematPanel.addListener(new defaultPanelEventHandler());
            }
        });
        this.promoMenuItems[1].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Promo Tebus Murah");
                PromoTebusPanel promoTebusPanel = new PromoTebusPanel();
                Controller.this.setContentPane(promoTebusPanel);
                promoTebusPanel.addListener(new defaultPanelEventHandler());
            }
        });
        this.promoMenuItems[2].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Promo Diskon By Jenis");
                PromoDiskonByJenisPanel ppup = new PromoDiskonByJenisPanel();
                Controller.this.setContentPane(ppup);
                ppup.addListener(new defaultPanelEventHandler());
            }
        });
        this.promoMenuItems[3].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Promo Hemat");
                PromoKhususPelanggan promoHematPanel = new PromoKhususPelanggan();
                Controller.this.setContentPane(promoHematPanel);
                promoHematPanel.addListener(new defaultPanelEventHandler());
            }
        });
        this.promoMenuItems[4].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Promo Tebus Murah");
                PromoTebusMurahPanel promoTebusMurahPanel = new PromoTebusMurahPanel();
                Controller.this.setContentPane(promoTebusMurahPanel);
                promoTebusMurahPanel.addListener(new defaultPanelEventHandler());
            }
        });
        this.promoVoucherMenuItem[0].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                PromoVoucherBelanja pvbd = new PromoVoucherBelanja((Frame)Controller.this, true);
                int w = pvbd.getWidth();
                int h = pvbd.getHeight();
                pvbd.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                pvbd.setVisible(true);
            }
        });
        this.promoVoucherMenuItem[1].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Promo Voucher Belanja");
                DaftarPromoVoucher dpv = new DaftarPromoVoucher();
                Controller.this.setContentPane(dpv);
                dpv.addListener(new defaultPanelEventHandler());
            }
        });
        this.pengaturanSoMenuItem.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                PengaturanStokOpnam pvbd = new PengaturanStokOpnam((Frame)Controller.this, true);
                int w = pvbd.getWidth();
                int h = pvbd.getHeight();
                pvbd.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                pvbd.setVisible(true);
            }
        });
        this.roleMenuItem.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Set Role");
                RolePanel rPanel = null;
                try {
                    rPanel = new RolePanel();
                }
                catch (Exception ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
                Controller.this.setContentPane(rPanel);
                rPanel.addListener(new defaultPanelEventHandler());
            }
        });
    }

    public void setTipeOutlet(String tipeOutlet) {
        this.tipeOutlet = tipeOutlet;
    }

    public void setKeteranganSoftware(String keteranganSoftware) {
        this.keteranganSoftware = keteranganSoftware;
    }

    private class passwordInputKoreksiStok
    implements PasswordInputListener {
        private passwordInputKoreksiStok() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR")) {
                KoreksiStokDialog koreksiStokDialog = new KoreksiStokDialog((Frame)Controller.this, true);
                int w = koreksiStokDialog.getWidth();
                int h = koreksiStokDialog.getHeight();
                koreksiStokDialog.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                koreksiStokDialog.setVisible(true);
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class passwordInputRepackBarang
    implements PasswordInputListener {
        private passwordInputRepackBarang() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR") || karyawan.getRole().equalsIgnoreCase("MANAGER") || karyawan.getRole().equalsIgnoreCase("ADMIN")) {
                RepackingDialog repackingDialog = new RepackingDialog((Frame)Controller.this, true);
                int w = repackingDialog.getWidth();
                int h = repackingDialog.getHeight();
                repackingDialog.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                repackingDialog.setVisible(true);
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class passwordInputPengembalianBarang
    implements PasswordInputListener {
        private passwordInputPengembalianBarang() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR") || karyawan.getRole().equalsIgnoreCase("ACCOUNTING") || karyawan.getRole().equalsIgnoreCase("ADMIN")) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Pengembalian Barang");
                PengembalianBarangPanel pengembalianBarang = new PengembalianBarangPanel();
                pengembalianBarang.addTransaksiListener(new penjualanPanelEventHandler());
                Controller.this.setContentPane(pengembalianBarang);
                pengembalianBarang.setDefaultFocus();
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class passwordInputReportKeuntungan
    implements PasswordInputListener {
        private passwordInputReportKeuntungan() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR") || karyawan.getRole().equalsIgnoreCase("ACCOUNTING")) {
                ReportKeuntunganDialog reportKeuntunganDialog = new ReportKeuntunganDialog(null, true);
                reportKeuntunganDialog.addListener(new reportKeuntunganDialogEventHandler());
                int w = reportKeuntunganDialog.getWidth();
                int h = reportKeuntunganDialog.getHeight();
                reportKeuntunganDialog.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                reportKeuntunganDialog.setVisible(true);
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class passwordInputKeuanganTiapShift
    implements PasswordInputListener {
        private passwordInputKeuanganTiapShift() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR") || karyawan.getRole().equalsIgnoreCase("ACCOUNTING") || karyawan.getRole().equalsIgnoreCase("ADMIN")) {
                ReportKeuanganShiftPanel keuanganShiftPanel = new ReportKeuanganShiftPanel();
                keuanganShiftPanel.addListener(new defaultPanelEventHandler());
                Controller.this.setContentPane(keuanganShiftPanel);
                Controller.this.setTitle("Report Keuangan Tiap Shift");
                Controller.this.menuBar.setVisible(false);
                Controller.this.menuBar.requestFocus();
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class passwordInputKeuanganGlobal
    implements PasswordInputListener {
        private passwordInputKeuanganGlobal() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR") || karyawan.getRole().equalsIgnoreCase("ACCOUNTING") || karyawan.getRole().equalsIgnoreCase("ADMIN")) {
                Controller.this.poService.insertPembelianFromPO();
                ReportKeuanganGlobal reportKeuanganGlobal = new ReportKeuanganGlobal();
                reportKeuanganGlobal.addListener(new defaultPanelEventHandler());
                Controller.this.setContentPane(reportKeuanganGlobal);
                Controller.this.setTitle("Report Keuangan Global");
                Controller.this.menuBar.setVisible(false);
                Controller.this.menuBar.requestFocus();
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class passwordInputPenjualanTiapKasir
    implements PasswordInputListener {
        private passwordInputPenjualanTiapKasir() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR") || karyawan.getRole().equalsIgnoreCase("MANAGER") || karyawan.getRole().equalsIgnoreCase("ADMIN")) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Report Keuangan Kasir");
                ReportKeuanganKasirPanel reportKeuanganKasirPanel = new ReportKeuanganKasirPanel();
                Controller.this.setContentPane(reportKeuanganKasirPanel);
                reportKeuanganKasirPanel.setDefault();
                reportKeuanganKasirPanel.addListener(new defaultPanelEventHandler());
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class passwordInputRekapPenjualan
    implements PasswordInputListener {
        private passwordInputRekapPenjualan() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR") || karyawan.getRole().equalsIgnoreCase("ACCOUNTING") || karyawan.getRole().equalsIgnoreCase("ADMIN")) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Report Rekap Penjualan");
                ReportRekapPenjualan reportRekapPenjualan = new ReportRekapPenjualan();
                Controller.this.setContentPane(reportRekapPenjualan);
                reportRekapPenjualan.addListener(new defaultPanelEventHandler());
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class passwordInputPenjualanSales
    implements PasswordInputListener {
        private passwordInputPenjualanSales() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR") || karyawan.getRole().equalsIgnoreCase("ACCOUNTING") || karyawan.getRole().equalsIgnoreCase("ADMIN")) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Report Penjualan Sales");
                ReportPenjualanSales rps = new ReportPenjualanSales();
                Controller.this.setContentPane(rps);
                rps.addListener(new defaultPanelEventHandler());
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class passwordInputDetailPenjualan
    implements PasswordInputListener {
        private passwordInputDetailPenjualan() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR") || karyawan.getRole().equalsIgnoreCase("ACCOUNTING") || karyawan.getRole().equalsIgnoreCase("ADMIN")) {
                ReportPenjualanDetailDialog reportPenjualanDetailDialog = new ReportPenjualanDetailDialog(null, true);
                reportPenjualanDetailDialog.addListener(new reportPenjualanDetailDialogEventHandler());
                int w = reportPenjualanDetailDialog.getWidth();
                int h = reportPenjualanDetailDialog.getHeight();
                reportPenjualanDetailDialog.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                reportPenjualanDetailDialog.setVisible(true);
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class passwordInputPenukaranPoin
    implements PasswordInputListener {
        private passwordInputPenukaranPoin() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR") || karyawan.getRole().equalsIgnoreCase("ACCOUNTING") || karyawan.getRole().equalsIgnoreCase("ADMIN")) {
                PenukaranPointDialog penukaranPointDialog = new PenukaranPointDialog(null, true);
                int w = penukaranPointDialog.getWidth();
                int h = penukaranPointDialog.getHeight();
                penukaranPointDialog.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                penukaranPointDialog.setVisible(true);
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class passwordInputSummaryPenjualan
    implements PasswordInputListener {
        private passwordInputSummaryPenjualan() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR") || karyawan.getRole().equalsIgnoreCase("ACCOUNTING") || karyawan.getRole().equalsIgnoreCase("ADMIN")) {
                ReportPenjualanDialog reportPenjualanDialog = new ReportPenjualanDialog(null, true);
                reportPenjualanDialog.addListener(new reportPenjualanDialogEventHandler());
                int w = reportPenjualanDialog.getWidth();
                int h = reportPenjualanDialog.getHeight();
                reportPenjualanDialog.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                reportPenjualanDialog.setVisible(true);
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class passwordInputSOM
    implements PasswordInputListener {
        private passwordInputSOM() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR") || karyawan.getRole().equalsIgnoreCase("INVENTORY") || karyawan.getRole().equalsIgnoreCase("STAF INVENTORY")) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Stop Opname Mandiri");
                StokOpnamMandiriPanel sPanel = null;
                try {
                    sPanel = new StokOpnamMandiriPanel();
                }
                catch (Exception ex) {
                    Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                }
                Controller.this.setContentPane(sPanel);
                sPanel.addListener(new defaultPanelEventHandler());
            } else {
                JOptionPane.showMessageDialog(null, "Anda tidak memiliki hak akses");
            }
        }

        @Override
        public void passwordFailed(String password) {
            JOptionPane.showMessageDialog(null, "Password Anda Salah");
        }
    }

    private class passwordBukaKasir
    implements PasswordInputListener {
        private passwordBukaKasir() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR") || karyawan.getRole().equalsIgnoreCase("ACCOUNTING")) {
                NewKasir newKasir = new NewKasir(null, true);
                int w = newKasir.getWidth();
                int h = newKasir.getHeight();
                newKasir.setLocation((Controller.this.screenWidth - w) / 2, (Controller.this.screenHeight - h) / 2);
                newKasir.setListener(new newKasirEventHandler());
                newKasir.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Anda tidak memiliki hak akses");
            }
        }

        @Override
        public void passwordFailed(String password) {
            JOptionPane.showMessageDialog(null, "Password Anda Salah");
        }
    }

    private class confirmDialogEventHandler
    implements ConfirmationDialogListener {
        private confirmDialogEventHandler() {
        }

        @Override
        public void yesClick() {
            Controller.this.canGoThrough = true;
        }

        @Override
        public void noClick() {
            Controller.this.canGoThrough = false;
        }
    }

    private class cetakHargaBarangEventHandler
    implements ReportJenisRakDialogListener {
        private cetakHargaBarangEventHandler() {
        }

        @Override
        public void tampilkanClick(String jenis, String rak) {
            try {
                File rear = new File("ireport/CetakHargaBarang.jasper");
                String path = rear.getAbsolutePath();
                HashMap<String, String> parameter = new HashMap<String, String>();
                parameter.put("M_JENIS_JENIS", '%' + jenis + '%');
                parameter.put("M_RAK_ID", '%' + rak + '%');
                JasperPrint print = JasperFillManager.fillReport((String)path, parameter, (Connection)ConnectionManager.getInstance().getDataSource().getConnection("root", "root"));
                JasperViewer.viewReport((JasperPrint)print, (boolean)false);
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Dokumen Tidak Ada " + ex);
            }
        }
    }

    private class dibawahHargaBeliEventHandler
    implements ReportDibawahHargaBeliListener {
        private dibawahHargaBeliEventHandler() {
        }

        @Override
        public void tampilkanClick(String jenis) {
            ReportDibawahHargaBeliPanel dibawahHargaBeliPanel = new ReportDibawahHargaBeliPanel(jenis);
            dibawahHargaBeliPanel.addListener(new defaultPanelEventHandler());
            Controller.this.setContentPane(dibawahHargaBeliPanel);
            Controller.this.setTitle("Report Dibawah harga beli");
            Controller.this.menuBar.setVisible(false);
            Controller.this.menuBar.requestFocus();
        }
    }

    private class reportHistoryHargaDialogEventHandler
    implements ReportHistoryHargaDialogListener {
        private reportHistoryHargaDialogEventHandler() {
        }

        @Override
        public void tampilkanClick(Date startDate, Date endDate, String idBarang) {
            ReportHistoryHargaPanel reportHistoryHargaPanel = new ReportHistoryHargaPanel(startDate, endDate, idBarang);
            reportHistoryHargaPanel.addListener(new defaultPanelEventHandler());
            Controller.this.setContentPane(reportHistoryHargaPanel);
            Controller.this.setTitle("Report History Harga");
            Controller.this.menuBar.setVisible(false);
            Controller.this.menuBar.requestFocus();
        }
    }

    private class reportPiutangEventHandler
    implements ReportPiutangDialogListener {
        private reportPiutangEventHandler() {
        }

        @Override
        public void tampilkanClick(Date startDate, Date endDate, String tipe, String customerId) {
            ReportPelunasanPiutangPanel reportPiutangPanel = new ReportPelunasanPiutangPanel(startDate, endDate, tipe, customerId);
            reportPiutangPanel.addListener(new defaultPanelEventHandler());
            Controller.this.setContentPane(reportPiutangPanel);
            Controller.this.setTitle("Report Piutang Customer");
            Controller.this.menuBar.setVisible(false);
            Controller.this.menuBar.requestFocus();
        }
    }

    private class pembelianPanelEventHandler
    implements PembelianPanelListener {
        private pembelianPanelEventHandler() {
        }

        @Override
        public void escapeClick() {
            Controller.this.setTitle("");
            MainPanel mainPanel = new MainPanel();
            mainPanel.addListener(new mainPanelEventHandler());
            Controller.this.setContentPane(mainPanel);
            Controller.this.menuBar.setVisible(true);
            Controller.this.menuBar.requestFocus();
        }

        @Override
        public void shortcutClick(String shortcut) {
            Controller.this.menuBar.setVisible(false);
            Controller.this.setTitle("Pembelian");
            PembelianPanel pembelianPanel = new PembelianPanel(shortcut);
            pembelianPanel.addListener(new pembelianPanelEventHandler());
            Controller.this.setContentPane(pembelianPanel);
            Controller.this.setVisible(true);
            pembelianPanel.setDefault();
        }
    }

    private class defaultPanelEventHandler
    implements DefaultPanelListener {
        private defaultPanelEventHandler() {
        }

        @Override
        public void exitClick() {
            Controller.this.setTitle("");
            MainPanel mainPanel = new MainPanel();
            mainPanel.addListener(new mainPanelEventHandler());
            Controller.this.setContentPane(mainPanel);
            Controller.this.menuBar.setVisible(true);
            Controller.this.menuBar.requestFocus();
        }
    }

    private class reportStokTerakhirDialogEventHandler
    implements ReportJenisRakDialogListener {
        private reportStokTerakhirDialogEventHandler() {
        }

        @Override
        public void tampilkanClick(String jenis, String rak) {
            ReportStokTerakhirPanel reportStokTerakhirPanel = new ReportStokTerakhirPanel(jenis, rak);
            reportStokTerakhirPanel.addListener(new defaultPanelEventHandler());
            Controller.this.setContentPane(reportStokTerakhirPanel);
            Controller.this.setTitle("Report Stok Terakhir");
            Controller.this.menuBar.setVisible(false);
            Controller.this.menuBar.requestFocus();
        }
    }

    private class reportHistoryStokDialogEventHandler
    implements ReportHistoryStokDialogListener {
        private reportHistoryStokDialogEventHandler() {
        }

        @Override
        public void tampilkanClick(Date startDate, Date endDate, Barang barang, String status) {
            ReportHistoryStokPanel historyStokPanel = new ReportHistoryStokPanel(startDate, endDate, barang, status);
            historyStokPanel.addListener(new defaultPanelEventHandler());
            Controller.this.setContentPane(historyStokPanel);
            Controller.this.setTitle("Report Koreksi Stok Barang");
            Controller.this.menuBar.setVisible(false);
            Controller.this.menuBar.requestFocus();
        }
    }

    private class mainPanelEventHandler
    implements MainPanelListener {
        private mainPanelEventHandler() {
        }

        @Override
        public void tampilkanClick(Date startDate, Date endDate, String idBarang) {
            ReportHistoryHargaPanel reportHistoryHargaPanel = new ReportHistoryHargaPanel(startDate, endDate, idBarang);
            reportHistoryHargaPanel.addListener(new defaultPanelEventHandler());
            Controller.this.setContentPane(reportHistoryHargaPanel);
            Controller.this.setTitle("Report History Harga");
            Controller.this.menuBar.setVisible(false);
            Controller.this.menuBar.requestFocus();
        }
    }

    private class reportStokBsBarangDialogEventHandler
    implements ReportStokBsBarangDialogListener {
        private reportStokBsBarangDialogEventHandler() {
        }

        @Override
        public void tampilkanClick(String jenis) {
            ReportStokBsBarangPanel reportStokBsBarangPanel = new ReportStokBsBarangPanel(jenis);
            reportStokBsBarangPanel.addListener(new defaultPanelEventHandler());
            Controller.this.setContentPane(reportStokBsBarangPanel);
            Controller.this.setTitle("Report Stok BS Barang");
            Controller.this.menuBar.setVisible(false);
            Controller.this.menuBar.requestFocus();
        }
    }

    private class reportKeuntunganDialogEventHandler
    implements ReportKeuntunganDialogListener {
        private reportKeuntunganDialogEventHandler() {
        }

        @Override
        public void tampilkanClick(Date startDate, Date endDate, String tipe, Barang barang, int limit) {
            ReportKeuntunganPanel reportKeuntunganPanel = new ReportKeuntunganPanel(startDate, endDate, tipe, barang, limit);
            reportKeuntunganPanel.addListener(new defaultPanelEventHandler());
            Controller.this.setContentPane(reportKeuntunganPanel);
            Controller.this.setTitle("Report Keuntungan");
            Controller.this.menuBar.setVisible(false);
            Controller.this.menuBar.requestFocus();
        }
    }

    private class reportBarangMinDialogEventHandler
    implements ReportBarangMinDialogListener {
        private reportBarangMinDialogEventHandler() {
        }

        @Override
        public void tampilkanClick(String jenis) {
            ReportBarangMinPanel reportBarangMinPanel = new ReportBarangMinPanel(jenis);
            reportBarangMinPanel.addListener(new defaultPanelEventHandler());
            Controller.this.setContentPane(reportBarangMinPanel);
            Controller.this.setTitle("Report Stok Dibawah Min");
            Controller.this.menuBar.setVisible(false);
            Controller.this.menuBar.requestFocus();
        }
    }

    private class reportMasterBarangDialogEventHandler
    implements ReportMasterBarangDialogListener {
        private reportMasterBarangDialogEventHandler() {
        }

        @Override
        public void tampilkanClick(String jenis, String rak) {
            ReportMasterBarangPanel reportMasterBarangPanel = new ReportMasterBarangPanel(jenis, rak);
            reportMasterBarangPanel.addListener(new defaultPanelEventHandler());
            Controller.this.setContentPane(reportMasterBarangPanel);
            Controller.this.setTitle("Report Master Barang");
            Controller.this.menuBar.setVisible(false);
            Controller.this.menuBar.requestFocus();
        }

        @Override
        public void tampilKanClickBySupplier(String lokasi, String supplierId) {
            ReportMasterBarangPanel reportMasterBarangPanel = new ReportMasterBarangPanel(lokasi, supplierId, "");
            reportMasterBarangPanel.addListener(new defaultPanelEventHandler());
            Controller.this.setContentPane(reportMasterBarangPanel);
            Controller.this.setTitle("Report Master Barang");
            Controller.this.menuBar.setVisible(false);
            Controller.this.menuBar.requestFocus();
        }
    }

    private class reportPenjualanDialogEventHandler
    implements ReportPenjualanDialogListener {
        private reportPenjualanDialogEventHandler() {
        }

        @Override
        public void tampilkanClick(Date startDate, Date endDate, String customerId, String keterangan) {
            ReportPenjualanPanel reportPenjualanPanel = new ReportPenjualanPanel(startDate, endDate, customerId, keterangan);
            reportPenjualanPanel.addListener(new defaultPanelEventHandler());
            Controller.this.setContentPane(reportPenjualanPanel);
            Controller.this.setTitle("Report Penjualan");
            Controller.this.menuBar.setVisible(false);
            Controller.this.menuBar.requestFocus();
        }
    }

    private class reportPembelianDetailDialogEventHandler
    implements ReportPembelianDetailDialogListener {
        private reportPembelianDetailDialogEventHandler() {
        }

        @Override
        public void tampilkanClick(Date startDate, Date endDate, String customerId, String barangId) {
            ReportPembelianDetailPanel pembelianDetailPanel = new ReportPembelianDetailPanel(startDate, endDate, customerId, barangId);
            pembelianDetailPanel.addListener(new defaultPanelEventHandler());
            Controller.this.setContentPane(pembelianDetailPanel);
            Controller.this.setTitle("Report Detail Penjualan");
            Controller.this.menuBar.setVisible(false);
            Controller.this.menuBar.requestFocus();
        }
    }

    private class reportPenjualanDetailDialogEventHandler
    implements ReportPenjualanDetailDialogListener {
        private reportPenjualanDetailDialogEventHandler() {
        }

        @Override
        public void tampilkanClick(Date startDate, Date endDate, String customerId, String barangId) {
            ReportPenjualanDetailPanel reportPenjualanPanel = new ReportPenjualanDetailPanel(startDate, endDate, customerId, barangId);
            reportPenjualanPanel.addListener(new defaultPanelEventHandler());
            Controller.this.setContentPane(reportPenjualanPanel);
            Controller.this.setTitle("Report Detail Penjualan");
            Controller.this.menuBar.setVisible(false);
            Controller.this.menuBar.requestFocus();
        }
    }

    private class newKasirCabangEventHandler2
    implements NewKasirListener {
        private newKasirCabangEventHandler2() {
        }

        @Override
        public void yaListener() {
            ShiftKasir cekshiftKasir = new ShiftKasirService().getOpenedShiftKasir(Controller.this.helper.getKasir());
            if (cekshiftKasir == null) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Penjualan Cabang");
                PenjualanCabangPanelUsingBatchPengiriman transaksiPanel = new PenjualanCabangPanelUsingBatchPengiriman("F5");
                transaksiPanel.addTransaksiListener(new penjualanCabangPanelEventHandler2());
                Controller.this.setContentPane(transaksiPanel);
                transaksiPanel.setDefaultFocus();
                ShiftKasir shiftKasir = new ShiftKasir();
                shiftKasir.setIdKasir(Controller.this.helper.getKasir());
                shiftKasir.setOpenedBy(Controller.this.helper.getKaryawan().getId());
                shiftKasir.setOpening(new Timestamp(System.currentTimeMillis()));
                shiftKasir.setTotalUang(0L);
                int b = Controller.this.shiftKasirService.create(shiftKasir);
                if (b == 1) {
                    JOptionPane.showMessageDialog(null, "Kasir " + Controller.this.helper.getKasir() + " Telah Aktif");
                } else {
                    JOptionPane.showMessageDialog(null, "Gagal Mengaktifkan Kasir");
                }
            }
        }
    }

    private class newKasirCabangEventHandler
    implements NewKasirListener {
        private newKasirCabangEventHandler() {
        }

        @Override
        public void yaListener() {
            ShiftKasir cekshiftKasir = new ShiftKasirService().getOpenedShiftKasir(Controller.this.helper.getKasir());
            if (cekshiftKasir == null) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Penjualan Cabang");
                PenjualanCabangPanel transaksiPanel = new PenjualanCabangPanel("F5");
                transaksiPanel.addTransaksiListener(new penjualanCabangPanelEventHandler());
                Controller.this.setContentPane(transaksiPanel);
                transaksiPanel.setDefaultFocus();
                ShiftKasir shiftKasir = new ShiftKasir();
                shiftKasir.setIdKasir(Controller.this.helper.getKasir());
                shiftKasir.setOpenedBy(Controller.this.helper.getKaryawan().getId());
                shiftKasir.setOpening(new Timestamp(System.currentTimeMillis()));
                shiftKasir.setTotalUang(0L);
                int b = Controller.this.shiftKasirService.create(shiftKasir);
                if (b == 1) {
                    JOptionPane.showMessageDialog(null, "Kasir " + Controller.this.helper.getKasir() + " Telah Aktif");
                } else {
                    JOptionPane.showMessageDialog(null, "Gagal Mengaktifkan Kasir");
                }
            }
        }
    }

    private class openedKasirExcelEventHandler2
    implements OpenedKasirListener {
        private openedKasirExcelEventHandler2() {
        }

        @Override
        public void lanjutkan() {
            Controller.this.menuBar.setVisible(false);
            Controller.this.setTitle("Penjualan Excel");
            PenjualanByExcel2 transaksiPanel = new PenjualanByExcel2("F5");
            transaksiPanel.addTransaksiListener(new penjualanCabangPanelEventHandler());
            Controller.this.setContentPane(transaksiPanel);
            transaksiPanel.setDefaultFocus();
        }
    }

    private class openedKasirExcelEventHandler
    implements OpenedKasirListener {
        private openedKasirExcelEventHandler() {
        }

        @Override
        public void lanjutkan() {
            Controller.this.menuBar.setVisible(false);
            Controller.this.setTitle("Penjualan");
            PenjualanByExcel transaksiPanel = new PenjualanByExcel("F5");
            transaksiPanel.addTransaksiListener(new penjualanCabangPanelEventHandler());
            Controller.this.setContentPane(transaksiPanel);
            transaksiPanel.setDefaultFocus();
        }
    }

    private class openedKasirCabangEventHandler2
    implements OpenedKasirListener {
        private openedKasirCabangEventHandler2() {
        }

        @Override
        public void lanjutkan() {
            Controller.this.menuBar.setVisible(false);
            Controller.this.setTitle("Penjualan Cabang New");
            PenjualanCabangPanelUsingBatchPengiriman transaksiPanel = new PenjualanCabangPanelUsingBatchPengiriman("F5");
            transaksiPanel.addTransaksiListener(new penjualanCabangPanelEventHandler2());
            Controller.this.setContentPane(transaksiPanel);
            transaksiPanel.setDefaultFocus();
        }
    }

    private class openedKasirCabangEventHandler
    implements OpenedKasirListener {
        private openedKasirCabangEventHandler() {
        }

        @Override
        public void lanjutkan() {
            Controller.this.menuBar.setVisible(false);
            Controller.this.setTitle("Penjualan");
            PenjualanCabangPanel transaksiPanel = new PenjualanCabangPanel("F5");
            transaksiPanel.addTransaksiListener(new penjualanCabangPanelEventHandler());
            Controller.this.setContentPane(transaksiPanel);
            transaksiPanel.setDefaultFocus();
        }
    }

    private class newKasirExcelEventHandler2
    implements NewKasirListener {
        private newKasirExcelEventHandler2() {
        }

        @Override
        public void yaListener() {
            ShiftKasir cekshiftKasir = new ShiftKasirService().getOpenedShiftKasir(Controller.this.helper.getKasir());
            if (cekshiftKasir == null) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Penjualan Excel");
                PenjualanByExcel2 transaksiPanel = new PenjualanByExcel2("F5");
                transaksiPanel.addTransaksiListener(new penjualanPanelEventHandler());
                Controller.this.setContentPane(transaksiPanel);
                transaksiPanel.setDefaultFocus();
                ShiftKasir shiftKasir = new ShiftKasir();
                shiftKasir.setIdKasir(Controller.this.helper.getKasir());
                shiftKasir.setOpenedBy(Controller.this.helper.getKaryawan().getId());
                shiftKasir.setOpening(new Timestamp(System.currentTimeMillis()));
                shiftKasir.setTotalUang(0L);
                int b = Controller.this.shiftKasirService.create(shiftKasir);
                if (b == 1) {
                    JOptionPane.showMessageDialog(null, "Kasir " + Controller.this.helper.getKasir() + " Telah Aktif");
                } else {
                    JOptionPane.showMessageDialog(null, "Gagal Mengaktifkan Kasir");
                }
            }
        }
    }

    private class newKasirExcelEventHandler
    implements NewKasirListener {
        private newKasirExcelEventHandler() {
        }

        @Override
        public void yaListener() {
            ShiftKasir cekshiftKasir = new ShiftKasirService().getOpenedShiftKasir(Controller.this.helper.getKasir());
            if (cekshiftKasir == null) {
                Controller.this.menuBar.setVisible(false);
                Controller.this.setTitle("Penjualan Excel");
                PenjualanByExcel transaksiPanel = new PenjualanByExcel("F5");
                transaksiPanel.addTransaksiListener(new penjualanPanelEventHandler());
                Controller.this.setContentPane(transaksiPanel);
                transaksiPanel.setDefaultFocus();
                ShiftKasir shiftKasir = new ShiftKasir();
                shiftKasir.setIdKasir(Controller.this.helper.getKasir());
                shiftKasir.setOpenedBy(Controller.this.helper.getKaryawan().getId());
                shiftKasir.setOpening(new Timestamp(System.currentTimeMillis()));
                shiftKasir.setTotalUang(0L);
                int b = Controller.this.shiftKasirService.create(shiftKasir);
                if (b == 1) {
                    JOptionPane.showMessageDialog(null, "Kasir " + Controller.this.helper.getKasir() + " Telah Aktif");
                } else {
                    JOptionPane.showMessageDialog(null, "Gagal Mengaktifkan Kasir");
                }
            }
        }
    }

    private class newKasirEventHandler
    implements NewKasirListener {
        private newKasirEventHandler() {
        }

        @Override
        public void yaListener() {
            ShiftKasir cekshiftKasir = new ShiftKasirService().getOpenedShiftKasir(Controller.this.helper.getKasir());
            if (cekshiftKasir == null) {
                ShiftKasir shiftKasir = new ShiftKasir();
                shiftKasir.setIdKasir(Controller.this.helper.getKasir());
                shiftKasir.setOpenedBy(Controller.this.helper.getKaryawan().getId());
                shiftKasir.setOpening(new Timestamp(System.currentTimeMillis()));
                shiftKasir.setTotalUang(0L);
                int b = Controller.this.shiftKasirService.create(shiftKasir);
                if (b == 1) {
                    JOptionPane.showMessageDialog(null, "Kasir " + Controller.this.helper.getKasir() + " Telah Aktif");
                    Controller.this.menuBar.setVisible(false);
                    Controller.this.setTitle("Penjualan");
                    PenjualanPanel1 transaksiPanel = new PenjualanPanel1("F5", Controller.this.keteranganSoftware);
                    transaksiPanel.addTransaksiListener(new penjualanPanelEventHandler());
                    Controller.this.setContentPane(transaksiPanel);
                    transaksiPanel.setDefaultFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "Gagal Mengaktifkan Kasir");
                }
            }
        }
    }

    private class openedKasirEventHandler
    implements OpenedKasirListener {
        private openedKasirEventHandler() {
        }

        @Override
        public void lanjutkan() {
            Controller.this.menuBar.setVisible(false);
            Controller.this.setTitle("Penjualan");
            PenjualanPanel1 transaksiPanel = new PenjualanPanel1("F5", Controller.this.keteranganSoftware);
            transaksiPanel.addTransaksiListener(new penjualanPanelEventHandler());
            Controller.this.setContentPane(transaksiPanel);
            transaksiPanel.setDefaultFocus();
        }
    }

    private class reportPembelianDialogEventHandler
    implements ReportPembelianDialogListener {
        private reportPembelianDialogEventHandler() {
        }

        @Override
        public void tampilkanClick(Date startDate, Date endDate, String idSupplier, String keterangan) {
            if (keterangan.equals("SEMUA")) {
                keterangan = "";
            }
            ReportPembelianPanel reportPembelianPanel = new ReportPembelianPanel(startDate, endDate, idSupplier, keterangan);
            reportPembelianPanel.addListener(new defaultPanelEventHandler());
            Controller.this.setContentPane(reportPembelianPanel);
            Controller.this.setTitle("Report Pembelian");
            Controller.this.menuBar.setVisible(false);
            Controller.this.menuBar.requestFocus();
        }
    }

    private class reportHutangDialogEventHandler
    implements ReportHutangDialogListener {
        private reportHutangDialogEventHandler() {
        }

        @Override
        public void tampilkanClick(String idSupplier, String keterangan) {
            if (keterangan.equals("SEMUA")) {
                keterangan = "";
            }
            ReportHutangPanel reportHutangPanel = new ReportHutangPanel(idSupplier, keterangan);
            reportHutangPanel.addListener(new defaultPanelEventHandler());
            Controller.this.setContentPane(reportHutangPanel);
            Controller.this.setTitle("Report Hutang");
            Controller.this.menuBar.setVisible(false);
            Controller.this.menuBar.requestFocus();
        }
    }

    private class reportPelunasanHutangDialogEventHandler
    implements ReportPelunasanHutangDialogListener {
        private reportPelunasanHutangDialogEventHandler() {
        }

        @Override
        public void tampilkanClick(Date startDate, Date endDate, String supplierId) {
            Controller.this.menuBar.setVisible(false);
            ReportPelunasanHutangPanel reportPelunasanHutangPanel = new ReportPelunasanHutangPanel(startDate, endDate, supplierId);
            reportPelunasanHutangPanel.addListener(new defaultPanelEventHandler());
            Controller.this.setContentPane(reportPelunasanHutangPanel);
            Controller.this.setVisible(true);
        }
    }

    private class reportPenerimaanBsEventHandler
    implements ReportPenerimaanBsListener {
        private reportPenerimaanBsEventHandler() {
        }

        @Override
        public void tampilkanClick(Date startDate, Date endDate, String lokasi, String status) {
            Controller.this.menuBar.setVisible(false);
            Controller.this.setTitle("Entry BS");
            if (lokasi.equals("SEMUA")) {
                lokasi = "";
            }
            if (status.equals("SEMUA")) {
                status = "";
            }
            ReportPenerimaanBsPanel reportPenerimaanBsPanel = new ReportPenerimaanBsPanel(startDate, endDate, lokasi, status);
            reportPenerimaanBsPanel.addListener(new defaultPanelEventHandler());
            Controller.this.setContentPane(reportPenerimaanBsPanel);
            Controller.this.setVisible(true);
        }
    }

    private class penjualanPanelEventHandler
    implements PenjualanPanelListener {
        private penjualanPanelEventHandler() {
        }

        @Override
        public void escapeClick() {
            Controller.this.setTitle("");
            MainPanel mainPanel = new MainPanel();
            mainPanel.addListener(new mainPanelEventHandler());
            Controller.this.setContentPane(mainPanel);
            Controller.this.menuBar.setVisible(true);
            Controller.this.menuBar.requestFocus();
        }

        @Override
        public void shortcutClick(String shortcut) {
            Controller.this.menuBar.setVisible(false);
            Controller.this.setTitle("Penjualan");
            PenjualanPanel1 transaksiPanel = new PenjualanPanel1(shortcut, Controller.this.keteranganSoftware);
            transaksiPanel.addTransaksiListener(new penjualanPanelEventHandler());
            Controller.this.setContentPane(transaksiPanel);
            Controller.this.setVisible(true);
            transaksiPanel.setDefaultFocus();
        }
    }

    private class penjualanCabangPanelEventHandler2
    implements PenjualanPanelListener {
        private penjualanCabangPanelEventHandler2() {
        }

        @Override
        public void escapeClick() {
            Controller.this.setTitle("");
            MainPanel mainPanel = new MainPanel();
            mainPanel.addListener(new mainPanelEventHandler());
            Controller.this.setContentPane(mainPanel);
            Controller.this.menuBar.setVisible(true);
            Controller.this.menuBar.requestFocus();
        }

        @Override
        public void shortcutClick(String shortcut) {
            Controller.this.menuBar.setVisible(false);
            Controller.this.setTitle("Penjualan Cabang");
            PenjualanCabangPanelUsingBatchPengiriman transaksiPanel = new PenjualanCabangPanelUsingBatchPengiriman(shortcut);
            transaksiPanel.addTransaksiListener(new penjualanCabangPanelEventHandler2());
            Controller.this.setContentPane(transaksiPanel);
            Controller.this.setVisible(true);
            transaksiPanel.setDefaultFocus();
        }
    }

    private class penjualanCabangPanelEventHandler
    implements PenjualanPanelListener {
        private penjualanCabangPanelEventHandler() {
        }

        @Override
        public void escapeClick() {
            Controller.this.setTitle("");
            MainPanel mainPanel = new MainPanel();
            mainPanel.addListener(new mainPanelEventHandler());
            Controller.this.setContentPane(mainPanel);
            Controller.this.menuBar.setVisible(true);
            Controller.this.menuBar.requestFocus();
        }

        @Override
        public void shortcutClick(String shortcut) {
            Controller.this.menuBar.setVisible(false);
            Controller.this.setTitle("Penjualan Cabang");
            PenjualanCabangPanel transaksiPanel = new PenjualanCabangPanel(shortcut);
            transaksiPanel.addTransaksiListener(new penjualanCabangPanelEventHandler());
            Controller.this.setContentPane(transaksiPanel);
            Controller.this.setVisible(true);
            transaksiPanel.setDefaultFocus();
        }
    }

    private class loginOnlineEventHandler
    implements LoginOnlineListener {
        private loginOnlineEventHandler() {
        }

        @Override
        public void onLoginSucceed(String status) {
            Controller.this.initAllService();
            Controller.this.karyawanService.sinkroniasaiKaryawanOnlineToOffline();
            Controller.this.cekRole();
            Controller.this.konstantaService.addColumnIfNotExists("minimarket", "t_penjualan", "id_transaksi_online", "VARCHAR(45)");
            Controller.this.konstantaService.addColumnIfNotExists("minimarket", "t_penjualan_sementara", "id_transaksi_online", "VARCHAR(45)");
            Controller.this.konstantaService.addColumnIfNotExists("minimarket", "stok_opnam_mandiri_detail", "nomor_urut", "INT");
            Controller.this.konstantaService.addColumnIfNotExists("minimarket", "m_shift_kasir", "transfer", "BIGINT", false, "0");
            Controller.this.konstantaService.addColumnIfNotExists("minimarket", "m_shift_kasir", "debit", "BIGINT", false, "0");
            Controller.this.konstantaService.addColumnIfNotExists("minimarket", "m_shift_kasir", "qris", "BIGINT", false, "0");
            Controller.this.konstantaService.addColumnIfNotExists("minimarket", "m_shift_kasir", "voucher", "BIGINT", false, "0");
            Controller.this.konstantaService.addColumnIfNotExists("minimarket", "m_shift_kasir", "status_sync_online", "SMALLINT", true, "0");
            Controller.this.konstantaService.addColumnIfNotExists("minimarket", "t_pengembalian_barang", "m_shift_kasir_id", "INT");
            if (!Controller.this.helper.getKaryawan().getId().equals("EMIL") && !Controller.this.helper.getKaryawan().getRole().equals("DIREKTUR")) {
                Controller.this.commitMenuItems[2].setVisible(false);
                Controller.this.commitMenuItems[3].setVisible(false);
                Controller.this.sinkronisasiMenu.setVisible(false);
            }
            Controller.this.setSize(800, 600);
            Controller.this.setLocation(0, 0);
            String statusProgram = "TOKO";
            Controller.this.helper.setJenisProgram("GUDANGTOKO");
            Controller.this.helper.setStatusProgram(statusProgram);
            if (status.equals("ADMIN")) {
                Controller.this.kasirId = 99;
            } else {
                Controller.this.kasirId = 1;
            }
            if (Controller.this.keteranganSoftware.equals("TOKO")) {
                Controller.this.mutasiBarangMenuItem.setEnabled(false);
            } else if (Controller.this.keteranganSoftware.equals("GUDANG")) {
                Controller.this.mutasiBarangMenuItem.setEnabled(true);
            }
            Controller.this.tipeProgram = "PUSAT";
            Controller.this.kasirParsial = false;
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime now1 = now.minusDays(1L);
            if (Controller.this.kasirId == 1 && !Controller.this.sinkron) {
                Controller.this.sinkron = true;
                try {
                    int status1 = Controller.this.customerCommitService.pushCustomerCommits();
                    if (status1 == 1) {
                        Controller.this.customerPointCommitService.pushCustomerCommits();
                        Controller.this.customerCommitService.pullCustomers();
                        JOptionPane.showMessageDialog(null, "Data Customer berhasil disinkronkan");
                    }
                }
                catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
                if (Controller.this.konstantaService.getConstantValue("lastSinkronStok") == null) {
                    Konstanta konstanta = new Konstanta();
                    konstanta.setKonstan("lastSinkronStok");
                    konstanta.setValue(dtf1.format(now1));
                    Controller.this.konstantaService.createConstant(konstanta);
                }
                if (dtf1.format(now).compareToIgnoreCase(Controller.this.konstantaService.getConstantValue("lastSinkronStok")) > 0) {
                    System.out.println(dtf1.format(now));
                    try {
                        int result = Controller.this.sinkronisasiBarangService.sinkronisasiBarang(1);
                        int resultSales = Controller.this.salesService.syncOnlineSalesToOffline();
                        if (result == 1) {
                            Controller.this.konstantaService.updateConstant("lastSinkronStok", dtf1.format(now));
                            JOptionPane.showMessageDialog(null, "Data Barang dan Barcode berhasil di sinkronisasi");
                        } else if (result == 2) {
                            Controller.this.konstantaService.updateConstant("lastSinkronStok", dtf1.format(now));
                            JOptionPane.showMessageDialog(null, "Sebagian data gagal di sinkronisasi, lakukan sinkronisasi sekali lagi");
                        } else {
                            JOptionPane.showMessageDialog(null, "Gagal melakukan sinkronisasi");
                        }
                    }
                    catch (IOException ex) {
                        Controller.this.konstantaService.updateConstant("sinkronStok", "false");
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                System.out.println(Controller.this.konstantaService.getConstantValue("lastSinkronPromo"));
                if (Controller.this.konstantaService.getConstantValue("lastSinkronPromo") == null) {
                    Konstanta konstanta = new Konstanta();
                    konstanta.setKonstan("lastSinkronPromo");
                    konstanta.setValue(dtf1.format(now1));
                    Controller.this.konstantaService.createConstant(konstanta);
                    System.out.println(konstanta.getKonstan());
                }
                if (dtf1.format(now).compareToIgnoreCase(Controller.this.konstantaService.getConstantValue("lastSinkronPromo")) > 0) {
                    Controller.this.syncPromoService.syncAllPromo();
                }
                int somoResult = Controller.this.somoService.downloadSOM();
                Controller.this.paketService.doSyncPaketOnlineToOffline();
                Controller.this.sinkron = false;
            }
            Controller.this.helper.setKasir(Controller.this.kasirId);
            Controller.this.setTitle("");
            MainPanel mainPanel = new MainPanel();
            mainPanel.addListener(new mainPanelEventHandler());
            Controller.this.setContentPane(mainPanel);
            Controller.this.setRole2();
            Controller.this.setPenjualanParsial();
            Controller.this.menuBar.setVisible(true);
            Controller.this.menuBar.requestFocus();
            if (Controller.this.helper.getStatusProgram().equals("GUDANG") && Controller.this.helper.getJenisProgram().equals("GUDANGTOKO")) {
                Controller.this.mutasiMenuItems[1].setVisible(false);
            }
        }

        @Override
        public void onLoginFailed() {
            JOptionPane.showMessageDialog(null, "FAILED");
        }
    }
}

