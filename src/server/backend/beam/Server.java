package server.backend.beam;
import java.net.*;
import java.nio.Buffer;
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
	 * Array di {@link ClientHandler} per la gestione di più clients
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
	//parte mia nuova pex
	private DataInputStream input;
	private DataOutputStream output;
	// come faccio a splittare in piu linee separata dallo spazio a capo
	public String pulitura(String testo)
	{
		String testoConvertito="";
		//pulisci il testo 
		//oss per scrivere commento in html si usa principalmente <p> <br> <h1,h2,h3>
		for(int conta=0;conta<testo.length();conta++)
		{
			
		testoConvertito=testoConvertito+testo.substring(testo.indexOf("<p>"),testo.indexOf("<p>"));
		testoConvertito=testoConvertito+testo.substring(testo.indexOf("<br>"),testo.indexOf("<br>"));
		testoConvertito=testoConvertito+testo.substring(testo.indexOf("<h1>"),testo.indexOf("<h1>"));	
		}
		
		return testoConvertito;
		
	}
	public String caricaeLetturaPagina(String url)
	{
		//https://www.claudiogarau.it/Java/leggere-una-pagina-web-con-java.php
		//legge tutto della pagina, bisogna pulirlo dalle robe inutili
		String testo="";
		  URL cg = null;
		try {
			cg = new URL("http://www.claudiogarau.it");
		} catch (MalformedURLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	        BufferedReader in = null;
			try {
				in = new BufferedReader(
				new InputStreamReader(cg.openStream()));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String inputLine =null;
	        try {
				while ((inputLine = in.readLine()) != null)
				{
					testo=inputLine+testo;
				}
				    
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      
	        return pulitura(testo);
	}
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
		try {
			
			  output.writeBytes("\n"+caricaeLetturaPagina(indirizzo));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//chiudere la connessione con il cliente inteoria close();
		
	}
	
	public static void main(String[] args)
	{
		
		 // far partire come thread in un'altra classe di gestione
		// fare metodi per bloccare i thread e per la disconnessione
		 
		Server s;
		try {
			s = Server.getInstance();
			s.run();
			//s.attendi();
			s.comunica();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
