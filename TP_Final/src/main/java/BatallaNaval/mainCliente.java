package BatallaNaval;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class mainCliente {
	Utils.colors Colors;
	InputStreamReader isr = new InputStreamReader(System.in);
	PrintStream ps = new PrintStream(System.out);
	BufferedReader br = new BufferedReader(isr);
	
	public static void main(String[] args) {
		new Cliente().iniciar();
	}//end main
	
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

}//mainCliente
