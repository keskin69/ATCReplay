package selectables.fields;

import gui.MainWindow;

import java.awt.event.MouseEvent;

import selectables.ATrack;

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
public class NWPField extends AField {
	public void action(int mouseButton) {
		if (mouseButton == MouseEvent.BUTTON1) {
			track.drawRoute = !track.drawRoute;
			MainWindow.getInstance().updateRadarView();
		}
	}

	public void init(ATrack track) {
		this.track = track;
		this.content = track.flight.nwp;

		// set the corner of the field relative to the track location
		this.offsetX = track.trackLabel.cornerX;
		this.offsetY = track.trackLabel.cornerY;
	}

}
