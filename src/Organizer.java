import model.Model;
import controller.Controller;
import view.View;

/*
 * Problemy
 * - Konwertowanie dat
 */

/**
 * G��wna klasa programu
 * @author Tomasz Adrianowski
 * @author Sliman Jakub El-Fara
 */
public class Organizer
{
	static Model      model;
    static View       view;
    static Controller controller;
    
	/**
	 * G��wna metoda klasy Organizer
	 * @param args tablica argument�w wywo�ania programu
	 */
	public static void main(String[] args)
	{
		model      = new Model();
	    view       = new View(model);
	    controller = new Controller(model, view);
	}
}