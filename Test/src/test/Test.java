/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.*;  
  
public class Test {  
   public static void main(String[] args) {
         String s = "sagar sharma";
       StringTokenizer st = new StringTokenizer(s);  
       
      
        
      // printing next token  
      System.out.println("Next token is : " + st.nextToken());
      
           System.out.println("Next token is : " + st.nextToken());
           String str[] = new String[5];
           str[1] = "sagar";
           str[2] = "sharma";
           System.out.println(str[1] + " " + str[2]);
   }      
}  