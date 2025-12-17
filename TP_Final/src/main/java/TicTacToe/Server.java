package TicTacToe;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import Utils.colors;

public class Server {

	PrintStream ps = new PrintStream(System.out);
	public static ArrayList<cli> clientesConectados = new ArrayList<>();
	
	void iniciarServidor() {
		ps.println("Iniciando servidor TicTacToe");
		HiloServidor serv = new HiloServidor();
		serv.setName("TicTacToeServer");

		serv.start();
	}//end iniciarServidor
	
	public void realizarMovimiento(Tablero tablero, int fil, int col, String simbolo) {
		tablero.colocarSimbolo(fil, col, simbolo);;
	}
	
	public static void gestionarPartida(cli X, cli O){
		Tablero partida = new Tablero();
		try {
			X.dos.writeUTF("");
		} catch (IOException e) {
			e.printStackTrace();
		}//end try/catch
	}//end gestionarPartida

	public Server() {
		iniciarServidor();
	}//end Server
}//Server

class cli implements Runnable {

	PrintStream ps = new PrintStream(System.out);

	String nick = "";
	Socket sock;
	Thread hilo;

	DataOutputStream dos;
	DataInputStream dis;
	boolean isConected;

	public cli(Socket sock, String nick, DataInputStream in, DataOutputStream out) {
		this.nick = nick;
		this.sock = sock;
		this.dis = in;
		this.dos = out;

		this.isConected = true;
		this.hilo = new Thread(this, nick);
	}//end cli

	public void run() {
		String msgRecibido = "";

		while (sock.isConnected() && this.isConected) {
			try {
				msgRecibido = dis.readUTF();

				ps.println("\n" + colors.YELLOW + "El cliente " + this.nick + " envia:" + msgRecibido + colors.RESET);
				this.dos.writeUTF(msgRecibido);

				if (msgRecibido == "0") {
						this.dis.close();
						this.dos.close();
						this.isConected = false;
						this.sock.close();
						Server.clientesConectados.remove(this); 
						ps.println( colors.YELLOW + "\tCliente " + this.nick + " se ha desconectado.\n" + colors.RESET );
						this.notificarClientes(false);
						break;
				}//end if

			} catch (IOException e) {
				e.printStackTrace();
			}//end try/catch
		}//end while

	}//end run

	public void notificarClientes(boolean b) {
		for (cli c : Server.clientesConectados) {
			if (c.isConected && !c.nick.equals(this.nick)) {
				try {
					if (b) {
						c.dos.writeUTF(colors.YELLOW + "\t---" + this.nick + " se ha unido al chat---" + colors.RESET);
					} else {
						c.dos.writeUTF(colors.YELLOW + "\t---" + this.nick + " se ha desconectado---" + colors.RESET);
						//terminar partida, forzar detenimiento o designar ganador al que no se fue.
					}//end if/else
				} catch (IOException e) {
					e.printStackTrace();
				}//end try/catch
			}//end if
		}//end for
	}//end notificarClientes

}//cli

class HiloServidor extends Thread {

	ServerSocket server;
	int puerto = 7777;
	Socket sockAux;
	PrintStream ps = new PrintStream(System.out);

	DataInputStream disCliente;
	DataOutputStream dosCliente;

	public HiloServidor() { try { server = new ServerSocket(puerto); } catch (IOException e) { e.printStackTrace(); }//end try/catch 
	}//end HiloServidor

	@Override
	public void run() {

		while (!partidaFinalizada()) {
			try {
				ps.println("Esperando conexión con un cliente");
				sockAux = server.accept();

				ps.println(colors.PURPLE + "Cliente conectado: " + sockAux.getInetAddress().getHostAddress()
						+ colors.RESET);

				disCliente = new DataInputStream(sockAux.getInputStream());
				dosCliente = new DataOutputStream(sockAux.getOutputStream());

				ps.println(colors.YELLOW + "Creando un cliente... Esperando NickName: " + colors.RESET);
				String ID = disCliente.readUTF();
				cli newCliente = new cli(sockAux, ID, disCliente, dosCliente);

				ps.println(colors.GREEN + "El cliente " + newCliente.nick + " accedió al servidor.\n" + colors.RESET);

				Server.clientesConectados.add(newCliente);
				newCliente.hilo.start();

				if (Server.clientesConectados.size() >= 2){ 
					cli jugador1 = Server.clientesConectados.get(0); 
					cli jugador2 = Server.clientesConectados.get(1); 
					Server.gestionarPartida(jugador1, jugador2);
				}//end if
				
			} catch (IOException e) {
				e.printStackTrace();
			}//end try/catch

		}//end while

	}//end run
}//HiloServidor