import java.awt.*;
import 	java.io.*;
import java.awt.event.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class tableroCanvas extends Canvas implements MouseListener{
	int _nfilas,_ncols,_max_filas,_max_cols;
	int[][] tablero;
	Integer [][] _topRest;
	Integer [][] _leftRest;
	int k=50;
	int celda=25;
	int _sep = 2;
	int color;
	int _xOffSet;
	int _yOffSet;
	
	
	public tableroCanvas(){}
	
	public int getCelda(){
		return celda;
	}

	public int getCols(){
		return _ncols;
	}
	
	public void setCelda(int a){
		celda = a;
		repaint();
	}
	
	public tableroCanvas(int filas,int columnas){
		_nfilas = filas;
		_ncols = columnas;
		tablero = new int[_nfilas][_ncols];
		color=0;
		for(int i = 0; i < _nfilas; i++){
			for(int j = 0; j < _ncols; j++){
				tablero[i][j] = 0;
			}
		}
		
		if (_nfilas % 2 == 0 ){
			_leftRest = new Integer[_nfilas][_ncols/2];
		} else {
			_leftRest = new Integer[_nfilas][_ncols/2 +1];
		}
		
		if (_ncols % 2 == 0){
			_topRest = new Integer[_nfilas/2][_ncols];
			
		} else {
			_topRest = new Integer[_nfilas/2 +1][_ncols];
		}

		iniRest();		
		repaint();
		this.addMouseListener(this);
	}
	
	/* **************************************************
	 * *************** GETTERS **************************
	 * **************************************************/
	public int[][] getTablero(){
		return tablero;
	}
	public int getNfil(){
		return _nfilas;
	}
	public int getNcol(){
		return _ncols;
	}
	public Integer[][] getRestF(){
		return _leftRest;
	}
	public Integer[][] getRestC(){
		return _topRest;
	}
	
	/* **************************************************
	 * *************** METHODS **************************
	 * **************************************************/
	
	private void iniRest(){
		for (int i = 0; i < _topRest.length; i++){
			for (int j = 0; j < _topRest[i].length; j++){
				_topRest[i][j] = 0;
			}
		}
		for (int i = 0; i < _leftRest.length; i++){
			for (int j = 0; j < _leftRest[i].length; j++){
				_leftRest[i][j] = 0;
			}
		}
		
	}
	
	public void execInverse(){
		int count = 0;
		iniRest();
				
		for(int i = 0; i < _nfilas; i++){
			for(int j = 0; j < _ncols; j++){
				if(tablero[i][j] < 0) { // Negro
					_leftRest[i][count]++;
				} else // Blaco o Gris if (tablero[i][j] == 0) { // Blanco
					if (_leftRest[i][count] > 0 ){
						count++;
					}				
			}
			count = 0;
		}
		
		for(int i = 0; i < _ncols; i++){
			for(int j = 0; j < _nfilas; j++){
				if(tablero[j][i] < 0) { // Negro
					_topRest[count][i]++;
				} else // Blaco o Gris if (tablero[i][j] == 0) { // Blanco
					if (_topRest[count][i] > 0 ){
						count++;
					}				
			}
			count = 0;
		}
		
		repaint();
	}
	
	public void paint(Graphics G){
		if (_nfilas % 2 == 0 ){
			_yOffSet = celda*_nfilas/2 + _sep*(_nfilas/2 +1) + _sep * 3;
			paintRest(0
					, _yOffSet
					, _nfilas, _ncols / 2, G, _leftRest);				
		} else {
			_yOffSet = celda*(_nfilas/2 +1) + _sep*(_nfilas/2 +2) + _sep * 3;
			paintRest(0
					, _yOffSet
					, _nfilas, _ncols / 2 + 1, G, _leftRest);			
		}
		
		if (_ncols % 2 == 0){
			_xOffSet = celda*_ncols/2 + _sep*(_ncols/2 +1) + _sep * 3;
			paintRest( _xOffSet
					, 0
					, _nfilas / 2, _ncols, G, _topRest);	
		} else {
			_xOffSet = celda*(_ncols/2 +1)+ _sep*(_ncols/2 + 2) + _sep * 3;
			paintRest( _xOffSet
					, 0
					, _nfilas / 2 + 1, _ncols, G, _topRest);
		}
		
		paintCuad(_xOffSet
				, _yOffSet
				, _nfilas, _ncols, G);
	}
	
	private int getFilaFromPosition(int x, int y){
		if (_yOffSet > y){
			return -1;
		}
		return (( - _yOffSet + y) / (celda + _sep));
	}
	private int getColFromPosition(int x, int y){
		if (_xOffSet > x){
			return -1;
		}
		return (( - _xOffSet + x) / (celda + _sep));
	}
	
	public void mousePressed(MouseEvent e){
		int y = e.getY();
		int x = e.getX();
		Integer fila = getFilaFromPosition(x, y);
		Integer col =  getColFromPosition(x, y);
		
		if ((fila >= 0) && (col >= 0) && ( fila < _nfilas) && ( col < _ncols)){
			tablero[fila][col] = getNextState(tablero[fila][col]);
			repaint();
		}
		
	}
	
	private int getNextState(int state){
		if (state == 0){
			return -1;
		} else if (state > 0){
			return 0;
		} else {
			return 1;
		}
	}
	
	// Esta solo sirve para el tablero ; hacer otra que sea para pintar numeros
	public void paintCuad(int xOffSet, int yOffSet, int nfilas, int ncols, Graphics g){
		int ix = xOffSet;
		int jy = yOffSet;
		
		setPreferredSize(new Dimension((2*k)+(nfilas*celda)+_max_cols*celda,(2*k)+(nfilas*celda)+_max_cols*celda));
		
		g.setColor(new Color(0,0,0));
		g.fillRect(ix, jy, _sep, celda*nfilas + _sep*(nfilas + 1) );
		g.setColor(new Color(0,0,0));
		g.fillRect(ix, jy,  celda*ncols + _sep*(ncols + 1), _sep);
		jy+= _sep; 
		ix+= _sep; 
		for(int i = 0; i < nfilas; i++){
			for(int j = 0; j < ncols; j++){
				if (tablero[i][j] == 0) {
					g.setColor(new Color(150,150,150)); // gris
				} else if(tablero[i][j] < 0) {			// Negro
					g.setColor(new Color(50,50,50));
				} else {
					g.setColor(new Color(255,255,255)); // Blanco
				}
				g.fillRect(ix, jy, celda, celda);
				
				ix+= celda ;
				g.setColor(new Color(0,0,0));
				g.fillRect(ix, jy, _sep, celda );
				ix+= _sep;
			}
			jy+= celda;
			ix = xOffSet + _sep;
			g.setColor(new Color(0,0,0));
			g.fillRect(ix, jy, celda*ncols + _sep*ncols, _sep );
			jy+= _sep;
		}
		
		
	}
	
	public void paintRest(int xOffSet, int yOffSet, int nfilas, int ncols, Graphics g, Integer[][] currentRest){
		int ix = xOffSet;
		int jy = yOffSet;
		
		setPreferredSize(new Dimension((2*k)+(nfilas*celda)+_max_cols*celda,(2*k)+(nfilas*celda)+_max_cols*celda));
		
		g.setColor(new Color(0,0,0));
		g.fillRect(ix, jy, _sep, celda*nfilas + _sep*(nfilas + 1) );
		g.setColor(new Color(0,0,0));
		g.fillRect(ix, jy,  celda*ncols + _sep*(ncols + 1), _sep);
		jy+= _sep; 
		ix+= _sep; 
		for(int i = 0; i < nfilas; i++){
			for(int j = 0; j < ncols; j++){
				g.setColor(new Color(255,255,255));
				g.fillRect(ix, jy, celda, celda);
				g.setColor(new Color(0,0,0));
					if (currentRest[i][j] != 0) {
					g.drawString(currentRest[i][j].toString()
							, ix + (celda/4), jy + (celda*3/4));
					}
				ix+= celda ;
				g.setColor(new Color(0,0,0));
				g.fillRect(ix, jy, _sep, celda );
				ix+= _sep;
			}
			jy+= celda;
			ix = xOffSet + _sep;
			g.setColor(new Color(0,0,0));
			g.fillRect(ix, jy, celda*ncols + _sep*ncols, _sep );
			jy+= _sep;
		}	
	}
	
	public boolean saveMe(String path){
		try{
			FileWriter fstream = new FileWriter(path);
			BufferedWriter out = new BufferedWriter(fstream);
			Integer e = _nfilas; out.write(e.toString() + System.getProperty("line.separator"));
					e = _ncols; out.write(e.toString() + System.getProperty("line.separator"));
			
			for (int i=0; i < _nfilas; i++){
				for (int j=0; j < _ncols; j++){

					e = tablero[i][j];
					try{
						out.write(e.toString() + System.getProperty("line.separator"));
					}catch (Exception ex){//Catch exception if any
						System.err.println("Writing Error: " + ex.getMessage());
					}
				}
			}
			
			e = _topRest.length; out.write(e.toString() + System.getProperty("line.separator"));
			e = _topRest[0].length; out.write(e.toString() + System.getProperty("line.separator"));
			for (int i = 0; i < _topRest.length; i++){
				for (int j = 0; j < _topRest[i].length; j++){
					e = _topRest[i][j];
					try{
						out.write(e.toString() + System.getProperty("line.separator"));
					}catch (Exception ex){//Catch exception if any
						System.err.println("Writing Error: " + ex.getMessage());
					}
				}
			}
			
			e = _leftRest.length; out.write(e.toString() + System.getProperty("line.separator"));
			e = _leftRest[0].length; out.write(e.toString() + System.getProperty("line.separator"));
			for (int i = 0; i < _leftRest.length; i++){
				for (int j = 0; j < _leftRest[i].length; j++){
					e = _leftRest[i][j];
					try{
						out.write(e.toString() + System.getProperty("line.separator"));
					}catch (Exception ex){//Catch exception if any
						System.err.println("Writing Error: " + ex.getMessage());
					}
				}
			}
			
			out.close();
			return true;
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
			return false;
		}
	}
///////////////////////////////////// ABRIR SIN NADA EN CASILLAS i=0 EJEMPLO i=1 //////////////////////////////////////////////////
	public tableroCanvas(String path,int ejem){
		try{
			FileInputStream fstream = new FileInputStream(path);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			int col =0;
			int fil = 0;
			String strLine;
			if ((strLine = br.readLine()) != null){
				_nfilas = Integer.parseInt(strLine);
			}// else { return false; }
			if ((strLine = br.readLine()) != null){
				_ncols = Integer.parseInt(strLine);
			}// else { return false; }

			tablero = new int[_nfilas][_ncols];
			for (int i=0; i < _nfilas; i++){
				for (int j=0; j < _ncols; j++){
					try{
						strLine = br.readLine();
						if(ejem == 0){
							tablero[i][j] = 0;
						}else{
							tablero[i][j] = Integer.parseInt(strLine);
						}
						
					}catch (Exception ex){//Catch exception if any
						System.err.println("Writing Error: " + ex.getMessage());
					}
					
				}
			}
			
			if ((strLine = br.readLine()) != null){
				fil = Integer.parseInt(strLine);
			}// else { return false; }
			if ((strLine = br.readLine()) != null){
				col = Integer.parseInt(strLine);
			}// else { return false; }
			
			_topRest = new Integer[fil][col];
			for (int i=0; i < fil; i++){
				for (int j=0; j < col; j++){
					try{
						strLine = br.readLine();
						_topRest[i][j] = (Integer) Integer.parseInt(strLine);
					}catch (Exception ex){//Catch exception if any
						System.err.println("Writing Error: " + ex.getMessage());
					}
				}
			}
			
			if ((strLine = br.readLine()) != null){
				fil = Integer.parseInt(strLine);
			}// else { return false; }
			if ((strLine = br.readLine()) != null){
				col = Integer.parseInt(strLine);
			}// else { return false; }
			
			_leftRest = new Integer[fil][col];
			for (int i=0; i < fil; i++){
				for (int j=0; j < col; j++){
					try{
						strLine = br.readLine();
						_leftRest[i][j] = (Integer) Integer.parseInt(strLine);
					}catch (Exception ex){//Catch exception if any
						System.err.println("Writing Error: " + ex.getMessage());
					}
				}
			}		
			
			in.close();
			repaint();
			this.addMouseListener(this);
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		//	return false;
		}
		//return true;
	}
	
	/**
	 * My JavaScript Alert
	 * JUST FOR DEBUGING!
	 * @param st
	 */
	private void alert(String st){
		JOptionPane.showMessageDialog(null,st);
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
