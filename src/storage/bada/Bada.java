package storage.bada;

import java.util.ArrayList;

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
 * @author not attributable
 * @version 1.0
 */
public class Bada {
	public ArrayList<BadaValue> desend = new ArrayList<BadaValue>();
	public ArrayList<BadaValue> cruise = new ArrayList<BadaValue>();
	public ArrayList<BadaValue> climb = new ArrayList<BadaValue>();
	public String acType;

	public static final short CLIMB = 2;
	public static final short DESCEND = 1;
	public static final short CRUISE = 0;

	public static final short LOW = 5;
	public static final short MEDIUM = 3;
	public static final short HIGH = 1;

	protected Bada(String acType) {
		this.acType = acType;
	}

	protected void fillData(int level, double flow, double fnom, double fhi,
			short attitude, int tas) {
		BadaValue badaData = new BadaValue(level, flow, fnom, fhi, tas);

		switch (attitude) {
		case DESCEND:
			desend.add(badaData);
			break;
		case CRUISE:
			cruise.add(badaData);
			break;
		case CLIMB:
			climb.add(badaData);
			break;
		}
	}

	protected BadaValue getBadaValue(ArrayList<BadaValue> vector, int level) {
		BadaValue value = null;

		for (int i = 0; i < vector.size(); i++) {
			value = (BadaValue) vector.get(i);
			if (value.level >= level) {
				return value;
			}
		}

		return null;
	}

	protected double getFuel(int level, short attitude, short mass) {
		BadaValue badaData = null;
		double fuel = 0D;

		switch (attitude) {
		case DESCEND:
			badaData = getBadaValue(desend, level);
			fuel = badaData.fnom;
			break;

		case CRUISE:
			badaData = getBadaValue(cruise, level);

			switch (mass) {
			case LOW:
				fuel = badaData.flow;
				break;

			case MEDIUM:
				fuel = badaData.fnom;
				break;

			case HIGH:
				fuel = badaData.fhi;
				break;

			default:
				fuel = badaData.fnom;
			}
			break;

		case CLIMB:
			badaData = getBadaValue(climb, level);
			fuel = badaData.fnom;
			break;
		}

		return fuel;
	}
}
