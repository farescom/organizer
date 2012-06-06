package view;

import model.*;
import java.awt.*;
import java.awt.event.*;
import java.util.GregorianCalendar;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Date;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import model.Model;
import controller.Controller;

/**
* Klasa reprezentujaca ramke okna glownego.
* W tej ramce u¿ytkownik ma dostep do:
* - graficznego kalendarza,
* - aktualnych zdarzen,
* - panelu zarzadzania zdarzeniami.
*/ 
public class MainFrame extends JFrame
{
	private static final long serialVersionUID = 2L;
	public JMenuBar menuBar;
	public JMenu menu;
	public JMenuItem importOption, exportOption, exit = new JMenuItem(), settings;
	
	//public CalendarProgram calendarProgram = new CalendarProgram(this);
	public static JPanel calendar, filters, tasks, main, tab, addEvent, flowPanel, options, panelAlarm, filtersPanel;
	public static JScrollPane nextEvent, currentEvent;
	public static JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	public static JButton delete, edit, buttonAddEvent, buttonNotEditEvent, deleteDate;
	public static JComboBox przypomnienie;
	public static JTextArea opis;
	public static JTextField miejsce, godzina, minuta, godzinaAlarmu, minutaAlarmu, byPlace, byDescription;
	public static JSpinner fromDate, toDate, fromHour, fromMinute, toHour, toMinute;
	public SpinnerModel spinnerModel;
	public static String data_rozpoczecia, data_zakonczenia, data_alarmu;
	public static JLabel lblDataRozpoczecia = new JLabel(), lblDataZakonczenia = new JLabel(), lblDataAlarmu = new JLabel();
	public static JLabel lblDataStartDelete = new JLabel(), lblDataEndDelete = new JLabel();
	public static JTabbedPane tabbedPane;
	public static ImagePanel data_roz = new ImagePanel(0, 16, 16, "data.png");
	public static ImagePanel data_zak = new ImagePanel(0, 16, 16, "data.png");
	public static ImagePanel dataAlarmu = new ImagePanel(0, 16, 16, "data.png");
	public static ImagePanel dataStartDelete = new ImagePanel(0, 16, 16, "data.png");
	public static ImagePanel dataEndDelete = new ImagePanel(0, 16, 16, "data.png");
	public static JCheckBox filterDate = new JCheckBox(), filterHour  = new JCheckBox(), filterPlace = new JCheckBox();
	public static JCheckBox filterDescription = new JCheckBox(), filterDelete = new JCheckBox();
	public Object source = null;
	GridBagConstraints c = new GridBagConstraints();
	
	public int location_x;
	public int location_y;
	
	public static Model model;
	
	/**
	* Konstruktor klasy odpowiadajacy za utworzenie okna i wstawieniu do tego okna elementow.
	* @param tytul tytul wyswietlany na gorze okna
	* @param x szerokosc okna
	* @param y wysokosc okna
	* @param locationX przesuniecie x okna od lewej krawedzi ekranu
	* @param locationY przesuniecie y okna od gornej krawedzi ekranu
	*/ 
 public MainFrame(String tytul, int x, int y, int locationX, int locationY)
    {  
        super(tytul);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        setSize(x, y); 
        location_x = locationX;
        location_y = locationY;
        setLocation(locationX,locationY);
        setVisible(true); 
        setLayout(new FlowLayout(FlowLayout.LEFT));
        
        main = new JPanel(new GridBagLayout());
        
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 0, 0, 0);
		
		tab = new JPanel(new FlowLayout(FlowLayout.LEFT));
		tabbedPane = new JTabbedPane(JTabbedPane.RIGHT);
		tabbedPane.addTab("Calendar", null, null, "Show calendar");
		tabbedPane.addTab(" "+model.checkedDay + " " + model.months[model.checkedMonth] + " Event", null, null, "Show events");
		tabbedPane.addTab("Add Event", null, null, "Add event");
		tabbedPane.addChangeListener(Controller.mainFrameEvent);
		tab.add(tabbedPane);
		
		
		c.gridx = 0;
		c.gridy = 0;
		main.add(tab, c);
		
