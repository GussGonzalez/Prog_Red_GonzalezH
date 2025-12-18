package TicTacToe;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import Utils.colors;

public class Server {

	PrintStream ps = new PrintStream(System.out);
	static PrintStream psG = new PrintStream(System.out);
	public static ArrayList<cli> clientesConectados = new ArrayList<>();
	
	void iniciarServidor() {
		ps.println("Iniciando servidor TicTacToe");
		HiloServidor serv = new HiloServidor();
		serv.setName("TicTacToeServer");

		serv.start();
	}//end iniciarServidor
	
	//el problema podría ser que es todo estático
	public static void gestionarPartida(cli X, cli O){
		Tablero partida = new Tablero();
		String respuesta = "";
		boolean valido = false;
		String estado = notificarEstadoJuego(partida, "X", "O");
		
		while(X.isConnected && O.isConnected) {
			try {
				partida.mostrarTablero(X.dos, X);
				partida.mostrarTablero(O.dos, O);
				
				O.dos.writeUTF("Es el turno del jugador X, por favor espere...");
				respuesta = mostrarOpciones(X.dos, X.dis, X);
				psG.println(respuesta);
				valido = validarMovimiento(partida, respuesta);
				while(!valido) {
					respuesta = mostrarOpciones(X.dos, X.dis, X);
					psG.println(respuesta);
					valido = validarMovimiento(partida, respuesta);
				}//end while
				partida.colocarSimbolo(Integer.valueOf(respuesta.charAt(0)), Integer.valueOf(respuesta.charAt(1)), "X");
				valido = false;
				
				estado = notificarEstadoJuego(partida, "X", "O");
				if (estado == "X" || estado == "O" || estado == "Empate") {
					if (estado == "X") {
						X.dos.writeUTF("¡Has ganado!");
						O.dos.writeUTF("Has perdido.");
					}else if(estado == "O") {
						O.dos.writeUTF("¡Has ganado!");
						X.dos.writeUTF("Has perdido.");
					}else {
						O.dos.writeUTF("Ha sido un empate");
					}//end if/else if/else
					
					X.dos.writeUTF("Partida Finalizada");
					O.dos.writeUTF("Partida Finalizada");
				}//end if
				
				X.dos.writeUTF("Es el turno del jugador O, por favor espere...");
				respuesta = mostrarOpciones(O.dos, O.dis, O);
				psG.println(respuesta);
				valido = validarMovimiento(partida, respuesta);
				while(!valido) {
					respuesta = mostrarOpciones(O.dos, O.dis, O);
					psG.println(respuesta);
					valido = validarMovimiento(partida, respuesta);
				}//end while
				partida.colocarSimbolo(Integer.valueOf(respuesta.charAt(0)), Integer.valueOf(respuesta.charAt(1)), "O");
				valido = false;
				
				if (estado == "X" || estado == "O" || estado == "Empate") {
					if (estado == "X") {
						X.dos.writeUTF("¡Has ganado!");
						O.dos.writeUTF("Has perdido.");
					}else if(estado == "O") {
						O.dos.writeUTF("¡Has ganado!");
						X.dos.writeUTF("Has perdido.");
					}else {
						O.dos.writeUTF("Ha sido un empate");
					}//end if/else if/else
					
					X.dos.writeUTF("Partida Finalizada");
					O.dos.writeUTF("Partida Finalizada");
				}//end if
			} catch (IOException e) {
				//ps.println("Ha ocurrido un error en gestionarPartida()");
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}//end try/catch
			
		}//end while

		//partidaFinalizada = true;
	}//end gestionarPartida
	
