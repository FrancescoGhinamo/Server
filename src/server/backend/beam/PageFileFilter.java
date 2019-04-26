package server.backend.beam;

import java.io.File;
import java.io.FilenameFilter;

import javax.swing.filechooser.FileFilter;

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
