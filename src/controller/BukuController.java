/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.BukuDAO;
import model.Buku;
import view.BukuView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class BukuController {

    private BukuView view;
    private BukuDAO dao;

    public BukuController(BukuView view) {
        this.view = view;
        this.dao = new BukuDAO();

        initController();
        loadTable();
    }

    // ================= INISIALISASI EVENT =================
    private void initController() {

        view.getBtnTambah().addActionListener(e -> tambahBuku());
        view.getBtnUpdate().addActionListener(e -> updateBuku());
        view.getBtnDelete().addActionListener(e -> deleteBuku());
        view.getBtnReset().addActionListener(e -> resetForm());
        view.getTable().getSelectionModel().addListSelectionListener(e -> isiFormDariTable());
    }

    // ================= TAMBAH =================
    private void tambahBuku() {
        try {
            Buku buku = ambilDataForm();
            dao.insert(buku);
            loadTable();
            view.clearForm();
            JOptionPane.showMessageDialog(view, "Data berhasil ditambahkan");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, e.getMessage());
        }
    }

    // ================= UPDATE =================
    private void updateBuku() {
        int row = view.getTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Pilih data yang akan diupdate");
            return;
        }

        try {
            int id = Integer.parseInt(view.getTable().getValueAt(row, 0).toString());
            Buku buku = ambilDataForm();
            buku.setId(id);

            dao.update(buku);
            loadTable();
            view.clearForm();
            view.getTable().clearSelection();
            JOptionPane.showMessageDialog(view, "Data berhasil diupdate");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, e.getMessage());
        }
    }

    // ================= DELETE =================
    private void deleteBuku() {
        int row = view.getTable().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Pilih data yang akan dihapus");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view,
                "Yakin ingin menghapus?",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(view.getTable().getValueAt(row, 0).toString());
            dao.delete(id);
            loadTable();
            view.getTable().clearSelection(); // 🔥 PENTING
            view.clearForm();
        }
    }

    // ================= LOAD TABLE =================
    private void loadTable() {
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
        model.setRowCount(0);

        List<Buku> list = dao.getAll();
        for (Buku b : list) {
            model.addRow(new Object[]{
                    b.getId(),
                    b.getJudul(),
                    b.getPenulis(),
                    b.getPenerbit(),
                    b.getTahunTerbit(),
                    b.getStok()
            });
        }
    }

    // ================= ISI FORM DARI TABLE =================
    private void isiFormDariTable() {
        int row = view.getTable().getSelectedRow();
        if (row != -1) {
            view.setJudul(view.getTable().getValueAt(row, 1).toString());
            view.setPenulis(view.getTable().getValueAt(row, 2).toString());
            view.setPenerbit(view.getTable().getValueAt(row, 3).toString());
            view.setTahun(view.getTable().getValueAt(row, 4).toString());
            view.setStok(view.getTable().getValueAt(row, 5).toString());
        }
    }
    
    private void resetForm() {
    view.clearForm();
    view.getTable().clearSelection();
    }

    // ================= AMBIL DATA + VALIDASI =================
    private Buku ambilDataForm() throws Exception {
    String judul = view.getJudul().trim();
    String penulis = view.getPenulis().trim();
    String penerbit = view.getPenerbit().trim();

    // ================= VALIDASI STRING =================
    if (judul.isEmpty() || penulis.isEmpty() || penerbit.isEmpty()) {
        throw new Exception("Semua kolom harus di isi!");
    }

    // ================= VALIDASI TAHUN =================
    String tahunStr = view.getTahun().trim();
    if (tahunStr.isEmpty()) {
        throw new Exception("Tahun terbit wajib diisi");
    }

    int tahun;
    try {
        tahun = Integer.parseInt(tahunStr);
    } catch (NumberFormatException e) {
        throw new Exception("Tahun terbit harus berupa angka");
    }

    if (tahun < 1900 || tahun > 2026) {
        throw new Exception("Tahun terbit harus antara 1900 - 2026");
    }

    // ================= VALIDASI STOK =================
    String stokStr = view.getStok().trim();
    if (stokStr.isEmpty()) {
        throw new Exception("Stok wajib diisi");
    }

    int stok;
    try {
        stok = Integer.parseInt(stokStr);
    } catch (NumberFormatException e) {
        throw new Exception("Stok harus berupa angka");
    }

    if (stok < 0) {
        throw new Exception("Stok tidak boleh negatif");
    }

    // ================= BUAT OBJEK MODEL =================
    return new Buku(judul, penulis, penerbit, tahun, stok);
    }
}