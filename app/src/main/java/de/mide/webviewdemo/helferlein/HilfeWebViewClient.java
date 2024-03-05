package de.mide.webviewdemo.helferlein;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class HilfeWebViewClient extends WebViewClient {

    private static final String TAG4LOGGING = " HilfeWebView";

    /** Activity mit WebView-Element. */
    private Context _context;


    /**
     * Konstruktor, um Context-Objekt in Objektvariable
     * zu kopieren.
     *
     * @param context Activity mit WebView-Element
     */
    public HilfeWebViewClient(Context context) {

        _context = context;
    }


    /**
     *
     * @param view WebView-Element, das das Event ausgelöst hat.
     *
     * @param request Request um neue URL zu laden (v.a. wegen Klick auf Hyperlink im
     *                dargestellten HTML).
     *
     * @return {@code false}, wenn die URL auch im WebView-Element angezeigt werden soll,
     *         {@code true}, wenn sie in einer externen Browser-App angezeigt werden
     *         soll.
     */
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

        final Uri zielUrl = request.getUrl();

        boolean interneURL = zielUrl.toString().startsWith("file:///android_asset/");
        if (interneURL) {

            Log.i(TAG4LOGGING, "Ziel-URL (in WebView anzeigen): " + zielUrl);
            return false; // im WebView anzeigen

        } else {

            Log.i(TAG4LOGGING, "Ziel-URL (in externem Browser anzeigen): " + zielUrl);

            final Intent intent = new Intent(Intent.ACTION_VIEW, zielUrl);
            _context.startActivity(intent);

            return true;
        }
    }

}
