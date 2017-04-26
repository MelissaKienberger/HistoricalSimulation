//This class combines the computed Value at Risks with the corresponding date for which it is computed
//This should be plotted with (date, VaR) date on x-axis and VaR on y-axis

import java.util.ArrayList;



public class VaRwithDate {
	
	ArrayList<Entry> listWithDate = new ArrayList<Entry>();
	
	public VaRwithDate(int c, ArrayList<Entry> input, ArrayList<Double> listOfVars){
		
	
		for(int i = 0; i < listOfVars.size(); i++){
			
			listWithDate.add(new Entry(input.get(i+c+1).date, listOfVars.get(i))); 
			//System.out.println("Inhalt listWithDate " + listWithDate.get(i));
		
		}
	
			
	}
	
}
