package view;

import controller.AnggotaController;
import model.Anggota;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class AnggotaView extends JPanel {

    private JTextField txtNama, txtAlamat, txtNoHp;
    private JButton btnSimpan, btnUpdate, btnHapus, btnReset;
    private JTable table;
    private DefaultTableModel tableModel;
    private AnggotaController controller;
    private int selectedId = -1;

    public AnggotaView() {
        controller = new AnggotaController();

        setLayout(null); // masih pakai absolute layout

        // ===== FORM INPUT =====
        JLabel lblNama = new JLabel("Nama");
        lblNama.setBounds(20, 20, 80, 25);
        add(lblNama);

        txtNama = new JTextField();
        txtNama.setBounds(100, 20, 150, 25);
        add(txtNama);

        JLabel lblAlamat = new JLabel("Alamat");
        lblAlamat.setBounds(20, 55, 80, 25);
        add(lblAlamat);

        txtAlamat = new JTextField();
        txtAlamat.setBounds(100, 55, 150, 25);
        add(txtAlamat);

        JLabel lblHp = new JLabel("No HP");
        lblHp.setBounds(20, 90, 80, 25);
        add(lblHp);

        txtNoHp = new JTextField();
        txtNoHp.setBounds(100, 90, 150, 25);
        add(txtNoHp);

        // VALIDASI ANGKA
        txtNoHp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) || txtNoHp.getText().length() >= 13) {
                    e.consume();
                }
            }
        });

        btnSimpan = new JButton("Simpan");
        btnSimpan.setBounds(100, 130, 80, 30);
        add(btnSimpan);

        btnReset = new JButton("Reset");
        btnReset.setBounds(190, 130, 80, 30);
        add(btnReset);

        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(100, 170, 80, 30);
        add(btnUpdate);

        btnHapus = new JButton("Hapus");
        btnHapus.setBounds(190, 170, 80, 30);
        add(btnHapus);

        // ===== TABLE =====
        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Nama", "Alamat", "No HP"}, 0
        );
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(280, 20, 350, 300);
        add(scrollPane);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                selectedId = Integer.parseInt(table.getValueAt(row, 0).toString());
                txtNama.setText(table.getValueAt(row, 1).toString());
                txtAlamat.setText(table.getValueAt(row, 2).toString());
                txtNoHp.setText(table.getValueAt(row, 3).toString());
            }
        });

        // ===== ACTION =====
        btnSimpan.addActionListener(e -> simpanData());
        btnReset.addActionListener(e -> resetForm());
        btnUpdate.addActionListener(e -> updateData());
        btnHapus.addActionListener(e -> hapusData());

        loadTable();
    }

    // ===== METHOD =====
    private void simpanData() {
        Anggota a = new Anggota();
        a.setNama(txtNama.getText());
        a.setAlamat(txtAlamat.getText());
        a.setNoHp(txtNoHp.getText());

        controller.tambahAnggota(a);
        loadTable();
        resetForm();
    }

    private void updateData() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Pilih data anggota yang ingin diupdate!",
                "Peringatan",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Anggota a = new Anggota();
        a.setIdAnggota(selectedId);
        a.setNama(txtNama.getText());
        a.setAlamat(txtAlamat.getText());
        a.setNoHp(txtNoHp.getText());

        controller.updateAnggota(a);
        JOptionPane.showMessageDialog(this, "Data berhasil diupdate");

        loadTable();
        resetForm();
    }

    private void hapusData() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(
                this,
                "Pilih data anggota yang ingin dihapus!",
                "Peringatan",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Yakin ingin menghapus data anggota ini?",
            "Konfirmasi",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            controller.hapusAnggota(selectedId);
            JOptionPane.showMessageDialog(this, "Data berhasil dihapus");
            loadTable();
            resetForm();
        }
    }


    private void resetForm() {
        txtNama.setText("");
        txtAlamat.setText("");
        txtNoHp.setText("");
        selectedId = -1;
        table.clearSelection();
    }

    private void loadTable() {
        tableModel.setRowCount(0);
        List<Anggota> list = controller.getAllAnggota();
        for (Anggota a : list) {
            tableModel.addRow(new Object[]{
                    a.getIdAnggota(),
                    a.getNama(),
                    a.getAlamat(),
                    a.getNoHp()
            });
        }
    }
}