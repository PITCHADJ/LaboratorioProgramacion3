import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.JFileChooser;

/**
 * 
 */

/**
 * @author Adrian y Andres
 * Desarrollaremos aqui la interfaz
 *
 */
public class ClasePrincipal extends JFrame implements ActionListener  {
	JMenuItem _tamanyoItem;
	Integer _fila, _columna;
	TextField _txtField_columna, _txtField_fila;
	Boolean _loaded = false;
	DefaultTableModel _tmUno = new DefaultTableModel();
	DefaultTableModel _tmDos = new DefaultTableModel();
	DefaultTableModel _tmRes = new DefaultTableModel();
	Matriz _matA, _matB, _matR;
	
	JPanel _panelPrincipal;
	JTabbedPane _panelTabulado;
	
	JMenu menufich=new JMenu("Ficheros");
	JMenu menuoper=new JMenu("Operar");
	JMenuItem itemcargar = new JMenuItem("Cargar");
	JMenuItem iteminit = new JMenuItem("Inicializar");
	
	
/**
 * Constructora
 */
	public ClasePrincipal(){
		alert("Recuerde introducir tamaño.");
		InicializarInterfaz();
		
	}
	
/**
 * My JavaScript Alert
 * JUST FOR DEBUGING!
 * @param st
 */
	private void alert(String st){
		JOptionPane.showMessageDialog(null,st);
	}
	
/**
 * Es la ventana Principal. La grandooootototota
 */
	public void InicializarInterfaz(){
		_panelTabulado = new JTabbedPane();
		this.setTitle("Calculadora de Matrices");
		this.setEnabled(true);
		this.setVisible(true);
		this.setSize(500,500);
		//BOQUEAMOS LOS MENUS QUE NO PODEMOS UTILIZAR HASTA TENER MATRICES DE TAMAÑO X.
		menufich.setEnabled(false);
		menuoper.setEnabled(false);
		itemcargar.setEnabled(false);
		iteminit.setEnabled(false);
		this.setJMenuBar(dameBarraMenu());
		this.damePanelPrincipal();
		this.setContentPane(_panelPrincipal);
	}
	
/**
 * Genera la barra del menu
 * @return JMenuBar
 */
	private JMenuBar dameBarraMenu(){
		JMenuBar barra=new JMenuBar();
		barra.add(getMenuMatrices());
		barra.add(getMenuOperar());
		barra.add(getMenuFicheros());
		
		return barra;
	}	

/**
 * Coge el item para sumar en el menu
 * @return
 */
	private JMenuItem getSumarItem(){
		JMenuItem item = new JMenuItem("Sumar");
		item.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){		
				_matR = _matA.add(_matB);
				showRes();
			}	
		});
		return item;
	}

/**
 * Coge el item para restar en el menu
 * @return
 */
	private JMenuItem getRestartItem(){
		JMenuItem item = new JMenuItem("Restar");
		item.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){		
				_matR = _matA.res(_matB);
				showRes();
			}	
		});
		return item;
	}

/**
 * Coge el item para multiplicar normal
 * @return
 */
	private JMenuItem getMulNormalItem(){
		JMenuItem item = new JMenuItem("Normal");
		item.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){		
				_matR = _matA.mul(_matB);
				showRes();
			}	
		});
		return item;
	}

/**
 * Coge el item para multiplicar strassen
 * @return
 */
	private JMenuItem getMulStrassenItem(){
		JMenuItem item = new JMenuItem("Strassen");
		item.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){		
				_matR = _matA.mulSp(_matB);
				showRes();
			}	
		});		
		return item;
	}

/**
 * Coge el sub-menu para multiplicar en el menu
 * @return
 */
	private JMenu getMultiplicarSubMenu(){
		JMenu subMenu = new JMenu("Sub menu Multiplicar");
		subMenu.add(getMulNormalItem());
		subMenu.add(getMulStrassenItem());
		
		return subMenu;
	}
	
