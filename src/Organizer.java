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
		
	    /*
		ArrayList <Zdarzenie> zdarzenia = new ArrayList <Zdarzenie>();
		for (int i = 0; i < 3; i++)
		{
			zdarzenia.add(new Zdarzenie(i, "Opis Zdarzenia nr "+i, 1,
					"2012-05-12 12:00:00", "2012-05-12 12:00:00", "polska", 0, 0));
		}
		
		Evolution evo = new Evolution();
		evo.zapiszCSV("pliczek", zdarzenia);
		*/
	    

	//Przyklad wykorzystania XML
	    /*
	    ArrayList <Zdarzenie> zdarzenia = new ArrayList <Zdarzenie>();
	    ArrayList <Zdarzenie> zdarzenia2 = new ArrayList <Zdarzenie>();
		for (int i = 0; i < 3; i++)
		{
			zdarzenia.add(new Zdarzenie(i, "Opis Zdarzenia nr "+i, 1,
					"2012-05-12 12:00:00", "2012-05-12 12:00:00", "polska", 0, 0));
		}
	
		String plik = "plik.xml";
		XML.toXML(zdarzenia, plik);
	
		XML.fromXML(plik, zdarzenia2);//55
		*/
	    AudioClip clip;
		File f;
		URL soundURL = null;
		f = new File ("src/resources/alarm8.wav");
		try
		{
			soundURL = f.toURI().toURL();
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		clip = java.applet.Applet.newAudioClip(soundURL);
		clip.play();
	    
	}
}