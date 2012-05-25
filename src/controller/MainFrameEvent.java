package controller;

import java.awt.event.*;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.sql.Date;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
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
	  else if (source == view.mainFrame.buttonAddEvent)  
      {
		    String opis = new String(view.mainFrame.opis.getText());
		    String miejsce = new String(view.mainFrame.miejsce.getText());
			String hour = new String(view.mainFrame.godzina.getText());
			String minute = new String(view.mainFrame.minuta.getText());
			Integer startDay = new Integer(model.mainFrame.startDay);
			Integer startMonth = new Integer(model.mainFrame.startMonth);
			Integer startYear = new Integer(model.mainFrame.startYear);
			Integer finishDay = new Integer(model.mainFrame.finishDay);
			Integer finishMonth = new Integer(model.mainFrame.finishMonth);
			Integer finishYear = new Integer(model.mainFrame.finishYear);
			String startDate = new String(startYear.toString()+"-"+startMonth.toString()+"-"+startDay.toString()+" "+hour+":"+minute+":00");
			String finishDate = new String(finishYear.toString()+"-"+finishMonth.toString()+"-"+finishDay.toString());
		  
		  	if(opis != "" && miejsce != "" && startDay != 0 && startMonth != 0 && startYear != 0  && finishDay != 0
		  			&& finishMonth != 0 && finishYear != 0 && hour != "" && minute != ""){
				
				if(model.baza.insert(opis, 1, startDate, finishDate, miejsce, 1, 1) == 1){
					System.out.println("Rekord zostal dodany");
					model.zdarzenia.add(new Zdarzenie(model.baza.nextID-1, opis, 1, startDate, finishDate, miejsce, 1, 1));
					System.out.println(model.zdarzenia.get(model.zdarzenia.size()-1).toString());
					view.mainFrame.refreshTableMonth();
				}
				else System.out.println("Rekord nie zostal dodany");
			}
			else
			{
				System.out.println("Nie mozna dodawac");
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
