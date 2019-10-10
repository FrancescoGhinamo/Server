package server.backend.service.fileService;

/**
 * Classe factory per {@link IByteFileService}
 *
 */
public class ByteFileServiceFactory {
	
	/**
	 * Ritorno del servizio
	 * @return servizio {@link IByteFileService}
	 */
	public static IByteFileService getByteFileService() {
		return (IByteFileService)new ByteFileServiceImpl();
	}
}
