package weather.rest_api.spring_boot.demo.service;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import reactor.core.publisher.Mono;
import weather.rest_api.spring_boot.demo.common.dto.CityCurrentWeatherDTO;
import weather.rest_api.spring_boot.demo.common.dto.CityForecastWeatherDTO;

/**
 * Weather service
 */
@Service
public interface WeatherService {

        /**
         * Get raw current weather for the city given as parameter
         * 
         * @param today today as String format @DateUtils.today
         * @param city city
         * @return raw current weather within an asynchronous reactive @Mono
         */
        public Mono<JsonNode> getRawCurrentWeatherForCity(String city);

        /**
         * Get raw forecast weather for the city given as parameter
         * 
         * @param today today as String format @DateUtils.today
         * @param city city
         * @return raw forecast weather within an asynchronous reactive @Mono
         */
        public Mono<JsonNode> getRawForecastWeatherForCity(String city);

        /**
         * Compute current weather for the city with raw data given as parameter
         * 
         * @param today today as String format @DateUtils.today
         * @param city city
         * @param rawData raw data
         * @return current weather
         */
        public CityCurrentWeatherDTO computeCurrentWeatherForCity(String today, String city,
                        JsonNode rawData);

        /**
         * Compute forecast weather for the city with raw data given as parameter
         *
         * @param today today as String format @DateUtils.today
         * @param city city
         * @param rawData raw data
         * @return forecast weather
         */
        public CityForecastWeatherDTO computeForecastWeatherForCity(String today, String city,
                        JsonNode rawData);
}
