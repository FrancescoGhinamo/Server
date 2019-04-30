package server.backend.beam.util;

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
