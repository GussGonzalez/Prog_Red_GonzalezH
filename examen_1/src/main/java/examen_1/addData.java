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
			BufferedReader brF = new BufferedReader (new FileReader (datos));
			String linea = "";
			
			ps.println(colors.YELLOW + "Ingrese una nueva letra para jugar!" + colors.RESET);
			String nuevaLetra = br.readLine();
			
			while( (linea = brF.readLine()) != null) {
				
				
			}
			
		} catch (FileNotFoundException e) {
			ps.println(colors.RED + "Ha ocurrido un error encontrando al archivo, por favor intente nuevamente" + colors.RESET);
		} catch (IOException e) {
			ps.println(colors.RED + "Ha ocurrido un error en la entrada de datos, por favor intente de nuevo" + colors.RESET);
		}//end try/catch
	}
}
