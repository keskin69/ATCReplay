package gui.viewport.horizontal;

import gui.MainWindow;
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
import tools.AirspaceUsage;
import utils.Options;
import co.ExerciseCO;

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
public class RadarView extends AViewport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MouseAction mouseAction = null;

	public RadarView() {
		super();

		mouseAction = new MouseAction(this);
		setMouseListener(mouseAction);
		this.setBackground(Resources.VIEWPORT_COLOR);
		this.setDoubleBuffered(true);
	}

	public void changeZoom(int factor) {
		super.changeZoom(factor);
		MainWindow.getInstance().verticalWindow.pnlView.changeZoom(factor);
	}

	public void dragCenter(int xVal, int yVal) {
		super.dragCenter(xVal, yVal);
		MainWindow.getInstance().verticalWindow.pnlView.dragCenter(xVal, yVal);
	}

	public void init() {
		setSectorBoundary(ListManager.sectorList);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		// Layer0
		// draw background map if there is any
		if (mapImage != null && Options.SHOW_MAP) {
			g2.drawImage(mapImage, null, 0, 0);
		}

		if (ListManager.init) {

			// Layer1
			if (Options.SHOW_SECTORS) {
				ListManager.sectorList.drawList(this, g2);
			}

			// TSA Areas
			ListManager.tsaSectorList.drawList(this, g2);

			// Layer2
			// Beacons
			if (Options.DRAW_FIX_POINTS || Options.SHOW_FIX_NAMES) {
				ListManager.fixList.drawList(this, g2);
			}

			if (Options.SHOW_AIRPORTS) {
				// Airports
				ListManager.airportList.drawList(this, g2);
			}

			if (Options.DRAW_AIRWAYS) {
				// Airways
				ListManager.airwaysList.drawList(this, g2);
			}

			// Layer3
			// Information
			if (Options.SHOW_TRAFFIC) {
				g2.setColor(Resources.INFO_COLOR);
				g2.setFont(Resources.INFO_FONT);

				// Traffic&Run information
				g2.drawString(ExerciseCO.simu, 5, this.getHeight() - 25);
				g2.drawString(ExerciseCO.traffic, 5, this.getHeight() - 5);
			}

			// Time
			if (Options.SHOW_TIMER) {
				g2.setColor(Resources.INFO_COLOR);
				g2.setFont(Resources.INFO_FONT);
				g2.drawString(ExerciseCO.CURRENT_TIME_STR, 5, 25);
			}

			// Layer4
			// do not draw tracks during heavy operatons
			if (Options.MODE.equals(Options.AIRSPACE_USAGE)) {
				// show air space usage
				AirspaceUsage.getInstance().draw(g2);
			} else if (Options.MODE.equals(Options.AIRWAY_USAGE)) {
				// show runway usage
				ListManager.routeList.drawList(this, g2);
			} else if (Options.MODE.equals(Options.NORMAL_MODE)) {
				if (Options.DRAW_TRACK) {
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
			}

			// Layer5
			if (Options.SHOW_COORDINATES) {
				// Lat/Lon information at cursor point
				g2.setColor(Resources.COORD_LABEL_COLOR);
				String coord = String.valueOf(x2Lon(mouseAction.currentX))
						+ " " + String.valueOf(y2Lat(mouseAction.currentY));
				g2.drawString(coord, (float) mouseAction.currentX,
						(float) mouseAction.currentY);

				this.repaint();
			}

			// Layer6
			// R&B
			// TODO

			// Layer 7
			// draw the frame
			/*
			 * if (showFrame) { g2.setColor(Resources.SELECTED_SECTOR_COLOR); if
			 * (frameX2 - frameX1 > 0 && frameY2 - frameY1 > 0) {
			 * g2.drawRect(frameX1, frameY1, frameX2 - frameX1, frameY2 -
			 * frameY1); } }
			 */

			// draw the R&B
		}
	}

	public void setCenter(int X, int Y) {
		super.setCenter(X, Y);
		MainWindow.getInstance().verticalWindow.pnlView.setCenter(X, Y);
	}

	public void setMouseListener(MouseListener mouseListener) {
		addMouseListener(mouseListener);
		addMouseMotionListener((MouseMotionListener) mouseListener);
		addMouseWheelListener(this);
	}
}
