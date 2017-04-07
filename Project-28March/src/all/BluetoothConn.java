/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package all;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.bluetooth.*;
import javax.microedition.io.*;


/**
 *
 * @author Sumedh
 */
public class BluetoothConn {
    //Create a UUID for SPP
    UUID uuid = new UUID("1101", true);
    //Create the servicve url
    String connectionString = "btspp://localhost:" + uuid +";name=Sample SPP Server";
    
    StreamConnectionNotifier streamConnNotifier;
    StreamConnection connection;
    RemoteDevice dev;
    OutputStream outStream;
    InputStream inStream;
	
    public void startServer() throws IOException{    
        //open server url
        this.streamConnNotifier = (StreamConnectionNotifier)Connector.open( connectionString );

        //Wait for client connection
        System.out.println("\nServer Started. Waiting for clients to connect...");
        this.connection = streamConnNotifier.acceptAndOpen();

        this.dev = RemoteDevice.getRemoteDevice(connection);
        System.out.println("Remote device address: " + dev.getBluetoothAddress());
        System.out.println("Remote device name: " + dev.getFriendlyName(true));
    }
    
    public void sendString(String message) throws IOException {
        this.outStream = connection.openOutputStream();
        message += "\n";
        
        byte[] msgBuffer = message.getBytes();
        outStream.write(msgBuffer);
    }
    
    public String receiveString() throws IOException {
        this.inStream = connection.openInputStream();
        
        BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));
	String receivedString = bReader.readLine();
        return receivedString;
    }
    
    public boolean checkStatus() {
        BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));
	String receivedString;
        try {
            receivedString = bReader.readLine();
            if(receivedString == null)
                return false;
        } catch(IOException e) {
            return false;
        }
        if(receivedString.equals("a"))
            return true;
        return false;
    }
    
}
