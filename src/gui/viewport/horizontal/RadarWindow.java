package gui.viewport.horizontal;

import gui.viewport.RasterUtils;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

import javax.swing.JInternalFrame;

import utils.Config;

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
public class RadarWindow extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// JScrollBar scrVer = new JScrollBar();
	// JScrollBar scrHor = new JScrollBar();
	BorderLayout borderLayout1 = new BorderLayout();

	public RadarView pnlView = new RadarView();

	public RadarWindow() {
		try {
			jbInit();
			setSize(Toolkit.getDefaultToolkit().getScreenSize().height, Toolkit
					.getDefaultToolkit().getScreenSize().height);
			setVisible(true);
			pnlView.changeCenter((int) pnlView.MAX_SCROLL / 2,
					(int) pnlView.MAX_SCROLL / 2);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent event) {
				super.componentResized(event);
				pnlView.viewWidth = getWidth();
				pnlView.viewHeight = getHeight();
			};
		});

		this.setLayout(borderLayout1);
		this.setIconifiable(true);
		this.setResizable(true);
		this.setTitle("Radar Window");
		this.add(pnlView, java.awt.BorderLayout.CENTER);

		// Alternative: Use scrollbars to scroll on the viewport
		/*
		 * 
		 * scrVer.setValue((int) ViewManager.MAX_SCROLL / 2);
		 * scrHor.setOrientation(JScrollBar.HORIZONTAL); scrHor.setValue((int)
		 * ViewManager.MAX_SCROLL / 2);
		 * 
		 * this.add(scrHor, java.awt.BorderLayout.SOUTH); this.add(scrVer,
		 * java.awt.BorderLayout.EAST); scrHor.addAdjustmentListener(new
		 * AdjustmentListener() { public void
		 * adjustmentValueChanged(AdjustmentEvent act) {
		 * ViewManager.changeCenter(scrHor.getValue(), scrVer.getValue()); } });
		 * 
		 * scrVer.addAdjustmentListener(new AdjustmentListener() { public void
		 * adjustmentValueChanged(AdjustmentEvent act) {
		 * ViewManager.changeCenter(scrHor.getValue(), scrVer.getValue()); } });
		 */
	}

	public void refresh() {
		pnlView.repaint();
	}

	public void setMapImage(String mapFile) {
		// TODO
		BufferedImage mapImage = RasterUtils.makeRaster(pnlView,
				Config.MAP_FILE, -27, 35, 60, 72);

		pnlView.setBackgroundMap(mapImage);
		pnlView.repaint();
	}
}
