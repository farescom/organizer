package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;

import controller.PanWatek;

/**
* Glowna klasa modelu, odpowiedzialna miedzy innymi za tworzenie obiektów klas warstwy danych
* Klasa zawiera w sobie wszystkie klasy modelu
*/ 
public class Model {

	// Aktualny dzien
	private static GregorianCalendar cal = new GregorianCalendar(); //Create calendar
	public static int currentDay = cal.get(GregorianCalendar.DAY_OF_MONTH);
	public static int currentMonth = cal.get(GregorianCalendar.MONTH)+1;
	public static int currentYear = cal.get(GregorianCalendar.YEAR);
	
	// Wybrany dzien na kalendarzu
	public static int checkedDay = currentDay;
	public static int checkedMonth = currentMonth;
	public static int checkedYear = currentYear;
	
	// Parametry koloru
	 public static Color kolorWeekendu = Color.LIGHT_GRAY;
	 public static Color kolorDnia = new Color(100, 250, 100);
	 public static Color kolorWybranegoDnia = new Color(250, 100, 100);
	 public static String[] months =  {"", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	
	// Wazne flagi kontrolera - okreœlaj¹ faktyczne ustawieñ
	public static int dni_waznosci = 1;	// ile dni zdarzenia istnieja, po czym zostana skasowane
	public static int kolor_dni_wolnych2 = 1;
	public static int kolor_aktualnego_dnia2 = 1;
	public static int kolor_zaznaczonego_dnia2 = 1;
		
	// flagi z ktorych korzysta przycisk "zapisz ustawienia"
	public static int kolor_dni_wolnych = 1;
	public static int kolor_aktualnego_dnia = 1;
	public static int kolor_zaznaczonego_dnia = 1;
	public static Color inny_kolor = Color.LIGHT_GRAY;
	public static int wybrana_liczba_dni = 1;
	 
	// Okno informacyjne w momencie uruchomienia alarmu
	public static JOptionPane alarmInformation;
	
	// Podstawowe skladowe wykorzysytwane w programie
	public static int guest = 0;
	public Database baza;
	public XML xml;
	public static ArrayList<Zdarzenie> zdarzenia = new ArrayList<Zdarzenie>();
	public MainFrameModel mainFrame;
	
	/**
	* Kontruktor domyslny klasy. Tworzy obiekt klasy Database.
	*/ 
	public Model()
	{
		baza = new Database();
	}
}
