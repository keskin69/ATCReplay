package storage.safetynets;

import storage.ListManager;
import utils.Config;
import co.FlightCO;

public class TdmAlert extends Alert {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TdmAlert() {
		type = "TDM";
	}

	public void update(short stat) {
		int flightId1 = getIntValue("flight_id");

		if (stat == Config.NEW) {
			updateTrack(flightId1, true);
		} else if (stat == Config.REMOVE) {
			updateTrack(flightId1, false);
		}

		flight1 = (FlightCO) ListManager.flightList.findByFlightId(flightId1);

		// TODO
		content = "";
	}

}
