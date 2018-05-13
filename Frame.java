import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ThreadAnimation extends JFrame {

    public ThreadAnimation() {
    	
    }

    public static void main(String[] args) {
        JFrame frame = new ThreadAnimation();
        frame.setResizable(false);
        frame.pack();
        frame.setTitle("Demo");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.CYAN);
        frame.setSize(1280, 720);
        frame.add(new Board());
        
        frame.setVisible(true);
    }
}