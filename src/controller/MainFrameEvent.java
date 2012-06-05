package controller;

import java.awt.event.*;
import java.io.File;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import view.*;
import model.*;

/**
* Klasa odpowiadaj�ca za obs�ug� zdarze� wywo�anych z poziomu ramki g��wnej programu (MainFrame)
*/ 
public class MainFrameEvent extends MouseAdapter implements ActionListener, ChangeListener, MouseListener, ItemListener{

	 public static View view;
	 public static Model model;
	 
	 public MainFrameEvent(){}
	
	 /**
	 * Metoda odpowiadajaca za obs�ug� zdarze� wywo�anych za pomoc� przycisk�w
	 * @param e - dost�p do konkretnego zdarzenia
	 */
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
			  model.mainFrame.tableMonth();
			  view.mainFrame.refreshTableMonth();
          }
	  }
	  else if (source == view.mainFrame.delete)  
      {
		    int ile = 0;
		    ArrayList<Zdarzenie> toDelete = new ArrayList<Zdarzenie>();
		    int delete = 0;
		    
			for(int i=0; i<model.mainFrame.tableDay.getRowCount(); i++)
			{
				if(model.mainFrame.tableDay.isRowSelected(i)){
					switch(model.guest){
						case 0: if(model.baza.delete(model.mainFrame.dayEvent.get(i).id) == 1){
									toDelete.add(model.zdarzenia.get(model.zdarzenia.indexOf(model.mainFrame.dayEvent.get(i))));
									ile++;
								}
								break;
						case 1: toDelete.add(model.zdarzenia.get(model.zdarzenia.indexOf(model.mainFrame.dayEvent.get(i))));
								ile++;
								break;
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
		Date startDate = new Date(), alarmDate = new Date();
		long roznica = 0;
		
		  	if(opis != "" && miejsce != "" && startDay != 0 && startMonth != 0 && startYear != 0 && !hour.isEmpty() && !minute.isEmpty()
		  		&& (view.mainFrame.panelAlarm.isVisible() == false || (view.mainFrame.panelAlarm.isVisible() == true
		  				&& alarmDay != 0 && alarmMonth != 0 && alarmYear != 0 && !hourAlarm.isEmpty() && !minuteAlarm.isEmpty()))){
		  		
		  		if(view.mainFrame.panelAlarm.isVisible() == true){
					if(alarmMonth < 10) alarmMonthString = new String("0"+alarmMonth.toString());
					else alarmMonthString = new String(alarmMonth.toString());
					if(alarmDay < 10) alarmDayString = new String("0"+alarmDay.toString());
					else alarmDayString = new String(alarmDay.toString());
					String alarmDateString = new String(alarmYear.toString()+"-"+alarmMonthString+"-"+alarmDayString);
					
					startDate.setYear(startYear);
					startDate.setMonth(startMonth);
					startDate.setDate(startDay);
					startDate.setHours(Integer.parseInt(hour));
					startDate.setMinutes(Integer.parseInt(minute));
					
					alarmDate.setYear(alarmYear);
					alarmDate.setMonth(alarmMonth);
					alarmDate.setDate(alarmDay);
					alarmDate.setHours(Integer.parseInt(hourAlarm));
					alarmDate.setMinutes(Integer.parseInt(minuteAlarm));
					
					roznica = ((startDate.getTime()/60000)-(alarmDate.getTime()/60000));
				}
		  		
		  		Calendar cal = new GregorianCalendar();
		  		int hourActual = cal.get(GregorianCalendar.HOUR_OF_DAY);
		  		int minuteActual = cal.get(GregorianCalendar.MINUTE);
		  		Date currentDate = new Date(model.currentYear, model.currentMonth, model.currentDay);
		  		
		  		if(Integer.parseInt(hour)<0 || Integer.parseInt(hour)>23 || Integer.parseInt(minute)<0 || Integer.parseInt(minute)>59){
		  			JOptionPane.showMessageDialog(null, "You typed bad hours for start");
		  		}
		  		else if(view.mainFrame.panelAlarm.isVisible() == true && (Integer.parseInt(hourAlarm)<0 || Integer.parseInt(hourAlarm)>23 || Integer.parseInt(minuteAlarm)<0 || Integer.parseInt(minuteAlarm)>59)){
		  			JOptionPane.showMessageDialog(null, "You typed bad hours for alarm");
		  		}
		  		else if(currentDate.compareTo(new Date(startYear, startMonth, startDay)) == 1){
		  			JOptionPane.showMessageDialog(null, "Data of start is too early");
		  		}
		  		else if(view.mainFrame.panelAlarm.isVisible() == true && ((startDate.compareTo(alarmDate) == -1) || (startDate.compareTo(alarmDate) == 0 && Integer.parseInt(hourAlarm) > Integer.parseInt(hour))
		  				|| (startDate.compareTo(alarmDate) == 0 && Integer.parseInt(hourAlarm) == Integer.parseInt(hour) && Integer.parseInt(minuteAlarm) >= Integer.parseInt(minute)))){
		  			JOptionPane.showMessageDialog(null, "Data of alarm is too late");
		  		}
		  		else if(view.mainFrame.panelAlarm.isVisible() == true && ((alarmDate.compareTo(currentDate) == -1) || (alarmDate.compareTo(currentDate) == 0 && Integer.parseInt(hourAlarm) < cal.get(GregorianCalendar.HOUR_OF_DAY))
		  				|| (alarmDate.compareTo(currentDate) == 0 && Integer.parseInt(hourAlarm) == cal.get(GregorianCalendar.HOUR_OF_DAY) && Integer.parseInt(minuteAlarm) < cal.get(GregorianCalendar.MINUTE)))){
		  			JOptionPane.showMessageDialog(null, "Data of alarm is too early");
		  		}
		  		else{
		  			if(view.mainFrame.buttonAddEvent.getText() == "Add Event"){
		  				
		  				System.out.println("add");
		  				
		  				switch(model.guest){
						case 0: if(model.baza.insert(opis, 1, startDateString, finishDateString, miejsce, roznica, 1) == 1){
									if(model.zdarzenia.add(new Zdarzenie(model.baza.nextID-1, opis, 1, startDateString, finishDateString, miejsce, roznica, 1))){
										JOptionPane.showMessageDialog(null, "Event was added");
										addEvent();
										view.mainFrame.currentEvent.setVisible(false);
									}
									else JOptionPane.showMessageDialog(null, "Event was not added");
								}
								else JOptionPane.showMessageDialog(null, "Event was not added");
								break;
						case 1: if(model.zdarzenia.add(new Zdarzenie(model.baza.nextID-1, opis, 1, startDateString, finishDateString, miejsce, roznica, 1))){
									JOptionPane.showMessageDialog(null, "Event was added");
									addEvent();
									view.mainFrame.currentEvent.setVisible(false);
									model.baza.nextID++;
								}
								else JOptionPane.showMessageDialog(null, "Event was not added");
								break;
		  				}
			  		}
		  			else{
		  				
		  				switch(model.guest){
						case 0: if(model.baza.update(model.mainFrame.dayEvent.get(model.mainFrame.rowSelectedDay).id, opis, 1, startDateString, finishDateString, miejsce, roznica, 1) == 1){
				  					if(model.zdarzenia.set(model.zdarzenia.indexOf(model.mainFrame.dayEvent.get(model.mainFrame.rowSelectedDay)),
				  							new Zdarzenie(model.zdarzenia.get(model.mainFrame.rowSelectedDay).id, opis, 1, startDateString, finishDateString, miejsce, roznica, 1)) != null){
										JOptionPane.showMessageDialog(null, "Event was edited");
										addEvent();
										view.mainFrame.notEditEvent();
									}
				  					else JOptionPane.showMessageDialog(null, "Event was not edited");
								}
								else JOptionPane.showMessageDialog(null, "Event was not edited");
								break;
						case 1: if(model.zdarzenia.set(model.zdarzenia.indexOf(model.mainFrame.dayEvent.get(model.mainFrame.rowSelectedDay)),
			  							new Zdarzenie(model.zdarzenia.get(model.mainFrame.rowSelectedDay).id, opis, 1, startDateString, finishDateString, miejsce, roznica, 1)) != null){
									JOptionPane.showMessageDialog(null, "Event was edited");
									addEvent();
									view.mainFrame.notEditEvent();
								}
			  					else JOptionPane.showMessageDialog(null, "Event was not edited");
								break;
		  				}
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
	 

	 /**
	 * Metoda odpowiadajaca za obs�ug� zdarze� wywo�anych za pomoc� obiekt�w klas JTabbedPane albo JSpinner
	 * @param e - dost�p do konkretnego zdarzenia
	 */
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
		else if(source == view.mainFrame.fromDate || source == view.mainFrame.toDate || source == view.mainFrame.fromHour
				|| source == view.mainFrame.fromMinute || source == view.mainFrame.toHour || source == view.mainFrame.toMinute){

			model.mainFrame.tableMonth();
			view.mainFrame.refreshTableMonth();
		}
	}

	/**
	 * Metoda odpowiadajaca za obs�ug� zdarze� wywo�anych za pomoc� wci�ni�cia klawisza myszy
	 * @param e - dost�p do konkretnego zdarzenia
	 */
	public void mousePressed(MouseEvent e) {
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
	
	/**
	 * Metoda odpowiadajaca za obs�ug� zdarze� wywo�anych za pomoc� obiekt�w klasy JCheckBox
	 * @param e - dost�p do konkretnego zdarzenia
	 */
	public void itemStateChanged(ItemEvent e) {
        
		if(e.getItemSelectable() == view.mainFrame.filterDate){
           if(view.mainFrame.filterDate.isSelected() == true){
        	   view.mainFrame.fromDate.setEnabled(true);
        	   view.mainFrame.toDate.setEnabled(true);
           }
           else{
        	   view.mainFrame.fromDate.setEnabled(false);
        	   view.mainFrame.toDate.setEnabled(false);
           }
           model.mainFrame.tableMonth();
		   view.mainFrame.refreshTableMonth();
		}
		else if(e.getItemSelectable() == view.mainFrame.filterHour){
			if(view.mainFrame.filterHour.isSelected() == true){
	     	   view.mainFrame.fromHour.setEnabled(true);
	     	   view.mainFrame.fromMinute.setEnabled(true);
	     	   view.mainFrame.toHour.setEnabled(true);
	     	   view.mainFrame.toMinute.setEnabled(true);
	        }
	        else{
	     	   view.mainFrame.fromHour.setEnabled(false);
	     	   view.mainFrame.fromMinute.setEnabled(false);
	     	   view.mainFrame.toHour.setEnabled(false);
	     	   view.mainFrame.toMinute.setEnabled(false);
	        }
			model.mainFrame.tableMonth();
			view.mainFrame.refreshTableMonth();
		}
	}
	
	/**
	 * Metoda odpowiadaj�ca za ustawianie odpowiednich parametr�w w momencie dodania zdarzenia
	 * b�d� jego modyfikacji
	 */
	public void addEvent(){
		model.mainFrame.tableDay();
		view.mainFrame.refreshTableDay();
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
