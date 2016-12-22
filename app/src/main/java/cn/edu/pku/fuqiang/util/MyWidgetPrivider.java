package pku.zhang.util;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import pku.zhang.myweather.MainActivity;
import pku.zhang.myweather.R;


public class MyWidgetPrivider extends AppWidgetProvider {

    public final String CLOCK_CLICK = "com.example.chen.myweather.widget_time.click";
    public final String WEATHER_CLICK = "com.example.chen.myweather.widget_weather.click";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        //加载  指定界面布局文件（作为Widget），创建 RemoteViews 对象
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.layout_widget);

        //更新
        UpdateUIService.context = context;
        UpdateUIService.widgetManager = appWidgetManager;
        UpdateUIService.remoteViews = views;

        Intent intent = new Intent(context, UpdateUIService.class);
        context.startService(intent); //开启Service
        Log.d("setIntent",CLOCK_CLICK);

        Intent intent1 = new Intent(CLOCK_CLICK);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intent1,0);
        views.setOnClickPendingIntent(R.id.clock,pendingIntent);
        Log.d("setIntent",WEATHER_CLICK);
        intent1.setAction(WEATHER_CLICK);
        pendingIntent = PendingIntent.getBroadcast(context,0,intent1,0);
        views.setOnClickPendingIntent(R.id.weather_icon,pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetIds,views);

    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Intent intent = new Intent(context,UpdateUIService.class);
        context.stopService(intent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.d("intent action",intent.getAction());
        if(intent.getAction().equals(CLOCK_CLICK)){

        }
        else if(intent.getAction().equals(WEATHER_CLICK)){
            Intent intent1 = new Intent();
            intent1.setClass(context, MainActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
        }
    }
}
