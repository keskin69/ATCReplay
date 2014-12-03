package gui.menu;

import java.awt.event.ActionEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import utils.Options;

public class TrackMenu extends JMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JMenu mnuSector = new JMenu();
	OptionSetter chkLabel = new OptionSetter("DRAW_LABEL");
	OptionSetter chkTrack = new OptionSetter("DRAW_TRACK");
	OptionSetter chkHist = new OptionSetter("DRAW_HISTORY");
	OptionSetter chkHistRoute = new OptionSetter("DRAW_HISTORY_ROUTE");
	OptionSetter chkHeading = new OptionSetter("DRAW_HEADING");
	OptionSetter chkLine2 = new OptionSetter("SHOW_LINE_2");

	public TrackMenu() {
		setText("Track");
		setMnemonic('T');

		addMenuListener(new MenuListener() {
			public void menuCanceled(MenuEvent e) {

			}

			public void menuDeselected(MenuEvent e) {
			}

			public void menuSelected(MenuEvent e) {
				mnuSector.removeAll();
				JCheckBoxMenuItem mnuSect = null;

				if (Options.SECTOR_LIST != null) {
					for (int i = 0; i < Options.SECTOR_LIST.length; i++) {
						mnuSect = new JCheckBoxMenuItem(Options.SECTOR_LIST[i]);
						mnuSect.setState(Options.SELECTED_SECTORS[i]);
						mnuSect.setUI(new StayOpenCheckBoxMenuItemUI());
						mnuSect
								.addActionListener(new java.awt.event.ActionListener() {
									public void actionPerformed(ActionEvent e) {
										for (int i = 0; i < mnuSector
												.getItemCount(); i++) {
											JCheckBoxMenuItem item = (JCheckBoxMenuItem) mnuSector
													.getItem(i);

											if (e.getActionCommand().equals(
													item.getText())) {
												Options.SELECTED_SECTORS[i] = item
														.getState();

												break;
											}
										}
									}
								});

						mnuSector.add(mnuSect);
					}
				}
			}
		});

		// Track Labels
		chkLabel.setText("Label");
		add(chkLabel);

		// Track
		chkTrack.setText("Symbol");
		add(chkTrack);

		// History Dots
		chkHist.setText("History");
		add(chkHist);

		// History Route
		chkHistRoute.setText("Overflown Route");
		add(chkHistRoute);

		// Heading
		chkHeading.setText("Heading");
		add(chkHeading);

		// Show only first line
		chkLine2.setText("Line 2");
		add(chkLine2);

		addSeparator();

		// sector filterin submenu
		mnuSector.setText("Sectors");
		add(mnuSector);
	}

}
