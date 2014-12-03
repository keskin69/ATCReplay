package gui.viewport;

import gui.MainWindow;

import java.awt.Polygon;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import map.Sector;
import selectables.ATrack;
import storage.ElementList;
import storage.TrackList;

public abstract class AViewport extends JPanel implements MouseWheelListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final int MAX_SCROLL = 100;
	private final int MAX_NUM_ZOOM = 10;

	protected double rangeLon = 0D;
	protected double rangeLat = 0D;

	public double latMin = 0D;
	public double latMax = 0D;
	public double lonMin = 0D;
	public double lonMax = 0D;

	public double viewHeight = 0;
	public double viewWidth = 0;
	private double lonLatRatio = 0D;

	private double zoomStep = 0D;
	private short zoomCounter = 0;

	protected double centerLat = 0D;
	protected double centerLon = 0D;

	public Rectangle2D boundary = null;
	private int lastScrollX = MAX_SCROLL / 2;
	private int lastScrollyY = MAX_SCROLL / 2;

	// boundary of the universe
	public double minLat = Double.MAX_VALUE;
	public double minLon = Double.MAX_VALUE;
	public double maxLat = Double.MIN_VALUE;
	public double maxLon = Double.MIN_VALUE;

	public ATrack selectedTrack = null;
	public TrackList trackList = new TrackList();

	/*
	 * public static boolean showFrame = false; public static int frameX1 =0;
	 * public static int frameY1 =0; public static int frameX2 =0; public static
	 * int frameY2 =0;
	 */

	public BufferedImage mapImage = null;

	/**
	 * changeCenter
	 * 
	 * @param lon
	 *            int
	 * @param lat
	 *            int
	 */
	public void changeCenter(int xVal, int yVal) {
		double lon = centerLon + rangeLon * (xVal - lastScrollX) / MAX_SCROLL;
		double lat = centerLat - rangeLat * (yVal - lastScrollyY) / MAX_SCROLL;

		if (checkLimit(lon, lat)) {
			centerLon += rangeLon * (xVal - lastScrollX) / MAX_SCROLL;
			centerLat -= rangeLat * (yVal - lastScrollyY) / MAX_SCROLL;

			lastScrollX = xVal;
			lastScrollyY = yVal;
		}
	}

	/**
	 * changeZoom
	 * 
	 * @param factor
	 *            int
	 * @author
	 */
	public void changeZoom(int factor) {
		if (factor < 0 && zoomCounter > (-1 * MAX_NUM_ZOOM)) {
			zoomCounter--;
			rangeLon -= zoomStep;
			rangeLat = rangeLon / lonLatRatio;

			updateView();
		} else if (factor > 0 && zoomCounter < 0) {
			zoomCounter++;
			rangeLon += zoomStep;
			rangeLat = rangeLon / lonLatRatio;

			updateView();
		}
	}

	private boolean checkLimit(double Lon, double Lat) {
		double latMinTemp = Lat - (rangeLat / 2D);
		double latMaxTemp = Lat + (rangeLat / 2D);
		double lonMinTemp = Lon - (rangeLon / 2D);
		double lonMaxTemp = Lon + (rangeLon / 2D);

		return (latMinTemp > minLat && latMaxTemp < maxLat
				&& lonMinTemp > minLon && lonMaxTemp < maxLon);
	}

	public void dragCenter(int xVal, int yVal) {
		double Lon = centerLon - (rangeLon * xVal) / viewWidth;
		double Lat = centerLat + (rangeLat * yVal) / viewHeight;

		if (checkLimit(Lon, Lat)) {
			centerLon = Lon;
			centerLat = Lat;

			updateView();
		}
	}

	public void highlightTrack(ATrack track) {
		if (selectedTrack != null) {
			selectedTrack.selected = false;
		}

		if (track != null) {
			selectedTrack = track;
			selectedTrack.selected = true;
		}

		repaint();
	}

	/*
	 * public void setFrame(int X1, int Y1, int X2, int Y2) { frameX1 = X1;
	 * frameX2 = X2; frameY1 = Y1; frameY2 = Y2;
	 * 
	 * // draw a frame showFrame = true; updateView(); }
	 * 
	 * public void removeFrame(){ showFrame = false; }
	 */

	public abstract void init();

	public double lat2Y(double lat) {
		double y = viewHeight - viewHeight * ((lat - latMin) / rangeLat);

		return y;
	}

	public double lon2X(double lon) {
		double x = viewWidth * ((lon - lonMin) / rangeLon);

		return x;
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		changeZoom(e.getWheelRotation());
	}

	public void setBackgroundMap(BufferedImage mapImage) {
		this.mapImage = mapImage;

		/*
		 * ((Graphics2D)this.getGraphics()).setRenderingHints (new
		 * RenderingHints (RenderingHints.KEY_ANTIALIASING,
		 * RenderingHints.VALUE_ANTIALIAS_ON));
		 */
	}

	public void setCenter(int X, int Y) {
		centerLat = y2Lat(Y);
		centerLon = x2Lon(X);

		updateView();
	}

	public abstract void setMouseListener(MouseListener mouseListener);

	public void setSectorBoundary(ElementList sectorList) {
		Sector sector = null;

		double lon = 0;
		double lat = 0;

		for (int i = 0; i < sectorList.size(); i++) {
			sector = (Sector) sectorList.get(i);

			for (Polygon poly : sector.polyList) {
				for (int j = 0; j < poly.npoints; j++) {
					lat = poly.ypoints[j] / Sector.CONV;
					lon = poly.xpoints[j] / Sector.CONV;

					if (lon < minLon) {
						minLon = lon;
					}

					if (lon > maxLon) {
						maxLon = lon;
					}

					if (lat < minLat) {
						minLat = lat;
					}

					if (lat > maxLat) {
						maxLat = lat;
					}
				}
			}
		}

		boundary = new Rectangle2D.Double();
		boundary.add(new Point2D.Double(minLon, minLat));
		boundary.add(new Point2D.Double(maxLon, maxLat));

		rangeLon = maxLon - minLon;
		rangeLat = maxLat - minLat;

		centerLat = minLat + (rangeLat / 2D);
		centerLon = minLon + (rangeLon / 2D);

		lonLatRatio = rangeLon / rangeLat;
		zoomStep = rangeLon / MAX_NUM_ZOOM;

		updateView();
	}

	/**
	 * updateView
	 */
	private void updateView() {
		latMin = centerLat - (rangeLat / 2D);
		latMax = centerLat + (rangeLat / 2D);
		lonMin = centerLon - (rangeLon / 2D);
		lonMax = centerLon + (rangeLon / 2D);

		MainWindow.getInstance().updateRadarView();
	}

	public double x2Lon(double x) {
		double lon = lonMin + rangeLon * x / viewWidth;

		return lon;
	}

	public double y2Lat(double y) {
		double lat = latMin + rangeLat * (viewHeight - y) / viewHeight;

		return lat;
	}
}
