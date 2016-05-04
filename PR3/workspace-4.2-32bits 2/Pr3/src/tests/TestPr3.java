package tests;

import junit.framework.Test;
import junit.framework.TestSuite;

public class TestPr3{
	public static Test suite() {
		TestSuite raiz = new TestSuite("Test de grafos");
		
		TestSuite grafo1 = new TestSuite("Grafo 1");
		grafo1.addTest(new TestGrafo1("testalgoritmoKruskal"));
		grafo1.addTest(new TestGrafo1("testalgoritmoDijkstra"));
		
		TestSuite grafo2 = new TestSuite("Grafo 2");
		grafo2.addTest(new TestGrafo2("testalgoritmoKruskal"));
		grafo2.addTest(new TestGrafo2("testalgoritmoDijkstra"));
		
		TestSuite grafo3 = new TestSuite("Grafo 3");
		grafo3.addTest(new TestGrafo3("testalgoritmoKruskal"));
		grafo3.addTest(new TestGrafo3("testalgoritmoDijkstra"));		
		
		raiz.addTest(grafo1);
		raiz.addTest(grafo2);
		raiz.addTest(grafo3);		
		
		return raiz;
		
	}

}
