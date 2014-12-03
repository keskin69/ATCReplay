package selectables;

import java.awt.Color;

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
public abstract class ASelectable implements ISelectable {
	public Color drawColor = Color.BLACK;

	protected void setDrawColor(Color drawColor) {
		this.drawColor = drawColor;
	}
}
