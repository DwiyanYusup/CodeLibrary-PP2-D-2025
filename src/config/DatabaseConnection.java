package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL =
        "jdbc:mysql://localhost:3306/db_perpustakaan?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection getConnection() {
        try {
            // 🔥 BARIS PENTING
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Koneksi berhasil!");
            return conn;
        } catch (Exception e) {
            System.out.println("Koneksi gagal!");
            e.printStackTrace();
            return null;
        }
    }
}
