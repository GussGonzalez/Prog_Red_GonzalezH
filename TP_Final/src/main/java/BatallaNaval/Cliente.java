package BatallaNaval;

import java.net.Socket;
import java.io.*;
import java.util.List;

import Utils.colors;

public class Cliente {
	//imports
	Utils.colors Colors;
	InputStreamReader isr = new InputStreamReader(System.in);
	PrintStream ps = new PrintStream(System.out);
	BufferedReader br = new BufferedReader(isr);
	
	private Socket socket;
	private DataOutputStream dosServidor;
	private DataInputStream disServidor;
	
	private Tablero tableroPropio;
	private Tablero tableroOponente; 
	    
	private String serverIp = "127.0.0.1";
	private int port = 666;
	
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
				ps.println(Colors.RED + "Error al leer la entrada. Intenta nuevamente.\n" + Colors.RESET);
			}//end try/catch
			
			return input;
	}//leerConsola
	
	public void iniciar() {
        System.out.println(Colors.CYAN + "Cliente Batalla Naval");
        
        System.out.print("Ingrese IP del servidor (default: 127.0.0.1). Si se quiere usar la default, simplemente presione enter: " + Colors.RESET);
        String ip = leerConsola();
        if (!ip.isEmpty()) this.serverIp = ip;
        
        try {
            socket = new Socket(serverIp, port);
            System.out.println(Colors.YELLOW + "Conectado al servidor. Esperando compañero..." + Colors.RESET);

            dosServidor = new DataOutputStream(socket.getOutputStream());
            disServidor = new DataInputStream(socket.getInputStream());
            tableroPropio = new Tablero();
            tableroOponente = new Tablero(); 

            posicionarBarcos();

            dosServidor.writeUTF(new Mensaje(Mensaje.Tipo.POSICIONAMIENTO_OK, "Tablero listo", tableroPropio));
            dosServidor.flush();
            System.out.println("Barcos posicionados. Esperando inicio de partida...");

            buclePrincipalJuego();

        } catch (IOException e) {
            System.err.println(Colors.RED + "❌ Error de conexión o I/O: " + e.getMessage() + Colors.RESET);
        } finally {
            cerrarRecursos();
        }//end try/catch/finally
    }//end iniciar
	
    private void posicionarBarcos() {
        List<Barco> barcosRestantes;
        do {
            barcosRestantes = tableroPropio.getBarcosNoColocados();
            if (barcosRestantes.isEmpty()) break;
            
            ps.println(Colors.CYAN + "\n--- Posicionamiento de Barcos ---" + Colors.RESET);
            tableroPropio.mostrarTablero(false);
            ps.println(Colors.CYAN + "\nBarcos pendientes:" + Colors.RESET);
            
            for (int i = 0; i < barcosRestantes.size(); i++) {
                Barco b = barcosRestantes.get(i);
                ps.printf("  %d) %s (Longitud: %d)\n", i + 1, b.getNombre(), b.getLongitud());
            }//end for

            try {
                ps.print("\nSelecciona un barco (ej. Portaaviones): ");
                String nombreBarco = leerConsola().trim();
                
                Barco barcoSel = barcosRestantes.stream()
                    .filter(b -> b.getNombre().equalsIgnoreCase(nombreBarco))
                    .findFirst().orElse(null);

                if (barcoSel == null) {
                    System.out.println("Barco no válido o ya colocado.");
                    continue;
                }//end if
                
                System.out.print("Fila inicial (0-9): ");
                int fila = Integer.parseInt(leerConsola().trim());
                System.out.print("Columna inicial (0-9): ");
                int col = Integer.parseInt(leerConsola().trim());
                System.out.print("Orientación (H/V): ");
                boolean horizontal = leerConsola().trim().toUpperCase().startsWith("H");

                if (!tableroPropio.colocarBarco(barcoSel.getNombre(), fila, col, horizontal)) {
                    System.out.println(Colors.RED + "ERROR: No se puede colocar aquí (límites o colisión). Intenta de nuevo." + Colors.RESET);
                } else {
                    System.out.println(Colors.GREEN + "¡Barco colocado!" + Colors.RESET);
                }//end if/else
            } catch (NumberFormatException e) {
                System.out.println(Colors.RED + "Entrada inválida. Usa números para coordenadas." + Colors.RESET);
            }//end try/catch
        } while (barcosRestantes.size() > 0);
    }//end posicionarBarcos
    
    private void buclePrincipalJuego() {
        boolean partidaEnCurso = true;
        
        while (partidaEnCurso) {
            try {
                Mensaje mensaje = (Mensaje) disServidor.readObject();
                
                switch (mensaje.getTipo()) {
                    case EMPEZAR_PARTIDA:
                        System.out.println("\n*** " + mensaje.getDatos() + " ***");
                        mostrarAmbosTableros();
                        if (mensaje.getDatos().contains("primero")) {
                            pedirDisparo();
                        }//end if
                        break;
                        
                    case ACTUALIZAR_ESTADO:
                        System.out.println("\n*** NOTIFICACION: " + mensaje.getDatos() + " ***");
                        if (mensaje.getDatos().contains("tu turno") || mensaje.getDatos().contains("Dispara de nuevo")) {
                            mostrarAmbosTableros();
                            pedirDisparo();
                        }//end if
                        break;
                        
                    case RESULTADO_DISPARO:
                        System.out.println("--- Tu Disparo: " + mensaje.getDatos() + " ---");
                        break;
                        
                    case ENVIO_TABLERO_OPONENTE:
                        tableroOponente = (Tablero) mensaje.getObjeto();
                        mostrarAmbosTableros();
                        break;
                        
                    case FIN_PARTIDA:
                        System.out.println("\n" + """
                            ---------------------------
                                🏁 FIN DE PARTIDA 🏁
                            ---------------------------
                            """ + mensaje.getDatos());
                        partidaEnCurso = false;
                        break;
                    default:
                        System.out.println("Mensaje de servidor desconocido.");
                }//end switch/case

            } catch (EOFException e) {
                System.out.println("Servidor cerró la conexión. Fin de partida.");
                partidaEnCurso = false;
            } catch (ClassNotFoundException | IOException e) {
                System.err.println("❌ Error de comunicación con el servidor: " + e.getMessage());
                partidaEnCurso = false;
            }//end try/catch
        }//end while partida
    }//end buclePrincipalJuego
    
    private void pedirDisparo() {
        ps.println("\n Ingresa coordenadas de disparo (Fila,Columna ej: 0,5):");
        try {
            String entrada = leerConsola().trim();
            if (!entrada.matches("\\d+,\\s*\\d+")) {
                 ps.println(Colors.RED + "Formato de coordenadas inválido. Intenta de nuevo." + Colors.RESET);
                 pedirDisparo(); 
                 return;
            }//end if
            dosServidor.writeObject(new Mensaje(Mensaje.Tipo.TURNO_DISPARO, entrada));
            dosServidor.flush();
        } catch (IOException e) {
            System.err.println("Error enviando disparo: " + e.getMessage());
        }//end try/catch
    }//end pedirDisparo
    
    private void mostrarAmbosTableros() {
        ps.println("\n" + """
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
        ps.println("==================================");
    }//end mostrarAmbosTableros

    private void cerrarRecursos() {
        try {
            if (dosServidor != null) dosServidor.close();
            if (disServidor != null) disServidor.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) { /* Ignorar */ }
    }//end cerrarRecursos

}//Cliente
