package view;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Model;

public class CalendarFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public CalendarProgram calendarProgram;
	public JPanel calendar;
	
	public static Model model;
	
	public CalendarFrame(String tytul, int SizeX, int SizeY, int x, int y, MainFrame mainFrame)
    {  
        super(tytul);  
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);  
        setSize(SizeX, SizeY);
        setLocation(x,y);  
        setResizable(false);
        setVisible(true);
        
        calendarProgram = new CalendarProgram(mainFrame);
		calendar = calendarProgram.createProgram();
		
		add(calendar);
    } 
	
}
