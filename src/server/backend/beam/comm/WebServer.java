package server.backend.beam.comm;
import java.net.*;
import java.util.ArrayList;

import java.io.*;

/**
 * Server, classe singleton
 *
 */
public class WebServer implements Runnable {

	/**
	 * Unica istanza della classe (singleton)
	 */
	private static WebServer me;

	/**
	 * Porta di apertura del servizio
	 */
	public static final int PORT = 80;

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
	 * Cartella origine in cui sono salvate tutte le pagine web
	 */
	public static final String SERVER_ROOT = new File("").getAbsolutePath() + "\\root";//indirizzo

	/**
	 * Ritorno dell'unica istanza della classe {@link Server}
	 * @return
	 * @throws IOException
	 */
	public static WebServer getInstance() throws IOException {
		if(me == null) {
			try {
				me = new WebServer();
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
	private WebServer() throws IOException {
		try {
			server = new ServerSocket(PORT);
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
			System.out.println("\t\t\tPronto, in ascolto sulla porta "+PORT);
			res = server.accept();
			System.out.println("\t\t\tConnessione stabilita ");
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

	/**
	 * 
	 * @param acceptingStopped: true per fermare l'attesa di nuove connessioni
	 */
	public void setAcceptingStopped(boolean acceptingStopped) {
		this.acceptingStopped = acceptingStopped;
	}


	

}
