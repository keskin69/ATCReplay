package parsers.offline;

import java.io.IOException;
import java.io.LineNumberReader;

import parsers.AParser;
import storage.ElementList;
import utils.Config;

public abstract class IPASParser extends AParser {
	public IPASParser(String fileName) {
		// ipas parsing
		itemList = new ElementList();

		// use IPAS directory selected by the user
		parse(Config.IPASDirectory, fileName);
	}

	protected String[] findNext(LineNumberReader lineReader, String pattern)
			throws IOException {
		String line = null;

		while (true) {
			line = lineReader.readLine();

			if (line == null) {
				break;
			}

			if (line.matches("\\s+" + pattern + ".*")) {
				return line.split("\\s+");
			}
		}

		return null;
	}

	protected String[] findNext(LineNumberReader lineReader, String pattern,
			String endPattern) throws IOException {
		String line = null;

		while (true) {
			line = lineReader.readLine();

			if (line == null) {
				break;
			}

			if (line.matches("\\s+" + pattern + ".*")) {
				return line.split("\\s+");
			} else if (line.matches("\\s+" + endPattern + ".*")) {
				return null;
			}
		}

		return null;
	}
}
