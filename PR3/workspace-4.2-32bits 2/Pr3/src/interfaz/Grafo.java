package interfaz;

import grafo.*;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JOptionPane;

import algoritmos.Dijkstra;
import algoritmos.Floyd;
import algoritmos.TADs.Info;

public class Grafo extends Canvas implements MouseListener{

	private static final long serialVersionUID = 1L;
	private Set<Arista> aristas;
	private Set<Vertice> vertices;
	private int numAristas,
		numVertices,
		estado,
		pesoArista,
		radio = 14,
		lado = (int) Math.sqrt(Math.pow(radio, 2)*2);
	private String nombreVertice;
	private Vertice origen;
	private String etiqueta="";

	public Grafo() {
		estado = 0;
		this.numAristas = 0;
		this.numVertices = 0;
		aristas = new HashSet<Arista>();
		vertices = new HashSet<Vertice>();
		this.setSize(400, 400);
		addMouseListener(this);
	}
	
	public void addArista(Arista arista){
		if (aristas.add(arista)) 
			numAristas++;		
	}
	
	public void addVertice(Vertice vertice){
		if (vertices.add(vertice)) 
			numVertices++;
	}
	
	public Iterator<Arista> dameAristas(){
		return aristas.iterator();
	}
	
	public Iterator<Vertice> dameVertices(){
		return vertices.iterator();
	}
	
