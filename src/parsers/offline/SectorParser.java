package parsers.offline;

import java.io.IOException;
import java.io.LineNumberReader;

import map.Sector;
import utils.Config;
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
public class SectorParser extends IPASParser {
	public SectorParser(String ipasFile) {
		super(ipasFile);
		System.out.println("Parsing Sector Information file");
	}

	public void coreParsing() throws IOException {
		Sector sector = null;
		String tokens[] = null;
		String sectorName;
		String sectorList = "";

		while (true) {
			// skip until to read IDENT
			tokens = findNext(lineReader, "IDENT");
			if (tokens == null) {
				break;
			}

			sectorName = tokens[2];
			sector = new Sector(sectorName, tokens[3]);

			try {
				while (true) {
					// skip until to read BOX
					// exit if you reach BOX
					tokens = findNext(lineReader, "BOX", "RECORD");
					if (tokens == null) {
						break;
					}

					sector.addPolygon();
					readSegment(lineReader, sector);
				}
			} catch (IOException ex) {
				// to exit the while loop
			}

			// exclude PILOT info screens
			if (!sector.name.startsWith(Config.IGNORE_SECTOR_WITH)) {
				itemList.add(sector);
				sectorList += sector.name + ",";
			}
		}

		Options.SECTOR_LIST = sectorList.split(",");
		Options.SELECTED_SECTORS = new boolean[Options.SECTOR_LIST.length];
		Options.HIGHLIGHT_SECTOR_MAP = new boolean[Options.SECTOR_LIST.length];

		for (int i = 0; i < Options.SECTOR_LIST.length; i++) {
			Options.SELECTED_SECTORS[i] = true;
			Options.HIGHLIGHT_SECTOR_MAP[i] = false;
		}
	}

	private final void readLatLon(LineNumberReader lineReader, Sector sector)
			throws IOException {
		double lat, lon;

		// until end of segment
		String tokens[] = findNext(lineReader, "LAT", "ENDL");
		if (tokens == null) {
			throw new IOException();
		}

		lat = new Double(tokens[3]).doubleValue();

		String line = lineReader.readLine();
		tokens = line.split("\\s+");
		lon = new Double(tokens[3]).doubleValue();

		sector.addVertex(lon, lat);
	}

	final void readSegment(LineNumberReader lineReader, Sector sector)
			throws IOException {
		String line = null;
		String tokens[] = null;

		try {
			while (true) {
				readLatLon(lineReader, sector);
				readLatLon(lineReader, sector);

				// read upper-lower level limits for sector
				tokens = findNext(lineReader, "LEVEL_INF");
				sector.flow = new Integer(tokens[2]);

				line = lineReader.readLine();
				tokens = line.split("\\s+");
				sector.ftop = new Integer(tokens[2]);
			}
		} catch (IOException ex) {
			// to exit the while loop
		}
	}
}
