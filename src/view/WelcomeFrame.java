package view;

import java.awt.*;
import java.awt.event.KeyEvent;

import javax.swing.*;

import model.Model;
import controller.Controller;

/**
* Klasa odpowiadajaca za utworzenie ramki powitalnej wyswietlanej na samym poczatku dzialania
* programu.
*/ 
public class WelcomeFrame extends JFrame
{	
	private static final long serialVersionUID = 1L;
	public ImagePanel zdjecie;
	public JMenuBar menuBar;
	public JMenuItem register, login, about, exit;
	public JMenu menu;
	public WelcomeInternalFrame registerFrame = new WelcomeInternalFrame(), loginFrame = new WelcomeInternalFrame();
	public int loginFlags = 0, registerFlags = 0;
	public int location_x;
	public int location_y;
	public static Model model;
	
	/**
	* Konstruktor klasy WelcomeFrame odpowiedzialny za utworzenie okna powitalnego.
	* @param tytul tytul wyswietlany na gorze okna
	* @param SizeX szerokosc okna
	* @param SizeY wysokosc okna
	* @param x przesuniecie x okna od lewej krawedzi ekranu
	* @param y przesuniecie y okna od gornej krawedzi ekranu
	*/ 
	public WelcomeFrame(String tytul, int SizeX, int SizeY, int x, int y) 
    {  
        super(tytul);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);  
        setSize(SizeX, SizeY);
        location_x = x;
        location_y = y;
        setLocation(x,y);  
        setVisible(true);
        
        // Tworzenie zdjecia              // 25
		zdjecie = new ImagePanel(0, SizeX-6, SizeY-54, "tlo2.png");
		setContentPane(zdjecie);
        
        // Tworzenie menu
         menuBar = new JMenuBar();
         	menu = new JMenu("Plik");
         		register = new JMenuItem("Rejestracja", KeyEvent.VK_F1); 
         		register.addActionListener(Controller.welcomeFrameEvent);
         		login = new JMenuItem("Logowanie", KeyEvent.VK_F2); 
         		login.addActionListener(Controller.welcomeFrameEvent);
         		about = new JMenuItem("O programie", KeyEvent.VK_F3);
         		about.addActionListener(Controller.welcomeFrameEvent);
	         	exit = new JMenuItem("Wyjscie", KeyEvent.VK_F3);
	         	exit.addActionListener(Controller.welcomeFrameEvent);
	   	     menu.add(register);
		     menu.add(login);
		     menu.add(about);
		     menu.add(exit);
         menuBar.add(menu);
	     this.setJMenuBar(menuBar);
    }
	
	/**
	* Klasa wewnetrzna klasy WelcomeFrame
	* Jest odpowiedzialna za utworzenie okna wewnetrznego
	*/ 
	public class WelcomeInternalFrame extends JInternalFrame
	{
		public JLabel login;
		public JLabel password;
		public JLabel result = new JLabel("");
		public JTextField loginT;
		public JPasswordField passwordT;
		public JPanel panel, panel_top, panel_middle, panel_bottom;
		public JButton submitB, exitB, loginAsGuest;
		
		/**
		* Konstruktor domyslny klasy WelcomeInternalFrame.
		*/ 
		public WelcomeInternalFrame(){}
		
		/**
		* Konstruktor klasy WelcomeInternalFrame.
		* @param text parametr okresla tytul okna wewnetrzneog jak rowniez wyswietlany
		* 		 napis na jednym z przyciskow.
		*/ 
		public WelcomeInternalFrame(String text)
    	{
			login = new JLabel("Login: ");
			password = new JLabel("Password: ");
			loginT = new JTextField(20);
			passwordT = new JPasswordField(20);
			panel = new JPanel();
			panel_top = new JPanel();
			panel_middle = new JPanel();
			submitB = new JButton(text);
			if(text=="Login") loginAsGuest = new JButton("Login as Guest");
			exitB = new JButton("Exit");
			
			
			setLayout(new BorderLayout());
			setBounds(200, 200, 500, 500);
    		
			panel.setLayout(new BorderLayout());
			
			panel_top.setLayout(new GridBagLayout());
    		GridBagConstraints c = new GridBagConstraints();
    		c.fill = GridBagConstraints.HORIZONTAL;
    		c.insets = new Insets(2, 2, 2, 2);
    		c.gridx = 0;
    		c.gridy = 0;
    		panel_top.add(login, c);
    		c.gridx = 1;
    		c.gridy = 0;
    		panel_top.add(loginT, c);
    		c.gridx = 0;
    		c.gridy = 1;
    		panel_top.add(password, c);
    		c.gridx = 1;
    		c.gridy = 1;
    		panel_top.add(passwordT, c);
    		panel.add(panel_top, BorderLayout.NORTH);
    		
    		panel_middle.setLayout(new FlowLayout());
    		panel_middle.add(submitB);
    		submitB.addActionListener(Controller.welcomeFrameEvent);
    		if(loginAsGuest != null){
    			panel_middle.add(loginAsGuest);
    			loginAsGuest.addActionListener(Controller.welcomeFrameEvent);
    		}
    		panel_middle.add(exitB);
    		exitB.addActionListener(Controller.welcomeFrameEvent);
    		panel.add(panel_middle, BorderLayout.SOUTH);
    		
    		add(panel, BorderLayout.NORTH);
    		
    		result.setForeground(Color.gray);
    		panel_bottom = new JPanel();
			panel_bottom.setLayout(new FlowLayout());
			panel_bottom.setPreferredSize(new Dimension(100, 20));
			panel_bottom.add(result);
			add(panel_bottom, BorderLayout.SOUTH);
    		
            setTitle(text);
            setVisible(true);
    	}
		
		public void show_result(String text)
		{
			result.setText(text);
		}
	}
	
}