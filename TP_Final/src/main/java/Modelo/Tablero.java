package Modelo;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Tablero implements Serializable {
	PrintStream ps = new PrintStream(System.out);

    private static final long serialVersionUID = 1L;
    public static final int TAMANIO = 10;
    private final ArrayList<ArrayList<Celda>> tablero;
    private final List<Barco> barcos;

    public Tablero() {
        this.tablero = new ArrayList<>();
        this.barcos = new ArrayList<>();
        inicializarTablero();
        inicializarBarcos();
    }//end Tablero

    private void inicializarTablero() {
        for (int i = 0; i < TAMANIO; i++) {
            ArrayList<Celda> fila = new ArrayList<>();
            for (int j = 0; j < TAMANIO; j++) {
                fila.add(new Celda());
            }//end for
            tablero.add(fila);
        }//end for
    }//end inicializarTablero
    
    private void inicializarBarcos() {
        barcos.add(new Barco("Portaaviones", 5));
        barcos.add(new Barco("Acorazado", 4));
        barcos.add(new Barco("Crucero", 3));
        barcos.add(new Barco("Submarino", 3));
        barcos.add(new Barco("Destructor", 2));
    }//end inicializarBarcos

    public boolean colocarBarco(String nombre, int fila, int col, boolean horizontal) {
        Barco barcoAColocar = barcos.stream()
            .filter(b -> b.getNombre().equalsIgnoreCase(nombre))
            .findFirst().orElse(null);
            
        if (barcoAColocar == null) return false;

        if (getBarcosColocados().contains(barcoAColocar)) {
            return false; 
        }//end if

        int longitud = barcoAColocar.getLongitud();

        if (fila < 0 || col < 0 || 
            (horizontal && col + longitud > TAMANIO) || 
            (!horizontal && fila + longitud > TAMANIO)) {
            return false;
        }//end if

        for (int i = 0; i < longitud; i++) {
            int f = horizontal ? fila : fila + i;
            int c = horizontal ? col + i : col;
            if (tablero.get(f).get(c).getTipo() == TipoCelda.BARCO) {
                return false;
            }//end if
        }//end for

        for (int i = 0; i < longitud; i++) {
            int f = horizontal ? fila : fila + i;
            int c = horizontal ? col + i : col;
            Celda celda = tablero.get(f).get(c);
            celda.setBarco(barcoAColocar); 
        }//end for
        
        return true;
    }//end colocarBarco
    
    public String disparar(int fila, int col) {
        if (fila < 0 || fila >= TAMANIO || col < 0 || col >= TAMANIO) {
            return "COORDENADAS_INVALIDAS";
        }//end if
        
        Celda celda = tablero.get(fila).get(col);
        if (celda.getTipo() == TipoCelda.FALLO || celda.getTipo() == TipoCelda.IMPACTO) {
             return "YA_DISPARADO";
        }//end if

        if (celda.getTipo() == TipoCelda.AGUA) {
            celda.setTipo(TipoCelda.FALLO);
            return "AGUA";
        } else if (celda.getTipo() == TipoCelda.BARCO) { 
            Barco barcoImpactado = celda.getBarco();
            
            if (barcoImpactado != null) {
                barcoImpactado.recibirImpacto();
                celda.setTipo(TipoCelda.IMPACTO);
                
                String nombreBarco = barcoImpactado.getNombre();
                
                if (barcoImpactado.estaHundido()) {
                    return "¡HUNDIDO! (" + nombreBarco + ")";
                } else {
                    return "¡IMPACTO!";
                }//end if/else
            }//end if
        }// end if/else if
        return "ERROR_DESCONOCIDO";
    }//end disparar

    public void mostrarTablero(boolean ocultarBarcos) {
        ps.print("  ");
        for (int i = 0; i < TAMANIO; i++) {
            ps.print(" " + i);
        }
        ps.println();
        
        for (int i = 0; i < TAMANIO; i++) {
            System.out.printf("%2d", i);
            for (int j = 0; j < TAMANIO; j++) {
                char simbolo = getSimboloCelda(tablero.get(i).get(j), ocultarBarcos);
                ps.print(" " + simbolo);
            }//end for
            ps.println();
        }//end for
    }//end mostrarTablero
    
    private char getSimboloCelda(Celda celda, boolean ocultarBarcos) {
        switch (celda.getTipo()) {
            case AGUA:
                return '~';
            case BARCO:
                return ocultarBarcos ? '~' : 'B';
            case IMPACTO:
                return 'X';
            case FALLO:
                return 'O';
            default:
                return '?';
        }//end switch/case
    }//end getSimboloCelda

    public void mostrarBarcos() {
        ps.println("\n--- Estado de Barcos Propios ---");
        for (Barco barco : barcos) {
            String estado = barco.estaHundido() ? "HUNDIDO" : "ACTIVO";
            ps.printf("- %s (Longitud: %d, Impactos: %d) -> %s\n", 
                                  barco.getNombre(), barco.getLongitud(), barco.getImpactosRecibidos(), estado);
        }//end for
    }//end mostrarBarcos

    public boolean todosLosBarcosCaidos() {
        return barcos.stream().allMatch(Barco::estaHundido);
    }//end todosLosBarcosCaidos
    
    public List<Barco> getBarcosColocados() {
         return tablero.stream()
            .flatMap(List::stream)
            .map(Celda::getBarco) 
            .filter(b -> b != null) 
            .distinct() 
            .collect(Collectors.toList());
    }//end getBarcosColocados
    
    public List<Barco> getBarcosNoColocados() {
        List<Barco> colocados = getBarcosColocados();
        return barcos.stream()
            .filter(b -> !colocados.contains(b))
            .collect(Collectors.toList());
    }//end getBarcosNoColocados
    
    public List<Barco> getBarcos() { return barcos; }
}//Tablero