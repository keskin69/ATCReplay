package map;

import gui.viewport.AViewport;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import resources.Resources;
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
public class Airport extends Fixpoint {
	public Airport(String name) {
		super(name);
		setDrawColor(Resources.AIRPORT_COLOR);
	}

	public void draw(AViewport view, Graphics2D g2) {
		double x = view.lon2X(lon);
		double y = view.lat2Y(lat);

		g2.setColor(drawColor);

		g2.draw(new Rectangle2D.Double(x - Options.BEACON_SIZE, y
				- Options.BEACON_SIZE, 2 * Options.BEACON_SIZE,
				2 * Options.BEACON_SIZE));

		if (Options.SHOW_FIX_NAMES) {
			g2.setFont(Resources.FIX_FONT);
			g2.drawString(name, (float) x, (float) y);
		}
	}
}
