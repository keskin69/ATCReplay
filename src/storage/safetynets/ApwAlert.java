package storage.safetynets;

import storage.ListManager;
import utils.Config;
import co.FlightCO;

public class ApwAlert extends Alert {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ApwAlert() {
		type = "APW";
	}

	public void update(short stat) {
		int flightId = getIntValue("track_id");

		if (stat == Config.NEW) {
			updateTrack(flightId, true);
		} else if (stat == Config.REMOVE) {
			updateTrack(flightId, false);
		}

		flight1 = (FlightCO) ListManager.flightList.findByFlightId(flightId);

		content = flight1.callsign + "&nbsp;"
				+ getStrValue("reserved_area_name");
	}
}
