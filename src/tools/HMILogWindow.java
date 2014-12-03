package tools;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

class HMILog_mnuClear_mouseAdapter extends MouseAdapter {
	private HMILogWindow adaptee;

	HMILog_mnuClear_mouseAdapter(HMILogWindow adaptee) {
		this.adaptee = adaptee;
	}

	public void mouseClicked(MouseEvent e) {
		adaptee.mnuClear_mouseClicked(e);
	}
}

class HMILog_mnuCopy_mouseAdapter extends MouseAdapter {
	private HMILogWindow adaptee;

	HMILog_mnuCopy_mouseAdapter(HMILogWindow adaptee) {
		this.adaptee = adaptee;
	}

	public void mouseClicked(MouseEvent e) {
		adaptee.mnuCopy_mouseClicked(e);
	}
}

class HMILog_txtLog_mouseAdapter extends MouseAdapter {
	private HMILogWindow adaptee;

	HMILog_txtLog_mouseAdapter(HMILogWindow adaptee) {
		this.adaptee = adaptee;
	}

	public void mouseClicked(MouseEvent e) {
		adaptee.txtLog_mouseClicked(e);
	}
}

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
public class HMILogWindow extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static HMILogWindow instance = null;

	public static HMILogWindow getInstance() {
		if (instance == null) {
			instance = new HMILogWindow();
		}

		return instance;
	}

	JScrollPane scrPane = new JScrollPane();

	JTextArea txtLog = new JTextArea();

	BorderLayout borderLayout1 = new BorderLayout();

	JPopupMenu mnuText = new JPopupMenu();

	JMenuItem mnuClear = new JMenuItem();
	JMenuItem mnuCopy = new JMenuItem();

	private HMILogWindow() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void append(String txt) {
		txtLog.setText(txtLog.getText() + txt + "\n");
	}

	private void jbInit() throws Exception {
		this.setTitle("HMI Log View");
		this.getContentPane().setLayout(borderLayout1);
		txtLog.addMouseListener(new HMILog_txtLog_mouseAdapter(this));
		mnuClear.addMouseListener(new HMILog_mnuClear_mouseAdapter(this));
		mnuCopy.addMouseListener(new HMILog_mnuCopy_mouseAdapter(this));
		scrPane.getViewport().add(txtLog);
		this.getContentPane().add(scrPane, java.awt.BorderLayout.CENTER);
		txtLog.setText("");
		txtLog.setEditable(false);
		this.setSize(300, 600);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(true);
		this.setClosable(true);
		this.setIconifiable(true);
		mnuText.setVisible(false);

		mnuClear.setText("Clear");
		mnuText.add(mnuClear);

		mnuCopy.setText("Copy");
		mnuText.add(mnuCopy);
	}

	public void mnuClear_mouseClicked(MouseEvent e) {
		txtLog.setText("");
	}

	public void mnuCopy_mouseClicked(MouseEvent e) {
		txtLog.copy();
	}

	public void txtLog_mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			// show small menu
			mnuText.setLocation(e.getX(), e.getY());
			mnuText.setVisible(true);
		}
	}
}
