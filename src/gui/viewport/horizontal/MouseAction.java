package gui.viewport.horizontal;

import gui.MainWindow;
import gui.viewport.AViewport;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.event.MouseInputAdapter;

import selectables.ASelectable;
import selectables.ATrack;
import selectables.ATrackLabel;
import selectables.ISelectable;
import selectables.Route;
import selectables.fields.AField;
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
public class MouseAction extends MouseInputAdapter implements
		MouseMotionListener {

	protected enum action {
		IDLE, LABEL, CENTERING, SHOW_COORDINATE, FRAME
	}

	protected ATrackLabel selectedLabel = null;
	protected action currentAction = action.IDLE;
	protected int dragStartX, dragStartY;
	public int currentX;
	public int currentY;

	protected AViewport viewport;

	public MouseAction(AViewport viewport) {
		this.viewport = viewport;
	}

	/**
	 * getSelection
	 * 
	 * @param e
	 *            MouseEvent
	 * @return Track
	 */
	protected ISelectable getSelection(MouseEvent e) {
		ASelectable selectable = null;

		for (ATrack track : viewport.trackList.values()) {
			if (track.isMouseInside(null, e)) {
				// symbol selected
				return track;
			} else if (track.trackLabel.isMouseInside(null, e)) {
				selectable = track.trackLabel.getSelectedField(e);

				if (selectable != null) {
					// field selected
					return selectable;
				} else {
					// label selected
					return track.trackLabel;
				}
			}
		}

		// no active component selected
		return null;
	}

	public void mouseClicked(MouseEvent e) {
		Object selected = getSelection(e);

		if (selected instanceof ATrack) {
			// symbol clicked
			ATrack track = (ATrack) selected;

			if (e.getButton() == MouseEvent.BUTTON3) {
				track.drawRoute = !track.drawRoute;
				MainWindow.getInstance().updateRadarView();
			}
		} else if (selected instanceof AField) {
			// a track field selected
			AField field = (AField) selected;
			field.action(e.getButton());
		} else if (selected instanceof Route) {
			// route clicked
		} else if (selected == null) {
			// no object selected
			if (e.getButton() == MouseEvent.BUTTON3) {
				// set center of the viewport
				viewport.setCenter(e.getX(), e.getY());
			}
		}
	}

	public void mouseDragged(MouseEvent e) {
		switch (currentAction) {
		case LABEL:
			// move label
			selectedLabel.moveLabel(e);
			viewport.repaint();

			break;

		case CENTERING:
			// change center of the viewport
			int deltaX = e.getX() - dragStartX;
			int deltaY = e.getY() - dragStartY;

			viewport.dragCenter(deltaX, deltaY);

			dragStartX = e.getX();
			dragStartY = e.getY();

			break;

		case SHOW_COORDINATE:
			// display coordinates of the current position
			currentX = e.getX();
			currentY = e.getY();
			break;

		case FRAME:
			// AViewport.setFrame(dragStartX, dragStartY, e.getX(), e.getY());
			break;
		}

	}

	public void mouseMoved(MouseEvent e) {
		// Check for tracks for highlighting
		for (ATrack track : viewport.trackList.values()) {
			if (track.trackLabel.isMouseInside(null, e)) {
				MainWindow.getInstance().highlightFligt(track.flight.callsign);

				return;
			}
		}

		// no track has the focus
		MainWindow.getInstance().highlightFligt(null);
	}

	public void mousePressed(MouseEvent e) {
		Object selected = getSelection(e);

		if (selected == null) {
			// viewport selected
			if (e.getButton() == MouseEvent.BUTTON2) {
				// draw a frame
				// currentAction = action.FRAME;
				// dragStartX = e.getX();
				// dragStartY = e.getY();
			} else if (e.getButton() == MouseEvent.BUTTON1) {
				// drag the viewport center
				currentAction = action.CENTERING;
				dragStartX = e.getX();
				dragStartY = e.getY();
			} else if (e.getButton() == MouseEvent.BUTTON3) {
				// display the lat/long coordinates of the viewport
				currentAction = action.SHOW_COORDINATE;
				Options.SHOW_COORDINATES = true;
			}
		} else if (selected instanceof ATrackLabel) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				// track label selected
				selectedLabel = (ATrackLabel) selected;
				currentAction = action.LABEL;
			}
		} else if (selected instanceof AField) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				// a field in the track is selected
				AField field = (AField) selected;
				selectedLabel = field.track.trackLabel;
				currentAction = action.LABEL;
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		/*
		 * if (currentAction == action.FRAME) { // remove the frame and show
		 * vertical view AViewport.removeFrame();
		 * 
		 * // show the vertical view double minLon =
		 * AViewport.x2Lon(gui.viewport.frameX1); double minLat =
		 * AViewport.x2Lon(gui.viewport.frameY1); double maxLon =
		 * AViewport.x2Lon(gui.viewport.frameX2); double maxLat =
		 * AViewport.x2Lon(gui.viewport.frameY2);
		 * 
		 * MainWindow.getInstance().showVerticalView(minLon, minLat, maxLon,
		 * maxLat); }
		 */

		selectedLabel = null;
		Options.SHOW_COORDINATES = false;
		currentAction = action.IDLE;
	}
}
