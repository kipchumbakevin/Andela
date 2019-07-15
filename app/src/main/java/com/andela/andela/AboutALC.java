package com.andela.andela;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AboutALC extends AppCompatActivity {
    WebView web;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_alc);

         String url = "https://google.com";
         web= findViewById(R.id.web);
         web.setWebViewClient(new WebViewClient());
         web.loadUrl(url);
    }
}
