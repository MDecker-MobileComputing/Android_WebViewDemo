package de.mide.webviewdemo;

import android.app.AlertDialog;
import android.content.DialogInterface;

import android.os.Bundle;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import de.mide.webviewdemo.helferlein.HilfeWebAppInterface;
import de.mide.webviewdemo.helferlein.HilfeWebViewClient;


/**
 * Activity enthält nur WebView-Element, ohne Layout-Datei!
 */
public class HilfeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        final WebView webView = new WebView( this );
        setContentView(webView);

        final WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        HilfeWebAppInterface wai = new HilfeWebAppInterface(this);
        webView.addJavascriptInterface( wai, "MeinJsPrefix" );

        final HilfeWebViewClient webClient = new HilfeWebViewClient(this);
        webView.setWebViewClient(webClient);

        webView.loadUrl("file:///android_asset/hilfe_index.html");
    }

}
