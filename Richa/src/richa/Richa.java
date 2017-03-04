/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package richa;

import java.util.Scanner;
/**
 *
 * @author Sagar
 */
public class Richa {
    public static class Test{
        int pr[] = new int[200];
        int cnt = 0, i,j;
        
    void primArr(){
        int temp[] = new int[200];
        for(i = 0; i<200; i++){
         temp[i] = 0;
        }
        for (i=2; i<200; i++){
            if(temp[i] == 0){
                if((i >= 'a' && i <= 'z') || (i >= 'A' && i <='Z')){
                pr[cnt]=i;
                cnt++;
                }
                for(j=2*i; j<200; j=j+i){
                    temp[j] = 1;
                }
            }
        }
    }
     char  returnValueCharacter(int ch, int arr[]){
       
         char ch1;
         for(i=0; i<= 30; i++){
           if(arr[i] >= ch){
               break;
           }
            }
         
         if( i > 0 && ((arr[i-1]+arr[i]))/2 == ch){
             ch1 = (char)arr [i-1];
         }
         else{
             ch1 = (char)arr[i];
         }
         return ch1;
        }
             
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       String toCheck = "AFREEN";
       String toConvert = "";
       int i = 0;
       Test test = new Test();
       test.primArr();
       
       for (i=0; i<toCheck.length(); i++){
           toConvert = toConvert + String.valueOf(test.returnValueCharacter((int)toCheck.charAt(i), test.pr));
       }
     System.out.println(toConvert);
            
        }
    
    
}
