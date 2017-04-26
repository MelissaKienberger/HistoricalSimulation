// Code from: http://stackoverflow.com/questions/37946507/how-to-import-and-plot-csv-in-java-with-timestamp
// Plotting the LIBOR data
// plots
// plots 2 graph in 1 panel
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


public class Testkopie extends ApplicationFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;    // now Test is a serialVersionUID

	public Testkopie(String applicationTitle) throws IOException {
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
    	
    	//LIBOR curve is created
        final XYSeries libor = new XYSeries("LIBOR");
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
                
                libor.add(d.getTime(), y); 
            }
        } catch (IOException |NumberFormatException | ParseException e  ) {
            e.printStackTrace(System.err);
            
            
        }
        
        //Value at Risk curve should be created
        final XYSeries ois= new XYSeries ("OIS");
        try {
            BufferedReader in = new BufferedReader(new FileReader("C:/Users/Melissa/Documents/Melissa/UNI/BA/OISalstxt.txt"));           
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
                
                ois.add(d.getTime(), y); 
            }
        } catch (IOException |NumberFormatException | ParseException e  ) {
            e.printStackTrace(System.err);
         }
        //add both curves to the dataset
        final XYSeriesCollection mycollection = new XYSeriesCollection();
        mycollection.addSeries(libor);
        mycollection.addSeries(ois);
        return mycollection;
        }

    public JFreeChart createChart(XYDataset dataset)
        throws NumberFormatException, IOException {
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
            "1 YEAR EUR SWAP (LIBOR) from 1995 up to 2016",    //title
            "Time",											  // label of x-axis
            "EUR LIBOR and EUR OIS in %",									  // label of y-axis
            dataset,										// data on which chart is based
            true, true, false);
        
        
        return chart;
        //  Adapt plot, find out how!!!


    }

    public static void main(String[] args)  throws IOException
    {
        final Testkopie demo = new Testkopie("EUR Interest Rates");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
             demo.setVisible(true);
    }
}
//TODO: save plot as png, zoom einbauen

