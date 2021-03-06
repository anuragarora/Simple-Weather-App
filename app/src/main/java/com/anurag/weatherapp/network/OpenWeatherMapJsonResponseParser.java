package com.anurag.weatherapp.network;

import com.anurag.weatherapp.model.weather.WeatherConditions;
import com.anurag.weatherapp.model.weather.WeatherForecastResponse;
import com.anurag.weatherapp.model.weather.WeatherMain;
import com.anurag.weatherapp.model.weather.WeatherParams;
import com.anurag.weatherapp.model.weather.WeatherResponse;
import com.anurag.weatherapp.model.weather.WeatherSys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.anurag.weatherapp.model.weather.WeatherConditions.WEATHER_CONDITIONS_DESCRIPTION_KEY;
import static com.anurag.weatherapp.model.weather.WeatherConditions.WEATHER_CONDITIONS_ICON_KEY;
import static com.anurag.weatherapp.model.weather.WeatherConditions.WEATHER_CONDITIONS_WEATHER_MAIN_KEY;
import static com.anurag.weatherapp.model.weather.WeatherMain.WEATHER_MAIN_TEMPERATURE_KEY;
import static com.anurag.weatherapp.model.weather.WeatherMain.WEATHER_MAIN_TEMPERATURE_MAX_KEY;
import static com.anurag.weatherapp.model.weather.WeatherMain.WEATHER_MAIN_TEMPERATURE_MIN_KEY;
import static com.anurag.weatherapp.model.weather.WeatherParams.WEATHER_PARAMS_MAIN_KEY;
import static com.anurag.weatherapp.model.weather.WeatherParams.WEATHER_PARAMS_NAME_KEY;
import static com.anurag.weatherapp.model.weather.WeatherParams.WEATHER_PARAMS_SYS_KEY;
import static com.anurag.weatherapp.model.weather.WeatherParams.WEATHER_PARAMS_WEATHER_KEY;
import static com.anurag.weatherapp.model.weather.WeatherResponse.WEATHER_RESPONSE_CODE_KEY;
import static com.anurag.weatherapp.model.weather.WeatherResponse.WEATHER_RESPONSE_MESSAGE_KEY;
import static com.anurag.weatherapp.model.weather.WeatherResponse.WEATHER_RESPONSE_WEATHER_PARAMETERS_LIST_KEY;
import static com.anurag.weatherapp.model.weather.WeatherSys.WEATHER_SYS_SUNRISE_KEY;
import static com.anurag.weatherapp.model.weather.WeatherSys.WEATHER_SYS_SUNSET_KEY;

/**
 * JsonParsing class for getWeather and getWeatherForecast response strings
 */
public class OpenWeatherMapJsonResponseParser implements OpenWeatherMapResponseParser {
    @Override
    public WeatherResponse parseWeatherResponse(String jsonString) throws JSONException {
        if (jsonString == null || jsonString.equals("")){
            throw new JSONException("jsonString is null or empty");
        } else {
            JSONObject jsonObject = new JSONObject(jsonString);
            return new WeatherResponse.Builder()
                    .mAccuracy(jsonObject.getString(WEATHER_RESPONSE_MESSAGE_KEY))
                    .mCod(jsonObject.getString(WEATHER_RESPONSE_CODE_KEY))
                    .mWeatherList(getWeatherParamsList(jsonObject.getJSONArray(WEATHER_RESPONSE_WEATHER_PARAMETERS_LIST_KEY)))
                    .build();
        }
    }

    /**
     * NOT IMPLEMENTED in weatherActivity!
     */
    @Override
    public WeatherForecastResponse parseWeatherForecastResponse(String jsonString) throws JSONException {
        return null;
    }

    private List<WeatherParams> getWeatherParamsList(JSONArray weatherListJSONArray) throws JSONException {
        JSONObject cityWeather = weatherListJSONArray.getJSONObject(0);
        List<WeatherParams> weatherList = new ArrayList<>();
        weatherList.add(new WeatherParams.Builder()
                .mName(cityWeather.getString(WEATHER_PARAMS_NAME_KEY))
                .mMain(getWeatherMain(cityWeather.getJSONObject(WEATHER_PARAMS_MAIN_KEY)))
                .mWeather(getWeatherCondition(cityWeather.getJSONArray(WEATHER_PARAMS_WEATHER_KEY)))
                .mSys(getWeatherSys(cityWeather.getJSONObject(WEATHER_PARAMS_SYS_KEY)))
                .build());

        return weatherList;
    }

    private WeatherSys getWeatherSys(JSONObject weatherSysObject) throws JSONException {
        return new WeatherSys.Builder()
                .mSunrise(weatherSysObject.has(WEATHER_SYS_SUNRISE_KEY) ?
                        weatherSysObject.getLong(WEATHER_SYS_SUNRISE_KEY) : 0L)

                .mSunset(weatherSysObject.has(WEATHER_SYS_SUNSET_KEY) ?
                        weatherSysObject.getLong(WEATHER_SYS_SUNSET_KEY) : 0L)

                .build();
    }

    private List<WeatherConditions> getWeatherCondition(JSONArray weather) throws JSONException {
        JSONObject weatherCondition = weather.getJSONObject(0);
        final WeatherConditions weatherConditions = new WeatherConditions.Builder()
                .mWeatherMain(weatherCondition.getString(WEATHER_CONDITIONS_WEATHER_MAIN_KEY))
                .mWeatherDescription(weatherCondition.getString(WEATHER_CONDITIONS_DESCRIPTION_KEY))
                .mWeatherIcon(weatherCondition.getString(WEATHER_CONDITIONS_ICON_KEY))
                .build();

        return new ArrayList<WeatherConditions>() {{
            add(weatherConditions);
        }};
    }

    private WeatherMain getWeatherMain(JSONObject weatherMain) throws JSONException {
        return new WeatherMain.Builder()
                .mMaxTemp(weatherMain.getDouble(WEATHER_MAIN_TEMPERATURE_MAX_KEY))
                .mMinTemp(weatherMain.getDouble(WEATHER_MAIN_TEMPERATURE_MIN_KEY))
                .mTemp(weatherMain.getDouble(WEATHER_MAIN_TEMPERATURE_KEY))
                .build();
    }
}
