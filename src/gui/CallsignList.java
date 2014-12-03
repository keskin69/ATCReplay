package gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import utils.Config;
import utils.SortingListModel;
import co.FlightCO;

public class CallsignList extends JInternalFrame {

	class CallsignEntry {
		String callsign = null;
		int flightId = 0;

		public String toString() {
			return callsign;
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JList lstCallsign = new JList();

	SortingListModel lstData = new SortingListModel();

	BorderLayout borderLayout = new BorderLayout();

	JScrollPane pnlScr = new JScrollPane();

	public CallsignList() {
		try {
			jbInit();
			setSize(170, 500);
			setVisible(true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		this.getContentPane().setLayout(borderLayout);
		this.setTitle("Flight List");

		lstCallsign.setBackground(new Color(212, 208, 200));
		lstCallsign.setModel(lstData);
		lstCallsign.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		pnlScr.getViewport().add(lstCallsign);
		lstCallsign.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent evt) {
				int idx = evt.getFirstIndex();
				String callsign = (String) lstData.getElementAt(idx);
				MainWindow.getInstance().highlightFligt(callsign);
			}
		});

		this.getContentPane().add(pnlScr, java.awt.BorderLayout.CENTER);
	}

	public void updateCallsignList(FlightCO flight, short stat) {
		if (stat == Config.REMOVE) {
			lstData.removeElement(flight.callsign);
		} else {
			// add to the list
			lstData.addElement(flight.callsign);
		}

		this.setTitle("Flight List - " + lstData.getSize());
	}
}
