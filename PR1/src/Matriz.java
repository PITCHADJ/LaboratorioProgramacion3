import 	java.io.*;

//import javax.swing.JOptionPane;


/**
 * @author Adrian y Andres
 * Desarrollaremos aqui la clase de la matriz
 *
 */
public class Matriz {
	int[][] data;
	int size;
	
	public Matriz(){
		data = new int[0][0];
	}
	public Matriz(int size){
		data = new int[size][size];
		this.size= size;
	}

	//PonerValor(fila,col)
	public void setVal(int i, int j, int n){
		data[i][j]= n;
	}
	
	//leerValor(fila,col)
	public int getVal(int i, int j){
		return data[i][j];
	}
	
	//copiar
	public Matriz clone(Matriz Extra){
		Matriz res = new Matriz(size);
		Integer resNum;
		for (int i=0; i < size; i++){
			for (int j=0; j < size; j++){
				resNum = data[i][j];// - Extra.getVal(i,j);
				res.setVal(i,j,resNum);
			}
		}
		return res;
	}
	
	//restar
	public Matriz res(Matriz Extra){
		Matriz res = new Matriz(size);
		Integer resNum;
		for (int i=0; i < size; i++){
			for (int j=0; j < size; j++){
				resNum = data[i][j] - Extra.getVal(i,j);
				res.setVal(i,j,resNum);
			}
		}
		return res;
	}
	
	//sumar
	public Matriz add(Matriz Extra){
		Matriz res = new Matriz(size);
		Integer resNum;
		for (int i=0; i < size; i++){
			for (int j=0; j < size; j++){
				resNum = data[i][j] + Extra.getVal(i,j);
				res.setVal(i,j,resNum);
			}
		}
		return res;
	}
	
	//multplicar
	public Matriz mul(Matriz Extra){
		Matriz res = new Matriz(size);
		int resNum;
		for (int i=0; i<size;i++){
			for(int j=0; j< size; j++){
				 resNum=0;
				for(int h=0;h<size;h++){
					resNum = resNum + data[i][h]*Extra.getVal(h, j);
				}
				res.setVal(i, j, resNum);
			}
		}
		return res;
	}
	
	//@todo more cute xD
	private boolean umbral(){
		return (size<=6 || size%2!=0);
	}
	
