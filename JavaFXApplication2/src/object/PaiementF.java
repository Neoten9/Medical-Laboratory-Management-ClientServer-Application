package object;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PaiementF implements Serializable {
    private transient IntegerProperty id_paiement_fournisseur;
    private transient StringProperty fournisseur;
    private transient StringProperty produit;
    private transient StringProperty montant;
    private transient StringProperty date;

    public PaiementF(int id_paiement_fournisseur, String fournisseur, String produit, String montant, String date) {
        this.id_paiement_fournisseur = new SimpleIntegerProperty(id_paiement_fournisseur);
        this.fournisseur = new SimpleStringProperty(fournisseur);
        this.produit = new SimpleStringProperty(produit);
        this.montant = new SimpleStringProperty(montant);
        this.date = new SimpleStringProperty(date);
    }

    public int getId_paiement_fournisseur() {
        return id_paiement_fournisseur.get();
    }

    public void setId_paiement_fournisseur(int id) {
        this.id_paiement_fournisseur.set(id);
    }

    public String getFournisseur() {
        return fournisseur.get();
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur.set(fournisseur);
    }

    public String getProduit() {
        return produit.get();
    }

    public void setProduit(String produit) {
        this.produit.set(produit);
    }

    public String getMontant() {
        return montant.get();
    }

    public void setMontant(String montant) {
        this.montant.set(montant);
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    // Serialization methods
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeInt(getId_paiement_fournisseur());
        s.writeObject(getFournisseur());
        s.writeObject(getProduit());
        s.writeObject(getMontant());
        s.writeObject(getDate());
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.id_paiement_fournisseur = new SimpleIntegerProperty(s.readInt());
        this.fournisseur = new SimpleStringProperty((String) s.readObject());
        this.produit = new SimpleStringProperty((String) s.readObject());
        this.montant = new SimpleStringProperty((String) s.readObject());
        this.date = new SimpleStringProperty((String) s.readObject());
    }
}
