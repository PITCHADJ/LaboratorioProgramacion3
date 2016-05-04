package grafo;

import java.awt.Color;

public class Arista implements Comparable<Arista> {
	
	private Vertice origen,destino;
	private int peso;
	private Color color;
	
	public Arista(Vertice origen, Vertice destino,int peso, Color color){
		this.origen = origen;
		this.destino = destino;
		this.peso = peso;
		this.color = color;
	}

	@Override
	public int compareTo(Arista arg0) {
		if(this.peso > arg0.peso) return 1;
		else if(this.peso < arg0.peso) return -1;
		else return 0;
	}
	
	public Vertice getOrigen() {
		return origen;
	}
	public void setOrigen(Vertice origen) {
		this.origen = origen;
	}
	public Vertice getDestino() {
		return destino;
	}
	public void setDestino(Vertice destino) {
		this.destino = destino;
	}
	public int getPeso() {
		return peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}

	public Arista clone(){
		Arista aAux = new Arista(origen.clone(),
				destino.clone(), 
				peso, new Color(this.color.getRGB()));
		return aAux;
	}
	
	private boolean equals(Arista obj) {
		return (this.color.equals(obj.getColor())&&
				this.destino.equals(obj.getDestino())&&
				this.origen.equals(obj.getOrigen())&&
				this.peso == obj.getPeso());
	}
	
	public boolean equals(Object obj){
		if (!Arista.class.isInstance(obj))
			return false;
		else return this.equals((Arista)obj);
	}

	@Override
	public String toString() {			
		return origen.toString()+";"+destino.toString()+";"+peso+";"+(long)color.getRGB();
	}
	
	

}
