package model;
import com.thoughtworks.xstream.XStream;
import java.io.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
* Klasa odpowiedzialna za operowanie pomiêdzy XMLem a Wydarzeniem.
*/
public class XML {
	
	/*Przyklad wykorzystania XML
	Zdarzenie zdarzenie = new Zdarzenie(1, "Pi³ka Nozna", 0, new Date(2012, 05, 23), new Date(2012, 05, 23), "£upkowa", 1, 1);
	Zdarzenie zdarzenie_XML;
	
	String plik = "plik.xml";
	XML.toXML(zdarzenie, plik);

	zdarzenie_XML = XML.fromXML(plik);
	System.out.println(zdarzenie_XML.toString());*/
	
	public static Database baza;
	public XML(Database baza)
	{
		this.baza = baza;
	}
	/**
	* Metoda zapisuje dane Wydarzenie do pliku XML
	* @param zdarzenie - Wydarzenie, ktore ma zostac zapisane do pliku XML
	* @param file_name - nazwa pliku XML
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
	* @param file_name - nazwa pliku XML
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
