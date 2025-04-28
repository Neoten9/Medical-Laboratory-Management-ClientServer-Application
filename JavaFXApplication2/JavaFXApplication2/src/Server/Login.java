/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import static Server.DbConnect.SendQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mohci
 */
public class Login {
    
    public static String HandleLogin(String userName, String password) throws SQLException{
        
            
            
            String query = "SELECT * FROM `utilisateur` WHERE utilisateur.nom ='"+userName+"' AND utilisateur.mot_de_passe ='"+password+"'"  ;
            
            
            
            ResultSet resultSet = SendQuery(query);
            if(resultSet.next()){
                return resultSet.getString("position");
            }else{
                return "null";
            }
            
            
        
    }
}
