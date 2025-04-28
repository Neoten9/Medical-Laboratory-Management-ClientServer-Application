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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import object.Bilan;
import object.Patient;
import object.Resultat;

/**
 *
 * @author mohci
 */
public class Validation {
    public static List<Resultat> searchValidation(String searchInput) throws SQLException{
        String query = "SELECT * FROM resultat WHERE id_echantillon LIKE '%"+searchInput+"%' AND v_biologist = 'valide' AND v_medcin != 'valide' ";
        List<Resultat> resultat = new ArrayList<>();
        
        ResultSet rs = SendQuery(query);

            while (rs.next()) {
                
                String result = rs.getString("resultat");
               
                String[] resultArray = result.split(",");
        
                List<String> resultList = Arrays.asList(resultArray);
                
                resultat.add(new Resultat(rs.getInt("id"),
                        rs.getInt("id_analyse"),
                        rs.getString("id_echantillon"),
                        rs.getString("v_biologist"),
                        rs.getString("v_medcin"),
                        resultList));
            }
    
        return resultat;
    }
    
    public static Bilan getBilan(String id) throws SQLException{
        
        String query = "SELECT * FROM bilan WHERE code_echantillon LIKE '%"+id+"%' ";
        
        ResultSet rs = SendQuery(query);

            rs.next();
                
            String codeEchan = rs.getString("code_echantillon");
            String[] codeEchanA = codeEchan.split(",");
            List<String> codeEchanL = Arrays.asList(codeEchanA);
            
            String idTests = rs.getString("id_tests");
            List<Integer> idTestsL = Arrays.stream(idTests.split(",")).map(Integer::parseInt).collect(Collectors.toList());
            
                
                
                Bilan bilan = new Bilan(rs.getInt("id"),
                    rs.getInt("id_patient"),
                    rs.getInt("id_caisse"),
                    codeEchanL,
                    idTestsL,
                    rs.getString("medcin_r"),
                    rs.getString("remarque"),
                    rs.getString("date"));
            
    
        return bilan;
    }
    
    public static Patient getPatient(int id) throws SQLException{
        String idP = Integer.toString(id);
        String query = "SELECT * FROM patient WHERE id_patient = '"+idP+"' ";
        
        ResultSet rsP = SendQuery(query);
                            
            rsP.next();
            Patient patient = new Patient(rsP.getInt("id_patient"),
                    rsP.getString("nom"),
                    rsP.getString("prenom"),
                    rsP.getString("date_de_naissance"),
                    rsP.getString("telephone"),
                    rsP.getString("email"),
                    rsP.getString("genre"));
            
    
        return patient;
    }
    
    public static void validerMedResultat(Resultat resultat){
        String idA = Integer.toString(resultat.getId_analyse().get());
        String id = Integer.toString(resultat.getId().get());
        
        String result = "";
        for (String r : resultat.getResult()){
            result = result + r + ",";
        }
              
        String query = "UPDATE resultat SET   v_medcin = 'valide' WHERE id = '"+id+"';";
        
        UpdateQuery(query);
    }
}
