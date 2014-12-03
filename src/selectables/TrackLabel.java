package selectables;

import gui.viewport.AViewport;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import selectables.fields.CallsignMenu;
import selectables.fields.NWPField;
import utils.Options;

/**
 * <p>
 * Title: Analog
 * </p>
 * 
 * <p>
 * Description: Analog tool package for log analayses
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Eurocontrol CRDS
 * </p>
 * 
 * @author Mustafa Keskin
 * @version 1.0
 */
public class TrackLabel extends ATrackLabel {

	// active fields
	private CallsignMenu csField = null;
	private NWPField nwpField = null;

	public TrackLabel(ATrack track) {
		this.track = track;

		// define active fields here
		this.csField = new CallsignMenu();
		this.nwpField = new NWPField();
	}

	public void drawLabel(AViewport view, Graphics2D g2) {

		// Label Content
		String line = null;
		height = 0D;

		// First Line
		// CALLSIGN+ASSUMING
		line = track.flight.callsign + " " + track.flight.assumingSector;
		printLine(g2, line);

		// Second Line
		if (Options.SHOW_LINE_2) {
			// AMODEC+ATT+W+GSPEED+NWP

			char att = ' ';
			/*
			 * TODO if (track.getIntValue("attitude") == 1) { att = 'V'; } else
			 * if (track.getIntValue("attitude") == 2) { att = '^'; }
			 */

			line = "A" + String.valueOf(track.flight.modec) + att
					+ track.flight.wake + String.valueOf(track.flight.gspeed)
					+ " " + track.flight.nwp;

			printLine(g2, line);
		}

		// Third Line
		// ALERTS
		line = "";

		// if (Options.SHOW_APW && track.alertAPW) {
		// line += "APW ";
		// }

		// if (track.alertSTCA) {
		// line += "STCA ";
		// }

		if (Options.SHOW_MTCD && track.flight.alertMTCD) {
			line += "MTCD ";
		}

		if (Options.SHOW_MONA && track.flight.alertMona) {
			line += "MONA ";
		}

		if (Options.SHOW_TDM && track.flight.alertTDM) {
			line += "TDM ";
		}

		printLine(g2, line);
	}

	public ASelectable getSelectedField(MouseEvent e) {
		csField.init(track);
		if (csField.isMouseInside(null, e)) {
			return csField;
		}

		nwpField.init(track);
		if (nwpField.isMouseInside(null, e)) {
			return nwpField;
		}

		return null;
	}

}
