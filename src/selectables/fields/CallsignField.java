package selectables.fields;

import selectables.ATrackLabel;

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
public class CallsignField extends ACallsignField {
	public CallsignField(ATrackLabel label) {
		super(label);
	}

	public void displayPopup() {
		System.out.println(label.track.flight.callsign);
	}

	public void init() {
		this.offsetX = label.cornerX;
		this.offsetY = label.cornerY;
		this.content = label.track.flight.callsign;
	}

	public String toString() {
		return this.content;
	}
}
