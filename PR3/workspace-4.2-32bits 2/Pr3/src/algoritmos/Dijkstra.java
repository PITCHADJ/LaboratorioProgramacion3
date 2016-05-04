package algoritmos;

import interfaz.Grafo;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;

import algoritmos.TADs.Info;

import grafo.Arista;
import grafo.Vertice;;

public class Dijkstra {
	
	private Vertice inicial;
	private HashSet<Vertice> yaVisitados;
	private PriorityQueue<Vertice> aVisitar;
	private HashMap<String, Info> tabla;
	private Grafo grafo;
	
	
	
	
	public Dijkstra(Vertice inicial, int numVertices, final Grafo g){
		this.inicial = inicial;
		this.grafo = g;
		// Conjunto de elementos ya visitados.
		this.yaVisitados = new HashSet<Vertice>(numVertices);
		// Tabla donde guardamos los resultados obtenidos (distancia y vértice más cercano)
		this.tabla = new HashMap<String, Info>();
		Iterator<Vertice> itV = grafo.dameVertices();
		Vertice vAux;
		while (itV.hasNext()){
			vAux = itV.next();
			if (this.inicial.equals(vAux)){
				Info iAux = new Info(0, null);
				tabla.put(this.inicial.getNombre(), iAux);
			}
			else {
				Info iAux = new Info(Integer.MAX_VALUE, null);
				tabla.put(vAux.getNombre(), iAux);
			}
		}
		// Añadir a la cola las aristas del Vértice original ordenadas.				
		Comparator<Vertice> miComparador = new Comparator<Vertice>(){
			public int compare (Vertice v1, Vertice v2){
				if (v1.equals(v2)) return 0;
				int valor1 = calcularRuta(v1),
					valor2 = calcularRuta(v2);
				if (valor1 <= valor2) return -1;
				else return 1;
			}
		};
		this.aVisitar = new PriorityQueue<Vertice>(numVertices,miComparador);
		this.aVisitar.add(this.inicial);
		
	}
	// Calcula la ruta desde el vértice inicial a v1,
	// sacando los datos de la tabla. 
	// Los que consultamos siempre están ya calculados su camino mínimo
	protected int calcularRuta(Vertice v1) {
		Info info = tabla.get(v1.getNombre());
		int acumulador = info.distancia;
		while(!info.ruta.equals(inicial)){
			info = tabla.get(info.ruta.getNombre());
			acumulador += info.distancia;
		}
		
		return acumulador;
	}

	public HashMap<String, Info> algoritmoDijkstra(){
		while (!aVisitar.isEmpty()){
			// Sacamos el que tenga menor camino desde el inicial.
			// poll() consulta y lo elimina.
			Vertice vAux = aVisitar.poll();
			yaVisitados.add(vAux);
			// Comparar distancias adyacentes y quedarnos con la mejor.
			relajarVertice(vAux);
		}
		return tabla;
		
	}
	
	private void relajarVertice(Vertice vAux) {
		Iterator<Arista> itA = grafo.dameAristas();
		Arista aAux;
		while (itA.hasNext()){
			aAux = itA.next();
			if (vAux.equals(aAux.getOrigen())&& 
					!pertenece(aAux.getDestino())){
				// El destino es un vértice a tratar.
				Vertice vDestino = aAux.getDestino();				
				Info infoO = tabla.get(vAux.getNombre());
				Info infoD = tabla.get(vDestino.getNombre());
				if (infoD.distancia > (infoO.distancia + aAux.getPeso())){
					infoD.distancia = infoO.distancia+aAux.getPeso();
					infoD.ruta = vAux;
					aVisitar.remove(vDestino);
					aVisitar.add(vDestino);
				}
				
				
			}
			else if (vAux.equals(aAux.getDestino())&&
					!pertenece(aAux.getOrigen())){
				// El origen es un vértice a tratar;
				Vertice vOrigen = aAux.getOrigen();				
				Info infoD = tabla.get(vAux.getNombre());
				Info infoO = tabla.get(vOrigen.getNombre());
				if (infoO.distancia > (infoD.distancia + aAux.getPeso())){
					infoO.distancia = infoD.distancia+aAux.getPeso();
					infoO.ruta = vAux;
					aVisitar.remove(vOrigen);
					aVisitar.add(vOrigen);					
				}
			}
			
		}
		
	}
	
	private boolean pertenece(Vertice v){
		return yaVisitados.contains(v);
	}

}
