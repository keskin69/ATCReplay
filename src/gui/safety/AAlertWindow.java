package gui.safety;

import gui.AListWindow;
import gui.MainWindow;
import storage.safetynets.Alert;
import utils.Config;

public abstract class AAlertWindow extends AListWindow {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void listSelection(Object obj) {
		AlertText alertText = (AlertText) obj;

		// TODO
		// Highlight both tracks for MTCD/STCA
		MainWindow.getInstance().highlightFligt(
				alertText.alert.flight1.callsign);
	}

	public void update(Object obj, short type) {
		Alert alert = (Alert) obj;

		AlertText line = null;

		if (alert.content == null) {
			return;
		}

		synchronized (this) {
			for (int i = lstData.size() - 1; i >= 0; i--) {
				line = (AlertText) lstData.elementAt(i);

				if (line.id == alert.getId()) {
					if (type == Config.REMOVE) {
						// remove the corresponding alert

						lstData.remove(i);

					} else {
						// update
						lstData.setElementAt(new AlertText(alert), i);
					}

					return;
				}
			}
		}

		// so it is a new alert
		AlertText alertEntry = new AlertText(alert);

		lstData.addElement(alertEntry);
	}
}
