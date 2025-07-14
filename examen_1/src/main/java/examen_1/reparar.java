package examen_1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

public class reparar {
	
	public reparar(){
		PrintStream ps = new PrintStream(System.out);
		
		File datos = new File ("..\\examen_1\\datos.dat");
		
		try {
			BufferedReader brF = new BufferedReader( new FileReader(datos) );
			
			if ( datos.exists() ) {
				File newDatos = new File("..\\ecamen_1\\datos.CSV");
				while( (brF.readLine()) != null ) {
					
				}//end while
			}//end if
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			ps.println(colors.RED + "Ha ocurrido un error encontrando al archivo, por favor ejecute de nuevo el programa." + colors.RESET);
			System.exit(0); // Termina completamente el código
		} catch (IOException e) {
			// TODO Auto-generated catch block
			ps.println(colors.RED + "Ha ocurrido un error encontrando al archivo, por favor ejecute de nuevo el programa." + colors.RESET);
			System.exit(0); // Termina completamente el código
		}

	}//end reparar
	

}//end reparar
