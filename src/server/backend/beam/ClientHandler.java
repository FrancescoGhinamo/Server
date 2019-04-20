package server.backend.beam;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import server.backend.service.fileService.ByteFileServiceFactory;
import server.backend.service.fileService.IByteFileService;
import server.backend.service.serverService.ByteServerServiceFactory;
import server.backend.service.serverService.IByteServerService;

/**
 * Thread per la gestione della comunicazione con un client
 * @author franc
 *
 */
public class ClientHandler implements Runnable {

	/**
	 * Socket stramite cui � connesso il client
	 */
	private Socket clientSocket;
	
	/**
	 * true per fermare il ClientHandler
	 */
	private boolean stopped;
	
	/**
	 * Servizio per la gestione dei file
	 */
	private IByteFileService fileService;
	
	/**
	 * Servizio per la comunicazione server
	 */
	private IByteServerService serverService;
	
	/**
	 * Costruttore del ClientHandler
	 * @param clientSocket: {@link Socket} tramite cui � connesso il client
	 */
	public ClientHandler(Socket clientSocket) {
		super();
		this.clientSocket = clientSocket;
		stopped = false;
		this.fileService = ByteFileServiceFactory.getByteFileService();
		this.serverService = ByteServerServiceFactory.getByteServerService();
	}

	


	@Override
	public void run() {
		while(!stopped) {
			//Usare la factory
//			System.out.println(String.valueOf(serverService.leggiByteIngresso(clientSocket)));
			
			try {
				DataInputStream input = new DataInputStream(clientSocket.getInputStream());
				byte [] ris = new byte[5];
				input.read(ris);
				
				ris.clone();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//...
			//da risposta al client
			//...
		}

	}

}
