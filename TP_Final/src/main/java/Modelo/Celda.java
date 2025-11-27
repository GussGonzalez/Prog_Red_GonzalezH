package Modelo;

import java.io.Serializable;

public class Celda implements Serializable {
    private static final long serialVersionUID = 1L; 

    private TipoCelda tipo;
    private Barco barco;

    public Celda() {
        this.tipo = TipoCelda.AGUA;
        this.barco = null;
    }

    public TipoCelda getTipo() {
        return tipo;
    }//end Celda

    public void setTipo(TipoCelda tipo) {
        this.tipo = tipo;
    }//end setTipo

    public Barco getBarco() {
        return barco;
    }//end getBarco

    public void setBarco(Barco barco) {
        this.barco = barco;
        if (barco != null) {
            this.tipo = TipoCelda.BARCO;
        }//end if
    }//end setBarco
}//Celda