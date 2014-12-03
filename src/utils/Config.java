package utils;

import gui.MainWindow;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

import storage.ListManager;

/**
 * <p>
 * Title: Analog Project
 * </p>
 * 
 * <p>
 * Description: Analyses Tool Development
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Eurocontrol CRDS Budapest
 * </p>
 * 
 * @author Mustafa Keskin
 * @version 1.0
 */
public final class Config implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static Properties properties = new Properties();

	public static final String VERSION = "V0.95";

	public static final String ImageJarFile = "jlfgr-1_0.jar";

	// instance of this constants
	private static Config instance = null;

	public static final String FILE_SEPERATOR = System
			.getProperty("file.separator");

	// log file patterns
	public static final String EONS_FILE_PATTERN = ".*data.*gz";
	public static final String HF_LOG_FILE_PATTERN = "eons.*";
	public static final String SECTOR_DEFINITION_FILE = "SECTORS_FILE.DATA";
	public static final String NAVIGATION_POINTS_FILE = "NAVIGATION_POINTS_FILE.DATA";
	public static final String AIRWAYS_FILE = "AIRWAYS_FILE.DATA";
	public static final String AIRSPACE_FILE = "AIRSPACE_RESERVATION_FILE.DATA";
	public static final String AIRPORT_FILE = "AIRPORTS_FILE.DATA";
	public static final String WAYPOINTS_FILE = "waypoints.txt";

	public static final String DELIM = ",";
	public static final String IGNORE_SECTOR_WITH = "PIL";

	// Location of all the simulation log files
	public static String LOG_FILE_BASE = null;
	public static File DATA_DIRECTORY = null;

	// DB Related parameters
	/*
	 * public static String DB_DRIVER = null; public static String DB_SERVER =
	 * null; public static int DB_SERVER_PORT = 0; public static String DB_NAME
	 * = null; public static String DB_USER = null; public static String
	 * DB_PASSWORD = null;
	 */

	public static String EonsDataFile = null;
	public static String IPASDirectory = null;
	public static String MAP_FILE = null;
	public static String HMI_LOG_FILE = null;
	public static String BADA_PATH = null;
	public static int REPORT_STEP = 2;
	public static String SKIP_SECTORS = null;

	// list of layers
	public static final short MAP_LAYER = 2;
	public static final short TRACK_LAYER = 1;

	// HMI Specific constants
	// WEAK:
	public static final String KEY_FOR_CONTROLLER_TYPE = ".*Position_Init: Sector is main position.*";
	public static final String KEY_FOR_POSITION_NAME = ".*Position_Init: MySector.*";
	public static final String KEY_FOR_POSITION_TYPE = ".*settings_org_com.mod: MySectorType.*";
	public static final String EONS_TIME_LABEL = "INFO:       Time = ";

	// new log prints
	public static final String NEW_SECTOR_IS_MAIN = "Sector is main position:";
	public static final String NEW_SECTOR_NAME = "MySector:";

	// co changes
	public static final short NEW = 0;
	public static final short UPDATE = 1;
	public static final short REMOVE = 2;

	// eons.data.gz specific constants
	public static final String EONSFLIGHTPLAN = "EonsFlightPlan";
	public static final String EONSROUTE = "EonsRoute";
	public static final String EONSEXERCISE = "EonsExercise";
	public static final String EONSSTCA = "EonsStca";
	public static final String EONSAPW = "EonsApw";
	public static final String EONSMTCD = "EonsMtca";
	public static final String EONSTDM = "EonsTrackDeviation";
	public static final String EONSMONA = "EonsMona";
	public static final String TIME_STR = "update_relative_time";
	public static final String EONSRESERVED = "EonsReservedArea";

	public static final short EONS_MILITARY_FLIGHT = 2;

	// DELIMS parser specific constants
	public static final String HFDELIM = ",";

	public static final int EC = 0;
	public static final int PC = 1;
	public static final int ASSIST = 2;
	public static final int SUPERVISOR = 3;
	public static final int FEED = 4;
	public static final int FLOW = 5;
	public static final int OTHER = 6;
	public static final int FEED_HYBRID = 7;
	public static final int UNKNOWN = 8;
	public static final int NONE = 9;

	public static Config getInstance(String confFile) {
		if (instance == null) {
			instance = new Config(confFile);
		}

		return instance;
	}

	@SuppressWarnings("unused")
	private static int getInt(String key) {
		int value = 0;

		try {
			value = new Integer(properties.getProperty(key)).intValue();
		} catch (Exception ex) {
			System.out.println(key
					+ " parameter does not exists in config file");
		}

		return value;
	}

	/**
	 * getProperties
	 * 
	 * @param propertyFile
	 *            String
	 */
	private static void getProperties(String propertyFile) {
		try {
			System.out.println("Reading properties from " + propertyFile);
			properties.load(new BufferedInputStream(new FileInputStream(
					propertyFile)));
		} catch (IOException ex) {
			// the resources file does not exist
			System.out.println("Properties file is missing at " + propertyFile);
			System.exit(1);
		}
	}

	public static String getRoleStr(int role) {
		String roleStr = "NONE";

		switch (role) {
		case EC:
			roleStr = "EC";
			break;
		case PC:
			roleStr = "PC";
			break;
		case ASSIST:
			roleStr = "ASS";
			break;
		case FEED:
			roleStr = "FEED";
			break;
		case SUPERVISOR:
			roleStr = "SUP";
			break;
		case FEED_HYBRID:
			roleStr = "FEED";
			break;
		case FLOW:
			roleStr = "FLOW";
			break;
		case OTHER:
			roleStr = "OTHER";
			break;
		default:
			roleStr = "UNKNOWN";
			break;
		}

		return roleStr;
	}

	private static String getString(String key) {
		String value = null;

		try {
			value = properties.getProperty(key);
		} catch (Exception ex) {
			System.out.println(key
					+ " parameter does not exists in config file");
		}

		return value;

	}

	private Config(String confFile) {
		// read config from the configuration file
		System.out.println("Reading system configuration from " + confFile);
		getProperties(confFile);

		LOG_FILE_BASE = getString("Simulation_Log_Files");

		EonsDataFile = getString("eons_file");

		String tmp = getString("Data_Files");
		DATA_DIRECTORY = new File(tmp);

		SKIP_SECTORS = getString("skip_sectors");

		/*
		 * DB_DRIVER = getString("DB_Driver_Name"); DB_SERVER =
		 * getString("DB_Server_Name"); DB_NAME = getString("DB_Name");
		 * DB_SERVER_PORT = getInt("DB_Server_Port"); DB_USER =
		 * getString("DB_User_Name"); DB_PASSWORD =
		 * getString("DB_User_Password");
		 */
		MAP_FILE = getString("Map_File");

		IPASDirectory = getString("ipas_directory");
		if (IPASDirectory != null) {
			new ListManager().initIPAS();

			// Init the radar viewports
			MainWindow.getInstance().radarWindow.pnlView.init();
			MainWindow.getInstance().verticalWindow.pnlView.init();
		}
	}
}
