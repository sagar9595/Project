/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projpid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Sagar
 */
public class ProjPID extends Thread {
    
    String arr1[] = new String[5];      // Stores the pid before creating new process
    String arr2[] = new String[6];      // Stores pid after creating new process
    int count1 = 0, count2 = 0;         // Total number of ids
    
    /**
    * ipr - (is process running)
    * takes pid and checks whether the given process is present or not
    */
     public boolean ipr(int pid){
        try {
            Runtime runtime = Runtime.getRuntime();
            String cmds[] = {"cmd", "/c", "tasklist /FI \"PID eq " + pid + "\""};
            Process proc = runtime.exec(cmds);

            InputStream inputstream = proc.getInputStream();
            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
            String line;
            while ((line = bufferedreader.readLine()) != null) {
                //Search the PID matched lines single line for the sequence: " 1300 "
                //if you find it, then the PID is still running.
                if (line.contains(" " + pid + " ")){
                    return true;
                }
            }

            return false;
        } catch (Exception ex) {
            System.out.println("Cannot query the tasklist for some reason.");
            System.exit(0);
        }

        return false;

    }
     
     /**
     * This is where the bluetooth part will be present 
     */
    @Override
     public void run(){
         while(true){
             try {
                 System.out.println("New thread");
                 Thread.sleep(1000);
                Scanner s = new Scanner(System.in);
                int i = s.nextInt();
                 if(i == 7)
                     break;
             } catch (InterruptedException ex) {
                // Logger.getLogger(ProjPID.class.getName()).log(Level.SEVERE, null, ex);
                 return;
             }
         }
     }
     
     
     /**
      * This function helps to save the pid before new process creation
      */
     public void beforeCreation(){
        try {
            Runtime runtime = Runtime.getRuntime();
            String cmds[] = {"cmd", "/c", "tasklist"};
            Process proc = runtime.exec(cmds);
            InputStream inputstream = proc.getInputStream();
            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
            String line;
            while ((line = bufferedreader.readLine()) != null) {
                if(line.contains("explorer.exe")){
                    StringTokenizer st = new StringTokenizer(line);
                    String id;
                    st.nextToken();
                    arr1[count1++] = st.nextToken();
                    
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Cannot query the tasklist for some reason.");
        }
    }
     
     /**
      * This process saves the id's after new process is created
      */
      public void afterCreation(){
        try {
            Runtime runtime = Runtime.getRuntime();
            String cmds[] = {"cmd", "/c", "tasklist"};
            Process proc = runtime.exec(cmds);
            InputStream inputstream = proc.getInputStream();
            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
            String line;
            while ((line = bufferedreader.readLine()) != null) {
                if(line.contains("explorer.exe")){
                    StringTokenizer st = new StringTokenizer(line);
                    String id;
                    st.nextToken();
                    arr2[count2++] = st.nextToken();
                    
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Cannot query the tasklist for some reason.");
        }
    }
      
      /**
       * This function helps to find the newly created process's id
       * @return 
       *    returns id
       */
      int findPID(){
          int c1, c2;
          
          for(c2 = 0; c2 < count2; c2++){
              for(c1 = 0; c1 < count1; c1++){
                  if(arr2[c2].equals(arr1[c1])){                    
                      break;                     
                  }
              }
            
              if(c1 == count1){
                  return Integer.parseInt(arr2[c2]);
              }
          }
          
         
       return 0;   
      }
      
      /**
       * main function which systematically calls all the above functions
       * @param path is the path to where the file explorer will open
       */
      void createProc(String path){
        try {
            beforeCreation();
            Runtime.getRuntime().exec("explorer /seperate /select, " + path);
            Thread.sleep(5000);
            afterCreation();
            int id = findPID();
            System.out.println(id);
            ProjPID p = new ProjPID();
            p.start();
            while(ipr(id) != false){
                if(!p.isAlive()){
                     Runtime.getRuntime().exec("taskkill /PID " + id + " /F ");
                    break;
                }
            }
            if(p.isAlive()){
                p.interrupt();
            }
        } catch (IOException ex) {
            Logger.getLogger(ProjPID.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ProjPID.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        // TODO code application logic here
        
        ProjPID p = new ProjPID();
        p.createProc("D:\\DSS\\decryption\\");
         System.out.println("Destroyed!!!!!!");
    }
    
}
