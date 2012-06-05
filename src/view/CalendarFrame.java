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

import view.Calendar.buttonNext_Action;
import view.Calendar.buttonPrev_Action;
import view.Calendar.comboYear_Action;
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
	* Klasa wewnêtrzna odpowiadajaca za wyswietlenie kalendarza w ramce kalendarza (w CalendarFrame)
	*/ 
	static class CalendarProgram{
		
		public static JLabel labelMonth, labelYear;
		public static JButton buttonPrev, buttonNext;
		public static JTable tableCalendar;
		public static JComboBox comboYear;
		public static JFrame frameMain;
		public static DefaultTableModel mtableCalendar; //Table model
		public static JScrollPane stableCalendar; //The scrollpane
		public static JPanel panelCalendar, panelTop, panelBottom, panelMonth;

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
		* @return JPanel - zwraca panel kalendarza, który bêdzie wstawiony do ramki kalendarza (CalendarFrame)
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
		}
		
		/**
		* Klasa wewnêtrzna, która okreœla styl renderowania komórek tabeli w kalendarzu w ramce kalendarza (w CalendarFrame)
		*/ 
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
					}
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

		/**
		* Klasa wewnêtrzna, która obs³uguje zdarzenia dla przycisku wyboru miesi¹ca (1 miesi¹c wstecz) dla kalendarza
		* w ramce kalendarza (w CalendarFrame)
		*/
		static class buttonPrev_Action implements ActionListener{
			public void actionPerformed (ActionEvent e){
				if(mainFrame.source == mainFrame.data_roz){
					if (model.mainFrame.startMonth == 1){ //Back one year
						model.mainFrame.startMonth = 12;
						model.mainFrame.startYear -= 1;
					}
					else{ //Back one month
						model.mainFrame.startMonth -= 1;
					}
					
					refreshCalendar(model.mainFrame.startMonth, model.mainFrame.startYear);
				}
				else if(mainFrame.source == mainFrame.data_zak){
					if (model.mainFrame.finishMonth == 1){ //Back one year
						model.mainFrame.finishMonth = 12;
						model.mainFrame.finishYear -= 1;
					}
					else{ //Back one month
						model.mainFrame.finishMonth -= 1;
					}
					
					refreshCalendar(model.mainFrame.finishMonth, model.mainFrame.finishYear);
				}
				else if(mainFrame.source == mainFrame.dataAlarmu){
					if (model.mainFrame.alarmMonth == 1){ //Back one year
						model.mainFrame.alarmMonth = 12;
						model.mainFrame.alarmYear -= 1;
					}
					else{ //Back one month
						model.mainFrame.alarmMonth -= 1;
					}
					
					refreshCalendar(model.mainFrame.alarmMonth, model.mainFrame.alarmYear);
				}
			}
		}
		
		/**
		* Klasa wewnêtrzna, która obs³uguje zdarzenia dla przycisku wyboru miesi¹ca (1 miesi¹c dalej) dla kalendarza
		* w ramce kalendarza (w CalendarFrame)
		*/
		static class buttonNext_Action implements ActionListener{
			public void actionPerformed (ActionEvent e){
				
				if(mainFrame.source == mainFrame.data_roz){
					if (model.mainFrame.startMonth == 12){ //Foward one year
						model.mainFrame.startMonth = 1;
						model.mainFrame.startYear += 1;
					}
					else{ //Foward one month
						model.mainFrame.startMonth += 1;
					}
					
					refreshCalendar(model.mainFrame.startMonth, model.mainFrame.startYear);
				}
				else if(mainFrame.source == mainFrame.data_zak){
					if (model.mainFrame.finishMonth == 12){ //Foward one year
						model.mainFrame.finishMonth = 1;
						model.mainFrame.finishYear += 1;
					}
					else{ //Foward one month
						model.mainFrame.finishMonth += 1;
					}
					
					refreshCalendar(model.mainFrame.finishMonth, model.mainFrame.finishYear);
				}
				else if(mainFrame.source == mainFrame.dataAlarmu){
					if (model.mainFrame.alarmMonth == 12){ //Foward one year
						model.mainFrame.alarmMonth = 1;
						model.mainFrame.alarmYear += 1;
					}
					else{ //Foward one month
						model.mainFrame.alarmMonth += 1;
					}
					
					refreshCalendar(model.mainFrame.alarmMonth, model.mainFrame.alarmYear);
				}
			}
		}
		
		/**
		* Klasa wewnêtrzna, która obs³uguje zdarzenia dla przycisku wyboru roku dla kalendarza
		* w ramce kalendarza (w CalendarFrame)
		*/
		static class comboYear_Action implements ActionListener{
			public void actionPerformed (ActionEvent e){
				JComboBox cb = (JComboBox)e.getSource();
				String year = (String)cb.getSelectedItem();
				
				if(mainFrame.source == mainFrame.data_roz){
					Integer yearInt = new Integer(model.mainFrame.startYear);
					if (!year.equals(yearInt.toString())){
						model.mainFrame.startYear = Integer.parseInt(year);
						refreshCalendar(model.mainFrame.startMonth, model.mainFrame.startYear);
					}
				}
				else if(mainFrame.source == mainFrame.data_zak){
					Integer yearInt = new Integer(model.mainFrame.finishYear);
					if (!year.equals(yearInt.toString())){
						model.mainFrame.finishYear = Integer.parseInt(year);
						refreshCalendar(model.mainFrame.finishMonth, model.mainFrame.finishYear);
					}
				}
				else if(mainFrame.source == mainFrame.dataAlarmu){
					Integer yearInt = new Integer(model.mainFrame.alarmYear);
					if (!year.equals(yearInt.toString())){
						model.checkedYear = Integer.parseInt(year);
						refreshCalendar(model.mainFrame.alarmMonth, model.mainFrame.alarmYear);
					}
				}
			}
		}
	}
}
