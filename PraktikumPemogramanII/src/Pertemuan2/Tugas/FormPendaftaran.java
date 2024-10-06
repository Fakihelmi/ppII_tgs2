package Pertemuan2.Tugas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;

public class FormPendaftaran extends JFrame implements ActionListener, ItemListener {

    private JTextField namaField, noHpField;
    private JTextArea biodataArea;
    private JCheckBox wnaCheckBox;
    private JRadioButton lakiRadioButton, perempuanRadioButton;
    private ButtonGroup genderGroup;
    private boolean checkBoxStatus;
    private JList<String> tabunganList;
    private JSlider transaksiSlider;
    private JPasswordField passwordField, confirmPasswordField;
    private JSpinner tanggalLahirSpinner;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem resetMenuItem, exitMenuItem;

    public FormPendaftaran() {
        super("Form Pendaftaran");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);  //
        setLocationRelativeTo(null);

        setupMenu();
        setupLayout();

        setVisible(true);
    }

    private void setupMenu() {
        menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        resetMenuItem = new JMenuItem("Reset");
        exitMenuItem = new JMenuItem("Exit");

        menu.add(resetMenuItem);
        menu.add(exitMenuItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        resetMenuItem.addActionListener(e -> resetForm());
        exitMenuItem.addActionListener(e -> System.exit(0));
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));


        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(5, 1, 10, 10));

        JLabel labelNama = new JLabel("Nama:");
        namaField = new JTextField(20);
        leftPanel.add(createFieldPanel(labelNama, namaField));

        JLabel labelNoHp = new JLabel("Nomor HP:");
        noHpField = new JTextField(20);
        leftPanel.add(createFieldPanel(labelNoHp, noHpField));

        JLabel labelGender = new JLabel("Jenis Kelamin:");
        lakiRadioButton = new JRadioButton("Laki-Laki");
        perempuanRadioButton = new JRadioButton("Perempuan");
        genderGroup = new ButtonGroup();
        genderGroup.add(lakiRadioButton);
        genderGroup.add(perempuanRadioButton);
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.add(lakiRadioButton);
        genderPanel.add(perempuanRadioButton);
        leftPanel.add(createFieldPanel(labelGender, genderPanel));

        JLabel labelTanggalLahir = new JLabel("Tanggal Lahir:");
        tanggalLahirSpinner = new JSpinner(new SpinnerDateModel());
        tanggalLahirSpinner.setEditor(new JSpinner.DateEditor(tanggalLahirSpinner, "dd-MM-yyyy"));
        leftPanel.add(createFieldPanel(labelTanggalLahir, tanggalLahirSpinner));

        JLabel labelStatusWNA = new JLabel("Status Warga Negara:");
        wnaCheckBox = new JCheckBox("Warga Negara Asing");
        wnaCheckBox.addItemListener(this);
        leftPanel.add(createFieldPanel(labelStatusWNA, wnaCheckBox));

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(5, 1, 10, 10));

        JLabel labelTabungan = new JLabel("Jenis Tabungan:");
        String[] optionsTabungan = {"Tabungan Pensiun", "Tabungan Pendidikan", "Tabungan Rumah", "Tabungan Anak"};
        tabunganList = new JList<>(optionsTabungan);
        tabunganList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPaneTabungan = new JScrollPane(tabunganList);
        scrollPaneTabungan.setPreferredSize(new Dimension(150, 60));
        rightPanel.add(createFieldPanel(labelTabungan, scrollPaneTabungan));

        JLabel labelFrekuensi = new JLabel("Frekuensi Transaksi:");
        transaksiSlider = new JSlider(0, 100, 0);
        transaksiSlider.setMajorTickSpacing(10);
        transaksiSlider.setPaintTicks(true);
        transaksiSlider.setPaintLabels(true);
        rightPanel.add(createFieldPanel(labelFrekuensi, transaksiSlider));

        JLabel labelPassword = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        rightPanel.add(createFieldPanel(labelPassword, passwordField));

        JLabel labelConfirmPassword = new JLabel("Konfirmasi Password:");
        confirmPasswordField = new JPasswordField(20);
        rightPanel.add(createFieldPanel(labelConfirmPassword, confirmPasswordField));

        JButton simpanButton = new JButton("Simpan");
        simpanButton.addActionListener(this);
        rightPanel.add(simpanButton);

        JPanel bottomPanel = new JPanel();
        biodataArea = new JTextArea(10, 50); 
        biodataArea.setEditable(false);
        JScrollPane scrollBiodata = new JScrollPane(biodataArea);
        bottomPanel.add(scrollBiodata);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        centerPanel.add(leftPanel);
        centerPanel.add(rightPanel);

        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createFieldPanel(JLabel label, Component field) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(label);
        panel.add(field);
        return panel;
    }

    private void resetForm() {
        namaField.setText("");
        noHpField.setText("");
        genderGroup.clearSelection();
        wnaCheckBox.setSelected(false);
        tabunganList.clearSelection();
        transaksiSlider.setValue(0);
        passwordField.setText("");
        confirmPasswordField.setText("");
        tanggalLahirSpinner.setValue(new java.util.Date());
        biodataArea.setText("");
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        checkBoxStatus = e.getStateChange() == ItemEvent.SELECTED;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String nama = namaField.getText();
        String noHp = noHpField.getText();
        String jenisKelamin = lakiRadioButton.isSelected() ? "Laki-Laki" : "Perempuan";
        String wnaStatus = checkBoxStatus ? "Warga Negara Asing" : "Warga Negara Indonesia";
        String tabunganTerpilih = tabunganList.getSelectedValue();
        String frekuensiTransaksi = String.valueOf(transaksiSlider.getValue());
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String passwordStatus;

        if (password.equals(confirmPassword)) {
            passwordStatus = "Pendaftaran Berhasil";
        } else {
            passwordStatus = "Maaf Pendaftaran Gagal, password tidak cocok";
            JOptionPane.showMessageDialog(this, passwordStatus, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String tanggalLahir = dateFormat.format(tanggalLahirSpinner.getValue());

        String biodata = "Nama : " + nama +
                "\nNomor HP : " + noHp +
                "\nJenis Kelamin : " + jenisKelamin +
                "\nTanggal Lahir : " + tanggalLahir +
                "\nStatus Warga Negara : " + wnaStatus +
                "\nJenis Tabungan : " + tabunganTerpilih +
                "\nFrekuensi Transaksi : " + frekuensiTransaksi +
                "\n" + passwordStatus;

        biodataArea.setText(biodata);
    }

    public static void main(String[] args) {
        new FormPendaftaran();
    }
}
