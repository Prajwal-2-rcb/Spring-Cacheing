package com.codesnippet.weather_service.controller;

import com.codesnippet.weather_service.entity.WeatherLog;
import com.codesnippet.weather_service.service.WeatherLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/logs")
public class WeatherLogController {


    private final WeatherLogService weatherLogService;

    public WeatherLogController(WeatherLogService weatherLogService) {
        this.weatherLogService = weatherLogService;
    }

    @GetMapping("/{id}")
    public WeatherLog getWeatherLog(@PathVariable Long id) {
        return weatherLogService.getLogById(id);
    }

    @PostMapping
    public WeatherLog addWeatherLog(@RequestBody WeatherLog weatherLog, Principal principal) {
        weatherLog.setPerformedBy(principal.getName());
        weatherLog.setTimeStamp(LocalDateTime.now());
        return weatherLogService.createLog(weatherLog);
    }
}
