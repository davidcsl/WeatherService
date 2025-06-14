package com.david.weather_service.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WeatherResponse {

  private String country;
  private String city;
  private String description;
}
