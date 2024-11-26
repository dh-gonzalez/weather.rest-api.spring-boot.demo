package weather.rest_api.spring_boot.demo.common.utils;

import java.util.List;
import weather.rest_api.spring_boot.demo.common.dto.ForecastBarometricPressuresTendencyEnum;
import weather.rest_api.spring_boot.demo.common.dto.ForecastGeneralTendencyEnum;
import weather.rest_api.spring_boot.demo.common.dto.ForecastTemperaturesTendencyEnum;

/**
 * Usefull methods for weather
 */
public final class WeatherUtils {

    /**
     * Compute general tendency based on average temperatures delta and average pressures delta
     * 
     * @param averageTemperaturesDelta average temperatures delta °C
     * @param averagePressuresDelta average pressures delta mb
     * @param temperatureThreshold temperature threshold °C
     * @param pressureThreshold pressurethreshold mb
     * @return general tendency
     */
    public static ForecastGeneralTendencyEnum computeGeneralTendency(
            double averageTemperaturesDelta, double averagePressuresDelta,
            double temperatureThreshold, double pressureThreshold) {

        if (averageTemperaturesDelta > temperatureThreshold
                && averagePressuresDelta > pressureThreshold) {
            return ForecastGeneralTendencyEnum.IMPROVEMENT;
        } else if (averageTemperaturesDelta <= -temperatureThreshold
                && averagePressuresDelta <= -pressureThreshold) {
            return ForecastGeneralTendencyEnum.DETERIORATION;
        }

        return ForecastGeneralTendencyEnum.STABLE;
    }

    /**
     * Compute temperatures tendency based on average temperatures delta
     * 
     * @param averageTemperaturesDelta average temperatures delta °C
     * @param temperatureThreshold temperature threshold °C
     * @return temperatures tendency
     */
    public static ForecastTemperaturesTendencyEnum computeTemperaturesTendency(
            double averageTemperaturesDelta, double temperatureThreshold) {

        if (averageTemperaturesDelta > temperatureThreshold) {
            return ForecastTemperaturesTendencyEnum.INCREASING;
        } else if (averageTemperaturesDelta <= -temperatureThreshold) {
            return ForecastTemperaturesTendencyEnum.DECREASING;
        }

        return ForecastTemperaturesTendencyEnum.STABLE;
    }

    /**
     * Compute pressures tendency based on average pressures delta
     * 
     * @param averagePressuresDelta average pressures delta mb
     * @param pressureLowThreshold pressure low threshold mb
     * @param pressureHighThreshold pressure high threshold mb
     * @return pressures tendency
     */
    public static ForecastBarometricPressuresTendencyEnum computePressuresTendency(
            double averagePressuresDelta, double pressureLowThreshold,
            double pressureHighThreshold) {

        if (averagePressuresDelta > pressureHighThreshold) {
            return ForecastBarometricPressuresTendencyEnum.SIGNIFICANT_INCREASE;
        } else if (averagePressuresDelta > pressureLowThreshold) {
            return ForecastBarometricPressuresTendencyEnum.INCREASING;
        } else if (averagePressuresDelta > -pressureLowThreshold) {
            return ForecastBarometricPressuresTendencyEnum.STABLE;
        } else if (averagePressuresDelta > -pressureHighThreshold) {
            return ForecastBarometricPressuresTendencyEnum.DECREASING;
        }
        return ForecastBarometricPressuresTendencyEnum.SIGNIFICANT_DECREASE;
    }

    /**
     * Compute average wind speeds according to Beaufort wind scale
     * 
     * @param windSpeeds wind speeds Km/h
     * @return average wind speeds Km/h
     */
    public static int computeAverageWindSpeedsInBeaufortScale(List<Double> windSpeeds) {

        double averageWindSpeed =
                windSpeeds.stream().mapToDouble(values -> values).average().orElseThrow();

        if (averageWindSpeed < 1) {
            return 0;
        } else if (averageWindSpeed <= 5) {
            return 1;
        } else if (averageWindSpeed <= 11) {
            return 2;
        } else if (averageWindSpeed <= 19) {
            return 3;
        } else if (averageWindSpeed <= 28) {
            return 4;
        } else if (averageWindSpeed <= 38) {
            return 5;
        } else if (averageWindSpeed <= 49) {
            return 6;
        } else if (averageWindSpeed <= 61) {
            return 7;
        } else if (averageWindSpeed <= 74) {
            return 8;
        } else if (averageWindSpeed <= 88) {
            return 9;
        } else if (averageWindSpeed <= 102) {
            return 10;
        } else if (averageWindSpeed <= 117) {
            return 11;
        }
        return 12;
    }
}
