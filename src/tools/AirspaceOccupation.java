package tools;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import co.FlightCO;

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
public class AirspaceOccupation {
	class FlightInfo {
		int id = 0;
		ArrayList<Point2D> points = null;

		public FlightInfo(int id) {
			this.id = id;
			points = new ArrayList<Point2D>();
		}

		public void addPoint(Point2D p) {
			points.add(p);

			if (points.size() > MAX_LOCATION) {
				points.remove(0);
			}
		}
	}

	private final int MAX_LOCATION = 10;

	private ArrayList<FlightInfo> locationVector = null;

	public AirspaceOccupation() {
		locationVector = new ArrayList<FlightInfo>();
	}

	public void draw(Graphics2D g2) {
		FlightInfo flightInfo = null;
		Point2D p = null;

		for (int i = 0; i < locationVector.size(); i++) {
			flightInfo = (FlightInfo) locationVector.get(i);

			for (int j = 0; j < flightInfo.points.size(); j++) {
				p = (Point2D) flightInfo.points.get(j);
				g2.fill(new Ellipse2D.Double(p.getX(), p.getY(), 3, 3));
			}
		}
	}

	private FlightInfo getFlight(int id) {
		FlightInfo flight = null;

		for (int i = 0; i < locationVector.size(); i++) {
			flight = (FlightInfo) locationVector.get(i);
			if (flight.id == id) {
				return flight;
			}
		}

		return null;
	}

	public void updateFlight(FlightCO flight) {
		int id = flight.getId();
		FlightInfo flightInfo = getFlight(id);

		if (flightInfo == null) {
			// a new flight
			locationVector.add(new FlightInfo(id));
		} else {
			// it exists before
			Point2D p = new Point2D.Double(flight.longitude, flight.latitude);
			flightInfo.points.add(p);
		}
	}
}
