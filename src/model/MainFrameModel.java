package model;

import java.awt.Component;
import java.sql.Date;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.util.ArrayList;

import view.View;

import controller.Controller;

class MultiLineCellRenderer implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		JTextArea ta = new JTextArea(1, 10);
		
		ta.setLineWrap(true);
		ta.setWrapStyleWord(true);
		ta.setOpaque(true);
		ta.setRows(3);
		ta.setText((value == null) ? "" : value.toString());
		return ta;
	}
}

public class MainFrameModel {

	public int startDay = 0, startMonth = 0, startYear = 0, finishDay = 0, finishMonth = 0, finishYear = 0;
	public static TableModel dataModelDay;
	public static TableModel dataModelMonth;
	
	public JTable tableDay;
	public JTable tableMonth;
	
	String month;
	String day;
	int ileDay = 0, ileMonth = 0;
	ArrayList<Zdarzenie> monthEvent = new ArrayList<Zdarzenie>();
	ArrayList<Zdarzenie> dayEvent = new ArrayList<Zdarzenie>();
	
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
		System.out.println(dayEvent);
		
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
	     tableDay.getColumnModel().getColumn(0).setPreferredWidth(40);
	     tableDay.getColumnModel().getColumn(1).setPreferredWidth(250);
	     tableDay.getColumnModel().getColumn(2).setPreferredWidth(100);
	}
	
	public void tableMonth(){
		
		monthEvent.removeAll(monthEvent);
		ileMonth=0;
		  
	  	  for(int i=0; i<Model.zdarzenia.size(); i++){
	  		  month = new String(Model.zdarzenia.get(i).data_rozpoczecia.substring(5, 7));
	  		  if(Integer.parseInt(month) == Model.checkedMonth){
	  			  ileMonth++;
	      		  monthEvent.add(Model.zdarzenia.get(i));
	      	  }
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
	     tableMonth.getColumnModel().getColumn(0).setPreferredWidth(30);
	     tableMonth.getColumnModel().getColumn(1).setPreferredWidth(40);
	     tableMonth.getColumnModel().getColumn(2).setPreferredWidth(180);
	     tableMonth.getColumnModel().getColumn(3).setPreferredWidth(50);
	     //tableMonth.setRowHeight(50);
	     TableColumn event = tableMonth.getColumn("Event");
	     event.setCellRenderer(new MultiLineCellRenderer());
	}
}
