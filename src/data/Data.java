package data;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import monte_carlo.Operacion;

public class Data implements Serializable {
    public static Data instance = new Data();

    public int muestras;
    public LocalTime media, desviacion;
//    public ArrayList<Persona> personas = new ArrayList();
    public ArrayList<Operacion> operaciones = new ArrayList();

    public static Data getInstance() {
        return instance;
    }
    
    public static void write() {
        try {
            File.write();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static boolean read() {
        try {
            instance = File.read();
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
}
