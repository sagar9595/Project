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
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;
/**
 *
 * @author sagar
 */
public class JdbcClass {
    /*
     * database name, username and password for a database
     */
    private static final String dbName = "log";
    private static final String username = "root";
    private static final String password = "sagar123";
    
    /*
     * JDBC Driver details
    */
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/";
    
    Connection conn = null;
    Statement stmt = null;
    
    
    void checkExistence(){
        int flag = 0;
        
        
         try{
             
            Class.forName("com.mysql.jdbc.Driver");
            conn=DriverManager.getConnection(DB_URL, username, password);
            ResultSet rs = conn.getMetaData().getCatalogs();
           
            while(rs.next()){
                String cat = rs.getString(1);
                if(dbName.equals(cat)){
                    System.out.println(cat);
                    flag = 1;
                    conn.setCatalog(dbName);  
                    break;
                }
            }
            
            if(flag == 0){
                createDatabase();
            }
            
            
        
         }
         
         catch(ClassNotFoundException | SQLException e){
            //System.out.println("Problem");
            e.printStackTrace();
        }
    }
    
    String randomName(){
        Random r = new Random();
        int num = r.nextInt(9000000) + 1000000;
        return Integer.toString(num);
    }
    
    void createDatabase(){
        try{
        Statement stmta = conn.createStatement();
        stmta.executeUpdate("create database log");
        conn.setCatalog(dbName);  
        stmta = conn.createStatement();
        String s = " create table info " +
                " ( folderID Integer not null auto_increment, " +
                " name varchar (255) not null, " + 
                " Password varchar(255) not null, "
                + " primary key (folderID))  ";
        stmta.executeUpdate(s);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    boolean isUnique(String id){
            try{
                stmt = conn.createStatement();
                String str = "select * from info where folderID = '" + id + "'";
                 ResultSet rs = stmt.executeQuery(str);
                    if(rs.next() == false){
                            return true;
                    }
                    else
                        return false;
            } catch (SQLException ex) {
            Logger.getLogger(JdbcClass.class.getName()).log(Level.SEVERE, null, ex);
        }
            return false;
    }
    
    String findID(String s){
        try{
        stmt = conn.createStatement();
        String str = "select folderID from info where password = '" + s + "'";
        ResultSet rs = stmt.executeQuery(str);
        if(rs.next() == false){
            return null;
        }
        else 
            return rs.getString(1);
         
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    void saveID(String name, String pass){
        try{
           stmt = conn.createStatement();
           String s = "insert into info (name, password) values ( '" +
                   name + "' , '" + pass + "' )";
           stmt.executeUpdate(s);
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        
    }
    void closeConnection(){
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(JdbcClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
 /*   public static void main(String[] args){
        JdbcClass h = new JdbcClass();
        h.checkExistence();
        h.saveID("sagar", "sharma");
        String s =h.findID("sharma");
        System.out.println(s);
        h.closeConnection();
    }*/
}