package com.example.jiashuaishuai.androidjsinteractive;

import android.app.Activity;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * Created by jiashuaishuai on 2016/3/31.
 */
public class JavaScriptObject {
    private Activity mContext;
    private WebView webView;


    public JavaScriptObject(Activity context, WebView webView) {
        this.mContext = context;
        this.webView = webView;

    }

    /**
     * Html传给App
     * @param param
     */
    @JavascriptInterface//1.7以上需要注解
    public void HtmlcallJava(final String param) {
        Toast.makeText(mContext, "HtmlCallApp:" + param, Toast.LENGTH_SHORT).show();
    }

    /**
     * Html传给App，App处理再传回给Html
     * @param param
     */
    @JavascriptInterface//1.7以上需要注解
    public void HtmlcallJava2(final String param) {
        mContext.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                webView.loadUrl("javascript: showFromHtml2('" + param + "2" + "')");
            }
        });


    }

    /**
     * App传给html参数，Html处理，传回给APp
     */
    @JavascriptInterface
    public void paramAppToHtmlToApp(final String param) {
        Toast.makeText(mContext, param, Toast.LENGTH_SHORT).show();

    }

}
