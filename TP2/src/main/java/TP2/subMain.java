package TP2;

import java.io.IOException;
import java.io.PrintStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;

public class subMain {
	PrintStream ps = new PrintStream(System.out);
	File Inventario = new File("..\\TP2\\Inventario.dat");
	PrintStream psf;
	
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
				ps.println(colors.RED + "Error al leer la entrada. Intenta nuevamente.\n" + colors.RESET);
			}//end try/catch
			
			return input;
	}//leerConsola
	
	
	
	public String textoEs(String txt) {
		String answer = "String"; 
		
		if (txt == "") {
			ps.println(colors.RED + "Input vacío, no válido \n" + colors.RESET);
			
		}else {
			try {
				Integer.parseInt(txt);
				answer = "Integer";
				
			}catch(NumberFormatException e1) {
				try {
	                // Intentar convertir a decimal
	                Float.parseFloat(txt.replace(',', '.'));
	                answer = "Float";
	                
				} catch (NumberFormatException e2) { }//end try/catch
			}//end try/catch
		}//end if/else
		
		return answer;
	}//esNumero
	
	
	
	public int convertNumI(String txt, String txtEs) {
		if (txt != null || txtEs != "String") {
				return Integer.parseInt(txt);
		}else{
			ps.println(colors.RED + "Entrada de datos incorrecta o no válida. No se puede convertir en un número\n" + colors.RESET);
			return 0;
		}//end if/else
	}//convertNumI(nteger)
	
	
	public Float convertNumF(String txt, String txtEs) {
		if (txt != null || txtEs != "String") {
				return Float.parseFloat(txt.replace(',', '.'));
		}else{
			ps.println(colors.RED + "Entrada de datos incorrecta o no válida. No se puede convertir en un número\n" + colors.RESET);
			return null;
		}//end if/else
	}//convertNumF(loat)
	
	
	
	public void crearInventario() {
		if ( !Inventario.exists() ) {
			try {
				psf = new PrintStream(  new FileOutputStream(Inventario)  );
				psf.flush();
				psf.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				ps.println(colors.RED + "\n No se pudo crear el archivo, porf favor intente de nuevo. \n" + colors.RESET);
			}//end try/catch
		}//end if/else
	}//crearInventario
	
	
	
	public void pedirDatos(String prodName, Float compra, Float venta, Integer stock) {
		Boolean esDatType = false;
		String temporal;
		
		ps.println("\n --- --- 「  Por favor ingrese los datos pedidos a continuación.  」 --- --- \n");
		
		ps.println(colors.YELLOW + "Nombre del producto: " + colors.RESET);
		prodName = leerConsola();
		
		
		ps.println("Precio de compra: " + colors.RESET);
		while ( esDatType == false) {
			if (  textoEs( temporal = leerConsola() ) == "Float" ) {
				compra = convertNumF(temporal, textoEs(temporal));
				esDatType = true;
			}else{
				ps.println(colors.RED + "\n Valor no válido, por favor revise que sus datos tengan el formato correcto e intente de nuevo." + colors.RESET);
			}//end if/else
		}//end while
		esDatType = false;
		
		
		ps.println(colors.YELLOW + "Precio de venta: " + colors.RESET);
		while ( esDatType == false) {
			if (  textoEs( temporal = leerConsola() ) == "Float" ) {
				venta = convertNumF(temporal, textoEs(temporal));
				esDatType = true;
			}else{
				ps.println(colors.RED + "\n Valor no válido, por favor revise que sus datos tengan el formato correcto e intente de nuevo." + colors.RESET);
			}//end if/else
		}//end while
		esDatType = false;
		
		
		ps.println(colors.YELLOW + "Stock: " + colors.RESET);
		while ( esDatType == false) {
			if (  textoEs( temporal = leerConsola() ) == "Integer" ) {
				stock = convertNumI(temporal, textoEs(temporal));
				esDatType = true;
			}else{
				ps.println(colors.RED + "\n Valor no válido, por favor revise que sus datos tengan el formato correcto e intente de nuevo." + colors.RESET);
			}//end if/else
		}//end while
	}//pedirDatos
	
	
	
	public void agregarAlInventario(String prodName, Float compra, Float venta, Integer stock) {
		if ( !Inventario.exists() ) {
			crearInventario();
		}//end if/else
		
		psf = new PrintStream(  new FileOutputStream(Inventario)  );
		psf.flush();
		psf.close();
	}//agregarAlInventario
	
}//subMain
