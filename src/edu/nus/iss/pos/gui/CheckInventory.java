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
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author Liu Zhenchang
 */
public class CheckInventory extends JFrame {
    
    private IInventoryService inventoryService = null;
    private JPanel jPanel1 = null;
    private JScrollPane jScrollPane1 = null;
    private Object[][] data = null;
    private JTable productsTable = null;
    
   /**
     * Creates new form CheckInventory
     * @param inventoryService
     * @throws java.lang.Exception
     */
    public CheckInventory(IInventoryService inventoryService) throws Exception {
        this.inventoryService = inventoryService;
        data = getData();
        init();
    }
    
    private void init() throws Exception {
        productsTable = new javax.swing.JTable();
        productsTable.setFillsViewportHeight(true);
        
        productsTable.setModel(new AbstractTableModel() {
            private final String[] columnNames = {"Product id", "Product name", "Description", "Quantity available", "Price", "Bar code number", 
                "Reorder quantity", "Order quantity", ""};
            
            @Override
            public int getRowCount() {
                return data.length;
            }

            @Override
            public int getColumnCount() {
                return columnNames.length;
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                return data[rowIndex][columnIndex];
            }
            
            @Override
            public String getColumnName(int col) {
                return columnNames[col];
            }
            
            @Override
            public boolean isCellEditable(int row, int col) {
                return col>=columnNames.length-2;
            }
           
            @Override
            public void setValueAt(Object value, int row, int col) {
                data[row][col] = value;
                fireTableCellUpdated(row, col);
            }
        });
        
        TableColumn tc = productsTable.getColumnModel().getColumn(8);
        tc.setCellEditor(new ButtonEditor());
        tc.setCellRenderer(new ButtonRenderer());

        TableColumn tc2 = productsTable.getColumnModel().getColumn(7);
        tc2.setCellRenderer(new TextFieldRender());
        
        jScrollPane1 = new JScrollPane(productsTable);
        productsTable.setFillsViewportHeight(true);
        jScrollPane1.setPreferredSize(new Dimension(1000, 200));
        jPanel1 = new JPanel();
        jPanel1.add(jScrollPane1);
        add(jPanel1);
        setTitle("Check inventory");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }
    
    public static void main(String[] args) {
                try {
                    new CheckInventory(new InventoryService(new UnitOfWork())).setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(CheckInventory.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    //Get products below threshold
    private Object[][] getData() throws Exception {
        Collection<Product> products = inventoryService.getProductsBelowThreshold();
        Object[][] proData = new Object[products.size()][9];
        int i = 0;
        for(Product product : products) {
            proData[i][0] = product.getKey();
            proData[i][1] = product.getName();
            proData[i][2] = product.getDescription();
            proData[i][3] = product.getQuantity();
            proData[i][4] = product.getPrice();
            proData[i][5] = product.getBarcodeNumber();
            proData[i][6] = product.getReorderQuantity();
            proData[i][7] = product.getOrderQuantity();
            proData[i][8] = "";
            i++;
        }
        return proData;
    }
    
    public class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
        
        private  JButton button = null;
        int row;
        int column;
        
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
            return button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //update
            Object[] selected = data[row];
            String category = selected[0].toString().split("/")[0];
            Category c = new Category(category);
            int index = Integer.parseInt(selected[0].toString().split("/")[1]);
            String name = selected[1].toString();
            String description = selected[2].toString();
            int quantity = Integer.parseInt(selected[3].toString());
            float price = Float.parseFloat(selected[4].toString());
            String barcode = selected[5].toString();
            int shreshold = Integer.parseInt(selected[6].toString());
            int orderQuantity = Integer.parseInt(productsTable.getValueAt(row, 7).toString());
            System.out.println(orderQuantity);
            Product product = new Product(c, index, name, description, quantity, price, barcode, shreshold, orderQuantity);
            try {
                inventoryService.reorderProduct(product);
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
}
