package object;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class CaisseF implements Serializable{
    private  transient SimpleIntegerProperty id_caisse;
    private  transient SimpleStringProperty date;
    private  transient SimpleIntegerProperty montant;
    private  transient SimpleIntegerProperty paye;
    private  transient String tests;
    private  transient SimpleStringProperty patient;
    
    
    public CaisseF(int Id, String Date, int Montant, int Paye, String Tests, String Patient) {
        this.id_caisse = new SimpleIntegerProperty(Id);
        this.date = new SimpleStringProperty(Date);
        this.montant = new SimpleIntegerProperty(Montant);
        this.paye = new SimpleIntegerProperty(Paye);
        this.tests = Tests;
        this.patient = new SimpleStringProperty(Patient);
        
    }

    public SimpleIntegerProperty getId() {
        return id_caisse;
    }

    public void setId(SimpleIntegerProperty id_caisse) {
        this.id_caisse = id_caisse;
    }
    public SimpleStringProperty getDate() {
        return date;
    }

    public void setDate(SimpleStringProperty date) {
        this.date = date;
    }
    public SimpleIntegerProperty getMontant() {
        return montant;
    }

    public void setMontant(SimpleIntegerProperty montant) {
        this.montant = montant;
    }

    public SimpleIntegerProperty getPaye() {
        return paye;
    }

    public void setPaye(SimpleIntegerProperty paye) {
        this.paye = paye;
    }

    public String getTests() {
        return tests;
    }
    public void setTests(String Tests) {
        this.tests = Tests;
    }
    
    public String testsProperty() {
        return tests;
    }

    

    public SimpleStringProperty getPatient() {
        return patient;
    }

    public void setPatient(SimpleStringProperty patient) {
        this.patient = patient;
    }

    

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeInt(id_caisse.get());
        s.writeUTF(date.get());
        s.writeInt(montant.get());
        s.writeInt(paye.get());
        s.writeUTF(tests);
        s.writeUTF(patient.get());
        
    }
    
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        id_caisse = new SimpleIntegerProperty(s.readInt());
        date = new SimpleStringProperty(s.readUTF());
        montant = new SimpleIntegerProperty(s.readInt());
        paye = new SimpleIntegerProperty(s.readInt());
        tests = s.readUTF();
        patient = new SimpleStringProperty(s.readUTF());
        
    }

}