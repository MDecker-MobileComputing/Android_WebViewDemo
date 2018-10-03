package de.mide.webview_demo;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;


/**
 * Nicht klausur-relevant!<br><br>
 * 
 * Eigene Unterklasse von WebViewClient-Objekt zur Behandlung von Events des WebView-Objektes.
 * Beispiele für solche Events sind z.B. Auftreten eines Fehlers oder Beendigung des Ladevorgangs.
 * <br><br>
 *
 * This project is licensed under the terms of the BSD 3-Clause License.
 */
public class MeinWebviewClient extends WebViewClient {

	/**
	 * Referenz auf Button, mit dem das Laden einer neuen Webseite gestartet wird;
	 * diese Button wird während des Ladevorgangs deaktiviert.
	 */
	protected Button _ladeButton = null;
	
	
	/**
	 * Konstruktor zur Übergabe Referenz auf Lade-Button.
	 * 
	 * @param ladeButton Referenz auf Button, mit dem der Ladevorgang gestartet wird.
	 *                   Dieser Button wird während des Ladevorgangs deaktiviert.
	 */
	public MeinWebviewClient(Button ladeButton) {
		_ladeButton = ladeButton;
	}
	
	
	/**
	 * Event-Handler-Methode für Fehlerfall; der Lade-Button wird wieder aktiviert.
	 */
	public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
		Log.e(MainActivity.TAG4LOGGING, 
			  "Fehler beim Laden einer URL mit dem WebView-Element: " + description + 
			  ", URL: " + failingUrl + ", Fehler-Code: " + errorCode);
		
		_ladeButton.setEnabled(true);
	}

	
	/**
     * Event-Handler für Start Ladevorgang, Lade-Button wird deaktiviert.
	 */
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		
		_ladeButton.setEnabled(false);
		
		Log.v(MainActivity.TAG4LOGGING, "Ladevorgang WebView-Element gestartet.");
	}
	
	
	/**
     * Event-Handler für Ende des Ladevorgangs, Lade-Button wird wieder aktiviert.
	 */	
	public void onPageFinished(WebView view, String url) {
		
		_ladeButton.setEnabled(true);
		
		Log.v(MainActivity.TAG4LOGGING, "Ladevorgang WebView-Element gestoppt.");
	}

	
	/**
	 * Damit bei Click auf Links in der vom WebView dargestellten HTML-Seite nicht
	 * in einer externe Browser-App geöffnet wird, sondern im WebView-Element dieser App.
	 */
	public boolean shouldOverrideUrlLoading (WebView webView, String url) {
		Log.v(MainActivity.TAG4LOGGING, "Aufruf externer URL: " + url);
		webView.loadUrl(url);
		return false;  
	}
	
}
