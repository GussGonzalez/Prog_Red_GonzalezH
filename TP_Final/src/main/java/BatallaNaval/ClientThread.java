package BatallaNaval;

import Modelo.Tablero;
import Utils.colors;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.Semaphore;

public class ClientThread implements Runnable {
	PrintStream ps = new PrintStream(System.out);

    private final Socket socket;
    private final String nombreJugador;
    private ObjectOutputStream oosServidor;
	private ObjectInputStream oisServidor;
    
    private Tablero tableroPropio; 
    private ClientThread oponente;
    private HandlerPartida partida;
    
    private final Semaphore tableroListo = new Semaphore(0); 

    public ClientThread(Socket socket, String nombre) {
        this.socket = socket;
        this.nombreJugador = nombre;
        ObjectOutputStream tempOut = null;
        ObjectInputStream tempIn = null;
        try {
            tempOut = new ObjectOutputStream(socket.getOutputStream());
            tempIn = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.err.println("Error al inicializar flujos para " + nombre + ": " + e.getMessage());
        }//end try/catch
        this.oosServidor = tempOut;
        this.oisServidor = tempIn;
    }//end ClientThread

    public void setOponente(ClientThread oponente) { this.oponente = oponente; }
    public void setPartida(HandlerPartida partida) { this.partida = partida; }
    public Semaphore getTableroListo() { return tableroListo; }

    public void enviarMensaje(Mensaje mensaje) {
        if (oosServidor == null) return;
        try {
            if (mensaje.getTipo() == Mensaje.Tipo.ENVIO_TABLERO_OPONENTE) {
            	oosServidor.reset();
            }
            
            oosServidor.writeObject(mensaje);
            oosServidor.flush();
        } catch (SocketException e) {
            ps.println(colors.MAGENTA + "Desconexión de " + nombreJugador + " detectada. Partida terminada." + colors.RESET);
        } catch (IOException e) {
            System.err.println("Error enviando mensaje a " + nombreJugador + ": " + e.getMessage());
        }//end try/catch/catch
    }//end enviarMensaje

    @Override
    public void run() {
        try {
            recibirPosicionamiento();
            tableroListo.release(); 
            while (!socket.isClosed()) {
            	System.out.println("while");
                Mensaje mensaje = (Mensaje) oisServidor.readObject();
                System.out.println("mesnje:"+mensaje);
                if (mensaje.getTipo() == Mensaje.Tipo.TURNO_DISPARO) {
                    manejarDisparo(mensaje.getDatos());
                }//end if
            }//end while
        } catch (EOFException | SocketException e) {
            ps.println(colors.MAGENTA + nombreJugador + " se desconectó." + colors.RESET);
        } catch (ClassNotFoundException | IOException e) {
            System.err.println("Error en el hilo de " + nombreJugador + ": " + e.getMessage());
        } finally {
            cerrarRecursos();
        }//end try/catch/catch/finally
    }//end run
    
    private void recibirPosicionamiento() throws ClassNotFoundException, IOException {
        Mensaje posMsg = (Mensaje) oisServidor.readObject();
        System.out.println( "posMSG:" + posMsg);
        if (posMsg.getTipo() == Mensaje.Tipo.POSICIONAMIENTO_OK) {
            this.tableroPropio = (Tablero) posMsg.getObjeto();
        } else {
             throw new IOException("Protocolo de posicionamiento inválido.");
        }//end if/else
    }//end recibirPosicionamiento

    private void manejarDisparo(String coordenadas) {
        if (!partida.esTurnoDe(this)) {
            enviarMensaje(new Mensaje(Mensaje.Tipo.RESULTADO_DISPARO, "ESPERA_TU_TURNO"));
            return;
        }//end if
        
        try {
            String[] parts = coordenadas.split(",");
            int fila = Integer.parseInt(parts[0].trim());
            int col = Integer.parseInt(parts[1].trim());
            String resultado = oponente.tableroPropio.disparar(fila, col);
            
            enviarMensaje(new Mensaje(Mensaje.Tipo.RESULTADO_DISPARO, resultado));
            
            oponente.enviarMensaje(new Mensaje(Mensaje.Tipo.ACTUALIZAR_ESTADO, "¡Te atacaron en (" + fila + "," + col + ")! Resultado: " + resultado));
            
            enviarMensaje(new Mensaje(Mensaje.Tipo.ENVIO_TABLERO_OPONENTE, "Tablero oponente actualizado", oponente.tableroPropio));
            
            if (oponente.tableroPropio.todosLosBarcosCaidos()) {
                partida.notificarFinPartida(this);
            } else if (resultado.contains("IMPACTO") || resultado.contains("HUNDIDO")) { 
                enviarMensaje(new Mensaje(Mensaje.Tipo.ACTUALIZAR_ESTADO, "¡Turno mantenido por impacto! Dispara de nuevo."));
            } else { 
                partida.cambiarTurno();
            }//end if/else if/else
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            enviarMensaje(new Mensaje(Mensaje.Tipo.RESULTADO_DISPARO, "COORDENADAS_INVALIDAS"));
        }//end try/catch
    }//end manejarDisparo
    
    private void cerrarRecursos() {
        try {
            if (oisServidor != null) oisServidor.close();
            if (oosServidor != null) oosServidor.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) { /* Ignorar */ }
    }//end cerrarRecursos
}//ClientThread
