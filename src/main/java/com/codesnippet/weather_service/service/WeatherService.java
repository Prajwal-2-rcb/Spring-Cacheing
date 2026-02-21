package com.codesnippet.weather_service.service;

import com.codesnippet.weather_service.entity.Weather;
import com.codesnippet.weather_service.repository.WeatherRepository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Service
public class WeatherService {

    private final WeatherRepository weatherRepository;

    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    @Cacheable(value = "weather" , key = "#city")
    public String getWeatherByCity(String city) {
        System.out.println("Fetching data from DB for city: " + city);
        Optional<Weather> weather = weatherRepository.findByCity(city);
        return weather.map(Weather::getForecast).orElse("Weather data not available");
    }

    @CachePut(value="weather",key = "#city")
    public String updateWeather(String city, String weatherUpdate) {
        Optional<Weather> optionalWeather = weatherRepository.findByCity(city);

        if (optionalWeather.isPresent()) {
            Weather weather = optionalWeather.get();
            weather.setForecast(weatherUpdate);
            weatherRepository.save(weather);
        }
        return weatherUpdate;
    }

    @Transactional
    @CacheEvict(value ="weather",key = "#city")
    public void deleteWeather(String city) {
        System.out.println("Removing weather data for city: " + city);
        weatherRepository.deleteByCity(city);
    }
}
