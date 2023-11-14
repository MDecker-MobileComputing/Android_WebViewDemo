package de.mide.webviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

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
    }

    /**
     * Event-Handler für Button zur Anzeige von Infos für den eingegebenen HTTP-Status-Code.
     * @param view Button, der Event ausgelöst hat
     */
    public void onNachschlagenButton(View view) {

        String statusCodeEingabeTrimmed = _statusCodeEditText.getText().toString().trim();
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

                zeigeToast("HTTP-Status-Code dürfen nicht größer als 599 sein.");
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


    private void zeigeToast(String nachricht) {

        Toast.makeText(this, nachricht, Toast.LENGTH_LONG).show();
    }

}