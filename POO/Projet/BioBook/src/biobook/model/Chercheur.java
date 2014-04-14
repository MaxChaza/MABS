/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biobook.model;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Maxime
 */
public class Chercheur {
    private String password;
    private String name;
    private String firstName;
    private String mail;
    private String login;
    
    private ArrayList<Experience> listExperiences;

    public Chercheur(String login, String password, String name, String firstName, String mail) {
        this.password = password;
        this.name = name;
        this.firstName = firstName;
        this.mail = mail;
        this.login = login;
        this.listExperiences = new ArrayList<Experience>();
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
        this.listExperiences = new ArrayList<Experience>();
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
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

    public ArrayList<Experience> getListExperiences() {
        return listExperiences;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
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

    public void setListExperiences(ArrayList<Experience> listExperiences) {
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
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.login, other.login)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuffer s = new StringBuffer();;
        s.append("#############\n"); 
        s.append("login=");
        s.append(login);
        s.append("\npassword=");
        s.append(password);
        s.append(",\nname=");
        s.append(name);
        s.append(",\nfirstName=");
        s.append(firstName);
        s.append(",\nmail=");
        s.append(mail);
        s.append(",\nExperiences=");
        for(Experience exp : listExperiences)
            s.append(exp);
        
        return s.toString();
    }
}
