package co;

import storage.Element;
import utils.Util;

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
public class ExerciseCO extends Element {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static int START_TIME = 0;
	public static int CURRENT_TIME = 0;
	public static String CURRENT_TIME_STR = "";
	public static String simu = "";
	public static String traffic = "";

	public static void updateTime(int currentTime) {
		if (currentTime == 0 && CURRENT_TIME_STR != null) {
			// We don't know the start time yet
			return;
		}

		CURRENT_TIME_STR = Util.getTimeStr(currentTime + START_TIME);
		CURRENT_TIME = currentTime;
	}

	public String getSimuInfo() {
		String info = getStrValue("name") + " " + getStrValue("traffic");

		return info;
	}
}
