package TicTacToe;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import Utils.colors;

public class Tablero {
	PrintStream ps = new PrintStream(System.out);
	
	ArrayList<String> col1 = new ArrayList<>(Arrays.asList(" ", " ", " "));
	ArrayList<String> col2 = new ArrayList<>(Arrays.asList(" ", " ", " "));
	ArrayList<String> col3 = new ArrayList<>(Arrays.asList(" ", " ", " "));
	ArrayList<ArrayList<String>> tablero = new ArrayList<>(Arrays.asList(col1, col2, col3));
	
	public void vaciar(int fila, int columna) { 
		if (tablero.get(fila).get(columna) != " " ) { tablero.get(fila).set(columna, " "); }//end if
	}//end vaciar
	
	public void marcar(int fila, int columna, String simbolo){
		if (simbolo == "X") { 
			tablero.get(fila).set(columna, colors.RED + simbolo + colors.RESET);
		}else if (simbolo == "O") {
			tablero.get(fila).set(columna, colors.BLUE + simbolo + colors.RESET);
		}else{
			ps.println("Símbolo no válido. Por favor coloque una X o una O.");
		}//end if/else if
	}//end marcar
	
	public boolean esVacia(int fila, int columna) { if (tablero.get(fila).get(columna) != " " ) { return false; } else {return true;} }//end esVacia
	
	public void mostrarTablero() {
		ps.println("|" + tablero.get(0).get(0) + "|" + tablero.get(1).get(0) + "|" + tablero.get(2).get(0) + "|");
		ps.println("|" + tablero.get(0).get(1) + "|" + tablero.get(1).get(1) + "|" + tablero.get(2).get(1) + "|");
		ps.println("|" + tablero.get(0).get(2) + "|" + tablero.get(1).get(2) + "|" + tablero.get(2).get(2) + "|");
	}//end mostrarTablero
	
	public void colocarSimbolo(int fila, int columna, String simbolo) { marcar(fila, columna, simbolo); }//end modificarTablero
	
	
	public boolean esGanador(String simbolo) {		
		if (tablero.get(0).get(0) == simbolo && tablero.get(1).get(1) == simbolo && tablero.get(2).get(2) == simbolo) {
			return true;
		}else if (tablero.get(0).get(2) == simbolo && tablero.get(1).get(1) == simbolo && tablero.get(2).get(0) == simbolo) {
			return true;
		}else {
			for (int i = 0 ; i<3 ; i++) {
				if (tablero.get(i).get(0) == simbolo && tablero.get(i).get(1) == simbolo && tablero.get(i).get(2) == simbolo) {
					return true;
				}else if(tablero.get(0).get(i) == simbolo && tablero.get(1).get(i) == simbolo && tablero.get(2).get(i) == simbolo){
					return true;
				}//end if
			}//end for
		}//end if/else if/else
		return false;
	}//end esGanador
	
	public boolean esEmpate(String simbolo) {
		if (esTableroCompleto() && !esGanador(simbolo)) {
			return true;
		}//end if
		return false;
	}//end esEmpate
	
	public boolean esTableroCompleto() {
		for (ArrayList<String> columna : tablero) {
            for (String celda : columna) { if (celda == " ") { return false; } }//end if & for
        }//end for
		return true;
	}//end esTableroCompleto
	

}//end Tablero
