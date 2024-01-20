package View;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.List;
import java.util.ArrayList;
import Model.Ball;

public class VW extends Canvas implements Runnable {

    private List<Ball> balls;
    private Image offscreenImage;
    TGV tgv;
    private int fps;
    private long lastTime;

    public VW(List<Ball> balls, TGV tgv) {
        this.tgv = tgv;
        this.balls = balls;
        Dimension d = new Dimension(500, 500);
        this.setPreferredSize(d);
        lastTime = System.nanoTime();
        
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void paint(Graphics g) {
        if (offscreenImage == null) {
            offscreenImage = createImage(getWidth(), getHeight());
        }
        Graphics offscreenGraphics = offscreenImage.getGraphics();
        clear((Graphics2D) offscreenGraphics);
        Graphics2D g2d = (Graphics2D) offscreenGraphics;
        for (Ball bola : balls) {
            bola.paint(g2d);
        }
        g.drawImage(offscreenImage, 0, 0, this);

    }

    public void clear(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

 
    private void calculateFPS() {
        long currentTime = System.nanoTime();
        long nanoElapsedTime = currentTime - lastTime;
        long elapsedTime = nanoElapsedTime / 1000000;
        fps = (int) (1000 / elapsedTime);
        lastTime = currentTime;
        tgv.fps.setText("FPS: " + fps);
    }
    @Override
    public void run() {
        while (true) {
            repaint();
            calculateFPS();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
