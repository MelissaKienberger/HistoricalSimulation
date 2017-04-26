import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.BasicStroke; 
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.data.xy.XYDataset; 
import org.jfree.data.xy.XYSeries; 
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities;

import jxl.read.biff.BiffException;

import org.jfree.chart.plot.XYPlot; 
import org.jfree.chart.ChartFactory; 
import org.jfree.chart.plot.PlotOrientation; 
import org.jfree.data.xy.XYSeriesCollection; 
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import java.util.Calendar;




public class ownXYSeries extends @XYSeries() {

public Calendar calendar;
public Double value;

public ownSeries(Calendar calendar, Double value){

		this.calendar=calendar;
		this.value=value;


}
public String toString()
{
	return "(" + calendar +", " + value + ")";
}

}