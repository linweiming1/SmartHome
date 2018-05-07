package com.lwm.common;

/**
 * 天气预报
 * @author lwm
 */
public class Weather {

	private int id;// 主键
	private String cityName;// 城市
	private String pm;// pm2.5
	private String date;// 时间
	private String weather;// 天气
	private String wind;// 风力
	private String temperature;// 温度
	private String time;// 更新时间 2017111214

	public Weather() {
		super();
	}

	public Weather(String cityName, String pm, String date, String weather,
			String wind, String temperature, String time) {
		super();
		this.cityName = cityName;
		this.pm = pm;
		this.date = date;
		this.weather = weather;
		this.wind = wind;
		this.temperature = temperature;
		this.time = time;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getPm() {
		return pm;
	}

	public void setPm(String pm) {
		this.pm = pm;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Weather [id=" + id + ", cityName=" + cityName + ", pm=" + pm
				+ ", date=" + date + ", weather=" + weather + ", wind=" + wind
				+ ", temperature=" + temperature + ", time=" + time + "]";
	}
}
