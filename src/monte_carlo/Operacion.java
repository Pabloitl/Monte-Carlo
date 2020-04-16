package monte_carlo;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Operacion implements Serializable {
    float probabilidad;
    String nombre;
    LocalTime duracion;

    public Operacion(String nombre, LocalTime duracion) {
        this.nombre = nombre;
        this.duracion = duracion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalTime getDuracion() {
        return duracion;
    }

    public void setDuracion(LocalTime duracion) {
        this.duracion = duracion;
    }

    public float getProbabilidad() {
        return probabilidad;
    }

    public void setProbabilidad(float probabilidad) {
        this.probabilidad = probabilidad;
    }

    public static HashMap<Operacion, Integer> generarTabla(ArrayList<Operacion> op, int muestras) {
        Random rand = new Random();
        HashMap<Operacion, Integer> resultado = new HashMap();

        for (int i = 0; i < muestras; i++) {
            int j = rand.nextInt(op.size());

            if (resultado.containsKey(op.get(j)))
                resultado.put(op.get(j), resultado.get(op.get(j)) + 1);
            else
                resultado.put(op.get(j), 1);
        }
        calcularProbabilidades(op, resultado);

        return resultado;
    }

    public static void calcularProbabilidades(ArrayList<Operacion> op, HashMap<Operacion, Integer> frecuencias) {
        int sum = frecuencias.values().stream().mapToInt(i -> i).sum();
        int probabilidadAnterior = 0;

        for (Operacion operacion : frecuencias.keySet()) {
            probabilidadAnterior += frecuencias.get(operacion) / sum;
            operacion.setProbabilidad(probabilidadAnterior);
        }
    }
}
