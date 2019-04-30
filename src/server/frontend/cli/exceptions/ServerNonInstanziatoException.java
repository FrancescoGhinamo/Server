package server.frontend.cli.exceptions;

/**
 * Eccezione generata quando si sta operando su un {@link WebServer} non ancora istanziato
 * @author franc
 *
 */
public class ServerNonInstanziatoException extends Exception {

	
	private static final long serialVersionUID = 314944217170709584L;
	
	/**
	 * Costruttore con messaggio di default
	 */
	public ServerNonInstanziatoException() {
		super("Il server non e' ancora stato istanziato");
	}
	
	
	/**
	 * Costruttore con messaggio personalizzato
	 */
	public ServerNonInstanziatoException(String msg) {
		super(msg);
	}

}
