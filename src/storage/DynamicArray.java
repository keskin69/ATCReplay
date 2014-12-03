package storage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * 
 * @author mkeskin
 */
public class DynamicArray extends HashMap<String, String> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getData(String name) {
		return getData(name, 0);
	}

	public String getData(String name, int index) {
		String key = name + " [" + String.valueOf(index) + "]";
		String value = (String) get(key);

		if (value == null) {
			value = "";
		}

		return value;
	}

	public void update(String line) {
		String tokens[] = line.split("=");
		String name = tokens[0].trim();
		String value = tokens[1].trim();

		put(name, value);
	}

	public void dump() {
		Set<String> set = this.keySet();
		Iterator<String> it = set.iterator();
		String key;
		// String result = "";

		while (it.hasNext()) {
			key = (String) it.next();
			// result += key + "=" + get(key) +
			// System.getProperty("line.separator");
			System.out.println(key + "=" + get(key));
		}
	}

	public boolean getBoolean(String field) {
		return getStrValue(field).equals("1");
	}

	public double getDblValue(String field) {
		return getDblValue(field, 0);
	}

	public double getDblValue(String field, int idx) {

		double value = 0D;

		try {
			value = new Double(getData(field, idx)).doubleValue();
		} catch (NumberFormatException ex) {
			// can be empty
		}

		return value;
	}

	public int getIntValue(String field) {
		return getIntValue(field, 0);
	}

	public int getIntValue(String field, int idx) {
		int value = 0;

		try {
			value = new Integer(getData(field, idx)).intValue();
		} catch (NumberFormatException ex) {
			// can be empty
		}

		return value;
	}

	public String getStrValue(String field) {
		return getStrValue(field, 0);
	}

	public String getStrValue(String field, int idx) {
		return getData(field, idx);
	}
}
