package storage.safetynets;

import storage.ListManager;
import utils.Config;
import co.FlightCO;

public class MonaAlert extends Alert {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MonaAlert() {
		type = "MONA";
	}

	public void update(short stat) {
		int flightId = getIntValue("flight_id");

		if (stat == Config.NEW) {
			updateTrack(flightId, true);
		} else if (stat == Config.REMOVE) {
			updateTrack(flightId, false);
		}

		flight1 = (FlightCO) ListManager.flightList.findByFlightId(flightId);

		// TODO
		content = "";
	}
}
