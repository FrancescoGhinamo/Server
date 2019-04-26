package server.backend.beam;

import java.io.File;
import java.io.FilenameFilter;


public class PageFileFilter implements FilenameFilter {

	

	@Override
	public boolean accept(File dir, String name) {
		boolean accept = false;
		if(name.endsWith(".html")) {
			accept = true;
		}
		return accept;
	}

}
