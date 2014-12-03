package utils;

import java.text.Collator;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

import javax.swing.AbstractListModel;

public class SortingListModel extends AbstractListModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Define a SortedSet
	TreeSet<Object> model;

	private static Comparator<Object> USEFUL_COMPARATOR = new Comparator<Object>() {
		public int compare(Object o1, Object o2) {
			String str1 = o1.toString();
			String str2 = o2.toString();
			Collator collator = Collator.getInstance();
			int result = collator.compare(str1, str2);

			return result;
		}
	};

	public SortingListModel() {
		model = new TreeSet<Object>(USEFUL_COMPARATOR);
	}

	public void addAll(Object elements[]) {
		Collection<Object> c = Arrays.asList(elements);
		model.addAll(c);
		fireContentsChanged(this, 0, getSize());
	}

	// Other methods
	public void addElement(Object element) {

		if (model.add(element)) {
			fireContentsChanged(this, 0, getSize());
		}
	}

	public void clear() {
		model.clear();
		fireContentsChanged(this, 0, getSize());
	}

	public boolean contains(Object element) {
		return model.contains(element);
	}

	public Object firstElement() {
		// Return the appropriate element
		return model.first();
	}

	public Object getElementAt(int index) {
		// Return the appropriate element
		Object obj = null;

		try {
			obj = model.toArray()[index];
		} catch (Exception ex) {
		}

		return obj;
	}

	// ListModel methods
	public int getSize() {
		// Return the model size
		return model.size();
	}

	public Iterator<Object> iterator() {
		return model.iterator();
	}

	public Object lastElement() {
		// Return the appropriate element
		return model.last();
	}

	public boolean removeElement(Object element) {
		boolean removed = model.remove(element);

		if (removed) {
			fireContentsChanged(this, 0, getSize());
		}
		return removed;
	}
}
