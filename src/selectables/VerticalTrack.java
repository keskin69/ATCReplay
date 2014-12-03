package selectables;

import gui.viewport.AViewport;
import gui.viewport.vertical.VerticalView;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import resources.Resources;
import utils.Options;
import co.FlightCO;

public class VerticalTrack extends ATrack {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VerticalTrack(FlightCO flight) {
		this.flight = flight;
		trackLabel = new VerticalLabel(this);

		assert (trackLabel != null);
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

		// draw symbol
		y = ((VerticalView)view).alt2Z(flight.modec);
		x = view.lon2X(flight.longitude);

		setTrackColor(g2);

		g2.fill(new Ellipse2D.Double(x - Options.TRACK_SIZE / 2, y
				- Options.TRACK_SIZE / 2, Options.TRACK_SIZE,
				Options.TRACK_SIZE));

		// draw label
		if (Options.DRAW_LABEL) {
			trackLabel.draw(view, g2);
		}

		// draw route
		if (drawRoute) {
			g2.setColor(Resources.ROUTE_LINE_COLOR);
			g2.setStroke(Resources.ROUTE_LINE);
			
			int first = flight.route.getCurrentPoint() + 1;
			int points = flight.route.getIntValue("points");
	
			double z1 = 0D;
			double lon1 = 0D;
			double z2 = 0D;
			double lon2 = 0D;			
			double fl = 0;

			for (int i = first; i < points-1;) {
				if (i == first) {
					z1 = y;
					lon1 = x;
				} else {
					z1 = z2;
					lon1 = lon2;
				}

				i++;
				fl = flight.route.getIntValue("computed_fl", i) / 100;
				z2 = ((VerticalView)view).alt2Z(fl);
				lon2 = flight.route.getDblValue("longitude", i);
				lon2 = view.lon2X(lon2);

				g2.draw(new Line2D.Double(lon1, z1, lon2, z2));
			}
		}
	}
}
