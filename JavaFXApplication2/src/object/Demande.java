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
public class Demande implements Serializable{
    private transient Bilan bilan;
    private transient List<Echantillon> echantillon;
    private transient List<Resultat> resultat;
    private transient Caisse caisse;
    private transient List<Analyse> analyse;
    private transient Patient patient;
    
    public Demande(Bilan b, List<Echantillon> e, List<Resultat> r, Caisse c, List<Analyse> a, Patient p){
        this.bilan = b;
        this.echantillon = new ArrayList<>(e);
        this.resultat = new ArrayList<>(r);
        this.caisse = c;
        this.analyse = new ArrayList<>(a);
        this.patient = p;
    }

    public Bilan getBilan() {
        return bilan;
    }

    public void setBilan(Bilan bilan) {
        this.bilan = bilan;
    }

    public List<Echantillon> getEchantillon() {
        return echantillon;
    }

    public void setEchantillon(List<Echantillon> echantillon) {
        this.echantillon = echantillon;
    }

    public List<Resultat> getResultat() {
        return resultat;
    }

    public void setResultat(List<Resultat> resultat) {
        this.resultat = resultat;
    }

    public Caisse getCaisse() {
        return caisse;
    }

    public void setCaisse(Caisse caisse) {
        this.caisse = caisse;
    }

    public List<Analyse> getAnalyse() {
        return analyse;
    }

    public void setAnalyse(List<Analyse> analyse) {
        this.analyse = analyse;
    }
    
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeObject(bilan);
        s.writeObject(echantillon);
        s.writeObject(resultat);
        s.writeObject(caisse);
        s.writeObject(analyse);
        s.writeObject(patient);
    }
    
     private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        bilan =  (Bilan) s.readObject();
        echantillon = (List<Echantillon>) s.readObject();
        resultat = (List<Resultat>) s.readObject();
        caisse =  (Caisse) s.readObject();
        analyse = (List<Analyse>) s.readObject();
        patient =  (Patient) s.readObject();
    }
}
