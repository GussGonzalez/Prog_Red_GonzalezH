package Guia1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Arrays;

public class UsandoReader {
	//setup
			InputStreamReader isr = new InputStreamReader(System.in);
			PrintStream ps = new PrintStream(System.out);
			PrintStream  psErr = new PrintStream(System.err);
			BufferedReader br = new BufferedReader (isr);
			
			//métodos extra
			public void vaciar() {
				try {
					if (System.in.available() > 0) {
					    br.readLine(); // flush leftover line
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//end try/catch
			}//end vaciar
			
			//cuenta las veces que hay un espacio
			public int espacios(String input) {
				int count = 0;
		        for (int i = 0; i < input.length(); i++) {
		            if (input.charAt(i) == 32) {
		                count++;
		            }//end if
		        }//end for
		        return count;
			}//end espacios
	
			
			
			
			
	public void ejercicio2A() {
		vaciar();

		//setup
		String[] apellidos = {"", "", ""};
		char ch = 0;
		int index = 0;
		String apellido = "";
		
		try {
			//Lectura
			ps.println("Ingrese tres apellidos separados por un espacio: ");
			apellido = br.readLine();
			ps.println(apellido);
			
			//Confirmación
			if (apellido != null && !apellido.isEmpty()) {
		        
				int count = espacios(apellido);
		        
				//verifica que hayan sólo 2 espacios, es decir, sólo 3 apellidos
		        if (count == 2) {
		        	//guarda todo en un array
		        	for (int i = 0 ; i < apellido.length(); i++) {
						ch = apellido.charAt(i);
						if (ch != 32) {
							apellidos[index] = apellidos[index] + String.valueOf(ch);
						}else if (index < 3){
							index +=1;
						}//end if/else
					}//end for
					
		        	
		        	//Resultado
					Arrays.sort(apellidos);
					ps.println("Los apellidos ordenados alfabéticamente quedan: " + Arrays.toString(apellidos));
					
					
		        }else {
		        	psErr.println("Por favor, ingrese tres apellidos.");
		        }//end if/else
				
            } else {
                psErr.println("Por favor, ingrese un valor válido.");
            }//end if/else de verificación


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//end try/catch
	}//end ejercicio2A

	
	public void ejercicio2B() {
		vaciar();

		//setup
		String[] nums = {"", "", "", ""};
		char ch = 0;
		int index = 0;
		String num = "";
		
		try {
			//Lectura
			ps.println("Ingrese cuatro números enteros separados por un espacio: ");
			num = br.readLine();
			ps.println(num);
			
			//Confirmación
			if (num != null && !num.isEmpty()) {
				
				int count = espacios(num);
		        
		        //verifica que hayan sólo 3 espacios, es decir, sólo 4 números
		        if (count == 3) {
		        	//guarda todo en un array
		        	for (int i = 0 ; i < num.length(); i++) {
						ch = num.charAt(i);
						if (ch != 32) {
							nums[index] = nums[index] + String.valueOf(ch);
						}else if (index < 4){
							index +=1;
						}//end if/else
					}//end for
					
		        	float res = Float.MAX_VALUE;
		        	//Resultado
		        	for (int i = 0; i < nums.length; i++) {
		        		
		        	    try {
		        	    	
		        	        res = Math.min(res, (Float.parseFloat(nums[i])) );
		        	        
		        	    } catch (NumberFormatException e) {
		        	        psErr.println("Valor no válido: " + nums[i] + ". Por favor ingresar un número.");
		        	    }//end try/catch
		        	}//end for
		        	

		        	if (res == Float.MAX_VALUE) {
		        	    psErr.println("No se ingresaron valores numéricos válidos.");
		        	} else {
		        	    ps.println("El número menor es: " + res);
		        	}//end if/else
										
		        }else {
		        	psErr.println("Por favor, ingrese cuatro números.");
		        }//end if/else
				
            } else {
                psErr.println("Por favor, ingrese un valor válido.");
            }//end if/else de verificación


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//end try/catch
	}//end ejercicio2B
	
	
	public void ejercicio2C() {
		vaciar();
		try {
			//lectura
			ps.println("Ingrese un número: ");
			int num = Integer.valueOf( br.readLine() );
			
			//Resultado
			if (num%2 == 0) {
				ps.println( String.valueOf(num) + " es un número par." );
			}else {
				ps.println( String.valueOf(num) + " es un número impar." );
			}//end if/else
			
			
		} catch(NumberFormatException e){ //en caso de no ser un número, tira error
			psErr.println("Valor no válido. Ingresa un número.");
			
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//end try/catch
	}//end ejercicio1C
	
	
	public void ejercicio2D() {
		vaciar();
		try {
			//lectura
			ps.println("Ingrese un número: ");
			Float num1 = Float.valueOf( br.readLine() );
			
			vaciar();
			ps.println("Ingrese otro número: ");
			Float num2 = Float.valueOf( br.readLine() );
			
			Float nMen = Math.min(num1, num2);
			Float nMax = Math.max(num1, num2);
			
			//Respuesta
			if (nMax % nMen == 0) {
				ps.println("El número mayor es divisible por el número menor.");
			}else {
				ps.println("El número mayor no es divisible por el número menor.");
			}//end if/else
			
		} catch (IOException | NumberFormatException e) {
			psErr.println("Valor no válido. Ingrese dos números.");
		}//end try/catch
		
	}//end ejercicio 2D
	
	
	public void ejercicio2E() {
		vaciar();
		try {
			//lectura
			ps.println("Ingrese su fecha de nacimiento");
			ps.println("Día: ");
            int dia = Integer.parseInt(br.readLine());
            
            vaciar();
            ps.println("Mes(número): ");
            int mes = Integer.parseInt(br.readLine());
            
            String signo;

            if ((mes == 3 && dia >= 21) || (mes == 4 && dia <= 19)) {
                signo = "Aries";
            } else if ( (mes == 4 && dia >= 20) || (mes == 5 && dia <= 20) ) {
                signo = "Tauro";
            } else if ( (mes == 5 && dia >= 21) || (mes == 6 && dia <= 20) ) {
                signo = "Géminis";
            } else if ( (mes == 6 && dia >= 21) || (mes == 7 && dia <= 22) ) {
                signo = "Cáncer";
            } else if ( (mes == 7 && dia >= 23) || (mes == 8 && dia <= 22) ) {
                signo = "Leo";
            } else if ( (mes == 8 && dia >= 23) || (mes == 9 && dia <= 22) ) {
                signo = "Virgo";
            } else if ( (mes == 9 && dia >= 23) || (mes == 10 && dia <= 22) ) {
                signo = "Libra";
            } else if ( (mes == 10 && dia >= 23) || (mes == 11 && dia <= 21) ) {
                signo = "Escorpio";
            } else if ( (mes == 11 && dia >= 22) || (mes == 12 && dia <= 21) ) {
                signo = "Sagitario";
            } else if ( (mes == 12 && dia >= 22) || (mes == 1 && dia <= 19) ) {
                signo = "Capricornio";
            } else if ( (mes == 1 && dia >= 20) || (mes == 2 && dia <= 18) ) {
                signo = "Acuario";
            } else if ( (mes == 2 && dia >= 19) || (mes == 3 && dia <= 20) ) {
                signo = "Piscis";
            } else {
                signo = "Fecha no válida. Por favor ingrese una fecha esxistente.";
            }//end if/else

            ps.println("Su signo es: " + signo + "!");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//end try/catch
	}//end ejerrcicio 2E
	
	
	public void ejercicio2F() {
		vaciar();
		try {
			//lectura
            ps.print("Ingrese el nombre y apellido de una persona: ");
            String pers1 = br.readLine();
            
            vaciar();
            ps.print("Ingrese el nombre y apellido de otra persona: ");
            String pers2 = br.readLine();

            //guarda sólo el apellido
            String apellido1 = pers1.substring(pers1.indexOf(" ") + 1);
            String apellido2 = pers2.substring(pers2.indexOf(" ") + 1);

            if (apellido1.length() > apellido2.length()) {
                ps.println("El apellido más largo es el de " + pers1);
            } else if (apellido2.length() > apellido1.length()) {
                ps.println("El apellido más largo es el de " + pers2);
            } else {
                ps.println("Los dos apellidos tienen la misma cantidad de letras.");
            }//end if/else
            
        } catch (IOException | StringIndexOutOfBoundsException e) {
            psErr.println("Valor no válido. Ingresa dos nombres Y apellidos por separado.");
        }//end try/catch
	}//end ejercicio2F
	
	
	public void ejercicio2G() {
		vaciar();
		try {
            ps.print("Ingrese un número entero positivo: ");
            int num = Integer.parseInt(br.readLine());

            if (num <= 0) {
                ps.println("Valor no válido. Ingrese un número positivo");
                return;
            }//end if

            ps.println("Esta es la tabla de multiplicar del número " + num + ":");
            for (int i = 1; i <= 10; i++) {
                ps.println(num + " x " + i + " = " + (num * i));
            }//end for
            
        } catch (IOException | NumberFormatException e) {
            ps.println("Valor no válido. Ingrese un número positivo.");
        }//end try/catch
	}//end ejercicio2G
	
	
	public void ejercicio2H() {
		vaciar();
		try {
            ps.print("Ingrese un número entero positivo: ");
            int num = Integer.parseInt(br.readLine());

            if (num <= 1) {
                ps.println(num + " no es un número primo.");
                return;
            }//end if

            boolean primo = true;
            for (int i = 2; i <= Math.sqrt(num); i++) {
                if (num % i == 0) {
                    primo = false;
                    break;
                }//end if
            }//end for

            if (primo) {
                ps.println(num + " es un número primo.");
            } else {
                ps.println(num + " no es un número primo.");
            }//end if/else
            
        } catch (IOException | NumberFormatException e) {
            ps.println("Valor no válido. Ingrese un número entero natural.");
        }//end try/catch
	}//end ejercicio2H
	
}//end UsandoReader
