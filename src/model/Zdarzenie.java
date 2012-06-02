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
    public long waznosc;
    public int rodzaj;
    public String godzina;
    public String dzien;
    
	/** Kontruktor klasy Zdarzenie */
    public Zdarzenie( int id, String opis, int czy_okres, String data_rozpoczecia, String data_zakonczenia, String miejsce, long waznosc, int rodzaj)
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
    
    public Zdarzenie(Zdarzenie zdarzenie)
    {
    	this.id = zdarzenie.id;
    	this.opis = zdarzenie.opis;
    	this.czy_okres = zdarzenie.czy_okres;
    	this.data_rozpoczecia = zdarzenie.data_rozpoczecia;
    	this.data_zakonczenia = zdarzenie.data_zakonczenia;
    	this.miejsce = zdarzenie.miejsce;
    	this.waznosc = zdarzenie.waznosc;
    	this.rodzaj = zdarzenie.rodzaj;
    	
    	this.godzina = new String(zdarzenie.data_rozpoczecia.substring(11, 16));
    	this.dzien = new String(zdarzenie.data_rozpoczecia.substring(8, 10));
    }
    
    public String toString()
    {
    	return (id + " " + opis + " " + data_rozpoczecia + " " + data_zakonczenia +
        		" " + czy_okres + " " + miejsce + " " + waznosc + " " + rodzaj);
    }
}
