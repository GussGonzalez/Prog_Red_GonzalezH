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


	public Client() {
		try {

			IP = InetAddress.getByName("127.0.0.1");
			sock = new Socket(IP, puerto);
			
			isConected = true;
			sendNickname = true;
			
			disServidor = new DataInputStream(sock.getInputStream());
			dosServidor = new DataOutputStream(sock.getOutputStream());

			if (sock.isConnected() && sendNickname) {
				ps.println(colors.YELLOW + "Ingrese su nickname:" + colors.RESET);
				String ID = buff.readLine();
				dosServidor.writeUTF(ID);
				sendNickname = false;

				ps.println(colors.YELLOW + "¡ Bienvenido a tu sala de TicTacToe " + ID + " !" + colors.RESET);
			}//end if

			ps.print("Esperando compañero...");

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}//end try/catch

		
		Thread enviarMensaje = new Thread(new Runnable() {
			@Override
			public void run() {
				String msg = "";
				while (!msg.equalsIgnoreCase("/salir")) {
					try {
						msg = buff.readLine();

						dosServidor.writeUTF(msg);
						ps.print("Esperando respuesta...");
					} catch (IOException e) {
						e.printStackTrace();
					}//end try/catch
				}//end while
				
				try {
					isConected = false;
					dosServidor.close();
					sock.close();
				} catch (IOException e) {
					e.printStackTrace();
				}//end try/catch
			}//end run
		}, "ENVIO");

		Thread recibirMensaje = new Thread(new Runnable() {
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

		
		recibirMensaje.start();
		enviarMensaje.start();
	}//end Client
	
}//Client