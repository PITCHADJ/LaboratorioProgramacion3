package algoritmos.TADs;

import grafo.Vertice;


/* Clase simple, como su fuera un registro(atrib públicos) de:
 * Entero => distancia y Vértice => Vértice anterior
 */
public class Info{
	public int distancia;
	public Vertice ruta;
	
	public Info(int d, Vertice v){
		this.distancia = d;
		this.ruta = v;
	}
	
	// equals se usa en los test.
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Info other = (Info) obj;
		if (distancia != other.distancia)
			return false;
		if (ruta == null) {
			if (other.ruta != null)
				return false;
		} else if (!ruta.equals(other.ruta))
			return false;
		return true;
	}
}
