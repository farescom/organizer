package model;

import java.sql.Date;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;

import view.View;

import controller.Controller;

public class MainFrameModel {

	public static int startDay = 0, startMonth = 0, startYear = 0, finishDay = 0, finishMonth = 0, finishYear = 0;
	public TableModel dataModelDay;
	public TableModel dataModelMonth;
	
	String month;
	String day;
	int ile = 0;
	ArrayList<Zdarzenie> monthEvent = new ArrayList<Zdarzenie>();
	ArrayList<Zdarzenie> dayEvent = new ArrayList<Zdarzenie>();
	
	public JTable tableDay()
	{
		ile=0;
		
		for(int i=0; i<Model.zdarzenia.size(); i++){
	  		  day = new String(Model.zdarzenia.get(i).data_rozpoczecia.substring(8, 10));
	  		  if(Integer.parseInt(day) == Model.checkedDay){
	      		  ile++;
	      		  dayEvent.add(Model.zdarzenia.get(i));
	      	  }
	  	 }
		
		System.out.println(ile);
		
		dataModelDay = new AbstractTableModel()
		{
			  private String[] columnNames = {"Current Event"};
			  private int rozmiar = 0;
			  
			  
	          public String getColumnName(int col)
	          {
	              return columnNames[col];
	          }
			  public int getColumnCount() { return 1; }
	          public int getRowCount() { return ile;}
	          public boolean isEditable()
	          {
	        	  return true;
	          }
	          public Object getValueAt(int row, int col) {
	        	  //return dayEvent.get(row).toString();
	        	  return row;
	          }
	     };
	     
	     JTable table = new JTable(dataModelDay);
	     Controller.mainFrameEvent.tableDay = table;
		
		return table;
	}
	
	public JTable tableMonth()
	{	
		  ile=0;
		  
	  	  for(int i=0; i<Model.zdarzenia.size(); i++){
	  		  month = new String(Model.zdarzenia.get(i).data_rozpoczecia.substring(5, 7));
	  		  if(Integer.parseInt(month) == Model.checkedMonth){
	      		  ile++;
	      		  monthEvent.add(Model.zdarzenia.get(i));
	      	  }
	  	  }
		
		dataModelMonth = new AbstractTableModel()
		{
			  private String[] columnNames = {View.months[Model.checkedMonth] + " Event" };
			  
	          public String getColumnName(int col)
	          {
	              return columnNames[col];
	          }
			  public int getColumnCount() { return 1; }
	          public int getRowCount() { return ile; }
	          public boolean isEditable(){ return true; }
	          public Object getValueAt(int row, int col) {
	        	  return monthEvent.get(row).toString();
	          }
	     };
	     
	     JTable table = new JTable(dataModelMonth);
	     Controller.mainFrameEvent.tableMonth = table;
		
		return table;
	}
	
}
