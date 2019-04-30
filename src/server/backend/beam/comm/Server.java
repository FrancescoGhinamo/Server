package server.backend.beam.comm;
import java.net.*;
import java.util.ArrayList;

import server.backend.beam.util.PageFileFilter;

import java.io.*;

/**
 * Server, classe singleton
 * @author franc
 *
 */
public class Server implements Runnable {
	
	/**
	 * Unica istanza della classe (singleton)
	 */
	private static Server me;
	
	/**
	 * Porta di apertura del servizio
	 */
	public static final int PORT = 80;
	
	/**
	 * {@link ServerSocket} esporre il server sulla rete
	 */
	private ServerSocket server;
	
	/**
	 * Array di {@link ClientHandler} per la gestione di più clients
	 */
	private ArrayList<ClientHandler> clientHandlers;
	
	/**
	 * True per interrompere l'attesa di nuove connessioni
	 */
	private boolean acceptingStopped;
	
	/**
	 * Referenze a pagine web salvate sul server
	 */
	private File[] pagine;
	
	public File[] getPagine() {
		return pagine;
	}

	public void setPagine(File[] pagine) {
		this.pagine = pagine;
	}
	/**
	 * Cartella origine in cui sono salvate tutte le pagine web
	 */
<<<<<<< HEAD:src/server/backend/beam/Server.java
	private static final String SERVER_ROOT = "C:\\Users\\Pellegrino Davide\\Documents\\paginee";//indirizzo
	
=======
	public static final String SERVER_ROOT = new File("").getAbsolutePath() + "\\root";//indirizzo
<<<<<<< HEAD:src/server/backend/beam/comm/WebServer.java

>>>>>>> 78f717b678351aad52384dca19e95898335cb305:src/server/backend/beam/comm/WebServer.java
=======
	
>>>>>>> parent of 78f717b... Creata classe di gestione:src/server/backend/beam/comm/Server.java
	/**
	 * Ritorno dell'unica istanza della classe {@link Server}
	 * @return
	 * @throws IOException
	 */
	public static Server getInstance() throws IOException {
		if(me == null) {
			try {
				me = new Server();
			} catch (IOException e) {
				throw e;
			}
		}
		return me;
	}
	
	/**
	 * Costruttore privato per Singleton
	 * @throws IOException 
	 */
	private Server() throws IOException {
		try {
			server = new ServerSocket(PORT);
			pagine = caricaPagine();
			
		} catch (IOException e) {
			throw e;
		}
		clientHandlers = new ArrayList<ClientHandler>();
		acceptingStopped = false;
		
	}
	
	/**
	 * Dalla cartella di root del server carica tutte le pagine disponibili
	 * @return
	 */
	public File[] caricaPagine() {
		File root = new File(SERVER_ROOT);
		
		return root.listFiles(new PageFileFilter());
		
	}
	
	/**
	 * Attende una connessione da parte di un client
	 * @return {@link Socket} di collegamento col client
	 */
	public Socket attendi() {
		
			
		Socket res = null;
		
		try {
			System.out.println("Pronto, in ascolto sulla porta "+PORT);
			res = server.accept();
			System.out.println("Connessione stabilita ");
		} catch (IOException e) {
			
		}
		
		
		return res;
		
	}
	
	@Override
	public void run() {
		while(!acceptingStopped) {
			ClientHandler cl = new ClientHandler(attendi());
			new Thread(cl).start();
			clientHandlers.add(cl);
		}
		
		try {
			server.close();
		} catch (IOException e) {
			
		}
		
	}
	

	/**
	 * 
	 * @return Ritorna il {@link ServerSocket} del server
	 */
	public ServerSocket getServer() {
		return server;
	}
	
	

}
