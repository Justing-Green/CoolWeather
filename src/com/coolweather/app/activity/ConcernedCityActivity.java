package com.coolweather.app.activity;

import java.util.ArrayList;
import java.util.List;

import com.coolweather.app.R;
import com.coolweather.app.util.CityAdapter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
/**
 * 
 * @author Justing-Green
 * 允许选择多城市效果不成功，解决Toast问题，但下拉刷新问题依然存在
 */
public class ConcernedCityActivity extends Activity implements OnClickListener {
	List<CityItem> cityList = new ArrayList<CityItem>();
	ListView listView;
	CityAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_city_layout);
		inits();
		adapter = new CityAdapter(ConcernedCityActivity.this, R.layout.
				city_item, cityList);
		listView = (ListView) findViewById(R.id.ciy_item);
		listView.setAdapter(adapter);
		Button back  =(Button) findViewById(R.id.backfrom_morecity);
		Button edit = (Button) findViewById(R.id.editting);
		Button add = (Button) findViewById(R.id.add_city);
		back.setOnClickListener(this);
		edit.setOnClickListener(this);
		add.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.backfrom_morecity:
			Intent intent = new Intent(this, WeatherActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.editting:
			Toast.makeText(this, "Editting", Toast.LENGTH_LONG).show();
			break;
		case R.id.add_city:
			Intent intent2 = new Intent(this, ChooseAreaActivity.class);
			intent2.putExtra("from_concerned_city", true);
			startActivity(intent2);
			finish();
			break;
		default:
			break;
		}
	}
	void inits() {
		SharedPreferences prefs = PreferenceManager.
				getDefaultSharedPreferences(this);
		if (prefs.getBoolean("city_selected", false)) {
			CityItem first = new CityItem(prefs.getString("city_name", ""),
					prefs.getString("temp2", "") + "/" + 
			prefs.getString("temp1", ""));
			cityList.add(first);
		}
	}
}
