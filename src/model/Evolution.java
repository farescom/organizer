package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Evolution
{
	File plik;
	String nazwa = "zdarzenia";
	String rozszerzenie = "txt";
	FileWriter zapis;
	public Evolution()
	{
	}
	
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
