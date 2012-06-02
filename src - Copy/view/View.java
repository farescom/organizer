package view;
import java.awt.EventQueue;
import java.awt.Color;
import model.Model;

/**
* Glowna klasa widoku, ktora bedzie jako skladowe zawierac w sobie wszystkie klasy widoku
*/ 
public class View {

// Parametry koloru
 public static Color kolorWeekendu = Color.LIGHT_GRAY;
 public static Color kolorDnia = new Color(100, 250, 100);
 public static Color kolorWybranegoDnia = new Color(250, 100, 100);
 public static String[] months =  {"", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	
 public ImagePanel imagePanel;
 public static MainFrame mainFrame;
 public WelcomeFrame welcomeFrame;
 public ProgressFrame progressFrame;
 public AboutUsFrame aboutUsFrame;
 public SettingsFrame settingsFrame;
 public ColorFrame colorFrame;
 public ExportFrame exportFrame;
 public static Calendar calendar;
 public static CalendarFrame calendarFrame;
 
 public Model model;
 
 public View(Model _model)
 {
  this.model = _model;
  
  ImagePanel.model = _model;
  MainFrame.model = _model;
  WelcomeFrame.model = _model;
  ProgressFrame.model = _model;
  AboutUsFrame.model = _model;
  SettingsFrame.model = _model;
  ColorFrame.model = _model;
  CalendarFrame.model = _model;
  Calendar.model = _model;
  CalendarFrame.model = _model;
  
  // WELCOME FRAME
  EventQueue.invokeLater( new Runnable()
  {  
            public void run()
            {  
             int SizeX_WelcomeFrame = 720;
             int SizeY_WelcomeFrame = 420;
             
             int SizeX_ProgressFrame = 300;
             int SizeY_ProgressFrame = 80;
             
             welcomeFrame = new WelcomeFrame("Witaj", SizeX_WelcomeFrame, SizeY_WelcomeFrame, 300, 100);
             welcomeFrame.setResizable(false);

             progressFrame = new ProgressFrame("Logowanie...", SizeX_ProgressFrame, SizeY_ProgressFrame,
               300+(SizeX_WelcomeFrame/2)-(SizeX_ProgressFrame/2), 100+(SizeY_WelcomeFrame/2)-(SizeY_ProgressFrame/2));
             progressFrame.setResizable(false);
             
             //model.baza.delete(1);
            }  
        });
  
  // MAIN FRAME
  EventQueue.invokeLater( new Runnable()
  {  
            public void run()
            {  
            	int SizeX_WelcomeFrame = 720;
            	int SizeY_WelcomeFrame = 420;
            	
            	int SizeX_ProgressFrame = 300;
            	int SizeY_ProgressFrame = 80;
            	
            	mainFrame = new MainFrame("Kalendarz", SizeX_WelcomeFrame, SizeY_WelcomeFrame, 300, 100);
            	mainFrame.setResizable(false);
            	mainFrame.setResizable(false);
            }  
        });
 }
 
}
