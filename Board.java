package Raymond;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Board extends JPanel implements Runnable {

    private final int INITIAL_X = 0;
    private final int INITIAL_Y = 0;
    private final int DELAY = 5;
    private int PlaneID;

    private Image plane;
    private Image background;
    private int x, y;
    private Thread animator;
    
    private JScrollPane jScrollPanel;
	private JTextArea textArea;

    public Board(int ID) {
    	
    	setDoubleBuffered(true);
    	loadImage();
    	
    	PlaneID = ID;
    	
    	textArea = new JTextArea(5,10);
		
		jScrollPanel = new JScrollPane(textArea);
		
		this.add(jScrollPanel);
    }


    private void loadImage() {

        ImageIcon ii = new ImageIcon("src/resources/plane.jpg");
        plane = ii.getImage();
        
        ImageIcon bg = new ImageIcon("src/resources/runway.jpg");
        background = bg.getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(background, 0, 0, null);
        drawStar(g);
    }
    
    public void start() {

        animator = new Thread(this);
        animator.start();
    }


    private void drawStar(Graphics g) {

        g.drawImage(plane, x, y, this);
        Toolkit.getDefaultToolkit().sync();
    }

    private int cycle() {

    	x += 1;
        y += 1;

        if (y > 1280) {
            return 1;
        }
        else {
        	return 0;
        }
    }

    public void run() {
    	
        x = INITIAL_X;
        y = INITIAL_Y;

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();
        
        int finished = 0;
        
        textArea.append("Plane " + PlaneID + " is landing!");

        while (finished == 0) {

            finished = cycle();
            repaint();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0) {
                sleep = 2;
            }

            try {
            	
                Thread.sleep(sleep);
                
            } catch (InterruptedException e) {
                
                String msg = String.format("Thread interrupted: %s", e.getMessage());
                
                JOptionPane.showMessageDialog(this, msg, "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }

            beforeTime = System.currentTimeMillis();
        }
    }
}