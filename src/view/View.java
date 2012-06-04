package view;
import java.awt.EventQueue;
import java.awt.Color;
import model.Model;

/**
* Glowna klasa widoku, odpowiedzialna miêdzy innymi za tworzenie obiektów klas warstwy interfejsu
* u¿ytkownika.
* Klasa zawiera w sobie wszystkie klasy widoku
*/ 
public class View
{

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
 
 /**
 * Kontruktor klasy widoki.
 * Tworzy obiekty sk³adowe warstwy interfejsu u¿ytkownika i wyœwietla g³ówne okno aplikacji.
 * @param _model referencja do g³ównej klasy warstwy danych "Model"
 */ 
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
             
             progressFrame = new ProgressFrame("Uruchamianie...", SizeX_ProgressFrame, SizeY_ProgressFrame,
               300+(SizeX_WelcomeFrame/2)-(SizeX_ProgressFrame/2), 100+(SizeY_WelcomeFrame/2)-(SizeY_ProgressFrame/2));
             progressFrame.setResizable(false);
            }  
   });
  
  int licznik = 7;
  float procent = 0;
  for (int i = 0; i < licznik; i++)
  {
	  try {Thread.sleep(500);}
	  catch (InterruptedException e1) { e1.printStackTrace();}
	  procent += 100/licznik;
	  if (procent == 98) procent = 100;
	  progressFrame.tekst.setText("Trwa ³adowanie programu... ("+procent+"%)");
	  progressFrame.progressBar.setValue((int) procent);
  }
  try {Thread.sleep(500);} catch (InterruptedException e1) { e1.printStackTrace();}
  progressFrame.hide();
  
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
            }  
  });
 }
 
}
