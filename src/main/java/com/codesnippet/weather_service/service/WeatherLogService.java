package com.codesnippet.weather_service.service;

import com.codesnippet.weather_service.entity.WeatherLog;
import com.codesnippet.weather_service.repository.WeatherLogRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

@Service
public class WeatherLogService {

    private final WeatherLogRepository weatherLogRepository;

    public WeatherLogService(WeatherLogRepository weatherLogRepository) {
        this.weatherLogRepository = weatherLogRepository;
    }


    @PostAuthorize("returnObject.performedBy==authentication.name")
    public WeatherLog getLogById(Long id) {
        return weatherLogRepository.findById(id).
                orElseThrow(()->new RuntimeException("WeatherLog not found"));
    }

    public WeatherLog createLog(WeatherLog weatherLog) {
        return weatherLogRepository.save(weatherLog);
    }
}
