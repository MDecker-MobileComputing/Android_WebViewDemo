package de.mide.webviewdemo;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import de.mide.webviewdemo.helferlein.HilfeWebAppInterface;
import de.mide.webviewdemo.helferlein.HilfeWebChromeClient;
import de.mide.webviewdemo.helferlein.HilfeWebViewClient;


/**
 * Activity enthält nur WebView-Element, es gibt keine zugehörige Layout-Datei!
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

        HilfeWebChromeClient webChromeClient = new HilfeWebChromeClient();
        webView.setWebChromeClient(webChromeClient);

        webView.loadUrl( "file:///android_asset/hilfe_index.html" );
    }

}
