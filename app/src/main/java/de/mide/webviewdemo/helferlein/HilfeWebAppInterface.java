package de.mide.webviewdemo.helferlein;

import android.app.Activity;
import android.app.AlertDialog;
import android.webkit.JavascriptInterface;

import static de.mide.webviewdemo.BuildConfig.BUILD_ZEITPUNKT;


/**
 * Eine Instanz dieser Methode muss mit {@code addJavascriptInterface()}
 * am WebView-Element registriert werden.
 * <br><br>
 *
 * siehe auch:
 * https://developer.android.com/develop/ui/views/layout/webapps/webview#UsingJavaScript
 */
public class HilfeWebAppInterface {

    private Activity _activity;


    /**
     * Konstruktor zum Übergeben von Referenz auf die Activity, die das
     * WebView-Element enthält.
     *
     * @param activity Referenz auf Activity
     */
    public HilfeWebAppInterface(Activity activity) {

        _activity = activity;
    }

    /**
     * Wenn das Objekt mit dem Prefix "MeinJsPrefix" am WebView registriert wird,
     * dann kann man die durch diese Methode bereitgestellte Funktion aus dem
     * JavaScript-Code im WebView-Element wie folgt aufrufen:
     * <pre>
     * MeinJsPrefix.zeigeDialogMitVersion();
     * </pre>
     */
    @JavascriptInterface
    public void zeigeDialogMitVersion() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(_activity);

        dialogBuilder.setMessage("Version der App: " + BUILD_ZEITPUNKT);
        dialogBuilder.setPositiveButton("OK", null);

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }


    /**
     * Wenn das Objekt mit dem Prefix "MeinJsPrefix" am WebView registriert wird,
     * dann kann man die durch diese Methode bereitgestellte Funktion aus dem
     * JavaScript-Code im WebView-Element wie folgt aufrufen:
     * <pre>
     * MeinJsPrefix.hilfeActivitySchliessen();
     * </pre>
     */
    @JavascriptInterface
    public void hilfeActivitySchliessen() {

        _activity.finish();
    }

}
