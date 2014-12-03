package gui.viewport.vertical;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JInternalFrame;

public class VerticalWindow extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// JScrollBar scrVer = new JScrollBar();
	// JScrollBar scrHor = new JScrollBar();
	BorderLayout borderLayout1 = new BorderLayout();

	public VerticalView pnlView = new VerticalView();

	public VerticalWindow() {
		try {
			jbInit();
			pnlView.changeCenter((int) pnlView.MAX_SCROLL / 2,
					(int) pnlView.MAX_SCROLL / 2);
			setSize(600, 600);
			setVisible(true);
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
		this.setTitle("Vertical View Window");
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

}
