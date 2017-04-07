/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package all;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Sagar
 */
public class FileHandler {
    
    static void DirectoryMove(String location, String destination) throws IOException{
        
        File src = new File(location);
        File dest = new File(destination);
        FileUtils.moveDirectoryToDirectory(src, dest, true);     
    
    }
    
    static void deleteDirectory(File element) throws FileNotDeletableException {
       
        if (element.isDirectory()) {
            for (File sub : element.listFiles()) {
                deleteDirectory(sub);
            }
        }
        element.setWritable(true);
        boolean c = element.delete();
        if(!c)
            throw( new FileNotDeletableException());
    }
    
    static boolean deleteFile(String path){
        File f = new File(path);
        boolean i = f.delete();
        return i;
    }
    
    static void createDirectory(){
    
        File f = new File("D:\\DSS\\encryption");
        if(!f.exists()){
            f.mkdirs();
        }
        f = new File("D:\\DSS\\decryption");
          if(!f.exists()){
            f.mkdirs();
        }
         
    }

}

class FileNotDeletableException extends Exception {
  
    public FileNotDeletableException(){
    }
    
    public FileNotDeletableException(String ex){
            super (ex);
    }
}