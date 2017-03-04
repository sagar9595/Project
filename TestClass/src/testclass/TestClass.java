/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testclass;

/**
 *
 * @author Sagar
 */
class TestClass {
    public static void main(String args[] ) throws Exception {
        String toCheck = "abcdE";
        String x = "";
        for(int i=0; i<toCheck.length(); i++){
          if(isLowerCase(toCheck.charAt(i))){
              x = x + String.valueOf(toCheck.charAt(i)).toUpperCase();
          }
          else{
            x = x + String.valueOf(toCheck.charAt(i)).toLowerCase();  
          }
           
        }
       System.out.println(x);
        
    }
    static boolean isLowerCase(char ch) {
        return (ch >= 'a' && ch <= 'z');
}
}