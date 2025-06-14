package com.david.weather_service.service;

import com.david.weather_service.model.dto.OpenWeatherResponse;
import com.david.weather_service.model.dto.Weather;
import com.david.weather_service.model.dto.WeatherResponse;
import com.david.weather_service.model.entity.WeatherEntity;
import com.david.weather_service.repository.WeatherRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class WeatherService {

  private final WeatherInfoAcquirer weatherInfoAcquirer;
  private final WeatherRepository weatherRepository;

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

    if (Objects.isNull(desc)) {
      throw new Exception("Invalid weather info from Open Weather.");
    }

    WeatherEntity weatherEntity = new WeatherEntity();
    weatherEntity.setId(String.valueOf(UUID.randomUUID()));
    weatherEntity.setCountry(country);
    weatherEntity.setCity(city);
    weatherEntity.setDescription(desc);
    weatherEntity.setTimestamp(Instant.now().toEpochMilli());

    WeatherEntity entityResponse = weatherRepository.saveAndFlush(weatherEntity);

    WeatherResponse weatherResponse = new WeatherResponse();
    weatherResponse.setCity(city);
    weatherResponse.setCountry(country);
    weatherResponse.setDescription(desc);

    return weatherResponse;

  }
}
