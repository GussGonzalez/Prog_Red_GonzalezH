package BatallaNaval;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.io.*;
import java.util.List;

public class Cliente {
	
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Tablero tableroPropio;
	private Tablero tableroOponente; 
	    
	private String serverIp = "127.0.0.1";
	private int port = 666;
	
	public void iniciar() {
        System.out.println("Cliente Batalla Naval");
        
        System.out.print("Ingrese IP del servidor (default: 127.0.0.1): ");
        String ip = mainCliente.leerConsola();
        if (!ip.isEmpty()) this.serverIp = ip;
        
        try {
            socket = new Socket(serverIp, port);
            System.out.println("Conectado al servidor. Esperando compañero...");

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            tableroPropio = new Tablero();
            tableroOponente = new Tablero(); 

            posicionarBarcos();

            out.writeObject(new Mensaje(Mensaje.Tipo.POSICIONAMIENTO_OK, "Tablero listo", tableroPropio));
            out.flush();
            System.out.println("Barcos posicionados. Esperando inicio de partida...");

            buclePrincipalJuego();

        } catch (IOException e) {
            System.err.println("❌ Error de conexión o I/O: " + e.getMessage());
        } finally {
            cerrarRecursos();
        }
    }

}//Cliente
