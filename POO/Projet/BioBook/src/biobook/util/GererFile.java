/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biobook.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author Maxime
 */
public class GererFile {
    
    /** copie le fichier source dans le fichier resultat
     * retourne vrai si cela réussit
     */
    public static void copyFile(File source, File dest) throws FileNotFoundException, IOException{
         
        // Declaration et ouverture des flux
        FileInputStream sourceFile = new FileInputStream(source);
        FileOutputStream destinationFile = new FileOutputStream(dest, true);

        // Lecture par segment de 0.5Mo 
        byte buffer[] = new byte[512 * 1024];
        int nbLecture;

        while ((nbLecture = sourceFile.read(buffer)) != -1){
                destinationFile.write(buffer, 0, nbLecture);
        }

        if( destinationFile!=null)
            destinationFile.close();

        if( destinationFile!=null)
            sourceFile.close();
    }
}
