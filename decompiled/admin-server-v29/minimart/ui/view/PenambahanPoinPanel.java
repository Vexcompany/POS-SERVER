/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jxl.Sheet
 *  jxl.Workbook
 *  jxl.read.biff.BiffException
 */
package minimart.ui.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import minimart.ui.listener.DefaultPanelListener;

public class PenambahanPoinPanel
extends JPanel {
    private JButton exitButton;
    private JLabel fileLbl;
    private JButton jButton1;
    private JScrollPane jScrollPane1;
    private JButton prosesBtn;
    private JTable reportTable;
    private String[] reportColumn = new String[]{"ID Customer", "Nama", "Jumlah Poin"};
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private DefaultPanelListener defaultPanelListener;
    private int screenWidth;
    private int screenHeight;
    private JFileChooser jChooser = new JFileChooser();

    public PenambahanPoinPanel() {
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    }

    public void addListener(DefaultPanelListener listener) {
        this.defaultPanelListener = listener;
    }

    private void fillData(File file) {
        Workbook workbook = null;
        try {
            try {
                workbook = Workbook.getWorkbook((File)file);
            }
            catch (IOException ex) {
                Logger.getLogger(PenambahanPoinPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            Sheet sheet = workbook.getSheet(0);
            for (int j = 1; j < sheet.getRows(); ++j) {
                Object[] rowValue = new Object[]{sheet.getCell(0, j).getContents(), sheet.getCell(1, j).getContents(), sheet.getCell(2, j).getContents()};
                this.reportTableModel.addRow(rowValue);
            }
        }
        catch (BiffException e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {
        this.jScrollPane1 = new JScrollPane();
        this.reportTable = new JTable();
        this.exitButton = new JButton();
        this.jButton1 = new JButton();
        this.fileLbl = new JLabel();
        this.prosesBtn = new JButton();
        this.setPreferredSize(new Dimension(800, 600));
        this.reportTable.setModel(this.reportTableModel);
        this.jScrollPane1.setViewportView(this.reportTable);
        this.exitButton.setText("Exit");
        this.exitButton.setPreferredSize(new Dimension(83, 23));
        this.exitButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PenambahanPoinPanel.this.exitButtonActionPerformed(evt);
            }
        });
        this.jButton1.setText("Select Excel File");
        this.jButton1.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PenambahanPoinPanel.this.jButton1ActionPerformed(evt);
            }
        });
        this.fileLbl.setText("-");
        this.prosesBtn.setText("Proses");
        this.prosesBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PenambahanPoinPanel.this.prosesBtnActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jScrollPane1, -1, 780, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jButton1, -1, -1, Short.MAX_VALUE).addComponent(this.prosesBtn, -1, -1, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.exitButton, -2, 109, -2).addComponent(this.fileLbl, -2, 275, -2)).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jButton1).addComponent(this.fileLbl)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.exitButton, -2, -1, -2).addComponent(this.prosesBtn)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jScrollPane1, -2, 465, -2).addGap(61, 61, 61)));
    }

    private void exitButtonActionPerformed(ActionEvent evt) {
        this.defaultPanelListener.exitClick();
    }

    private void jButton1ActionPerformed(ActionEvent evt) {
        this.jChooser.showOpenDialog(null);
        File file = this.jChooser.getSelectedFile();
        if (!file.getName().endsWith("xls")) {
            JOptionPane.showMessageDialog(null, "Please select only Excel file.", "Error", 0);
        } else {
            this.fileLbl.setText(file.getPath());
            this.fillData(file);
        }
    }

    private void prosesBtnActionPerformed(ActionEvent evt) {
    }
}

