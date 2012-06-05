package view;

import java.awt.*;
import javax.swing.*;
import model.Model;

/**
* Klasa rozszerzajaca JPanel. Umozliwia utworzenie Panelu wypelnionego zdjeciem "w tle".
* Rysowanie zdjecia odbywa sie poprzez nadpisanie metody paintComponent
*/ 
public class ImagePanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	Image image;
	String nazwa;
	int odstep;
	int SizeX;
	int SizeY;
	
	public static Model model;
	
	/**
	* Konstruktor klasy odpowiadajacy za utworzenie panelu ze zdjeciem
	* @param nazwa nazwa panelu
	* @param odstep odstep zdjecia od krancow panelu
	* @param SizeX szerokosc panelu
	* @param SizeY wysokosc panelu
	*/ 
	ImagePanel(String nazwa, int odstep, int SizeX, int SizeY)
	{
		super();
		this.nazwa = nazwa;
		this.odstep = odstep;
		this.SizeX = SizeX-10-15;
		this.SizeY = SizeY-10-60;
		
		image = Toolkit.getDefaultToolkit().getImage("src/resources/"+nazwa);
	}
	
	/**
	* Konstruktor klasy odpowiadajacy za utworzenie panelu ze zdjeciem
	* @param odstep odstep zdjecia od krancow panelu
	* @param SizeX szerokosc panelu
	* @param SizeY wysokosc panelu
	* @param nazwa nazwa panelu
	*/ 
	ImagePanel(int odstep, int SizeX, int SizeY, String nazwa)
	{
		super();
		this.nazwa = nazwa;
		this.odstep = odstep;
		this.SizeX = SizeX;
		this.SizeY = SizeY;
		
		image = Toolkit.getDefaultToolkit().getImage("src/resources/"+nazwa);
	}

	/**
	* Metoda odpowiedzialna za odrysowywanie komponentu
	* @param g obiekt klasy Graphics, za pomoca ktorego odbywa sie rysowanie w scenie.
	*/ 
	public void paintComponent(Graphics g)
	{
		 g.drawImage(image,odstep,odstep,SizeX,SizeY, this);
	}
}
