/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.gui;

import edu.nus.iss.pos.core.Product;
import edu.nus.iss.pos.core.services.IInventoryService;
import edu.nus.iss.pos.dao.repositories.UnitOfWork;
import edu.nus.iss.pos.services.InventoryService;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractCellEditor;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author Liu Zhenchang
 */
public class CheckInventory extends CustomedFrame {
    
    private IInventoryService inventoryService = null;
    private JPanel jPanel1 = null;
    private JScrollPane jScrollPane1 = null;
    private JTable productsTable = null;
    
   /**
     * Creates new form CheckInventory
     * @param inventoryService
     * @throws java.lang.Exception
     */
    public CheckInventory(IInventoryService inventoryService) throws Exception {
        this.inventoryService = inventoryService;
        productsTable = new javax.swing.JTable();
        ProductTableModel model = new ProductTableModel(inventoryService.getProductsBelowThreshold());
        productsTable.setModel(model);
        
        TableColumn tc = productsTable.getColumnModel().getColumn(8);
        tc.setCellEditor(new ButtonEditor());
        tc.setCellRenderer(new ButtonRenderer());
        
        super.getContentPane().setLayout(new FlowLayout());
        
        TableColumn tc2 = productsTable.getColumnModel().getColumn(7);
        tc2.setCellRenderer(new TextFieldRender());
        jScrollPane1 = new JScrollPane(productsTable);
        productsTable.setFillsViewportHeight(true);
        jScrollPane1.setPreferredSize(new Dimension(1000, 200));
        jPanel1 = new JPanel();
        jPanel1.add(jScrollPane1);
        super.setPreferredSize(new Dimension(1050, 300));
        super.add(jPanel1);
        super.setTitle("Check inventory");
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.pack();
    }

    
    public static void main(String[] args) {
                try {
                    new CheckInventory(new InventoryService(new UnitOfWork())).setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(CheckInventory.class.getName()).log(Level.SEVERE, null, ex);
                }
    }

    public class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
        
        private  JButton button = null;
        int row;
        int column;
        JTable table;
        
        public ButtonEditor() {
            button = new JButton("Replenish");
            button.setOpaque(true);
            button.addActionListener(this);
        }
        
        @Override
        public Object getCellEditorValue() {
            return "";
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.row = row;
            this.column = column;
            this.table = table;
            //button.setEnabled((boolean) value);
            return button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ProductTableModel model = (ProductTableModel) table.getModel();
            try {
                inventoryService.reorderProduct(model.getproductAt(row));
                int newQuantity = Integer.parseInt(table.getModel().getValueAt(row, 3).toString()) + Integer.parseInt(table.getModel().getValueAt(row, 7).toString());
                table.setValueAt(newQuantity, row, 3);
            } catch (Exception ex) {
                Logger.getLogger(CheckInventory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    class ButtonRenderer implements TableCellRenderer {
        
        JButton button = null;
        
        public ButtonRenderer() {
            button = new JButton("Replenish");
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            ProductTableModel model = (ProductTableModel) table.getModel();
           // button.setEnabled((boolean) value);
            return button;
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
            field.setText(value.toString());
            return field;
        }
    }
    
    class ProductTableModel extends AbstractTableModel {
        
        private List<Product> products = null;
        private final String[] columns = {"Product id", "Product name", "Description", "Quantity available", "Price", "Bar code number", 
                "Reorder quantity", "Order quantity", ""};
        
        public ProductTableModel(Iterable<Product> products){
            super();
            this.products = new ArrayList();
            for(Product p : products){
                this.products.add(p);
            }
        }
        
        @Override
        public int getRowCount() {
            return this.products.size();
        }

        @Override
        public int getColumnCount() {
            return columns.length;
        }
        
        //Set this ceil editable.
        @Override
        public boolean isCellEditable(int row, int col) {
            if(products.get(row).getQuantity() < products.get(row).getReorderQuantity())
                return col>columns.length-3;
            return false;
        }
        @Override
        public void setValueAt(Object value, int row, int col) {
            if(col == 7)
                products.get(row).setOrderQuantity(Integer.parseInt(value.toString()));
            //Make the changes displayed in the table.
            fireTableCellUpdated(row, col);
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            switch(columnIndex){
                case 0:
                    return products.get(rowIndex).getKey();
                case 1:
                    return products.get(rowIndex).getName();
                case 2:
                    return products.get(rowIndex).getDescription();
                case 3:
                    return products.get(rowIndex).getQuantity();
                case 4:
                    return products.get(rowIndex).getPrice();
                case 5:
                    return products.get(rowIndex).getBarcodeNumber();
                case 6:
                    return products.get(rowIndex).getReorderQuantity();
                case 7:
                    return products.get(rowIndex).getOrderQuantity();
                default:
                    return (products.get(rowIndex).getQuantity() < products.get(rowIndex).getReorderQuantity());
            }
        }
        
        public Product getproductAt(int rowIndex){
            return products.get(rowIndex);
        }
    }
}
