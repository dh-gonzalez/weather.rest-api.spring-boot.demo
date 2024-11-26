package weather.rest_api.spring_boot.demo.data;

import java.io.File;
import java.io.IOException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Weather data mock for tests
 */
public class WeatherData {

    /**
     * Raw current weather data
     * 
     * @return Raw current weather data
     * @throws IOException
     */
    public static JsonNode rawCurrentWeatherData() throws IOException {

        File jsonFile = new File("src/test/resources/data/rawCurrentWeatherData.json");
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(jsonFile);
    }

    /**
     * Raw forecast weather data
     * 
     * @return Raw forecast weather data
     * @throws IOException
     */
    public static JsonNode rawForecastWeatherData() throws IOException {

        File jsonFile = new File("src/test/resources/data/rawForecastWeatherData.json");
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(jsonFile);
    }

    /**
     * Raw forecast weather data<br>
     * General tendency : DETERIORATION<br>
     * Temperatures tendency : DECREASING<br>
     * Barometric pressures tendency : SIGNIFICANT_DECREASE<br>
     * 
     * @return Raw forecast weather data
     * @throws IOException
     */
    public static JsonNode rawForecastWeatherData_SIGNIFICANT_DECREASE() throws IOException {

        File jsonFile = new File(
                "src/test/resources/data/rawForecastWeatherData_SIGNIFICANT_DECREASE.json");
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(jsonFile);
    }

    /**
     * Raw forecast weather data<br>
     * General tendency : DETERIORATION<br>
     * Temperatures tendency : DECREASING<br>
     * Barometric pressures tendency : DECREASING<br>
     * 
     * @return Raw forecast weather data
     * @throws IOException
     */
    public static JsonNode rawForecastWeatherData_DECREASING() throws IOException {

        File jsonFile = new File("src/test/resources/data/rawForecastWeatherData_DECREASING.json");
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(jsonFile);
    }

    /**
     * Raw forecast weather data<br>
     * General tendency : STABLE<br>
     * Temperatures tendency : STABLE<br>
     * Barometric pressures tendency : STABLE<br>
     * 
     * @return Raw forecast weather data
     * @throws IOException
     */
    public static JsonNode rawForecastWeatherData_STABLE() throws IOException {

        File jsonFile = new File("src/test/resources/data/rawForecastWeatherData_STABLE.json");
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(jsonFile);
    }

    /**
     * Raw forecast weather data<br>
     * General tendency : IMPROVEMENT<br>
     * Temperatures tendency : INCREASING<br>
     * Barometric pressures tendency : INCREASING<br>
     * 
     * @return Raw forecast weather data
     * @throws IOException
     */
    public static JsonNode rawForecastWeatherData_INCREASING() throws IOException {

        File jsonFile = new File("src/test/resources/data/rawForecastWeatherData_INCREASING.json");
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(jsonFile);
    }

    /**
     * Raw forecast weather data<br>
     * General tendency : IMPROVEMENT<br>
     * Temperatures tendency : INCREASING<br>
     * Barometric pressures tendency : SIGNIFICANT_INCREASING<br>
     * 
     * @return Raw forecast weather data
     * @throws IOException
     */
    public static JsonNode rawForecastWeatherData_SIGNIFICANT_INCREASING() throws IOException {

        File jsonFile = new File(
                "src/test/resources/data/rawForecastWeatherData_SIGNIFICANT_INCREASING.json");
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(jsonFile);
    }
}
