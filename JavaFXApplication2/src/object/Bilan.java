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
public class Bilan implements Serializable{
    private transient SimpleIntegerProperty id;
    private transient SimpleIntegerProperty id_patient;
    private transient SimpleIntegerProperty id_caisse;
    private transient List<String> code_echantillon;
    private transient List<Integer> idTests;
    private transient SimpleStringProperty medcin;
    private transient SimpleStringProperty remarque;
    private transient SimpleStringProperty date;
    
    public Bilan(int Id, int Id_patient, int Id_caisse, List<String> Code_echantillon, List<Integer> IdTests, String Medcin, String Remarque, String Date) {
        this.id = new SimpleIntegerProperty(Id);
        this.id_patient = new SimpleIntegerProperty(Id_patient);
        this.id_caisse = new SimpleIntegerProperty(Id_caisse);
        this.code_echantillon = new ArrayList<>(Code_echantillon);
        this.idTests = new ArrayList<>(IdTests);
        this.medcin = new SimpleStringProperty(Medcin);
        this.remarque = new SimpleStringProperty(Remarque);
        this.date = new SimpleStringProperty(Date);
    }

    public SimpleIntegerProperty getId() {
        return id;
    }

    public void setId(SimpleIntegerProperty id) {
        this.id = id;
    }

    public SimpleIntegerProperty getId_patient() {
        return id_patient;
    }

    public void setId_patient(SimpleIntegerProperty id_patient) {
        this.id_patient = id_patient;
    }

    public SimpleIntegerProperty getId_caisse() {
        return id_caisse;
    }

    public void setId_caisse(SimpleIntegerProperty id_caisse) {
        this.id_caisse = id_caisse;
    }

    public List<String> getCode_echantillon() {
        return code_echantillon;
    }

    public void setCode_echantillon(List<String> code_echantillon) {
        this.code_echantillon = code_echantillon;
    }

    public List<Integer> getIdTests() {
        return idTests;
    }

    public void setIdTests(List<Integer> idTests) {
        this.idTests = idTests;
    }

    public SimpleStringProperty getMedcin() {
        return medcin;
    }

    public void setMedcin(SimpleStringProperty medcin) {
        this.medcin = medcin;
    }

    public SimpleStringProperty getRemarque() {
        return remarque;
    }

    public void setRemarque(SimpleStringProperty remarque) {
        this.remarque = remarque;
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
        s.writeInt(id_patient.get());
        s.writeInt(id_caisse.get());
        s.writeObject(code_echantillon);
        s.writeObject(idTests);
        s.writeUTF(medcin.get());
        s.writeUTF(remarque.get());
        s.writeUTF(date.get());
    }
    
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        id = new SimpleIntegerProperty(s.readInt());
        id_patient = new SimpleIntegerProperty(s.readInt());
        id_caisse = new SimpleIntegerProperty(s.readInt());
        code_echantillon = (List<String>) s.readObject();
        idTests = (List<Integer>) s.readObject();
        medcin = new SimpleStringProperty(s.readUTF());
        remarque = new SimpleStringProperty(s.readUTF());
        date = new SimpleStringProperty(s.readUTF());
    }
}
