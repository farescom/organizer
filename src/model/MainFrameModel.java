package model;

import java.sql.Date;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import view.View;

import controller.Controller;

public class MainFrameModel {

	public static int startDay = 0, startMonth = 0, startYear = 0, finishDay = 0, finishMonth = 0, finishYear = 0;
	
	public JTable tableDay()
	{
		
		TableModel dataModel = new AbstractTableModel()
		{
			  private String[] columnNames = {"Current Event"};
			  private int rozmiar = 0;
			  
			  
	          public String getColumnName(int col)
	          {
	              return columnNames[col];
	          }
			  public int getColumnCount() { return 1; }
	          public int getRowCount() { return rozmiar;}
	          public boolean isEditable()
	          {
	        	  return true;
	          }
	          public Object getValueAt(int row, int col) {
	        	  if(Model.zdarzenia.get(row).data_rozpoczecia == new String(Model.checkedYear+"-"+Model.checkedMonth+"-"+Model.checkedDay)){
	        		  rozmiar++;
	        		  return Model.zdarzenia.get(row).toString();
	        	  }
	        	  return null;
	          }
	     };
	     
	     JTable table = new JTable(dataModel);
	     Controller.mainFrameEvent.table = table;
		
		return table;
	}
	
	public JTable tableMonth()
	{
		
		TableModel dataModel = new AbstractTableModel()
		{
			  private String[] columnNames = {View.months[Model.checkedMonth] + " Event" };
			
	          public String getColumnName(int col)
	          {
	              return columnNames[col];
	          }
			  public int getColumnCount() { return 1; }
	          public int getRowCount() { return Model.zdarzenia.size();}
	          public boolean isEditable()
	          {
	        	  return true;
	          }
	          public Object getValueAt(int row, int col) { return Model.zdarzenia.get(row).toString(); }
	     };
	     
	     JTable table = new JTable(dataModel);
	     Controller.mainFrameEvent.table = table;
		
		return table;
	}
	
}
