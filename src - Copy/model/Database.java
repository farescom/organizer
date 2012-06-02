package model;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
* Klasa odpowiedzialna za komunikacj� z baz� danych.
* @param args tablica argument�w wywo�ania programu
*/
public class Database
{
	
	 /* Baza danych
	 Database baza = new Database();
	 ArrayList<Zdarzenie> zdarzenia = new ArrayList<Zdarzenie>();
	 baza.connection();
	 baza.get(-1, zdarzenia); System.out.println(" ");  // wszystkie zdarzenia
	 baza.get(1, zdarzenia); System.out.println(" "); // zdarzenie o id = 1
	 baza.get(4, zdarzenia); System.out.println(" "); // zdarzenie o id nieistniejacym
	 baza.get("SELECT * FROM zdarzenia", zdarzenia);  // zapytanie
	 Date datunka = new Date(15);
	 baza.update(2, "tekst", 1, datunka, datunka, null, 61, -1); // null lub -1 gdy nietykalne

	 Zdarzenie jakies = new Zdarzenie(2, "tekstss", 1, datunka, datunka, null, 6, -1);
	 baza.update(2, jakies);

	 baza.insert("aaa", 111, datunka, datunka, "aa", 11, 111); // null lub -1 gdy nietykalne
	 zdarzenia.removeAll(zdarzenia);
	 baza.get(-1, zdarzenia);
	 System.out.println(zdarzenia.toString()); System.out.println(" ");
	 */
		
	public String driver;
	public String sql_server_address;
	public String user;
	public String password;
	public Connection connection;
	public boolean czy_polaczono;
	public String table = "zdarzenia";
    public int dni_waznosci;
    public int kolor_1;
    public int kolor_2;
    public int kolor_3;
    public int nextID;
	 
	 /**
	 * Kontruktor domy�lny tworzy obiekt klasy Database z domy�lnymi ustawieniami
	 */
	public Database ()
	{
		 driver = "com.mysql.jdbc.Driver";
		 sql_server_address = "jdbc:mysql://instance14902.db.xeround.com:9974/baza";
		 user = "organizer";
		 password = "organizer";
	}
	 
	 /**
	 * Kontruktor tworzy obiekt klasy Database z ustawieniami podanymi jako argumenty
	 */
	 public Database (String driver, String sql_server_address, String user, String password)
	{
		this.driver = driver;
		this.sql_server_address = sql_server_address;
		this.user = user;
		this.password = password;
	}
	 
	 /**
	 * Metoda tworzy po��czenie z baz� danych
	 */
	 public boolean connection()
		{
			try
			{
	            Class.forName(driver);
	            connection = DriverManager.getConnection(sql_server_address, user, password);
	            return true;
			}
			catch (Exception wyjatek)
			{
	            return false;
			}
	 }
	 
	 /**
	 * Metoda ustala tablice zdarze� dla zalogowanego u�ytkownika
	 */
	 public void set_table(String table)
	 {
		this.table = table;
	 }
	 
	 /**
	 * Metoda wykonuje przekazane polecenie i zwraca wyniki do listy podanej w drugim parametrze
	 * @param zapytanie zapytanie sql
	 * @param zdarzenia referencja do obiektu klasy ArrayList, do kt�rego zostan� dopisane zwr�cone wyniki
	 */
	 public int get(String zapytanie, ArrayList <Zdarzenie> zdarzenia)
		{
		 	try
		 	{
		 		Statement statement = connection.createStatement();
	            ResultSet result = statement.executeQuery(zapytanie);
	           
	            String data_zakonczenia;
	            //zdarzenia = new ArrayList<Zdarzenie>();
	           
	           while (result.next())
	           {
	              //if (result.getInt("czy_okres") == 1) data_zakonczenia = result.getString("data_zakonczenia");
	              //else data_zakonczenia = null;
	              
	              zdarzenia.add(new Zdarzenie(result.getInt("id"), result.getString("opis"), 
	            		  		result.getInt("czy_okres"), result.getString("data_rozpoczecia"), 
	            		  		result.getString("data_zakonczenia"), result.getString("miejsce"), result.getInt("waznosc"),
	            		  		result.getInt("rodzaj")));
	           }
	           return 1;
		 	}
		 	catch (Exception wyjatek)
	        {
	            return 0;
	        }
	 }
	 
