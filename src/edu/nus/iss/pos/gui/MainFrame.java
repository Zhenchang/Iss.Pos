/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.gui;

import edu.nus.iss.pos.core.Customer;
import edu.nus.iss.pos.core.Member;
import edu.nus.iss.pos.core.Product;
import edu.nus.iss.pos.core.Transaction;
import edu.nus.iss.pos.core.TransactionDetail;
import edu.nus.iss.pos.core.services.IDiscountsService;
import edu.nus.iss.pos.core.services.IInventoryService;
import edu.nus.iss.pos.core.services.IMembershipService;
import edu.nus.iss.pos.core.services.ISalesService;
import edu.nus.iss.pos.dao.repositories.UnitOfWork;
import edu.nus.iss.pos.services.DiscountsService;
import edu.nus.iss.pos.services.InventoryService;
import edu.nus.iss.pos.services.MembershipService;
import edu.nus.iss.pos.services.SalesService;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author Zaid
 */
public class MainFrame extends javax.swing.JFrame {

    private final IMembershipService membershipService;
    private Customer customer;
    private final ISalesService salesService;
    private final IInventoryService inventoryService;
    private Transaction currentTransaction;
    private final IDiscountsService discountsService;
    
       
    public MainFrame(IMembershipService membershipService, ISalesService salesService, IInventoryService inventoryService,IDiscountsService discountsService) throws Exception {
        initComponents();
        
        this.discountsService = discountsService;
        this.inventoryService = inventoryService;
        this.salesService = salesService;
        this.membershipService = membershipService;
        this.customer = Customer.getInstance();
        currentTransaction = salesService.beginTransaction(customer);
        removeMember();
        mySetComponents();
    
        
    }
    
    
    
