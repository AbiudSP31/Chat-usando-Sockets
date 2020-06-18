import java.net.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class Cliente{ 
	Criptografia cripto=new Criptografia();
	Scanner in = new Scanner(System.in);
	Socket socket;
	BufferedReader entrada;
	PrintWriter salida;
	ArrayList<String> contactos = new ArrayList<>();
	ArrayList<String> clientesActivos = new ArrayList<>();
	String user, pass;
	ChatClienteGUI gui;

	public class ChatClienteGUI extends javax.swing.JFrame { //Clase que se encarga de construir la interfaz grafica del Cliente
	    // Declaracion de variables                    
	    private JButton btAddContacto, btSignup, btLogin, btEnviar, btEliminarContacto;
	    private JLabel jLabel1, jLabel2, jLabel3, jLabel4, jLabel5, jLabel6, lbChat, lbContactos;
	    private JScrollPane jScrollPane1, jScrollPane2;
	    private JSeparator jSeparator1;
	    private JList<String> listaContactos;
	    private JPanel pChat, pContactos, pEntrar, pLogin, pSignup;
	    private JTabbedPane pestanas;
	    private JTextArea txtChat;
	    private JTextField txtDirServer, txtMensaje, txtPassL, txtPassS, txtPortServer, txtUserL, txtUserS;

	    public ChatClienteGUI() {
	        initComponents();
	    }
	                       
	    private void initComponents() {
	        pChat = new JPanel();
	        jScrollPane2 = new JScrollPane();
	        txtChat = new JTextArea();
	        btEnviar = new JButton();
	        txtMensaje = new JTextField();
	        lbChat = new JLabel();
	        pContactos = new JPanel();
	        jScrollPane1 = new JScrollPane();
	        listaContactos = new JList<>();
	        btAddContacto = new JButton();
	        btEliminarContacto = new JButton();
	        lbContactos = new JLabel();
	        pEntrar = new JPanel();
	        jLabel1 = new JLabel();
	        txtDirServer = new JTextField();
	        jLabel2 = new JLabel();
	        txtPortServer = new JTextField();
	        pestanas = new JTabbedPane();
	        pLogin = new JPanel();
	        jLabel5 = new JLabel();
	        txtUserL = new JTextField();
	        txtPassL = new JTextField();
	        jLabel6 = new JLabel();
	        btLogin = new JButton();
	        pSignup = new JPanel();
	        jLabel3 = new JLabel();
	        jLabel4 = new JLabel();
	        txtUserS = new JTextField();
	        txtPassS = new JTextField();
	        btSignup = new JButton();
	        jSeparator1 = new JSeparator();

	        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
	        setTitle("Cliente");
	        setResizable(false);

	        txtChat.setEditable(false);
	        txtChat.setColumns(20);
	        txtChat.setRows(5);
	        jScrollPane2.setViewportView(txtChat);

	        btEnviar.setText("Enviar");
	        btEnviar.setEnabled(false);
	        btEnviar.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                btEnviarActionPerformed(evt);
	            }
	        });

	        lbChat.setText("Chat");

	        javax.swing.GroupLayout pChatLayout = new javax.swing.GroupLayout(pChat);
	        pChat.setLayout(pChatLayout);
	        pChatLayout.setHorizontalGroup(
	            pChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(pChatLayout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(pChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addComponent(jScrollPane2)
	                    .addGroup(pChatLayout.createSequentialGroup()
	                        .addGap(0, 12, Short.MAX_VALUE)
	                        .addComponent(txtMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
	                        .addGap(18, 18, 18)
	                        .addComponent(btEnviar))
	                    .addGroup(pChatLayout.createSequentialGroup()
	                        .addComponent(lbChat)
	                        .addGap(0, 0, Short.MAX_VALUE)))
	                .addContainerGap())
	        );
	        pChatLayout.setVerticalGroup(
	            pChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(pChatLayout.createSequentialGroup()
	                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addComponent(lbChat)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addGroup(pChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(btEnviar)
	                    .addComponent(txtMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                .addContainerGap())
	        );

	        listaContactos.setEnabled(false);
	        jScrollPane1.setViewportView(listaContactos);

	        btAddContacto.setText("A\u00f1adir Contacto");
	        btAddContacto.setEnabled(false);
	        btAddContacto.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                btAddContactoActionPerformed(evt);
	            }
	        });

	        btEliminarContacto.setText("EliminarContacto");
	        btEliminarContacto.setEnabled(false);
	        btEliminarContacto.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                btEliminarContactoActionPerformed(evt);
	            }
	        });

	        lbContactos.setText("Contactos");

	        javax.swing.GroupLayout pContactosLayout = new javax.swing.GroupLayout(pContactos);
	        pContactos.setLayout(pContactosLayout);
	        pContactosLayout.setHorizontalGroup(
	            pContactosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(pContactosLayout.createSequentialGroup()
	                .addGroup(pContactosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addGroup(pContactosLayout.createSequentialGroup()
	                        .addContainerGap()
	                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
	                    .addGroup(pContactosLayout.createSequentialGroup()
	                        .addContainerGap()
	                        .addComponent(lbContactos)
	                        .addGap(0, 0, Short.MAX_VALUE)))
	                .addContainerGap())
	            .addGroup(pContactosLayout.createSequentialGroup()
	                .addGap(24, 24, 24)
	                .addGroup(pContactosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addComponent(btEliminarContacto)
	                    .addComponent(btAddContacto))
	                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	        );
	        pContactosLayout.setVerticalGroup(
	            pContactosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(pContactosLayout.createSequentialGroup()
	                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addComponent(lbContactos)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(btAddContacto)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(btEliminarContacto)
	                .addContainerGap())
	        );

	        jLabel1.setText("Direcci\u00f3n del Servidor:");

	        txtDirServer.setText("localHost");

	        jLabel2.setText("Puerto del Servidor:");

	        txtPortServer.setText("9999");

	        pestanas.setName(""); // NOI18N

	        jLabel5.setText("Nombre de Usuario:");

	        jLabel6.setText("Contrase\u00f1a:");

	        btLogin.setText("Iniciar Sesi\u00f3n");
	        btLogin.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                btLoginActionPerformed(evt);
	            }
	        });

	        javax.swing.GroupLayout pLoginLayout = new javax.swing.GroupLayout(pLogin);
	        pLogin.setLayout(pLoginLayout);
	        pLoginLayout.setHorizontalGroup(
	            pLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(pLoginLayout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(pLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addComponent(jLabel5)
	                    .addComponent(jLabel6))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                .addGroup(pLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
	                    .addComponent(txtPassL, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
	                    .addComponent(txtUserL))
	                .addGap(45, 45, 45)
	                .addComponent(btLogin)
	                .addContainerGap(200, Short.MAX_VALUE))
	        );
	        pLoginLayout.setVerticalGroup(
	            pLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(pLoginLayout.createSequentialGroup()
	                .addGroup(pLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addGroup(pLoginLayout.createSequentialGroup()
	                        .addContainerGap()
	                        .addGroup(pLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                            .addComponent(jLabel5)
	                            .addComponent(txtUserL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                        .addGroup(pLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                            .addComponent(txtPassL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                            .addComponent(jLabel6)))
	                    .addGroup(pLoginLayout.createSequentialGroup()
	                        .addGap(23, 23, 23)
	                        .addComponent(btLogin)))
	                .addContainerGap(13, Short.MAX_VALUE))
	        );

	        pestanas.addTab("Login", pLogin);

	        jLabel3.setText("Nombre de Usuario:");

	        jLabel4.setText("Contrase\u00f1a:");


	        btSignup.setText("Registrarse");
	        btSignup.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                btSignupActionPerformed(evt);
	            }
	        });

	        javax.swing.GroupLayout pSignupLayout = new javax.swing.GroupLayout(pSignup);
	        pSignup.setLayout(pSignupLayout);
	        pSignupLayout.setHorizontalGroup(
	            pSignupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(pSignupLayout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(pSignupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addComponent(jLabel3)
	                    .addComponent(jLabel4))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                .addGroup(pSignupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
	                    .addComponent(txtPassS, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
	                    .addComponent(txtUserS))
	                .addGap(45, 45, 45)
	                .addComponent(btSignup)
	                .addContainerGap(208, Short.MAX_VALUE))
	        );
	        pSignupLayout.setVerticalGroup(
	            pSignupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(pSignupLayout.createSequentialGroup()
	                .addGroup(pSignupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addGroup(pSignupLayout.createSequentialGroup()
	                        .addContainerGap()
	                        .addGroup(pSignupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                            .addComponent(jLabel3)
	                            .addComponent(txtUserS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                        .addGroup(pSignupLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                            .addComponent(txtPassS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                            .addComponent(jLabel4)))
	                    .addGroup(pSignupLayout.createSequentialGroup()
	                        .addGap(23, 23, 23)
	                        .addComponent(btSignup)))
	                .addContainerGap(13, Short.MAX_VALUE))
	        );

	        pestanas.addTab("Signup", pSignup);

	        javax.swing.GroupLayout pEntrarLayout = new javax.swing.GroupLayout(pEntrar);
	        pEntrar.setLayout(pEntrarLayout);
	        pEntrarLayout.setHorizontalGroup(
	            pEntrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(pEntrarLayout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(pEntrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addComponent(pestanas)
	                    .addGroup(pEntrarLayout.createSequentialGroup()
	                        .addComponent(jLabel1)
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                        .addComponent(txtDirServer, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
	                        .addGap(52, 52, 52)
	                        .addComponent(jLabel2)
	                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                        .addComponent(txtPortServer, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
	                        .addGap(0, 0, Short.MAX_VALUE)))
	                .addContainerGap())
	        );
	        pEntrarLayout.setVerticalGroup(
	            pEntrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(pEntrarLayout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(pEntrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                    .addComponent(jLabel1)
	                    .addComponent(txtDirServer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addComponent(jLabel2)
	                    .addComponent(txtPortServer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addComponent(pestanas, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addContainerGap())
	        );

	        pestanas.getAccessibleContext().setAccessibleName("tab");

	        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
	        getContentPane().setLayout(layout);
	        layout.setHorizontalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addComponent(jSeparator1)
	                    .addComponent(pEntrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                    .addGroup(layout.createSequentialGroup()
	                        .addComponent(pChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                        .addGap(18, 18, 18)
	                        .addComponent(pContactos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
	                .addContainerGap())
	        );
	        layout.setVerticalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addComponent(pEntrar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                    .addComponent(pChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                    .addComponent(pContactos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
	                .addContainerGap())
	        );

	        pack();
	    }                                                                                                         

	    private void btSignupActionPerformed(java.awt.event.ActionEvent evt) {
	        if(signup(getTxtDirServer(), Integer.parseInt(getTxtPortServer()), getTxtUserS(), getTxtPassS())){
	    		JOptionPane.showMessageDialog(this, "Bienvenido " + user + "!");
	    		btEnviar.setEnabled(true);
	        	btAddContacto.setEnabled(true);

	        	btSignup.setEnabled(false);				//
	        	txtPortServer.setEnabled(false);		//
	        	txtDirServer.setEnabled(false);			// Se desactivan las cajas de texto encargadas
	        	txtUserS.setEnabled(false);				// de recibir informacion para registrarse
	        	txtPassS.setEnabled(false);				//
	        	pestanas.setEnabled(false);				//
	    	}
	    	else{	//Se entra aqui en caso de que no se haya concretado el registro del usuario
	    		try{		
	    			socket.close();		
		    		entrada.close();
		    		salida.close();
	    		}
	    		catch(IOException io){	    			
	    		}
	    		catch(NullPointerException e){	    			
	    		}
	    	}	       
	        this.repaint();                        
	    }                                        

	    private void btLoginActionPerformed(java.awt.event.ActionEvent evt) { 
	    	if(login(getTxtDirServer(), Integer.parseInt(getTxtPortServer()), getTxtUserL(), getTxtPassL())){
	    		JOptionPane.showMessageDialog(this, "Bienvenido " + user + "!");
	    		btEnviar.setEnabled(true);
	        	btAddContacto.setEnabled(true);

	        	setListaContactos(contactosToArray());
	        	if(contactos.size()>0){
	        		btEliminarContacto.setEnabled(true);		
	        		listaContactos.setEnabled(true);
	        	}

	        	btLogin.setEnabled(false);				//
	        	txtPortServer.setEnabled(false);		//
	        	txtDirServer.setEnabled(false);			// Se desactivan las cajas de texto encargadas
	        	txtUserL.setEnabled(false);				// de recibir informacion para iniciar sesion
	        	txtPassL.setEnabled(false);				//
	        	pestanas.setEnabled(false);				//
	    	}
	    	else{	//Se entra aqui en caso de que no se haya concretado el inicio de sesion del usuario
	    		try{
	    			socket.close();
		    		entrada.close();
		    		salida.close();
	    		}
	    		catch(IOException io){

	    		}	 
	    		catch(NullPointerException e){

	    		}   		
	    	}	       
	        this.repaint();
	    }                                       

	    private void btEnviarActionPerformed(java.awt.event.ActionEvent evt) {    
	    	setListaContactos(contactosToArray());                                     
	        String destino = getSelectedValueListaContactos();	        
	        if(destino!=null){
	        	String[] destinoC=destino.split(" ");
	        	destino = destino.substring(0, destino.length()-destinoC[destinoC.length-1].length()-1);	        	
	        	if(existeContacto(destino)){
		        	if(estaConectado(destino)){
		        		String mensaje = getTxtMensaje();
		        		if(!mensaje.equals("") && !mensaje.equals(" ")){
		        			enviarMensaje(destino, mensaje);
		        			addTxtChat("[Tu->"+destino+"]: "+mensaje);
		        			setTxtMensaje("");
		        		}
		        		else{
				    		JOptionPane.showMessageDialog(this, "Escribe algo primero.");
				    	}
		        	}
		        	else{
		        		JOptionPane.showMessageDialog(this, "El usuario no esta conectado");
		        	}	
	        	}
	        	else{
		    		JOptionPane.showMessageDialog(this, "Error al seleccionar un destinatario [2].");
		    	}
	        }
	        else{
	    		JOptionPane.showMessageDialog(this, "Error al seleccionar un destinatario.");
	    	}
	    }                                        

	    private void btAddContactoActionPerformed(java.awt.event.ActionEvent evt) {
	    	solicitarUsuariosEnLinea();
	    	setListaContactos(contactosToArray());	    	
	    	if(clientesActivos.size()>0){
	    		String[] cA = usuariosActivosParaAnadirToArray();
	    		if(!cA[0].equals("") && cA.length>1){
	        		String name = (String) JOptionPane.showInputDialog(this, "Seleccione el usuario para agregar como contacto:", "Agregar contacto", JOptionPane.QUESTION_MESSAGE, null, cA, cA[0]);
	        		if(name!=null)
						if(!name.equals("---")){
							anadirContacto(name);
			        		setListaContactos(contactosToArray());
			        		JOptionPane.showMessageDialog(this, "Contacto a\u00f1adido.");
						}	        				        		
	    		}
	    		else{
		    		JOptionPane.showMessageDialog(this, "No hay usuarios para agregar en este momento.");
		    	}
	    	}
	    	else{
	    		JOptionPane.showMessageDialog(this, "No hay usuarios activos en este momento.");
	    	}
	    }                                             

	    private void btEliminarContactoActionPerformed(java.awt.event.ActionEvent evt) {
	    	solicitarUsuariosEnLinea();
	    	setListaContactos(contactosToArray());	    	
	    	if(contactos.size()>0){	   
	    		String[] con = contactosToArray(); 		
	    		if(con!=null){
	        		String name = (String) JOptionPane.showInputDialog(this, "Seleccione el contacto para eliminar:", "Eliminar contacto", JOptionPane.QUESTION_MESSAGE, null, con, con[0]);
		        	if(name!=null){
		        		String[] destinoC=name.split(" ");
		        		name = name.substring(0, name.length()-destinoC[destinoC.length-1].length()-1);
		        		eliminarContacto(name);
		        		setListaContactos(contactosToArray());
		        		JOptionPane.showMessageDialog(this, "Contacto eliminado.");
		        	}	
	    		}
	    		else{
		    		//JOptionPane.showMessageDialog(this, "No hay usuarios para agregar en este momento.");
		    	}
	    	}
	    	else{
	    		JOptionPane.showMessageDialog(this, "No hay contactos que eliminar.");
	    	}
	    }                                                  
	    
	    public String getTxtUserS(){
	        return txtUserS.getText();
	    }
	    
	    public String getTxtPassS(){
	        return txtPassS.getText();
	    }
	    
	    public String getTxtUserL(){
	        return txtUserL.getText();
	    }
	    
	    public String getTxtPassL(){
	        return txtPassL.getText();
	    }
	    
	    public String getTxtDirServer(){
	        return txtDirServer.getText();
	    }
	    
	    public String getTxtPortServer(){
	        return txtPortServer.getText();
	    }
	    
	    public String getTxtChat(){
	        return txtChat.getText();
	    }
	    
	    public String getTxtMensaje(){
	        return txtMensaje.getText();
	    }

	    public void setTxtMensaje(String str){
	    	txtMensaje.setText(str);
	    }
	    
	    public void setTxtChat(String texto){
	        txtChat.setText(texto);
	    }
	    
	    public void addTxtChat(String texto){	    	
	        txtChat.setText(getTxtChat()+"\n"+texto);
	        solicitarUsuariosEnLinea();
	        setListaContactos(contactosToArray());
	    }
	    
	    public void setListaContactos(String[] contactos){  
	    	int index=listaContactos.getSelectedIndex();      
	        listaContactos.setListData(contactos);	        
	        if(contactos.length>0){
	        	btEliminarContacto.setEnabled(true);
	        	listaContactos.setEnabled(true);
	        }else{
	        	btEliminarContacto.setEnabled(false);
	        	listaContactos.setEnabled(false);
	        }
	        this.repaint();
	        listaContactos.setSelectedIndex(index);
	    }
	    
	    public String getSelectedValueListaContactos(){
	        return listaContactos.getSelectedValue();
	    }                
	}	

	public class EntradaMensajesThread extends Thread{	 //Clase que se encarga de gestionar la entrada de mensajes al socket cliente   
		public void run(){
			try{
				while(true){
					String entry = cripto.desencriptar(entrada.readLine());	
					String[] ent = entry.split(" "); 
					if(ent[0].equals("lca")){ //Se recibe la lista de usuarios en linea
						String usuarios = entry.substring(4);
						clientesActivos = stringToArrayList(usuarios);
					}
					else if(ent[0].equals("msg")){	//Se recibe un mensaje			
						String mensaje = entry.substring(4);
						gui.addTxtChat(mensaje); //Se agrega directamente al texto del chat
					}				
					else{ //En caso de lo que se reciba sea algo diferente
						gui.addTxtChat(entry); //Se agrega directamente al texto del chat
					}
				}	
			}
			catch(Exception e){ //Si se entra aqui es porque se cerro el la comunicacion con el servidor, entonces se cierra el chat				
				JOptionPane.showMessageDialog(gui, "Ocurrio un problema de comunicacion con el Servidor. Cerrando chat");			
				System.out.println("Ocurrio un problema de comunicacion con el Servidor. Cerrando chat");							
				System.exit(-1);
			}
			finally{ //Por ultimo se cierra el socket y sus respectivas salidas
				try{
		            entrada.close();
		            salida.close();
		            socket.close();
		        }
		        catch(IOException ioException){				
		        }
			}
		}
	}

	void solicitarUsuariosEnLinea(){		//Se le solicita al servidor que mande la lista actualizada de usuarios activos
    	try{ 		
    		salida.println(cripto.encriptar("lca ")); 
    		Thread.sleep(200);
    	}catch(Exception e){
			e.printStackTrace();
		}
    }

	void enviarContactosActualizados(){		//Se le manda la nueva lista de contactos al servidor, para que la respalde en la base de datos
    	try{
			salida.println(cripto.encriptar("act "+arrayListToString(contactos)));							
		}catch(Exception e){							
			JOptionPane.showMessageDialog(gui, "Ocurrio un problema de comunicacion con el Servidor");			
		}
    }

    boolean estaConectado(String c){
    	solicitarUsuariosEnLinea();
    	return clientesActivos.contains(c);    	
    }


    void anadirContacto(String nContacto){ //Se puede mejorar para no ingresar duplicados
    	try{
    		if(clientesActivos.size()>1){
				for (int i=0; i<clientesActivos.size(); i++){
					if(clientesActivos.get(i).equals(nContacto) && !existeContacto(nContacto)){
						contactos.add(nContacto);
						enviarContactosActualizados();						
						return;
					}
				}				
    		}
    	}catch(Exception e){
			e.printStackTrace();
		}   
    }

    void eliminarContacto(String elim){ //Se puede implemetar un contains y remove(obj)
    	try{	    		
    		if(contactos.size()>0){
				for (int i=0; i<contactos.size(); i++){
					if(contactos.get(i).equals(elim)){
						contactos.remove(i);
						enviarContactosActualizados();
						return;
					}
				}				
    		}
    	}catch(Exception e){
			e.printStackTrace();
		} 
    }

    void enviarMensaje(String destino, String mensaje){ //Enviar mensaje al servidor, incluyendo un destinatario y un texto como mensaje
    	try{
    		if(existeContacto(destino)){
	    		salida.println(cripto.encriptar("msg "+destino+" "+mensaje));		    				    		
	    	}	    	
    	}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(gui, "No se pudo enviar el mensaje");				 
		}
    }

    boolean existeContacto(String contacto){ 
    	return contactos.contains(contacto);
    }

	String[] contactosToArray(){ // Se convierten los contactos en un array. Esta funcion se ocupa para poder mostrar los contactos en la interfaz grafica, incluyendo si estan en linea o no		
		if(contactos.size()>0){
			solicitarUsuariosEnLinea();
			String[] c = new String[contactos.size()];
			for(int i=0; i<contactos.size(); i++){
				if(contactos.get(i).length()>0){
					if(clientesActivos.contains(contactos.get(i)))
		    			c[i] = contactos.get(i) + " (Conectado)";
		    		else
		    			c[i] = contactos.get(i) + " (Desconectado)";
				}
			}
			return c;
		}
		else{
			return new String[0];
		}		
	}

	String[] usuariosActivosParaAnadirToArray(){ //Sirve para mandar los usuarios activos a la interfaz grafica como un String[]. Como primer elemento siempre manda guiones como opcion por defecto
		solicitarUsuariosEnLinea();		
		String str="--- ";
		for(int i=0; i<clientesActivos.size(); i++){
			if(!clientesActivos.get(i).equals(user) && !contactos.contains(clientesActivos.get(i)))
    			str = str + clientesActivos.get(i) +" ";
		}	
		String[] c=str.split(" ");			
		return c;
	}

    boolean signup(String direccionServer, int puertoServer, String u, String p){ //Funcion que gestiona el registro de un nuevo usuario. Regresa true si la operacion tuvo exito
    	try{
			user=u;
			pass=p;	
	    	if(!p.equals("") && !p.equals(" ") && !p.equals("  ")){			//El campo contraseña no esta vacio?
				if(!u.equals(" ") && !u.equals("") && !u.equals("  ")){		//El campo usuario no esta vacio?
					socket = new Socket(direccionServer,puertoServer);
					entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					salida = new PrintWriter( new OutputStreamWriter(socket.getOutputStream()),true );
					String crypU=cripto.encriptar(user);
					String crypP=cripto.encriptar(pass);

					salida.println(cripto.encriptar("signup"));				//Se le indica al servidor lo que se va a hacer
					if(cripto.desencriptar(entrada.readLine()).equals("ok")){
						salida.println(crypU);						
						if(cripto.desencriptar(entrada.readLine()).equals("ok")){ // El usuario no estaba registrado con anterioridad?
							salida.println(crypP);
							cripto.desencriptar(entrada.readLine());
							salida.println(cripto.encriptar("bye"));
							if(cripto.desencriptar(entrada.readLine()).equals("ok")){
								JOptionPane.showMessageDialog(gui, "Registro con exito");								
								iniciarConexion();
								return true;
							}
							else
								JOptionPane.showMessageDialog(gui, "Ha ocurrido un error en el registro.");				
						}
						else{
							JOptionPane.showMessageDialog(gui, "El nombre de usuario ya esta registrado.");				
						}
					}
					else
						JOptionPane.showMessageDialog(gui, "Ha ocurrido un error en el registro.");
				}
				else{
					JOptionPane.showMessageDialog(gui, "Ingrese un nombre de usuario.");
				}
	    	}else{
	    		JOptionPane.showMessageDialog(gui, "Ingrese una contrase\u00f1a v\u00e1lida.");
	    	}				
			return false;
    	}
    	catch(UnknownHostException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return false;
    }

    boolean login(String direccionServer, int puertoServer, String u, String p){ //Funcion que gestiona el inicio de sesion de un usuario. Regresa true si la operacion tuvo exito
    	try{      							
			user=u;
			pass=p;
    		if(!u.equals(" ") && !u.equals("") && !u.equals("  ")){			//El campo usuario no esta vacio?
    			if(!p.equals("") && !p.equals(" ") && !p.equals("  ")){		//El campo contraseña no esta vacio?
    				socket = new Socket(direccionServer,puertoServer);
					entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					salida = new PrintWriter( new OutputStreamWriter(socket.getOutputStream()),true );

    				String crypU=cripto.encriptar(user);
					String crypP=cripto.encriptar(pass);
					String entries;
					
					salida.println(cripto.encriptar("login"));			//Se le indica al servidor lo que se va a hacer
					entries=cripto.desencriptar(entrada.readLine());
					if(entries.equals("ok")){
						salida.println(crypU);
						entries=cripto.desencriptar(entrada.readLine());;
						if(entries.equals("ok")){
							salida.println(cripto.encriptar("ok"));
							String ma=cripto.desencriptar(entrada.readLine());
							String mezcla=cripto.mezcla(pass, ma);				
							String md5=cripto.md5(mezcla);
							salida.println(cripto.encriptar(md5)); 				//Se le envia al servidor la huella digital creada con md5 para ver si coinciden
							entries=cripto.desencriptar(entrada.readLine());;
							if(entries.equals("ok")){							//Si coinciden las huellas digitales...
								contactos = stringToArrayList(entrada.readLine());								
								JOptionPane.showMessageDialog(gui, "Loggeado exitosamente.");
								iniciarConexion(); 								//Se crea el hilo que gestionan las entradas de mensajes
								return true;
							}
							else
								JOptionPane.showMessageDialog(gui, entries);				
						}
						else
							JOptionPane.showMessageDialog(gui, entries);							
					}
					else
						JOptionPane.showMessageDialog(gui, entries);
    			}
    			else{
    				JOptionPane.showMessageDialog(gui, "Ingrese una contrase\u00f1a");
    			}
    		}
    		else{
    			JOptionPane.showMessageDialog(gui, "Ingrese un nombre de usuario");
    		}
			return false;					
    	}
    	catch(UnknownHostException e){
			e.printStackTrace();
			return false;
		}
		catch(IOException e){
			e.printStackTrace();
			return false;
		}
    }

    String arrayListToString(ArrayList<String> al){ 	//Funcion utilizada para mandar un arrayList a traves de un socket
    	String res="";
    	for(int i=0; i<al.size(); i++){
    		res=res+al.get(i)+" ";
    	}    	
    	return res;
    }

    ArrayList<String> stringToArrayList(String str){   //Funcion utilizada para recibir un arrayList a traves de un socket

    	ArrayList<String> res = new ArrayList<>();
    	String[] s = str.split(" ");    	
    	for(int i=0; i<s.length; i++)
    		if(s[i].length()>0)
    			res.add(s[i]);    	
    	return res;
    }

    void iniciarConexion(){ 		//Funcion que crea un hilo para la gestion de mensajes de entrada
		try{			
			EntradaMensajesThread entradaDeMensajes = new EntradaMensajesThread();
			entradaDeMensajes.start();			
		}
		catch(Exception e){
			e.printStackTrace();
		}		
    }   

    void mostrarGUI(){ 	//Crea y muestra la interfaz grafica
    	gui = new ChatClienteGUI();
    	gui.setVisible(true);
    }

	public static void main(String[] args){
		Cliente cliente=new Cliente();
		cliente.mostrarGUI();
	}			
}
