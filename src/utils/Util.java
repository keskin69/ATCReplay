package utils;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jfree.data.time.Minute;

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
public final class Util {

	// Mean radius in KM
	final static double EARTH_RADIUS = 6371.0;
	final static double NM2KM = 1.852;

	public static double calculateBearing(Point2D p1, Point2D p2) {
		double res = -(double) (Math.atan2(p2.getY() - p1.getY(), p2.getX()
				- p1.getX()) * 180 / Math.PI) + 90.0D;

		if (res < 0)
			return res + 360.0f;
		else
			return res;
	}

	// Destination point given distance and bearing from start point
	// distance in NM
	public static Point2D CalculatePoint(double lon1, double lat1,
			double distance, double brng) {

		double dd = Math.toRadians(distance * NM2KM / EARTH_RADIUS);

		lon1 = Math.toRadians(lon1);
		lat1 = Math.toRadians(lat1);
		brng = Math.toRadians(brng);

		double lat2 = Math.asin(Math.sin(lat1) * Math.cos(dd) + Math.cos(lat1)
				* Math.sin(dd) * Math.cos(brng));
		double lon2 = lon1
				+ Math.atan2(Math.sin(brng) * Math.sin(dd) * Math.cos(lat1),
						Math.cos(dd) - Math.sin(lat1) * Math.sin(lat2));

		lat2 = Math.toDegrees(lat2);
		lon2 = Math.toDegrees(lon2);

		Point2D p = new Point.Double();
		p.setLocation(lon2, lat2);

		return p;
	}

	public static Minute getMinute(String time) {
		int m = new Integer(time.substring(3, 5)).intValue();
		int h = new Integer(time.substring(0, 2)).intValue();

		Minute min = new Minute(m, h, 1, 1, 1970);

		return min;
	}

	/**
	 * converts time string in hh:mm:ss format to an integer time in seconds
	 * 
	 * @param timeStr
	 *            String
	 * @return int
	 */
	public static int getTime(String timeStr) {
		String timeArr[] = timeStr.trim().split(":");
		int time = 0;

		try {
			time = new Integer(timeArr[0]).intValue() * (60 * 60);
			time += new Integer(timeArr[1]).intValue() * 60;

			if (timeArr.length == 3) {
				time += new Integer(timeArr[2]).intValue();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}

		return time;
	}

	/**
	 * Convert time in seconds to hh:mm:ss format string
	 * 
	 * @param time
	 *            int
	 * @return String
	 */
	public static String getTimeStr(int time) {
		String str = "";
		int tmp = 0;

		tmp = time / (60 * 60);
		if (tmp < 10) {
			str += "0";
		}
		str += String.valueOf(tmp) + ":";

		time = time - tmp * (60 * 60);
		tmp = time / 60;
		if (tmp < 10) {
			str += "0";
		}
		str += String.valueOf(tmp) + ":";

		time = time - tmp * 60;
		tmp = time;
		if (tmp < 10) {
			str += "0";
		}
		str += String.valueOf(tmp);

		return str;
	}

	public static String getTimeStrShort(int time) {
		return getTimeStr(time).substring(0, 5);
	}

	private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	public double distance(double lat1, double lon1, double lat2, double lon2,
			char unit) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
				* Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		if (unit == 'K') {
			dist = dist * 1.609344;
		} else if (unit == 'N') {
			dist = dist * 0.8684;
		}

		return (dist);
	}

	/**
	 * @param lat
	 * @param lon
	 * @return This script calculates great-circle distances between the two
	 *         points – that is, the shortest distance over the earth’s surface
	 *         – using the ‘Haversine’ formula.
	 * 
	 *         It assumes a spherical earth, ignoring ellipsoidal effects –
	 *         which is accurate enough* for most purposes… – giving an
	 *         ‘as-the-crow-flies’ distance between the two points (ignoring any
	 *         hills!).
	 * 
	 */
	public double GreatCircleDistance(double lat1, double lon1, double lat2,
			double lon2) {
		double p1 = Math.cos(lat1) * Math.cos(lon1) * Math.cos(lat2)
				* Math.cos(lon2);
		double p2 = Math.cos(lat1) * Math.sin(lon1) * Math.cos(lat2)
				* Math.sin(lon2);
		double p3 = Math.sin(lat1) * Math.sin(lat2);

		return (Math.acos(p1 + p2 + p3) * EARTH_RADIUS);
	}

	private double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}

	public void saveJPG(Component c, File file) throws IOException {
		Dimension dim = c.getPreferredSize();
		BufferedImage im = new BufferedImage(dim.width, dim.height,
				BufferedImage.TYPE_INT_RGB);
		c.paint(im.getGraphics());
		ImageIO.write(im, "jpg", file);
	}
}
