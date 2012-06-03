package model;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.sql.Date;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.util.ArrayList;

import view.MainFrame;
import view.View;

import controller.Controller;

public class MainFrameModel {

	public int startDay = 0, startMonth = 0, startYear = 0; 
	public int finishDay = 0, finishMonth = 0, finishYear = 0;
	public int alarmDay = 0, alarmMonth = 0, alarmYear = 0;
	public static TableModel dataModelDay;
	public static TableModel dataModelMonth;
	
	public JTable tableDay;
	public JTable tableMonth;
	
	String day, month, year, hour, minute;
	public int rowSelectedMonth = 0, rowSelectedDay = 0;
	int ileDay = 0, ileMonth = 0, ileMonthTemp = 0;
	public ArrayList<Zdarzenie> monthEvent = new ArrayList<Zdarzenie>();
	public ArrayList<Zdarzenie> dayEvent = new ArrayList<Zdarzenie>();
	ArrayList<Zdarzenie> monthEventTemp = new ArrayList<Zdarzenie>();
	ArrayList<Zdarzenie> dayEventTemp = new ArrayList<Zdarzenie>();
	
	public MainFrameModel()
	{
		tableDay();
		tableMonth();
	}
	
	public void tableDay(){
		
		dayEvent.removeAll(dayEvent);
		ileDay=0;
		
		for(int i=0; i<Model.zdarzenia.size(); i++){
	  		  day = new String(Model.zdarzenia.get(i).data_rozpoczecia.substring(8, 10));
	  		  if(Integer.parseInt(day) == Model.checkedDay){
	  			  ileDay++;
	      		  dayEvent.add(Model.zdarzenia.get(i));
	      	  }
	  	 }
		
		dataModelDay = new AbstractTableModel()
		{
			  /**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private String[] columnNames = {"Hour", "Event", "Place"};
			  
	          public String getColumnName(int col)
	          {
	              return columnNames[col];
	          }
			  public int getColumnCount() { return 3; }
	          public int getRowCount() { return ileDay;}
	          public boolean isEditable(){ return true; }
	          public Object getValueAt(int row, int col) {
	        	  switch(col)
	        	  {
	        	  	case 0: return dayEvent.get(row).godzina;
	        	  	case 1: return dayEvent.get(row).opis;
	        	  	case 2: return dayEvent.get(row).miejsce;
	        	  }
	        	  return null;
	          }
	     };
	     
	     tableDay = new JTable(dataModelDay);
	     tableDay.setName("tableDay");
	     tableDay.getColumnModel().getColumn(0).setPreferredWidth(50);
	     tableDay.getColumnModel().getColumn(1).setPreferredWidth(240);
	     tableDay.getColumnModel().getColumn(2).setPreferredWidth(100);
	     tableDay.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	     
	     MultiLineCellRenderer multiLineCellRenderer = new MultiLineCellRenderer();
	     tableDay.getColumnModel().getColumn(0).setCellRenderer(multiLineCellRenderer);
	     tableDay.getColumnModel().getColumn(1).setCellRenderer(multiLineCellRenderer);
	     tableDay.getColumnModel().getColumn(2).setCellRenderer(multiLineCellRenderer);
	     for(int i = 0; i< tableDay.getRowCount(); i++)
	     {
	    	 //tableDay.setRowHeight(15);
	    	 tableDay.setRowHeight(i, 15 * (dayEvent.get(i).opis.length()/30+1));
	     }
	}
	
	public void tableMonth(){
		
		monthEvent.removeAll(monthEvent);
		ileMonth=0;
		
		System.out.println(Model.zdarzenia);
		
	  	  for(int i=0; i<Model.zdarzenia.size(); i++){
	  		  month = new String(Model.zdarzenia.get(i).data_rozpoczecia.substring(5, 7));
	  		  year = new String(Model.zdarzenia.get(i).data_rozpoczecia.substring(0, 4));
	  		  
		  		if(Integer.parseInt(month) == Model.checkedMonth && Integer.parseInt(year) == Model.checkedYear){
		  			  ileMonth++;
		      		  monthEvent.add(Model.zdarzenia.get(i));
		      	}
	  	  }
	  	  
	  	if(View.mainFrame.filterDate.isSelected() == true){
	  		
	  		monthEventTemp.removeAll(monthEventTemp);
	  		ileMonthTemp = 0;
	  		for(int i=0; i<monthEvent.size(); i++){
				  day = new String(monthEvent.get(i).data_rozpoczecia.substring(8, 10));
		  	      if(Integer.parseInt(day)>=Integer.parseInt(View.mainFrame.fromDate.getValue().toString())
		  	    		  && Integer.parseInt(day)<=Integer.parseInt(View.mainFrame.toDate.getValue().toString())){
			  			  
		  	    	  	  ileMonthTemp++;
			      		  monthEventTemp.add(monthEvent.get(i));
			      }
			}

	  		monthEvent.removeAll(monthEvent);
  			monthEvent.addAll(monthEventTemp);
  			ileMonth = ileMonthTemp;
		}
	  	if(View.mainFrame.filterHour.isSelected() == true){

	  		monthEventTemp.removeAll(monthEventTemp);
	  		ileMonthTemp = 0;
	  		for(int i=0; i<monthEvent.size(); i++){
				  hour = new String(monthEvent.get(i).data_rozpoczecia.substring(11, 13));
				  minute = new String(monthEvent.get(i).data_rozpoczecia.substring(14, 16));
				  
				  if(Integer.parseInt(hour)>Integer.parseInt(View.mainFrame.fromHour.getValue().toString())
		  	    		  && Integer.parseInt(hour)<Integer.parseInt(View.mainFrame.toHour.getValue().toString())){

		  	    	  	  ileMonthTemp++;
			      		  monthEventTemp.add(monthEvent.get(i));
			      }
				  else if(Integer.parseInt(hour)==Integer.parseInt(View.mainFrame.fromHour.getValue().toString())){
					  
					  	if(Integer.parseInt(minute)>=Integer.parseInt(View.mainFrame.fromMinute.getValue().toString())){
		  	    	 
		  	    	  	  ileMonthTemp++;
			      		  monthEventTemp.add(monthEvent.get(i));
		  	    	  	}	  
			      }
				  else if(Integer.parseInt(hour)==Integer.parseInt(View.mainFrame.toHour.getValue().toString())){
					  
					  	if(Integer.parseInt(minute)<=Integer.parseInt(View.mainFrame.toMinute.getValue().toString())){
		  	    	  	
		  	    	  	  ileMonthTemp++;
			      		  monthEventTemp.add(monthEvent.get(i));
		  	    	  	}	  
			      }
			}

	  		monthEvent.removeAll(monthEvent);
  			monthEvent.addAll(monthEventTemp);
  			ileMonth = ileMonthTemp;
		}
		
		dataModelMonth = new AbstractTableModel()
		{
			  /**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private String[] columnNames = {"Day", "Hour", "Event", "Place"};
			  
	          public String getColumnName(int col)
	          {
	              return columnNames[col];
	          }
			  public int getColumnCount() { return 4; }
	          public int getRowCount() { return ileMonth; }
	          public boolean isEditable(){ return true; }
	          public Object getValueAt(int row, int col) {
	        	  switch(col)
	        	  {
	        	  	case 0: return monthEvent.get(row).dzien;
	        	  	case 1: return monthEvent.get(row).godzina;
	        	  	case 2: return monthEvent.get(row).opis;
	        	  	case 3: return monthEvent.get(row).miejsce;
	        	  }
	        	  return null;
	          }
	     };
	     
	     tableMonth = new JTable(dataModelMonth);
	     tableMonth.setName("tableMonth");
	     tableMonth.getColumnModel().getColumn(0).setPreferredWidth(30);
	     tableMonth.getColumnModel().getColumn(1).setPreferredWidth(40);
	     tableMonth.getColumnModel().getColumn(2).setPreferredWidth(150);
	     tableMonth.getColumnModel().getColumn(3).setPreferredWidth(80);
	     tableMonth.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	     TableMonthRenderer tableMonthRenderer = new TableMonthRenderer();
	     tableMonth.setDefaultRenderer(tableMonth.getColumnClass(0), tableMonthRenderer);
	     
	}
	
	class TableMonthRenderer extends DefaultTableCellRenderer{
		public Integer day = 0;
		
		public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
			super.getTableCellRendererComponent(table, value, selected, focused, row, column);
			
			if((selected == true && MainFrame.tabbedPane.getSelectedIndex() != 1)){
				day = Integer.parseInt(table.getValueAt(row, 0).toString());
				
				Model.checkedDay = day;
				MainFrame.tabbedPane.setTitleAt(1, Model.checkedDay + " " + View.months[Model.checkedMonth] + " Event");
				tableDay();
				View.mainFrame.refreshTableDay();
				MainFrame.calendar.setVisible(false);
				MainFrame.currentEvent.setVisible(true);
				MainFrame.addEvent.setVisible(false);
				MainFrame.tabbedPane.setSelectedIndex(1);
				rowSelectedMonth = row;
			}
			else if(selected == true && MainFrame.tabbedPane.getSelectedIndex() == 1){
				day = Integer.parseInt(table.getValueAt(row, 0).toString());
				if(Model.checkedDay != day)
				{
					Model.checkedDay = day;
					MainFrame.tabbedPane.setTitleAt(1, Model.checkedDay + " " + View.months[Model.checkedMonth] + " Event");
					tableDay();
					View.mainFrame.refreshTableDay();
				}
			}

			setBorder(null);
			setForeground(Color.black);
			return this;  
		}
	}
	
	class MultiLineCellRenderer implements TableCellRenderer {
		
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {

			JTextArea ta = new JTextArea();
			
			if(isSelected)
			{
				ta.setBackground(new Color(184, 207, 229));
				for(int i=0; i<tableMonth.getRowCount(); i++)
				{
					if(tableMonth.isRowSelected(i))
						tableMonth.getSelectionModel().removeSelectionInterval(i, i);
				}
				MainFrame.edit.setEnabled(true);
				MainFrame.delete.setEnabled(true);
				rowSelectedDay = row;
			}
			
			ta.setLineWrap(true);
			ta.setWrapStyleWord(true);
			ta.setOpaque(true);
			ta.setText((value == null) ? "" : value.toString());
			Font font = new Font("Lucida Console", Font.PLAIN, 12);
			ta.setFont(font);
			return ta;
		}
	}
}
