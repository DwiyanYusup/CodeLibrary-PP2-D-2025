package model;

import java.util.Date;

public class Peminjaman {

    private int idPeminjaman;
    private int idAnggota;
    private int idBuku;
    private String namaAnggota;
    private String judulBuku;
    private Date tanggalPinjam;
    private Date tanggalKembali;
    private String status;

    // ===== GETTER =====
    public int getIdPeminjaman() {
        return idPeminjaman;
    }

    public int getIdAnggota() {
        return idAnggota;
    }

    public int getIdBuku() {
        return idBuku;
    }

    public Date getTanggalPinjam() {
        return tanggalPinjam;
    }

    public Date getTanggalKembali() {
        return tanggalKembali;
    }

    public String getStatus() {
        return status;
    }

    // ===== SETTER =====
    public void setIdPeminjaman(int idPeminjaman) {
        this.idPeminjaman = idPeminjaman;
    }

    public void setIdAnggota(int idAnggota) {
        this.idAnggota = idAnggota;
    }

    public void setIdBuku(int idBuku) {
        this.idBuku = idBuku;
    }

    public void setTanggalPinjam(Date tanggalPinjam) {
        this.tanggalPinjam = tanggalPinjam;
    }

    public void setTanggalKembali(Date tanggalKembali) {
        this.tanggalKembali = tanggalKembali;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getNamaAnggota() {
    return namaAnggota;
    }

    public void setNamaAnggota(String namaAnggota) {
        this.namaAnggota = namaAnggota;
    }

    public String getJudulBuku() {
        return judulBuku;
    }

    public void setJudulBuku(String judulBuku) {
        this.judulBuku = judulBuku;
    }
}