		c.gridx = 0;
		c.gridy = 1;
		View.calendar = new Calendar();
		calendar = View.calendar.createProgram();
		options = new JPanel();
		
		currentEvent = new JScrollPane(model.mainFrame.tableDay);
		currentEvent.setBorder(BorderFactory.createTitledBorder(model.checkedDay + " " + model.months[model.checkedMonth] + " Event"));
		currentEvent.setPreferredSize(new Dimension(370, 220));
		currentEvent.setVisible(false);
		addEvent(c);
		
		options.add(currentEvent);
		options.add(calendar);
		options.add(addEvent);
		
		main.add(options, c);
		
		c.gridx = 0;
		c.gridy = 2;
		tasks = new JPanel(new GridLayout(2, 1));
		tasks.setBorder(BorderFactory.createTitledBorder("Tasks"));
		
		flowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		delete = new JButton("Delete");
		edit = new JButton("Edit");
		edit.setEnabled(false);
		delete.setEnabled(false);
		flowPanel.add(edit);
		flowPanel.add(delete);
		tasks.add(flowPanel);
		
		flowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		/*filterDelete = new JCheckBox();
		filterDelete.addItemListener(Controller.mainFrameEvent);
		flowPanel.add(filterDelete);*/
		flowPanel.add(new JLabel("Delete events from "));
		flowPanel.add(dataStartDelete);
		flowPanel.add(lblDataStartDelete);
		flowPanel.add(new JLabel(" to "));
		flowPanel.add(dataEndDelete);
		flowPanel.add(lblDataEndDelete);
		dataStartDelete.addMouseListener(Controller.mainFrameEvent);
		dataEndDelete.addMouseListener(Controller.mainFrameEvent);
		deleteDate = new JButton("Delete");
		flowPanel.add(deleteDate);
		deleteDate.addActionListener(Controller.mainFrameEvent);
		tasks.add(flowPanel);
		
		edit.addActionListener(Controller.mainFrameEvent);
		delete.addActionListener(Controller.mainFrameEvent);
		
		main.add(tasks, c);
		
		addFilters(c);
			
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 3;
		
		nextEvent = new JScrollPane(model.mainFrame.tableMonth);
		nextEvent.setPreferredSize(new Dimension(320, 240));
		nextEvent.setBorder(BorderFactory.createTitledBorder(model.months[Model.checkedMonth] + " Event"));
		
		splitPane.setLeftComponent(filtersPanel);
		splitPane.setRightComponent(nextEvent);
		
		main.add(splitPane, c);
		
		add(main);
		
