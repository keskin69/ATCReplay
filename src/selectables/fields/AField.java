package selectables.fields;

import gui.viewport.AViewport;

import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

import javax.swing.JPopupMenu;

import selectables.ASelectable;
import selectables.ATrack;

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
abstract public class AField extends ASelectable {
	private static Rectangle2D getFieldBounds(Graphics g, String content) {
		Graphics2D g2 = (Graphics2D) g;
		FontRenderContext frc = g2.getFontRenderContext();
		Font font = g2.getFont();

		return font.getStringBounds(content, frc);
	}

	protected double offsetX = 0;
	protected double offsetY = 0;
	protected JPopupMenu menu = null;

	protected Component comp = null;
	private double width = 0;

	private double height = 0;
	protected String content = null;

	public ATrack track = null;

	public abstract void action(int mouseButton);

	protected void displayPopup() {
		menu.show(comp, (int) (offsetX + width), (int) (offsetY + height));
	}

	public void draw(AViewport view, Graphics2D g2) {
	}

	protected void hidePopup() {
		menu.setVisible(false);
	}

	public abstract void init(ATrack track);

	public boolean isMouseInside(AViewport view, MouseEvent e) {
		double x = e.getPoint().getX();
		double y = e.getPoint().getY();

		comp = e.getComponent();
		Graphics g = comp.getGraphics();
		Rectangle2D bound = getFieldBounds(g, content);
		width = bound.getWidth();
		height = bound.getHeight();

		if (x <= offsetX + width && y <= offsetY + height && x >= offsetX
				&& y >= offsetY) {
			return true;
		}

		return false;
	}

}
