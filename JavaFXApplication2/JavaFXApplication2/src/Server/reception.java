/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import static Server.DbConnect.SendQuery;
import static Server.DbConnect.UpdateQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import object.Patient;

/**
 *
 * @author mohci
 */
public class reception {
    
    public static List<Patient> SearchPatient(String searchinput) throws SQLException{
        
        String query = "SELECT * FROM patient WHERE nom LIKE '%"+searchinput+"%' OR prenom LIKE '%"+searchinput+"%'";
        List<Patient> data = new ArrayList<>();
            
        ResultSet rs = SendQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id_patient");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String dateNaissance = rs.getString("date_de_naissance");
                String telephone = rs.getString("telephone");
                String email = rs.getString("email");
                String genre = rs.getString("genre");
                data.add(new Patient(id, nom, prenom, dateNaissance, telephone, email, genre));
            }
    
        return data;
        
    }
    
    public static Patient ajouterPatient(Patient p) throws SQLException{
        Patient data = null;
        
        String query = String.format("INSERT INTO Patient (nom, prenom, date_de_naissance, telephone, email, genre) VALUES ('%s', '%s', '%s', '%s', '%s', '%s')",
                                   p.getNom(), p.getPrenom(), p.getDateNaissance(), p.getTelephone(), p.getEmail(), p.getGenre());
        
        UpdateQuery(query);
        
        query = "SELECT * FROM patient WHERE nom='" + p.getNom() + "' AND prenom='" + p.getPrenom() + "' AND date_de_naissance='" + p.getDateNaissance() + "' AND telephone='" + p.getTelephone() + "'";
        ResultSet rs = SendQuery(query);
        rs.next();
        data = new Patient(rs.getInt("id_patient"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("date_de_naissance"),
                            rs.getString("telephone"),
                            rs.getString("email"),
                            rs.getString("genre"));
        
        return data;
    }
}
