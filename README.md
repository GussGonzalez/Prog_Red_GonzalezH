# Programación Sobre Redes 2025
## Gonzalez Herrera Agustina Sol
- Mail: gonzalez.h.agustina@gmail.com
- Mail secundario: gus.g.backup@gmail.com
- 6to1ra Computación
- ET32 de14

## Gonzalo Nicolás Consorti
Profesor de la materia

## 
<img src="https://adorotedevote.neocities.org/images/straw.gif" width="600" height="100">
<!-- No te fijes el link profe, no pude encontrar la fuente original, habían muchas resubidas :( -->

## 

# Contenido de la rama
- Carpeta TP Final
   - src/main/java/TPFinal
       - []
   - pom.xml
- .gitignore
- README.md

## 
<img src="https://adorotedevote.neocities.org/images/straw.gif" width="600" height="100">
<!-- No te fijes el link profe, no pude encontrar la fuente original, habían muchas resubidas :( -->

##
# Consigna
Desarrollar un juego para dos jugadores utilizando programación en red mediante gestión y manejo de flujos de datos.

### Requisitos Funcionales
El juego debe estar basado en un tablero de casillas. Los jugadores deben elegir entre dos símbolos (X y O). El jugador 1 es Y, el jugador 2 es O. El objetivo del juego es conseguir alinear tres de tus símbolos de manera horizontal, vertical o diagonal antes que el oponente

El servidor debe aceptar conexiones de dos clientes (que juegan entre sí); gestionar el estado del juego (tablero) para ambos jugadores; coordinar los turnos alternados; validar los movimientos de cada jugador (verifica que la casilla esté vacía antes de realizar un movimiento); notificar el resultado de la jugada (¿quién ganó?, ¿es un empate?); y determinar y notificar al ganador cuando alguien gane o si es un empate.

El cliente se conecta al servidor proporcionando la IP y el puerto; permite al jugador hacer un movimiento (poner su X o O en una celda); recibe y muestra el estado actualizado del tablero; muestra un mensaje indicando el resultado de cada jugada (X gana, O gana, empate); y, al finalizar, muestra el resultado del juego (quién ganó o empate).

Se utilizarán TCP/IP para la comunicación cliente-servidor. El protocolo de comunicación debe ser claro y sencillo:
- El cliente se conecta al servidor y se autentica.
- El servidor gestiona los turnos de los jugadores y los notifica.
- El cliente envía la coordenada de la celda en la que desea colocar su símbolo.
- El servidor responde con el estado actualizado del tablero y si hubo un ganador o empate.
- El servidor notifica a los jugadores si la partida ha terminado.

El servidor debe manejar múltiples clientes utilizando (Thread) para gestionar cada conexión de forma concurrente. Cada conexión de cliente debe ser atendida en un hilo separado para que el servidor pueda manejar varios juegos simultáneamente. Se debe implementar adecuada para garantizar que el estado del juego no se vea afectado por cambios concurrentes entre hilos.

Utilizar las clases de : InputStream, OutputStream, ObjectInputStream, ObjectOutputStream (si se serializan objetos), BufferedReader, PrintWriter (si se utiliza texto).

Implementar manejo robusto de excepciones. Asegurar que todos los recursos sean después de finalizar la conexión.

##
### Requisitos Técnicos Obligatorios
- Implementación correcta de ServerSocket y Socket.
- Uso de Thread para concurrencia.
- Captura y gestión de excepciones como IOException, SocketException, etc.
- Código organizado en clases con propósitos claros.
- El juego debe ser por consola (sin interfaz gráfica).

#### Estructura de Código:
- Representa una casilla del tablero. Puede estar vacía o contener una 'X' o 'O'.

**Atributos**: String estado – Puede ser "VACIA", "X", o "O".
| Métodos | Explicación |
|     ---    |   ---   |
| `vaciar()`| Vacía la celda. |
| `marcar(String simbolo)` |   Marca la celda con 'X' o 'O'.|
| `esVacia()` | Retorna true si la celda está vacía.|


- Representa el tablero de 3x3 del juego.

**Atributos**: Celda[][] tablero – El tablero de juego.
| Métodos | Explicación |
|     ---    |   ---   |
| `mostrarTablero()`| Muestra el tablero por consola. |
| `colocarSimbolo(int fila, int col, String simbolo)` |   Coloca el símbolo del jugador en la casilla correspondiente.|
| `esGanador()` | Retorna true si hay un ganador. |
| `esEmpate()` | Retorna true si el juego ha terminado en empate. |
| `esTableroCompleto()` | Retorna true si el tablero está lleno. |


 - Representa a un jugador (Jugador 1 o Jugador 2).

**Atributos**: String nombre – Nombre del jugador. String simbolo – Símbolo del jugador ('X' o 'O').
| Métodos | Explicación |
|     ---    |   ---   |
| `realizarMovimiento(int fila, int col)`| Realiza un movimiento, colocando su símbolo en la casilla correspondiente. |


- Gestiona las conexiones con los clientes y el flujo de la partida.

| Métodos | Explicación |
|     ---    |   ---   |
| `iniciarServidor()`| Inicia el servidor y espera conexiones. |
| `gestionarPartida()` | Coordina el flujo del juego entre los dos jugadores. |
| `validarMovimiento()` | Verifica que la jugada sea válida. |
| `notificarEstadoJuego()` | Informa a los jugadores sobre el estado de la partida (si hay ganador, empate, etc.). | 


- Conecta al jugador al servidor y gestiona su participación en el juego.

| Métodos | Explicación |
|     ---    |   ---   |
| `conectarConServidor()`| Establece la conexión con el servidor. |
| `realizarMovimiento()` | Permite al jugador realizar un movimiento. |
| `validarMovimiento()` | Verifica que la jugada sea válida. |
| `recibirEstadoJuego()` | Recibe y muestra el estado del juego después de cada jugada. Condiciones de Victoria/Derrota |


- Un jugador gana si logra alinear tres de sus símbolos en una fila, columna o diagonal.
- Si el otro jugador logra alinear tres de sus símbolos antes.
- Si todas las casillas están ocupadas y no hay ganador.
