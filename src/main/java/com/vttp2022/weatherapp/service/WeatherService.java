package com.vttp2022.weatherapp.service;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.vttp2022.weatherapp.model.Weather;

@Service
public class WeatherService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);
    
    private static String URL ="https://api.openweathermap.org/data/2.5/weather";

    @Value("${open.weather.map}")
    private String apiKey;

    private boolean haskey;

    @PostConstruct
    private void init(){
        haskey = null != apiKey;
        logger.info(">>> API KEY : "+ haskey);
        
    }

    public Optional<Weather> getWeather(String city){
        String weatheUrl = UriComponentsBuilder.fromUriString(URL)
                            .queryParam("q", city.replaceAll(" ", "+"))
                            .queryParam("units", "metric")
                            .queryParam("appid", apiKey)
                            .toUriString();

        logger.info(">>> Complete Weather URI API address : " + weatheUrl);
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;

        try{
            resp = template.getForEntity(weatheUrl, String.class);
            Weather w = Weather.create(resp.getBody());
            logger.info(">>> Complete Weather URI API address : " + weatheUrl);
        
            return Optional.of(w);
        }catch(Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
