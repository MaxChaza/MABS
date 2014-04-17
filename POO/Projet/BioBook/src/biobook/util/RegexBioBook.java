package biobook.util;
import java.util.regex.*;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Maxime
 */
public class RegexBioBook {
    private static Pattern pattern;
    private static Matcher matcher;
    private String pat;
    
    String mail = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$";
    
    public RegexBioBook(String regex){
        
        switch (regex) {
            case "mail" :
                pat=mail;
                break;

//            case valeur2 :
//             Liste d'instructions
//             break;
//
//            case valeurN... :
//             Liste d'instructions
//             break;

            default: 
                pat="";
            }
        pattern = Pattern.compile(pat);
    }
    
    public boolean test(String mot){
        matcher = pattern.matcher(mot.toUpperCase());
        return matcher.matches();
    }
}   