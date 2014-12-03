package tools;

import gui.MainWindow;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;

import co.ExerciseCO;

import selectables.ATrack;
import utils.Util;

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
public class VerticalView extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ATrack track = null;

	public VerticalView(ATrack track2) {
		super("Vertical View Window");

		this.track = track2;

		XYDataset xydataset = createDataset();
		JFreeChart jfreechart = createChart(xydataset);
		ChartPanel chartpanel = new ChartPanel(jfreechart);

		chartpanel.setPreferredSize(new Dimension(500, 400));

		setContentPane(chartpanel);

		this.pack();
		this.setLocation(0, 0);
		this.setVisible(true);
		this.setResizable(true);
		this.setClosable(true);
		this.setIconifiable(true);
		MainWindow.getInstance().addFrame(this, JDesktopPane.POPUP_LAYER);
	}

	private JFreeChart createChart(XYDataset xydataset) {
		JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(
				track.flight.callsign, "Time", "Level", xydataset, true, true,
				false);

		jfreechart.setBackgroundPaint(Color.white);
		XYPlot xyplot = (XYPlot) jfreechart.getPlot();
		xyplot.setBackgroundPaint(Color.lightGray);
		xyplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));
		xyplot.setDomainGridlinePaint(Color.white);
		xyplot.setRangeGridlinePaint(Color.white);
		XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer) xyplot
				.getRenderer();

		for (int i = 0; i < xyplot.getSeriesCount(); i++) {
			xylineandshaperenderer.setSeriesShapesVisible(i, true);
			xylineandshaperenderer.setSeriesShapesFilled(i, true);
		}

		NumberAxis numberaxis = (NumberAxis) xyplot.getRangeAxis();
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		return jfreechart;
	}

	private XYDataset createDataset() {
		TimeSeries timeSeries = new TimeSeries(track.flight.callsign);
		int points = track.flight.route.getIntValue("points");

		Minute eto = null;
		double level = 0D;
		// String beacon = "";

		int first = track.flight.route.getCurrentPoint();
		int i = first;

		for (i = first; i < points; i++) {
			eto = timeTrans(i);
			level = levelTrans(i);
			
			// TODO
			// beacon = track.route.getStrValue("beacon", i);
			timeSeries.addOrUpdate(eto, level);
		}

		TimeSeriesCollection xyseriescollection = new TimeSeriesCollection();
		xyseriescollection.addSeries(timeSeries);

		return xyseriescollection;
	}

	private double levelTrans(int i) {
		int fl = track.flight.route.getIntValue("computed_fl", i);
		return fl / 100D;
	}

	private Minute timeTrans(int i) {
		int eto = track.flight.route.getIntValue("eto", i) + ExerciseCO.START_TIME;
		Minute min = Util.getMinute(Util.getTimeStrShort(eto));

		return min;
	}
}
