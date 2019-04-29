package server.backend.beam.util;

/**
 * Eccezione lanciata in caso si stia cercado una risorsa inesistente sul server
 * @author franc
 *
 */
public class ResourceNotFoundException extends Exception {

	
	private static final long serialVersionUID = 6729779447996722964L;
	
	/**
	 * Costruttore con messaggio di default
	 */
	public ResourceNotFoundException() {
		super("Risorsa non trovata");
	}
	
	/**
	 * Costruttore con messaggio personalizzato
	 * @param msg: messaggio per l'eccezione
	 */
	public ResourceNotFoundException(String msg) {
		super(msg);
	}

}
