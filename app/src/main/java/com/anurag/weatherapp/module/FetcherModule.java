package com.anurag.weatherapp.module;


import com.anurag.weatherapp.fetcher.WeatherFetcher;
import com.anurag.weatherapp.fetcher.WeatherForecastFetcher;
import com.anurag.weatherapp.fetcher.WeatherForecastNetworkFetcher;
import com.anurag.weatherapp.fetcher.WeatherNetworkFetcher;

import static com.anurag.weatherapp.module.ApplicationModule.getBroadcastManager;
import static com.anurag.weatherapp.module.ApplicationModule.resources;
import static com.anurag.weatherapp.module.NetworkManagerModule.httpUrlConnectionNetworkManager;
import static com.anurag.weatherapp.module.NetworkManagerModule.jsonResponseParser;

/**
 * Module for fetcher/loader to facilitate dependency injection
 */
public class FetcherModule {
    public static WeatherFetcher WeatherFetcher() {
        return new WeatherNetworkFetcher(httpUrlConnectionNetworkManager(),
                jsonResponseParser(), resources(), getBroadcastManager());
    }

    public static WeatherForecastFetcher WeatherForecastFetcher() {
        return new WeatherForecastNetworkFetcher(httpUrlConnectionNetworkManager(),
                jsonResponseParser(), resources());
    }
}
