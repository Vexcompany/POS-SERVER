/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view.report;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import minimart.engine.model.PenerimaanBs;
import minimart.engine.service.PenerimaanBsService;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.view.report.ReportPenerimaanBsDetail;

public class ReportPenerimaanBsPanel
extends JPanel {
    private JButton detailButton;
    private JFormattedTextField endDateField;
    private JButton exitButton;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JScrollPane jScrollPane1;
    private JTable reportTable;
    private JFormattedTextField startDateField;
    private String[] reportColumn = new String[]{"ID", "Tanggal", "Karyawan", "Lokasi", "Status"};
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private PenerimaanBsService penerimaanBsService = new PenerimaanBsService();
    private Date startDate;
    private Date endDate;
    private DefaultPanelListener defaultPanelListener;
    private int screenWidth;
    private int screenHeight;
    private String lokasi;
    private String status;

    public ReportPenerimaanBsPanel(Date startDate, Date endDate, String lokasi, String status) {
        this.initComponents();
        this.startDate = startDate;
        this.endDate = endDate;
        this.startDateField.setValue(startDate);
        this.endDateField.setValue(endDate);
        this.lokasi = lokasi;
        this.status = status;
        this.loadData();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    }

    public void addListener(DefaultPanelListener listener) {
        this.defaultPanelListener = listener;
    }

    private void loadData() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String startDateString = simpleDateFormat.format(this.startDate);
        String endDateString = simpleDateFormat.format(this.endDate);
        List<PenerimaanBs> penerimaanBses = this.penerimaanBsService.getPenerimaanBsByDate(startDateString, endDateString, this.lokasi, this.status);
        for (PenerimaanBs penerimaanBs : penerimaanBses) {
            Object[] rowData = new Object[]{penerimaanBs.getId(), penerimaanBs.getTanggal(), penerimaanBs.getKaryawanId(), penerimaanBs.getLokasi(), penerimaanBs.getStatus()};
            this.reportTableModel.addRow(rowData);
        }
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.startDateField = new JFormattedTextField();
        this.endDateField = new JFormattedTextField();
        this.jScrollPane1 = new JScrollPane();
        this.reportTable = new JTable();
        this.detailButton = new JButton();
        this.exitButton = new JButton();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setText("Tanggal Awal");
        this.jLabel2.setText("Tanggal Akhir");
        this.startDateField.setEditable(false);
        this.startDateField.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(DateFormat.getDateInstance(1))));
        this.startDateField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPenerimaanBsPanel.this.startDateFieldKeyPressed(evt);
            }
        });
        this.endDateField.setEditable(false);
        this.endDateField.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(DateFormat.getDateInstance(1))));
        this.reportTable.setModel(this.reportTableModel);
        this.jScrollPane1.setViewportView(this.reportTable);
        this.detailButton.setText("Detail");
        this.detailButton.setPreferredSize(new Dimension(83, 23));
        this.detailButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPenerimaanBsPanel.this.detailButtonActionPerformed(evt);
            }
        });
        this.exitButton.setText("Exit");
        this.exitButton.setPreferredSize(new Dimension(83, 23));
        this.exitButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPenerimaanBsPanel.this.exitButtonActionPerformed(evt);
            }
        });
        this.exitButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPenerimaanBsPanel.this.exitButtonKeyPressed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1, -1, 780, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(292, 292, 292).addComponent(this.detailButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.exitButton, -2, -1, -2)).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jLabel2)).addGap(24, 24, 24).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.startDateField, -1, 122, Short.MAX_VALUE).addComponent(this.endDateField)))).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(17, 17, 17).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.startDateField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.endDateField, -2, -1, -2)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.detailButton, -2, -1, -2).addComponent(this.exitButton, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, 440, -2).addGap(46, 46, 46)));
    }

    private void exitButtonActionPerformed(ActionEvent evt) {
        this.defaultPanelListener.exitClick();
    }

    private void detailButtonActionPerformed(ActionEvent evt) {
        if (this.reportTable.getSelectedRow() != -1) {
            String id = this.reportTableModel.getValueAt(this.reportTable.getSelectedRow(), 0).toString();
            ReportPenerimaanBsDetail rpd = new ReportPenerimaanBsDetail(null, true, id);
            int w = rpd.getWidth();
            int h = rpd.getHeight();
            rpd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            rpd.setVisible(true);
        }
    }

    private void startDateFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.defaultPanelListener.exitClick();
        }
    }

    private void exitButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.defaultPanelListener.exitClick();
        }
    }
}

