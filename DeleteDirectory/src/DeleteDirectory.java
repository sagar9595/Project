
import static com.oracle.jrockit.jfr.ContentType.StackTrace;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileDeleteStrategy;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sagar
 */
public class DeleteDirectory {
    
     void deleteDirectory(File element) throws FileNotDeletableException {
       
    if (element.isDirectory()) {
        for (File sub : element.listFiles()) {
            deleteDirectory(sub);
        }
    }
    element.setWritable(true);
    boolean c = element.delete();
    if(!c){
        throw new FileNotDeletableException("File is opened somewhere");
    }
    }
     
     public static void main(String[] args){
         DeleteDirectory d = new DeleteDirectory();
         try {
             d.deleteDirectory(new File("D:\\abcd"));
         } catch (FileNotDeletableException ex) {
             Logger.getLogger(DeleteDirectory.class.getName()).log(Level.SEVERE, null, ex);
         }
         
         FileDeleteStrategy file = FileDeleteStrategy.FORCE;
         try {

		file.delete(new File("D:\\abcd"));
	} catch (Exception fileDeleteException) {
		System.out.println("Falied!!!!");
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