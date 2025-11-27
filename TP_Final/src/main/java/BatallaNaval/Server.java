package BatallaNaval;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	PrintStream ps = new PrintStream(System.out);

    private static final int PUERTO = 666;
    private final ConcurrentLinkedQueue<Socket> salaDeEspera = new ConcurrentLinkedQueue<>();
    private int contadorPartidas = 0;
    
    private final ExecutorService pool = Executors.newCachedThreadPool(); 

    public void iniciar() {
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            serverSocket.setReuseAddress(true); 
            
            ps.println("Servidor de Batalla Naval iniciado en el puerto " + PUERTO);
            ps.println("Esperando clientes...");

            while (true) {
                Socket clienteSocket = serverSocket.accept();
                ps.println("  -> Nuevo cliente conectado desde: " + clienteSocket.getInetAddress().getHostAddress());

                salaDeEspera.add(clienteSocket);
                
                if (salaDeEspera.size() >= 2) {
                    Socket jugador1 = salaDeEspera.poll();
                    Socket jugador2 = salaDeEspera.poll();
                    
                    contadorPartidas++;
                    HandlerPartida partida = new HandlerPartida(jugador1, jugador2, "Partida " + contadorPartidas);
                    
                    pool.execute(partida); 
                    ps.println("\n--- Partida #" + contadorPartidas + " creada. Esperando posicionamiento. ---\n");
                }//end if
            }//end while
        } catch (IOException e) {
            System.err.println("Error grave en el Servidor: " + e.getMessage());
        } finally {
            pool.shutdown();
        }//end try/catch/finally
    }//end iniciar
}//Servidor
