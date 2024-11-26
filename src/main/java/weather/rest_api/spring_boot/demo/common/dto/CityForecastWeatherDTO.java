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
        return generalTendency;
    }

    public ForecastTemperaturesTendencyEnum getTemperaturesTendency() {
        return temperaturesTendency;
    }

    public ForecastBarometricPressuresTendencyEnum getBarometricPressuresTendency() {
        return barometricPressuresTendency;
    }

    public int getAverageWindSpeedsInBeaufortScale() {
        return averageWindSpeedsInBeaufortScale;
    }

    @Override
    public String toString() {
        return "CityForecastWeatherDTO [city=" + city + ", date=" + date + ", generalTendency="
                + generalTendency + ", temperaturesTendency=" + temperaturesTendency
                + ", barometricPressuresTendency=" + barometricPressuresTendency
                + ", averageWindSpeedsInBeaufortScale=" + averageWindSpeedsInBeaufortScale + "]";
    }
}
