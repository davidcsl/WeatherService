package com.david.weather_service.service;

import com.david.weather_service.model.dto.OpenWeatherResponse;
import com.david.weather_service.model.dto.Weather;
import com.david.weather_service.model.entity.WeatherEntity;
import com.david.weather_service.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class WeatherInfoAcquirer {

  @Value("${open-weather.url}")
  private String OPEN_WEATHER_URL;

  @Value("${open-weather.appid}")
  private String APPID;

  private final WeatherRepository weatherRepository;

  public String queryWeather(String country, String city) throws Exception {

    if (Objects.isNull(city) || Objects.isNull(country)) {
      throw new Exception("Country / City not detected.");
    }

    WeatherEntity weatherEntity;
    weatherEntity = weatherRepository.findByCountryAndCity(country, city);

    if (Objects.isNull(weatherEntity)) {
      weatherEntity = queryFromOpenWeather(city, country);
      weatherRepository.saveAndFlush(weatherEntity);
    }

    String desc = Optional.ofNullable(weatherEntity)
            .map(WeatherEntity::getDescription)
            .orElse(null);

    if (Objects.isNull(desc)) {
      throw new Exception("Invalid weather info from Open Weather.");
    }
    return desc;
  }

  private WeatherEntity queryFromOpenWeather(String city,
                                             String country) {

    RestTemplate restTemplate = new RestTemplate();
    String url = String.format(OPEN_WEATHER_URL, city, country, APPID);

    log.info("Obtaining weather info from Open Weather Name Service: {}", OPEN_WEATHER_URL);
    OpenWeatherResponse openWeatherResponse = restTemplate.getForObject(url, OpenWeatherResponse.class);

    String desc = Optional.ofNullable(openWeatherResponse)
            .map(OpenWeatherResponse::getWeather)
            .map(weathers -> weathers.get(0))
            .map(Weather::getDescription)
            .orElse(null);

    WeatherEntity weatherEntity = new WeatherEntity();
    weatherEntity.setId(String.valueOf(UUID.randomUUID()));
    weatherEntity.setCountry(country);
    weatherEntity.setCity(city);
    weatherEntity.setDescription(desc);
    weatherEntity.setTimestamp(Instant.now().toEpochMilli());

    return weatherEntity;
  }

}
