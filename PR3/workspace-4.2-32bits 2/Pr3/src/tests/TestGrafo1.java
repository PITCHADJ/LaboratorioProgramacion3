package tests;

import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;

import grafo.Arista;
import grafo.Vertice;
import interfaz.Grafo;
import algoritmos.Dijkstra;
import algoritmos.Kruskal;
import algoritmos.TADs.Info;
import junit.framework.TestCase;

public class TestGrafo1 extends TestCase {
	
	private Kruskal kruskal1;
	private Dijkstra dijkstra1;
	private HashSet<Arista> solucionK1;
	private HashMap<String, Info> solucionD1;
	private Grafo grafo1;
	
	public TestGrafo1(String testName){
		super(testName);
	}
	
	public void setUp() throws Exception{
		// GRAFO 1 => Preparar el grafo a tratar.
		grafo1 = new Grafo();
		Vertice array[] = new Vertice[5];
		for (int i = 0;i<5;i++){
			array[i] = new Vertice(0, 0, Color.black, Integer.toString(i));
			grafo1.addVertice(array[i]);
		}
		Arista array2[] = new Arista[6];
		array2[0] = new Arista(array[0], array[1], 1, Color.lightGray);
		array2[1] = new Arista(array[1], array[2], 2, Color.lightGray);
		array2[2] = new Arista(array[2], array[3], 3, Color.lightGray);
		array2[3] = new Arista(array[3], array[4], 4, Color.lightGray);
		array2[4] = new Arista(array[4], array[0], 5, Color.lightGray);
		array2[5] = new Arista(array[2], array[4], 6, Color.lightGray);
		for (int i = 0;i<6;i++) grafo1.addArista(array2[i]);
		// Kruskal y Dijkstra para este grafo.
		kruskal1 = new Kruskal(grafo1.dameAristas(), 
				grafo1.dameVertices(), grafo1.getNumVertices());
		dijkstra1 = new Dijkstra(array[0], grafo1.getNumVertices(), grafo1);
		// Soluciones esperadas.
		// Para Kruskal el conjunto => {01, 12, 23, 34}; que corresponden con el array[0-3].
		solucionK1 = new HashSet<Arista>();
		for (int i = 0; i<4; i++) // la solución concuerda con ese orden
			solucionK1.add(array2[i]);
		/* Para Dijkstra la tabla siguiente:
		 *  Vertice =>		| 0	| 1 | 2 | 3 | 4 |
		 *  Distancia =>	| 0 | 1 | 3 | 6 | 5 |
		 *  V. anterior =>	| n | 0 | 1 | 2 | 0 |
		 */
		solucionD1 = new HashMap<String, Info>();
		solucionD1.put(array[0].getNombre(), new Info(0, null));
		solucionD1.put(array[1].getNombre(), new Info(1, array[0]));
		solucionD1.put(array[2].getNombre(),new Info(3, array[1]));
		solucionD1.put(array[3].getNombre(),new Info(6, array[2]));
		solucionD1.put(array[4].getNombre(),new Info(5, array[0]));
			
	}
	
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testalgoritmoKruskal(){
		assertEquals(solucionK1, kruskal1.algoritmoKruskal());
	}
	
	public void testalgoritmoDijkstra(){
		assertEquals(solucionD1, dijkstra1.algoritmoDijkstra());
	}
	
}
