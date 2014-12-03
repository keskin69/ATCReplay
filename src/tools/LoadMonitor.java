package tools;

import java.awt.Color;
import java.awt.Dimension;

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

import storage.ElementList;
import storage.ListManager;
import utils.Options;
import utils.Util;
import co.ExerciseCO;
import co.FlightCO;

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
public class LoadMonitor extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static TimeSeries timeSeries = null;

	public LoadMonitor() {
		super("Traffic Monitoring Window");

		XYDataset xydataset = createDataset();

		JFreeChart jfreechart = createChart(xydataset);
		ChartPanel chartpanel = new ChartPanel(jfreechart);

		chartpanel.setPreferredSize(new Dimension(400, 400));
		setContentPane(chartpanel);

		this.pack();
		this.setVisible(false);
		this.setResizable(true);
		this.setClosable(true);
		this.setIconifiable(true);
	}

	private JFreeChart createChart(XYDataset xydataset) {
		JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(
				ExerciseCO.CURRENT_TIME_STR, "Time", "A/C", xydataset, true,
				true, false);

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
		timeSeries = new TimeSeries("Total A/C");

		TimeSeriesCollection xyseriescollection = new TimeSeriesCollection();
		xyseriescollection.addSeries(timeSeries);

		return xyseriescollection;
	}

	public void updateDataset() {
		if (!ExerciseCO.CURRENT_TIME_STR.equals("")) {
			Minute min = Util.getMinute(ExerciseCO.CURRENT_TIME_STR);

			ElementList list = ListManager.flightList;
			int ctr = 0;
			String str;
			FlightCO flight = null;

			for (Object obj : list) {
				flight = (FlightCO) obj;

				if (flight.flight_phase == 1) {
					for (int j = 0; j < Options.SECTOR_LIST.length; j++) {
						if ((Options.SELECTED_SECTORS[j])) {
							str = Options.SECTOR_LIST[j];

							if (flight.assumingSector.equals(str)) {
								ctr++;
								break;
							}
						}
					}
				}
			}

			timeSeries.addOrUpdate(min, ctr);
		}
	}
}
