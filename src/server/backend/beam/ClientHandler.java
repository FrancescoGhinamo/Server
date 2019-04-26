package server.backend.beam;

import java.io.File;
import java.io.IOException;
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

			byte [] ris = serverService.leggiByteIngresso(clientSocket);


			char[] chars = new char[ris.length];
			for(int i = 0; i < ris.length; i++) {
				chars[i] = (char) ris[i];
			}
			File file = null;
			String indirizzo = String.valueOf(chars);
			String nome=indirizzo.substring(indirizzo.indexOf("//"), indirizzo.indexOf("HTTP"));
			try {
				for(int conta=0;conta<Server.getInstance().getPagine().length;conta++)
				{
					if(nome.equals(Server.getInstance().getPagine()[conta].getName()))
					{
						file=Server.getInstance().getPagine()[conta];
						
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println(indirizzo);

			byte[] page = fileService.leggiByte(file);
			serverService.inviaByte(page, clientSocket);


		}

	}
}
