package gui;

import gui.menu.MainMenu;
import gui.safety.MTCDWindow;
import gui.safety.STCAWindow;
import gui.safety.TDMWindow;
import gui.viewport.horizontal.RadarWindow;
import gui.viewport.vertical.VerticalWindow;

import java.awt.Toolkit;

import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import parsers.online.AOnlineParser;
import parsers.online.EonsParser;
import parsers.online.HMILogParser;
import selectables.ATrack;
import storage.ListManager;
import tools.AirspaceUsage;
import tools.HMILogWindow;
import tools.LoadMonitor;
import utils.Config;
import utils.FileFilterSuite;
import utils.Options;

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
public class MainWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static MainWindow instance = null;
	private static boolean newMap = false;

	public static MainWindow getInstance() {
		if (instance == null) {
			instance = new MainWindow();
		}

		return instance;
	}

	// internal panels
	public RadarWindow radarWindow = new RadarWindow();

	public VerticalWindow verticalWindow = new VerticalWindow();

	// TODO other alerts
	public STCAWindow stcaAlert = new STCAWindow();

	public TDMWindow tdmAlert = new TDMWindow();

	public MTCDWindow mtcdAlert = new MTCDWindow();

	public MainMenu mnuMain = new MainMenu();

	private JDesktopPane desktop = new JDesktopPane();

	public LoadMonitor pnlLoad = new LoadMonitor();

	public HMILogWindow pnlHMILog = null;

	public ControlWindow pnlControl = new ControlWindow();

	public CallsignList pnlCallsign = new CallsignList();

	// Threads
	private Thread hmiLogThread = null;

	private AOnlineParser hmiParser = null;

	private Thread eonsParserThread = null;

	private AOnlineParser eonsParser = null;

	private MainWindow() {
		try {
			jbInit();
			setVisible(true);
			pack();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void addFrame(JInternalFrame frame, Integer layer) {
		desktop.add(frame, layer);
	}

	public void exitForm() {
		int res = JOptionPane.showConfirmDialog(this,
				"Do you really want to quit the application?", "Exit",
				JOptionPane.OK_CANCEL_OPTION);

		if (res == JOptionPane.YES_OPTION) {
			// stop the threads
			ToolBar.REPLAY_STATUS = ToolBar.run_status.STOPPED;
			this.dispose();

			System.exit(0);
		}
	}

	public void highlightFligt(String callsign) {
		ATrack track = null;

		if (callsign != null) {
			track = radarWindow.pnlView.trackList.findByCallsign(callsign);
			radarWindow.pnlView.highlightTrack(track);

			track = verticalWindow.pnlView.trackList.findByCallsign(callsign);
			verticalWindow.pnlView.highlightTrack(track);
		} else {
			radarWindow.pnlView.highlightTrack(track);
			verticalWindow.pnlView.highlightTrack(track);
		}
	}

	private void jbInit() throws Exception {
		this.setContentPane(desktop);

		// maximize the window
		desktop.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
		// desktop.setOpaque(false);

		// make dragging faster by setting drag mode to Outline
		desktop.putClientProperty("JDesktopPane.dragMode", "outline");

		this.setJMenuBar(mnuMain);
		this.setTitle("Traffic Analyser " + Config.VERSION);

		// add main components
		addFrame(radarWindow, JDesktopPane.DEFAULT_LAYER);
		addFrame(verticalWindow, JDesktopPane.DEFAULT_LAYER);

		addFrame(stcaAlert, JDesktopPane.POPUP_LAYER);
		addFrame(tdmAlert, JDesktopPane.POPUP_LAYER);
		addFrame(mtcdAlert, JDesktopPane.POPUP_LAYER);

		addFrame(pnlControl, JDesktopPane.POPUP_LAYER);
		addFrame(pnlCallsign, JDesktopPane.DEFAULT_LAYER);
		addFrame(pnlLoad, JDesktopPane.POPUP_LAYER);

		// set their initial locations
		stcaAlert.setLocation(
				Toolkit.getDefaultToolkit().getScreenSize().height, 0);
		tdmAlert.setLocation(
				Toolkit.getDefaultToolkit().getScreenSize().height, 100);
		mtcdAlert.setLocation(
				Toolkit.getDefaultToolkit().getScreenSize().height, 300);

		pnlCallsign.setLocation(
				Toolkit.getDefaultToolkit().getScreenSize().width
						- pnlCallsign.getWidth(), 0);
		radarWindow.setLocation(0, 0);
		pnlLoad.setLocation(0, 0);
		verticalWindow.setLocation(
				Toolkit.getDefaultToolkit().getScreenSize().height, 500);

		// Set to ignore the button
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				exitForm();
			}
		});
	}

	public void loadHMILog() {
		JFileChooser fileChooser = new JFileChooser();

		fileChooser.setDialogTitle("Select HMI log file to load");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setCurrentDirectory(Config.DATA_DIRECTORY);
		fileChooser.setFileFilter(FileFilterSuite.HMIFile);

		int returnVal = fileChooser.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			Config.HMI_LOG_FILE = fileChooser.getSelectedFile()
					.getAbsolutePath();
		}
	}

	public void loadMap() {
		JFileChooser fileChooser = new JFileChooser();

		fileChooser.setDialogTitle("Select map to load");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setCurrentDirectory(Config.DATA_DIRECTORY);

		int returnVal = fileChooser.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			Config.MAP_FILE = fileChooser.getSelectedFile().getAbsolutePath();
		}

		newMap = true;
		updateRadarView();
	}

	public void setEonsLog() {
		JFileChooser fileChooser = new JFileChooser();

		fileChooser.setDialogTitle("Select eons.data.gz for analysing");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setCurrentDirectory(Config.DATA_DIRECTORY);
		fileChooser.setFileFilter(FileFilterSuite.EonsFile);

		int returnVal = fileChooser.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			Config.EonsDataFile = fileChooser.getSelectedFile()
					.getAbsolutePath();
		}
	}

	public void setIPASLog() {
		JFileChooser fileChooser = new JFileChooser();

		fileChooser.setDialogTitle("Select IPAS directory");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setCurrentDirectory(Config.DATA_DIRECTORY);

		int returnVal = fileChooser.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			Config.IPASDirectory = fileChooser.getSelectedFile()
					.getAbsolutePath();

			new ListManager().initIPAS();

			// Init the radar viewports
			radarWindow.pnlView.init();
			verticalWindow.pnlView.init();
		}
	}

	public void setMode(String mode) {
		Options.MODE = mode;
		this.setTitle("ACE Traffic Analyser V" + Config.VERSION + " - " + mode);
	}

	public void showHMILog() {
		if (pnlHMILog == null) {
			pnlHMILog = HMILogWindow.getInstance();
			addFrame(pnlHMILog, JDesktopPane.POPUP_LAYER);
			pnlHMILog.setLocation(600, 0);
			pnlHMILog.setVisible(true);
		}
	}

	public void showMap() {
		// set raster map
		if (Options.SHOW_MAP) {
			if (Config.MAP_FILE != null && newMap) {
				// load map into memory as a raster
				radarWindow.setMapImage(Config.MAP_FILE);

				newMap = false;
			}
		}
	}

	/**
	 * start
	 */
	public void start() {
		// start or resume

		// start Eons data reader thread
		// parse eons.data file
		if (eonsParserThread == null) {
			eonsParser = new EonsParser(Config.EonsDataFile);
			eonsParserThread = new Thread(eonsParser);
			// eonsParserThread.setPriority(Thread.MIN_PRIORITY);
			eonsParserThread.start();
			desktop.setSelectedFrame(radarWindow);

			if (Options.MODE.equals(Options.AIRSPACE_USAGE)) {
				AirspaceUsage.viewport = radarWindow.pnlView;
			}
		}

		// start HMILog Thread
		if ((hmiLogThread == null) && (Config.HMI_LOG_FILE != null)) {
			hmiParser = new HMILogParser(Config.HMI_LOG_FILE);
			hmiLogThread = new Thread(hmiParser);
			// hmiLogThread.setPriority(Thread.MIN_PRIORITY);
			hmiLogThread.start();
		}

		// wake up all threads
		if (eonsParser != null) {
			eonsParser.resumeThread();
		}

		if (hmiParser != null) {
			hmiParser.resumeThread();
		}
	}

	public void updateRadarView() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				radarWindow.refresh();
			}
		});

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				verticalWindow.refresh();
			}
		});
	}
}
