package Server;

import static Server.DbConnect.SendQuery;
import static Server.DbConnect.UpdateQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import object.PersonnelS;
import object.Stock;

public class Personnel {

    // Méthode pour ajouter un nouvel utilisateur à la base de données
    public static PersonnelS ajouterPersonnel(PersonnelS aPersonnel) throws SQLException {
        String query = "INSERT INTO utilisateur (nom, prenom, mot_de_passe, email, position, Telephone, Genre, DateNaissance) "
                     + "VALUES ('" + aPersonnel.getNom() + "', '" + aPersonnel.getPrenom() + "', '" + aPersonnel.getMotDePass() + "', '"
                     + aPersonnel.getEmail() + "', '" + aPersonnel.getPosition() + "', '" + aPersonnel.getTelephone() + "', '"
                     + aPersonnel.getGenre() + "', '" + aPersonnel.getDateNaissance() + "')";
        
        UpdateQuery(query);
        
        return aPersonnel;
    }
    public static void updatePersonnel(PersonnelS aPersonnel) throws SQLException {
    String query = "UPDATE utilisateur SET "
                 + "nom = '" + aPersonnel.getNom() + "', "
                 + "prenom = '" + aPersonnel.getPrenom() + "', "
                 + "mot_de_passe = '" + aPersonnel.getMotDePass() + "', "
                 + "email = '" + aPersonnel.getEmail() + "', "
                 + "position = '" + aPersonnel.getPosition() + "', "
                 + "Telephone = '" + aPersonnel.getTelephone() + "', "
                 + "Genre = '" + aPersonnel.getGenre() + "', "
                 + "DateNaissance = '" + aPersonnel.getDateNaissance() + "' "
                 + "WHERE id = " + aPersonnel.getId();
    System.out.println(aPersonnel.getId());
    // Exécute la mise à jour
    UpdateQuery(query);
    
    
}

    public static List<PersonnelS> SearchPersonnel(String searchInput) throws SQLException {
        String query = "SELECT * FROM utilisateur WHERE nom LIKE '%" + searchInput + "%' OR prenom LIKE '%"+searchInput+"%' ";
        List<PersonnelS> data = new ArrayList<>();
        
        System.out.println("Executing query: " + query);
        ResultSet rs = SendQuery(query);

        while (rs.next()) {
            int id = rs.getInt("id");
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            String mot_de_passe = rs.getString("mot_de_passe");
            String email = rs.getString("email");
            String position = rs.getString("position");
            String telephone = rs.getString("telephone");
            String genre = rs.getString("genre");
            String dateNaissance = rs.getString("dateNaissance");
           
            data.add(new PersonnelS(id, nom, prenom, mot_de_passe, email, position, telephone, genre, dateNaissance));
        }
        
        System.out.println("Number of employees found: " + data.size());
        return data;
    }
}
