package monte_carlo;

import data.Data;
import gui.Datos;
import gui.Resultados;
import gui.MainFrame;
import java.util.ArrayList;

public class Simulacion {
    static MainFrame window = new MainFrame();

    public static void run() {
        window.setFrame(new Datos().getFrame());
    }

    public static void calculos() {
        Data info = Data.getInstance();
        Operacion.generarTabla(info.operaciones, info.muestras);
        ArrayList<Persona> tabla = Persona.generarMuestras(Data.getInstance().muestras);
        showResults(tabla.toArray(new Persona[] {}));
    }

    public static void showResults(Persona[] res) {
        window.setFrame(new Resultados(res).getFrame());
    }
}
