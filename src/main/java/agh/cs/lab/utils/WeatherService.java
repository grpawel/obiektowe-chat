package agh.cs.lab.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by Paweł Grochola on 23.01.2017.
 */

public class WeatherService {
    private final static String API_KEY = "684771b73e2d01e1f79abca03594f0be";
    private final Downloader downloader;

    public WeatherService(final Downloader downloader) {
        this.downloader = downloader;
    }

    public Weather getWeatherFromCity(final String cityName) {
        final String json = downloader.downloadUrl("http://api.openweathermap.org/data/2.5/weather?q="
                + cityName + "&APPID=" + API_KEY);
        final JsonParser parser = new JsonParser();
        final JsonObject wholeObject = parser.parse(json).getAsJsonObject();
        final JsonObject mainObject = wholeObject.getAsJsonObject("main");
        final Double temperature = mainObject.getAsJsonPrimitive("temp").getAsDouble();
        final Double pressure = mainObject.getAsJsonPrimitive("pressure").getAsDouble();
        final Double clouds = wholeObject.getAsJsonObject("clouds").getAsJsonPrimitive("all").getAsDouble();
        final Double windSpeed = wholeObject.getAsJsonObject("wind").getAsJsonPrimitive("speed").getAsDouble();

        return new Weather(temperature, pressure, clouds, windSpeed);
    }
}
