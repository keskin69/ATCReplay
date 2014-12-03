package gui.menu;

import gui.MainWindow;

import java.awt.event.ActionEvent;
import java.lang.reflect.Field;

import javax.swing.JCheckBoxMenuItem;

import utils.Options;

public class OptionSetter extends JCheckBoxMenuItem {

	private static final long serialVersionUID = 1L;

	public String name = null;
	private Field f = null;

	public OptionSetter(final String name) {
		this.name = name;

		try {
			f = Options.class.getDeclaredField(name);
			this.setSelected(f.getBoolean(null));
		} catch (Exception e) {
			e.printStackTrace();
		}

		addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean state = ((JCheckBoxMenuItem) e.getSource()).getState();

				try {
					f.setBoolean(new Options(), state);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				MainWindow.getInstance().updateRadarView();
			}
		});
	}
}
