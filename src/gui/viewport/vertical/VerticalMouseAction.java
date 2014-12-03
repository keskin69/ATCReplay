package gui.viewport.vertical;

import gui.viewport.AViewport;
import gui.viewport.horizontal.MouseAction;

import java.awt.event.MouseEvent;

import selectables.ATrackLabel;
import selectables.fields.AField;

public class VerticalMouseAction extends MouseAction {

	public VerticalMouseAction(AViewport viewport) {
		super(viewport);
	}

	public void mouseDragged(MouseEvent e) {
		switch (currentAction) {

		case LABEL:
			// move label
			selectedLabel.moveLabel(e);
			viewport.repaint();

			break;
		}
	}

	public void mousePressed(MouseEvent e) {
		Object selected = getSelection(e);

		if (selected == null) {
			// viewport selected
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
}
