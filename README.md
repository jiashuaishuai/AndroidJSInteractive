# AndroidJSInteractive
android 实现与Html之间的交互，Demo
## JS交互

```java
 webView.getSettings().setJavaScriptEnabled(true);//支持js
  webView.addJavascriptInterface(new JavaScriptObject(this, webView), "jsObj");
```

## webView内启动qq，打电话等功能

```java
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

```
## webViewClient中函数概述

```java
/** 
 * 在开始加载网页时会回调 
 */  
public void onPageStarted(WebView view, String url, Bitmap favicon)   
/** 
 * 在结束加载网页时会回调 
 */  
public void onPageFinished(WebView view, String url)  
/** 
 * 拦截 url 跳转,在里边添加点击链接跳转或者操作 
 */  
public boolean shouldOverrideUrlLoading(WebView view, String url)  
/** 
 * 加载错误的时候会回调，在其中可做错误处理，比如再请求加载一次，或者提示404的错误页面 
 */  
public void onReceivedError(WebView view, int errorCode,String description, String failingUrl)  
/** 
 * 当接收到https错误时，会回调此函数，在其中可以做错误处理 
 */  
public void onReceivedSslError(WebView view, SslErrorHandler handler,SslError error)  
/** 
 * 在每一次请求资源时，都会通过这个函数来回调 
 */  
public WebResourceResponse shouldInterceptRequest(WebView view,  
        String url) {  
    return null;  
}  

```




## 摘自 <http://blog.csdn.net/harvic880925/article/details/51523983>
