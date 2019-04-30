package server.frontend.cli;

import java.io.IOException;

import server.backend.beam.comm.WebServer;
import server.frontend.cli.exceptions.ServerNonInstanziatoException;

/**
 * Offre il controllo tramite CLI del server
 * @author franc
 *
 */
public class ServerManagerCLI {


	/**
	 * Server controllato
	 */
	private WebServer webServer;


	/**
	 * Costruttore del ServerManager
	 * @throws IOException: eccezioni lanciate durante la creazione del server
	 */
	public ServerManagerCLI() throws IOException {
		try {
			webServer = WebServer.getInstance();
		} catch (IOException e) {
			throw e;
		}
	}

	/**
	 * Attiva il server
	 * @throws ServerNonInstanziatoException: il server su cui si sta operando non e' ancora stato istanziato
	 */
	public void startServer() throws ServerNonInstanziatoException {
		if(webServer != null) {
			new Thread(webServer).start();
		}
		else {
			throw new ServerNonInstanziatoException();
		}
	}

	public static void main(String[] args) {
		
		/*
		 * creare menu con varie opzioni...
		 */
		try {
			ServerManagerCLI sMan = new ServerManagerCLI();
			try {
				sMan.startServer();
			} catch (ServerNonInstanziatoException e) {
				System.out.println("\n" + e.getMessage());
			}
		} catch (IOException e) {
			System.out.println("\n\tProblema durante l'istanza del server\n");
			System.out.println(e.getMessage());
		}

	}

}
