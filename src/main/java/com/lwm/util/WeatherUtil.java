package com.lwm.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.lwm.common.WeatherData;
import com.lwm.common.WeatherDto;
import com.lwm.common.WeatherInfo;
import com.lwm.common.Weather;
import org.apache.commons.lang3.StringUtils;

/**
 * 百度天气预报工具类
 */
public class WeatherUtil {

	private static final String URL = "http://api.map.baidu.com/telematics/v3/weather?location=CITY&output=json&ak=KEY";
	private static final String KEY = "8r49aUUBvq15d9fNcEzpUMX7";

	/**
	 * 天气API
	 *
	 * @param city
	 * @return
	 */
	public static Weather getWeather(String city) {
		if (StringUtils.isEmpty(city)) {
			return null;
		}
		city = HttpUtil.urlEncodeUTF8(city);
		String json = HttpUtil.httpGet(URL.replace("CITY", city).replace("KEY", KEY));

		WeatherDto dto = new Gson().fromJson(json, WeatherDto.class);

		if (dto == null) {
			return null;
		}
		Weather weather = null;
		List<WeatherInfo> results = dto.getResults();
		if (results != null && results.size() > 0) {
			WeatherInfo result = results.get(0);
			if (result != null) {
				List<WeatherData> weather_data = result.getWeather_data();
				if (weather_data != null && weather_data.size() > 0) {
					Date now = new Date();
					SimpleDateFormat formatter = new SimpleDateFormat(
							"yyyy-MM-dd E");
					WeatherData weatherData = weather_data.get(0);
					weather = new Weather(result.getCurrentCity(),
							result.getPm25(), weatherData.getDate(),
							weatherData.getWeather(), weatherData.getWind(),
							weatherData.getTemperature(), formatter.format(now));
				}
			}
		}

		return weather;
	}
}
