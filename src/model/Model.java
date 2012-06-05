package model;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;

import controller.PanWatek;

/**
* Glowna klasa modelu, odpowiedzialna mi�dzy innymi za tworzenie obiekt�w klas warstwy danych
* Klasa zawiera w sobie wszystkie klasy modelu
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
	
	public static JOptionPane alarmInformation;
	
	public static int guest = 0;
	public Database baza;
	public XML xml;
	public static ArrayList<Zdarzenie> zdarzenia = new ArrayList<Zdarzenie>();
	public MainFrameModel mainFrame;
	
	/**
	* Kontruktor domy�lny klasy. Tworzy obiekt klasy Database.
	*/ 
	public Model()
	{
		baza = new Database();
	}
}
