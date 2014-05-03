/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biobook.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;

/**
 *
 * @author Maxime
 */
public class Chercheur implements Serializable{
    static private final long serialVersionUID = 6L;
    private String password;
    private String name;
    private String firstName;
    private String mail;
    private String login;
    
    private HashSet<Experience> listExperiences;

    public Chercheur(String login, String password, String name, String firstName, String mail) {
        this.password = password;
        this.name = name;
        this.firstName = firstName;
        this.mail = mail;
        this.login = login;
        this.listExperiences = new HashSet<Experience>();
    }
    
    public Chercheur(String password, String name, String firstName, String mail) {
        this.password = password;
        this.name = name;
        this.firstName = firstName;
        this.mail = mail;
        StringBuffer log = new StringBuffer(firstName);
        log.append(".");
        log.append(name);
        this.login = log.toString();
        this.listExperiences = new HashSet<Experience>();
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return name;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMail() {
        return mail;
    }

    public String getLogin() {
        return login;
    }

    public HashSet<Experience> getListExperiences() {
        return listExperiences;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String name) {
        this.name = name;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setListExperiences(HashSet<Experience> listExperiences) {
        this.listExperiences = listExperiences;
    }

    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Chercheur other = (Chercheur) obj;
        return Objects.equals(this.login, other.login);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("\t\n"); 
        s.append("\tlogin=");
        s.append(login);
        s.append("\n\tpassword=");
        s.append(password);
        s.append(",\n\tname=");
        s.append(name);
        s.append(",\n\tfirstName=");
        s.append(firstName);
        s.append(",\n\tmail=");
        s.append(mail);
        s.append(",\n\tExperiences=");
        for(Experience exp : listExperiences){
            s.append(exp.getLabel());
            s.append("\n\t");
        }
        
        return s.toString();
    }
}
