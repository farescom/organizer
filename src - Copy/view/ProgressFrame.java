package view;

import model.*;
import controller.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import controller.WelcomeFrameEvent;
import java.sql.Date;
import java.util.ArrayList;
import java.awt.EventQueue;
import java.awt.Button;
import java.awt.EventQueue;

public class ProgressFrame extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	JPanel panel;
	public JProgressBar progressBar;
	public JLabel tekst;
	public int procent = 0;
	public Button but;
	
	public static Model model;
	
	public ProgressFrame(String tytul, int SizeX, int SizeY, int x, int y)
    {  
        super(tytul);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        setSize(SizeX, SizeY);
        setLocation(x,y);  
        setResizable(false);
        setVisible(true);
        
        // Tworzenie panelu
        panel = new JPanel();
                
        // Tworzenie tekstu
        tekst = new JLabel("Trwa logowanie do programu... ("+procent+"%)");
        panel.add(tekst);
                
        // Tworzenie progressBar
        progressBar = new JProgressBar();
        progressBar.setMaximum(100);
        progressBar.setValue(0);
        panel.add(progressBar);
        add(panel);
                
        but = new Button("cos");
        panel.add(but);
        but.addActionListener(this);


    }
	
	public void actionPerformed(ActionEvent e)
	{
        Object source = e.getSource();
        if(source == but)  
        {
        	procent += 15;
        	tekst.setText("Trwa logowanie do programu... ("+procent+"%)");
        	progressBar.setValue(procent);
        }
	} 
}