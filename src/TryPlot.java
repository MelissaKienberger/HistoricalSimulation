//This class plots the computed VaR's but without date on x-axis but with its index (still problem with timeSeriesChart)
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class TryPlot extends JFrame {
	private static final long serialVersionUID = 1L; 
	
	
	//ArrayList<Double> test = new ArrayList<Double>();
			
	public TryPlot( ArrayList<Double> listOfVars) throws NumberFormatException, IOException{
		
		//this.test=listOfVars;
		//ChartPanel chartpanel1 = new ChartPanel(createChart(createDataset(listOfVars)));
		this.add(new ChartPanel(createChart(createDataset(listOfVars))));
		
	}
	
	 public XYSeriesCollection createDataset( ArrayList<Double> test) {
	        final XYSeries series = new XYSeries("VaR");
	        try {
	           
	                for(int i=0; i < test.size(); i++){
	                	
	                //adds date and values in (x,y)-style to the series
	                
	                series.add(i, test.get(i)); 
	            }
	        }catch (NumberFormatException  e  ) {
	            e.printStackTrace(System.err);
	        }
	        return new XYSeriesCollection(series);
	    }
	 
	 public JFreeChart createChart(XYDataset dataset)
		        throws NumberFormatException, IOException {
		        JFreeChart chart = ChartFactory.createTimeSeriesChart(
		            "Value at Risk",    //title
		            "Index",											  // label of x-axis
		            "Value at Risk",									  // label of y-axis
		            dataset,										// data on which chart is based
		            true, true, false);
		        
		        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        return chart;
		        //  Adapt plot, find out how!!!
	 }
}


