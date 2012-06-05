package view;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Model;

/**
* Klasa reprezentuj¹ca ramkê "O nas", w której s¹ wyœwietlane informacje o twórcach programu.
*/ 
public class AboutUsFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	public ImagePanel zdjecie;
	JPanel panel_gorny, panel_dolny, panel_1, panel_2, panel_3, panel_4;
	ImagePanel zdjecie_1, zdjecie_2;
	Button button1, button2, button3, button4;
	JLabel tekst, tworcy;
	public static Model model;
	

	/**
	* Konstruktor klasy odpowiadajacy za utworzenie okna "O nas"
	* @param tytul tytul wyswietlany na gorze okna
	* @param SizeX szerokosc okna
	* @param SizeY wysokosc okna
	* @param x przesuniecie x okna od lewej krawedzi ekranu
	* @param y przesuniecie y okna od gornej krawedzi ekranu
	*/ 
	public AboutUsFrame(String tytul, int SizeX, int SizeY, int x, int y) 
    {  
        super(tytul);
        //welcomeFrameEvent = new WelcomeFrameEvent(this);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);  
        setSize(SizeX, SizeY);
        setLocation(x,y);
        setResizable(false);
        setVisible(true);
        
        // Górna czêœæ
        setLayout(new GridLayout(2,1));
        	panel_gorny = new JPanel();
        	panel_gorny.setLayout(new FlowLayout());
        	tekst = new JLabel();
        	tworcy = new JLabel();
        	
        	tekst.setText("<html><br>SLIMTOM Organizer" +
        			"<br>Program zosta³ zrealizowany w ramach zajêæ laboratoryjnych z przedmiotu \"Programowanie" +
        			"<br>Komponentowe\".     G³ównym celem programu jest pomoc u¿ytkownikowi w organizacji czasu" +
        			"<br>przeznaczonego na wykonanie ró¿nych zadañ w odpowiednim czasie.   Dzia³a on na podobnej" +
        			"<br>zasadzie jak Evolution, czy Outlook. Podstawow¹ zalet¹ programu jest struktura programu" +
        			"<br>w której warstwa danych jest umiejscowiona na zdalnym serwerze, dziêki czemu u¿ytkownik" +
        			"<br>ma dostêp do aplikacji z ka¿dego miejsca na Œwiecie.");
        	tworcy.setText("<html><br>Tomasz Adrianowski" +
        					   "<br>tomaszadrianowski@gmail.com" +
        					   "<br>tel. 796 343 178" +
        					   "<br>" +
        					   "<br>Sliman Jakub El-Fara" +
        					   "<br>jakub_fara@hotmail.com" +
        					   "<br>tel. 796 343 178" +
        					   "</html>");
        	
        	panel_gorny.add(tekst);
        add(panel_gorny);
        
        	panel_dolny = new JPanel();
        	panel_dolny.setLayout(new GridLayout(1,3));
        		panel_1 = new ImagePanel(20, 134, 138, "tomasz_adrianowski.jpg");
        		panel_2 = new JPanel();
        			panel_2.add(tworcy);
        		panel_3 = new ImagePanel(20, 134, 138, "jakub_elfara.jpg");
        		
        		
        		panel_dolny.add(panel_1);
        			//zdjecie_1 = new ImagePanel("jakub_elfara.jpg", 10, 20, 200);
        			//panel_1.add(zdjecie_1);
        		//	button1 = new Button("1");
        		//	panel_1.add(button1);
        		panel_dolny.add(panel_2);
        		//	panel_2.add(new Button("2"));
        		panel_dolny.add(panel_3);
        		//	panel_3.add(new Button("3"));
        add(panel_dolny);
    }
}