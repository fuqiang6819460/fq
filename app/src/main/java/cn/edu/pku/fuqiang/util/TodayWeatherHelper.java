package pku.zhang.util;

import android.util.Log;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import pku.zhang.bean.TodayWeather;
import pku.zhang.myweather.MainActivity;

/**
 * Created by Chen on 2015/6/18.
 */
public class TodayWeatherHelper {

    //获取天气信息，保存在 TodayWeather 对象中
    public static TodayWeather getTodayWeather(String cityCode) {

        final String address = "http://wthrcdn.etouch.cn/WeatherApi?citykey=" + cityCode;
        Log.d("myWeather", address);

        TodayWeather todayWeather = null;
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(address);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = httpResponse.getEntity();
                InputStream responseStream = entity.getContent();
                responseStream = new GZIPInputStream(responseStream);

                BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream));
                StringBuilder response = new StringBuilder();
                String str;
                while ((str = reader.readLine()) != null) {
                    response.append(str);
                }
                String responseStr = response.toString();
                //Log.d("myWeather",responseStr);
                todayWeather = XmlParse.parseXML(responseStr);
                Log.d("myWeather", todayWeather.toString());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return todayWeather;
    }


}




