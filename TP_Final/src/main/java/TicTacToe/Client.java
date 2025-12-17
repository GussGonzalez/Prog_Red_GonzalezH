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
	boolean isConected = false;

	boolean sendNickname = false;
	boolean compañeroConectado = false;
	
	//while (!compañeroConectado) { ps.print("Esperando compañero...");}

	public void conectarConServidor() {
		try {
			sock = new Socket(IP, puerto);
			
			isConected = true;
			sendNickname = true;
			
			disServidor = new DataInputStream(sock.getInputStream());
			dosServidor = new DataOutputStream(sock.getOutputStream());
			
			IP = InetAddress.getByName("127.0.0.1");
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
				String res;
				try {
					res = buff.readLine();
					dosServidor.writeUTF(res);
					ps.print("Esperando respuesta...");
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				try {
					isConected = false;
					dosServidor.close();
					sock.close();
				} catch (IOException e) {
					e.printStackTrace();
				}//end try/catch
			}//end run
		}, "ENVIO");

		
		Thread recibirEstadoJuego = new Thread(new Runnable() {
			@Override
			public void run() {
				String msg = "";
				while (isConected) {
					 try {
						msg = disServidor.readUTF();
						ps.println( colors.YELLOW + msg + colors.RESET);
					} catch (IOException e) {
						e.printStackTrace();
					}//end try/catch
				}//end while
				
				try {
					isConected = false;
					disServidor.close();
					sock.close();
				} catch (IOException e) {
					e.printStackTrace();
				}//end try/catch			
			}//end run
		}, "RECIBIR");

		
		recibirEstadoJuego.start();
		realizarMovimiento.start();
	}//end Client
	
}//Client