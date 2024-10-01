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
    private final JComboBox<String> cmbServicio;
    private final JLabel lblProximoPaciente;
    private JTextArea txtAreaCola;
    private ColaPacientes colaPacientes;
    private JSlider sliderVelocidad;
    private JLabel lblVelocidad;

    private int tiempoAtencion = 15;
    private boolean atendiendo = false;

    public EPS() {
        setTitle("Simulación EPS");
        setLayout(new BorderLayout());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        colaPacientes = new ColaPacientes();

        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new GridLayout(4, 2, 5, 5));

        panelIzquierdo.add(new JLabel("Cédula (10 dígitos):"));
        txtCedula = new JTextField();
        panelIzquierdo.add(txtCedula);

        panelIzquierdo.add(new JLabel("Categoría:"));
        cmbCategoria = new JComboBox<>(new String[]{"Menor de 60 años", "Adulto Mayor", "Persona con Discapacidad", "Otra"});
        panelIzquierdo.add(cmbCategoria);

        panelIzquierdo.add(new JLabel("Servicio:"));
        cmbServicio = new JComboBox<>(new String[]{"Consulta Médico General", "Consulta Médica Especializada", "Prueba de Laboratorio", "Imágenes Diagnósticas"});
        panelIzquierdo.add(cmbServicio);

        JButton btnRegistrar = new JButton("Registrar Paciente");
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarPaciente();
            }
        });
        panelIzquierdo.add(btnRegistrar);

        panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(panelIzquierdo, BorderLayout.WEST);

        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new BorderLayout());

        JPanel panelProximo = new JPanel();
        panelProximo.setLayout(new BorderLayout());
        lblProximoPaciente = new JLabel("Próximo Paciente: N/A");
        panelProximo.add(lblProximoPaciente, BorderLayout.CENTER);
        panelProximo.setBorder(BorderFactory.createTitledBorder("Información del Próximo Paciente"));
        panelDerecho.add(panelProximo, BorderLayout.NORTH);

        JPanel panelLista = new JPanel();
        panelLista.setLayout(new BorderLayout());
        txtAreaCola = new JTextArea();
        txtAreaCola.setEditable(false);
        panelLista.add(new JScrollPane(txtAreaCola), BorderLayout.CENTER);
        panelLista.setBorder(BorderFactory.createTitledBorder("Cola de Pacientes"));
        panelDerecho.add(panelLista, BorderLayout.CENTER);

        JPanel panelSlider = new JPanel();
        panelSlider.setLayout(new BorderLayout());

        lblVelocidad = new JLabel("Tiempo de atención: " + tiempoAtencion + " minutos");
        panelSlider.add(lblVelocidad, BorderLayout.NORTH);

        sliderVelocidad = new JSlider(1, 30, tiempoAtencion);
        sliderVelocidad.addChangeListener(e -> {
            tiempoAtencion = sliderVelocidad.getValue();
            lblVelocidad.setText("Tiempo de atención: " + tiempoAtencion + " minutos");
        });

        panelSlider.add(sliderVelocidad, BorderLayout.SOUTH);

        panelDerecho.add(panelSlider, BorderLayout.SOUTH);

        panelDerecho.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(panelDerecho, BorderLayout.EAST);
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
        
        if (colaPacientes.tamaño() >= 10 && !atendiendo) {
            mostrarProximoPaciente(colaPacientes.verProximoPaciente());
            iniciarAtencion();
        } else {
            mostrarProximoPaciente(colaPacientes.verProximoPaciente());
        }
    }

    private void actualizarCola() {
        StringBuilder sb = new StringBuilder();
        for (Paciente p : colaPacientes.getPacientes()) {
            sb.append(p.getCedula()).append(" - ").append(p.getCategoria()).append(" - ").append(p.getServicio()).append(" - ").append(p.getHoraLlegada()).append("\n");
        }
        txtAreaCola.setText(sb.toString());
    }

    private void mostrarProximoPaciente(Paciente paciente) {
        if (paciente != null) {
            lblProximoPaciente.setText("Próximo Paciente: " + paciente.getCedula() + " - " + paciente.getCategoria() + " - " + paciente.getServicio() + " - " + paciente.getHoraLlegada());
        } else {
            lblProximoPaciente.setText("Próximo Paciente: N/A");
        }
    }

    private void iniciarAtencion() {
        atendiendo = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (colaPacientes.tamaño() > 0) {
                    Paciente pacienteActual = colaPacientes.siguientePaciente();
                    mostrarProximoPaciente(pacienteActual);
                    try{
                        int tiempoAtencionMilisegundos = tiempoAtencion * 60 * 10;
                        Thread.sleep(tiempoAtencionMilisegundos);
                    } catch (InterruptedException e) {
                    }
                    
                    lblProximoPaciente.setText("Atendiendo: " + pacienteActual.getCedula());
                    
                    actualizarCola();
                    
                    if (colaPacientes.tamaño() > 0) {
                        mostrarProximoPaciente(colaPacientes.verProximoPaciente());
                    } else {
                        lblProximoPaciente.setText("Próximo Paciente: N/A");
                    }
                }
                atendiendo = false;
            }
        }).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EPS frame = new EPS();
            frame.setVisible(true);
        });
    }
}