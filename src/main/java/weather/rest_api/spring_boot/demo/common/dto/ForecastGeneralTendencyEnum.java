package weather.rest_api.spring_boot.demo.common.dto;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Forecast general tendency
 */
public enum ForecastGeneralTendencyEnum {

    //
    IMPROVEMENT("Improvement"),
    //
    STABLE("Stable"),
    //
    DETERIORATION("Deterioration");

    private final String tendency;

    private ForecastGeneralTendencyEnum(String tendency) {
        this.tendency = tendency;
    }

    @JsonValue
    public String getDisplayName() {
        return this.tendency;
    }
}
