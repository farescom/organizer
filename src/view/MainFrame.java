package view;

import model.*;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.sql.Date;
import java.text.DateFormatSymbols;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import model.Model;
import controller.Controller;

public class MainFrame extends JFrame
{
	private static final long serialVersionUID = 2L;
	public JMenuBar menuBar;
	public JMenu menu;
	public JMenuItem importOption, exportOption, exit, settings;
	
	//public CalendarProgram calendarProgram = new CalendarProgram(this);
	public static JPanel calendar, statistics, tasks, main, tab, addEvent, flowPanel;
	public static JScrollPane nextEvent, currentEvent;
	public JSplitPane splitPane;
	public JButton delete, edit, buttonAddEvent;
	public JComboBox przypomnienie;
	public JTextArea opis;
	public JTextField miejsce, godzina, minuta;
	public String data_rozpoczecia, data_zakonczenia;
	public static JTabbedPane tabbedPane;
	
	public int location_x;
	public int location_y;
	
	public static Model model;
	
 
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
        
        GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(1, 1, 1, 1);
		
		tab = new JPanel(new FlowLayout(FlowLayout.LEFT));
		tabbedPane = new JTabbedPane(JTabbedPane.RIGHT);
		tabbedPane.addTab("Calendar", null, null, "1 Nothing");
		tabbedPane.addTab("Current Event", null, null, "2 Nothing");
		tabbedPane.addTab("Add Event", null, null, "3 Nothing");
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
		tabbedPane.addChangeListener(Controller.mainFrameEvent);
		tab.add(tabbedPane);
		
		
		c.gridx = 0;
		c.gridy = 0;
		main.add(tab, c);
		
		c.gridx = 0;
		c.gridy = 1;
		View.calendarProgram = new CalendarProgram(this);
		calendar = View.calendarProgram.createProgram();
		currentEvent = new JScrollPane(model.mainFrame.tableDay());
		currentEvent.setBorder(BorderFactory.createTitledBorder("Current Event"));
		currentEvent.setPreferredSize(new Dimension(370, 160));
		currentEvent.setVisible(false);
		addEvent(c);
		
		main.add(currentEvent, c);
		main.add(calendar, c);
		main.add(addEvent, c);
		
		c.gridx = 0;
		c.gridy = 2;
		tasks = new JPanel();
		tasks.setBorder(BorderFactory.createTitledBorder("Tasks"));
		delete = new JButton("Delete");

		edit = new JButton("Edit");
		
		tasks.add(edit);
		tasks.add(delete);
		
		edit.addActionListener(Controller.mainFrameEvent);
		delete.addActionListener(Controller.mainFrameEvent);
		
		main.add(tasks, c);
		
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 3;
		statistics = new JPanel();
		statistics.setPreferredSize(new Dimension(320, 100));
		statistics.setBorder(BorderFactory.createTitledBorder("Statistics"));
		
		model.baza.get("SELECT * FROM "+model.baza.table, model.zdarzenia);
		
		nextEvent = new JScrollPane(model.mainFrame.tableMonth());
		nextEvent.setPreferredSize(new Dimension(320, 240));
		nextEvent.setBorder(BorderFactory.createTitledBorder(View.months[Model.checkedMonth] + " Event"));

		nextEvent.add(new JButton("fr"));
		
		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		
		splitPane.setLeftComponent(statistics);
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
 
 
 	public void addEvent(GridBagConstraints c)
 	{
 		addEvent = new JPanel(new GridLayout(4, 1));
 		
		addEvent.setBorder(BorderFactory.createTitledBorder("Add Event"));
		addEvent.setPreferredSize(new Dimension(370, 200));
		addEvent.setVisible(false);
		
		opis = new JTextArea(4, 25);
		opis.setLineWrap(true);
		miejsce = new JTextField(10);
		data_rozpoczecia = new String();
		data_zakonczenia = new String();
		przypomnienie = new JComboBox();
		godzina = new JTextField(2);
		minuta = new JTextField(2);
		buttonAddEvent = new JButton("Add Event");
		
		flowPanel = new JPanel();
		JLabel describe = new JLabel("Describe: ");
		flowPanel.setAlignmentY(TOP_ALIGNMENT);
		flowPanel.add(describe);
		flowPanel.add(opis);
		addEvent.add(flowPanel);	
		
		flowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		flowPanel.add(new JLabel("Place: "));
		flowPanel.add(miejsce);
		addEvent.add(flowPanel);
		
		flowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		flowPanel.add(new JLabel("Select date of start: "));
		flowPanel.add(new JLabel());
		flowPanel.add(new JLabel("Select date of end: "));
		flowPanel.add(new JLabel());
		addEvent.add(flowPanel);
		
		flowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		flowPanel.add(new JLabel("Reminder: "));
		flowPanel.add(przypomnienie);
		flowPanel.add(new JLabel("Hour: "));
		flowPanel.add(godzina);
		flowPanel.add(new JLabel("Minute: "));
		flowPanel.add(minuta);
		flowPanel.add(buttonAddEvent);
		buttonAddEvent.addActionListener(Controller.mainFrameEvent);
		addEvent.add(flowPanel);
 	}
 
 	private JComponent makeTextPanel(String string) {
		// TODO Auto-generated method stub
		return null;
	}
}