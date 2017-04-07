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
    int keysize ;
    
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
        String id;
        
        while(true){
            int num = r.nextInt(9000000) + 1000000;
            id =  Integer.toString(num);
            try{
                stmt = conn.createStatement();
                String str = "select * from info where folderID = '" + id + "'";
                 ResultSet rs = stmt.executeQuery(str);
                    if(rs.next() == false){
                            return id;
                    }
                 
            } catch (SQLException ex) {
            Logger.getLogger(JdbcClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    
    void createDatabase(){
        try{
        Statement stmta = conn.createStatement();
        stmta.executeUpdate("create database log");
        conn.setCatalog(dbName);  
        stmta = conn.createStatement();
        String s = " create table info " +
                " ( folderID Integer not null, " +
                " name varchar (255) not null, " + 
                " password varchar(255) not null, " +
                " algorithm varchar (255) not null, " +
                " transformation varchar (255) not null, " +
                "keySize Integer, " + 
                 " primary key (name, password), unique key(folderID) )";
        stmta.executeUpdate(s);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    
  
    ResultSet findID(String user){  // use rs.getString(1); to take string, use rs.next() to check if String exists
        try{
        stmt = conn.createStatement();
        String str = "select password, folderID, algorithm, transformation, keySize from info where "
                + "name = '" + user  + "'";
        ResultSet rs = stmt.executeQuery(str);
        ResultSet rs1 = rs;
        
        if(rs.next() == false){
            return null;
        }
        else {
            keysize = rs1.getInt("keySize");
            return rs;
        }
         
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    void saveID(String name, String pass, String algo, String trans, String fold, int keysize){
        try{
           stmt = conn.createStatement();
           String s = "insert into info (name, password, algorithm, transformation, folderID , keySize) "
                   + " values ( '" + name + "' , '" + pass +  "' , '"  + algo + "', '"
                   + trans + "' , '" + fold + "'," + keysize +  " )";
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
    
    void removeEntry(String id){
        
           try{
           stmt = conn.createStatement();
           String s = "delete from  info where folderID = '" + id  + " '";
           stmt.executeUpdate(s);
            
        }catch(SQLException e){
            e.printStackTrace();
        }      
    }
    
   /* public static void main(String[] args){
        JdbcClass h = new JdbcClass();
        h.checkExistence();
        h.saveID("sagar", "sharma", "Blowfish", "Blowfish", "2234567");
   //     ResultSet s =h.findID("sagar", "sharma");
     /*   try {
           System.out.println(s.getString(1) + " " + s.getString(2)  + " "  + s.getString(3));
        } catch (SQLException ex) {
            Logger.getLogger(JdbcClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        h.closeConnection();
    }*/
}