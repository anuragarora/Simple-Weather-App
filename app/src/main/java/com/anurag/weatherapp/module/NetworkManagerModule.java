package com.anurag.weatherapp.module;

import com.anurag.weatherapp.network.HttpUrlConnectionNetworkManager;
import com.anurag.weatherapp.network.NetworkManager;
import com.anurag.weatherapp.network.OpenWeatherMapJsonResponseParser;
import com.anurag.weatherapp.network.OpenWeatherMapResponseParser;

import static com.anurag.weatherapp.module.ApplicationModule.resources;

/**
 * Network Manager Module to facilitate injecting network manager and JSONParser.
 */
class NetworkManagerModule {
    static NetworkManager httpUrlConnectionNetworkManager(){
        return new HttpUrlConnectionNetworkManager(resources());
    }

    static OpenWeatherMapResponseParser jsonResponseParser(){
        return new OpenWeatherMapJsonResponseParser();
    }
}
