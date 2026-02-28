package com.codesnippet.weather_service.repository;

import com.codesnippet.weather_service.entity.WeatherLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherLogRepository extends JpaRepository<WeatherLog, Long> {
}
