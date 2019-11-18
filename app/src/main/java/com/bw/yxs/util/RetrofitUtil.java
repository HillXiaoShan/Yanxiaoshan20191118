package com.bw.yxs.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.bw.yxs.R;
import com.bw.yxs.api.API;
import com.bw.yxs.app.App;

import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {
    public static RetrofitUtil util;
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private RetrofitUtil(){
        try {

            //创建证书对象，方便管理证书数据
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);//初始化证书资源，首次是空


            //校验证书，x.509协议，所有的证书都是通过x.509协议生成的
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            X509Certificate certificate = (X509Certificate) certificateFactory.generateCertificate(App.context.getResources().openRawResource(R.raw.server));


//ssl协议入场，看看是不是符合ssl协议标准
            SSLContext sc = SSLContext.getInstance("TLS");
//信任证书管理,这个是由我们自己生成的,信任我们自己的服务器证书
            TrustManager tm = new MyTrustManager(certificate);
            sc.init(null, new TrustManager[]{
                    tm
            }, null);

            okHttpClient=new OkHttpClient.Builder()
                    .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(5, TimeUnit.SECONDS)
                    .writeTimeout(5, TimeUnit.SECONDS)
                    .sslSocketFactory(sc.getSocketFactory(), (X509TrustManager) tm)//校验ssl证书
                    .hostnameVerifier(new TrustHostnameVerifier())//校验主机名（校验服务器），域名验证
                    .build();

            retrofit=new Retrofit.Builder()
                    .baseUrl(API.url)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }catch (Exception io){
            Log.e("tag","网络异常");
        }
    }

    //单例模式
    public static RetrofitUtil getInstance(){
        if (util==null){
            synchronized (RetrofitUtil.class){
                if (util==null){
                    util=new RetrofitUtil();
                }
            }
        }
        return util;
    }

    //网络判断
    public boolean getNet(Context context){
        ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info!=null){
            return info.isConnected();
        }
        return false;
    }

    //动态代理
    public <T> T createService(Class<T> tClass){
        return retrofit.create(tClass);
    }

    static class MyTrustManager implements X509TrustManager {
        X509Certificate cert;


        MyTrustManager(X509Certificate cert) {
            this.cert = cert;
        }


        /**
         129          * 信任客户端的
         130          * @param chain
         131          * @param authType
         132          * @throws CertificateException
         133          */
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            // 我们在客户端只做服务器端证书校验。
        }


        /**
         140          * 信任服务器的
         141          * @param chain
         142          * @param authType
         143          * @throws CertificateException
         144          */
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            // 确认服务器端证书和代码中 hard code 的 CRT 证书相同。
            if (chain[0].equals(this.cert)) {
                Log.i("Jin", "checkServerTrusted Certificate from server is valid!");
                return;// found match
            }
            throw new CertificateException("checkServerTrusted No trusted server cert found!");
        }


        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }




    /**
     163      * 校验主机名
     164      */
    public static class TrustHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {


            if (hostname.trim().equals("172.17.8.100")) {//必须注意，根据题目要求配置相应主机名（域名或者ip地址）
                return true;
            }else{
                return false;
            }
        }
    }

}
