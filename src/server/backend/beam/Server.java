package server.backend.beam;
import java.net.*;
import java.util.ArrayList;


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
	private static final int port = 8080;
	
	/**
	 * {@link ServerSocket} esporre il server sulla rete
	 */
	private ServerSocket server;
	
	/**
	 * Array di {@link ClientHandler} per la gestione di pi� clients
	 */
	private ArrayList<ClientHandler> clientHandlers;
	
	/**
	 * True per interrompere l'attesa di nuove connessioni
	 */
	private boolean acceptingStopped;
	
	
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
			server = new ServerSocket(port);
		} catch (IOException e) {
			throw e;
		}
		clientHandlers = new ArrayList<ClientHandler>();
		acceptingStopped = false;
	}
	
	
	/**
	 * Attende una connessione da parte di un client
	 * @return {@link Socket} di collegamento col client
	 */
	public Socket attendi() {
		
			
		Socket res = null;
		
		try {
			System.out.println("Pronto, in ascolto sulla porta "+port);
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
	
	
	public static void main(String[] args)
	{
		
		 // far partire come thread in un'altra classe di gestione
		// fare metodi per bloccare i thread e per la disconnessione
		 
		Server s;
		try {
			s = Server.getInstance();
			s.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
