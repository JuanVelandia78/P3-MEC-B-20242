package eps;

import java.util.LinkedList;
import java.util.Queue;

public class ColaPacientes {
    private Queue<Paciente> pacientes;

    public ColaPacientes() {
        pacientes = new LinkedList<>();
    }

    public void agregarPaciente(Paciente paciente) {
        pacientes.add(paciente);
    }

    public Paciente siguientePaciente() {
        return pacientes.poll();
    }

    public int tamaño() {
        return pacientes.size();
    }

    public Queue<Paciente> getPacientes() {
        return pacientes;
    }

    public Paciente verProximoPaciente() {
        return pacientes.peek();
    }
}




