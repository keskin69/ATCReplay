package gui.safety;

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
public class STCAWindow extends AAlertWindow {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public STCAWindow() {
		init();
		setTitle("STCA Alerts");
		setSize(400, 100);
		setVisible(true);
	}
}
