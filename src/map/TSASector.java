package map;

import gui.viewport.AViewport;

import java.awt.Graphics2D;
import java.awt.Polygon;

import resources.Resources;
import storage.ListManager;
import utils.Options;
import co.TSACO;

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
public class TSASector extends Sector {
	public int status = 0;

	public TSASector(String name) {
		super(name, "");
	}

	/**
	 * drawSectors
	 */

	public void draw(AViewport view, Graphics2D g2) {
		// TODO show TSA name
		// g2.setFont(Resources.SMALL_FONT);

		for (Polygon poly : polyList) {

			if (status == TSACO.NOT_ACTIVE) {
				g2.setColor(Resources.TSA_LINE_COLOR);
			} else if (status == TSACO.PRE_ACTIVE) {
				// fill area with a light TSA color
				g2.setColor(Resources.TSA_PREACTIVE_COLOR);
				fillPoly(view, poly, g2);
			} else {
				// active
				g2.setColor(Resources.TSA_ACTIVE_COLOR);
				fillPoly(view, poly, g2);
			}

			if (isSelected()) {
				g2.setColor(Resources.SELECTED_SECTOR_COLOR);
			}

			drawPoly(view, poly, g2);
		}
	}

	public boolean isSelected() {
		int i = 0;
		for (Object tsa : ListManager.tsaSectorList) {
			if (this.name.equals(((TSASector) tsa).name)) {
				return Options.SELECTED_TSA_MAPS[i];
			}
			i++;
		}

		assert (true);
		return false;
	}

}
