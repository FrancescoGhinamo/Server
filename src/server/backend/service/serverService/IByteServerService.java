package server.backend.service.serverService;

import java.net.Socket;

/**
 * Interfaccia con metodi di comunicazione server - client con invio e ricezione di byte
 *
 */
public interface IByteServerService {
	
	/**
	 * Legge tutto lo stream di byte trasmesso da un client
	 * @param s: {@link Socket} con referenza al client
	 * @return contenuto in byte del flusso di comunicazione
	 */
	public byte[] leggiByteIngresso(Socket s);
	
	/**
	 * Invia ad un determinato client un flusso di byte
	 * @param bytes: flusso di byte da trasmettere al client
	 * @param s: {@link Socket} con referenza al client
	 */
	public void inviaByte(byte[] bytes, Socket s);

}
