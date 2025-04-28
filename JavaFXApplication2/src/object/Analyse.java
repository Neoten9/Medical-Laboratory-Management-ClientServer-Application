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
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author mohci
 */
public class Analyse implements Serializable{
    private transient SimpleIntegerProperty id;
    private transient SimpleStringProperty nomAnalyse;
    private transient SimpleStringProperty prix;
    private transient SimpleStringProperty nomCourant;
    private transient SimpleStringProperty echantillon;
    private transient List<String> desc;
    private transient List<String> unite;
    private transient List<String> valeurUs;
    
    public Analyse(int Id, String NomAnalyse, String Prix, String NomCourant, String Echantillon, List<String> Desc, List<String> Unite, List<String> ValeurUs) {
        this.id = new SimpleIntegerProperty(Id);
        this.nomAnalyse = new SimpleStringProperty(NomAnalyse);
        this.prix = new SimpleStringProperty(Prix);
        this.nomCourant = new SimpleStringProperty(NomCourant);
        this.echantillon = new SimpleStringProperty(Echantillon);
        this.desc = new ArrayList<>(Desc);
        this.unite = new ArrayList<>(Unite);
        this.valeurUs = new ArrayList<>(ValeurUs);
    }

    public SimpleIntegerProperty getId() {
        return id;
    }

    public void setId(SimpleIntegerProperty id) {
        this.id = id;
    }
    
    public IntegerProperty idProperty() {
        return id;
    }

    public String getNomAnalyse() {
        return nomAnalyse.get();
    }

    public void setNomAnalyse(SimpleStringProperty nomAnalyse) {
        this.nomAnalyse = nomAnalyse;
    }
    
    public StringProperty nomAnalyseProperty() {
        return nomAnalyse;
    }

    public String getPrix() {
        return prix.get();
    }

    public void setPrix(SimpleStringProperty prix) {
        this.prix = prix;
    }
    
    public StringProperty prixProperty() {
        return prix;
    }

    public String getNomCourant() {
        return nomCourant.get();
    }

    public void setNomCourant(SimpleStringProperty nomCourant) {
        this.nomCourant = nomCourant;
    }
    
    public StringProperty nomCourantProperty() {
        return nomCourant;
    }

    public String getEchantillon() {
        return echantillon.get();
    }

    public void setEchantillon(SimpleStringProperty echantillon) {
        this.echantillon = echantillon;
    }
    
    public StringProperty echantillonProperty() {
        return echantillon;
    }

    public List<String> getDesc() {
        return desc;
    }

    public void setDesc(SimpleListProperty<String> desc) {
        this.desc = desc;
    }

    public List<String> getUnite() {
        return unite;
    }

    public void setUnite(SimpleListProperty<String> unite) {
        this.unite = unite;
    }
    
    public List<String> getValeurUs() {
        return valeurUs;
    }

    public void setValeurUs(SimpleListProperty<String> valeurUs) {
        this.valeurUs = valeurUs;
    }
    
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeInt(id.get());
        s.writeUTF(nomAnalyse.get());
        s.writeUTF(prix.get());
        s.writeUTF(nomCourant.get());
        s.writeUTF(echantillon.get());
        s.writeObject(desc);
        s.writeObject(unite);
        s.writeObject(valeurUs);
    }
    
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        id = new SimpleIntegerProperty(s.readInt());
        nomAnalyse = new SimpleStringProperty(s.readUTF());
        prix = new SimpleStringProperty(s.readUTF());
        nomCourant = new SimpleStringProperty(s.readUTF());
        echantillon = new SimpleStringProperty(s.readUTF());
        desc = (List<String>) s.readObject();
        unite = (List<String>) s.readObject();
        valeurUs = (List<String>) s.readObject();
    }
    
}
