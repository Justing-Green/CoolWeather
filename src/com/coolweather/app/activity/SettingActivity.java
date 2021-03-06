package com.coolweather.app.activity;

import java.util.ArrayList;
import java.util.List;

import com.coolweather.app.R;
import com.coolweather.app.service.AutoUpdateService;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class SettingActivity extends Activity {
	private Button back;
	private List<String> settingList = new ArrayList<String>();
	private ArrayAdapter<String> adapter;
	private ListView listView;
	private String[] yesOrnot = new String[]{"开启", "禁止"};
	private String[] frequency = new String[]{"0.5","1","2","4","8"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_layout);
		back = (Button) findViewById(R.id.setting_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SettingActivity.this,
						WeatherActivity.class);
				startActivity(intent);
				finish();
			}
		});
		initList();
		listView = (ListView) findViewById(R.id.setting_list_view);
		adapter = new ArrayAdapter<String>(SettingActivity.this,
				android.R.layout.simple_list_item_1, settingList);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch ((int)id) {
				case 0:
					showDialog();
					break;
				case 1:
					setTime();
					break;
				case 2:
					Toast.makeText(SettingActivity.this, "暂时无法更新",
							Toast.LENGTH_LONG).show();
					break;
				default:
					break;
				}
			}
			
		});
	}
	private void showDialog() {
		new AlertDialog.Builder(this).setTitle("允许后台自动更新天气").
		setItems(yesOrnot,new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							Intent intent = new Intent(SettingActivity.this,
									AutoUpdateService.class);
							startService(intent);
							Toast.makeText(SettingActivity.this, "您已经开启了后台更新服务",
									Toast.LENGTH_SHORT).show();
							break;
						case 1:
							Intent intent2 = new Intent(SettingActivity.this,
									AutoUpdateService.class);
							stopService(intent2);
							Toast.makeText(SettingActivity.this, "您已经关闭了后台更新服务",
									Toast.LENGTH_SHORT).show();
							break;
						default:
							break;
						}
					}
				}).setNegativeButton("Cancel", new DialogInterface
						.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();
	}
	private void setTime() {
		new AlertDialog.Builder(this).setTitle("设置自动更新频率(/h)").
		setItems(frequency,new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(SettingActivity.this,
								AutoUpdateService.class);
						switch (which) {
						case 0:
							intent.putExtra("frequency", 0.5);
							Toast.makeText(SettingActivity.this,
									"后台每半小时更新一次",Toast.LENGTH_SHORT).
									show();
							break;
						case 1:
							intent.putExtra("frequency", 1.0);
							Toast.makeText(SettingActivity.this,
									"后台每一小时更新一次",Toast.LENGTH_SHORT).
									show();
							break;
						case 2:
							intent.putExtra("frequency", 2.0);
							Toast.makeText(SettingActivity.this,
									"后台每两小时更新一次",Toast.LENGTH_SHORT).
									show();
							break;
						case 3:
							intent.putExtra("frequency", 4.0);
							Toast.makeText(SettingActivity.this,
									"后台每四小时更新一次",Toast.LENGTH_SHORT).
									show();
							break;
						case 4:
							intent.putExtra("frequency", 8.0);
							Toast.makeText(SettingActivity.this,
									"后台每八小时更新一次",Toast.LENGTH_SHORT).
									show();
							break;
						default:
							break;
						}
						startService(intent);
					}
				}).setNegativeButton("Cancel", new DialogInterface
						.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();
	}
	private void initList() {
		String autoRefreshing = "自动更新天气";
		settingList.add(autoRefreshing);
		String refreshFrequency = "更新频率";
		settingList.add(refreshFrequency);
		String newVersion = "检查新版本";
		settingList.add(newVersion);
	}
}
