package dao;

import config.DatabaseConnection;

import model.Peminjaman;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PeminjamanDAO {

    private Connection conn;

    // constructor (dependency injection)
    public PeminjamanDAO(Connection conn) {
        this.conn = conn;
    }

    // CREATE
    public void insert(Peminjaman p) throws SQLException {
        String sql = "INSERT INTO peminjaman "
                   + "(id_anggota, id_buku, tanggal_pinjam, status) "
                   + "VALUES (?, ?, ?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, p.getIdAnggota());
        ps.setInt(2, p.getIdBuku());
        ps.setDate(3, new java.sql.Date(p.getTanggalPinjam().getTime()));
        ps.setString(4, p.getStatus());
        ps.executeUpdate();
        ps.close();
    }

    // UPDATE (pengembalian buku)
    public void update(Peminjaman p) throws SQLException {
        String sql = "UPDATE peminjaman "
                   + "SET tanggal_kembali=?, status=? "
                   + "WHERE id_peminjaman=?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setDate(1, new java.sql.Date(p.getTanggalKembali().getTime()));
        ps.setString(2, p.getStatus());
        ps.setInt(3, p.getIdPeminjaman());
        ps.executeUpdate();
        ps.close();
    }

    // DELETE
    public void delete(int idPeminjaman) throws SQLException {
        String sql = "DELETE FROM peminjaman WHERE id_peminjaman=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idPeminjaman);
        ps.executeUpdate();
        ps.close();
    }

    // READ BY ID
    public Peminjaman getById(int idPeminjaman) throws SQLException {
        String sql = "SELECT * FROM peminjaman WHERE id_peminjaman=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idPeminjaman);

        ResultSet rs = ps.executeQuery();
        Peminjaman p = null;

        if (rs.next()) {
            p = new Peminjaman();
            p.setIdPeminjaman(rs.getInt("id_peminjaman"));
            p.setIdAnggota(rs.getInt("id_anggota"));
            p.setIdBuku(rs.getInt("id_buku"));
            p.setTanggalPinjam(rs.getDate("tanggal_pinjam"));
            p.setTanggalKembali(rs.getDate("tanggal_kembali"));
            p.setStatus(rs.getString("status"));
        }

        rs.close();
        ps.close();
        return p;
    }

    // READ ALL
    public List<Peminjaman> getAll() throws SQLException {
        List<Peminjaman> list = new ArrayList<>();
        String sql = "SELECT \n" +
"            p.id_peminjaman,\n" +
"            a.nama AS nama_anggota,\n" +
"            b.judul AS judul_buku,\n" +
"            p.tanggal_pinjam,\n" +
"            p.tanggal_kembali,\n" +
"            p.status\n" +
"        FROM peminjaman p\n" +
"        JOIN anggota a ON p.id_anggota = a.id_anggota\n" +
"        JOIN buku b ON p.id_buku = b.id_buku\n" +
"        ORDER BY p.id_peminjaman DESC";

        try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Peminjaman p = new Peminjaman();
            p.setIdPeminjaman(rs.getInt("id_peminjaman"));
            p.setNamaAnggota(rs.getString("nama_anggota"));
            p.setJudulBuku(rs.getString("judul_buku"));
            p.setTanggalPinjam(rs.getDate("tanggal_pinjam"));
            p.setTanggalKembali(rs.getDate("tanggal_kembali"));
            p.setStatus(rs.getString("status"));

            list.add(p);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return list;
    }   
}
