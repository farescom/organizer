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
* Klasa zewnetrzna obslugujaca wszystkie zdarzenia pochodzace z okna SettingsFrame
*/ 
public class SettingsFrameEvent implements ActionListener, ChangeListener
{
 public static View view;
 public static Model model;
 
 /**
 * Kontruktor domyœlny klasy.
 */ 
 public SettingsFrameEvent(){}
 //public SettingsFrameEvent(Controller controller){this.controller = controller;}

   /**
	* Metoda odpowiadajaca za obs³uge zdarzeñ wywo³anych za pomoca przyciskow
	* @param e - objekt klasy ActionEvent umo¿liwiajacy dostep do konkretnego zdarzenia
	*/
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();  
        if(source == view.settingsFrame.opcja_1)  		model.kolor_dni_wolnych = 1;
        else if(source == view.settingsFrame.opcja_2)  	model.kolor_dni_wolnych = 2;
        else if(source == view.settingsFrame.opcja_3)  	model.kolor_dni_wolnych = 3;
        else if(source == view.settingsFrame.opcja_4)  
        {
    	  	// Paleta kolorow
    	  	if (view.colorFrame == null)
    	  		view.colorFrame = new ColorFrame("Pallete of colors", 465, 420,
    	  				view.settingsFrame.location_x + (view.settingsFrame.size().width/2) - (465/2),
    	  				view.settingsFrame.location_y + (view.settingsFrame.size().height/2) - (420/2) + 20);
    	  	if (!view.colorFrame.isShowing()) view.colorFrame.show();
        }
        else if(source == view.settingsFrame.dzien_1)  model.kolor_aktualnego_dnia = 1;
        else if(source == view.settingsFrame.dzien_2)  model.kolor_aktualnego_dnia = 2;
        else if(source == view.settingsFrame.dzien_3)  model.kolor_aktualnego_dnia = 3;
        
