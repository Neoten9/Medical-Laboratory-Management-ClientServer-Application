
package Server;

import static Server.DbConnect.SendQuery;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import object.Resultat;
import object.CaisseF;
public class Caise {
   public static List<CaisseF> SearchCaisseF(String searchInput) throws SQLException {
        String query = "SELECT * FROM caisse WHERE patient LIKE '%" + searchInput + "%' ";
        List<CaisseF> data = new ArrayList<>();
        
        System.out.println("Executing query: " + query);
        ResultSet rs = SendQuery(query);

        while (rs.next()) {
            int id_caisse = rs.getInt("id_caisse");
            String date = rs.getString("date");
            int montant = rs.getInt("montant");
            int paye = rs.getInt("paye");
            String tests = rs.getString("tests");
            String patient = rs.getString("patient");
            
            
            data.add(new CaisseF(id_caisse, date, montant, paye, tests, patient));
        }
        
        System.out.println("Number of employees found: " + data.size());
        return data;
    }
}
