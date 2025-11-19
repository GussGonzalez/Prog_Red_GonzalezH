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
	PrintStream ps = new PrintStream(System.out);
    DataInputStream disServidor = null;
    DataOutputStream dosServidor = null;
    BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
    InetAddress IP = null;
    int puerto = 7777;
    Socket sock = null;
    boolean isConected = false;
    private String sharedKey = "";
    private SecureRandom sr = Utils.sr;
    
    
}//Cliente
