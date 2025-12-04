package BatallaNaval;

import java.io.*;
import java.util.List;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import java.util.logging.Level;
import java.util.logging.Logger;

import BatallaNaval.Mensaje.Tipo;

import Modelo.Barco;
import Modelo.Tablero;
import Utils.colors;

public class Cliente {
	//imports
	PrintStream ps = new PrintStream(System.out);

	DataInputStream disServidor = null;
	DataOutputStream dosServidor = null;

	InputStreamReader is = new InputStreamReader(System.in);
	BufferedReader buff = new BufferedReader(is);

	InetAddress IP = null;
	InetAddress serverIP = null;
	int puerto = 7777;
	Socket sock = null;
	boolean isConected = false;

	boolean sendNickname = false;
	
	
	private ObjectOutputStream oosServidor;
	private ObjectInputStream oisServidor;
	
	private Tablero tableroPropio;
	private Tablero tableroOponente; 
	    
	
	public String leerConsola() {
		int linea;
        String input = "";
        
			try {
				while ( (linea = System.in.read()) != 13) { //mientras no se haya presionado enter
				    if(linea != 10)//si no es un LF (line feed), básicamente no toma el byte extra de salto de línea que se estaba guardando
					    input = input + (char)linea;
				}//end while 
				
				ps.println(input);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				ps.println(colors.RED + "Error al leer la entrada. Intenta nuevamente.\n" + colors.RESET);
			}//end try/catch
			
			return input;
	}//leerConsola
	
	public void iniciar() {
        ps.println(colors.CYAN + "Cliente Batalla Naval");
        
        try {
        	IP = InetAddress.getByName("127.0.0.1");
			sock = new Socket(IP, puerto);
			
			isConected = true;
			sendNickname = true;
			
            ps.println(colors.YELLOW + "Conectado al servidor. Esperando compañero..." + colors.RESET);

            //oosServidor = new ObjectOutputStream(sock.getOutputStream());

            //ps.println( "H" );
            //oisServidor = new ObjectInputStream(sock.getInputStream());
            dosServidor = new DataOutputStream( sock.getOutputStream() );
           // ps.println( "O" );
            tableroPropio = new Tablero();
            tableroOponente = new Tablero(); 
            
            ps.println( "Hola" );
            if (sock.isConnected() && sendNickname) {
				ps.println("Ingrese su nombre de usuario:");
				String ID = leerConsola();
				dosServidor.writeUTF(ID);
				sendNickname = false;

				ps.println("Bienvenido al chat " + ID);
			}

            posicionarBarcos();
            
            oosServidor.writeUTF("Tablero listo");
            oosServidor.writeObject(new Mensaje(Tipo.POSICIONAMIENTO_OK, "Tablero listo", tableroPropio));//es aca el error
            oosServidor.flush();
            ps.println(colors.GREEN + "Barcos posicionados. Esperando inicio de partida..." + colors.RESET);

            buclePrincipalJuego();

        } catch (UnknownHostException e) {
			Logger.getLogger(Cliente.class.getName()).log(Level.WARNING, null, e);
		} catch (IOException e) {
			Logger.getLogger(Cliente.class.getName()).log(Level.WARNING, null, e);
        } finally {
            cerrarRecursos();
        }//end try/catch/catch/finally
    }//end iniciar
	
    private void posicionarBarcos() {
        List<Barco> barcosRestantes = tableroPropio.getBarcosNoColocados();;
        while (barcosRestantes.size() > 0){
            barcosRestantes = tableroPropio.getBarcosNoColocados();
            if (barcosRestantes.isEmpty()) break;
            
            ps.println(colors.CYAN + "\n--- Posicionamiento de Barcos ---" + colors.RESET);
            tableroPropio.mostrarTablero(false);
            ps.println("\nBarcos pendientes:");
            for (int i = 0; i < barcosRestantes.size(); i++) {
                Barco b = barcosRestantes.get(i);
                ps.printf("  %d) %s (Longitud: %d)\n", i + 1, b.getNombre(), b.getLongitud());
            }//end for

            try {
                ps.print(colors.YELLOW + "\nSelecciona un barco (ej. Portaaviones): " + colors.RESET);
                String nombreBarco = leerConsola().trim();
                
                Barco barcoSel = barcosRestantes.stream()
                    .filter(b -> b.getNombre().equalsIgnoreCase(nombreBarco))
                    .findFirst().orElse(null);

                if (barcoSel == null) {
                    ps.println(colors.RED + "Barco no válido o ya colocado." + colors.RESET);
                    continue;
                }//end if
                
                ps.print("Fila inicial (0-9): ");
                int fila = Integer.parseInt(leerConsola().trim());
                ps.print("Columna inicial (0-9): ");
                int col = Integer.parseInt(leerConsola().trim());
                ps.print("Orientación (H/V): ");
                boolean horizontal = leerConsola().trim().toUpperCase().startsWith("H");

                if (!tableroPropio.colocarBarco(barcoSel.getNombre(), fila, col, horizontal)) {
                    ps.println(colors.RED + "ERROR: No se puede colocar aquí (límites o colisión). Intenta de nuevo." + colors.RESET);
                } else {
                    ps.println(colors.GREEN + "¡Barco colocado!" + colors.RESET);
                }//end if/else
            } catch (NumberFormatException e) {
                ps.println(colors.RED + "Entrada inválida. Usa números para coordenadas." + colors.RESET);
            }//end try/catch
        }//end while
    }//end posicionarBarcos
    
