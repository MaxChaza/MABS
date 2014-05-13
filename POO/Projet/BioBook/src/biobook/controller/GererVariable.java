/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biobook.controller;

import biobook.model.DocumentJoint;
import java.sql.Connection;
import java.util.HashSet;

/**
 *
 * @author Maxime
 */
public class GererVariable extends GererDocumentJoint{
    private static final String reqInsert = "INSERT INTO variable (labelVariable, valeur, labelExperience)VALUES(?,?,?)";    
    private static final String reqAllByExp= "SELECT * FROM variable where labelExperience=?";
    private static final String reqDeleteAllByExp= "DELETE * FROM variable WHERE labelExperience=?"; 
    private static final String reqDelete= "DELETE FROM variable WHERE labelExperience=? and labelVariable=?";
    private static Connection c;
    
    public GererVariable() {
        super();
    }

    @Override
    public void insert(DocumentJoint docJoint, String labExp) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public HashSet<DocumentJoint> getAllByExp(String labExp) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteAllByExp(String labExp) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(String DocJoint, String labExp) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
