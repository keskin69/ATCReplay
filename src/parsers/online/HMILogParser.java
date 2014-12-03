package parsers.online;

import tools.HMILogWindow;
import utils.HMIItem;
import co.ExerciseCO;

public class HMILogParser extends AOnlineParser {
	int lastLine = 0;

	public HMILogParser(String fileName) {
		super(fileName);
	}

	protected void parsing(String line) throws Exception {
		if (line.startsWith("\"CRDS,")) {
			HMIItem item = null;

			item = new HMIItem(line);

			if (item.time - ExerciseCO.START_TIME > ExerciseCO.CURRENT_TIME) {
				lineReader.setLineNumber(lastLine);
			} else {
				HMILogWindow.getInstance().append(line);
				lastLine = lineReader.getLineNumber();
			}
		}
	}
}
