package gui.viewport.vertical;

import gui.viewport.AViewport;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import resources.Resources;
import selectables.ATrack;
import storage.ListManager;

public class VerticalView extends AViewport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private VerticalMouseAction mouseAction = null;
	private static final double MIN_ALT = 0D;
	private static final double MAX_ALT = 600D;

	public VerticalView() {
		super();

		mouseAction = new VerticalMouseAction(this);
		setMouseListener(mouseAction);
		this.setBackground(Resources.VIEWPORT_COLOR);
		this.setDoubleBuffered(true);
	}

	public void init() {
		setSectorBoundary(ListManager.sectorList);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		// Draw tracks
		ATrack track = null;
		Iterator<ATrack> it = trackList.values().iterator();
		try {
			while (it.hasNext()) {
				track = it.next();
				track.draw(this, g2);
			}
		} catch (ConcurrentModificationException ex) {
		}
	}

	public void setMouseListener(MouseListener mouseListener) {
		addMouseListener(mouseListener);
		addMouseMotionListener((MouseMotionListener) mouseListener);
	}
	
	public double alt2Z(double alt) {
		double z = viewHeight - viewHeight * ((alt - MIN_ALT) / (MAX_ALT-MIN_ALT));

		return z;
	}
}
