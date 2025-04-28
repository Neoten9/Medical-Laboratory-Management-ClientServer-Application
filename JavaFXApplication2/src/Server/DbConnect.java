package Server;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbConnect {

    private static final String URL = "jdbc:mysql://localhost/labo";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            Logger.getLogger(DbConnect.class.getName()).log(Level.SEVERE, "JDBC Driver not found!", e);
        }
    }

    // ✅ Fix: Add missing getConnection() method
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static ResultSet SendQuery(String Query) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection(); // ✅ Use the new method
            statement = connection.createStatement();
            resultSet = statement.executeQuery(Query);
            return resultSet;
        } catch (SQLException ex) {
            Logger.getLogger(DbConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void UpdateQuery(String Query) {
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = getConnection(); // ✅ Use the new method
            stmt = conn.createStatement();
            int affectedRows = stmt.executeUpdate(Query);
            System.out.println("Updated successfully, affected rows: " + affectedRows);
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Integer getIdBack(String Query) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection(); // ✅ Use the new method
            pstmt = conn.prepareStatement(Query, Statement.RETURN_GENERATED_KEYS);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
