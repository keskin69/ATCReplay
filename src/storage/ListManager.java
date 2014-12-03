package storage;

import gui.MainWindow;
import gui.ToolBar;

import java.io.LineNumberReader;

import parsers.offline.AirportParser;
import parsers.offline.AirwaysParser;
import parsers.offline.FixParser;
import parsers.offline.SectorParser;
import parsers.offline.TSAParser;
import parsers.offline.WaypointParser;
import selectables.RadarTrack;
import selectables.Route;
import selectables.VerticalTrack;
import storage.safetynets.AlertList;
import storage.safetynets.ApwAlert;
import storage.safetynets.MonaAlert;
import storage.safetynets.MtcdAlert;
import storage.safetynets.StcaAlert;
import storage.safetynets.TdmAlert;
import utils.Config;
import utils.Util;
import co.ExerciseCO;
import co.FlightCO;
import co.TSACO;

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
public class ListManager {
	// co
	public static ElementList flightList = null;
	public static AlertList stcaList = null;
	public static AlertList mtcdList = null;
	public static AlertList tdmList = null;
	public static AlertList monaList = null;
	public static AlertList apwList = null;
	public static ElementList routeList = null;
	public static ElementList exercise = null;
	public static ElementList tsaList = null;

	// map
	public static ElementList tsaSectorList = null;
	public static ElementList sectorList = null;
	public static ElementList fixList = null;
	public static ElementList airportList = null;
	public static ElementList airwaysList = null;
	public static ElementList waypointList = null;

	public static boolean init = false;

	private static void defaultBlock(LineNumberReader lineReader, short stat)
			throws Exception {
		String line = lineReader.readLine();
		String tmp = line.split("=")[1];
		int numOfLine = new Integer(tmp.trim()).intValue();

		for (int i = 0; i < numOfLine; i++) {
			if (line.startsWith("viewport_")) {
				i--;
				continue;
			}

			line = lineReader.readLine();

			if (line.startsWith(Config.TIME_STR)) {
				ExerciseCO.updateTime(new Integer(line.split("=")[1].trim())
						.intValue());
			}
		}

		if (stat == Config.NEW) {
			// unlock
			line = lineReader.readLine();

			// discard
			line = lineReader.readLine();
		}
	}

	public static void init() {
		flightList = new ElementList(FlightCO.class);
		stcaList = new AlertList(StcaAlert.class);
		tdmList = new AlertList(TdmAlert.class);
		monaList = new AlertList(MonaAlert.class);
		mtcdList = new AlertList(MtcdAlert.class);
		apwList = new AlertList(ApwAlert.class);
		tsaList = new ElementList(TSACO.class);
		routeList = new ElementList(Route.class);
		exercise = new ElementList(ExerciseCO.class);
	}

	/**
	 * update
	 * 
	 * @param lineReader
	 *            LineReader
	 * @param line
	 *            String
	 * @throws Exception
	 */
	public static Element update(LineNumberReader lineReader, String line)
			throws Exception {
		String tokens[] = null;
		Element elem = null;
		short stat = -1;

		tokens = line.split(" ");

		if (tokens[0].equals("construct")) {
			stat = Config.NEW; // new
		} else if (tokens[0].equals("set")) {
			stat = Config.UPDATE; // update
		} else if (tokens[0].equals("destruct")) {
			stat = Config.REMOVE; // remove
		} else {
			System.out.println("ERROR at " + lineReader.getLineNumber());
		}

		int id = new Integer(tokens[2]).intValue();

		if (tokens[1].equals(Config.EONSFLIGHTPLAN)) {
			elem = updateFlight(lineReader, id, stat);
		} else if (tokens[1].equals(Config.EONSEXERCISE)) {
			elem = exercise.update(lineReader, id, stat);

			ExerciseCO.CURRENT_TIME = elem.getIntValue("time");
			if (stat == Config.NEW) {
				String tmp = elem.getData("start_time");
				ExerciseCO.START_TIME = Util.getTime(tmp);
				ExerciseCO.simu = elem.getData("name");
				ExerciseCO.traffic = elem.getData("traffic");
			}
		} else if (tokens[1].equals(Config.EONSSTCA)) {
			elem = stcaList.update(lineReader, id, stat);
		} else if (tokens[1].equals(Config.EONSTDM)) {
			elem = tdmList.update(lineReader, id, stat);
		} else if (tokens[1].equals(Config.EONSMONA)) {
			elem = monaList.update(lineReader, id, stat);
		} else if (tokens[1].equals(Config.EONSAPW)) {
			elem = apwList.update(lineReader, id, stat);
		} else if (tokens[1].equals(Config.EONSROUTE)) {
			elem = updateRoute(lineReader, id, stat);
		} else if (tokens[1].equals(Config.EONSMTCD)) {
			elem = mtcdList.update(lineReader, id, stat);
		} else if (tokens[1].equals(Config.EONSRESERVED)) {
			elem = tsaList.update(lineReader, id, stat);
		} else if (stat != Config.REMOVE) {
			defaultBlock(lineReader, stat);
		}

		return elem;
	}

