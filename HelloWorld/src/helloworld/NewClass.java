
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package helloworld;
import java.util.*;

public class NewClass {
 //   @override
   public static void main(String[] args){
       LinkedList<Integer> n = new LinkedList<>();
       n.add(10);
       n.add(21);
       n.add(311);
       System.out.println("Size of arrylist is " + n.contains(3));
      ListIterator it = n.listIterator();
      while(it.hasNext()){
          System.out.println("Val is " + it.next());
          
      }
       System.out.println("Val is " + it.previous());
       try{
       System.out.println("Val is " + n.get(2));
       }catch(Exception e){
           System.out.println("ArrayList Out of Bound");
       }
   }
}
