package Chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Server {
	Utils.Colors colors;
	PrintStream ps = new PrintStream(System.out);
	
    public static final List<cli> clientesConectados = Collections.synchronizedList(new ArrayList<>());

    public Server() {
        ps.println("INICIANDO SERVIDOR");
        HiloServidor serv = new HiloServidor();
        serv.setName("SERVIDOR");
        serv.start();
    }//end Server
    
  /*
    public static void main(String[] args) {
        new Server();
    }
  */

    
    
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
        try {
            while (sock.isConnected() && this.isConected) {
                msgRecibido = dis.readUTF();
                
                if (msgRecibido.equals("FORWARD::PRIVATE")) {
                    String recipient = dis.readUTF();
                    
                    int ivLen = dis.readInt();
                    byte[] iv = new byte[ivLen]; 
                    dis.readFully(iv);
                    
                    int cipherLen = dis.readInt();
                    byte[] cipherBytes = new byte[cipherLen]; 
                    dis.readFully(cipherBytes);

                    String type = dis.readUTF();
                    String extra = dis.readUTF();
                    boolean sent = false;
                    
                    synchronized (Server.clientesConectados) {
                        for (cli c : Server.clientesConectados) {
                            if (c.nick.equalsIgnoreCase(recipient) && c.isConected) {
                                synchronized (c.dos) {
                                    c.dos.writeUTF("INCOMING::PRIVATE");
                                    c.dos.writeUTF(this.nick);
                                    c.dos.writeInt(ivLen); 
                                    c.dos.write(iv);
                                    c.dos.writeInt(cipherLen); 
                                    c.dos.write(cipherBytes);
                                    c.dos.writeUTF(type);
                                    c.dos.writeUTF(extra == null ? "" : extra);
                                    c.dos.flush();
                                }//end synchronized
                                sent = true;
                                break;
                                
                            }//end if
                        }//end for
                    }//end synchronized
                    
                    if (!sent) {
                        synchronized (dos) {
                            dos.writeUTF("RESP::NOTFOUND");
                            dos.writeUTF(recipient);
                            dos.flush();
                        }//end synchronized
                    }//end if
                    continue;
                }//end if
                
                
                if (msgRecibido.equals("FORWARD::BROADCAST")) {
                    int ivLen = dis.readInt();
                    byte[] iv = new byte[ivLen]; 
                    dis.readFully(iv);
                    
                    int cipherLen = dis.readInt();
                    byte[] cipherBytes = new byte[cipherLen]; 
                    dis.readFully(cipherBytes);
                    
                    String type = dis.readUTF();
                    String extra = dis.readUTF();
                    
                    synchronized (Server.clientesConectados) {
                        for (cli c : Server.clientesConectados) {
                            if (c.nick.equalsIgnoreCase(this.nick)) continue;
                            synchronized (c.dos) {
                                c.dos.writeUTF("INCOMING::BROADCAST");
                                c.dos.writeUTF(this.nick);
                                c.dos.writeInt(ivLen); c.dos.write(iv);
                                c.dos.writeInt(cipherLen); c.dos.write(cipherBytes);
                                c.dos.writeUTF(type);
                                c.dos.writeUTF(extra == null ? "" : extra);
                                c.dos.flush();
                            }//end synchronized
                        }//end for
                    }//end synchronized
                    continue;
                }//end if
                
                
                if (msgRecibido.equals("/listar")) {
                    String lista;
                    
                    synchronized (Server.clientesConectados) {
                        lista = Server.clientesConectados.stream()
                                .map(c -> c.nick)
                                .collect(Collectors.joining(","));
                    }//end synchronized
                    
                    synchronized (dos) {
                        dos.writeUTF("RESP::LIST");
                        dos.writeUTF(lista);
                        dos.flush();
                    }//end synchronized
                    continue;
                    
                }//end if
                
                
                if (msgRecibido.startsWith("/")) {
                    if (msgRecibido.equals("/salir")) {
                        this.isConected = false;
                        try { this.dis.close(); } catch (Exception e) {}//end try/catch
                        try { this.dos.close(); } catch (Exception e) {}//end try/catch
                        try { this.sock.close(); } catch (Exception e) {}//end try/catch
                        
                        Server.clientesConectados.remove(this);
                        ps.println(colors.BLUE + "\tCliente " + this.nick + " se ha desconectado.\n" + colors.RESET);
                        
                        this.notificarClientes(false);
                        break;
                    }//end if
                }//end if
                
                
                String cliName = "Todos";
                String msg = msgRecibido;
                
                if (msgRecibido.contains("&")) {
                    String[] token = msgRecibido.split("&", 2);
                    cliName = token[0].trim().toLowerCase();
                    msg = token.length > 1 ? token[1].trim() : "";
                }//end if
                
                ps.println("\n" + colors.RED + "El cliente " + this.nick + " envia:" + msgRecibido + "\n\t"
                        + " al cliente =>" + colors.GREEN + (cliName.equals("Todos") ? " Todos" : cliName.toUpperCase())
                        + "\n" + colors.RESET);
                
                if (!msg.equals("")) {
                    synchronized (Server.clientesConectados) {
                        for (cli c : Server.clientesConectados) {
                            if (!c.isConected) continue;
                            
                            if (!cliName.equals("Todos") && cliName.equalsIgnoreCase(c.nick)) {
                                synchronized (c.dos) {
                                    c.dos.writeUTF(this.nick + ":" + msg);
                                    c.dos.flush();
                                }//end synchronized
                                break;
                                
                            } else if (cliName.equals("Todos") && !c.nick.equalsIgnoreCase(this.nick)) {
                                synchronized (c.dos) {
                                    c.dos.writeUTF(this.nick + ":" + msg);
                                    c.dos.flush();
                                }//end synchronized
                            }//end if/else if
                        }//end for
                    }//end synchronized
                }//end if
            }//end while
            
        } catch (IOException e) {
            try {
                this.isConected = false;
                this.dis.close();
                this.dos.close();
                this.sock.close();
            } catch (Exception ex) {}//end try/catch
            
            Server.clientesConectados.remove(this);
            ps.println(colors.BLUE + "Cliente " + this.nick + " desconectado inesperadamente." + colors.RESET);
            this.notificarClientes(false);
        }//end try/catch
    }//end cli


    public void notificarClientes(boolean joined) {
        synchronized (Server.clientesConectados) {
            for (cli c : Server.clientesConectados) {
                if (c.isConected && !c.nick.equals(this.nick)) {
                    try {
                        synchronized (c.dos) {
                            String msg = joined ? ("\t---" + this.nick + " se ha unido al chat---") : ("\t---" + this.nick + " se ha desconectado---");
                            c.dos.writeUTF(colors.MAGENTA + msg + colors.RESET);
                            c.dos.flush();
                        }//end synchronized
                    } catch (IOException e) {}//end try/catch
                }//end if
            }//end for
        }//end synchronized
    }//end notificarClientes
}//cli

    
    
    
    class HiloServidor extends Thread {
        ServerSocket server;
        int puerto = 5000;
        Socket sockAux;
        
        PrintStream ps = new PrintStream(System.out);
        DataInputStream disCliente;            //recibe del cliente
        DataOutputStream dosCliente;           //devuelve al cliente

        public HiloServidor() {
            try {
                server = new ServerSocket(puerto);
            } catch (IOException e) {
                e.printStackTrace();
            }//end try/catch
            
        }//end HiloServidor
          
        @Override
    	public void run() {
        	while (true) {
            	try {
                	ps.println("Esperando conexion con un cliente");
                	sockAux = server.accept();
                	ps.println(colors.YELLOW + "BIENVENIDO AL SERVIDOR LOCAL" + colors.RESET);
                	ps.println(colors.YELLOW + "Cliente conectado: " + sockAux.getInetAddress().getHostAddress() + colors.RESET);
                	
                	disCliente = new DataInputStream(sockAux.getInputStream());
                	dosCliente = new DataOutputStream(sockAux.getOutputStream());
                	
                	ps.println(colors.BLUE + "Creando un cliente... Esperando NickName" + colors.RESET);
                	String ID = disCliente.readUTF();
                	
                	if (ID == null || ID.trim().isEmpty()) ID = "anon";
                	
                	cli newCliente = new cli(sockAux, ID, disCliente, dosCliente);
                	ps.println(colors.RED + "El cliente " + newCliente.nick + " accedió al servidor.\n" + colors.RESET);
                	Server.clientesConectados.add(newCliente);
                	
                	newCliente.hilo.start();
                	newCliente.notificarClientes(true);
                	
            	} catch (IOException e) {
                	e.printStackTrace();
            	}//end try/catch
        	}//end while
    	}//end run

    }//end HiloServidor class
    
}//Server
