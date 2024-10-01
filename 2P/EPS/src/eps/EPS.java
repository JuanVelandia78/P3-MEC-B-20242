package eps;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EPS extends JFrame {
    private JTextField txtCedula;
    private JComboBox<String> cmbCategoria;
    private JComboBox<String> cmbServicio;
    private JTextArea txtAreaCola;
    private ColaPacientes colaPacientes;

    public EPS() {
        setTitle("Simulación EPS");
        setLayout(new BorderLayout());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        colaPacientes = new ColaPacientes();

        // Panel de registro de paciente
        JPanel panelRegistro = new JPanel(new GridLayout(4, 2, 5, 5));
        panelRegistro.add(new JLabel("Cédula:"));
        txtCedula = new JTextField();
        panelRegistro.add(txtCedula);

        panelRegistro.add(new JLabel("Categoría:"));
        cmbCategoria = new JComboBox<>(new String[]{"Menor de 60 años", "Adulto Mayor", "Persona con Discapacidad", "Otra"});
        panelRegistro.add(cmbCategoria);

        panelRegistro.add(new JLabel("Servicio:"));
        cmbServicio = new JComboBox<>(new String[]{"Consulta General", "Consulta Especializada", "Laboratorio", "Imágenes"});
        panelRegistro.add(cmbServicio);

        JButton btnRegistrar = new JButton("Registrar Paciente");
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarPaciente();
            }
        });
        panelRegistro.add(btnRegistrar);

        add(panelRegistro, BorderLayout.WEST);

        // Área de la cola de pacientes
        txtAreaCola = new JTextArea();
        txtAreaCola.setEditable(false);
        add(new JScrollPane(txtAreaCola), BorderLayout.CENTER);
    }

    private void registrarPaciente() {
        String cedula = txtCedula.getText();
        if (cedula.length() != 10) {
            JOptionPane.showMessageDialog(this, "Cédula inválida. Debe tener 10 dígitos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String categoria = (String) cmbCategoria.getSelectedItem();
        String servicio = (String) cmbServicio.getSelectedItem();
        String horaLlegada = new SimpleDateFormat("HH:mm:ss").format(new Date());

        Paciente nuevoPaciente = new Paciente(cedula, categoria, servicio, horaLlegada);
        colaPacientes.agregarPaciente(nuevoPaciente);
        actualizarCola();
    }

    private void actualizarCola() {
        StringBuilder sb = new StringBuilder();
        for (Paciente p : colaPacientes.getPacientes()) {
            sb.append(p.getCedula()).append(" - ").append(p.getCategoria()).append(" - ").append(p.getServicio()).append(" - ").append(p.getHoraLlegada()).append("\n");
        }
        txtAreaCola.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EPS frame = new EPS();
            frame.setVisible(true);
        });
    }
}
