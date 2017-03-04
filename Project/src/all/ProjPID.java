/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package all;

import java.io.BufferedReader;
import java.io.File;
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

    private  void deleteDirectory(File element) {
       
    if (element.isDirectory()) {
        for (File sub : element.listFiles()) {
            deleteDirectory(sub);
        }
    }
    element.setWritable(true);
    boolean c = element.delete();
    if(!c)
        System.out.println("NOt able to delete!!!!!!!!");
    }
    
    String arr1[] = new String[100];      // Stores the pid before creating new process
    String arr2[] = new String[100];      // Stores pid after creating new process
    int count1 = 0, count2 = 0;         // Total number of ids
    
    /**
     * ipr - (is process running)
     * @param pid -takes pid and checks whether the given process is present or not
     * @return true if exist flase if not
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
    @SuppressWarnings({"SleepWhileInLoop", "empty-statement"})
     public void run(){
         while(true){
             try {
                 System.out.println("New thread");
                 Thread.sleep(1000);
          
           
             } catch (InterruptedException e) {
                // Logger.getLogger(ProjPID.class.getName()).log(Level.SEVERE, null, ex);
                return;
             }
             catch(Exception e){
               return;
             }
         }
     }
     
     
     /**
      * This function helps to save the pid before new process creation
      */
    @SuppressWarnings("CallToPrintStackTrace")
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
    @SuppressWarnings("CallToPrintStackTrace")
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
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(ProjPID.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        
        Zip z = new Zip("abcd");
        Encryption e = new Encryption("Blowfish", "Blowfish", "Maryasda", "abcd");
        
        try {
        
        e.decrypt();
        File f = new File("D:\\DSS\\encryption\\" + "abcd.zip");
        f.delete();
        z.extract();
        f = new File("D:\\DSS\\encryption\\" + "abcd.zipe");
        f.delete();
        ProjPID p = new ProjPID();
        
        p.createProc("D:\\DSS\\decryption\\abcd");
        System.out.println("Destroyed!!!!!!");
        z.zipFolder();
        e.encrypt();
        f = new File("D:\\DSS\\decryption\\" + "abcd.zipe");
        f.delete();
         p.deleteDirectory(new File("D:\\DSS\\decryption\\" + "abcd"));
        } 
        catch (CryptoException ex) {
            Logger.getLogger(ProjPID.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ProjPID.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}