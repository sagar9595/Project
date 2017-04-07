/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package all;

/**
 *
 * @author Sagar
 */

import static all.ProjPID.Integration;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UI {

    private static void showMenu1(){
        System.out.println("1: New User");
        System.out.println("2: Existing User");
         System.out.println("3: To Exit");
    }
    
    private static void showMenu2(){
        System.out.println("Enter your details");
    }
    
    public static void main(String[] args) throws SQLException{
    
        JdbcClass  db = new JdbcClass();  // Database Creation
        db.checkExistence();
        int i;
        OUTER:
        while (true) {
            showMenu1();
            Scanner sc = new Scanner(System.in);
            int choice, keysize;
            ResultSet rs;
            String algo, trans, user, pass, id, path;
            choice = sc.nextInt();
            switch (choice) {
          
                case 1:
              
                  System.out.println("Enter the username and password");
                  sc.nextLine();
                  user = sc.nextLine();
                  pass = sc.nextLine();
                  rs = db.findID(user, pass);
                  if(rs != null){
                      System.out.println("This user already exists, plz enter different username");
                      break;
                  }
                  id = db.randomName();
                  System.out.println("Enter the algorithm and transformation to be used");
                  algo = sc.nextLine();
                  trans = sc.nextLine();
                  System.out.println("Enter the algorithm key size");
                  keysize = sc.nextInt();
                  sc.nextLine();
                  System.out.println("Enter the location of folder");
                  path = sc.nextLine();
                  File fc = new File(path);
                  if(!fc.exists()){
                      System.out.println("Plz enter correct path");
                      break;
                  }
            
                try {
                    FileHandler.DirectoryMove(path, "D:\\DSS\\decryption\\" + id);
                } catch (IOException ex) {
                    Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                }
            
                  db.saveID(user, pass, algo, trans, id, keysize);
                  Zip z = new Zip(id);
                  
                    for(i = pass.length(); i <= keysize + 1; i++ )
                        pass = pass + "x";
                    pass = pass.substring(0, keysize);
                  Encryption e = new Encryption(algo, trans, pass , id);
            
                try {
                    z.zipFolder();
                     e.encrypt();
                
                } catch (Exception ex) {
                    Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                }
            
                   fc = new File("D:\\DSS\\decryption\\" + id + ".zipe");
                  fc.delete();
                  ProjPID p = new ProjPID();
            
                try {
                    FileHandler.deleteDirectory(new File("D:\\DSS\\decryption\\" + id));
                } catch (FileNotDeletableException ex) {
                    Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                }
            
                  break;
                case 2:
                    showMenu2();
                   
                    System.out.println("Enter username");
                    sc.nextLine();
                    user = sc.nextLine();
                    System.out.println("Username is  " + user);
                    System.out.println("Enter password");
                    pass = sc.nextLine();
                    System.out.println("Password is    " + pass);
                    rs = db.findID(user, pass);
                    
                    // db.checkId();
                    if(rs == null){
                        System.out.println("Not a valid user, plz register first");
                        continue;
                    }
                    keysize = db.keysize;
                    for(i = pass.length(); i <= keysize + 1; i++ )
                        pass = pass + "x";
                    pass = pass.substring(0, keysize);
                    id = rs.getString(1);
                    algo = rs.getString(2);
                    trans = rs.getString(3);
                    File f = new File("D:\\DSS\\encryption\\" + id + ".zip");
                    System.out.println("Location is " + "D:\\DSS\\encryption\\" + id + ".zip");
                    if(!f.exists()){
                        db.removeEntry(id);
                        System.out.println("Removed entry from db2");
                        continue;
                    }
            {
                try {
                    Integration(id, algo, trans, pass);
                } catch (IOException | InterruptedException ex) {
                    Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (CantUnZipException ex) {
                    Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                    
                    
                    
                    break;
                case 3:
                    System.out.println("GoodBye!!");
                    break OUTER;
                default:
                    System.out.println("Please enter correct choice");
                    break;
            }
        }
        
        db.closeConnection();
    }

}
