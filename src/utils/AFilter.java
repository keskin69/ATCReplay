package utils;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * <p>
 * Title: Replay Tool For ACE
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * 
 * <p>
 * Company: Eurocontrol - CRDS
 * </p>
 * 
 * @author Mustafa Keskin
 * @version 1.0
 */
public class AFilter extends FileFilter {
	public String extension = null;
	private String descr = null;

	public AFilter(String extension, String descr) {
		this.extension = extension;
		this.descr = descr;
	}

	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}

		String name = f.getName();

		return name.endsWith(extension);
	}

	public String getDescription() {
		return descr + " (*" + extension + ")";
	}
}
