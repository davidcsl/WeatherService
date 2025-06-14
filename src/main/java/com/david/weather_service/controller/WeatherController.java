package com.david.weather_service.controller;

import com.david.weather_service.model.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@Slf4j
public class WeatherController {

  @GetMapping("openweather/{city}/{country}")
  public ResponseEntity getWeather(@PathVariable String city,
                                   @PathVariable String country,
                                   @RequestParam String apiKey) {

    log.info("Weather from {},{} is ok", country, city);
    log.info("API Key from user is {}", apiKey);

    WeatherResponse weatherResponse = new WeatherResponse();

    return ResponseEntity.ok(weatherResponse);
  }
}
