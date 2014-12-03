package co;

import java.io.IOException;
import java.io.LineNumberReader;

import map.TSASector;
import storage.Element;
import storage.ListManager;

public class TSACO extends Element {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int NOT_ACTIVE = 0;
	public static final int PRE_ACTIVE = 1;
	public static final int ACTIVE = 2;

	public void update(LineNumberReader lineReader, short stat)
			throws IOException {
		super.update(lineReader, stat);

		String name = getStrValue("name");
		TSASector tsa = null;

		for (Object obj : ListManager.tsaSectorList) {
			tsa = (TSASector) obj;

			if (name.equals(tsa.name)) {
				tsa.status = getIntValue("area_status");

				break;
			}
		}
	}
}
