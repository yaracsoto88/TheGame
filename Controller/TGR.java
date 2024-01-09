package Controller;

import Model.Ball;

public class TGR {

    public TGR() {
    }

    public void collide(Object o1, Object o2) {
        if (o1 instanceof Ball && o2 instanceof String) {
            wallCollide((Ball) o1, (String) o2);
        }
        if (o1 instanceof Ball && o2 instanceof Ball) {
            ballCollide((Ball) o1, (Ball) o2);
        }
    }

    private void wallCollide(Ball ball, String wall) {
        if (wall.equals("x")) {
            ball.setVx(-ball.getVx());
        } else {
            ball.setVy(-ball.getVy());
        }
    }

    private void ballCollide(Ball b1,Ball b2){
        int vx1 = b1.getVx();
        int vy1 = b1.getVy();
        int vx2 = b2.getVx();
        int vy2 = b2.getVy();
        b1.setVx(vx2);
        b1.setVy(vy2);
        b2.setVx(vx1);
        b2.setVy(vy1);
    }
}