		 menuBar = new JMenuBar();
	        menu = new JMenu("File");
	     	importOption = new JMenuItem("Import", KeyEvent.VK_F1);
	     	importOption.addActionListener(Controller.mainFrameEvent);
	     	exportOption = new JMenuItem("Export", KeyEvent.VK_F2);
	     	exportOption.addActionListener(Controller.mainFrameEvent);
	     	settings = new JMenuItem("Settings", KeyEvent.VK_F3);
	     	settings.addActionListener(Controller.mainFrameEvent);
	     	exit = new JMenuItem("Exit", KeyEvent.VK_F4);
	     	exit.addActionListener(Controller.mainFrameEvent);
	       menu.add(importOption);
	       menu.add(exportOption);
	       menu.add(settings);
	       menu.add(exit);
	     menuBar.add(menu);
	     this.setJMenuBar(menuBar);
    }
 
 
 	/**
	* Metoda odpowiadajaca za wyswietlenie panelu filtrow w glownym oknie aplikacji (MainFrame)
	* @param c - ogranicznik dla layoutu GridBagLayout wykorzystywanym przy tworzeniu glownego okna aplikacji
	*/
 	public void addFilters(GridBagConstraints c){
 		
 		c.insets = new Insets(-3, 0, 0, 0);
		filtersPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, -4));
		filtersPanel.setPreferredSize(new Dimension(320, 100));
		filtersPanel.setBorder(BorderFactory.createTitledBorder("Filters"));
		filters = new JPanel(new GridBagLayout());
			
			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 2;
			flowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			flowPanel.add(new JLabel("Show events:"));
			filters.add(flowPanel, c);
		
			c.gridwidth = 1;
			c.gridx = 0;
			c.gridy = 1;
			flowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			filterDate = new JCheckBox();
			filterDate.addItemListener(Controller.mainFrameEvent);
			flowPanel.add(filterDate);
			filters.add(flowPanel, c);
			
			c.gridx = 0;
			c.gridy = 2;
			flowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			filterHour = new JCheckBox();
			filterHour.addItemListener(Controller.mainFrameEvent);
			flowPanel.add(filterHour);
			filters.add(flowPanel, c);
			
			c.gridx = 0;
			c.gridy = 4;
			flowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			filterPlace = new JCheckBox();
			filterPlace.addItemListener(Controller.mainFrameEvent);
			flowPanel.add(filterPlace);
			filters.add(flowPanel, c);
			
			c.gridx = 0;
			c.gridy = 5;
			flowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			filterDescription = new JCheckBox();
			filterDescription.addItemListener(Controller.mainFrameEvent);
			flowPanel.add(filterDescription);
			filters.add(flowPanel, c);
			
			
			c.gridx = 1;
			c.gridy = 1;
			GregorianCalendar cal = new GregorianCalendar(model.currentYear, model.currentMonth-1, 1);
			int nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
			flowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			flowPanel.add(new JLabel("From "));
			spinnerModel = new SpinnerNumberModel(model.currentDay, 0, nod, 1);  
				fromDate = new JSpinner(spinnerModel);
				((JSpinner.DefaultEditor)fromDate.getEditor()).getTextField().setEditable(false);
				fromDate.addChangeListener(Controller.mainFrameEvent);
				fromDate.setEnabled(false);
			flowPanel.add(fromDate);
			flowPanel.add(new JLabel(" to "));
			spinnerModel = new SpinnerNumberModel(nod, 0, nod, 1);  
				toDate = new JSpinner(spinnerModel);
				((JSpinner.DefaultEditor)toDate.getEditor()).getTextField().setEditable(false);
				toDate.addChangeListener(Controller.mainFrameEvent);
				toDate.setEnabled(false);
			flowPanel.add(toDate);
			flowPanel.add(new JLabel(" Day"));
			filters.add(flowPanel, c);
			
			c.gridx = 1;
			c.gridy = 2;
			flowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			flowPanel.add(new JLabel("From: "));
			flowPanel.add(new JLabel("Hour: "));
				spinnerModel = new SpinnerNumberModel(12, 0, 23, 1);  
				fromHour = new JSpinner(spinnerModel);
				((JSpinner.DefaultEditor)fromHour.getEditor()).getTextField().setEditable(false);
				fromHour.addChangeListener(Controller.mainFrameEvent);
				fromHour.setEnabled(false);
			flowPanel.add(fromHour);
			flowPanel.add(new JLabel("  Minute: "));
				spinnerModel = new SpinnerNumberModel(00, 0, 59, 1);  
				fromMinute = new JSpinner(spinnerModel);
				((JSpinner.DefaultEditor)fromMinute.getEditor()).getTextField().setEditable(false);
				fromMinute.addChangeListener(Controller.mainFrameEvent);
				fromMinute.setEnabled(false);
			flowPanel.add(fromMinute);
			filters.add(flowPanel, c);
			
			c.gridx = 1;
			c.gridy = 3;
			flowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			flowPanel.add(new JLabel("To: "));
			flowPanel.add(new JLabel("     Hour: "));
				spinnerModel = new SpinnerNumberModel(20, 0, 23, 1);  
				toHour = new JSpinner(spinnerModel);
				((JSpinner.DefaultEditor)toHour.getEditor()).getTextField().setEditable(false);
				toHour.addChangeListener(Controller.mainFrameEvent);
				toHour.setEnabled(false);
			flowPanel.add(toHour);
			flowPanel.add(new JLabel("  Minute: "));
				spinnerModel = new SpinnerNumberModel(00, 0, 59, 1);  
				toMinute = new JSpinner(spinnerModel);
				((JSpinner.DefaultEditor)toMinute.getEditor()).getTextField().setEditable(false);
				toMinute.addChangeListener(Controller.mainFrameEvent);
				toMinute.setEnabled(false);
			flowPanel.add(toMinute);
			filters.add(flowPanel, c);
			
			c.gridx = 1;
			c.gridy = 4;
			flowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			flowPanel.add(new JLabel("By place: "));
			byPlace = new JTextField(10);
			byPlace.setEnabled(false);
			byPlace.getDocument().addDocumentListener(Controller.mainFrameEvent);
			flowPanel.add(byPlace);
			filters.add(flowPanel, c);
			
			c.gridx = 1;
			c.gridy = 5;
			flowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			flowPanel.add(new JLabel("By description: "));
			byDescription = new JTextField(15);
			byDescription.setEnabled(false);
			byDescription.getDocument().addDocumentListener(Controller.mainFrameEvent);
			flowPanel.add(byDescription);
			filters.add(flowPanel, c);
			
		filtersPanel.add(filters);
		c.insets = new Insets(1, 1, 1, 1);
 	}
 
 	/**
	* Metoda odpowiadajaca za wyswietlenie panelu dodawania zdarzen w glownym oknie aplikacji (MainFrame)
	* @param c - ogranicznik dla layoutu GridBagLayout wykorzystywanym przy tworzeniu glownego okna aplikacji
	*/
 	public void addEvent(GridBagConstraints c)
 	{
 		GridLayout layout = new GridLayout(6, 1);
 		layout.setVgap(1);
 		addEvent = new JPanel(layout);
 		
 		
		addEvent.setBorder(BorderFactory.createTitledBorder("Add Event"));
		addEvent.setPreferredSize(new Dimension(370, 300));
		addEvent.setVisible(false);
		
		
		opis = new JTextArea(2, 25);
		opis.setLineWrap(true);
		opis.setTabSize(200);
		JScrollPane textAreaPane = new JScrollPane(opis);
		miejsce = new JTextField(10);
		data_rozpoczecia = new String();
		data_zakonczenia = new String();
		data_alarmu = new String();
		String[] takCzyNie = {"no", "yes"};
		przypomnienie = new JComboBox(takCzyNie);
		godzina = new JTextField(2);
		minuta = new JTextField(2);
		godzinaAlarmu = new JTextField(2);
		minutaAlarmu = new JTextField(2);
		buttonAddEvent = new JButton("Add Event");
		buttonNotEditEvent = new JButton("Do Not Edit Event");
		
		flowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel describe = new JLabel("Description: ");
		flowPanel.setAlignmentY(TOP_ALIGNMENT);
		flowPanel.add(describe);
		flowPanel.add(textAreaPane);
		addEvent.add(flowPanel);	
		
		flowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		flowPanel.add(new JLabel("Place: "));
		flowPanel.add(miejsce);
		addEvent.add(flowPanel);
		
		flowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		flowPanel.add(new JLabel("Date of start: "));
		flowPanel.add(data_roz);
		lblDataRozpoczecia.setText(data_rozpoczecia);
		flowPanel.add(lblDataRozpoczecia);
		if(model.mainFrame.startDay != 0 && model.mainFrame.startMonth != 0 && model.mainFrame.startYear !=0)
			lblDataRozpoczecia.setText(model.mainFrame.startDay + " - " + model.mainFrame.startMonth + " - " + model.mainFrame.startYear);
		flowPanel.add(new JLabel("Hour: "));
		flowPanel.add(godzina);
		flowPanel.add(new JLabel("Minute: "));
		flowPanel.add(minuta);
		data_roz.addMouseListener(Controller.mainFrameEvent);
		addEvent.add(flowPanel);
		
		/*flowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		flowPanel.add(new JLabel("Select date of end: "));
		flowPanel.add(data_zak);
		lblDataZakonczenia.setText(data_zakonczenia);
		flowPanel.add(lblDataZakonczenia);
		data_zak.addMouseListener(Controller.mainFrameEvent);
		addEvent.add(flowPanel);*/
		
		flowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		flowPanel.add(new JLabel("Reminder: "));
		flowPanel.add(przypomnienie);
		przypomnienie.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				String napis = (String)cb.getSelectedItem();
				if(napis == "yes")
				{
					panelAlarm.setVisible(true);
				}
				else{
					panelAlarm.setVisible(false);
				}
			}
		});
		addEvent.add(flowPanel);
		
		panelAlarm = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelAlarm.add(new JLabel("Date of alarm: "));
		panelAlarm.add(dataAlarmu);
		lblDataAlarmu.setText(data_alarmu);
		panelAlarm.add(lblDataAlarmu);
		panelAlarm.add(new JLabel("Hour: "));
		panelAlarm.add(godzinaAlarmu);
		panelAlarm.add(new JLabel("Minute: "));
		panelAlarm.add(minutaAlarmu);
		dataAlarmu.addMouseListener(Controller.mainFrameEvent);
		panelAlarm.setVisible(false);
		addEvent.add(panelAlarm);
		
		flowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		flowPanel.add(buttonAddEvent);
		buttonAddEvent.addActionListener(Controller.mainFrameEvent);
		flowPanel.add(buttonNotEditEvent);
		buttonNotEditEvent.addActionListener(Controller.mainFrameEvent);
		buttonNotEditEvent.setVisible(false);
		addEvent.add(flowPanel);
 	}
 
 	/**
	* Metoda odpowiadajaca za odswie¿enie panelu "currentEvent" wyswietlajacego zdarzenia z danego dnia
	*/
 	public void refreshTableDay()
 	{
 		options.remove(currentEvent);
 		
 		currentEvent = new JScrollPane(model.mainFrame.tableDay);
		currentEvent.setBorder(BorderFactory.createTitledBorder(model.checkedDay + " " + model.months[model.checkedMonth] + " Event"));
		currentEvent.setPreferredSize(new Dimension(370, 215));
		currentEvent.setVisible(true);

		options.add(currentEvent);
		
		this.invalidate();
		this.validate();
 	}
 	
 	/**
	* Metoda odpowiadajaca za odswie¿enie panelu "nextEvent" wyswietlajacego zdarzenia z danego miesiaca
	*/
 	public void refreshTableMonth()
 	{	
 		nextEvent = new JScrollPane(model.mainFrame.tableMonth);
		nextEvent.setPreferredSize(new Dimension(320, 240));
		nextEvent.setBorder(BorderFactory.createTitledBorder(model.months[Model.checkedMonth] + " Event"));

		splitPane.setRightComponent(nextEvent); 

		this.invalidate();
		this.validate();
 	}
 	
 	/**
	* Metoda odpowiadajaca za wyswietlenie panelu edycji zdarzen w glownym oknie aplikacji (MainFrame)
	*/
 	public void editEvent(){
 		
 		Zdarzenie editZdarzenie = new Zdarzenie(model.mainFrame.dayEvent.get(model.mainFrame.rowSelectedDay));
 		
 		tabbedPane.setTitleAt(2, "Edit Event");
 		tabbedPane.setSelectedIndex(2);
 		tabbedPane.setEnabledAt(0, false);
 		tabbedPane.setEnabledAt(1, false);
 		addEvent.setVisible(true);
 		currentEvent.setVisible(false);
 		model.mainFrame.tableMonth.setRowSelectionAllowed(false);
 		filterDate.setEnabled(false);
 		filterHour.setEnabled(false);
 		buttonAddEvent.setText("Edit Event");
 		buttonNotEditEvent.setVisible(true);
 		opis.setText(editZdarzenie.opis);
 		miejsce.setText(editZdarzenie.miejsce);
 		data_rozpoczecia = editZdarzenie.data_rozpoczecia;
 		model.mainFrame.startDay = Integer.parseInt(data_rozpoczecia.substring(8, 10));
 		model.mainFrame.startMonth = Integer.parseInt(data_rozpoczecia.substring(5, 7));
 		model.mainFrame.startYear = Integer.parseInt(data_rozpoczecia.substring(0, 4));
 		godzina.setText(editZdarzenie.godzina.substring(0, 2));
 		minuta.setText(editZdarzenie.godzina.substring(3, 5));
 		
 		lblDataRozpoczecia.setText(model.mainFrame.startDay + " - " + model.mainFrame.startMonth + " - " + model.mainFrame.startYear);
 		
 		if(editZdarzenie.waznosc > 0){
 			
 			System.out.println(editZdarzenie.waznosc);
 			
 			przypomnienie.setSelectedIndex(1);
 			panelAlarm.setVisible(true);
 			
 			Date alarmDate = new Date();
 	 		alarmDate.setYear(model.mainFrame.startYear);
 	 		alarmDate.setMonth(model.mainFrame.startMonth);
 	 		alarmDate.setDate(model.mainFrame.startDay);
 	 		alarmDate.setHours(Integer.parseInt(editZdarzenie.godzina.substring(0, 2)));
 	 		alarmDate.setMinutes(Integer.parseInt(editZdarzenie.godzina.substring(3, 5)));
 	 		
 	 		long miliseconds = alarmDate.getTime() - editZdarzenie.waznosc*60*1000;
 	 		alarmDate = new Date(miliseconds);
 	 		
 	 		
 	 		if(alarmDate.getDay()<10) alarmDate.setDate(Integer.parseInt("0"+alarmDate.getDay()));
 	 		
 	 		data_alarmu = alarmDate.getYear()+"";
 	 		if(alarmDate.getMonth()<10) data_alarmu += "-0"+alarmDate.getMonth();
 	 		else data_alarmu += "-"+alarmDate.getMonth();
 	 		if(alarmDate.getDay()<10) data_alarmu += "-0"+alarmDate.getDay();
 	 		else data_alarmu += "-"+alarmDate.getDay();
 	 		
 	 		System.out.println("Data alarmu: "+ data_alarmu);
 	 		model.mainFrame.alarmDay = Integer.parseInt(data_alarmu.substring(8, 10));
 	 		model.mainFrame.alarmMonth = Integer.parseInt(data_alarmu.substring(5, 7));
 	 		model.mainFrame.alarmYear = Integer.parseInt(data_alarmu.substring(0, 4));
 	 		
 	 		System.out.println(new Integer(alarmDate.getHours()).toString());
 	 		
 	 		godzinaAlarmu.setText(new Integer(alarmDate.getHours()).toString());
 	 		minutaAlarmu.setText(new Integer(alarmDate.getMinutes()).toString());
 	 		
 	 		lblDataAlarmu.setText(model.mainFrame.alarmDay + " - " + model.mainFrame.alarmMonth + " - " + model.mainFrame.alarmYear);
 		}
 		
 	}
 	
 	/**
	* Metoda odpowiadajaca za zakonczenie wyswietlania panelu edycji zdarzen w glownym oknie aplikacji (MainFrame)
	*/
 	public void notEditEvent(){
 		
 		tabbedPane.setTitleAt(2, "Add Event");
 		tabbedPane.setSelectedIndex(1);
 		tabbedPane.setEnabledAt(0, true);
 		tabbedPane.setEnabledAt(1, true);
 		addEvent.setVisible(false);
 		currentEvent.setVisible(true);
 		model.mainFrame.tableMonth.setRowSelectionAllowed(true);
 		filterDate.setEnabled(true);
 		filterHour.setEnabled(true);
 		buttonAddEvent.setText("Add Event");
 		buttonNotEditEvent.setVisible(false);
		opis.setText("");
		miejsce.setText("");
		godzina.setText("");
		minuta.setText("");
		godzinaAlarmu.setText("");
		minutaAlarmu.setText("");
		lblDataRozpoczecia.setText("");
		lblDataAlarmu.setText("");
		przypomnienie.setSelectedIndex(0);
 	}
 	
 	private JComponent makeTextPanel(String string) {
		// TODO Auto-generated method stub
		return null;
	}
}