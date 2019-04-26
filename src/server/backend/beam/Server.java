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
	private static final int port = 80;
	
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
	private static final String SERVER_ROOT = "";//indirizzo
	
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
			server = new ServerSocket(port);
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
	
	/*
	//parte mia nuova pex
	private DataInputStream input;
	private DataOutputStream output;
	// come faccio a splittare in piu linee separata dallo spazio a capo
	
	
	public void comunica()
	{
		//aspetta l indirizzo url della pagina 
		String indirizzo=null;
		try {
			indirizzo = input.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}*/
	
	public static void main(String[] args)
	{
		
		 // far partire come thread in un'altra classe di gestione
		// fare metodi per bloccare i thread e per la disconnessione
		 
		Server s;
		try {
			s = Server.getInstance();
			s.run();
			//s.attendi();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