	 /**
	 * Metoda pobiera z tabeli zdarzenia w bazie danych krotki odpowiadajace przekazanemu
	 * w pierwszym parametrze identyfikatorze. 
	 * Je�eli jest to warto�� -1 funkcja pobiera ca�� zawarto�� tabeli
	 * @param numer numer id, dla kt�rego ma zosta� pobrana krotka z bazy danych
	 * @param zdarzenia referencja do obiektu klasy ArrayList, do kt�rego zostan� dopisane zwr�cone wyniki
	 */
		public int get(int numer, ArrayList <Zdarzenie> zdarzenia)
		{
			try
			{
			   String zapytanie;
			   if (numer == -1) zapytanie = "SELECT * FROM "+table;
			   else zapytanie = "SELECT * FROM "+table+" WHERE id = "+numer;
			   
			   Statement statement = connection.createStatement();
	           ResultSet result = statement.executeQuery(zapytanie);
	           //String data_zakonczenia;
	           
	           while (result.next())
	           {
	               //if (result.getInt("czy_okres") == 1) data_zakonczenia = result.getString("data_zakonczenia");
	               //else data_zakonczenia = null;
	               
	               zdarzenia.add(new Zdarzenie(result.getInt("id"), result.getString("opis"), 
	               result.getInt("czy_okres"), result.getString("data_rozpoczecia"), 
	               result.getString("data_zakonczenia"), result.getString("miejsce"), result.getInt("waznosc"),
	               result.getInt("rodzaj")));
	           }
	           return 1;
			}
			catch (Exception wyjatek)
	        {
	            return 0;
	        }
	 }
	 
	 /**
	 * Metoda do aktualizacji krotek w bazie danych
	 * null, -1 oznaczaj� nie zmieniania danej pozycji
	 */ 
	 public int update(int id, String opis, int czy_okres, String data_rozpoczecia, String data_zakonczenia,
	   String miejsce, int waznosc, int rodzaj)
	{
		try
		{
			 Statement statement = connection.createStatement(
	                    ResultSet.TYPE_SCROLL_INSENSITIVE,
	                    ResultSet.CONCUR_UPDATABLE);
	         ResultSet result = statement.executeQuery("SELECT * FROM "+table+" WHERE id = "+id);
	
	         result.absolute(1);
	         if (opis != null) result.updateString("opis", opis);
	         if (czy_okres != -1) result.updateInt("czy_okres", czy_okres);
	         if (data_rozpoczecia != null) result.updateString("data_rozpoczecia", data_rozpoczecia);
	         if (data_zakonczenia != null) result.updateString("data_zakonczenia", data_zakonczenia);
	         if (miejsce != null) result.updateString("miejsce", miejsce);
	         if (waznosc != -1) result.updateInt("waznosc", waznosc);
	         if (rodzaj != -1) result.updateInt("rodzaj", rodzaj);
	         result.updateRow();
	         
	         return 1;
		}
		catch(Exception wyjatek)
		{
			return 0;
		}
	}
	 
