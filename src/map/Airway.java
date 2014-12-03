package map;

import gui.viewport.AViewport;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import resources.Resources;
import selectables.ASelectable;
import storage.ListManager;
import utils.Options;

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
public class Airway extends ASelectable {
	public String name = "";
	public ArrayList<String> points = null;

	public Airway(String name) {
		this.name = name;
		setDrawColor(Resources.AIRWAYS_COLOR);
		points = new ArrayList<String>();
	}

	public void draw(AViewport view, Graphics2D g2) {
		double x1 = 0D;
		double y1 = 0D;
		double x2 = 0D;
		double y2 = 0D;
		double lon = 0D;
		double lat = 0D;
		String pointName = null;
		Point2D p = null;

		g2.setColor(drawColor);

		for (int i = 0; i < points.size();) {
			pointName = (String) points.get(i++);
			p = getLatLon(pointName);

			if (p == null) {
				break;
			}

			lon = p.getX();
			lat = p.getY();

			x1 = view.lon2X(lon);
			y1 = view.lat2Y(lat);

			pointName = (String) points.get(i++);
			p = getLatLon(pointName);

			if (p == null) {
				break;
			}

			lon = p.getX();
			lat = p.getY();

			x2 = view.lon2X(lon);
			y2 = view.lat2Y(lat);

			if (Options.DRAW_AIRWAYS) {
				g2.draw(new Line2D.Double(x1, y1, x2, y2));
			}
		}
	}

	private Point2D getLatLon(String pointName) {
		Point2D p = null;
		Fixpoint fix = null;

		for (int i = 0; i < ListManager.waypointList.size(); i++) {
			fix = (Fixpoint) ListManager.waypointList.get(i);
			if (fix.name.equals(pointName)) {
				p = new Point2D.Double(fix.lon, fix.lat);
				return p;
			}

		}

		return p;
	}

	public boolean isMouseInside(AViewport view, MouseEvent e) {
		return false;
	}

}
