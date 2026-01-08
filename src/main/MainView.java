/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import javax.swing.*;
import java.sql.Connection;

import config.DatabaseConnection;
import view.BukuView;
import controller.BukuController;
import view.AnggotaView;
import view.BukuView;
import view.PeminjamanView;
import view.PeminjamanView;


public class MainView extends JFrame {

    public MainView() {

        setTitle("Sistem Perpustakaan");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Connection conn = DatabaseConnection.getConnection();

        JTabbedPane tab = new JTabbedPane();

        BukuView bukuView = new BukuView();
        new BukuController(bukuView);
        tab.addTab("Buku", bukuView);

        tab.addTab("Anggota", new AnggotaView());
        tab.addTab("Peminjaman", new PeminjamanView(conn));
        add(tab);
        setVisible(true);
    }
}


