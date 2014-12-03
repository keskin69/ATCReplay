package selectables;

import gui.viewport.AViewport;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class VerticalLabel extends ATrackLabel {

	public VerticalLabel(ATrack track) {
		this.track = track;

		assert (this.track != null);
	}

	public void drawLabel(AViewport view, Graphics2D g2) {
		// callsign
		height = 0D;
		String line = track.flight.callsign;
		printLine(g2, line);
	}

	@Override
	public ASelectable getSelectedField(MouseEvent e) {
		// TODO Auto-generated method stub
		return null;
	}
}
