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
import java.util.Calendar;

import org.jfree.chart.plot.XYPlot; 
import org.jfree.chart.ChartFactory; 
import org.jfree.chart.plot.PlotOrientation; 
import org.jfree.data.xy.XYSeriesCollection; 
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;






public class XYLineChart_AWT extends ApplicationFrame{ 

	
   public XYLineChart_AWT( String applicationTitle, String chartTitle, ArrayList<Entry> dataset)
   {
      super(applicationTitle);
      JFreeChart xylineChart = ChartFactory.createXYLineChart(             //.createXYLineChart
         chartTitle ,
         "x-axis: date" ,
         "y-axis: value" ,
         owncreateDataset(dataset) ,
         PlotOrientation.VERTICAL ,
         true , true , false);
         
      ChartPanel chartPanel = new ChartPanel( xylineChart );
      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
      final XYPlot plot = xylineChart.getXYPlot( );
      XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
      renderer.setSeriesPaint( 0 , Color.RED );
      renderer.setSeriesPaint( 1 , Color.GREEN );
     
      renderer.setSeriesStroke( 0 , new BasicStroke( 4.0f ) );
      renderer.setSeriesStroke( 1 , new BasicStroke( 3.0f ) );
    
      plot.setRenderer( renderer ); 
      setContentPane( chartPanel ); 
   }
   
   
 /*methode die calendar auf x Achse akzeptiert
   
   public Calendar acceptdate(){
	   
	   Calendar acceptdate;
	   return acceptdate;
   }*/
   
   public XYDataset owncreateDataset (ArrayList<Entry> dataset){   
	   
	// lege neue serien an
	final XYSeries interestRate1 = new XYSeries("Interest Rate 1");

	// füge werte hinzu
	for (int i = 0 ; i < dataset.size(); i++)
	{
		
		interestRate1.add(i, dataset.get(i).value);
	// interestRate1.add(dataset.get(i).date, dataset.get(i).value);
	}
	//dataset.get(i).getvalue()
	// füge alle serein zu einer sammlugn zusammen
	final XYSeriesCollection mycollection = new XYSeriesCollection();
	
	mycollection.addSeries(interestRate1);
	
	return mycollection;


	}
   
   //hier alter code:
//   public XYDataset createDataset() 
//   {
//      final XYSeries interestRate = new XYSeries( "Interest Rate" );  
//      for (int i=0; i < input.size(); i++) 
    	  
//      {
          //  auf x Achse soll das Datum des inputs stehen
          // auf y Achse die Werte des Zins
          
          //input.get(i).date.getdate();
//          interestRate.add(i, input.get(i).value );
/*      }
                
     // interestRate.add( 2.0 , 4.0 );          
     
      
      final XYSeries valueAtRisk = new XYSeries( "valueAtRisk" );          
      valueAtRisk.add( 1.0 , 4.0 );          
      valueAtRisk.add( 2.0 , 5.0 );          
      valueAtRisk.add( 3.0 , 6.0 );          
             
      final XYSeriesCollection dataset = new XYSeriesCollection( );          
      dataset.addSeries( interestRate );          
      dataset.addSeries( valueAtRisk );          
    
          return dataset;
   }*/
      
	public static void main(String[] args) throws IOException, BiffException
	{
		// TODO Auto-generated method stub
		InputManager input = new InputManager("C:\\Users\\Melissa\\Documents\\Melissa\\UNI\\BA\\EUR Interest Rates (up to 2016) Excel97-03.xls");
		
		
		XYLineChart_AWT chart = new XYLineChart_AWT("Comparison of VaR with Real Interest Rate", "Simulated VaR vs. actual interest rate");
	      chart.pack( );          
	      RefineryUtilities.centerFrameOnScreen( chart );          
	      chart.setVisible( true ); 
		
		
		
	
	}
	


  
}

