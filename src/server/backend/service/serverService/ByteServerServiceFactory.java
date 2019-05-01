package server.backend.service.serverService;


/**
 * Classe factory per {@link IByteServerService}
 *
 */
public class ByteServerServiceFactory {

	/**
	 * Ritorno del servizio
	 * @return servizio {@link IByteServerService}
	 */
	public static IByteServerService getByteServerService() {
		return (IByteServerService)new ByteServerServiceImpl();
	}
}
