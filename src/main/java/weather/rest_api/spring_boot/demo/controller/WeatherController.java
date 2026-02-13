package weather.rest_api.spring_boot.demo.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import reactor.core.publisher.Mono;
import weather.rest_api.spring_boot.demo.common.Constants;
import weather.rest_api.spring_boot.demo.common.dto.CityCurrentWeatherDTO;
import weather.rest_api.spring_boot.demo.common.dto.CityForecastWeatherDTO;
import weather.rest_api.spring_boot.demo.common.dto.ErrorDetailsDTO;
import weather.rest_api.spring_boot.demo.service.WeatherService;

/**
 * Weather controller
 * 
 * Exposes a versioned REST API for weather current and forecast requests
 */
@RestController
@RequestMapping(Constants.api)
public class WeatherController {

        /** Logger for this class */
        private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

        /**
         * Date formatter
         */
        public final DateTimeFormatter _dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        /**
         * Current weather cache
         */
        private final Map<String, CityCurrentWeatherDTO> _currentWeatherCache =
                        new ConcurrentHashMap<>();

        /**
         * Forecast weather cache
         */
        private final Map<String, CityForecastWeatherDTO> _forecastWeatherCache =
                        new ConcurrentHashMap<>();


        /** Weather service */
        private final WeatherService _weatherService;

        /**
         * Constructor
         * 
         * @param weatherService
         */
        public WeatherController(WeatherService weatherService) {
                _weatherService = weatherService;
        }

        @Operation(summary = "Return current weather informations for requested city",
                        description = "Return following current weather informations for requested city :\n"
                                        + "* brief description\n" + "* temperature in °C\n"
                                        + "* wind speed in Km/h\n"
                                        + "* relative humidity percentage")
        @ApiResponses({
                        //
                        @ApiResponse(responseCode = "200",
                                        description = "City has been identified and current weather informations has been returned"),
                        //
                        @ApiResponse(responseCode = "500",
                                        description = "Unexpected error has occurred while processing request",
                                        content = @Content(
                                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                        schema = @Schema(
                                                                        implementation = ErrorDetailsDTO.class)))})
        @GetMapping(value = Constants.v1 + "/weather/current",
                        produces = MediaType.APPLICATION_JSON_VALUE)
        public Mono<CityCurrentWeatherDTO> currentWeatherForCity(
                        @Parameter(description = "City name", example = "Toulouse") @RequestParam(
                                        value = "city") String city) {

                String today = LocalDate.now().format(_dateFormatter);
                String cityLowerCase = city.toLowerCase();
                String keyCache = today + "-" + cityLowerCase;
                LOGGER.info("Current weather has been requested for today {} and city {} ", today,
                                cityLowerCase);

                // Look if current weather is in cache
                return Mono.justOrEmpty(_currentWeatherCache.get(keyCache))
                                // If not then asynchronous request for raw current weather data
                                .switchIfEmpty(Mono.defer(() -> _weatherService
                                                .getRawCurrentWeatherForCity(cityLowerCase)
                                                // On response treat data and map it to DTO for
                                                // caller
                                                .map(rawData -> _weatherService
                                                                .computeCurrentWeatherForCity(today,
                                                                                cityLowerCase,
                                                                                rawData))
                                                // Save data in cache
                                                .doOnNext(data -> _currentWeatherCache.put(keyCache,
                                                                data))));
        }

        @Operation(summary = "Return forecast weather informations for requested city",
                        description = "Return following forecast weather informations for requested city :\n"
                                        + "* General tendency\n" + "* Temperatures tendency\n"
                                        + "* Barometric pressures tendency\n"
                                        + "* Average wind speeds according to Beaufort wind scale\n")
        @ApiResponses({
                        //
                        @ApiResponse(responseCode = "200",
                                        description = "City has been identified and forecast weather informations has been returned"),
                        //
                        @ApiResponse(responseCode = "500",
                                        description = "Unexpected error has occurred while processing request",
                                        content = @Content(
                                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                        schema = @Schema(
                                                                        implementation = ErrorDetailsDTO.class)))})
        @GetMapping(value = Constants.v1 + "/weather/forecast",
                        produces = MediaType.APPLICATION_JSON_VALUE)
        public Mono<CityForecastWeatherDTO> forecastWeatherForCity(
                        @Parameter(description = "City name", example = "Toulouse") @RequestParam(
                                        value = "city") String city) {

                String today = LocalDate.now().format(_dateFormatter);
                String cityLowerCase = city.toLowerCase();
                String keyCache = today + "-" + cityLowerCase;
                LOGGER.info("Forecast weather has been requested for today {} and city {} ", today,
                                cityLowerCase);

                // Look if forecast weather is in cache
                return Mono.justOrEmpty(_forecastWeatherCache.get(keyCache))
                                // If not then asynchronous request for raw forecast weather data
                                .switchIfEmpty(Mono.defer(() -> _weatherService
                                                .getRawForecastWeatherForCity(cityLowerCase)
                                                // On response treat data and map it to DTO for
                                                // caller
                                                .map(rawData -> _weatherService
                                                                .computeForecastWeatherForCity(
                                                                                today,
                                                                                cityLowerCase,
                                                                                rawData))
                                                // Save data in cache
                                                .doOnNext(data -> _forecastWeatherCache
                                                                .put(keyCache, data))));
        }
}
