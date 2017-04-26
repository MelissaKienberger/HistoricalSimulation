
public class Auto {
	
	private String Farbe;
	private int Türenanzahl;
	private double PS;
	
	public Auto(String F, int T, double PS)
	{
		this.Farbe = F;
		this.Türenanzahl = T;
		this.PS = PS;
		
	}
	
	public String getFarbe()  // Funktion getFarbe gibt mir Farbe 

	{
		return Farbe;
	}
}
