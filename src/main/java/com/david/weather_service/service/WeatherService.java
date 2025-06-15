package com.david.weather_service.service;

import com.david.weather_service.model.dto.WeatherResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class WeatherService {

  private final WeatherInfoAcquirer weatherInfoAcquirer;

  public WeatherResponse processQuery(String city,
                                      String country) throws Exception {

    String desc = weatherInfoAcquirer.queryWeather(country, city);

    WeatherResponse weatherResponse = new WeatherResponse();
    weatherResponse.setCity(city);
    weatherResponse.setCountry(country);
    weatherResponse.setDescription(desc);

    return weatherResponse;

  }
}
