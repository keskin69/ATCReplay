package parsers.online;

import gui.MainWindow;
import storage.Element;
import storage.ListManager;
import co.ExerciseCO;

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
public class EonsParser extends AOnlineParser {

	public EonsParser(String fileName) {
		super(fileName);
	}

	protected void parsing(String line) throws Exception {
		Element currElement = null;

		if (!line.startsWith("viewport_")) {

			// update the data sets
			currElement = ListManager.update(lineReader, line);

			if (currElement != null) {
				// change timer
				if (currElement.getTime() != 0) {
					ExerciseCO.updateTime(currElement.getTime());
				}

				MainWindow.getInstance().updateRadarView();
			}

		}
	}
}
