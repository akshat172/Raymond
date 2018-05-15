package Raymond;

import java.awt.Color;

import javax.swing.JFrame;

public class ThreadAnimation extends JFrame {
    JFrame frame;
    Board b;
    public ThreadAnimation() {
        this.frame = new JFrame();
        this.frame.setResizable(false);
        this.frame.pack();
        this.frame.setTitle("Runway");
        this.frame.setLocationRelativeTo(null);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.getContentPane().setBackground(Color.CYAN);
        this.frame.setSize(1280, 720);
        b=new Board();
        this.frame.add(b);
        this.frame.setVisible(true);
    }


    public static void main(String[] args) {
        JFrame frame = new ThreadAnimation();
        frame.setResizable(false);
        frame.pack();
        frame.setTitle("Runway");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.CYAN);
        frame.setSize(1280, 720);
        frame.add(new Board());
        frame.setVisible(true);
    }
    public void close(){
        this.frame.setVisible(false);
        this.frame.dispose();
    }
    public void animate() {
        //this.frame.getContentPane();
        //for (Board b : this.getRootPane().getComponents()){
        b.start();

       // }

    }
}