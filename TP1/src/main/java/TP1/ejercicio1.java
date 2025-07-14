package TP1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;



public class ejercicio1 {
	
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
			e.printStackTrace();
		}//end try/catch
	}//end vaciar
	
	
	
	
	public void solution(int[] vector1, int[] vector2) {
		vaciar();
		int count = 0;
		
		
		
		File numsF = new File("..\\TP1\\numsF.txt");
		PrintStream psf;
		
		try {
			if ( !numsF.exists()) {
				psf = new PrintStream(  new FileOutputStream(numsF)  );
				psf.println("4\n2\n0\n5\n0"); // 4  2  0  5  0
					
				psf.flush();
				psf.close();
					
				ps.println("Archivo creado exitosamente!");
			}//end if
			
			FileReader fr = new FileReader( numsF );
			BufferedReader brf = new BufferedReader( fr );
			String aux;
			int i2 = 0;
			while(  (aux=brf.readLine()) != null  ) {
				vector1[i2] = Integer.parseInt( aux );
				i2 += 1;
			}//end while
			brf.close();
			fr.close();
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			psErr.println("Ha ocurrido un error al crear el archivo, intente reiniciando el programa.");
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//end try/catch
			
		
		
		
		ps.println("Por favor ingrese 5 números separados por un salto de línea. Deben haber mínimo dos ceros(0) ingresados.");
		try {
					
			for (int i = 0 ; i<5 ; i++) {
				vector2[i] = Integer.parseInt(br.readLine());
				vaciar();
				
				if (vector2[i] == 0) {
					count += 1;
				}
			}//end for
			
			if (count < 2) {
				psErr.println("No se han detectado suficientes ceros. Por favor vuelva a inentarlo y lea las intrucciones con atención.");
					vector2 = (int[]) null;
			}else{
				ps.println("Los datos se han guardado correctamente. Ya puede proceder al ejercicio 2.");
			}//end if/else
			
			
		}catch (NumberFormatException | IOException e) {
			psErr.println("Ha ocurrido un error. Intente ingresar números sin espacios ni caracteres adicionales.");
		}//end try/catch
		
		vaciar();
		
		/*
		for (int i = 0 ; i<5 ; i++) {
			ps.println(vector1[i]);
			ps.println(vector2[i]);
		}//end test for
		*/
	}//end solution
}//end ejercicio1
