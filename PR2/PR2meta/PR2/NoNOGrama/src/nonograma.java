import java.util.LinkedList;
import java.util.ListIterator;

import org.omg.PortableInterceptor.USER_EXCEPTION;

/**
 * 
 */

/**
 * @author
 *
 */
public class nonograma {
	tableroCanvas canvas;
	Integer[][] restFila;
	Integer[][] restColu;
	int nFilas, nCols, filaActual;
	int [][] tablero;
	int [] filaUndo;
	LinkedList<int[]> solFilas[];
	LinkedList<int[]> solCols[];
	int [][] solActual;
	boolean primero = true;
	ListIterator<int[]> itSolFilas[];
	ListIterator<int[]> itSolCols[];
	
	
	//boolean nonograma(tableroCanvas c, int[][] t, int nf, int nc){
	public nonograma(tableroCanvas c, int[][] t, Integer[][] restf, Integer[][]restc, int nf, int nc){
		canvas = c;
		tablero = t;
		restFila = restf;
		restColu = restc;
		nFilas = nf;
		nCols = nc;
	}
		
	
	public boolean nonogram(){
		// flase -> noHayMas Soluciones
		// true -> calcula la siguente solucion y la deja en solActual
		
		if (primero){
			calcularSolFilas();
			calcularSolCols();
			solActual = new int[nFilas][];
			primero = false;
			filaActual = 0;
		}
		
		return busquedaBackTracking();
		// return true;
	}
	
	@SuppressWarnings("unchecked")
	private void calcularSolFilas(){
		
		solFilas   = (LinkedList<int[]> [])   new LinkedList[nFilas];
		itSolFilas = (ListIterator<int[]> []) new ListIterator[nFilas];
		for(int i = 0; i < nFilas; i++){
			solFilas[i] = new LinkedList<int[]>();
			itSolFilas[i] = null;
			int tmn = myCountFila(i);
		
			int[] unaSol = new int[tmn];
			calcularSolFilas(i, 0, 0, unaSol, solFilas[i]);
		}
	}
	
	private int myCountFila(int i){
		int r = 0;
		for (int j = 0; j < restFila[i].length; j++) {
			if (restFila[i][j] != 0) {
				r++;
			}
		}
		return r;
	}	
	
	private int myCountColu(int i){
		int r = 0;
		for (int j = 0; j < restColu.length; j++) {
			if (restColu[j][i] != 0) {
				r++;
			}
		}
		return r;
	}
	
	@SuppressWarnings("unchecked")
	private void calcularSolCols(){
		
		solCols   = (LinkedList<int[]> [])   new LinkedList[nCols];
		itSolCols = (ListIterator<int[]> []) new ListIterator[nCols];
		for(int i = 0; i < nCols; i++){
			solCols[i] = new LinkedList<int[]>();
			itSolCols[i] = null;
			int tmn = myCountColu(i);
		
			int[] unaSol = new int[tmn];
			calcularSolCols(0, i, 0, unaSol, solCols[i]);
		}
	}
	
	private void calcularSolFilas(int fila, int col, int cont, int sol[], LinkedList<int[]> lista){
		if (cont == sol.length) {
			lista.add(sol.clone());
		} else 	{
			if ( col > nCols ) { 
				return;
			}			
			if ( restFila[fila][cont] <= - col + nCols ){
				sol[cont] = col;
				calcularSolFilas(fila, col + restFila[fila][cont] + 1, cont + 1, sol, lista);	
			}else{
				return; // Continue 
			}
			
			if (tablero[fila][col] <= 0){ 
				calcularSolFilas(fila, col + 1, cont, sol, lista);
			}
		}
	}
	private void calcularSolCols(int fila, int col, int cont, int sol[], LinkedList<int[]> lista){
		if (cont == sol.length) {
			lista.add(sol.clone());
		} else {
			if ( fila > nFilas ) {
				return;
			}
			if ( restColu[cont][col] <= - fila + nFilas ){
				sol[cont] = fila;
				calcularSolCols(fila + restColu[cont][col] + 1, col, cont + 1, sol, lista);	
			}else{
				return; // Continue 
			}
			
			if (tablero[fila][col] <= 0){ 
				calcularSolCols(fila + 1, col, cont, sol, lista);
			}
		}
	}
	