        else if(source == view.settingsFrame.zaz_1)  model.kolor_zaznaczonego_dnia = 1;
        else if(source == view.settingsFrame.zaz_2)  model.kolor_zaznaczonego_dnia = 2;
        else if(source == view.settingsFrame.zaz_3)  model.kolor_zaznaczonego_dnia = 3;
        else if (source == view.settingsFrame.zapisz)
        {
        	// dokonanie trwa³ych zmian
        	// 1- w programie
        	if(model.kolor_dni_wolnych == 1)
        	{
        		model.kolor_dni_wolnych2 = 1;
            	model.kolorWeekendu = Color.LIGHT_GRAY;
            	view.mainFrame.setBackground(model.kolorWeekendu);
        	}
        	else if(model.kolor_dni_wolnych == 2)
        	{
        		model.kolor_dni_wolnych2 = 2;
        		model.kolorWeekendu = Color.white;
        		view.mainFrame.setBackground(model.kolorWeekendu);
        	}
        	else if(model.kolor_dni_wolnych == 3)
        	{
        		model.kolor_dni_wolnych2 = 3;
        		model.kolorWeekendu = Color.cyan;
        		view.mainFrame.setBackground(model.kolorWeekendu);
        	}
        	else if(model.kolor_dni_wolnych == 4)
        	{
        		model.kolor_dni_wolnych2 = 4;
        		model.kolorWeekendu = model.inny_kolor;
        		view.mainFrame.setBackground(model.kolorWeekendu);
        	}

        	if(model.kolor_aktualnego_dnia == 1)
        	{
        		model.kolor_aktualnego_dnia2 = 1;
        		model.kolorDnia = new Color(100, 250, 100);
            	view.mainFrame.setBackground(model.kolorDnia);
        	}
        	else if(model.kolor_aktualnego_dnia == 2)
        	{
        		model.kolor_aktualnego_dnia2 = 2;
        		model.kolorDnia = Color.white;
            	view.mainFrame.setBackground(model.kolorDnia);
        	}
        	else if(model.kolor_aktualnego_dnia == 3)
        	{
        		model.kolor_aktualnego_dnia2 = 3;
        		model.kolorDnia = Color.cyan;
            	view.mainFrame.setBackground(model.kolorDnia);
        	}
        	
        	if(model.kolor_zaznaczonego_dnia == 1)
        	{
        		model.kolor_zaznaczonego_dnia2 = 1;
        		model.kolorWybranegoDnia = new Color(250, 100, 100);
            	view.mainFrame.setBackground(model.kolorWybranegoDnia);
        	}
        	else if(model.kolor_zaznaczonego_dnia == 2)
        	{
        		model.kolor_zaznaczonego_dnia2 = 2;
        		model.kolorWybranegoDnia = Color.white;
            	view.mainFrame.setBackground(model.kolorWybranegoDnia);
        	}
        	else if(model.kolor_zaznaczonego_dnia == 3)
        	{
        		model.kolor_zaznaczonego_dnia2 = 3;
        		model.kolorWybranegoDnia = Color.cyan;
            	view.mainFrame.setBackground(model.kolorWybranegoDnia);
        	}
        	// 2 - W bazie
        	switch(model.guest){
			case 0: model.baza.query2("UPDATE `user` SET `waznosc_zdarzen`="+model.wybrana_liczba_dni+"," +
		        			"`kolorWeekendu`="+model.kolor_dni_wolnych+"," +
		        			"`kolorDnia`="+model.kolor_aktualnego_dnia+"," +
		        			"`kolorWybranegoDnia`="+model.kolor_zaznaczonego_dnia+" " +
		        					"WHERE `ID` = "+model.baza.identyfikator);
					model.dni_waznosci = model.wybrana_liczba_dni;
		        	view.settingsFrame.hide();
					break;
			case 1: model.dni_waznosci = model.wybrana_liczba_dni;
        			view.settingsFrame.hide();
					break;
			}
        }
        else if (source == view.settingsFrame.anuluj)
        {
        	// Przywrocenie ustawien - tylko graficznie
        	
           	if(model.kolor_dni_wolnych2 == 1) view.settingsFrame.opcja_1.setSelected(true);
        	else if(model.kolor_dni_wolnych2 == 2) view.settingsFrame.opcja_2.setSelected(true);
        	else if(model.kolor_dni_wolnych2 == 3) view.settingsFrame.opcja_3.setSelected(true);
        	else if(model.kolor_dni_wolnych2 == 4) view.settingsFrame.opcja_4.setSelected(true);

        	if(model.kolor_aktualnego_dnia2 == 1) view.settingsFrame.dzien_1.setSelected(true);
        	else if(model.kolor_aktualnego_dnia2 == 2) view.settingsFrame.dzien_2.setSelected(true);
        	else if(model.kolor_aktualnego_dnia2 == 3) view.settingsFrame.dzien_3.setSelected(true);
        	
        	if(model.kolor_zaznaczonego_dnia2 == 1) view.settingsFrame.zaz_1.setSelected(true);
        	else if(model.kolor_zaznaczonego_dnia2 == 2) view.settingsFrame.zaz_2.setSelected(true);
        	else if(model.kolor_zaznaczonego_dnia2 == 3) view.settingsFrame.zaz_3.setSelected(true);
        	
	    	view.settingsFrame.dni.setText(model.dni_waznosci+" miesiacach.");
	    	view.settingsFrame.slider.setValue(model.dni_waznosci);
        	view.settingsFrame.hide();
        }
	}
	
   /**
	* Metoda odpowiadajaca za obs³uge zdarzeñ wywo³anych wykonaniem pewnych zmian
	* w komponentach np. przesunieciem suwaka.
	* @param e - objekt klasy ActionEvent umo¿liwiajacy dostep do konkretnego zdarzenia
	*/
	public void stateChanged(ChangeEvent e)
	{
	    JSlider source = (JSlider)e.getSource();
	    
	    if (!source.getValueIsAdjusting())   // view.settingsFrame.opcja_1
	    {
	    	model.wybrana_liczba_dni = (int)source.getValue();
	    	view.settingsFrame.dni.setText(model.wybrana_liczba_dni+" miesiacach.");
	    }
	}	
}