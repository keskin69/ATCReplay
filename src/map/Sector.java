package map;

import gui.viewport.AViewport;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import resources.Resources;
import selectables.ASelectable;
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
public class Sector extends ASelectable {
	public String name = null;
	public String freq = null;
	public int flow = 0;
	public int ftop = 0;
	public ArrayList<Polygon> polyList = null;
	public final static double CONV = 1000000D;

	public Sector(String name, String freq) {
		this.name = name;
		this.freq = freq;

		polyList = new ArrayList<Polygon>();
	}

	public void addPolygon() {
		polyList.add(new Polygon());
	}

	// add a vertex to current polygon inside the sector
	public void addVertex(double lon, double lat) {
		polyList.get(polyList.size() - 1).addPoint((int) (lon * CONV),
				(int) (lat * CONV));
	}

	/**
	 * draw Sectors
	 */
	public void draw(AViewport view, Graphics2D g2) {
		g2.setFont(Resources.SMALL_FONT);
		g2.setColor(drawColor);

		if (isSelected()) {
			setDrawColor(Resources.SELECTED_SECTOR_COLOR);

			for (Polygon poly : polyList) {
				fillPoly(view, poly, g2);
			}
		} else {
			setDrawColor(Resources.SECTOR_COLOR);

			for (Polygon poly : polyList) {
				drawPoly(view, poly, g2);
			}
		}

	}

	final protected void drawPoly(AViewport view, Polygon poly, Graphics2D g2) {
		double y1 = 0D;
		double x1 = 0D;
		double y2 = 0D;
		double x2 = 0D;

		for (int i = 0; i < poly.npoints; i += 2) {
			y1 = view.lat2Y(poly.ypoints[i] / CONV);
			x1 = view.lon2X(poly.xpoints[i] / CONV);
			y2 = view.lat2Y(poly.ypoints[i + 1] / CONV);
			x2 = view.lon2X(poly.xpoints[i + 1] / CONV);

			g2.draw(new Line2D.Double(x1, y1, x2, y2));
		}
	}

	final protected void fillPoly(AViewport view, Polygon poly, Graphics2D g2) {
		int y = 0;
		int x = 0;
		Polygon p = new Polygon();

		for (int i = 0; i < poly.npoints; i++) {
			y = (int) view.lat2Y(poly.ypoints[i] / CONV);
			x = (int) view.lon2X(poly.xpoints[i] / CONV);
			p.addPoint(x, y);
		}

		g2.fill(p);
	}

	public boolean isInsideSector(double lon, double lat) {
		for (Polygon poly : polyList) {
			if (poly.contains(lon * CONV, lat * CONV)) {
				return true;
			}
		}

		return false;
	}

	public boolean isInsideSector(double lon, double lat, int alt) {
		if (flow >= alt || ftop >= alt) {
			for (Polygon poly : polyList) {
				if (poly.contains(lon * CONV, lat * CONV)) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean isMouseInside(AViewport view, MouseEvent e) {
		double lon = view.x2Lon(e.getX()) * CONV;
		double lat = view.y2Lat(e.getY()) * CONV;

		return isInsideSector(lon, lat);
	}

	public boolean isSelected() {
		for (int i = 0; i < Options.SECTOR_LIST.length; i++) {
			if (this.name.equals(Options.SECTOR_LIST[i])) {
				return Options.HIGHLIGHT_SECTOR_MAP[i];
			}
		}

		assert (true);
		return false;
	}
}
