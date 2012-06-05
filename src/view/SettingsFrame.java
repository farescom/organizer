package view;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JSlider;
import controller.Controller;
import java.awt.Color;
import model.Model;

/**
* Klasa reprezentuj¹ca ramkê "Ustawien".
* W tej ramce uzytkownik moze zmienic wiele ustawien zwi¹zanych z trybem dzia³ania programu.
* W przypadku wyboru opcji "zapisz" wszystkie zmiany s¹ zachowywane w aplikacji i w zdalnej
* bazie danych.
*/ 
public class SettingsFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	public JPanel panel_0, panel_1, panel_2, panel_3, panel_4, panel_5, panel_6;
	public JLabel kolor, alarm, kasowanie, dni;
	public JRadioButton opcja_1, opcja_2, opcja_3, opcja_4;
	public JRadioButton dzien_1, dzien_2, dzien_3;
	public JRadioButton zaz_1, zaz_2, zaz_3;
	public ButtonGroup group;
	public JButton zapisz, anuluj;
	public int location_x, location_y;
	public JSlider slider;
	public static Model model;
	
	/**
	* Konstruktor klasy odpowiadajacy za utworzenie okna
	* @param tytul tytul wyswietlany na gorze okna
	* @param SizeX szerokosc okna
	* @param SizeY wysokosc okna
	* @param x przesuniecie x okna od lewej krawedzi ekranu
	* @param y przesuniecie y okna od gornej krawedzi ekranu
	*/ 
	public SettingsFrame(String tytul, int SizeX, int SizeY, int x, int y) 
    {  
        super(tytul);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);  
        setSize(SizeX, SizeY);
        setLocation(x,y);
        setVisible(true);
        setResizable(false);
        location_x = x;
        location_y = y;
        
        // Górna czêsc
        setLayout(new GridLayout(7,1));
        	panel_0 = new JPanel(); panel_0.setLayout(new FlowLayout(FlowLayout.LEFT));
        	panel_1 = new JPanel(); panel_1.setLayout(new FlowLayout(FlowLayout.LEFT));
        	panel_2 = new JPanel(); panel_2.setLayout(new FlowLayout(FlowLayout.LEFT));
        	panel_3 = new JPanel(); panel_3.setLayout(new FlowLayout(FlowLayout.LEFT));
        	panel_4 = new JPanel(); panel_4.setLayout(new FlowLayout(FlowLayout.LEFT));
        	panel_5 = new JPanel(); panel_5.setLayout(new FlowLayout(FlowLayout.LEFT));
        	panel_6 = new JPanel(); panel_6.setLayout(new FlowLayout());
        add(panel_0);
        add(panel_1);
        add(panel_2);
        add(panel_3);
        add(panel_4);
        add(panel_5);
        add(panel_6);
        
        panel_0.add(new JLabel("Ustawienia kolorów"));
        panel_0.setBackground(Color.LIGHT_GRAY);
        
        kolor = new JLabel("   Weekendy:             ");
        panel_1.add(kolor);
        	opcja_1 = new JRadioButton("Domyslny");  		opcja_1.setActionCommand("Domyslny");
        	opcja_2 = new JRadioButton("Bia³y");			opcja_2.setActionCommand("Bia³y");
        	opcja_3 = new JRadioButton("Jasno niebieski");	opcja_3.setActionCommand("Jasno niebieski");
        	opcja_4 = new JRadioButton("inny (z palety)");				opcja_4.setActionCommand("inny");
        	group = new ButtonGroup();
        	group.add(opcja_1);		group.add(opcja_2);		group.add(opcja_3);		group.add(opcja_4);
        	panel_1.add(opcja_1);
        	panel_1.add(opcja_2);
        	panel_1.add(opcja_3);
        	panel_1.add(opcja_4);
        	opcja_1.addActionListener(Controller.settingsFrameEvent);
        	opcja_2.addActionListener(Controller.settingsFrameEvent);
        	opcja_3.addActionListener(Controller.settingsFrameEvent);
        	opcja_4.addActionListener(Controller.settingsFrameEvent);
        
        	if(model.baza.kolor_1 == 1) opcja_1.setSelected(true);
        	else if(model.baza.kolor_1 == 2) opcja_2.setSelected(true);
        	else if(model.baza.kolor_1 == 3) opcja_3.setSelected(true);
        	else if(model.baza.kolor_1 == 4) opcja_4.setSelected(true);
        	
        panel_2.add(new JLabel("   Aktualny dzien:      "));
        	dzien_1 = new JRadioButton("Domyslny");  		dzien_1.setActionCommand("Domyslny");
        	dzien_1.setSelected(true);
        	dzien_2 = new JRadioButton("Bia³y");			dzien_2.setActionCommand("Bia³y");
        	dzien_3 = new JRadioButton("Jasno niebieski");	dzien_3.setActionCommand("Jasno niebieski");
        	group = new ButtonGroup();
        	group.add(dzien_1);		group.add(dzien_2);		group.add(dzien_3);
        	panel_2.add(dzien_1);
        	panel_2.add(dzien_2);
        	panel_2.add(dzien_3);
        	dzien_1.addActionListener(Controller.settingsFrameEvent);
        	dzien_2.addActionListener(Controller.settingsFrameEvent);
        	dzien_3.addActionListener(Controller.settingsFrameEvent);
    	
        	if(model.baza.kolor_2 == 1) dzien_1.setSelected(true);
        	else if(model.baza.kolor_2 == 2) dzien_2.setSelected(true);
        	else if(model.baza.kolor_2 == 3) dzien_3.setSelected(true);
        	
        panel_3.add(new JLabel("   Zaznaczony dzien: "));
        	zaz_1 = new JRadioButton("Domyslny");  			zaz_1.setActionCommand("Domyslny");
        	
        	zaz_2 = new JRadioButton("Bia³y");				zaz_2.setActionCommand("Bia³y");
        	zaz_3 = new JRadioButton("Jasno niebieski");	zaz_3.setActionCommand("Jasno niebieski");
        	group = new ButtonGroup();
        	group.add(zaz_1);		group.add(zaz_2);		group.add(zaz_3);
        	panel_3.add(zaz_1);
        	panel_3.add(zaz_2);
        	panel_3.add(zaz_3);
        	zaz_1.addActionListener(Controller.settingsFrameEvent);
        	zaz_2.addActionListener(Controller.settingsFrameEvent);
        	zaz_3.addActionListener(Controller.settingsFrameEvent);
        	
        	if(model.baza.kolor_3 == 1) zaz_1.setSelected(true);
        	else if(model.baza.kolor_3 == 2) zaz_2.setSelected(true);
        	else if(model.baza.kolor_3 == 3) zaz_3.setSelected(true);
        	
        panel_4.add(new JLabel("Automatyczne usuwanie wiadomosci"));
        panel_4.setBackground(Color.LIGHT_GRAY);
        
        // Automatyczne usuwanie
        panel_5.add(new JLabel("   Usuwaj zdarzenia po "));
        	// splitpane
            System.out.println(model.baza.dni_waznosci);
        	slider = new JSlider(0, 12, model.baza.dni_waznosci);
        	slider.addChangeListener(Controller.settingsFrameEvent);
        	panel_5.add(slider);
        	// tekst
        	dni = new JLabel(model.baza.dni_waznosci+" miesi¹cu.");
        	panel_5.add(dni);
	
        zapisz = new JButton("Zapisz");
        anuluj = new JButton("Anuluj");
        panel_6.add(zapisz);
        panel_6.add(anuluj);
        zapisz.addActionListener(Controller.settingsFrameEvent);
        anuluj.addActionListener(Controller.settingsFrameEvent);
        pack();
    }
}