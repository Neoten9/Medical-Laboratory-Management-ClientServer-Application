/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import static Server.DbConnect.SendQuery;
import static Server.DbConnect.UpdateQuery;
import static Server.DbConnect.getIdBack;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import object.Analyse;
import object.Bilan;
import object.Caisse;
import object.Demande;
import object.Echantillon;
import object.Patient;
import object.Resultat;
import java.sql.Statement;
import java.sql.Connection;


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
    
    public static List<Analyse> SearchAnalyse(String searchinput) throws SQLException{
        
        String query = "SELECT * FROM analyses WHERE nom_analyse LIKE '%"+searchinput+"%' OR nom_courant LIKE '%"+searchinput+"%'";
        List<Analyse> data = new ArrayList<>();
            
        ResultSet rs = SendQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id_analyse");
                String nom_analyse = rs.getString("nom_analyse");
                String prix = rs.getString("prix");
                String nom_courant = rs.getString("nom_courant");
                String échantillon = rs.getString("échantillon");
                String descr = rs.getString("descr");
                String unite = rs.getString("unite");
                String valeurUs = rs.getString("valeur_us");
                
                // Split the string by commas
                String[] descrArray = descr.split(",");
                String[] uniteArray = unite.split(",");
                String[] valeurUsArray = valeurUs.split(",");
        
                // Convert the array to a list
                List<String> descrList = Arrays.asList(descrArray);
                List<String> uniteList = Arrays.asList(uniteArray);
                List<String> valeurUsList = Arrays.asList(valeurUsArray);
                
                data.add(new Analyse(id, nom_analyse, prix, nom_courant, échantillon, descrList, uniteList, valeurUsList));
            }
    
        return data;
        
    }
    
    public static void sauvBilan(List<Echantillon> echantillon, Caisse caisse, Bilan bilan){
        
        String query = String.format("INSERT INTO caisse (montant, paye, tests, patient, date) VALUES ('%s', '%s', '%s', '%s', '%s')",
                                   caisse.getMontant().get(), caisse.getPaye().get(), caisse.getTests(), caisse.getPatient().get(), caisse.getDate().get() );
        int caisseId = getIdBack(query);
        
        String codeE = "";
        for (Echantillon echan : echantillon){
            
            String idtests = "";
            for (Integer idtest : echan.getIdTests()){
                System.out.println(idtest);
                System.out.println(echan.getCode());
                query = String.format("INSERT INTO resultat (id_analyse, id_echantillon, v_biologist, v_medcin, resultat) VALUES ('%s', '%s', '%s', '%s', '%s')",
                                   idtest, echan.getCode().get(), "", "", "");
                UpdateQuery(query);
                
                idtests = idtests + Integer.toString(idtest) + ",";
            }
            
            query = String.format("INSERT INTO echantillon (code, type, id_tests) VALUES ('%s', '%s', '%s')",
                                   echan.getCode().get(), echan.getType(), idtests );
            UpdateQuery(query);
            
            codeE = codeE + echan.getCode() + ",";    
        }
        
        String code = "";
        for (Echantillon echan : echantillon){
            code = code + echan.getCode().get() + ",";
        }
        
        String tests = "";
        for (Integer id : bilan.getIdTests()){
            tests = tests + Integer.toString(id) + ",";
        }
        query = String.format("INSERT INTO bilan (id_patient, id_caisse, code_echantillon, id_tests, medcin_r, remarque, date) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                                  bilan.getId_patient().get(),  caisseId, code, tests, bilan.getMedcin().get(), bilan.getRemarque().get(), bilan.getDate().get());
        UpdateQuery(query);
    }
    
    public static List<Demande> SearchDemande(String searchinput) throws SQLException {
    String query = "SELECT b.* FROM bilan b INNER JOIN patient p ON b.id_patient = p.id_patient WHERE p.nom LIKE '%" + searchinput + "%' OR b.id LIKE '%" + searchinput + "%';";
    List<Demande> demandes = new ArrayList<>();

    // Open resources at the start
    try (Connection conn = DbConnect.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {

        while (rs.next()) {
            String codeEchan = rs.getString("code_echantillon");
            List<String> codeEchanL = Arrays.asList(codeEchan.split(","));

            List<Integer> idTestsL = Arrays.stream(rs.getString("id_tests").split(","))
                                           .map(Integer::parseInt)
                                           .collect(Collectors.toList());

            Bilan bilan = new Bilan(
                rs.getInt("id"),
                rs.getInt("id_patient"),
                rs.getInt("id_caisse"),
                codeEchanL,
                idTestsL,
                rs.getString("medcin_r"),
                rs.getString("remarque"),
                rs.getString("date")
            );

            // Fetch Patient data
            String idPatient = Integer.toString(rs.getInt("id_patient"));
            String queryP = "SELECT * FROM `patient` WHERE id_patient = '" + idPatient + "';";
            Patient patient = null;

            try (Statement stmtP = conn.createStatement();
                 ResultSet rsP = stmtP.executeQuery(queryP)) {
                if (rsP.next()) {
                    patient = new Patient(
                        rsP.getInt("id_patient"),
                        rsP.getString("nom"),
                        rsP.getString("prenom"),
                        rsP.getString("date_de_naissance"),
                        rsP.getString("telephone"),
                        rsP.getString("email"),
                        rsP.getString("genre")
                    );
                }
            }

            // Fetch Caisse data
            String idCaisse = Integer.toString(rs.getInt("id_caisse"));
            String queryC = "SELECT * FROM `caisse` WHERE id_caisse = '" + idCaisse + "';";
            Caisse caiss = null;

            try (Statement stmtC = conn.createStatement();
                 ResultSet rsC = stmtC.executeQuery(queryC)) {
                if (rsC.next()) {
                    caiss = new Caisse(
                        rsC.getInt("id_caisse"),
                        rsC.getInt("montant"),
                        rsC.getInt("paye"),
                        rsC.getString("tests"),
                        rsC.getString("patient"),
                        rsC.getString("date")
                    );
                }
            }

            // Fetch Echantillon, Resultat, and Analyse
            List<Echantillon> echantillon = new ArrayList<>();
            List<Resultat> resultat = new ArrayList<>();
            List<Analyse> analyse = new ArrayList<>();

            for (String echan : codeEchanL) {
                String queryE = "SELECT * FROM `echantillon` WHERE code = '" + echan + "';";
                try (Statement stmtE = conn.createStatement();
                     ResultSet rsE = stmtE.executeQuery(queryE)) {
                    while (rsE.next()) {
                        List<Integer> idTestsEL = Arrays.stream(rsE.getString("id_tests").split(","))
                                                        .map(Integer::parseInt)
                                                        .collect(Collectors.toList());
                        echantillon.add(new Echantillon(echan, rsE.getString("type"), idTestsEL));
                    }
                }

                String queryR = "SELECT * FROM `resultat` WHERE id_echantillon = '" + echan + "';";
                try (Statement stmtR = conn.createStatement();
                     ResultSet rsR = stmtR.executeQuery(queryR)) {
                    while (rsR.next()) {
                        List<String> resultL = Arrays.asList(rsR.getString("resultat").split(","));
                        resultat.add(new Resultat(
                            rsR.getInt("id"),
                            rsR.getInt("id_analyse"),
                            echan,
                            rsR.getString("v_biologist"),
                            rsR.getString("v_medcin"),
                            resultL
                        ));

                        String queryA = "SELECT * FROM `analyses` WHERE id_analyse = '" + rsR.getInt("id_analyse") + "';";
                        try (Statement stmtA = conn.createStatement();
                             ResultSet rsA = stmtA.executeQuery(queryA)) {
                            if (rsA.next()) {
                                List<String> descrList = Arrays.asList(rsA.getString("descr").split(","));
                                List<String> uniteList = Arrays.asList(rsA.getString("unite").split(","));
                                List<String> valeurUsList = Arrays.asList(rsA.getString("valeur_us").split(","));

                                analyse.add(new Analyse(
                                    rsA.getInt("id_analyse"),
                                    rsA.getString("nom_analyse"),
                                    rsA.getString("prix"),
                                    rsA.getString("nom_courant"),
                                    rsA.getString("échantillon"),
                                    descrList,
                                    uniteList,
                                    valeurUsList
                                ));
                            }
                        }
                    }
                }
            }

            demandes.add(new Demande(bilan, echantillon, resultat, caiss, analyse, patient));
        }
    }

    return demandes;
}

    
    public static void updateCaisse(Caisse caisse){
        
                String montant = Integer.toString(caisse.getMontant().get());
                String paye = Integer.toString(caisse.getPaye().get());
                String idC = Integer.toString(caisse.getId().get());
        String query = "UPDATE caisse SET date = '"+caisse.getDate().get()+"', montant = '"+montant+"', paye = '"+paye+"', tests = '"+caisse.getTests()+"', patient = '"+caisse.getPatient().get()+"' WHERE id_caisse = '"+idC+"';";
        
        UpdateQuery(query);
    }
}
