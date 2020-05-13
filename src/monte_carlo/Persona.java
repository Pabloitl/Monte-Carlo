package monte_carlo;

import java.time.LocalTime;
import data.Data;
import java.util.ArrayList;
import java.util.Random;

public class Persona {
    public LocalTime horaLlegada, tiempoLlegadas, horaAtencion, tiempoEspera, tiempoOperacion, horaSalida;
    public Operacion operacion;

    public Persona(final Persona anteriorPersona) {
        if (anteriorPersona == null)
            setDefaults();
        else
            setValues(anteriorPersona);
        tiempoLlegadas = tiempoLlegadas();
        tiempoEspera = tiempoEspera();
        operacion = operacion();
        tiempoOperacion = tiempoOperacion();
        horaSalida = horaSalida();
    }

    private void setDefaults() {
        horaLlegada = horaLlegada(null, null);
        horaAtencion = horaAtencion(null, null, null, null);
    }

    private void setValues(final Persona anteriorPersona) {
        horaLlegada = horaLlegada(anteriorPersona.horaLlegada,
                                  anteriorPersona.tiempoLlegadas);
        horaAtencion = horaAtencion(anteriorPersona.horaSalida,
                                    anteriorPersona.horaLlegada,
                                    anteriorPersona.tiempoLlegadas,
                                    anteriorPersona.tiempoOperacion);
    }

    private LocalTime horaLlegada(final LocalTime horaAnterior, final LocalTime llegadasAnterior) {
        if (horaAnterior == null)
            return LocalTime.of(7, 0);
        else
            return horaAnterior.plusNanos(llegadasAnterior.toNanoOfDay());
    }

    private LocalTime tiempoLlegadas() {
        final Data info = Data.getInstance();
        return new Estadistica(info.media, info.desviacion).sample();
    }

    private LocalTime horaAtencion(final LocalTime salidaAnterior, final LocalTime llegadaAnterior, LocalTime tiempoLlegada, LocalTime operacionAnterior) {
        if (salidaAnterior == null) return horaLlegada;
        if (salidaAnterior.isBefore(llegadaAnterior) ) {
            if (horaLlegada.isBefore(llegadaAnterior) && (salidaAnterior.isBefore(horaLlegada) || salidaAnterior.plusSeconds(tiempoLlegada.toSecondOfDay()).isBefore(salidaAnterior)))
                return horaLlegada;
            return salidaAnterior;
        }
        if (salidaAnterior.isAfter(horaLlegada)) {
            if (horaLlegada.isBefore(llegadaAnterior) && (salidaAnterior.isBefore(horaLlegada) || salidaAnterior.plusSeconds(tiempoLlegada.toSecondOfDay()).isBefore(salidaAnterior))) return horaLlegada;
            return salidaAnterior;
        }
        return horaLlegada;
    }

    private LocalTime tiempoEspera() {
        if (horaAtencion.isBefore(horaLlegada)) {
            final LocalTime mediaNoche = LocalTime.MIDNIGHT;
            return mediaNoche.minusSeconds(horaLlegada.toSecondOfDay()).plusSeconds(horaAtencion.toSecondOfDay());
        }
        return horaAtencion.minusNanos(horaLlegada.toNanoOfDay());
    }

    private Operacion operacion() {
        final Data info = Data.getInstance();
        final float rand = new Random().nextFloat();
        Operacion resultado = info.operaciones.get(0);
        float min = 1;

        for (final Operacion op : info.operaciones) {
            if (rand <= op.probabilidad && op.probabilidad <= min) {
                resultado = op;
                min = op.probabilidad;
            }
        }
        return resultado;
    }

    private LocalTime tiempoOperacion() {
        return operacion.duracion;
    }

    private LocalTime horaSalida() {
        return horaAtencion.plusNanos(tiempoOperacion.toNanoOfDay());
    }

    public static ArrayList<Persona> generarMuestras(final int muestras) {
        Persona anterior = null;
        final ArrayList<Persona> tabla = new ArrayList();

        for (int i = 0; i < muestras; i++) {
            tabla.add(anterior = new Persona(anterior));
        }
        return tabla;
    }
}
