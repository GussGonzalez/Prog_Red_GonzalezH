package Colecciones;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class main {

	public static void main(String[] args) {
		//vector
		int[] vector = new int[5];
		
		//Crear una Lista
		ArrayList<String> nombres = new ArrayList<>();
		ArrayList comidas = new ArrayList<>();
		List<Integer> numeros = new ArrayList<>();
		
		
		LinkedList<Integer> otraLista = new LinkedList<>();
		
		List<String> productos;
		
		nombres.add("");
		nombres.addAll( comidas );
		nombres.clear();
		nombres.clone();
		nombres.contains(""); //boolean True/False
		nombres.containsAll( comidas );
		nombres.get( 2 ); //devuelve por index
		nombres.indexOf( "Juan" ); //devuelve dónde se encuentra esto
		nombres.isEmpty(); //boolean
		nombres.remove(0);
		nombres.remove("Juan");
		nombres.removeAll( comidas );
		nombres.size(); //
		nombres.set(4, "Pedro" ); //add pero podés elegir la posición
		nombres.sort(null);
		nombres.subList(0, 7);
		nombres.toArray();//arrayList --> vec[]
		
		
		
		//mapas -> hash
		
		//        Key,   Value
		HashMap<Integer, String> diccionario = new HashMap<>();
		LinkedHashMap<CadenaDeCaracteres.main, String> diccEnlace = new LinkedHashMap<>();
		Map<Integer, String> dicc = new HashMap<>();
		
		diccionario.containsKey( 5 );
		diccionario.get( 2 );
		diccionario.put(4, "hola");//una clave que no exista un valor. lo reemplaza automaticamente si ya existe
		diccionario.remove(5);
		diccionario.remove(5, "hola");
		diccionario.entrySet();
		
		
		
		HashSet<Integer> DNI = new HashSet<>();
		LinkedHashSet<Integer> CUIL = new LinkedHashSet<>();
		Set<Integer> dni = new HashSet();
		
	}
	
	
	public static void recorrido()
	{
		ArrayList<String> nombres = new ArrayList<>();
		
		for(int i=0; i<=nombres.size(); i++) 
		{
			nombres.get(i);
		}
		
		for(String item : nombres) 
		{
			System.out.println( item );
		}
		
		Iterator it = nombres.iterator();
		while( it.hasNext() ) 
		{
			System.out.println( it.next() );
		}
		
	}
}