	private static String mostrarOpciones(DataOutputStream dos, DataInputStream dis, cli jugador) {
		String res = "";
		try {
			jugador.dos.writeUTF(colors.YELLOW + "¡Es tu turno! ");
			
			jugador.dos.writeUTF(colors.CYAN + "\n --- --- 「 ✦ Opciones ✦ 」 --- --- ");
			jugador.dos.writeUTF("   1.  	(Realizar movimiento)");
			jugador.dos.writeUTF("   0.  	(Desconectar)");
			jugador.dos.writeUTF("¡ Ingresa el número que se desea ejecutar ! : " + colors.RESET);
			
			switch ( Integer.valueOf(jugador.dis.readUTF()) ) {
            case 1:
                jugador.dos.writeUTF("Ingrese la fila en la que desee colocar: ");
                res = jugador.dis.readUTF();
                
                jugador.dos.writeUTF("Ingrese la columna en la que desee colocar: ");
                res += jugador.dis.readUTF();
                break;
            case 0:
            	jugador.dos.writeUTF("¡Saliendo del programa! Gracias por probar :3");
                jugador.isConnected = false;
                break;
            default:
            	jugador.dos.writeUTF("Opción inválida. Por favor, ingrese un número de las opciones.");
			}//end switch/case
			
		} catch (IOException e) {
			e.printStackTrace();
		}//end try/catch
		
		return res;
	}//end mostrarOpciones
	
	public void realizarMovimiento(Tablero tablero, int fil, int col, String simbolo) {
		tablero.colocarSimbolo(fil, col, simbolo);
	}//end realizarMovimiento
	
	private static boolean validarMovimiento(Tablero tablero, String res) {
		if (res != "" && res.length() <= 2) {
			Integer fila = Integer.valueOf(res.charAt(0));
			Integer columna = Integer.valueOf(res.charAt(1));
			
			if(tablero.esVacia(fila, columna)) {
				return true;
			}//end if
		}//end if
			return false;
	}//validarMovimiento
	
	private static String notificarEstadoJuego(Tablero tablero, String simbolo1, String simbolo2){
		if (!tablero.esGanador(simbolo1)) {
			if (!tablero.esEmpate(simbolo1, simbolo2)) {
				return "En curso";
			}else if(!tablero.esGanador(simbolo2)) {
				return "Empate";
			}else
				return simbolo2;
		}else
			return simbolo1;
	}//notificarEstadoJuego

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
	boolean isConnected;

	public cli(Socket sock, String nick, DataInputStream in, DataOutputStream out) {
		this.nick = nick;
		this.sock = sock;
		this.dis = in;
		this.dos = out;

		this.isConnected = true;
		this.hilo = new Thread(this, nick);
	}//end cli

	public void run() {
		 String msgRecibido = "";
		 

		while (sock.isConnected() && this.isConnected) {
			try {
				
				msgRecibido = dis.readUTF();
				ps.println("\n" + colors.YELLOW + "El cliente " + this.nick + " envia:" + msgRecibido + colors.RESET);
				//this.dos.writeUTF(msgRecibido);

				if (msgRecibido == "0") {
						this.dis.close();
						this.dos.close();
						this.isConnected = false;
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
			if (c.isConnected && !c.nick.equals(this.nick)) {
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
	
	cli jugador1;
	cli jugador2;
	
	public HiloServidor() { try { server = new ServerSocket(puerto); } catch (IOException e) { e.printStackTrace(); }//end try/catch 
	}//end HiloServidor

	@Override
	public void run() {
		while (!(Server.clientesConectados.size() >= 2)) {
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
			} catch (IOException e) {
				e.printStackTrace();
			}//end try/catch
		}//end while

		boolean partidaFinalizada = false;
		cli jugador1 = Server.clientesConectados.get(0); 
		cli jugador2 = Server.clientesConectados.get(1);
		while (partidaFinalizada == false && jugador1.isConnected && jugador2.isConnected) {
			Server.gestionarPartida(jugador1, jugador2);
			partidaFinalizada = true;
		}//end while
		
		try {
			jugador1.dis.close();
			jugador1.dos.close();
			jugador1.isConnected = false;
			jugador1.sock.close();
			Server.clientesConectados.remove(jugador1); 
			ps.println( colors.YELLOW + "\tCliente " + jugador1.nick + " se ha desconectado.\n" + colors.RESET );
			jugador1.notificarClientes(false);
			
			jugador2.dis.close();
			jugador2.dos.close();
			jugador2.isConnected = false;
			jugador2.sock.close();
			Server.clientesConectados.remove(jugador2); 
			ps.println( colors.YELLOW + "\tCliente " + jugador2.nick + " se ha desconectado.\n" + colors.RESET );
			jugador1.notificarClientes(false);
		} catch (IOException e) {
			e.printStackTrace();
		}//end try/catch

	}//end run
}//HiloServidor