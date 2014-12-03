package co;

import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;

import selectables.Route;
import storage.Element;
import tools.AirspaceUsage;
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
public class FlightCO extends Element {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// from flight plan CO
	public double heading = 0;
	public int gspeed = 0;
	public double latitude = 0D;
	public double longitude = 0D;
	public String callsign = null;
	public int flight_phase = 0;
	public int modec = 0;
	public String nwp = "nwp";
	public Route route = null;
	public int type = 0;
	public boolean isDL = false;
	public char wake = 0;

	// external
	public boolean alertSTCA = false;
	public boolean alertMTCD = false;
	public boolean alertMona = false;
	public boolean alertAPW = false;
	public boolean alertTDM = false;

	public String assumingSector = "";
	public ArrayList<Double> history = null;

	public FlightCO() {
		history = new ArrayList<Double>();
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	/**
	 * update
	 * 
	 * @param lineReader
	 *            LineReader
	 * @param stat
	 *            short
	 * @throws Exception
	 */
	public void update(LineNumberReader lineReader, short stat)
			throws IOException {
		super.update(lineReader, stat);

		callsign = getData("callsign");
		flight_phase = getIntValue("flight_phase");
		heading = getDblValue("heading");
		latitude = getDblValue("latitude");
		longitude = getDblValue("longitude");
		modec = getIntValue("modec");
		gspeed = (int) getDblValue("g_speed");
		wake = 'M';
		type = getIntValue("flight_type");
		isDL = (getIntValue("ads_c_equipment") == 1);

		// record the new history position
		if (Options.HISTORY_SIZE < history.size()) {
			history.remove(0);
			history.remove(1);
		}

		history.add(new Double(latitude));
		history.add(new Double(longitude));

		if (Options.MODE.equals(Options.AIRSPACE_USAGE)) {
			if (flight_phase == 1) {
				AirspaceUsage.getInstance().update(longitude, latitude, modec);
			}
		}
	}

}
