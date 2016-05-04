package grafo;

import java.awt.Color;

public class Vertice{
	int x;
	int y;
	Color color;
	String nombre;
	
	public Vertice(int x,int y,Color color, String nombre) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.nombre = nombre;
	}


	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public Vertice clone(){
		int x1 = this.x;
		int y1 = this.y;
		Color color1 = new Color(this.color.getRGB());
		String nombre1 = new String(nombre);
		Vertice aux = new Vertice(x1, y1, color1, nombre1);
		return aux;
	}
	
	private boolean equals(Vertice arg0) {
		return ((arg0.getX() == this.x)&&
				(arg0.getY() == this.y)&&
				(arg0.getColor().equals(color))&&
				(arg0.getNombre().equals(this.nombre)));
	}
	
	public boolean equals(Object obj){
		if (!Vertice.class.isInstance(obj))
			return false;
		else return this.equals((Vertice)obj);
	}
	public String toString() {
		String aux;
		aux = nombre+":"+x+":"+y+":"+(long)color.getRGB();
		return aux;
	}

}