/**
 * Genera el menu de matricez
 * @return JMenu
 */
		private JMenu getMenuOperar(){
			menuoper.add(getSumarItem());
			menuoper.add(getRestartItem());
			menuoper.add(getMultiplicarSubMenu());
			
			return menuoper;
		}


	private JMenuItem getMat2ItemR(){
		JMenuItem item = new JMenuItem("Segunda matriz");
		item.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){		
				JFileChooser ficher = new JFileChooser();
				
				Integer err = ficher.showSaveDialog(ClasePrincipal.this);
				if (err == 0){
					String path = ficher.getSelectedFile().getAbsolutePath();
						
					_matB.readMe(path);	
					if ( (_matB.getSize() > _columna) || (_matB.getSize() > _fila) ){
						alert("Your loaded Matrix has a bigger size");
					} else {
						_columna = _matB.getSize();
						_fila = _matB.getSize();
						
						_tmDos.setColumnCount(0);
						_tmDos.setRowCount(0);
						_panelTabulado.setComponentAt(1, damePanelTabla(_tmDos));
						for (int i=0; i < _fila; i++){
							for (int j=0; j < _columna; j++){
								_tmDos.setValueAt(_matB.getVal(i, j), i, j);
							}
						}
					}
				}
			}	
		});	
		
		return item;
	}
	

	private JMenuItem getMat1ItemR(){
		JMenuItem item = new JMenuItem("Primera matriz");
		item.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){		
				JFileChooser ficher = new JFileChooser();
				
				Integer err = ficher.showSaveDialog(ClasePrincipal.this);
				if (err == 0){
					String path = ficher.getSelectedFile().getAbsolutePath();
					_matA.readMe(path);	
					if ( (_matA.getSize() > _columna) || (_matA.getSize() > _fila) ){
						alert("Your loaded Matrix has a bigger size");
					} else {
						_columna = _matA.getSize();
						_fila = _matA.getSize();
						
						_tmUno.setColumnCount(0);
						_tmUno.setRowCount(0);
						_panelTabulado.setComponentAt(0, damePanelTabla(_tmUno));
						for (int i=0; i < _fila; i++){
							for (int j=0; j < _columna; j++){
								_tmUno.setValueAt(_matA.getVal(i, j), i, j);
							}
						}
					}
				}
			}	
		});		
		
		return item;
	}


	private JMenuItem getMat2ItemS(){
		JMenuItem item = new JMenuItem("Segunda matriz");
		item.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){		
				JFileChooser ficher = new JFileChooser();
				
				Integer err = ficher.showSaveDialog(ClasePrincipal.this);
				if (err == 0){
					String path = ficher.getSelectedFile().getAbsolutePath();
					_matB.saveMe(path);
				}
			}	
		});	
		
		return item;
	}


	private JMenuItem getMat1ItemS(){
		JMenuItem item = new JMenuItem("Primera matriz");
		item.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){		
				JFileChooser ficher = new JFileChooser();
				
				Integer err = ficher.showSaveDialog(ClasePrincipal.this);
				if (err == 0){
					String path = ficher.getSelectedFile().getAbsolutePath();
					_matA.saveMe(path);
				}
			}	
		});	
		
		return item;
	}


	private JMenuItem getMatRrItemS(){
		JMenuItem item = new JMenuItem("Matriz Resultante");
		item.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){		
				JFileChooser ficher = new JFileChooser();
				
				Integer err = ficher.showSaveDialog(ClasePrincipal.this);
				if (err == 0){
					String path = ficher.getSelectedFile().getAbsolutePath();
					_matR.saveMe(path);
				}
				
			}	
		});	
		
		return item;
	}


	private JMenu getFichGuardarMenu(){
		JMenu submenu = new JMenu("Guardar");
		submenu.add(getMat1ItemS());
		submenu.add(getMat2ItemS());
		submenu.add(getMatRrItemS());
		
		return submenu;
	}


	private JMenu getFichLeerMenu(){
		JMenu submenu = new JMenu("Leer");
		submenu.add(getMat1ItemR());
		submenu.add(getMat2ItemR());
		
		return submenu;
	}


	private JMenuItem getFichDefaultItem(){
		JMenuItem item = new JMenuItem("Default");
		item.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){	
				_matA.readMe("matriz1.txt");
				_matB.readMe("matriz2.txt");
				if ( (_matA.getSize() > _columna) || (_matA.getSize() > _fila) || (_matA.getSize() != _matB.getSize()) ){
					alert("file has been manually wrongly moddified!!!");
				} else {
					_columna = _matA.getSize();
					_fila = _matA.getSize();
					
					_tmUno.setColumnCount(0);	_tmDos.setColumnCount(0);
					_tmUno.setRowCount(0);		_tmDos.setRowCount(0);
					_tmRes.setRowCount(0);		_tmRes.setColumnCount(0);
					_panelTabulado.setComponentAt(0, damePanelTabla(_tmUno));
					_panelTabulado.setComponentAt(1, damePanelTabla(_tmDos));
					_panelTabulado.setComponentAt(2, damePanelTabla(_tmRes));
					for (int i=0; i < _fila; i++){
						for (int j=0; j < _columna; j++){
							_tmUno.setValueAt(_matA.getVal(i, j), i, j);
							_tmDos.setValueAt(_matB.getVal(i, j), i, j);
							_tmRes.setValueAt(0, i, j);
						}
					}
				}
			}	
		});
		
		return item;
	}
	

	private JMenu getMenuFicheros(){
		menufich.add(getFichLeerMenu());
		menufich.add(getFichGuardarMenu());
		menufich.add(getFichDefaultItem());
		
		return menufich;
	}


	private JMenuItem getInicializarMenuItem(){
		iteminit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){		
				inirMatrices();
			}	
		});
		
		return iteminit;
	}

