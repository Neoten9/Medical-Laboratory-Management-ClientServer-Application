/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mohci
 */
public class DbConnect {
    
    
    
    public static ResultSet SendQuery(String Query) {
        
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {

            String url = "jdbc:mysql://localhost/labo";
            String user = "root";
            String passwordDB = "";

            Class.forName("com.mysql.cj.jdbc.Driver");
            
            connection = DriverManager.getConnection(url, user, passwordDB);
            
            statement = connection.createStatement();
            
            resultSet = statement.executeQuery(Query);
            
            
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DbConnect.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DbConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return resultSet;
    }
    
    public static void UpdateQuery(String Query) {
        
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {

            String url = "jdbc:mysql://localhost/labo";
            String user = "root";
            String passwordDB = "";

            Class.forName("com.mysql.cj.jdbc.Driver");
            
            
            try (Connection conn = DriverManager.getConnection(url, user, passwordDB);
                Statement stmt = conn.createStatement()) {
            
                int affectedRows = stmt.executeUpdate(Query);
                System.out.println("Inserted successfully, affected rows: " + affectedRows);
            
            } catch (SQLException e) {
                System.out.println("SQL Error: " + e.getMessage());
                e.printStackTrace();
            }
            
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DbConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
}
