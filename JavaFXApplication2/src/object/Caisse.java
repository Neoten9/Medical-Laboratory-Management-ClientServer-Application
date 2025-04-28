/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package object;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author mohci
 */
public class Caisse implements Serializable{
    private  transient SimpleIntegerProperty id;
    private  transient SimpleIntegerProperty montant;
    private  transient SimpleIntegerProperty paye;
    private  transient String tests;
    private  transient SimpleStringProperty patient;
    private  transient SimpleStringProperty date;
    
    public Caisse(int Id, int Montant, int Paye, String Tests, String Patient, String Date) {
        this.id = new SimpleIntegerProperty(Id);
        this.montant = new SimpleIntegerProperty(Montant);
        this.paye = new SimpleIntegerProperty(Paye);
        this.tests = Tests;
        this.patient = new SimpleStringProperty(Patient);
        this.date = new SimpleStringProperty(Date);
    }

    public SimpleIntegerProperty getId() {
        return id;
    }

    public void setId(SimpleIntegerProperty id) {
        this.id = id;
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
    
    public String testsProperty() {
        return tests;
    }

    public void setTests(String Tests) {
        this.tests = Tests;
    }

    public SimpleStringProperty getPatient() {
        return patient;
    }

    public void setPatient(SimpleStringProperty patient) {
        this.patient = patient;
    }

    public SimpleStringProperty getDate() {
        return date;
    }

    public void setDate(SimpleStringProperty date) {
        this.date = date;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeInt(id.get());
        s.writeInt(montant.get());
        s.writeInt(paye.get());
        s.writeUTF(tests);
        s.writeUTF(patient.get());
        s.writeUTF(date.get());
    }
    
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        id = new SimpleIntegerProperty(s.readInt());
        montant = new SimpleIntegerProperty(s.readInt());
        paye = new SimpleIntegerProperty(s.readInt());
        tests = s.readUTF();
        patient = new SimpleStringProperty(s.readUTF());
        date = new SimpleStringProperty(s.readUTF());
    }

}
