package TP2;

import java.io.IOException;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class subMain {
	PrintStream ps = new PrintStream(System.out);
	File Inventario = new File("..\\TP2\\Inventario.dat");
	PrintStream psf;
	
	String prodName;
	Float compra;
	Float venta;
	Integer stock;
	
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
			answer = "null";
			
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
		
		ps.println(answer);
		return answer;
	}//esNumero
	
	
	
	public int convertNumI(String txt, String txtEs) {
		if (txt != null || txtEs == "Integer") {
			return Integer.parseInt(txt);
		}else{
			ps.println(colors.RED + "Entrada de datos incorrecta o no válida. No se puede convertir en un número\n" + colors.RESET);
			return 0;
		}//end if/else
	}//convertNumI(nteger)
	
	
	public Float convertNumF(String txt, String txtEs) {
		if (txt != null || txtEs == "Float") {
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
				ps.println(colors.RED + "\n No se pudo crear el archivo, por favor intente de nuevo. \n" + colors.RESET);
			}//end try/catch
		}//end if/else
	}//crearInventario
	
	
	
	public String pedirDatos() {
		Boolean esDatType = false;
		String datos;
		String temporal;
		
		ps.println("\n --- --- 「  Por favor ingrese los datos pedidos a continuación.  」 --- --- \n");
		
		ps.println(colors.YELLOW + "Nombre del producto: " + colors.RESET);
		prodName = leerConsola();
		datos = prodName + ";";
		
		
		ps.println(colors.YELLOW + "Precio de compra (por favor ingresar un numero con , o .): " + colors.RESET);
		while ( esDatType == false) {
			if (  textoEs( temporal = leerConsola() ) == "Float" ) { //si no funciona es porque no está tomando bien el float, hacer un println con textoes(
				compra = convertNumF(temporal, textoEs(temporal));
				datos += compra + ";";
				esDatType = true;
			}else{
				ps.println(colors.RED + "\n Valor no válido, por favor revise que sus datos tengan el formato correcto e intente de nuevo." + colors.RESET);
			}//end if/else
		}//end while
		esDatType = false;
		
		
		ps.println(colors.YELLOW + "Precio de venta (por favor ingresar un numero con , o .): " + colors.RESET);
		while ( esDatType == false) {
			if (  textoEs( temporal = leerConsola() ) == "Float" ) {
				venta = convertNumF(temporal, textoEs(temporal));
				datos += venta + ";";
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
				datos += stock;
				esDatType = true;
			}else{
				ps.println(colors.RED + "\n Valor no válido, por favor revise que sus datos tengan el formato correcto e intente de nuevo." + colors.RESET);
			}//end if/else
		}//end while
		
		return datos;
	}//pedirDatos
	
	
	
	public void agregarAlInventario() {
		if ( !Inventario.exists() ) { 
			crearInventario(); 
			psf.println( pedirDatos() );
			psf.flush();
			psf.close();
		}else{
			
		}//end if/else
	}//agregarAlInventario
	
	//revisar, es literalmente lo que está en el Git de Consor
	public String leerArchivo(File file) {
		FileReader fr = null;
		BufferedReader br = null;

		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);

			String line = "";
			String texto = "";
			
			while ((line = br.readLine()) != null) {		
				texto = texto.concat(line);
			}//end while
			return texto;
			
		} catch (FileNotFoundException e) {
			Logger.getLogger(subMain.class.getName()).log(Level.WARNING, null, e);
			ps.println(colors.RED + "No se encontró el archivo" + colors.RESET);
		} catch (IOException e) {
			Logger.getLogger(subMain.class.getName()).log(Level.WARNING, null, e);
			ps.println(colors.RED + "Ha ocurrido un error" + colors.RESET);
		} finally {
			try {
				if (fr != null)
					fr.close();
				if (br != null)
					br.close();
			} catch (IOException e) {
				Logger.getLogger(subMain.class.getName()).log(Level.WARNING, null, e);
				ps.println(colors.RED + "Ha ocurrido un error" + colors.RESET);
			}//end finally try/catch
		}//end try/catch/finally
		return null;
		
	}//leerArchivo
	
	
}//subMain
