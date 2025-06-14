package com.david.weather_service.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Weather")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class WeatherEntity {

  @Id
  @Column(name = "ID")
  private String id;

  @Column(name = "COUNTRY")
  private String country;

  @Column(name = "CITY")
  private String city;

  @Column(name = "DESCRIPTION")
  private String description;

  @Column(name = "TIMESTAMP")
  private long timestamp;
}
