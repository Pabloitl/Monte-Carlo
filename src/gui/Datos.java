package gui;

import data.Data;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalTime;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import monte_carlo.Operacion;
import monte_carlo.Simulacion;

public class Datos {
    JFrame window;
    JLabel dsLabel, mediaLabel, muestrasLabel;
    JTextField dsField, mediaField, muestrasField;
    DefaultTableModel operacionesModel;
    JTable operacionesTabla;
    JScrollPane scroll;
    JButton confirm;

    public Datos() {
        window           = new JFrame("Datos");
        dsLabel          = new JLabel("Desviación estándar");
        mediaLabel       = new JLabel("Media");
        muestrasLabel    = new JLabel("Muestras");
        dsField          = new JTextField("00:00:30");
        mediaField       = new JTextField("00:12:00");
        muestrasField    = new JTextField("800");
        operacionesModel = new DefaultTableModel(new String[]{"Nombre", "Duración"}, 100);
        loadData();
        operacionesTabla = new JTable(operacionesModel);
        scroll           = new JScrollPane(operacionesTabla);
        confirm          = new JButton("Aceptar");

        configurar();
        escuchas();
        armar();
    }

    public JFrame getFrame() {
        return window;
    }

    private void configurar() {
        window.setLayout(new FlowLayout());
    }

    private void escuchas() {
        confirm.addMouseListener(new ButtonListener());
    }

    private void armar() {
        window.add(dsLabel);
        window.add(dsField);
        window.add(mediaLabel);
        window.add(mediaField);
        window.add(muestrasLabel);
        window.add(muestrasField);
        window.add(scroll);
        window.add(confirm);
    }
    
    private boolean validarDatos() {
        String fechaRegex = "\\d{2}:\\d{2}(?::\\d{2})?";
        
        if (!dsField.getText().matches(fechaRegex))
            return false;
        if (!mediaField.getText().matches(fechaRegex))
            return false;
        if (!muestrasField.getText().matches("[1-9][0-9]+"))
            return false;
        for (int i = 0; i < operacionesModel.getRowCount(); i++) {
            if (operacionesModel.getValueAt(i, 0) == null ||
                    operacionesModel.getValueAt(i, 1) == null)
                break;
            if (!operacionesModel.getValueAt(i, 0).toString().matches("[a-zA-Z ]+"))
                return false;
            if (!operacionesModel.getValueAt(i, 1).toString().matches(fechaRegex))
                return false;
            
        }
        return true;
    }

    private void loadData() {
        if (!Data.read())
            return;
        Data info = Data.getInstance();

        mediaField.setText(info.media.toString());
        dsField.setText(info.desviacion.toString());
        muestrasField.setText(String.valueOf(info.muestras));
        for (int i = 0; i < info.operaciones.size(); i++) {
            Operacion temp = info.operaciones.get(i);

            operacionesModel.setValueAt(
                    temp.getNombre(), i, 0);
            operacionesModel.setValueAt(
                    temp.getDuracion().toString(), i, 1);
        }
    }

    private void saveData() {
        Data info = Data.getInstance();

        info.media = LocalTime.parse(mediaField.getText());
        info.desviacion = LocalTime.parse(dsField.getText());
        info.muestras = Integer.parseInt(muestrasField.getText());
        info.operaciones.clear();
        for (int i = 0; i < operacionesModel.getRowCount(); i++) {
            if (operacionesModel.getValueAt(i, 0) == null ||
                    operacionesModel.getValueAt(i, 1) == null)
                break;
            info.operaciones.add(new Operacion(
                    operacionesModel.getValueAt(i, 0).toString(),
                    LocalTime.parse(operacionesModel.getValueAt(i, 1).toString())));
        }
        Data.write();
    }

    private class ButtonListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent me) {
            if (me.getSource() == confirm) {
                if (!validarDatos()) {
                    JOptionPane.showMessageDialog(null, "Datos invalidos");
                    return;
                }
                saveData();
                window.setVisible(false);
                Simulacion.calculos();
            }
        }

        @Override
        public void mousePressed(MouseEvent me) {
        }

        @Override
        public void mouseReleased(MouseEvent me) {
        }

        @Override
        public void mouseEntered(MouseEvent me) {
        }

        @Override
        public void mouseExited(MouseEvent me) {
        }
    }
}
