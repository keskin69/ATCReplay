package storage.safetynets;

import storage.ListManager;
import utils.Config;
import co.FlightCO;

public class StcaAlert extends Alert {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static boolean newStca = false;

	public StcaAlert() {
		type = "STCA";
	}

	public void update(short stat) {
		int flightId1 = getIntValue("track_id1");
		int flightId2 = getIntValue("track_id2");

		if (stat == Config.NEW || stat == Config.UPDATE) {
			updateTrack(flightId1, true);
			updateTrack(flightId2, true);
		} else if (stat == Config.REMOVE) {
			updateTrack(flightId1, false);
			updateTrack(flightId2, false);
		}

		if (stat == Config.NEW) {
			newStca = true;
		}

		flight1 = (FlightCO) ListManager.flightList.findByFlightId(flightId1);
		flight2 = (FlightCO) ListManager.flightList.findByFlightId(flightId2);

		content = getStrValue("min_separation");
	}
}
