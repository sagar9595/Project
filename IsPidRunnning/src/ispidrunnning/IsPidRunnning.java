/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ispidrunnning;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class IsPidRunnning {

    public static void main(String[] args) {

        //this function prints all running processes
        showAllProcessesRunningOnWindows();

        //this prints whether or not processID 1300 is running
        System.out.println("is PID 5768 running? " + 
            isProcessIdRunningOnWindows(5768));

    }

    /**
     * Queries {@code tasklist} if the process ID {@code pid} is running.
     * @param pid the PID to check
     * @return {@code true} if the PID is running, {@code false} otherwise
     */
    public static boolean isProcessIdRunningOnWindows(int pid){
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
            ex.printStackTrace();
            System.out.println("Cannot query the tasklist for some reason.");
            System.exit(0);
        }

        return false;

    }

    /**
     * Prints the output of {@code tasklist} including PIDs.
     */
    public static void showAllProcessesRunningOnWindows(){
        try {
            Runtime runtime = Runtime.getRuntime();
            String cmds[] = {"cmd", "/c", "tasklist"};
            Process proc = runtime.exec(cmds);
            InputStream inputstream = proc.getInputStream();
            InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
            String line;
            while ((line = bufferedreader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Cannot query the tasklist for some reason.");
        }
    }
}