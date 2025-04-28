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

/**
 *
 * @author mohci
 */
public class Echantillon implements Serializable{
    private transient SimpleStringProperty code;
    private transient String type;
    private transient List<Integer> idTests;
    
    
    public Echantillon(String Code, String Type, List<Integer> IdTests) {
        this.code = new SimpleStringProperty(Code);
        this.type = Type;
        this.idTests = new ArrayList<>(IdTests);
    }

    public SimpleStringProperty getCode() {
        return code;
    }

    public void setCode(SimpleStringProperty id) {
        this.code = id;
    }
    
    public SimpleStringProperty codeProperty() {
        return code;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String id) {
        this.type = id;
    }
    
    public String typeProperty() {
        return type;
    }

    public List<Integer> getIdTests() {
        return idTests;
    }

    public void setIdTests(SimpleListProperty<Integer> desc) {
        this.idTests = desc;
    }

    
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeUTF(code.get());
        s.writeUTF(type);
        s.writeObject(idTests);
    }
    
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        code = new SimpleStringProperty(s.readUTF());
        type = s.readUTF();
        idTests = (List<Integer>) s.readObject();
    }
    
}
