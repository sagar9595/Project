/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pid;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author Sagar
 */
public class Pid {
    
    void x(){
        try {
    String line;
   Process p = Runtime.getRuntime().exec
    (System.getenv("windir") +"\\system32\\"+"tasklist.exe");
    BufferedReader input =
            new BufferedReader(new InputStreamReader(p.getInputStream()));
    while ((line = input.readLine()) != null) {
        System.out.println(line); //<-- Parse data here.
    }
    input.close();
} catch (Exception err) {
    err.printStackTrace();
}
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Pid p = new Pid();
        p.x();
        // TODO code application logic here
    }
    
}
