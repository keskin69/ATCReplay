package utils;

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
public class FileFilterSuite {
	public static final AFilter EonsFile = new AFilter(".gz",
			"Eons Recording File");
	public static final AFilter HMIFile = new AFilter(".log",
			"HMI Recording file");
}
