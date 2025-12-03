package TP2;

import java.io.IOException;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class subMain {
	PrintStream ps = new PrintStream(System.out);
	File Inventario = new File("..\\TP2\\Inventario.dat");
	PrintStream psf;
	PrintStream psfb;
	
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
	
	
	
	public void crearInventario(File Archivo) {
		if ( !Archivo.exists() ) {
			try {
				psf = new PrintStream(  new FileOutputStream(Archivo)  );
				psf.flush();
				psf.close();
			} catch (FileNotFoundException e) {
				Logger.getLogger(subMain.class.getName()).log(Level.WARNING, null, e);
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
		crearInventario(Inventario);
			File backupI = new File("..\\TP2\\backup.dat");
			crearInventario(backupI);
			List<String> data = leerArchivo(Inventario);
			
			for( int i=0 ; i<=data.size() ; i++ ){	
				psfb.println( data.get(i) );
			}
			psfb.println( pedirDatos() );
			psfb.close();
			
			Inventario.delete();
			crearInventario(Inventario);
			data = leerArchivo(backupI);
			for( int i=0 ; i<=data.size() ; i++ ){	
				psf.println( data.get(i) );
			}
			psf.close();
			

	}//agregarAlInventario
	
	public List<String> leerArchivo(File file) {
		
		List<String> textoCompleto = new LinkedList<>();
		
		//Leer archivo y volcar los datos en memoria VOLATIL (un array)
		try(BufferedReader brf = new BufferedReader(new FileReader(file)))
		{
			String lineas = ""; String EOF = null;
			while( (lineas = brf.readLine()) != EOF )
			{
				//puede tener logica para filtrar qué entra al Array o no
				ps.println(lineas);
				textoCompleto.add(lineas);
			}//end while
			
			return textoCompleto;
			
		} catch (IOException e) {
			ps.println(colors.RED + "El archivo no fue encontrado, no existe o está vacío" + colors.RESET);
			Logger.getLogger(subMain.class.getName()).log(Level.WARNING, null, e);
		}//end try/catch/finally
		return null;
		
	}//leerArchivo
	
	
	/* archivo es Inventario
	 * producto es el producto a editar o eliminar
	 * eliminar si es true, editar si es false
	 */
	public void editarArchivo(File archivo, String producto, Boolean eliminar) {
		File backupI = new File("..\\TP2\\backup.dat");
		crearInventario(backupI);
		List<String> data = leerArchivo(Inventario);
		
		for( int i=0 ; i<=data.size() ; i++ ){	
			psfb.println( data.get(i) );
		}
		psfb.println( pedirDatos() );
		psfb.close();
		
		data = leerArchivo(backupI);
		for( int i=0 ; i<=data.size() ; i++ ){	
			psf.println( data.get(i) );
		}
		psf.close();
		
		
		if (!eliminar) {
			//editar producto
		}else {
			//eliminar producto
		}//end if/else
		
	}//editarArchivo
	
	public void modificarArchivoTemporalLinea(File archivoOriginal, String buscar, String reemplazar)  {
		File archTemp = new File( archivoOriginal.getAbsolutePath() + ".tmp" );
		
		try (
			BufferedReader br = new BufferedReader( new FileReader(archivoOriginal) );
			BufferedWriter bw = new BufferedWriter( new FileWriter(archTemp) );
			)
		{
			if (buscar == null || buscar == "" || reemplazar == null || reemplazar == "") {
				
				
			}else if() {
			
				String linea = ""; String EOF = null;
				while( (linea = br.readLine()) != EOF )
				{
					//la edicion necesaria
					if( linea.contains(buscar) )
					{
						linea = linea.replace(buscar, reemplazar);
					}//end if
				
					bw.write(linea);
					bw.newLine();
				}//end while
			
			}//end if/else if/else
		if( !archivoOriginal.delete() )
			throw new IOException("No se pudo borrar el archivo original");  
			
		if( !archTemp.renameTo(archivoOriginal) )
			throw new IOException("No se pudo renombrar el archivo temporal.");
		}catch (Exception e) {
			Logger.getLogger(subMain.class.getName()).log(Level.WARNING, null, e);
		}//end try/catch/throws
	}//end modificarArchivoTemporalLinea
	
	
}//subMain
