import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RefineryUtilities;

import jxl.read.biff.BiffException;


public class HistoricalSimulation {
	//chooses scenario (absolute changes)
	public static double getScenario(double alpha, double deltas[], int c)
	{
		int index = (int)((1.0 - alpha) * c);
		//System.out.println("index des sortierten deltas: " +index);
		return deltas[index];
	}

	//computes VaR based on absolute changes
	public static ArrayList<Double> getValueAtRisk(double alpha, int c, ArrayList<Entry> input)

	{
		//values at risk to calculate
		ArrayList<Double> valueAtRisk = new ArrayList<Double>();
		//System.out.println("Inputsize: " + input.size());
		//run over all history data
		for(int i = 0; i < input.size() - c-1; i++)
			//for(int i=0; i < 30; i++)
		{
			// stores all local difference
			double[] deltas = new double[c];

			// calculate all c differences and add them on the last day
			for(int j = 0; j < c; j++)
			{

				deltas [j]= input.get(i +c ).value + (input.get(i + j + 1).value - input.get(i + j).value); //j-th entry in vector, vector as array list 
				//System.out.println("i=" +i +", delta["+j+"]=" +deltas [j]);

			}
			// sort deltas
			Arrays.sort(deltas);
			//prints the sorted vector of deltas:
			//System.out.println(Arrays.toString(deltas)); 


			//take value at risk with confidence niveau alpha

			valueAtRisk.add(getScenario(alpha, deltas, c));
			//System.out.println("at index " + (i + c) + " entspricht Tag"+ (i+c+1) +" berechnet ich VaR für Tag " +(i+c+2) + "also index"+ (i+c+1)+  ": " + valueAtRisk.get(i));
			//System.out.println("größe der Value at risk liste: " +valueAtRisk.size());

		}

		return valueAtRisk;
	}
	
	//Expected Shortfall for absolute Changes
	public static ArrayList<Entry> absoluteES(double alpha, int c, ArrayList<Entry> input)

	{
		//values at risk to calculate
		ArrayList<Entry> expectedShortFall = new ArrayList<Entry>();
		double es =0;
		//run over all history data
		//for(int i = 0; i < input.size() - c-1; i++)
			for(int i=0; i < 20; i++)
		{
			// stores all local difference
			double[] deltas = new double[c];

			// calculate all c differences and add them on the last day
			for(int j = 0; j < c; j++)
			{

				deltas [j]= input.get(i +c ).value + (input.get(i + j + 1).value - input.get(i + j).value); //j-th entry in vector, vector as array list 
				//System.out.println("i=" +i +", delta["+j+"]=" +deltas [j]);

			}
			// sort deltas
			Arrays.sort(deltas);
			//prints the sorted vector of deltas:
			//System.out.println(Arrays.toString(deltas)); 
			
			double computedVar = getScenario(alpha, deltas,c);
			//System.out.println("VaR: "+computedVar);
			//prints the sorted vector of deltas:
			//System.out.println("Deltas "Arrays.toString(deltas));
			//sums over the values that are smaller than Value at risk
			double sumOfValuesSmallerThanVar=0;
			int counter =0;
			
			for(int j=0; j<c; j++){
				if(deltas[j] < computedVar){
					counter +=1;
					sumOfValuesSmallerThanVar += deltas[j];
				}
			 es = sumOfValuesSmallerThanVar/counter;
			 
				
			}
			//System.out.println("Sum of values smaller than Var "+ sumOfValuesSmallerThanVar);


			//take value at risk with confidence niveau alpha

			expectedShortFall.add(new Entry (input.get(i+c+1).date, es));
			//System.out.println("at index " + (i + c) + " entspricht Tag"+ (i+c+1) +" berechnet ich VaR für Tag " +(i+c+2) + "also index"+ (i+c+1)+  ": " + valueAtRisk.get(i));
			//System.out.println("größe der Value at risk liste: " +valueAtRisk.size());

		}

		return expectedShortFall;
	}


	/**
	 * @param args
	 */
	//count exceptions: if real interest falls under computed VaR over whole time period
	public static int exceptionCounter (double alpha, int c,  ArrayList<Entry> input, ArrayList<Double> getValueAtRisk){

		int j=0;

		for(int i=0; i < getValueAtRisk.size(); i++) {					//last index in list should also be compared with its real value
			if(input.get(i+c+1).value < getValueAtRisk.get(i))   //if real value is smaller than the value at risk: count as exception
			{

				j=j+1;							
			}
			else
			{
				j=j;
			}
			// System.out.println("index des inputs ist " +(i+c) + "wird verglichen mit index VaR ist" + i);
			// System.out.println("Tatsächlicher Wert: "+ input.get(i+c).value + "vs. VaR: " + getValueAtRisk.get(i));

		}

		System.out.println("Ausnahmen: " + j);

		return j;
	}



