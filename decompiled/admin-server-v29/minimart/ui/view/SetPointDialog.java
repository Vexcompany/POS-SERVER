/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import minimart.engine.controller.Helper;
import minimart.engine.model.Konstanta;
import minimart.engine.service.KonstantaService;

public class SetPointDialog
extends JDialog {
    private JCheckBox aktifkanCheck;
    private JButton cancelButton;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JButton okButton;
    private JRadioButton omsetRadio;
    private JTextField poinLainField;
    private JTextField poinRokokField;
    private JTextField profitField;
    private JRadioButton profitRadio;
    private KonstantaService konstantaService = new KonstantaService();
    ButtonGroup bgroup = new ButtonGroup();

    public SetPointDialog(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        this.poinRokokField.requestFocus();
        this.poinRokokField.selectAll();
        this.cekDataDBPoin();
        this.loadData();
        this.bgroup.add(this.profitRadio);
        this.bgroup.add(this.omsetRadio);
    }

    private void cekDataDBPoin() {
        Konstanta konstanta;
        String poinRokok = this.konstantaService.getConstantValue("pointRokok");
        String poinLain = this.konstantaService.getConstantValue("pointLain");
        String aktifkanPoin = this.konstantaService.getConstantValue("aktifkanPoint");
        String poinProfit = this.konstantaService.getConstantValue("poinProfit");
        String kategoriPoin = this.konstantaService.getConstantValue("kategoriPoin");
        if (poinRokok == null) {
            konstanta = new Konstanta();
            konstanta.setKonstan("poinRokok");
            konstanta.setValue("0");
            this.konstantaService.createConstant(konstanta);
            JOptionPane.showMessageDialog(null, "Pembuatan Konstan PoinRokok");
        }
        if (poinLain == null) {
            konstanta = new Konstanta();
            konstanta.setKonstan("poinLain");
            konstanta.setValue("0");
            this.konstantaService.createConstant(konstanta);
            JOptionPane.showMessageDialog(null, "Pembuatan Konstan PoinLain");
        }
        if (aktifkanPoin == null) {
            konstanta = new Konstanta();
            konstanta.setKonstan("aktifkanPoint");
            konstanta.setValue("YES");
            this.konstantaService.createConstant(konstanta);
            JOptionPane.showMessageDialog(null, "Pembuatan Konstan AktifkanPoin");
        }
        if (poinProfit == null) {
            konstanta = new Konstanta();
            konstanta.setKonstan("poinProfit");
            konstanta.setValue("2000");
            this.konstantaService.createConstant(konstanta);
            JOptionPane.showMessageDialog(null, "Pembuatan Konstan PoinProfit");
        }
        if (kategoriPoin == null) {
            konstanta = new Konstanta();
            konstanta.setKonstan("kategoriPoin");
            konstanta.setValue("OMSET");
            this.konstantaService.createConstant(konstanta);
            JOptionPane.showMessageDialog(null, "Pembuatan Konstan KategoriPoin");
        }
    }

    private void loadData() {
        String poinRokok = this.konstantaService.getConstantValue("pointRokok");
        String poinLain = this.konstantaService.getConstantValue("pointLain");
        String aktifkanPoin = this.konstantaService.getConstantValue("aktifkanPoint");
        String poinProfit = this.konstantaService.getConstantValue("poinProfit");
        String kategoriPoin = this.konstantaService.getConstantValue("kategoriPoin");
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
        System.out.println(kategoriPoin);
        if (kategoriPoin.equals("OMSET")) {
            this.omsetRadio.setSelected(true);
        } else if (kategoriPoin.equals("PROFIT")) {
            this.profitRadio.setSelected(true);
        }
        this.poinRokokField.setText(poinRokok);
        this.poinLainField.setText(poinLain);
        this.profitField.setText(poinProfit);
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.poinRokokField = new JTextField();
        this.poinLainField = new JTextField();
        this.jLabel3 = new JLabel();
        this.okButton = new JButton();
        this.cancelButton = new JButton();
        this.aktifkanCheck = new JCheckBox();
        this.omsetRadio = new JRadioButton();
        this.profitRadio = new JRadioButton();
        this.jLabel4 = new JLabel();
        this.profitField = new JTextField();
        this.jLabel5 = new JLabel();
        this.setDefaultCloseOperation(2);
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setText("Poin Rokok");
        this.jLabel2.setText("Poin Lain");
        this.poinRokokField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                SetPointDialog.this.poinRokokFieldKeyPressed(evt);
            }
        });
        this.poinLainField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                SetPointDialog.this.poinLainFieldKeyPressed(evt);
            }
        });
        this.jLabel3.setFont(new Font("Tahoma", 1, 14));
        this.jLabel3.setText("Setting Poin Belanja");
        this.okButton.setText("OK");
        this.okButton.setPreferredSize(new Dimension(65, 23));
        this.okButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                SetPointDialog.this.okButtonActionPerformed(evt);
            }
        });
        this.okButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                SetPointDialog.this.okButtonKeyPressed(evt);
            }
        });
        this.cancelButton.setText("Cancel");
        this.cancelButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                SetPointDialog.this.cancelButtonActionPerformed(evt);
            }
        });
        this.cancelButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                SetPointDialog.this.cancelButtonKeyPressed(evt);
            }
        });
        this.aktifkanCheck.setText("Aktifkan");
        this.aktifkanCheck.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
                SetPointDialog.this.aktifkanCheckMouseClicked(evt);
            }
        });
        this.omsetRadio.setText("Berdasarkan Omset");
        this.profitRadio.setText("Berdasarkan Profit");
        this.jLabel4.setText("Profit :");
        this.jLabel5.setText("= 1 Poin belanja");
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(22, 22, 22).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel3).addGap(44, 44, 44).addComponent(this.aktifkanCheck)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addGap(21, 21, 21).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2).addComponent(this.jLabel1)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.poinRokokField, -2, 69, -2).addComponent(this.poinLainField, -2, 69, -2))).addComponent(this.omsetRadio).addGroup(layout.createSequentialGroup().addComponent(this.okButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.profitField, GroupLayout.Alignment.TRAILING).addComponent(this.cancelButton)))).addGroup(layout.createSequentialGroup().addGap(21, 21, 21).addComponent(this.jLabel4)).addComponent(this.profitRadio)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel5))).addContainerGap(100, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.aktifkanCheck)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.omsetRadio).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.poinRokokField, -2, -1, -2)).addGap(9, 9, 9).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.poinLainField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.profitRadio).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4).addComponent(this.profitField, -2, -1, -2).addComponent(this.jLabel5)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.okButton, -2, -1, -2).addComponent(this.cancelButton)).addContainerGap()));
        this.pack();
    }

    private void poinRokokFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (Helper.getInstance().isInteger(this.poinRokokField.getText())) {
                this.poinLainField.requestFocus();
                this.poinLainField.selectAll();
                this.poinRokokField.setBackground(Color.white);
            } else {
                this.poinRokokField.setBackground(Color.red);
            }
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void poinLainFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (Helper.getInstance().isInteger(this.poinLainField.getText())) {
                this.okButton.requestFocus();
                this.poinLainField.setBackground(Color.white);
            } else {
                this.poinRokokField.setBackground(Color.red);
            }
        } else if (evt.getKeyCode() == 27 || evt.getKeyCode() == 38) {
            this.poinRokokField.requestFocus();
            this.poinRokokField.selectAll();
        }
    }

    private void okButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.saveData();
        } else if (evt.getKeyCode() == 27 || evt.getKeyCode() == 27) {
            this.poinLainField.requestFocus();
            this.poinLainField.selectAll();
        } else if (evt.getKeyCode() == 39) {
            this.cancelButton.requestFocus();
        }
    }

    private void cancelButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.dispose();
        } else if (evt.getKeyCode() == 27 || evt.getKeyCode() == 27) {
            this.poinLainField.requestFocus();
            this.poinLainField.selectAll();
        } else if (evt.getKeyCode() == 37) {
            this.okButton.requestFocus();
        }
    }

    private void cancelButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void aktifkanCheckMouseClicked(MouseEvent evt) {
        this.poinRokokField.requestFocus();
        this.poinRokokField.selectAll();
    }

    private void okButtonActionPerformed(ActionEvent evt) {
        this.saveData();
    }

    private void saveData() {
        Konstanta pointRokok = new Konstanta();
        pointRokok.setKonstan("pointRokok");
        pointRokok.setValue(this.poinRokokField.getText());
        Konstanta pointLain = new Konstanta();
        pointLain.setKonstan("pointLain");
        pointLain.setValue(this.poinLainField.getText());
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
        int pointRokokResult = this.konstantaService.updateConstant(pointRokok);
        int pointLainResult = this.konstantaService.updateConstant(pointLain);
        int aktifkanResult = this.konstantaService.updateConstant(aktifkan);
        int kategoriResult = this.konstantaService.updateConstant(kategori);
        int poinProfitResult = this.konstantaService.updateConstant(poinProfit);
        if (pointLainResult == 1 && pointRokokResult == 1 && aktifkanResult == 1 && kategoriResult == 1 && poinProfitResult == 1) {
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
            this.cancelButton.requestFocus();
        } else {
            JOptionPane.showMessageDialog(null, "Penyimpanan Gagal" + poinProfitResult + "," + pointRokokResult + "," + aktifkanResult + "," + kategoriResult + "," + poinProfitResult);
        }
    }
}

