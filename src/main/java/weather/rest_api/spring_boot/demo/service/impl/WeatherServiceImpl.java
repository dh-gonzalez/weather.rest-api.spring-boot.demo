package weather.rest_api.spring_boot.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.databind.JsonNode;
import reactor.core.publisher.Mono;
import weather.rest_api.spring_boot.demo.common.dto.CityCurrentWeatherDTO;
import weather.rest_api.spring_boot.demo.common.dto.CityForecastWeatherDTO;
import weather.rest_api.spring_boot.demo.common.utils.MathUtils;
import weather.rest_api.spring_boot.demo.common.utils.WeatherUtils;
import weather.rest_api.spring_boot.demo.exception.InconsistentRawDataException;
import weather.rest_api.spring_boot.demo.service.WeatherService;

/**
 * Weather service implementation
 */
@Component
public class WeatherServiceImpl implements WeatherService {

        /** Logger for this class */
        private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

        @Value("${spring.weatherbit.currentUrl:/v2.0/current}")
        private String _weatherbitCurrentUrl;

        @Value("${spring.weatherbit.forecastUrl:/v2.0/forecast/daily}")
        private String _weatherbitForecastUrl;

        @Value("${spring.weatherbit.apiKey}")
        private String _weatherbitApiKey;

        @Value("${spring.weatherbit.lang:en}")
        private String _weatherbitLang;

        @Value("${spring.application.threshold.temperature:1.0}")
        private double _temperatureThreshold;

        @Value("${spring.application.threshold.pressureLow:0.5}")
        private double _pressureLowThreshold;

        @Value("${spring.application.threshold.pressureHigh:2.0}")
        private double _pressureHighThreshold;

        /** REST Client to call weatherbit API */
        private final WebClient _weatherbitWebClient;

        /**
         * Constructor
         * 
         * @param weatherbitWebClient
         */
        public WeatherServiceImpl(WebClient weatherbitWebClient) {
                _weatherbitWebClient = weatherbitWebClient;
        }

        @Override
        public Mono<JsonNode> getRawCurrentWeatherForCity(String city) {

                LOGGER.debug("Requesting weatherbit API for current weather for city {} ", city);

                // GET request on weatherbit API for current weather for city
                return _weatherbitWebClient.get()
                                //
                                .uri(uriBuilder -> uriBuilder
                                                //
                                                .path(_weatherbitCurrentUrl)
                                                //
                                                .queryParam("key", _weatherbitApiKey)
                                                //
                                                .queryParam("lang", _weatherbitLang)
                                                //
                                                .queryParam("units", "M")
                                                //
                                                .queryParam("city", city)
                                                //
                                                .build())
                                //
                                .retrieve()
                                //
                                .bodyToMono(JsonNode.class)
                                //
                                .switchIfEmpty(Mono.error(
                                                new InconsistentRawDataException("No data")));
        }

        @Override
        public Mono<JsonNode> getRawForecastWeatherForCity(String city) {

                LOGGER.debug("Requesting weatherbit API for forecast weather for city {} ", city);

                // GET request on weatherbit API for forecast weather for city
                return _weatherbitWebClient.get()
                                //
                                .uri(uriBuilder -> uriBuilder
                                                //
                                                .path(_weatherbitForecastUrl)
                                                //
                                                .queryParam("key", _weatherbitApiKey)
                                                //
                                                .queryParam("lang", _weatherbitLang)
                                                //
                                                .queryParam("units", "M")
                                                //
                                                .queryParam("city", city)
                                                //
                                                .build())
                                //
                                .retrieve()
                                //
                                .bodyToMono(JsonNode.class)
                                //
                                .switchIfEmpty(Mono.error(
                                                new InconsistentRawDataException("No data")));
        }

