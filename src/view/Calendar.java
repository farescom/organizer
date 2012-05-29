/*Contents of CalendarProgran.class */
package view;
//Import packages
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.*;
import javax.swing.table.*;

import model.Model;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import view.*;
import controller.Controller;

public class Calendar{
	
	public static Model model;
	
	public static JLabel labelMonth, labelYear;
	public static JButton buttonPrev, buttonNext;
	public static JTable tableCalendar;
	public static JComboBox comboYear;
	public static JFrame frameMain;
	public static DefaultTableModel mtableCalendar; //Table model
	public static JScrollPane stableCalendar; //The scrollpane
	public static JPanel panelCalendar, panelTop, panelBottom, panelMonth;
	
	public JPanel createProgram(){
		
		panelCalendar = new JPanel(new BorderLayout());
		panelTop = new JPanel(new BorderLayout());
		panelBottom = new JPanel(new BorderLayout());
		
		//Create controls
		panelMonth = new JPanel(new FlowLayout(FlowLayout.CENTER));
		labelMonth = new JLabel ("January");
		panelMonth.add(labelMonth);
		labelYear = new JLabel ("Change year:");
		comboYear = new JComboBox();
		buttonPrev = new JButton ("<<");
		buttonNext = new JButton (">>");
		mtableCalendar = new DefaultTableModel(){public boolean isCellEditable(int rowIndex, int mColIndex){return false;}};
		tableCalendar = new JTable(mtableCalendar);
		stableCalendar = new JScrollPane(tableCalendar);
		stableCalendar.setPreferredSize(new Dimension(360, 140));
		
		//Register action listeners
		buttonPrev.addActionListener(new buttonPrev_Action());
		buttonNext.addActionListener(new buttonNext_Action());
		
		//Add controls to pane
		panelTop.add(buttonPrev, BorderLayout.WEST);
		panelTop.add(panelMonth, BorderLayout.CENTER);
		panelTop.add(buttonNext, BorderLayout.EAST);
		panelBottom.add(labelYear, BorderLayout.WEST);
		panelBottom.add(comboYear, BorderLayout.EAST);
		
		panelCalendar.add(panelTop, BorderLayout.NORTH);
		panelCalendar.add(stableCalendar, BorderLayout.CENTER);
		panelCalendar.add(panelBottom, BorderLayout.SOUTH);
		
		//Set border
		panelCalendar.setBorder(BorderFactory.createTitledBorder("Calendar"));
		panelCalendar.setPreferredSize(new Dimension(370, 216));
		
		//Add headers
		String[] headers = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"}; //All headers
		for (int i=0; i<7; i++){
			mtableCalendar.addColumn(headers[i]);
		}

		//No resize/reorder
		tableCalendar.getTableHeader().setResizingAllowed(false);
		tableCalendar.getTableHeader().setReorderingAllowed(false);

		//Single cell selection
		tableCalendar.setColumnSelectionAllowed(true);
		tableCalendar.setRowSelectionAllowed(true);
		tableCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//Set row/column count
		tableCalendar.setRowHeight(20);
		mtableCalendar.setColumnCount(7);
		mtableCalendar.setRowCount(6);
		
		//Populate table
		for (int i=model.currentYear-100; i<=model.currentYear+100; i++){
			comboYear.addItem(String.valueOf(i));
		}
		comboYear.setSelectedItem(String.valueOf(model.currentYear)); //Select the correct year in the combo box
		comboYear.addActionListener(new comboYear_Action());
		
		
		//Refresh calendar
		refreshCalendar (model.currentMonth, model.currentYear); //Refresh calendar
		
		return panelCalendar;
	}
	
	public static void refreshCalendar(int month, int year){
		//Variables
		int nod, som; //Number Of Days, Start Of Month
		
		System.out.println("Month: "+month);
		
		//Allow/disallow buttons
		buttonPrev.setEnabled(true);
		buttonNext.setEnabled(true);
		if (month == 1 && year <= model.currentYear-100){buttonPrev.setEnabled(false);} //Too early
		if (month == 12 && year >= model.currentYear+100){buttonNext.setEnabled(false);} //Too late
		labelMonth.setText(View.months[month]); //Refresh the month label (at the top)
		labelMonth.setBounds(160-labelMonth.getPreferredSize().width/2, 25, 180, 25); //Re-align label with calendar
		comboYear.setSelectedItem(String.valueOf(year)); //Select the correct year in the combo box
		
		//Clear table
		for (int i=0; i<6; i++){
			for (int j=0; j<7; j++){
				mtableCalendar.setValueAt(null, i, j);
			}
		}
		
		//Get first day of month and number of days
		GregorianCalendar cal = new GregorianCalendar(year, month-1, 1);
		nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		som = cal.get(GregorianCalendar.DAY_OF_WEEK);
		if(som == 1) som = 7;
		else som--;
		
		//Draw calendar
		for (int i=1; i<=nod; i++){
			int row = new Integer((i+som-2)/7);
			int column  =  (i+som-2)%7;
			mtableCalendar.setValueAt(i, row, column);
		}

		//Apply renderers
		tableCalendar.setDefaultRenderer(tableCalendar.getColumnClass(0), new tableCalendarRenderer());
;	}

	static class tableCalendarRenderer extends DefaultTableCellRenderer{
		public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
			super.getTableCellRendererComponent(table, value, selected, focused, row, column);
			if (column == 5 || column == 6){ //Week-end
				setBackground(View.kolorWeekendu);
			}
			else{ //Week
				setBackground(new Color(255, 255, 255));
			}
			if (value != null){
				if (Integer.parseInt(value.toString()) == model.currentDay && model.currentMonth == model.checkedMonth && model.currentYear == model.checkedYear){ //Today
					setBackground(View.kolorDnia);
				}
			}
			if(focused == true)
			{
				model.checkedDay = Integer.parseInt(value.toString());
				model.mainFrame.tableDay();
				View.mainFrame.refreshTableDay();
				MainFrame.calendar.setVisible(false);
				MainFrame.currentEvent.setVisible(true);
				MainFrame.tabbedPane.setSelectedIndex(1);
			}
			if(selected == true)
			{
				setBackground(View.kolorWybranegoDnia);
			}
			setBorder(null);
			setForeground(Color.black);
			return this;  
		}
	}

	static class buttonPrev_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){

				if (model.checkedMonth == 1){ //Back one year
					model.checkedMonth = 12;
					model.checkedYear -= 1;
				}
				else{ //Back one month
					model.checkedMonth -= 1;
				}
				
				model.mainFrame.tableMonth();
				View.mainFrame.refreshTableMonth();
				
				refreshCalendar(model.checkedMonth, model.checkedYear);
			
		}
	}
	static class buttonNext_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			
				if (model.checkedMonth == 12){ //Foward one year
					model.checkedMonth = 1;
					model.checkedYear += 1;
				}
				else{ //Foward one month
					model.checkedMonth += 1;
				}
				
				model.mainFrame.tableMonth();
				View.mainFrame.refreshTableMonth();
				refreshCalendar(model.checkedMonth, model.checkedYear);
			
		}
	}
	static class comboYear_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			JComboBox cb = (JComboBox)e.getSource();
			String year = (String)cb.getSelectedItem();
			 
			Integer yearInt = new Integer(model.checkedYear);
			if (!year.equals(yearInt.toString())){
				model.checkedYear = Integer.parseInt(year);
				refreshCalendar(model.checkedMonth, model.checkedYear);
			}
		}
	}
}