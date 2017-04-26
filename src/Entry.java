import java.util.Calendar;

public class Entry
{
	public Calendar date;
	public double value;
	
	
	public Entry(){};
	
	public Entry(Calendar date, double value)
	{
		this.date = date;
		this.value = value;
	}
	
	Calendar getdate(){
		return date;
	}
	
	public double getvalue(){
		return value;
	}
	
	
	@Override
	public String toString()
	{
		return "(" + date.getTime() + ", " + value + ")";
	}
	
	public double justvalue(){
		double x;
		
		x= value;
		
		
		return  x;
		
	}
}