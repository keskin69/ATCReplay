package co;

import storage.Element;

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
public class RouteCO extends Element {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FlightCO flight = null;

	public RouteCO() {
	}

	public int getCurrentPoint() {
		int points = getIntValue("points");
		int overflown = 0;
		int i = 0;
		int currentPoint = 0;

		for (; i < points; i++) {
			overflown = getIntValue("overflown", i);
			if (overflown == 1) {
				currentPoint = i - 1;
			}
		}

		if (currentPoint < 0) {
			currentPoint = 0;
		}

		return currentPoint;
	}

	public String getNextValue(String field) {
		int idx = getCurrentPoint() + 1;
		int max = getIntValue("points");
		String value = null;

		while (true) {
			if (idx == max) {
				idx--;
				break;
			}

			value = getStrValue(field, idx);
			if (!value.equals("")) {
				return value;
			}

			idx++;
		}

		return getStrValue(field, idx);
	}

	public void updateTrackValues() {
		flight.nwp = getNextValue("beacon");
		flight.assumingSector = getNextValue("assuming_sector");
	}
}
