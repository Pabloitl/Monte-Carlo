package gui;

import data.Data;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javax.swing.JPanel;
import monte_carlo.Persona;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.HistogramDataset;

public class Resultados {
    public Resultados() {
        // TODO: Gui que muestra los resultados
        JOptionPane.showMessageDialog(null, "Resultados");
    }
    
    public void frame() {
        JFrame f = new JFrame();
        f.add(grafica());
        f.setVisible(true);
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
    
    public static void main(String[] args) {
        new Resultados().frame();
    }
}
