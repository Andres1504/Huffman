
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JOptionPane;


public class Huffman {
	
	 private String rellenar(String cadena){
		 String nueva="";
		 for(int i=0;i<8-cadena.length();i++){
			 nueva+="0";
		 }
		 nueva+=cadena;
		 return nueva;
	 }
	 
	 private String quitar(String cad,Integer n){
		 String nueva="";
		 for(int i=n;i<cad.length();i++)
			 nueva+=cad.charAt(i);
		 return nueva;
	 }

	public String[] Comprimir(String mensaje) throws RangoNulo, ListaVacia, IOException, NombreNulo{
		
		Lista cadena=new Lista();
		cadena.toLista(mensaje);
		cadena.recorrer();
		ListaArbol lista=new ListaArbol();
		lista.toListaArbol(cadena);		
		lista.obtenerArbol();
		ListaTabla tabla=new ListaTabla();
		tabla=lista.obtenerHojas("", tabla, lista.getInicio().getArbol().getRaiz());
		String direcciones=tabla.unirTodo(mensaje);
		String archivo=JOptionPane.showInputDialog(null,"Escribe el nombre del archivo","Crear Archivo",JOptionPane.QUESTION_MESSAGE);
		if(archivo.length()==0){
			JOptionPane.showMessageDialog(null, "Error, debes escribir un nombre","Error",JOptionPane.ERROR_MESSAGE);
			throw new NombreNulo();
		}
		
		String codificado=escribir(direcciones,tabla,archivo+".txt");
		String[] array={direcciones,codificado};
		return array;
	}
	
	public String Descomprimir() throws IOException, NombreNulo{
		String archivo=JOptionPane.showInputDialog(null,"Escribe el nombre del archivo","Abrir Archivo",JOptionPane.QUESTION_MESSAGE);
		if(archivo.length()==0){
			JOptionPane.showMessageDialog(null, "Error, debes escribir un nombre","Error",JOptionPane.ERROR_MESSAGE);
			throw new NombreNulo();
		}
		File doc=new File(archivo+".txt");
	    FileReader fr= new FileReader(doc);
	    BufferedReader lector=new BufferedReader(fr);
	    
	    String apoyo2=lector.readLine();
	    Integer falta=Integer.parseInt(lector.readLine());
	    
	    String apoyo="";
	    String mensaje="";
	    byte bit2;
	    
	    for(int i=0;i<apoyo2.length();i++){
	    	bit2=(byte)apoyo2.charAt(i);
			apoyo=Integer.toBinaryString(bit2 & 0xFF);
			if(apoyo.length()<8){
				apoyo=rellenar(apoyo);
				if(i==(apoyo2.length()-1))
					apoyo=quitar(apoyo,falta);
			}
			mensaje+=apoyo;
	    }
	    ListaTabla tabla=new ListaTabla();
	    apoyo=lector.readLine();
	    int a=0;
	
	    while(apoyo!=null){
	    	tabla.agregar(apoyo.charAt(0)+"", quitar(apoyo,1));
	    	apoyo=lector.readLine();
	    }
	    JOptionPane.showMessageDialog(null, "Archivo encontrado: \nnombre: "+archivo+".txt");
		apoyo="";
		String original="";
		apoyo2="";
		for(int i=0;i<mensaje.length();i++){
			apoyo=apoyo+mensaje.charAt(i);
			apoyo2=tabla.LetraDe(apoyo);
			if(apoyo2!=null){
				original=original+apoyo2;
				apoyo="";
				apoyo2="";
			}
		}
		return original;
	}
	 private String escribir(String mensaje,ListaTabla tabla,String nombre) throws IOException{
	        File f=new File(nombre);
	        FileWriter w=new FileWriter(f);
	        BufferedWriter bw=new BufferedWriter(w);
	        PrintWriter escritor=new PrintWriter(bw);
	        String bits="";
	        String codificado="";
	        int falta=0;
	        byte bin;
	        char car;
	        for(int i=0;i<mensaje.length();i++){
	        	if(bits.length()==8){
	        		bin = (byte)Short.parseShort(bits, 2);
	        		car=(char)(bin & 0xFF);
	        		codificado+=car;
	        		bits="";
	        		i--;
	        	}else
	        		bits+=mensaje.charAt(i);
	        }
			if(bits.length()!=0){
				bin = (byte)Short.parseShort(bits, 2);
        		car=(char)(bin & 0xFF);
        		codificado+=car;
        		falta=8-bits.length();
			}
			escritor.write(codificado+"\n");
			escritor.write(falta+"\n");
			NodoTabla aux=tabla.getInicio();
	        while(aux!=null){
	        	escritor.write(aux.getDato()+aux.getUbicacion()+"\n");
	        	aux=aux.getSiguiente();
	        }
	        escritor.close();
	        bw.close();
	        JOptionPane.showMessageDialog(null, "Archivo generado exitosamente!!!\n nombre: "+nombre);
	        return codificado;
	    }
}
