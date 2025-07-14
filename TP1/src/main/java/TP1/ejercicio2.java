package TP1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.logging.Logger;
import java.util.logging.Level;



public class ejercicio2 {
	
	PrintStream ps = new PrintStream(System.out);
	PrintStream  psErr = new PrintStream(System.err);
	
	//métodos extra
	private void cargarVectores(int[] vector, PrintStream print) {
		int resta = 0;
		int div = 0;
		
		for (int i = 0; i<4 ; i++) {
			resta = vector[i+1] - 3;
			div = vector[i] / resta;
			print.append("\n" + vector[i] + "/" + resta + " = " + div);
		}//end for
		
		resta = vector[0] - 3;
		div = vector[5] / resta;
		print.append("\n" + vector[5] + "/" + resta + " = " + div);
	}//end cargarVectores
	
	
	public void solution(int[] vector1, int[] vector2) {
		
		File resF = new File("..\\TP1\\resultados.txt");
		PrintStream psf;
		
		File errF = new File("..\\TP1\\errores.txt");
		PrintStream psErr;
		
			try {
				if ( !resF.exists()) {
					psf = new PrintStream( new FileOutputStream(resF, true) );
					psf.append("Vector 1");
					cargarVectores(vector1, psf);
					
					psf.append("");
					psf.append("\nVector2");
					cargarVectores(vector2, psf);
					
					psf.flush();
					psf.close();
						
					ps.println("Archivos creados exitosamente!");
				}else{
					resF.delete();
					
					psf = new PrintStream( new FileOutputStream(resF, true) );
					psf.append("Vector 1");
					cargarVectores(vector1, psf);
					
					psf.append("");
					psf.append("\nVector2");
					cargarVectores(vector2, psf);
					
					psf.flush();
					psf.close();
						
					ps.println("Archivos creados exitosamente!");
				}//end if/else
				
				
			} catch (FileNotFoundException e) {
				try {
					psErr = new PrintStream( new FileOutputStream(errF, true) );
					psErr.append(vector1[i] + "/" + resta + " = ");
					psErr.append(Logger.getLogger(ejercicio2.class.getName()).log(Level.WARNING, null, e));
					psErr.append("");
					
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}//end try/catch
		
	}//end solution
	
	
}//end ejercicio2
