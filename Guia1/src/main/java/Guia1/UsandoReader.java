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
		String apellidos [] = null;
		char ch = 0;
		int secondI = -1;
		
		try {
			ps.println("Ingrese tres apellidos separados por un espacio: ");
			String apellido = br.readLine();
			
			if (apellido != null && !apellido.isEmpty()) {
            } else {
                psErr.println("Por favor, ingrese una opción válida.");
            }
			
			/*
			for (int i = 0 ; i < apellido.length(); i++) {
				ch = apellido.charAt(i);
				if (ch != 32) {
					apellidos[secondI] = apellidos[secondI] + String.valueOf(ch);
				}else {
					secondI +=1;
				}
				ps.println(apellidos);
			}
			*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//end ejercicio2A

}//end UsandoReader
