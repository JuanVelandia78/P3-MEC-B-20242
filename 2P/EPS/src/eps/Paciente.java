package eps;

public class Paciente {
    private String cedula;
    private String categoria;
    private String servicio;
    private String horaLlegada;

    public Paciente(String cedula, String categoria, String servicio, String horaLlegada) {
        this.cedula = cedula;
        this.categoria = categoria;
        this.servicio = servicio;
        this.horaLlegada = horaLlegada;
    }

    public String getCedula() {
        return cedula;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getServicio() {
        return servicio;
    }

    public String getHoraLlegada() {
        return horaLlegada;
    }
}




