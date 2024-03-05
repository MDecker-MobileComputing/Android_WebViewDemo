package de.mide.webviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * App demonstriert Verwendung von WebView-Element.
 * <br><br>
 *
 * This project is licensed under the terms of the BSD 3-Clause License.
 */
public class MainActivity extends AppCompatActivity {

    /** Eingabefeld für HTTP-Status-Code, nimmt nur Zahlen bis zu drei Stellen entgegen. */
    private EditText _statusCodeEditText = null;

    /** Browser-Widget. */
    private WebView _webView = null;


    /**
     * Lifecycle-Methode, lädt Layout-Datei und füllt Member-Variablen mit Referenzen
     * auf UI-Elemente.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _statusCodeEditText = findViewById(R.id.editTextNumber);
        _webView = findViewById(R.id.webview1);

        hilfeseiteAnzeigen();
    }


    /**
     * Methode bringt statische HTML-Seite (Raw Ressource)
     * in WebView-Element zur Anzeige.
     */
    private void hilfeseiteAnzeigen() {

        final String hilfeseiteHtml = ladeHtmlRawRessource();

        final String mimeType = "text/html";
        final String encoding = "UTF-8";
        _webView.loadData( hilfeseiteHtml, mimeType, encoding );
    }


    /**
     * HTML-Seite als "raw Ressource" laden.
     *
     * <br>
     * Lösung nach https://stackoverflow.com/a/73633910/1364368
     *
     * @return String aus Raw-Ressource-Datei
     */
    private String ladeHtmlRawRessource() {

        final InputStream is = getResources().openRawResource( R.raw.hilfeseite );

        final BufferedReader br = new BufferedReader( new InputStreamReader(is) );

        final String text = br.lines().reduce("\n", (a,b) -> a+b);

        return text;
    }


    /**
     * Event-Handler für Button zur Anzeige von Infos für den eingegebenen HTTP-Status-Code.
     * @param view Button, der Event ausgelöst hat
     */
    public void onNachschlagenButton(View view) {

        final String statusCodeEingabeTrimmed = _statusCodeEditText.getText().toString().trim();
        if (statusCodeEingabeTrimmed.length() != 3) {

            zeigeToast("HTTP-Status-Code muss genau drei Ziffern haben.");
            return;
        }

        if (statusCodeEingabeTrimmed.startsWith("0")) {

            zeigeToast("HTTP-Status-Codes dürfen nicht mit führenden Nullen anfangen.");
            return;
        }

        int codeAsInt = -1;
        try {

            codeAsInt = Integer.parseInt(statusCodeEingabeTrimmed);
            if (codeAsInt >= 600) {

                zeigeToast("HTTP-Status-Code darf nicht größer als 599 sein.");
                return;
            }
        }
        catch (NumberFormatException ex) {

            zeigeToast("Interner Fehler beim Parsen der Eingabe: " + ex);
            return;
        }

        String url = "https://http.cat/status/" + statusCodeEingabeTrimmed;

        _webView.loadUrl(url);
    }


    /**
     * Event-Handler für Button, um Hilfeseite (besteht nur aus WebView)
     * aufzurufen.
     */
    public void onHilfeButton(View view) {

        final Intent intent = new Intent(this, HilfeActivity.class );
        startActivity(intent);
    }


    /**
     * Nachricht in Toast anzeigen (verschwindet von selbst wieder).
     *
     * @param nachricht Text, der in Toast angezeigt wird.
     */
    private void zeigeToast(String nachricht) {

        Toast.makeText(this, nachricht, Toast.LENGTH_LONG).show();
    }

}