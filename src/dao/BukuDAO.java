/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.DatabaseConnection;
import model.Buku;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BukuDAO {

    // ===== INSERT =====
    public void insert(Buku buku) {
        String sql = "INSERT INTO buku (id_buku, judul, penulis, penerbit, tahun_terbit, stok) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, buku.getJudul());
            ps.setString(2, buku.getPenulis());
            ps.setString(3, buku.getPenerbit());
            ps.setInt(4, buku.getTahunTerbit());
            ps.setInt(5, buku.getStok());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ===== SELECT ALL =====
    public List<Buku> getAll() {
        List<Buku> list = new ArrayList<>();
        String sql = "SELECT * FROM buku";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Buku buku = new Buku(
                        rs.getInt("id_buku"),
                        rs.getString("judul"),
                        rs.getString("penulis"),
                        rs.getString("penerbit"),
                        rs.getInt("tahun_terbit"),
                        rs.getInt("stok")
                );
                list.add(buku);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ===== UPDATE =====
    public void update(Buku buku) {
        String sql = "UPDATE buku SET judul=?, penulis=?, penerbit=?, tahun_terbit=?, stok=? WHERE id_buku=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, buku.getJudul());
            ps.setString(2, buku.getPenulis());
            ps.setString(3, buku.getPenerbit());
            ps.setInt(4, buku.getTahunTerbit());
            ps.setInt(5, buku.getStok());
            ps.setInt(6, buku.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ===== DELETE =====
    public void delete(int id_buku) {
        String sql = "DELETE FROM buku WHERE id_buku=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id_buku);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}