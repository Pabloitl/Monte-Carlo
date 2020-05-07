package gui;

import data.Data;
import java.awt.BorderLayout;
import java.util.HashMap;
import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import monte_carlo.Operacion;

import monte_carlo.Persona;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class Resultados {
    JFrame window;
    JTabbedPane tabs;
    DefaultTableModel resultadosModel;
    JTable resultadosTabla;
    JScrollPane scrollTable;

    static String[] columnas = {
        "Hora de Llegada",
        "Tiempo / llegada",
        "Hora de Atención",
        "Tiempo de espera",
        "Op escogida",
        "Tiempo de Op",
        "Hora de salida"
    };

    public Resultados(Persona[] res, HashMap<Operacion, Integer> op) {
        window          = new JFrame();
        tabs = new JTabbedPane();
        resultadosModel = new DefaultTableModel();
        llenarTabla(res);
        resultadosTabla = new JTable(resultadosModel);
        scrollTable = new JScrollPane(resultadosTabla);

        configurar();
        armar(res, op);
    }

    private void llenarTabla(Persona[] res) {
        resultadosModel.setColumnIdentifiers(columnas);
        for (Persona p : res) {
            resultadosModel.addRow(new String[] {
                    p.horaLlegada.toString(),
                    p.tiempoLlegadas.toString(),
                    p.horaAtencion.toString(),
                    p.tiempoEspera.toString(),
                    p.operacion.getNombre(),
                    p.operacion.getDuracion().toString(),
                    p.horaSalida.toString()
                });
        }
    }

    public JFrame getFrame() {
        return window;
    }

    private void configurar() {
        window.setLayout(new BorderLayout());
    }

    private void armar(Persona[] res, HashMap<Operacion, Integer> op) {
        tabs.add(scrollTable, 0);
        tabs.add(operaciones(op), 1);
        tabs.add(grafica(res), 2);
        window.add(tabs);
    }

    public JPanel grafica(Persona[] res) {
        Data info = Data.getInstance();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        float horaAnterior = 0;
        int adicional = 0;

        for (int i = 0; i < res.length; i++) {
            float horaActual = res[i].tiempoEspera.getHour() + res[i].tiempoEspera.getMinute() / 60f + res[i].tiempoEspera.getSecond() / 3600f;
            if (horaActual < horaAnterior)
                adicional++;
            horaAnterior = horaActual;
            dataset.addValue(horaAnterior + 24 * adicional,
                    "Tiempo de espera",
                    String.valueOf(i));
        }
        JFreeChart histogram = ChartFactory.createLineChart(
                "Tiempo de espera",
                "# Persona",
                "Horas",
                dataset);
        return new ChartPanel(histogram);
    }

    public JPanel operaciones(HashMap<Operacion, Integer> op) {
        JPanel panel = new JPanel();
        DefaultTableModel model = new DefaultTableModel();
        JTable tabla = new JTable(model);
        JScrollPane scroll = new JScrollPane(tabla);
        int sum = 0;
        
        panel.setLayout(new BorderLayout());
        model.setColumnIdentifiers(new String[] {"Operacion", "Cuenta", "F(x)"});
        for (Operacion o : op.keySet()) {
            model.addRow(new String[] {o.getNombre(),
                String.valueOf(op.get(o)),
                String.valueOf(o.getProbabilidad())});
            sum += op.get(o);
        }
        model.addRow(new String[] {"Total", String.valueOf(sum)});
        panel.add(scroll, BorderLayout.CENTER);
        
        return panel;
    }
}
