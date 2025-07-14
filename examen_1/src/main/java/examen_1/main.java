package examen_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class main {

	public static void main(String[] args) {
		
		//imports
		InputStreamReader isr = new InputStreamReader(System.in);
		PrintStream ps = new PrintStream(System.out);
		BufferedReader br = new BufferedReader(isr);
		
		addData agregar = new addData();
		
		new reparar();
		
		while (true) {
			int opcion = -1; //lo que elige el usuario del menu
            int linea;
            String temporal = "";
            
            mostrarMenuPrincipal(ps);            
            try {
				while ( (linea = System.in.read()) != 13) { //mientras no se haya presionado enter
                    if(linea != 10)//si no es un LF (line feed), básicamente no toma el byte extra de salto de línea que se estaba guardando
					    temporal = temporal + (char)linea;
				}//end while 
				
				try {
					Integer.parseInt(String.valueOf((String)temporal)); //chequear que el valor ingresado sea un número
					
					opcion = Integer.parseInt(temporal);
					
					switch (opcion) {
	                case 1:
						agregar.addingData();
	                    break;
	                case 2:
						//eraseData();
	                    break;
	                case 3:
	                	//showData();
	                	break;
	                case 0:
	                    System.out.println(colors.GREEN + "¡Hasta luego!" + colors.RESET);
	                    return;
	                default:
	                    System.out.println(colors.RED + "Opción no válida. Intente nuevamente." + colors.RESET);
					}//end swtich

					if (opcion == 0) {
                    break; 
					}//end if
                
                
				} catch(NumberFormatException e){ //en caso de no serlo, tira error
					ps.println(colors.RED + "Valor no válido. Ingresa un número." + colors.RESET);
					continue;
				}//end try/catch
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				ps.println(colors.RED + "Error al leer la entrada. Intenta nuevamente." + colors.RESET);
			}//end try/catch

        }//end while
		
		System.exit(0); // Termina completamente el código
    }//end main

    private static void mostrarMenuPrincipal(PrintStream ps) {        
        ps.println("\n --- --- 「 ✦ Menu ✦ 」 --- --- ");
		
		ps.println(colors.YELLOW + "   1.  	(Agregar datos nuevos)");
        ps.println("   2.  	(Eliminar datos)");
        ps.println("   3.  	(Mostrar datos)");
        ps.println("   0.  	(Salir)" + colors.RESET);
        ps.print("¡ Ingresa el número del ejercicio que desee ejecutar ! : ");
    }//end mostrarMenuPrincipal
}//main
