package de.mide.webviewdemo;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import de.mide.webviewdemo.helferlein.HilfeWebViewClient;


/**
 * Activity enthält nur WebView-Element, ohne Layout-Datei!
 */
public class HilfeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        WebView webView = new WebView( this );
        setContentView(webView);

        HilfeWebViewClient webClient = new HilfeWebViewClient(this);
        webView.setWebViewClient(webClient);

        webView.loadUrl("file:///android_asset/hilfe_index.html");
    }

}
