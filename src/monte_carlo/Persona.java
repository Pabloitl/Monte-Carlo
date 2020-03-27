package monte_carlo;

import java.time.LocalTime;

public class Persona {
    LocalTime horaLlegada, tiempoLlegadas, horaAtencion, tiempoEspera, tiempoOperacion, horaSalida;
    Operacion operacion;

    public Persona(Persona anteriorPersona) {
        horaLlegada();
        tiempoLlegadas();
        horaAtencion();
        tiempoEspera();
        operacion();
        tiempoOperacion();
        horaSalida();
    }

    public LocalTime horaLlegada() {
        return null;
    }

    public LocalTime tiempoLlegadas() {
        return null;
    }

    public LocalTime horaAtencion() {
        return null;
    }

    public LocalTime tiempoEspera() {
        return null;
    }

    public void operacion() {
    }

    public void tiempoOperacion() {
    }

    public void horaSalida() {
    }
}
