
package object;




import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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
public class Employé implements Serializable{
    private  transient SimpleIntegerProperty id_paiement_employe;
    private  transient SimpleStringProperty employé;
    private  transient SimpleStringProperty position;
    private  transient SimpleStringProperty montant;
    private  transient SimpleStringProperty date;
    
    public Employé(int Id, String Employe, String Position, String Montant, String Date) {
        this.id_paiement_employe = new SimpleIntegerProperty(Id);
        this.employé = new SimpleStringProperty(Employe);
        this.position = new SimpleStringProperty(Position);
        this.montant = new SimpleStringProperty(Montant);
        this.date = new SimpleStringProperty(Date);
        
    }

    public int getId() {
        return id_paiement_employe.get();
    }

    public String getEmploye() {
        return employé.get();
    }
    
    public String getPosition() {
        return position.get();
    }
    public String getMontant() {
        return montant.get();
    }

    public String getDate() {
        return date.get();
    }

    // Property getters for table binding
    public IntegerProperty idProperty() {
        return id_paiement_employe;
    }

    public StringProperty employéProperty() {
        return employé;
    }
    
    public StringProperty positionProperty() {
        return position;
    }
    public StringProperty montantProperty() {
        return montant;
    }
  
    public StringProperty dateProperty() {
        return date;
    }
    
    
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeObject(getId());
        s.writeObject(getEmploye());
        s.writeObject(getPosition());
        s.writeObject(getMontant());
        s.writeObject(getDate());
    }
    
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        id_paiement_employe = new SimpleIntegerProperty((int) s.readObject());
        employé = new SimpleStringProperty((String) s.readObject());
        position = new SimpleStringProperty((String) s.readObject());
        montant = new SimpleStringProperty((String) s.readObject());
        date = new SimpleStringProperty((String) s.readObject());
    }
}


