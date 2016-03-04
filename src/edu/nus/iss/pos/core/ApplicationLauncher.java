package edu.nus.iss.pos.core;

import edu.nus.iss.pos.core.dao.IUnitOfWork;
import edu.nus.iss.pos.dao.format.FileType;
import edu.nus.iss.pos.dao.repositories.UnitOfWork;
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
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            IUnitOfWork db = new UnitOfWork();
            
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
