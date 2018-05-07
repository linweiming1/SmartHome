package com.lwm.common;

/*
* 天气类中的具体信息
* */
public class WeatherIndex {

	private String title;//穿衣
	private String zs;//冷
	private String tipt;//指数
	private String des;//描述
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getZs() {
		return zs;
	}
	public void setZs(String zs) {
		this.zs = zs;
	}
	public String getTipt() {
		return tipt;
	}
	public void setTipt(String tipt) {
		this.tipt = tipt;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
}