	/**
	 * updateFlight
	 * 
	 * @param lineReader
	 *            LineReader
	 * @param id
	 *            int
	 * @param stat
	 *            short
	 * @return Element
	 * @throws Exception
	 */
	private static Element updateFlight(LineNumberReader lineReader, int id,
			short stat) throws Exception {
		FlightCO flight = (FlightCO) flightList.update(lineReader, id, stat);

		// update callsign list
		MainWindow.getInstance().pnlCallsign.updateCallsignList(flight, stat);

		// update load panel
		MainWindow.getInstance().pnlLoad.updateDataset();

		if (stat == Config.NEW) {
			// add to track lists
			MainWindow.getInstance().radarWindow.pnlView.trackList.put(
					flight.callsign, new RadarTrack(flight));
			MainWindow.getInstance().verticalWindow.pnlView.trackList.put(
					flight.callsign, new VerticalTrack(flight));
		} else if (stat == Config.REMOVE) {
			// Remove from the track lists
			MainWindow.getInstance().radarWindow.pnlView.trackList
					.remove(flight.callsign);
			MainWindow.getInstance().verticalWindow.pnlView.trackList
					.remove(flight.callsign);
		}

		return flight;
	}

	/**
	 * updateRoute
	 * 
	 * @param lineReader
	 *            LineReader
	 * @param id
	 *            int
	 * @param stat
	 *            short
	 * @return Element
	 * @throws Exception
	 */
	private static Element updateRoute(LineNumberReader lineReader, int id,
			short stat) throws Exception {
		Route route = (Route) routeList.update(lineReader, id, stat);

		if (stat == Config.NEW) {
			FlightCO flight = (FlightCO) flightList.findByFlightId(route
					.getFlightId());
			flight.setRoute(route);
			route.flight = flight;
		}

		route.updateTrackValues();

		return route;
	}

	public void initIPAS() {
		init();

		// read sector maps
		try {
			SectorParser parser = new SectorParser(
					Config.SECTOR_DEFINITION_FILE);
			sectorList = parser.getList();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// read fix points
		try {
			FixParser parser = new FixParser(Config.NAVIGATION_POINTS_FILE);
			fixList = parser.getList();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// read waypoints
		try {
			WaypointParser parser = new WaypointParser(Config.WAYPOINTS_FILE);
			waypointList = parser.getList();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// read airways
		try {
			AirwaysParser parser = new AirwaysParser(Config.AIRWAYS_FILE);
			airwaysList = parser.getList();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// read TSA areas
		try {
			TSAParser parser = new TSAParser(Config.AIRSPACE_FILE);
			tsaSectorList = parser.getList();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// read airports
		try {
			AirportParser parser = new AirportParser(Config.AIRPORT_FILE);
			airportList = parser.getList();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		init = true;
		ToolBar.REPLAY_STATUS = ToolBar.run_status.PAUSED;
	}
}
