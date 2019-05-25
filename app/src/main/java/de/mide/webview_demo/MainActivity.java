package de.mide.webview_demo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;


/** 
 * App für Demo des UI-Elements "WebView" (Browser-Widget).<br><br>
 * 
 * Mit dem WebView-Element werden einzelne Dilbert-Comics angezeigt.
 * Die URL muss ein Datum enthalten. Beispiel-URL für den ältesten Comic (16. April 1989):
 * <a href="http://dilbert.com/strip/1989-04-16">http://dilbert.com/strip/1989-04-16</a>
 * <br><br>
 * Die Datums-Werte für die Comics werden durch einen Zufallsgenerator erzeugt
 * (siehe Methode {@link MainActivity#erzeugeZufallsDatum()}.
 * Sollte über die URL ein Datums-Wert vor dem 16. April 1989 spezifiziert werden,
 * dann wird automatisch dieser älteste Comic anzeigt. Für Datumswerte in der Zukunft
 * wird auf den aktuellen (d.h. heutigen) Comic weitergeleitet.
 * <br><br>
 *
 * This project is licensed under the terms of the BSD 3-Clause License.
 */
public class MainActivity extends Activity implements OnClickListener {

	public static final String TAG4LOGGING = "WebViewDemo";
		
	/** Button, mit dem ein neuer Comic geladen wird. */
	protected Button _ladeButton = null;
	
	/** Browser-Element, in dem die Hilfeseite oder ein Comic angezeigt wird. */
	protected WebView _webview = null;
	
	/** Zufallsgenerator für Erzeugungs von Zufallsdatumswerten. */
	protected Random _random = new Random();
	
	
	/**
	 * Lifecycle-Methode für Initialisierung der UI.
	 * Lädt auch die Hilfe-Seite in das WebView-Element.
	 */	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Referenzen auf UI-Elemente abfragen
		_ladeButton  = findViewById( R.id.ladeButton );
		_webview     = findViewById( R.id.webview1   );
		
		
		// Event-Handler-Objekt für Button zuweisen
		_ladeButton.setOnClickListener(this);
										
		_webview.setWebViewClient( new MeinWebviewClient( _ladeButton ) );   // nicht klausur-relevant
		
