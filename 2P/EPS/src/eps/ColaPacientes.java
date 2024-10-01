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

    public Paciente siguientePaciente() {
        if (!pacientes.isEmpty()) {
            return pacientes.remove(0);
        }
        return null;
    }

    public Paciente verProximoPaciente() {
        if (!pacientes.isEmpty()) {
            return pacientes.get(0);
        }
        return null;
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }
}



