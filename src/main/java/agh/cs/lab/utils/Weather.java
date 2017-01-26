package agh.cs.lab.utils;

/**
 * Created by Paweł Grochola on 23.01.2017.
 */

public class Weather {
    private final Double temperature;
    private final Double pressure;
    private final Double clouds;
    private final Double windSpeed;

    public Weather(final Double temperature, final Double pressure, final Double clouds, final Double windSpeed) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.clouds = clouds;
        this.windSpeed = windSpeed;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Double getPressure() {
        return pressure;
    }

    public Double getClouds() {
        return clouds;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }
}
