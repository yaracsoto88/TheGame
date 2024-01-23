package Controller;

import Server.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import Data.PeerLocation;
import Interlocutor.Peer;
import Model.*;

public class TGPCT {

    private CCT conexiones;
    private TGCT game;
    private String filename = "my.ini";
    private ArrayList<Peer> peers = new ArrayList<>();
    TGR rules;

    public TGPCT() {
        rules = new TGR(this);
        game = new TGCT(this);
        loadConfiguration(filename);
        conexiones = new CCT("localhost", this, peers);

    }

    public static void main(String[] args) {
        TGPCT game = new TGPCT();
    }

    public void collide(Object o1, Object o2) {
        rules.collide(o1, o2);
    }

    public void enviarDerecha(Ball ball) {
        if (conexiones.canSend(ball, PeerLocation.EAST)) {
            ball.setPosx(1);
            ball.kill();
            conexiones.enviarBall(ball, PeerLocation.EAST);
        } else {
            System.err.println("tiene que rebotar");
            ball.setVx(-ball.getVx());           
        }
    }

    public void enviarIzquierda(Ball ball) {
        if (conexiones.canSend(ball, PeerLocation.WEST)) {
            ball.setPosx(499);
            ball.kill();
            conexiones.enviarBall(ball, PeerLocation.WEST);
        } else {
            ball.setVx(-ball.getVx()); 
        }
    }

    public void ballRecieved(Ball ball) {
        game.addBall(ball);
    }

    public void loadConfiguration(String fileName) {
        File archivo = new File(fileName);
        try {
            Scanner scanner = new Scanner(archivo);

            while (scanner.hasNextLine()) {

                String linea = scanner.nextLine();

                String[] partes = linea.split(",");

                String primerString = partes[0];
                int segundoInt = Integer.parseInt(partes[1]);
                String tercerString = partes[2];

                peers.add(new Peer(primerString, segundoInt, PeerLocation.valueOf(tercerString)));

            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    
}
