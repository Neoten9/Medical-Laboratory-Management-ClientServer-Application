package Server;

import static Server.DbConnect.SendQuery;
import static Server.DbConnect.UpdateQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import object.Fournisseur;
import object.PaiementF;

public class PaiementFournisseur {
    public static void ajouterPaiementF(PaiementF p) throws SQLException {
        String query = String.format("INSERT INTO paiementfournisseur (fournisseur, produit, montant, Date) VALUES ('%s', '%s', '%s', '%s')",
                                   p.getFournisseur(), p.getProduit(), p.getMontant(), p.getDate());
        UpdateQuery(query);

        
    }
    public static void updatePaiementFournisseur(Fournisseur apaiementF) throws SQLException {
    String query = "UPDATE paiementfournisseur SET "
                 + "fournisseur = '" + apaiementF.getFournisseur() + "', "
                 + "produit = '" + apaiementF.getProduit() + "', "
                 + "montant = '" + apaiementF.getMontant() + "', "
                 + "date = '" + apaiementF.getDate() + "' "
                 + "WHERE id_paiement_fournisseur = " + apaiementF.getId();
    System.out.println(apaiementF.getId());
    // Exécute la mise à jour
    UpdateQuery(query);
    
    
}
    public static List<Fournisseur> SearchFournisseur(String searchInput) throws SQLException {
        String query = "SELECT * FROM paiementfournisseur WHERE fournisseur LIKE '%" + searchInput + "%' ";
        List<Fournisseur> data = new ArrayList<>();
        
        System.out.println("Executing query: " + query);
        ResultSet rs = SendQuery(query);

        while (rs.next()) {
            int id_paiement_fournisseur = rs.getInt("id_paiement_fournisseur");
            String fournisseur = rs.getString("fournisseur");
            String produit = rs.getString("produit");
            String montant = rs.getString("montant");
            String date = rs.getString("date");
            data.add(new Fournisseur(id_paiement_fournisseur, fournisseur, produit, montant, date));
        }
        
        System.out.println("Number of employees found: " + data.size());
        return data;
    }
}
