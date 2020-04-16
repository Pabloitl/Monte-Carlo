package gui;

import data.Data;
import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import monte_carlo.Persona;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class Resultados {
    JFrame window;
    DefaultTableModel resultadosModel;
    JTable resultadosTabla;

    static String[] columnas = {
        "Hora de Llegada",
        "Tiempo / llegada",
        "Hora de Atención",
        "Tiempo de espera",
        "Op escogida",
        "Tiempo de Op",
        "Hora de salida"
    };

    public Resultados(Persona[] res) {
        window          = new JFrame();
        resultadosModel = new DefaultTableModel();
        llenarTabla(res);
        resultadosTabla = new JTable(resultadosModel);

        configurar();
        armar();
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

    }

    private void armar() {
        window.add(resultadosTabla);
    }

    public JPanel grafica() {
        Data info = Data.getInstance();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(1, "A", "b");
        dataset.addValue(1, "A", "B");
        dataset.addValue(1, "a", "b");
        JFreeChart histogram = ChartFactory.createBarChart(
                "Titulo",
                "X Label",
                "Y Label",
                dataset,
                PlotOrientation.HORIZONTAL,
                true,
                true,
                true);
        return new ChartPanel(histogram);
    }
}
