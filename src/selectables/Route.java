package selectables;

import gui.viewport.AViewport;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;

import resources.Resources;
import utils.Options;
import utils.Util;
import co.ExerciseCO;
import co.RouteCO;

public class Route extends RouteCO implements ISelectable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void draw(AViewport view, Graphics2D g2) {
		int points = getIntValue("points");
		double lat1 = 0D;
		double lon1 = 0D;
		double lat2 = 0D;
		double lon2 = 0D;

		g2.setFont(Resources.SMALL_FONT);

		int current = getCurrentPoint() + 1;
		int eto = 0;

		// Draw overflown route
		if (Options.DRAW_HISTORY_ROUTE || Options.MODE == Options.AIRWAY_USAGE) {
			drawOverflownRoute(view, g2, current);
		}

		// Draw future route
		if (Options.MODE == Options.NORMAL_MODE) {
			for (int i = current; i < points - 1;) {
				if (i == current) {
					lat1 = view.lat2Y(flight.latitude);
					lon1 = view.lon2X(flight.longitude);
				} else {
					lat1 = lat2;
					lon1 = lon2;
				}

				i++;
				lat2 = getDblValue("latitude", i);
				lat2 = view.lat2Y(lat2);

				lon2 = getDblValue("longitude", i);
				lon2 = view.lon2X(lon2);

				// Normal route leg
				g2.setColor(Resources.ROUTE_LINE_COLOR);
				g2.setStroke(Resources.ROUTE_LINE);
				g2.draw(new Line2D.Double(lon1, lat1, lon2, lat2));

				// Make a buffer zone around the route
				g2.setStroke(Resources.ROUTE_SHADOW);
				g2.setColor(Resources.ROUTE_SHADOW_COLOR);
				g2.draw(new Line2D.Double(lon1, lat1, lon2, lat2));

				// Route points information; eto and level
				// TODO
				eto = getIntValue("eto", i) + ExerciseCO.START_TIME;
				g2.setColor(Resources.ROUTE_TEXT_COLOR);
				g2.drawString(Util.getTimeStrShort(eto), (float) lon2,
						(float) lat2);

				// reset stroke
				g2.setStroke(Resources.NORMAL_LINE);

				// TODO
				// show a mark
			}
		}
	}

	public void drawOverflownRoute(AViewport view, Graphics2D g2, int current) {
		double lat1 = 0D;
		double lon1 = 0D;
		double lat2 = 0D;
		double lon2 = 0D;
		int eto = 0;

		for (int i = 0; i < current - 1;) {
			if (i == 0) {
				lat1 = getDblValue("latitude", i);
				lat1 = view.lat2Y(lat1);

				lon1 = getDblValue("longitude", i);
				lon1 = view.lon2X(lon1);
			} else {
				lat1 = lat2;
				lon1 = lon2;
			}

			i++;

			if ((i < current - 2) || (Options.MODE == Options.AIRWAY_USAGE)) {
				lat2 = getDblValue("latitude", i);
				lat2 = view.lat2Y(lat2);

				lon2 = getDblValue("longitude", i);
				lon2 = view.lon2X(lon2);
			} else {
				lat2 = view.lat2Y(flight.latitude);
				lon2 = view.lon2X(flight.longitude);
			}

			// print eto and level
			// TODO
			if (Options.MODE != Options.AIRWAY_USAGE) {
				eto = getIntValue("eto", i) + ExerciseCO.START_TIME;
				g2.setStroke(Resources.DASH_LINE);

				if (i < current) {
					g2.setColor(Resources.FIX_COLOR);
					g2.drawString(Util.getTimeStrShort(eto), (float) lon2,
							(float) lat2);
				}
			} else {
				// draw buffer
				g2.setStroke(Resources.ROUTE_SHADOW);
				g2.setColor(Resources.ROUTE_SHADOW_COLOR);
				g2.draw(new Line2D.Double(lon1, lat1, lon2, lat2));
				g2.setColor(Resources.ROUTE_LINE_COLOR);
			}

			g2.draw(new Line2D.Double(lon1, lat1, lon2, lat2));
		}
	}

	public boolean isMouseInside(AViewport view, MouseEvent e) {
		return false;
	}
}
