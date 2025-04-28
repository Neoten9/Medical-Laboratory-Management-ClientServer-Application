package Server;

import static Server.DbConnect.SendQuery;
import static Server.DbConnect.UpdateQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import object.Employé;
import object.PaiementE;

public class PaiementEmployé {
    public static PaiementE ajouterPaiementE(PaiementE p) throws SQLException {
        String employé = p.getEmployéN() + " " + p.getEmployéP(); // Concatenate with space
        String query = String.format("INSERT INTO paiementemploye (employé, position, montant, date) VALUES ('%s', '%s', '%s', '%s')",
                                     employé, p.getPosition(), p.getMontant(), p.getDate());
        UpdateQuery(query);

        query = String.format("SELECT * FROM paiementemploye WHERE employé='%s' AND position='%s' AND montant='%s' AND date='%s'",
                              employé, p.getPosition(), p.getMontant(), p.getDate());
        ResultSet rs = SendQuery(query);

        if (rs.next()) {
            return new PaiementE(rs.getInt("id_paiement_employe"),
                                 p.getEmployéN(),
                                 p.getEmployéP(),
                                 rs.getString("position"),
                                 rs.getString("montant"),
                                 rs.getString("date"));
        }
        return null;
    }
    public static void updatePaiementEmployé(Employé apaiementE) throws SQLException {
    String query = "UPDATE paiementemploye SET "
                 + "employé = '" + apaiementE.getEmploye() + "', "
                 + "position = '" + apaiementE.getPosition() + "', "
                 + "montant = '" + apaiementE.getMontant() + "', "
                 + "date = '" + apaiementE.getDate() + "' "
                 + "WHERE id_paiement_employe = " + apaiementE.getId();
    System.out.println(apaiementE.getId());
    // Exécute la mise à jour
    UpdateQuery(query);
    
    
}

    public static List<Employé> SearchEmployé(String searchInput) throws SQLException {
        String query = "SELECT * FROM paiementemploye WHERE employé LIKE '%" + searchInput + "%' ";
        List<Employé> data = new ArrayList<>();
        
        System.out.println("Executing query: " + query);
        ResultSet rs = SendQuery(query);

        while (rs.next()) {
            int id_paiement_employe = rs.getInt("id_paiement_employe");
            String employé = rs.getString("employé");
            String position = rs.getString("position");
            String montant = rs.getString("montant");
            String date = rs.getString("date");
            data.add(new Employé(id_paiement_employe, employé, position, montant, date));
        }
        
        System.out.println("Number of employees found: " + data.size());
        return data;
    }
}