	//computes expected shortfall over the whole time period (all dates in input), not only for a period c
	public static double backTesting(int c, int counter, ArrayList<Entry> input, ArrayList<Double> getValueAtRisk){

		double sum =0;
		//double j=0;
		for(int i=0; i < getValueAtRisk.size(); i++){
			if(input.get(i+c+1).value < getValueAtRisk.get(i))
			{ //j=j+1; // should be equal to counter, number for monitoring
				sum += getValueAtRisk.get(i); //überprüfen ob das stimmt

			}
		}

		double backTesting = (1.0 / counter)*sum;

		System.out.println("Rechnung 1/"+counter +" * " +sum +"=" +backTesting);

		return backTesting; 
	}


	//count exceptions within a period of w days    W is an arbitrary time period for comparing computed data with real data  within this period
	// c is the period that is used for computing VaR
	public static ArrayList<Double> backtestingForW (int c, int w,  ArrayList<Entry> input, ArrayList<Double> getValueAtRisk){
		ArrayList<Double> backtesting = new ArrayList<Double>();
		int m=0;
		double sum = 0;
		double backtestingForPeriod=0;
		for(int i=0; i < getValueAtRisk.size()-w; i++){ //time frame moves every day 1 day
			m=0;
			sum=0;
			for(int j=0; j<w; j++){ // time frame is of size w

				if(input.get(i+c+1+j).value < getValueAtRisk.get(i+j)) {
					m=m+1; // counts exceptions

					sum += getValueAtRisk.get(i+j); // sums over the exception's values
					backtestingForPeriod = sum / m;  // expected shortfall for the time frame w
					backtesting.add(backtestingForPeriod);
					//System.out.println("Bei der Periode " +i +", Ausnahmen gleich " + m + ", Summe " + sum + ", ES "+expectedShortfallForPeriod);

				}


			}
		}
		return backtesting; // returns an ArrayList with the Expected Shortfall values

	}


	// Computing Relative Changes and VaR starts

	//gets scenario (based on relative changes
	public static double getScenarioRel(double alpha, double deltas[], int c)
	{
		int index = (int)((1.0 - alpha) * c);
		//System.out.println("index des sortierten deltas: " +index);
		return deltas[index];
	}

	//computes VaR based on relative changes
	public static ArrayList<Double> getRelativeValueAtRisk(double alpha, int c, ArrayList<Entry> input)
	{
		//values at risk to calculate
		ArrayList<Double> valueAtRisk = new ArrayList<Double>();

		//run over all history data
		for(int i = 0; i < input.size() - c-1; i++)
			//for(int i=0; i < 30; i++)
		{
			// stores all local relative changes multiplied to the current value
			double[] deltas = new double[c];

			// calculate all c relative changes
			for(int j = 0; j < c; j++)
			{
				//computes the current value multiplied with rel. change	
				deltas [j]= input.get(i + c).value*(1.0 +(input.get(i + j + 1).value - input.get(i + j).value)/input.get(i + j).value); //j-th entry in vector, vector as array list 
				//System.out.println("i=" +i +", delta["+j+"]=" +deltas [j]);

			}
			// sort deltas
			Arrays.sort(deltas);
			//prints the sorted vector of deltas:
			//System.out.println(Arrays.toString(deltas)); 


			//take value at risk with confidence niveau alpha

			valueAtRisk.add(getScenarioRel(alpha, deltas, c));
			//System.out.println("at index " + (i + c) + " entspricht Tag"+ (i+c+1) +" berechnet ich VaR für Tag " +(i+c+2) + "also index"+ (i+c+1)+  ": " + valueAtRisk.get(i));

		}

		return valueAtRisk;
	}   //Relative Changes for Value at Risk completed here
	
	
	//Expected Shortfall for Relative Changes
	
	public static ArrayList<Entry> relativeES(double alpha, int c, ArrayList<Entry> input){
		
		ArrayList<Entry> expectedShortFall = new ArrayList<Entry>();
		double es=0;
		//run over all history data
				for(int i = 0; i < input.size() - c-1; i++)
					//for(int i=0; i < 20; i++)
				{
					// stores all local relative changes multiplied to the current value
					double[] deltas = new double[c];

					// calculate all c relative changes
					for(int j = 0; j < c; j++)
					{
						//computes the current value multiplied with rel. change	
						deltas [j]= input.get(i + c).value*(1.0 +(input.get(i + j + 1).value - input.get(i + j).value)/input.get(i + j).value); //j-th entry in vector, vector as array list 
						//System.out.println("i=" +i +", delta["+j+"]=" +deltas [j]);

					}
					// sort deltas
					Arrays.sort(deltas);
					double computedVar = getScenarioRel(alpha, deltas,c);
					//System.out.println("VaR: "+computedVar);
					//prints the sorted vector of deltas:
					//System.out.println(Arrays.toString(deltas));
					//sums over the values that are smaller than Value at risk
					double sumOfValuesSmallerThanVar=0;
					int counter =0;
					
					for(int j=0; j<c; j++){
						if(deltas[j] < computedVar){
							counter +=1;
							sumOfValuesSmallerThanVar += deltas[j];
						}
					 es = sumOfValuesSmallerThanVar/counter;
					 
						
					}
					//System.out.println("Sum of values smaller than VaR: "+ sumOfValuesSmallerThanVar + ", Anzahl "+ counter);
					
					//expectedShortFall.add(new Entry(input.get(i+c+1).date, es));

					//System.out.println("Datum und ES "+ expectedShortFall);

					

				}

				return expectedShortFall;
	}


	
	// combine Date and VaR in an ArrayList<Entry>	
	
