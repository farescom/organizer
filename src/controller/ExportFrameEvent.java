package controller;

import java.awt.event.*;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import view.*;
import model.*;

public class ExportFrameEvent implements ActionListener, ChangeListener
{
 public static View view;
 public static Model model;
 public static Controller controller;
 public int opcja = 1;
 
 
 public ExportFrameEvent(){}

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
	
	public void stateChanged(ChangeEvent e)
	{
	}	
}
