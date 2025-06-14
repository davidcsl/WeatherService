package com.david.weather_service.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class OpenWeatherResponse {

  private String name;
  private Sys sys;
  private List<Weather> weather;
}
