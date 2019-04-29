package server.backend.beam;

<<<<<<< HEAD
import java.io.ByteArrayOutputStream;

import java.io.DataInputStream;
=======
>>>>>>> 78e44cefd574ff25fc7d070fb576c07e307e98c4
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
<<<<<<< HEAD
import java.net.*;
=======
>>>>>>> 78e44cefd574ff25fc7d070fb576c07e307e98c4
import java.text.SimpleDateFormat;
import java.util.Calendar;


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
	 * La pagina web e' stata trovata e non si sono presentati errori interni
	 */
	private static final String CODE_OK = "200 OK";

	/**
	 * La pagina web non e' stata trovata
	 */
	private static final String CODE_NOT_FOUND = "404 Not Found";

	/**
	 * Il contenuto che verra' inviato � o file html
	 */
	private static final String TYPE_HTML = "text/html";

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


	/**
	 * Crea la stringa di risposta per il client del tipo:
	 * HTTP/1.1 200 OK
	 * Date: Mon, 27 Jul 2009 12:28:53 GMT
	 * Server: Apache/2.2.14 (Win32)
	 * Last-Modified: Wed, 22 Jul 2009 19:15:56 GMT
	 * Content-Length: 88
	 * Content-Type: text/html
	 * Connection: Closed
	 *
	 * @param pagina: nome dalla pagina cercata
	 * @param code: codice responso
	 * @param contLength: lunghezza del contenuto da inviare
	 * @param contType: tipo di contenuto
	 * @param connOpen: true se la connessione e' ancora aperta, altrimenti false
	 * @return stringa di risposta
	 */
	public String creaResponseHeader(String pagina, String code, int contLength, String contType, boolean connOpen) {

		StringBuffer strBuff = new StringBuffer();
		SimpleDateFormat fmt = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");

		strBuff.append("HTTP/" + pagina + " 1.1 " + code + "\r\n");
		strBuff.append("Date: " + fmt.format(Calendar.getInstance().getTime()) + "\r\n");
		strBuff.append("Server: Server (Win64)\r\n");
		strBuff.append("Last-Modified: Sun, 28 Apr 2019 19:15:56 GMT\r\n");
		strBuff.append("Content-Length: " + contLength + "\r\n");
		strBuff.append("Content-Type: " + contType + "\r\n");
		if(connOpen) {
			strBuff.append("Connection: Open\r\n");
		}
		else {
			strBuff.append("Connection: Closed\r\n");
		}
		return strBuff.toString();
	}


	/**
	 * Ricerca del file corrispondete alla pagina dato il nome
	 * @param nome: nome della pagina da ricercare
	 * @return file corrispondente alla pagina
	 */
	private File recuperaPagina(String nome) throws FileNotFoundException {
		File file = null;
		try {
			for(int conta=0;conta<Server.getInstance().getPagine().length;conta++)
			{
				if(nome.equals(Server.getInstance().getPagine()[conta].getName()))
				{
					file=Server.getInstance().getPagine()[conta];

				}
			}
		} catch (IOException e) {

		}

		if(!file.exists()) {
			throw new FileNotFoundException();
		}
		return file;
	}

	/**
	 * Estrae dalla stringa di richiesta il nome della pagina cercata
	 * @param richiesta: richiesta pervenuta dal client
	 * @return nome della pagina cercata
	 */
	private String estraiNomePagina(String richiesta) {
		return richiesta.substring(richiesta.indexOf('/') + 1, richiesta.indexOf("HTTP") - 1);
	}

	@Override
	public void run() {
		while(!stopped) {

			//lettura della richista
			byte [] byteLetti = serverService.leggiByteIngresso(clientSocket);

			String richiesta = null;
			//conversione della richiesta in stringa
			richiesta = new String(byteLetti);
			System.out.println(richiesta);

			//recupero del nome della pagina cercata
			String nomePagina = estraiNomePagina(richiesta);


			/*
			 * Recupero del file corrispondente all pagina
			 * Invio del file al client
			 * Invio messaggio di errore nel caso di pagina non trovata
			 */
			try {
				
				//recupero del file corrispondente
				File filePagina = recuperaPagina(nomePagina);
				//lettura dal disco della pagina
				byte[] page = fileService.leggiByte(filePagina);

//				String strResponso = creaResponseHeader(nomePagina, CODE_OK, page.length, TYPE_HTML, true);

				/*
				 * METODO PIU' LINEARE E NON FUNZIONANTE
				 */
				
				

				
				//invio del responso
//				serverService.inviaByte(strResponso.getBytes(), clientSocket);
				

				//invio della pagina
				serverService.inviaByte(page, clientSocket);
				


			} catch (FileNotFoundException e) {

				//la pagina non � stata trovata -> responso di errore
				
				
				String strResponso = creaResponseHeader(nomePagina, CODE_NOT_FOUND, 0, "", true);
				
				

			}




		}

	}
}
