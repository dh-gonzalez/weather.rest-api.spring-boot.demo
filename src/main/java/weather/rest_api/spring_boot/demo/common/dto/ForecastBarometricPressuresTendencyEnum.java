package weather.rest_api.spring_boot.demo.common.dto;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Forecast temperatures tendency
 */
public enum ForecastBarometricPressuresTendencyEnum {

    //
    SIGNIFICANT_INCREASE("Significant increase"),
    //
    INCREASING("Increasing"),
    //
    STABLE("Stable"),
    //
    DECREASING("Decreasing"),
    //
    SIGNIFICANT_DECREASE("Significant decrease");

    private final String tendency;

    private ForecastBarometricPressuresTendencyEnum(String tendency) {
        this.tendency = tendency;
    }

    @JsonValue
    public String getDisplayName() {
        return this.tendency;
    }
}
