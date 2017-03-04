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

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class UI {

    public void DirectoryMove(String location, String destination){
        File src = new File(location);
        File dest = new File(destination);
        
        try{
            FileUtils.moveDirectory(src, dest);
        } catch (IOException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private static void showMenu1(){
        System.out.println("1: New User");
        System.out.println("2: Existing User");
    }
    
    public static void main(String[] args){
        
        showMenu1();
    }

}
