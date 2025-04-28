
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
public class Fournisseur implements Serializable{
    private  transient SimpleIntegerProperty id_paiement_fournisseur;
    private  transient SimpleStringProperty fournisseur;
    private  transient SimpleStringProperty produit;
    private  transient SimpleStringProperty montant;
    private  transient SimpleStringProperty date;
    
    public Fournisseur(int Id, String Fournisseur, String Produit, String Montant, String Date) {
        this.id_paiement_fournisseur = new SimpleIntegerProperty(Id);
        this.fournisseur = new SimpleStringProperty(Fournisseur);
        this.produit = new SimpleStringProperty(Produit);
        this.montant = new SimpleStringProperty(Montant);
        this.date = new SimpleStringProperty(Date);
        
    }

    public int getId() {
        return id_paiement_fournisseur.get();
    }

    public String getFournisseur() {
        return fournisseur.get();
    }
    
    public String getProduit() {
        return produit.get();
    }
    public String getMontant() {
        return montant.get();
    }

    public String getDate() {
        return date.get();
    }

    // Property getters for table binding
    public IntegerProperty idProperty() {
        return id_paiement_fournisseur;
    }

    public StringProperty fournisseurProperty() {
        return fournisseur;
    }
    
    public StringProperty produitProperty() {
        return produit;
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
        s.writeObject(getFournisseur());
        s.writeObject(getProduit());
        s.writeObject(getMontant());
        s.writeObject(getDate());
    }
    
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        id_paiement_fournisseur = new SimpleIntegerProperty((int) s.readObject());
        fournisseur = new SimpleStringProperty((String) s.readObject());
        produit = new SimpleStringProperty((String) s.readObject());
        montant = new SimpleStringProperty((String) s.readObject());
        date = new SimpleStringProperty((String) s.readObject());
    }
}


