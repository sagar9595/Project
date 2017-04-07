/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.IOException;
import java.util.Scanner;
import javax.bluetooth.*;

/**
 *
 * @author Sumedh
 */
public class Project {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        
        LocalDevice localDevice = LocalDevice.getLocalDevice();
        System.out.println("Address: "+localDevice.getBluetoothAddress());
        System.out.println("Name: "+localDevice.getFriendlyName());
        
        BluetoothConn conn = new BluetoothConn();
        conn.startServer();
        
        //sending string to android
        System.out.println("Enter String to be sent : ");
        String stringToSend = in.nextLine();
        conn.sendString(stringToSend);
        String receivedString = conn.receiveString();
        System.out.println("Received string is " + receivedString);
        
       
        for(int i = 0; i < 2; i++) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            if(conn.checkStatus()) {
                System.out.println("success");
                continue;
            }
            System.out.println("connection terminated");
            System.exit(0);
        }
    }
    
}
