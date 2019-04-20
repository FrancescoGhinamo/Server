package server.backend.service.fileService;

import java.io.File;

/**
 * Interfaccia contenente metodi per la lettura e scrittura di byte su file
 * @author franc
 *
 */
public interface IByteFileService {
	
	/**
	 * Legge tutti i byte contenuti in un file
	 * @param f: referenza al file sorgente
	 * @return array di byte contenuti nel file
	 */
	public byte[] leggiByte(File f);

	/**
	 * Scrive su file i byte specificati
	 * @param f: referenza al file destinazione
	 * @param bytes: array di byte da scrivere sul file
	 */
	public void scriviByte(File f, byte[] bytes);
}
