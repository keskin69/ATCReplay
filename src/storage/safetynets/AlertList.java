package storage.safetynets;

import gui.MainWindow;

import java.io.IOException;
import java.io.LineNumberReader;

import storage.ElementList;

public class AlertList extends ElementList {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AlertList(Class<? extends Alert> elemClass) {
		this.elemClass = elemClass;
	}

	public Alert update(LineNumberReader lineReader, int id, short stat)
			throws IOException {
		Alert alert = (Alert) super.update(lineReader, id, stat);

		alert.update(stat);

		if (alert instanceof StcaAlert) {
			MainWindow.getInstance().stcaAlert.update(alert, stat);
		} else if (alert instanceof TdmAlert) {
			MainWindow.getInstance().tdmAlert.update(alert, stat);
		} else if (alert instanceof MtcdAlert) {
			MainWindow.getInstance().mtcdAlert.update(alert, stat);
		}

		return alert;
	}
}
