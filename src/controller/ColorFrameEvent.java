package controller;

import java.awt.event.*;
import view.*;
import model.*;

public class ColorFrameEvent implements ActionListener
{
 public static View view;
 public static Model model;
 public static Controller controller;
 
 public ColorFrameEvent(){}

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