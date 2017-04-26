import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


public class InputManager 
{
    private String inputFile;

    public InputManager(String inputFile)
    {
        this.inputFile = inputFile;
    }
    
    public static ArrayList<Entry> data = new ArrayList<Entry>(); // Prototyp der Funktion
    
   // @SuppressWarnings("deprecation")
	public ArrayList<Entry> readDataFromExcel() throws IOException, BiffException  
    {
        File inputWorkbook = new File(inputFile); // Liest allgemeine Datei ein
        Workbook w;

        w = Workbook.getWorkbook(inputWorkbook); // macht aus allgemeiner Datei eine Excel Datei
            // Get the first sheet


            Sheet sheet = w.getSheet(0);
             // Loop over first 10 column and lines
             //System.out.println(sheet.getColumns() +  " " +sheet.getRows());
            for (int j = 1; j <sheet.getRows(); j++) 
            {
                
            	  	Cell cellDate = sheet.getCell(0,j);

            	  	Cell cellValue = sheet.getCell(1,j);

                    String value = cellValue.getContents();
                    value = value.replace(',', '.');
//                    System.out.println("Value: "+value);
                    
                    Double Value = Double.parseDouble(value);
                    
//                    System.out.println("Zins: "+Value);
                    
                    String s = cellDate.getContents();
//                    System.out.println("Datum: "+s);
                    String [] datum2 = s.split("/");
                    
 //                   System.out.println("Datum: "+datum2);
                    
                    
//                    System.out.println("Tag: "+datum2[0]);
                    
                    int Tag = Integer.parseInt(datum2[0]);
                    int Monat = Integer.parseInt(datum2[1])-1;  //since January starts at 0 and December is month 11
                    int Jahr = Integer.parseInt(datum2[2]);
//                    System.out.println("Tag: "+Tag);
//                    System.out.println("Monat: "+Monat);
//                    System.out.println("Jahr: "+Jahr);
                   Calendar date = new GregorianCalendar(Jahr < 20 ? Jahr+2000 : Jahr + 1900, Monat, Tag);
                  // System.out.println("Date " + date.getTime());
                   //Date myDate = date.getTime();
                   //System.out.println("Datum "+ myDate);

                    
                    data.add(new Entry(date, Value)); // Methode add, welche als Parameter den Wert erwartet, der in die Liste eingefügt werden soll.
                    //System.out.println(cell.getContents());
                
            }


    return data;
    }
    
    
    


}