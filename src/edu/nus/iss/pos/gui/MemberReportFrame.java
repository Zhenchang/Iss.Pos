/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.pos.gui;

import edu.nus.iss.pos.core.Member;
import edu.nus.iss.pos.core.services.IMembershipService;
import edu.nus.iss.pos.dao.repositories.UnitOfWork;
import edu.nus.iss.pos.services.MembershipService;
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
 * @author Ankan
 */
public class MemberReportFrame extends javax.swing.JFrame {

     private IMembershipService membershipService;
    /**
     * Creates new form MemberReportFrame
     */
    public MemberReportFrame(IMembershipService membershipService) throws Exception {
        initComponents();
        this.setContent(membershipService);
    }
    
     private void setContent(IMembershipService membershipService) throws Exception{
        this.membershipService = membershipService;
        TableModel tableModel;
         tableModel = new MemberTableModel(this.membershipService.getAllMembers());
        this.jTable1.setModel(tableModel);
        TableColumn tc = this.jTable1.getColumnModel().getColumn(3);

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
            MemberTableModel tableModel = (MemberTableModel) this.jTable.getModel();
            
            try {
                membershipService.updateMember(tableModel.getMemberAt(this.row));
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
    
    
    class MemberTableModel extends AbstractTableModel {
        
        private List<Member> memberList;
        private final String [] columns = {"MemberId","MemberName","Loyalty points",""};
        
        public MemberTableModel(Iterable<Member> memberList){
            super();
            this.memberList = new ArrayList<Member>();
            for(Member member : memberList){
                this.memberList.add(member);
            }
        }

        @Override
        public int getRowCount() {
            return this.memberList.size();
        }
        
        @Override
        public String getColumnName(int columnIndex){
            return this.columns[columnIndex];
        }

        @Override
        public int getColumnCount() {
            return this.columns.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            switch(columnIndex){
                case 0:
                    System.out.println(this.memberList.get(rowIndex).getKey());
                    return this.memberList.get(rowIndex).getKey();
                    
                case 1:
                    return this.memberList.get(rowIndex).getName();
                case 2:
                    return this.memberList.get(rowIndex).getLoyaltyPoints();
                default:
                    return null;
            }
        }
        
        @Override
        public void setValueAt(Object value, int row, int column){
            if(column == 1){
                this.memberList.get(row).setName(value.toString());
            } 
            fireTableCellUpdated(row, column);
        }
        
        public Member getMemberAt(int rowIndex){
            return this.memberList.get(rowIndex);
        }
        
        @Override
        public boolean isCellEditable(int row, int column){
            if(column == 0 || column == 2){
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

        jButton1.setText("Cancel");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Add");

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
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
            java.util.logging.Logger.getLogger(MemberReportFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MemberReportFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MemberReportFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MemberReportFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                 try {
                    IMembershipService membershipService = new MembershipService(new UnitOfWork());
                    new MemberReportFrame(membershipService).setVisible(true);
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
