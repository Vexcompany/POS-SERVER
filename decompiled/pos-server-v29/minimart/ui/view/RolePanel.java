/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import minimart.engine.controller.Helper;
import minimart.engine.model.Role;
import minimart.engine.service.RoleService;
import minimart.ui.listener.DefaultPanelListener;

public class RolePanel
extends JPanel {
    private JButton exitBtn;
    private JLabel jLabel1;
    private JScrollPane jScrollPane1;
    private JTable jTable1;
    private JComboBox<String> roleCombo;
    private JButton saveBtn;
    private JButton tampilkanBtn;
    private Helper helper = Helper.getInstance();
    private String[] roleColumn = new String[]{"ID", "Role", "Menu", "Access"};
    private DefaultTableModel roleTableModel = new DefaultTableModel(new String[0][1], this.roleColumn){

        @Override
        public boolean isCellEditable(int row, int col) {
            return true;
        }
    };
    private final RoleService roleService = new RoleService();
    private DefaultPanelListener defaultPanelListener;

    public RolePanel() {
        this.initComponents();
        this.jScrollPane1.setHorizontalScrollBarPolicy(30);
        this.loadData("SEMUA");
    }

    private void clearTable() {
        int size = this.roleTableModel.getRowCount();
        for (int a = 0; a < size; ++a) {
            this.roleTableModel.removeRow(0);
        }
    }

    private void loadData(String search) {
        this.clearTable();
        if (search.equals("SEMUA")) {
            search = "";
        }
        List<Role> roles = this.roleService.getRoles(search);
        for (Role role : roles) {
            Object[] rowValue = new Object[]{role.getId(), role.getRole(), role.getMenu(), role.getAccess()};
            this.roleTableModel.addRow(rowValue);
        }
    }

    public void addListener(DefaultPanelListener listener) {
        this.defaultPanelListener = listener;
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.roleCombo = new JComboBox();
        this.jScrollPane1 = new JScrollPane();
        this.jTable1 = new JTable();
        this.saveBtn = new JButton();
        this.exitBtn = new JButton();
        this.tampilkanBtn = new JButton();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setText("ROLE");
        this.roleCombo.setModel(new DefaultComboBoxModel<String>(new String[]{"SEMUA", "DIREKTUR", "MANAGER", "ADMIN", "INVENTORY", "STAF INVENTORY", "ACCOUNTING", "KASIR"}));
        this.jTable1.setModel(this.roleTableModel);
        this.jScrollPane1.setViewportView(this.jTable1);
        this.saveBtn.setText("Save");
        this.saveBtn.setPreferredSize(new Dimension(72, 23));
        this.saveBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                RolePanel.this.saveBtnActionPerformed(evt);
            }
        });
        this.exitBtn.setText("Exit");
        this.exitBtn.setPreferredSize(new Dimension(72, 23));
        this.exitBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                RolePanel.this.exitBtnActionPerformed(evt);
            }
        });
        this.tampilkanBtn.setText("Tampilkan");
        this.tampilkanBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                RolePanel.this.tampilkanBtnActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, -1, 780, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addGap(18, 18, 18).addComponent(this.roleCombo, -2, 106, -2)).addGroup(layout.createSequentialGroup().addComponent(this.tampilkanBtn).addGap(5, 5, 5).addComponent(this.saveBtn, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.exitBtn, -2, -1, -2))).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.roleCombo, -2, -1, -2)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.saveBtn, -2, -1, -2).addComponent(this.exitBtn, -2, -1, -2).addComponent(this.tampilkanBtn)).addGap(12, 12, 12).addComponent(this.jScrollPane1, -1, 505, Short.MAX_VALUE).addContainerGap()));
    }

    private void exitBtnActionPerformed(ActionEvent evt) {
        this.defaultPanelListener.exitClick();
    }

    private void tampilkanBtnActionPerformed(ActionEvent evt) {
        this.loadData(this.roleCombo.getSelectedItem().toString());
    }

    private void saveBtnActionPerformed(ActionEvent evt) {
        ArrayList<Role> roleData = new ArrayList<Role>();
        for (int a = 0; a < this.roleTableModel.getRowCount(); ++a) {
            Role role = this.roleService.getRoleById(this.roleTableModel.getValueAt(a, 0).toString());
            role.setAccess(Integer.parseInt(this.roleTableModel.getValueAt(a, 3).toString()));
            role.setUpdated(new Date());
            role.setUpdatedBy(this.helper.getKaryawan().getRole());
            roleData.add(role);
        }
        int result = this.roleService.updateRole(roleData);
        if (result == 1) {
            JOptionPane.showMessageDialog(null, "Data Telah Tersimpan");
        } else {
            JOptionPane.showMessageDialog(null, "Penyimpanan Data Gagal");
        }
    }

    public class MyDefaultTableModel
    extends DefaultTableModel {
        private boolean[][] editable_cells;

        private MyDefaultTableModel(int rows, int cols) {
            super(rows, cols);
            this.editable_cells = new boolean[rows][cols];
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return this.editable_cells[row][column];
        }

        public void setCellEditable(int row, int col, boolean value) {
            this.editable_cells[row][col] = value;
            this.fireTableCellUpdated(row, col);
        }
    }
}

