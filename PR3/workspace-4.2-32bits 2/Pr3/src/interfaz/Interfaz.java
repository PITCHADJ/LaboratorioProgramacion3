package interfaz;

import grafo.Arista;
import grafo.Vertice;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import algoritmos.Kruskal;

public class Interfaz extends JFrame {

	private static final long serialVersionUID = 1L;
	private Grafo canvas;
	private JPanel p;
	
	public Interfaz() {
		this.setJMenuBar(barraDeMenus());
		p = new JPanel();
		canvas = new Grafo();
		p.add(canvas);
		this.setContentPane(p);
		
	}
	
	private void setParametros() {
		this.setTitle("Práctica 3: Grafo");
		this.setEnabled(true);
		this.setVisible(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setSize(500, 500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
	private JMenuBar barraDeMenus(){
		JMenuBar barraDeMenu = new JMenuBar();
		JMenu archivo = new JMenu("Archivo");
		JMenu grafo = new JMenu("Grafo");
		JMenu operaciones = new JMenu("Operaciones");
		
		JMenuItem nuevo = new JMenuItem("Nuevo");
		JMenuItem abrir = new JMenuItem("Abrir");
		JMenuItem guardar = new JMenuItem("Guardar");
		JMenuItem salir = new JMenuItem("Salir");
		JMenu ejemplos = new JMenu("Ejemplos");
		JMenuItem test = new JMenuItem("Test1");
		JMenuItem test2 = new JMenuItem("Test2");
		JMenuItem test3 = new JMenuItem("Test3");
		JMenuItem Ejemplo1 = new JMenuItem("Ejemplo1");
		JMenuItem Ejemplo2 = new JMenuItem("Ejemplo2");
		eventoEjemplos(test,"src/interfaz/ejemplos/test1.txt");
		eventoEjemplos(test2,"src/interfaz/ejemplos/test2.txt");
		eventoEjemplos(test3,"src/interfaz/ejemplos/test3.txt");
		eventoEjemplos(Ejemplo1,"src/interfaz/ejemplos/ejemplo1.txt");
		eventoEjemplos(Ejemplo2,"src/interfaz/ejemplos/ejemplo2.txt");

		eventosArchivo(nuevo,abrir, guardar, salir);
		
		ejemplos.add(test);
		ejemplos.add(test2);
		ejemplos.add(test3);
		ejemplos.add(Ejemplo1);
		ejemplos.add(Ejemplo2);

		archivo.add(nuevo);
		archivo.add(abrir);
		archivo.add(guardar);
		archivo.add(ejemplos);
		archivo.add(salir);
		
		JMenuItem vertice = new JMenuItem("Introducir vértice");
		JMenuItem arista = new JMenuItem("Introducir arista");
		JMenuItem borrarV = new JMenuItem("Borrar vértice");
		JMenuItem borrarA = new JMenuItem("Borrar arista");
		eventosGrafos(vertice,arista,borrarV,borrarA);
		
		grafo.add(vertice);
		grafo.add(arista);
		grafo.add(borrarV);
		grafo.add(borrarA);
		
		JMenuItem dijkstra = new JMenuItem("Dijkstra");
		JMenuItem kruskal = new JMenuItem("Kruskal");
		JMenuItem floyd = new JMenuItem("Floyd");
		eventosOperaciones(dijkstra,kruskal,floyd);
		
		operaciones.add(dijkstra);
		operaciones.add(kruskal);
		operaciones.add(floyd);
		
		barraDeMenu.add(archivo);
		barraDeMenu.add(grafo);
		barraDeMenu.add(operaciones);
		return barraDeMenu;
	}
	
	private void eventoEjemplos(JMenuItem ejemplo,final String ruta) {
		ejemplo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				File path = new File(ruta);
				Scanner f1;
				try {
					f1 = new Scanner(path);
					abrir(f1);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		
	}

	private void eventosArchivo(JMenuItem nuevo,JMenuItem abrir, JMenuItem guardar,
			JMenuItem salir) {
		nuevo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				p.remove(canvas);
				canvas = new Grafo();
				p.add(canvas);
				setContentPane(p);
			}
		});
		
		abrir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					JFileChooser selecFichero = new JFileChooser();
					int i = selecFichero.showOpenDialog(Interfaz.this);
					if (i == 0) {
						File fich = selecFichero.getSelectedFile();
						try {
							Scanner f1 = new Scanner(fich);
							abrir(f1);
							canvas.cambiarEstado(0);
							canvas.setEtiqueta("Archivo abierto: "+fich.toString());
						}catch (FileNotFoundException e1) {
							JOptionPane.showMessageDialog(null,
									"Ese fichero no existe.", "Error",
									JOptionPane.ERROR_MESSAGE);
						} catch (Exception e2) {
							JOptionPane.showMessageDialog(null,
									"Error al leer el fichero.", "Error",
									JOptionPane.ERROR_MESSAGE);
						}
					
				}				
			}
		});
		guardar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					JFileChooser selecFichero = new JFileChooser();
					int i = selecFichero.showOpenDialog(Interfaz.this);
					if (i == 0) {
						File fich = selecFichero.getSelectedFile();
						PrintWriter f = new PrintWriter(new FileOutputStream(
								fich, true));
						guardar(f);
						canvas.cambiarEstado(0);
						canvas.setEtiqueta("Archivo guardado en: "+fich.toString());
						f.close();
						JOptionPane.showMessageDialog(null,
								"Se ha guardado en: " + fich.getPath());
					}
				} catch (FileNotFoundException e1) { 
					JOptionPane.showMessageDialog(null,
							"Fichero protegido contra escritura.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} catch (NullPointerException e2) { // Error al darle a
													// cancelar.
					JOptionPane.showMessageDialog(null, "Cancelado.",
							"Mensaje", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e2) { // No he podido reproducirlo.
					JOptionPane.showMessageDialog(null,
							"Error al guardar el fichero.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		salir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
	}
	
	private void eventosGrafos(JMenuItem vertice, JMenuItem arista,
			JMenuItem borrarV,JMenuItem borrarA) {
		vertice.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String nombreVertice = JOptionPane.showInputDialog("Introduce el nombre del vértice:");
				canvas.setNombreVertice(nombreVertice);
				canvas.cambiarEstado(3);
			}
		});
		arista.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					String peso = 
						JOptionPane.showInputDialog("Introduce el valor de la Arista:");
				int pesoArista = Integer.parseInt(peso);
				canvas.setPesoArista(pesoArista);
				canvas.cambiarEstado(1);				
				}
				catch (Exception e2) {
				JOptionPane.showMessageDialog(null,
						"Introduce un valor entero.", "Error",
						JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		borrarV.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				canvas.cambiarEstado(4);				
			}
		});
		borrarA.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.cambiarEstado(5);				
			}
		});
		
	}
	
	private void eventosOperaciones(JMenuItem dijkstra, JMenuItem kruskal, JMenuItem floyd) {
		dijkstra.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				canvas.cambiarEstado(7);
				
			}
		});
		kruskal.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Kruskal prueba = new Kruskal(canvas.dameAristas(), canvas.dameVertices(),canvas.getNumVertices());
				Set<Arista> sol = prueba.algoritmoKruskal();
				canvas.aplicarKruskal(sol);
			}
			
		});
		
		/////////////////////////////////////////////////////////
		floyd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				canvas.cambiarEstado(10);
				
			}
		});
	}
	
	public void abrir(Scanner f){
		// Leer vertices hasta "Aristas"
		int nfilas = 0;
		boolean leerV = true;
		Grafo gAux = new Grafo();
		while (f.hasNext()) {
			String s = f.nextLine();
			if (nfilas==0 && s.equals("Vertices")){
				// leido inicio
				nfilas++;
			}
			else if (s.equals("Aristas")){
				// empezamos con aristas.
				nfilas++;
				leerV = false;
			}
			else if (leerV){
				// bucle para leer vértices.
				Vertice vAux = extraerVertice(s);
				gAux.addVertice(vAux);
				nfilas++;
			}
			else if (!leerV){
				// bucle para leer aristas.
				Arista aAux = extraerArista(s,gAux);
				gAux.addArista(aAux);
				nfilas++;
			}
		}
		p.remove(canvas);
		this.canvas = gAux;
		p.add(canvas);
		this.setContentPane(p);
	}
	
	private Arista extraerArista(String s,Grafo g) {
		String[] array = s.split(";");
		if (array.length > 3){
			Vertice origen = extraerVertice(array[0]);
			Vertice destino = extraerVertice(array[1]);
			// buscar si existen en gAux
			Iterator<Vertice> itV = g.dameVertices();
			Vertice aux;
			boolean fin = false,
				estadoO = false,
				estadoD = false;
			while (itV.hasNext() && !fin){
				aux = itV.next();
				if (aux.equals(origen)&&!estadoO){
					origen = aux;
					estadoO = true;
				}
				if (aux.equals(destino)&&!estadoD){
					destino = aux;
					estadoD = true;
				}
				fin = estadoO && estadoD;
			}
			Arista aAux = new Arista(origen, destino, 
						Integer.parseInt(array[2]),
						new Color(Integer.parseInt(array[3])));
			return aAux;
		} else	return null;
	}

	private Vertice extraerVertice(String s){
		String[] array = s.split(":");
		if (array.length > 3){
		Vertice vAux = new Vertice(Integer.parseInt(array[1]),
				Integer.parseInt(array[2]), 
				new Color(Integer.parseInt(array[3])), 
				array[0]);
		return vAux;
		}
		else return null;
	}
	
	private void guardar(PrintWriter f){
		Iterator<Vertice> itV = canvas.dameVertices();
		f.println("Vertices");
		while(itV.hasNext()){
			Vertice vAux = itV.next();
			f.println(vAux.toString());
		}
		Iterator<Arista> itA = canvas.dameAristas();
		f.println("Aristas");
		while(itA.hasNext()){
			Arista aAux= itA.next();
			f.println(aAux.toString());
		}
	}
	
	public static void main(String[] args) {
		Interfaz form = new Interfaz();
		form.setSize(500, 500);
		form.setParametros();
		
	}



}
