package parsers.offline;

import java.io.IOException;

import map.Fixpoint;
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
public class WaypointParser extends AParser {
	public WaypointParser(String ipasFile) {
		super(ipasFile);
		System.out.println("Parsing waypoint Information file");
	}

	public final void coreParsing() throws IOException {
		Fixpoint point = null;
		String tokens[] = null;
		String line = null;

		while (true) {
			line = lineReader.readLine();

			if (line == null) {
				break;
			}

			tokens = line.split(";");
			point = new Fixpoint(tokens[0]);
			point.lat = new Double(tokens[1]).doubleValue();
			point.lon = new Double(tokens[2]).doubleValue();

			itemList.add(point);
		}
	}

}
