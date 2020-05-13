package gui;

import data.Data;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import java.time.LocalTime;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import monte_carlo.Operacion;
import monte_carlo.Simulacion;

public class Datos {
    JFrame window;
    JPanel panel;
    JPanel labelPanel [];
    JLabel dsLabel, mediaLabel, muestrasLabel, transitoLabel;
    JTextField dsField, mediaField, muestrasField;
    DefaultTableModel operacionesModel;
    JTable operacionesTabla;
    JScrollPane scroll;
    JButton confirm;
    ImageIcon transito;


    public Datos() {
        window           = new JFrame("Datos");
        panel            = new JPanel();
        transito         = new ImageIcon ("images/transito.png");
        dsLabel          = new JLabel("Desviación estándar");
        mediaLabel       = new JLabel("Media");
        muestrasLabel    = new JLabel("Muestras");
        transitoLabel    = new JLabel(transito);
        dsField          = new JTextField("00:00:30");
        mediaField       = new JTextField("00:12:00");
        muestrasField    = new JTextField("800");
        operacionesModel = new DefaultTableModel(new String[]{"Nombre", "Duración"}, 100);
        loadData();
        operacionesTabla = new JTable(operacionesModel);
        scroll           = new JScrollPane(operacionesTabla);
        confirm          = new JButton("Aceptar");
        labelPanel       = new JPanel [3];
        for (int i = 0; i < labelPanel.length; i++) {
            labelPanel [i] = new JPanel ();
        }
        configurar();
        personalizar();
        escuchas();
        armar();
    }

    public JFrame getFrame() {
        return window;
    }

    private void configurar() {
        window.setLayout(new BorderLayout());
        window.setResizable(false);
        panel.setLayout(null);
        window.setSize(600, 600);
        dsLabel.setBounds(50, 50, 200,20 );
        dsField.setBounds(200, 50, 200,20 );
        mediaLabel.setBounds(50, 100, 200,20 );
        mediaField.setBounds(200, 100, 200,20 );
        muestrasLabel.setBounds(50, 150, 200,20 );
        muestrasField.setBounds(200, 150, 200,20 );
        confirm.setBounds(200, 200, 200, 30);
        scroll.setBounds(0,250 ,600 , 350);
        transitoLabel.setBounds(400, 50, 200,200 );
        for (int i = 0, y = 50; i < labelPanel.length; i++, y +=50) {
            labelPanel [i].setBounds (50, y , 200,20);
        }
    }

    private void escuchas() {
        confirm.addMouseListener(new ButtonListener());
    }

    private void personalizar (){
        Color colorFuente = new Color (18, 50, 171);
        Color colorBackground = new Color (161, 171, 18);
        Color colorPanel = new Color (246, 255, 105);
        Font fuente = new Font ("Arial", Font.BOLD,14);
        dsLabel.setFont(fuente);
        mediaLabel.setFont(fuente);
        muestrasLabel.setFont(fuente);
        dsLabel.setForeground(colorFuente);
        mediaLabel.setForeground(colorFuente);
        muestrasLabel.setForeground(colorFuente);
        panel.setBackground(colorPanel);
        for (int i = 0; i < labelPanel.length; i++) {
            labelPanel [i].setBackground(colorBackground);
        }
    }


    private void armar() {
        window.add(panel);
        panel.add(dsLabel);
        panel.add(dsField);
        panel.add(mediaLabel);
        panel.add(mediaField);
        panel.add(muestrasLabel);
        panel.add(muestrasField);
        panel.add(scroll);
        panel.add(confirm);
        for (int i = 0; i < labelPanel.length; i++) {
            panel.add(labelPanel[i]);
        }
        panel.add(transitoLabel);
    }

    private boolean validarDatos() {
        String fechaRegex = "[0-2][0-3]:[0-5]\\d(?::[0-5]\\d)?";

        if (!dsField.getText().matches(fechaRegex))
            return false;
        if (!mediaField.getText().matches(fechaRegex))
            return false;
        if (!muestrasField.getText().matches("[1-9][0-9]+"))
            return false;
        if (LocalTime.parse(dsField.getText()).isAfter(LocalTime.parse(mediaField.getText())))
            return false;
        if (operacionesModel.getRowCount() < 1)
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
