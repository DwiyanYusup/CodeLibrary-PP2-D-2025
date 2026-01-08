/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.AnggotaDAO;
import model.Anggota;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class AnggotaController {

    private AnggotaDAO dao;

    public AnggotaController() {
        dao = new AnggotaDAO();
    }

    public void tambahAnggota(Anggota a) {
        try {
            if (a.getNama().isEmpty() || a.getAlamat().isEmpty() || a.getNoHp().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Semua data wajib diisi!");
                return;
            }

            if (!a.getNoHp().matches("\\d{11,13}")) {
                JOptionPane.showMessageDialog(
                    null,
                    "No HP harus terdiri dari 11 sampai 13 angka!"
                );
                return;
            }


            dao.insert(a);
            JOptionPane.showMessageDialog(null, "Data anggota berhasil ditambahkan");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public List<Anggota> getAllAnggota() {
        try {
            return dao.getAll();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return null;
        }
    }

    public void updateAnggota(Anggota a) {
        try {
            // Validasi ID
            if (a.getIdAnggota() <= 0) {
                JOptionPane.showMessageDialog(null, "Pilih data yang akan diupdate!");
                return;
            }

            // Validasi field wajib
            if (a.getNama().isEmpty() || a.getAlamat().isEmpty() || a.getNoHp().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Semua data wajib diisi!");
                return;
            }

            // Validasi No HP 11–13 digit
            if (!a.getNoHp().matches("\\d{11,13}")) {
                JOptionPane.showMessageDialog(
                    null,
                    "No HP harus terdiri dari 11 sampai 13 angka!"
                );
                return;
            }

            dao.update(a);
            JOptionPane.showMessageDialog(null, "Data anggota berhasil diupdate");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void hapusAnggota(int id) {
        try {
            dao.delete(id);
            JOptionPane.showMessageDialog(null, "Data anggota berhasil dihapus");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}