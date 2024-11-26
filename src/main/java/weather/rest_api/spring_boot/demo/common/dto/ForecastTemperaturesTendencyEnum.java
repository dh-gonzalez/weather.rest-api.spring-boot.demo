package weather.rest_api.spring_boot.demo.common.dto;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Forecast temperatures tendency
 */
public enum ForecastTemperaturesTendencyEnum {

    //
    INCREASING("Increasing"),
    //
    STABLE("Stable"),
    //
    DECREASING("Decreasing");

    private final String tendency;

    private ForecastTemperaturesTendencyEnum(String tendency) {
        this.tendency = tendency;
    }

    @JsonValue
    public String getDisplayName() {
        return this.tendency;
    }
}
