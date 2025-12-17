package TicTacToe;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import Utils.colors;

public class Client {

	PrintStream ps = new PrintStream(System.out);

	DataInputStream disServidor = null;
	DataOutputStream dosServidor = null;

	InputStreamReader is = new InputStreamReader(System.in);
	BufferedReader buff = new BufferedReader(is);

	InetAddress IP = null;
	int puerto = 7777;
	Socket sock = null;
	boolean isConnected = false;

	boolean sendNickname = false;
	boolean compañeroConectado = false;
	boolean partidaFinalizada = false;
	boolean miTurno = false;

	public void conectarConServidor() {
		try {
			IP = InetAddress.getByName("127.0.0.1");
			
			sock = new Socket(IP, puerto);
			
			isConnected = true;
			sendNickname = true;
			
			disServidor = new DataInputStream(sock.getInputStream());
			dosServidor = new DataOutputStream(sock.getOutputStream());

			if (sock.isConnected() && sendNickname) {
				ps.println(colors.YELLOW + "Ingrese su nickname:" + colors.RESET);
				String ID = buff.readLine();
				dosServidor.writeUTF(ID);
				sendNickname = false;
				ps.println(colors.YELLOW + "¡ Bienvenido al servidor TicTacToe " + ID + " !" + colors.RESET);
			}//end if
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}//end try/catch
	}

	public Client() {
		conectarConServidor();
		
		Thread realizarMovimiento = new Thread(new Runnable() {
			@Override
			public void run() {
				while (isConnected && !partidaFinalizada) {
					Integer res;
					boolean valido = false;
					while (!valido) {
						try{
							res = Integer.valueOf(buff.readLine());
							dosServidor.writeUTF(String.valueOf(res));
							valido = true;
						} catch (IOException e) {
							ps.println(colors.RED + "Input inválido, ingrese un único número." + colors.RESET);
							e.printStackTrace();
						}
						
						try {
							isConnected = false;
							dosServidor.close();
							sock.close();
						} catch (IOException e) {
							e.printStackTrace();
						}//end try/catch
					}
				}//end while
			}//end run
		}, "ENVIO");

		
		Thread recibirEstadoJuego = new Thread(new Runnable() {
			@Override
			public void run() {
				String msg = "";
				while (isConnected) {
					 try {
						msg = disServidor.readUTF();
						ps.println(msg);
					} catch (IOException e) {
						e.printStackTrace();
					}//end try/catch
				}//end while
				
				try {
					isConnected = false;
					disServidor.close();
					sock.close();
				} catch (IOException e) {
					e.printStackTrace();
				}//end try/catch			
			}//end run
		}, "RECIBIR");

		
		recibirEstadoJuego.start();
		realizarMovimiento.start();
		
		/*
		while (!compañeroConectado) { 
			try {
				ps.print("Esperando compañero...");
				compañeroConectado = disServidor.readUTF() == "TRUE" ? true : false ;
			} catch (IOException e) {
				e.printStackTrace();
			}//end try/catch
		}//end while
		

		while(!partidaFinalizada) {
			try {
				//miTurno = disServidor.readUTF() == "TRUE" ? true : false;
				 
				while (miTurno) {
					//miTurno = disServidor.readUTF() == "TRUE" ? true : false;
				}//end while
			
				if (disServidor.readUTF() == "Partida finalizada") {
					partidaFinalizada = true;
				}//end if
			} catch (IOException e) {
				e.printStackTrace();
			}//end try/catch
		}//end while
		*/
	}//end Client
	
}//Client