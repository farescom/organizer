package controller;

// Do zrobienia
// - wlaczenie tylko jednej muzyki (jak jakas juz jest, to nie wlaczaj drugiej)
// - dobranie fajnej melodii

import java.util.ArrayList;
import java.util.Date;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import model.Zdarzenie;

public class PanWatek extends Thread
{
	String nazwa;
	int minuty;
	ArrayList<Zdarzenie> zdarzenia;
	AudioClip clip;
	File f;
	URL soundURL = null;
	
	public PanWatek(String nazwa, int minuty, ArrayList<Zdarzenie> zdarzenia)
	{
		this.nazwa = nazwa;
		this.minuty = minuty;
		this.zdarzenia = zdarzenia;
	}
	public void run()
	{
		f = new File ("src/resources/alarm.wav");
		try
		{
			soundURL = f.toURI().toURL();
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		clip = java.applet.Applet.newAudioClip(soundURL);
		
	  while(true)
	  {
		
		try {Thread.sleep(1000*60);}				// sprawdzanie co minute
		catch (InterruptedException e1) { e1.printStackTrace();}
		
		for (int i = 0; i <zdarzenia.size(); i++)   // Dla kazdego zdarzenia
		{
			// 1 - aktualna data
			Date data_teraz = new Date();
			
			// 2 - data danego zdarzenia
			Date data_zdarzenia = new Date();
			String stringowa_data = zdarzenia.get(i).data_rozpoczecia;
			int rok = Integer.parseInt(stringowa_data.substring(0, 4));
			int miesiac = Integer.parseInt(stringowa_data.substring(5, 7));
			int dzien = Integer.parseInt(stringowa_data.substring(8, 10));
			int godzina = Integer.parseInt(stringowa_data.substring(11, 13));
			int minuta = Integer.parseInt(stringowa_data.substring(14, 16));
			data_zdarzenia.setYear(rok-1900);
			data_zdarzenia.setMonth(miesiac-1);
			data_zdarzenia.setDate(dzien);
			data_zdarzenia.setHours(godzina);
			data_zdarzenia.setMinutes(minuta);
			
			// roznica mierzona w minutach od aktualnego czasu do zdarzenia
			long roznica = ((data_zdarzenia.getTime()/60000)-(data_teraz.getTime()/60000));
			
			if (roznica == zdarzenia.get(i).waznosc)
			{
				// Alarm glosowy
				//System.out.println("-----------------");
				clip.play();
			}

			// Komunikaty
			//System.out.println(roznica);
			//System.out.println(stringowa_data);
			//System.out.println(rok + "-" + miesiac + "-" + dzien + " " + godzina + ":" + minuta);
		}
	  }
	}
}
