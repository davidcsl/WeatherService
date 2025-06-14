package com.david.weather_service.service;

import com.david.weather_service.model.dto.OpenWeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@Slf4j
public class WeatherInfoAcquirer {

  @Value("${open-weather.url}")
  private String OPEN_WEATHER_URL;

  @Value("${open-weather.appid}")
  private String APPID;

  public OpenWeatherResponse queryWeather(String country, String city) throws Exception {

    if (Objects.isNull(city) || Objects.isNull(country)) {
      throw new Exception("Country / City not detected.");
    }

    RestTemplate restTemplate = new RestTemplate();
    String url = String.format(OPEN_WEATHER_URL, city, country, APPID);

    log.info("Obtaining weather info from Open Weather Name Service: {}", OPEN_WEATHER_URL);
    OpenWeatherResponse openWeatherResponse = restTemplate.getForObject(url, OpenWeatherResponse.class);

    log.info("Weather info for {},{}: {}", country, city, openWeatherResponse);
    return openWeatherResponse;
  }

}
