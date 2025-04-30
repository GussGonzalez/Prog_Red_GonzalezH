package archivos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class files {
	
	PrintStream ps;
	File file;
	
	/**
	 * Constructor
	 */
	
//throw delega el trycatch a quien está llamando el método
	public void archivos(){
		ps = new PrintStream(System.out);
		
		//    "c:\\user\\Redes-04\\omg.txt"
		file = new File("omg.txt");//crea un nuevo archivo
		
		/*
		file.createNewFile();
		file.delete();
		file.deleteOnExit(); //borra el archivo una vez terminado
		file.exists();//para ver si el archivo está o no está
		file.getAbsoluteFile();//te devuelve el archivo directamente
		file.getName();
		file.getPath(); //devuelve la ruta de origen
		file.getParent();//devuelve el directorio
		file.getTotalSpace();//devuelve la cantidad de bytes que pesa un archivo
		file.isDirectory();//para saber si es una carpeta
		file.isHidden();//
		file.isDirectory();//
		file.isFile();//
		file.list();//devuelve un listado de strings
		file.listFiles();//devuelve 
		file.mkdir();//para crear carpetas
		file.renameTo(new File("waa.txt"));//cambia el nombre
		*/
		
		this.rutaFiles(file);//llama al método
		this.crearFileConPrintStreamEasy(file);
	}
	
	
	/**
	 * 
	 * JavaDoc
	 * 
	 * ESTE TEXTO NO TIENE NINGUNA ETIQUETA DE IDENTIFICACION. ESTO NO VA A
	 * AAPARECER :( Tambien se puede agregar referencias a class o methodos o
	 * atributos con la instruccion: {
	 *
	 * @ por ejemplo: {@code <html></html>} o usar {@link String}
	 *
	 * @param f Este metodo recibe un archivo.
	 * @see FlujoDeDatos.File.
	 * @since v1.0
	 * @exception
	 * @throws
	 * @return
	 */

	
	
	public void rutaFiles(File f)
	{
	}

	//con esto se puede empezar a escribir en el archivo
	public void crearFileConPrintStreamEasy(File f) {
		
		FileOutputStream fos = null;
		PrintStream fs = null;
		
		try {
			fos = new FileOutputStream( f );
			fs = new PrintStream(fos);
			
			fs.print("Una linea");
			fs.println("Nueva linea");
			fs.write( 'd' );//escribe
			fs.append( ("HAIII") );//es como un print, pero escribe en la posición del cursor
			fs.flush();//limpia todo lo que hay en el canal
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			
			//to import Logger use the /util/ one
			Logger.getLogger(files.class.getName()).log(Level.WARNING,null,e);
			
		}finally{ //se ejecuta independientemente si hubo o no un error
			try {
				if (fs != null)
					fs.close();
			
				if (fos != null)
						fos.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//end try/catch
		}//end try/catch/finally
		
	}//end crearFileConPrintStreamEasy
	
	public void crearFileConPrinter(File f) {
	}

	/**
	*
	* @param f
	*/
	public void crearFileConBuffer(File f) {
	}


	/**
	 * Descripcion
	 *
	 * @param f un archivo al leer
	 * @return Todo el texto leido.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public String LeerFileConBuffer(File f) throws FileNotFoundException, IOException {
	}

	public void leerFileCaracterAcaractet(File f) {
	}

}
