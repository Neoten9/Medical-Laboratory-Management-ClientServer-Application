/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package object;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author mohci
 */
public class Patient implements Serializable{
    private  transient SimpleIntegerProperty id;
    private  transient SimpleStringProperty nom;
    private  transient SimpleStringProperty prenom;
    private  transient SimpleStringProperty dateNaissance;
    private  transient SimpleStringProperty telephone;
    private  transient SimpleStringProperty email;
    private  transient SimpleStringProperty genre;

    public Patient(int Id, String Nom, String Prenom, String DateNaissance, String Telephone, String Email, String Genre) {
        this.id = new SimpleIntegerProperty(Id);
        this.nom = new SimpleStringProperty(Nom);
        this.prenom = new SimpleStringProperty(Prenom);
        this.dateNaissance = new SimpleStringProperty(DateNaissance);
        this.telephone = new SimpleStringProperty(Telephone);
        this.email = new SimpleStringProperty(Email);
        this.genre = new SimpleStringProperty(Genre);
        
    }

    public int getId() {
        return id.get();
    }

    public String getNom() {
        return nom.get();
    }
    
    public String getPrenom() {
        return prenom.get();
    }
    
    public String getDateNaissance() {
        return dateNaissance.get();
    }
    
    public String getTelephone() {
        return telephone.get();
    }
   
    public String getEmail() {
        return email.get();
    }
    
    public String getGenre() {
        return genre.get();
    }

    // Property getters for table binding
    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty nomProperty() {
        return nom;
    }
    
    public StringProperty prenomProperty() {
        return prenom;
    }
    
    public StringProperty dateNaissanceProperty() {
        return dateNaissance;
    }

    public StringProperty telephoneProperty() {
        return telephone;
    }
    
    public StringProperty emailProperty() {
        return email;
    }
    
    public StringProperty genreProperty() {
        return genre;
    }
    
    
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeObject(getId());
        s.writeObject(getNom());
        s.writeObject(getPrenom());
        s.writeObject(getDateNaissance());
        s.writeObject(getTelephone());
        s.writeObject(getEmail());
        s.writeObject(getGenre());
    }
    
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        id = new SimpleIntegerProperty((int) s.readObject());
        nom = new SimpleStringProperty((String) s.readObject());
        prenom = new SimpleStringProperty((String) s.readObject());
        dateNaissance = new SimpleStringProperty((String) s.readObject());
        telephone = new SimpleStringProperty((String) s.readObject());
        email = new SimpleStringProperty((String) s.readObject());
        genre = new SimpleStringProperty((String) s.readObject());
    }
}

