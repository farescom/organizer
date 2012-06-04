package view;

import model.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import java.awt.Button;

/**
* Klasa reprezentuj�ca ramk� "post�pu", w kt�rej wy�wietlane s� informacje o procentowej
* ilo�ci wczytanych danych programu do pami�ci.
*/ 
public class ProgressFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	JPanel panel;
	public JProgressBar progressBar;
	public JLabel tekst;
	public int procent = 0;
	public Button but;
	
	public static Model model;
	
	/**
	* Konstruktor klasy odpowiadajacy za utworzenie okna wy�wietlaj�cego informacje o 
	* uruchamianym programie (w postaci procentu).
	* @param tytul tytul wyswietlany na gorze okna
	* @param SizeX szerokosc okna
	* @param SizeY wysokosc okna
	* @param x przesuniecie x okna od lewej krawedzi ekranu
	* @param y przesuniecie y okna od gornej krawedzi ekranu
	*/ 
	public ProgressFrame(String tytul, int SizeX, int SizeY, int x, int y)
    {  
        super(tytul);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        setSize(SizeX, SizeY);
        setLocation(x,y);  
        setResizable(false);
        setVisible(true);
        
        // Tworzenie panelu
        panel = new JPanel();
                
        // Tworzenie tekstu
        tekst = new JLabel("Trwa �adowanie programu... ("+procent+"%)");
        panel.add(tekst);
                
        // Tworzenie progressBar
        progressBar = new JProgressBar();
        progressBar.setMaximum(100);
        progressBar.setValue(0);
        panel.add(progressBar);
        add(panel);
    }
}