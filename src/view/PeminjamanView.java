package view;

import controller.PeminjamanController;
import model.Peminjaman;
import model.ComboItem;
import dao.AnggotaDAO;
import dao.BukuDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

public class PeminjamanView extends JPanel {

    private PeminjamanController controller;

    private JTable table;
    private DefaultTableModel tableModel;

    private JComboBox<ComboItem> cbAnggota;
    private JComboBox<ComboItem> cbBuku;
    private JTextField txtIdPeminjaman;
    private JSpinner spTanggalPinjam;
    private JSpinner spTanggalKembali;
    
    private JButton btnSimpan;
    private JButton btnKembalikan;
    private JButton btnHapus;
    private JButton btnReset;


    // ===== CONSTRUCTOR =====
    public PeminjamanView(Connection conn) {
        controller = new PeminjamanController(conn);
        initComponents();
        loadComboBox();
        loadTable();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // ===== FORM =====
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 5, 5));

        txtIdPeminjaman = new JTextField();
        txtIdPeminjaman.setEnabled(false);

        cbAnggota = new JComboBox<>();
        cbBuku = new JComboBox<>();

        spTanggalPinjam = new JSpinner(new SpinnerDateModel());
        spTanggalPinjam.setEditor(new JSpinner.DateEditor(spTanggalPinjam, "dd/MM/yyyy"));

        spTanggalKembali = new JSpinner(new SpinnerDateModel());
        spTanggalKembali.setEditor(new JSpinner.DateEditor(spTanggalKembali, "dd/MM/yyyy"));


        panelForm.add(new JLabel("ID Peminjaman"));
        panelForm.add(txtIdPeminjaman);
        panelForm.add(new JLabel("Anggota"));
        panelForm.add(cbAnggota);
        panelForm.add(new JLabel("Buku"));
        panelForm.add(cbBuku);
        panelForm.add(new JLabel("Tanggal Pinjam"));
        panelForm.add(spTanggalPinjam);
        panelForm.add(new JLabel("Tanggal Kembali"));
        panelForm.add(spTanggalKembali);

        // ===== BUTTON =====
        JPanel panelButton = new JPanel();

        btnSimpan = new JButton("Simpan");
        btnKembalikan = new JButton("Kembalikan");
        btnHapus = new JButton("Hapus");
        btnReset = new JButton("Reset");

        panelButton.add(btnSimpan);
        panelButton.add(btnKembalikan);
        panelButton.add(btnHapus);
        panelButton.add(btnReset);

        // ===== TABLE =====
        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Nama Anggota", "Judul Buku", "Tgl Pinjam", "Tgl Kembali", "Status"}, 0
        );
        table = new JTable(tableModel);

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(panelForm, BorderLayout.CENTER);
        topPanel.add(panelButton, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);


        // ===== ACTION =====
        btnSimpan.addActionListener(e -> simpan());
        btnKembalikan.addActionListener(e -> kembalikan());
        btnHapus.addActionListener(e -> hapus());
        btnReset.addActionListener(e -> resetForm());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                pilihTable();
            }
        });
    }

    // ===== ACTION METHOD =====

    private void simpan() {
        ComboItem anggota = (ComboItem) cbAnggota.getSelectedItem();
        ComboItem buku = (ComboItem) cbBuku.getSelectedItem();
        Date tanggalPinjam = clearTime((Date) spTanggalPinjam.getValue());

        if (anggota == null || buku == null) {
            JOptionPane.showMessageDialog(this, "Pilih anggota dan buku!");
            return;
        }

        // validasi tanggal pinjam (opsional tapi bagus)
        Date today = new Date();
        if (tanggalPinjam.after(today)) {
            JOptionPane.showMessageDialog(
                this,
                "Tanggal pinjam tidak boleh melebihi hari ini!",
                "Validasi Tanggal",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        controller.tambahPeminjaman(
            anggota.getId(),
            buku.getId(),
            tanggalPinjam
        );

        JOptionPane.showMessageDialog(this, "Peminjaman berhasil disimpan");

        loadTable();
        resetForm();
    }

    private void kembalikan() {
        if (txtIdPeminjaman.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data peminjaman terlebih dahulu!");
            return;
        }

        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data peminjaman di tabel!");
            return;
        }

        try {
            int id = Integer.parseInt(txtIdPeminjaman.getText());

            // tanggal dari spinner
            Date tanggalKembali = clearTime((Date) spTanggalKembali.getValue());

            // tanggal pinjam dari tabel (String)
            String tglPinjamStr = tableModel.getValueAt(row, 3).toString();

            // samakan format dengan tabel
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            Date tanggalPinjam = sdf.parse(tglPinjamStr);

            // VALIDASI LOGIS
            if (tanggalKembali.before(tanggalPinjam)) {
                JOptionPane.showMessageDialog(
                    this,
                    "Tanggal kembali tidak boleh lebih awal dari tanggal pinjam!",
                    "Validasi Tanggal",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            controller.kembalikanBuku(id, tanggalKembali);
            JOptionPane.showMessageDialog(this, "Buku berhasil dikembalikan");

            loadTable();
            resetForm();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi kesalahan tanggal!");
            e.printStackTrace();
        }
    }

    private void hapus() {
        if (txtIdPeminjaman.getText().isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Pilih data peminjaman terlebih dahulu!",
                "Peringatan",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Yakin ingin menghapus data peminjaman ini?",
            "Konfirmasi",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(txtIdPeminjaman.getText());
            controller.hapusPeminjaman(id);

            JOptionPane.showMessageDialog(this, "Data peminjaman berhasil dihapus");
            loadTable();
            resetForm();
        }
    }


    private void pilihTable() {
        int row = table.getSelectedRow();
        txtIdPeminjaman.setText(tableModel.getValueAt(row, 0).toString());
        btnKembalikan.setEnabled(true);
        btnHapus.setEnabled(true);
    }

    private void resetForm() {
        txtIdPeminjaman.setText("");
        cbAnggota.setSelectedIndex(0);
        cbBuku.setSelectedIndex(0);
    }

    private void loadTable() {
        tableModel.setRowCount(0);
        List<Peminjaman> list = controller.getAllPeminjaman();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        for (Peminjaman p : list) {
            String tglPinjam = p.getTanggalPinjam() != null
                    ? sdf.format(p.getTanggalPinjam())
                    : "-";

            String tglKembali = p.getTanggalKembali() != null
                    ? sdf.format(p.getTanggalKembali())
                    : "-";

            tableModel.addRow(new Object[]{
                    p.getIdPeminjaman(),
                    p.getNamaAnggota(),   // kalau sudah pakai JOIN
                    p.getJudulBuku(),     // kalau sudah pakai JOIN
                    tglPinjam,
                    tglKembali,
                    p.getStatus()
            });
        }
    }

    private void loadComboBox() {
        cbAnggota.removeAllItems();
        cbBuku.removeAllItems();

        AnggotaDAO anggotaDAO = new AnggotaDAO();
        BukuDAO bukuDAO = new BukuDAO();

        for (ComboItem a : anggotaDAO.getComboAnggota()) {
            cbAnggota.addItem(a);
        }

        for (ComboItem b : bukuDAO.getComboBuku()) {
            cbBuku.addItem(b);
        }
    }
    
    private Date clearTime(Date date) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);
        cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cal.set(java.util.Calendar.MINUTE, 0);
        cal.set(java.util.Calendar.SECOND, 0);
        cal.set(java.util.Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

}