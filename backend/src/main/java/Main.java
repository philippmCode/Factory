import java.sql.*;

public class Main {

    public static void main(String[] args) {
        String jdbcUrl = System.getenv("JDBC_URL");
        String db_username = System.getenv("DB_USERNAME");
        String password = System.getenv("PASSWORD");

        if (jdbcUrl == null || db_username == null || password == null) {
            System.err.println("Fehler: Eine oder mehrere Umgebungsvariablen (JDBC_URL, USERNAME, PASSWORD) sind nicht gesetzt.");
            return; // Beende das Programm, wenn die Variablen fehlen
        }

        try (Connection conn = DriverManager.getConnection(jdbcUrl, db_username, password)) {
            String sql = "SELECT merkmal_bezeichner, band, anlage FROM merkmal";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String merkmal = rs.getString("merkmal_bezeichner");
                String band = rs.getString("band");
                String anlage = rs.getString("anlage");
                System.out.println(merkmal + ", " + band + ", " + anlage);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}