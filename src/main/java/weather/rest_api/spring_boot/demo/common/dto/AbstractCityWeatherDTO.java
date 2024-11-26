package weather.rest_api.spring_boot.demo.common.dto;

/**
 * Common parameters of city weather representations
 */
public abstract class AbstractCityWeatherDTO {

    /** City name */
    protected String city;

    /** Observation date format YYYY-MM-DD */
    protected String date;

    public void setCity(String city) {
        this.city = city;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCity() {
        return this.city;
    }

    public String getDate() {
        return this.date;
    }
}
