package controller;

import java.awt.event.*;
import java.awt.Color;

import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import view.*;
import model.*;

/**
* Klasa zewnetrzna obslugujaca wszystkie zdarzenia pochodz¹ce z okna SettingsFrame
*/ 
public class SettingsFrameEvent implements ActionListener, ChangeListener
{
 public static View view;
 public static Model model;
 public Controller controller;
 
 /**
 * Kontruktor domyœlny klasy.
 */ 
 public SettingsFrameEvent(){}
 //public SettingsFrameEvent(Controller controller){this.controller = controller;}

   /**
	* Metoda odpowiadajaca za obs³ugê zdarzeñ wywo³anych za pomoc¹ przycisków
	* @param e - objekt klasy ActionEvent umo¿liwiaj¹cy dostêp do konkretnego zdarzenia
	*/
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();  
        if(source == view.settingsFrame.opcja_1)  		controller.kolor_dni_wolnych = 1;
        else if(source == view.settingsFrame.opcja_2)  	controller.kolor_dni_wolnych = 2;
        else if(source == view.settingsFrame.opcja_3)  	controller.kolor_dni_wolnych = 3;
        else if(source == view.settingsFrame.opcja_4)  
        {
    	  	// Paleta kolorów
    	  	if (view.colorFrame == null)
    	  		view.colorFrame = new ColorFrame("Paleta kolorów", 465, 420,
    	  				view.settingsFrame.location_x + (view.settingsFrame.size().width/2) - (465/2),
    	  				view.settingsFrame.location_y + (view.settingsFrame.size().height/2) - (420/2) + 20);
    	  	if (!view.colorFrame.isShowing()) view.colorFrame.show();
        }
        else if(source == view.settingsFrame.dzien_1)  controller.kolor_aktualnego_dnia = 1;
        else if(source == view.settingsFrame.dzien_2)  controller.kolor_aktualnego_dnia = 2;
        else if(source == view.settingsFrame.dzien_3)  controller.kolor_aktualnego_dnia = 3;
        
