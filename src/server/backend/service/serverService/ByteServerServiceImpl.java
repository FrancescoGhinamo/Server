package server.backend.service.serverService;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Implementazione dell'interfaccia {@link IByteFileService}
 * @author franc
 *
 */
public class ByteServerServiceImpl implements IByteServerService {
	
	/**
	 * Costruttore package
	 */
	public ByteServerServiceImpl() {
		
	}

	@Override
	public byte[] leggiByteIngresso(Socket s) {
		DataInputStream dis = null;
		ByteArrayOutputStream bArrStr = new ByteArrayOutputStream();
		try {
			dis = new DataInputStream(s.getInputStream());
			
			byte[] buffer = new byte[4096];
			int nRead = 0;
			
			while((nRead = dis.read(buffer, 0, buffer.length)) != -1) {
				bArrStr.write(buffer, 0, nRead);
			}
			
		} catch (IOException e) {
			
		}
		finally {
			if(dis != null) {
				try {
					dis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(bArrStr != null) {
				try {
					bArrStr.close();
				} catch (IOException e) {
					
				}
			}
		}
		
		
		return bArrStr.toByteArray();
	}

	@Override
	public void inviaByte(byte[] bytes, Socket s) {
		//IMPLEMENTARE

	}

}
