package view;

import model.*;
import controller.*;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
* Klasa odpowiadajaca za utworzenie ramki, za pomoc¹ której mo¿na dokonaæ wyboru koloru z palety.
*/ 
public class ColorFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	public JColorChooser tcc;
	public JPanel panel;
	public JButton zatwierdz, anuluj;
	public static Model model;
	
	/**
	* Konstruktor klasy odpowiadajacy za utworzenie okna wyboru koloru z palety
	* @param tytul tytul wyswietlany na gorze okna
	* @param SizeX szerokosc okna
	* @param SizeY wysokosc okna
	* @param x przesuniecie x okna od lewej krawedzi ekranu
	* @param y przesuniecie y okna od gornej krawedzi ekranu
	*/ 
	public ColorFrame(String tytul, int SizeX, int SizeY, int x, int y)
    {  
        super(tytul);  
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);  
        setSize(SizeX, SizeY);
        setLocation(x,y);  
        setResizable(false);
        setVisible(true);

        panel = new JPanel();
        	// Tworzenie palety wyboru koloru
        	tcc = new JColorChooser(panel.getForeground());   //tcc.setBackground(Color.red);
        	zatwierdz = new JButton("ZatwierdŸ");
        	anuluj = new JButton("Anuluj");
        	panel.add(tcc);
        	panel.add(zatwierdz);
        	panel.add(anuluj);
        add(panel);
        zatwierdz.addActionListener(Controller.colorFrameEvent);
        anuluj.addActionListener(Controller.colorFrameEvent);
    } 
}