    public final void mySetComponents(){
        this.jTextField1.setEditable(false);
        ((SpinnerNumberModel)jSpinner1.getModel()).setMinimum(0);
        jLabel6.setForeground(Color.blue);       
        
        this.jTextField2.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
               paymentTextChanged();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                paymentTextChanged();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                paymentTextChanged();
            }
         });
        this.jTextField1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
               jTextFieldTextChanged();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                jTextFieldTextChanged();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                jTextFieldTextChanged();
            }
         });
        
        syncTableWithCustomer();
    }
        
    private void syncTableWithCustomer(){
        this.jTable1.setModel(new TransactionTableModel(this.currentTransaction));
        
        TableColumn tableColumn = this.jTable1.getColumnModel().getColumn(2);
        tableColumn.setCellEditor(new QuantityEditor());
        tableColumn.setCellRenderer(new QuantityRenderer());
        
        TableColumn tableColumn2 = this.jTable1.getColumnModel().getColumn(4);
        ButtonRenderer b = new ButtonRenderer();
        tableColumn2.setCellRenderer(b);
        tableColumn2.setCellEditor(b);
    }
    class ButtonRenderer extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
        
        JButton button = null;
        private int row;
        public ButtonRenderer() {
            button = new JButton("Delete");
            button.addActionListener((ActionEvent e) -> {
                int i=0;
                for(TransactionDetail d : currentTransaction.getTransactionDetails()){
                    if(i == row){
                        currentTransaction.removeTransactionDetail(d);
                        break;
                    }
                    i++;
                }
                try {
                    refreshTotal();
                } catch (Exception ex) {
                }
                jTable1.updateUI();
                
            });
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            this.row = row;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return getTableCellRendererComponent(table, value, isSelected, isSelected, row, column);
        }
    }
    
    class CellJSpinner extends JSpinner{
        public int row;
        
        public void clearListeners(){
            for(ChangeListener l : this.getChangeListeners()){
                this.removeChangeListener(l);
            }
        }
    }
    class QuantityEditor extends AbstractCellEditor implements TableCellEditor{

        private final CellJSpinner jSpinner;
        
        public QuantityEditor(){
            this.jSpinner = new CellJSpinner();
            SpinnerNumberModel model = new SpinnerNumberModel();
            model.setMinimum(1);
            model.setMaximum(1);
            this.jSpinner.setModel(model);
            ((JSpinner.DefaultEditor)jSpinner.getEditor()).getTextField().setHorizontalAlignment(JTextField.CENTER);
            jSpinner.addChangeListener((ChangeEvent e) -> {
                TransactionTableModel model1 = (TransactionTableModel) jTable1.getModel();
                if (e.getSource().equals(jSpinner)) {
                    model1.setValueAt(jSpinner.getValue(), jSpinner.row, 2);
                    model1.fireTableDataChanged();
                    try {
                        refreshTotal();
                    } catch (Exception ex) {
                    }
                }
            });
        }

        @Override
        public Object getCellEditorValue() {
            return this.jSpinner.getValue();
        }
        

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.jSpinner.row = row;  
            this.jSpinner.setValue(Integer.parseInt(value.toString()));
            TransactionTableModel model = (TransactionTableModel) jTable1.getModel();
            TransactionDetail detail =  model.getTransactionDetailAt(row);
            int max = detail.getProduct().getQuantity();
            ((SpinnerNumberModel)this.jSpinner.getModel()).setMaximum(max);
            return this.jSpinner;
        }

    }
    
    protected void refreshTotal() throws Exception {
        int discount = discountsService.getDiscountForTransaction(currentTransaction);
        float finalPrice = this.salesService.getFinalPrice(currentTransaction, discount, (Integer)this.jSpinner1.getValue());
        if(this.customer instanceof Member){
            this.jLabel12.setText(String.format("$ %.2f", Member.convertPointsToDollars((Integer)this.jSpinner1.getValue())));
            jLabel9.setText(Member.convertDollarsToPoints(finalPrice) + "");
            ((SpinnerNumberModel)jSpinner1.getModel()).setMaximum(((Member)currentTransaction.getCustomer()).getLoyaltyPoints());            
        }else{
            jLabel9.setText("0");
        }
        
        jLabel2.setText(String.format("$ %.2f", finalPrice));
        //jTextField2.setText(String.format("%.2f", finalPrice));
        jLabel4.setText(String.format("%d%%", discount));
    } 
    
    class QuantityRenderer implements TableCellRenderer {
        
        private final JSpinner jSpinner;
        
            public QuantityRenderer() {
            this.jSpinner = new JSpinner();
            ((JSpinner.DefaultEditor)this.jSpinner.getEditor()).getTextField().setHorizontalAlignment(JTextField.CENTER);
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
           this.jSpinner.setValue(Integer.parseInt(value.toString()));
           return this.jSpinner;
        }
    }
    
    
    
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UnitOfWork unitOfWork = new UnitOfWork();
                    new MainFrame(new MembershipService(unitOfWork),new SalesService(unitOfWork),new InventoryService(unitOfWork), new DiscountsService(unitOfWork)).setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(CategoryReportFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    class TransactionTableModel extends AbstractTableModel{
        
        private final String column[] = {"product name", "price", "quantity","sub total", "operation"};
        private final Transaction transaction;
        
        public TransactionTableModel(Transaction transaction){
            this.transaction = transaction;
        }

        @Override
        public int getRowCount() {
            return ((Collection) this.transaction.getTransactionDetails()).size();
        }

        @Override
        public int getColumnCount() {
            return this.column.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            List<TransactionDetail> c = (List) this.transaction.getTransactionDetails();
           
            switch(columnIndex){
                case 0:
                    return c.get(rowIndex).getProduct().getName();
                case 1:
                    return c.get(rowIndex).getProduct().getPrice();
                case 2:
                    return c.get(rowIndex).getQuantityPurchased();
                case 3:
                    return c.get(rowIndex).getQuantityPurchased() * c.get(rowIndex).getProduct().getPrice();
                default:
            }
            return null;
        }

        @Override
        public String getColumnName(int column) {
            return this.column[column]; //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            List<TransactionDetail> c = (List) this.transaction.getTransactionDetails();
            switch(columnIndex){
                case 2:
                    c.get(rowIndex).setQuantityPurchased(Integer.parseInt(aValue.toString()));
                    break;
                case 3:
                    super.setValueAt(aValue, rowIndex, columnIndex);
                    break;
                default:
            }
            fireTableCellUpdated(rowIndex, columnIndex);
        }
        
        @Override
        public boolean isCellEditable(int row, int column){
            if(column == 2 || column == 4){
                return true;
            }
            return false;
        }
        
        public TransactionDetail getTransactionDetailAt(int row){
            List<TransactionDetail> c = (List) this.transaction.getTransactionDetails();
            return c.get(row);
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

        jMenuItem5 = new javax.swing.JMenuItem();
        jPanel4 = new javax.swing.JPanel();
        jDialog1 = new javax.swing.JDialog();
        jDialog2 = new javax.swing.JDialog();
        jDialog3 = new javax.swing.JDialog();
        jDialog4 = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jLabel12 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem11 = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem13 = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        jMenuItem16 = new javax.swing.JMenuItem();

        jMenuItem5.setText("jMenuItem5");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jDialog2Layout = new javax.swing.GroupLayout(jDialog2.getContentPane());
        jDialog2.getContentPane().setLayout(jDialog2Layout);
        jDialog2Layout.setHorizontalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog2Layout.setVerticalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jDialog3Layout = new javax.swing.GroupLayout(jDialog3.getContentPane());
        jDialog3.getContentPane().setLayout(jDialog3Layout);
        jDialog3Layout.setHorizontalGroup(
            jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog3Layout.setVerticalGroup(
            jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jDialog4Layout = new javax.swing.GroupLayout(jDialog4.getContentPane());
        jDialog4.getContentPane().setLayout(jDialog4Layout);
        jDialog4Layout.setHorizontalGroup(
            jDialog4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog4Layout.setVerticalGroup(
            jDialog4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(102, 102, 102));

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jTextField1.setEditable(false);
        jTextField1.setBackground(java.awt.Color.gray);
        jTextField1.setForeground(new java.awt.Color(0, 153, 0));
        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextField1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField1FocusLost(evt);
            }
        });

        jLabel5.setText("Member Name");

        jLabel6.setText("Points");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        jLabel7.setText("Member ID");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(0, 204, 204));

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

        jButton1.setText("Add Product");
        jButton1.setAlignmentY(0.0F);
        jButton1.setAutoscrolls(true);
        jButton1.setIconTextGap(0);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 204));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Total");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("0.00");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Discount");

        jLabel4.setBackground(new java.awt.Color(255, 255, 204));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("0.00");

        jButton2.setText("Check Out");
        jButton2.setEnabled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Reset");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("New Points");

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("0");

        jTextField2.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        jLabel11.setText("Payment");

        jLabel13.setText("Change");

        jLabel14.setText("0.0");

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Redeem Points");

        jSpinner1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinner1StateChanged(evt);
            }
        });

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("00.00");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addGap(50, 50, 50))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(54, 54, 54)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65)
                .addComponent(jButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenu1.setText("Inventory");

        jMenuItem1.setText("Categories");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Products");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("Vendors");
        jMenu1.add(jMenuItem3);
        jMenu1.add(jSeparator1);

        jMenuItem4.setText("Add New Category");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuItem6.setText("Add New Product");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);
        jMenu1.add(jSeparator2);

        jMenuItem10.setText("Reorder Products");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem10);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Sales");

        jMenuItem7.setText("Transactions");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem7);

        jMenuItem8.setText("Discounts");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem8);
        jMenu2.add(jSeparator3);

        jMenuItem9.setText("Add New Discount");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem9);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Membership");

        jMenuItem11.setText("Members");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem11);
        jMenu3.add(jSeparator4);

        jMenuItem12.setText("Add New Member");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem12);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("System");

        jMenuItem13.setText("Store Keepers");
        jMenu4.add(jMenuItem13);
        jMenu4.add(jSeparator5);

        jMenuItem14.setText("Change Password");
        jMenu4.add(jMenuItem14);

        jMenuItem15.setText("Logout");
        jMenu4.add(jMenuItem15);
        jMenu4.add(jSeparator6);

        jMenuItem16.setText("Exit");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem16);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        resetFrame();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void resetFrame(){
        try {
            this.removeMember();
            currentTransaction.clearTransactionDetail();
            currentTransaction = salesService.beginTransaction(customer);
            jLabel2.setText("-");
            jTextField2.setText("");
            jTextField1.setText("");
            jLabel9.setText("-");
            jLabel4.setText("-");
            syncTableWithCustomer();
            jTable1.updateUI();
        } catch (Exception ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            // TODO add your handling code here:
            String barcode = JOptionPane.showInputDialog("Please input the barcode of the product");
            Product product = this.searchProduct(barcode);
            if(product != null){
                this.salesService.addToCart(currentTransaction, product, 1);
                this.jTable1.updateUI();
                this.refreshTotal();
            } else {
                JOptionPane.showMessageDialog(null, "No such product", "No such product", JOptionPane.ERROR_MESSAGE); 
            }
        } catch (Exception ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            // TODO add your handling code here:
            int discount = this.discountsService.getDiscountForTransaction(currentTransaction);
            int usePoints = (int)this.jSpinner1.getValue();
            float finalPrice = salesService.getFinalPrice(currentTransaction, discount, usePoints);
            this.salesService.checkout(currentTransaction, discount, usePoints);
            new BillFrame(currentTransaction, finalPrice , discount, Member.convertDollarsToPoints(finalPrice), usePoints).setVisible(true);
            resetFrame();
        } catch (Exception ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusLost
        if(this.jTextField1.getText().equals("")){
            this.jTextField1.setEditable(false);
            this.jTextField1.setBackground(Color.gray);
        }else{
            if(!(this.customer instanceof Member)){
                Object[] options = { "OK", "CANCEL" };
                int result = JOptionPane.showOptionDialog(null, "No souch member, do you want to add a new Member?", "Warning",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                        null, options, options[0]);
                if(result != JOptionPane.OK_OPTION){
                    this.jTextField1.requestFocus();
                }else{
                    //MemberRegistration newMemberFrame = new MemberRegistration(this, true, membershipService);
                    //newMemberFrame.setVisible(true);
                    //Member m = newMemberFrame.getMember();
                    //if(m==null){
                        this.jTextField1.requestFocus();
                    //}else{
                        //try {
                        //    setMember(m);
                        //} catch (Exception ex) {
                        //}
                    //}
                }
            }
        }
    }//GEN-LAST:event_jTextField1FocusLost

    private void removeMember(){
        this.customer = Customer.getInstance();
        this.currentTransaction.setCustomer(customer);
        jLabel5.setText("   ");
        jLabel4.setText(String.format("%d%%", 0));
        jLabel6.setText("   ");
        jSpinner1.getModel().setValue(0);
        jPanel1.setVisible(false);
    }
    private void setMember(Member m) throws Exception{
        if(m != null){
            this.customer = m;
            this.currentTransaction.setCustomer(customer);
            jPanel1.setVisible(true);
            jLabel5.setText(m.getName());
            jLabel6.setText(m.getLoyaltyPoints() + "");
            int discount = discountsService.getDiscountForTransaction(currentTransaction);
            jLabel4.setText(String.format("%d%%", discount));
            refreshTotal();
            
        }else{
            removeMember();
        }
    }
    private void jTextField1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusGained
        // TODO add your handling code here:
        this.jTextField1.setEditable(true);
        this.jTextField1.setBackground(Color.white);
    }//GEN-LAST:event_jTextField1FocusGained

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        try {
            new AddProductFrame(inventoryService).setVisible(true);
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        new AddCagagoryFrame(inventoryService).setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        try {
            new CategoryReportFrame(inventoryService).setVisible(true);
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        try {
            new ProductsReportFrame(inventoryService).setVisible(true);
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        try {
            new CheckInventory(inventoryService).setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        try {
            new TransactionReport(salesService).setVisible(rootPaneCheckingEnabled);
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed

    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        new AddDiscountFrame(discountsService).setVisible(true);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        new MemberRegistration(membershipService).setVisible(true);
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jSpinner1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner1StateChanged
        try {
            // TODO add your handling code here:
            refreshTotal();
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_jSpinner1StateChanged

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        // TODO add your handling code here:
        int value = 0;
        try{
            value = Integer.parseInt(jLabel6.getText());
            jSpinner1.getModel().setValue(value);
        }catch(Exception e){
        }
    }//GEN-LAST:event_jLabel6MouseClicked

    private void paymentTextChanged(){
        float value = 0;
        float total = 0;
        try{
            value = Float.parseFloat(jTextField2.getText());
            total = Float.parseFloat(jLabel2.getText().replace("$ ", ""));
            if(total < value){
                jTextField2.setForeground(Color.green);
                jLabel14.setText(String.format("$ %.2f", value - total));
                if(total > 0){
                    jButton2.setEnabled(true);
                }
                return;
            }
        }catch(Exception e){
        }
        jTextField2.setForeground(Color.red);
        jLabel14.setText("$ 0.00");
        jButton2.setEnabled(false);
    }
    
    private void jTextFieldTextChanged(){
        try {
            Member m = this.membershipService.searchMemberById(this.jTextField1.getText());
            setMember(m);
        } catch (Exception ex) {
        }
        if(this.customer instanceof Member){
            this.jTextField1.setForeground(new Color(0,153,0));
        }else{
            this.jTextField1.setForeground(Color.red);
        }
    }
    
    private Product searchProduct(String barcode) throws Exception{
        Product product = this.inventoryService.searchProductByBarcode(barcode);
        return product;
    } 
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JDialog jDialog2;
    private javax.swing.JDialog jDialog3;
    private javax.swing.JDialog jDialog4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
