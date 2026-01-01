package dao;

import config.DatabaseConnection;
import model.Buku;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BukuDAO {

    // ================= CREATE =================
    public void insert(Buku buku) {
        String sql = "INSERT INTO buku (judul, penulis, penerbit, tahun_terbit, stok) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, buku.getJudul());
            ps.setString(2, buku.getPenulis());
            ps.setString(3, buku.getPenerbit());
            ps.setInt(4, buku.getTahunTerbit());
            ps.setInt(5, buku.getStok());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= READ =================
    public List<Buku> getAll() {
        List<Buku> list = new ArrayList<>();
        String sql = "SELECT * FROM buku";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Buku buku = new Buku();
                buku.setIdBuku(rs.getInt("id_buku"));
                buku.setJudul(rs.getString("judul"));
                buku.setPenulis(rs.getString("penulis"));
                buku.setPenerbit(rs.getString("penerbit"));
                buku.setTahunTerbit(rs.getInt("tahun_terbit"));
                buku.setStok(rs.getInt("stok"));

                list.add(buku);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ================= UPDATE =================
    public void update(Buku buku) {
        String sql = "UPDATE buku SET judul=?, penulis=?, penerbit=?, "
                   + "tahun_terbit=?, stok=? WHERE id_buku=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, buku.getJudul());
            ps.setString(2, buku.getPenulis());
            ps.setString(3, buku.getPenerbit());
            ps.setInt(4, buku.getTahunTerbit());
            ps.setInt(5, buku.getStok());
            ps.setInt(6, buku.getIdBuku());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= DELETE =================
    public void delete(int idBuku) {
        String sql = "DELETE FROM buku WHERE id_buku=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idBuku);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}