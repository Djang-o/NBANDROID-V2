/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.nbandroid.netbeans.gradle.v2.project.distributions;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author arsi
 */
public class AndroidDistributionsPanel extends javax.swing.JPanel implements TableModel {

    private final List<DistributionPOJO> distributions;

    /**
     * Creates new form AndroidDistributionsPanel
     */
    public AndroidDistributionsPanel() {
        initComponents();
        jLabel1.setText("");
        distributions = AndroidDistributionsProvider.getDefault().getDistributions();
        jTable1.setModel(this);
        jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                // do some actions here, for example
                // print first column value from selected row
                int selectedRow = jTable1.getSelectedRow();
                if (selectedRow > -1) {
                    DistributionPOJO dist = distributions.get(selectedRow);
                    String tmp = "<html>";
                    DescriptionBlocksPOJO[] descriptionBlocks = dist.getDescriptionBlocks();
                    for (DescriptionBlocksPOJO db : descriptionBlocks) {
                        String body = db.getBody();
                        String title = db.getTitle();
                        tmp += "<h1>" + title + "</h1>";
                        tmp += body + "";
                    }
                    tmp += "</html>";
                    jLabel1.setText(tmp);
                } else {
                    jLabel1.setText("");
                }
            }

        });
        if (!distributions.isEmpty()) {
            jTable1.setRowSelectionInterval(distributions.size() - 1, distributions.size() - 1);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jLabel1 = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setRowHeight(40);
        jScrollPane1.setViewportView(jTable1);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(AndroidDistributionsPanel.class, "AndroidDistributionsPanel.jLabel1.text")); // NOI18N
        jScrollPane2.setViewportView(jLabel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
            .addComponent(jScrollPane2)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    @Override
    public int getRowCount() {
        return distributions.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Android platform version";
            case 1:
                return "API Level";
            case 2:
                return "Cumulative distribution";
            default:
                return "err";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    private static final NumberFormat NF = new DecimalFormat("0.0");
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String tmp = "<html>";
        switch (columnIndex) {
            case 0:
                tmp += "<b><span style='font-size:20px'>" + distributions.get(rowIndex).getVersion() + " </span></b>";
                tmp += distributions.get(rowIndex).getName();
                break;
            case 1:
                tmp += "<span style='font-size:17px'>" + distributions.get(rowIndex).getApiLevel() + " </span>";
                break;
            case 2:
                double supportedDistributionForApiLevel = 100 * getSupportedDistributionForApiLevel(Integer.valueOf(distributions.get(rowIndex).getApiLevel()));
                tmp += "<span style='font-size:20px'>" + NF.format(supportedDistributionForApiLevel) + "% </span>";
                break;
        }
        return tmp + "</html>";
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
    }

    public double getSupportedDistributionForApiLevel(int apiLevel) {
        if (apiLevel <= 0) {
            return 0;
        }
        double unsupportedSum = 0;
        for (DistributionPOJO d : distributions) {
            if (Integer.valueOf(d.getApiLevel()) >= apiLevel) {
                break;
            }
            unsupportedSum += Double.valueOf(d.getDistributionPercentage());
        }
        return 1 - unsupportedSum;
    }
}
