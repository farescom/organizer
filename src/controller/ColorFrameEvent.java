package controller;

import java.awt.event.*;
import view.*;
import model.*;

/**
* Klasa zewnetrzna obslugujaca wszystkie zdarzenia pochodz¹ce z okna wyboru koloru
* z palety "ColorFrame"
*/ 
public class ColorFrameEvent implements ActionListener
{
 public static View view;
 public static Model model;
 public static Controller controller;
 
 /**
 * Kontruktor domyœlny klasy.
 */ 
 public ColorFrameEvent(){}

 /**
	* Metoda odpowiadajaca za obs³ugê zdarzeñ wywo³anych za pomoc¹ przycisków
	* @param e - objekt klasy ActionEvent umo¿liwiaj¹cy dostêp do konkretnego zdarzenia
	*/
	public void actionPerformed(ActionEvent e)
	{
        Object source = e.getSource();
        if(source == view.colorFrame.zatwierdz)  
        {
        	controller.inny_kolor = view.colorFrame.tcc.getColor();
        	controller.kolor_dni_wolnych = 4;
        	view.colorFrame.hide();
        }
        else if(source == view.colorFrame.anuluj)  
        {
        	view.colorFrame.hide();
        }
	}
}