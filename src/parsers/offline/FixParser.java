package parsers.offline;

import java.io.IOException;

import map.Fixpoint;
import parsers.AParser;

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
public class FixParser extends AParser {
	public FixParser(String ipasFile) {
		super(ipasFile);
		System.out.println("Parsing Fixpoint Information file");
	}

	public final void coreParsing() throws IOException {
		Fixpoint point = null;
		String tokens[] = null;
		String line = null;

		while (true) {
			tokens = findNext("IDENT");
			if (tokens == null) {
				break;
			}

			point = new Fixpoint(tokens[2]);

			tokens = findNext("LAT");
			if (tokens == null) {
				break;
			}

			point.lat = new Double(tokens[3]).doubleValue();

			line = lineReader.readLine();
			tokens = line.split("\\s+");
			point.lon = new Double(tokens[3]).doubleValue();

			itemList.add(point);
		}
	}
}
