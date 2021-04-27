package edu.umb.cs443;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.JsonReader;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;


public class MainActivity extends FragmentActivity implements OnMapReadyCallback{

	public final static String DEBUG_TAG="edu.umb.cs443.MYMSG";
	public final static String API_KEY="cc4ae6e545dee0a295a471824c9fdbda";
	EditText e;
	TextView t;
	ImageView i;
    String urlA = "https://api.openweathermap.org/data/2.5/weather?";
    String iconURL = "https://openweathermap.org/img/wn/";
    String temp;
    String icon;
    Thread run;
    Bitmap bitmap;
    String input;
    InputStream res;
    Double lat;
    Double lon;


    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapFragment mFragment=((MapFragment) getFragmentManager().findFragmentById(R.id.map));
        mFragment.getMapAsync(this);

        e = (EditText) findViewById(R.id.editText);
        t = (TextView) findViewById(R.id.textView);
        i = (ImageView) findViewById(R.id.imageView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void getWeatherInfo(View v){
        input = e.getText().toString();

        // try to parse input into int if NumberFormException, assume input is a city and not a zip code
        try{
            System.out.println(Integer.parseInt(input));

            //create url with user input

            run = new Thread(new Runnable() {

                @Override
                public void run() {

                    try {
                        String url = urlA + "zip=" + input + "" + "&APPID=" + API_KEY + "&units=metric";
                        URL u = new URL(url);
                        HttpsURLConnection request = (HttpsURLConnection) u.openConnection();
                        request.connect();

                        res = request.getInputStream();

                        Scanner s = new Scanner(res).useDelimiter("\\A");
                        String result = s.hasNext() ? s.next() : "";

                        JSONObject json = new JSONObject(result);

                        temp = json.getJSONObject("main").getString("temp");
                        System.out.println("temp:");
                        System.out.println(temp);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                t.setText(temp + "°C");
                            }
                        });

                        icon = json.getJSONArray("weather").getJSONObject(0).getString("icon");
                        icon = icon + ".png";
                        System.out.println(icon);

                        URL imageURL = new URL(iconURL + icon);
                        HttpsURLConnection imageReq = (HttpsURLConnection) imageURL.openConnection();
                        imageReq.setRequestMethod("GET");
                        imageReq.connect();

                        InputStream is = imageReq.getInputStream();

                        bitmap = BitmapFactory.decodeStream(is);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                i.setImageBitmap(bitmap);
                            }
                        });

                        lat = json.getJSONObject("coord").getDouble("lat");
                        lon = json.getJSONObject("coord").getDouble("lon");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 12f));
                            }
                        });

                    } catch (Exception ex) {System.out.println(ex);}

                }
            });
            run.start();

        } catch (NumberFormatException e) {

            run = new Thread(new Runnable() {

                @Override
                public void run() {

                    try {
                        String url = urlA + "q=" + input + "" + "&APPID=" + API_KEY + "&units=metric";
                        URL u = new URL(url);
                        HttpsURLConnection request = (HttpsURLConnection) u.openConnection();
                        request.connect();

                        res = request.getInputStream();

                        Scanner s = new Scanner(res).useDelimiter("\\A");
                        String result = s.hasNext() ? s.next() : "";

                        JSONObject json = new JSONObject(result);

                        temp = json.getJSONObject("main").getString("temp");
                        System.out.println("temp:");
                        System.out.println(temp);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                t.setText(temp + "°C");
                            }
                        });

                        icon = json.getJSONArray("weather").getJSONObject(0).getString("icon");
                        icon = icon + ".png";
                        System.out.println(icon);

                        URL imageURL = new URL(iconURL + icon);
                        HttpsURLConnection imageReq = (HttpsURLConnection) imageURL.openConnection();
                        imageReq.setRequestMethod("GET");
                        imageReq.connect();

                        InputStream is = imageReq.getInputStream();

                        bitmap = BitmapFactory.decodeStream(is);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                i.setImageBitmap(bitmap);
                            }
                        });

                        lat = json.getJSONObject("coord").getDouble("lat");
                        lon = json.getJSONObject("coord").getDouble("lon");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 12f));
                            }
                        });

                    } catch (Exception ex) {System.out.println(ex);}

                }
            });
            run.start();

        } catch (Exception e) {
            System.out.println(e);
        }

    }



    @Override
    public void onMapReady(GoogleMap map) {
        this.mMap=map;
    }
}
