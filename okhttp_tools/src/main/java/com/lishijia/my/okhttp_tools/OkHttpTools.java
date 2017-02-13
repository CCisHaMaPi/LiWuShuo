package com.lishijia.my.okhttp_tools;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lsj on 2017/2/7.
 */

public class OkHttpTools {

    private static OkHttpClient client;

    static {
        if (null == client){
            client = new OkHttpClient.Builder().build();
        }
    }

    public static OkHttpBuilder builder(){

        return new OkHttpBuilder();
    }

    public static class OkHttpBuilder {

        private String url;
        private OkCallBack okCallBack;

        private Handler mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (null != msg.obj){
                    okCallBack.success(msg.obj.toString());
                }
            }
        };

        public OkHttpBuilder url(String url){
            this.url = url;
            return this;
        }

        public void callback(OkCallBack callBack){
            this.okCallBack = callBack;
        }


        public OkHttpBuilder get(){
            if (TextUtils.isEmpty(url)){
                throw new NullPointerException("url is empty !");
            }

            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    String result = response.body().string();
                    Message message = mHandler.obtainMessage();
                    message.obj = result;
                    message.sendToTarget();
                }
            });
            return this;
        }

    }

    public interface OkCallBack {
        void success(String result);
    }
}