        @Override
        public CityCurrentWeatherDTO computeCurrentWeatherForCity(String today, String city,
                        JsonNode rawData) {

                LOGGER.debug("Received current weather for today {} and city {} : {}", today, city,
                                rawData.toPrettyString());

                // Extract raw weather data
                JsonNode dataArray = rawData.path("data");
                if (dataArray.isMissingNode() || !dataArray.isArray() || dataArray.size() != 1) {
                        // Raw data inconsistent if there is no node 'data' or else node 'data' is
                        // not an array
                        // or else node 'data' size is different from 1
                        throw new InconsistentRawDataException(
                                        "Raw data does not have a 'data' array with exactly 1 element");
                }
                JsonNode data0 = dataArray.get(0);
                String description = data0.path("weather").path("description").asText();
                double temp = data0.path("temp").asDouble();
                // wind speed as to be converted from m/s to Km/h
                double windSpeed = data0.path("wind_spd").asDouble() * 3.6;
                int rh = data0.path("rh").asInt();

                // Data mapping to DTO
                CityCurrentWeatherDTO cityCurrentWeatherDTO = new CityCurrentWeatherDTO(city, today,
                                description, temp, windSpeed, rh);

                LOGGER.debug(cityCurrentWeatherDTO.toString());
                return cityCurrentWeatherDTO;
        }

        @Override
        public CityForecastWeatherDTO computeForecastWeatherForCity(String today, String city,
                        JsonNode rawData) {

                LOGGER.debug("Received forecast weather for today {} and city {} : {}", today, city,
                                rawData.toPrettyString());

                // Iterate over data elements and extract raw weather data
                List<Double> temperatures = new ArrayList<>();
                List<Double> barometricPressures = new ArrayList<>();
                List<Double> windSpeeds = new ArrayList<>();

                JsonNode dataArray = rawData.path("data");
                if (dataArray.isMissingNode() || !dataArray.isArray() || dataArray.size() < 2) {
                        // Raw data inconsistent if there is no node 'data' or else node 'data' is
                        // not an array
                        // or else node 'data' size is strictly less than 2
                        throw new InconsistentRawDataException(
                                        "Raw data does not have a 'data' array with at least 2 elements");
                }
                StreamSupport.stream(dataArray.spliterator(), false).forEach(dataElement -> {
                        temperatures.add(dataElement.path("temp").asDouble());
                        barometricPressures.add(dataElement.path("pres").asDouble());
                        // wind speed as to be converted from m/s to Km/h
                        windSpeeds.add(dataElement.path("wind_spd").asDouble() * 3.6);
                });

                LOGGER.debug("Temperatures °C         : {}", temperatures);
                LOGGER.debug("Barometric pressures mb : {}", barometricPressures);
                LOGGER.debug("Wind speeds Km/h        : {}", windSpeeds);

                // Data mapping to DTO
                CityForecastWeatherDTO cityForecastWeatherDTO = new CityForecastWeatherDTO();
                cityForecastWeatherDTO.setCity(city);
                cityForecastWeatherDTO.setDate(today);
                computeForecastWeather(cityForecastWeatherDTO, temperatures, barometricPressures,
                                windSpeeds);

                LOGGER.debug(cityForecastWeatherDTO.toString());
                return cityForecastWeatherDTO;
        }

        /**
         * Compute forecast weather data based on temperatures, barometric pressures and wind speeds
         * 
         * @param cityForecastWeatherDTO forecast weather data to compute
         * @param temperatures list of temperatures °C
         * @param barometricPressures list of barometric pressures mb
         * @param windSpeeds list of wind speeds Km/h
         */
        private void computeForecastWeather(CityForecastWeatherDTO cityForecastWeatherDTO,
                        List<Double> temperatures, List<Double> barometricPressures,
                        List<Double> windSpeeds) {

                // Compute average delta for temperatures and barometric pressures
                double averageTemperaturesDelta = MathUtils.computeAverageDelta(temperatures);
                double averagePressuresDelta = MathUtils.computeAverageDelta(barometricPressures);

                // Compute general tendency based on temperatures and pressures
                cityForecastWeatherDTO.setGeneralTendency(WeatherUtils.computeGeneralTendency(
                                averageTemperaturesDelta, averagePressuresDelta,
                                _temperatureThreshold, _pressureLowThreshold));
                // Compute temperatures tendency
                cityForecastWeatherDTO.setTemperaturesTendency(
                                WeatherUtils.computeTemperaturesTendency(averageTemperaturesDelta,
                                                _temperatureThreshold));
                // Compute pressures tendency
                cityForecastWeatherDTO.setBarometricPressuresTendency(
                                WeatherUtils.computePressuresTendency(averagePressuresDelta,
                                                _pressureLowThreshold, _pressureHighThreshold));
                // Compute average wind speeds according to Beaufort wind scale
                cityForecastWeatherDTO.setAverageWindSpeedsInBeaufortScale(
                                WeatherUtils.computeAverageWindSpeedsInBeaufortScale(windSpeeds));
        }
}