	boolean busquedaBackTracking(){
		if (filaActual == nFilas){
			filaActual--;
		}
		while (filaActual >= 0 && filaActual < nFilas){
			if (itSolFilas[filaActual] == null) {
				itSolFilas[filaActual] = solFilas[filaActual].listIterator();
			}
			if ( solActual[filaActual] != null){ // eso puede ser == y no != a saber!
				quitarFilaTablero(filaActual, solActual[filaActual]);
			}
			
			boolean aplicable = false;
			while (itSolFilas[filaActual] != null && itSolFilas[filaActual].hasNext() && !aplicable){
				solActual[filaActual] = itSolFilas[filaActual].next();
				
				if (esAplicable(filaActual, solActual[filaActual])){
					aplicable = true;
				}
				
				if(aplicable){
					ponerFilaTablero(filaActual, solActual[filaActual]);
					if ( 	(filaActual < nFilas - 1 && !conflicto() )
						||  (nFilas - 1 == filaActual && esValido()  )
						){
							filaActual++;
					} else {
						aplicable = false;
					}
				}
			} // 1er while
			
			if (filaActual >= nFilas){
				return true;
			} 
			
			if (!aplicable){
				itSolFilas[filaActual] = null;
				if ( filaActual >= 0 ){
					quitarFilaTablero(filaActual, solActual[filaActual]);
					solActual[filaActual] = null;
				}
				filaActual--;
			}
			
		} //2do while
		
		return false;
	}
	
	private boolean esValFil(){
		// Filas Valid
		for (int i = 0; i < nFilas; i++) {
			int user_black = 0;
			for (int j = 0; j < nCols; j++) {
				if ( tablero[i][j] < 0 ) { //Negro
					user_black++;
				}
			}
			
			int rest_black = 0;
			for (int j = 0; j < restFila[i].length; j++) {
				rest_black += restFila[i][j];
			}
			
			if (user_black != rest_black){
				return false;
			}
		}
		return true;
	}
	
	private boolean esValCol(){
		// Filas Valid
		for (int i = 0; i < nCols; i++) {
			int user_black = 0;
			for (int j = 0; j < nFilas; j++) {
				if ( tablero[j][i] < 0 ) { //Negro
					user_black++;
				}
			}
			
			int rest_black = 0;
			for (int j = 0; j < restColu.length; j++) {
				rest_black += restColu[j][i];
			}
			
			if (user_black != rest_black){
				return false;
			}
		}
		return true;
	}
	
	public boolean esValido(){
		return ( esValFil() && esValCol() );		
	}
	
	// ESTO ESTA MAL
	private void ponerFilaTablero(int filaActual, int[] solActual){
		filaUndo = tablero[filaActual].clone();
		for (int i = 0; i < solActual.length; i++) {
			tablero[filaActual][solActual[i]] = -1;
		}
	}
	
	// ESTO ESTA MAL
	private void quitarFilaTablero(int filaActual, int[] solActual){
		tablero[filaActual] = filaUndo;
		filaUndo = tablero[filaActual].clone();
	}
	
	private boolean esAplicable(int filaActual,int[] solActual){
		for (int i = 0; i < solActual.length; i++) {
			if ( tablero[filaActual][solActual[i]] > 0) { // es blanca
				return false;
			}
		}
		return true;
	}
	
	private boolean conflicto(){
		for (int i = 0; i < nCols; i++) {
			if ( conflictoCol(i) ) {
				return true;
			}
		}
		return false;
	}
	
	private boolean conflictoCol(int i){
		ListIterator<int[]> itSolCols = solCols[i].listIterator();
		while (itSolCols.hasNext()) {
			if (funcionaConUna(i, itSolCols.next())) {
				return false;
			}
		}
		return true;
	}
	
	private boolean funcionaConUna( int col, int[] solActualCol){
		for (int i = 0; i < solActualCol.length; i++) {
			if ( tablero[solActualCol[i]][col] <= 0 ){ //negro
				return true;
			}
		}
		return false;
	}

	public int cosas() {
		return 5 + 5;
	}
		
}
