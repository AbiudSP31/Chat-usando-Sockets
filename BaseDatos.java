import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BaseDatos{
	public File f;
	public String line;
	public ArrayList<Usuario> clientesRegistrados=new ArrayList<>();

	public BaseDatos(){
		f=new File("bd.txt");
	}

	public ArrayList<Usuario> leerArchivo(){
		String[] st;
		clientesRegistrados.clear();
		try{
			DataInputStream in = new DataInputStream(new FileInputStream(f));  
			while ((line = in.readLine()) != null) {                      	            
	            st=line.split("//");
	            if(st[0].length()>0){
	            	Usuario user = new Usuario(st[0], st[1]);
		            int i=2;
		            while(i<st.length){
		            	if(st[i].length()>0)
		            		user.nuevoContacto(st[i]);
		            	i++;
		            }
	            	clientesRegistrados.add(user);
	            }      	            	
	        }
		}
		catch (IOException e) { 
            System.out.println(e.toString());
        }
        return clientesRegistrados;
	}

	public void escribirArchivo(ArrayList<Usuario> ur){
		limpiarArchivo();
		try{
			PrintStream out = new PrintStream(new FileOutputStream("bd.txt"));
			for(int i=0;i<ur.size();i++){
				String esc=ur.get(i).getNombre();
				esc = esc + "//" + ur.get(i).getPass()+"//";
				ArrayList<String> contactos = ur.get(i).getContactos();
				for(int k=0; k<contactos.size();k++){
					if(contactos.get(k).length()>0)
						esc = esc + contactos.get(k) + "//";					
				}
				out.println(esc);
			}
			out.close();
		}
		catch (IOException e) { }
	}

	public void limpiarArchivo(){
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter("bd.txt"));
			bw.write("");
			bw.close();
		}
		catch (IOException e) { }
	}

	public void actualizarArchivo(Usuario nuevo){
		ArrayList<Usuario> aux=leerArchivo();	
		aux.add(nuevo);
		escribirArchivo(aux);
	}

	public void actualizarContactosPorUsuario(String nombre, ArrayList<String> contactos){
		ArrayList<Usuario> aux = leerArchivo();	
		//imprimirArrayList(aux);
		for(int i=0; i<aux.size(); i++){
			if(aux.get(i).getNombre().equals(nombre)){
				aux.get(i).actualizarContactos(contactos);
			}
		}
		//imprimirArrayList(aux);
		escribirArchivo(aux);
	}

	public void imprimirArrayList(ArrayList<Usuario> al){
		System.out.println("**************************");  
		for(int i=0; i<al.size();i++)
			System.out.println(al.get(i).getNombre());
		System.out.println("**************************");  
	}
}