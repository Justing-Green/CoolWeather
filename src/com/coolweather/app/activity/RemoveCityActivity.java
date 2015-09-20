package com.coolweather.app.activity;

import com.coolweather.app.R;
import com.coolweather.app.util.RemoveCityAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

public class RemoveCityActivity extends Activity implements OnClickListener {
	ListView removeListView;
	ListAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.remove_city_layout);
		adapter = new RemoveCityAdapter(RemoveCityActivity.this, R.layout.
				remove_city_item, CityCollector.City);
		removeListView = (ListView) findViewById(R.id.remove_ciy_item);
		removeListView.setAdapter(adapter);
		Button cancle = (Button) findViewById(R.id.cancle); 
		Button determine = (Button) findViewById(R.id.determine);
		cancle.setOnClickListener(this);
		determine.setOnClickListener(this);
 	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cancle:
			Intent intent = new Intent(RemoveCityActivity.this,
					ConcernedCityActivity.class);
			startActivity(intent);
			finish();
			break;
		case R.id.determine:
			Intent intent2 = new Intent(RemoveCityActivity.this,
					ConcernedCityActivity.class);
			startActivity(intent2);
			finish();
			break;
		default:
			break;
		}
	}
}
