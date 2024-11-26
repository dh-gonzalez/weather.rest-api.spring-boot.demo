package weather.rest_api.spring_boot.demo.common.dto;

/**
 * The current weather for a city (for a day)
 */
public class CityCurrentWeatherDTO extends AbstractCityWeatherDTO {

    /** Small weather description */
    private String description;

    /** Temperature in degrees Celsius */
    private double temperatureInCelsius;

    /** Wind in Km/h */
    private double windInKmPerHour;

    /** Relative humidity in percentage */
    private int relativeHumidityInPercentage;

    /**
     * Constructor
     * 
     * @param city city
     * @param date date
     * @param description description
     * @param temperatureInCelsius temperature C°
     * @param windInKmPerHour wind Km/h
     * @param relativeHumidityInPercentage relative humidity %
     */
    public CityCurrentWeatherDTO(String city, String date, String description,
            double temperatureInCelsius, double windInKmPerHour, int relativeHumidityInPercentage) {
        this.city = city;
        this.date = date;
        this.description = description;
        this.temperatureInCelsius = temperatureInCelsius;
        this.windInKmPerHour = windInKmPerHour;
        this.relativeHumidityInPercentage = relativeHumidityInPercentage;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTemperatureInCelsius(double temperatureInCelsius) {
        this.temperatureInCelsius = temperatureInCelsius;
    }

    public void setWindInKmPerHour(double windInKmPerHour) {
        this.windInKmPerHour = windInKmPerHour;
    }

    public void setRelativeHumidityInPercentage(int relativeHumidityInPercentage) {
        this.relativeHumidityInPercentage = relativeHumidityInPercentage;
    }

    public String getDescription() {
        return this.description;
    }

    public double getTemperatureInCelsius() {
        return this.temperatureInCelsius;
    }

    public double getWindInKmPerHour() {
        return this.windInKmPerHour;
    }

    public int getRelativeHumidityInPercentage() {
        return this.relativeHumidityInPercentage;
    }

    @Override
    public String toString() {
        return "CityCurrentWeatherDTO [city=" + this.city + ", date=" + this.date + ", description="
                + this.description + ", temperatureInCelsius=" + this.temperatureInCelsius
                + ", windInKmPerHour=" + this.windInKmPerHour + ", relativeHumidityInPercentage="
                + this.relativeHumidityInPercentage + "]";
    }
}
