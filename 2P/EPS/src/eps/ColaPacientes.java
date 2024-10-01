package eps;

import java.util.LinkedList;
import java.util.List;

public class ColaPacientes {
    private List<Paciente> pacientes;

    public ColaPacientes() {
        pacientes = new LinkedList<>();
    }

    public void agregarPaciente(Paciente paciente) {
        pacientes.add(paciente);
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }
}



