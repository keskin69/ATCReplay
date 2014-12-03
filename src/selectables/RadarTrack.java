package selectables;

import gui.viewport.AViewport;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import resources.Resources;
import utils.Options;
import utils.Util;
import co.FlightCO;

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
public class RadarTrack extends ATrack {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public short vectorLength = 0;

	public RadarTrack(FlightCO flight) {
		this.flight = flight;
		trackLabel = new TrackLabel(this);
	}

	public void draw(AViewport view, Graphics2D g2) {
		visible = false;

		if (flight.flight_phase != 1) {
			return;
		}

		if (!insideSector()) {
			return;
		}

		visible = true;

		y = view.lat2Y(flight.latitude);
		x = view.lon2X(flight.longitude);

		if (marked) {
			g2.draw(new Ellipse2D.Double(x - Options.MARK_SIZE, y
					- Options.MARK_SIZE, Options.MARK_SIZE * 2,
					Options.MARK_SIZE * 2));
		}

		// draw history
		if (Options.DRAW_HISTORY) {
			// don't draw the last point (current location)
			int i = 0;

			double histX;
			double histY;

			for (; i < flight.history.size() - 2;) {
				histY = ((Double) flight.history.get(i++)).doubleValue();
				histY = view.lat2Y(histY);
				histX = ((Double) flight.history.get(i++)).doubleValue();
				histX = view.lon2X(histX);
				g2.fill(new Ellipse2D.Double(histX, histY,
						Options.TRACK_SIZE / 2, Options.TRACK_SIZE / 2));
			}
		}

		// draw symbol
		setTrackColor(g2);

		if (flight.isDL) {
			// draw a circle for DL fligths
			g2.fill(new Ellipse2D.Double(x - Options.TRACK_SIZE / 2, y
					- Options.TRACK_SIZE / 2, Options.TRACK_SIZE,
					Options.TRACK_SIZE));

		} else {
			// draw a rectangle for nonDL flights
			g2.fill(new Rectangle2D.Double(x - Options.TRACK_SIZE / 2, y
					- Options.TRACK_SIZE / 2, Options.TRACK_SIZE,
					Options.TRACK_SIZE));
		}

		// draw label
		if (Options.DRAW_LABEL) {
			trackLabel.draw(view, g2);
		}

		// draw heading
		if (Options.DRAW_HEADING) {
			drawSpeedVector(view, g2);
		}

		// draw route
		if (drawRoute) {
			flight.route.draw(view, g2);
		}
	}

	private void drawSpeedVector(AViewport view, Graphics2D g2) {
		Point2D p1 = new Point.Double();
		p1.setLocation(this.flight.longitude, this.flight.latitude);

		for (int i = 1; i <= Options.SPEED_LENGTH; i++) {
			if (i % 2 == 1) {
				g2.setColor(Resources.HEADING_COLOR1);
			} else {
				g2.setColor(Resources.HEADING_COLOR2);
			}

			Point2D p2 = Util.CalculatePoint(p1.getX(), p1.getY(),
					this.flight.gspeed, this.flight.heading);
			g2.draw(new Line2D.Double(view.lon2X(p1.getX()), view.lat2Y(p1
					.getY()), view.lon2X(p2.getX()), view.lat2Y(p2.getY())));

			p1.setLocation(p2.getX(), p2.getY());
		}
	}

}
