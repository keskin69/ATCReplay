package tools;

import gui.viewport.AViewport;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

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
public class AirspaceUsage {
	private static final short WIDTH = 100;
	private static final short HEIGHT = 100;
	private static final int MARK_SIZE = 10;

	private static int space[][] = null;
	public static AViewport viewport = null;
	public static AirspaceUsage instance = null;

	public static AirspaceUsage getInstance() {
		if (instance == null) {
			instance = new AirspaceUsage();
		}

		return instance;
	}

	private static int mapX(double lon) {
		double w = (lon - viewport.minLon)
				/ ((viewport.maxLon - viewport.minLon) / WIDTH);

		return (int) w;
	}

	private static int mapY(double lat) {
		double h = (lat - viewport.minLat)
				/ ((viewport.maxLat - viewport.minLat) / HEIGHT);

		return (int) (HEIGHT - h);
	}

	private static double ReverseMapX(int w) {
		double lon = viewport.minLon + w * (viewport.maxLon - viewport.minLon)
				/ WIDTH;
		return viewport.lon2X(lon);
	}

	private static double ReverseMapY(int h) {
		double lat = viewport.minLat + (HEIGHT - h)
				* (viewport.maxLat - viewport.minLat) / HEIGHT;
		return viewport.lat2Y(lat);
	}

	private AirspaceUsage() {
		space = new int[WIDTH][HEIGHT];
	}

	public void draw(Graphics2D g2) {
		double y = 0;
		double x = 0;
		Color color = null;

		for (int w = 0; w < WIDTH; w++) {
			for (int h = 0; h < HEIGHT; h++) {
				if (space[w][h] > 0) {
					x = ReverseMapX(w);
					y = ReverseMapY(h);

					color = new Color(space[w][h] % 255, 80, 100);
					g2.setColor(color);

					g2.fill(new Ellipse2D.Double(x - MARK_SIZE / 2, y
							- MARK_SIZE / 2, MARK_SIZE, MARK_SIZE));
				}
			}
		}
	}

	public void update(double x, double y, int modec) {
		int w = mapX(x);
		int h = mapY(y);

		try {
			space[w][h] += 1;
		} catch (ArrayIndexOutOfBoundsException ex) {

		}
	}

}
