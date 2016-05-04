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

public class TestGrafo2 extends TestCase {
	
	private Kruskal kruskal2;
	private Dijkstra dijkstra2;
	private HashSet<Arista> solucionK21,solucionK22;
	private HashMap<String, Info> solucionD2;
	private Grafo grafo2;
	
	public TestGrafo2(String testName){
		super(testName);
	}
	
	public void setUp() throws Exception{
		// GRAFO 2 => Preparar el grafo a tratar.
		grafo2 = new Grafo();
		Vertice array[] = new Vertice[7];
		for (int i = 0;i<7;i++){
			array[i] = new Vertice(0, 0, Color.black, Integer.toString(i));
			grafo2.addVertice(array[i]);
		}
		Arista array2[] = new Arista[8];
		array2[0] = new Arista(array[0], array[1], 1, Color.lightGray);
		array2[1] = new Arista(array[1], array[2], 1, Color.lightGray);
		array2[2] = new Arista(array[2], array[5], 1, Color.lightGray);
		array2[3] = new Arista(array[0], array[3], 1, Color.lightGray);
		array2[4] = new Arista(array[3], array[4], 1, Color.lightGray);
		array2[5] = new Arista(array[1], array[6], 20, Color.lightGray);
		array2[6] = new Arista(array[4], array[6], 20, Color.lightGray);
		array2[7] = new Arista(array[4], array[5], 4, Color.lightGray);
		for (int i = 0;i<8;i++) grafo2.addArista(array2[i]);
		
		// Kruskal y Dijkstra para este grafo.
		kruskal2 = new Kruskal(grafo2.dameAristas(), 
				grafo2.dameVertices(), grafo2.getNumVertices());
		dijkstra2 = new Dijkstra(array[2], grafo2.getNumVertices(), grafo2);
		
		/* Soluciones esperadas.
		* Para Kruskal, Hay dos soluciones 
		* 1) => {01,12,03,34,25,16}
		* 2) => {01,12,03,34,25,46} 
		*/
		solucionK21 = new HashSet<Arista>();
		solucionK22 = new HashSet<Arista>();
		solucionK21.add(array2[0]); solucionK22.add(array2[0]);
		solucionK21.add(array2[1]); solucionK22.add(array2[1]);
		solucionK21.add(array2[2]); solucionK22.add(array2[2]);
		solucionK21.add(array2[3]); solucionK22.add(array2[3]);
		solucionK21.add(array2[4]); solucionK22.add(array2[4]);
		solucionK21.add(array2[5]); solucionK22.add(array2[6]);
		/* Para Dijkstra (inicio en el vértice 2) la tabla siguiente:
		 *  Vertice =>		| 0	| 1 | 2 | 3 | 4 | 5 | 6 |
		 *  Distancia =>	| 2 | 1 | 0 | 3 | 4 | 1 | 21|
		 *  V. anterior =>	| 1 | 2 | n | 0 | 3 | 2 | 1 |
		 */
		solucionD2 = new HashMap<String, Info>();
		solucionD2.put(array[0].getNombre(), new Info(2, array[1]));
		solucionD2.put(array[1].getNombre(), new Info(1, array[2]));
		solucionD2.put(array[2].getNombre(),new Info(0, null));
		solucionD2.put(array[3].getNombre(),new Info(3, array[0]));
		solucionD2.put(array[4].getNombre(),new Info(4, array[3]));
		solucionD2.put(array[5].getNombre(),new Info(1, array[2]));
		solucionD2.put(array[6].getNombre(),new Info(21, array[1]));
		
	}
	
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testalgoritmoKruskal(){
		assertTrue(solucionK22.equals(kruskal2.algoritmoKruskal())||
				solucionK21.equals(kruskal2.algoritmoKruskal()));
	}
	
	public void testalgoritmoDijkstra(){
		assertEquals(solucionD2, dijkstra2.algoritmoDijkstra());
	}

}