    private void buclePrincipalJuego() {
        boolean partidaEnCurso = true;
        
        while (partidaEnCurso) {
            try {
                Mensaje mensaje = (Mensaje) oisServidor.readObject();
                
                switch (mensaje.getTipo()) {
                    case EMPEZAR_PARTIDA:
                        ps.println("\n*** " + mensaje.getDatos() + " ***");
                        mostrarAmbosTableros();
                        if (mensaje.getDatos().contains("primero")) {
                            pedirDisparo();
                        }//end if
                        break;
                        
                    case ACTUALIZAR_ESTADO:
                        ps.println("\n*** NOTIFICACION: " + mensaje.getDatos() + " ***");
                        if (mensaje.getDatos().contains("tu turno") || mensaje.getDatos().contains("Dispara de nuevo")) {
                            mostrarAmbosTableros();
                            pedirDisparo();
                        }//end if
                        break;
                        
                    case RESULTADO_DISPARO:
                        ps.println(colors.GREEN + "--- Tu Disparo: " + mensaje.getDatos() + " ---" + colors.RESET);
                        break;
                        
                    case ENVIO_TABLERO_OPONENTE:
                        tableroOponente = (Tablero) mensaje.getObjeto();
                        mostrarAmbosTableros();
                        break;
                        
                    case FIN_PARTIDA:
                        ps.println(colors.CYAN + "\n" + """
                            ---------------------------
                                 FIN DE PARTIDA 
                            ---------------------------
                            """ + mensaje.getDatos() + colors.RESET);
                        partidaEnCurso = false;
                        break;
                    default:
                        ps.println("Mensaje de servidor desconocido.");
                }//end switch/case

            } catch (EOFException e) {
                ps.println("Servidor cerró la conexión. Fin de partida.");
                partidaEnCurso = false;
                
                Logger.getLogger(Cliente.class.getName()).log(Level.WARNING, null, e);
                
            } catch (ClassNotFoundException | IOException e) {
                System.err.println("Error de comunicación con el servidor: " + e.getMessage());
                partidaEnCurso = false;
                Logger.getLogger(Cliente.class.getName()).log(Level.WARNING, null, e);
            }//end try/catch
        }//end while partida
    }//end buclePrincipalJuego
    
    private void pedirDisparo() {
        ps.println("\n Ingresa coordenadas de disparo (Fila,Columna ej: 0,5):");
        try {
            String entrada = leerConsola().trim();
            if (!entrada.matches("\\d+,\\s*\\d+")) {
                 ps.println(colors.RED + "Formato de coordenadas inválido. Intenta de nuevo." + colors.RESET);
                 pedirDisparo(); 
                 return;
            }//end if
            oosServidor.writeObject(new Mensaje(Mensaje.Tipo.TURNO_DISPARO, entrada));
            oosServidor.flush();
        } catch (IOException e) {
            System.err.println("Error enviando disparo: " + e.getMessage());
            Logger.getLogger(Cliente.class.getName()).log(Level.WARNING, null, e);
        }//end try/catch
    }//end pedirDisparo
    
    private void mostrarAmbosTableros() {
        ps.println( "\n" + """
            ==================================
                  TU TABLERO (PROPIO)
            ==================================
            """);
        tableroPropio.mostrarTablero(false); 
        tableroPropio.mostrarBarcos(); 

        ps.println("\n" + """
            ==================================
                 TABLERO OPONENTE
            ==================================
            """);
        tableroOponente.mostrarTablero(true); 
        ps.println("==================================" + colors.RESET);
    }//end mostrarAmbosTableros

    private void cerrarRecursos() {
        try {
            if (oosServidor != null) oosServidor.close();
            if (oisServidor != null) oisServidor.close();
            if (sock != null && !sock.isClosed()) sock.close();
        } catch (IOException e) { 
        	Logger.getLogger(Cliente.class.getName()).log(Level.WARNING, null, e);
        }//end try/catch
    }//end cerrarRecursos

}//Cliente
