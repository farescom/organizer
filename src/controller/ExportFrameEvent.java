package controller;

import java.awt.event.*;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import view.*;
import model.*;

/**
* Klasa zewnetrzna obslugujaca wszystkie zdarzenia pochodzace z okna eksportowania
* danych aplikacji "ExportFrame"
*/ 
public class ExportFrameEvent implements ActionListener
{
 public static View view;
 public static Model model;
 public int opcja = 1;
 
 /**
 * Kontruktor domyœlny klasy.
 */ 
 public ExportFrameEvent(){}

   /**
	* Metoda odpowiadajaca za obsluge zdarzen wywolanych za pomoca przyciskow
	* @param e - objekt klasy ActionEvent umozliwiaj¹cy dostep do konkretnego zdarzenia
	*/
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();  
        if(source == view.exportFrame.export) 
        {
        	if (opcja == 1) // eksport do xml
        	{
                final JFileChooser fc = new JFileChooser();
                int returnVal = fc.showSaveDialog(view.exportFrame);
                if (returnVal == JFileChooser.APPROVE_OPTION)
                {
                    File file = fc.getSelectedFile();
                    XML.toXML(model.zdarzenia, file);
                    view.exportFrame.hide();
                }
        	}
        	else 			// eksport do Evolution
        	{
        		Evolution evo = new Evolution();
                final JFileChooser fc = new JFileChooser();
                int returnVal = fc.showSaveDialog(view.exportFrame);
                if (returnVal == JFileChooser.APPROVE_OPTION)
                {
                    File file = fc.getSelectedFile();
                    evo.zapiszCSV(file, model.zdarzenia);
                    view.exportFrame.hide();
                }
        	}
        }
        if(source == view.exportFrame.cancel) 
        	view.exportFrame.hide();
        if(source == view.exportFrame.opcja_1) 
        	opcja = 1;
        if(source == view.exportFrame.opcja_2) 
        	opcja = 2;
	}	
}
