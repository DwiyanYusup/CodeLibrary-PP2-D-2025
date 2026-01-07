/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package latihan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Swingdasar {

    public static void main(String[] args) {

        JFrame frame = new JFrame("CRUD JTable");
        frame.setSize(650, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // ===== PANEL INPUT =====
        JPanel panelInput = new JPanel(new GridLayout(3, 2, 5, 5));

        JTextField txtJudul = new JTextField();
        JTextField txtPenulis = new JTextField();
        JTextField txtStok = new JTextField();

        panelInput.add(new JLabel("Judul Buku"));
        panelInput.add(txtJudul);
        panelInput.add(new JLabel("Penulis"));
        panelInput.add(txtPenulis);
        panelInput.add(new JLabel("Stok"));
        panelInput.add(txtStok);

        // ===== MODEL & TABEL =====
        String[] kolom = {"Judul", "Penulis", "Stok"};
        DefaultTableModel model = new DefaultTableModel(kolom, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // ===== PANEL TOMBOL =====
        JPanel panelButton = new JPanel();

        JButton btnTambah = new JButton("Tambah");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");

        panelButton.add(btnTambah);
        panelButton.add(btnUpdate);
        panelButton.add(btnDelete);

        // ===== EVENT TAMBAH =====
        btnTambah.addActionListener(e -> {

            String judul = txtJudul.getText().trim();
            String penulis = txtPenulis.getText().trim();
            String stokText = txtStok.getText().trim();

            if (judul.isEmpty() || penulis.isEmpty() || stokText.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Semua field wajib diisi!");
                return;
            }

            int stok;
            try {
                stok = Integer.parseInt(stokText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Stok harus angka!");
                return;
            }

            model.addRow(new Object[]{judul, penulis, stok});
            clearInput(txtJudul, txtPenulis, txtStok);
        });

        // ===== KLIK BARIS TABEL =====
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                txtJudul.setText(model.getValueAt(row, 0).toString());
                txtPenulis.setText(model.getValueAt(row, 1).toString());
                txtStok.setText(model.getValueAt(row, 2).toString());
            }
        });

        // ===== EVENT UPDATE =====
        btnUpdate.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(frame, "Pilih data yang akan diupdate!");
                return;
            }

            model.setValueAt(txtJudul.getText(), row, 0);
            model.setValueAt(txtPenulis.getText(), row, 1);
            model.setValueAt(txtStok.getText(), row, 2);

        });

        // ===== EVENT DELETE =====
        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(frame, "Pilih data yang akan dihapus!");
                return;
            }

            model.removeRow(row);
            clearInput(txtJudul, txtPenulis, txtStok);
        });

        // ===== TAMBAH KE FRAME =====
        frame.add(panelInput, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(panelButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    // ===== METHOD BANTU =====
    private static void clearInput(JTextField a, JTextField b, JTextField c) {
        a.setText("");
        b.setText("");
        c.setText("");
    }
}





