package pku.zhang.util;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;



import java.text.SimpleDateFormat;
import java.util.Date;

import pku.zhang.bean.TodayWeather;
import pku.zhang.myweather.R;

public class UpdateUIService extends Service {

    public final int UPDATE_WEATHER = 1;
    private SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");

    private static Date lastUpdateWeatherDate = new Date();

    public static Context context;
    public static AppWidgetManager widgetManager;
    public static RemoteViews remoteViews;
    SharedPreferences sharedPreferences;
    String cityCode;

    private int[] numViews = new int[]{
            R.id.hour01,R.id.hour02,R.id.minute01,R.id.minute02};

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("broad cast receiver","-service started-");
           //更细 UI
            updateUI();
        }
    };

    private void updateUI(){
        Date date = new Date();
        String timeString = sdf.format(date);
        int num;
        for(int i = 0 ; i < 4; i++){
            num = timeString.charAt(i) - 48;
            remoteViews.setTextViewText(numViews[i],String.valueOf(num));
        }
        ComponentName componentName = new ComponentName(context,MyWidgetPrivider.class);
        widgetManager.updateAppWidget(componentName,remoteViews);
        if(date.getTime() - lastUpdateWeatherDate.getTime() > 18000000) {
            Log.d("update weather",date.toString());
            lastUpdateWeatherDate = date;
            updateTodayWeather();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_TIME_CHANGED);
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        registerReceiver(broadcastReceiver,intentFilter);
        init();
    }

    void init(){
        //获取配置文件 中的 城市编号
        sharedPreferences = context.getSharedPreferences("config", MODE_PRIVATE);
        cityCode = sharedPreferences.getString("main_city_code","101010100");
        updateTodayWeather();
    }

    void updateTodayWeather() {
        //创建线程，更新信息
        new Thread(new Runnable() {
            @Override
            public void run() {
                TodayWeather todayWeather = TodayWeatherHelper.getTodayWeather(cityCode);//涉及到网络操作
               //更新界面
                if(todayWeather != null){
                    Message msg = new Message();
                    msg.what = UPDATE_WEATHER;
                    msg.obj = todayWeather;
                    handler.sendMessage(msg);
                }

            }
        }).start();
    }


    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case UPDATE_WEATHER:
                    updateWeatherInfo((TodayWeather)msg.obj);
            }
        }
    };

    //更新天气，图片、文字内容等
    private void updateWeatherInfo(TodayWeather todayWeather){
        Date date = new Date();

        String weatherTypePicName = "";
        if(todayWeather!= null)
            weatherTypePicName = "biz_plugin_weather_"+ MyPinyinHelper.toPinyin(todayWeather.getType());
        else
            weatherTypePicName = "biz_plugin_weather_qing";

        remoteViews.setImageViewResource(R.id.weather_icon, GeneralUtil.getDrawableId(context,weatherTypePicName));
        CharSequence info = (date.getMonth()+1)+"月"+todayWeather.getDate()+" | "
                +todayWeather.getCity()+" "+todayWeather.getLow()+"~"+todayWeather.getHigh();

        remoteViews.setTextViewText(R.id.info,info);
        ComponentName componentName = new ComponentName(context,MyWidgetPrivider.class);
        widgetManager.updateAppWidget(componentName,remoteViews);
    }



    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updateUI();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

}
