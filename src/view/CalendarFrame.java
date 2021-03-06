package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.Controller;

import view.Calendar.tableCalendarRenderer;

import model.Model;

/**
* Klasa odpowiadajaca za wyswietlenie ramki kalendarza w momencie wyboru daty rozpoczecia zdarzenia
* badz w momencie wyboru daty alarmu
*/ 
public class CalendarFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public CalendarProgram calendarProgram;
	public JPanel calendar;
	
	public static JLabel labelMonth, labelYear;
	public static JButton buttonPrev, buttonNext;
	public static JTable tableCalendar;
	public static JComboBox comboYear;
	public static JFrame frameMain;
	public static DefaultTableModel mtableCalendar; //Table model
	public static JScrollPane stableCalendar; //The scrollpane
	public static JPanel panelCalendar, panelTop, panelBottom, panelMonth;
	
	public static Model model;
	
	/**
	* Konstruktor klasy CalendarFrame odpowiedzialny za utworzenie ramki kalendarza
	* @param tytul tytul wyswietlany na gorze okna
	* @param SizeX szerokosc okna
	* @param SizeY wysokosc okna
	* @param x przesuniecie x okna od lewej krawedzi ekranu
	* @param y przesuniecie y okna od gornej krawedzi ekranu
	* @param mainFrame obiekt typu MainFrame - dostep do skladowych klasy MainFrame
	*/ 
	public CalendarFrame(String tytul, int SizeX, int SizeY, int x, int y, MainFrame mainFrame)
    {  
        super(tytul);  
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);  
        setSize(SizeX, SizeY);
        setLocation(x,y);  
        setResizable(false);
        setVisible(true);
        
        calendarProgram = new CalendarProgram(mainFrame);
		calendar = calendarProgram.createProgram();
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent evt) {
				View.mainFrame.source = null;
			  }
		});
		add(calendar);
    } 
	
	/**
	* Klasa wewnetrzna odpowiadajaca za wyswietlenie kalendarza w ramce kalendarza (w CalendarFrame)
	*/ 
	public static class CalendarProgram{

		static MainFrame mainFrame;
		
		/**
		* Konstruktor klasy CalendarProgram odpowiedzialnej za umieszczenie elementow w ramce kalendarza (w CalendarFrame)
		* @param _mainFrame obiekt typu MainFrame - dostep do skladowych klasy MainFrame
		*/
		public CalendarProgram(MainFrame _mainFrame){
			this.mainFrame = _mainFrame;
		}
		
		/**
		* Metoda odpowiadajaca za umieszczenie elementow w ramce kalendarza (w CalendarFrame)
		* @return JPanel - zwraca panel kalendarza, ktory bedzie wstawiony do ramki kalendarza (CalendarFrame)
		*/ 
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
			buttonPrev.addActionListener(Controller.mainFrameEvent);
			buttonNext.addActionListener(Controller.mainFrameEvent);
			
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
			comboYear.addActionListener(Controller.mainFrameEvent);
			
			
			//Refresh calendar
			refreshCalendar (model.currentMonth, model.currentYear); //Refresh calendar
			
			return panelCalendar;
		}
		
		/**
		* Metoda odpowiadajaca za wyswietlenie kalendarza w ramce kalendarza (CalendarFrame) po wykonaniu operacji na nim
		* (odswiezenie kalendarza)
		* @param month - wybrany miesiac na kalendarzu
		* @param year - wybrany rok na kalendarzu
		*/ 
		public static void refreshCalendar(int month, int year){
			//Variables
			int nod, som; //Number Of Days, Start Of Month
			
			//Allow/disallow buttons
			buttonPrev.setEnabled(true);
			buttonNext.setEnabled(true);
			if (month == 1 && year <= model.currentYear-100){buttonPrev.setEnabled(false);} //Too early
			if (month == 12 && year >= model.currentYear+100){buttonNext.setEnabled(false);} //Too late
			labelMonth.setText(model.months[month]); //Refresh the month label (at the top)
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
		}
		
		/**
		* Klasa wewnetrzna, ktora okre�la styl renderowania komorek tabeli w kalendarzu w ramce kalendarza (w CalendarFrame)
		*/ 
		static class tableCalendarRenderer extends DefaultTableCellRenderer{
			public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
				super.getTableCellRendererComponent(table, value, selected, focused, row, column);
				if (column == 5 || column == 6){ //Week-end
					setBackground(model.kolorWeekendu);
				}
				else{ //Week
					setBackground(new Color(255, 255, 255));
				}
				if (value != null){
					if (Integer.parseInt(value.toString()) == model.currentDay && model.currentMonth == model.checkedMonth && model.currentYear == model.checkedYear){ //Today
						setBackground(model.kolorDnia);
					}
				}
				if(focused == true && mainFrame.source != null)
				{
					if(value != null){
						
						if(mainFrame.source == mainFrame.data_roz){
							model.mainFrame.startDay = Integer.parseInt(value.toString());
							View.calendarFrame.dispose();
							System.out.println("Data rozpoczecia: " + model.mainFrame.startDay + " - " + model.mainFrame.startMonth + " - " + model.mainFrame.startYear);
							View.mainFrame.lblDataRozpoczecia.setText(model.mainFrame.startDay + " - " + model.mainFrame.startMonth + " - " + model.mainFrame.startYear);
							mainFrame.source = null;
						}
						else if(mainFrame.source == mainFrame.data_zak){
							model.mainFrame.finishDay = Integer.parseInt(value.toString());
							View.calendarFrame.dispose();
							System.out.println("Data zakonczenia: " + model.mainFrame.finishDay + " - " + model.mainFrame.finishMonth + " - " + model.mainFrame.finishYear);
							View.mainFrame.lblDataZakonczenia.setText(model.mainFrame.finishDay + " - " + model.mainFrame.finishMonth + " - " + model.mainFrame.finishYear);
							mainFrame.source = null;
						}
						else if(mainFrame.source == mainFrame.dataAlarmu){
							model.mainFrame.alarmDay = Integer.parseInt(value.toString());
							View.calendarFrame.dispose();
							System.out.println("Data alarmu: " + model.mainFrame.alarmDay + " - " + model.mainFrame.alarmMonth + " - " + model.mainFrame.alarmYear);
							View.mainFrame.lblDataAlarmu.setText(model.mainFrame.alarmDay + " - " + model.mainFrame.alarmMonth + " - " + model.mainFrame.alarmYear);
							mainFrame.source = null;
						}
						else if(mainFrame.source == mainFrame.dataStartDelete){
							model.mainFrame.deleteStartDay = Integer.parseInt(value.toString());
							View.calendarFrame.dispose();
							System.out.println("Data poczatku usuwania: " + model.mainFrame.deleteStartDay + " - " + model.mainFrame.deleteStartMonth + " - " + model.mainFrame.deleteStartYear);
							View.mainFrame.lblDataStartDelete.setText(model.mainFrame.deleteStartDay + " - " + model.mainFrame.deleteStartMonth + " - " + model.mainFrame.deleteStartYear);
							mainFrame.source = null;
						}
						else if(mainFrame.source == mainFrame.dataEndDelete){
							model.mainFrame.deleteEndDay = Integer.parseInt(value.toString());
							View.calendarFrame.dispose();
							System.out.println("Data konca usuwania: " + model.mainFrame.deleteEndDay + " - " + model.mainFrame.deleteEndMonth + " - " + model.mainFrame.deleteEndYear);
							View.mainFrame.lblDataEndDelete.setText(model.mainFrame.deleteEndDay + " - " + model.mainFrame.deleteEndMonth + " - " + model.mainFrame.deleteEndYear);
							mainFrame.source = null;
						}
					}
				}
				if(selected == true)
				{
					setBackground(model.kolorWybranegoDnia);
				}
				setBorder(null);
				setForeground(Color.black);
				return this;  
			}
		}
	}
}
