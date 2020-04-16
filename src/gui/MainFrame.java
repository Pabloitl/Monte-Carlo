package gui;

import javax.swing.JFrame;

public class MainFrame {
    private JFrame window;
    
    public MainFrame() {
        window = new JFrame();
        
    }
    
    public void setFrame(JFrame window) {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window = window;
        this.window.setVisible(true);
    }
}
