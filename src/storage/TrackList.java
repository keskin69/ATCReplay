package storage;

import java.util.HashMap;

import selectables.ATrack;

public class TrackList extends HashMap<String, ATrack> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ATrack findByCallsign(String callsign) {
		return (ATrack) this.get(callsign);
	}
}
