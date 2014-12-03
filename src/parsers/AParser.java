package parsers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Enumeration;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import storage.ElementList;
import utils.Config;

/**
 * <p>
 * Title: Analog
 * </p>
 * 
 * <p>
 * Description: Analog tool package for log analayses
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Eurocontrol CRDS
 * </p>
 * 
 * @author Mustafa Keskin
 * @version 1.0
 */
public abstract class AParser {
	protected ElementList itemList = null;
	protected LineNumberReader lineReader = null;
	public String logFileName = null;

	public AParser() {
	}

	public AParser(String fileName) {
		// ipas parsing
		itemList = new ElementList();

		// use IPAS directory selected by the user
		parse(Config.IPASDirectory, fileName);
	}

	public abstract void coreParsing() throws IOException;

	protected String[] findNext(String pattern) throws IOException {
		String line = null;

		while (true) {
			line = lineReader.readLine();

			if (line == null) {
				break;
			}

			if (line.matches("\\s+" + pattern + "\\s+.*")) {
				return line.split("\\s+");
			}
		}

		return null;
	}

	protected String[] findNext(String pattern, String endPattern)
			throws IOException {
		String line = null;

		while (true) {
			line = lineReader.readLine();

			if (line == null) {
				break;
			}

			if (line.matches("\\s+" + pattern + "\\s+.*")) {
				return line.split("\\s+");
			} else if (line.matches("\\s+" + endPattern + "\\s+.*")) {
				return null;
			}
		}

		return null;
	}

	/**
	 * Reads a GZip file inside a zip file, iterating through each entry and
	 * dumping the contents to the console.
	 */
	private void getGZIP2Reader(String zipPackage, String gzFileName) {
		ZipFile zipFile = null;
		BufferedReader gzipReader = null;

		try {
			// ZipFile offers an Enumeration of all the files in the Zip file
			zipFile = new ZipFile(zipPackage);

			for (Enumeration<? extends ZipEntry> e = zipFile.entries(); e
					.hasMoreElements();) {
				ZipEntry zipEntry = (ZipEntry) e.nextElement();

				if (zipEntry.getName().endsWith(gzFileName)) {
					// we found the file inside the zip package
					logFileName = new File(zipEntry.getName()).getName();

					gzipReader = new BufferedReader(new InputStreamReader(
							new GZIPInputStream(zipFile
									.getInputStream(zipEntry))));

					while (!gzipReader.ready()) {
					}
					lineReader = new LineNumberReader(gzipReader);
					coreParsing();
					gzipReader.close();
				}
			}
		} catch (IOException ioe) {
			System.out.println("An IOException occurred: " + ioe.getMessage());

			if (gzipReader != null) {
				try {
					gzipReader.close();
				} catch (IOException ex) {
				}
			}
		}

		if (gzipReader == null) {
			System.out.println(gzFileName + " cannot be found inside "
					+ zipPackage);
		}
	}

	public ElementList getList() {
		return itemList;
	}

	/**
	 * It creates a LineReader for the given file and calls the parserCore
	 * routine
	 * 
	 * @param fileName
	 *            String
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	protected void normalFile(String fileName) {
		try {
			lineReader = new LineNumberReader(new FileReader(fileName));
			coreParsing();
			lineReader.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * This will called the parserCore function after creating a lineReader
	 * 
	 * @param zipName
	 *            String
	 * @param fileName
	 *            String
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	protected void parse(String packageName, String fileName) {
		if (packageName != null && packageName.endsWith(".zip")) {
			if (fileName.endsWith("gz")) {
				// A gzip file inside a zip package
				getGZIP2Reader(packageName, fileName);
			} else {
				// a normal file in a zip package
				// search for the fileName in the zip package and read it
				readZipFile(packageName, fileName);
			}
		} else if (fileName.endsWith(".gz")) {
			// it is a single gzip file
			readGZIPFile(fileName);
		} else if (packageName != null) {
			// it is a normal file inside a directory
			normalFile(packageName + Config.FILE_SEPERATOR + fileName);
		} else {
			// it is not a compressed file
			normalFile(fileName);
		}
	}

	/**
	 * This function reads a single gzip compressed file and called parserCore
	 * function to be parsed
	 * 
	 * @param fileName
	 *            String
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	protected void readGZIPFile(String fileName) {
		try {
			lineReader = new LineNumberReader(new InputStreamReader(
					new GZIPInputStream(new FileInputStream(fileName))));

			while (!lineReader.ready()) {
			}

			coreParsing();
			lineReader.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (lineReader != null) {
				try {
					lineReader.close();
				} catch (IOException ioe) {
				}
			}
		}
	}

	/**
	 * This function search for the fileName inside the zip package and then it
	 * calls parserCore routine to parse the given file
	 * 
	 * @param zipName
	 *            String
	 * @param fileName
	 *            String
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	protected void readZipFile(String zipPackage, String fileNamePattern) {

		ZipFile zipFile = null;
		ZipEntry zipEntry = null;
		String zipContentName = null;

		try {
			try {
				zipFile = new ZipFile(zipPackage);
			} catch (IOException ex) {
				ex.printStackTrace();
			}

			for (Enumeration<? extends ZipEntry> e = zipFile.entries(); e
					.hasMoreElements();) {
				zipEntry = (ZipEntry) e.nextElement();

				zipContentName = new File(zipEntry.getName()).getName();

				if (zipContentName.matches(fileNamePattern)) {
					logFileName = zipContentName;

					// we found the file name
					lineReader = new LineNumberReader(new InputStreamReader(
							zipFile.getInputStream(zipEntry)));

					// waits until reader is ready
					while (!lineReader.ready()) {
					}
					coreParsing();
					lineReader.close();
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (zipFile != null) {
				try {
					zipFile.close();
				} catch (IOException ioe) {
				}
			}
		}
	}

}
