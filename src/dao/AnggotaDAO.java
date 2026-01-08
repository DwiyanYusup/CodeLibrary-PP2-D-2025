/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.DatabaseConnection;
import model.Anggota;
import model.ComboItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnggotaDAO {

    private Connection conn;

    public AnggotaDAO() {
        conn = DatabaseConnection.getConnection();
    }

    // CREATE
    public void insert(Anggota a) throws SQLException {
        String sql = "INSERT INTO anggota (nama, alamat, no_hp) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, a.getNama());
        ps.setString(2, a.getAlamat());
        ps.setString(3, a.getNoHp());
        ps.executeUpdate();
    }

    // READ
    public List<Anggota> getAll() throws SQLException {
        List<Anggota> list = new ArrayList<>();
        String sql = "SELECT * FROM anggota";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            Anggota a = new Anggota();
            a.setIdAnggota(rs.getInt("id_anggota"));
            a.setNama(rs.getString("nama"));
            a.setAlamat(rs.getString("alamat"));
            a.setNoHp(rs.getString("no_hp"));
            list.add(a);
        }
        return list;
    }

    // UPDATE
    public void update(Anggota a) throws SQLException {
        String sql = "UPDATE anggota SET nama=?, alamat=?, no_hp=? WHERE id_anggota=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, a.getNama());
        ps.setString(2, a.getAlamat());
        ps.setString(3, a.getNoHp());
        ps.setInt(4, a.getIdAnggota());
        ps.executeUpdate();
    }

    // DELETE
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM anggota WHERE id_anggota=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }
    
    public List<ComboItem> getComboAnggota() {
        List<ComboItem> list = new ArrayList<>();
        String sql = "SELECT id_anggota, nama FROM anggota";

        try (Connection c = DatabaseConnection.getConnection();
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery(sql)) {

            while (r.next()) {
                list.add(new ComboItem(
                    r.getInt("id_anggota"),
                    r.getString("nama")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
