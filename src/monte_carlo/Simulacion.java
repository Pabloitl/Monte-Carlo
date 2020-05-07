package monte_carlo;

import data.Data;
import gui.Datos;
import gui.Resultados;
import gui.MainFrame;
import java.util.ArrayList;
import java.util.HashMap;

public class Simulacion {
    static MainFrame window = new MainFrame();

    public static void run() {
        window.setFrame(new Datos().getFrame());
    }

    public static void calculos() {
        Data info = Data.getInstance();
        HashMap<Operacion, Integer> operaciones = Operacion.generarTabla(info.operaciones, info.muestras / 2);
        ArrayList<Persona> tabla = Persona.generarMuestras(Data.getInstance().muestras);
        showResults(tabla.toArray(new Persona[] {}), operaciones);
    }

    public static void showResults(Persona[] res, HashMap<Operacion, Integer> op) {
        window.setFrame(new Resultados(res, op).getFrame());
    }
}
