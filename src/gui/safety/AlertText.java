package gui.safety;

import javax.swing.JLabel;

import resources.Resources;
import storage.safetynets.Alert;

public class AlertText extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int id = 0;
	public Alert alert;

	public AlertText(Alert alert) {
		String txt = "<html>" + alert.flight1.callsign + "&nbsp;";

		if (alert.flight2 != null) {
			txt += alert.flight2.callsign + "&nbsp;";
		}

		txt += alert.content;
		setText(txt + "</html>");
		id = alert.getId();

		this.alert = alert;
		setFont(Resources.ALERT_FONT);
	}

	public String toString() {
		return this.getText();
	}
}
