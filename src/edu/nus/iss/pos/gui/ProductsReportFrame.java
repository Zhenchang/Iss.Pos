/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.gui;

import edu.nus.iss.pos.core.Category;
import edu.nus.iss.pos.core.Product;
import edu.nus.iss.pos.core.services.IInventoryService;
import edu.nus.iss.pos.dao.repositories.UnitOfWork;
import edu.nus.iss.pos.services.InventoryService;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 *
 * @author zz
 */
public class ProductsReportFrame extends javax.swing.JFrame {

    /**
     * Creates new form ProductsReportFrame
     */
    
    private IInventoryService inventoryService;
    private List<Product> products;
    
    public ProductsReportFrame(IInventoryService inventoryService) throws Exception {
        initComponents();
        this.inventoryService = inventoryService;
        TableModel tableModel = new ProductTableModel(this.getAllProducts());
        this.jTable1.setModel(tableModel);
        
        TableColumn tc = this.jTable1.getColumnModel().getColumn(8);

        tc.setCellEditor(new ButtonEditor());
        tc.setCellRenderer(new ButtonRenderer());

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
            ProductTableModel tableMode = (ProductTableModel)this.jTable.getModel();
            Product product = tableMode.getProductAt(this.row);
            if(product.getBarcodeNumber().contains(",")){
                JOptionPane.showConfirmDialog(null,
                    "The field can not contain comma", "warning", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE, null);
                return ;
            } else if(product.getName().contains(",")){
                JOptionPane.showConfirmDialog(null,
                    "The field can not contain comma", "warning", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE, null);
                tableMode.fireTableStructureChanged();
                return ;
            }
            try {
                inventoryService.updateProduct(product);
            } catch (Exception ex) {
                Logger.getLogger(ProductsReportFrame.class.getName()).log(Level.SEVERE, null, ex);
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
    
    
    private Iterable<Product> getAllProducts() throws Exception{
        this.products = new ArrayList<Product>();
        Iterable<Product> tempProducts = new ArrayList<Product>();
        Iterable<Category> categorys = this.inventoryService.getAllCategory();
        for(Category category : categorys){
            tempProducts = this.inventoryService.getProductsByCategoryId(category.getKey());
            for(Product product : tempProducts){
                this.products.add(product);
            }
        }
        return this.products;
    }
    
    
    
    class ProductTableModel extends AbstractTableModel {
        
        private List<Product> productList;
        private final String [] columns = {"id ","name","description", "quantity available","Prce","Bar code number","threshold","orderQuantity",""};
        
        public ProductTableModel(Iterable<Product> productList){
            super();
            this.productList = new ArrayList<Product>();
            for(Product product : productList){
                this.productList.add(product);
            }
        }

        @Override
        public int getRowCount() {
            return this.productList.size();
        }

        @Override
        public int getColumnCount() {
            return this.columns.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            switch(columnIndex){
                case 0:
                    return this.productList.get(rowIndex).getKey();
                case 1:
                    return this.productList.get(rowIndex).getName();
                case 2:
                    return this.productList.get(rowIndex).getDescription();
                case 3:
                    return this.productList.get(rowIndex).getQuantity();
                case 4:
                    return this.productList.get(rowIndex).getPrice();
                case 5:
                    return this.productList.get(rowIndex).getBarcodeNumber();
                case 6:
                    return this.productList.get(rowIndex).getReorderQuantity();
                case 7:
                    return this.productList.get(rowIndex).getOrderQuantity();
                default:
                    return null;
            }
        }
        
        @Override
        public void setValueAt(Object value, int row, int column){
            System.out.println(column);
            switch(column){
                case 1:
                    this.productList.get(row).setName(value.toString());
                    break;
                case 2:
                    this.productList.get(row).setDescription(value.toString());
                    break;
                case 3:
                    this.productList.get(row).setQuantity(Integer.parseInt(value.toString()));
                    break;
                case 4:
                    this.productList.get(row).setPrice(Float.parseFloat(value.toString()));
                    break;
                case 5:
                    this.productList.get(row).setBarcodeNumber(value.toString());
                    break;
                case 6:
                    this.productList.get(row).setReorderQuantity(Integer.parseInt(value.toString()));
                    break;
                case 7:
                    this.productList.get(row).setOrderQuantity(Integer.parseInt(value.toString()));
                    break;
                default:
            }
            fireTableCellUpdated(row, column);
        }

        @Override
        public String getColumnName(int column) {
            return this.columns[column];//To change body of generated methods, choose Tools | Templates.
        }
        
        
        
        public Product getProductAt(int rowIndex){
            return this.productList.get(rowIndex);
        }
        
        public boolean isCellEditable(int row, int column){
            if(column == 0){
                return false;
            }
            return true;
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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 746, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            java.util.logging.Logger.getLogger(ProductsReportFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProductsReportFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProductsReportFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProductsReportFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new ProductsReportFrame(new InventoryService(new UnitOfWork())).setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(ProductsReportFrame.class.getName()).log(Level.SEVERE, null, ex);
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
