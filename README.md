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
##添加打开App功能
核心代码html端
```html
        <a href="http://www.baidu.com?params=张三">打开app页面(http://www.baidu.com?params=张三)</a>
        <br><br><br>
        <a href="app://AndroidHtml?params=zhangsan">打开app页面(app://AndroidHtml?params=zhangsan)</a>
        <br>
```
Android端代码
```java
<activity
            android:name=".openapp.AppActivityActivity"
            android:label="@string/title_activity_app">


            <!--第一种方式:自定义-->
            <intent-filter>
                <action android:name="android.intent.action.VIEW" />

                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.BROWSABLE" />

                <data
                    android:host="AndroidHtml"
                    android:scheme="app" />
            </intent-filter>

            <!--第二种方式：以http协议-->
            <intent-filter>
                <action android:name="android.intent.action.VIEW" />

                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.BROWSABLE" />

                <data
                    android:host="www.baidu.com"
                    android:scheme="http" />
            </intent-filter>

            <!-- 2个方式选择一种就可以了-->
        </activity>
```
Activity端代码
```java
public class AppActivityActivity extends AppCompatActivity {
    public static final String TAG = "AppActivityActivity";

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        textView = (TextView) findViewById(R.id.text);

        Intent intent = getIntent();
        String scheme = intent.getScheme();
        Uri uri = intent.getData();


        if (uri != null) {
            String host = uri.getHost();
            String path = uri.getPath();
            String encodedPath = uri.getEncodedPath();
            String queryString = uri.getQuery();

            // 链接所有数据
            String dataString = intent.getDataString();

            // 获取链接所携带的参数数据
            String params = uri.getQueryParameter("params");
        }
    }
}
```



## 摘自 <http://blog.csdn.net/harvic880925/article/details/51523983>
##打开app摘自<http://blog.csdn.net/fenggit/article/details/51028277>
