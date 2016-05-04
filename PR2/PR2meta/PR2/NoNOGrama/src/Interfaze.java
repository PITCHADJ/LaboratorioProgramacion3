import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
//
/**
 * @author Andres y Adrian
 *
 */
@SuppressWarnings("serial")
public class Interfaze extends JFrame implements ActionListener{
	/*int restricciones_filas[][];
	int restricciones_colus[][];	*/
	tableroCanvas _canvas;
	JFrame F ;
	TextField txtField;
	JPanel _p;
	nonograma _nono;
	JMenu _Nonogram = new JMenu("Nonograma");
	JMenu _Zoom = new JMenu("Zoom: ");
	

	
	public static void main(String[] args){
		Interfaze I=new Interfaze();
		I.setEnabled(true);
		I.setVisible(true);
		I.setSize(500,500);
	}
	
	public Interfaze(){
		F = this;
		/*tableroCanvas gRestTop;
		tableroCanvas gRestLeft;*/
		_p=new JPanel();
		
		
		//_canvas = new tableroCanvas(11,9);
		
		F.setTitle("NoNoGrama");
		F.setEnabled(true);
		F.setVisible(true);
		F.setSize(800,800);
		_Nonogram.setEnabled(false);
		_Zoom.setEnabled(false);
		F.setJMenuBar(getMenu());
		
		F.setLayout(new BorderLayout());
        
        repaint();
        
	}
	
	
	
	private JMenuBar getMenu(){
		JMenuBar m = new JMenuBar();
		m.add(getFicheroMenu());
		m.add(getNonogramaMenu());
		m.add(getAyudaMenu());
		m.add(getZoom());/*
		m.add(getZoomMore());
		m.add(getZoomLess());*/
		
		return m;
	}
	
	/*  		MENU FICHEROS
	 * 			CON ITEMS
	 */
	private JMenu getFicheroMenu(){
		JMenu M = new JMenu("Fichero");
		//M.add(new JMenuItem("soy primer item"));
		M.add(getMenuItemEjemplo());
		M.add(getMenuItemNuevo());
		M.add(getMenuItemAbrir());
		M.add(getMenuItemGuardar());
		return(M);
	}
	private JMenu getMenuItemEjemplo(){
		JMenu item=new JMenu("Ejemplo");
		item.add(getMenuItemCara());
		item.add(getMenuItemCuadrado());
		item.add(getMenuItemTriangualo());
		return item;
	}
	
