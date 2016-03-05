package edu.nus.iss.pos.gui;

import edu.nus.iss.pos.core.dao.*;
import edu.nus.iss.pos.core.services.IUsersService;
import edu.nus.iss.pos.dao.repositories.*;
import edu.nus.iss.pos.services.UsersService;
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
            // TODO code application logic here
            IUnitOfWork db = new UnitOfWork();
            IUsersService usersService = new UsersService(db);
            /* Create and display the form */
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new LoginFrame(usersService).setVisible(true);
                }
            });
            //db.getRepository(FileType.User).add( new User("omari", "132456"));
            
            //db.getRepository(FileType.User).update("zaid", new User("zaid", "1324"));
            
            //db.getRepository(FileType.User).delete(new User("zaid", "1324"));
            
            // Iterable<User> users = (Iterable<User>) db.getRepository(FileType.User).getAll();
//            for(User u : users){
//               System.out.println(u.getUsername());
//            }
            
//            User u = (User) db.getRepository(FileType.User).getByKey("zaid");
//            System.out.println(u.getUsername() + u.getPassword());
            
        } catch (Exception ex) {
            Logger.getLogger(ApplicationLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
