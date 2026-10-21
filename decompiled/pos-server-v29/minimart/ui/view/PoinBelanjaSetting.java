/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import minimart.engine.model.Jenis;
import minimart.engine.model.Konstanta;
import minimart.engine.service.JenisService;
import minimart.engine.service.KonstantaService;
import minimart.ui.listener.DefaultPanelListener;

public class PoinBelanjaSetting
extends JPanel {
    private JCheckBox aktifkanCheck;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JScrollPane jScrollPane1;
    private JTable jTable1;
    private JButton keluarBtn;
    private JRadioButton omsetRadio;
    private JTextField profitField;
    private JRadioButton profitRadio;
    private JButton simpanBtn;
    private String[] tableCol = new String[]{"Jenis", "Sales"};
    private KonstantaService konstantaService = new KonstantaService();
    private DefaultPanelListener listener;
    private JenisService jenisService = new JenisService();
    private ButtonGroup bg = new ButtonGroup();
    private DefaultTableModel poinBelanjaTM = new DefaultTableModel(new String[0][1], this.tableCol){

        @Override
        public boolean isCellEditable(int row, int column) {
            return true;
        }
    };

    public PoinBelanjaSetting() {
        this.initComponents();
        this.bg.add(this.omsetRadio);
        this.bg.add(this.profitRadio);
        this.loadData();
    }

    public void addListener(DefaultPanelListener listener) {
        this.listener = listener;
    }

    private void loadData() {
        this.clearTable();
        String aktifkanPoin = this.konstantaService.getConstantValue("aktifkanPoint");
        String kategoriPoin = this.konstantaService.getConstantValue("kategoriPoin");
        String poinProfit = this.konstantaService.getConstantValue("poinProfit");
        this.profitField.setText(poinProfit);
        if (kategoriPoin.equals("OMSET")) {
            this.omsetRadio.setSelected(true);
        } else if (kategoriPoin.equals("PROFIT")) {
            this.profitRadio.setSelected(true);
        }
        if (aktifkanPoin.equals("YES")) {
            this.aktifkanCheck.setSelected(true);
        } else if (aktifkanPoin.equals("NO")) {
            this.aktifkanCheck.setSelected(false);
        }
        List<Jenis> datas = this.jenisService.getJenisses("");
        for (Jenis data : datas) {
            Object[] inputRow = new Object[]{data.getJenis(), data.getPoinSales()};
            this.poinBelanjaTM.addRow(inputRow);
        }
    }

    private void clearTable() {
        int size = this.poinBelanjaTM.getRowCount();
        for (int a = 0; a < size; ++a) {
            this.poinBelanjaTM.removeRow(0);
        }
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.omsetRadio = new JRadioButton();
        this.profitRadio = new JRadioButton();
        this.aktifkanCheck = new JCheckBox();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.jScrollPane1 = new JScrollPane();
        this.jTable1 = new JTable();
        this.keluarBtn = new JButton();
        this.simpanBtn = new JButton();
        this.profitField = new JTextField();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setText("Setting Poin Belanja");
        this.omsetRadio.setText("Berdasarkan Omset");
        this.profitRadio.setText("Berdasarkan Profit");
        this.aktifkanCheck.setText("Aktifkan");
        this.jLabel2.setText("Profit:");
        this.jLabel3.setText("= 1 Poin Belanja");
        this.jTable1.setModel(this.poinBelanjaTM);
        this.jScrollPane1.setViewportView(this.jTable1);
        this.keluarBtn.setText("Keluar");
        this.keluarBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PoinBelanjaSetting.this.keluarBtnActionPerformed(evt);
            }
        });
        this.simpanBtn.setText("Simpan");
        this.simpanBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PoinBelanjaSetting.this.simpanBtnActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.aktifkanCheck)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.profitRadio).addComponent(this.omsetRadio)).addGap(0, 0, Short.MAX_VALUE)))).addGroup(layout.createSequentialGroup().addGap(42, 42, 42).addComponent(this.jLabel2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.profitField, -2, 82, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel3).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.simpanBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.keluarBtn)).addComponent(this.jScrollPane1, GroupLayout.Alignment.TRAILING, -1, 790, Short.MAX_VALUE)).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.aktifkanCheck)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.omsetRadio).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.profitRadio).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.jLabel2).addComponent(this.keluarBtn).addComponent(this.simpanBtn).addComponent(this.profitField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, -1, -2).addContainerGap(85, Short.MAX_VALUE)));
    }

    private void keluarBtnActionPerformed(ActionEvent evt) {
        this.listener.exitClick();
    }

    private void simpanBtnActionPerformed(ActionEvent evt) {
        int count = 0;
        for (int a = 0; a < this.poinBelanjaTM.getRowCount(); ++a) {
            int result = this.jenisService.updateJenis(this.poinBelanjaTM.getValueAt(a, 0).toString(), Integer.parseInt(this.poinBelanjaTM.getValueAt(a, 1).toString()));
            if (result == 0) {
                JOptionPane.showMessageDialog(null, "Input data " + this.poinBelanjaTM.getValueAt(a, 0).toString() + " Gagal");
            }
            count += result;
        }
        Konstanta aktifkan = new Konstanta();
        aktifkan.setKonstan("aktifkanPoint");
        if (this.aktifkanCheck.isSelected()) {
            aktifkan.setValue("YES");
        } else {
            aktifkan.setValue("NO");
        }
        Konstanta kategori = new Konstanta();
        kategori.setKonstan("kategoriPoin");
        if (this.omsetRadio.isSelected()) {
            kategori.setValue("OMSET");
        } else if (this.profitRadio.isSelected()) {
            kategori.setValue("PROFIT");
        }
        Konstanta poinProfit = new Konstanta();
        poinProfit.setKonstan("poinProfit");
        poinProfit.setValue(this.profitField.getText());
        int aktifkanResult = this.konstantaService.updateConstant(aktifkan);
        int kategoriResult = this.konstantaService.updateConstant(kategori);
        int poinProfitResult = this.konstantaService.updateConstant(poinProfit);
        if (count == this.poinBelanjaTM.getRowCount() && aktifkanResult == 1 && kategoriResult == 1 && poinProfitResult == 1) {
            JOptionPane.showMessageDialog(null, "Penyimpanan Data Berhasil");
        } else {
            JOptionPane.showMessageDialog(null, "Ada data yang gagal tersimpan");
        }
    }
}

