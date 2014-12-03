package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import utils.Options;
import utils.Util;
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
 * Company:
 * </p>
 * 
 * @author Mustafa Keskin
 * @version 1.0
 */
public class ToolBar extends JToolBar {
	class Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if ("PAUSE".equals(e.getActionCommand())) {
				REPLAY_STATUS = run_status.PAUSED;
				txtTarget.setText(ExerciseCO.CURRENT_TIME_STR);
			} else if ("START".equals(e.getActionCommand())) {
				ToolBar.REPLAY_STATUS = ToolBar.run_status.SYNCRONIZED;
				MainWindow.getInstance().start();
			} else if ("STEP".equals(e.getActionCommand())) {
				TARGET_TIME = Util.getTimeStr(Util
						.getTime(ExerciseCO.CURRENT_TIME_STR) + 60);
				REPLAY_STATUS = run_status.ONE_STEP_ONLY;
				MainWindow.getInstance().start();
			} else if ("FFW".equals(e.getActionCommand())) {
				TARGET_TIME = txtTarget.getText();
				REPLAY_STATUS = run_status.FFW;
				MainWindow.getInstance().start();
			} else if ("SHADOW".equals(e.getActionCommand())) {
				REPLAY_STATUS = run_status.SHADOW;
			} else if ("FFW_STCA".equals(e.getActionCommand())) {
				REPLAY_STATUS = run_status.FFW_STCA;
				MainWindow.getInstance().start();
			}

			updateButtonStatus();
		}
	}

	public static enum run_status {
		STOPPED, SHADOW, SYNCRONIZED, PAUSED, ONE_STEP_ONLY, FFW, FFW_STCA
	};

	class ToolButton extends JButton {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ToolButton(String action, String toolTip, String iconStr) {
			ImageIcon icon = createImageIcon(iconStr);
			setIcon(icon);
			setMargin(new Insets(0, 0, 0, 0));
			setActionCommand(action);
			setToolTipText(toolTip);
			addActionListener(listener);
		}

		/** Returns an ImageIcon, or null if the path was invalid. */
		private ImageIcon createImageIcon(String path) {
			java.net.URL imgURL = getClass().getResource(path);
			if (imgURL != null) {
				return new ImageIcon(imgURL);
			} else {
				System.err.println("Couldn't find file: " + path);
				return null;
			}
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Listener listener = new Listener();
	private ToolButton btnForward = null;
	private ToolButton btnStep = null;

	private ToolButton btnStart = null;
	private ToolButton btnPause = null;
	private ToolButton btnSTCA = null;
	// ToolButton btnStop = null;

	private JTextField txtTarget = new JTextField();

	private JSlider sldSpeed = new JSlider();

	public static String TARGET_TIME = "";

	public static run_status REPLAY_STATUS = run_status.STOPPED;

	public ToolBar() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		this.setBorder(BorderFactory.createEtchedBorder());
		txtTarget.setFont(new java.awt.Font("Dialog", Font.PLAIN, 14));
		txtTarget.setMaximumSize(new Dimension(65, 28));
		txtTarget.setMinimumSize(new Dimension(65, 28));
		txtTarget.setToolTipText("Simulation target time");
		txtTarget.setText("__:__:__");

		sldSpeed.setMaximum(5);
		sldSpeed.setMinimum(1);
		sldSpeed.setValue(1);
		sldSpeed.setMajorTickSpacing(1);
		sldSpeed.setMaximumSize(new Dimension(200, 24));
		sldSpeed.setToolTipText("Replay speed [1-5]");
		this.add(sldSpeed);

		sldSpeed.addChangeListener(new ChangeListener() {
			// This method is called whenever the slider's value is changed
			public void stateChanged(ChangeEvent evt) {
				JSlider slider = (JSlider) evt.getSource();
				Options.REPLAY_SPEED = slider.getValue();
			}
		});

		// stop button
		// btnStop = new ToolButton("STOP", "Stop simulation",
		// "/toolbarButtonGraphics/media/Stop24.gif");
		// add(btnStop);

		// pause button
		btnPause = new ToolButton("PAUSE", "Pause simulation",
				"/toolbarButtonGraphics/media/Pause24.gif");
		add(btnPause);

		// start button
		btnStart = new ToolButton("START", "Start/Continue simulation",
				"/toolbarButtonGraphics/media/Play24.gif");
		add(btnStart);

		// step forward button
		btnStep = new ToolButton("STEP", "One Step Forward",
				"/toolbarButtonGraphics/media/StepForward24.gif");
		add(btnStep);

		// forward button
		btnForward = new ToolButton("FFW",
				"Full speed forward until target time",
				"/toolbarButtonGraphics/media/FastForward24.gif");
		add(btnForward);

		// forward until STCA button
		btnSTCA = new ToolButton("FFW_STCA",
				"Full speed forward until next STCA",
				"/toolbarButtonGraphics/navigation/Forward24.gif");
		add(btnSTCA);

		// target time
		add(txtTarget);

		this.setEnabled(false);
	}

	// TODO
	public void updateButtonStatus() {

	}
}
