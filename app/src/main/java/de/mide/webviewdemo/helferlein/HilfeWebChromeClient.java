package de.mide.webviewdemo.helferlein;

import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;


/**
 * Ein Objekt dieser Klasse muss am WebView-Element mit der Methode {@code setWebViewClient()}
 * registriert werden.
 */
public class HilfeWebChromeClient extends WebChromeClient {

    /**
     * Ausgabe von {@code console.log()} im JavaScript-Code auf Android-Logger schreiben.
     *
     * siehe auch
     * <a href="https://developer.android.com/develop/ui/views/layout/webapps/debugging#java">hier</a>
     *
     * @param consoleMessage Objekt mit Details zu Log-Nachricht, die mit von WebView-Element
     *                       ausgeführtem JavaScript-Code geschrieben wurden
     *
     * @return {@code true}, wenn die Nachricht behandelt wurde.
     */
    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {

        super.onConsoleMessage(consoleMessage);

        String msg = consoleMessage.message()    + " -- Zeile " +
                     consoleMessage.lineNumber() + " von "      +
                     consoleMessage.sourceId();

        Log.i("Hilfeseite", msg);

        return true;
    }
}
