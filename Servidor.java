import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Servidor{
	public static BaseDatos archivo = new BaseDatos();
	public static ArrayList<Usuario> usuariosReg = archivo.leerArchivo();
	public static ArrayList<String> usuariosEnLinea = new ArrayList<>();
	public static HashMap<String, Socket> clientesActivos = new HashMap<String, Socket>();		//Diccionario que sirve para encontrar el socket del usuario al que se quiera mandar un mensaje
	public static ServidorGUI gui;

	public static class ServidorGUI extends javax.swing.JFrame{	//Clase que se encarga de construir la interfaz grafica
	    private javax.swing.JButton btSalir;
	    private javax.swing.JLabel jLabel1, jLabel2, lbDir, lbPort;
	    private javax.swing.JScrollPane jScrollPane1;
	    private javax.swing.JTextArea txtSalida;

	    public ServidorGUI() {
	        initComponents();
	    }

	    private void initComponents() {

	        jScrollPane1 = new javax.swing.JScrollPane();
	        txtSalida = new javax.swing.JTextArea();
	        btSalir = new javax.swing.JButton();
	        jLabel1 = new javax.swing.JLabel();
	        jLabel2 = new javax.swing.JLabel();
	        lbDir = new javax.swing.JLabel();
	        lbPort = new javax.swing.JLabel();

	        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
	        setTitle("Servidor");
	        setResizable(false);

	        txtSalida.setEditable(false);
	        txtSalida.setColumns(20);
	        txtSalida.setRows(5);
	        jScrollPane1.setViewportView(txtSalida);

	        btSalir.setText("Salir");
	        btSalir.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                btSalirActionPerformed(evt);
	            }
	        });

	        jLabel1.setText("Direcci\u00f3n:");
	        jLabel2.setText("Puerto:");
	        lbDir.setText("localHost");
	        lbPort.setText("9999");

	        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
	        getContentPane().setLayout(layout);
	        layout.setHorizontalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addComponent(jScrollPane1)
	                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
	                        .addGap(0, 0, Short.MAX_VALUE)
	                        .addComponent(btSalir))
	                    .addGroup(layout.createSequentialGroup()
	                        .addComponent(jLabel1)
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                        .addComponent(lbDir)
	                        .addGap(91, 91, 91)
	                        .addComponent(jLabel2)
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                        .addComponent(lbPort)
	                        .addGap(0, 207, Short.MAX_VALUE)))
	                .addContainerGap())
	        );
	        layout.setVerticalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
	                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(jLabel1)
	                    .addComponent(jLabel2)
	                    .addComponent(lbDir)
	                    .addComponent(lbPort))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(btSalir)
	                .addGap(4, 4, 4))
	        );

	        pack();
	        setLocationRelativeTo(null);
	    }                        

	    private void btSalirActionPerformed(java.awt.event.ActionEvent evt) {                                        
	        System.exit(-1);
	    }                                       

	    public void setLbPort(String port){
	        lbPort.setText(port);
	    }
	    
	    public void setLbDireccion(String dir){
	        lbDir.setText(dir);
	    }
	    
	    private String getTxtSalidaChat() {
	        return txtSalida.getText();
	    }
	    
	    public void addTxtSalidaChat(String texto){	    	
		txtSalida.setText(getTxtSalidaChat()+texto+"\n");
	    }                            
	}


	public static class HiloPeticionCliente extends Thread{		//Clase que se encarga de gestionar a cada cliente que entre
		Socket s;
		Criptografia cripto;
		BufferedReader entrada;
		PrintWriter salida;
		Usuario usuario;

		public HiloPeticionCliente(Socket s) throws IOException{
			this.s = s;			
			this.cripto=new Criptografia();
			entrada = new BufferedReader(new InputStreamReader(this.s.getInputStream()));
			salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.s.getOutputStream())), true);			
		}

		public boolean hayUsuariosRegistrados(){
	    	ArrayList<Usuario> u=archivo.leerArchivo();
	    	if(u==null || u.isEmpty())
	    		return false;
	    	return true;
	    }

	    public boolean existeUsuario(String nombre){
	    	for (int i=0; i<usuariosReg.size(); i++){
	    		if(usuariosReg.get(i).getNombre().equals(nombre))
	    			return true;
	    	}
	    	return false;
	    }

		public String generarMensageAleatorio(){ 	//Se genera un mensaje aleatorio de 4096 
			String ma="";
			int n1=33;			//Se elige un rango en el que los caracteres saldran,
			int n2=122;			// segun ASCII

			for(int i=0; i<4096;i++){
				int numAleatorio=(int)Math.floor(Math.random()*(n2-n1)+n1);
				ma+=(char)numAleatorio;
			}		
			return ma;		
		}

		public Usuario getUsuarioPorNombre(String user){
			for(int i=0; i<usuariosReg.size();i++){
				if(usuariosReg.get(i).getNombre().equals(user)){
					return usuariosReg.get(i);
				}
			}			
			return null;
		}

		public String buscarPassPorUsuario(String user){	//Devuelve la contraseña almacenada en la base de datos dado un nombre de usuario
	    	ArrayList<Usuario> usuarios=archivo.leerArchivo();
	    	for (int i=0; i<usuarios.size(); i++){
	    		if(user.equals(usuarios.get(i).getNombre()))
	    			return usuarios.get(i).getPass();
	    	}
	    	return null;
	    }

		public int signup() throws UnsupportedEncodingException, IOException{			//Funcion encargada de gestionar el registro de un usuario nuevo
			gui.addTxtSalidaChat("[Server]: Un usuario esta intentando registrarse");		
			salida.println(cripto.encriptar("ok"));
			String userC=entrada.readLine();
			if(!existeUsuario(cripto.desencriptar(userC))){
				salida.println(cripto.encriptar("ok"));
				String passC=entrada.readLine();
				salida.println(cripto.encriptar("[Server]: Contrase\u00f1a correcta"));
				String str=cripto.desencriptar(entrada.readLine());							
				usuario = new Usuario(cripto.desencriptar(userC), cripto.desencriptar(passC));
				archivo.actualizarArchivo(usuario);
				clientesActivos.put(usuario.getNombre(), s);

				salida.println(cripto.encriptar("ok"));
				gui.addTxtSalidaChat("[Server]: Registro completado. "+usuario.getNombre()+" se ha unido al chat.");				
				return 1;
			}
			else{
				salida.println(cripto.encriptar("[Server]: error"));
				return 0;							
			}
		}

		public int login() throws UnsupportedEncodingException, IOException{		//Funcion encargada de gestionar el inicio de sesion de un usuario		
			gui.addTxtSalidaChat("[Server]: Un usuario esta intentando iniciar sesion");	
			if(hayUsuariosRegistrados()){
				salida.println(cripto.encriptar("ok"));
				String user=cripto.desencriptar(entrada.readLine());
				String pass=buscarPassPorUsuario(user);
				if(pass!=null){
					salida.println(cripto.encriptar("ok"));
					entrada.readLine();
					String ma=generarMensageAleatorio();
					salida.println(cripto.encriptar(ma));
					String mezcla=cripto.mezcla(pass, ma);
					String md5Servidor=cripto.md5(mezcla);
					String md5Cliente=cripto.desencriptar(entrada.readLine());
					if(md5Cliente.equals(md5Servidor)){
						salida.println(cripto.encriptar("ok"));
						usuario = getUsuarioPorNombre(user);
						gui.addTxtSalidaChat("[Server]: "+usuario.getNombre()+" se ha unido al chat.");						
						clientesActivos.put(usuario.getNombre(), s);

						salida.println(arrayListToString(usuario.getContactos()));						
						return 1;
					}						
					else{
						gui.addTxtSalidaChat("[Server]: "+user+" ha intentando iniciar sesion sin exito. Contrase\u00f1a incorrecta.");						
						salida.println(cripto.encriptar("[Server]: Contrase\u00f1a incorrecta"));					
					}	
				}
				else{
					gui.addTxtSalidaChat("[Server]: Usuario no encontrado");					
					salida.println(cripto.encriptar("[Server]: Usuario no encontrado"));
				}
			}else{
				gui.addTxtSalidaChat("[Server]: No es posible iniciar sesion en este momento. No hay usuarios registrados.");				
				salida.println(cripto.encriptar("[Server]: Operacion cancelada porque no hay usuarios registrados"));
			}
			return 0;
		}

		public void enviarMensaje(String str) throws UnsupportedEncodingException, IOException{		//Se encarga de enviar un mensaje al usuario que se indique
			String[] msg = str.split(" ");
			String destino = msg[1];
			String mensaje = str.substring(5+destino.length());

			if(existeUsuario(destino)){
				gui.addTxtSalidaChat("[Server]: "+usuario.getNombre()+" solicito enviar un mensaje.");							
				Socket sD = clientesActivos.get(destino);	

				PrintWriter salidaDestino = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sD.getOutputStream())), true);							
				salidaDestino.println(cripto.encriptar("msg ["+usuario.getNombre()+"]: "+mensaje));	
				gui.addTxtSalidaChat("["+usuario.getNombre()+"->"+destino+"]: "+mensaje);				
			}
		}

		public void actualizarContactosEnLinea(){

		}

		public String arrayListToString(ArrayList<String> al){
	    	String res="";
	    	for(int i=0; i<al.size(); i++){
	    		res=res+al.get(i)+" ";
	    	}	    	
	    	return res;
	    }

	    public ArrayList<String> stringToArrayList(String str){   	        
	    	ArrayList<String> res = new ArrayList<>();
	    	String[] s = str.split(" ");
	    	for(int i=0; i<s.length; i++)
	    		res.add(s[i]);
	    	return res;
	    }

		public void enviarUsuariosEnLinea() throws UnsupportedEncodingException, IOException{			
			actualizarContactosEnLinea();
			salida.println(cripto.encriptar("lca "+arrayListToString(usuariosEnLinea)));			
		}

		public void imprimirArrayList(ArrayList<String> al){
			for(int i=0; i<al.size();i++)
				System.out.println(al.get(i));
		}

		public void actualizarContactos(String str)  throws UnsupportedEncodingException, IOException, ClassNotFoundException{			
			String cont = str.substring(4);
			ArrayList<String> contactos = stringToArrayList(cont);
			
			archivo.actualizarContactosPorUsuario(usuario.getNombre(), contactos);
		}

		public void run(){
			try{				
				String str = cripto.desencriptar(entrada.readLine());
				int res=0;
				if(str.equals("signup")){
					res = signup();														
				}
				else if(str.equals("login")){
					res = login();					
				}	
				if(res==1){
					usuariosEnLinea.add(usuario.getNombre());
					while(true){											//El hilo se queda en espera de lo que el cliente conectado solicite hacer
						str = cripto.desencriptar(entrada.readLine());		//
						String[] entries = str.split(" ");
						if(entries[0].equals("msg")){		//El cliente solicita enviar un mensaje					
							enviarMensaje(str);
						}
						else if(entries[0].equals("lca")){	//El cliente solicita la lista de usuarios activos
							enviarUsuariosEnLinea();
						}
						else if(entries[0].equals("act")){	//El cliente solicita actualizar su lista de contactos. El servidor la guardara en la base de datos
							actualizarContactos(str);
						}
					}
				}					
				salida.close();
				entrada.close();
			}
			catch(SocketException se){				
				gui.addTxtSalidaChat("[Server]: "+usuario.getNombre()+" ha abandonado el chat");				
				usuariosEnLinea.remove(usuariosEnLinea.indexOf(usuario.getNombre()));		//El cliente ha abandonado el chat, asi que se elimina de las listas de clientes activos
				clientesActivos.remove(usuario.getNombre());
			}
			catch(Exception e){
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}


	public static void main(String[] args){
		ServerSocket ss=null;
		Socket s=null;
		int port=9999;
		String IP_local="localHost";
		try{			
			ss=new ServerSocket(port);
			InetAddress direccion = InetAddress.getLocalHost();
            String nombreDelHost = direccion.getHostName();//nombre host
            IP_local = direccion.getHostAddress();//ip como String
            System.out.println("La IP de la maquina local es : " + IP_local);
            System.out.println("El nombre del host local es : " + nombreDelHost);
		}
		catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		gui = new ServidorGUI();			//Se crea la interfaz grafica y se le mandan los valores de direccion y puerto, para que se muestren
		gui.setVisible(true);
		gui.setLbDireccion(IP_local);
		gui.setLbPort(Integer.toString(port));

		while(true){
			try{
				s=ss.accept();			//Llega un nuevo cliente
				gui.addTxtSalidaChat("Nueva conexion aceptada: "+s);
				new HiloPeticionCliente(s).start(); 	//Se crea un nuevo hilo para gestionar al nuevo cliente								
				s=null;				
			}
			catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}
}