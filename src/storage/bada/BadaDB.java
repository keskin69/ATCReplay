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
public class BadaDB {
	public static ArrayList<Bada> vectorBADA = null;

	public static void addType(String type) {
		Bada bada = new Bada(type);
		vectorBADA.add(bada);
	}

	public static void fill(String acType, int level, int tas, double flow,
			double fnom, double fhi, short attitude) {
		Bada bada = findType(acType);

		if (bada != null) {
			bada.fillData(level, flow, fnom, fhi, attitude, tas);
		} else {
			addType(acType);
		}
	}

	private static Bada findType(String acType) {
		Bada bada = null;

		for (int i = 0; i < vectorBADA.size(); i++) {
			bada = (Bada) vectorBADA.get(i);

			if (bada.acType.equals(acType)) {
				return bada;
			}
		}

		return null;
	}

	public static double getFuel(String acType, int level, short attitude,
			short mass) {
		double fuel = 0D;
		Bada bada = findType(acType);

		if (bada != null) {
			fuel = bada.getFuel(level, attitude, mass);
		}

		return fuel;
	}
}
