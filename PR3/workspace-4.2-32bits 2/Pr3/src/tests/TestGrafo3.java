package tests;

import grafo.Arista;
import grafo.Vertice;
import interfaz.Grafo;

import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;

import algoritmos.Dijkstra;
import algoritmos.Kruskal;
import algoritmos.TADs.Info;
import junit.framework.TestCase;

public class TestGrafo3 extends TestCase {
	
	private Kruskal kruskal3;
	private Dijkstra dijkstra3;
	private HashSet<Arista> solucionK3;
	private HashMap<String, Info> solucionD3;
	private Grafo grafo3;
	
	public TestGrafo3(String testName){
		super(testName);
	}
	
	public void setUp() throws Exception{
		// GRAFO 3 => Preparar el grafo a tratar.
		grafo3 = new Grafo();
		Vertice array[] = new Vertice[7];
		for (int i = 0;i<7;i++){
			array[i] = new Vertice(0, 0, Color.black, Integer.toString(i));
			grafo3.addVertice(array[i]);
		}
		Arista array2[] = new Arista[11];
		array2[0] = new Arista(array[0], array[1], 7, Color.lightGray);
		array2[1] = new Arista(array[1], array[2], 8, Color.lightGray);
		array2[2] = new Arista(array[0], array[3], 5, Color.lightGray);
		array2[3] = new Arista(array[1], array[4], 7, Color.lightGray);
		array2[4] = new Arista(array[2], array[4], 5, Color.lightGray);
		array2[5] = new Arista(array[1], array[3], 9, Color.lightGray);
		array2[6] = new Arista(array[3], array[4],15, Color.lightGray);
		array2[7] = new Arista(array[3], array[5], 6, Color.lightGray);
		array2[8] = new Arista(array[4], array[5], 8, Color.lightGray);
		array2[9] = new Arista(array[4], array[6], 9, Color.lightGray);
		array2[10] = new Arista(array[5], array[6], 11, Color.lightGray);
		for (int i = 0;i<11;i++) grafo3.addArista(array2[i]);
		
		// Kruskal y Dijkstra para este grafo.
		kruskal3 = new Kruskal(grafo3.dameAristas(), 
				grafo3.dameVertices(), grafo3.getNumVertices());
		dijkstra3 = new Dijkstra(array[0], grafo3.getNumVertices(), grafo3);
		
		/* Soluciones esperadas.
		* Para Kruskal => {01,14,24,03,35,46}
		*/
		solucionK3 = new HashSet<Arista>();
		solucionK3.add(array2[0]);
		solucionK3.add(array2[3]); 
		solucionK3.add(array2[4]);
		solucionK3.add(array2[2]);
		solucionK3.add(array2[7]);
		solucionK3.add(array2[9]); 
		/* Para Dijkstra (inicio en el vértice 2) la tabla siguiente:
		 *  Vertice =>		| 0	| 1 | 2 | 3 | 4 | 5 | 6 |
		 *  Distancia =>	| 0 | 7 | 15| 5 | 14| 11| 22|
		 *  V. anterior =>	| n | 0 | 1 | 0 | 1 | 3 | 5 |
		 */
		solucionD3 = new HashMap<String, Info>();
		solucionD3.put(array[0].getNombre(), new Info(0, null));
		solucionD3.put(array[1].getNombre(), new Info(7, array[0]));
		solucionD3.put(array[2].getNombre(),new Info(15, array[1]));
		solucionD3.put(array[3].getNombre(),new Info(5, array[0]));
		solucionD3.put(array[4].getNombre(),new Info(14, array[1]));
		solucionD3.put(array[5].getNombre(),new Info(11, array[3]));
		solucionD3.put(array[6].getNombre(),new Info(22, array[5]));
		
	}
	
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testalgoritmoKruskal(){
		assertEquals(solucionK3, kruskal3.algoritmoKruskal());
	}
	
	public void testalgoritmoDijkstra(){
		assertEquals(solucionD3, dijkstra3.algoritmoDijkstra());
	}

}
