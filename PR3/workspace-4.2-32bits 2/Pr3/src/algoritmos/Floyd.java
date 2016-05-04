package algoritmos;

import interfaz.Grafo;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

import algoritmos.TADs.Info;

import grafo.Arista;
import grafo.Vertice;;

public class Floyd {
	
	private Vertice inicial;
	private Vertice destino;
	private Stack<String> soluciones;
	
	private HashMap<String, Info> tabla;
	private HashMap<String, HashMap<String, Integer> > tablaFloyd;
	private Grafo grafo;
	
	
	public Floyd(Vertice inicial, Vertice destino, int numVertices, final Grafo g){
		this.soluciones = new Stack<String>(); 
		this.destino = destino; 
		this.inicial = inicial; 
		this.grafo = g;
		
		this.tablaFloyd = new HashMap<String, HashMap<String, Integer> >();
		Iterator<Arista> itA = grafo.dameAristas();
		while(itA.hasNext()){
			Arista current = itA.next();
			HashMap<String, Integer> tmp = tablaFloyd.get(current.getOrigen().getNombre());
			if (tmp == null){
				tmp = new HashMap<String, Integer>();
			} 
			tmp.put(current.getDestino().getNombre(), current.getPeso());
			tablaFloyd.put(current.getOrigen().getNombre(), tmp);
		}
		itA = grafo.dameAristas();
		while(itA.hasNext()){
			Arista current = itA.next();
			HashMap<String, Integer> tmp = tablaFloyd.get(current.getDestino().getNombre());
			if (tmp == null){
				tmp = new HashMap<String, Integer>();
			} 
			tmp.put(current.getOrigen().getNombre(), current.getPeso());
			tablaFloyd.put(current.getDestino().getNombre(), tmp);
		}
		
	}
	

	public HashMap<String, Info> algoritmoFloyd(){
		HashMap<String, Integer> tmp = tablaFloyd.get(inicial.getNombre());
		Iterator<String> itRow = tmp.keySet().iterator();
		Boolean encontrado = false;
		
		while(!encontrado){
			int min_i = 100000;
			String min_s = "null";
			
			while(itRow.hasNext() && !encontrado){
				String current = itRow.next();
				int val = tmp.get(current);
				
				encontrado = destino.getNombre().equals(current);
				// minimo
				if (val <= min_i){
					min_i = val;
					min_s = current;
				}
			}
			
			if(!encontrado){
				soluciones.push(min_s);
				tmp = tablaFloyd.get(min_s);
				itRow = tmp.keySet().iterator();
			}
		}
		return tabla;		
	}
	
}