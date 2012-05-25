package model;
import com.thoughtworks.xstream.XStream;
import java.io.*;

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
	
}
