package agh.cs.lab.users.chatbot;

import agh.cs.lab.messages.Message;
import agh.cs.lab.utils.Weather;
import agh.cs.lab.utils.WeatherService;
import agh.cs.lab.utils.LevenshteinMatcher;

/**
 * Created by Paweł Grochola on 23.01.2017.
 */
public class QuestionWeather implements IQuestion {
    private static final Integer MAX_DISTANCE = 4;
    private static final String MATCHING_QUESTION = "jaka jest pogoda w krakowie?";
    private final WeatherService weatherService;
    private static final Double CELSIUS_TO_KELVIN = 273.15;

    public QuestionWeather(final WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Override
    public Boolean matches(final Message message) {
        return new LevenshteinMatcher().matches(MATCHING_QUESTION, message.getMessage().toLowerCase(), MAX_DISTANCE);
    }

    @Override
    public String response(final Message message) {
        final Weather weather = weatherService.getWeatherFromCity("Kraków");
        return "Pogoda w Krakowie : "
                + (weather.getTemperature() - CELSIUS_TO_KELVIN) + " C, "
                + weather.getPressure() + " hPa, "
                + "wiatr " + weather.getWindSpeed() + " m/s, "
                + "zachmurzenie " + weather.getClouds() + "%";
    }
}