	 /**
	 * Metoda do aktualizacji krotek
	 * null, -1 oznaczaj� nie zmieniania danej pozycji
	 */ 
	 public int update(int id, Zdarzenie zdarzenie)
	 {
		try
		{
			Statement statement = connection.createStatement(
	                    ResultSet.TYPE_SCROLL_INSENSITIVE,
	                    ResultSet.CONCUR_UPDATABLE);
	         ResultSet result = statement.executeQuery("SELECT * FROM "+table+" WHERE id = "+id);
	
	         result.absolute(1);
	         if (zdarzenie.opis != null) result.updateString("opis", zdarzenie.opis);
	         if (zdarzenie.czy_okres != -1) result.updateInt("czy_okres", zdarzenie.czy_okres);
	         if (zdarzenie.data_rozpoczecia != null) result.updateString("data_rozpoczecia", zdarzenie.data_rozpoczecia);
	         if (zdarzenie.data_zakonczenia != null) result.updateString("data_zakonczenia", zdarzenie.data_zakonczenia);
	         if (zdarzenie.miejsce != null) result.updateString("miejsce", zdarzenie.miejsce);
	         if (zdarzenie.waznosc != -1) result.updateInt("waznosc", zdarzenie.waznosc);
	         if (zdarzenie.rodzaj != -1) result.updateInt("rodzaj", zdarzenie.rodzaj);
	         result.updateRow();
	         
	         return 1;
	  	}
		catch(Exception wyjatek)
		{
			return 0;
		}
	 }
	 
	 /**
	 * Metoda do dodawania krotek
	 */ 
	 public int insert(String opis, int czy_okres, String data_rozpoczecia, String data_zakonczenia,
	   String miejsce, int waznosc, int rodzaj)
	{
		try
		{
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
	
		    int result = statement.executeUpdate("INSERT INTO "+table+" VALUES("+nextID+", '"+opis+"', "
		               +czy_okres+", '"+data_rozpoczecia.toString()+"', '"+data_zakonczenia.toString()+"', '"+miejsce+"', "+
		               +waznosc+", "+rodzaj+")");
		    
		     nextID++;
		     return 1;
		}
		catch(Exception wyjatek)
		{
			System.out.println(wyjatek.getMessage());
			return 0;
		}
	 }
	 
	 /**
	 * Metoda do rejestrowania uzytkownika
	 * @param login - string okreslajacy login nowego uzytkownika
	 * @param password - string okreslajacy haslo nowego uzytkownika
	 */ 
	 public int register(String login, char[] password)
	{
		String strPassword = new String(password);
	  
		if(!login.isEmpty() && !strPassword.isEmpty())
		{
			try
			{
				Statement statement = connection.createStatement();
	          
				// sprawdzenie czy juz nie ma takiego uzytkownika w bazie
				ResultSet result = statement.executeQuery("SELECT ID FROM user WHERE Login='"+login+"'");
				if(result.next())
				{
					return 2;
				}
	    
				// jezeli nie ma, to zapisanie uzytkownika do tabeli 'user'
				statement.executeUpdate("INSERT INTO user VALUES (NULL, '"+login+"', '"+strPassword+"', '1', 1, 1, 1)");
	    
				// sprawdzenie jakie ID zostalo przypisane userowi
				result = statement.executeQuery("SELECT ID FROM user WHERE Login='"+login+"' AND Password='"+strPassword+"'");
	    
				// utworzenie tabeli 'zdarzenia_IDUsera'
				if(result.next())
				{
					int id = result.getInt("ID");
					statement.executeUpdate("CREATE TABLE zdarzenia_"+id+"(ID smallint AUTO_INCREMENT, opis varchar(200),"
	             +" czy_okres tinyint(1), data_rozpoczecia datetime, data_zakonczenia datetime, miejsce varchar(50),"
	             +" waznosc int(11), rodzaj int(11), PRIMARY KEY(ID))");
				}
				return 1;
			}
			catch(Exception wyjatek)
			{
				System.out.println(wyjatek.getMessage());
				return 0;
			}
		}
		return 0;
	 }
	 
