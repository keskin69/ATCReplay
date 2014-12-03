package gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.DefaultListModel;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public abstract class AListWindow extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JList lstAlert = new JList();

	protected DefaultListModel lstData = new DefaultListModel();

	BorderLayout borderLayout = new BorderLayout();

	JScrollPane pnlScr = new JScrollPane();

	protected void init() {
		this.getContentPane().setLayout(borderLayout);
		this.setTitle("Alert List");

		lstAlert.setBackground(new Color(212, 208, 200));
		lstAlert.setModel(lstData);
		lstAlert.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		lstAlert.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent evt) {
				int idx = evt.getFirstIndex();
				Object obj = lstData.getElementAt(idx);
				listSelection(obj);
			}
		});

		pnlScr.getViewport().add(lstAlert);

		this.getContentPane().add(pnlScr, java.awt.BorderLayout.CENTER);
	}

	public abstract void listSelection(Object obj);

	public abstract void update(Object obj, short type);
}
