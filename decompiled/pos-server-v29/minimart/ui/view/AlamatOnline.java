/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import minimart.engine.model.CustomerAlamat;
import minimart.engine.model.Region;
import minimart.engine.service.RegionService;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.listener.WilayahPanelListener;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanelWilayah;

public class AlamatOnline
extends JPanel {
    private JTextField alamatLengkapField;
    private JButton cancelButton;
    private JButton cariWilayahButton;
    private JLabel idKecamatanText;
    private JLabel idKelurahanText;
    private JLabel idKotaText;
    private JLabel idProvinsiText;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JTextField kecamatanField;
    private JTextField kelurahanField;
    private JTextField kotaField;
    private JTextField namaPenerimaField;
    private JTextField provinsiField;
    private JButton saveButton;
    private JTextField teleponPenerimaField;
    private WilayahPanelListener listener;
    private int screenHeight;
    private int screenWidth;
    private RegionService regionService = new RegionService();
    List<Region> regionList;
    private String idCustomer = "";
    private String namaCustomer = "";
    private String telepon = "";
    private String alamatLengkap = "";
    private CustomerAlamat customerAlamat = null;

    public AlamatOnline(String idCustomer, String namaCustomer, String telepon) {
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.idCustomer = idCustomer;
        this.namaCustomer = namaCustomer;
        this.telepon = telepon;
        this.customerAlamat = this.regionService.getCustomerAlamatPrimaryByIdCustomer(idCustomer);
        if (this.customerAlamat != null) {
            this.namaCustomer = this.customerAlamat.getPenerima();
            this.telepon = this.customerAlamat.getTelepon();
            this.alamatLengkap = this.customerAlamat.getAlamat();
            Region region = null;
            if (this.customerAlamat.getIdRegKelurahan() != null) {
                region = this.regionService.getRegionByIdKelurahan(this.customerAlamat.getIdRegKelurahan());
            }
            if (region != null) {
                this.populateFormRegion(region);
            }
        }
        this.populateForm();
    }

    public void addListener(WilayahPanelListener wilayahPanelListener) {
        this.listener = wilayahPanelListener;
    }

    public void closeWindow() {
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window != null) {
            window.dispose();
        }
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.alamatLengkapField = new JTextField();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.namaPenerimaField = new JTextField();
        this.jLabel4 = new JLabel();
        this.teleponPenerimaField = new JTextField();
        this.jLabel5 = new JLabel();
        this.provinsiField = new JTextField();
        this.kelurahanField = new JTextField();
        this.jLabel6 = new JLabel();
        this.kotaField = new JTextField();
        this.jLabel7 = new JLabel();
        this.kecamatanField = new JTextField();
        this.jLabel8 = new JLabel();
        this.saveButton = new JButton();
        this.cancelButton = new JButton();
        this.cariWilayahButton = new JButton();
        this.idProvinsiText = new JLabel();
        this.idKotaText = new JLabel();
        this.idKecamatanText = new JLabel();
        this.idKelurahanText = new JLabel();
        this.jLabel1.setText("Alamat Lengkap");
        this.alamatLengkapField.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                AlamatOnline.this.alamatLengkapFieldActionPerformed(evt);
            }
        });
        this.jLabel3.setText("Nama Penerima");
        this.namaPenerimaField.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                AlamatOnline.this.namaPenerimaFieldActionPerformed(evt);
            }
        });
        this.jLabel4.setText("Telepon Penerima");
        this.teleponPenerimaField.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                AlamatOnline.this.teleponPenerimaFieldActionPerformed(evt);
            }
        });
        this.jLabel5.setText("Provinsi");
        this.provinsiField.setEditable(false);
        this.provinsiField.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                AlamatOnline.this.provinsiFieldActionPerformed(evt);
            }
        });
        this.kelurahanField.setEditable(false);
        this.kelurahanField.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                AlamatOnline.this.kelurahanFieldActionPerformed(evt);
            }
        });
        this.jLabel6.setText("Kota");
        this.kotaField.setEditable(false);
        this.kotaField.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                AlamatOnline.this.kotaFieldActionPerformed(evt);
            }
        });
        this.jLabel7.setText("Kecamatan");
        this.kecamatanField.setEditable(false);
        this.kecamatanField.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                AlamatOnline.this.kecamatanFieldActionPerformed(evt);
            }
        });
        this.jLabel8.setText("Kelurahan");
        this.saveButton.setText("Save");
        this.saveButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                AlamatOnline.this.saveButtonActionPerformed(evt);
            }
        });
        this.saveButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                AlamatOnline.this.saveButtonKeyPressed(evt);
            }
        });
        this.cancelButton.setText("Cancel");
        this.cancelButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                AlamatOnline.this.cancelButtonActionPerformed(evt);
            }
        });
        this.cariWilayahButton.setText("Cari Wilayah");
        this.cariWilayahButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                AlamatOnline.this.cariWilayahButtonActionPerformed(evt);
            }
        });
        this.idProvinsiText.setText("-");
        this.idKotaText.setText("-");
        this.idKecamatanText.setText("-");
        this.idKelurahanText.setText("-");
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jLabel3).addComponent(this.jLabel4)).addGap(20, 20, 20).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.teleponPenerimaField, -2, 232, -2).addComponent(this.namaPenerimaField, -2, 232, -2).addComponent(this.alamatLengkapField, -2, 232, -2))))).addGroup(layout.createSequentialGroup().addGap(44, 44, 44).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addComponent(this.saveButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cancelButton)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel6).addComponent(this.jLabel7).addComponent(this.jLabel8).addComponent(this.jLabel5)).addGap(20, 20, 20).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.kecamatanField, -1, 158, Short.MAX_VALUE).addComponent(this.kotaField).addComponent(this.provinsiField).addComponent(this.kelurahanField, GroupLayout.Alignment.TRAILING))).addComponent(this.cariWilayahButton, GroupLayout.Alignment.LEADING, -2, 146, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.idProvinsiText).addComponent(this.idKotaText).addComponent(this.idKecamatanText).addComponent(this.idKelurahanText)))).addContainerGap(92, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.alamatLengkapField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.namaPenerimaField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4).addComponent(this.teleponPenerimaField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel5).addComponent(this.provinsiField, -2, -1, -2).addComponent(this.idProvinsiText)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel6).addComponent(this.kotaField, -2, -1, -2).addComponent(this.idKotaText)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel7).addComponent(this.kecamatanField, -2, -1, -2).addComponent(this.idKecamatanText)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel8).addComponent(this.kelurahanField, -2, -1, -2).addComponent(this.idKelurahanText)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.cariWilayahButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.saveButton).addComponent(this.cancelButton)).addContainerGap()));
    }

    private void alamatLengkapFieldActionPerformed(ActionEvent evt) {
    }

    private void namaPenerimaFieldActionPerformed(ActionEvent evt) {
    }

    private void teleponPenerimaFieldActionPerformed(ActionEvent evt) {
    }

    private void provinsiFieldActionPerformed(ActionEvent evt) {
    }

    private void kelurahanFieldActionPerformed(ActionEvent evt) {
    }

    private void kotaFieldActionPerformed(ActionEvent evt) {
    }

    private void kecamatanFieldActionPerformed(ActionEvent evt) {
    }

    private void cariWilayahButtonActionPerformed(ActionEvent evt) {
        SearchDialog sd = new SearchDialog(null, true, "Cari data Provinsi / Kota / Kecamatan / Kelurahan");
        sd.addSearchDialogListener(new searchRegionDialogEventHandler());
        int w = sd.getWidth();
        int h = sd.getHeight();
        sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
        sd.setVisible(true);
    }

    private void cancelButtonActionPerformed(ActionEvent evt) {
        this.closeWindow();
    }

    private void saveButtonActionPerformed(ActionEvent evt) {
        this.saveData();
    }

    private void saveButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.saveData();
        }
    }

    private void populateFormRegion(Region selectedRegion) {
        this.provinsiField.setText(selectedRegion.getProvinsi());
        this.kotaField.setText(selectedRegion.getKota());
        this.kecamatanField.setText(selectedRegion.getKecamatan());
        this.kelurahanField.setText(selectedRegion.getKelurahan());
        this.idProvinsiText.setText(selectedRegion.getIdProvinsi());
        this.idKotaText.setText(selectedRegion.getIdKota());
        this.idKecamatanText.setText(selectedRegion.getIdKecamatan());
        this.idKelurahanText.setText(selectedRegion.getIdKelurahan());
    }

    private void populateForm() {
        this.alamatLengkapField.setText(this.alamatLengkap);
        this.namaPenerimaField.setText(this.namaCustomer);
        this.teleponPenerimaField.setText(this.telepon);
    }

    private void saveData() {
        String alamat = this.alamatLengkapField.getText();
        String namaPenerima = this.namaPenerimaField.getText();
        String teleponPenerima = this.teleponPenerimaField.getText();
        String idRegKelurahan = this.idKelurahanText.getText();
        if (this.customerAlamat == null) {
            this.customerAlamat = new CustomerAlamat();
        }
        this.customerAlamat.setAlamat(alamat);
        this.customerAlamat.setPenerima(namaPenerima);
        this.customerAlamat.setTelepon(teleponPenerima);
        this.customerAlamat.setIdRegKelurahan(idRegKelurahan);
        this.customerAlamat.setIdCustomer(this.idCustomer);
        this.customerAlamat.setIsDefault(true);
        int result = 0;
        if (this.customerAlamat.getId() != null) {
            System.out.println("Update -> " + this.customerAlamat.getAlamat());
            result = this.regionService.updateCustomerAlamat(this.customerAlamat);
        } else {
            this.customerAlamat.setJudul("Utama");
            System.out.println("Insert -> " + this.customerAlamat.getAlamat());
            result = this.regionService.insertCustomerAlamat(this.customerAlamat);
        }
        if (result > 0) {
            this.listener.wilayahSave(this.customerAlamat.getAlamat(), this.kotaField.getText(), this.teleponPenerimaField.getText());
            this.closeWindow();
        } else {
            JOptionPane.showMessageDialog(null, "Gagal insert / update alamat customer");
        }
    }

    private class searchPanelRegionEventHandler
    implements SearchPanelListener {
        private searchPanelRegionEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            int index = Integer.parseInt(chooseValue);
            Region selectedRegion = AlamatOnline.this.regionList.get(index);
            AlamatOnline.this.populateFormRegion(selectedRegion);
        }
    }

    private class searchRegionDialogEventHandler
    implements SearchDialogListener {
        private searchRegionDialogEventHandler() {
        }

        @Override
        public void searchClick(String searchWord) {
            Object[] colName = new String[]{"PROVINSI", "KOTA / KABUPATEN", "KECAMATAN", "KELURAHAN"};
            DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
            AlamatOnline.this.regionList = AlamatOnline.this.regionService.getRegion(searchWord);
            for (Region region : AlamatOnline.this.regionList) {
                Object[] temp = new String[]{region.getProvinsi(), region.getKota(), region.getKecamatan(), region.getKelurahan()};
                dtm.addRow(temp);
            }
            SearchPanelWilayah sp = new SearchPanelWilayah(null, true, dtm, "Data Region");
            sp.addSearchPanelListener(new searchPanelRegionEventHandler());
            sp.setVisible(true);
        }

        public void dispose() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}

