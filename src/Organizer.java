import model.Model;
import controller.Controller;
import view.View;

/**
 * Glowna klasa programu
 * @author Tomasz Adrianowski
 * @author Sliman Jakub El-Fara 
 */
public class Organizer
{
	static Model      model;
    static View       view;
    static Controller controller;
    
	/**
	 * Glowna metoda klasy Organizer
	 * @param args tablica argumentow wywolania programu
	 */
    
	public static void main(String[] args)
	{
		if (args.length == 0)
		{
		   model      = new Model();
		   view       = new View(model);
		   controller = new Controller(model, view);
		}
		else if (!(args[0].equals("exit")))
		{
		   System.out.print(args[0]);
		   model      = new Model();
	       view       = new View(model);
	       controller = new Controller(model, view);
		}
	}
}