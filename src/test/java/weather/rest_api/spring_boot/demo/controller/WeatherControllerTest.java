package weather.rest_api.spring_boot.demo.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.fasterxml.jackson.databind.JsonNode;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import weather.rest_api.spring_boot.demo.common.dto.CityCurrentWeatherDTO;
import weather.rest_api.spring_boot.demo.common.dto.CityForecastWeatherDTO;
import weather.rest_api.spring_boot.demo.common.dto.ForecastBarometricPressuresTendencyEnum;
import weather.rest_api.spring_boot.demo.common.dto.ForecastGeneralTendencyEnum;
import weather.rest_api.spring_boot.demo.common.dto.ForecastTemperaturesTendencyEnum;
import weather.rest_api.spring_boot.demo.data.WeatherData;
import weather.rest_api.spring_boot.demo.service.WeatherService;

/**
 * Unit test class of controller WeatherController
 */
@ExtendWith(MockitoExtension.class)
public class WeatherControllerTest {

        // The services to be mocked
        @Mock
        private WeatherService weatherService;

        // The controller to be tested
        @InjectMocks
        private WeatherController weatherController;

        /**
         * Test method @WeatherController.currentWeatherForCity for nominal cases
         * 
         * @throws IOException
         */
        @Test
        public void test_currentWeatherForCity_Nominal() throws IOException {

                // Test data
                JsonNode rawCurrentWeatherData = WeatherData.rawCurrentWeatherData();
                CityCurrentWeatherDTO cityCurrentWeatherDTO = new CityCurrentWeatherDTO("city",
                                "2024-11-22", "description", 0.0, 0.0, 0);

                // Mocks behaviour
                when(weatherService.getRawCurrentWeatherForCity("city"))
                                .thenReturn(Mono.just(rawCurrentWeatherData));
                when(weatherService.computeCurrentWeatherForCity(anyString(), eq("city"),
                                eq(rawCurrentWeatherData))).thenReturn(cityCurrentWeatherDTO);

                // Call service method to test - cache is empty
                Mono<CityCurrentWeatherDTO> response =
                                weatherController.currentWeatherForCity("city");
                // Retrieve asynchronous result and check expected result
                StepVerifier.create(response).expectNext(cityCurrentWeatherDTO).verifyComplete();

                // Check mocks have been correctly called
                verify(weatherService).getRawCurrentWeatherForCity("city");
                verify(weatherService).computeCurrentWeatherForCity(anyString(), eq("city"),
                                eq(rawCurrentWeatherData));

                // Call service method to test - cache is not empty
                response = weatherController.currentWeatherForCity("city");
                // Retrieve asynchronous result and check expected result
                StepVerifier.create(response).expectNext(cityCurrentWeatherDTO).verifyComplete();

                // Mocks have not been called because cache has provided result
                verify(weatherService).getRawCurrentWeatherForCity("city");
                verify(weatherService).computeCurrentWeatherForCity(anyString(), eq("city"),
                                eq(rawCurrentWeatherData));
        }

        /**
         * Test method @WeatherController.forecastWeatherForCity for nominal cases
         * 
         * @throws IOException
         */
        @Test
        public void test_forecastWeatherForCity_Nominal() throws IOException {

                // Test data
                JsonNode rawForecastWeatherData = WeatherData.rawForecastWeatherData();
                CityForecastWeatherDTO cityForecastWeatherDTO = new CityForecastWeatherDTO();
                cityForecastWeatherDTO.setCity("city");
                cityForecastWeatherDTO.setDate("2024-11-22");
                cityForecastWeatherDTO.setGeneralTendency(ForecastGeneralTendencyEnum.STABLE);
                cityForecastWeatherDTO
                                .setTemperaturesTendency(ForecastTemperaturesTendencyEnum.STABLE);
                cityForecastWeatherDTO.setBarometricPressuresTendency(
                                ForecastBarometricPressuresTendencyEnum.STABLE);
                cityForecastWeatherDTO.setAverageWindSpeedsInBeaufortScale(0);

                // Mocks behaviour
                when(weatherService.getRawForecastWeatherForCity("city"))
                                .thenReturn(Mono.just(rawForecastWeatherData));
                when(weatherService.computeForecastWeatherForCity(anyString(), eq("city"),
                                eq(rawForecastWeatherData))).thenReturn(cityForecastWeatherDTO);

                // Call service method to test - cache is empty
                Mono<CityForecastWeatherDTO> response =
                                weatherController.forecastWeatherForCity("city");
                // Retrieve asynchronous result and check expected result
                StepVerifier.create(response).expectNext(cityForecastWeatherDTO).verifyComplete();

                // Check mocks have been correctly called
                verify(weatherService).getRawForecastWeatherForCity("city");
                verify(weatherService).computeForecastWeatherForCity(anyString(), eq("city"),
                                eq(rawForecastWeatherData));

                // Call service method to test - cache is not empty
                response = weatherController.forecastWeatherForCity("city");
                // Retrieve asynchronous result and check expected result
                StepVerifier.create(response).expectNext(cityForecastWeatherDTO).verifyComplete();

                // Mocks have not been called because cache has provided result
                verify(weatherService).getRawForecastWeatherForCity("city");
                verify(weatherService).computeForecastWeatherForCity(anyString(), eq("city"),
                                eq(rawForecastWeatherData));
        }
}
