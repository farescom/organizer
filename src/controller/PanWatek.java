package controller;

// Do zrobienia
// - wlaczenie tylko jednej muzyki (jak jakas juz jest, to nie wlaczaj drugiej)
// - dobranie fajnej melodii

import java.util.ArrayList;
import java.util.Date;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.EventQueue;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JOptionPane;

import view.MainFrame;
import view.View;
import model.Model;
import model.Zdarzenie;

/**
* Klasa odpowiadajaca za obs³uge watku, ktory bedzie odpowiedzialny za uruchamianie alarmow
*/
public class PanWatek extends Thread
{
	String nazwa;
	int minuty;
	ArrayList<Zdarzenie> zdarzenia;
	AudioClip clip;
	File f;
	URL soundURL = null;
	
	public static View view;
	public static Model model;
	
	/**
	* Konstruktor klasy PanWatek inicjalizujacy swoje sk³adowe
	* @param nazwa - nazwa watku
	* @param minuty - d³ugos trwania alarmu
	* @param zdarzenia - lista zdarzeñ, ktora bedzie sprawdzana w celu uruchomienia alarmu, dla konkretnego zdarzenia
	*/ 
	public PanWatek(String nazwa, int minuty, ArrayList<Zdarzenie> zdarzenia)
	{
		this.nazwa = nazwa;
		this.minuty = minuty;
		this.zdarzenia = zdarzenia;
	}
	
	/**
	* Metoda odpowiadajaca za uruchomienie nowego watku dla klasy PanWatek
	*/
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
		
		try {Thread.sleep(1000*6);}				// sprawdzanie co minute
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
			
			System.out.println(roznica + ": " + model.zdarzenia.get(i).waznosc);
			if (roznica == model.zdarzenia.get(i).waznosc)
			{
				// Alarm dŸwiekowy / Alarm kolorowy
					clip.play();
					
					if(model.alarmInformation == null){
						messageThread(i);
					}
					try
					{
						model.mainFrame.alarmID = i;
						for (int ii = 0; ii < 3; ii++)
						{
							// czerwony kolor
							model.mainFrame.alarmColor = new Color(241, 122, 88);
							model.mainFrame.tableMonth();
							view.mainFrame.refreshTableMonth();
							Thread.sleep(1000*1);
							
							// normalny - czyli bialy
							model.mainFrame.alarmColor = Color.white;
							model.mainFrame.tableMonth();
							view.mainFrame.refreshTableMonth();
							Thread.sleep(1000*1);
						}
						model.mainFrame.alarmID = -1;
					}
					catch (InterruptedException e1) { e1.printStackTrace();}
					clip.stop();
			}

			// Komunikaty
			
			/*System.out.println(roznica);
			System.out.println(stringowa_data);
			System.out.println(rok + "-" + miesiac + "-" + dzien + " " + godzina + ":" + minuta);*/
		}
	  }
	}
	
	/**
	* Metoda odpowiadajaca za wyswietlenie informacji o nadchodzacym zdarzeniu w nowym watku
	* @param i - indeks okreslajacy, dla ktorego zdarzenia z listy zdarzeñ wyswietlane jest przypomnienie
	*/
	public void messageThread(final int i){
		EventQueue.invokeLater( new Runnable()
		{  
            public void run()
            {  
            	model.alarmInformation = new JOptionPane();
				model.alarmInformation.showMessageDialog(null, "Remember!\n Hour: "+model.zdarzenia.get(i).godzina+
						"\n Describe: "+model.zdarzenia.get(i).opis+"\n Place: "+model.zdarzenia.get(i).miejsce);
				model.zdarzenia.get(i).waznosc = -1;
				if(model.guest == 0) model.baza.update(model.zdarzenia.get(i).id, model.zdarzenia.get(i));
				model.alarmInformation = null;
            }  
        });
	}
}
