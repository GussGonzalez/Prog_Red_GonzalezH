package Guia1;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class UsandoSystem extends main{
	
	InputStreamReader isr = new InputStreamReader(System.in);
	PrintStream ps = new PrintStream(System.out);
	PrintStream  psErr = new PrintStream(System.err);
	
	
	public String leerLinea() {
		int linea;
		String sentence = "";
		
		try {
			
			while ( (linea = System.in.read()) != 13) { //mientras no se haya presionado enter
				sentence = sentence + (char)linea;
			}//end while
			
		} catch (IOException e) {
			
			// TODO Auto-generated catch block
			psErr.println("Error al leer la entrada. Intenta nuevamente.");
			
		}//end try/catch
		
		return sentence;
		
	}//end leerLinea
	
	
	
	public void ejercicio1A() {

		try {
			ps.println("\nIngresa el valor de una hora de trabajo:");
			
			String input = leerLinea();
			Float.parseFloat(input); //chequear que el valor ingresado sea un número
			
			Float valorH = Float.parseFloat(input);
			ps.println(valorH);
			
			
			ps.println("\nIngresa la cantidad de horas trabajadas:");
			
			input = leerLinea();
			Float.parseFloat(input); //chequear que el valor ingresado sea un número
				
			Float cantH = Float.parseFloat(input);
			ps.println(cantH);
			
			
			//Resultado
			Float sueldoBruto = valorH * cantH;
			ps.println("El sueldo bruto es " + String.valueOf(sueldoBruto));
				
				
		}
		catch(NumberFormatException e){ //en caso de no serlo, tira error
			psErr.println("Valor no válido. Ingresa un número.");
		}//end try/catch
		
		
	}

}
