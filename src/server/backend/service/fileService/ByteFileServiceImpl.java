package server.backend.service.fileService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Implementazione dell'interfaccia {@link IByteFileService}
 * @author franc
 *
 */
public class ByteFileServiceImpl implements IByteFileService {
	
	/**
	 * Costruttore package
	 */
	ByteFileServiceImpl() {
		
	}

	@Override
	public byte[] leggiByte(File f) {
		FileInputStream fis = null;
		ByteArrayOutputStream bArrStr = new ByteArrayOutputStream();
		try {
			fis = new FileInputStream(f);
			
			byte[] buffer = new byte[4096];
			int nRead = 0;
			
			while((nRead = fis.read(buffer, 0, buffer.length)) != -1) {
				bArrStr.write(buffer, 0, nRead);
			}
			
			
		} catch (IOException e) {
			
		}
		finally {
			if(fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					
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
	public void scriviByte(File f, byte[] bytes) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(f);
			fos.write(bytes, 0, bytes.length);
		} catch (IOException e) {
			
		}
		finally {
			if(fos != null) {
				try {
					fos.flush();
				} catch (IOException e) {
					
				}
				finally {
					try {
						fos.close();
					} catch (IOException e) {
						
					}
				}
				
			}
		}

	}

}
