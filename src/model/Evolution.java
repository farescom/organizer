package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Klasa zajmuj¹ca siê konwertowaniem zdarzen do standardowego formatu zgodnego np. z Evolution.
 * format: CSV.
 */
public class Evolution
{
	File plik;
	String nazwa = "zdarzenia";
	String rozszerzenie = "txt";
	FileWriter zapis;
	
	/**
	 * Kontruktor domyœlny
	 */
	public Evolution(){}
	
	/**
	 * Metoda odpowiedzialna za zapisanie kolekcji zdarzen do odpowiedniego formatu CSV
	 * @param nazwa nazwa pliku, do ktorego maja zostac zapisane dane
	 * @param zdarzenia kolekcja zdarzen zawierajaca wszystkie zdarzenia do zapisu
	 */
	public void zapiszCSV(String nazwa, ArrayList <Zdarzenie> zdarzenia)
	{
		this.nazwa = nazwa;
		rozszerzenie = "csv";
		
		plik = new File(this.nazwa+"."+rozszerzenie);
		try
		{
			plik.createNewFile();
			zapis = new FileWriter(plik);

		 // Nag³ówek pliku
			zapis.write("\"Subject\",\"Location\",\"Start Date\",\"Start Time\",\"End Date\",\"End Time\",\"Hidden\",\"Categories\",\"Notes\"");
			zapis.write(13);
			zapis.write(10);
			
		 // Zdarzenia
			for (int i = 0; i < zdarzenia.size(); i++)
			{
				String tytul;
				if (zdarzenia.get(i).opis.length() < 20)
					tytul = new String(zdarzenia.get(i).opis);
				else
					tytul = new String(zdarzenia.get(i).opis.substring(0, 20));
				tytul = "\"" + tytul + "...\",";
				
				zapis.write(tytul);
				
				zapis.write("\"" + zdarzenia.get(i).miejsce+ "\",");

				zapis.write("\"" + zdarzenia.get(i).data_rozpoczecia.substring(0, 10)+ "\",");
				
				zapis.write("\"" + zdarzenia.get(i).data_rozpoczecia.substring(11, 16)+ "\",");
				
				zapis.write("\"" + zdarzenia.get(i).data_rozpoczecia.substring(0, 10)+ "\",");
				
				zapis.write("\"" + zdarzenia.get(i).data_rozpoczecia.substring(11, 16)+ "\",");
				
				zapis.write("\"False\",");
				
				zapis.write("\"\",");
				
				zapis.write("\""+ zdarzenia.get(i).opis +"\"");
				
				zapis.write(13);
				zapis.write(10);
			}
			zapis.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Metoda odpowiedzialna za zapisanie kolekcji zdarzen do odpowiedniego formatu CSV
	 * @param plik obiekt klasy File zawierajacy m.in. sciezke do miejsca, gdzie maja zostac
	 * zapisane dane z kolekcji zdarzenia.
	 * @param zdarzenia kolekcja zdarzen zawierajaca wszystkie zdarzenia do zapisu
	 */
	public void zapiszCSV(File plik, ArrayList <Zdarzenie> zdarzenia)
	{
		try
		{
			plik.createNewFile();
			zapis = new FileWriter(plik);

		 // Nag³ówek pliku
			zapis.write("\"Subject\",\"Location\",\"Start Date\",\"Start Time\",\"End Date\",\"End Time\",\"Hidden\",\"Categories\",\"Notes\"");
			zapis.write(13);
			zapis.write(10);
			
		 // Zdarzenia
			for (int i = 0; i < zdarzenia.size(); i++)
			{
				String tytul;
				if (zdarzenia.get(i).opis.length() < 20)
					tytul = new String(zdarzenia.get(i).opis);
				else
					tytul = new String(zdarzenia.get(i).opis.substring(0, 20));
				tytul = "\"" + tytul + "...\",";
				
				zapis.write(tytul);
				
				zapis.write("\"" + zdarzenia.get(i).miejsce+ "\",");

				zapis.write("\"" + zdarzenia.get(i).data_rozpoczecia.substring(0, 10)+ "\",");
				
				zapis.write("\"" + zdarzenia.get(i).data_rozpoczecia.substring(11, 16)+ "\",");
				
				zapis.write("\"" + zdarzenia.get(i).data_rozpoczecia.substring(0, 10)+ "\",");
				
				zapis.write("\"" + zdarzenia.get(i).data_rozpoczecia.substring(11, 16)+ "\",");
				
				zapis.write("\"False\",");
				
				zapis.write("\"\",");
				
				zapis.write("\""+ zdarzenia.get(i).opis +"\"");
				
				zapis.write(13);
				zapis.write(10);
			}
			zapis.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
}
