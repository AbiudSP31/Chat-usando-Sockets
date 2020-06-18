import java.util.ArrayList;

public class Usuario{
	public String nombre;
	public String pass;
	public ArrayList<String> contactos;

	public Usuario(String n, String pass){
		this.nombre = n;
		this.pass = pass;
		this.contactos = new ArrayList<>();		
	}

	public String getNombre(){
		return nombre;
	}

	public String getPass(){
		return pass;
	}

	public void nuevoContacto(String contacto){
		contactos.add(contacto);
	}
	public void eliminarContacto(String contacto){
		for(int i=0; i<contactos.size(); i++)
			if(contactos.get(i).equals(contactos))
				contactos.remove(i);
	}

	public ArrayList<String> getContactos(){
		return contactos;
	}

	public void actualizarContactos(ArrayList<String> nuevosContactos){
		contactos.clear();
		contactos = nuevosContactos;
	}
}