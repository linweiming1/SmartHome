package com.lwm.common;

/**
 * 天气详情类
 */
public class WeatherData {

	private String date;//周三 12月03日
	private String dayPictureUrl;
	private String nightPictureUrl;
	private String weather;//晴
	private String wind;//微风
	private String temperature;//5 ~ -3℃
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDayPictureUrl() {
		return dayPictureUrl;
	}
	public void setDayPictureUrl(String dayPictureUrl) {
		this.dayPictureUrl = dayPictureUrl;
	}
	public String getNightPictureUrl() {
		return nightPictureUrl;
	}
	public void setNightPictureUrl(String nightPictureUrl) {
		this.nightPictureUrl = nightPictureUrl;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getWind() {
		return wind;
	}
	public void setWind(String wind) {
		this.wind = wind;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
}
