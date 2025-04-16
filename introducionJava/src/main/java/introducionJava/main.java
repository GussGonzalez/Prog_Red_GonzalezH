package introducionJava;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class main {

	public static void main(String[] args) {
		/*
		System.out.print();   //manda el dato sin enter
		System.out.println();  // manda un enter al final
		System.out.write();  //byte
		System.out.printf("texto", var , var2 , var3); // concatenar datos
		//  "texto" + var + "otro texto" + var2     <- concatener datos
		
		System.err;
		*/
		
		PrintStream ps = new PrintStream(System.out); 
		PrintStream psErr = new PrintStream(System.err);
		
		ps.println("estamos todo bien");
		

		try {
			/*
			int linea;
			String palabra="";
			while( (linea = System.in.read())  !=  13  )
			{
				palabra = palabra + (char)linea; 
			}
			*/
			/*
			 * %s   String
			 * %n   int
			 * %f   float
			 * %d   double
			 * %b   boolean
			 
			ps.printf( "La variable palabra:%s \n" , palabra );
			*/
			
			InputStreamReader isr = new InputStreamReader( System.in );		
			BufferedReader br = new BufferedReader( isr ); //equivalente al PrintStream
			//BufferedReader br2 = new BufferedReader(   new InputStreamReader(System.in) );

			/*
			int linea2 = 0;
			String palabra2="";
			while( (linea2 = br.read())  !=  13  )
			{
				palabra2 = palabra2 + (char)linea2; 
			}
			*/
			ps.printf( "Con readline:%s \n" , br.readLine() ); //lee toda la linea completa
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		psErr.println("Esto es un error");
	}

}
