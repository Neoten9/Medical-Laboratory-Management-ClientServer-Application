package object;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PersonnelS implements Serializable {
    private transient SimpleIntegerProperty id;
    private transient StringProperty nom;
    private transient StringProperty prenom;
    private transient StringProperty mot_de_pass;
    private transient StringProperty email;
    private transient StringProperty position;
    private transient StringProperty telephone;
    private transient StringProperty genre;
    private transient StringProperty dateNaissance;

    // Constructeur avec id
    public PersonnelS(int id, String nom, String prenom, String mot_de_pass, String email,  String position, String telephone, String genre, String dateNaissance) {
        this.id = new SimpleIntegerProperty(id);
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
        this.mot_de_pass = new SimpleStringProperty(mot_de_pass);
        this.email = new SimpleStringProperty(email);
        this.position = new SimpleStringProperty(position);
        this.telephone = new SimpleStringProperty(telephone);
        this.genre = new SimpleStringProperty(genre);
        this.dateNaissance = new SimpleStringProperty(dateNaissance);
    }

    // Constructeur sans id
    public PersonnelS(String nom, String prenom, String mot_de_pass, String email,  String position, String telephone, String genre, String dateNaissance) {
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
        this.mot_de_pass = new SimpleStringProperty(mot_de_pass);
        this.email = new SimpleStringProperty(email);
        this.position = new SimpleStringProperty(position);
        this.telephone = new SimpleStringProperty(telephone);
        this.genre = new SimpleStringProperty(genre);
        this.dateNaissance = new SimpleStringProperty(dateNaissance);
        
    }

    // Getters et setters...
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getNom() {
        return nom.get();
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public String getPrenom() {
        return prenom.get();
    }

    public void setPrenom(String prenom) {
        this.prenom.set(prenom);
    }
    public String getMotDePass() {
        return mot_de_pass.get();
    }

    public void setMotDePass(String mot_de_pass) {
        this.mot_de_pass.set(mot_de_pass);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }
    
    public String getPosition() {
        return position.get();
    }

    public void setPosition(String position) {
        this.position.set(position);
    }
    
    public String getTelephone() {
        return telephone.get();
    }

    public void setTelephone(String telephone) {
        this.telephone.set(telephone);
    }
    
    public String getGenre() {
        return genre.get();
    }

    public void setGenre(String genre) {
        this.genre.set(genre);
    }

    public String getDateNaissance() {
        return dateNaissance.get();
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance.set(dateNaissance);
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeInt(getId());
        s.writeObject(getNom());
        s.writeObject(getPrenom());
        s.writeObject(getMotDePass());
        s.writeObject(getEmail());
        s.writeObject(getPosition());
        s.writeObject(getTelephone());
        s.writeObject(getGenre());
        s.writeObject(getDateNaissance());
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.id = new SimpleIntegerProperty(s.readInt());
        this.nom = new SimpleStringProperty((String) s.readObject());
        this.prenom = new SimpleStringProperty((String) s.readObject());
        this.mot_de_pass = new SimpleStringProperty((String) s.readObject());
        this.email = new SimpleStringProperty((String) s.readObject());
        this.position = new SimpleStringProperty((String) s.readObject());
        this.telephone = new SimpleStringProperty((String) s.readObject());
        this.genre = new SimpleStringProperty((String) s.readObject());
        this.dateNaissance = new SimpleStringProperty((String) s.readObject());
    }
}
