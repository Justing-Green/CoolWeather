package com.coolweather.app.activity;

public class CityItem {
	public String cityName;
	public String weatherInfo;
	public CityItem(String cityName, String weatherInfo) {
		this.cityName = cityName;
		this.weatherInfo = weatherInfo;
	}
	public String getCityName() {
		return cityName;
	}
	public String getWeatherInfo() {
		return weatherInfo;
	}
}
