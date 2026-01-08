/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.DatabaseConnection;
import model.Buku;
import model.ComboItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BukuDAO {

    // ===== INSERT =====
    public void insert(Buku buku) {
        String sql = "INSERT INTO buku (id_buku, judul, penulis, penerbit, tahun_terbit, stok) VALUES (?, ?, ?, ?, ?,?)";

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
    
    // ===== GET STOK =====
    public int getStokById(int idBuku) throws SQLException {
        String sql = "SELECT stok FROM buku WHERE id_buku=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idBuku);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("stok");
            }
        }
        return 0;
    }

    // ===== KURANGI STOK =====
    public void kurangiStok(int idBuku) throws SQLException {
        String sql = "UPDATE buku SET stok = stok - 1 WHERE id_buku=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idBuku);
            ps.executeUpdate();
        }
    }

    // ===== TAMBAH STOK =====
    public void tambahStok(int idBuku) throws SQLException {
        String sql = "UPDATE buku SET stok = stok + 1 WHERE id_buku=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idBuku);
            ps.executeUpdate();
        }
    }
    
    public List<ComboItem> getComboBuku() {
        List<ComboItem> list = new ArrayList<>();
        String sql = "SELECT id_buku, judul FROM buku WHERE stok > 0";

        try (Connection c = DatabaseConnection.getConnection();
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery(sql)) {

            while (r.next()) {
                list.add(new ComboItem(
                    r.getInt("id_buku"),
                    r.getString("judul")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}