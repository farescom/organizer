package controller;

import java.awt.event.*;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.io.File;
import java.sql.Date;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
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

public class MainFrameEvent extends MouseAdapter implements ActionListener, ChangeListener, MouseListener{

	 public static View view;
	 public static Model model;
	 
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
	  else if (source == view.mainFrame.exportOption) 
	  {
		  	// Export
		  if (view.exportFrame == null)
		  	view.exportFrame = new ExportFrame("Export", 560, 370,
		  		view.mainFrame.location_x + (view.mainFrame.size().width/2) - (560/2),
		  		view.mainFrame.location_y + (view.mainFrame.size().height/2) - (370/2) + 20);
		  if (!view.exportFrame.isShowing()) view.exportFrame.show();
	  }
	  else if (source == view.mainFrame.importOption) 
	  {
		  	// Import
          final JFileChooser fc = new JFileChooser();
          int returnVal = fc.showOpenDialog(view.exportFrame);
          if (returnVal == JFileChooser.APPROVE_OPTION)
          {
              File file = fc.getSelectedFile();
              XML.fromXML(file, model.zdarzenia);
          }
          
          System.out.println(model.zdarzenia.size());
	  }
	  
	  
	  else if (source == view.mainFrame.delete)  
      {
		    int ile = 0;
		    ArrayList<Zdarzenie> toDelete = new ArrayList<Zdarzenie>();
		    int delete = 0;
			for(int i=0; i<model.mainFrame.tableDay.getRowCount(); i++)
			{
				if(model.mainFrame.tableDay.isRowSelected(i)){
					if(model.baza.delete(model.mainFrame.dayEvent.get(i).id) == 1){
						    toDelete.add(model.zdarzenia.get(model.zdarzenia.indexOf(model.mainFrame.dayEvent.get(i))));
							ile++;
					}
					else{
						System.out.println("blad: "+model.zdarzenia.get(i).id);
					}
				}
			}
			for(int i=0; i<ile; i++){
				model.zdarzenia.remove(toDelete.get(i));
			}
			
			System.out.println(model.zdarzenia);
			
			JOptionPane.showMessageDialog(null, ile+" event was deleted");
			model.mainFrame.tableDay();
			view.mainFrame.refreshTableDay();
			model.mainFrame.tableMonth();
			view.mainFrame.refreshTableMonth();
      }
	  else if (source == view.mainFrame.edit)  
      {
			for(int i=0; i<model.zdarzenia.size(); i++)
			{
				if(model.mainFrame.tableMonth.isRowSelected(i) && view.mainFrame.opis.getText() != null ) System.out.println("zedytowano: "
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
			String startMonthString;
			if(startMonth < 10) startMonthString = new String("0"+startMonth.toString());
			else startMonthString = new String(startMonth.toString());
			String startDayString;
			if(startDay < 10) startDayString = new String("0"+startDay.toString());
			else startDayString = new String(startDay.toString());
			Integer startYear = new Integer(model.mainFrame.startYear);
			Integer finishDay = new Integer(model.mainFrame.finishDay);
			Integer finishMonth = new Integer(model.mainFrame.finishMonth);
			Integer finishYear = new Integer(model.mainFrame.finishYear);
			String startDate = new String(startYear.toString()+"-"+startMonthString+"-"+startDayString+" "+hour+":"+minute+":00");
			String finishDate = new String(finishYear.toString()+"-"+finishMonth.toString()+"-"+finishDay.toString());
		  
		  	if(opis != "" && miejsce != "" && startDay != 0 && startMonth != 0 && startYear != 0  && finishDay != 0
		  			&& finishMonth != 0 && finishYear != 0 && hour != "" && minute != ""){
				
				if(model.baza.insert(opis, 1, startDate, finishDate, miejsce, 1, 1) == 1){
					if(model.zdarzenia.add(new Zdarzenie(model.baza.nextID-1, opis, 1, startDate, finishDate, miejsce, 1, 1))){
						JOptionPane.showMessageDialog(null, "Event was added");
						model.mainFrame.tableMonth();
						view.mainFrame.refreshTableMonth();
					}
				}
				else JOptionPane.showMessageDialog(null, "Event was not added");
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
				view.mainFrame.edit.setEnabled(false);
				view.mainFrame.delete.setEnabled(false);
				for(int i=0; i<model.mainFrame.tableMonth.getRowCount(); i++)
				{
					if(model.mainFrame.tableMonth.isRowSelected(i))
						model.mainFrame.tableMonth.getSelectionModel().removeSelectionInterval(i, i);
				}
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
				view.mainFrame.edit.setEnabled(false);
				view.mainFrame.delete.setEnabled(false);
				for(int i=0; i<model.mainFrame.tableMonth.getRowCount(); i++)
				{
					if(model.mainFrame.tableMonth.isRowSelected(i))
						model.mainFrame.tableMonth.getSelectionModel().removeSelectionInterval(i, i);
				}
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
			view.mainFrame.source = e.getSource();
			if(view.mainFrame.source == view.mainFrame.data_roz){
				if(model.mainFrame.startMonth == 0) model.mainFrame.startMonth = model.currentMonth;
				if(model.mainFrame.startYear == 0) model.mainFrame.startYear = model.currentYear;
				View.calendarFrame = new CalendarFrame("Select date", 370, 300, 310, 110, View.mainFrame);
			}
			else if(view.mainFrame.source == view.mainFrame.data_zak){
				if(model.mainFrame.finishMonth == 0) model.mainFrame.finishMonth = model.currentMonth;
				if(model.mainFrame.finishYear == 0) model.mainFrame.finishYear = model.currentYear;
				View.calendarFrame = new CalendarFrame("Select date", 370, 300, 310, 110, View.mainFrame);
			}
	}
}
