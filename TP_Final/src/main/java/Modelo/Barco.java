package Modelo;

import java.io.Serializable;

public class Barco implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String nombre;
    private final int longitud;
    private int impactosRecibidos;

    public Barco(String nombre, int longitud) {
        this.nombre = nombre;
        this.longitud = longitud;
        this.impactosRecibidos = 0;
    }//end Barco

    public String getNombre() {
        return nombre;
    }//end getNombre

    public int getLongitud() {
        return longitud;
    }

    public int getImpactosRecibidos() {
        return impactosRecibidos;
    }//end getImpactosRecibidos

    public void recibirImpacto() {
        this.impactosRecibidos++;
    }//end recibirImpacto

    public boolean estaHundido() {
        return impactosRecibidos >= longitud;
    }//end estaHundido
    
}//Barco