package resources;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

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
public class Resources {
	// Colors
	public static final Color INFO_COLOR = Color.GRAY;
	public static final Color SECTOR_COLOR = Color.GRAY;
	public static final Color SELECTED_SECTOR_COLOR = new Color(200, 50, 64);
	public static final Color TRACK_COLOR = Color.BLACK;
	public static final Color HEADING_COLOR1 = new Color(110, 230, 200);
	public static final Color HEADING_COLOR2 = new Color(110, 20, 255, 200);
	public static final Color TRACK_ALERT_COLOR = Color.RED;

	public static final Color FIX_COLOR = Color.GRAY;
	public static final Color AIRPORT_COLOR = new Color(50, 50, 80);
	public static final Color TSA_LINE_COLOR = new Color(240, 150, 0);
	public static final Color TSA_PREACTIVE_COLOR = new Color(240, 140, 0, 10);
	public static final Color TSA_ACTIVE_COLOR = new Color(240, 160, 0, 60);
	public static final Color AIRWAYS_COLOR = new Color(120, 40, 220);
	public static final Color MILITARY_COLOR = new Color(20, 140, 200);
	public static final Color WARNING_COLOR = new Color(230, 140, 10);
	public static final Color VIEWPORT_COLOR = new Color(70, 70, 70);
	public static final Color SELECTED_TRACK_COLOR = new Color(100, 120, 100,
			100);
	public static final Color COORD_LABEL_COLOR = Color.ORANGE;

	public static final Color ROUTE_LINE_COLOR = new Color(117, 190, 79);
	public static final Color ROUTE_SHADOW_COLOR = new Color(100, 100, 100, 100);
	public static final Color ROUTE_TEXT_COLOR = new Color(40, 140, 200);

	// Strokes
	public static final float[] dashPattern = { 10f };
	public static final BasicStroke DASH_LINE = new BasicStroke(2,
			BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10f, dashPattern, 5f);

	public static final BasicStroke NORMAL_LINE = new BasicStroke(1,
			BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);

	public static final BasicStroke ROUTE_LINE = new BasicStroke(2,
			BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);

	public static final BasicStroke ROUTE_SHADOW = new BasicStroke(10,
			BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);

	// Fonts
	public static final Font ALERT_FONT = new java.awt.Font("Dialog",
			Font.BOLD, 24);
	public static final Font TRACK_HIGHLIGHTED_FONT = new java.awt.Font(
			"Dialog", Font.BOLD, 12);
	public static final Font TRACK_FONT = new java.awt.Font("Dialog",
			Font.PLAIN, 12);
	public static final Font FIX_FONT = new java.awt.Font("Dialog", Font.PLAIN,
			10);
	public static final Font INFO_FONT = new Font("SansSerif", Font.BOLD, 24);
	public static final Font SMALL_FONT = new Font("SansSerif", Font.PLAIN, 10);
}
