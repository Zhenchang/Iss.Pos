package edu.nus.iss.pos.gui;

import edu.nus.iss.pos.core.dao.*;
import edu.nus.iss.pos.core.services.IInventoryService;
import edu.nus.iss.pos.core.services.IUsersService;
import edu.nus.iss.pos.dao.repositories.*;
import edu.nus.iss.pos.services.DiscountsService;
import edu.nus.iss.pos.services.InventoryService;
import edu.nus.iss.pos.services.MembershipService;
import edu.nus.iss.pos.services.SalesService;
import edu.nus.iss.pos.services.UsersService;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Zaid
 */
public class ApplicationLauncher {
    
    
     /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws IOException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        
        System.out.print(new File(".").getCanonicalPath());
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Macintosh*".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        try {
            IUnitOfWork db = new UnitOfWork();
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    try {
                        new LoginFrame(
                                new MembershipService(db),
                                new SalesService(db),
                                new InventoryService(db), 
                                new DiscountsService(db), 
                                new UsersService(db))
                                .setVisible(true);
                    } catch (Exception ex) {
                        Logger.getLogger(ApplicationLauncher.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            });
            
        } catch (Exception ex) {
            Logger.getLogger(ApplicationLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
