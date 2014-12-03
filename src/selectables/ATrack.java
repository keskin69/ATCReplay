package selectables;

import gui.viewport.AViewport;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import resources.Resources;

import utils.Config;
import utils.Options;
import co.FlightCO;

public abstract class ATrack implements ISelectable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public double x = 0;
	public double y = 0;
	public boolean visible = true;
	public FlightCO flight = null;
	public ATrackLabel trackLabel = null;
	public boolean selected = false;
	public boolean drawRoute = false;
	public Color trackColor = null;
	public boolean marked = false;

	public abstract void draw(AViewport view, Graphics2D g2);

	protected boolean insideSector() {
		if (!flight.assumingSector.equals("")) {
			for (int i = 0; i < Options.SECTOR_LIST.length; i++) {
				if (Options.SECTOR_LIST[i].equals(flight.assumingSector)) {
					return Options.SELECTED_SECTORS[i];
				}
			}
		} else {
			return true;
		}

		return false;
	}

	public boolean isMouseInside(AViewport view, MouseEvent e) {
		if (visible) {
			if (Math.abs(x - e.getPoint().getX()) <= Options.TRACK_SIZE
					&& Math.abs(y - e.getPoint().getY()) <= Options.TRACK_SIZE) {
				return true;
			}
		}

		return false;
	}

	protected void setTrackColor(Graphics2D g2) {
		if (flight.type == Config.EONS_MILITARY_FLIGHT) {
			g2.setColor(Resources.MILITARY_COLOR);
		} else if (flight.alertSTCA) {
			g2.setColor(Resources.TRACK_ALERT_COLOR);
		} else if (flight.alertAPW) {
			g2.setColor(Resources.WARNING_COLOR);
		} else {
			g2.setColor(Resources.TRACK_COLOR);
		}

		trackColor = g2.getColor();
	}
}
