package controller;

import dao.PeminjamanDAO;
import dao.BukuDAO;
import model.Peminjaman;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

public class PeminjamanController {

    private PeminjamanDAO peminjamanDAO;
    private BukuDAO bukuDAO;

    public PeminjamanController(Connection conn) {
        this.peminjamanDAO = new PeminjamanDAO(conn);
        this.bukuDAO = new BukuDAO();
    }

    // ================== CREATE ==================
    public void tambahPeminjaman(
            int idAnggota,
            int idBuku,
            Date tanggalPinjam
    ) {
        try {
            // ===== VALIDASI =====
            if (idAnggota <= 0) {
                JOptionPane.showMessageDialog(null, "Anggota belum dipilih");
                return;
            }

            if (idBuku <= 0) {
                JOptionPane.showMessageDialog(null, "Buku belum dipilih");
                return;
            }

            if (tanggalPinjam == null) {
                JOptionPane.showMessageDialog(null, "Tanggal pinjam belum diisi");
                return;
            }

            // cek stok buku
            int stok = bukuDAO.getStokById(idBuku);
            if (stok <= 0) {
                JOptionPane.showMessageDialog(null, "Stok buku habis");
                return;
            }

            // ===== PROSES SIMPAN =====
            Peminjaman p = new Peminjaman();
            p.setIdAnggota(idAnggota);
            p.setIdBuku(idBuku);
            p.setTanggalPinjam(tanggalPinjam);
            p.setStatus("Dipinjam");

            peminjamanDAO.insert(p);
            bukuDAO.kurangiStok(idBuku);

            JOptionPane.showMessageDialog(null, "Peminjaman berhasil disimpan");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan peminjaman\n" + e.getMessage());
        }
    }

    // ================== UPDATE (PENGEMBALIAN) ==================
    public void kembalikanBuku(
            int idPeminjaman,
            Date tanggalKembali
    ) {
        try {
            if (idPeminjaman <= 0) {
                JOptionPane.showMessageDialog(null, "Data peminjaman belum dipilih");
                return;
            }

            if (tanggalKembali == null) {
                JOptionPane.showMessageDialog(null, "Tanggal kembali belum diisi");
                return;
            }

            Peminjaman p = peminjamanDAO.getById(idPeminjaman);
            if (p == null) {
                JOptionPane.showMessageDialog(null, "Data peminjaman tidak ditemukan");
                return;
            }

            // update data
            p.setTanggalKembali(tanggalKembali);
            p.setStatus("Dikembalikan");

            peminjamanDAO.update(p);
            bukuDAO.tambahStok(p.getIdBuku());

            JOptionPane.showMessageDialog(null, "Buku berhasil dikembalikan");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal mengembalikan buku\n" + e.getMessage());
        }
    }

    // ================== DELETE ==================
    public void hapusPeminjaman(int idPeminjaman) {
        try {
            if (idPeminjaman <= 0) {
                JOptionPane.showMessageDialog(null, "Data peminjaman belum dipilih");
                return;
            }

            Peminjaman p = peminjamanDAO.getById(idPeminjaman);
            if (p == null) {
                JOptionPane.showMessageDialog(null, "Data peminjaman tidak ditemukan");
                return;
            }

            // jika masih dipinjam, kembalikan stok
            if ("Dipinjam".equals(p.getStatus())) {
                bukuDAO.tambahStok(p.getIdBuku());
            }

            peminjamanDAO.delete(idPeminjaman);
            JOptionPane.showMessageDialog(null, "Data peminjaman berhasil dihapus");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal menghapus peminjaman\n" + e.getMessage());
        }
    }

    // ================== READ (UNTUK JTABLE) ==================
    public List<Peminjaman> getAllPeminjaman() {
        try {
            return peminjamanDAO.getAll();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal mengambil data peminjaman");
            return null;
        }
    }
}