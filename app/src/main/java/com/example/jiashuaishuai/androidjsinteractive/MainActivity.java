package com.example.jiashuaishuai.androidjsinteractive;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private WebView webView;
    private Button appCallHtml, appCallHtmlParam, paramHtmlToApp, paramAppToHtml,my_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.webView);

        appCallHtml = (Button) findViewById(R.id.appCallHtml);
        appCallHtmlParam = (Button) findViewById(R.id.appCallHtmlParam);
        paramHtmlToApp = (Button) findViewById(R.id.paramHtmlToApp);
        paramAppToHtml = (Button) findViewById(R.id.paramAppToHtml);
        my_btn = (Button) findViewById(R.id.my_btn);
        my_btn.setOnClickListener(this);
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
        webView.setWebViewClient(new WebViewClient() {
            /**
             * 拦截Url跳转，在
             * @param view
             * @param url
             * @return
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http") || url.startsWith("https")) { //http和https协议开头的执行正常的流程
                    view.loadUrl(url);
                } else {  //其他的URL则会开启一个Acitity然后去调用原生APP
                    Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(in);
                }
                return true;
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                if (url.endsWith("jpg")) {
                    Bitmap bitmap  = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
                    WebResourceResponse response = new WebResourceResponse("image/png", "UTF-8", inputStream);
                    return response;
                }
                return super.shouldInterceptRequest(view, request);
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.appCallHtml:
                String param = "AppCallHtml:params:App--Html";
                webView.loadUrl("javascript: showFromHtml('" + param + "')");
                break;
            case R.id.appCallHtmlParam:

                param = "AppCallHtml:params:App--Html";
                webView.loadUrl("javascript: showFromHtml('" + param + "')");
                break;
            case R.id.paramHtmlToApp:
                /**
                 * App传给html参数，Html处理，传回给APp
                 */
                webView.loadUrl("javascript: showFromHtml2('param:App-Html')");
                break;
            case R.id.paramAppToHtml:
                param = "AppCallHtml:params:App--Html";
                webView.loadUrl("javascript: showFromHtml('" + param + "')");
                break;
            case R.id.my_btn:
                param = "App参数";
                webView.evaluateJavascript("javascript: returnParams('" + param + "')", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        Toast.makeText(MainActivity.this,value,Toast.LENGTH_SHORT).show();
                    }
                });
                break;

        }

    }

}
