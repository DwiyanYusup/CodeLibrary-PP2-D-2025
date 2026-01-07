/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BukuView extends JFrame {

    public JTextField txtJudul;
    public JTextField txtPenulis;
    public JTextField txtPenerbit;
    public JTextField txtTahunTerbit;
    public JTextField txtStok;

    public JButton btnTambah;
    public JButton btnUpdate;
    public JButton btnDelete;
    public JButton btnReset;

    public JTable table;
    public DefaultTableModel model;

    public BukuView() {

        setTitle("Manajemen Buku Perpustakaan");
        setSize(650, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== PANEL INPUT =====
        JPanel panelInput = new JPanel(new GridLayout(3, 2, 5, 5));

        txtJudul = new JTextField();
        txtPenulis = new JTextField();
        txtPenerbit = new JTextField();
        txtTahunTerbit = new JTextField();
        txtStok = new JTextField();

        panelInput.add(new JLabel("Judul Buku"));
        panelInput.add(txtJudul);
        panelInput.add(new JLabel("Penulis"));
        panelInput.add(txtPenulis);
        panelInput.add(new JLabel("Penerbit"));
        panelInput.add(txtPenerbit);
        panelInput.add(new JLabel("Tahun Terbit"));
        panelInput.add(txtTahunTerbit);
        panelInput.add(new JLabel("Stok"));
        panelInput.add(txtStok);

        // ===== TABEL =====
        String[] kolom = {"ID", "Judul", "Penulis", "Penerbit", "TahunTerbit", "Stok",};
        model = new DefaultTableModel(kolom, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // ===== PANEL BUTTON =====
        JPanel panelButton = new JPanel();

        btnTambah = new JButton("Tambah");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnReset = new JButton("Reset");

        panelButton.add(btnTambah);
        panelButton.add(btnUpdate);
        panelButton.add(btnDelete);
        panelButton.add(btnReset);

        // ===== TAMBAH KE FRAME =====
        add(panelInput, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelButton, BorderLayout.SOUTH);
    }
    // ================== GETTER COMPONENT ==================

    public JTable getTable() {
        return table;
    }

    public JButton getBtnTambah() {
        return btnTambah;
    }

    public JButton getBtnUpdate() {
        return btnUpdate;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }
    
    public JButton getBtnReset() {
    return btnReset;
    }

    public String getJudul() {
        return txtJudul.getText();
    }

    public String getPenulis() {
        return txtPenulis.getText();
    }

    public String getPenerbit() {
        return txtPenerbit.getText();
    }

    public String getTahun() {
        return txtTahunTerbit.getText();
    }

    public String getStok() {
        return txtStok.getText();
    }
    // ================== SETTER FORM ==================

    public void setJudul(String judul) {
        txtJudul.setText(judul);
    }

    public void setPenulis(String penulis) {
        txtPenulis.setText(penulis);
    }

    public void setPenerbit(String penerbit) {
        txtPenerbit.setText(penerbit);
    }

    public void setTahun(String tahun) {
        txtTahunTerbit.setText(tahun);
    }

    public void setStok(String stok) {
        txtStok.setText(stok);
    }
    
    public void clearForm() {
    txtJudul.setText("");
    txtPenulis.setText("");
    txtPenerbit.setText("");
    txtTahunTerbit.setText("");
    txtStok.setText("");
    }
}
