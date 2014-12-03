package parsers.online;

import gui.ToolBar;

import java.io.IOException;

import parsers.AParser;
import storage.safetynets.StcaAlert;
import utils.Options;
import utils.Util;
import co.ExerciseCO;

public abstract class AOnlineParser extends AParser implements Runnable {

	public AOnlineParser(String fileName) {
		logFileName = fileName;
	}

	/**
	 * parse
	 * 
	 * @param lineReader
	 *            LineReader
	 */
	public void coreParsing() throws IOException {
		String line = null;

		try {
			while (ToolBar.REPLAY_STATUS != ToolBar.run_status.STOPPED) {
				// if it is not paused
				line = lineReader.readLine();
				if (line == null) {
					if (ToolBar.REPLAY_STATUS != ToolBar.run_status.SHADOW) {
						// in sync mode wait for new line from the system
						break;
					}
				}

				try {
					parsing(line);
				} catch (Exception ex) {
					System.out.println("Problem reading at line = "
							+ lineReader.getLineNumber());
					System.out.println(ex.getMessage());
				}

				switch (ToolBar.REPLAY_STATUS) {
				case FFW:
					if (Util.getTime(ExerciseCO.CURRENT_TIME_STR) >= Util
							.getTime(ToolBar.TARGET_TIME)) {
						// we reach the target time
						ToolBar.REPLAY_STATUS = ToolBar.run_status.PAUSED;

						// TODO
						// Update ToolBar button status
					}

					break;

				case ONE_STEP_ONLY:
					if (Util.getTime(ExerciseCO.CURRENT_TIME_STR) >= Util
							.getTime(ToolBar.TARGET_TIME)) {
						// we reach the target time
						ToolBar.REPLAY_STATUS = ToolBar.run_status.PAUSED;

						// TODO
						// Update ToolBar button status
					}
					break;

				case FFW_STCA:
					if (StcaAlert.newStca) {
						// we have an STCA
						ToolBar.REPLAY_STATUS = ToolBar.run_status.PAUSED;

						StcaAlert.newStca = false;

						// TODO
						// Update ToolBar button status
					}

					break;

				case SYNCRONIZED:
					if (ExerciseCO.CURRENT_TIME != 0
							&& Options.REPLAY_SPEED != 5) {
						Thread.sleep(10 / Options.REPLAY_SPEED);
					}

					break;

				case PAUSED:
					// pause the parsing action
					synchronized (this) {
						wait();
					}
					break;

				case SHADOW:
					// No specific action here
					break;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	abstract void parsing(String line) throws Exception;

	public synchronized void resumeThread() {
		// resume the parsing action
		notify();
	}

	public void run() {
		if (logFileName != null) {
			// use the eons.data.gz selected by the user
			parse(null, logFileName);
		}
	}

}
