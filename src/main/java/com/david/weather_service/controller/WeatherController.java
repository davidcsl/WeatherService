package com.david.weather_service.controller;

import com.david.weather_service.model.dto.WeatherResponse;
import com.david.weather_service.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class WeatherController {

  private final WeatherService weatherService;

  @GetMapping("openweather/{city}/{country}")
  public ResponseEntity getWeather(@PathVariable String city,
                                   @PathVariable String country,
                                   @RequestParam String apiKey) {

    log.info("Weather from {},{} requested", country, city);

    try {
      WeatherResponse weatherResponse = weatherService.processQuery(city, country);
      return ResponseEntity.ok(weatherResponse);
    } catch (Exception e) {
      return ResponseEntity.status(400).body(e.getMessage());
    }
  }
}
