package storage.bada;

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
 * @author not attributable
 * @version 1.0
 */
public class BadaValue {
	protected int level;
	protected double fnom;
	protected double flow;
	protected double fhi;

	protected BadaValue(int level, double flow, double fnom, double fhi, int tas) {
		this.level = level;
		this.flow = flow;
		this.fnom = fnom;
		this.fhi = fhi;
	}
}
