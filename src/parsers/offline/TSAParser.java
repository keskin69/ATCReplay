package parsers.offline;

import java.io.IOException;

import map.TSASector;
import utils.Options;

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
public class TSAParser extends SectorParser {
	public TSAParser(String ipasFile) {
		super(ipasFile);
		System.out.println("Parsing TSA Information file");
	}

	public final void coreParsing() throws IOException {
		TSASector tsa = null;
		String tokens[] = null;

		while (true) {
			tokens = findNext("RESERVED_AREA_NAME");
			if (tokens == null) {
				break;
			}

			tsa = new TSASector(tokens[2]);

			try {
				while (true) {
					// skip until to read BOX
					// exit if you reach BOX
					tokens = findNext(lineReader, "BOX", "RESERVED_AREA");
					if (tokens == null) {
						break;
					}

					tsa.addPolygon();

					readSegment(lineReader, tsa);
				}
			} catch (IOException ex) {
				// to exit the while loop
			}

			itemList.add(tsa);
		}

		Options.SELECTED_TSA_MAPS = new boolean[itemList.size()];
		Options.TSA_LIST = new String[itemList.size()];

		int i = 0;
		for (Object obj : itemList) {
			Options.TSA_LIST[i++] = ((TSASector) obj).name;
		}
	}

}
