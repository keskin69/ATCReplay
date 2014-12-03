package storage.safetynets;

import storage.ListManager;
import utils.Config;
import co.FlightCO;

public class MtcdAlert extends Alert {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MtcdAlert() {
		type = "MTCD";
	}

	public void update(short stat) {
		int flightId1 = getIntValue("flight_id1");
		int flightId2 = getIntValue("flight_id2");

		if (stat == Config.NEW || stat == Config.UPDATE) {
			if (getData("conflict_virtuality").equals("0")) {
				// conflict_virtuality
				// conflict_type
				updateTrack(flightId1, true);
				updateTrack(flightId2, true);
			}
		} else if (stat == Config.REMOVE) {
			updateTrack(flightId1, false);
			updateTrack(flightId2, false);
		}

		flight1 = (FlightCO) ListManager.flightList.findByFlightId(flightId1);
		flight2 = (FlightCO) ListManager.flightList.findByFlightId(flightId2);

		content = getStrValue("min_separation");
	}
}