        else if(source == view.settingsFrame.zaz_1)  controller.kolor_zaznaczonego_dnia = 1;
        else if(source == view.settingsFrame.zaz_2)  controller.kolor_zaznaczonego_dnia = 2;
        else if(source == view.settingsFrame.zaz_3)  controller.kolor_zaznaczonego_dnia = 3;
        else if (source == view.settingsFrame.zapisz)
        {
        	// dokonanie trwa³ych zmian
        	// 1- w programie
        	if(controller.kolor_dni_wolnych == 1)
        	{
        		controller.kolor_dni_wolnych2 = 1;
            	view.kolorWeekendu = Color.LIGHT_GRAY;
            	view.mainFrame.setBackground(view.kolorWeekendu);
        	}
        	else if(controller.kolor_dni_wolnych == 2)
        	{
        		controller.kolor_dni_wolnych2 = 2;
            	view.kolorWeekendu = Color.white;
            	view.mainFrame.setBackground(view.kolorWeekendu);
        	}
        	else if(controller.kolor_dni_wolnych == 3)
        	{
        		controller.kolor_dni_wolnych2 = 3;
            	view.kolorWeekendu = Color.cyan;
            	view.mainFrame.setBackground(view.kolorWeekendu);
        	}
        	else if(controller.kolor_dni_wolnych == 4)
        	{
        		controller.kolor_dni_wolnych2 = 4;
            	view.kolorWeekendu = controller.inny_kolor;
            	view.mainFrame.setBackground(view.kolorWeekendu);
        	}

        	if(controller.kolor_aktualnego_dnia == 1)
        	{
        		controller.kolor_aktualnego_dnia2 = 1;
            	view.kolorDnia = new Color(100, 250, 100);
            	view.mainFrame.setBackground(view.kolorDnia);
        	}
        	else if(controller.kolor_aktualnego_dnia == 2)
        	{
        		controller.kolor_aktualnego_dnia2 = 2;
            	view.kolorDnia = Color.white;
            	view.mainFrame.setBackground(view.kolorDnia);
        	}
        	else if(controller.kolor_aktualnego_dnia == 3)
        	{
        		controller.kolor_aktualnego_dnia2 = 3;
            	view.kolorDnia = Color.cyan;
            	view.mainFrame.setBackground(view.kolorDnia);
        	}
        	
        	if(controller.kolor_zaznaczonego_dnia == 1)
        	{
        		controller.kolor_zaznaczonego_dnia2 = 1;
            	view.kolorWybranegoDnia = new Color(250, 100, 100);
            	view.mainFrame.setBackground(view.kolorWybranegoDnia);
        	}
        	else if(controller.kolor_zaznaczonego_dnia == 2)
        	{
        		controller.kolor_zaznaczonego_dnia2 = 2;
            	view.kolorWybranegoDnia = Color.white;
            	view.mainFrame.setBackground(view.kolorWybranegoDnia);
        	}
        	else if(controller.kolor_zaznaczonego_dnia == 3)
        	{
        		controller.kolor_zaznaczonego_dnia2 = 3;
            	view.kolorWybranegoDnia = Color.cyan;
            	view.mainFrame.setBackground(view.kolorWybranegoDnia);
        	}
        	// 2 - W bazie
        	switch(model.guest){
			case 0: model.baza.query2("UPDATE `user` SET `waznosc_zdarzen`="+controller.wybrana_liczba_dni+"," +
		        			"`kolorWeekendu`="+controller.kolor_dni_wolnych+"," +
		        			"`kolorDnia`="+controller.kolor_aktualnego_dnia+"," +
		        			"`kolorWybranegoDnia`="+controller.kolor_zaznaczonego_dnia+" " +
		        					"WHERE `ID` = "+model.baza.identyfikator);
		        	controller.dni_waznosci = controller.wybrana_liczba_dni;
		        	view.settingsFrame.hide();
					break;
			case 1: controller.dni_waznosci = controller.wybrana_liczba_dni;
        			view.settingsFrame.hide();
					break;
			}
        }
        else if (source == view.settingsFrame.anuluj)
        {
        	// Przywrocenie ustawien - tylko graficznie
        	
           	if(controller.kolor_dni_wolnych2 == 1) view.settingsFrame.opcja_1.setSelected(true);
        	else if(controller.kolor_dni_wolnych2 == 2) view.settingsFrame.opcja_2.setSelected(true);
        	else if(controller.kolor_dni_wolnych2 == 3) view.settingsFrame.opcja_3.setSelected(true);
        	else if(controller.kolor_dni_wolnych2 == 4) view.settingsFrame.opcja_4.setSelected(true);

        	if(controller.kolor_aktualnego_dnia2 == 1) view.settingsFrame.dzien_1.setSelected(true);
        	else if(controller.kolor_aktualnego_dnia2 == 2) view.settingsFrame.dzien_2.setSelected(true);
        	else if(controller.kolor_aktualnego_dnia2 == 3) view.settingsFrame.dzien_3.setSelected(true);
        	
        	if(controller.kolor_zaznaczonego_dnia2 == 1) view.settingsFrame.zaz_1.setSelected(true);
        	else if(controller.kolor_zaznaczonego_dnia2 == 2) view.settingsFrame.zaz_2.setSelected(true);
        	else if(controller.kolor_zaznaczonego_dnia2 == 3) view.settingsFrame.zaz_3.setSelected(true);
        	
	    	view.settingsFrame.dni.setText(controller.dni_waznosci+" miesi¹cach.");
	    	view.settingsFrame.slider.setValue(controller.dni_waznosci);
        	view.settingsFrame.hide();
        }
	}
	
   /**
	* Metoda odpowiadajaca za obs³ugê zdarzeñ wywo³anych wykonaniem pewnych zmian
	* w komponentach np. przesuniêciem suwaka.
	* @param e - objekt klasy ActionEvent umo¿liwiaj¹cy dostêp do konkretnego zdarzenia
	*/
	public void stateChanged(ChangeEvent e)
	{
	    JSlider source = (JSlider)e.getSource();
	    
	    if (!source.getValueIsAdjusting())   // view.settingsFrame.opcja_1
	    {
	    	controller.wybrana_liczba_dni = (int)source.getValue();
	    	view.settingsFrame.dni.setText(controller.wybrana_liczba_dni+" miesi¹cach.");
	    }
	}	
}