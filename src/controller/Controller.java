package controller;
import java.awt.Color;

import model.Model;
import view.View;

/**
* Glowna klasa kontrolera, ktora bedzie jako skladowe zawierac w sobie wszystkie klasy kontrolera
*/ 
public class Controller {

	public static WelcomeFrameEvent welcomeFrameEvent = new WelcomeFrameEvent();
	public static MainFrameEvent mainFrameEvent = new MainFrameEvent();
	public static SettingsFrameEvent settingsFrameEvent = new SettingsFrameEvent();
	public static ColorFrameEvent colorFrameEvent = new ColorFrameEvent();
	public static ExportFrameEvent exportFrameEvent = new ExportFrameEvent();
	public static PanWatek panWatek;
	
	public Model model;
	public View view;
	
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
	
	public Controller(Model _model, View _view)
	{
		this.model = _model;
		this.view = _view;
		
		MainFrameEvent.model = _model;
		MainFrameEvent.view = _view;
		
		WelcomeFrameEvent.model = _model;
		WelcomeFrameEvent.view = _view;
		
		SettingsFrameEvent.model = _model;
		SettingsFrameEvent.view = _view;
		
		ColorFrameEvent.model = _model;
		ColorFrameEvent.view = _view;
		
		ExportFrameEvent.model = _model;
		ExportFrameEvent.view = _view;
		
		PanWatek.model = _model;
		PanWatek.view = _view;
	}
	
}
