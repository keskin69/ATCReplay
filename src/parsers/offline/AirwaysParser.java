package parsers.offline;

import java.io.IOException;

import map.Airway;
import parsers.AParser;

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
public class AirwaysParser extends AParser {
	public AirwaysParser(String ipasFile) {
		super(ipasFile);
		System.out.println("Parsing airways information file");
	}

	public final void coreParsing() throws IOException {
		Airway airway = null;
		String tokens[] = null;
		String line = null;

		while (true) {
			tokens = findNext("IDENT");
			if (tokens == null) {
				break;
			}

			airway = new Airway(tokens[2]);

			while (true) {
				tokens = findNext("START_POINT", "RECORD");
				if (tokens == null) {
					break;
				}

				airway.points.add(tokens[2]);

				line = lineReader.readLine();
				tokens = line.split("\\s+");
				airway.points.add(tokens[2]);
			}

			itemList.add(airway);
		}
	}
}
