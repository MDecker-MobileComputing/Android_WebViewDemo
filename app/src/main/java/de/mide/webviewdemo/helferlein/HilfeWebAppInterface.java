package de.mide.webviewdemo.helferlein;

import android.app.AlertDialog;
import android.content.Context;
import android.webkit.JavascriptInterface;

import static de.mide.webviewdemo.BuildConfig.BUILD_ZEITPUNKT;

/**
 * Eine Instanz dieser Methode muss mit {@code addJavascriptInterface()}
 * am WebView-Element registriert werden.
 */
public class HilfeWebAppInterface {

    private Context _context;

    public HilfeWebAppInterface(Context ctx) {

        _context = ctx;
    }

    @JavascriptInterface
    public void zeigeDialogMitVersion() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(_context);

        dialogBuilder.setMessage("Version der App: " + BUILD_ZEITPUNKT);
        dialogBuilder.setPositiveButton("OK", null);

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

}
