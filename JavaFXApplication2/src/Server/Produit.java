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

import object.Stock;

/**
 *
 * @author mohci
 */
public class Produit {
    
    public static void AjouterProduit(Stock p) throws SQLException{
        Stock data = null;
        
        String query = String.format("INSERT INTO stock (produit, quantité, fournisseur, Date) VALUES ('%s', '%s', '%s', '%s')",
                                   p.getProduit(), p.getQuantité(), p.getFournisseur(), p.getDate());
        
        UpdateQuery(query);
        
        
    }
    
    public static void updateStock(Stock astock) throws SQLException {
    String query = "UPDATE stock SET "
                 + "produit = '" + astock.getProduit() + "', "
                 + "quantité = '" + astock.getQuantité() + "', "
                 + "fournisseur = '" + astock.getFournisseur() + "', "
                 + "Date = '" + astock.getDate() + "' "
                 + "WHERE id_stock = " + astock.getId();
    System.out.println(astock.getId());
    // Exécute la mise à jour
    UpdateQuery(query);
    
    
}
    
    public static List<Stock> SearchStock(String searchInput) throws SQLException {
        String query = "SELECT * FROM stock WHERE fournisseur LIKE '%" + searchInput + "%' OR produit LIKE '%"+searchInput+"%' ";
        List<Stock> data = new ArrayList<>();
        
        System.out.println("Executing query: " + query);
        ResultSet rs = SendQuery(query);

        while (rs.next()) {
            int id_stock = rs.getInt("id_stock");
            String produit = rs.getString("produit");
            String quantité = rs.getString("quantité");
            String fournisseur = rs.getString("fournisseur");
            String date = rs.getString("date");
            data.add(new Stock(id_stock, produit, quantité,fournisseur, date));
        }
        
        System.out.println("Number of employees found: " + data.size());
        return data;
    }
}
