package com.lishijia.my.liwushuo.view.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;

import com.lishijia.my.liwushuo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InfoWebActivity extends AppCompatActivity {

    @BindView(R.id.info_webView)
    WebView webView;
    private String webViewUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_webview);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (null != bundle && !bundle.isEmpty()){
            webViewUrl = bundle.getString("url");
            webView.loadUrl(webViewUrl);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (event.getAction() == KeyEvent.ACTION_DOWN){
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()){
                        webView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
