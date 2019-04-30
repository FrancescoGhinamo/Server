package server.backend.beam.comm;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import server.backend.beam.util.ResourceNotFoundException;
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
	 * Il contenuto che verra' inviato e' file html
	 */
	private static final String TYPE_HTML = "text/html";

	/**
	 * Il contenuto che verra' inviato e' documento codificato
	 */
	private static final String TYPE_XHTML = "application/xhtml+xml";

	/**
	 * Il contenuto che verra' inviato e' documento codificato
	 */
	private static final String TYPE_XML = "application/xml";

	/**
	 * Immagina per web formattata
	 */
	private static final String TYPE_IMAGE_WEBP = "image/webp";

	/**
	 * Animated PNG 
	 */
	private static final String TYPE_IMAGE_APNG = "image/apng";

	/**
	 * Generica immagine
	 */
	private static final String TYPE_IMAGE_GENERIC = "image/*";

	/**
	 * Generica risorsa
	 */
	private static final String TYPE_GENERIC = "*/*";


	/**
	 * Socket stramite cui è connesso il client
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
	 * @param clientSocket: {@link Socket} tramite cui è connesso il client
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
		strBuff.append("\r\n");
		return strBuff.toString();
	}


	/**
	 * Ricerca del file corrispondete alla pagina dato il nome
	 * @param nome: nome della pagina da ricercare
	 * @return file corrispondente alla pagina
	 * @throws ResourceNotFoundException: risorsa cercata non trovata
	 */
	private File recuperaPagina(String nome) throws ResourceNotFoundException {

		File file = null;
		/*
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
		 */

		//ricerca del file nel file system
		file = new File(Server.SERVER_ROOT + "\\" + nome);

		//controllo dell'esisteza del file
		if(!file.exists()) {
			throw new ResourceNotFoundException();
		}



		return file;
	}

	/**
	 * Estrae dalla stringa di richiesta il nome della pagina cercata
	 * @param richiesta: richiesta pervenuta dal client
	 * @return nome della pagina cercata
	 */
	private String estraiNomePagina(String richiesta) {
		String ris = "";
		try {
			ris = richiesta.substring(richiesta.indexOf('/') + 1, richiesta.indexOf("HTTP") - 1);
		}
		catch(StringIndexOutOfBoundsException e) {
			ris = "";
		}
		return ris;
	}

	/**
	 * Dato il nome della risorsa cercata ne identifica il tipo
	 * @param nome: nome della risorsa cercata
	 * @return tipo corrispondente per il response header
	 * @throws ResourceNotFoundException: la risorsa cercata non e' stata trovata
	 */
	private String tipoDocumento(String nome) throws ResourceNotFoundException {
		String type = "";

		int i = 0;
		if((i = nome.lastIndexOf('.')) != -1) {
			String ext = nome.substring(i + 1);
			switch(ext) {
			case "html":
				type = TYPE_HTML;
				break;

			case "htm":
				type = TYPE_HTML;
				break;

			case "xhtml":
				type = TYPE_XHTML;
				break;

			case "xml":
				type = TYPE_XML;
				break;

			case "webp":
				type = TYPE_IMAGE_WEBP;
				break;

			case "apng":
				type = TYPE_IMAGE_APNG;
				break;

			case "png":
				type = TYPE_IMAGE_GENERIC;
				break;

			case "ico":
				type = TYPE_IMAGE_GENERIC;
				break;

			case "jpg":
				type = TYPE_IMAGE_GENERIC;
				break;

			case "jpeg":
				type = TYPE_IMAGE_GENERIC;
				break;

			case "gif":
				type = TYPE_IMAGE_GENERIC;
				break;

			case "bmp":
				type = TYPE_IMAGE_GENERIC;
				break;


			default:
				type = TYPE_GENERIC;

			}
		}
		else {
			throw new ResourceNotFoundException();
		}



		return type;
	}

	/**
	 * Concatena due array di byte
	 * @param arr1: array posizionato per primo
	 * @param arr2: array posizionato per secondo
	 * @return array contenente arr1 e arr2 concatenati (arr1 prima di arr2)
	 */
	private byte[] concat(byte[] arr1, byte[] arr2) {
		byte[] ris = new byte[arr1.length + arr2.length];

		int index = 0;
		for(int i = 0; i < arr1.length; i++) {
			ris[index++] = arr1[i];
		}

		for(int i = 0; i < arr2.length; i++) {
			ris[index++] = arr2[i];
		}

		return ris;
	}

	@Override
	public void run() {

		//lettura della richista
		byte [] byteLetti = serverService.leggiByteIngresso(clientSocket);

		String richiesta = null;
		//conversione della richiesta in stringa
		richiesta = new String(byteLetti);
		System.out.println(richiesta);

		//recupero del nome della pagina cercata
		String nomeRisorsa = estraiNomePagina(richiesta);




		//se e' stato inserito una risorsa da cercare
		if(!nomeRisorsa.equals("")) {
			/*
			 * Recupero del file corrispondente all pagina
			 * Invio del file al client
			 * Invio messaggio di errore nel caso di pagina non trovata
			 */
			try {

				//recupero del file corrispondente
				File filePagina = recuperaPagina(nomeRisorsa);
				//lettura dal disco della pagina
				byte[] response = fileService.leggiByte(filePagina);

				//recupero tipo di risorsa richiesta
				String tipo = tipoDocumento(nomeRisorsa);
				//			System.out.println(tipo);

				//preparazione response header
				String strResponseHeader = creaResponseHeader(nomeRisorsa, CODE_OK, response.length, tipo, false);

				//preparazione responso
				if(tipo.equals(TYPE_HTML) && richiesta.contains("Chrome")) {
					response = concat(strResponseHeader.getBytes(), response);
				}


				//invio del responso
				serverService.inviaByte(response, clientSocket);



			} catch (ResourceNotFoundException e) {

				//la pagina non è stata trovata -> responso di errore


				System.out.println("Risorsa non trovata");
				//lettura dal disco della pagina di errore 404

				try {
					byte[] response = fileService.leggiByte(recuperaPagina("404.htm"));
					String strResponso = creaResponseHeader("404.html", CODE_OK, response.length, TYPE_HTML, false);
					if(richiesta.contains("Chrome")) {
						response = concat(strResponso.getBytes(), response);
					}

					serverService.inviaByte(response, clientSocket);
				} catch (ResourceNotFoundException ex) {

				}



			}

		}
		else {

			//invio pagina di benvenuto
			System.out.println("Invio pagina di benvenuto");

			try {
				byte[] response = fileService.leggiByte(recuperaPagina("welcome.htm"));
				String strResponso = creaResponseHeader("welcome.html", CODE_OK, response.length, TYPE_HTML, false);
				
				if(richiesta.contains("Chrome")) {
					response = concat(strResponso.getBytes(), response);
				}

				serverService.inviaByte(response, clientSocket);
			} catch (ResourceNotFoundException ex) {

			}
		}

		try {
			clientSocket.close();
		} catch (IOException e) {

		}




	}
}
