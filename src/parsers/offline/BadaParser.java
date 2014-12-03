package parsers.offline;

import java.io.File;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;

import parsers.AParser;
import storage.bada.Bada;
import storage.bada.BadaDB;

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
 * @author not attributable
 * @version 1.0
 */
public class BadaParser extends AParser {

	private String acType = null;

	public BadaParser(String badaDirectory) {

		if (BadaDB.vectorBADA != null) {
			// already filled
			return;
		} else {
			BadaDB.vectorBADA = new ArrayList<Bada>();
		}

		File dir = new File(badaDirectory);
		File files[] = dir.listFiles();

		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].getAbsolutePath().endsWith(".PTF")) {
					acType = files[i].getName().split("_")[0];
					normalFile(files[i].getAbsolutePath());
				}
			}
		}
	}

	public void coreParsing() throws IOException {

	}

	public void parserCore(LineNumberReader lineReader) throws IOException {
		String line;
		String parts[];
		int level;

		while (true) {
			line = lineReader.readLine();

			if (line == null) {
				break;
			}

			if (lineReader.getLineNumber() >= 17) {
				parts = line.split("\\|");

				try {
					level = new Integer(parts[0].trim()).intValue();
					processLine(level, parts[1], Bada.CRUISE);
					processLine(level, parts[2], Bada.CLIMB);
					processLine(level, parts[3], Bada.DESCEND);
				} catch (ArrayIndexOutOfBoundsException ex) {
				} catch (NumberFormatException ex) {
					break;
				}

				// skip one line
				line = lineReader.readLine();
			}
		}
	}

	private void processLine(int level, String line, short attitude) {
		String content[] = line.split(" +");
		int tas;
		double fnom = 0D;
		double flow = 0D;
		double fhi = 0D;

		tas = new Integer(content[1]).intValue();

		switch (attitude) {
		case Bada.CRUISE:
			flow = new Double(content[2]).doubleValue();
			fnom = new Double(content[3]).doubleValue();
			fhi = new Double(content[4]).doubleValue();
			break;

		case Bada.CLIMB:
			fnom = new Double(content[5]).doubleValue();
			break;

		case Bada.DESCEND:
			fnom = new Double(content[3]).doubleValue();
			break;
		}

		BadaDB.fill(acType, level, tas, flow, fnom, fhi, attitude);

	}
}
