/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testwait;

import java.io.File;
import java.io.IOException;

  
/**
 *
 * @author Sagar
 */
public class TestWait {

      public void run() throws IOException {
      
        String currentDir = new File(System.getProperty("user.home"),"desktop/notepad.lnk").getCanonicalPath();
        ProcessBuilder processBuild = new ProcessBuilder(currentDir);
        processBuild.command("cmd", "/c", "start","/wait", "explorer", ".");
        Process p= processBuild.start();
        try {
            p.waitFor();
        } catch (InterruptedException ex) {

        }
        System.out.println("process terminated!");

    }
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        TestWait t = new TestWait();
        t.run();
        
    }
    
}
