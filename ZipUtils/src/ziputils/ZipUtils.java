/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ziputils;

/**
 *
 * @author Sagar
 */

   
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipUtils
{
  private static final int  BUFFER_SIZE = 4096;

  private  void extractFile(ZipInputStream in, File outdir, String name) throws IOException
  {
    byte[] buffer = new byte[BUFFER_SIZE];
    BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(outdir,name)));
    int count = -1;
    while ((count = in.read(buffer)) != -1)
      out.write(buffer, 0, count);
    out.close();
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
   * @param zipfile Input .zip file
   * @param outdir Output directory
   */
  public void extract(File zipfile, File outdir)
  {
    try
    {
      ZipInputStream zin = new ZipInputStream(new FileInputStream(zipfile));
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
        /* this part is necessary because file entry can come before
         * directory entry where is file located
         * i.e.:
         *   /foo/foo.txt
         *   /foo/
         */
        dir = dirpart(name);
      
        if( dir != null )
          mkdirs(outdir,dir);

        extractFile(zin, outdir, name);
      }
      zin.close();
    } 
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  public static void main(String[] args){
      ZipUtils z = new ZipUtils();
      File a = new File("D:\\a.zip");
      File b = new File("D:");
    
      z.extract(a, b);
  }
}
