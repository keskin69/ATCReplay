package selectables.fields;

import gui.viewport.AViewport;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import selectables.ASelectable;
import selectables.ATrackLabel;

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
abstract public class ACallsignField extends ASelectable {
	private static Rectangle2D getFieldBounds(Graphics g, String content) {
		Graphics2D g2 = (Graphics2D) g;
		FontRenderContext frc = g2.getFontRenderContext();
		Font font = g2.getFont();

		return font.getStringBounds(content, frc);
	}

	public String content = null;
	public double offsetX = 0;
	public double offsetY = 0;

	public ATrackLabel label = null;

	public ACallsignField(ATrackLabel label) {
		this.label = label;
	}

	public abstract void displayPopup();

	public void draw(AViewport view, Graphics2D g2) {
	}

	protected abstract void init();

	public boolean isMouseInside(AViewport view, MouseEvent e) {
		init();

		double x = e.getPoint().getX();
		double y = e.getPoint().getY();
		Graphics g = e.getComponent().getGraphics();
		Rectangle2D bound = getFieldBounds(g, content);
		float width = (float) bound.getWidth();
		float height = (float) bound.getHeight();

		if (x <= offsetX + width && y <= offsetY + height) {
			return true;
		}

		return false;
	}

	public abstract String toString();

}
