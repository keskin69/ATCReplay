package gui.menu;

import gui.MainWindow;

import java.awt.event.ActionEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import utils.Options;

public class MapMenu extends JMenu {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JMenu mnuTSA = new JMenu();
	JMenu mnuSectorMap = new JMenu();

	OptionSetter chkSector = new OptionSetter("SHOW_SECTORS");
	OptionSetter chkFix = new OptionSetter("DRAW_FIX_POINTS");
	OptionSetter chkAirport = new OptionSetter("SHOW_AIRPORTS");
	OptionSetter chkFixName = new OptionSetter("SHOW_FIX_NAMES");
	OptionSetter chkAirways = new OptionSetter("DRAW_AIRWAYS");
	OptionSetter chkMap = new OptionSetter("SHOW_MAP");

	public MapMenu() {
		setText("Maps");
		setMnemonic('M');

		addMenuListener(new MenuListener() {
			public void menuCanceled(MenuEvent e) {

			}

			public void menuDeselected(MenuEvent e) {

			}

			public void menuSelected(MenuEvent e) {

				// TSA Map Sub Menu
				mnuTSA.removeAll();
				JCheckBoxMenuItem mnuTemp = null;

				if (Options.TSA_LIST != null) {
					for (int i = 0; i < Options.TSA_LIST.length; i++) {
						mnuTemp = new JCheckBoxMenuItem(Options.TSA_LIST[i]);
						mnuTemp.setState(Options.SELECTED_TSA_MAPS[i]);
						mnuTemp.setUI(new StayOpenCheckBoxMenuItemUI());
						mnuTemp
								.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(ActionEvent e) {
										for (int i = 0; i < mnuTSA
												.getItemCount(); i++) {
											JCheckBoxMenuItem item = (JCheckBoxMenuItem) mnuTSA
													.getItem(i);
											if (e.getActionCommand().equals(
													item.getText())) {
												Options.SELECTED_TSA_MAPS[i] = item
														.getState();
												MainWindow.getInstance()
														.updateRadarView();
												break;
											}
										}
									}
								});

						mnuTSA.add(mnuTemp);
					}
				}

				// Sector Map SubMenu
				mnuSectorMap.removeAll();
				JCheckBoxMenuItem mnuSect = null;

				if (Options.SECTOR_LIST != null) {
					for (int i = 0; i < Options.SECTOR_LIST.length; i++) {
						mnuSect = new JCheckBoxMenuItem(Options.SECTOR_LIST[i]);
						mnuSect.setState(Options.HIGHLIGHT_SECTOR_MAP[i]);
						mnuSect.setUI(new StayOpenCheckBoxMenuItemUI());
						mnuSect
								.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(ActionEvent e) {
										for (int i = 0; i < mnuSectorMap
												.getItemCount(); i++) {
											JCheckBoxMenuItem item = (JCheckBoxMenuItem) mnuSectorMap
													.getItem(i);
											if (e.getActionCommand().equals(
													item.getText())) {
												Options.HIGHLIGHT_SECTOR_MAP[i] = item
														.getState();
												MainWindow.getInstance()
														.updateRadarView();
												break;
											}
										}
									}
								});

						mnuSectorMap.add(mnuSect);
					}
				}
			}
		});

		chkFixName.setText("Beacon Names");
		add(chkFixName);

		chkFix.setText("Beacons");
		add(chkFix);

		chkAirport.setText("Airports");
		add(chkAirport);

		chkAirways.setText("Airways");
		add(chkAirways);

		chkMap.setText("Background Map");
		add(chkMap);

		addSeparator();

		mnuSectorMap.setText("Sectors");
		add(mnuSectorMap);

		mnuTSA.setText("TSA");
		add(mnuTSA);
	}
}
