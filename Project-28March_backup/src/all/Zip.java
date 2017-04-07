/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package all;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
/**
 *
 * @author Sagar
 */

/**
 * ZipFolder() method is used to zip the folder
 * extract() method is used to unzip the folder
 */
public class Zip {
    
    
 private static final int  BUFFER_SIZE = 4096;
 private String destZipFile = "";
 private String srcFolder = "";
 private final String dLocation = "D:\\DSS\\decryption\\";
 private final String eLocation = "D:\\DSS\\encryption\\";
    
 Zip(String a){
     srcFolder = a;
     destZipFile = srcFolder + ".zipe";
 }

 
    /* Zip */
  public void zipFolder() throws Exception {
    ZipOutputStream zip;
    FileOutputStream fileWriter;
    fileWriter = new FileOutputStream( dLocation + destZipFile);
    zip = new ZipOutputStream(fileWriter);
    addFolderToZip("", dLocation + srcFolder, zip);
    zip.flush();
    zip.close();
    fileWriter.flush();
    fileWriter.close();
  }

   private void addFileToZip(String path, String srcFile, ZipOutputStream zip)
      throws Exception {

    File folder = new File(srcFile);
    if (folder.isDirectory()) {
      addFolderToZip(path, srcFile, zip);
    } else {
      byte[] buf = new byte[1024];
      int len;
      FileInputStream in = new FileInputStream(srcFile);
      zip.putNextEntry(new ZipEntry(path + "\\" + folder.getName()));
      while ((len = in.read(buf)) > 0) {
        zip.write(buf, 0, len);
      }
      in.close();
    }
      }

  private void addFolderToZip(String path, String srcFolder, ZipOutputStream zip)
      throws Exception {
    File folder = new File(srcFolder);

    for (String fileName : folder.list()) {
      if (path.equals("")) {
        addFileToZip(folder.getName(), srcFolder + "\\" + fileName, zip);
      } else {
        addFileToZip(path + "\\" + folder.getName(), srcFolder + "\\" + fileName, zip);
      }
    }
  }
  /* Unzip*/
 
  private  void extractFile(ZipInputStream in, File outdir, String name) throws IOException
  {
    byte[] buffer = new byte[BUFFER_SIZE];
    FileOutputStream s = new FileOutputStream(new File(outdir,name));
    BufferedOutputStream out = new BufferedOutputStream(s);
    int count;
    while ((count = in.read(buffer)) != -1)
      out.write(buffer, 0, count);
    out.flush();
    out.close();
    s.flush();
    s.close();
  }

  private void mkdirs(File outdir,String path)
  {
      boolean c;
    File d = new File(outdir, path);
    if( d.exists() ) {
      } else {
        c= d.mkdirs();
      }
   
  }

  private String dirpart(String name){
  //  int s = name.lastIndexOf( File.separatorChar );
     
   int s =  name.lastIndexOf("\\");
   
    return s == -1 ? null : name.substring( 0, s );
  }

  /***
   * Extract zipfile to outdir with complete directory structure
   * @throws all.CantUnZipException this will be thrown when there is collision
   *         in algorithm
   */
  public void extract() throws CantUnZipException
  {
      File zipfile = new File(eLocation + destZipFile);
      File outdir = new File(dLocation);
    try
    {
        FileInputStream s = new FileInputStream(zipfile);
      ZipInputStream zin = new ZipInputStream(s);
      ZipEntry entry;
      String name, dir;
      while ((entry = zin.getNextEntry()) != null)
      {
        name = entry.getName();
        if( entry.isDirectory() )
        {
          mkdirs(outdir,name);
          continue;
        }
       
        dir = dirpart(name);
      
        if( dir != null )
          mkdirs(outdir,dir);

        extractFile(zin, outdir, name);
      }
      zin.close();
      s.close();
    } 
    catch (IOException e)
    {
        //e.printStackTrace();
        throw(new CantUnZipException("Wrong password"));
    }
  }
  
/*  public static void main(String[] args){
      Zip z = new Zip("MY PROJECT");
     try {
         z.extract();
         z.zipFolder();
     } catch (Exception ex) {
         Logger.getLogger(Zip.class.getName()).log(Level.SEVERE, null, ex);
     }
  }
  */
}


class CantUnZipException extends Exception{
    
    public CantUnZipException(){       
    }
    
    public CantUnZipException(String ex){
        super(ex);
    }
}