		ladeHilfeSeite();
	}
	
	
	/**
	 * Einzige Methode aus Interface dem OnClickListener, Event-Handler für Button.
	 * Lädt HTML-Seite mit neuem Comic anhand Zufalls-Datum in das WebView-Element.
	 * 
	 * @param view View-Objekt, welches das Event ausgelöst hat.
	 */	
	@Override
	public void onClick(View view) {
				
		String artikelURL = "http://dilbert.com/strip/" + erzeugeZufallsDatum();
		// Um das Laden von unverschlüsselten Web-Seiten (http statt https) zu
        // erlauben, kann u.a. in der Manifest-Datei das Attribut
        // android:usesCleartextTraffic="true" gesetzt werden; es gibt aber
        // noch andere Möglichkeiten, siehe https://stackoverflow.com/a/50834600/1364368
		
		Log.i(TAG4LOGGING, "Zufalls-URL: " + artikelURL);
				 						
		_webview.loadUrl( artikelURL );
	}

	
	/**
	 * Erzeugt ein Zufalls-Datum zum Aufruf eines Dilbert-Comics. Es ist gewährleistet,
	 * dass das Datum nicht vor dem <i>16. April 1989</i> ist, weil dies der älteste
	 * verfügbare Comic ist.<br><br>
	 * 
	 * <b>Funktionsweise:</b> Es wird erst ein Zufallsjahr ausgewürfelt. Wenn dieses Jahr größer als
	 * 1989 und echt kleiner als das aktuelle Jahr ist, dann wird der Tag im Jahr ausgewürfelt
	 * (unter Berücksichtigung der Tage für dieses Jahr, z.B. 365 für Nicht-Schaltjahre).
	 * Es werden zwei Objekte der Klasse {@link java.util.GregorianCalendar} verwendet: Eines, um
	 * die aktuelle Jahreszahl und den aktuellen Tag zu bestimmen, und ein zweites, mit dem das
	 * Zufallsdatum "konstruiert" wird.
	 * Siehe auch: <a href="http://stackoverflow.com/a/3985644/1364368">http://stackoverflow.com/a/3985644/1364368</a>   
	 * <br><br> 
	 *  
	 * @return String mit Zufalls-Datum im Format <i>yyyy-mm-dd</i>,
     *         kann direkt an URL angehängt werden.
	 *         Die Zahl für den Tag und den Monat ist immer zweistellig.<br>
	 *         Beispiel: <i>2014-01-31</i> (31. Januar 2014)
	 */
	@SuppressLint("SimpleDateFormat")
	protected String erzeugeZufallsDatum() {

		// *** Schritt 1: Aktuellen Tag (z.B. "32" für 1. Februar) & Jahreszahl bestimmen ***
		GregorianCalendar calendarHeute = new GregorianCalendar();
						
		int jahrHeute = calendarHeute.get(Calendar.YEAR);  // aktuelles Jahr, z.B. 2015
		
		
		// *** Schritt 2: Zufalls-Datum erstellen ***
		int zufallsTagImJahr = 0;
				
		int zufallsJahr = erzeugeZufallszahlImBereich(1989, jahrHeute);
		
		
		GregorianCalendar calendarZufall = new GregorianCalendar();
		calendarZufall.set(Calendar.YEAR, zufallsJahr);

		
		int maxTagImJahr = calendarZufall.getActualMaximum( Calendar.DAY_OF_YEAR ); // hat aktuelles Jahr 365 oder 366 Tage? 
		
		if (zufallsJahr == 1989) {              // Fall 1: Tage vor dem 16. April 1989 sind nicht gültig
			
			// Der 16. April 1989 ist der Tag Nr 106 in diesem Jahr (siehe auch Ergebnis Linux-Befehl: date --date='1989-04-16' +%j)
			// Der 31. April 1989 war der Tag Nr 365 in diesem Jahr 
			zufallsTagImJahr = erzeugeZufallszahlImBereich(106, 365);
			
		} else if (zufallsJahr == jahrHeute) {  // Fall 2: Tage nach dem heutigen Datum sind nicht zulässig
		
			int nummerTagHeute = calendarHeute.get(GregorianCalendar.DAY_OF_YEAR); // Nummer des heutigen Tages (1..366)
			
			zufallsTagImJahr = erzeugeZufallszahlImBereich(1, nummerTagHeute);
									
		} else {                                // Fall 3: Alle Tage zwischen 1. Januar und 31. Dezember sind zulässig
			
			zufallsTagImJahr = erzeugeZufallszahlImBereich(1, maxTagImJahr);
						
		}

		calendarZufall.set(Calendar.DAY_OF_YEAR, zufallsTagImJahr);
		
		
		
		// *** Schritt 3: String-Repräsentation des Zufall-Datums bauen ***
		DateFormat datumsFormatierer = new SimpleDateFormat("yyyy-MM-dd");
		
		return datumsFormatierer.format( calendarZufall.getTime() );
	}
	
	
	/**
	 * Methode liefert ganzzahlige Zufallszahl in einem bestimmten Bereich zurück.
	 * 
	 * @param minWert Zufallszahl darf nicht echt-kleiner als diese Zahl sein.
	 *
	 * @param maxWert Zufallszahl darf nicht echt-größer als diese Zahl sein.
	 *
	 * @return Zufallszahl im geschlossenen Werte-Bereich von <i>minWert</i> bis <i>maxWert</i>.
	 */
	protected int erzeugeZufallszahlImBereich(int minWert, int maxWert) {
		
		int differenz = maxWert - minWert;
		
		if (differenz == 0) {
			Log.w(TAG4LOGGING, "Der Zufallsgenerator wurde für minWert==maxWert aufgerufen");
			return minWert;
		}
		
		int zufallszahl = minWert + _random.nextInt(differenz + 1);
		
		return zufallszahl;		
	}
	
	
	/**
	 * Lädt HTML-Datei als <i>"Raw Resource"</i> und stellt diese Seite im WebView-Element dar.
	 * HTML-Datei muss mit Kodierung <i>"UTF-8"</i> gespeichert sein.
	 */
	protected void ladeHilfeSeite() {

		try {
			
			InputStream    is     = getResources().openRawResource(R.raw.hilfe);
			BufferedReader reader = new BufferedReader( new InputStreamReader(is) ); 
			
			// HTML-Seite zeilenweise einlesen
			String       currentLine  = null;
			StringBuffer stringBuffer = new StringBuffer();
			while ( (currentLine = reader.readLine()) != null )
				stringBuffer.append(currentLine);
							
			reader.close();
						
			_webview.loadData(stringBuffer.toString(), "text/html", "UTF-8"); // "UTF-8" ist Encoding
			
		}
		catch (Exception ex) {
			String errorMessage = "Exception beim Laden der Hilfeseite (Raw Resource): " + ex.toString();
			Log.e(TAG4LOGGING, errorMessage);
			Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();			
		}
	}
	
};
