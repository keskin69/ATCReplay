package gui.menu;

import javax.swing.MenuSelectionManager;
import javax.swing.plaf.basic.BasicCheckBoxMenuItemUI;

public class StayOpenCheckBoxMenuItemUI extends BasicCheckBoxMenuItemUI {
	protected void doClick(MenuSelectionManager msm) {
		menuItem.doClick(0);
	}
}
