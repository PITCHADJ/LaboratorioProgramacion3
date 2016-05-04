package algoritmos.TADs;


import grafo.Vertice;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class BosqueConjDis{
	
	private Map<String, ArbolGeneral<Vertice>> tabla;
	
	public BosqueConjDis(Iterator<Vertice> iterador) { 
		tabla = new HashMap<String, ArbolGeneral<Vertice>>();
		while (iterador.hasNext()){
			Vertice vAux = iterador.next().clone();
			ArbolGeneral<Vertice> arbolAux2 = new ArbolGeneral<Vertice>(vAux,null);
			tabla.put(vAux.getNombre(), arbolAux2);
		}
	}
	
	
	public String dameGrupo(Vertice a){
		ArbolGeneral<Vertice> tAuxb1 = tabla.get(a.getNombre());
		return tAuxb1.raiz().getNombre();
		
	}
	
	
	public void union(String s1, String s2){
		ArbolGeneral<Vertice> tAuxb1 = tabla.get(s1);
		ArbolGeneral<Vertice> tAuxb2 = tabla.get(s2);
		//  Unión por tamaño 
		if (tAuxb1.getRango() >= tAuxb2.getRango()){
			tAuxb1.insertar(tAuxb2);
			tabla.put(s2, tAuxb1);
			tAuxb2.cambiarRaizSuperior(tAuxb1);
		}
		else {
			tAuxb2.insertar(tAuxb1);
			tabla.put(s1, tAuxb2);
			tAuxb1.cambiarRaizSuperior(tAuxb2);
		}
	}
	

}
