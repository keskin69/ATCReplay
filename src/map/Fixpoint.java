package map;

import gui.viewport.AViewport;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;

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
public class Fixpoint extends ASelectable {
	public double lat = 0D;
	public double lon = 0D;
	public String name = "";

	public Fixpoint(String name) {
		this.name = name;
		setDrawColor(Resources.FIX_COLOR);
	}

	public void draw(AViewport view, Graphics2D g2) {
		double x = view.lon2X(lon);
		double y = view.lat2Y(lat);

		g2.setColor(drawColor);
		g2.setFont(Resources.SMALL_FONT);

		if (Options.DRAW_FIX_POINTS) {
			g2.draw(new Line2D.Double(x - Options.BEACON_SIZE, y, x
					+ Options.BEACON_SIZE, y));
			g2.draw(new Line2D.Double(x, y - Options.BEACON_SIZE, x, y
					+ Options.BEACON_SIZE));
		}

		if (Options.SHOW_FIX_NAMES) {
			g2.setFont(Resources.FIX_FONT);
			g2.drawString(name, (float) x + Options.BEACON_SIZE, (float) y
					+ Options.BEACON_SIZE);
		}
	}

	public boolean isMouseInside(AViewport view, MouseEvent e) {
		return false;
	}
}
