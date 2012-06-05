package model;
import com.thoughtworks.xstream.XStream;
import java.io.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
* Klasa odpowiedzialna za operowanie pomiêdzy XMLem a Zdarzeniami.
* Zawiera dwie podstawowe metody: konwersji danych z programu do formatu XML i odwrotnie.
*/
public class XML {
	
	public static Database baza;
	
	/**
	* Kontruktor domyslny klasy. Ustawia referencje do bazy danych na ktorej beda wykonywane 
	* operacje konwersji.
	* @param baza referencja do bazy danych.
	*/
	public XML(Database baza)
	{
		this.baza = baza;
	}
	
	/**
	* Metoda zapisuje dane Zdarzenie do pliku XML
	* @param zdarzenie - zdarzenie, ktore ma zostac zapisane do pliku XML
	* @param file_name - nazwa pliku XML, do ktorego ma zostac zapisane zdarzenie
	*/
	public static void toXML(Zdarzenie zdarzenie, String file_name)
	{	
		XStream xStream = new XStream();
		String xml;
		
		xml = xStream.toXML(zdarzenie);
		
		File file = new File(file_name);
		
		try
		{
			file.createNewFile();
			FileWriter streamOutput = new FileWriter(file);
			streamOutput.write(xml);
			streamOutput.close();
		}
		catch (IOException io)
		{
			System.out.println(io.getMessage());
		}
		catch (Exception se)
		{
			System.err.println("blad sec!");
		}
	}
	
	/**
	* Metoda zapisuje kolekcje zdarzen do pliku XML
	* @param zdarzenia - kolekcja zdarzen, ktore maja zostac zapisane do pliku XML
	* @param file_name - nazwa pliku XML, do ktorego ma zostac zapisane zdarzenie
	*/
	public static void toXML(ArrayList <Zdarzenie> zdarzenia, String file_name)
	{	
		XStream xStream = new XStream();
		String xml = "<xml>\n\n";
		
		for (int i = 0; i < zdarzenia.size(); i++)
		xml = xml + xStream.toXML(zdarzenia.get(i))+"\n \n";
		
		xml += "\n\n</xml>";
		
		File file = new File(file_name);
		
		try
		{
			file.createNewFile();
			FileWriter streamOutput = new FileWriter(file);
			streamOutput.write(xml);
			streamOutput.close();
		}
		catch (IOException io)
		{
			System.out.println(io.getMessage());
		}
		catch (Exception se)
		{
			System.err.println("blad sec!");
		}
	}
	
	/**
	* Metoda zapisuje kolekcje zdarzen do pliku XML
	* @param zdarzenia - kolekcja zdarzen, ktore maja zostac zapisane do pliku XML
	* @param file_name - obiekt typu File zawierajacy w sobie m.in. sciezke do miejsca
	* w ktorym ma zostac zapisany plik XML.
	*/
	public static void toXML(ArrayList <Zdarzenie> zdarzenia, File file)
	{	
		XStream xStream = new XStream();
		String xml = "<xml>\n\n";
		
		for (int i = 0; i < zdarzenia.size(); i++)
		xml = xml + xStream.toXML(zdarzenia.get(i))+"\n \n";
		
		xml += "\n\n</xml>";
		
		try
		{
			file.createNewFile();
			FileWriter streamOutput = new FileWriter(file);
			streamOutput.write(xml);
			streamOutput.close();
		}
		catch (IOException io)
		{
			System.out.println(io.getMessage());
		}
		catch (Exception se)
		{
			System.err.println("blad sec!");
		}
	}
	
	
	/**
	* Metoda odczytuje dane Wydarzenie z pliku XML
	* @param file_name - nazwa pliku XML, z ktorego ma zostac wczytane zdarzenie.
	* return referencja do obiektu typu Zdarzenie
	*/
	public static Zdarzenie fromXML(String file_name)
	{	
		XStream xStream = new XStream();
		String xml = "";
		
		try
		{
			FileReader fr = new FileReader(file_name);
			BufferedReader br = new BufferedReader(fr);
			String s;
			
			while((s = br.readLine()) != null)
			{
				xml+=s;
			}
			
			fr.close();
		}
		catch (FileNotFoundException io)
		{
			System.out.println(io.getMessage());
		}
		catch (IOException io)
		{
			System.out.println(io.getMessage());
		}
		return (Zdarzenie) xStream.fromXML(xml);
	}
	
	/**
	* Metoda odczytuje dane Wydarzenie z pliku XML
	* @param file_name - nazwa pliku XML, z ktorego maja zostac wczytane zdarzenia.
	* @param zdarzenia - referencja do kolekcji, do ktorej zostana zapisane wydobyte
	* z pliku XML zdarzenia
	*/
	public static void fromXML(String file_name, ArrayList <Zdarzenie> zdarzenia)
	{	
		XStream xStream = new XStream();
		String xml = "";
		
		try
		{
			FileReader fr = new FileReader(file_name);
			BufferedReader br = new BufferedReader(fr);
			String s;
			
			int licznik_obiektow = 0;
			while((s = br.readLine()) != null)
			{
				if(!s.equals(" "))
				{
					// linie opisujace ten sam obiekt
					   xml+=s;
				}
				else
				{
					zdarzenia.add((Zdarzenie) xStream.fromXML(xml));
					xml="";
					licznik_obiektow++;
				}
			}
			fr.close();
		}
		catch (FileNotFoundException io)
		{
			System.out.println(io.getMessage());
		}
		catch (IOException io)
		{
			System.out.println(io.getMessage());
		}
	}
	
	/**
	* Metoda odczytuje dane Wydarzenie z pliku XML
	* @param file_name - obiekt typu File zawierajacy m.in. sciezke pliku XML,
	* z ktorego maja zostac wczytane zdarzenia.
	* @param zdarzenia - referencja do kolekcji, do ktorej zostana zapisane wydobyte
	* z pliku XML zdarzenia
	*/
	public static void fromXML(File file_name, ArrayList <Zdarzenie> zdarzenia)
	{	
		XStream xStream = new XStream();
		String xml = "";
		
		try
		{
			FileReader fr = new FileReader(file_name);
			BufferedReader br = new BufferedReader(fr);
			String s;
			
			int licznik_obiektow = 0;
			while((s = br.readLine()) != null)
			{
				if(!s.equals(" "))
				{
					// linie opisujace ten sam obiekt
					   if (!s.equals("<xml>"))
						   if (!s.equals("</xml>"))
							   xml+=s;
				}
				else
				{
					Zdarzenie nowe = (Zdarzenie) xStream.fromXML(xml);
					// zmiana identyfikatora
					nowe.id = baza.nextID;
					
					switch(Model.guest){
					case 0: // dodanie do listy w programie
							zdarzenia.add(nowe);
							// dodanie do bazy
							baza.insert(nowe.opis, nowe.czy_okres, nowe.data_rozpoczecia, nowe.data_zakonczenia,
										nowe.miejsce, nowe.waznosc, nowe.rodzaj);
							break;
					case 1: // dodanie do listy w programie
							zdarzenia.add(nowe);
							baza.nextID++;
							break;
	  				}
					xml="";
					licznik_obiektow++;
				}
			}
			fr.close();
		}
		catch (FileNotFoundException io)
		{
			System.out.println(io.getMessage());
		}
		catch (IOException io)
		{
			System.out.println(io.getMessage());
		}
	}
	
}