	private JMenuItem getMenuItemCara(){
		JMenuItem item=new JMenuItem("Cara");item.setEnabled(true);
		item.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				_Nonogram.setEnabled(true);
				_Zoom.setEnabled(true);
				_canvas = new tableroCanvas("cara.bin",1);	
				iniCanvas(_canvas.getCols(), true);
			}	
		});
		return item;
		
	}
	private JMenuItem getMenuItemCuadrado(){
		JMenuItem item=new JMenuItem("Cuadrado");
		item.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){	
				_Nonogram.setEnabled(true);
				_Zoom.setEnabled(true);
				_canvas = new tableroCanvas("cuadrado.bin",1);	
				iniCanvas(_canvas.getCols(), true);
			}	
		});
		return item;
	}
	private JMenuItem getMenuItemTriangualo(){
		JMenuItem item=new JMenuItem("Triangulo");
		item.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){	
				_Nonogram.setEnabled(true);
				_Zoom.setEnabled(true);
				_canvas = new tableroCanvas("triangulo.bin",1);	
				iniCanvas(_canvas.getCols(), true);
			}	
		});
		return item;
	}
	
	
	
	private JMenuItem getMenuItemNuevo(){
		JMenuItem item=new JMenuItem("Nuevo");
		item.setEnabled(true);
		item.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){		
				txtField = new TextField(20);
				_Nonogram.setEnabled(true);
				_Zoom.setEnabled(true);
				JFrame f = new JFrame();
				f.setEnabled(true);
				f.setVisible(true);
				f.setSize(300,300);
				f.setTitle("Tamaño NoNogRaMa");
				
				JPanel inside = damePanelTamanyo(f);
				
				f.add(inside);
			}	
		});
		
		return(item);
	}
	private JMenuItem getMenuItemAbrir(){
		JMenuItem item=new JMenuItem("Abrir");
		item.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){		
				JFileChooser ficher = new JFileChooser();
				_Nonogram.setEnabled(true);
				_Zoom.setEnabled(true);
				if (ficher.showSaveDialog(Interfaze.this) == 0) {
					String path = ficher.getSelectedFile().getAbsolutePath();
					_canvas = new tableroCanvas(path,0);	
					iniCanvas(_canvas.getCols(), true);
			        //_canvas.setVisible(true);
			        //_canvas.repaint();
				}
			}	
		});	
		return item;
	}
	
	
	
	private JMenuItem getMenuItemGuardar(){
		JMenuItem item=new JMenuItem("Guardar");
		
		item.setEnabled(true);
		item.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){		
				JFileChooser ficher = new JFileChooser();
				_Nonogram.setEnabled(true);
				_Zoom.setEnabled(true);
				if (ficher.showSaveDialog(Interfaze.this) == 0) {
					String path = ficher.getSelectedFile().getAbsolutePath();
					_canvas.saveMe(path);	
				}
			}	
		});	
		
		return item;
	}
	
	
	/*  		MENU NONOGRAMA
	 * 			CON ITEMS
	 */
	private JMenu getNonogramaMenu(){
		//_Nonogram = new JMenu("Nonograma");
		_Nonogram.add(getSubMenuItemDirecto());
		_Nonogram.add(getMenuItemInverso());
		_Nonogram.add(getMenuItemCheck());
		return(_Nonogram);
	}
	private JMenuItem getMenuItemCheck(){
		JMenuItem item=new JMenuItem("check");
		item.setEnabled(true);
		item.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){	
				_nono = new nonograma(_canvas, _canvas.getTablero()
						, _canvas.getRestF(), _canvas.getRestC(), _canvas.getNfil(), _canvas.getNcol());
				if (_nono.esValido()){
					alert("Tu tienes las casillas validas");
				} else {
					alert("Tu color de pelo es rozado, pero las casillas estan mal");
				}
			}	
		});
		return item;
	}
	private JMenu getSubMenuItemDirecto(){
		JMenu submenu=new JMenu("Directo");
		submenu.add(getMenuItemPrimera());
		submenu.add(getMenuItemSiguiente());
		return submenu;
	}
				/*
				 * 		CREAMOS EL SUBMENU DE NONOGRAMA DIRECTO
				 * 			PRIMERA Y SIGUIENTE
				 */
			private JMenuItem getMenuItemPrimera(){
				JMenuItem item=new JMenuItem("Primera");
				
				item.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						_nono = new nonograma(_canvas, _canvas.getTablero()
								, _canvas.getRestF(), _canvas.getRestC(), _canvas.getNfil(), _canvas.getNcol());
						if (_nono.nonogram()){
								_canvas.repaint();
						} else {
							alert("No hay solución");
						}
					}	
				});
				return item;
				
			}
			private JMenuItem getMenuItemSiguiente(){
				JMenuItem item=new JMenuItem("Siguiente");
				
				item.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){	
						if (_nono.nonogram()){
								_canvas.repaint();
						} else {
							alert("No hay más soluciones");
						}
					}	
				});
				return item;
			}
			
	private JMenuItem getMenuItemInverso(){
		JMenuItem item=new JMenuItem("Inverso");
		item.setEnabled(true);
		item.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){		
				_canvas.execInverse();
			}	
		});
		
		return item;
	}
	/*  		MENU AYUDA
	 * 			
	 */
	private JMenu getAyudaMenu(){
		JMenu M = new JMenu("Ayuda");
		return(M);
	}
	
	private JMenu getZoom(){
		//JMenu _Zoom = new JMenu("Zoom: ");
		_Zoom.add(getZoomMore());
		_Zoom.add(getZoomLess());
		return(_Zoom);
	}
	
	
		private JMenuItem getZoomMore(){
			JMenuItem M = new JMenuItem( "+ ");

			M.setEnabled(true);
			M.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					int a = _canvas.getCelda();
					_canvas.setCelda(a+5);
				}	
			});
			
			return(M);
		}
		private JMenuItem getZoomLess(){
			JMenuItem M = new JMenuItem(" - ");

			M.setEnabled(true);
			M.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					int a = _canvas.getCelda();
					_canvas.setCelda(a-5);
				}	
			});
			return(M);
		}
	
	private void iniCanvas(int cols, boolean abrir){
		if (!abrir){
			_canvas = new tableroCanvas(cols, cols);
		}		
		
		_p.removeAll();
		_p.setLayout(new BorderLayout());
		_p.validate();
		_p.add("Center", _canvas);
        F.add("Center", _p);
		F.setVisible(true);
        _canvas.setVisible(true);
        _canvas.repaint();
	}
	
	
	public void actionPerformed(ActionEvent e) {}
	private JPanel damePanelTamanyo(JFrame father){
		JPanel inside = new JPanel();
		Button btn = new Button("Ok");
		final JFrame f = father;
		
		inside.setLayout(new FlowLayout());
		inside.add(new Label("Tamaño:. "));
		inside.add(txtField);
		inside.add(btn);
		btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String val;
				int cols = 0;
			    try {
			    	val = txtField.getText().isEmpty() ? "0" : txtField.getText();
			    	cols = Integer.parseInt(val);
			    	iniCanvas(cols, false);
			    } catch(NumberFormatException err) { 
			    	alert("Somehow theres been a terrible accidente in torrejon, sorry");
			    }
				
				f.dispose();
			}
			
		});
		
		return(inside);
	}	
/**
 * My JavaScript Alert
 * JUST FOR DEBUGING!
 * @param st
 */
	private void alert(String st){
		JOptionPane.showMessageDialog(null,st);
	}
}