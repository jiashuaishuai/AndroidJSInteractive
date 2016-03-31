package com.example.jiashuaishuai.androidjsinteractive;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private WebView webView;
    private Button appCallHtml, appCallHtmlParam, paramHtmlToApp, paramAppToHtml;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.webView);

        appCallHtml = (Button) findViewById(R.id.appCallHtml);
        appCallHtmlParam = (Button) findViewById(R.id.appCallHtmlParam);
        paramHtmlToApp = (Button) findViewById(R.id.paramHtmlToApp);
        paramAppToHtml = (Button) findViewById(R.id.paramAppToHtml);
        appCallHtml.setOnClickListener(this);
        appCallHtmlParam.setOnClickListener(this);
        paramHtmlToApp.setOnClickListener(this);
        paramAppToHtml.setOnClickListener(this);
        //设置编码记
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        //支持js
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setBackgroundColor(Color.argb(0, 0, 0, 0));

        webView.addJavascriptInterface(new JavaScriptObject(this, webView), "jsObj");
        webView.loadUrl("file:///android_asset/index.html");


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.appCallHtml:
                String param = "AppCallHtml:params:App--Html";
                webView.loadUrl("javascript: showFromHtml('"+param+"')");
                break;
            case R.id.appCallHtmlParam:

                param = "AppCallHtml:params:App--Html";
                webView.loadUrl("javascript: showFromHtml('"+param+"')");
                break;
            case R.id.paramHtmlToApp:
                /**
                 * App传给html参数，Html处理，传回给APp
                 */
                webView.loadUrl("javascript: showFromHtml2('param:App-Html')");
                break;
            case R.id.paramAppToHtml:
                param = "AppCallHtml:params:App--Html";
                webView.loadUrl("javascript: showFromHtml('"+param+"')");
                break;

        }

    }

}
