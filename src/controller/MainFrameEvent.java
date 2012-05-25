package controller;

import java.awt.event.*;
import java.awt.EventQueue;
import java.sql.Date;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import view.*;
import model.*;

public class MainFrameEvent implements ActionListener, ChangeListener{

	 public static View view;
	 public static Model model;
	 
	 public JTable table;
	 public int first = 0, last = 0;
	 
	 public MainFrameEvent(){}
	
	 @Override
	 public void actionPerformed(ActionEvent e) {
	  
	  Object source = e.getSource(); 
	  if (source == view.mainFrame.exit)  
	  {
		  System.exit(0);
	  }
	  else if (source == view.mainFrame.settings) 
	  {
	  	// Ustawienia
	  	if (view.settingsFrame == null)
	  		view.settingsFrame = new SettingsFrame("Ustawienia", 560, 370,
	  				view.mainFrame.location_x + (view.mainFrame.size().width/2) - (560/2),
	  				view.mainFrame.location_y + (view.mainFrame.size().height/2) - (370/2) + 20);
	  	if (!view.settingsFrame.isShowing()) view.settingsFrame.show();
	  }
	  else if (source == view.mainFrame.delete)  
      {
			for(int i=0; i<model.zdarzenia.size(); i++)
			{
				if(table.isRowSelected(i)) System.out.println("usunieto: " + model.baza.delete(model.zdarzenia.get(i).id));
			}
      }
	  else if (source == view.mainFrame.edit)  
      {
			for(int i=0; i<model.zdarzenia.size(); i++)
			{
				if(table.isRowSelected(i) && view.mainFrame.opis.getText() != null ) System.out.println("zedytowano: "
						+ model.baza.update(model.zdarzenia.get(i).id, view.mainFrame.opis.getText(), 1, model.zdarzenia.get(i).data_rozpoczecia, model.zdarzenia.get(i).data_zakonczenia, null, 61, -1));
			}
      }
	  
	 }

	@Override
	public void stateChanged(ChangeEvent e) {
		
		Object source = e.getSource();
		if(source == view.mainFrame.tabbedPane)
		{
			if(view.mainFrame.tabbedPane.getSelectedIndex() == 0) 
			{
				view.mainFrame.calendar.setVisible(true);
				view.mainFrame.currentEvent.setVisible(false);
				view.mainFrame.addEvent.setVisible(false);
			}
			else if(view.mainFrame.tabbedPane.getSelectedIndex() == 1) 
			{
				view.mainFrame.calendar.setVisible(false);
				view.mainFrame.currentEvent.setVisible(true);
				view.mainFrame.addEvent.setVisible(false);
			}
			else if(view.mainFrame.tabbedPane.getSelectedIndex() == 2) 
			{
				view.mainFrame.calendar.setVisible(false);
				view.mainFrame.currentEvent.setVisible(false);
				view.mainFrame.addEvent.setVisible(true);
			}
		}
	}
}
