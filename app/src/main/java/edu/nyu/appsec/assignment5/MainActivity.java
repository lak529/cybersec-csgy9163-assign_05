package edu.nyu.appsec.assignment5;

//import android.Manifest;
//import android.content.Context;
import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
import android.net.Uri;
//import android.net.http.SslError;
//import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

//import java.io.BufferedInputStream;
//import java.io.IOException;
//import java.io.SerializablePermission;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;

public class MainActivity extends AppCompatActivity /*implements LocationListener*/ {
    private static final String SPELL_CHECK_URL = "http://appsecclass.report:8080/";
    private static final String KNOWN_HOST = "appsecclass.report";

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url = String.valueOf(request.getUrl());
            String host = Uri.parse(url).getHost();

            if (KNOWN_HOST.equals(host)) {
                return false;
            }

            //Luna 2019/12/09 just disabling this, we shouldn't have to open external resources

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            view.getContext().  //Luna 2019/12/09 added view.getContext().  does it make a difference?
                    startActivity(intent);

            return true;
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request){
            String url = String.valueOf(request.getUrl());
            String host = Uri.parse(url).getHost();

            if (KNOWN_HOST.equals(host)) {
                return super.shouldInterceptRequest(view,request);
            }
            else {
                return null;
            }
        }
    }
/*
    *//* Get location data to provide language localization
    *  Supported languages ar-DZ zh-CN en-US en-IN en-AU fr-FR
    *//*
    @Override
    public void onLocationChanged(Location location) {
        //Luna 2019/12/09 disabled.  Dont' think sending our location is needed
*//*        URL url = null;
        try {
            url = new URL(SPELL_CHECK_URL + "metrics"
                    +"?lat="
                    +location.getLatitude()+"&long=" + location.getLongitude()
            );
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return;
        }

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }*//*
    }*/

/*    *//* Necessary to implement the LocationListener interface
    *//*
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {}

    @Override
    public void onProviderEnabled(String s) {}

    @Override
    public void onProviderDisabled(String s) {}*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView view = new WebView(this);
        view.setWebViewClient(new MyWebViewClient());

        WebSettings settings = view.getSettings();
        settings.setJavaScriptEnabled(true);  //Luna 2019/12/09 its ugly that this is required just to support bootstrap

        //Luna 2019/12/09 explicitly disable the following
        settings.setAllowFileAccessFromFileURLs(false);
        settings.setAllowUniversalAccessFromFileURLs(false);
        settings.setAllowFileAccess(false);
        settings.setGeolocationEnabled(false);

        //Luna 2019/12/09 disable the location manager, since we have no need of GPS
//        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
/*        if (!(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }*/

        setContentView(view);
        view.loadUrl(SPELL_CHECK_URL + "register");
    }
}
