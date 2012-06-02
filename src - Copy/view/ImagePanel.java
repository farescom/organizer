package view;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import model.Model;


public class ImagePanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	Image image;
	String nazwa;
	int odstep;
	int SizeX;
	int SizeY;
	
	public static Model model;
	
	ImagePanel(String nazwa, int odstep, int SizeX, int SizeY)
	{
		super();
		this.nazwa = nazwa;
		this.odstep = odstep;
		this.SizeX = SizeX-10-15;
		this.SizeY = SizeY-10-60;
		
		image = Toolkit.getDefaultToolkit().getImage("src/resources/"+nazwa);
	}
	
	ImagePanel(int odstep, int SizeX, int SizeY, String nazwa)
	{
		super();
		this.nazwa = nazwa;
		this.odstep = odstep;
		this.SizeX = SizeX;
		this.SizeY = SizeY;
		
		image = Toolkit.getDefaultToolkit().getImage("src/resources/"+nazwa);
	}

	public void paintComponent(Graphics g)
	{
		 g.drawImage(image,odstep,odstep,SizeX,SizeY, this);
	}
}