	public static ArrayList<Entry> listWithDate (int c, ArrayList<Entry> input, ArrayList<Double> listOfVars){
		ArrayList<Entry> listWithDate = new ArrayList<Entry>();
	
		for(int i = 0; i < listOfVars.size(); i++){
			
			listWithDate.add(new Entry(input.get(i+c+1).date, listOfVars.get(i))); 
			//System.out.println("Inhalt listWithDate " + listWithDate.get(i));
		
		}
	
		return listWithDate;	
	}
	
	
	// hier plotten, VaR incl. date and value
	public JFreeChart picture(ArrayList<Entry> abs, ArrayList<Entry>rel, ArrayList<Entry> input){
		TimeSeries absVar = new TimeSeries("VaR based on abs. changes");
		TimeSeries relVar = new TimeSeries("Var based on rel. changes");
		TimeSeries availableData = new TimeSeries("LIBOR/OIS");

		for ( int i=0; i < abs.size(); i++) {

			absVar.add(new org.jfree.data.time.Day (abs.get(i).date.getTime()), abs.get(i).value);
			relVar.add(new org.jfree.data.time.Day (rel.get(i).date.getTime()), rel.get(i).value);
			availableData.add(new org.jfree.data.time.Day (input.get(i).date.getTime()), input.get(i).value);
		}
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(absVar);
		dataset.addSeries(relVar);
		dataset.addSeries(availableData);

		final JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Title",
				"Date", // x-axis Label
				"Value", // y-axis Label
				dataset, // Dataset
//				PlotOrientation.VERTICAL, // Plot Orientation
				true, // Show Legend
				true, // Use tooltips
				false // Configure chart to generate URLs?
				);
		return chart;
	};



	public static void main(String[] args) throws IOException, BiffException
	{
		// TODO Auto-generated method stub

		//reads in the excel list
		InputManager input = new InputManager("C:\\Users\\Melissa\\Documents\\Melissa\\UNI\\BA\\EUR Interest Rates (up to 2016) Excel97-03.xls");

		//stores the excel data in the arrayList called data
		ArrayList<Entry> data = input.readDataFromExcel(); 


		double confidenceLevel = 0.5; //sets confidence Level at which VaR is computed
		int period = 10;  // sets period of days for which VaR is computed, e.g. VaR based on last ten days

		ArrayList<Double> atRisk = getValueAtRisk(confidenceLevel, period, data); //gives an ArrayList with the computed values for Value of Risk based on absolute changes
		ArrayList<Double> VaROnRelChanges = getRelativeValueAtRisk(confidenceLevel, period, data); // gives an ArrayList with computed values for VaR based on rel. changes
		
		//combines the two different computed VaR's with the corresponding date
		ArrayList<Entry> absVarWithDate = listWithDate(period, data, atRisk); //list with entries of form (date, VaR on abs. changes)
		ArrayList<Entry> relVarWithDate = listWithDate(period, data, VaROnRelChanges);//list with entries( date, VaR on rel. changes)

		// computes ES in arrayList with form (date, ES)
		ArrayList<Entry> relativeExpectedShortfall = relativeES(confidenceLevel, period, data);
		ArrayList<Entry> AbsoluteExpectedShortfall = absoluteES(confidenceLevel, period, data);
		
		int counter = exceptionCounter(confidenceLevel, period, data, atRisk); // counts exceptions over the whole dataset
		//System.out.println("Ausnahme" + counter);		
		double getBackTesting = backTesting(period, counter, data, atRisk);  // period, number of exceptions, input data and value at risk
		//System.out.println("backTesting is " + getBackTesting);							// computes ES over whole dataset


		ArrayList w = backtestingForW(period, 10, data, atRisk); //computes ES for an arbitrary period of w days
		//System.out.println("For period is " + w.toString());
		
		
		HistoricalSimulation histSim = new HistoricalSimulation();
		ChartPanel panel = new ChartPanel(histSim.picture(absVarWithDate,relVarWithDate , data));//		
		final String panelzoom = panel.ZOOM_IN_DOMAIN_COMMAND;
		JFrame frame = new JFrame();
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		


//		final TryPlot demo = new TryPlot(atRisk);
		final TryPlot demo = new TryPlot(atRisk);
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);

		
	}

}