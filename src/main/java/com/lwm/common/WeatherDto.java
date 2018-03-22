package com.lwm.common;

import java.util.List;


/**
 * 百度天气实体类
 * @版本 V 1.0
 */
public class WeatherDto {

	private String status;//返回结果状态信息
	private String date;//当前时间
	private List<WeatherInfo> results;//天气预报信息 结果只有一条
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<WeatherInfo> getResults() {
		return results;
	}
	public void setResults(List<WeatherInfo> results) {
		this.results = results;
	}
}
