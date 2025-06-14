package com.david.weather_service.service;

import com.david.weather_service.model.OpenWeatherResponse;
import com.david.weather_service.model.Weather;
import com.david.weather_service.model.WeatherResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class WeatherService {

  private final WeatherInfoAcquirer weatherInfoAcquirer;

  public WeatherResponse processQuery(String city,
                                      String country,
                                      String apikey) throws Exception {

    OpenWeatherResponse openWeatherResponse = weatherInfoAcquirer.queryWeather(country, city);

    String desc = Optional
            .ofNullable(openWeatherResponse)
            .map(OpenWeatherResponse::getWeather)
            .map(weathers -> weathers.get(0))
            .map(Weather::getDescription)
            .orElse(null);

    WeatherResponse weatherResponse = new WeatherResponse();
    weatherResponse.setCity(city);
    weatherResponse.setCountry(country);
    weatherResponse.setDescription(desc);

    return weatherResponse;

  }
}
