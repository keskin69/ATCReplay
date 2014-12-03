package storage;

import java.io.IOException;
import java.io.LineNumberReader;

import utils.Config;

/**
 * 
 * @author mkeskin
 */
public class Element extends DynamicArray {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int updateTime = 0;
	private int id = 0;

	/** Creates a new instance of Element */
	public Element() {
	}

	public int getFlightId() {
		return getIntValue("flight_id");
	}

	public int getId() {
		return id;
	}

	public int getTime() {
		return updateTime;
	}

	public void init(int id, LineNumberReader lineReader) throws IOException {
		this.id = id;
		update(lineReader, Config.NEW);
	}

	public void update(LineNumberReader lineReader, short stat)
			throws IOException {
		String line = null;
		String tmp = null;
		int numOfLine = 0;

		if (stat == Config.REMOVE) {
			return;
		}

		line = lineReader.readLine();
		tmp = line.split("=")[1];
		numOfLine = new Integer(tmp.trim()).intValue();

		int i = 0;
		for (; i < numOfLine; i++) {
			if (line.startsWith("viewport_")) {
				i--;
				continue;
			}

			line = lineReader.readLine();
			update(line);
		}

		updateTime();

		if (stat == Config.NEW) {
			// unlock
			line = lineReader.readLine();

			// discard
			line = lineReader.readLine();
		}
	}

	private void updateTime() {
		String str = getData(Config.TIME_STR);

		if (!str.equals("")) {
			try {
				updateTime = new Integer(str).intValue();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
