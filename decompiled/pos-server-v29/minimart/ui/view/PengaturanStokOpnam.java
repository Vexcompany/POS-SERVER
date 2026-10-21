/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle;
import minimart.engine.service.KonstantaService;

public class PengaturanStokOpnam
extends JDialog {
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JButton keluarBtn;
    private JCheckBox metodeCheck;
    private JCheckBox sembunyikanCheck;
    private JButton simpanBtn;
    private KonstantaService konstantaService = new KonstantaService();

    public PengaturanStokOpnam(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        String hide = this.konstantaService.getConstantValue("hideStock");
        String blindSom = this.konstantaService.getConstantValue("blindSom");
        if (hide.equals("YES")) {
            this.sembunyikanCheck.setSelected(true);
        } else {
            this.sembunyikanCheck.setSelected(false);
        }
        if (blindSom.equals("YES")) {
            this.metodeCheck.setSelected(true);
        } else {
            this.metodeCheck.setSelected(false);
        }
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.sembunyikanCheck = new JCheckBox();
        this.metodeCheck = new JCheckBox();
        this.keluarBtn = new JButton();
        this.simpanBtn = new JButton();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("Sembunyikan Stok");
        this.jLabel2.setText("Metode SOM");
        this.sembunyikanCheck.setText("YES");
        this.metodeCheck.setText("BLIND SO");
        this.keluarBtn.setText("Keluar");
        this.keluarBtn.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
                PengaturanStokOpnam.this.keluarBtnMouseClicked(evt);
            }
        });
        this.simpanBtn.setText("Simpan");
        this.simpanBtn.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
                PengaturanStokOpnam.this.simpanBtnMouseClicked(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(33, 33, 33).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jLabel2)).addGap(42, 42, 42).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.sembunyikanCheck).addComponent(this.metodeCheck)).addContainerGap(75, Short.MAX_VALUE)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap(-1, Short.MAX_VALUE).addComponent(this.simpanBtn, -2, 97, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.keluarBtn, -2, 92, -2).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.sembunyikanCheck)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.metodeCheck)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.keluarBtn).addComponent(this.simpanBtn)).addContainerGap()));
        this.pack();
    }

    private void keluarBtnMouseClicked(MouseEvent evt) {
        this.dispose();
    }

    private void simpanBtnMouseClicked(MouseEvent evt) {
        if (this.sembunyikanCheck.isSelected()) {
            this.konstantaService.updateConstant("hideStock", "YES");
        } else {
            this.konstantaService.updateConstant("hideStock", "NO");
        }
        if (this.metodeCheck.isSelected()) {
            this.konstantaService.updateConstant("blindSom", "YES");
        } else {
            this.konstantaService.updateConstant("blindSom", "NO");
        }
        JOptionPane.showMessageDialog(null, "Penyimpanan Berhasil");
    }
}

