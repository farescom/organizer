import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import model.Evolution;
import model.Model;
import model.XML;
import model.Zdarzenie;
import controller.Controller;
import view.View;

/*
 * Problemy
 * - Konwertowanie dat
 */

/**
 * G³ówna klasa programu
 * @author Tomasz Adrianowski
 * @author Sliman Jakub El-Fara 
 */
public class Organizer
{
	static Model      model;
    static View       view;
    static Controller controller;
    
	/**
	 * G³ówna metoda klasy Organizer
	 * @param args tablica argumentów wywo³ania programu
	 */
    
	public static void main(String[] args)
	{
		model      = new Model();
	    view       = new View(model);
	    controller = new Controller(model, view);
	}
}