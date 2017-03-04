/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bluetooth;
import 
/**
 *
 * @author Sagar
 */
public class Bluetooth {

    void locateDevices(){
        private static Object lock=new Object();

 try{
            // 1
            LocalDevice localDevice = LocalDevice.getLocalDevice();

            // 2
            DiscoveryAgent agent = localDevice.getDiscoveryAgent();
            
            // 3
            agent.startInquiry(DiscoveryAgent.GIAC, new MyDiscoveryListener());

            try {
                synchronized(lock){
                    lock.wait();
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Device Inquiry Completed. ");
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
