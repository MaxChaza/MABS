/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biobook.controller;

import biobook.model.DocumentJoint;
import java.util.HashSet;

/**
 *
 * @author Maxime
 */
public class GererDocument extends GererDocumentJoint {

    public GererDocument() {
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
