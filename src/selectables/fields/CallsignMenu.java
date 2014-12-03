package selectables.fields;

import gui.MainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import selectables.ATrack;
import tools.ShowCOContent;
import tools.VerticalView;

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
public class CallsignMenu extends AField {
	private JMenuItem mnuMark = null;
	private JMenuItem mnuCO = null;
	private JMenuItem mnuVAW = null;
	private JMenuItem mnuRoute = null;
	private JMenuItem mnuSpeed = null;

	public void action(int mouseButton) {
		if (mouseButton == MouseEvent.BUTTON1) {
			menu = new JPopupMenu();
			menu.add(track.flight.callsign);
			menu.addSeparator();

			// MARK
			mnuMark = new JMenuItem("MARK");
			mnuMark.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					hidePopup();
					track.marked = !track.marked;

					if (track.marked) {
						mnuMark.setText("UNMARK");
					} else {
						mnuMark.setText("MARK");
					}

					MainWindow.getInstance().updateRadarView();
				}
			});
			menu.add(mnuMark);

			// CO
			mnuCO = new JMenuItem("Show CO");
			mnuCO.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					hidePopup();
					new ShowCOContent(track).run();
					MainWindow.getInstance().updateRadarView();
				}
			});
			menu.add(mnuCO);

			// VAW
			mnuVAW = new JMenuItem("VAW");
			mnuVAW.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					hidePopup();
					new VerticalView(track);
				}
			});
			menu.add(mnuVAW);

			// Route
			mnuRoute = new JMenuItem("Route");
			mnuRoute.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					hidePopup();
					track.drawRoute = !track.drawRoute;

					// also draw route on vertical window
					track = MainWindow.getInstance().verticalWindow.pnlView.trackList
							.findByCallsign(track.flight.callsign);
					track.drawRoute = !track.drawRoute;

					MainWindow.getInstance().updateRadarView();
				}
			});
			menu.add(mnuRoute);

			// Speed Vector
			mnuSpeed = new JMenuItem("Speed Vector");
			mnuSpeed.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					hidePopup();
					MainWindow.getInstance().updateRadarView();
				}
			});
			menu.add(mnuSpeed);

			super.displayPopup();
		}
	}

	public void init(ATrack track) {
		this.track = track;
		this.content = track.flight.callsign;

		// set the corner of the field relative to the track location
		this.offsetX = track.trackLabel.cornerX;
		this.offsetY = track.trackLabel.cornerY;
	}
}
