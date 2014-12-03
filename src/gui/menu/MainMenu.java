package gui.menu;

import gui.MainWindow;
import gui.ToolBar;

import java.awt.event.ActionEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import utils.Options;
import utils.PrintUtilities;
import utils.SaveUtilities;

class ChkMode extends JMenuItem {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String mode = null;

	public ChkMode() {
		addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.getInstance().setMode(mode);
			}
		});
	}
}

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
public class MainMenu extends JMenuBar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// The tool bar
	ToolBar toolbar = new ToolBar();

	// File
	JMenu mnuFile = new JMenu();
	JMenuItem mnuLog = new JMenuItem();
	JMenuItem mnuEons = new JMenuItem();
	JMenuItem mnuIPAS = new JMenuItem();
	JMenuItem mnuRaster = new JMenuItem();
	JMenuItem mnuHMI = new JMenuItem();
	JMenuItem mnuPrint = new JMenuItem();
	JMenuItem mnuSave = new JMenuItem();
	JMenuItem mnuExit = new JMenuItem();

	// Map Menu
	MapMenu mnuMaps = new MapMenu();

	// View menu
	JMenu mnuView = new JMenu();
	JCheckBoxMenuItem stcaWindow = new JCheckBoxMenuItem();
	JCheckBoxMenuItem tdmWindow = new JCheckBoxMenuItem();
	JCheckBoxMenuItem mtcdWindow = new JCheckBoxMenuItem();
	JCheckBoxMenuItem mnuCallsignList = new JCheckBoxMenuItem();
	JMenuItem mnuLoad = new JMenuItem();
	JMenuItem mnuHMILog = new JMenuItem();

	// Track
	TrackMenu mnuTrack = new TrackMenu();

	// Filter
	JMenu mnuFilter = new JMenu();
	OptionSetter chkMil = new OptionSetter("SHOW_MIL");
	OptionSetter chkCiv = new OptionSetter("SHOW_CIVIL");
	OptionSetter chkDL = new OptionSetter("SHOW_DL");
	OptionSetter chkNonDL = new OptionSetter("SHOW_NON_DL");

	// Info
	JMenu mnuInfo = new JMenu();
	OptionSetter chkTime = new OptionSetter("SHOW_TIMER");
	OptionSetter chkTraffic = new OptionSetter("SHOW_TRAFFIC");

	// Alerts
	JMenu mnuAlert = new JMenu();
	OptionSetter chkMTCD = new OptionSetter("SHOW_MTCD");
	OptionSetter chkMONA = new OptionSetter("SHOW_MONA");
	OptionSetter chkAPW = new OptionSetter("SHOW_APW");
	OptionSetter chkTDM = new OptionSetter("SHOW_TDM");

	// Mode menu
	JMenu mnuMode = new JMenu();
	ChkMode chkNormal = new ChkMode();
	ChkMode chkSpaceUsage = new ChkMode();
	ChkMode chkRunwayUsage = new ChkMode();
	ChkMode chkLoad = new ChkMode();

	public MainMenu() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		// File Menu
		mnuFile.setText("File");
		mnuFile.setMnemonic('F');
		this.add(mnuFile);

		mnuEons.setText("EONS File");
		mnuEons.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.getInstance().setEonsLog();
			}
		});
		mnuFile.add(mnuEons);

		mnuIPAS.setText("IPAS Directory");
		mnuIPAS.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.getInstance().setIPASLog();
			}
		});
		mnuFile.add(mnuIPAS);

		mnuRaster.setText("Load Map File");
		mnuRaster.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.getInstance().loadMap();
				MainWindow.getInstance().showMap();
			}
		});
		mnuFile.add(mnuRaster);

		mnuHMI.setText("HMI Log File");
		mnuHMI.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.getInstance().loadHMILog();
			}
		});
		mnuFile.add(mnuHMI);

		mnuFile.addSeparator();

		mnuPrint.setText("Print");
		mnuPrint.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PrintUtilities
						.printComponent(MainWindow.getInstance().radarWindow.pnlView);
			}
		});
		mnuFile.add(mnuPrint);

		mnuSave.setText("Save");
		mnuSave.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SaveUtilities.saveFrame(MainWindow.getInstance().radarWindow);
			}
		});
		mnuFile.add(mnuSave);

		mnuFile.addSeparator();

		mnuExit.setMnemonic('x');
		mnuExit.setText("Exit");
		mnuExit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.gc();
				System.exit(0);
			}
		});

		mnuFile.add(mnuExit);

		// Map Menu
		this.add(mnuMaps);

		// View Menu
		mnuView.setText("View");
		mnuView.setMnemonic('V');
		this.add(mnuView);

		mnuView.add(stcaWindow);
		stcaWindow.setSelected(true);
		stcaWindow.setText("STCA Window");
		stcaWindow.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean state = ((JCheckBoxMenuItem) e.getSource()).getState();
				MainWindow.getInstance().stcaAlert.setVisible(state);
			}
		});

		mnuView.add(tdmWindow);
		tdmWindow.setSelected(true);
		tdmWindow.setText("TDM Window");
		tdmWindow.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean state = ((JCheckBoxMenuItem) e.getSource()).getState();
				MainWindow.getInstance().tdmAlert.setVisible(state);
			}
		});

		mnuView.add(mtcdWindow);
		mtcdWindow.setSelected(true);
		mtcdWindow.setText("MTCD Window");
		stcaWindow.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean state = ((JCheckBoxMenuItem) e.getSource()).getState();
				MainWindow.getInstance().mtcdAlert.setVisible(state);
			}
		});

		mnuView.add(mnuCallsignList);
		mnuCallsignList.setSelected(true);
		mnuCallsignList.setText("Flight List");
		mnuCallsignList.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean state = ((JCheckBoxMenuItem) e.getSource()).getState();
				MainWindow.getInstance().pnlCallsign.setVisible(state);
			}
		});

		// Load Monitoring
		mnuLoad.setText("Load Monitoring");
		mnuLoad.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.getInstance().pnlLoad.setVisible(true);
			}
		});
		mnuView.add(mnuLoad);

		// HMI Log Viewer
		mnuHMILog.setText("Show HMI Logs");
		mnuHMILog.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow.getInstance().showHMILog();
			}
		});
		mnuView.add(mnuHMILog);

		// Track Menu
		this.add(mnuTrack);

		// Info menu
		this.add(mnuInfo);
		mnuInfo.setText("Info");

		chkTime.setText("Timer");
		mnuInfo.add(chkTime);

		chkTraffic.setText("Traffic");
		mnuInfo.add(chkTraffic);

		// Filter menu
		this.add(mnuFilter);
		mnuFilter.setText("Filter");

		chkMil.setText("Military");
		mnuFilter.add(chkMil);

		chkCiv.setText("Civil");
		mnuFilter.add(chkCiv);

		chkDL.setText("Datalink");
		mnuFilter.add(chkDL);

		chkNonDL.setText("Non Datalink");
		mnuFilter.add(chkNonDL);

		// Alerts
		this.add(mnuAlert);
		mnuAlert.setText("Safety Nets");
		chkMTCD.setText("MTCD");
		mnuAlert.add(chkMTCD);

		chkAPW.setText("APW");
		mnuAlert.add(chkAPW);

		chkTDM.setText("TDM");
		mnuAlert.add(chkTDM);

		chkMONA.setText("MONA");
		mnuAlert.add(chkMONA);

		// Mode menu
		mnuMode.setText("Mode");
		mnuMode.setMnemonic('o');
		mnuMode.addMenuListener(new MenuListener() {
			public void menuCanceled(MenuEvent e) {

			}

			public void menuDeselected(MenuEvent e) {
			}

			public void menuSelected(MenuEvent e) {
				// mnuMode.setEnabled(Exercise.REPLAY_STATUS ==
				// Exercise.STOPPED);
			}
		});
		this.add(mnuMode);

		chkNormal.setText(Options.NORMAL_MODE);
		chkNormal.mode = Options.NORMAL_MODE;
		mnuMode.add(chkNormal);

		// Airspace usage Monitoring
		chkSpaceUsage.setText(Options.AIRSPACE_USAGE);
		chkSpaceUsage.mode = Options.AIRSPACE_USAGE;
		mnuMode.add(chkSpaceUsage);

		// Airspace usage Monitoring
		chkRunwayUsage.setText(Options.AIRWAY_USAGE);
		chkRunwayUsage.mode = Options.AIRWAY_USAGE;
		mnuMode.add(chkRunwayUsage);

		// Load Graph
		chkLoad.setText(Options.LOAD_GRAPH);
		chkLoad.mode = Options.LOAD_GRAPH;
		mnuMode.add(chkLoad);

		// add the toolbar
		add(toolbar);
	}
}
