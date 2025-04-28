package object;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PaiementE implements Serializable {
    private transient SimpleIntegerProperty id_paiement_employe;
    private transient StringProperty employéN;
    private transient StringProperty employéP;
    private transient StringProperty position;
    private transient StringProperty montant;
    private transient StringProperty date;

    // Constructeur avec id
    public PaiementE(int id_paiement_employe, String employéN, String employéP, String position, String montant, String date) {
        this.id_paiement_employe = new SimpleIntegerProperty(id_paiement_employe);
        this.employéN = new SimpleStringProperty(employéN);
        this.employéP = new SimpleStringProperty(employéP);
        this.position = new SimpleStringProperty(position);
        this.montant = new SimpleStringProperty(montant);
        this.date = new SimpleStringProperty(date);
    }

    // Constructeur sans id
    public PaiementE(String employéN, String employéP, String position, String montant, String date) {
        this.employéN = new SimpleStringProperty(employéN);
        this.employéP = new SimpleStringProperty(employéP);
        this.position = new SimpleStringProperty(position);
        this.montant = new SimpleStringProperty(montant);
        this.date = new SimpleStringProperty(date);
    }

    // Getters et setters...
    public int getId_paiement_employe() {
        return id_paiement_employe.get();
    }

    public void setId_paiement_employe(int id) {
        this.id_paiement_employe.set(id);
    }

    public String getEmployéN() {
        return employéN.get();
    }

    public void setEmployéN(String employéN) {
        this.employéN.set(employéN);
    }

    public String getEmployéP() {
        return employéP.get();
    }

    public void setEmployéP(String employéP) {
        this.employéP.set(employéP);
    }

    public String getPosition() {
        return position.get();
    }

    public void setPosition(String position) {
        this.position.set(position);
    }

    public String getMontant() {
        return montant.get();
    }

    public void setMontant(String montant) {
        this.montant.set(montant);
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeInt(getId_paiement_employe());
        s.writeObject(getEmployéN());
        s.writeObject(getEmployéP());
        s.writeObject(getPosition());
        s.writeObject(getMontant());
        s.writeObject(getDate());
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.id_paiement_employe = new SimpleIntegerProperty(s.readInt());
        this.employéN = new SimpleStringProperty((String) s.readObject());
        this.employéP = new SimpleStringProperty((String) s.readObject());
        this.position = new SimpleStringProperty((String) s.readObject());
        this.montant = new SimpleStringProperty((String) s.readObject());
        this.date = new SimpleStringProperty((String) s.readObject());
    }
}
