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
public class Resultat implements Serializable{
    private transient SimpleIntegerProperty id;
    private transient SimpleIntegerProperty id_analyse;
    private transient SimpleStringProperty id_echantillon;
    private transient SimpleStringProperty v_biologist;
    private transient SimpleStringProperty v_medcin;
    private transient List<String> result;
    
    public Resultat(int Id, int Id_analyse, String Id_echantillon, String V_biologist, String V_medcin, List<String> Result) {
        this.id = new SimpleIntegerProperty(Id);
        this.id_analyse = new SimpleIntegerProperty(Id_analyse);
        this.id_echantillon = new SimpleStringProperty(Id_echantillon);
        this.v_biologist = new SimpleStringProperty(V_biologist);
        this.v_medcin = new SimpleStringProperty(V_medcin);
        this.result = new ArrayList<>(Result);
    }

    public SimpleIntegerProperty getId() {
        return id;
    }

    public void setId(SimpleIntegerProperty id) {
        this.id = id;
    }

    public SimpleIntegerProperty getId_analyse() {
        return id_analyse;
    }

    public void setId_analyse(SimpleIntegerProperty id_analyse) {
        this.id_analyse = id_analyse;
    }

    public SimpleStringProperty getId_echantillon() {
        return id_echantillon;
    }

    public void setId_echantillon(SimpleStringProperty id_echantillon) {
        this.id_echantillon = id_echantillon;
    }

    public SimpleStringProperty getV_biologist() {
        return v_biologist;
    }

    public void setV_biologist(SimpleStringProperty v_biologist) {
        this.v_biologist = v_biologist;
    }

    public SimpleStringProperty getV_medcin() {
        return v_medcin;
    }

    public void setV_medcin(SimpleStringProperty v_medcin) {
        this.v_medcin = v_medcin;
    }

    public List<String> getResult() {
        return result;
    }

    public void setResult(List<String> result) {
        this.result = result;
    }
    
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeInt(id.get());
        s.writeInt(id_analyse.get());
        s.writeUTF(id_echantillon.get());
        s.writeUTF(v_biologist.get());
        s.writeUTF(v_medcin.get());
        s.writeObject(result);
    }
    
     private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        id = new SimpleIntegerProperty(s.readInt());
        id_analyse = new SimpleIntegerProperty(s.readInt());
        id_echantillon = new SimpleStringProperty(s.readUTF());
        v_biologist = new SimpleStringProperty(s.readUTF());
        v_medcin = new SimpleStringProperty(s.readUTF());
        result = (List<String>) s.readObject();
    }
}