	//SpecialMultiply
	public Matriz mulSp(Matriz Extra){
		Matriz res = new Matriz(size);
		Matriz A11,A12,A21,A22,B11,B12,B21,B22,S1,S2,S3,S4,S5,S6,S7,S8,S9,S10,M1,M2,M3,M4,M5,M6,M7;
		Matriz C11,C12,C21,C22;
		if (umbral()){
			res=this.mul(Extra);
		} else {
			A11=new Matriz(size/2);
			A12=new Matriz(size/2);
			A21=new Matriz(size/2);
			A22=new Matriz(size/2);
			B11=new Matriz(Extra.getSize()/2);
			B12=new Matriz(Extra.getSize()/2);
			B21=new Matriz(Extra.getSize()/2);
			B22=new Matriz(Extra.getSize()/2);
			
			//@todo render 
			for(int i=0;i<(size/2);i++){
				for (int j= 0; j<(size/2);j++){
					A11.setVal(i, j, this.getVal(i, j));
					B11.setVal(i, j, Extra.getVal(i, j));
				}
			}
			for(int i=0;i<(size/2);i++){
				for (int j=(size/2);j<(size);j++){
					A12.setVal(i, (j-(size/2)), this.getVal(i, j));
					B12.setVal(i, (j-(size/2)), Extra.getVal(i, j));
				}
			}
			for(int i=(size/2);i<size;i++){
				for (int j= 0; j<(size/2);j++){
					A21.setVal((i-(size/2)), j, this.getVal(i, j));
					B21.setVal((i-(size/2)), j, Extra.getVal(i, j));
				}
			}
			for(int i=(size/2);i<size;i++){
				for (int j= (size/2);j<(size);j++){
					A22.setVal((i-(size/2)), (j-(size/2)), this.getVal(i, j));
					B22.setVal((i-(size/2)), (j-(size/2)), Extra.getVal(i, j));
				}
			}
			S1=new Matriz(size/2);
			S2=new Matriz(size/2);
			S3=new Matriz(size/2);
			S4=new Matriz(size/2);
			S5=new Matriz(size/2);
			S6=new Matriz(size/2);
			S7=new Matriz(size/2);
			S8=new Matriz(size/2);
			S9=new Matriz(size/2);
			S10=new Matriz(size/2);
			S1=A11.add(A22);
			S2=B11.add(B22);
			S3=A21.add(A22);
			S4=B12.res(B22);
			S5=B21.res(B11);
			S6=A11.add(A12);
			S7=A21.res(A11);
			S8=B11.add(B12);
			S9=A12.res(A22);
			S10=B21.add(B22);
			
			M1=new Matriz(size/2);
			M2=new Matriz(size/2);
			M3=new Matriz(size/2);
			M4=new Matriz(size/2);
			M5=new Matriz(size/2);
			M6=new Matriz(size/2);
			M7=new Matriz(size/2);
			M1=S1.mulSp(S2);
			M2=S3.mulSp(B11);
			M3=A11.mulSp(S4);
			M4=A22.mulSp(S5);
			M5=S6.mulSp(B22);
			M6=S7.mulSp(S8);
			M7=S9.mulSp(S10);
			C11=new Matriz(size/2);
			C12=new Matriz(size/2);
			C21=new Matriz(size/2);
			C22=new Matriz(size/2);
			
			C11=M1.add(M4.add(M7.res(M5)));
			C12=M3.add(M5);
			C21=M2.add(M4);
			C22=M1.add(M3.add(M6.res(M2)));
			
			//RECOMPONEMOS UNA MATRIZ DE TAMAÑO SIZE X SIZE
			
			for(int i=0;i<(size/2);i++){
				for (int j= 0; j<(size/2);j++){
					res.setVal(i, j, C11.getVal(i, j));
				}
			}
			for(int i=0;i<(size/2);i++){
				for (int j= (size/2);j<(size);j++){
					res.setVal(i, j, C12.getVal(i, (j-(size/2)) ));
				}
			}
			for(int i=(size/2);i<size;i++){
				for (int j= 0; j<(size/2);j++){
					res.setVal(i, j, C21.getVal( (i-(size/2)), j));
				}
			}
			for(int i=(size/2);i<size;i++){
				for (int j= (size/2);j<(size);j++){
					res.setVal(i, j, C22.getVal( (i-(size/2)), (j-(size/2)) ));
				}
			}
			
			
		}
		return (res);
	}
	
	//getSize
	public int getSize(){
		return size;
	}
	
	public boolean saveMe(String path){
		try{
			FileWriter fstream = new FileWriter(path);
			BufferedWriter out = new BufferedWriter(fstream);
			Integer el = size;
			out.write(el.toString() + System.getProperty("line.separator"));
			for (int i=0; i < size; i++){
				for (int j=0; j < size; j++){
					try{
						el = data[i][j];
						out.write(el.toString() + System.getProperty("line.separator"));
					}catch (Exception e){//Catch exception if any
						System.err.println("Writing Error: " + e.getMessage());
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
	
	public boolean readMe(String path){
		try{
			FileInputStream fstream = new FileInputStream(path);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			int ix = 0;
			String strLine;
			if ((strLine = br.readLine()) != null){
				size = Integer.parseInt(strLine);
			}
			while ((strLine = br.readLine()) != null)   {
				int i = ix / size;
				int j = ix % size;
				ix++;
				data[i][j] = Integer.parseInt(strLine);
			}

			in.close();
			return true;
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
			return false;
		}
	}
}
