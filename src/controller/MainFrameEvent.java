package controller;

import java.awt.event.*;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
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
			view.mainFrame.editEvent();
      }
	  else if (source == view.mainFrame.buttonAddEvent)  
      {
		  
		String opis = new String(view.mainFrame.opis.getText());
	    String miejsce = new String(view.mainFrame.miejsce.getText());
		String hour = new String(view.mainFrame.godzina.getText());
		String minute = new String(view.mainFrame.minuta.getText());
		String hourAlarm = new String(view.mainFrame.godzinaAlarmu.getText());
		String minuteAlarm = new String(view.mainFrame.minutaAlarmu.getText());
		Integer startDay = new Integer(model.mainFrame.startDay);
		Integer startMonth = new Integer(model.mainFrame.startMonth);
		Integer startYear = new Integer(model.mainFrame.startYear);
		String startMonthString;
		if(startMonth < 10) startMonthString = new String("0"+startMonth.toString());
		else startMonthString = new String(startMonth.toString());
		String startDayString;
		if(startDay < 10) startDayString = new String("0"+startDay.toString());
		else startDayString = new String(startDay.toString());
		Integer alarmDay = new Integer(model.mainFrame.alarmDay);
		Integer alarmMonth = new Integer(model.mainFrame.alarmMonth);
		Integer alarmYear = new Integer(model.mainFrame.alarmYear);
		String alarmMonthString;
		String alarmDayString;
		String startDateString = new String(startYear.toString()+"-"+startMonthString+"-"+startDayString+" "+hour+":"+minute+":00");
		String finishDateString = new String(startDateString);
		Date startDate, alarmDate;
		long roznica = 0;
		
		if(view.mainFrame.panelAlarm.isVisible() == true){
			if(alarmMonth < 10) alarmMonthString = new String("0"+alarmMonth.toString());
			else alarmMonthString = new String(alarmMonth.toString());
			if(alarmDay < 10) alarmDayString = new String("0"+alarmDay.toString());
			else alarmDayString = new String(alarmDay.toString());
			String alarmDateString = new String(alarmYear.toString()+"-"+alarmMonthString+"-"+alarmDayString);
			
			startDate = new Date();
			startDate.setYear(startYear);
			startDate.setMonth(startMonth);
			startDate.setDate(startDay);
			startDate.setHours(Integer.parseInt(hour));
			startDate.setMinutes(Integer.parseInt(minute));
			
			alarmDate = new Date();
			alarmDate.setYear(alarmYear);
			alarmDate.setMonth(alarmMonth);
			alarmDate.setDate(alarmDay);
			alarmDate.setHours(Integer.parseInt(hourAlarm));
			alarmDate.setMinutes(Integer.parseInt(minuteAlarm));
			
			roznica = ((startDate.getTime()/60000)-(alarmDate.getTime()/60000));
			
			System.out.println("Roznica: "+roznica);
		}
		    
		  	if(opis != "" && miejsce != "" && startDay != 0 && startMonth != 0 && startYear != 0 && hour != "" && minute != ""
		  		&& (view.mainFrame.panelAlarm.isVisible() == false || (view.mainFrame.panelAlarm.isVisible() == true
		  				&& alarmDay != 0 && alarmMonth != 0 && alarmYear != 0))){
				
		  		
		  		if(Integer.parseInt(hour)<0 || Integer.parseInt(hour)>23 || Integer.parseInt(minute)<0 || Integer.parseInt(minute)>59){
		  			JOptionPane.showMessageDialog(null, "You typed bad hours for start");
		  		}
		  		else if(view.mainFrame.panelAlarm.isVisible() == true && (Integer.parseInt(hourAlarm)<0 || Integer.parseInt(hourAlarm)>23 || Integer.parseInt(minuteAlarm)<0 || Integer.parseInt(minuteAlarm)>59)){
		  			JOptionPane.showMessageDialog(null, "You typed bad hours for alarm");
		  		}
		  		else if(new Date(model.currentYear, model.currentMonth, model.currentDay).compareTo(new Date(startYear, startMonth, startDay)) == 1){
		  			JOptionPane.showMessageDialog(null, "Data of start is too early");
		  		}
		  		else if(new Date(startYear, startMonth, startDay).compareTo(new Date(alarmYear, alarmMonth, alarmDay)) == -1){
		  			JOptionPane.showMessageDialog(null, "Data of alarm is too late");
		  		}
		  		else{
		  			if(view.mainFrame.buttonAddEvent.getText() == "Add Event"){
		  				
			  			if(model.baza.insert(opis, 1, startDateString, finishDateString, miejsce, roznica, 1) == 1){
							if(model.zdarzenia.add(new Zdarzenie(model.baza.nextID-1, opis, 1, startDateString, finishDateString, miejsce, roznica, 1))){
								JOptionPane.showMessageDialog(null, "Event was added");
								model.mainFrame.tableMonth();
								view.mainFrame.refreshTableMonth();
								view.mainFrame.opis.setText("");
								view.mainFrame.miejsce.setText("");
								view.mainFrame.godzina.setText("");
								view.mainFrame.minuta.setText("");
								view.mainFrame.godzinaAlarmu.setText("");
								view.mainFrame.minutaAlarmu.setText("");
								view.mainFrame.lblDataRozpoczecia.setText("");
								view.mainFrame.lblDataAlarmu.setText("");
								model.mainFrame.alarmDay = 0;
								model.mainFrame.alarmMonth = 0;
								model.mainFrame.alarmYear = 0;
								model.mainFrame.startDay = 0;
								model.mainFrame.startMonth = 0;
								model.mainFrame.startYear = 0;
								view.mainFrame.panelAlarm.setVisible(false);
							}
						}
						else JOptionPane.showMessageDialog(null, "Event was not added");
			  		}
		  			else{

		  				if(model.baza.update(model.zdarzenia.get(model.mainFrame.rowSelectedDay).id, opis, 1, startDateString, finishDateString, miejsce, roznica, 1) == 1){
		  					if(model.zdarzenia.set(model.zdarzenia.indexOf(model.zdarzenia.get(model.mainFrame.rowSelectedDay)),
		  							new Zdarzenie(model.baza.nextID-1, opis, 1, startDateString, finishDateString, miejsce, roznica, 1)) != null){
								JOptionPane.showMessageDialog(null, "Event was edited");
								model.mainFrame.tableMonth();
								view.mainFrame.refreshTableMonth();
								view.mainFrame.opis.setText("");
								view.mainFrame.miejsce.setText("");
								view.mainFrame.godzina.setText("");
								view.mainFrame.minuta.setText("");
								view.mainFrame.godzinaAlarmu.setText("");
								view.mainFrame.minutaAlarmu.setText("");
								view.mainFrame.lblDataRozpoczecia.setText("");
								view.mainFrame.lblDataAlarmu.setText("");
								model.mainFrame.alarmDay = 0;
								model.mainFrame.alarmMonth = 0;
								model.mainFrame.alarmYear = 0;
								model.mainFrame.startDay = 0;
								model.mainFrame.startMonth = 0;
								model.mainFrame.startYear = 0;
								view.mainFrame.panelAlarm.setVisible(false);
							}
						}
						else JOptionPane.showMessageDialog(null, "Event was not edited");
		  			}
		  		}
		 }
	  	else
		{
			JOptionPane.showMessageDialog(null, "You must fill all fields");
		}
      }
	  else if (source == view.mainFrame.buttonNotEditEvent)  
	  {
		  view.mainFrame.notEditEvent();
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
				view.mainFrame.tasks.setVisible(true);
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
				view.mainFrame.tasks.setVisible(true);
			}
			else if(view.mainFrame.tabbedPane.getSelectedIndex() == 2) 
			{
				view.mainFrame.calendar.setVisible(false);
				view.mainFrame.currentEvent.setVisible(false);
				view.mainFrame.addEvent.setVisible(true);
				view.mainFrame.edit.setEnabled(false);
				view.mainFrame.delete.setEnabled(false);
				view.mainFrame.tasks.setVisible(false);
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
			else if(view.mainFrame.source == view.mainFrame.dataAlarmu){
				if(model.mainFrame.alarmMonth == 0) model.mainFrame.alarmMonth = model.currentMonth;
				if(model.mainFrame.alarmYear == 0) model.mainFrame.alarmYear = model.currentYear;
				View.calendarFrame = new CalendarFrame("Select date", 370, 300, 310, 110, View.mainFrame);
			}
	}
}
