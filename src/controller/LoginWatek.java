package controller;

import java.awt.EventQueue;

import model.MainFrameModel;
import model.Model;
import model.XML;
import view.MainFrame;
import view.ProgressFrame;
import view.View;

public class LoginWatek extends Thread
{
	String nazwa;
	public static View view;
	public static Model model;
	
	public LoginWatek()
	{
	}
	
	public void run()
	{
		/**/
    	System.out.println("Progress");
        int SizeX_WelcomeFrame2 = 720;
        int SizeY_WelcomeFrame2 = 420;
        int SizeX_ProgressFrame2 = 300;
        int SizeY_ProgressFrame2 = 80;
		view.progressFrame = new ProgressFrame("Logowanie...", SizeX_ProgressFrame2, SizeY_ProgressFrame2,
                300+(SizeX_WelcomeFrame2/2)-(SizeX_ProgressFrame2/2), 100+(SizeY_WelcomeFrame2/2)-(SizeY_ProgressFrame2/2));
		view.progressFrame.setResizable(false);

    	if(model.baza.czy_polaczono == false){
			model.baza.czy_polaczono = model.baza.connection();
			model.xml = new XML(model.baza);
    	}
    	
    	if(model.baza.czy_polaczono == true)
    	{
    		System.out.println("--- 1");
    		
    		view.welcomeFrame.loginFlags = model.baza.login(view.welcomeFrame.loginFrame.loginT.getText(), view.welcomeFrame.loginFrame.passwordT.getPassword());
    		System.out.println("--- 2");
    		
    		if(view.welcomeFrame.loginFlags == 1){
        		
    				model.baza.get("SELECT * FROM "+model.baza.table+" ORDER BY ID ASC", model.zdarzenia);
    				System.out.println("--- 3");
    				if(model.zdarzenia.size()-1 > 0) model.baza.nextID = model.zdarzenia.get(model.zdarzenia.size()-1).id+1;
    				
    				view.welcomeFrame.dispose();
    				model.mainFrame = new MainFrameModel();
    				
    				System.out.println("--- 4");
    				// Uruchumienie w¹tka alarmów
        			PanWatek panWatek = new PanWatek("Alarmy", 1, model.zdarzenia);
        			panWatek.start();
        			
            		EventQueue.invokeLater( new Runnable()
            		{  
                        public void run()
                        {  
                        	int SizeX_WelcomeFrame = 720;
                        	int SizeY_WelcomeFrame = 420;
                        	
                        	int SizeX_ProgressFrame = 300;
                        	int SizeY_ProgressFrame = 80;
                        	
                        	view.mainFrame = new MainFrame("Kalendarz", SizeX_WelcomeFrame, SizeY_WelcomeFrame, 300, 100);
                        	view.mainFrame.setResizable(false);
                        	view.mainFrame.setResizable(false);
                        }  
                    });
        	}
        	else if(view.welcomeFrame.loginFlags == 0){
        		view.welcomeFrame.loginFrame.show_result("You must fill all fields!");
        	}
        	else if(view.welcomeFrame.loginFlags == 2){
        		view.welcomeFrame.loginFrame.show_result("Access denied!");
        	}
    	}
    	else{
    		view.welcomeFrame.registerFrame.show_result("Connection with database is unavailable! Try again later!");
    	}
	}
}
