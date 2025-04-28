
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
public class Stock implements Serializable{
    private  transient SimpleIntegerProperty id_stock;
    private  transient SimpleStringProperty produit;
    private  transient SimpleStringProperty quantité;
    private  transient SimpleStringProperty fournisseur;
    private  transient SimpleStringProperty date;
    
    public Stock(int Id,  String Produit,  String Quantité, String Fournisseur, String Date) {
        this.id_stock = new SimpleIntegerProperty(Id);
        this.produit = new SimpleStringProperty(Produit);
        this.quantité = new SimpleStringProperty(Quantité);
        this.fournisseur = new SimpleStringProperty(Fournisseur);
        this.date = new SimpleStringProperty(Date);
        
    }

    public int getId() {
        return id_stock.get();
    }
    public String getProduit() {
        return produit.get();
    }
    public String getQuantité() {
        return quantité.get();
    }
    public String getFournisseur() {
        return fournisseur.get();
    }

    public String getDate() {
        return date.get();
    }

    // Property getters for table binding
    public IntegerProperty idProperty() {
        return id_stock;
    }
    public StringProperty produitProperty() {
        return produit;
    }
    public StringProperty quantitéProperty() {
        return quantité;
    }

    public StringProperty fournisseurProperty() {
        return fournisseur;
    }
  
    public StringProperty dateProperty() {
        return date;
    }
    
    
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeObject(getId());
        s.writeObject(getProduit());
        s.writeObject(getQuantité());
        s.writeObject(getFournisseur());
        s.writeObject(getDate());
    }
    
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        id_stock = new SimpleIntegerProperty((int) s.readObject());
        produit = new SimpleStringProperty((String) s.readObject());
        quantité = new SimpleStringProperty((String) s.readObject());
        fournisseur = new SimpleStringProperty((String) s.readObject());
        date = new SimpleStringProperty((String) s.readObject());
    }
}


