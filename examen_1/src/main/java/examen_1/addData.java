package examen_1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class addData {
	
	InputStreamReader isr = new InputStreamReader(System.in);
	PrintStream ps = new PrintStream(System.out);
	PrintStream  psErr = new PrintStream(System.err);
	BufferedReader br = new BufferedReader (isr);
	
	//métodos extra
	private void vaciar() {
		try {
			if (System.in.available() > 0) {
			    br.readLine(); // flush leftover line
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			ps.println(colors.RED + "Ha ocurrido un error al vaciar los datos, por favor intente de nuevo" + colors.RESET);
		}//end try/catch
	}//end vaciar
	
	
	
	public void addingData() {
		File datos = new File("..\\examen_1\\datos.CSV");
		
		try {
			ps.println(colors.YELLOW + "Ingrese una nueva letra para jugar!" + colors.RESET);
			String nuevaLetra = br.readLine();
			vaciar();
			
			BufferedReader brF = new BufferedReader (new FileReader (datos));
			
			while ( !verifLetra(nuevaLetra, brF ) ) {
				ps.println(colors.RED + "Dato no válido. Por favor ingrese una única letra" + colors.RESET);
			}//end while
			
			brF.close();
			PrintStream psF = new PrintStream ( new FileOutputStream(datos) );
			
			for (int i = 0; i<5 ; i++) {
				while (verifPalabra(br.readLine(), nuevaLetra)){
					ps.println(colors.YELLOW + "Dato no válido. Por favor ingrese una palabra cuya inicial coincida con la letra ingresada anteriormente." + colors.RESET);
				}//end while
			}//end for
			
		} catch (FileNotFoundException e) {
			ps.println(colors.RED + "Ha ocurrido un error encontrando al archivo, por favor intente nuevamente" + colors.RESET);
		} catch (IOException e) {
			ps.println(colors.RED + "Ha ocurrido un error en la entrada de datos, por favor intente de nuevo" + colors.RESET);
		}//end try/catch
	}//end adding data
	
	
	
	private boolean verifLetra(String newInitial, BufferedReader brF) {
		boolean valido = true;
		String linea = "";
		
		if ( String.valueOf(newInitial.charAt(1)) != null) { //Que sea un único caracter y que se pueda convertir a un String
			valido = false;
		}else {
			try {
				while( (linea = brF.readLine()) != null) {
					
					if (newInitial == String.valueOf( linea.charAt(0) )) {//si la nueva letra es igual a la inicial
						valido = false;
					}//end if
				}//end while
				
			} catch (IOException e) {
				ps.println(colors.RED + "Ha ocurrido un error en la lectura de datos, por favor intente de nuevo" + colors.RESET);
			}//end try/catch
		}//end if/else
		
		return valido;
	}//end verifLetra
	
	
	private boolean verifPalabra(String palabra, String inicial) {
		boolean valido = true;
		String i = String.valueOf(palabra.charAt(0));

		if( i != inicial || i == null){
			valido = false;
		}//end if/else
		
		return valido;
	}
}
