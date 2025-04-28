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
import object.Analyse;
import object.Resultat;

/**
 *
 * @author mohci
 */
public class Plan {
    
    public static List<Resultat> searchEchantillon(String searchInput) throws SQLException{
        String query = "SELECT * FROM resultat WHERE id_echantillon LIKE '%"+searchInput+"%' AND v_biologist != 'valide' ";
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
    
    public static Analyse getAnalyse(int id) throws SQLException{
        String idA = Integer.toString(id);
        String query = "SELECT * FROM analyses WHERE id_analyse = '"+idA+"' ";
        
        ResultSet rs = SendQuery(query);

            rs.next();
                
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
                
                
                Analyse analyse = new Analyse(id,
                        rs.getString("nom_analyse"),
                        rs.getString("prix"),
                        rs.getString("nom_courant"),
                        rs.getString("échantillon"),
                        descrList,
                        uniteList,
                        valeurUsList);
            
    
        return analyse;
    }
    
    public static void validerResultat(Resultat resultat){
        String idA = Integer.toString(resultat.getId_analyse().get());
        String id = Integer.toString(resultat.getId().get());
        
        String result = "";
        for (String r : resultat.getResult()){
            result = result + r + ",";
        }
              
        String query = "UPDATE resultat SET id_analyse = '"+idA+"', id_echantillon = '"+resultat.getId_echantillon().get()+"', v_biologist = 'valide', v_medcin = '', resultat = '"+result+"' WHERE id = '"+id+"';";
        
        UpdateQuery(query);
    }
}
