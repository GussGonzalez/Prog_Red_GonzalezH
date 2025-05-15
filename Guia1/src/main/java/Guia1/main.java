package Guia1;

import java.io.IOException;
//import java.io.InputStreamReader;
import java.io.PrintStream;

public class main {
	
	//public static final String ANSI_PINK = "\u001b[38;5;218m"; //para color

	public static void main(String[] args) {
		
		//setup
		//InputStreamReader isr = new InputStreamReader(System.in);
		PrintStream ps = new PrintStream(System.out);
		PrintStream  psErr = new PrintStream(System.err);
		
		
		UsandoSystem punto1 = new UsandoSystem();
		UsandoReader punto2 = new UsandoReader();
		
		//punto1.ejercicio1A();
		
		//loop, menu infinito
		while( true )
		{
		
            int opcion = -1; //lo que elige el usuario del menu
            int linea;
            String temporal = "";            
			//Menu en si
			//ps.println(ANSI_PINK.concat("       「 ✦ Menu ✦ 」       ")); //con color
			ps.println("\n --- --- 「 ✦ Menu ✦ 」 --- --- ");
			
			ps.println("   1.  	(Ejercicio 1-a)");
            ps.println("   2.  	(Ejercicio 1-b)");
            ps.println("   3.  	(Ejercicio 1-c)");
            ps.println("   4.  	(Ejercicio 1-d)");
            ps.println("   5.  	(Ejercicio 1-e)");
            ps.println("   6.  	(Ejercicio 1-f)");
            ps.println("   7.  	(Ejercicio 1-g)");
            ps.println("   8.  	(Ejercicio 2-a)");
            ps.println("   9.  	(Ejercicio 2-b)");
            ps.println("   10. 	(Ejercicio 2-c)");
            ps.println("   11. 	(Ejercicio 2-d)");
            ps.println("   12. 	(Ejercicio 2-e)");
            ps.println("   13. 	(Ejercicio 2-f)");
            ps.println("   14. 	(Ejercicio 2-g)");
            ps.println("   15. 	(Ejercicio 2-h)");
            ps.println("   0.  	(Salir)");
            ps.print("¡ Ingresa el número del ejercicio que se desea ejecutar ! : ");
            
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
                        punto1.ejercicio1A();
                        break;
                    case 2:
                        punto1.ejercicio1B();
                        break;
                    case 3:
                        punto1.ejercicio1C();
                        break;
                    case 4:
                        punto1.ejercicio1D();
                        break;
                    case 5:
                        punto1.ejercicio1E();
                        break;
                    case 6:
                        punto1.ejercicio1F();
                        break;
                    case 7:
                        punto1.ejercicio1G();
                        break;
                    case 8:
                        punto2.ejercicio2A();
                        break;
                    case 9:
                        punto2.ejercicio2B();
                        break;
                    case 10:
                        punto2.ejercicio2C();
                        break;
                    case 11:
                        punto2.ejercicio2D();
                        break;
                    case 12:
                        punto2.ejercicio2F();
                        break;
                    case 13:
                        punto2.ejercicio2G();
                        break;
                    case 14:
                        punto2.ejercicio2H();
                        break;

                    case 0:
                        ps.println("¡Saliendo del programa! Gracias por probar :3");
                        break;
                    default:
                        ps.println("Opción inválida. Por favor, ingrese un número del menú.");
                }//end switch

					if (opcion == 0) {
                    break; 
					} 
                
                
				}
				catch(NumberFormatException e){ //en caso de no serlo, tira error
					psErr.println("Valor no válido. Ingresa un número.");
					continue;
				}//end try/catch
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				psErr.println("Error al leer la entrada. Intenta nuevamente.");
			}//end try/catch
            
		}//end while menu

		System.exit(0); // Termina completamente el código
	} //end objeto main

}//end clase main
