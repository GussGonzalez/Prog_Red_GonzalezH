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
- Carpeta TP1
   - src/main/java/TP1
     - main.java (clase main)
     - ejercicio1.java (clase del ejercicio 1)
     - ejercicio2.java (clase del ejercicio 2)
   - pom.xml
   - numsF.txt (puede ser borrado, lo crea el mismo programa)
- .gitignore
- README.md

## 
<img src="https://adorotedevote.neocities.org/images/straw.gif" width="600" height="100">
<!-- No te fijes el link profe, no pude encontrar la fuente original, habían muchas resubidas :( -->

##
# Consigna
1) Ingresar datos numéricos (por diferentes orígenes, sin usar la clase Scanner para leer o
escribir e ingresando, por lo menos, 2 veces el numero ‘0’ por cada uno de los orígenes de
datos entrantes e enviarlos a un algoritmo que los almacene en 2 sectores de memoria
distintos:
- volátil (en memoria) – “vectores”, para guardar la información en tiempo real (ingresar
por lo menos 5 valores). Vector de 5 espacios.
- no-volátil (disco) – “archivos de texto”, para guardar y leer los datos del proceso (guardar
por lo menos 5 valores). Uno por cada renglón. //no crear el archivo de texto a mano.

2) Leer los datos numéricos del vector cargado en el punto 1 y generar 2 archivos de salida:
- Por un lado, los resultados de dividir entre cada dato leído y el número que quede de la
siguiente operación (siguiente número del vector - 3) en un archivo “resultados.txt”. Con el
formato en un renglón por cuenta: numero1 / numero2 = resultado
- Y por el otro lado, cada vez que la división tire un error matemático o de falta de algún
número de entrada, guardar el error correspondiente en un archivo “error.txt” ubicado en
la carpeta del proyecto. ”. Con el formato en un renglón por cuenta: numero1 / numero2 =
error
