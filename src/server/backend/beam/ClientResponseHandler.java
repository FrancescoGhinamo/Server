package server.backend.beam;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * Classe adibia alla gestione della risposta al client
 * @author franc
 *
 */
public class ClientResponseHandler implements HttpHandler {

	
	/**
	 * Codice response
	 */
	private int code;
	
	/**
	 * Response header
	 */
	private String responseHeader;
	
	/**
	 * Contenuto del responso
	 */
	private byte[] responseContent;
	
	/**
	 * Costruttore
	 * @param code: codice response
	 * @param responseHeader: header
	 * @param responseContent: contenuto in byte del responso (null in caso di errore)
	 */
	public ClientResponseHandler(int code, String responseHeader, byte[] responseContent) {
		super();
		this.code = code;
		this.responseHeader = responseHeader;
		this.responseContent = responseContent;
	}


	@Override
	public void handle(HttpExchange t) throws IOException {
		
//        t.sendResponseHeaders(code, responseHeader.length());
//        OutputStream os = t.getResponseBody();
//        os.write(responseHeader.getBytes());
//        os.close();
        
        if(responseContent != null) {
        	t.sendResponseHeaders(code, responseContent.length);
            OutputStream os = t.getResponseBody();
            os.write(responseContent);
            os.close();
        }


	}

}
