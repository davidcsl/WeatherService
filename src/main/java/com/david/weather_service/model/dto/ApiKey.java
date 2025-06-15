package com.david.weather_service.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApiKey {

  private String apikey;
  private long timestamp;
  private long lives;

  public ApiKey(String apikey) {
    this.apikey = apikey;
  }

}
