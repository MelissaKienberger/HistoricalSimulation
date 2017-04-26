import java.util.ArrayList;
import java.util.Arrays;


public class RelativeChanges {
	
	ArrayList<Entry> input = new ArrayList<Entry>();
	public RelativeChanges(double alpha, int c, ArrayList<Entry> in){
		
		input = in;
		getRelativeValueAtRisk(alpha,c, input);
		
		
		
	}
	
	// Computing Relative Changes and VaR starts
	public static double getScenarioRel(double alpha, double deltas[], int c)
	{
		int index = (int)((1.0 - alpha) * c);
		//System.out.println("index des sortierten deltas: " +index);
		return deltas[index];
	}
	
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
			deltas [j]= input.get(i + c).value*((input.get(i + j + 1).value - input.get(i + j).value)/input.get(i + j).value); //j-th entry in vector, vector as array list 
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
	

}
