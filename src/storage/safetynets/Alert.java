package storage.safetynets;

import storage.Element;
import storage.ListManager;
import co.FlightCO;

public abstract class Alert extends Element {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String content = null;
	public String type;
	public FlightCO flight1;
	public FlightCO flight2;

	public abstract void update(short stat);

	public void updateTrack(int flightId, boolean status) {
		FlightCO flight = (FlightCO) ListManager.flightList
				.findByFlightId(flightId);

		if (flight != null) {
			if (this instanceof StcaAlert) {
				flight.alertSTCA = status;
			} else if (this instanceof ApwAlert) {
				flight.alertAPW = status;
			} else if (this instanceof TdmAlert) {
				flight.alertTDM = status;
			} else if (this instanceof MonaAlert) {
				flight.alertMona = status;
			} else if (this instanceof MtcdAlert) {
				flight.alertMTCD = status;
			}
		}
	}
}
