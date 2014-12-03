package utils;

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
public class Options {
	public final static String NORMAL_MODE = "Replay Mode";
	public final static String ONLINE_MODE = "Online Mode";
	public final static String AIRSPACE_USAGE = "Airspace Usage Mode";
	public final static String AIRWAY_USAGE = "Airway Usage Mode";
	public final static String LOAD_GRAPH = "System Load Graph Mode";

	public static String MODE = NORMAL_MODE;
	public static int REPLAY_SPEED = 1;

	// Track related
	public static boolean DRAW_TRACK = true;
	public static boolean DRAW_HISTORY = false;
	public static boolean DRAW_HISTORY_ROUTE = false;
	public static boolean DRAW_LABEL = true;
	public static boolean DRAW_HEADING = true;
	public static boolean SHOW_LINE_2 = false;

	// Map Related
	public static boolean SHOW_AIRPORTS = false;
	public static boolean DRAW_FIX_POINTS = false;
	public static boolean SHOW_FIX_NAMES = false;
	public static boolean DRAW_AIRWAYS = false;
	public static boolean SHOW_SECTORS = true;
	public static boolean SHOW_COORDINATES = false;
	public static boolean SHOW_MAP = false;

	// Info
	public static boolean SHOW_TIMER = true;
	public static boolean SHOW_TRAFFIC = true;

	// Alerts on track
	public static boolean SHOW_MTCD = false;
	public static boolean SHOW_APW = true;
	public static boolean SHOW_TDM = false;
	public static boolean SHOW_MONA = true;

	// Flight filtering
	public static boolean SHOW_CIVIL = true;
	public static boolean SHOW_MIL = true;
	public static boolean SHOW_DL = true;
	public static boolean SHOW_NON_DL = true;

	public static boolean SELECTED_SECTORS[] = null;
	public static String SECTOR_LIST[] = null;
	public static String TSA_LIST[] = null;

	public static boolean HIGHLIGHT_SECTOR_MAP[] = null;
	public static boolean SELECTED_TSA_MAPS[] = null;

	public static int HISTORY_SIZE = 5;
	public static int SPEED_LENGTH = 3;
	public static final int MARK_SIZE = 10;
	public static final int BEACON_SIZE = 4;
	public static final int TRACK_SIZE = 6;
	public static final int LEADER_LENGTH = 30;
	public static final int AIRWAYS_WIDTH = 3;
}
