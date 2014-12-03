package storage;

import gui.viewport.AViewport;

import java.awt.Graphics2D;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;

import selectables.ISelectable;
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
public class ElementList extends ArrayList<Object> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Class<? extends Element> elemClass = null;
	private boolean changed = false;

	public ElementList() {
	}

	public ElementList(Class<? extends Element> elemClass) {
		this.elemClass = elemClass;
	}

	public void drawList(AViewport view, Graphics2D g2) {
		ISelectable obj = null;

		try {
			for (int i = 0; i < size(); i++) {
				obj = (ISelectable) get(i);
				obj.draw(view, g2);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public Element findByCallsign(String callsign) {
		for (Object obj : this) {

			if (((Element) obj).getStrValue("callsign").equals(callsign)) {
				return (Element) obj;
			}
		}

		return null;
	}

	public Element findByFlightId(int id) {
		Element element = null;
		for (int i = 0; i < size(); i++) {
			element = (Element) get(i);

			if (element.getFlightId() == id) {
				return element;
			}
		}

		return null;
	}

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	public Element update(LineNumberReader lineReader, int id, short stat)
			throws IOException {
		Element obj = null;
		boolean found = false;
		setChanged(true);

		if (stat == Config.NEW) {
			try {
				obj = (Element) elemClass.newInstance();
			} catch (Exception ex) {
				ex.printStackTrace();
				System.exit(1);
			}

			obj.init(id, lineReader);

			add(obj);
		} else if (stat == Config.UPDATE) {
			// find the element
			for (int i = 0; i < size(); i++) {
				obj = (Element) get(i);

				if (obj.getId() == id) {
					obj.update(lineReader, stat);
					found = true;
					break;
				}
			}

			// Element cannot found
			if (!found) {
				String line = lineReader.readLine();
				String tmp = line.split("=")[1];
				int numOfLine = new Integer(tmp.trim()).intValue();

				int i = 0;
				for (; i < numOfLine; i++) {
					if (line.startsWith("viewport_")) {
						i--;
						continue;
					}

					line = lineReader.readLine();
				}
			}
		} else {
			// destruct the object
			for (int i = 0; i < size(); i++) {
				obj = (Element) get(i);

				if (obj.getId() == id) {
					remove(i);
					break;
				}
			}
		}

		return obj;
	}
}
