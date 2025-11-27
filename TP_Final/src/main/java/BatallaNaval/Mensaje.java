package BatallaNaval;

import java.io.Serializable;

public class Mensaje implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum Tipo {
        CONEXION_INICIAL,
        POSICIONAMIENTO_OK,
        EMPEZAR_PARTIDA,
        TURNO_DISPARO,
        RESULTADO_DISPARO,
        ACTUALIZAR_ESTADO,
        FIN_PARTIDA,
        ENVIO_TABLERO_OPONENTE 
    }

    private final Tipo tipo;
    private final String datos; 
    private final Object objeto; 

    public Mensaje(Tipo tipo, String datos, Object objeto) {
        this.tipo = tipo;
        this.datos = datos;
        this.objeto = objeto;
    }//end Mensaje
    
    public Mensaje(Tipo tipo, String datos) {
        this(tipo, datos, null);
    }//end Mensaje

    public Tipo getTipo() { return tipo; }
    public String getDatos() { return datos; }
    public Object getObjeto() { return objeto; }
}//Mensaje
