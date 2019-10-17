package com.ali.informationsample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class NetUtil {

    //1、写一个 当前类的成员变量
    static NetUtil netUtil = new NetUtil();

    //2、geti
    public static NetUtil getInstance() {
        return netUtil;
    }

    //3、私有构造器
    private NetUtil() {
    }

    //流转成json  , Inputstream -> String
    public String io2String(InputStream inputStream) {
        //三件套
        byte[] bytes = new byte[1024];
        int len = -1;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String json = "";
        try {
            //开始一边读一边写
            while ((len = inputStream.read(bytes)) != -1) {
                byteArrayOutputStream.write(bytes, 0, len);
            }
            //写完之后，把输出流转成byte数组
            byte[] bytes1 = byteArrayOutputStream.toByteArray();
            json = new String(bytes1);
        } catch (IOException e) {

        } finally {
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return json;
    }


    //流转成Bitmap  , Inputstream -> Bitmap
    public Bitmap io2Bitmap(InputStream inputStream) {
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        return bitmap;
    }


    //联网请求返回json , url路径 -> String
    public void doGet(final String httpUrl, final MyCallBack myCallBack) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected void onPostExecute(String s) {
                //主线程
                myCallBack.onDoGetSuccess(s);
            }

            @Override
            protected String doInBackground(String... strings) {
                //子线程  联网
                String json = "";
                InputStream inputStream = null;
                try {
                    URL url = new URL(httpUrl);
                    //获取连接对象
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //设置请求方式
                    httpURLConnection.setRequestMethod("GET");
                    //设置超时时间
                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.setReadTimeout(6000);
                    //真正打开连接
                    httpURLConnection.connect();
                    //判断是否成功, 只有状态码 是 200 的时候，算成功
                    if (httpURLConnection.getResponseCode() == 200) {
                        //成功之后拿到流
                        inputStream = httpURLConnection.getInputStream();
                        //得到流之后转成json
                        json = io2String(inputStream);
                    } else {
                        //失败之后log提醒
                        Log.e("TAG", "请求失败");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return json;
            }
        }.execute();


    }


    //联网请求返回Bitmap  url路径 -》Bitmap
    public void doGetPhoto(final String httpUrl, final MyCallBack callBack) {
        new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected void onPostExecute(Bitmap bitmap) {
                callBack.onDoGetPhotoSuccess(bitmap);
            }

            @Override
            protected Bitmap doInBackground(String... strings) {
                Bitmap bitmap = null;
                InputStream inputStream = null;
                try {
                    URL url = new URL(httpUrl);
                    //获取连接对象
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    //设置请求方式
                    httpURLConnection.setRequestMethod("GET");
                    //设置超时时间
                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.setReadTimeout(6000);
                    //真正打开连接
                    httpURLConnection.connect();
                    //判断是否成功, 只有状态码 是 200 的时候，算成功
                    if (httpURLConnection.getResponseCode() == 200) {
                        //成功之后拿到流
                        inputStream = httpURLConnection.getInputStream();
                        //得到流之后转成json
                        bitmap = io2Bitmap(inputStream);
                    } else {
                        //失败之后log提醒
                        Log.e("TAG", "请求图片失败");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return bitmap;
            }
        }.execute();
    }


    //是否有网
    public boolean hasNet(Context context) {
        //获取ConnectivityManager
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取网络信息
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        //只有网络信息这个类不为null，并且可用，返回true
        if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
            return true;
        } else {
            return false;
        }

    }


    //是否是WIFI
    public boolean isWifi(Context context) {
        //获取ConnectivityManager
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取网络信息
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        //只有网络信息这个类不为null，并且可用，返回true
        if (activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        } else {
            return false;
        }
    }

    //当前是否是移动网络 4G 3G
    public boolean isMobile(Context context) {
        //获取ConnectivityManager
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取网络信息
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        //只有网络信息这个类不为null，并且可用，返回true
        if (activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        } else {
            return false;
        }
    }


    public interface MyCallBack {
        void onDoGetSuccess(String json);

        void onDoGetPhotoSuccess(Bitmap bitmap);
    }
}
