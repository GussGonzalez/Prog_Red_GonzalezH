package Guia1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class UsandoReader {
	//setup
			InputStreamReader isr = new InputStreamReader(System.in);
			PrintStream ps = new PrintStream(System.out);
			PrintStream  psErr = new PrintStream(System.err);
			BufferedReader br = new BufferedReader (isr);
	
	public void ejercicio2A() {
		try {
			if (System.in.available() > 0) {
			    br.readLine(); // flush leftover line
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//end try/catch

		
		String[] apellidos = new String[3];
		char ch = 0;
		int index = 0;
		String apellido = "";
		
		try {
			ps.println("Ingrese tres apellidos separados por un espacio: ");
			apellido = br.readLine();
			ps.println(apellido);
			
			if (apellido != null && !apellido.isEmpty()) {
				
				for (int i = 0 ; i < apellido.length(); i++) {
					ch = apellido.charAt(i);
					if (ch != 32) {
						apellidos[index] = apellidos[index] + String.valueOf(ch);
					}else if (index > 2){
						index +=1;
					}//end if/else
					ps.println(apellidos);
				}//end for
				
            } else {
                psErr.println("Por favor, ingrese un valor válido.");
            }//end if/else de verificación


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//end try/catch
	}//end ejercicio2A

}//end UsandoReader
