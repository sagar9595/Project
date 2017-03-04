/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filewriterexample;

/**
 *
 * @author Sagar
 */

import java.io.FileWriter;  
public class FileWriterExample {  
    public static void main(String args[]){    
         try{    
             try (FileWriter fw = new FileWriter("D:test")) {
                 fw.write("Welcome to javaTpoint.");
             }    
          }catch(Exception e){System.out.println(e);}    
          System.out.println("Success...");    
     }    
}  