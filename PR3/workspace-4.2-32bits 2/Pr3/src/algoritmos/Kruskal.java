package algoritmos;

import grafo.Arista;
import grafo.Vertice;

import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;

import algoritmos.TADs.BosqueConjDis;

public class Kruskal {
	private PriorityQueue<Arista> colaP;
	private BosqueConjDis bosque;
	private Set<Arista> solucion;
	private int numVertices;
	
	public Kruskal(Iterator<Arista> itA, Iterator<Vertice> itV,int numVertices) {
		this.numVertices = numVertices;
		colaP = new PriorityQueue<Arista>();
		while (itA.hasNext()){
			Arista aAux = itA.next();
			colaP.add(aAux);
		}
		bosque = new BosqueConjDis(itV);
		solucion = new HashSet<Arista>();
		
	}
	
	public Set<Arista> algoritmoKruskal(){
		while (!colaP.isEmpty()&&(solucion.size()< numVertices)){
			Arista a = colaP.poll();
			String s1 = bosque.dameGrupo(a.getOrigen());
			String s2 = bosque.dameGrupo(a.getDestino());
			if (! s1.equals(s2)) {
				bosque.union(s1, s2);
				solucion.add(a);
			}
		}
		return solucion;
	}
}
