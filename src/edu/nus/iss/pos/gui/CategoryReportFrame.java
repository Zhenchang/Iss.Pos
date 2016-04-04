/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.gui;

import edu.nus.iss.pos.core.Category;
import edu.nus.iss.pos.core.services.IInventoryService;
import edu.nus.iss.pos.dao.repositories.UnitOfWork;
import edu.nus.iss.pos.services.InventoryService;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 *
 * @author zz
 */
public class CategoryReportFrame extends javax.swing.JFrame {
    
    private IInventoryService inventoryService;

    /**
     * Creates new form CategoryReportFrame
     */
    public CategoryReportFrame(IInventoryService inventoryService) throws Exception {
        initComponents();
        this.setContent(inventoryService);
    }
    
    private void setContent(IInventoryService inventoryService) throws Exception{
        this.inventoryService = inventoryService;
        TableModel tableModel = new CategoryTableModel(this.inventoryService.getAllCategory());
        this.jTable1.setModel(tableModel);
        TableColumn tc = this.jTable1.getColumnModel().getColumn(2);
        
        TableColumn tc1 = this.jTable1.getColumnModel().getColumn(3);
        
        

        tc.setCellEditor(new ButtonEditor());
        tc.setCellRenderer(new ButtonRenderer());
        
        tc1.setCellEditor(new ViewVendorEditor());
        tc1.setCellRenderer(new ViewVendorRenderer());

    }
    
    class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener{
        
        private JButton jButton;
        private JTable jTable;
        private int row;
        private int column;
        
        public ButtonEditor(){
            this.jButton = new JButton("save");
            jButton.setOpaque(true);
            jButton.addActionListener(this);
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.jTable = table;
            this.row = row;
            this.column = column;
            return this.jButton;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("clicked");
            CategoryTableModel tableModel = (CategoryTableModel) this.jTable.getModel();
            
            try {
                
                inventoryService.updateCategory(tableModel.getCategoryAt(this.row));
            } catch (Exception ex) {
                Logger.getLogger(CategoryReportFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
        
    class ButtonRenderer implements TableCellRenderer {
        
        JButton button = null;
        
        public ButtonRenderer() {
            button = new JButton("save");
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
           // button.setEnabled((boolean) value);
            return button;
        }
    }
    
    
    class ViewVendorEditor extends AbstractCellEditor implements TableCellEditor, ActionListener{
        
        private JButton jButton;
        private JTable jTable;
        private int row;
        private int column;
        
        public ViewVendorEditor(){
            this.jButton = new JButton("view vendor");
            jButton.setOpaque(true);
            jButton.addActionListener(this);
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.jTable = table;
            this.row = row;
            this.column = column;
            return this.jButton;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            CategoryTableModel tableModel = (CategoryTableModel) this.jTable.getModel();
            
            try {
                VendorReportFrame dialog = new VendorReportFrame(null, true, inventoryService, tableModel.getCategoryAt(this.row));
                dialog.setVisible(true);
                dialog.dispose();
            } catch (Exception ex) {
                Logger.getLogger(CategoryReportFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
        
    class ViewVendorRenderer implements TableCellRenderer {
        
        JButton button = null;
        
        public ViewVendorRenderer() {
            button = new JButton("view vendor");
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
           // button.setEnabled((boolean) value);
            return button;
        }
    }
    
    
    
    class CategoryTableModel extends AbstractTableModel {
        
        private List<Category> categoryList;
        private final String [] columns = {"id ","name","",""};
        
        public CategoryTableModel(Iterable<Category> categoryList){
            super();
            this.categoryList = new ArrayList<Category>();
            for(Category category : categoryList){
                this.categoryList.add(category);
            }
        }

        @Override
        public int getRowCount() {
            return this.categoryList.size();
        }
        
        @Override
        public String getColumnName(int cloumnIndex){
            return this.columns[cloumnIndex];
        }

        @Override
        public int getColumnCount() {
            return this.columns.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            switch(columnIndex){
                case 0:
                    return this.categoryList.get(rowIndex).getKey();
                case 1:
                    return this.categoryList.get(rowIndex).getName();
                default:
                    return null;
            }
        }
        
        @Override
        public void setValueAt(Object value, int row, int column){
            if(column == 1){
                this.categoryList.get(row).setName(value.toString());
            }
            fireTableCellUpdated(row, column);
        }
        
        public Category getCategoryAt(int rowIndex){
            return this.categoryList.get(rowIndex);
        }
        
        public boolean isCellEditable(int row, int column){
            if(column == 0){
                return false;
            }
            return true;
        }
        
    }
    
    class TextFieldRender implements TableCellRenderer {
        
        JTextField field = null;
        
        public TextFieldRender() {
            field = new JTextField();
            field.setBackground(Color.GRAY);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return field;
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

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("cancel");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("add");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

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
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        new AddCagagoryFrame(inventoryService).setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CategoryReportFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CategoryReportFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CategoryReportFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CategoryReportFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    IInventoryService inventoryService = new InventoryService(new UnitOfWork());
                    new CategoryReportFrame(inventoryService).setVisible(true);
                   
                    
                } catch (Exception ex) {
                    Logger.getLogger(CategoryReportFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
