package server.frontend.cli;

import java.io.IOException;

import server.backend.beam.comm.WebServer;
import server.in.Menu;

/**
 * Offre il controllo tramite CLI del server
 *
 */
public class ServerManagerCLI {

	/**
	 * Voci del menu
	 */
	private static final String[] VOCI_MENU = {"AvviaServer", "Termina attesa nuove connessioni", "Uscire"};

	
	/**
	 * Costruttore del ServerManager
	 */
	public ServerManagerCLI() {
		
	}

	/**
	 * Attiva il server
	 * @throws IOException: lanciata in caso di errori durante l'attivazione del server
	 */
	public void startServer() throws IOException {
		try {
			new Thread(WebServer.getInstance()).start();
		} catch (IOException e) {
			throw e;
		}
	}
	
	/**
	 * Interrompe l'attesa di nuove connessioni
	 * @throws IOException: sollevata in caso di errore durante la richiesta dell'istanza del server
	 */
	public void stopServer() throws IOException {
		try {
			WebServer.getInstance().setAcceptingStopped(true);
		} catch (IOException e) {
			throw e;
		}
	}
	

	public static void main(String[] args) {
	
		ServerManagerCLI sMan = new ServerManagerCLI();
		
		Menu mnu = new Menu(VOCI_MENU);
		System.out.println("\tWEB SERVER");
		int scelta = 0;
		do {
			scelta = mnu.sceltaMenu();
			switch(scelta) {
			case 1:
				try {
					sMan.startServer();
				} catch (IOException e) {
					System.out.println("\t" + e.getMessage());
				}
				break;
				
			case 2:
				try {
					sMan.stopServer();
				} catch (IOException e) {
					System.out.println("\t" + e.getMessage());
				}
				break;
				
			case 3:
				System.out.println("\n\t\t *** Uscita dal programma ***");
				break;
				
				default:
					System.out.println("\t*** Opzione non riconosciuta ***");
			}
		}
		while(scelta != 0);
		
		System.exit(0);

	}

}