	 /**
	 * Metoda do logowania uzytkownika
	 * @param login - string okreslajacy login uzytkownika
	 * @param password - string okreslajacy haslo uzytkownika
	 */ 
	 public int login(String login, char[] password)
	{
		String strPassword = new String(password);
	  
		if(!login.isEmpty() && !strPassword.isEmpty())
		{
			try
			{
				Statement statement = connection.createStatement();
				ResultSet result = statement.executeQuery("SELECT * FROM user WHERE Login='"+login+"' AND Password='"+strPassword+"'");
				if(result.next())
				{
					// Ustalanie tabeli ze zdarzeniami
				     int id = result.getInt("ID");
				     table = "zdarzenia_"+id;
				     
				     // Wydobywanie informacji o dniach waznosci i kolorach
				     	dni_waznosci = result.getInt("waznosc_zdarzen");
				     	kolor_1 = result.getInt("kolorWeekendu");
				     	kolor_2 = result.getInt("kolorDnia");
				     	kolor_3 = result.getInt("kolorWybranegoDnia");
				     
				     // usuwanie starych zdarzen
				     	GregorianCalendar cal = new GregorianCalendar();
				     	String dzien = (new Integer(cal.get(GregorianCalendar.DAY_OF_MONTH))).toString();
				     	int miesiacik = cal.get(GregorianCalendar.MONTH)+1;
				     	String miesiac = (new Integer(miesiacik)).toString();
				     	int rocznik = cal.get(GregorianCalendar.YEAR);
				     	String rok = (new Integer(rocznik)).toString();
				     	
						String datuneczka = rok+"-"+miesiac+"-"+dzien;
						System.out.println(datuneczka + " - " + dni_waznosci);
						
						// zmiana 
						if ( miesiacik > dni_waznosci)
						{
							miesiacik = miesiacik - dni_waznosci;
						}
						else
						{
							rocznik--;
							miesiacik = 12 - (dni_waznosci - miesiacik);
						}
						String datuneczka2 = rocznik+"-"+miesiacik+"-"+dzien;
						System.out.println("* " + datuneczka2);
						query2("DELETE FROM zdarzenia WHERE data_rozpoczecia < '"+datuneczka2+"'");
						//query("DELETE FROM '"+table+"' WHERE data_rozpoczecia < '"+datuneczka2+"'");
						//System.out.println(dni_waznosci + " | " + kolor_dni_wolnych2 + " | " + kolor_aktualnego_dnia2 + " | " + kolor_zaznaczonego_dnia2);
						System.out.println(table);
						System.out.println("Zostales zalogowany!");
						System.out.println(dni_waznosci);
						return 1;
				}
				else
				{
					System.out.println("Nie zostales zalogowany!");
					return 2;
				}
			}
			catch(Exception wyjatek)
			{
				System.out.println(wyjatek.getMessage());
				return 0;
			}
		}
		return 0;
	}
	 
	 /**
	 * Metoda usuwa z tabeli krotki o podanej wartosci id
	 */
	 public int delete(int numer)
	{
		try
		{
			String zapytanie;
			if (numer == -1) zapytanie = "DELETE FROM "+table;
			else zapytanie = "DELETE FROM "+table+" WHERE id="+numer;
	     
	     	Statement statement = connection.createStatement();
	        int result = statement.executeUpdate(zapytanie);
	        return 1;
		}
	    catch (Exception wyjatek)
	    {
	    	System.out.println(wyjatek.getMessage());
	    	return 0;
	    }
	}
	 
	 public int query(String zapytanie)
	{
		try
		{
			Statement statement2 = connection.createStatement();
			ResultSet result2 = statement2.executeQuery(zapytanie);
			return 1;
		}
	    catch (Exception wyjatek)
	    {
	    	System.out.println(wyjatek.getMessage());
	    	return 0;
	    }
	}
	 
	 /**
		 * Metoda usuwa z tabeli krotki spe�niaj�ce pewien warunek
		 */
		 public int query2(String zapytanie)
		{
			try
			{
				Statement statement2 = connection.createStatement();
				int result2 = statement2.executeUpdate(zapytanie);
				return 1;
			}
		    catch (Exception wyjatek)
		    {
		    	System.out.println(wyjatek.getMessage());
		    	return 0;
		    }
		}
}