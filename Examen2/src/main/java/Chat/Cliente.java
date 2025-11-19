package Chat;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.security.SecureRandom;


public class Cliente {
	Utils.Colors colors;
	Utils.Security sec;
	
	PrintStream ps = new PrintStream(System.out);
    DataInputStream disServidor = null;
    DataOutputStream dosServidor = null;
    BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
    InetAddress IP = null;
    int puerto = 5000;
    Socket sock = null;
    boolean isConected = false;
    private String sharedKey = "";
    private SecureRandom sr = sec.sr;
   
    
    public Cliente() {
        try {
            IP = InetAddress.getByName("130.10.1.54");
            sock = new Socket(IP, puerto);
            isConected = true;
            disServidor = new DataInputStream(sock.getInputStream());
            dosServidor = new DataOutputStream(sock.getOutputStream());
            
            if (sock.isConnected()) {
                ps.println(colors.YELLOW + "Ingrese su ID:" + colors.RESET);
                String ID = buff.readLine();
                
                if (ID == null) ID = "anon";
                
                ps.println(colors.YELLOW + "Ingrese la clave secreta compartida (misma para todos los clientes):" + colors.RESET);
                String clave = buff.readLine();
                
                if (clave == null) clave = "";
                this.sharedKey = clave;
                
                dosServidor.writeUTF(ID);
                dosServidor.flush();
                ps.println(colors.BLACK + "Bienvenido al chat " + ID + colors.RESET);
            }//end if
            ps.print("\t->");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }//end try/catch

        
        
        Thread enviarMensaje = new Thread(() -> {
            String msg = "";
            try {
                while (true) {
                    msg = buff.readLine();
                    if (msg == null) break;
                    
                    msg = msg.trim();
                    
                    if (msg.equals("")) { ps.print("\t->"); continue; }
                    
                    if (msg.equalsIgnoreCase("/logout")) {
                        dosServidor.writeUTF("/logout");
                        dosServidor.flush();
                        break;
                        
                    } else if(msg.equalsIgnoreCase("/hi")){
                    	ps.println(colors.BLUE + "BIENVENIDO AL SERVIDOR LOCAL" + colors.RESET);
                    	
                    } else if ( msg.equalsIgnoreCase("/verComandos") ) {
                        comandos();
                        
                    } else {
                        broadcastTexto(msg);
                    }//end if/else
                    
                    ps.print("\t->");
                }//end while
                
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    isConected = false;
                    if (dosServidor != null) dosServidor.close();
                    if (sock != null) sock.close();
                } catch (Exception ex) {}//end try/catch
            }//end try/catch/finally
        }, "ENVIO");//end enviarMensaje
        
        

