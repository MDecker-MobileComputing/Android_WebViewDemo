package de.mide.webviewdemo.helferlein;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * Dieses WebClient-Objekt wird benötigt, um das Verhalten beim Klicken auf URLs
 * in einer vom WebView-Element dargestellten HTML-Seite zu steuern.
 */
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
     * Wenn im WebView auf einen HTML-Link geklickt wird, dann entscheidet diese Methode,
     * ob der Link im WebView geöffnet wird, oder in einer externen Browser-App.
     *
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

        final boolean interneURL = zielUrl.toString().startsWith("file:///android_asset/");
        if (interneURL) {

            Log.i(TAG4LOGGING, "Ziel-URL (in WebView anzeigen): " + zielUrl);
            return false; // im WebView anzeigen

        } else {

            Log.i(TAG4LOGGING, "Ziel-URL (in externem Browser anzeigen): " + zielUrl);

            final Intent intent = new Intent(Intent.ACTION_VIEW, zielUrl);

            if (wirdIntentUnterstuetzt(_context, intent)) {

                _context.startActivity(intent);
            }

            return true;
        }
    }


    /**
     * Methode überprüft, ob ein impliziter Intent auf dem Gerät abgeschickt werden kann.
     * Wenn es nicht mindestens eine App, die den Intent unterstützt, dann crasht die
     * App, wenn der Intent trotzdem abgeschickt wird.
     *
     * @param context Referenz auf Activity
     *
     * @param intent Impliziter Intent
     *
     * @return {@code true}, wenn es mindestens eine App auf dem Gerät gibt, die den Intent
     *         verarbeiten kann, sonst {@code false}
     */
    public static boolean wirdIntentUnterstuetzt(Context context, Intent intent) {

        PackageManager packageManager = context.getPackageManager();
        ComponentName componentName   = intent.resolveActivity(packageManager);

        if (componentName == null) {

            Log.w(TAG4LOGGING,
                    "Nicht-unterstützter Intent: ACTION=" + intent.getAction() +
                            ", DATA=" + intent.getDataString() );

            return false;

        } else {

            return true;
        }
    }

}
