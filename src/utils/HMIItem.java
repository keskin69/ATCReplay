package utils;

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
public class HMIItem {
	// A CRDS Log contains
	// CRDS, type, real_time, current_time, action, component, callsign, desc,
	// value:auxValue, free text;
	static final int TYPE_FIELD = 1; // unused
	static final int CURRNET_TIME_FIELD = 2;
	static final int SIMU_TIME_FIELD = 3;
	static final int ACTION_FIELD = 4;
	static final int COMP_FIELD = 5;
	static final int CALLSING_FIELD = 6;
	static final int DESC_FIELD = 7;
	static final int VALUE_FIELD = 8;

	/**
	 * Time of the action
	 */
	public int time = -1;

	public String sysTime = null;

	/**
	 * From which component the action is triggerd
	 */
	public short component = 0;

	/**
	 * The callsign of the aircraft
	 */
	public String callsign = null;

	/**
	 * The action code for this event
	 */
	public int action = 0;

	/**
	 * Special remarks and values for this event
	 */
	public String value = null;

	/**
	 * Additional description
	 */
	public String description = null;

	/*
	 * This value comes from the HMILogs where there is an additional value
	 * right after the original value. Notation will be value:auxValue in the
	 * hmi logs
	 */
	// public String auxValue = null;
	/**
	 * Parse the HF log line and fill HFItem
	 * 
	 * @param line
	 *            String
	 */
	public HMIItem(String line) {
		String tags[] = line.split(Config.HFDELIM);

		String tmp = tags[SIMU_TIME_FIELD];
		if (!tmp.equals("")) {
			time = Util.getTime(tmp);
		}

		int tmpTime = Util.getTime(tags[CURRNET_TIME_FIELD]);
		// TODO: BUG: There is a one hour difference on sysTime
		tmpTime += 60 * 60;
		sysTime = Util.getTimeStr(tmpTime);

		try {
			component = new Integer(tags[COMP_FIELD]).shortValue();
			callsign = tags[CALLSING_FIELD].trim();
			action = new Integer(tags[ACTION_FIELD]).intValue();
			description = tags[DESC_FIELD];
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}

		try {
			value = tags[VALUE_FIELD];
			/*
			 * value = value.split(":")[0];
			 * 
			 * try { auxValue = value.split(":")[1]; } catch
			 * (ArrayIndexOutOfBoundsException ex) { }
			 */
		} catch (IndexOutOfBoundsException ex) {
			value = "";
		}
	}

	public String getTime() {
		return Util.getTimeStrShort(time);
	}

	public boolean isAction(int action) {
		return (this.action == action);
	}

	public boolean isDLFlight() {
		// check first digit of action field
		// 1 on the item.value means it is a DL aircraft
		// 0 on the item.value means it is a Non-DL aircraft
		try {
			if (value.charAt(0) == '1') {
				return true;
			} else {
				return false;
			}
		} catch (StringIndexOutOfBoundsException ex) {
			return false;
		}
	}

	public boolean isMilFlight() {
		try {
			if (value.charAt(1) == '2') {
				return true;
			}
		} catch (StringIndexOutOfBoundsException ex) {

		}

		return false;
	}
}
