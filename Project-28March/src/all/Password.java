/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package all;

import bcrypt.BCrypt;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Sagar
 */
public class Password {
    
    
    static String password; // this stores the password
    /**
     * 
     * @param password users password that he entered
     * @param salt a string generated by BCrypt.gensalt() which will be stored on phone.
     * @return hash of the password
     */
    static String generateHash(String password, String salt){
        String val = BCrypt.hashpw(password, salt);
        return val.substring(29, val.length());
    }
    
    
    /**
     * 
     * @param input  the entered password to check
     * @param hash   the hash saved in database
     * @param salt   the salt saved on phone
     * @return       true if matches else false
     */
    static boolean checkPassword(String input, String hash, String salt){
        boolean i = BCrypt.checkpw(input, salt + hash);
        return i;
    }
 
    /**
     * 
     * @param name  name of the user
     * @param pass  password of the user
     * @param algo  user to be used
     * @param trans trans to be used
     * @param fold folderid
     * @param keysize size of the key
     * @param db db class object
     * @param bc bluetoothConn class object
     * @return
     * @throws IOException 
     */
    static String firstTimeUserEntry(String name, String pass, String algo, 
            String trans, String fold, int keysize, JdbcClass db, BluetoothConn bc) throws IOException{
        
       String salt = BCrypt.gensalt();
       String hash = generateHash(pass, salt);
       //send salt
      bc.sendString("aaaa");
      String reply = bc.receiveString();
      if(reply.contains("n")){
          System.out.println("This laptop s already registered with that phone");
          return null;
      }
      bc.sendString(salt);
      reply = bc.receiveString();
      if(reply.contains("n")){
          bc.sendString(salt);
           reply = bc.receiveString();
      }
       //save hash
       db.saveID(name, hash, algo, trans, fold, keysize);
       password = salt + pass;
        return salt + pass;
    }
    
    
    /**
     * 
     * @param name username
     * @param inp  entered password to be checked
     * @param db   JdbcClass object
     * @param bc   BluetoothConn class object
     * @return     resultSet if authentication successful else null
     * @throws SQLException 
     */
    static ResultSet savedUserEntry(String name, String inp, JdbcClass db, 
            BluetoothConn bc) throws SQLException, IOException{
        
        password = null;
        ResultSet rs;
        rs = db.findID(name);
        String salt = "null", hash;
        // take salt
      bc.sendString("zzzz");
      String reply = bc.receiveString();
      if(reply.contains("n")){
          System.out.println("The phone is not activated");
          return null;
      }
      bc.sendString("kkkk");
      reply = bc.receiveString();
      if(reply.length() != 29){
           System.out.println("Authentication Failed");
           return null;
      }
      salt = reply;
        //check hash
        hash = rs.getString("password");
        if(checkPassword(inp, hash, salt) == false)
            return null;
        else{
            password = salt + inp;
            return rs;
        }
    }
}
