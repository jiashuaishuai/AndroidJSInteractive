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
