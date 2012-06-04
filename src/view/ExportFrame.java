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
* Klasa odpowiadajaca za eksportowanie danych z programu.
*/ 
public class ExportFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	public JPanel panel_0, panel_1, panel_2, panel_3, panel_4, panel_5, panel_6;
	public JLabel kolor, alarm, kasowanie, dni;
	public JRadioButton opcja_1, opcja_2, opcja_3, opcja_4;
	public JRadioButton dzien_1, dzien_2, dzien_3;
	public JRadioButton zaz_1, zaz_2, zaz_3;
	public ButtonGroup group;
	public JButton export, cancel;
	public int location_x, location_y;
	public JSlider slider;
	public static Model model;
	//public AboutUsFrameEvent aboutUsFrameEvent;
	
	/**
	* Konstruktor klasy odpowiadajacy za utworzenie okna eksportowania
	* @param tytul tytul wyswietlany na gorze okna
	* @param SizeX szerokosc okna
	* @param SizeY wysokosc okna
	* @param x przesuniecie x okna od lewej krawedzi ekranu
	* @param y przesuniecie y okna od gornej krawedzi ekranu
	*/ 
	public ExportFrame(String tytul, int SizeX, int SizeY, int x, int y) 
    {  
        super(tytul);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);  
        setSize(SizeX, SizeY);
        setLocation(x,y);
        setVisible(true);
        setResizable(false);
        location_x = x;
        location_y = y;
        
        
        // Górna czêœæ
        setLayout(new GridLayout(2,1));
        panel_0 = new JPanel(); panel_0.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel_1 = new JPanel(); panel_1.setLayout(new FlowLayout());
        add(panel_0);
        add(panel_1);
        
        panel_0.add(new JLabel("Export events to: "));
        panel_0.setBackground(Color.LIGHT_GRAY);
       		opcja_1 = new JRadioButton("XML");
       		opcja_1.setActionCommand("XML");
       		opcja_1.setBackground(Color.LIGHT_GRAY);
       		opcja_1.addActionListener(Controller.exportFrameEvent);
       		opcja_1.setSelected(true);
       	panel_0.add(opcja_1);
       		opcja_2 = new JRadioButton("Evolution CSV");
       		opcja_2.setActionCommand("Evolution CSV");
       		opcja_2.setBackground(Color.LIGHT_GRAY);
       		opcja_2.addActionListener(Controller.exportFrameEvent);
        panel_0.add(opcja_2);
        group = new ButtonGroup();
    	group.add(opcja_1);
    	group.add(opcja_2);
        panel_0.add(new JLabel(" format."));
        	
        export = new JButton("Export");
        cancel = new JButton("Cancel");
        panel_1.add(export);
        panel_1.add(cancel);
        export.addActionListener(Controller.exportFrameEvent);
        cancel.addActionListener(Controller.exportFrameEvent);

        pack();
        
    }
}