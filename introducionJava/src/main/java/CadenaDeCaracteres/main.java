package CadenaDeCaracteres;

import java.io.PrintStream;
import java.util.StringTokenizer;

public class main {

    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_MAGENTA = "\u0033[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";	
	
	public static void main(String[] args) {
		
		String palabra = "LinKevin";
		
		palabra.charAt( 5 ); //agarra un caracter x
		palabra.compareTo("Kevin"); //false compara texto completo
		palabra.compareToIgnoreCase("linkevin"); //true
		palabra.concat("/profile");  //agrege texto al FINAL
		palabra.contains("Ke");   // devuelve si existe el texto
		palabra.indexOf( '\n' ); //lo mismo qur charAt pero busca un caracter 
		palabra.indexOf("Ke"); //ubicacion de esa palabra devuelve 3
		palabra.length();  //largo total de lacadena
		palabra.lastIndexOf('i'); //ultima aparicion de un texto o caracter
		palabra.replace("Kevin", "Roman");
		palabra.replace("i", "X");  //  LXnKevin
		palabra.replaceAll("i", "x.X"); // Lx.XnKevx.Xn
		palabra.toString();
		palabra.valueOf(5); //tranfoma a texto  
		palabra.trim();//quita espacios en blanco adelante y al final
		palabra.toLowerCase();
		palabra.toUpperCase();
		
		palabra.toCharArray(); //conviete un string en un vector
		// ['L','i','n','K','e','v','i','n']
		palabra.split("K"); // vec[] => { "Lin" , "evin" } Lin evin
		palabra.substring( 2 , 4 ); //LinKevin  -> nKev 
		
		
		//conversion de Enteros a texto y viceversa
		//Tokenized
		// String = 75+9+63=10
		StringTokenizer st = new StringTokenizer( "75+9+36=10" , "\\+" );
		PrintStream ps = new PrintStream(System.out);
		
		//  ANSIcolor + texto + ANSIreset
		ps.println( ANSI_GREEN.concat( String.valueOf(st.countTokens() ).concat(ANSI_RESET) )  ); // 3
		
		while( st.hasMoreTokens() )
		{
			ps.println( st.nextToken() );   // el texto en si como STRING
		}
		
		//ps.println( st.nextToken() ); 
		//ps.println( st.nextToken() ); 
		//ps.println( st.nextToken() ); 
		//ps.println( st.nextElement() ); // objeto representato con STRING
		
		int num = Integer.parseInt("9");
		
	}

}
