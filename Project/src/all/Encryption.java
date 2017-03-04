/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package all;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
 
/**
 *
 * @author Sagar
 */
public class Encryption {
    
    private  String ALGORITHM;
    private String TRANSFORMATION, key ;
    private String en = "D://DSS//encryption//";
    private String de = "D://DSS//decryption//";
    private String lc;
    
    
    Encryption(String al, String tr, String ki, String l){
        ALGORITHM = al;
        TRANSFORMATION = tr;
        key = ki;
        lc = l;
    }
   
    public void changeAlgo(String al, String tr){
        ALGORITHM = al;
        TRANSFORMATION = tr;
        
    }
 
    public void encrypt() throws CryptoException {
        File inputFile, outputFile;
        inputFile = new File("D://DSS//decryption//" + lc + ".zipe");
        outputFile = new File("D://DSS//encryption//" + lc + ".zip");
        doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
    }
 
    public void decrypt() throws CryptoException {
        File inputFile, outputFile;
        inputFile = new File("D://DSS//encryption//" + lc + ".zip");
        outputFile = new File("D://DSS//encryption//" + lc + ".zipe");
        doCrypto(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
    }
 
    @SuppressWarnings("ConvertToTryWithResources")
    private void doCrypto(int cipherMode, String key, File inputFile, File outputFile) throws CryptoException {
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);
             
            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);
             
            byte[] outputBytes = cipher.doFinal(inputBytes);
             
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);
             
            inputStream.close();
            outputStream.close();
             
        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException | IOException ex) {
            throw new CryptoException("Error encrypting/decrypting file", ex);
        }
    }   
    
  /*  public static void main(String[] args){
        Encryption e;
        e = new Encryption( "Blowfish", "Blowfish", "asdfghjkl");
        try {
            e.encrypt("D:\\DSS\\decryption\\MY PROJECT.zip", "D:\\DSS\\decryption\\MY PROJECT.z");
        } catch (CryptoException ex) {
            Logger.getLogger(Encryption.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
}
 class CryptoException extends Exception {
 
    public CryptoException() {
    }
 
    public CryptoException(String message, Throwable throwable) {
        super(message, throwable);
    }
}