	public void cambiarEstado(int i){
		this.estado = i;
		etiqueta = significado(i);
		this.repaint();
	}
	private String significado(int i){
		switch (i){
			case 0:
				return "";
			case 1:
				return "Selecciona origen.";
			case 2:
				return "Selecciona destino ("+ origen.getNombre()+") .";
			case 3:
				return "Selecciona vértice ("+nombreVertice+").";
			case 4:
				return "Selecciona vértice para borrar.";
			case 5:
				return "Selecciona origen de la arista para borrar.";
			case 6:
				return "Selecciona destino de la arista para borrar.";
			case 7:
			case 10:
				return "Selecciona origen del camino";
			case 8:
			case 9:
				return "Selcciona destino del camino ("+ origen.getNombre()+") .";
				
			default:
					return "";
		}
	}
	public void setNombreVertice(String nombreVertice) {
		this.nombreVertice = nombreVertice;
	}
	public void setPesoArista(int pesoArista) {
		this.pesoArista = pesoArista;
	}
	public int getNumVertices() {
		return numVertices;
	}
	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
		this.repaint();
	}
	
	@Override
	public void paint(Graphics g){
		g.setColor(Color.white);
		g.fillRect(0, 0, 400, 400);
		pintarVertices(g);
		pintarAristas(g);
		g.setColor(Color.black);
		g.drawString(etiqueta, 10, 10);
	}
	
	public void aplicarKruskal(Set<Arista> sol){
		// reiniciar los colores de todas.
		reiniciarColor();
		this.repaint();
		// buscarlas y cambiar el color
		Iterator<Arista> it = sol.iterator();
		Arista aux1,aux2;
		while (it.hasNext()){
			aux1 = it.next();
			Iterator<Arista> it2 = aristas.iterator();
			while (it2.hasNext()){
				aux2 = it2.next();
				if (aux1.equals(aux2)) aux2.setColor(Color.red);
			}
		}
		etiqueta = "Kruskal Aplicado";
		this.repaint();
	}
	
	private void aplicarDijkstra(Vertice origen2, Vertice destino) {
		Dijkstra dij = new Dijkstra(origen2, numVertices, this);
		HashMap<String, Info> solucion = dij.algoritmoDijkstra();
		dibujarDijkstra(origen2,destino,solucion);
		etiqueta = "Dijkstra Aplicado";
		this.repaint();	
	}

	
	private void aplicarFloyd(Vertice origen2, Vertice destino) {
		Floyd dij = new Floyd(origen2, destino, numVertices, this);
		HashMap<String, Info> solucion  = dij.algoritmoFloyd();
		dibujarFloyd(origen2,destino,solucion);
		etiqueta = "Floyd Aplicado";
		this.repaint();	
	}

	private void dibujarDijkstra(Vertice origen2, Vertice destino,
			HashMap<String, Info> solucion) {
		Vertice nuevoDestino = destino;
		reiniciarColor();
		while (!origen2.equals(nuevoDestino)){
			Info d = solucion.get(nuevoDestino.getNombre());
			cambiarColorArista(d.ruta,nuevoDestino);
			nuevoDestino = d.ruta;
			
		}
	}

	private void cambiarColorArista(Vertice nuevoDestino, Vertice ruta) {
		// buscarlas y cambiar el color
		Iterator<Arista> it = this.dameAristas();
		Arista aux1;
		while (it.hasNext()){
			aux1 = it.next();
			if ((aux1.getDestino().equals(nuevoDestino)&&
					aux1.getOrigen().equals(ruta)) ||
					(aux1.getDestino().equals(ruta) &&
					aux1.getOrigen().equals(nuevoDestino))){
						aux1.setColor(Color.green);
					}
		}		
	}

	private void reiniciarColor() {
		Iterator<Arista> it = aristas.iterator();
		while (it.hasNext()){
			it.next().setColor(Color.lightGray);
		}
	}

	private void pintarVertices(Graphics g){
		Iterator<Vertice> itPintarV = vertices.iterator();
		while (itPintarV.hasNext()){
			Vertice vAux = itPintarV.next();
			g.setColor(vAux.getColor());
			g.fillOval(vAux.getX()-(lado/2), vAux.getY()-(lado/2), lado, lado);
			g.setColor(Color.black);
			g.drawString(vAux.getNombre(), vAux.getX(), vAux.getY()-radio-5);
		}
		
		
	}
	
	private void pintarAristas(Graphics g1){
		Iterator<Arista> itPintarA = aristas.iterator();
		Graphics2D g = (Graphics2D)g1;
		g.setStroke(new BasicStroke(2));
		while (itPintarA.hasNext()){
			Arista aAux = itPintarA.next();
			g.setColor(aAux.getColor());
			int XOrigen = aAux.getOrigen().getX(),
				YOrigen = aAux.getOrigen().getY(),
				XDestino =  aAux.getDestino().getX(),
				YDestino =  aAux.getDestino().getY();
			// pintar arista
			g.drawLine(XOrigen,YOrigen,XDestino,YDestino);
			g.setColor(Color.black);
			int XString, YString;
			if (XOrigen <= XDestino)
				XString = ((XDestino - XOrigen)/2)+XOrigen +3;
			else XString = ((XOrigen - XDestino)/2)+XDestino +3;
			if (YOrigen <= YDestino)
				YString = ((YDestino - YOrigen)/2)+YOrigen - 4;
			else YString = ((YOrigen - YDestino)/2)+YDestino -4;
			// pintar peso
				g.drawString(Integer.toString(aAux.getPeso()),
						XString,YString);
			
		}
	}
	
	private Color colorAleatorio(){
		int aleatorios[] = {(int)(Math.random()*255+1),
				(int)(Math.random()*255+1),
				(int)(Math.random()*255+1)};
		Color colorAleatorio = new Color(aleatorios[0],
				aleatorios[1], 
				aleatorios[2]);	
		return colorAleatorio;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}
	

	private void dibujarFloyd(Vertice origen2, Vertice destino,	HashMap<String, Info> solucion) {
		aplicarDijkstra(origen2, destino);
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if (estado == 1){
			// Recoger pulsacion para arista-> origen
			// Buscar en el hashset un vertice con x,y + radio
			// si esta coger el origen (referencia)
			boolean fin = false;
			Iterator<Vertice> buscaV = dameVertices();
			while (buscaV.hasNext() && !fin){
				Vertice vAux = buscaV.next();
				if ((vAux.getX()>(x-radio))&&(vAux.getX()<(x+radio))&&
						(vAux.getY()>(y-radio))&&(vAux.getY()<(y+radio))){
					fin = true;
					origen = vAux;
					//estado = 2;
					this.cambiarEstado(2);
				}				
			}
			if (!fin) JOptionPane.showMessageDialog(null,
					"Ese vértice no existe", "Error",
					JOptionPane.ERROR_MESSAGE);			
		}
		else if (estado == 2) {
			// Recoger pulsacion para arista-> destino
			// Recoger pulsacion para arista-> origen
			// Buscar en el hashset un vertice con x,y + radio
			boolean fin = false;
			Iterator<Vertice> buscaV = dameVertices();
			while (buscaV.hasNext() && !fin){
				Vertice vAux = buscaV.next();
				if ((vAux.getX()>(x-radio))&&(vAux.getX()<(x+radio))&&
						(vAux.getY()>(y-radio))&&(vAux.getY()<(y+radio))){
					fin = true;
					Arista nuevaArista = new Arista(origen, vAux, pesoArista, Color.lightGray);
					this.addArista(nuevaArista);
					this.repaint();
					//estado = 0;
					this.cambiarEstado(0);
				}				
			}
			if (!fin) JOptionPane.showMessageDialog(null,
					"Ese vértice no existe", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		else if (estado == 3){
			// Recoger pulsacion para vertice
			Vertice nuevoVertice = new Vertice(x, y, colorAleatorio(), nombreVertice);
			this.addVertice(nuevoVertice);
			//estado = 0;
			this.cambiarEstado(0);
			this.repaint();
		}
		else if (estado == 4){
			// borrar vertice, y posibles aristas
			boolean fin = false;
			Iterator<Vertice> buscaV = dameVertices();
			while (buscaV.hasNext() && !fin){
				Vertice vAux = buscaV.next();
				if ((vAux.getX()>(x-radio))&&(vAux.getX()<(x+radio))&&
						(vAux.getY()>(y-radio))&&(vAux.getY()<(y+radio))){
					fin = true;
					//estado = 0;
					this.cambiarEstado(0);
					if (vertices.remove(vAux)){
						Iterator<Arista> it = aristas.iterator();
						Arista aAux;
						HashSet<Arista> aux  = new HashSet<Arista>();
						while (it.hasNext()){
							aAux = it.next();
							if (vAux.equals(aAux.getOrigen())||
									vAux.equals(aAux.getDestino()))
								aux.add(aAux);
						}
						aristas.removeAll(aux);
						numVertices--;
						numAristas -= aux.size();
						this.repaint();
					}
					
				}				
			}
			if (!fin) JOptionPane.showMessageDialog(null,
					"Ese vértice no existe", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		else if (estado == 5){
			// Guardar origen a borrar.
			boolean fin = false;
			Iterator<Vertice> buscaV = dameVertices();
			while (buscaV.hasNext() && !fin){
				Vertice vAux = buscaV.next();
				if ((vAux.getX()>(x-radio))&&(vAux.getX()<(x+radio))&&
						(vAux.getY()>(y-radio))&&(vAux.getY()<(y+radio))){
					fin = true;
					origen = vAux;
					//estado = 6;
					this.cambiarEstado(6);
				}				
			}
			if (!fin) JOptionPane.showMessageDialog(null,
					"Ese vértice no existe", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		else if (estado == 6){
			boolean fin = false;
			Iterator<Vertice> buscaV = dameVertices();
			Vertice destino=null;
			while (buscaV.hasNext() && !fin){
				Vertice vAux = buscaV.next();
				if ((vAux.getX()>(x-radio))&&(vAux.getX()<(x+radio))&&
						(vAux.getY()>(y-radio))&&(vAux.getY()<(y+radio))){
					fin = true;
					destino = vAux;				
				}				
			}
			// borrar arista que concuerde con origen-destino.
			//(fin cambia a significar lo contrario)
			//estado = 0;
			this.cambiarEstado(0);
			Iterator<Arista> it = dameAristas();
			Arista aAux,borrar=null;
			while(it.hasNext()&&fin){
				aAux = it.next();
				if ((origen.equals(aAux.getOrigen())&&
						destino.equals(aAux.getDestino()))||
						((destino.equals(aAux.getOrigen()))&&
						(origen.equals(aAux.getDestino())))){
					fin = false;
					borrar = aAux;
				}					
			}
			aristas.remove(borrar);
			this.repaint();
			if (fin) JOptionPane.showMessageDialog(null,
					"Esa arista no existe", "Error",
					JOptionPane.ERROR_MESSAGE);		
		}
		else if (estado == 7){
			// Recoger pulsacion para arista-> origen
			// Buscar en el hashset un vertice con x,y + radio
			// si esta coger el origen (referencia)
			boolean fin = false;
			Iterator<Vertice> buscaV = dameVertices();
			while (buscaV.hasNext() && !fin){
				Vertice vAux = buscaV.next();
				if ((vAux.getX()>(x-radio))&&(vAux.getX()<(x+radio))&&
						(vAux.getY()>(y-radio))&&(vAux.getY()<(y+radio))){
					fin = true;
					origen = vAux;
					this.cambiarEstado(8);
				}				
			}
			if (!fin) JOptionPane.showMessageDialog(null,
					"Ese vértice no existe", "Error",
					JOptionPane.ERROR_MESSAGE);	
		}
		else if (estado == 8){
			// Aplicar Dijkstra
			boolean fin = false;
			Iterator<Vertice> buscaV = dameVertices();
			Vertice destino = null;
			while (buscaV.hasNext() && !fin){
				Vertice vAux = buscaV.next();
				if ((vAux.getX()>(x-radio))&&(vAux.getX()<(x+radio))&&
						(vAux.getY()>(y-radio))&&(vAux.getY()<(y+radio))){
					fin = true;
					destino = vAux;
					this.cambiarEstado(8);
				}				
			}
			
			if (!fin) JOptionPane.showMessageDialog(null,
					"Ese vértice no existe", "Error",
					JOptionPane.ERROR_MESSAGE);	
			else{
				this.cambiarEstado(0);
				aplicarDijkstra(origen,destino);
			}			
		}
		else if (estado == 9){
			// Aplicar Floyd
			boolean fin = false;
			Iterator<Vertice> buscaV = dameVertices();
			Vertice destino = null;
			while (buscaV.hasNext() && !fin){
				Vertice vAux = buscaV.next();
				if ((vAux.getX()>(x-radio))&&(vAux.getX()<(x+radio))&&
						(vAux.getY()>(y-radio))&&(vAux.getY()<(y+radio))){
					fin = true;
					destino = vAux;
					this.cambiarEstado(9);
				}				
			}
			
			if (!fin) JOptionPane.showMessageDialog(null,
					"Ese vértice no existe", "Error",
					JOptionPane.ERROR_MESSAGE);	
			else{
				this.cambiarEstado(0);
				aplicarFloyd(origen,destino);
			}			
		}
		else if (estado == 10){
			// Recoger pulsacion para arista-> origen
			// Buscar en el hashset un vertice con x,y + radio
			// si esta coger el origen (referencia)
			boolean fin = false;
			Iterator<Vertice> buscaV = dameVertices();
			while (buscaV.hasNext() && !fin){
				Vertice vAux = buscaV.next();
				if ((vAux.getX()>(x-radio))&&(vAux.getX()<(x+radio))&&
						(vAux.getY()>(y-radio))&&(vAux.getY()<(y+radio))){
					fin = true;
					origen = vAux;
					this.cambiarEstado(9);
				}				
			}
			if (!fin) JOptionPane.showMessageDialog(null,
					"Ese vértice no existe", "Error",
					JOptionPane.ERROR_MESSAGE);	
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

}
