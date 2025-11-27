package BatallaNaval;

import java.io.PrintStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class HandlerPartida implements Runnable {
	PrintStream ps = new PrintStream(System.out);

    private final String nombrePartida;
    private final ClientThread jugador1;
    private final ClientThread jugador2;
    private final AtomicBoolean turnoJugador1; 
    
    private volatile boolean partidaActiva = true; 

    public HandlerPartida(Socket s1, Socket s2, String nombre) {
        this.nombrePartida = nombre;
        this.jugador1 = new ClientThread(s1, "Jugador 1");
        this.jugador2 = new ClientThread(s2, "Jugador 2");
        this.turnoJugador1 = new AtomicBoolean(true); 
    }// end HandlerPartida

    @Override
    public void run() {
        try {
            jugador1.setOponente(jugador2);
            jugador2.setOponente(jugador1);
            jugador1.setPartida(this);
            jugador2.setPartida(this);

            new Thread(jugador1).start();
            new Thread(jugador2).start();

            jugador1.getTableroListo().acquire();
            ps.println("[" + nombrePartida + "] Jugador 1 listo.");
            jugador2.getTableroListo().acquire();
            ps.println("[" + nombrePartida + "] Jugador 2 listo.");
            
            jugador1.enviarMensaje(new Mensaje(Mensaje.Tipo.EMPEZAR_PARTIDA, "¡La batalla ha comenzado! Eres el primero en disparar."));
            jugador2.enviarMensaje(new Mensaje(Mensaje.Tipo.EMPEZAR_PARTIDA, "¡La batalla ha comenzado! Espera el turno del oponente."));
            
            while (partidaActiva) {
                Thread.sleep(1000); 
            }//end while

        } catch (InterruptedException e) {
            System.err.println("[" + nombrePartida + "] Partida interrumpida: " + e.getMessage());
        } finally {
            ps.println("[" + nombrePartida + "] Partida finalizada.");
        }//end try/catch/finally
    }//end run

    public synchronized boolean esTurnoDe(ClientThread jugador) {
        return (jugador == jugador1 && turnoJugador1.get()) || (jugador == jugador2 && !turnoJugador1.get());
    }//end esTurnoDe
    
    public synchronized void cambiarTurno() {
        turnoJugador1.set(!turnoJugador1.get());
        
        ClientThread siguiente = turnoJugador1.get() ? jugador1 : jugador2;
        ClientThread actual = turnoJugador1.get() ? jugador2 : jugador1;
        
        siguiente.enviarMensaje(new Mensaje(Mensaje.Tipo.ACTUALIZAR_ESTADO, "¡Es tu turno! Ingresa tus coordenadas."));
        actual.enviarMensaje(new Mensaje(Mensaje.Tipo.ACTUALIZAR_ESTADO, "Turno del oponente. Esperando..."));
    }//end cambiarTurno

    public synchronized void notificarFinPartida(ClientThread ganador) {
    	ClientThread perdedor = (ganador == jugador1) ? jugador2 : jugador1;
        
        ganador.enviarMensaje(new Mensaje(Mensaje.Tipo.FIN_PARTIDA, "¡VICTORIA! Todos los barcos del oponente han sido hundidos."));
        perdedor.enviarMensaje(new Mensaje(Mensaje.Tipo.FIN_PARTIDA, "DERROTA. Todos tus barcos han sido hundidos."));
        
        this.partidaActiva = false;
    }//end notificarFinPartida
}//HanlderPartida
