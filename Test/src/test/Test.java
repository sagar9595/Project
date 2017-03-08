/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.File;
import java.util.*;  
  
public class Test {  
    String hi;
     void func(Test s){
        s = new Test();
        s.hi = "yooooo!!!";
    }
   public  static void main(String[] args) {
         String s = "sagar sharma";
       StringTokenizer st = new StringTokenizer(s);  
       
      
        
      // printing next token  
      System.out.println("Next token is : " + st.nextToken());
      
           System.out.println("Next token is : " + st.nextToken());
           String str[] = new String[5];
           str[1] = "sagar";
           str[2] = "sharma";
           System.out.println(str[1] + " " + str[2]);
           
           Test b = new Test();
           Test c = new Test();
           b.hi = "hellllo!!!!!";
           System.out.println(b.hi);
           c.func(b);
           System.out.println(b.hi);
           
           File f = new File("D:\\DSS\\encryption\\" + "2234567");
           System.out.println(f.exists());
   }      
}  