/**
 * Coge el item para restar en el menu
 * @return
 */
	private JMenuItem getCargarMenuItem(){
		itemcargar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){		
				cargadorMatrices();
			}	
		});
		
		return itemcargar;
	}
	

	private JMenu getMenuMatrices(){
		JMenu menu=new JMenu("Matrices");
		menu.add(getTamanyoMenuItem());
		menu.add(getCargarMenuItem());
		menu.add(getInicializarMenuItem());
		
		return menu;
	}
	
/**
 * Genera la ventana de tamaño de matrices
 * esta ventana tiene que recoger las filas y las columnas
 * de las matrices con las que vamos a trabajar.
 * 
 * @return
 */
	private JMenuItem getTamanyoMenuItem(){
		_tamanyoItem = new JMenuItem("Tamaño");
		_tamanyoItem.setEnabled(true);
		_tamanyoItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){		
				_txtField_fila = new TextField(20);
				_txtField_columna = new TextField(20);
				
				JFrame f = new JFrame();
				f.setEnabled(true);
				f.setVisible(true);
				f.setSize(300,300);
				f.setTitle("Tamaño matrices");
				
				JPanel inside = damePanelTamanyo(f);
				
				f.add(inside);
			}	
		});
		
		return(_tamanyoItem);
	}
	
	public void actionPerformed(ActionEvent e) {}
	private JPanel damePanelTamanyo(JFrame father){
		JPanel inside = new JPanel();
		Button btn = new Button("Lixto");
		final JFrame f = father;
		
		inside.setLayout(new FlowLayout());
		inside.add(new Label("Filas:. "));
		inside.add(_txtField_fila);
		inside.add(new Label("Columnas:. "));
		inside.add(_txtField_columna);
		inside.add(btn);
		btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String val;
			    try {
			    	val = _txtField_fila.getText().isEmpty() ? "0" : _txtField_fila.getText();
			    	_fila = Integer.parseInt(val); 
			    } catch(NumberFormatException err) { 
			    	_fila = 0;
			    }
					
				try { 
					val = _txtField_columna.getText().isEmpty() ? "0" :  _txtField_fila.getText();
					_columna = Integer.parseInt(val); 
			    } catch(NumberFormatException err) { 
			    	_columna = 0; 
			    }
				
				// Ahora hay que crear el panel de tablas
				_matA = new Matriz(_fila);
				_matB = new Matriz(_fila);
				_matR = new Matriz(_fila);
				_panelTabulado.removeAll();
				_tmUno.setRowCount(0);_tmUno.setColumnCount(0);
				_tmDos.setRowCount(0);_tmDos.setColumnCount(0);
				_tmRes.setRowCount(0);_tmRes.setColumnCount(0);
				_panelTabulado.add("Matriz Uno", damePanelTabla(_tmUno));
				_panelTabulado.add("Matriz Dos", damePanelTabla(_tmDos));
				_panelTabulado.add("Matriz Resultante", damePanelTabla(_tmRes));
				// HABILITAMOS EL MENU FICHEROS,CARGAR E INICIALIZAR CUANDO TENEMOS TAMAÑO
				menufich.setEnabled(true);
				itemcargar.setEnabled(true);
				iteminit.setEnabled(true);
				menuoper.setEnabled(true);
				
				f.dispose();
			}
			
		});
		
		return(inside);
	}
	 
	private void damePanelPrincipal(){
		_panelPrincipal = new JPanel();
		
		_panelPrincipal.setLayout(new BorderLayout());
		_panelPrincipal.add("Center", _panelTabulado);
		_panelPrincipal.validate();
	}
	
	private JPanel damePanelTabla(DefaultTableModel tm){
		JPanel aux = new JPanel();
		JTable tabla = new JTable(tm);
		
		aux.setLayout(new BorderLayout());
		for (int i = 0; i < _columna; i++){
			tm.addColumn(new String(""));
		}
		for (int i = 0; i < _fila; i++){
			tm.addRow(new String[_columna]);
		}
		aux.add("Center", tabla);
		aux.validate();
		
		return aux;
	}
	
