/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import minimart.ui.listener.ConfirmationDialogListener;

public class PopupPromo
extends JDialog {
    private JTable dataTable;
    private JLabel jLabel1;
    private JScrollPane jScrollPane3;
    private JLabel labelPromo;
    private JButton noBtn;
    private JButton yesBtn;
    private List<Object[]> dataPromo;
    private String[] transactionColumn = new String[]{"ID", "Nama Barang", "Harga", "Satuan", "Keterangan"};
    private DefaultTableModel promoTableModel = new DefaultTableModel(new String[0][1], this.transactionColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private ConfirmationDialogListener confirmationDialogListener;
    String tipePromo;

    public PopupPromo(Frame parent, boolean modal, List<Object[]> dataPromo, String promo) {
        super(parent, modal);
        this.initComponents();
        this.tipePromo = promo;
        if (promo.equals("TEBUSMURAH")) {
            this.labelPromo.setText("Pilih salah satu promo TEBUS MURAH dibawah ini");
        } else if (promo.equals("PWP")) {
            this.labelPromo.setText("Pilih salah satu promo PWP dibawah ini");
        }
        this.dataTable.requestFocus();
        this.dataPromo = dataPromo;
        TableColumn col1 = this.dataTable.getColumnModel().getColumn(1);
        col1.setPreferredWidth(250);
        for (int a = 0; a < dataPromo.size(); ++a) {
            this.promoTableModel.addRow(dataPromo.get(a));
        }
    }

    public void addListener(ConfirmationDialogListener confirmationDialogListener) {
        this.confirmationDialogListener = confirmationDialogListener;
    }

    private void initComponents() {
        this.labelPromo = new JLabel();
        this.noBtn = new JButton();
        this.yesBtn = new JButton();
        this.jScrollPane3 = new JScrollPane();
        this.dataTable = new JTable();
        this.jLabel1 = new JLabel();
        this.setDefaultCloseOperation(2);
        this.labelPromo.setText("Pilih salah satu PROMO TEBUS MURAH dibawah ini:");
        this.noBtn.setText("Tidak");
        this.noBtn.setPreferredSize(new Dimension(73, 29));
        this.noBtn.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
                PopupPromo.this.noBtnMouseClicked(evt);
            }
        });
        this.yesBtn.setText("Ya");
        this.yesBtn.setPreferredSize(new Dimension(73, 29));
        this.yesBtn.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseClicked(MouseEvent evt) {
                PopupPromo.this.yesBtnMouseClicked(evt);
            }
        });
        this.dataTable.setModel(this.promoTableModel);
        this.dataTable.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PopupPromo.this.dataTableKeyPressed(evt);
            }
        });
        this.jScrollPane3.setViewportView(this.dataTable);
        this.jLabel1.setFont(new Font("Tahoma", 1, 16));
        this.jLabel1.setForeground(new Color(255, 0, 51));
        this.jLabel1.setText("Tekan ESC untuk keluar");
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.labelPromo).addGap(0, 0, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.yesBtn, -2, -1, -2).addGap(3, 3, 3).addComponent(this.noBtn, -2, -1, -2)).addComponent(this.jScrollPane3, -1, 601, Short.MAX_VALUE)).addContainerGap()))));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.labelPromo).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane3, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.noBtn, -2, -1, -2).addComponent(this.yesBtn, -2, -1, -2).addComponent(this.jLabel1)).addContainerGap()));
        this.pack();
    }

    private void noBtnMouseClicked(MouseEvent evt) {
        this.confirmationDialogListener.noClick();
        this.dispose();
    }

    private void yesBtnMouseClicked(MouseEvent evt) {
        this.confirmationDialogListener.yesClick();
        this.dispose();
    }

    private void dataTableKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }
}

