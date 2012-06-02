package model;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import controller.PanWatek;

/**
* Glowna klasa modelu, ktora bedzie jako skladowe zawierac w sobie wszystkie klasy modelu
*/ 
public class Model {

	// Wybrany dzien na kalendarzu
	private static GregorianCalendar cal = new GregorianCalendar(); //Create calendar
	public static int currentDay = cal.get(GregorianCalendar.DAY_OF_MONTH);
	public static int currentMonth = cal.get(GregorianCalendar.MONTH)+1;
	public static int currentYear = cal.get(GregorianCalendar.YEAR);
	
	// Wybrany dzien na kalendarzu
	public static int checkedDay = currentDay;
	public static int checkedMonth = currentMonth;
	public static int checkedYear = currentYear;
	
	
	public Database baza;
	public XML xml;
	public static ArrayList<Zdarzenie> zdarzenia = new ArrayList<Zdarzenie>();
	public MainFrameModel mainFrame;
	
	public Model()
	{	
		baza = new Database();
		baza.czy_polaczono = baza.connection();
		
		if(baza.czy_polaczono == true)
		{
			baza.get("SELECT * FROM "+baza.table+" ORDER BY ID ASC", zdarzenia);
			baza.update(299, "asd", 1, "2012-02-02", "2012-02-02", "asd", 20, 1);
			
			if(zdarzenia.size()-1 > 0) baza.nextID = zdarzenia.get(zdarzenia.size()-1).id+1;
			mainFrame = new MainFrameModel();
			// Trzeba to przeniesc tam, gdzie jest logowanie
			PanWatek panWatek = new PanWatek("Alarmy", 1, zdarzenia);
			panWatek.start();
		}
		else
		{
			// dzialamy w trybie bez netu
		}
	}
}
