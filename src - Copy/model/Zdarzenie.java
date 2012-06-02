package model;

import java.sql.Date;

/**
* Klasa reprezentuj¹ca zdarzenie
*/
public class Zdarzenie
{
	/** identyfikator zdarzenia */
    public int id;
    public String opis;
    public int czy_okres;
    public String data_rozpoczecia;
    public String data_zakonczenia;
    public String miejsce;
    public int waznosc;
    public int rodzaj;
    public String godzina;
    public String dzien;
    
	/** Kontruktor klasy Zdarzenie */
    public Zdarzenie( int id, String opis, int czy_okres, String data_rozpoczecia, String data_zakonczenia, String miejsce, int waznosc, int rodzaj)
    {
    	this.id = id;
    	this.opis = opis;
    	this.czy_okres = czy_okres;
    	this.data_rozpoczecia = data_rozpoczecia;
    	this.data_zakonczenia = data_zakonczenia;
    	this.miejsce = miejsce;
    	this.waznosc = waznosc;
    	this.rodzaj = rodzaj;
    	
    	this.godzina = new String(this.data_rozpoczecia.substring(11, 16));
    	this.dzien = new String(this.data_rozpoczecia.substring(8, 10));
    }
    
    public String toString()
    {
    	return (id + " " + opis + " " + data_rozpoczecia + " " + data_zakonczenia +
        		" " + czy_okres + " " + miejsce + " " + waznosc + " " + rodzaj);
    }
}
