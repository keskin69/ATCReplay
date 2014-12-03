package tools;

import gui.MainWindow;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import selectables.ATrack;
import storage.Element;
import co.ExerciseCO;

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
public class ShowCOContent extends JInternalFrame implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ATrack track = null;

	JTextArea txaContent = new JTextArea();

	JScrollPane scrMain = new JScrollPane();

	public ShowCOContent(ATrack track2) {
		try {
			jbInit();
			setSize(200, 800);
			setVisible(true);
			this.track = track2;

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		this.txaContent.setEditable(false);
		scrMain.getViewport().add(txaContent);
		this.getContentPane().add(scrMain, java.awt.BorderLayout.CENTER);

	}

	public void run() {
		String str = "";
		this
				.setTitle(track.flight.callsign + " "
						+ ExerciseCO.CURRENT_TIME_STR);

		str = "FlightPlan\n";
		str += viewVector(track.flight);
		str.replace("\\[.\\]", "");

		str += "\nRoute\n";
		str += viewVector(track.flight.route);

		txaContent.setText(str);

		this.setLocation(0, 0);
		this.setResizable(true);
		this.setClosable(true);
		this.setIconifiable(true);
		MainWindow.getInstance().addFrame(this, JDesktopPane.MODAL_LAYER);
	}

	private String viewVector(Element item) {
		String resultStr = "";
		String result[] = new String[item.size()];

		Set<String> keyList = item.keySet();
		int i = 0;
		Iterator<String> itr = keyList.iterator();

		while (itr.hasNext()) {
			Object key = itr.next();
			result[i] = key + "\t" + item.get(key);
			i++;
		}

		Arrays.sort(result);

		for (i = 0; i < result.length; i++) {
			resultStr += result[i] + "\n";
		}

		return resultStr;
	}
}
