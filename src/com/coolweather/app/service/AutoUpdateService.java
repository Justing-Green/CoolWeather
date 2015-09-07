package com.coolweather.app.service;

import com.coolweather.app.receiver.AutoUpdateReceiver;
import com.coolweather.app.util.HttpCallbackListener;
import com.coolweather.app.util.HttpUtil;
import com.coolweather.app.util.Utility;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.widget.Toast;
/**
 * 
 * @author Justing-Green
 * 现阶段存在问题：Toast滞后，启动服务后无法恢复各标志位的初始化信息
 *
 */
public class AutoUpdateService extends Service {
	//后台自动更新标志位choose,0为未设置时的默认值，1为设置允许标志位，2为设置禁止标志位
	private int choose;
	//后台更新频率frequency
	private double frequency;
	//Toast提示标志位,2为默认不提示
	private int freTag = 2;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	@Override
	public int onStartCommand(final Intent intent, int flags, int startId) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				choose = intent.getIntExtra("choose", 0);
				frequency = intent.getDoubleExtra("frequency", 8.0);
				if (choose == 1) {
					updateWeather();
					freTag = 1;
				} else if (choose == 2) {
					freTag = 0;
					onDestroy();
				} else {
					updateWeather();
				}
				
			}
		}).start();
		if ( freTag ==1) {
			Toast.makeText(AutoUpdateService.this, "已开启后台自动更新天气，每" 
					+ frequency + "小时更新一次",Toast.LENGTH_SHORT).show();
		} else if (freTag == 0){
			Toast.makeText(AutoUpdateService.this, "已停止后台自动更新天气",
					Toast.LENGTH_SHORT).show();
		}
		AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
		int anHour = (int)(frequency * 60 * 60 * 1000);
//		int anHour = 8 * 60 * 60 * 1000;
		long tiggerAtTime  = SystemClock.elapsedRealtime() + anHour;
		Intent i = new Intent(this, AutoUpdateReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
		manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, tiggerAtTime, pi);
		return super.onStartCommand(intent, flags, startId);
	}
	private void updateWeather() {
		SharedPreferences prefs = PreferenceManager.
				getDefaultSharedPreferences(this);
		String weatherCode = prefs.getString("weather_code", "");
		String address = "http://www.weather.com.cn/data/cityinfo/" + weatherCode
				+ ".html";
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response) {
				Utility.handleWeatherResponse(AutoUpdateService.this, response);
			}
			@Override
			public void onError(Exception e) {
				e.printStackTrace();
			}
		});
	}
}
