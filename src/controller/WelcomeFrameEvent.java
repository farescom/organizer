package controller;
import java.awt.EventQueue;
import java.awt.event.*;

import view.AboutUsFrame;
import view.MainFrame;
import view.View;
import model.Model;

/**
* Klasa zewnetrzna obslugujaca wszystkie zdarzenia zaistniale w oknie powitalnym
*/ 
public class WelcomeFrameEvent implements ActionListener{

	public static View view;
	public static Model model;
	
	public WelcomeFrameEvent(){}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object source = e.getSource();  
        if(source == view.welcomeFrame.register)  
        {
        	if(view.welcomeFrame.registerFrame.login == null){
        		view.welcomeFrame.registerFrame = view.welcomeFrame.new WelcomeInternalFrame("Register");
        		view.welcomeFrame.add(view.welcomeFrame.registerFrame);
        	}
        	if(!view.welcomeFrame.registerFrame.isShowing()) view.welcomeFrame.registerFrame.show();
        }
        else if (source == view.welcomeFrame.login)  
        {
        	if(view.welcomeFrame.loginFrame.login == null){
        		view.welcomeFrame.loginFrame = view.welcomeFrame.new WelcomeInternalFrame("Login");
            	view.welcomeFrame.add(view.welcomeFrame.loginFrame);
        	}
        	if(!view.welcomeFrame.loginFrame.isShowing()) view.welcomeFrame.loginFrame.show();
        }
        else if (source == view.welcomeFrame.about)  
        {
        	// O programie
        	 if (view.aboutUsFrame == null)
                 view.aboutUsFrame = new AboutUsFrame("O nas...", 560, 370,
                   view.welcomeFrame.location_x + (view.welcomeFrame.size().width/2) - (560/2),
                   view.welcomeFrame.location_y + (view.welcomeFrame.size().height/2) - (370/2) + 20);
             if (!view.aboutUsFrame.isShowing()) view.aboutUsFrame.show();
        }
        else if (source == view.welcomeFrame.exit)  
        {
        	System.exit(0);
        }
        else if (source == view.welcomeFrame.registerFrame.submitB)  
        {
        	if(model.baza.czy_polaczono == true)
        	{

            	view.welcomeFrame.registerFlags = model.baza.register(view.welcomeFrame.registerFrame.loginT.getText(), view.welcomeFrame.registerFrame.passwordT.getPassword());
            	if(view.welcomeFrame.registerFlags == 0){
            		view.welcomeFrame.registerFrame.show_result("Musisz wypelnic wszsytkie pola!");
            	}
            	if(view.welcomeFrame.registerFlags == 1){
            		view.welcomeFrame.registerFrame.show_result("Zostales zarejestrowany! Mozesz sie zalogowac!");
            	}
            	else if(view.welcomeFrame.registerFlags == 2){
            		view.welcomeFrame.registerFrame.show_result("Uzytkownik o podanej nazwie juz istnieje!");
            	}
        	}
        	else{
        		view.welcomeFrame.registerFrame.show_result("Brak polaczenia z baza danych!");
        	}
        	
        }
        else if (source == view.welcomeFrame.loginFrame.submitB)  
        {
        	if(model.baza.czy_polaczono == true)
        	{
        		view.welcomeFrame.loginFlags = model.baza.login(view.welcomeFrame.loginFrame.loginT.getText(), view.welcomeFrame.loginFrame.passwordT.getPassword());
        		
        		if(view.welcomeFrame.loginFlags == 1){
            		view.welcomeFrame.dispose();
            		
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
            		//model.baza.get("SELECT * FROM "+model.baza.table, model.zdarzenia);
            	}
            	else if(view.welcomeFrame.loginFlags == 0){
            		view.welcomeFrame.loginFrame.show_result("Musisz wypelnic wszsytkie pola!");
            	}
            	else if(view.welcomeFrame.loginFlags == 2){
            		view.welcomeFrame.loginFrame.show_result("Nieudana próba logowania!");
            	}
        	}
        	else{
        		view.welcomeFrame.registerFrame.show_result("Brak polaczenia z baza danych!");
        	}
        	
        }
        else if (source == view.welcomeFrame.registerFrame.exitB)  
        {
        	view.welcomeFrame.registerFrame.dispose();
        }
        else if (source == view.welcomeFrame.loginFrame.exitB)  
        {
        	view.welcomeFrame.loginFrame.dispose();
        }
	}
	
}
