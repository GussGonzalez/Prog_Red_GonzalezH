package Guia1;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class UsandoSystem extends main{
	//setup
		InputStreamReader isr = new InputStreamReader(System.in);
		PrintStream ps = new PrintStream(System.out);
		PrintStream  psErr = new PrintStream(System.err);
	
		//método para leer líneas
		public String leerLinea() {
			int linea;
			String sentence = "";
		
			try {
			
				while ( (linea = System.in.read()) != 13) { //mientras no se haya presionado enter
					if(linea != 10)//si no es un LF (line feed), básicamente no toma el byte extra de salto de línea que se estaba guardando
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
			ps.println("\nIngresa el valor de una hora de trabajo: ");
			
			String input = leerLinea();
			Float.parseFloat(input); //chequear que el valor ingresado sea un número
			
			Float valorH = Float.parseFloat(input);
			ps.println(valorH);
			
			
			ps.println("\nIngresa la cantidad de horas trabajadas: ");
			
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
		
	}//end ejercicio1A
	
	
	public void ejercicio1B() {
		
		try {
			ps.println("\nIngresa uno de los ángulos de un triángulo: ");
			
			String input = leerLinea();
			Integer.parseInt(input); //chequear que el valor ingresado sea un número
			
			int ang1 = Integer.parseInt(input);
			ps.println(ang1);
			
			
			ps.println("\nIngresa otro de los ángulos del mismo triángulo: ");
			
			input = leerLinea();
			Integer.parseInt(input); //chequear que el valor ingresado sea un número
			
			Float ang2 = Float.parseFloat(input);
			ps.println(ang2);
			
			
			if ( ang1 >= 180   ||   ang1 >= 90 && ang2>=(180 - ang1)  ||   ang2>=180   ||  ang2 >= 90 && ang1>=(180 - ang2)   ||  180 - (ang1+ang2) > 0 ) {//verifica que este triángulo sea posible
				psErr.println("Ángulos no válidos. Por favor ingrese ángulos de un triángulo real.");
			}else {
				//Resultado
				float ang3;
				
				ang3 = 180 - (ang1+ang2);
				ps.println("El tercer ángulo de este triángulo es: " + String.valueOf(ang3));
				
			}//end else/if
				
				
		}
		catch(NumberFormatException e){ //en caso de no serlo, tira error
			psErr.println("Valor no válido. Ingresa un número.");
		}//end try/catch
		
	}//end ejercicio1B
	
	
	public void ejercicio1C() {
		try {
			ps.println("\nIngresa el área o superficie de tu cuadrado en metros cuadrados: ");
			
			String input = leerLinea();
			Float.parseFloat(input); //chequear que el valor ingresado sea un número
			
			Float area = Float.parseFloat(input);
			ps.println(area);
			
			//Resultado
			double lados = Math.sqrt(area); //calcula la raíz cuadrada del área
			double perimetro = lados*4;
			ps.println("El perímetro de este cuadrado es: " + String.valueOf(perimetro));
				
				
		}
		catch(NumberFormatException e){ //en caso de no serlo, tira error
			psErr.println("Valor no válido. Ingresa un número.");
		}//end try/catch
		
	}//end ejercicio1C
	
	
	public void ejercicio1D() {
		try {
			ps.println("\nIngresa la temperatura en °Fahrenheit: ");
			
			String input = leerLinea();
			Float.parseFloat(input); //chequear que el valor ingresado sea un número
			
			Float tempF = Float.parseFloat(input);
			ps.println(tempF);
			
			//Resultado
			double tempC = (tempF - 32) * 5/9;
			ps.println("La temperatura en °Celcius es: °" + String.valueOf(Math.round(tempC)));
				
				
		}
		catch(NumberFormatException e){ //en caso de no serlo, tira error
			psErr.println("Valor no válido. Ingresa un número.");
		}//end try/catch
		
	}//end ejercicio1D
	
	
	public void ejercicio1E() {
		try {
			ps.println("\nIngresa un tiempo en segundos: ");
			
			String input = leerLinea();
			Integer.parseInt(input); //chequear que el valor ingresado sea un número
			
			int timeS = Integer.parseInt(input);
			ps.println(timeS);
			
			//Resultado
			int days = Math.round(timeS/86400);//1 día = 86400 segundos
			int hours = Math.round((timeS%86400)/3600);//1 hora = 3600 segundos
			int minutes = Math.round((timeS%3600)/60);
			int seconds = Math.round(timeS % 60);
			
			ps.println( String.valueOf(timeS) + " segundos son " + days + " días, " + hours + " horas, " + minutes + " minutos y " + seconds + " segundos." );
				
				
		}
		catch(NumberFormatException e){ //en caso de no serlo, tira error
			psErr.println("Valor no válido. Ingresa un número.");
		}//end try/catch
		
	}//end ejercicio1E
	
	
	public void ejercicio1F() {
		try {
			ps.println("\nIngresa el precio de tu artículo: ");
			
			String input = leerLinea();
			Integer.parseInt(input); //chequear que el valor ingresado sea un número
			
			int precioP = Integer.parseInt(input);
			ps.println(precioP);
			
			//Resultado
			double plan1 = precioP - (precioP*0.1);
			double plan2P = precioP + (precioP*0.1);
			double plan2 = plan2P/2;
			double plan3P = precioP + (precioP*0.15);
			double plan3 = plan3P/4;
			double plan4P = precioP + (precioP/4);
			double plan4A = (plan4P*0.60)/4;
			double plan4B = (plan4P*0.40)/4;
			
			ps.println("¡A continuación mostraremos los distintos planes de pago para tu producto!");
			ps.println("Plan n°1:");
			ps.println( "Con el 100% en efectivo, el coste total sería $" + String.valueOf(plan1) );
			ps.println("Plan n°2:");
			ps.println( "Abona 50% en efectivo ahora y el resto en 2 cuotas de $" + String.valueOf(plan2/2) + " cada una. El coste total sería $" + String.valueOf(plan2P) );
			ps.println("Plan n°3:");
			ps.println( "Abona el 25% en efectivo ahora y el resto en 5 cuotas de $" + String.valueOf(precioP/5) + " cada una. El coste total sería $" + String.valueOf(plan3P) );
			ps.println("Plan n°4:");
			ps.println( "Abona todo en 8 cuotas totales. Las primeras 4 cuotas de $" + String.valueOf(plan4A) + " cada una, y las últimas 4 de $" + String.valueOf(plan4B) + " cada una. El coste total sería $" + String.valueOf(plan4P) );
				
		}
		catch(NumberFormatException e){ //en caso de no serlo, tira error
			psErr.println("Valor no válido. Ingresa un número.");
		}//end try/catch
		
	}//end ejercicio1F
	
	
	public void ejercicio1G() {
			ps.println("\nIngresa tu signo zodiacal: ");
			
			String signo = leerLinea();
			ps.println(signo);
			
			//Resultado
			String mes;

	        switch (signo.toLowerCase()) {
	            case "aries":
	            	ps.println("Tu mes de nacimiento probablemente sea Marzo o Abril");
	                break;
	            case "tauro":
	            	ps.println("Tu mes de nacimiento probablemente sea Abril o Mayo");
	                break;
	            case "geminis":
	            	ps.println("Tu mes de nacimiento probablemente sea Mayo o Junio");
	                break;
	            case "cancer":
	            	ps.println("Tu mes de nacimiento probablemente sea Junio o Julio");
	                break;
	            case "leo":
	            	ps.println("Tu mes de nacimiento probablemente sea Julio o Agosto");
	                break;
	            case "virgo":
	            	ps.println("Tu mes de nacimiento probablemente sea Agosto o Septiembre");
	                break;
	            case "libra":
	            	ps.println("Tu mes de nacimiento probablemente sea Septiembre u Octubre");
	                break;
	            case "escorpio":
	            	ps.println("Tu mes de nacimiento probablemente sea Octubre o Noviembre");
	                break;
	            case "sagitario":
	            	ps.println("Tu mes de nacimiento probablemente sea Noviembre o Diciembre");
	                break;
	            case "capricornio":
	            	ps.println("Tu mes de nacimiento probablemente sea Diciembre o Enero");
	                break;
	            case "acuario":
	            	ps.println("Tu mes de nacimiento probablemente sea Enero o Febrero");
	                break;
	            case "piscis":
	            	ps.println("Tu mes de nacimiento probablemente sea Febrero o Marzo");
	                break;
	            default:
	                ps.println("Signo no reconocido.");
	        }//end switch/case
		
		
	}//end ejercicio1G

	
}//end UsandoSystem
