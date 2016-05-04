package algoritmos.TADs;

import java.util.ArrayList;

/*	�rbol general sencillo. Los hijos son listas (ArrayList)
 * 	Como nos interesa su ra�z, para saber si el �rbol pertenece
 * 	a otro �rbol, almacenamos esa informaci�n en raizSuperior.
 * 	Si raizSuperior = null => el �rbol no pertenece a otro, la 
 * 	ra�z es la real. 
 * 
 */

public class ArbolGeneral<E> {
	
	private nodoArbol raiz;
	private ArbolGeneral<E> raizSuperior;
	private int rango;
	
	public ArbolGeneral(E dato, ArbolGeneral<E> arbol){
		raiz = new nodoArbol(dato); 
		rango = 1;
		if (arbol == null) raizSuperior = null;
		else raizSuperior = arbol;
	}
	
	public void cambiarRaizSuperior(ArbolGeneral<E> arbol){
		if (arbol == null) raizSuperior = null;
		else raizSuperior = arbol;
	}
	public E raiz(){
		if (raizSuperior == null)
			return raiz.dato;
		else return raizSuperior.raiz();
	}
	public int getRango(){
		return rango;
	}
	public void insertar(ArbolGeneral<E> a){
		raiz.hijos.add(a.raiz);
		rango += a.rango;
	}
	
	public String toString(){
		return raiz.dato.toString()+": "+rango; 
		// Est� incompleto, falta por recorrer el resto del arbol,
		// pero solo lo uso para depurar y con est� informaci�n vale.
	}
	
	public void cambiarRaiz(nodoArbol nodo){
		this.raiz = nodo;
	}
	
	private class nodoArbol{
		public E dato;
		public ArrayList<nodoArbol> hijos;
		
		public nodoArbol(E dato){
			this.dato = dato;
			hijos = new ArrayList<ArbolGeneral<E>.nodoArbol>();
		}

		public String toString(){
			return this.dato.toString()+hijos.toString();
		}
	}
}