/**
 * Yo soy el que saca los datos de las matrices 
 * y te los mete en un super array
 */
	private void cargadorMatrices(){
		String val;
		Integer num1, num2;
		for (int i=0; i < _fila; i++){
			for (int j=0; j < _columna; j++){
				try {
			    	val = _tmUno.getValueAt(i, j).toString(); //.isEmpty()? "0" : _tmUno.getValueAt(i, j).toString();
			    	num1 = Integer.parseInt(val); 
			    } catch(NumberFormatException err) { 
			    	num1 = 0;
			    } catch (NullPointerException err2){
			    	num1 = 0;
			    }
				try {
			    	val = _tmDos.getValueAt(i, j).toString();
			    	num2 = Integer.parseInt(val); 
			    } catch(NumberFormatException err) { 
			    	num2 = 0;
			    } catch (NullPointerException err2){
			    	num2 = 0;
			    }
				_matA.setVal(i, j, num1);
				_matB.setVal(i, j, num2);
				_loaded = true;
				
			}
		}
		
	}
	

	private void showRes(){
		Integer n;
		for (int i=0; i < _fila; i++){
			for (int j=0; j < _columna; j++){
				n =_matR.getVal(i, j);
				_tmRes.setValueAt(n, i, j);
			}
		}
	}
	
	public void inirMatrices(){
		for (int i=0; i < _fila; i++){
			for (int j=0; j < _columna; j++){
				_matA.setVal(i, j, 0);
				_tmUno.setValueAt(0, i, j);
				_matB.setVal(i, j, 0);
				_tmDos.setValueAt(0, i, j);
			}
		}
	}
	
/**
 * Inicia nuestra ventanita magica y la muestra
 * @param args
 */
	public static void main(String[] args){
		ClasePrincipal f = new ClasePrincipal();
		f.setEnabled(true);
		f.setVisible(true);
		f.setSize(500,500);
	}
}