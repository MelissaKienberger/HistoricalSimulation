//This class plots the Libor data out of an txt-file
// Code from: http://stackoverflow.com/questions/37946507/how-to-import-and-plot-csv-in-java-with-timestamp



import java.awt.BasicStroke;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities; 


public class Test extends ApplicationFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;    // now Test is a serialVersionUID

	public Test(String applicationTitle) throws IOException {
        super(applicationTitle);       
       // this.add(new ChartPanel(createChart(createDataset()))); //class: org.jfree.chart.ChartPanel, Type Component
     ChartPanel chartpanel1 = new ChartPanel(createChart(createDataset()));
     
      XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
     renderer.setSeriesPaint( 0 , Color.RED );
     renderer.setSeriesPaint( 1 , Color.GREEN );
    
     renderer.setSeriesStroke( 0 , new BasicStroke( 4.0f ) );
     renderer.setSeriesStroke( 1 , new BasicStroke( 3.0f ) );
     
     final XYPlot plot = createChart(createDataset()).getXYPlot( );
     plot.setRenderer( renderer ); 
     setContentPane( chartpanel1 ); 
    }

	  
	 
    public XYSeriesCollection createDataset() {
        final XYSeries series = new XYSeries("LIBOR");
        try {
            BufferedReader in = new BufferedReader(new FileReader("C:/Users/Melissa/Documents/Melissa/UNI/BA/LIBORalstxt.txt"));           
            SimpleDateFormat f = new SimpleDateFormat("dd.MM.yy");  
          
            String s = null;
            while ((s = in.readLine()) != null) {
            	//System.out.println(s);
            	
            	//separate String at ";" and build a vector of Strings
                String[] a = s.split(";");  
                
                //first entry in vector, a[0], is a date
               Date d = f.parse(a[0]); 
               
               //second entry is a double, namely values of LIBOR curve
               String number = a[1];
                double y = Double.parseDouble(number.replace(',', '.'));
                
                //adds date and values in (x,y)-style to the series
                
                series.add(d.getTime(), y); 
            }
        } catch (IOException |NumberFormatException | ParseException e  ) {
            e.printStackTrace(System.err);
        }
        return new XYSeriesCollection(series);
    }

    public JFreeChart createChart(XYDataset dataset)
        throws NumberFormatException, IOException {
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
            "1 YEAR EUR SWAP (LIBOR) from 1995 up to 2016",    //title
            "Time",											  // label of x-axis
            "LIBOR in %",									  // label of y-axis
            dataset,										// data on which chart is based
            true, true, false);
        
        
        return chart;
        //  Adapt plot, find out how!!!


    }

    public static void main(String[] args)  throws IOException
    {
        final Test demo = new Test("EUR Interest Rates");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }
}
//TODO: save plot as png, zoom einbauen
