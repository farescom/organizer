package model;

import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
* Glowna klasa modelu, ktora bedzie jako skladowe zawierac w sobie wszystkie klasy modelu
*/ 
public class Model {

	// Wybrany dzien na kalendarzu
	private static GregorianCalendar cal = new GregorianCalendar(); //Create calendar
	public static int currentDay = cal.get(GregorianCalendar.DAY_OF_MONTH);
	public static int currentMonth = cal.get(GregorianCalendar.MONTH);
	public static int currentYear = cal.get(GregorianCalendar.YEAR);
	
	// Wybrany dzien na kalendarzu
	public static int checkedDay;
	public static int checkedMonth;
	public static int checkedYear;
	
	
	public Database baza;
	public XML xml;
	public static ArrayList<Zdarzenie> zdarzenia = new ArrayList<Zdarzenie>();
	public MainFrameModel mainFrame;
	
	public Model()
	{	
		baza = new Database();
		baza.czy_polaczono = baza.connection();
		mainFrame = new MainFrameModel();
		
		if(baza.czy_polaczono == true)
		{
		}
	}
	
}