        Thread recibirMensaje = new Thread(() -> {
            try {
                while (isConected) {
                    String token = disServidor.readUTF();
                    
                    if ("RESP::LIST".equals(token)) {
                        String lista = disServidor.readUTF();
                        ps.println(colors.YELLOW + "Usuarios: " + lista + colors.RESET);
                        
                    } else if ("RESP::NOTFOUND".equals(token)) {
                        String t = disServidor.readUTF();
                        ps.println(colors.BLUE + "Usuario no encontrado: " + t + colors.RESET);
                        
                    } else if ("INCOMING::PRIVATE".equals(token) || "INCOMING::BROADCAST".equals(token)) {
                        String from = disServidor.readUTF();
                        int ivLen = disServidor.readInt();
                        byte[] iv = new byte[ivLen];
                        disServidor.readFully(iv);
                        
                        int cipherLen = disServidor.readInt();
                        byte[] cipherBytes = new byte[cipherLen];
                        disServidor.readFully(cipherBytes);
                        String type = disServidor.readUTF();
                        String extra = disServidor.readUTF();
                        
                        byte[] plainBytes = sec.decriptarBytes(sharedKey, iv, cipherBytes);
                        if (plainBytes == null) {
                            ps.println(colors.BLUE + "Error al descifrar mensaje de " + from + colors.RESET);
                            
                        } else {
                            if ("TEXT".equals(type)) {
                                String plain = new String(plainBytes, java.nio.charset.StandardCharsets.UTF_8);
                                
                                if ("INCOMING::PRIVATE".equals(token)) {
                                    ps.println(colors.GREEN + "[PRIVADO de " + from + "] " + plain + colors.RESET);
                                } else {
                                    ps.println(colors.GREEN + "[" + from + "] " + plain + colors.RESET);
                                }//end if/else
                                
                            } else if ("FILE".equals(type)) {
                                byte[] fileBytes = plainBytes;
                                File dir = new File("recibidos");
                                
                                if (!dir.exists()) dir.mkdirs();
                                File out = new File(dir, extra == null ? "file" : extra);
                                int i = 1;
                                String filename = out.getName();
                                
                                while (out.exists()) {
                                    int dot = filename.lastIndexOf('.');
                                    String nameOnly = dot > 0 ? filename.substring(0, dot) : filename;
                                    String ext = dot > 0 ? filename.substring(dot) : "";
                                    out = new File(dir, nameOnly + "_" + i + ext);
                                    i++;
                                }//end while
                                
                                try (FileOutputStream fos = new FileOutputStream(out)) {
                                    fos.write(fileBytes);
                                    fos.flush();
                                }//end try
                                ps.println(colors.BLACK + "Archivo recibido de " + from + " -> " + out.getAbsolutePath() + colors.RESET);
                            }//end if/else if
                        }//end if/else
                        
                    } else {
                        ps.println(colors.BLACK + token + colors.RESET);
                    }//end if/else
                    ps.print("\t->");
                    
                }//end while
            } catch (Exception e) {
                try { if (sock != null) sock.close(); } catch (Exception ex) {}
            }//end try/catch
            
        }, "RECIBIR");//end recibirMensaje

        recibirMensaje.start();
        enviarMensaje.start();
    }//end Cliente
    
    
    private void comandos() {
        ps.println(colors.YELLOW + "Comandos disponibles:" + colors.RESET);
        ps.println("/hi - vuelve a enviar el mensaje de bienvenida");
        ps.println("/logout - desconectarse");
        ps.println("/verComandos - mostrar comandos");
    }//end comandos

    
    
    private void enviarMsgPrivado(String to, String texto) {
        try {
            byte[] iv = new byte[16];
            sr.nextBytes(iv);
            
            byte[] plain = texto.getBytes(java.nio.charset.StandardCharsets.UTF_8);
            byte[] cipher = sec.encriptarBytes(sharedKey, iv, plain);
            
            synchronized (dosServidor) {
                dosServidor.writeUTF("FORWARD::PRIVATE");
                dosServidor.writeUTF(to);
                dosServidor.writeInt(iv.length); dosServidor.write(iv);
                dosServidor.writeInt(cipher.length); dosServidor.write(cipher);
                dosServidor.writeUTF("TEXT");
                dosServidor.writeUTF("");
                dosServidor.flush();
            }//end synchronized
            
            ps.println(colors.BLACK + "Mensaje privado enviado a " + to + colors.RESET);
        } catch (Exception e) {
            ps.println(colors.BLUE + "Error al enviar mensaje: " + e.getMessage() + colors.RESET);
        }//end try/catch
    }//end enviarMsgPrivado

 

    private void broadcastTexto(String texto) {
        try {
            byte[] iv = new byte[16];
            sr.nextBytes(iv);
            byte[] plain = texto.getBytes(java.nio.charset.StandardCharsets.UTF_8);
            byte[] cipher = sec.encriptarBytes(sharedKey, iv, plain);
            
            synchronized (dosServidor) {
                dosServidor.writeUTF("FORWARD::BROADCAST");
                dosServidor.writeInt(iv.length); dosServidor.write(iv);
                dosServidor.writeInt(cipher.length); dosServidor.write(cipher);
                dosServidor.writeUTF("TEXT");
                dosServidor.writeUTF("");
                dosServidor.flush();
            }//end synchronized
            
            ps.println(colors.BLACK + "Mensaje difundido a todos." + colors.RESET);
        } catch (Exception e) {
            ps.println(colors.BLUE + "Error broadcast: " + e.getMessage() + colors.RESET);
        }//end try/catch
    }//end broadcastTexto
    
}//Cliente
