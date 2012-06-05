package controller;
import java.awt.Color;

import model.Model;
import view.View;

/**
* Glowna klasa kontrolera, ktora bedzie jako skladowe zawierac w sobie wszystkie klasy kontrolera
*/ 
public class Controller
{

	public static WelcomeFrameEvent welcomeFrameEvent = new WelcomeFrameEvent();
	public static MainFrameEvent mainFrameEvent = new MainFrameEvent();
	public static SettingsFrameEvent settingsFrameEvent = new SettingsFrameEvent();
	public static ColorFrameEvent colorFrameEvent = new ColorFrameEvent();
	public static ExportFrameEvent exportFrameEvent = new ExportFrameEvent();
	public static PanWatek panWatek;
	//public static LoginWatek loginWatek = new LoginWatek(); -- nie korzystamy
	
	public Model model;
	public View view;
	
	/**
	 * Konstruktor klasy Controller.
	 * Inicjalizuje niektore sk³adowe statyczne klas, bed¹cych skladowymi klasy Controller
	 * @param _model referencja do g³ównej klasy warstwy danych "Model"
	 * @param _view referencja do g³ównej klasy warstwy danych "View"
	 */ 	
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
