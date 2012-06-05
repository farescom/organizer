package controller;

import java.awt.event.*;
import view.*;
import model.*;

/**
* Klasa zewnetrzna obslugujaca wszystkie zdarzenia pochodzace z okna wyboru koloru
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
	* Metoda odpowiadajaca za obsluge zdarzen wywolanych za pomoc¹ przyciskow
	* @param e - objekt klasy ActionEvent umozliwiajacy dostêp do konkretnego zdarzenia
	*/
	public void actionPerformed(ActionEvent e)
	{
        Object source = e.getSource();
        if(source == view.colorFrame.zatwierdz)  
        {
        	model.inny_kolor = view.colorFrame.tcc.getColor();
        	model.kolor_dni_wolnych = 4;
        	view.colorFrame.hide();
        }
        else if(source == view.colorFrame.anuluj)  
        {
        	view.colorFrame.hide();
        }
	}
}