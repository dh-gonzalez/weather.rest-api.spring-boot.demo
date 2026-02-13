package weather.rest_api.spring_boot.demo.common.dto;

/**
 * The forecast weather for a city
 */
public class CityForecastWeatherDTO extends AbstractCityWeatherDTO {

    /** General tendency */
    private ForecastGeneralTendencyEnum generalTendency = ForecastGeneralTendencyEnum.STABLE;

    /** Temperatures tendency */
    private ForecastTemperaturesTendencyEnum temperaturesTendency =
            ForecastTemperaturesTendencyEnum.STABLE;

    /** Barometric pressures */
    private ForecastBarometricPressuresTendencyEnum barometricPressuresTendency =
            ForecastBarometricPressuresTendencyEnum.STABLE;

    /** Average wind speeds expressed following the Beaufort wind scale */
    private int averageWindSpeedsInBeaufortScale;

    public void setGeneralTendency(ForecastGeneralTendencyEnum generalTendency) {
        this.generalTendency = generalTendency;
    }

    public void setTemperaturesTendency(ForecastTemperaturesTendencyEnum temperaturesTendency) {
        this.temperaturesTendency = temperaturesTendency;
    }

    public void setBarometricPressuresTendency(
            ForecastBarometricPressuresTendencyEnum barometricPressuresTendency) {
        this.barometricPressuresTendency = barometricPressuresTendency;
    }

    public void setAverageWindSpeedsInBeaufortScale(int averageWindSpeedsInBeaufortScale) {
        this.averageWindSpeedsInBeaufortScale = averageWindSpeedsInBeaufortScale;
    }

    public ForecastGeneralTendencyEnum getGeneralTendency() {
        return this.generalTendency;
    }

    public ForecastTemperaturesTendencyEnum getTemperaturesTendency() {
        return this.temperaturesTendency;
    }

    public ForecastBarometricPressuresTendencyEnum getBarometricPressuresTendency() {
        return this.barometricPressuresTendency;
    }

    public int getAverageWindSpeedsInBeaufortScale() {
        return this.averageWindSpeedsInBeaufortScale;
    }

    @Override
    public String toString() {
        return "CityForecastWeatherDTO [city=" + this.city + ", date=" + this.date
                + ", generalTendency=" + this.generalTendency + ", temperaturesTendency="
                + this.temperaturesTendency + ", barometricPressuresTendency="
                + this.barometricPressuresTendency + ", averageWindSpeedsInBeaufortScale="
                + this.averageWindSpeedsInBeaufortScale + "]";
    }
}
