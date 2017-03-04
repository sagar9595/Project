/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package directorymove;

/**
 *
 * @author Sagar
 */
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class DirectoryMove {
    public static void main(String[] args) {
        String source = "D:\\demo";
        File srcDir = new File(source);

        String destination = "D:\\target";
        File destDir = new File(destination);

        try {
            //
            // Move the source directory to the destination directory.
            // The destination directory must not exists prior to the
            // move process.
            //
            FileUtils.moveDirectory(srcDir, destDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}