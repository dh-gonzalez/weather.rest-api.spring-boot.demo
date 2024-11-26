package weather.rest_api.spring_boot.demo.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.IOException;
import java.util.Arrays;
import java.util.function.Function;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import weather.rest_api.spring_boot.demo.common.dto.CityCurrentWeatherDTO;
import weather.rest_api.spring_boot.demo.common.dto.CityForecastWeatherDTO;
import weather.rest_api.spring_boot.demo.common.dto.ForecastBarometricPressuresTendencyEnum;
import weather.rest_api.spring_boot.demo.common.dto.ForecastGeneralTendencyEnum;
import weather.rest_api.spring_boot.demo.common.dto.ForecastTemperaturesTendencyEnum;
import weather.rest_api.spring_boot.demo.common.utils.WeatherUtils;
import weather.rest_api.spring_boot.demo.data.WeatherData;
import weather.rest_api.spring_boot.demo.exception.InconsistentRawDataException;
import weather.rest_api.spring_boot.demo.service.impl.WeatherServiceImpl;

/**
 * Unit test class of service WeatherService
 */
@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {

        // The services to be mocked
        @Mock
        private WebClient webClientMock;
        @Mock
        private WebClient.RequestHeadersUriSpec<?> requestHeadersUriSpecMock;
        @Mock
        private WebClient.ResponseSpec responseSpecMock;

        // The service to be tested
        @InjectMocks
        private WeatherServiceImpl weatherService;

        /**
         * Test method @WeatherService.getRawCurrentWeatherForCity for nominal cases
         * 
         * @throws IOException
         */
        @Test
        public void test_getRawCurrentWeatherForCity_Nominal() throws IOException {

                // Test data
                JsonNode rawCurrentWeatherData = WeatherData.rawCurrentWeatherData();

                // Mocks behaviour
                when(webClientMock.get()).thenAnswer(invocation -> requestHeadersUriSpecMock);
                when(requestHeadersUriSpecMock.uri(any(Function.class)))
                                .thenAnswer(invocation -> requestHeadersUriSpecMock);
                when(requestHeadersUriSpecMock.retrieve()).thenReturn(responseSpecMock);
                when(responseSpecMock.bodyToMono(JsonNode.class))
                                .thenReturn(Mono.just(rawCurrentWeatherData));

                // Call service method to test
                Mono<JsonNode> response = weatherService.getRawCurrentWeatherForCity("city");
                // Retrieve asynchronous result and check expected result
                StepVerifier.create(response).expectNext(rawCurrentWeatherData).verifyComplete();

                // Check mocks have been correctly called
                verify(webClientMock).get();
                verify(requestHeadersUriSpecMock).uri(any(Function.class));
                verify(requestHeadersUriSpecMock).retrieve();
                verify(responseSpecMock).bodyToMono(JsonNode.class);
        }

        /**
         * Test method @WeatherService.getRawForecastWeatherForCity for nominal cases
         * 
         * @throws IOException
         */
        @Test
        public void test_getRawForecastWeatherForCity_Nominal() throws IOException {

                // Test data
                JsonNode rawForecastWeatherData = WeatherData.rawForecastWeatherData();

                // Mocks behaviour
                when(webClientMock.get()).thenAnswer(invocation -> requestHeadersUriSpecMock);
                when(requestHeadersUriSpecMock.uri(any(Function.class)))
                                .thenAnswer(invocation -> requestHeadersUriSpecMock);
                when(requestHeadersUriSpecMock.retrieve()).thenReturn(responseSpecMock);
                when(responseSpecMock.bodyToMono(JsonNode.class))
                                .thenReturn(Mono.just(rawForecastWeatherData));

                // Call service method to test
                Mono<JsonNode> response = weatherService.getRawForecastWeatherForCity("city");
                // Retrieve asynchronous result and check expected result
                StepVerifier.create(response).expectNext(rawForecastWeatherData).verifyComplete();

                // Check mocks have been correctly called
                verify(webClientMock).get();
                verify(requestHeadersUriSpecMock).uri(any(Function.class));
                verify(requestHeadersUriSpecMock).retrieve();
                verify(responseSpecMock).bodyToMono(JsonNode.class);
        }

        /**
         * Test method @WeatherService.computeCurrentWeatherForCity for error cases
         * 
         * @throws JsonMappingException
         * @throws JsonProcessingException
         */
        @Test
        public void test_computeCurrentWeatherForCity_WhenRawDataIsInconsistent()
                        throws JsonMappingException, JsonProcessingException {

                // Inconsistent raw data
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode inconsistentRawCurrentWeatherData = objectMapper.readTree("{}");

                // ///////////////////////////////////////////////
                // Call service method to test - no node 'data' //
                // ///////////////////////////////////////////////
                try {
                        weatherService.computeCurrentWeatherForCity("2024-11-22", "toulouse",
                                        inconsistentRawCurrentWeatherData);
                        Assertions.fail("This call should have thrown an "
                                        + InconsistentRawDataException.class.getName());
                } catch (InconsistentRawDataException ex) {
                        // Check expected result
                        Assertions.assertEquals(
                                        "Raw data does not have a 'data' array with exactly 1 element",
                                        ex.getMessage());
                }

                // ////////////////////////////////////////////////////////////
                // Call service method to test - node 'data' is not an array //
                // ////////////////////////////////////////////////////////////
                inconsistentRawCurrentWeatherData = objectMapper.readTree("{\"data\":\"toto\"}");
                try {
                        weatherService.computeCurrentWeatherForCity("2024-11-22", "toulouse",
                                        inconsistentRawCurrentWeatherData);
                        Assertions.fail("This call should have thrown an "
                                        + InconsistentRawDataException.class.getName());
                } catch (InconsistentRawDataException ex) {
                        // Check expected result
                        Assertions.assertEquals(
                                        "Raw data does not have a 'data' array with exactly 1 element",
                                        ex.getMessage());
                }

                // ////////////////////////////////////////////////////////////////////////////
                // Call service method to test - node 'data' does not have exactly 1 element //
                // ////////////////////////////////////////////////////////////////////////////
                inconsistentRawCurrentWeatherData = objectMapper.readTree("{\"data\":[]}");
                try {
                        weatherService.computeCurrentWeatherForCity("2024-11-22", "toulouse",
                                        inconsistentRawCurrentWeatherData);
                        Assertions.fail("This call should have thrown an "
                                        + InconsistentRawDataException.class.getName());
                } catch (InconsistentRawDataException ex) {
                        // Check expected result
                        Assertions.assertEquals(
                                        "Raw data does not have a 'data' array with exactly 1 element",
                                        ex.getMessage());
                }
        }

        /**
         * Test method @WeatherService.computeCurrentWeatherForCity for nominal cases
         * 
         * @throws IOException
         */
        @Test
        public void test_computeCurrentWeatherForCity_Nominal() throws IOException {

                // Test data
                JsonNode rawCurrentWeatherData = WeatherData.rawCurrentWeatherData();

                // Call service method to test
                CityCurrentWeatherDTO cityCurrentWeatherDTO =
                                weatherService.computeCurrentWeatherForCity("2024-11-22",
                                                "toulouse", rawCurrentWeatherData);

                // Check expected result
                Assertions.assertEquals("toulouse", cityCurrentWeatherDTO.getCity());
                Assertions.assertEquals("2024-11-22", cityCurrentWeatherDTO.getDate());
                Assertions.assertEquals("Clear sky", cityCurrentWeatherDTO.getDescription());
                Assertions.assertEquals(8.0, cityCurrentWeatherDTO.getTemperatureInCelsius());
                // 5.7 m/s * 3.6 = 20.52 Km/h
                Assertions.assertEquals(20.52, cityCurrentWeatherDTO.getWindInKmPerHour());
                Assertions.assertEquals(70,
                                cityCurrentWeatherDTO.getRelativeHumidityInPercentage());
        }

        /**
         * Test method @WeatherService.computeForecastWeatherForCity for error cases
         * 
         * @throws JsonMappingException
         * @throws JsonProcessingException
         */
        @Test
        public void test_computeForecastWeatherForCity_WhenRawDataIsInconsistent()
                        throws JsonMappingException, JsonProcessingException {

                // Inconsistent raw data
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode inconsistentRawCurrentWeatherData = objectMapper.readTree("{}");

                // ///////////////////////////////////////////////
                // Call service method to test - no node 'data' //
                // ///////////////////////////////////////////////
                try {
                        weatherService.computeForecastWeatherForCity("2024-11-22", "toulouse",
                                        inconsistentRawCurrentWeatherData);
                        Assertions.fail("This call should have thrown an "
                                        + InconsistentRawDataException.class.getName());
                } catch (InconsistentRawDataException ex) {
                        // Check expected result
                        Assertions.assertEquals(
                                        "Raw data does not have a 'data' array with at least 2 elements",
                                        ex.getMessage());
                }

                // ////////////////////////////////////////////////////////////
                // Call service method to test - node 'data' is not an array //
                // ////////////////////////////////////////////////////////////
                inconsistentRawCurrentWeatherData = objectMapper.readTree("{\"data\":\"toto\"}");
                try {
                        weatherService.computeForecastWeatherForCity("2024-11-22", "toulouse",
                                        inconsistentRawCurrentWeatherData);
                        Assertions.fail("This call should have thrown an "
                                        + InconsistentRawDataException.class.getName());
                } catch (InconsistentRawDataException ex) {
                        // Check expected result
                        Assertions.assertEquals(
                                        "Raw data does not have a 'data' array with at least 2 elements",
                                        ex.getMessage());
                }

                // ////////////////////////////////////////////////////////////////////////////
                // Call service method to test - node 'data' does not have exactly 1 element //
                // ////////////////////////////////////////////////////////////////////////////
                inconsistentRawCurrentWeatherData = objectMapper.readTree("{\"data\":[]}");
                try {
                        weatherService.computeForecastWeatherForCity("2024-11-22", "toulouse",
                                        inconsistentRawCurrentWeatherData);
                        Assertions.fail("This call should have thrown an "
                                        + InconsistentRawDataException.class.getName());
                } catch (InconsistentRawDataException ex) {
                        // Check expected result
                        Assertions.assertEquals(
                                        "Raw data does not have a 'data' array with at least 2 elements",
                                        ex.getMessage());
                }
        }

        /**
         * Test method @WeatherService.computeForecastWeatherForCity for nominal cases<br>
         * Test calculation for results general tendency, temperatures tendency and pressures
         * tendency<br>
         * 
         * Average wind speeds according to Beaufort scale is not fully tested as it would require a
         * significant amount of data<br>
         * See @WeatherServiceTest.test_computeForecastWeatherForCity_AvgWindSpeedsBeaufortScale_Nominal
         * 
         * @throws IOException
         */
        @Test
        public void test_computeForecastWeatherForCity_Tendencies_Nominal() throws IOException {

                // Mocks behaviour
                ReflectionTestUtils.setField(weatherService, "_temperatureThreshold", 1.0);
                ReflectionTestUtils.setField(weatherService, "_pressureLowThreshold", 0.5);
                ReflectionTestUtils.setField(weatherService, "_pressureHighThreshold", 2.0);

                // ///////////////////////////////////
                // Test data SIGNIFICANT_DECREASING //
                // ///////////////////////////////////
                JsonNode rawForecastWeatherData =
                                WeatherData.rawForecastWeatherData_SIGNIFICANT_DECREASE();

                // Call service method to test
                CityForecastWeatherDTO cityCurrentWeatherDTO =
                                weatherService.computeForecastWeatherForCity("2024-11-22",
                                                "toulouse", rawForecastWeatherData);

                // Check expected result
                Assertions.assertEquals("toulouse", cityCurrentWeatherDTO.getCity());
                Assertions.assertEquals("2024-11-22", cityCurrentWeatherDTO.getDate());
                Assertions.assertEquals(ForecastGeneralTendencyEnum.DETERIORATION,
                                cityCurrentWeatherDTO.getGeneralTendency());
                Assertions.assertEquals(ForecastTemperaturesTendencyEnum.DECREASING,
                                cityCurrentWeatherDTO.getTemperaturesTendency());
                Assertions.assertEquals(
                                ForecastBarometricPressuresTendencyEnum.SIGNIFICANT_DECREASE,
                                cityCurrentWeatherDTO.getBarometricPressuresTendency());
                Assertions.assertEquals(0,
                                cityCurrentWeatherDTO.getAverageWindSpeedsInBeaufortScale());


                // ///////////////////////
                // Test data DECREASING //
                // ///////////////////////
                rawForecastWeatherData = WeatherData.rawForecastWeatherData_DECREASING();

                // Call service method to test
                cityCurrentWeatherDTO = weatherService.computeForecastWeatherForCity("2024-11-22",
                                "toulouse", rawForecastWeatherData);

                // Check expected result
                Assertions.assertEquals("toulouse", cityCurrentWeatherDTO.getCity());
                Assertions.assertEquals("2024-11-22", cityCurrentWeatherDTO.getDate());
                Assertions.assertEquals(ForecastGeneralTendencyEnum.DETERIORATION,
                                cityCurrentWeatherDTO.getGeneralTendency());
                Assertions.assertEquals(ForecastTemperaturesTendencyEnum.DECREASING,
                                cityCurrentWeatherDTO.getTemperaturesTendency());
                Assertions.assertEquals(ForecastBarometricPressuresTendencyEnum.DECREASING,
                                cityCurrentWeatherDTO.getBarometricPressuresTendency());
                Assertions.assertEquals(1,
                                cityCurrentWeatherDTO.getAverageWindSpeedsInBeaufortScale());

                // ///////////////////
                // Test data STABLE //
                // ///////////////////
                rawForecastWeatherData = WeatherData.rawForecastWeatherData_STABLE();

                // Call service method to test
                cityCurrentWeatherDTO = weatherService.computeForecastWeatherForCity("2024-11-22",
                                "toulouse", rawForecastWeatherData);

                // Check expected result
                Assertions.assertEquals("toulouse", cityCurrentWeatherDTO.getCity());
                Assertions.assertEquals("2024-11-22", cityCurrentWeatherDTO.getDate());
                Assertions.assertEquals(ForecastGeneralTendencyEnum.STABLE,
                                cityCurrentWeatherDTO.getGeneralTendency());
                Assertions.assertEquals(ForecastTemperaturesTendencyEnum.STABLE,
                                cityCurrentWeatherDTO.getTemperaturesTendency());
                Assertions.assertEquals(ForecastBarometricPressuresTendencyEnum.STABLE,
                                cityCurrentWeatherDTO.getBarometricPressuresTendency());
                Assertions.assertEquals(2,
                                cityCurrentWeatherDTO.getAverageWindSpeedsInBeaufortScale());

                // ///////////////////////
                // Test data INCREASING //
                // ///////////////////////
                rawForecastWeatherData = WeatherData.rawForecastWeatherData_INCREASING();

                // Call service method to test
                cityCurrentWeatherDTO = weatherService.computeForecastWeatherForCity("2024-11-22",
                                "toulouse", rawForecastWeatherData);

                // Check expected result
                Assertions.assertEquals("toulouse", cityCurrentWeatherDTO.getCity());
                Assertions.assertEquals("2024-11-22", cityCurrentWeatherDTO.getDate());
                Assertions.assertEquals(ForecastGeneralTendencyEnum.IMPROVEMENT,
                                cityCurrentWeatherDTO.getGeneralTendency());
                Assertions.assertEquals(ForecastTemperaturesTendencyEnum.INCREASING,
                                cityCurrentWeatherDTO.getTemperaturesTendency());
                Assertions.assertEquals(ForecastBarometricPressuresTendencyEnum.INCREASING,
                                cityCurrentWeatherDTO.getBarometricPressuresTendency());
                Assertions.assertEquals(3,
                                cityCurrentWeatherDTO.getAverageWindSpeedsInBeaufortScale());

                // ///////////////////////////////////
                // Test data SIGNIFICANT_INCREASING //
                // ///////////////////////////////////
                rawForecastWeatherData =
                                WeatherData.rawForecastWeatherData_SIGNIFICANT_INCREASING();

                // Call service method to test
                cityCurrentWeatherDTO = weatherService.computeForecastWeatherForCity("2024-11-22",
                                "toulouse", rawForecastWeatherData);

                // Check expected result
                Assertions.assertEquals("toulouse", cityCurrentWeatherDTO.getCity());
                Assertions.assertEquals("2024-11-22", cityCurrentWeatherDTO.getDate());
                Assertions.assertEquals(ForecastGeneralTendencyEnum.IMPROVEMENT,
                                cityCurrentWeatherDTO.getGeneralTendency());
                Assertions.assertEquals(ForecastTemperaturesTendencyEnum.INCREASING,
                                cityCurrentWeatherDTO.getTemperaturesTendency());
                Assertions.assertEquals(
                                ForecastBarometricPressuresTendencyEnum.SIGNIFICANT_INCREASE,
                                cityCurrentWeatherDTO.getBarometricPressuresTendency());
                Assertions.assertEquals(4,
                                cityCurrentWeatherDTO.getAverageWindSpeedsInBeaufortScale());
        }

        /**
         * Test method @WeatherUtils.computeAverageWindSpeedsInBeaufortScale for nominal cases
         */
        @Test
        public void test_computeForecastWeatherForCity_AvgWindSpeedsBeaufortScale_Nominal() {

                Assertions.assertEquals(0, WeatherUtils
                                .computeAverageWindSpeedsInBeaufortScale(Arrays.asList(0.0)));
                Assertions.assertEquals(1, WeatherUtils
                                .computeAverageWindSpeedsInBeaufortScale(Arrays.asList(4.0)));
                Assertions.assertEquals(2, WeatherUtils
                                .computeAverageWindSpeedsInBeaufortScale(Arrays.asList(10.0)));
                Assertions.assertEquals(3, WeatherUtils
                                .computeAverageWindSpeedsInBeaufortScale(Arrays.asList(18.0)));
                Assertions.assertEquals(4, WeatherUtils
                                .computeAverageWindSpeedsInBeaufortScale(Arrays.asList(27.0)));
                Assertions.assertEquals(5, WeatherUtils
                                .computeAverageWindSpeedsInBeaufortScale(Arrays.asList(37.0)));
                Assertions.assertEquals(6, WeatherUtils
                                .computeAverageWindSpeedsInBeaufortScale(Arrays.asList(48.0)));
                Assertions.assertEquals(7, WeatherUtils
                                .computeAverageWindSpeedsInBeaufortScale(Arrays.asList(60.0)));
                Assertions.assertEquals(8, WeatherUtils
                                .computeAverageWindSpeedsInBeaufortScale(Arrays.asList(73.0)));
                Assertions.assertEquals(9, WeatherUtils
                                .computeAverageWindSpeedsInBeaufortScale(Arrays.asList(87.0)));
                Assertions.assertEquals(10, WeatherUtils
                                .computeAverageWindSpeedsInBeaufortScale(Arrays.asList(101.0)));
                Assertions.assertEquals(11, WeatherUtils
                                .computeAverageWindSpeedsInBeaufortScale(Arrays.asList(116.0)));
                Assertions.assertEquals(12, WeatherUtils
                                .computeAverageWindSpeedsInBeaufortScale(Arrays.asList(118.0)));
        }
}
